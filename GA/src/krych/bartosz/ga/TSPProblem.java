package krych.bartosz.ga;

import krych.bartosz.ga.individual.Individual;

public class TSPProblem {

    private City[] cities;
    private int countCity;

    public TSPProblem(City[] cities) {
        this.cities = cities;
        countCity = cities.length;
    }


    public Double getDistance(Individual individual) {
        Double pathDist = 0D;
        City fromCity;
        City toCity;
        for (int i = 0; i < countCity; i++) {
            fromCity = cities[individual.getCityByIdx(i)];
            if (i + 1 < countCity) {
                toCity = cities[individual.getCityByIdx(i + 1)];
            } else {
                toCity = cities[individual.getCityByIdx(0)];
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
