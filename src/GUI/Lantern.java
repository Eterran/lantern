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
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Lantern");
        showLoginScene(primaryStage);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void showLoginScene(Stage stg) {
        Label label = new Label("Welcome to Lantern");
        label.setFont(Font.font("Lato", FontWeight.BOLD, 30));
        label.setPadding(new Insets(10, 0, 6, 0));
        TextField usernameTF = new TextField();
        usernameTF.setPadding(new Insets(0, 0, 10, 0));
        TextField passwordTF = new TextField();
        passwordTF.setPadding(new Insets(0, 0, 10, 0));

        Button loginButton = new Button();
        loginButton.setText("Login");
        loginButton.setFont(Font.font("Montserrat", FontWeight.NORMAL, 12));
        loginButton.setPadding(new Insets(8, 179, 8, 179));
        loginButton.setStyle("-fx-background-color: " + color.MAIN.getCode() +
                     "; -fx-text-fill: white; -fx-font-size: 18px;" +
                     "-fx-border-color: black; -fx-border-width: 2px;" +
                     "-fx-background-radius: 10px; -fx-border-radius: 10px;");
        loginButton.setOnAction(e -> {
            if (checkLoginCredentials(usernameTF.getText(), passwordTF.getText())) {
                showSuccessScene(stg);
            }
        });

        Button registerButton = new Button();
        registerButton.setText("Register");
        registerButton.setFont(Font.font("Montserrat", FontWeight.NORMAL, 12));
        registerButton.setPadding(new Insets(8, 170, 8, 170));
        registerButton.setStyle("-fx-background-color: " + color.ACCENT.getCode() +
                        "; -fx-text-fill: white; -fx-font-size: 18px;" +
                        "-fx-border-color: black; -fx-border-width: 2px;" +
                        "-fx-background-radius: 10px; -fx-border-radius: 10px;");
        registerButton.setOnAction(e -> showRegistrationScene(stg));

        VBox labelBox = new VBox(10);
        labelBox.setAlignment(Pos.CENTER);
        labelBox.getChildren().add(label);
        
        VBox inputBox = new VBox(10);
        inputBox.setPadding(new Insets(0, 10, 0, 14));
        Text usernameText = new Text("Username:");
        usernameText.setFont(Font.font("Montserrat", FontWeight.NORMAL, 14));
        VBox usernameBox = new VBox(usernameText);
        usernameBox.setPadding(new Insets(0, 10, 0, 14));
        inputBox.getChildren().add(usernameBox);
        inputBox.getChildren().add(usernameTF);

        Text passwordText = new Text("Password:");
        passwordText.setFont(Font.font("Montserrat", FontWeight.NORMAL, 14));
        VBox passwordBox = new VBox(passwordText);
        passwordBox.setPadding(new Insets(0, 10, 0, 14));
        inputBox.getChildren().add(passwordBox);
        inputBox.getChildren().add(passwordTF);

        VBox buttonBox = new VBox(10);
        buttonBox.setPadding(new Insets(80, 0, 0, 0));
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().add(loginButton);
        buttonBox.getChildren().add(registerButton);

        VBox rootBox = new VBox(15);
        rootBox.setPrefSize(300, 300);
        rootBox.setMaxSize(500, 450);
        rootBox.setMinSize(500, 450);
        
        rootBox.setBackground(new Background(new BackgroundFill(
            Color.web(color.BACKGROUND.getCode()), new CornerRadii(6), Insets.EMPTY)));
        //rootBox.setBorder(new Border(new BorderStroke(
            //Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(10), BorderWidths.DEFAULT)));
        // root.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,
        // CornerRadii.EMPTY, Insets.EMPTY)));
        rootBox.getChildren().addAll(labelBox, inputBox, buttonBox);

        StackPane rootPane = new StackPane();
        rootPane.getStyleClass().add("background");
        rootPane.getChildren().add(rootBox);

        Scene scene = new Scene(rootPane, 800, 500);
        scene.getStylesheets().add("resources/style.css");
        stg.setScene(scene);
        history.push(stg.getScene());
    }

    public static void showRegistrationScene(Stage stg) {
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
            if (checkRegisterCredentials(usernameTF.getText(), passwordTF.getText(), comboBox.getValue())) {
                showSuccessScene(stg);
            }
        });

        Button backButton = new Button();
        backButton.setText("Back");
        backButton.setOnAction(e -> goBack(stg));

        VBox vbox = new VBox(10);
        vbox.getChildren().add(label);
        vbox.getChildren().add(new VBox(new Text("Username:") {
            {
                setFill(Color.WHITE);
            }
        }) {
            {
                setPadding(new Insets(0, 0, 0, 4));
            }
        });
        vbox.getChildren().add(usernameTF);
        vbox.getChildren().add(new VBox(new Text("Password:") {
            {
                setFill(Color.WHITE);
            }
        }) {
            {
                setPadding(new Insets(0, 0, 0, 4));
            }
        });
        vbox.getChildren().add(passwordTF);
        vbox.getChildren().add(new VBox(new Text("Role:") {
            {
                setFill(Color.WHITE);
            }
        }) {
            {
                setPadding(new Insets(0, 0, 0, 4));
            }
        });
        vbox.getChildren().add(comboBox);
        vbox.getChildren().add(registerButton);
        vbox.getChildren().add(backButton);

        StackPane root = new StackPane();

        Pane backgroundPane = new Pane();
        backgroundPane
                .setBackground(new Background(new BackgroundFill(Color.SANDYBROWN, CornerRadii.EMPTY, Insets.EMPTY)));
        // root.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,
        // CornerRadii.EMPTY, Insets.EMPTY)));
        backgroundPane.getStyleClass().add("background");

        root.getChildren().add(backgroundPane);
        root.getChildren().add(vbox);

        Scene scene = new Scene(root, 800, 500);
        scene.getStylesheets().add("resources/style.css");
        stg.setScene(scene);
    }
    public static void showHomeScene2(Stage stg){
        history.clear();
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
        backButton.setOnAction(e -> {
            goBack(stg);
        });
        tab1.setOnAction(e -> {
            history.push(stg.getScene());
            layout1.getChildren().clear();
            layout1.getChildren().add(tabs);
            layout1.getChildren().add(loadProfileTab());
        });
        tab2.setOnAction(e -> {
            history.push(stg.getScene());
            layout1.getChildren().clear();
            layout1.getChildren().add(tabs);
            layout1.getChildren().add(new Label("Discussion Page Content"));
        });
        tab3.setOnAction(e -> {
            history.push(stg.getScene());
            layout1.getChildren().clear();
            layout1.getChildren().add(tabs);
            layout1.getChildren().add(new Label("Global Leaderboard Content"));
        });
        
        if(AccessManager.hasAccess("Student", AccessManager.ContentType.STUDENT)){
            tab4 = new Button("Quizzes");
            tab4.setOnAction(e -> {
                layout1.getChildren().clear();
                layout1.getChildren().add(new Label("Quizzes"));
            });
        } else if(AccessManager.hasAccess("Educator", AccessManager.ContentType.EDUCATOR)){
            tab4 = new Button("Create Quizzes");
            tab5 = new Button("Create Events");
            tab4.setOnAction(e -> {
                layout1.getChildren().clear();
                layout1.getChildren().add(new Label("Create Quizzes"));
            });
            tab5.setOnAction(e -> {
                layout1.getChildren().clear();
                layout1.getChildren().add(new Label("Create Events"));
            });
        } else if(AccessManager.hasAccess("Parent", AccessManager.ContentType.PARENT)){
            tab4 = new Button("Make Bookings");
            tab4.setOnAction(e -> {
                layout1.getChildren().clear();
                layout1.getChildren().add(new Label("Make Bookings"));
            });
        }
        layout1.getChildren().add(tabs);
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
    public static void showHomeScene(Stage stg) {
        history.clear();
        BorderPane root = new BorderPane();
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

        tabHome.setContent(loadHomeTab());
        tabProfile.setContent(loadProfileTab());
        
        //TODO: Add content to tabs
        Label content3 = new Label("Content 3");
        content3.setStyle("-fx-background-color: " + color.BACKGROUND.getCode() + ";");
        tab3.setContent(content3);
        Label content4 = new Label("Content 4");
        content4.setStyle("-fx-background-color: " + color.BACKGROUND.getCode() + ";");
        tab4.setContent(content4);
        Label content5 = new Label("Content 5");
        content5.setStyle("-fx-background-color: " + color.BACKGROUND.getCode() + ";");
        tab5.setContent(content5);

        tabHome.setStyle("-fx-min-width: 1000px, -fx-pref-width: 1000px;" + "-fx-min-height: 600px, -fx-pref-height: 600px;");
        tabProfile.setStyle("-fx-min-width: 1000px, -fx-pref-width: 1000px;" + "-fx-min-height: 600px, -fx-pref-height: 600px;");
        tab3.setStyle("-fx-min-width: 1000px, -fx-pref-width: 1000px;" + "-fx-min-height: 600px, -fx-pref-height: 600px;");
        tab4.setStyle("-fx-min-width: 1000px, -fx-pref-width: 1000px;" + "-fx-min-height: 600px, -fx-pref-height: 600px;");
        tab5.setStyle("-fx-min-width: 1000px, -fx-pref-width: 1000px;" + "-fx-min-height: 600px, -fx-pref-height: 600px;");
        tabPane.getTabs().addAll(tabHome, tabProfile, tab3, tab4, tab5);
        backButton.setOnAction(e -> {
            goBackTab(tabPane);
            goBackTab(tabPane);
        });
        tabHistory.push(tabHome);

        tabPane.setStyle(
                "-fx-tab-min-width:100px; -fx-tab-max-width:100px; -fx-tab-min-height:30px; -fx-tab-max-height:30px; -fx-font-size:14px; -fx-font-weight:bold;");

        tabHome.setStyle("-fx-background-color: "+color.BACKGROUND.getCode()+"; -fx-text-fill: white;");
        tabProfile.setStyle("-fx-background-color: "+color.MAIN.getCode()+"; -fx-text-fill: white;");
        tab3.setStyle("-fx-background-color: "+color.MAIN.getCode()+"; -fx-text-fill: white;");
        tab4.setStyle("-fx-background-color: "+color.MAIN.getCode()+"; -fx-text-fill: white;");
        tab5.setStyle("-fx-background-color: "+color.MAIN.getCode()+"; -fx-text-fill: white;");

        tabHome.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            if (isNowSelected) {
                if ((!tabHistory.isEmpty() || tabHistory.peek() != tabPane.getSelectionModel().getSelectedItem())) {
                    tabHistory.push(tabPane.getSelectionModel().getSelectedItem());
                }
                tabHome.setStyle("-fx-background-color: "+color.BACKGROUND.getCode()+"; -fx-text-fill: white;");
            } else {
                tabHome.setStyle("-fx-background-color: "+color.MAIN.getCode()+"; -fx-text-fill: white;");
            }
        });
        tabProfile.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            if (isNowSelected) {
                if ((!tabHistory.isEmpty() || tabHistory.peek() != tabPane.getSelectionModel().getSelectedItem())) {
                    tabHistory.push(tabPane.getSelectionModel().getSelectedItem());
                }
                tabProfile.setStyle("-fx-background-color: "+color.BACKGROUND.getCode()+"; -fx-text-fill: white;");
            } else {
                tabProfile.setStyle("-fx-background-color: "+color.MAIN.getCode()+"; -fx-text-fill: white;");
            }
        });
        tab3.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            if (isNowSelected) {
                if ((!tabHistory.isEmpty() || tabHistory.peek() != tabPane.getSelectionModel().getSelectedItem())) {
                    tabHistory.push(tabPane.getSelectionModel().getSelectedItem());
                }
                tab3.setStyle("-fx-background-color: "+color.BACKGROUND.getCode()+"; -fx-text-fill: white;");
            } else {
                tab3.setStyle("-fx-background-color: "+color.MAIN.getCode()+"; -fx-text-fill: white;");
            }
        });
        tab4.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            if (isNowSelected) {
                if ((!tabHistory.isEmpty() || tabHistory.peek() != tabPane.getSelectionModel().getSelectedItem())) {
                    tabHistory.push(tabPane.getSelectionModel().getSelectedItem());
                }
                tab4.setStyle("-fx-background-color: "+color.BACKGROUND.getCode()+"; -fx-text-fill: white;");
            } else {
                tab4.setStyle("-fx-background-color: "+color.MAIN.getCode()+"; -fx-text-fill: white;");
            }
        });
        tab5.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            if (isNowSelected) {
                if ((!tabHistory.isEmpty() || tabHistory.peek() != tabPane.getSelectionModel().getSelectedItem())) {
                    tabHistory.push(tabPane.getSelectionModel().getSelectedItem());
                }
                tab5.setStyle("-fx-background-color: "+color.BACKGROUND.getCode()+"; -fx-text-fill: white;");
            } else {
                tab5.setStyle("-fx-background-color: "+color.MAIN.getCode()+"; -fx-text-fill: white;");
            }
        });

        VBox layout1 = new VBox(10);
        layout1.getChildren().addAll(tabPane);

        root.setCenter(layout1);
        root.setTop(backButton);

        Scene scene1 = new Scene(root, 1000, 650);

        stg.setScene(scene1);
    }
    public static VBox loadHomeTab(){
        VBox homeTab = new VBox(10);
        Label homeContent = new Label("Home Content");
        homeTab.setStyle("-fx-background-color: " + color.BACKGROUND.getCode() + ";");
        homeTab.getChildren().add(homeContent);
        return homeTab;
    }
    public static VBox loadProfileTab(){
        VBox profileTab = new VBox(10);
        Label profileLabel = new Label("Profile");
        profileLabel.setFont(Font.font("Lato", FontWeight.BOLD, 30));
        profileTab.setStyle("-fx-background-color: " + color.BACKGROUND.getCode() + ";");
        profileTab.getChildren().add(profileLabel);
        profileTab.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        //TODO: Implement user class
        Insets padding = new Insets(10, 10, 10, 10);
        HBox usernameBox = createInfoBox("Username: ", "JohnDoe", padding);
        HBox roleBox = createInfoBox("Role: ", "Student", padding);
        HBox emailBox = createInfoBox("Email: ", "", padding);
        HBox locationCoordinateBox = createInfoBox("Location: ", "", padding);
        profileTab.getChildren().addAll(usernameBox, roleBox, emailBox, locationCoordinateBox);

        if(AccessManager.hasAccess("Student", AccessManager.ContentType.STUDENT)){
            Label studentLabel = new Label("Students");
            profileTab.getChildren().add(studentLabel);
            //TODO access database
            HBox pointBox = createInfoBox("Points", "3", 10);
            //TODO FRIENDS
            VBox friendBox = new VBox(10);
            
            profileTab.getChildren().addAll(pointBox, friendBox);
        } else if(AccessManager.hasAccess("Educator", AccessManager.ContentType.EDUCATOR)){
            Label educatorLabel = new Label("Educators");
            profileTab.getChildren().add(educatorLabel);
            HBox quizBox = new HBox(10);
            HBox eventBox = new HBox(10);
            Text quizCreated = new Text("Quizzes Created: ");
            Text eventCreated = new Text("Events Created: ");
            //TODO access database 
            Text quizCount = new Text("5");
            Text eventCount = new Text("3");
            quizBox.getChildren().addAll(quizCreated, quizCount);
            eventBox.getChildren().addAll(eventCreated, eventCount);
            profileTab.getChildren().addAll(quizBox, eventBox);
        } else if(AccessManager.hasAccess("Parent", AccessManager.ContentType.PARENT)){
            Label parentLabel = new Label("Parents");
            profileTab.getChildren().add(parentLabel);
            HBox bookingBox = new HBox(10);
            Text bookingMade = new Text("Bookings Made: ");
            //TODO access database 
            Text bookingCount = new Text("5");
            bookingBox.getChildren().addAll(bookingMade, bookingCount);
            profileTab.getChildren().add(bookingBox);
        } else {
            profileTab.getChildren().add(new Label("No Profile Information"));
        }
        return profileTab;
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

    public static void showSuccessScene(Stage primaryStage) {
        VBox successLayout = new VBox(50);
        successLayout.setAlignment(Pos.CENTER);
        successLayout.getChildren().addAll(new javafx.scene.control.Label("LOGIN SUCCESS") {
            {
                setFont(Font.font(30));
            }
        });

        BorderPane successPane = new BorderPane();
        successPane.setCenter(successLayout);

        Scene successScene = new Scene(successPane, 500, 450);

        primaryStage.setScene(successScene);

        try {
            Thread.sleep(1000);
            showHomeScene2(primaryStage);
        } catch (InterruptedException e) {
            e.printStackTrace();
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
    private static HBox createInfoBox(String labelText, String valueText, int spacing) {
        Text label = new Text(labelText);
        Text value = new Text(valueText);
        HBox infoBox = new HBox(spacing);
        infoBox.getChildren().addAll(label, value);
        return infoBox;
    }
    private static HBox createInfoBox(String labelText, String valueText, Insets spacing) {
        Text label = new Text(labelText);
        Text value = new Text(valueText);
        HBox infoBox = new HBox(0);
        infoBox.setPadding(spacing);
        infoBox.getChildren().addAll(label, value);
        return infoBox;
    }
}
