public enum SortParameters {
    fromAtoZ("from A to Z"), fromZtoA("from Z to A"), fromLOWtoHiGH("from LOW to HIGH"), fromHIGHToLOW("from HIGH to LOW"), fromNearestToFarthest("from Nearest to Farthest"), fromFarthestToNearest("from Farthest to Nearest");

    private final String parameter;

    SortParameters(String name) {
        this.parameter = name;
    }
    public String getParameter() {
        return parameter;
    }
}
