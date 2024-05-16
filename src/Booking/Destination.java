package Booking;

public class Destination {
    private String name;
    private String coordinate;
    private double distance;
    private double X;
    private double Y;
    private String id;

    private static final String[] destinationNames = {
            "Petrosains Science Discovery Centre",
            "Tech Dome Penang",
            "Agro Technology Park in MARDI",
            "National Science Centre",
            "Marine Aquarium and Museum",
            "Pusat Sains & Kreativiti Terengganu",
            "Biomedical Museum",
            "Telegraph Museum",
            "Penang Science Cluster"
    };

    public Destination(String name, String coordinate) {
        this.name = name;
        this.coordinate = coordinate;
        this.id = generateId(name);
        convert(coordinate);
    }

    private void convert(String value) {
        String[] coordinates = value.split(",");
        X = Double.parseDouble(coordinates[0]);
        Y = Double.parseDouble(coordinates[1]);
    }

    private String generateId(String name) {
        for (int i = 0; i < destinationNames.length; i++) {
            if (destinationNames[i].equals(name)) {
                return "ID" + (i + 1);
            }
        }
        return "Unknown";
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

    public String getId() {
        return id;
    }
    public String toString(){
        return name;
    }
}
