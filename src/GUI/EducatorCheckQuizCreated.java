package GUI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Database.Login_Register;
import Database.Quiz;
import Database.QuizData;
import Database.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EducatorCheckQuizCreated {

    public static VBox vboxput() {
        //title
        VBox vbox1 = new VBox();
        vbox1.setStyle("-fx-background-color: lightyellow");
        Label label1 = new Label("Quiz Created");
        label1.setStyle("-fx-font-weight: bold; -fx-font-size: 16");
        label1.setPadding(new Insets(10));
        vbox1.getChildren().add(label1);

        //content in the scrollpane
        HBox row =  new HBox(); 
        VBox column1 = new VBox(); 
        VBox column2 = new VBox(); 
        column1.setPadding(new Insets(10));
        column2.setPadding(new Insets(10));
        row.getChildren().addAll(column1, column2);

        HBox.setHgrow(column1, Priority.ALWAYS);
        HBox.setHgrow(column2, Priority.ALWAYS);

        column1.setStyle("-fx-background-color: lightblue");
        column1.setSpacing(20); // Set spacing between items
        column2.setStyle("-fx-background-color: lightblue");
        column2.setSpacing(20); // Set spacing between items
        
        ArrayList<QuizData> quizCreated = Quiz.getQuizUser(Lantern.getConn(), User.getCurrentUser().getUsername());
        int size = quizCreated.size();
        for (int i = 0; i <size ; i++) {

            String labelText1 = quizCreated.get(i).getQuizTitle();
            String labelText2 = quizCreated.get(i).getTheme();
            String labelText3 = quizCreated.get(i).getDescription();
            String labelText4 = quizCreated.get(i).getContent();
            
        
            BorderPane borderPane = BPForAllQuiz(labelText1, labelText2, labelText3, labelText4);
            if(i%2==0){
                column1.getChildren().addAll(borderPane);
            }else{
                column2.getChildren().addAll(borderPane);
            }
        }

        if (size % 2 == 0) {
            column1.getChildren().add(AddBorderPane());
        } else {
            column2.getChildren().add(AddBorderPane());
        }
        //creating scrollpane
        ScrollPane scrollPane1 = new ScrollPane(row);
        scrollPane1.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); //Never show horizontal scrollbar
        scrollPane1.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); // Alaways show vertical scrollbar as needed

        scrollPane1.setFitToWidth(true);
        scrollPane1.setFitToHeight(true);
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(scrollPane1);

        VBox mainvbox = new VBox();
        mainvbox.getChildren().addAll(vbox1, borderPane);
       return mainvbox;
    }


    public static BorderPane BPForAllQuiz(String labelText1, String labelText2, String labelText3, String labelText4) {
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #226c94");
        borderPane.setPadding(new Insets(15));

        Label label1 = new Label(labelText1);
        label1.setTextFill(Color.WHITE);
        label1.setFont(Font.font("Arial", FontWeight.BOLD, 25));
        Label label2 = new Label(labelText2);
        label2.setTextFill(Color.WHITE);
        Label label3 = new Label(labelText3);
        label3.setTextFill(Color.WHITE);
        Label label4 = new Label(labelText4);
        label4.setTextFill(Color.WHITE);
        MenuButton menubutton = new MenuButton();
        menubutton.setText("Settings");
        MenuItem editItem = new MenuItem("Edit");
        MenuItem deleteItem = new MenuItem("Delete");
        menubutton.getItems().addAll(editItem, deleteItem);
        menubutton.setAlignment(Pos.TOP_RIGHT);

        editItem.setOnAction(event -> {
            //method to display the form (which all textfield contains the info in db)
            //--> then update the latest row into db
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(showEditPage(labelText1,labelText2,labelText3,labelText4), 400, 300));
            stage.setTitle("Edit Event");
            stage.showAndWait();
            //edit table in quizAttempt

            
  
        });

        deleteItem.setOnAction(event ->{
            QuizData deleteQuiz = new QuizData(labelText1, labelText2, labelText3, labelText4); 
            Quiz.deleteQuiz2(Lantern.getConn(),deleteQuiz,User.getCurrentUser().getUsername());
            //delete done
            //but how to drop the column in QuizAttempt column?
            //update the latest list on the gui, how?

        });


        BorderPane.setAlignment(label1, Pos.TOP_LEFT);
        BorderPane.setAlignment(label2, Pos.TOP_LEFT);
        BorderPane.setAlignment(label3, Pos.CENTER_LEFT);
        BorderPane.setAlignment(label4, Pos.CENTER_LEFT);
        BorderPane.setAlignment(menubutton, Pos.TOP_RIGHT);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox topBox = new HBox(label1, spacer, menubutton);
        topBox.setPrefHeight(40);
        VBox middleBox = new VBox(label2, label3);
        HBox bottomBox = new HBox(label4);

        borderPane.setTop(topBox);
        borderPane.setCenter(middleBox);
        borderPane.setBottom(bottomBox);

        return borderPane;
    }

    public static BorderPane AddBorderPane() {
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color:lightyellow");
        borderPane.setPadding(new Insets(15));
       
        Button addButton = new Button("Add Quiz");    
        borderPane.setCenter(addButton);

        addButton.setOnAction(event -> {
            VBox content = EducatorCreateQuiz.tabCreateQuiz();
            Stage stage = new Stage();
            stage.setScene(new Scene(content, 500, 300));
            stage.setTitle("Create Quiz");
            stage.show();
        });

        BorderPane.setAlignment(addButton, Pos.CENTER);

        return borderPane;
    }

    public static VBox showEditPage(String title, String theme, String description, String content) {
            Label Qtitle = new Label("Edit Quiz");
            Qtitle.setPadding(new Insets(10, 0, 0, 10));
            Qtitle.setTextFill(Color.BLACK);
            Qtitle.setStyle("-fx-font-size: 20px;-fx-font-weight: bold");
    
            Label quizTitle = new Label("Quiz Title:");
            Label quizDescription = new Label("Quiz Description:");
            Label quizTheme = new Label("Quiz Theme:");
            Label quizContent = new Label("Quiz Content:");
            VBox vbox = new VBox();
            vbox.getChildren().addAll(quizTitle, quizDescription, quizTheme, quizContent);
            vbox.setStyle("-fx-font-size: 20px;-fx-font-weight: bold");
    
            TextField quizTitleTF = new TextField(title);
            TextArea quizDescriptionTA = new TextArea(description);
            ComboBox<String> themeComboBox = new ComboBox<>();
            themeComboBox.getItems().addAll("Science", "Technology", "Engineering", "Mathematics");
            themeComboBox.setValue(theme);
            themeComboBox.setEditable(true); 
            TextArea quizContentTF = new TextArea(content);
    
            quizTitleTF.setPromptText("Enter title");
            quizDescriptionTA.setPromptText("Enter description");
            quizContentTF.setPromptText("Enter content");
    
            Button saveBtn = new Button("Save");
            Button cancelBtn = new Button("Cancel");
            saveBtn.setStyle("-fx-background-color: #000000; -fx-text-fill: white");
            cancelBtn.setStyle("-fx-background-color:#000000; -fx-text-fill: white");

            saveBtn.setOnAction(e -> {
                if (quizTitleTF.getText().isEmpty() || quizDescriptionTA.getText().isEmpty() ||
                        themeComboBox.getValue() == null || quizContentTF.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Please fill in all fields before saving.", ButtonType.OK);
                    alert.showAndWait();
                } else {
                    String QTitle = quizTitleTF.getText();
                    String Qdescription = quizDescriptionTA.getText();
                    String Qtheme = themeComboBox.getValue();
                    String Qcontent = quizContentTF.getText();

                    QuizData newQuiz = new QuizData(QTitle, Qdescription, Qtheme, Qcontent);
                    QuizData oldQuiz = new QuizData(title, description, theme, content);
                    boolean updateQuiz = updateQuiz(Lantern.getConn(), oldQuiz, newQuiz, User.getCurrentUser().getUsername());
            

                    if (updateQuiz) {
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setContentText("Quiz updated successfully!");
                        alert.show();
                        
                        //how to straight away update in the ui 
        
                    } else {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Failed to update quiz.");
                        alert.showAndWait();
                    }
                }

                });
             
    
                GridPane gridPane = new GridPane();
                gridPane.setMinSize(400, 200);
                gridPane.setPadding(new Insets(10, 10, 10, 10));
                gridPane.setVgap(6);
                gridPane.setHgap(6);
        
                // Set ColumnConstraints
                ColumnConstraints column1 = new ColumnConstraints();
                column1.setPercentWidth(20);
                ColumnConstraints column2 = new ColumnConstraints();
                column2.setPercentWidth(80);
                gridPane.getColumnConstraints().addAll(column1, column2);
        
                // Set RowConstraints
                for (int i = 0; i < 7; i++) {
                    RowConstraints row = new RowConstraints();
                    row.setVgrow(Priority.ALWAYS);
                    gridPane.getRowConstraints().add(row);
                }
        
                gridPane.setAlignment(Pos.CENTER);
                gridPane.add(quizTitle, 0, 0);
                gridPane.add(quizTitleTF, 1, 0);
                gridPane.add(quizDescription, 0, 1);
                gridPane.add(quizDescriptionTA, 1, 1);
                gridPane.add(quizTheme, 0, 2);
                gridPane.add(themeComboBox, 1, 2);
                gridPane.add(quizContent, 0, 3);
                gridPane.add(quizContentTF, 1, 3);
        
                gridPane.add(saveBtn, 0, 5);
                gridPane.add(cancelBtn, 0, 6);
        
                VBox mainvBox = new VBox();
                mainvBox.getChildren().addAll(Qtitle, gridPane);
                mainvBox.setStyle("-fx-background-color: #e1e8f0; -fx-font-weight: bold;");
                VBox.setVgrow(mainvBox, Priority.ALWAYS);
        
                return mainvBox;
            }

    public static boolean updateQuiz(Connection conn, QuizData oldquiz, QuizData newquiz, String username) {
         Login_Register lg=new Login_Register();
         int id =lg.getID(username, conn);
        try {
            String query = "UPDATE Quiz SET quizTitle = ?, description = ?, theme = ?, content = ? WHERE main_id = ? AND quizTitle = ? AND description = ? AND theme = ? AND content = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, newquiz.getQuizTitle());
            statement.setString(2, newquiz.getDescription());
            statement.setString(3, newquiz.getTheme());
            statement.setString(4, newquiz.getContent());
            statement.setInt(5,id);

            statement.setString(6, oldquiz.getQuizTitle());
            statement.setString(7, oldquiz.getDescription());
            statement.setString(8, oldquiz.getTheme());
            statement.setString(9,oldquiz.getContent());
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0; 
    
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    

}



