package com.vivianhonghoa.chess.model.pieces;

import com.vivianhonghoa.chess.model.Board;
import com.vivianhonghoa.chess.model.Case;

import java.util.ArrayList;
import java.util.List;

public abstract class Piece {
    private final Color color;
    protected Board board;
    protected int row;
    protected int col;

    protected Piece(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setPosition(Board board, int row, int col) {
        this.board = board;
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public abstract List<Case> getAccessibleCases();

    /**
     * Helper for sliding pieces (rook, bishop, queen).
     * Walks in a direction until leaving the board or hitting a piece.
     */
    protected List<Case> getCasesInDirection(int dRow, int dCol) {
        List<Case> cases = new ArrayList<>();
        int r = row + dRow;
        int c = col + dCol;
        while (Case.isValid(r, c)) {
            Piece target = board.getPiece(r, c);
            if (target == null) {
                cases.add(new Case(r, c));
            } else {
                if (target.getColor() != this.color) {
                    cases.add(new Case(r, c));
                }
                break;
            }
            r += dRow;
            c += dCol;
        }
        return cases;
    }

    public enum Color {
        BLANC,
        NOIR;
    }
}
