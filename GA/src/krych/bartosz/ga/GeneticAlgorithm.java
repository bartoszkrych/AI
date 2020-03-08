package krych.bartosz.ga;

import krych.bartosz.ga.individual.AlgorithmIndividual;

public class GeneticAlgorithm {
    private TSPProblem tspProblem;
    private AlgorithmIndividual algorithmIndividual;


    public GeneticAlgorithm(TSPProblem tspProblem, AlgorithmIndividual algorithmIndividual) {
        this.tspProblem = tspProblem;
        this.algorithmIndividual = algorithmIndividual;
    }

    public void showEx() {
        for (int i = 0; i < tspProblem.getCountCity(); i++) {
            System.out.println(algorithmIndividual.getNewIndividual(i + 1 % tspProblem.getCountCity()).toString());
        }
    }


}
