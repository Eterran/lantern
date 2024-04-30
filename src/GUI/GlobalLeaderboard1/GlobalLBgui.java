package GUI.GlobalLeaderboard1;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

//find the launch.json file in .vscode then paste your JavaFX SDK path to run
public class GlobalLBgui {

    // public static void main(String[] args) {
    //     launch(args);
    // }

    // @Override
    // public void start(Stage primaryStage) throws Exception {
      
    //     Scene scene = new Scene(loadGL());
    //     primaryStage.setResizable(true);
    //     primaryStage.setTitle("Global Leaderboard");
    //     primaryStage.setScene(scene);
    //     primaryStage.show();

    // }
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

    public static VBox loadGL1(){
        Label title = new Label("Global Leaderboard") ;
        HBox titleBox = new HBox(title);
        title.setFont(Font.font("Lato", FontWeight.BOLD, 30));
        titleBox.setAlignment(javafx.geometry.Pos.CENTER); // Center the title label
        title.setStyle("-fx-text-fill: white;");

        BorderPane borderPane = new BorderPane();
        //borderPane.setPadding(new Insets(0));

        Label rankLabel = new Label("Rank");
        rankLabel.setFont(Font.font(20));
        rankLabel.setStyle("-fx-text-fill: white;");

        Label usernameLabel = new Label("Username");
        usernameLabel .setFont(Font.font(20));
        usernameLabel.setStyle("-fx-text-fill: white;");

        Label pointsLabel = new Label("Points");
        pointsLabel.setFont(Font.font(20));
        pointsLabel.setStyle("-fx-text-fill: white;");

        borderPane.setLeft(rankLabel);
        borderPane.setCenter(usernameLabel);
        borderPane.setRight(pointsLabel);

        AnchorPane.setLeftAnchor(borderPane, 50.0);
        AnchorPane.setRightAnchor(borderPane, 50.0);
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(borderPane);
    
        ListView<String> listView1 = new ListView<>();
        listView1.setStyle("-fx-background-color:darkblue;");
        ListView<String> listView2 = new ListView<>();
        listView2.setStyle("-fx-background-color:darkblue;");
        ListView<String> listView3 = new ListView<>();
        listView3.setStyle("-fx-background-color:darkblue;");
        ListView<String> listView4 = new ListView<>();
        listView4.setStyle("-fx-background-color:darkblue;");
        ListView<String> listView5 = new ListView<>();
        listView5.setStyle("-fx-background-color:darkblue;");

        VBox listViewsVBox = new VBox(10);
        listViewsVBox.getChildren().addAll(listView1, listView2, listView3, listView4, listView5);
       

        VBox mainVBox = new VBox(5);
        mainVBox.getChildren().addAll(titleBox,anchorPane, listViewsVBox);
        mainVBox.setStyle("-fx-background-color: black;");
        
        return mainVBox;
    }

   
}
