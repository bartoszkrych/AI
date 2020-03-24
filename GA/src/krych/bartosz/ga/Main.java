package krych.bartosz.ga;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

    private static int maxIter = 6000;
    private static int popSize = 300;
    private static int crossProb = 95;
    private static int mutProb = 75;
    private static String filename;
    private static SelectT selectT = SelectT.TOURNAMENT;
    private static int selectionParam = 100;
    private static String[] testedFile = new String[]{"berlin52", "kroA100", "kroA150", "kroA200", "fl417"};

    // na potrzeby badan zmienilem kod na nieoptymalny ale potrzebny do generowania raportów
    public void saveToFile(List<String[]> dataLines, int saveIter) throws IOException {
        File csvOutputFile = new File("comparision/" + filename + "_2_" + saveIter + ".csv");
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            dataLines.stream()
                    .map(s -> String.join(";", s))
                    .forEach(pw::println);
        }
    }

    public static void saveToFile(List<String[]> dataLines) throws IOException {
        File txtOutputFile = new File("comparision/" + filename + "_q.txt");
        try (PrintWriter pw = new PrintWriter(txtOutputFile)) {
            dataLines.stream()
                    .map(s -> String.join(";", s))
                    .forEach(pw::println);
        }
    }

    public static void main(String[] args) throws IOException {

        // Badanie GA

//        for (int k = 0; k < testedFile.length; k++) {
//            filename = testedFile[k];
//            TSPProblem tsp = new TSPProblem(getData(filename));
////            for (int j = 0; j < testedProb.length; j++) {
////                maxIter = testedProb[j];
//            List<String[]> dataLinesResult = new ArrayList<>();
//            List<Double> resultFit = new ArrayList<>();
//            dataLinesResult.add(new String[]{"iteration", "Fitness"});
//
//            for (int i = 1; i <= 10; i++) {
//                GeneticAlgorithm ga = new GeneticAlgorithm(tsp, maxIter, popSize, (double) crossProb / (double) 100, (double) mutProb / (double) 100, selectT, selectionParam, i);
//                System.out.print("GA:     ");
//                Double result = ga.startAlgorithm();
//                resultFit.add(result);
//                dataLinesResult.add(new String[]{i + "", +result + ""});
//            }
//            dataLinesResult.add(new String[]{"----", "----"});
//            dataLinesResult.add(new String[]{"best", Collections.min(resultFit) + " "});
//            dataLinesResult.add(new String[]{"worst", Collections.max(resultFit) + " "});
//            dataLinesResult.add(new String[]{"avg", resultFit.stream().mapToDouble(Double::doubleValue).average().getAsDouble() + " "});
//            saveToFile(dataLinesResult);
//        }

        // Badanie algorytmu losowego

//        int randIter = 100000;
//        for (int k = 0; k < testedFile.length; k++) {
//            filename = testedFile[k];
//            TSPProblem tsp = new TSPProblem(getData(filename));
//            List<String[]> dataLinesResult = new ArrayList<>();
//            List<Double> resultFit = new ArrayList<>();
//            RandAlgorithm randA = new RandAlgorithm(tsp);
//
//            for (int i = 1; i <= 10; i++) {
//                Double result = randA.getNewIndividual(randIter).getFitness();
//                resultFit.add(result);
//            }
//            dataLinesResult.add(new String[]{"----", "----"});
//            dataLinesResult.add(new String[]{"best", Collections.min(resultFit) + " "});
//            dataLinesResult.add(new String[]{"worst", Collections.max(resultFit) + " "});
//            dataLinesResult.add(new String[]{"avg", resultFit.stream().mapToDouble(Double::doubleValue).average().getAsDouble() + " "});
//            saveToFile(dataLinesResult);
//        }

//      Badanie Algorytmu genetycznego


//        GreedyAlgorithm greedyA = new GreedyAlgorithm(tsp);
//        System.out.println("Greedy: " + greedyA.bestFromCity().toString());

//        for (int k = 0; k < testedFile.length; k++) {
//            filename = testedFile[k];
//            TSPProblem tsp = new TSPProblem(getData(filename));
//            List<String[]> dataLinesResult = new ArrayList<>();
//            List<Double> resultFit = new ArrayList<>();
//
//            for (int i = 0; i < tsp.getCountCity(); i++) {
//                GreedyAlgorithm randA = new GreedyAlgorithm(tsp);
//                Double result = randA.getNewIndividual(i).getFitness();
//                resultFit.add(result);
//            }
//            dataLinesResult.add(new String[]{"----", "----"});
//            dataLinesResult.add(new String[]{"best", Collections.min(resultFit) + " "});
//            dataLinesResult.add(new String[]{"worst", Collections.max(resultFit) + " "});
//            dataLinesResult.add(new String[]{"avg", resultFit.stream().mapToDouble(Double::doubleValue).average().getAsDouble() + " "});
//            saveToFile(dataLinesResult);
//        }
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
