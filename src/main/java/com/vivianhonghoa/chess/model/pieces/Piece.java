package com.vivianhonghoa.chess.model.pieces;

import com.vivianhonghoa.chess.model.engine.Board;
import com.vivianhonghoa.chess.model.engine.Case;

import java.util.ArrayList;
import java.util.List;

public abstract class Piece {
    private final Color color;
    protected Board board;
    protected int row;
    protected int col;
    protected boolean hasMoved = false;

    protected Piece(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public Piece setBoard(Board board) {
        this.board = board;
        return this;
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

    public abstract PieceType getType();

    public abstract char getLetter();

    public abstract List<Case> getAccessibleCases();

    public boolean canMoveTo(Case targetCase) {
        return getAccessibleCases().contains(targetCase);
    }

    /**
     * Helper for non-sliding pieces (king, knight).
     * Adds the square if it is on the board and empty or occupied by an opponent.
     */
    protected void addIfAccessible(List<Case> cases, int r, int c) {
        if (Case.isValid(r, c)) {
            Piece target = board.getPieceAt(r, c);
            if (target == null || target.getColor() != getColor()) {
                cases.add(new Case(r, c));
            }
        }
    }

    /**
     * Helper for sliding pieces (rook, bishop, queen).
     * Walks in a direction until leaving the board or hitting a piece.
     */
    protected List<Case> getCasesInDirection(int dRow, int dCol) {
        List<Case> cases = new ArrayList<>();
        int r = row + dRow;
        int c = col + dCol;
        while (Case.isValid(r, c)) {
            Piece target = board.getPieceAt(r, c);
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

    public enum PieceType {
        PAWN, ROOK, KNIGHT, BISHOP, QUEEN, KING
    }

    public enum Color {
        WHITE,
        BLACK;
    }
}
