package krych.bartosz.ga.individual;

import krych.bartosz.ga.TSPProblem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandAlgorithm implements AlgorithmIndividual {

    private int countCity;
    private TSPProblem tsp;

    public RandAlgorithm(TSPProblem tsp) {
        this.countCity = tsp.getCountCity();
        this.tsp = tsp;
    }

    private Individual generateRandIndi() {
        List<Integer> gen = new ArrayList<>();
        for (int i = 0; i < countCity; i++) {
            gen.add(i);
        }
        Collections.shuffle(gen);

        Integer[] genInt = new Integer[countCity];
        gen.toArray(genInt);
        return new Individual(genInt, tsp);
    }

    public Individual randAlgorithm(int numberOfRand) {
        Individual bestInd = generateRandIndi();
        Individual newInd;
        for (int i = 0; i < numberOfRand; i++) {
            newInd = generateRandIndi();
            if (bestInd.getFitness() > newInd.getFitness()) bestInd = newInd;
        }
        return bestInd;
    }

    @Override
    public Individual getNewIndividual() {
        return generateRandIndi();
    }

    @Override
    public Individual getNewIndividual(int numberOfRand) {
        return randAlgorithm(numberOfRand);
    }
}
