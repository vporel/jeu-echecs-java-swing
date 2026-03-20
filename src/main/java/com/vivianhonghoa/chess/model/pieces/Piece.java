package com.vivianhonghoa.chess.model.pieces;

import com.vivianhonghoa.chess.model.engine.Board;
import com.vivianhonghoa.chess.model.Case;
import com.vivianhonghoa.chess.model.pieces.accessiblecases.AccessibleCasesCalculator;

import java.util.ArrayList;
import java.util.List;

public abstract class Piece {
    private final Color color;
    protected Board board;
    protected int row;
    protected int col;
    protected boolean hasMoved = false;
    protected final AccessibleCasesCalculator accessibleCasesCalculator;

    protected Piece(Color color, AccessibleCasesCalculator accessibleCasesCalculator) {
        this.color = color;
        this.accessibleCasesCalculator = accessibleCasesCalculator;
    }

    public Color getColor() {
        return color;
    }

    public Piece setBoard(Board board) {
        this.board = board;
        return this;
    }

    public Case getPosition(){
        return new Case(row, col);
    }

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public abstract Type getType();

    public abstract char getLetter();

    public List<Case> getAccessibleCases() {
        return accessibleCasesCalculator.getAccessibleCases(board, this);
    }

    public boolean canMoveTo(Case targetCase) {
        return getAccessibleCases().contains(targetCase);
    }

    public String getUnicodeSymbol() {
        return switch (getType()) {
            case KING -> "\u265A";
            case QUEEN -> "\u265B";
            case ROOK -> "\u265C";
            case BISHOP -> "\u265D";
            case KNIGHT -> "\u265E";
            case PAWN -> "\u265F";
        };
    }

    public enum Type {
        PAWN, ROOK, KNIGHT, BISHOP, QUEEN, KING
    }

    public enum Color {
        WHITE,
        BLACK;
    }
}
