package krych.bartosz.ga.individual;

import krych.bartosz.ga.TSPProblem;

public class Individual {
    private Integer[] genotype;
    private Double fitness;

    public Individual(Integer[] route, TSPProblem tsp) {
        this.genotype = route;
        this.fitness = tsp.getDistance(this);
    }

    public Double getFitness() {
        return fitness;
    }

    public int getCityByIdx(Integer idx) {
        return genotype[idx];
    }

    public Integer[] getGenotype() {
        return genotype;
    }

    @Override
    public String toString() {
        return "Individual{" +
                "fitness=" + fitness +
                '}';
    }
}
