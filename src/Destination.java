import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Destination {
    private String name;
    private double x;
    private double y;

    public Destination(String name, double x, double y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}

    private double calculateDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public void suggestDestinations(double userX, double userY) {
        ArrayList<Double> distances = new ArrayList<>();

        for (int i = 0; i < destinations.size(); i++) {
            double distance = calculateDistance(userX, userY, X.get(i), Y.get(i));
            distances.add(distance);
        }

        for (int i = 0; i < destinations.size() - 1; i++) {
            for (int j = i + 1; j < destinations.size(); j++) {
                if (distances.get(i) > distances.get(j)) {
                    double tempDistance = distances.get(i);
                    distances.set(i, distances.get(j));
                    distances.set(j, tempDistance);

                    String tempDestination = destinations.get(i);
                    destinations.set(i, destinations.get(j));
                    destinations.set(j, tempDestination);
                }
            }
        }
    }
}
