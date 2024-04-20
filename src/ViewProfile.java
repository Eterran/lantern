public class ViewProfile {
    private User user;

    public ViewProfile(User user) {
        this.user = user;
    }

    public void displayProfile() {
        System.out.println("Name: " + user.getUsername());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Role: " + user.getRole());
        System.out.println("Coordinates: " + user.getCoordinate());

        String role = user.getRole();
        if (role.equals("Educators")) {
            displayEducatorInfo();
        } else if (role.equals("Parents")) {
            displayParentInfo();
        } else if (role.equals("Young Students")) {
            displayStudentInfo();
        }
    }

    private void displayEducatorInfo() {
        System.out.println("Number of quizzes created: " + getNumberOfQuizzesCreated());
        System.out.println("Number of events created: " + getNumberOfEventsCreated());
    }

    private void displayParentInfo() {
        System.out.println("Past bookings made:");
        for (String booking : user.getChildrens()) {
            System.out.println("- " + booking);
        }
    }

    private void displayStudentInfo() {
        System.out.println("Points: " + user.getPoints());
        System.out.println("Friends:");
        for (String friend : user.getParents()) {
            System.out.println("- " + friend);
        }
    }

    private int getNumberOfQuizzesCreated() {
        
        return 0; 
    }

    private int getNumberOfEventsCreated() {
        
        return 0; 
    }
}
