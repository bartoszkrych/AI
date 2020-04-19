package classes;

public class GameParams {
    public static int gameMode = Consts.HvsAi;
    public static int maxDepth1 = 4;
    public static int maxDepth2 = 4;


    public static final String getColorNameByNumber(int number) {
        switch (number) {
            case 1:
                return "Red";
            case 2:
                return "Yellow";
            default:
                return "Red";
        }
    }
}
