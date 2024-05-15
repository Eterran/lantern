package GUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Separator;

import java.sql.Connection;
import java.util.*;

import Database.Database;

public class Lantern extends Application {
    private static Deque<Scene> history = new ArrayDeque<Scene>();
    private static Deque<Tab> tabHistory = new ArrayDeque<Tab>();

    public static Connection getConn() {
        return Database.connectionDatabase();
    }

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
        Image icon = new Image("resources/assets/lantern_icon_square.png");
        primaryStage.getIcons().add(icon);
        LoginPage.showLoginScene(primaryStage);
        primaryStage.setResizable(false);
        primaryStage.show();
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
    public static Region createSpacer(){
        Region spacer = new Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
        return spacer;
    }
    public static VBox createHorizontalSeparator(int spacing){
        Separator separator = new Separator();
        separator.setOrientation(Orientation.HORIZONTAL);
        VBox sepBox = new VBox();
        Insets padding = new Insets(spacing, 0, spacing, 0);
        sepBox.setPadding(padding);
        sepBox.getChildren().add(separator);
        sepBox.setAlignment(Pos.CENTER);
        return sepBox;
    }
    public static VBox createVerticalSeparator(int spacing){
        Separator separator = new Separator();
        separator.setOrientation(Orientation.VERTICAL);
        VBox sepBox = new VBox(spacing);
        Insets padding = new Insets(0, spacing, 0, spacing);
        sepBox.setPadding(padding);
        sepBox.getChildren().add(separator);
        sepBox.setAlignment(Pos.CENTER);
        return sepBox;
    }

    public static VBox createInfoBox(String labelText, String valueText, int spacing) {
        Text label = new Text(labelText);
        Text value = new Text(valueText);
        label.getStyleClass().add("text_label");
        value.getStyleClass().add("text_content");
        VBox infoBox = new VBox(spacing);
        infoBox.getChildren().addAll(label, value);
        infoBox.getStylesheets().add("resources/style.css");
        return infoBox;
    }
    public static VBox createInfoVBox(String labelText, String valueText, Insets spacing) {
        Text label = new Text(labelText);
        Text value = new Text(valueText);
        label.getStyleClass().add("text_label");
        value.getStyleClass().add("text_content");
        VBox infoBox = new VBox(0);
        infoBox.setPadding(spacing);
        infoBox.getChildren().addAll(label, value);
        infoBox.getStylesheets().add("resources/style.css");
        return infoBox;
    }
    public static VBox createInfoVBox(String labelText, Double valueText, Insets spacing) {
        Text label = new Text(labelText);
        Text value = new Text(valueText.toString());
        label.getStyleClass().add("text_label");
        value.getStyleClass().add("text_content");
        VBox infoBox = new VBox(0);
        infoBox.setPadding(spacing);
        infoBox.getChildren().addAll(label, value);
        infoBox.getStylesheets().add("resources/style.css");
        return infoBox;
    }
    public static VBox createInfoVBox(String labelText, Integer valueText, Insets spacing) {
        Text label = new Text(labelText);
        Text value = new Text(valueText.toString());
        label.getStyleClass().add("text_label");
        value.getStyleClass().add("text_content");
        VBox infoBox = new VBox(0);
        infoBox.setPadding(spacing);
        infoBox.getChildren().addAll(label, value);
        infoBox.getStylesheets().add("resources/style.css");
        return infoBox;
    }
    public static HBox createInfoHBox(String labelText, Double valueText, int spacing) {
        Text label = new Text(labelText);
        Text value = new Text(valueText.toString());
        label.getStyleClass().add("text_label");
        value.getStyleClass().add("text_content");
        HBox infoBox = new HBox(spacing);
        infoBox.getChildren().addAll(label, value);
        infoBox.getStylesheets().add("resources/style.css");
        return infoBox;
    }
    public static HBox createInfoHBox(String labelText, Double valueText, Insets spacing) {
        Text label = new Text(labelText);
        Text value = new Text(valueText.toString());
        label.getStyleClass().add("text_label");
        value.getStyleClass().add("text_content");
        HBox infoBox = new HBox(0);
        infoBox.setPadding(spacing);
        infoBox.getChildren().addAll(label, value);
        infoBox.getStylesheets().add("resources/style.css");
        return infoBox;
    }
    public static HBox createInfoHBox(String labelText, Integer valueText, int spacing) {
        Text label = new Text(labelText);
        Text value = new Text(valueText.toString());
        label.getStyleClass().add("text_label");
        value.getStyleClass().add("text_content");
        HBox infoBox = new HBox(spacing);
        infoBox.getChildren().addAll(label, value);
        infoBox.getStylesheets().add("resources/style.css");
        return infoBox;
    }
    public static HBox createInfoHBox(String labelText, Integer valueText, Insets spacing) {
        Text label = new Text(labelText);
        Text value = new Text(valueText.toString());
        label.getStyleClass().add("text_label");
        value.getStyleClass().add("text_content");
        HBox infoBox = new HBox(0);
        infoBox.setPadding(spacing);
        infoBox.getChildren().addAll(label, value);
        infoBox.getStylesheets().add("resources/style.css");
        return infoBox;
    }
    public static HBox createInfoHBox(String labelText, String valueText, int spacing) {
        Text label = new Text(labelText);
        Text value = new Text(valueText);
        label.getStyleClass().add("text_label");
        value.getStyleClass().add("text_content");
        HBox infoBox = new HBox(spacing);
        infoBox.getChildren().addAll(label, value);
        infoBox.getStylesheets().add("resources/style.css");
        return infoBox;
    }
    public static HBox createInfoHBox(String labelText, String valueText, Insets spacing) {
        Text label = new Text(labelText);
        Text value = new Text(valueText);
        label.getStyleClass().add("text_label");
        value.getStyleClass().add("text_content");
        HBox infoBox = new HBox(0);
        infoBox.setPadding(spacing);
        infoBox.getChildren().addAll(label, value);
        infoBox.getStylesheets().add("resources/style.css");
        return infoBox;
    }
}
