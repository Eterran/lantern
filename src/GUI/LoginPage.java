package GUI;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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

public class LoginPage {
    public static void showLoginScene(Stage stg) {
        Image icon = new Image("resources/assets/lantern_icon.jpg");
        ImageView iconView = new ImageView(icon);
        iconView.setFitHeight(350);
        iconView.setFitWidth(350);
        Label label = new Label("Welcome to Lantern");
        label.getStyleClass().add("title");
        label.setPadding(new Insets(14, 0, 10, 0));
        TextField usernameTF = new TextField();
        usernameTF.setPadding(new Insets(0, 0, 10, 0));
        TextField passwordTF = new TextField();
        passwordTF.setPadding(new Insets(0, 0, 10, 0));

        Button loginButton = new Button();
        loginButton.setText("Login");
        loginButton.getStyleClass().add("normal_text_label");
        loginButton.setPadding(new Insets(8, 179, 8, 179));
        loginButton.setStyle("-fx-background-color: " + color.MAIN.getCode() +
                     "; -fx-text-fill: white; -fx-font-size: 18px;" +
                     "-fx-border-color: black; -fx-border-width: 2px;" +
                     "-fx-background-radius: 10px; -fx-border-radius: 10px;");
        loginButton.setOnAction(e -> {
            if (Lantern.checkLoginCredentials(usernameTF.getText(), passwordTF.getText())) {
                showSuccessScene(stg);
            }
        });

        Button registerButton = new Button();
        registerButton.setText("Register");
        registerButton.getStyleClass().add("normal_text_label");
        registerButton.setPadding(new Insets(8, 170, 8, 170));
        registerButton.setStyle("-fx-background-color: " + color.ACCENT.getCode() +
                        "; -fx-text-fill: white; -fx-font-size: 18px;" +
                        "-fx-border-color: black; -fx-border-width: 2px;" +
                        "-fx-background-radius: 10px; -fx-border-radius: 10px;");
        registerButton.setOnAction(e -> RegistrationPage.showRegistrationScene(stg));

        VBox labelBox = new VBox();
        labelBox.setAlignment(Pos.CENTER);
        labelBox.getChildren().addAll(label);
        
        VBox inputBox = new VBox(10);
        inputBox.setPadding(new Insets(0, 10, 0, 14));
        Text usernameText = new Text("Username:");
        usernameText.getStyleClass().add("normal_text_content");
        VBox usernameBox = new VBox(usernameText);
        usernameBox.setPadding(new Insets(0, 10, 0, 14));
        inputBox.getChildren().add(usernameBox);
        inputBox.getChildren().add(usernameTF);

        Text passwordText = new Text("Password:");
        passwordText.getStyleClass().add("normal_text_content");
        VBox passwordBox = new VBox(passwordText);
        passwordBox.setPadding(new Insets(0, 10, 0, 14));
        inputBox.getChildren().add(passwordBox);
        inputBox.getChildren().add(passwordTF);

        VBox buttonBox = new VBox(10);
        buttonBox.setPadding(new Insets(80, 0, 0, 0));
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().add(loginButton);
        buttonBox.getChildren().add(registerButton);

        VBox loginBox = new VBox(15);
        loginBox.setPrefSize(600, 500);
        loginBox.setMaxSize(600, 500);
        loginBox.setMinSize(600, 500);
        
        loginBox.setBackground(new Background(new BackgroundFill(
            Color.web(color.BACKGROUND.getCode()), new CornerRadii(6), Insets.EMPTY)));
        //rootBox.setBorder(new Border(new BorderStroke(
            //Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(10), BorderWidths.DEFAULT)));
        // root.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,
        // CornerRadii.EMPTY, Insets.EMPTY)));
        loginBox.getChildren().addAll(labelBox, inputBox, buttonBox);

        Separator separator = new Separator();
        separator.setPadding(new Insets(0, 0, 0, 0));
        separator.getStyleClass().add("separator_vertical");
        separator.setOrientation(javafx.geometry.Orientation.VERTICAL);

        HBox rootBox = new HBox();
        rootBox.setAlignment(Pos.CENTER);
        rootBox.setBackground(new Background(new BackgroundFill(
            Color.web(color.BACKGROUND.getCode()), new CornerRadii(6), Insets.EMPTY)));
        rootBox.getChildren().addAll(iconView, separator, loginBox);
        StackPane rootPane = new StackPane();
        rootBox.prefWidthProperty().bind(rootPane.widthProperty().multiply(0.85));
        rootBox.prefHeightProperty().bind(rootPane.heightProperty().multiply(0.85));
        rootBox.setMaxSize(850, 600);
        
        rootPane.getStyleClass().add("background");
        rootPane.getChildren().add(rootBox);

        Scene scene = new Scene(rootPane, 1200, 700);
        scene.getStylesheets().add("resources/style.css");
        stg.setScene(scene);
        Lantern.Push_History(stg.getScene());
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
            Sidebar.showHomeScene(primaryStage);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
