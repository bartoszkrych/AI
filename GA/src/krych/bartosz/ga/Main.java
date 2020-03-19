package krych.bartosz.ga;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

    private static int crossProb = 70;
    private static int mutProb;
    private static String filename;
    private static SelectT selectT = SelectT.TOURNAMENT;
    private static int selectionParam = 30;
    private static int[] testedProb = new int[]{5, 20, 50, 70, 95};
    private static String[] testedFile = new String[]{"kroA100", "kroA150", "kroA200"};

    public void saveToFile(List<String[]> dataLines, int saveIter) throws IOException {
        File csvOutputFile = new File("mut_" + mutProb + "/" + filename + "_tour_" + crossProb + "_" + mutProb + "_" + saveIter + ".csv");
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            dataLines.stream()
                    .map(s -> String.join(";", s))
                    .forEach(pw::println);
        }
    }

    public static void saveToFile(List<String[]> dataLines) throws IOException {
        File txtOutputFile = new File("mut_" + mutProb + "/" + filename + "_tour_" + crossProb + "_" + mutProb + ".txt");
        try (PrintWriter pw = new PrintWriter(txtOutputFile)) {
            dataLines.stream()
                    .map(s -> String.join(";", s))
                    .forEach(pw::println);
        }
    }

    public static void main(String[] args) throws IOException {
        int maxIter = 1000;
        int popSize = 300;

        for (int k = 0; k < testedFile.length; k++) {
            filename = testedFile[k];
            TSPProblem tsp = new TSPProblem(getData(filename));
            for (int j = 0; j < testedProb.length; j++) {
                mutProb = testedProb[j];
                List<String[]> dataLinesResult = new ArrayList<>();
                List<Double> resultFit = new ArrayList<>();
                dataLinesResult.add(new String[]{"iteration", "Fitness"});

                for (int i = 1; i <= 10; i++) {
                    GeneticAlgorithm ga = new GeneticAlgorithm(tsp, maxIter, popSize, (double) crossProb / (double) 100, (double) mutProb / (double) 100, selectT, selectionParam, i);
                    System.out.print("GA:     ");
                    Double result = ga.startAlgorithm();
                    resultFit.add(result);
                    dataLinesResult.add(new String[]{i + "", +result + ""});
                }
                dataLinesResult.add(new String[]{"----", "----"});
                dataLinesResult.add(new String[]{"best", Collections.min(resultFit) + " "});
                dataLinesResult.add(new String[]{"worst", Collections.max(resultFit) + " "});
                dataLinesResult.add(new String[]{"avg", resultFit.stream().mapToDouble(Double::doubleValue).average().getAsDouble() + " "});
                saveToFile(dataLinesResult);
            }
        }


        int randIter = 1000000;
//        RandAlgorithm randA = new RandAlgorithm(tsp);
//        System.out.println("Random: " + randA.getNewIndividual(randIter).toString());

//        GreedyAlgorithm greedyA = new GreedyAlgorithm(tsp);
//        System.out.println("Greedy: " + greedyA.bestFromCity().toString());
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
