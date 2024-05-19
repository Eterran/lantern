package GUI;

import java.sql.Connection;
import java.util.ArrayList;

import Database.Quiz;
import Database.QuizData;
import Database.User;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
public class listOutQuizCreated{

    // @Override
    // public void start(Stage primaryStage) {

    //     // Set up the scene
    //     Scene scene = new Scene(educatorEditList(), 600, 400);
    //     primaryStage.setScene(scene);
    //     primaryStage.setTitle("Product Table");
    //     primaryStage.show();

    // }

    public static VBox educatorEditList(){
        BorderPane bp  = new BorderPane();
        Label title = new Label("Edit Quiz");
        title.setPadding(new Insets(10, 0, 10, 0));
        title.setStyle("-fx-font-size: 20px");
        VBox topvbox = new VBox();
        topvbox.setAlignment(javafx.geometry.Pos.CENTER);
        topvbox.setPadding(new Insets(0, 0, 10, 0));

         TableView<EditQuiz> tableView = new TableView<EditQuiz>();

         TableColumn<EditQuiz, String> titleCol = new TableColumn<EditQuiz, String>("Quiz Title");
          titleCol.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
          titleCol.setPrefWidth(200);
          titleCol.setResizable(false); 
          titleCol.setCellFactory(TextFieldTableCell.forTableColumn()); 
  
          TableColumn<EditQuiz, String> descriptionCol = new TableColumn<EditQuiz, String>("Description");
          descriptionCol.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
          descriptionCol.setPrefWidth(200);
          descriptionCol.setResizable(false); 
          descriptionCol.setCellFactory(TextFieldTableCell.forTableColumn()); 
  
          TableColumn<EditQuiz, String> themeCol = new TableColumn<EditQuiz, String>("Theme");
          themeCol.setCellValueFactory(cellData -> cellData.getValue().themeProperty());
          themeCol.setPrefWidth(100);
          themeCol.setResizable(false); 
          themeCol.setCellFactory(TextFieldTableCell.forTableColumn()); 

          TableColumn<EditQuiz, String> contentCol = new TableColumn<EditQuiz, String>("Content");
          contentCol.setCellValueFactory(cellData -> cellData.getValue().contentProperty());
          contentCol.setPrefWidth(100);
          contentCol.setResizable(false);
          contentCol.setCellFactory(TextFieldTableCell.forTableColumn()); 
  
          Button updateButton = new Button("Update");
        
          topvbox.getChildren().addAll(title, updateButton);
          tableView.getColumns().addAll(titleCol, descriptionCol, themeCol, contentCol);
         tableView.setColumnResizePolicy(TableView. CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);


        ArrayList<QuizData> quizList = Quiz.getQuizUser(Lantern.getConn(), User.getCurrentUser().getUsername());
        ObservableList<EditQuiz> data = FXCollections.observableArrayList();
        for (QuizData quiz : quizList) {
            EditQuiz editQuiz = new EditQuiz(
                    quiz.getQuizTitle(),
                    quiz.getDescription(),
                    quiz.getTheme(),
                    quiz.getContent()
            );
            data.add(editQuiz);
        };
    
    //     public static void updateLatestQuiz(Connection connection, QuizData quiz) {

    //    updateButton.setOnAction(event ->{
    //         Quiz.updateLatestQuiz(Lanter.getConn(), )
    //    });
          tableView.setItems(data);
          tableView.setEditable(true);
  
         bp.setTop(topvbox);
         bp.setCenter(tableView);
         VBox mainvbox = new VBox();
         mainvbox.getChildren().add(bp);
         VBox.setVgrow(bp, Priority.ALWAYS);
         return mainvbox;
    }



    // public static void main(String[] args) {
    //     launch(args);
    // }
}

class EditQuiz {
    private final StringProperty title;
    private final StringProperty description;
    private final StringProperty theme;
    private final StringProperty content;

    public EditQuiz(String title, String description, String theme, String content) {
        this.title = new SimpleStringProperty(title);
        this.description = new SimpleStringProperty(description);
        this.theme = new SimpleStringProperty(theme);
        this.content = new SimpleStringProperty(content);
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getTheme() {
        return theme.get();
    }

    public StringProperty themeProperty() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme.set(theme);
    }

    public String getContent() {
        return content.get();
    }

    public StringProperty contentProperty() {
        return content;
    }

    public void setContent(String content) {
        this.content.set(content);
    }
}
