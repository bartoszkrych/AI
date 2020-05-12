package krych.bartosz;

import krych.bartosz.classes.GameConsole;
import krych.bartosz.classes.algorithms.AlphaBeta;
import krych.bartosz.classes.algorithms.GameAlgorithm;
import krych.bartosz.classes.algorithms.MinMax;
import krych.bartosz.classes.algorithms.NegaScout;
import krych.bartosz.classes.functions.EstimateFunction;
import krych.bartosz.classes.functions.JustWinnerEstFun;
import krych.bartosz.classes.functions.ThreeInLineEstFun;
import krych.bartosz.classes.functions.TwoThreeInLineEstFun;

import java.util.ArrayList;
import java.util.List;

public class Research {

    private GameConsole game;
    private List<String[]> dataLines;
    private List<String[]> sumLines;
    private DataSaver dataSaver;

    public Research(GameConsole gameConsole) {
        this.game = gameConsole;
    }

    public void comparingMMvsAB(int est1, int est2) {
        EstimateFunction ef1 = getEstimateFunction(est1);
        EstimateFunction ef2 = getEstimateFunction(est2);

        iterationTwoAlg(Consts.AlphaBeta, Consts.MinMax, 10, 7, ef1, ef2);
        iterationTwoAlg(Consts.MinMax, Consts.AlphaBeta, 7, 10, ef1, ef2);

        if (est1 != est2) {
            iterationTwoAlg(Consts.AlphaBeta, Consts.MinMax, 10, 7, ef2, ef1);
            iterationTwoAlg(Consts.MinMax, Consts.AlphaBeta, 7, 10, ef2, ef1);
        }
    }

    public void comparingOne(int alg, int est1, int est2) {
        EstimateFunction ef1 = getEstimateFunction(est1);
        EstimateFunction ef2 = getEstimateFunction(est2);
        iterationOneAlg(alg, ef1, ef2);

        if (est1 != est2)
            iterationOneAlg(alg, ef2, ef1);
    }

    public void fightMMAB(int est1, int est2) {
        EstimateFunction ef1 = getEstimateFunction(est1);
        EstimateFunction ef2 = getEstimateFunction(est2);

        fightTwoAlg(Consts.AlphaBeta, Consts.MinMax, ef1, ef2);
        if (est1 != est2)
            fightTwoAlg(Consts.MinMax, Consts.AlphaBeta, ef1, ef2);
    }


