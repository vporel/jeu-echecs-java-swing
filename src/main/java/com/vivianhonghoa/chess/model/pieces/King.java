package com.vivianhonghoa.chess.model.pieces;

import com.vivianhonghoa.chess.model.engine.Board;
import com.vivianhonghoa.chess.model.engine.BoardHelper;
import com.vivianhonghoa.chess.model.Case;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {

    public King(Color color) {
        super(color, (board, piece) -> {
            List<Case> cases = new ArrayList<>();

            Case position = piece.getPosition();

            // The king can move 1 square in any direction
            PieceUtils.addIfAccessible(board, piece, cases, new Case(position.row() - 1, position.col() - 1));
            PieceUtils.addIfAccessible(board, piece, cases, new Case(position.row() - 1, position.col()));
            PieceUtils.addIfAccessible(board, piece, cases, new Case(position.row() - 1, position.col() + 1));
            PieceUtils.addIfAccessible(board, piece, cases, new Case(position.row(), position.col() - 1));
            PieceUtils.addIfAccessible(board, piece, cases, new Case(position.row(), position.col() + 1));
            PieceUtils.addIfAccessible(board, piece, cases, new Case(position.row() + 1, position.col() - 1));
            PieceUtils.addIfAccessible(board, piece, cases, new Case(position.row() + 1, position.col()));
            PieceUtils.addIfAccessible(board, piece, cases, new Case(position.row() + 1, position.col() + 1));

            addCastlingMoves(board, piece, cases);

            return  cases;
        });
    }

    @Override
    public Type getType() {
        return Type.KING;
    }

    @Override
    public char getLetter() {
        return 'K';
    }

    private static void addCastlingMoves(Board board, Piece piece, List<Case> cases) {
        if (piece.hasMoved()) return;
        int row = piece.getRow();
        int col = piece.getCol();

        Color opponent = (piece.getColor() == Color.WHITE) ? Color.BLACK : Color.WHITE;

        // King must not be in check
        if (BoardHelper.isSquareAttackedBy(board, row, col, opponent)) return;

        // Queenside castling: King at col 3 → col 1, Rook(0) → col 2
        Piece qRook = board.getPieceAt(row, 0);
        if (qRook != null && qRook.getType() == Type.ROOK && qRook.getColor() == piece.getColor() && !qRook.hasMoved()) {
            // Path must be clear: cols 1, 2
            if (board.getPieceAt(row, 1) == null && board.getPieceAt(row, 2) == null) {
                // King must not pass through or land on attacked square: cols 1, 2
                if (!BoardHelper.isSquareAttackedBy(board, row, 1, opponent)
                        && !BoardHelper.isSquareAttackedBy(board, row, 2, opponent)) {
                    cases.add(new Case(row, 1));
                }
            }
        }

        // Kingside castling: King at col 3 → col 5, Rook(7) → col 4
        Piece kRook = board.getPieceAt(row, 7);
        if (kRook != null && kRook.getType() == Type.ROOK && kRook.getColor() == piece.getColor() && !kRook.hasMoved()) {
            // Path must be clear: cols 4, 5, 6
            if (board.getPieceAt(row, 4) == null && board.getPieceAt(row, 5) == null
                    && board.getPieceAt(row, 6) == null) {
                // King must not pass through or land on attacked square: cols 4, 5
                if (!BoardHelper.isSquareAttackedBy(board, row, 4, opponent)
                        && !BoardHelper.isSquareAttackedBy(board, row, 5, opponent)) {
                    cases.add(new Case(row, 5));
                }
            }
        }
    }
}
