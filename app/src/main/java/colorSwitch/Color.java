public enum Color {
    TURQUOISE("TURQUOISE"),
    MAGENTA("#ff1dce"),
    ORANGE("#ff8300"),
    DARK_BLUE("#4d4df8");

    public final String colorCode;

    private Color(String colorCode) {
        this.colorCode = colorCode;
    }
}