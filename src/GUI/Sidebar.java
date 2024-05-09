package GUI;

import java.util.Stack;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.management.ValueExp;

import Database.User;
import javafx.animation.TranslateTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Sidebar {
    private static VBox[] pages = new VBox[10];
    private static Stack<Integer> sidebarHistory = new Stack<Integer>();
    private static int currentPage = 1;
    public final static AccessManager accessManager = new AccessManager();
    final static AtomicBoolean isSidebarRetracted = new AtomicBoolean(false);

    public static void showHomeScene(Stage stg){
        Lantern.Clear_History();
        BorderPane root = new BorderPane();
        LinearGradient gradientRoot = new LinearGradient(
            0.3,
            0,
            1,
            0,
            true,
            CycleMethod.NO_CYCLE,
            new Stop(0, Color.web(color.SIDEBAR.getCode())),
            new Stop(1, Color.web(color.ACCENT2.getCode()))
        );
        root.setBackground(new Background(new BackgroundFill(gradientRoot, new CornerRadii(0), Insets.EMPTY)));
        Image image = new Image("resources/assets/back_arrow.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        Button backButton = new Button();
        backButton.setGraphic(imageView);
        backButton.getStyleClass().add("back_button");
        HBox layout1 = new HBox();
        layout1.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        layout1.setBackground(new Background(new BackgroundFill(Color.web(color.BACKGROUND.getCode()), new CornerRadii(0), Insets.EMPTY)));
        VBox tabs = new VBox(4);
        LinearGradient gradient = new LinearGradient(
            0.6,
            0,
            1,
            0,
            true,
            CycleMethod.NO_CYCLE,
            new Stop(0, Color.web(color.SIDEBAR.getCode())),
            new Stop(1, Color.web(color.BACKGROUND.getCode()))
        );
        tabs.setBackground(new Background(new BackgroundFill(gradient, new CornerRadii(0), Insets.EMPTY)));
        //Border border = new Border(borderStroke);
        //tabs.setBorder(border);
        Button retractButton = new Button();
        Image sidebarImage = new Image("resources/assets/sidebar_icon.png");
        ImageView sideBarImageView = new ImageView(sidebarImage);
        sideBarImageView.setFitHeight(30);
        sideBarImageView.setFitWidth(30);
        retractButton.setGraphic(sideBarImageView);
        retractButton.getStyleClass().add("back_button");
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000), tabs);
        tt.setCycleCount(1);
        tt.setAutoReverse(false);
        DoubleProperty sidebarWidth = new SimpleDoubleProperty();
        sidebarWidth.set(tabs.getWidth());
        retractButton.setOnAction(e -> {
            if(isSidebarRetracted.get()){
                tt.setToX(0);
                sidebarWidth.set(tabs.getWidth());
            } else {
                tt.setToX(-tabs.getWidth() + 60);
                sidebarWidth.set(60);
            }
            isSidebarRetracted.set(!isSidebarRetracted.get());
            tt.play();
        });
        
        tabs.getChildren().add(retractButton);
        Button tab1 = new Button("Profile");
        Button tab2 = new Button("Quiz");
        Button tab3 = new Button("Global Leaderboard");

        VBox profileBox = Profile.loadProfileTab(User.getCurrentUser());
        VBox discussionBox = QuizPage.quizPageTab();
        VBox leaderboardBox = GlobalLeaderboard.globalLeaderBoardTab();
        VBox box4 = new VBox(10);
        VBox box5 = new VBox(10);

        profileBox.prefWidthProperty().bind(sidebarWidth);
        discussionBox.prefWidthProperty().bind(sidebarWidth);
        leaderboardBox.prefWidthProperty().bind(sidebarWidth);
        box4.prefWidthProperty().bind(sidebarWidth);
        box5.prefWidthProperty().bind(sidebarWidth);
        profileBox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        discussionBox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        leaderboardBox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        box4.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        box5.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        
        StackPane stackPane = new StackPane();
        stackPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        HBox.setHgrow(stackPane, Priority.ALWAYS);
        VBox.setVgrow(stackPane, Priority.ALWAYS);
        stackPane.getChildren().addAll(profileBox, discussionBox, leaderboardBox, box4, box5);

        backButton.setOnAction(e -> {
            goBackSidebar();
        });
        tab1.setOnAction(e -> {
            push_SidebarHistory(1);
            setOneVisible(1);
        });
        tab2.setOnAction(e -> {
            push_SidebarHistory(2);
            setOneVisible(2);
        });
        tab3.setOnAction(e -> {
            push_SidebarHistory(3);
            setOneVisible(3);
        });

        pages[1] = profileBox;
        pages[2] = discussionBox;
        pages[3] = leaderboardBox;
        pages[4] = box4;
        pages[5] = box5;
        setOneVisible(1);
        layout1.getChildren().addAll(tabs, stackPane);
        tab1.setMaxWidth(Double.MAX_VALUE);
        tab2.setMaxWidth(Double.MAX_VALUE);
        tab3.setMaxWidth(Double.MAX_VALUE);
        HBox backAndRetract = new HBox();
        backAndRetract.getChildren().addAll(backButton, retractButton);
        HBox.setHgrow(backButton, Priority.ALWAYS);
        backButton.setMaxWidth(Double.MAX_VALUE);
        tabs.getChildren().addAll(backAndRetract, tab1, createSeparator(), tab2, createSeparator(), tab3);
        accessManager.getAccessibleButtons(accessManager.getUserRole(User.getCurrentUser())).forEach(buttonSupplier -> {
            tabs.getChildren().add(createSeparator());
            tabs.getChildren().add(buttonSupplier.get());
        });
        root.setCenter(layout1);
        Scene scene1 = new Scene(root, 1000, 650);
        tab1.getStyleClass().add("sidebar_button");
        tab2.getStyleClass().add("sidebar_button");
        tab3.getStyleClass().add("sidebar_button");
        // if(tab4 != null){
        //     tab4.getStyleClass().add("sidebar_button");
        //     tabs.getChildren().addAll(sep3Box, tab4);
        //     tab4.setMaxWidth(Double.MAX_VALUE);
        // }
        // if(tab5 != null){
        //     tab5.getStyleClass().add("sidebar_button");
        //     Separator separator4 = new Separator();
        //     separator4.setOrientation(javafx.geometry.Orientation.HORIZONTAL);
        //     HBox sep4Box = new HBox(0);
        //     sep4Box.getChildren().add(separator4);
        //     sep4Box.setMaxWidth(Double.MAX_VALUE);
        //     sep4Box.setAlignment(Pos.CENTER);
        //     HBox.setHgrow(sep4Box, Priority.ALWAYS);
        //     tabs.getChildren().addAll(sep4Box, tab5);
        //     tab5.setMaxWidth(Double.MAX_VALUE);
        // }
        scene1.getStylesheets().add("resources/style.css");
        stg.setScene(scene1);
    }
    public static void setOneVisible(int index){
        for(int i = 1; i <= pages.length; i++){
            if(pages[i] == null)
                break;
            if(i == index){
                pages[i].setVisible(true);
            } else {
                pages[i].setVisible(false);
            }
        }
        currentPage = index;
        if(sidebarHistory.isEmpty()){
            sidebarHistory.push(index);
        }
    }
    private static boolean goBackSidebar(){
        if(!sidebarHistory.isEmpty() && currentPage == sidebarHistory.peek()){
            sidebarHistory.pop();
        }
        if(!sidebarHistory.isEmpty()){
            setOneVisible(sidebarHistory.pop());
            return true;
        }
        return false;
    }
    public static void push_SidebarHistory(int index){
        if(sidebarHistory.isEmpty() || sidebarHistory.peek() != index)
            sidebarHistory.push(index);
    }
    public static AccessManager getAccessManager(){
        return accessManager;
    }
    private static VBox createSeparator(){
        Separator separator = new Separator();
        separator.setOrientation(Orientation.HORIZONTAL);
        VBox sepBox = new VBox(0);
        sepBox.getChildren().add(separator);
        sepBox.setAlignment(Pos.CENTER);
        return sepBox;
    }
}
