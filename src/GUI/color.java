package GUI;

public enum color {
    MAIN("#FF8F00"),
    ACCENT("#FDB85F"),
    BACKGROUND("#FEECD5"),
    SIDEBAR("#8B0000");

    private final String code;

    color(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
