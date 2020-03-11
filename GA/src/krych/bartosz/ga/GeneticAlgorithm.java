package krych.bartosz.ga;

import krych.bartosz.ga.individual.AlgorithmIndividual;
import krych.bartosz.ga.individual.Individual;
import krych.bartosz.ga.individual.RandAlgorithm;

import java.util.*;
import java.util.stream.Collectors;

public class GeneticAlgorithm {
    private TSPProblem tspProblem;
    private int posSize = 1000;

    private int reproductSize = 100;
    private int maxIter = 1000;
    private int generationSize = 1000;
    private double crossProb = 0.6;
    private double mutProb = 0.1;
    private int genomeSize;

    private Individual bestIndividual;


    public GeneticAlgorithm(TSPProblem tspProblem) {
        this.tspProblem = tspProblem;
        this.genomeSize = tspProblem.getCountCity();
    }

    public List<Individual> initPopulation() {
        List<Individual> population = new ArrayList<>();
        AlgorithmIndividual algIndi = new RandAlgorithm(tspProblem);
        for (int i = 0; i < posSize; i++) {
            population.add(algIndi.getNewIndividual());
        }
        return population;
    }

    public void startAlgorithm() {
        List<Individual> population = initPopulation();
        bestIndividual = population.get(0);
        Individual popBestIndividual = bestIndividual;
        for (int i = 0; i < maxIter; i++) {
//            List<Individual> selected = selection(population);
            population = createGeneration(population);
            popBestIndividual = Collections.min(population, (p1, p2) -> p1.getFitness().compareTo(p2.getFitness()));
            if (bestIndividual.getFitness() > popBestIndividual.getFitness()) {
                bestIndividual = popBestIndividual;
                System.out.println("CHANGED IT");
            }
            System.out.println("pop: " + i + ",     best: " + popBestIndividual.toString());
        }

        System.out.println(bestIndividual.toString());
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
        float selectedVal = (float) (random.nextFloat() * totalFit);
        float recVal = (float) 1 / selectedVal;
        float curSum = 0F;
        for (Individual i : population) {
            curSum += (float) 1 / i.getFitness();
            if (curSum >= recVal) {
                return i;
            }
        }
        return population.get(random.nextInt(posSize));
    }


    public List<Individual> createGeneration(List<Individual> population) {
        List<Individual> generation = new ArrayList<>();
        int curGeneration = 0;
        Random random = new Random();
        while (curGeneration < generationSize) {
            List<Individual> parents = getParents(population);
            if (random.nextDouble() < crossProb) {
                List<Individual> children = crossoverPMX(parents);
                for (int i = 0; i < 2; i++) {
                    if (random.nextDouble() < mutProb) {
                        children.set(i, mutate(children.get(i)));
                    }
                }
                generation.addAll(children);
            } else {
                generation.addAll(parents);
            }
            curGeneration += 2;
        }
        return generation;
    }

    private List<Individual> getParents(List<Individual> list) {
        Random r = new Random();
        int length = list.size();

        for (int i = length - 1; i >= length - 2; --i) {
            Collections.swap(list, i, r.nextInt(i + 1));
        }
        return list.subList(length - 2, length);
    }

    private List<Individual> crossoverPMX(List<Individual> parents) {
        Random random = new Random();
        int breakpoint = random.nextInt(genomeSize);
        List<Individual> children = new ArrayList<>();

        List<Integer> parent1 = Arrays.stream(parents.get(0).getGenotype()).boxed().collect(Collectors.toList());
        List<Integer> parent2 = Arrays.stream(parents.get(1).getGenotype()).boxed().collect(Collectors.toList());

        // Child 1
        for (int i = 0; i < breakpoint; i++) {
            int newVal;
            newVal = parent2.get(i);
            Collections.swap(parent1, parent1.indexOf(newVal), i);
        }
        children.add(new Individual(parent1.stream().mapToInt(Integer::intValue).toArray(), tspProblem));

        parent1 = Arrays.stream(parents.get(0).getGenotype()).boxed().collect(Collectors.toList());
        // Child 2
        for (int i = breakpoint; i < genomeSize; i++) {
            int newVal = parent1.get(i);
            Collections.swap(parent2, parent2.indexOf(newVal), i);
        }
        children.add(new Individual(parent2.stream().mapToInt(Integer::intValue).toArray(), tspProblem));

        return children;
    }

    public Individual mutate(Individual children) {
        Random random = new Random();
        List<Integer> genome = Arrays.stream(children.getGenotype()).boxed().collect(Collectors.toList());
        Collections.swap(genome, random.nextInt(genomeSize), random.nextInt(genomeSize));
        return new Individual(genome.stream().mapToInt(Integer::intValue).toArray(), tspProblem);
    }

    private void printPop(List<Individual> pop, int iter) {
        System.out.println("\n ############################         POP " + iter + "        ############################\n");
        for (Individual i : pop) System.out.print(i.toString() + "   ");
        System.out.println("\n\n\n");
    }
}
