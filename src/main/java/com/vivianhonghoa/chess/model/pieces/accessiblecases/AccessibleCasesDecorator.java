package com.vivianhonghoa.chess.model.pieces.accessiblecases;

import com.vivianhonghoa.chess.model.Case;
import com.vivianhonghoa.chess.model.engine.Board;
import com.vivianhonghoa.chess.model.engine.BoardHelper;
import com.vivianhonghoa.chess.model.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public abstract class AccessibleCasesDecorator implements AccessibleCasesCalculator {

    protected final AccessibleCasesCalculator decorated;

    protected AccessibleCasesDecorator(AccessibleCasesCalculator decorated) {
        this.decorated = decorated;
    }

    @Override
    public List<Case> getAccessibleCases(Board board, Piece piece) {
        return decorated.getAccessibleCases(board, piece);
    }

    /**
     * Helper for sliding pieces (rook, bishop, queen).
     * Walks in a direction until leaving the board or hitting a piece.
     */
    protected List<Case> getCasesInDirection(Board board, Case position, int dRow, int dCol) {
        Piece piece = board.getPieceAt(position);
        Piece.Color color = piece.getColor();
        List<Case> cases = new ArrayList<>();
        int r = position.row() + dRow;
        int c = position.col() + dCol;
        while (BoardHelper.isValid(r, c)) {
            Piece target = board.getPieceAt(r, c);
            if (target == null) {
                cases.add(new Case(r, c));
            } else {
                if (target.getColor() != color) {
                    cases.add(new Case(r, c));
                }
                break;
            }
            r += dRow;
            c += dCol;
        }
        return cases;
    }
}