    private void iterationTwoAlg(int gameAlg1, int gameAlg2, int i1, int i2, EstimateFunction est1, EstimateFunction est2) {
        GameAlgorithm p1_ai, p2_ai;
        String p1, p2;
        if (gameAlg1 == Consts.MinMax) p1 = "MM";
        else if (gameAlg1 == Consts.AlphaBeta) p1 = "AB";
        else p1 = "NS";

        if (gameAlg2 == Consts.MinMax) p2 = "MM";
        else if (gameAlg2 == Consts.AlphaBeta) p2 = "AB";
        else p2 = "NS";


        for (int j = 1; j <= i1; j++) {

            if (gameAlg1 == Consts.MinMax) p1_ai = new MinMax(j, Consts.P_1, est1);
            else if (gameAlg1 == Consts.AlphaBeta) p1_ai = new AlphaBeta(j, Consts.P_1, est1);
            else {
                p1_ai = new NegaScout(j, Consts.P_1, est1);
            }
            sumLines = new ArrayList<>();
            sumLines.add(new String[]{p2, "moves winner", "time game", p1 + " time", p2 + " time", p1 + " win", p2 + " win", "draws"});
            for (int k = 1; k <= i2; k++) {

                if (gameAlg2 == Consts.MinMax) p2_ai = new MinMax(k, Consts.P_2, est2);
                else if (gameAlg2 == Consts.AlphaBeta) p2_ai = new AlphaBeta(k, Consts.P_2, est2);
                else {
                    p2_ai = new NegaScout(k, Consts.P_2, est2);
                }

                dataSaver = new DataSaver(p1_ai.getNameAlg() + "_" + p2_ai.getNameAlg() + "/" + est1.getNameEst() + "_" + est2.getNameEst(),
                        j + "_" + k);

                System.out.println("            " + p1_ai.getNameAlg() + " (" + j + ")         " + p2_ai.getNameAlg() + " (" + k + ")         ");
                dataLines = new ArrayList<>();
                dataLines.add(new String[]{"Winner", "Moves", "Time", "P1 time", "P2 time"});
                List<Integer> winners = new ArrayList<>();
                List<Integer> moves = new ArrayList<>();
                List<Integer> times = new ArrayList<>();
                List<Integer> timeMoveP1 = new ArrayList<>();
                List<Integer> timeMoveP2 = new ArrayList<>();
                for (int i = 1; i <= 10; i++) {
                    Integer[] temp = game.startAiVsAi(p1_ai, p2_ai, true);
                    winners.add(temp[0]);
                    moves.add(temp[1]);
                    times.add(temp[2]);
                    timeMoveP1.add(temp[3]);
                    timeMoveP2.add(temp[4]);
                    dataLines.add(new String[]{temp[0] + " ", temp[1] + " ", temp[2] + " ", temp[3] + " ", temp[4] + " "});
                }
                double dMove, dTime, dTimeP1, dTimeP2;
                int p1Win, p2Win, draws;
                dMove = moves.stream().mapToDouble(Integer::doubleValue).average().orElse(0);
                dTime = times.stream().mapToDouble(Integer::doubleValue).average().orElse(0);
                dTimeP1 = timeMoveP1.stream().mapToDouble(Integer::doubleValue).average().orElse(0);
                dTimeP2 = timeMoveP2.stream().mapToDouble(Integer::doubleValue).average().orElse(0);
                p1Win = (int) winners.stream().filter(w -> w.equals(Consts.P_1)).count();
                p2Win = (int) winners.stream().filter(w -> w.equals(Consts.P_2)).count();
                draws = (int) winners.stream().filter(w -> w.equals(Consts.EMPTY)).count();
                dataLines.add(new String[]{"-----------", "------------"});
                dataLines.add(new String[]{"moves", dMove + ""});
                dataLines.add(new String[]{"times", dTime + ""});
                dataLines.add(new String[]{"timeP1Move", dTimeP1 + ""});
                dataLines.add(new String[]{"timeP2Move", dTimeP2 + ""});
                dataLines.add(new String[]{p1_ai.getNameAlg(), p1Win + ""});
                dataLines.add(new String[]{p2_ai.getNameAlg(), p2Win + ""});
                dataLines.add(new String[]{"draws", draws + ""});
                dataSaver.saveToFile(dataLines, "csv");
                sumLines.add(new String[]{k + "", dMove + "", dTime + "", dTimeP1 + "", dTimeP2 + "", p1Win + "", p2Win + "", draws + ""});

                if (k == i2) {
                    dataSaver = new DataSaver(p1_ai.getNameAlg() + "_" + p2_ai.getNameAlg() + "/" + est1.getNameEst() + "_" + est2.getNameEst(),
                            j + "_sum");
                    dataSaver.saveToFile(sumLines, "csv");
                }
            }
        }
    }

