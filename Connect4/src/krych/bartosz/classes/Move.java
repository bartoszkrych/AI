package krych.bartosz.classes;

public class Move {
    private Integer col;
    private Integer row;
    private Integer estimate;

    public Move(Integer row, Integer col) {
        this.col = col;
        this.row = row;
    }

    public Move() {
    }

    public Move(Integer estimate) {
        this.estimate = estimate;
    }

    public Move(Integer row, Integer col, Integer estimate) {
        this.col = col;
        this.row = row;
        this.estimate = estimate;
    }

    public Move(Move secMove) {
        this.col = secMove.col;
        this.row = secMove.row;
        this.estimate = secMove.estimate;
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

    public Integer getEstimate() {
        return estimate;
    }

    public void setEstimate(Integer estimate) {
        this.estimate = estimate;
    }

    public Move scoutChange() {
        this.estimate = -1 * estimate;
        return this;
    }

    public Move scoutInc() {
        this.estimate++;
        return this;
    }

    public Move scoutChangeMinus() {
        this.estimate = -1 * estimate - 1;
        return this;
    }

    @Override
    public String toString() {
        return "Move{" +
                "col=" + col +
                ", row=" + row +
                ", fitness=" + estimate +
                '}';
    }
}
