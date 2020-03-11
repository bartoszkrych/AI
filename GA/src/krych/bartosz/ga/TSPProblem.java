package krych.bartosz.ga;

public class TSPProblem {

    private City[] cities;
    private int countCity;

    public TSPProblem(City[] cities) {
        this.cities = cities;
        countCity = cities.length;
    }


    public Double getDistance(int[] genotype) {
        Double pathDist = 0D;
        City fromCity;
        City toCity;
        for (int i = 0; i < countCity; i++) {
            fromCity = cities[genotype[i]];
            if (i + 1 < countCity) {
                toCity = cities[genotype[i + 1]];
            } else {
                toCity = cities[genotype[0]];
            }
            pathDist += fromCity.distance(toCity);
        }
        return pathDist;
    }

    public City[] getCities() {
        return cities;
    }

    public int getCountCity() {
        return countCity;
    }

    public void printCities() {
        for (int i = 0; i < countCity; i++) {
            System.out.println(cities[i].toString());
        }
    }
}
