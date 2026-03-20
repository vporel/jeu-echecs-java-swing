package com.vivianhonghoa.chess.model.pieces;

import com.vivianhonghoa.chess.model.engine.Case;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {

    public King(Color color) {
        super(color);
    }

    @Override
    public Type getType() {
        return Type.KING;
    }

    @Override
    public char getLetter() {
        return 'K';
    }

    @Override
    public List<Case> getAccessibleCases() {
        List<Case> cases = new ArrayList<>();

        // The king can move 1 square in any direction
        addIfAccessible(cases, row - 1, col - 1);
        addIfAccessible(cases, row - 1, col);
        addIfAccessible(cases, row - 1, col + 1);
        addIfAccessible(cases, row, col - 1);
        addIfAccessible(cases, row, col + 1);
        addIfAccessible(cases, row + 1, col - 1);
        addIfAccessible(cases, row + 1, col);
        addIfAccessible(cases, row + 1, col + 1);

        addCastlingMoves(cases);

        return cases;
    }

    private void addCastlingMoves(List<Case> cases) {
        if (hasMoved) return;

        Color opponent = (getColor() == Color.WHITE) ? Color.BLACK : Color.WHITE;

        // King must not be in check
        if (board.isSquareAttackedBy(row, col, opponent)) return;

        // Queenside castling: King at col 3 → col 1, Rook(0) → col 2
        Piece qRook = board.getPieceAt(row, 0);
        if (qRook != null && qRook.getType() == Type.ROOK && qRook.getColor() == getColor() && !qRook.hasMoved()) {
            // Path must be clear: cols 1, 2
            if (board.getPieceAt(row, 1) == null && board.getPieceAt(row, 2) == null) {
                // King must not pass through or land on attacked square: cols 1, 2
                if (!board.isSquareAttackedBy(row, 1, opponent)
                        && !board.isSquareAttackedBy(row, 2, opponent)) {
                    cases.add(new Case(row, 1));
                }
            }
        }

        // Kingside castling: King at col 3 → col 5, Rook(7) → col 4
        Piece kRook = board.getPieceAt(row, 7);
        if (kRook != null && kRook.getType() == Type.ROOK && kRook.getColor() == getColor() && !kRook.hasMoved()) {
            // Path must be clear: cols 4, 5, 6
            if (board.getPieceAt(row, 4) == null && board.getPieceAt(row, 5) == null
                    && board.getPieceAt(row, 6) == null) {
                // King must not pass through or land on attacked square: cols 4, 5
                if (!board.isSquareAttackedBy(row, 4, opponent)
                        && !board.isSquareAttackedBy(row, 5, opponent)) {
                    cases.add(new Case(row, 5));
                }
            }
        }
    }
}
