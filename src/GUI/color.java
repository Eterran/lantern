package GUI;

public enum color {
    MAIN("#FF8F00"),
    ACCENT("#FDB85F"),
    ACCENT2("#F43030"),
    BACKGROUND("#9E9E9E"),
    SIDEBAR("#424242");

    private final String code;

    color(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
