package GUI;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;
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
    
    public static String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        } else {
            return str.substring(0, 1).toUpperCase() + str.substring(1);
        }
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
        VBox.setVgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
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
    public static VBox createHorizontalSeparator(Insets padding){
        Separator separator = new Separator();
        separator.setOrientation(Orientation.HORIZONTAL);
        VBox sepBox = new VBox();
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
    public static HBox createInfoHBox(String labelText, ArrayList<String> relationships, Insets spacing) {
        Text label = new Text(labelText);
        StringBuilder valueText = new StringBuilder();
        if (relationships != null) {
            for (String relation : relationships) {
                if (relation != null) {
                    valueText.append(relation).append(", ");
                }
            }
        }
        if (valueText.length() > 0) {
            valueText.delete(valueText.length() - 2, valueText.length());
        }
        Text value = new Text("None");
        if(valueText.length() > 0){
            value = new Text(valueText.toString());
            value.getStyleClass().add("text_content");
        } else {
            value.getStyleClass().add("text_greyed_out");
        }
        label.getStyleClass().add("text_label");
        
        TextFlow textFlow = new TextFlow();
        textFlow.setPrefWidth(0);
        textFlow.getChildren().add(value);

        HBox infoBox = new HBox(0);
        textFlow.prefWidthProperty().bind(infoBox.widthProperty().subtract(20));
        infoBox.setPadding(spacing);
        infoBox.getStylesheets().add("resources/style.css");
        infoBox.getChildren().addAll(label, textFlow);

        return infoBox;
    }
    public static void playTransition(Pane pane, Double duration){
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(duration), pane);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(duration), pane);
        scaleTransition.setFromX(0.8);
        scaleTransition.setFromY(0.8);
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);

        ParallelTransition parallelTransition = new ParallelTransition(fadeTransition, scaleTransition);

        parallelTransition.play();
    }
}
