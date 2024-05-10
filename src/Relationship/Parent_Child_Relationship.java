package Relationship;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import Database.ParentChildren;

public class Parent_Child_Relationship {
    private ParentChildren parentChildren;

    public Parent_Child_Relationship() {
        this.parentChildren = new ParentChildren();
    }

    public void readRelationship(Connection connection) {
        try {
            Scanner s = new Scanner(new FileInputStream("lantern-1/ParentChild.txt"));
            while (s.hasNextLine()) {
                String line = s.nextLine();
                String[] parts = line.split(",");
                String parentName = parts[0].trim();
                String childName = parts[1].trim();

                // Request parent to add child
                parentChildren.request(connection, childName, parentName);
            }
            s.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void createRelationship(Connection connection, String childName, String parentName) {
        try {
            parentChildren.request(connection, childName, parentName);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void acceptRequest(Connection connection, String childName, String parentName) {
        try {
            parentChildren.acceptRequest(connection, childName, parentName);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void declineRequest(Connection connection, String childName, String parentName) {
        try {
            parentChildren.declineRequest(connection, childName, parentName);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
