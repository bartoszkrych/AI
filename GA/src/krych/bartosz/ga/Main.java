package krych.bartosz.ga;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        TSPProblem tsp = new TSPProblem(getData());
        GeneticAlgorithm ga = new GeneticAlgorithm(tsp);

        ga.startAlgorithm();
    }

    private static City[] getData() {
        File file = new File("TSP/berlin52.tsp");
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
