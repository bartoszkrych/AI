package krych.bartosz.ga;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GeneticAlgorithm {
    private int populationSize;
    //    private double mutationProb;
//    private double crossProb;
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
        for (int i = 0; i < gen.size(); i++) {
            randRoute[i] = cities[genInt[i]];
        }
        return new Individual(randRoute);
    }
}
