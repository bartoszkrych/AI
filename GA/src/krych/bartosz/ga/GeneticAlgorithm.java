package krych.bartosz.ga;

import krych.bartosz.ga.individual.AlgorithmIndividual;
import krych.bartosz.ga.individual.Individual;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithm {
    private TSPProblem tspProblem;
    private AlgorithmIndividual algorithmIndividual;
    private int posSize = 5;

    private int indiAlgNumber = 10;

    private int reproductSize = 10;


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
            population.add(algorithmIndividual.getNewIndividual(indiAlgNumber));
        }
        return population;
    }

    public List<Individual> selection(List<Individual> population) {
        List<Individual> selected = new ArrayList<>();
        for (int i = 0; i < reproductSize; i++) {
            selected.add(rouletteSelection(population));
        }
        return selected;
    }

    public Individual rouletteSelection(List<Individual> population) {
        double totalFit = population.stream().map(Individual::getFitness).mapToDouble(Double::doubleValue).sum();
        Random random = new Random();
        double selectedVal = random.nextDouble() * totalFit;
        double recVal = 1 / selectedVal;
        double curSum = 0D;
        for (Individual i : population) {
            curSum += 1 / i.getFitness();
            if (curSum >= recVal) {
                return i;
            }
        }
        return population.get(random.nextInt(posSize));
    }

}
