package GUI;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import Database.User;

public class AccessManager {
    private final Map<UserRole, List<Supplier<Button>>> buttonAccessRules;
    private final Map<UserRole, List<Supplier<VBox>>> vBoxAccessRules;

    public AccessManager() {
        buttonAccessRules = new EnumMap<>(UserRole.class);
        vBoxAccessRules = new EnumMap<>(UserRole.class);

        buttonAccessRules.put(UserRole.EDUCATOR, List.of(
            createButton("Create Quizzes", () -> {
                Sidebar.push_SidebarHistory(4);
                Sidebar.setOneVisible(4);
            }),
            createButton("Create Events", () -> {
                Sidebar.push_SidebarHistory(5);
                Sidebar.setOneVisible(5);
            })
        ));
        vBoxAccessRules.put(UserRole.EDUCATOR, List.of(
                this::createEducatorProfileVBox
            ));
        buttonAccessRules.put(UserRole.STUDENT, List.of(
            createButton("Quizzes", () -> {
                Sidebar.push_SidebarHistory(4);
                Sidebar.setOneVisible(4);
            }),
            createButton("Friends", () -> {
                Sidebar.push_SidebarHistory(5);
                Sidebar.setOneVisible(5);
            })
        ));
        vBoxAccessRules.put(UserRole.STUDENT, List.of(
                this::createStudentProfileVBox
            ));
        buttonAccessRules.put(UserRole.PARENT, List.of(
            createButton("Make Bookings", () -> {
                Sidebar.push_SidebarHistory(4);
                Sidebar.setOneVisible(4);
            })
        ));
        vBoxAccessRules.put(UserRole.PARENT, List.of(
                this::createParentProfileVBox
            ));
    }

    public List<Supplier<Button>> getAccessibleButtons(UserRole role) {
        return buttonAccessRules.getOrDefault(role, List.of());
    }
    public List<Supplier<VBox>> getAccessibleVBoxes(UserRole role) {
        return vBoxAccessRules.getOrDefault(role, List.of());
    }
    private Supplier<Button> createButton(String text, Runnable action) {
        return () -> {
            Button button = new Button(text);
            button.setOnAction(e -> action.run());
            button.getStyleClass().add("sidebar_button");
            button.setMaxWidth(Double.MAX_VALUE);
            return button;
        };
    }
    public UserRole getUserRole(User user) {
        if(user.getRole().toLowerCase().equals("educator")) {
            return UserRole.EDUCATOR;
        } else if(user.getRole().toLowerCase().equals("student")) {
            return UserRole.STUDENT;
        } else if(user.getRole().toLowerCase().equals("parent")) {
            return UserRole.PARENT;
        } else {
            throw new IllegalArgumentException("Invalid user role: " + user.getRole());
        }
    }
    private VBox createEducatorProfileVBox() {
        VBox vBox = new VBox();
        //TODO database get quizzes created
        HBox quizzes = Lantern.createInfoHBox("QUIZZES CREATED: ", "User.getCurrentUser().get", new Insets(8, 10, 8, 15));
        vBox.getChildren().add(quizzes);
        return vBox;
    }
    private VBox createStudentProfileVBox() {
        VBox vBox = new VBox();
        HBox quizzes = Lantern.createInfoHBox("POINTS: ", User.getCurrentUser().getPoints(), new Insets(8, 10, 8, 15));
        vBox.getChildren().add(quizzes);
        return vBox;
    }
    private VBox createParentProfileVBox() {
        VBox vBox = new VBox();
        //TODO database get bookings made
        HBox quizzes = Lantern.createInfoHBox("BOOKINGS MADE: ", "User.getCurrentUser().get", new Insets(8, 10, 8, 15));
        vBox.getChildren().add(quizzes);
        return vBox;
    }

    public static enum UserRole {
        EDUCATOR, STUDENT, PARENT
    }
}