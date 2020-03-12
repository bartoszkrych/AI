package krych.bartosz.ga;

import krych.bartosz.ga.individual.AlgorithmIndividual;
import krych.bartosz.ga.individual.Individual;
import krych.bartosz.ga.individual.RandAlgorithm;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GeneticAlgorithm {
    private TSPProblem tspProblem;
    private int genomeSize;
    private Individual bestIndividual;

    private int maxIter;
    private int popSize;
    private double crossProb;
    private double mutProb;

    private int tournamentSize;

    public GeneticAlgorithm(TSPProblem tspProblem, int maxIter, int popSize, double crossProb, double mutProb) {
        this.tspProblem = tspProblem;
        this.genomeSize = tspProblem.getCountCity();
        this.maxIter = maxIter;
        this.popSize = popSize;
        this.crossProb = crossProb;
        this.mutProb = mutProb;
        this.tournamentSize = (int) (popSize * 0.33);
    }

    private List<Individual> initPopulation() {
        List<Individual> population = new ArrayList<>();
        AlgorithmIndividual algIndi = new RandAlgorithm(tspProblem);
        for (int i = 0; i < popSize; i++) {
            population.add(algIndi.getNewIndividual());
        }
        return population;
    }

    public void startAlgorithm() {
        List<Individual> population = initPopulation();
        bestIndividual = population.get(0);
        Individual popBestIndividual = bestIndividual;
        List<String[]> dataLines = new ArrayList<>();
        dataLines.add(new String[]{"pop", "best", "worst", "avg"});

        for (int i = 0; i < maxIter; i++) {
            List<Individual> selected = selection(population);
            population = createGeneration(selected);
            popBestIndividual = Collections.min(population, (p1, p2) -> p1.getFitness().compareTo(p2.getFitness()));
            if (bestIndividual.getFitness() > popBestIndividual.getFitness()) {
                bestIndividual = popBestIndividual;
            }
            dataLines.add(new String[]{i + "", popBestIndividual.getFitness() + "", Collections.max(population, (p1, p2) -> p1.getFitness().compareTo(p2.getFitness())).getFitness() + "", population.stream().mapToDouble(Individual::getFitness).average().orElse(0) + ""});
        }
        try {
            saveToFile(dataLines);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(bestIndividual.toString());
    }

    private List<Individual> selection(List<Individual> population) {
        List<Individual> selected = new ArrayList<>();
        for (int i = 0; i < popSize; i++) {
            selected.add(tournamentSelection(population));
        }
        return selected;
    }

    private Individual rouletteSelection(List<Individual> population) {
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
        return population.get(random.nextInt(popSize));
    }

    private static List<Individual> getRandomInd(List<Individual> list, int n) {
        Random r = new Random();
        int length = list.size();

        if (length < n) return null;

        for (int i = length - 1; i >= length - n; --i) {
            Collections.swap(list, i, r.nextInt(i + 1));
        }
        return list.subList(length - n, length);
    }

    private Individual tournamentSelection(List<Individual> population) {
        List<Individual> selected = getRandomInd(population, tournamentSize);
        return Collections.min(selected, (i1, i2) -> i1.getFitness().compareTo(i2.getFitness()));
    }


    private List<Individual> createGeneration(List<Individual> population) {
        List<Individual> generation = new ArrayList<>();
        int curGeneration = 0;
        while (curGeneration < popSize) {
            List<Individual> parents = getRandomInd(population, 2);
            if (Math.random() < crossProb) {
                List<Individual> children = crossoverPMX(parents);
                for (int i = 0; i < 2; i++) {
                    if (Math.random() < mutProb) {
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

    private Individual mutate(Individual children) {
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

    public void saveToFile(List<String[]> dataLines) throws IOException {
        File csvOutputFile = new File("result.txt");
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            dataLines.stream()
                    .map(this::convertToCSV)
                    .forEach(pw::println);
        }
    }

    public String convertToCSV(String[] data) {
        return Stream.of(data)
                .collect(Collectors.joining(","));
    }

}
