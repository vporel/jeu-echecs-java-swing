package com.vivianhonghoa.chess.model;

public class BoxData {
    private final int row;
    private final int col;
    private BoxState state;
    private Piece piece;

    public BoxData(int row, int col) {
        this.row = row;
        this.col = col;
        this.state = BoxState.DEFAULT;
        this.piece = null;
    }


    public int getRow() { return row; }
    public int getCol() { return col; }
    public BoxState getState() { return state; }
    public void setState(BoxState state) { this.state = state; }

    public Piece getPiece() { return piece; }
    public void setPiece(Piece piece) { this.piece = piece; }
    public boolean isEmpty() { return piece == null; }

    public boolean checkPosition(int row, int col) {
        return this.row == row && this.col == col;
    }

    public enum BoxState {
        DEFAULT, CLICKED, HOVERED
    }
}
