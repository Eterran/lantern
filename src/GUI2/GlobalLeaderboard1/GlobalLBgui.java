
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//find the launch.json file in .vscode then paste your JavaFX SDK path to run
public class GlobalLBgui extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
      
        Scene scene = new Scene(loadGL());
        primaryStage.setResizable(true);
        primaryStage.setTitle("Global Leaderboard");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    public  VBox loadGL() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GLScene.fxml"));
        Parent root = loader.load();

        // Set the controller for the FXML file
        globalLController controller = loader.getController();
        
        VBox vbox = new VBox();
        vbox.setStyle("-fx-background-color:black"); 
        vbox.getChildren().add(root); 
        
        
        return vbox;
    }

   
}
