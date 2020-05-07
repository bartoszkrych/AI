package krych.bartosz;

import krych.bartosz.classes.Consts;
import krych.bartosz.classes.GameConsole;
import krych.bartosz.interfaces.EstimateFunction;
import krych.bartosz.interfaces.GameAlgorithm;

import java.util.List;

public class MainConsole {

    public static void main(String[] args) {
        int p1_depth, p2_depth;
        EstimateFunction p1_estimate, p2_estimate;
        GameAlgorithm p1_ai, p2_ai;
        GameConsole game = new GameConsole();
        List<String[]> dataLines;
        DataSaver dataSaver;

        Research research = new Research(new GameConsole());

        research.comparingOne(Consts.AlphaBeta, Consts.ThreeInLine, Consts.ThreeInLine);
        research.comparingOne(Consts.AlphaBeta, Consts.TwoThreeInLine, Consts.ThreeInLine);
        research.comparingOne(Consts.AlphaBeta, Consts.TwoThreeInLine, Consts.TwoThreeInLine);

        research.comparingOne(Consts.MinMax, Consts.ThreeInLine, Consts.ThreeInLine);
        research.comparingOne(Consts.MinMax, Consts.TwoThreeInLine, Consts.ThreeInLine);
        research.comparingOne(Consts.MinMax, Consts.TwoThreeInLine, Consts.TwoThreeInLine);

        research.comparingMMvsAB(Consts.ThreeInLine, Consts.ThreeInLine);
        research.comparingMMvsAB(Consts.TwoThreeInLine, Consts.ThreeInLine);
        research.comparingMMvsAB(Consts.TwoThreeInLine, Consts.TwoThreeInLine);


//        /**     P1      **/
////        p1_depth = 1;
//        p1_estimate = new ThreeInLineEstFun();
////        p1_ai = new AlphaBeta(p1_depth, Consts.P_1, p1_estimate);
//
//        /**     P2      **/
////        p2_depth = 1;
//        p2_estimate = new ThreeInLineEstFun();
////        p2_ai = new MinMax(p2_depth, Consts.P_1, p2_estimate);
//
//
//        for (int j = 1; j <= 7; j++) {
//            p1_depth = j;
//            p1_ai = new MinMax(p1_depth, Consts.P_1, p1_estimate);
////            for (int k = 1; k <= 10; k++) {
//                p2_depth = j;
//                p2_ai = new MinMax(p2_depth, Consts.P_2, p2_estimate);
//
//                dataSaver = new DataSaver(p1_ai.getNameAlg() + "_" + p2_ai.getNameAlg() + "_AVG/" + p1_estimate.getNameEst() + "_" + p2_estimate.getNameEst(),
//                        p1_depth + "_" + p2_depth);
//
//                dataLines = new ArrayList<>();
//                dataLines.add(new String[]{"WINNER", "MOVES", "TIME"});
//                List<Integer> winners = new ArrayList<>();
//                List<Integer> moves = new ArrayList<>();
//                List<Integer> times = new ArrayList<>();
//                List<Double> timePerMove = new ArrayList<>();
//                for (int i = 1; i <= 10; i++) {
//                    Integer[] temp = game.startAiVsAi(p1_ai, p2_ai, true);
//                    winners.add(temp[0]);
//                    moves.add(temp[1]);
//                    times.add(temp[2]);
//                    timePerMove.add(temp[2].doubleValue()/temp[1].doubleValue());
//                    dataLines.add(new String[]{temp[0] + " ", temp[1] + " ", temp[2] +" ",timePerMove.get(i-1)+""});
//                }
//                dataLines.add(new String[]{"-", "-", "-"});
//                dataLines.add(new String[]{"moves", moves.stream().mapToDouble(Integer::doubleValue).average().orElse(0) + ""});
//                dataLines.add(new String[]{"times", times.stream().mapToDouble(Integer::doubleValue).average().orElse(0) + ""});
//                dataLines.add(new String[]{"timeMove", timePerMove.stream().mapToDouble(Double::doubleValue).average().orElse(0) + ""});
//                dataLines.add(new String[]{p1_ai.getNameAlg(), winners.stream().filter(w -> w.equals(Consts.P_1)).count() + ""});
//                dataLines.add(new String[]{p2_ai.getNameAlg(), winners.stream().filter(w -> w.equals(Consts.P_2)).count() + ""});
//                dataLines.add(new String[]{"draws", winners.stream().filter(w -> w.equals(Consts.EMPTY)).count() + ""});
//                dataSaver.saveToFile(dataLines);
////            }
//        }
//
//
//
//
//        /**     P1      **/
////        p1_depth = 1;
//        p1_estimate = new TwoThreeInLineEstFun();
////        p1_ai = new AlphaBeta(p1_depth, Consts.P_1, p1_estimate);
//
//        /**     P2      **/
////        p2_depth = 1;
//        p2_estimate = new TwoThreeInLineEstFun();
////        p2_ai = new MinMax(p2_depth, Consts.P_1, p2_estimate);
//
//
//        for (int j = 1; j <= 7; j++) {
//            p1_depth = j;
//            p1_ai = new MinMax(p1_depth, Consts.P_1, p1_estimate);
////            for (int k = 1; k <= 10; k++) {
//            p2_depth = j;
//            p2_ai = new MinMax(p2_depth, Consts.P_2, p2_estimate);
//
//            dataSaver = new DataSaver(p1_ai.getNameAlg() + "_" + p2_ai.getNameAlg() + "_AVG/" + p1_estimate.getNameEst() + "_" + p2_estimate.getNameEst(),
//                    p1_depth + "_" + p2_depth);
//
//            dataLines = new ArrayList<>();
//            dataLines.add(new String[]{"WINNER", "MOVES", "TIME"});
//            List<Integer> winners = new ArrayList<>();
//            List<Integer> moves = new ArrayList<>();
//            List<Integer> times = new ArrayList<>();
//            List<Double> timePerMove = new ArrayList<>();
//            for (int i = 1; i <= 10; i++) {
//                Integer[] temp = game.startAiVsAi(p1_ai, p2_ai, true);
//                winners.add(temp[0]);
//                moves.add(temp[1]);
//                times.add(temp[2]);
//                timePerMove.add(temp[2].doubleValue()/temp[1].doubleValue());
//                dataLines.add(new String[]{temp[0] + " ", temp[1] + " ", temp[2] +" ",timePerMove.get(i-1)+""});
//            }
//            dataLines.add(new String[]{"-", "-", "-"});
//            dataLines.add(new String[]{"moves", moves.stream().mapToDouble(Integer::doubleValue).average().orElse(0) + ""});
//            dataLines.add(new String[]{"times", times.stream().mapToDouble(Integer::doubleValue).average().orElse(0) + ""});
//            dataLines.add(new String[]{"timeMove", timePerMove.stream().mapToDouble(Double::doubleValue).average().orElse(0) + ""});
//            dataLines.add(new String[]{p1_ai.getNameAlg(), winners.stream().filter(w -> w.equals(Consts.P_1)).count() + ""});
//            dataLines.add(new String[]{p2_ai.getNameAlg(), winners.stream().filter(w -> w.equals(Consts.P_2)).count() + ""});
//            dataLines.add(new String[]{"draws", winners.stream().filter(w -> w.equals(Consts.EMPTY)).count() + ""});
//            dataSaver.saveToFile(dataLines);
////            }
//        }
//
//        /**     P1      **/
////        p1_depth = 1;
//        p1_estimate = new TwoThreeInLineEstFun();
////        p1_ai = new AlphaBeta(p1_depth, Consts.P_1, p1_estimate);
//
//        /**     P2      **/
////        p2_depth = 1;
//        p2_estimate = new ThreeInLineEstFun();
////        p2_ai = new MinMax(p2_depth, Consts.P_1, p2_estimate);
//
//
//        for (int j = 1; j <= 7; j++) {
//            p1_depth = j;
//            p1_ai = new MinMax(p1_depth, Consts.P_1, p1_estimate);
////            for (int k = 1; k <= 10; k++) {
//            p2_depth = j;
//            p2_ai = new MinMax(p2_depth, Consts.P_2, p2_estimate);
//
//            dataSaver = new DataSaver(p1_ai.getNameAlg() + "_" + p2_ai.getNameAlg() + "_AVG/" + p1_estimate.getNameEst() + "_" + p2_estimate.getNameEst(),
//                    p1_depth + "_" + p2_depth);
//
//            dataLines = new ArrayList<>();
//            dataLines.add(new String[]{"WINNER", "MOVES", "TIME"});
//            List<Integer> winners = new ArrayList<>();
//            List<Integer> moves = new ArrayList<>();
//            List<Integer> times = new ArrayList<>();
//            List<Double> timePerMove = new ArrayList<>();
//            for (int i = 1; i <= 10; i++) {
//                Integer[] temp = game.startAiVsAi(p1_ai, p2_ai, true);
//                winners.add(temp[0]);
//                moves.add(temp[1]);
//                times.add(temp[2]);
//                timePerMove.add(temp[2].doubleValue()/temp[1].doubleValue());
//                dataLines.add(new String[]{temp[0] + " ", temp[1] + " ", temp[2] +" ",timePerMove.get(i-1)+""});
//            }
//            dataLines.add(new String[]{"-", "-", "-"});
//            dataLines.add(new String[]{"moves", moves.stream().mapToDouble(Integer::doubleValue).average().orElse(0) + ""});
//            dataLines.add(new String[]{"times", times.stream().mapToDouble(Integer::doubleValue).average().orElse(0) + ""});
//            dataLines.add(new String[]{"timeMove", timePerMove.stream().mapToDouble(Double::doubleValue).average().orElse(0) + ""});
//            dataLines.add(new String[]{p1_ai.getNameAlg(), winners.stream().filter(w -> w.equals(Consts.P_1)).count() + ""});
//            dataLines.add(new String[]{p2_ai.getNameAlg(), winners.stream().filter(w -> w.equals(Consts.P_2)).count() + ""});
//            dataLines.add(new String[]{"draws", winners.stream().filter(w -> w.equals(Consts.EMPTY)).count() + ""});
//            dataSaver.saveToFile(dataLines);
////            }
//        }
//
//
//
//
//
//        /**     P1      **/
////        p1_depth = 1;
//        p1_estimate = new ThreeInLineEstFun();
////        p1_ai = new AlphaBeta(p1_depth, Consts.P_1, p1_estimate);
//
//        /**     P2      **/
////        p2_depth = 1;
//        p2_estimate = new TwoThreeInLineEstFun();
////        p2_ai = new MinMax(p2_depth, Consts.P_1, p2_estimate);
//
//
//        for (int j = 1; j <= 7; j++) {
//            p1_depth = j;
//            p1_ai = new MinMax(p1_depth, Consts.P_1, p1_estimate);
////            for (int k = 1; k <= 10; k++) {
//            p2_depth = j;
//            p2_ai = new MinMax(p2_depth, Consts.P_2, p2_estimate);
//
//            dataSaver = new DataSaver(p1_ai.getNameAlg() + "_" + p2_ai.getNameAlg() + "_AVG/" + p1_estimate.getNameEst() + "_" + p2_estimate.getNameEst(),
//                    p1_depth + "_" + p2_depth);
//
//            dataLines = new ArrayList<>();
//            dataLines.add(new String[]{"WINNER", "MOVES", "TIME"});
//            List<Integer> winners = new ArrayList<>();
//            List<Integer> moves = new ArrayList<>();
//            List<Integer> times = new ArrayList<>();
//            List<Double> timePerMove = new ArrayList<>();
//            for (int i = 1; i <= 10; i++) {
//                Integer[] temp = game.startAiVsAi(p1_ai, p2_ai, true);
//                winners.add(temp[0]);
//                moves.add(temp[1]);
//                times.add(temp[2]);
//                timePerMove.add(temp[2].doubleValue()/temp[1].doubleValue());
//                dataLines.add(new String[]{temp[0] + " ", temp[1] + " ", temp[2] +" ",timePerMove.get(i-1)+""});
//            }
//            dataLines.add(new String[]{"-", "-", "-"});
//            dataLines.add(new String[]{"moves", moves.stream().mapToDouble(Integer::doubleValue).average().orElse(0) + ""});
//            dataLines.add(new String[]{"times", times.stream().mapToDouble(Integer::doubleValue).average().orElse(0) + ""});
//            dataLines.add(new String[]{"timeMove", timePerMove.stream().mapToDouble(Double::doubleValue).average().orElse(0) + ""});
//            dataLines.add(new String[]{p1_ai.getNameAlg(), winners.stream().filter(w -> w.equals(Consts.P_1)).count() + ""});
//            dataLines.add(new String[]{p2_ai.getNameAlg(), winners.stream().filter(w -> w.equals(Consts.P_2)).count() + ""});
//            dataLines.add(new String[]{"draws", winners.stream().filter(w -> w.equals(Consts.EMPTY)).count() + ""});
//            dataSaver.saveToFile(dataLines);
////            }
//        }
//

//        for (int j = 1; j <= 10; j++) {
//            p1_depth = j;
//            p1_ai = new AlphaBeta(p1_depth, Consts.P_1, p1_estimate);
//            for (int k = 1; k <= 7; k++) {
//                p2_depth = k;
//                p2_ai = new MinMax(p2_depth, Consts.P_1, p2_estimate);
//
//                dataSaver = new DataSaver(p1_ai.getNameAlg() + "_" + p2_ai.getNameAlg() + "_AVG/" + p1_estimate.getNameEst() + "_" + p2_estimate.getNameEst(),
//                        p1_depth + "_" + p2_depth);
//
//                dataLines = new ArrayList<>();
//                dataLines.add(new String[]{"WINNER", "MOVES", "TIME"});
//                List<Integer> winners = new ArrayList<>();
//                List<Integer> moves = new ArrayList<>();
//                List<Integer> times = new ArrayList<>();
//                for (int i = 1; i <= 10; i++) {
//                    Integer[] temp = game.startAiVsAi(p1_ai, p2_ai, true);
//                    winners.add(temp[0]);
//                    moves.add(temp[1]);
//                    times.add(temp[2]);
//                    dataLines.add(new String[]{temp[0] + " ", temp[1] + " ", temp[2] + " "});
//                }
//                dataLines.add(new String[]{"-", "-", "-"});
//                dataLines.add(new String[]{"moves", moves.stream().mapToDouble(Integer::doubleValue).average().orElse(0) + ""});
//                dataLines.add(new String[]{"times", times.stream().mapToDouble(Integer::doubleValue).average().orElse(0) + ""});
//                dataLines.add(new String[]{p1_ai.getNameAlg(), winners.stream().filter(w -> w.equals(Consts.P_1)).count() + ""});
//                dataLines.add(new String[]{p2_ai.getNameAlg(), winners.stream().filter(w -> w.equals(Consts.P_2)).count() + ""});
//                dataLines.add(new String[]{"draws", winners.stream().filter(w -> w.equals(Consts.EMPTY)).count() + ""});
//                dataSaver.saveToFile(dataLines);
//            }
//        }
/*

         //FOR HEURISTIC

        //     P1
//        p1_depth = 1;
        p1_estimate = new ThreeInLineEstFun();
//        p1_ai = new AlphaBeta(p1_depth, Consts.P_1, p1_estimate);

        ///     P2
//        p2_depth = 1;
        p2_estimate = new TwoThreeInLineEstFun();
//        p2_ai = new MinMax(p2_depth, Consts.P_1, p2_estimate);


        for (int j = 1; j <= 7; j++) {
            p1_depth = j;
            p1_ai = new MinMax(p1_depth, Consts.P_1, p1_estimate);
            for (int k = 1; k <= 10; k++) {
                p2_depth = k;
                p2_ai = new AlphaBeta(p2_depth, Consts.P_1, p2_estimate);

                dataSaver = new DataSaver(p1_ai.getNameAlg() + "_" + p2_ai.getNameAlg() + "_AVG/" + p1_estimate.getNameEst() + "_" + p2_estimate.getNameEst(),
                        p1_depth + "_" + p2_depth);

                dataLines = new ArrayList<>();
                dataLines.add(new String[]{"WINNER", "MOVES", "TIME"});
                List<Integer> winners = new ArrayList<>();
                List<Integer> moves = new ArrayList<>();
                List<Integer> times = new ArrayList<>();
                for (int i = 1; i <= 10; i++) {
                    Integer[] temp = game.startAiVsAi(p1_ai, p2_ai, true);
                    winners.add(temp[0]);
                    moves.add(temp[1]);
                    times.add(temp[2]);
                    dataLines.add(new String[]{temp[0] + " ", temp[1] + " ", temp[2] + " "});
                }
                dataLines.add(new String[]{"-", "-", "-"});
                dataLines.add(new String[]{"moves", moves.stream().mapToDouble(Integer::doubleValue).average().orElse(0) + ""});
                dataLines.add(new String[]{"times", times.stream().mapToDouble(Integer::doubleValue).average().orElse(0) + ""});
                dataLines.add(new String[]{p1_ai.getNameAlg(), winners.stream().filter(w -> w.equals(Consts.P_1)).count() + ""});
                dataLines.add(new String[]{p2_ai.getNameAlg(), winners.stream().filter(w -> w.equals(Consts.P_2)).count() + ""});
                dataLines.add(new String[]{"draws", winners.stream().filter(w -> w.equals(Consts.EMPTY)).count() + ""});
                dataSaver.saveToFile(dataLines);
            }
        }

        for (int j = 1; j <= 10; j++) {
            p1_depth = j;
            p1_ai = new AlphaBeta(p1_depth, Consts.P_1, p1_estimate);
            for (int k = 1; k <= 7; k++) {
                p2_depth = k;
                p2_ai = new MinMax(p2_depth, Consts.P_1, p2_estimate);

                dataSaver = new DataSaver(p1_ai.getNameAlg() + "_" + p2_ai.getNameAlg() + "_AVG/" + p1_estimate.getNameEst() + "_" + p2_estimate.getNameEst(),
                        p1_depth + "_" + p2_depth);

                dataLines = new ArrayList<>();
                dataLines.add(new String[]{"WINNER", "MOVES", "TIME"});
                List<Integer> winners = new ArrayList<>();
                List<Integer> moves = new ArrayList<>();
                List<Integer> times = new ArrayList<>();
                for (int i = 1; i <= 10; i++) {
                    Integer[] temp = game.startAiVsAi(p1_ai, p2_ai, true);
                    winners.add(temp[0]);
                    moves.add(temp[1]);
                    times.add(temp[2]);
                    dataLines.add(new String[]{temp[0] + " ", temp[1] + " ", temp[2] + " "});
                }
                dataLines.add(new String[]{"-", "-", "-"});
                dataLines.add(new String[]{"moves", moves.stream().mapToDouble(Integer::doubleValue).average().orElse(0) + ""});
                dataLines.add(new String[]{"times", times.stream().mapToDouble(Integer::doubleValue).average().orElse(0) + ""});
                dataLines.add(new String[]{p1_ai.getNameAlg(), winners.stream().filter(w -> w.equals(Consts.P_1)).count() + ""});
                dataLines.add(new String[]{p2_ai.getNameAlg(), winners.stream().filter(w -> w.equals(Consts.P_2)).count() + ""});
                dataLines.add(new String[]{"draws", winners.stream().filter(w -> w.equals(Consts.EMPTY)).count() + ""});
                dataSaver.saveToFile(dataLines);
            }
        }

*/

//        new GameConsole().startHumVsAi(new NegaScout(7, Consts.P_2, new ThreeInLineEstFun()));
    }
}
