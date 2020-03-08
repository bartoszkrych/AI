package krych.bartosz.ga;

public class Individual {
    private Integer[] route;
    private Double distance;

    public Individual(Integer[] route, TSPProblem tsp) {
        this.route = route;
        distance = tsp.getDistance(this);
    }

    public Double getDistance() {
        return distance;
    }

    public int getCityByIdx(Integer idx) {
        return route[idx];
    }

    @Override
    public String toString() {
        return "Individual{" +
                "distance=" + distance +
                '}';
    }
}
