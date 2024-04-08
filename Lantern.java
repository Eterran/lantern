import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import java.util.*;
import java.lang.Thread;


public class Lantern extends Application {
    private static Deque<Scene> history = new ArrayDeque<Scene>(); 
    private static Deque<Tab> tabHistory = new ArrayDeque<Tab>();
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Lantern");
        showLoginScene(primaryStage);
        primaryStage.show();
    }
    public static void showLoginScene(Stage stg){
        Label label = new Label("Welcome to Lantern");
        label.setFont(Font.font(30));
        label.setPadding(new Insets(0, 0, 10, 4));
        TextField usernameTF = new TextField();
        usernameTF.setPadding(new Insets(0, 0, 10, 0));
        TextField passwordTF = new TextField();
        passwordTF.setPadding(new Insets(0, 0, 10, 0));

        Button loginButton = new Button();
        loginButton.setText("Login");
        loginButton.setPadding(new Insets(10, 20, 10, 20));
        loginButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px;");

        loginButton.setOnAction(e -> {
            if (checkLoginCredentials(usernameTF.getText(), passwordTF.getText())){
                showSuccessScene(stg);
            }
        });

        Button registerButton = new Button();
        registerButton.setText("Register");
        registerButton.setOnAction(e -> showRegistrationScene(stg));

        VBox vbox = new VBox(10);
        vbox.getChildren().add(label);
        vbox.getChildren().add(new VBox(new Text("Username:")) {{ setPadding(new Insets(0, 0, 0, 4)); }});
        vbox.getChildren().add(usernameTF);
        vbox.getChildren().add(new VBox(new Text("Password:")) {{ setPadding(new Insets(0, 0, 0, 4)); }});
        vbox.getChildren().add(passwordTF);
        vbox.getChildren().add(loginButton);
        vbox.getChildren().add(registerButton);

        StackPane root = new StackPane();

        Scene scene = new Scene(root, 500, 450);
        vbox.setBackground(new Background(new BackgroundFill(Color.SANDYBROWN, CornerRadii.EMPTY, Insets.EMPTY)));
        //root.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

        root.getChildren().add(vbox);

        stg.setScene(scene);
        history.push(stg.getScene());
    }
    public static void showRegistrationScene(Stage stg){
        history.push(stg.getScene());
        Label label = new Label("Register");
        label.setFont(Font.font(30));
        label.setPadding(new Insets(0, 0, 10, 4));
        label.setTextFill(Color.WHITE);
        TextField usernameTF = new TextField();
        usernameTF.setPadding(new Insets(0, 0, 10, 0));
        TextField passwordTF = new TextField();
        passwordTF.setPadding(new Insets(0, 0, 10, 0));

        ComboBox<String> comboBox = new ComboBox<String>();
        comboBox.getItems().addAll("Student", "Parent", "Educator");

        Button registerButton = new Button();
        registerButton.setText("Register");
        registerButton.setOnAction(e -> {
            if (checkRegisterCredentials(usernameTF.getText(), passwordTF.getText(), comboBox.getValue())){
                showSuccessScene(stg);
            }
        });

        Button backButton = new Button();
        backButton.setText("Back");
        backButton.setOnAction(e -> goBack(stg));

        VBox vbox = new VBox(10);
        vbox.getChildren().add(label);
        vbox.getChildren().add(new VBox(new Text("Username:") {{setFill(Color.WHITE);}}) {{setPadding(new Insets(0, 0, 0, 4)); }});
        vbox.getChildren().add(usernameTF);
        vbox.getChildren().add(new VBox(new Text("Password:") {{setFill(Color.WHITE);}}) {{setPadding(new Insets(0, 0, 0, 4)); }});
        vbox.getChildren().add(passwordTF);
        vbox.getChildren().add(new VBox(new Text("Role:") {{setFill(Color.WHITE);}}) {{setPadding(new Insets(0, 0, 0, 4)); }});
        vbox.getChildren().add(comboBox);
        vbox.getChildren().add(registerButton);
        vbox.getChildren().add(backButton);

        StackPane root = new StackPane();

        Pane backgroundPane = new Pane();
        backgroundPane.setBackground(new Background(new BackgroundFill(Color.SANDYBROWN, CornerRadii.EMPTY, Insets.EMPTY)));
        //root.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        backgroundPane.setStyle("-fx-background-image: url('assets/lantern_background.jpg'); " + "-fx-background-size: cover; " + "-fx-opacity: 0.7;");

        root.getChildren().add(backgroundPane);
        root.getChildren().add(vbox);

        Scene scene = new Scene(root, 500, 450);
        stg.setScene(scene);
    }
    public static void showHomeScene(Stage stg){
        history.clear();
        Button backButton = new Button("Back");
        TabPane tabPane = new TabPane();
        Tab tabHome = new Tab("Home");
        Tab tabProfile = new Tab("Profile");
        Tab tab3 = new Tab("3");
        Tab tab4 = new Tab("4");
        Tab tab5 = new Tab("5");
        tabHome.setClosable(false);
        tabProfile.setClosable(false);
        tab3.setClosable(false);
        tab4.setClosable(false);
        tab5.setClosable(false);
        tabHome.setContent(new Label("Home Content"));
        tabProfile.setContent(new Label("Profile Content"));
        tab3.setContent(new Label("3 Content"));
        tab4.setContent(new Label("4 Content"));
        tab5.setContent(new Label("5 Content"));

        tabPane.getTabs().addAll(tabHome, tabProfile, tab3, tab4, tab5);
        backButton.setOnAction(e -> {goBackTab(tabPane); goBackTab(tabPane);});
        tabHistory.push(tabHome);

        tabPane.setStyle("-fx-tab-min-width:100px; -fx-tab-max-width:100px; -fx-tab-min-height:30px; -fx-tab-max-height:30px; -fx-font-size:14px; -fx-font-weight:bold;");

        tabHome.setStyle("-fx-background-color: #008080; -fx-text-fill: white;");
        tabProfile.setStyle("-fx-background-color: #2f4f4f; -fx-text-fill: white;");
        tab3.setStyle("-fx-background-color: #2f4f4f; -fx-text-fill: white;");
        tab4.setStyle("-fx-background-color: #2f4f4f; -fx-text-fill: white;");
        tab5.setStyle("-fx-background-color: #2f4f4f; -fx-text-fill: white;");

        tabHome.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            if (isNowSelected) {
                if ((!tabHistory.isEmpty() || tabHistory.peek() != tabPane.getSelectionModel().getSelectedItem())) {
                    tabHistory.push(tabPane.getSelectionModel().getSelectedItem());
                }
                tabHome.setStyle("-fx-background-color: #008080; -fx-text-fill: white;");
            } else {
                tabHome.setStyle("-fx-background-color: #2f4f4f; -fx-text-fill: white;");
            }
        });
        tabProfile.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            if (isNowSelected) {
                if ((!tabHistory.isEmpty() || tabHistory.peek() != tabPane.getSelectionModel().getSelectedItem())) {
                    tabHistory.push(tabPane.getSelectionModel().getSelectedItem());
                }
                tabProfile.setStyle("-fx-background-color: #008080; -fx-text-fill: white;");
            } else {
                tabProfile.setStyle("-fx-background-color: #2f4f4f; -fx-text-fill: white;");
            }
        });
        tab3.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            if (isNowSelected) {
                if ((!tabHistory.isEmpty() || tabHistory.peek() != tabPane.getSelectionModel().getSelectedItem())) {
                    tabHistory.push(tabPane.getSelectionModel().getSelectedItem());
                }
                tab3.setStyle("-fx-background-color: #008080; -fx-text-fill: white;");
            } else {
                tab3.setStyle("-fx-background-color: #2f4f4f; -fx-text-fill: white;");
            }
        });
        tab4.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            if (isNowSelected) {
                if ((!tabHistory.isEmpty() || tabHistory.peek() != tabPane.getSelectionModel().getSelectedItem())) {
                    tabHistory.push(tabPane.getSelectionModel().getSelectedItem());
                }
                tab4.setStyle("-fx-background-color: #008080; -fx-text-fill: white;");
            } else {
                tab4.setStyle("-fx-background-color: #2f4f4f; -fx-text-fill: white;");
            }
        });
        tab5.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            if (isNowSelected) {
                if ((!tabHistory.isEmpty() || tabHistory.peek() != tabPane.getSelectionModel().getSelectedItem())) {
                    tabHistory.push(tabPane.getSelectionModel().getSelectedItem());
                }
                tab5.setStyle("-fx-background-color: #008080; -fx-text-fill: white;");
            } else {
                tab5.setStyle("-fx-background-color: #2f4f4f; -fx-text-fill: white;");
            }
        });

        VBox layout1 = new VBox(10);
        layout1.getChildren().addAll(tabPane);
        
        BorderPane pane1 = new BorderPane();
        pane1.setTop(backButton);
        pane1.setCenter(layout1);

        Scene scene1 = new Scene(pane1, 1000, 650);

        stg.setScene(scene1);
    }
    public static void showScene1(Stage stg){
        if(!history.isEmpty())
        if(history.peek() != stg.getScene()){
            history.push(stg.getScene());
        }
        Button backButton = new Button("Back");
        Button nextButton = new Button("Next");

        backButton.setOnAction(e -> goBack(stg));
        nextButton.setOnAction(e -> showScene2(stg));

        VBox layout1 = new VBox(10);
        layout1.getChildren().addAll(new javafx.scene.control.Label("Scene 1"));
        
        BorderPane pane1 = new BorderPane();
        pane1.setTop(backButton);
        pane1.setCenter(layout1);
        pane1.setBottom(nextButton);

        Scene scene1 = new Scene(pane1, 500, 450);

        stg.setScene(scene1);
    }

    public static void showScene2(Stage stg){
        if(history.peek() != stg.getScene()){
            history.push(stg.getScene());
        }
        Button backButton = new Button("Back");

        backButton.setOnAction(e -> goBack(stg));

        VBox layout1 = new VBox(10);
        layout1.getChildren().addAll(new javafx.scene.control.Label("Scene 2"));
        
        BorderPane pane1 = new BorderPane();
        pane1.setTop(backButton);
        pane1.setCenter(layout1);

        Scene scene1 = new Scene(pane1, 500, 450);

        stg.setScene(scene1);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static boolean goBack(Stage stg){
        if(!history.isEmpty()){
            stg.setScene(history.pop());
            return true;
        } else {
            return false;
        }
    }
    public static boolean goBackTab(TabPane tabPane){
        if(!tabHistory.isEmpty()){
            tabPane.getSelectionModel().select(tabHistory.pop());
            return true;
        } else {
            return false;
        }
    }

    public static void showSuccessScene(Stage primaryStage){
        VBox successLayout = new VBox(10);
        successLayout.getChildren().addAll(new javafx.scene.control.Label("LOGIN SUCCESS") {{setFont(Font.font(30));}});
        
        BorderPane successPane = new BorderPane();
        successPane.setCenter(successLayout);

        Scene successScene = new Scene(successPane, 500, 450);

        primaryStage.setScene(successScene);

        try {
            Thread.sleep(1000);
            showHomeScene(primaryStage);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkLoginCredentials(String username, String password){
        if(username.equals("a") && password.equals("a")){
            return true;
        }
        return false;
    }

    public static boolean checkRegisterCredentials(String username, String password, String role){
        if(!(username.equals("a") && password.equals("a"))){
            return true;
        }
        return false;
    }
}
