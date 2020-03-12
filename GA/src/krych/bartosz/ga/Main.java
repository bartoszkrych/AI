package krych.bartosz.ga;

import krych.bartosz.ga.individual.GreedyAlgorithm;
import krych.bartosz.ga.individual.RandAlgorithm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        int maxIter = 2000;
        int popSize = 250;
        double crossProb = 0.8;
        double mutProb = 0.1;
        //Type of selection:
        //  *   TOURNAMENT
        //  *   KRYCH
        //  *   ROULETTE
        //  *   WITHOUT
        SelectT selectT = SelectT.TOURNAMENT;

        String filename = "berlin52";

        TSPProblem tsp = new TSPProblem(getData(filename));
        GeneticAlgorithm ga = new GeneticAlgorithm(tsp, maxIter, popSize, crossProb, mutProb, selectT);
        System.out.print("GA:     ");
        ga.startAlgorithm();

        int randIter = 1000000;
        RandAlgorithm randA = new RandAlgorithm(tsp);
        System.out.println("Random: " + randA.getNewIndividual(randIter).toString());

        GreedyAlgorithm greedyA = new GreedyAlgorithm(tsp);
        System.out.println("Greedy: " + greedyA.bestFromCity().toString());
    }

    private static City[] getData(String filename) {
        File file = new File("TSP/" + filename + ".tsp");
        List<City> cities = new ArrayList<>();

        double[] readDouble;
        boolean startCities = false;
        String line;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if (line.equals("EOF")) break;
                if (startCities) {
                    Pattern pattern = Pattern.compile(" ");

                    readDouble = pattern.splitAsStream(line)
                            .mapToDouble(Double::parseDouble)
                            .toArray();

                    cities.add(new City(readDouble[1], readDouble[2]));
                }
                if (line.equals("NODE_COORD_SECTION")) startCities = true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        City[] arrCities = new City[cities.size()];
        cities.toArray(arrCities);
        return arrCities;
    }
}
