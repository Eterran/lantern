package GUI;

import java.util.Stack;

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
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

public class Sidebar {
    private static VBox[] pages = new VBox[10];
    private static Stack<Integer> sidebarHistory = new Stack<Integer>();
    private static int currentPage = 1;

    public static void showHomeScene2(Stage stg){
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
        BorderStroke borderStroke = new BorderStroke(
            Paint.valueOf(color.SIDEBAR.getCode()),
            BorderStrokeStyle.SOLID,
            new CornerRadii(0),
            new BorderWidths(1)
        );
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
        Button tab1 = new Button("Profile");
        Button tab2 = new Button("Discussion Page");
        Button tab3 = new Button("Global Leaderboard");
        Button tab4 = null;
        Button tab5 = null;

        Separator separator1 = new Separator(Orientation.HORIZONTAL);
        Separator separator2 = new Separator(Orientation.HORIZONTAL);
        Separator separator3 = new Separator(Orientation.HORIZONTAL);
        VBox sep1Box = new VBox(0);
        sep1Box.getChildren().add(separator1);
        VBox sep2Box = new VBox(0);
        sep2Box.getChildren().add(separator2);
        VBox sep3Box = new VBox(0);
        sep3Box.getChildren().add(separator3);
        sep1Box.setAlignment(Pos.CENTER);
        sep2Box.setAlignment(Pos.CENTER);
        sep3Box.setAlignment(Pos.CENTER);

        VBox profileBox = Profile.loadProfileTab();
        VBox discussionBox = new VBox();
        VBox leaderboardBox = GlobalLeaderboard.globalLeaderBoardTab();
        VBox box4 = new VBox(10);
        VBox box5 = new VBox(10);
        profileBox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        discussionBox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        leaderboardBox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        box4.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        box5.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        pages[1] = profileBox;
        pages[2] = discussionBox;
        pages[3] = leaderboardBox;
        pages[4] = box4;
        pages[5] = box5;
        StackPane stackPane = new StackPane();
        stackPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        HBox.setHgrow(stackPane, Priority.ALWAYS);
        stackPane.getChildren().addAll(profileBox, discussionBox, leaderboardBox, box4, box5);
        //TEMP
        discussionBox.getChildren().add(new Label("Discussion Page"));
        box4.getChildren().add(new Label("Box 4"));
        box5.getChildren().add(new Label("Box 5"));
        //
        setOneVisible(1);
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
        if(AccessManager.hasAccess("Student", AccessManager.ContentType.STUDENT)){
            tab4 = new Button("Quizzes");
            tab4.setOnAction(e -> {
                push_SidebarHistory(4);
                setOneVisible(4);
            });
        } else if(AccessManager.hasAccess("Educator", AccessManager.ContentType.EDUCATOR)){
            tab4 = new Button("Create Quizzes");
            tab5 = new Button("Create Events");
            box4 = EducatorCreateQuiz.tabCreateQuiz();
            tab4.setOnAction(e -> {
                push_SidebarHistory(4);
                setOneVisible(4);
            });
            tab5.setOnAction(e -> {
                push_SidebarHistory(5);
                setOneVisible(5);
            });
        } else if(AccessManager.hasAccess("Parent", AccessManager.ContentType.PARENT)){
            tab4 = new Button("Make Bookings");
            tab4.setOnAction(e -> {
                push_SidebarHistory(4);
                setOneVisible(4);
            });
        }
        layout1.getChildren().addAll(tabs, stackPane);
        tab1.setMaxWidth(Double.MAX_VALUE);
        tab2.setMaxWidth(Double.MAX_VALUE);
        tab3.setMaxWidth(Double.MAX_VALUE);
        tabs.getChildren().addAll(backButton, tab1, sep1Box, tab2, sep2Box, tab3);
        root.setCenter(layout1);
        Scene scene1 = new Scene(root, 1000, 650);
        tab1.getStyleClass().add("sidebar_button");
        tab2.getStyleClass().add("sidebar_button");
        tab3.getStyleClass().add("sidebar_button");
        if(tab4 != null){
            tab4.getStyleClass().add("sidebar_button");
            tabs.getChildren().addAll(sep3Box, tab4);
            tab4.setMaxWidth(Double.MAX_VALUE);
        }
        if(tab5 != null){
            tab5.getStyleClass().add("sidebar_button");
            Separator separator4 = new Separator();
            separator4.setOrientation(javafx.geometry.Orientation.HORIZONTAL);
            HBox sep4Box = new HBox(0);
            sep4Box.getChildren().add(separator4);
            sep4Box.setMaxWidth(Double.MAX_VALUE);
            sep4Box.setAlignment(Pos.CENTER);
            HBox.setHgrow(sep4Box, Priority.ALWAYS);
            tabs.getChildren().addAll(sep4Box, tab5);
            tab5.setMaxWidth(Double.MAX_VALUE);
        }
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
    private static void push_SidebarHistory(int index){
        if(sidebarHistory.isEmpty() || sidebarHistory.peek() != index)
            sidebarHistory.push(index);
    }
}
