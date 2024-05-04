package GUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
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
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import java.util.*;

import GUI.AccessManager.ContentType;

import java.lang.Thread;

public class Lantern extends Application {
    private static Deque<Scene> history = new ArrayDeque<Scene>();
    private static Deque<Tab> tabHistory = new ArrayDeque<Tab>();
    
    //TODO: Implement user class
    //private User user;

    public static Scene Pop_History(){
        return history.pop();
    }
    public static void Push_History(Scene scene){
        if(history.peek() != scene)
            history.push(scene);
    }
    public static boolean History_isEmpty(){
        return history.isEmpty();
    }
    public static void Clear_History(){
        history.clear();
    }
    public static Scene Peek_History(){
        return history.peek();
    }

    public static Tab Pop_TabHistory(){
        return tabHistory.pop();
    }
    public static void Push_TabHistory(Tab tab){
        if(tabHistory.peek() != tab)
            tabHistory.push(tab);
    }
    public static boolean TabHistory_isEmpty(){
        return tabHistory.isEmpty();
    }
    public static void Clear_TabHistory(){
        tabHistory.clear();
    }
    public static Tab Peek_TabHistory(){
        return tabHistory.peek();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Lantern");
        LoginPage.showLoginScene(primaryStage);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    
    public static VBox loadHomeTab(){
        VBox homeTab = new VBox(10);
        Label homeContent = new Label("Home Content");
        homeTab.setStyle("-fx-background-color: " + color.BACKGROUND.getCode() + ";");
        homeTab.getChildren().add(homeContent);
        return homeTab;
    }
    
    public static void showScene1(Stage stg) {
        if (!history.isEmpty())
            if (history.peek() != stg.getScene()) {
                history.push(stg.getScene());
            }
        Button backButton = new Button("Back");
        Button nextButton = new Button("Next");

        backButton.setOnAction(e -> goBack(stg));
        //nextButton.setOnAction(e -> showScene2(stg));

        VBox layout1 = new VBox(10);
        layout1.getChildren().addAll(new javafx.scene.control.Label("Scene 1"));

        BorderPane pane1 = new BorderPane();
        pane1.setTop(backButton);
        pane1.setCenter(layout1);
        pane1.setBottom(nextButton);

        Scene scene1 = new Scene(pane1, 500, 450);

        stg.setScene(scene1);
    }

    public static boolean goBack(Stage stg) {
        if (!history.isEmpty()) {
            stg.setScene(history.pop());
            return true;
        } else {
            return false;
        }
    }

    public static boolean goBackTab(TabPane tabPane) {
        if (!tabHistory.isEmpty()) {
            tabPane.getSelectionModel().select(tabHistory.pop());
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkLoginCredentials(String username, String password) {
        if (username.equals("a") && password.equals("a")) {
            return true;
        }
        return false;
    }

    public static boolean checkRegisterCredentials(String username, String password, String role) {
        if (!(username.equals("a") && password.equals("a"))) {
            return true;
        }
        return false;
    }
    public static HBox createInfoBox(String labelText, String valueText, int spacing) {
        Text label = new Text(labelText);
        Text value = new Text(valueText);
        label.getStyleClass().add("normal_text");
        value.getStyleClass().add("normal_text");
        HBox infoBox = new HBox(spacing);
        infoBox.getChildren().addAll(label, value);
        return infoBox;
    }
    public static HBox createInfoBox(String labelText, String valueText, Insets spacing) {
        Text label = new Text(labelText);
        Text value = new Text(valueText);
        label.getStyleClass().add("normal_text");
        value.getStyleClass().add("normal_text");
        HBox infoBox = new HBox(0);
        infoBox.setPadding(spacing);
        infoBox.getChildren().addAll(label, value);
        return infoBox;
    }
    public static HBox createInfoBox(String labelText, Double valueText, Insets spacing) {
        Text label = new Text(labelText);
        Text value = new Text(valueText.toString());
        label.getStyleClass().add("normal_text");
        value.getStyleClass().add("normal_text");
        HBox infoBox = new HBox(0);
        infoBox.setPadding(spacing);
        infoBox.getChildren().addAll(label, value);
        return infoBox;
    }
    public static HBox createInfoBox(String labelText, Double valueText, int spacing) {
        Text label = new Text(labelText);
        Text value = new Text(valueText.toString());
        label.getStyleClass().add("normal_text");
        value.getStyleClass().add("normal_text");
        HBox infoBox = new HBox(spacing);
        infoBox.getChildren().addAll(label, value);
        return infoBox;
    }
}
