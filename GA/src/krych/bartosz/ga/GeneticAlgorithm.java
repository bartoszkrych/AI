package krych.bartosz.ga;

import krych.bartosz.ga.individual.AlgorithmIndividual;
import krych.bartosz.ga.individual.Individual;

import java.util.ArrayList;
import java.util.List;

public class GeneticAlgorithm {
    private TSPProblem tspProblem;
    private AlgorithmIndividual algorithmIndividual;
    private int posSize = 5;

    private int indiAlgNumber = 10;


    public GeneticAlgorithm(TSPProblem tspProblem, AlgorithmIndividual algorithmIndividual) {
        this.tspProblem = tspProblem;
        this.algorithmIndividual = algorithmIndividual;
    }

    public void showEx() {
        for (int i = 0; i < tspProblem.getCountCity(); i++) {
            System.out.println(algorithmIndividual.getNewIndividual(i + 1 % tspProblem.getCountCity()).toString());
        }
    }

    public List<Individual> initPopulation() {
        List<Individual> population = new ArrayList<>();
        for (int i = 0; i < posSize; i++) {
            population.add(algorithmIndividual.getNewIndividual(10));
        }

        return population;
    }


}
