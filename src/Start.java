import Database.User;
import GUI.Lantern;

public class Start extends Lantern{
    public static void main(String[] args) {
        User dumStu = new User();
        dumStu.dummyStudent();
        launch(args);
    }
}
