package krych.bartosz.ga;

public class Fitness {
    private City[] route;
    private Double distance;
    private Double fitnessVal;

    public Fitness(City[] route) {
        this.route = route;
        distance = 0D;
        fitnessVal = 0D;
    }

    public Double routeDistance()
    {
        if (distance == 0)
        {
            Double pathDist = 0D;
            City fromCity;
            City toCity;
            for(int i = 0 ; i < route.length; i++)
            {
                fromCity = route[i];
                if(i+1 < route.length)
                {
                    toCity = route[i+1];
                }
                else
                {
                    toCity = route[0];
                }
                pathDist += fromCity.distance(toCity);
            }
            distance = pathDist;
        }
        return distance;
    }
    public Double routeFitness()
    {
        if(fitnessVal == 0)
        {
            fitnessVal = 1/routeDistance();
        }
        return fitnessVal;
    }
}
