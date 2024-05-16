package GUI;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainEducatorCreateQuiz{

    public static VBox QuizButton() {
        HBox hbox = new HBox(20);
        Button button = new Button("Add");
        Button button1 = new Button("Edit");
        Button button2 = new Button("Delete");
        button.setPrefSize(100, 50);
        button1.setPrefSize(100, 50);
        button2.setPrefSize(100, 50);

        hbox.getChildren().addAll(button, button1, button2);

        VBox mainVBox = new VBox();
        mainVBox.getChildren().add(hbox);
        VBox.setVgrow(hbox, javafx.scene.layout.Priority.ALWAYS);

        button.setOnAction(e -> {
            VBox content = EducatorCreateQuiz.tabCreateQuiz();
            Stage stage = new Stage();
            stage.setScene(new Scene(content, 500, 300));
            stage.setTitle("Create Quiz");
            stage.show();

        });
        button1.setOnAction(e ->{
            //list out 
            //put in edit function once the user choose to edit

        });
        button2.setOnAction(e->{
            //delete 
            
        });
    
        return mainVBox;
        
    }

}

