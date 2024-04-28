import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import java.util.ArrayList;

public class globalLController {

    @FXML
    private TableView<data> LeaderboardTable;

    @FXML
    private Label gLTitle;

    @FXML
    private BorderPane leaderBoardPane;

    @FXML
    private TableColumn<data, Integer> rank;

    @FXML
    private TableColumn<data, String> username;

    @FXML
    private TableColumn<data, Integer> points;

    public void initialize() {

        
        rank.prefWidthProperty().bind(LeaderboardTable.widthProperty().multiply(0.2)); 
        username.prefWidthProperty().bind(LeaderboardTable.widthProperty().multiply(0.5)); 
        points.prefWidthProperty().bind(LeaderboardTable.widthProperty().multiply(0.3)); 

        rank.setCellValueFactory(new PropertyValueFactory<>("rank"));
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        points.setCellValueFactory(new PropertyValueFactory<>("points"));

        // Add  data to the table
        ArrayList<data> datalist = new ArrayList<>();
        datalist.add(new data(1, "John Doe", 100));
        datalist.add(new data(2, "Jane Smith", 80));
       

        LeaderboardTable.getItems().addAll(datalist);

        
    }

    public static class data {
        private final int rank;
        private final String username;
        private final int points;

        public data(int rank, String username, int points) {
            this.rank = rank;
            this.username = username;
            this.points = points;
        }

        public int getRank() {
            return rank;
        }

        public String getUsername() {
            return username;
        }

        public int getPoints() {
            return points;
        }
    }
}
