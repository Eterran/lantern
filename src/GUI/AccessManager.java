package GUI;

import java.sql.Connection;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import Database.User;
import Database.Event;
import Database.Quiz;
import Database.Booking;

public class AccessManager {
    private final Map<UserRole, List<Supplier<Button>>> buttonAccessRules1;
    private final Map<UserRole, List<Supplier<Button>>> buttonAccessRules2;
    private final Map<UserRole, List<Supplier<Button>>> retractedButtonAccessRules1;
    private final Map<UserRole, List<Supplier<Button>>> retractedButtonAccessRules2;
    private final Map<UserRole, List<Supplier<VBox>>> vBoxAccessRules;
    private final Map<UserRole, List<Supplier<VBox>>> sidebarAccessRules1;
    private final Map<UserRole, List<Supplier<VBox>>> sidebarAccessRules2;
    
    private Connection conn = Lantern.getConn();

    public AccessManager() {
        buttonAccessRules1 = new EnumMap<>(UserRole.class);
        buttonAccessRules2 = new EnumMap<>(UserRole.class);
        retractedButtonAccessRules1 = new EnumMap<>(UserRole.class);
        retractedButtonAccessRules2 = new EnumMap<>(UserRole.class);
        vBoxAccessRules = new EnumMap<>(UserRole.class);
        sidebarAccessRules1 = new EnumMap<>(UserRole.class);
        sidebarAccessRules2 = new EnumMap<>(UserRole.class);

        buttonAccessRules1.put(UserRole.EDUCATOR, List.of(
            createButton("Create Quizzes", () -> {
                Sidebar.selectTab(5);
            }, Sidebar.getCreateQuizzesIcon())
        ));
        buttonAccessRules2.put(UserRole.EDUCATOR, List.of(
            createButton("Create Events", () -> {
                Sidebar.selectTab(6);
            }, Sidebar.getCreateEventIcon())
        ));
        retractedButtonAccessRules1.put(UserRole.EDUCATOR, List.of(
            createdRetractedButton(Sidebar.getRCreateQuizzesIcon(), () -> {
                Sidebar.selectTab(5);
            })
        ));
        retractedButtonAccessRules2.put(UserRole.EDUCATOR, List.of(
            createdRetractedButton(Sidebar.getRCreateEventIcon(), () -> {
                Sidebar.selectTab(6);
            })
        ));
        vBoxAccessRules.put(UserRole.EDUCATOR, List.of(
            this::createEducatorProfileVBox
        ));
        sidebarAccessRules1.put(UserRole.EDUCATOR, List.of(
            this::createEducatorSidebar1
        ));
        sidebarAccessRules2.put(UserRole.EDUCATOR, List.of(
            this::createEducatorSidebar2
        ));
        buttonAccessRules1.put(UserRole.STUDENT, List.of(
            createButton("Quizzes", () -> {
                Sidebar.selectTab(5);
            }, Sidebar.getQuizzesIcon())
        ));
        buttonAccessRules2.put(UserRole.STUDENT, List.of(
            createButton("Friends", () -> {
                Sidebar.selectTab(6);
            }, Sidebar.getFriendlistIcon())
        ));
        retractedButtonAccessRules1.put(UserRole.STUDENT, List.of(
            createdRetractedButton(Sidebar.getRQuizzesIcon(), () -> {
                Sidebar.selectTab(5);
            })
        ));
        retractedButtonAccessRules2.put(UserRole.STUDENT, List.of(
            createdRetractedButton(Sidebar.getRFriendlistIcon(), () -> {
                Sidebar.selectTab(6);
            })
        ));
        vBoxAccessRules.put(UserRole.STUDENT, List.of(
            this::createStudentProfileVBox
        ));
        sidebarAccessRules1.put(UserRole.STUDENT, List.of(
            this::createStudentSidebar1
        ));
        sidebarAccessRules2.put(UserRole.STUDENT, List.of(
            this::createStudentSidebar2
        ));
        buttonAccessRules1.put(UserRole.PARENT, List.of(
            createButton("Make Bookings", () -> {
                Sidebar.selectTab(5);
            }, Sidebar.getBookingsIcon())
        ));
        retractedButtonAccessRules1.put(UserRole.PARENT, List.of(
            createdRetractedButton(Sidebar.getRBookingsIcon(), () -> {
                Sidebar.selectTab(5);
            })
        ));
        vBoxAccessRules.put(UserRole.PARENT, List.of(
            this::createParentProfileVBox
        ));
        sidebarAccessRules1.put(UserRole.PARENT, List.of(
            this::createParentSidebar1
        ));
        sidebarAccessRules2.put(UserRole.PARENT, List.of(
            this::createParentSidebar1
        ));
    }

