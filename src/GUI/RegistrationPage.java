package GUI;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import Database.Database;
import Database.Login_Register;

public class RegistrationPage {
    public static void showRegistrationScene(Stage stg) {
        Connection conn = Database.connectionDatabase();
        Lantern.Push_History(stg.getScene());
        Label label = new Label("Register");
        label.getStyleClass().add("title");
        label.setPadding(new Insets(0, 0, 10, 4));
        label.setTextFill(Color.WHITE);
        TextField usernameTF = new TextField();
        usernameTF.setPadding(new Insets(0, 0, 10, 0));
        usernameTF.setPromptText("Username");
        usernameTF.setPrefWidth(100);
        TextField emailTF = new TextField();
        emailTF.setPadding(new Insets(0, 0, 10, 0));
        emailTF.setPromptText("Email");
        emailTF.setPrefWidth(100);
        TextField passwordTF = new TextField();
        passwordTF.setPadding(new Insets(0, 0, 10, 0));
        passwordTF.setPromptText("Password");
        passwordTF.setPrefWidth(100);
        TextField confirmPasswordTF = new TextField();
        confirmPasswordTF.setPadding(new Insets(0, 0, 10, 0));
        confirmPasswordTF.setPromptText("Confirm Password");
        confirmPasswordTF.setPrefWidth(100);

        ComboBox<String> comboBox = new ComboBox<String>();
        comboBox.getItems().addAll("Student", "Parent", "Educator");
        VBox roleBox = new VBox();
        roleBox.getChildren().add(comboBox);

        Button registerButton = new Button();
        registerButton.setText("Register");
        registerButton.setPadding(new Insets(8, 100, 8, 100));
        registerButton.setStyle("-fx-background-color: " + color.MAIN.getCode() +
                                "; -fx-text-fill: white; -fx-font-size: 18px;" +
                                "-fx-border-color: black; -fx-border-width: 2px;" +
                                "-fx-background-radius: 10px; -fx-border-radius: 10px;");
        registerButton.setOnAction(e -> {
            String password = passwordTF.getText();
            String confirmPassword = confirmPasswordTF.getText();
            boolean passwordMatch = password.equals(confirmPassword);
            boolean usernameExists = true;
            if (!passwordMatch) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Password Mismatch");
                alert.setHeaderText(null);
                alert.setContentText("The password does not match the confirm password.");
                alert.showAndWait();
            } try {
                if (Database.usernameExists(conn, usernameTF.getText())){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Duplicate Username");
                    alert.setHeaderText(null);
                    alert.setContentText("The username is taken.");
                    alert.showAndWait();
                    usernameExists = true;
                } else {
                    usernameExists = false;
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            if(passwordMatch && !usernameExists)
                if (Login_Register.register(new ArrayList<String>(), new ArrayList<String>(), usernameTF.getText(), emailTF.getText(), password, comboBox.getValue())) {
                    LoginPage.showSuccessScene(stg);
                }
        });

        Button backButton = new Button();
        backButton.setText("Back");
        backButton.setPadding(new Insets(8, 100, 8, 100));
        backButton.setStyle("-fx-background-color: " + color.ACCENT.getCode() +
                            "; -fx-text-fill: white; -fx-font-size: 18px;" +
                            "-fx-border-color: black; -fx-border-width: 2px;" +
                            "-fx-background-radius: 10px; -fx-border-radius: 10px;");
        backButton.setOnAction(e -> Lantern.goBack(stg));

        VBox vbox = new VBox(10);
        vbox.getChildren().add(label);
        vbox.getChildren().add(new VBox(new Text("Username:") {
            {
                setFill(Color.WHITE);
            }
            {
                getStyleClass().add("normal_text_label");
            }
        }) {
            {
                setPadding(new Insets(0, 0, 0, 4));
            }
        });
        vbox.getChildren().add(usernameTF);
        vbox.getChildren().add(new VBox(new Text("Email:") {
            {
                setFill(Color.WHITE);
            }
            {
                getStyleClass().add("normal_text_label");
            }
        }) {
            {
                setPadding(new Insets(0, 0, 0, 4));
            }
        });
        vbox.getChildren().add(emailTF);
        vbox.getChildren().add(new VBox(new Text("Password:") {
            {
                setFill(Color.WHITE);
            }
            {
                getStyleClass().add("normal_text_label");
            }
        }) {
            {
                setPadding(new Insets(0, 0, 0, 4));
            }
        });
        vbox.getChildren().add(passwordTF);
        vbox.getChildren().add(new VBox(new Text("Confirm Password:") {
            {
                setFill(Color.WHITE);
            }
            {
                getStyleClass().add("normal_text_label");
            }
        }) {
            {
                setPadding(new Insets(0, 0, 0, 4));
            }
        });
        vbox.getChildren().add(confirmPasswordTF);
        vbox.getChildren().add(new VBox(new Text("Role:") {
            {
                setFill(Color.WHITE);
            }
            {
                getStyleClass().add("normal_text_label");
            }
        }) {
            {
                setPadding(new Insets(0, 0, 0, 4));
            }
        });
        vbox.getChildren().add(roleBox);
        vbox.getChildren().add(registerButton);
        vbox.getChildren().add(backButton);
        vbox.setAlignment(javafx.geometry.Pos.CENTER);
        StackPane root = new StackPane();

        Pane backgroundPane = new Pane();
        backgroundPane
                .setBackground(new Background(new BackgroundFill(Color.SANDYBROWN, CornerRadii.EMPTY, Insets.EMPTY)));
        // root.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,
        // CornerRadii.EMPTY, Insets.EMPTY)));
        backgroundPane.getStyleClass().add("background");

        root.getChildren().add(backgroundPane);
        root.getChildren().add(vbox);

        Scene scene = new Scene(root, 1000, 650);
        scene.getStylesheets().add("resources/style.css");
        stg.setScene(scene);
    }
}
