package com.vivianhonghoa.chess.model.pieces.accessiblecases;

import com.vivianhonghoa.chess.model.Case;
import com.vivianhonghoa.chess.model.engine.Board;
import com.vivianhonghoa.chess.model.engine.BoardHelper;
import com.vivianhonghoa.chess.model.pieces.Piece;
import com.vivianhonghoa.chess.model.pieces.PieceUtils;

import java.util.ArrayList;
import java.util.List;

public class KingAccessibleCasesCalculator implements AccessibleCasesCalculator {

    @Override
    public List<Case> getAccessibleCases(Board board, Piece piece) {
        List<Case> cases = new ArrayList<>();
        Case position = piece.getPosition();

        PieceUtils.addIfAccessible(board, piece, cases, new Case(position.row() - 1, position.col() - 1));
        PieceUtils.addIfAccessible(board, piece, cases, new Case(position.row() - 1, position.col()));
        PieceUtils.addIfAccessible(board, piece, cases, new Case(position.row() - 1, position.col() + 1));
        PieceUtils.addIfAccessible(board, piece, cases, new Case(position.row(), position.col() - 1));
        PieceUtils.addIfAccessible(board, piece, cases, new Case(position.row(), position.col() + 1));
        PieceUtils.addIfAccessible(board, piece, cases, new Case(position.row() + 1, position.col() - 1));
        PieceUtils.addIfAccessible(board, piece, cases, new Case(position.row() + 1, position.col()));
        PieceUtils.addIfAccessible(board, piece, cases, new Case(position.row() + 1, position.col() + 1));

        addCastlingMoves(board, piece, cases);

        return cases;
    }

    private void addCastlingMoves(Board board, Piece piece, List<Case> cases) {
        if (piece.hasMoved()) return;
        int row = piece.getRow();
        int col = piece.getCol();

        Piece.Color opponent = (piece.getColor() == Piece.Color.WHITE) ? Piece.Color.BLACK : Piece.Color.WHITE;

        if (BoardHelper.isSquareAttackedBy(board, row, col, opponent)) return;

        // Queenside castling: King at col 3 → col 1, Rook(0) → col 2
        Piece qRook = board.getPieceAt(row, 0);
        if (qRook != null && qRook.getType() == Piece.Type.ROOK && qRook.getColor() == piece.getColor() && !qRook.hasMoved()) {
            if (board.getPieceAt(row, 1) == null && board.getPieceAt(row, 2) == null) {
                if (!BoardHelper.isSquareAttackedBy(board, row, 1, opponent)
                        && !BoardHelper.isSquareAttackedBy(board, row, 2, opponent)) {
                    cases.add(new Case(row, 1));
                }
            }
        }

        // Kingside castling: King at col 3 → col 5, Rook(7) → col 4
        Piece kRook = board.getPieceAt(row, 7);
        if (kRook != null && kRook.getType() == Piece.Type.ROOK && kRook.getColor() == piece.getColor() && !kRook.hasMoved()) {
            if (board.getPieceAt(row, 4) == null && board.getPieceAt(row, 5) == null
                    && board.getPieceAt(row, 6) == null) {
                if (!BoardHelper.isSquareAttackedBy(board, row, 4, opponent)
                        && !BoardHelper.isSquareAttackedBy(board, row, 5, opponent)) {
                    cases.add(new Case(row, 5));
                }
            }
        }
    }
}
