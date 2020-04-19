package classes;

public class Move {
    private Integer col;
    private Integer row;
    private Integer val;

    public Move(Integer row, Integer col) {
        this.col = col;
        this.row = row;
    }

    public Move() {
    }

    public Move(Integer val) {
        this.val = val;
    }

    public Move(Integer row, Integer col, Integer val) {
        this.col = col;
        this.row = row;
        this.val = val;
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

    public Integer getVal() {
        return val;
    }

    public void setVal(Integer val) {
        this.val = val;
    }
}
