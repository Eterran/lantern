package GUI;

import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import Database.Login_Register;
import Database.User;

public class FriendGraph {
    private static Connection conn = Lantern.getConn();
    private static final double NODE_RADIUS = 20;
    private static final double REPULSION_CONSTANT = 50000;
    private static final double ATTRACTION_CONSTANT = 0.01;
    private static final double DAMPING = 0.5;
    private static Map<String, Set<String>> connections = new HashMap<>();

    public static VBox createFriendGraph() {
        //System.out.println("Creating friend graph...");
        VBox graphContainer = new VBox();
        Text title = new Text("Friend Graph");
        title.getStyleClass().add("title");
        graphContainer.getChildren().add(title);
        Pane graphPane = new Pane();
        graphPane.prefWidthProperty().bind(graphContainer.widthProperty());
        graphPane.prefHeightProperty().bind(graphContainer.heightProperty());
        graphContainer.getChildren().add(graphPane);
    
        Map<String, Circle> userNodes = new HashMap<>();
        Map<String, StackPane> userLabels = new HashMap<>();
        int[] totalNodes = {0};

        try (ResultSet rs = getFriends()) {
            while (rs.next()) {
                String mainUser = getUsername(rs.getInt("main_id"));
                String completeUser = rs.getString("complete");

                if (!userNodes.containsKey(mainUser)) totalNodes[0]++;
                if (!userNodes.containsKey(completeUser)) totalNodes[0]++;
                connections.computeIfAbsent(mainUser, k -> new HashSet<>()).add(completeUser);
                connections.computeIfAbsent(completeUser, k -> new HashSet<>()).add(mainUser);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int[] index = {0};

        try (ResultSet rs = getFriends()) {
            while (rs.next()) {
                String mainUser = getUsername(rs.getInt("main_id"));
                String completeUser = rs.getString("complete");

                Circle mainUserNode = userNodes.computeIfAbsent(mainUser, k -> createUserNode(k, graphPane, userLabels, index[0]++, totalNodes[0]));
                Circle completeUserNode = userNodes.computeIfAbsent(completeUser, k -> createUserNode(k, graphPane, userLabels, index[0]++, totalNodes[0]));

                Line line = new Line();
                line.startXProperty().bind(mainUserNode.centerXProperty());
                line.startYProperty().bind(mainUserNode.centerYProperty());
                line.endXProperty().bind(completeUserNode.centerXProperty());
                line.endYProperty().bind(completeUserNode.centerYProperty());
                graphPane.getChildren().add(0, line);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        applyForceDirectedLayout(userNodes, userLabels, graphPane);
    
        graphPane.setLayoutX((graphContainer.getWidth() - graphPane.getPrefWidth()) / 2);
        graphPane.setLayoutY((graphContainer.getHeight() - graphPane.getPrefHeight()) / 2);
    
        addZoomAndPan(graphPane);
        graphContainer.getStylesheets().add("resources/style.css");
        graphContainer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        VBox.setVgrow(graphContainer, javafx.scene.layout.Priority.ALWAYS);

        Button resetViewButton = new Button("Reset View");
        resetViewButton.setOnAction(event -> {
            graphPane.setScaleX(1.0);
            graphPane.setScaleY(1.0);

            graphPane.setTranslateX(0);
            graphPane.setTranslateY(0);

            Sidebar.setBox7(createFriendGraph());
        });
        resetViewButton.getStyleClass().add("login_button");
        graphContainer.getChildren().add(resetViewButton);

        return graphContainer;
    }

    private static Circle createUserNode(String username, Pane graphPane, Map<String, StackPane> userLabels, int index, int totalNodes) {
        Circle circle = new Circle(NODE_RADIUS);

        // circle.setOnMouseClicked(event -> {
        //     Sidebar.setBox7(Profile.loadProfileTab(Login_Register.getUser(username, conn)));
        // });

        double angle = 2 * Math.PI * index / totalNodes;
        double centerX = graphPane.widthProperty().get() / 2 + 200 * Math.cos(angle);
        double centerY = graphPane.heightProperty().get() / 2 + 200 * Math.sin(angle);
        circle.setCenterX(centerX);
        circle.setCenterY(centerY);
    
        Text text = new Text(username);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(circle, text);
        stackPane.setLayoutX(circle.getCenterX() - NODE_RADIUS);
        stackPane.setLayoutY(circle.getCenterY() - NODE_RADIUS);
    
        userLabels.put(username, stackPane);
        graphPane.getChildren().add(stackPane);

        stackPane.setOnMouseClicked(event -> {
            Sidebar.setothersProfile(Profile.loadProfileTab(Login_Register.getUser(username, conn)));
            System.out.println("Current user:" + User.getCurrentUser().getUsername() + " clicked on " + username + "'s profile.");
        });

        circle.getStyleClass().add("user_node");
        return circle;
    }

    private static void applyForceDirectedLayout(Map<String, Circle> userNodes, Map<String, StackPane> userLabels, Pane graphPane) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(20), event -> {
            for (Map.Entry<String, Circle> entry : userNodes.entrySet()) {
                Circle circle = entry.getValue();
                double forceX = 0;
                double forceY = 0;
    
                for (Map.Entry<String, Circle> otherEntry : userNodes.entrySet()) {
                    if (entry == otherEntry) continue;
                    Circle otherCircle = otherEntry.getValue();
                    double dx = circle.getCenterX() - otherCircle.getCenterX();
                    double dy = circle.getCenterY() - otherCircle.getCenterY();
                    double distance = Math.sqrt(dx * dx + dy * dy);
                    if (distance == 0) continue;
                    double repulsion = REPULSION_CONSTANT / (distance * distance);
    
                    forceX += repulsion * dx / distance;
                    forceY += repulsion * dy / distance;
    
                    if (areConnected(entry.getKey(), otherEntry.getKey())) { // only connected nodes are attracted
                        double attraction = ATTRACTION_CONSTANT * distance;
                        forceX -= attraction * dx / distance;
                        forceY -= attraction * dy / distance;
                    }
                }
                circle.setCenterX(circle.getCenterX() + forceX * DAMPING);
                circle.setCenterY(circle.getCenterY() + forceY * DAMPING);
    
                StackPane label = userLabels.get(entry.getKey());
                label.setLayoutX(circle.getCenterX() - NODE_RADIUS);
                label.setLayoutY(circle.getCenterY() - NODE_RADIUS);
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private static void addZoomAndPan(Pane graphPane) {
        graphPane.setOnScroll((ScrollEvent event) -> {
            double zoomFactor = 1.05;
            double deltaY = event.getDeltaY();
            if (deltaY < 0) {
                zoomFactor = 0.95;
            }
            double newScaleX = graphPane.getScaleX() * zoomFactor;
            double newScaleY = graphPane.getScaleY() * zoomFactor;
        
            if (newScaleX > 2.0 || newScaleY > 2.0) {
                newScaleX = 2.0;
                newScaleY = 2.0;
            }
        
            if (newScaleX < 0.5 || newScaleY < 0.5) {
                newScaleX = 0.5;
                newScaleY = 0.5;
            }
        
            graphPane.setScaleX(newScaleX);
            graphPane.setScaleY(newScaleY);
            event.consume();
        });
    
        graphPane.setOnMousePressed(event -> {
            graphPane.setUserData(new double[]{event.getSceneX(), event.getSceneY(), graphPane.getTranslateX(), graphPane.getTranslateY()});
        });
    
        graphPane.setOnMouseDragged(event -> {
            double[] data = (double[]) graphPane.getUserData();
            double deltaX = event.getSceneX() - data[0];
            double deltaY = event.getSceneY() - data[1];
            graphPane.setTranslateX(data[2] + deltaX);
            graphPane.setTranslateY(data[3] + deltaY);
        });
    }

    private static ResultSet getFriends() throws SQLException {
        String sql = "SELECT * FROM friends WHERE complete IS NOT NULL";
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(sql);
    }
    private static String getUsername(int main_id) throws SQLException {
        String sql = "SELECT username FROM user WHERE id = " + main_id;
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        rs.next();
        return rs.getString("username");
    }
    private static boolean areConnected(String user1, String user2) {
        return connections.getOrDefault(user1, Collections.emptySet()).contains(user2);
    }
}