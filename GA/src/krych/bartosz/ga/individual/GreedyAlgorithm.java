package krych.bartosz.ga.individual;

import krych.bartosz.ga.City;
import krych.bartosz.ga.TSPProblem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GreedyAlgorithm implements AlgorithmIndividual {

    private int countCity;
    private TSPProblem tsp;

    public GreedyAlgorithm(TSPProblem tsp) {
        this.countCity = tsp.getCountCity();
        this.tsp = tsp;
    }

    public Individual generateGreedyIndi(int cityIdx) {
        List<Integer> liCities = new ArrayList<>();
        liCities.add(cityIdx);
        City[] cities = tsp.getCities();
        for (int i = 0; i < countCity - 1; i++) {
            Double minDis = Double.MAX_VALUE;
            int nearCityIdx = -1;
            for (int j = 0; j < countCity; j++) {
                if (!liCities.contains(j)) {
                    Double dis = cities[liCities.get(i)].distance(cities[j]);
                    if (dis < minDis) {
                        minDis = dis;
                        nearCityIdx = j;
                    }
                }
            }
            liCities.add(nearCityIdx);
        }

        Integer[] arrCity = new Integer[countCity];
        liCities.toArray(arrCity);
        return new Individual(arrCity, tsp);
    }

    public Individual getNewIndividual() {
        return generateGreedyIndi(new Random().nextInt(countCity));
    }

    public Individual getNewIndividual(int cityIdx) {
        return generateGreedyIndi(cityIdx);
    }

}