    private void iterationOneAlg(int gameAlg1, EstimateFunction est1, EstimateFunction est2) {
        GameAlgorithm p1_ai, p2_ai;
        int k;
        if (gameAlg1 == Consts.MinMax) k = 7;
        else if (gameAlg1 == Consts.AlphaBeta) k = 10;
        else k = 7;

        sumLines = new ArrayList<>();
        sumLines.add(new String[]{"depth", "moves", "time", "P1 times", "P2 times", "p1 win", "p2 win", "draws"});
        for (int j = 1; j <= k; j++) {

            if (gameAlg1 == Consts.MinMax) {
                p1_ai = new MinMax(j, Consts.P_1, est1);
                p2_ai = new MinMax(j, Consts.P_2, est2);
            } else if (gameAlg1 == Consts.AlphaBeta) {
                p1_ai = new AlphaBeta(j, Consts.P_1, est1);
                p2_ai = new AlphaBeta(j, Consts.P_2, est2);
            } else {
                p1_ai = new NegaScout(j, Consts.P_1, est1);
                p2_ai = new NegaScout(j, Consts.P_2, est2);
            }
            System.out.println("            " + p1_ai.getNameAlg() + " (" + j + ")         " + p2_ai.getNameAlg() + " (" + j + ")         ");

            dataSaver = new DataSaver(p1_ai.getNameAlg() + "_" + p2_ai.getNameAlg() + "_+10/" + est1.getNameEst() + "_" + est2.getNameEst(),
                    j + "_" + j);

            dataLines = new ArrayList<>();
            dataLines.add(new String[]{"Winner", "Moves", "Time", "P1 time", "P2 time"});
            List<Integer> winners = new ArrayList<>();
            List<Integer> moves = new ArrayList<>();
            List<Integer> times = new ArrayList<>();
            List<Integer> timeMoveP1 = new ArrayList<>();
            List<Integer> timeMoveP2 = new ArrayList<>();
            for (int i = 1; i <= 20; i++) {
                Integer[] temp = game.startAiVsAi(p1_ai, p2_ai, true);
                winners.add(temp[0]);
                moves.add(temp[1]);
                times.add(temp[2]);
                timeMoveP1.add(temp[3]);
                timeMoveP2.add(temp[4]);
                dataLines.add(new String[]{temp[0] + " ", temp[1] + " ", temp[2] + " ", temp[3] + " ", temp[4] + " "});
            }
            double dMove, dTime, dTimeP1, dTimeP2;
            int p1Win, p2Win, draws;
            dMove = moves.stream().mapToDouble(Integer::doubleValue).average().orElse(0);
            dTime = times.stream().mapToDouble(Integer::doubleValue).average().orElse(0);
            dTimeP1 = timeMoveP1.stream().mapToDouble(Integer::doubleValue).average().orElse(0);
            dTimeP2 = timeMoveP2.stream().mapToDouble(Integer::doubleValue).average().orElse(0);
            p1Win = (int) winners.stream().filter(w -> w.equals(Consts.P_1)).count();
            p2Win = (int) winners.stream().filter(w -> w.equals(Consts.P_2)).count();
            draws = (int) winners.stream().filter(w -> w.equals(Consts.EMPTY)).count();
            dataLines.add(new String[]{"-----------", "------------"});
            dataLines.add(new String[]{"moves", dMove + ""});
            dataLines.add(new String[]{"times", dTime + ""});
            dataLines.add(new String[]{"timeP1Move", dTimeP1 + ""});
            dataLines.add(new String[]{"timeP2Move", dTimeP2 + ""});
            dataLines.add(new String[]{p1_ai.getNameAlg(), p1Win + ""});
            dataLines.add(new String[]{p2_ai.getNameAlg(), p2Win + ""});
            dataLines.add(new String[]{"draws", draws + ""});
            dataSaver.saveToFile(dataLines, "csv");

            sumLines.add(new String[]{j + "", dMove + "", dTime + "", dTimeP1 + "", dTimeP2 + "", p1Win + "", p2Win + "", draws + ""});
            if (j == k) {
                dataSaver = new DataSaver(p1_ai.getNameAlg() + "_" + p2_ai.getNameAlg() + "+10/" + est1.getNameEst() + "_" + est2.getNameEst(),
                        "sum");
                dataSaver.saveToFile(sumLines, "csv");
            }
        }

    }


