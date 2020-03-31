package krych.bartosz;

import krych.bartosz.abstra.Constraint;
import krych.bartosz.abstra.Heuristic;
import krych.bartosz.abstra.Problem;
import krych.bartosz.abstra.Variable;
import krych.bartosz.crossword.CrosswordVariable;
import krych.bartosz.sudoku.SudokuVariable;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static krych.bartosz.crossword.Orientation.VERTICAL;

public class ForwardChecking<P extends Problem<V>, C extends Constraint<T, V>, V extends Variable<T>, T, H extends Heuristic<V>> {
    private P problem;
    private C constraint;
    private H heuristic;
    private List<V> variables;


    private int resultsCount;
    private int reversionFirst;
    private int leavesFirst;
    private long timeFirst;
    private int reversion;
    private int leaves;
    private long startTime;


    public ForwardChecking(P problem, C constraint, H heuristic) {
        this.problem = problem;
        this.constraint = constraint;
        this.heuristic = heuristic;
    }

    public void start() {
        resultsCount = 0;
        reversionFirst = 0;
        leavesFirst = 0;
        timeFirst = 0;
        reversion = 0;
        leaves = 0;
        variables = heuristic.sort(problem.getVariables());

        startTime = System.nanoTime();
        execute(0);
        System.out.println("results: " + resultsCount + "\nMethod executed      ->      reversions: " + reversion + ",  leaves: " + leaves + ", time:" + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime) + "ms");
        if (resultsCount > 0)
            System.out.println("First result         ->      reversions: " + reversionFirst + ",  leaves: " + leavesFirst + ", time:" + TimeUnit.NANOSECONDS.toMillis(timeFirst) + "ms");
        System.out.println();
    }

    private boolean execute(int n) {
        if (n == variables.size()) {
            resultsCount++;
            if (resultsCount == 1) {
                timeFirst = System.nanoTime() - startTime;
                reversionFirst = reversion;
                leavesFirst = leaves;
            }
//            printResult(variables);
            return false;
        }

        //special for sudoku but sudoku dont working
        if (variables.get(n).getValue() != null) {
            return execute(n + 1);
        }

        for (T k : variables.get(n).getDomain()) {
            leaves++;
            List<V> toCheckList = List.copyOf(variables);
            toCheckList.get(n).setValue(k);

            if (filterDomains(toCheckList)) {
                variables.get(n).setValue(k);
                if (execute(n + 1))
                    return true;
                else
                    variables.get(n).setValue(null);
            }
        }
        reversion++;
        variables.get(n).setValue(null);
        return false;
    }

    private boolean filterDomains(List<V> list) {
        List<V> varIsNull = list.stream().filter(x -> x.getValue() == null).collect(Collectors.toList());
        for (V v : varIsNull) {
            int counter = 0;
            for (T t : v.getDomain()) {
                if (!constraint.isGood(list, v, t)) counter++;
            }
            if (counter == v.getDomain().size()) return false;
        }

        return true;
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
            int i = 0;
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
