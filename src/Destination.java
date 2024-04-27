import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Destination {
    private ArrayList<String> destinations;
    private ArrayList<Double> X;
    private ArrayList<Double> Y;
    private String[] data;
    private int size = 0;

    public Destination() {
        destinations = new ArrayList<>();
        X = new ArrayList<>();
        Y = new ArrayList<>();
        readDataFromFile();
    }

    private void readDataFromFile() {
        try (Scanner s = new Scanner(new FileInputStream("Eterran/lantern/Booking.txt"))) {
            while (s.hasNextLine()) {
                size++;
                s.nextLine();
            }

            data = new String[size];
            s = new Scanner(new FileInputStream("booking.txt"));

            int i = 0;
            while (s.hasNextLine()) {
                String line = s.nextLine();
                data[i] = line;
                i++;
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        }

        for (int i = 0; i < size; i++) {
            if (i % 2 == 0) {
                destinations.add(data[i]);
            } else {
                String[] xy = data[i].split(",");
                X.add(Double.parseDouble(xy[0].trim()));
                Y.add(Double.parseDouble(xy[1].trim()));
            }
        }
    }

    public ArrayList<String> getDestinations() {
        return destinations;
    }

    public ArrayList<Double> getX() {
        return X;
    }

    public ArrayList<Double> getY() {
        return Y;
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
