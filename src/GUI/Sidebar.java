package GUI;

import java.util.Stack;

import GUI.GlobalLeaderboard1.GlobalLBgui;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class Sidebar {
    private static VBox[] pages = new VBox[10];
    private static Stack<Integer> sidebarHistory = new Stack<Integer>();
    private static int currentPage = 1;

    public static void showHomeScene2(Stage stg){
        Lantern.Clear_History();
        BorderPane root = new BorderPane();
        Button backButton = new Button("Back");
        HBox layout1 = new HBox();
        layout1.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        layout1.setBackground(new Background(new BackgroundFill(Color.web(color.BACKGROUND.getCode()), new CornerRadii(0), Insets.EMPTY)));
        VBox tabs = new VBox(10);
        BorderStroke borderStroke = new BorderStroke(
            Paint.valueOf(color.SIDEBAR.getCode()),
            BorderStrokeStyle.SOLID,
            new CornerRadii(0),
            new BorderWidths(1)
        );
        tabs.setBackground(new Background(new BackgroundFill(Color.web(color.SIDEBAR.getCode()), new CornerRadii(0), Insets.EMPTY)));
        Border border = new Border(borderStroke);
        tabs.setBorder(border);
        Button tab1 = new Button("Profile");
        Button tab2 = new Button("Discussion Page");
        Button tab3 = new Button("Global Leaderboard");
        Button tab4 = null;
        Button tab5 = null;
        VBox profileBox = Profile.loadProfileTab();
        VBox discussionBox = EducatorCreateQuiz.tabCreateQuiz();
        VBox leaderboardBox = new VBox(10);
        VBox box4 = new VBox(10);
        VBox box5 = new VBox(10);
        pages[1] = profileBox;
        pages[2] = discussionBox;
        pages[3] = leaderboardBox;
        pages[4] = box4;
        pages[5] = box5;
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(profileBox, discussionBox, leaderboardBox, box4, box5);
        //TEMP
        discussionBox.getChildren().add(new Label("Discussion Page"));
        leaderboardBox.getChildren().add(new Label("Global Leaderboard"));
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
        layout1.getChildren().add(tabs);
        layout1.getChildren().add(stackPane);
        tabs.getChildren().addAll(tab1, tab2, tab3);
        root.setTop(backButton);
        root.setCenter(layout1);
        Scene scene1 = new Scene(root, 1000, 650);
        tab1.getStyleClass().add("button");
        tab2.getStyleClass().add("button");
        tab3.getStyleClass().add("button");
        if(tab4 != null)
            tab4.getStyleClass().add("button");
        if(tab5 != null)
            tab5.getStyleClass().add("button");
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