    private void fightTwoAlg(int gameAlg1, int gameAlg2, EstimateFunction est1, EstimateFunction est2) {
        GameAlgorithm p1_ai, p2_ai;
        int k = 8;
//        if (gameAlg1 == Consts.MinMax) k = 7;
//        else if (gameAlg1 == Consts.AlphaBeta) k = 10;
//        else k = 7;

        sumLines = new ArrayList<>();
        sumLines.add(new String[]{"depth", "moves", "time", "P1 times", "P2 times", "p1 win", "p2 win", "draws"});
        for (int j = 1; j <= k; j++) {

            if (gameAlg2 == Consts.MinMax) p2_ai = new MinMax(j, Consts.P_2, est2);
            else if (gameAlg2 == Consts.AlphaBeta) p2_ai = new AlphaBeta(j, Consts.P_2, est2);
            else {
                p2_ai = new NegaScout(j, Consts.P_2, est2);
            }

            if (gameAlg1 == Consts.MinMax) {
                p1_ai = new MinMax(j, Consts.P_1, est1);
            } else if (gameAlg1 == Consts.AlphaBeta) {
                p1_ai = new AlphaBeta(j, Consts.P_1, est1);
            } else {
                p1_ai = new NegaScout(j, Consts.P_1, est1);
            }
            System.out.println("            " + p1_ai.getNameAlg() + " (" + j + ")         " + p2_ai.getNameAlg() + " (" + j + ")         ");

            dataSaver = new DataSaver(p1_ai.getNameAlg() + "_" + p2_ai.getNameAlg() + "_+10/" + est1.getNameEst() + "_" + est2.getNameEst(),
                    j + "_" + j);

            dataLines = new ArrayList<>();
            dataLines.add(new String[]{"Winner", "Moves", "Time", "P1 time", "P2 time"});
            List<Integer> winners = new ArrayList<>();
            List<Integer> moves = new ArrayList<>();
            List<Integer> times = new ArrayList<>();
            List<Integer> timeMoveP1 = new ArrayList<>();
            List<Integer> timeMoveP2 = new ArrayList<>();
            for (int i = 1; i <= 20; i++) {
                Integer[] temp = game.startAiVsAi(p1_ai, p2_ai, true);
                winners.add(temp[0]);
                moves.add(temp[1]);
                times.add(temp[2]);
                timeMoveP1.add(temp[3]);
                timeMoveP2.add(temp[4]);
                dataLines.add(new String[]{temp[0] + " ", temp[1] + " ", temp[2] + " ", temp[3] + " ", temp[4] + " "});
            }
            double dMove, dTime, dTimeP1, dTimeP2;
            int p1Win, p2Win, draws;
            dMove = moves.stream().mapToDouble(Integer::doubleValue).average().orElse(0);
            dTime = times.stream().mapToDouble(Integer::doubleValue).average().orElse(0);
            dTimeP1 = timeMoveP1.stream().mapToDouble(Integer::doubleValue).average().orElse(0);
            dTimeP2 = timeMoveP2.stream().mapToDouble(Integer::doubleValue).average().orElse(0);
            p1Win = (int) winners.stream().filter(w -> w.equals(Consts.P_1)).count();
            p2Win = (int) winners.stream().filter(w -> w.equals(Consts.P_2)).count();
            draws = (int) winners.stream().filter(w -> w.equals(Consts.EMPTY)).count();
            dataLines.add(new String[]{"-----------", "------------"});
            dataLines.add(new String[]{"moves", dMove + ""});
            dataLines.add(new String[]{"times", dTime + ""});
            dataLines.add(new String[]{"timeP1Move", dTimeP1 + ""});
            dataLines.add(new String[]{"timeP2Move", dTimeP2 + ""});
            dataLines.add(new String[]{p1_ai.getNameAlg(), p1Win + ""});
            dataLines.add(new String[]{p2_ai.getNameAlg(), p2Win + ""});
            dataLines.add(new String[]{"draws", draws + ""});
            dataSaver.saveToFile(dataLines, "csv");

            sumLines.add(new String[]{j + "", dMove + "", dTime + "", dTimeP1 + "", dTimeP2 + "", p1Win + "", p2Win + "", draws + ""});
            if (j == k) {
                dataSaver = new DataSaver(p1_ai.getNameAlg() + "_" + p2_ai.getNameAlg() + "_+10/" + est1.getNameEst() + "_" + est2.getNameEst(),
                        "sum");
                dataSaver.saveToFile(sumLines, "csv");
            }
        }

    }


    private EstimateFunction getEstimateFunction(int est) {
        if (est == Consts.JustWinner) return new JustWinnerEstFun();
        else if (est == Consts.ThreeInLine) return new ThreeInLineEstFun();
        return new TwoThreeInLineEstFun();
    }
}
