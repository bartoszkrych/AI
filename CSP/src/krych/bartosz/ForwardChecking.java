package krych.bartosz;

import krych.bartosz.abstra.*;
import krych.bartosz.crossword.CrosswordVariable;
import krych.bartosz.sudoku.SudokuVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static krych.bartosz.crossword.Orientation.VERTICAL;

public class ForwardChecking<P extends Problem<V>, C extends Constraint<T, V>, V extends Variable<T>, T, H extends VarHeuristic<V>, D extends DomHeuristic<V>> {
    private P problem;
    private C constraint;
    private H varHeuristic;
    private D domHeuristic;
    private List<V> variables;
    private List<List<Boolean>> boolList;

    private int resultsCount;
    private int reversionsFirst;
    private int nodesFirst;
    private long timeFirst;
    private int reversions;
    private int nodes;
    private long startTime;
    private DataSaver dataSaver;

    public ForwardChecking(P problem, C constraint, H varHeuristic, D domHeuristic, DataSaver dataSaver) {
        this.problem = problem;
        this.constraint = constraint;
        this.varHeuristic = varHeuristic;
        this.domHeuristic = domHeuristic;
        this.dataSaver = dataSaver;
        this.dataSaver.setCspType("forward");
    }

    public void start() {
        List<String[]> dataLines = new ArrayList<>();
        List<Integer> firNodes = new ArrayList<>();
        List<Integer> firRevs = new ArrayList<>();
        List<Long> firTime = new ArrayList<>();
        List<Integer> nods = new ArrayList<>();
        List<Integer> revs = new ArrayList<>();
        List<Long> times = new ArrayList<>();
        dataLines.add(new String[]{"firTime", "firNodes", "firRevs", "time", "nodes", "revs"});
        for (int i = 0; i < 10; i++) {
            initValues();
            startTime = System.nanoTime();
            execute(0);
            Long endTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
            System.out.println("results: " + resultsCount + "\nMethod executed      ->      reversions: " + reversions + ",  nodes: " + nodes + ", time:" + endTime + "ms");
//            if (resultsCount > 0)
            System.out.println("First result         ->      reversions: " + reversionsFirst + ",  nodes: " + nodesFirst + ", time:" + TimeUnit.NANOSECONDS.toMillis(timeFirst) + "ms");
            System.out.println();
            firNodes.add(nodesFirst);
            firRevs.add(reversionsFirst);
            firTime.add(TimeUnit.NANOSECONDS.toMillis(timeFirst));
            nods.add(nodes);
            revs.add(reversions);
            times.add(endTime);
        }
        dataLines.add(new String[]{String.valueOf(firTime.stream().mapToLong(Long::longValue).average().orElse(-1)),
                String.valueOf(firNodes.stream().mapToInt(Integer::intValue).average().orElse(-1)),
                String.valueOf(firRevs.stream().mapToInt(Integer::intValue).average().orElse(-1)),
                String.valueOf(times.stream().mapToLong(Long::longValue).average().orElse(-1)),
                String.valueOf(nods.stream().mapToInt(Integer::intValue).average().orElse(-1)),
                String.valueOf(revs.stream().mapToInt(Integer::intValue).average().orElse(-1))});
        dataSaver.saveToFile(dataLines);
    }

    private boolean execute(int n) {
        if (n == variables.size()) {
            resultsCount++;
            if (resultsCount == 1) {
                timeFirst = System.nanoTime() - startTime;
                reversionsFirst = reversions;
                nodesFirst = nodes;
            }
//            printResult(variables);
            return false;
        }

        for (T k : variables.get(n).getDomain()) {
            nodes++;
            if (filterDomains(k, n) && execute(n + 1)) return true;
            resetBoolList(n + 1);
        }
        reversions++;
        variables.get(n).setValue(null);
        return false;
    }

    private boolean filterDomains(T value, int n) {
        int countValues = variables.stream().filter(x -> x.getValue() != null && x.getValue().equals(value)).collect(Collectors.toList()).size();
        if (countValues > 0) return false;
        variables.get(n).setValue(value);
        List<V> varIsNull = variables.stream().filter(x -> x.getValue() == null).collect(Collectors.toList());
        for (int i = 0; i < varIsNull.size(); i++) {
            for (int j = 0; j < varIsNull.get(i).getDomain().size(); j++) {
                if (boolList.get(n + i + 1).get(j) && !constraint.isGood(variables, varIsNull.get(i), varIsNull.get(i).getDomain().get(j))) {
                    boolList.get(n + i + 1).set(j, false);
                }
            }
            if (!boolList.get(n + i + 1).contains(Boolean.TRUE)) return false;
        }
        return true;
    }

    private void initValues() {
        resultsCount = 0;
        reversionsFirst = 0;
        nodesFirst = 0;
        timeFirst = 0;
        reversions = 0;
        nodes = 0;
        variables = varHeuristic.sort(problem.getVariables());
        domHeuristic.sort(variables);
        initBoolList();
    }

    private void initBoolList() {
        boolList = new ArrayList<>();
        for (V v : variables) {
            List<Boolean> array = new ArrayList<>();
            for (T ignored : v.getDomain()) {
                array.add(Boolean.TRUE);
            }
            boolList.add(array);
        }
    }

    private void resetBoolList(int idx) {
        for (int i = idx; i < boolList.size(); i++) {
            for (int j = 0; j < boolList.get(i).size(); j++) {
                if (!boolList.get(i).get(j)) boolList.get(i).set(j, true);
            }
        }
    }

    private void printResult(List<V> list) {
        System.out.println("#####       RESULT - " + resultsCount + "       #####");
        if (list.get(0) instanceof CrosswordVariable) {
            char[][] result = new char[problem.getHeight()][problem.getWidth()];
            List<CrosswordVariable> listA = (List<CrosswordVariable>) list;

            for (CrosswordVariable v : listA) {
                int col = v.getjBegin();
                int row = v.getiBegin();
                int len = v.getLength();
                if (v.getValue() == null) continue;
                if (v.getOrientation() == VERTICAL) {
                    for (int i = 0; i < len; i++)
                        result[i + row][col] = v.getValue().charAt(i);
                } else {
                    for (int i = 0; i < len; i++)
                        result[row][i + col] = v.getValue().charAt(i);
                }
            }

            for (char[] chars : result) {
                for (int j = 0; j < result[0].length; j++) {
                    if (chars[j] == 0) System.out.print("# ");
                    else System.out.print(chars[j] + " ");
                }
                System.out.println();
            }

            System.out.println("\n");
        } else {
            List<SudokuVariable> listA = (List<SudokuVariable>) list;
            for (SudokuVariable v : listA) {
                Integer val = v.getValue();
                System.out.print(val == null ? 0 : val);
                System.out.print(" ");
                if (v.getJ() == 2 || v.getJ() == 5) System.out.print("| ");
                if (v.getI() == 2 || v.getI() == 5) System.out.print("\n---------------------");
                if (v.getJ() == 8) System.out.println();
            }

        }
    }

}