    public List<Supplier<Button>> getAccessibleButtons1(UserRole role) {
        return buttonAccessRules1.getOrDefault(role, List.of());
    }
    public List<Supplier<Button>> getAccessibleButtons2(UserRole role) {
        return buttonAccessRules2.getOrDefault(role, List.of());
    }
    public List<Supplier<VBox>> getAccessibleVBoxes(UserRole role) {
        return vBoxAccessRules.getOrDefault(role, List.of());
    }
    public List<Supplier<VBox>> getAccessibleSidebar1(UserRole role) {
        return sidebarAccessRules1.getOrDefault(role, List.of());
    }
    public List<Supplier<VBox>> getAccessibleSidebar2(UserRole role) {
        return sidebarAccessRules2.getOrDefault(role, List.of());
    }
    public List<Supplier<Button>> getAccessibleRetractedButtons1(UserRole role) {
        return retractedButtonAccessRules1.getOrDefault(role, List.of());
    }
    public List<Supplier<Button>> getAccessibleRetractedButtons2(UserRole role) {
        return retractedButtonAccessRules2.getOrDefault(role, List.of());
    }
    // private Supplier<Button> createButton(String text, Runnable action) {
    //     return () -> {
    //         Button button = new Button(text);
    //         button.setOnAction(e -> action.run());
    //         button.getStyleClass().add("sidebar_button");
    //         button.setMaxWidth(Double.MAX_VALUE);
    //         return button;
    //     };
    // }
    private Supplier<Button> createButton(String text, Runnable action, ImageView img) {
        return () -> {
            Button button = new Button();
            HBox hbox = new HBox();
            Text tex = new Text(text);
            tex.setFill(javafx.scene.paint.Color.WHITE);
            hbox.getChildren().addAll(img, Lantern.createVerticalSeparator(8), tex);
            button.setGraphic(hbox);
            button.setOnAction(e -> action.run());
            button.getStyleClass().add("sidebar_button");
            button.setMaxWidth(Double.MAX_VALUE);
            return button;
        };
    }
    private Supplier<Button> createdRetractedButton(ImageView img, Runnable action){
        return () -> {
            Button button = new Button();
            HBox hbox = new HBox();
            hbox.getChildren().add(img);
            button.setGraphic(hbox);
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
        HBox quizzes = Lantern.createInfoHBox("QUIZZES CREATED: ", Quiz.getNumberOfQuiz(conn, User.getCurrentUser().getUsername()), new Insets(8, 10, 8, 15));
        HBox events = Lantern.createInfoHBox("EVENTS CREATED: ", Event.getNumberOfEvent(conn, User.getCurrentUser().getUsername()), new Insets(8, 10, 8, 15));
        vBox.getChildren().addAll(quizzes, events);
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
        HBox quizzes = Lantern.createInfoHBox("BOOKINGS MADE: ", Booking.viewBooking(conn, User.getCurrentUser().getUsername()).size(), new Insets(8, 10, 8, 15));
        vBox.getChildren().add(quizzes);
        return vBox;
    }
    private VBox createEducatorSidebar1() {
        VBox vBox = EducatorCreateEvent.tabCreateEvent();
        return vBox;
    }
    private VBox createEducatorSidebar2() {
        VBox vBox = EducatorCreateQuiz.tabCreateQuiz();
        return vBox;
    }
    private VBox createStudentSidebar1() {
        VBox vBox = QuizPage.quizPageTab();
        return vBox;
    }
    private VBox createStudentSidebar2() {
        VBox vBox = FriendList.loadFriendList();
        return vBox;
    }
    private VBox createParentSidebar1() {
        VBox vBox = BookingPageGUI.BookingTabPage();
        return vBox;
    }
    public static enum UserRole {
        EDUCATOR, STUDENT, PARENT
    }
}