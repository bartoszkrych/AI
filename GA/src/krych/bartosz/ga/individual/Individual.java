package krych.bartosz.ga.individual;

import krych.bartosz.ga.TSPProblem;

public class Individual {
    private int[] genotype;
    private Double fitness;

    public Individual(int[] route, TSPProblem tsp) {
        this.genotype = route;
        this.fitness = tsp.getDistance(route);
    }

    public Double getFitness() {
        return fitness;
    }

    public int getCityByIdx(int idx) {
        return genotype[idx];
    }

    public int[] getGenotype() {
        return genotype;
    }

    @Override
    public String toString() {
        return "Individual{" +
                "fitness=" + fitness +
                '}';
    }
}
