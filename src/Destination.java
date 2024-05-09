import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Destination {
    private String name;
    private String coordinate;
    private double X;
    private double Y;
    

    public Destination(String name, String coordinate) {
        this.name = name;
        this.coordinate = coordinate;
        convert(coordinate);
    }
    public void convert(String value){
        String[] coordinates = value.split(",");
        X = Double.parseDouble(coordinates[0]);
        Y = Double.parseDouble(coordinates[1]);
    }

    public String getName() {
        return name;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public double getX() {
        return X;
    }

    public double getY() {  
        return Y;
    }
}

    

