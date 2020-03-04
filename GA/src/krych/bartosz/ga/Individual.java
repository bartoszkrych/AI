package krych.bartosz.ga;

import java.text.DecimalFormat;

public class Individual {
    private City[] route;
    private Double distance;
    private Double fitness;

    public Individual(City[] route) {
        this.route = route;
        distance = 0D;
        fitness = 0D;
        getFitness();
    }

    public Double getDistance() {
        if (distance == 0) {
            Double pathDist = 0D;
            City fromCity;
            City toCity;
            for (int i = 0; i < route.length; i++) {
                fromCity = route[i];
                if (i + 1 < route.length)
                {
                    toCity = route[i+1];
                } else {
                    toCity = route[0];
                }
                pathDist += fromCity.distance(toCity);
            }
            distance = pathDist;
        }
        return distance;
    }

    public Double getFitness() {
        if (fitness == 0) {
            fitness = 1 / getDistance();
        }
        return fitness;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.#########");

        return "Individual{" +
                "distance=" + distance +
                ", fitness=" + df.format(fitness) +
                '}';
    }
}
