package krych.bartosz.ga;

import krych.bartosz.ga.individual.AlgorithmIndividual;
import krych.bartosz.ga.individual.Individual;
import krych.bartosz.ga.individual.RandAlgorithm;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class GeneticAlgorithm {
    private Random random = new Random();
    private TSPProblem tspProblem;
    private SelectT selectT;
    private int genomeSize;
    private int tournamentSize;
    private int rouletteEpsilon;

    private int maxIter;
    private int popSize;
    private double crossProb;
    private double mutProb;

    Main main = new Main();
    int saveIter;


    public GeneticAlgorithm(TSPProblem tspProblem, int maxIter, int popSize, double crossProb, double mutProb, SelectT selectT, int selectionParam, int saveIter) {
        this.tspProblem = tspProblem;
        this.genomeSize = tspProblem.getCountCity();
        this.maxIter = maxIter;
        this.popSize = popSize;
        this.crossProb = crossProb;
        this.mutProb = mutProb;
        this.selectT = selectT;
        if (selectT == SelectT.TOURNAMENT)
            this.tournamentSize = selectionParam;
        if (selectT == SelectT.ROULETTE)
            this.rouletteEpsilon = selectionParam;
        this.saveIter = saveIter;
    }

    public Double startAlgorithm() {
        List<Individual> population = initPopulation();
        Individual bestIndividual = population.get(0);
        Individual popBestIndividual;
        List<String[]> dataLines = new ArrayList<>();
        dataLines.add(new String[]{"pop", "best", "worst", "avg", "THEBEST"});

        for (int i = 0; i < maxIter; i++) {
            List<Individual> selected = selection(population);
            population = createGeneration(selected);
            popBestIndividual = Collections.min(population, Comparator.comparing(Individual::getFitness));
            if (bestIndividual.getFitness() > popBestIndividual.getFitness()) {
                bestIndividual = popBestIndividual;
            }
            dataLines.add(new String[]{i + 1 + "",
                    popBestIndividual.getFitness() + "",
                    Collections.max(population, (p1, p2) -> p1.getFitness().compareTo(p2.getFitness())).getFitness() + "",
                    population.stream().mapToDouble(Individual::getFitness).average().orElse(0) + "",
                    bestIndividual.getFitness() + ""});
        }
        try {
            main.saveToFile(dataLines, saveIter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(bestIndividual.toString());

        return bestIndividual.getFitness();
    }

    private List<Individual> initPopulation() {
        List<Individual> population = new ArrayList<>();
        AlgorithmIndividual algIndi = new RandAlgorithm(tspProblem);
        for (int i = 0; i < popSize; i++) {
            population.add(algIndi.getNewIndividual(1000));
        }
        return population;
    }

    private List<Individual> selection(List<Individual> population) {
        List<Individual> selected = new ArrayList<>();
        for (int i = 0; i < popSize; i++) {
            if (selectT == SelectT.TOURNAMENT) {
                selected.add(tournamentSelection(population));
            } else if (selectT == SelectT.ROULETTE) {
                selected.add(rouletteSelection(population));
            } else if (selectT == SelectT.WITHOUT) {
                return population;
            } else if (selectT == SelectT.KRYCH) {
                selected.add(mySelection(population));
            }
        }
        return selected;
    }

    private Individual rouletteSelection(List<Individual> population) {
        List<Double> weights = generateWeights(population);
        double totalWeight = weights.stream().mapToDouble(Double::doubleValue).sum();
        double selectedVal = (Math.random() * totalWeight);
        int i = -1;
        do {
            selectedVal -= (weights.get(++i));
        } while (selectedVal > 0.0);

        return population.get(i);
    }

    private List<Double> generateWeights(List<Individual> population) {
        double worstFit = population.stream().max(Comparator.comparing(Individual::getFitness)).get().getFitness();
        List<Double> weights = new ArrayList<>();
        for (int i = 0; i < popSize; i++) {
            double weight = Math.pow(2, (worstFit - population.get(i).getFitness() + rouletteEpsilon) / 100);
            weights.add(weight);
        }
        return weights;
    }

    private Individual tournamentSelection(List<Individual> population) {
        List<Individual> selected = getRandomInd(population, tournamentSize);
        return Collections.min(Objects.requireNonNull(selected), (i1, i2) -> i1.getFitness().compareTo(i2.getFitness()));
    }

    private Individual mySelection(List<Individual> population) {
        List<Individual> selected = population.stream().sorted(Comparator.comparing(Individual::getFitness)).collect(Collectors.toList());
        selected = selected.subList(0, (int) (popSize * 0.5));
        return getRandomInd(selected, 1).get(0);
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

    private Individual mutate(Individual individual) {
        int[] genotype = individual.getGenotype();
        int randomIndex = random.nextInt(genomeSize);
        int randomDestination = random.nextInt(genomeSize);

        int temp = genotype[randomIndex];
        if (randomIndex < randomDestination) {
            System.arraycopy(genotype, randomIndex + 1, genotype, randomIndex, randomDestination - randomIndex);
        } else {
            System.arraycopy(genotype, randomDestination, genotype, randomDestination + 1, randomIndex - randomDestination);
        }
        genotype[randomDestination] = temp;
        return new Individual(genotype, tspProblem);
    }

    private List<Individual> getRandomInd(List<Individual> list, int n) {
        int length = list.size();

        if (length < n) return new ArrayList<>();

        for (int i = length - 1; i >= length - n; --i) {
            Collections.swap(list, i, random.nextInt(i + 1));
        }
        return list.subList(length - n, length);
    }

    public void printPop(List<Individual> pop, int iter) {
        System.out.println("\n ############################         POP " + iter + "        ############################\n");
        for (Individual i : pop) System.out.print(i.toString() + "   ");
        System.out.println("\n\n\n");
    }

}

