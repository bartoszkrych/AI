package krych.bartosz.ga;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {

        getData();
    }

    private static List<City> randomRoute(List<City> cities) {
        List<Integer> gen = new ArrayList<>();
        for (int i = 0; i < cities.size(); i++) {
            gen.add(i);
        }
        Collections.shuffle(gen);

        Integer[] genInt = new Integer[cities.size()];
        gen.toArray(genInt);

        List<City> result = new ArrayList<>();
        for (int i = 0; i < gen.size(); i++) {
            result.add(cities.get(genInt[i]));
        }
        return result;
    }

    private static void getData() {
        File file = new File("TSP/berlin11_modified.tsp");
        List<City> cities = new ArrayList<>();

        double[] readDouble;
        boolean startCities = false;
        String line;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                line= scanner.nextLine();
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
    }
}
