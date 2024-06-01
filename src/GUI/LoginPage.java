package GUI;

import java.sql.Connection;
import java.sql.SQLException;

import Database.Login_Register;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoginPage {
    private static Connection conn = Lantern.getConn();
    
    public static void showLoginScene(Stage stg) {
        Button registerButton = new Button();
        registerButton.setMinSize(274, 387);
        registerButton.getStyleClass().add("lantern_button");
        Label label = new Label("Welcome to Lantern");
        label.getStyleClass().add("title");
        label.setPadding(new Insets(14, 0, 10, 0));
        TextField usernameTF = new TextField();
        usernameTF.setPadding(new Insets(0, 0, 10, 10));
        TextField passwordTF = new TextField();
        passwordTF.setPadding(new Insets(0, 0, 10, 10));
        ImageView user_icon = new ImageView(new Image("resources/assets/user_icon.jpg"));
        user_icon.setFitWidth(40);
        user_icon.setFitHeight(40);
        ImageView pw_icon = new ImageView(new Image("resources/assets/padlock_icon.jpg"));
        pw_icon.setFitWidth(40);
        pw_icon.setFitHeight(40);
        usernameTF.getStyleClass().add("text_field_with_icon");
        passwordTF.getStyleClass().add("text_field_with_icon");
        usernameTF.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
        passwordTF.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
        HBox username_hbox = new HBox();
        HBox password_hbox = new HBox();
        username_hbox.getChildren().addAll(user_icon, Lantern.createVerticalSeparator(4), usernameTF);
        password_hbox.getChildren().addAll(pw_icon, Lantern.createVerticalSeparator(4), passwordTF);
        username_hbox.setAlignment(Pos.CENTER_LEFT);
        password_hbox.setAlignment(Pos.CENTER_LEFT);
        username_hbox.getStyleClass().add("background_transparent");
        password_hbox.getStyleClass().add("background_transparent");

        Button loginButton = new Button();
        loginButton.setText("Login");
        loginButton.setPadding(new Insets(8, 140, 8, 140));
        loginButton.getStyleClass().add("login_button");
        loginButton.setOnAction(e -> {
            try {
                if(Login_Register.login(usernameTF.getText(), passwordTF.getText(), conn)) {
                    showSuccessScene(stg);
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Login Failed");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid username or password. Please try again.");
                    alert.showAndWait();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });

        registerButton.setOnAction(e -> RegistrationPage.showRegistrationScene(stg));

        VBox labelBox = new VBox();
        labelBox.setAlignment(Pos.CENTER);
        labelBox.getChildren().addAll(label);
        
        VBox inputBox = new VBox(10);
        inputBox.setPadding(new Insets(0, 0, 0, 14));
        Text usernameText = new Text("Username:");
        usernameText.getStyleClass().add("text_content");
        VBox usernameBox = new VBox(usernameText);
        usernameBox.setPadding(new Insets(0, 0, 0, 14));
        inputBox.getChildren().add(usernameBox);
        inputBox.getChildren().add(username_hbox);

        Text passwordText = new Text("Password:");
        passwordText.getStyleClass().add("text_content");
        VBox passwordBox = new VBox(passwordText);
        passwordBox.setPadding(new Insets(0, 0, 0, 14));
        inputBox.getChildren().add(passwordBox);
        inputBox.getChildren().add(password_hbox);

        VBox buttonBox = new VBox(10);
        buttonBox.setPadding(new Insets(80, 0, 0, 0));
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().add(loginButton);

        VBox loginBox = new VBox(15);
        loginBox.setPrefSize(600, 500);
        loginBox.setMaxSize(600, 500);
        loginBox.setMinSize(600, 500);
        inputBox.prefWidthProperty().bind(loginBox.widthProperty());
        labelBox.prefWidthProperty().bind(loginBox.widthProperty());
        buttonBox.prefWidthProperty().bind(loginBox.widthProperty());
        username_hbox.prefWidthProperty().bind(inputBox.widthProperty());
        password_hbox.prefWidthProperty().bind(inputBox.widthProperty());
        usernameTF.prefWidthProperty().bind(username_hbox.widthProperty().multiply(0.65));
        passwordTF.prefWidthProperty().bind(password_hbox.widthProperty().multiply(0.65));
        
        loginBox.setBackground(new Background(new BackgroundFill(
            Color.web(color.BACKGROUND.getCode()), new CornerRadii(6), Insets.EMPTY)));
        loginBox.getChildren().addAll(labelBox, inputBox, buttonBox);

        Separator separator = new Separator();
        separator.setPadding(new Insets(0, 0, 0, 0));
        separator.getStyleClass().add("separator_vertical");
        separator.setOrientation(javafx.geometry.Orientation.VERTICAL);

        HBox rootBox = new HBox();
        rootBox.setAlignment(Pos.CENTER);
        rootBox.setBackground(new Background(new BackgroundFill(
            Color.web(color.BACKGROUND.getCode()), new CornerRadii(6), Insets.EMPTY)));
        VBox leftBox = new VBox(0);
        leftBox.setAlignment(Pos.CENTER);
        leftBox.setPadding(new Insets(0, 50, 0, 50));
        leftBox.getChildren().add(registerButton);
        Text registerText = new Text("Don't have an account?");
        Text registerText2 = new Text("Click the lantern to register!");
        registerText.getStyleClass().add("text_content");
        registerText2.getStyleClass().add("text_content");
        leftBox.getChildren().addAll(registerText, registerText2);
        rootBox.getChildren().addAll(leftBox, separator, loginBox);
        StackPane rootPane = new StackPane();
        rootBox.prefWidthProperty().bind(rootPane.widthProperty().multiply(0.85));
        rootBox.prefHeightProperty().bind(rootPane.heightProperty().multiply(0.85));
        rootBox.setMaxSize(850, 600);
        
        rootPane.getStyleClass().add("background");
        rootPane.getChildren().add(rootBox);

        Lantern.playTransition(rootBox, 1.3);

        Scene scene = new Scene(rootPane, 1200, 700);
        scene.getStylesheets().add("resources/style.css");
        stg.setScene(scene);
        Lantern.Push_History(stg.getScene());
    }

    public static void showSuccessScene(Stage primaryStage) {
        VBox successLayout = new VBox(50);
        successLayout.setBackground(new Background(new BackgroundFill(
            Color.web(color.BACKGROUND.getCode()), new CornerRadii(6), Insets.EMPTY)));
        successLayout.setAlignment(Pos.CENTER);
        ImageView loading = new ImageView(new Image("resources/assets/lit_lantern.gif"));
        
        successLayout.getChildren().addAll(loading, new javafx.scene.control.Label("LOGIN SUCCESS") {
            {
                setFont(Font.font(30));
            }
            {
                getStyleClass().add("title");
            }
        });

        BorderPane successPane = new BorderPane();
        successPane.setCenter(successLayout);
        
        Scene successScene = new Scene(successPane, 1200, 700);
        successScene.getStylesheets().add("resources/style.css");
        primaryStage.setScene(successScene);

        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> Sidebar.showHomeScene(primaryStage));
        pause.play();
    }
}
