package krych.bartosz.ga;

public class City {
    private Double x;
    private Double y;

    public City(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "City{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public Double distance(City city) {
        Double xDis = Math.abs(x - city.x);
        Double yDis = Math.abs(y - city.y);
        return Math.sqrt((xDis * xDis) + (yDis * yDis));
    }
}
