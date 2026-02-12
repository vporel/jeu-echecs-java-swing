package com.vivianhonghoa.chess.model;

public class BoxData {
    private final int row;
    private final int col;
    private BoxState state;

    public BoxData(int row, int col) {
        this.row = row;
        this.col = col;
        this.state = BoxState.DEFAULT;
    }


    public int getRow() { return row; }
    public int getCol() { return col; }
    public BoxState getState() { return state; }
    public void setState(BoxState state) { this.state = state; }

    public boolean checkPosition(int row, int col) {
        return this.row == row && this.col == col;
    }

    public enum BoxState {
        DEFAULT, CLICKED, HOVERED
    }
}
