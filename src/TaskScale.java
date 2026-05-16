public enum TaskScale {
    EVERYDAY("hour"), GLOBAL("day"), GENERAL("day");

    private final String value;

    TaskScale(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }

}
