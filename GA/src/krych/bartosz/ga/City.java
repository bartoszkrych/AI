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
}
