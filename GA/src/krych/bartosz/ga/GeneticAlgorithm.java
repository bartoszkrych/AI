package krych.bartosz.ga;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithm {
    private int populationSize;
    private City[] cities;
    private int countCity;

    public GeneticAlgorithm(int populationSize, City[] cities) {
        this.populationSize = populationSize;
        this.cities = cities;
        countCity = cities.length;
    }

    private Individual generateRandIndi() {
        List<Integer> gen = new ArrayList<>();
        for (int i = 0; i < countCity; i++) {
            gen.add(i);
        }
        Collections.shuffle(gen);

        Integer[] genInt = new Integer[countCity];
        gen.toArray(genInt);

        City[] randRoute = new City[countCity];
        for (int i = 0; i < countCity; i++) {
            randRoute[i] = cities[genInt[i]];
        }
        return new Individual(randRoute);
    }

    private Individual generateGreedyIndi() {
        List<City> liCities = new ArrayList<>();
        liCities.add(cities[new Random().nextInt(countCity)]);
        for (int i = 0; i < countCity - 1; i++) {
            Double minDis = Double.MAX_VALUE;
            City nearCity = null;
            for (int j = 0; j < countCity; j++) {
                if (!liCities.contains(cities[j])) {
                    Double dis = liCities.get(i).distance(cities[j]);
                    if (dis < minDis) {
                        minDis = dis;
                        nearCity = cities[j];
                    }
                }
            }
            liCities.add(nearCity);
        }

        City[] arrCity = new City[countCity];
        liCities.toArray(arrCity);
        return new Individual(arrCity);
    }

    public void showEx() {
        System.out.println("RANDOM");
        for (int i = 0; i < populationSize; i++) {
            System.out.println(generateGreedyIndi().toString());
        }

        System.out.println("GREEDY");
        for (int i = 0; i < populationSize; i++) {
            System.out.println(generateGreedyIndi().toString());
        }
    }


}
