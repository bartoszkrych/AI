package krych.bartosz.classes;

public class Move {
    private Integer col;
    private Integer row;
    private Integer fitness;

    public Move(Integer row, Integer col) {
        this.col = col;
        this.row = row;
    }

    public Move() {
    }

    public Move(Integer fitness) {
        this.fitness = fitness;
    }

    public Move(Integer row, Integer col, Integer fitness) {
        this.col = col;
        this.row = row;
        this.fitness = fitness;
    }

    public Integer getCol() {
        return col;
    }

    public void setCol(Integer col) {
        this.col = col;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getFitness() {
        return fitness;
    }

    public void setFitness(Integer fitness) {
        this.fitness = fitness;
    }
}
