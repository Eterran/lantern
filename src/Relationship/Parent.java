package Relationship;

import java.util.ArrayList;

public class Parent {
    private String name;
    private ArrayList<Child> children;

    public Parent(String name) {
        this.name = name;
        this.children = new ArrayList<Child>();
    }

    public void addChild(Child child) {
        children.add(child);
    }

    public void removeChild(Child child) {
        children.remove(child);
    }

    public String getName() {
        return name;
    }

    public ArrayList<Child> getChildren() {
        return children;
    }
    
}
