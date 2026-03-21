package com.vivianhonghoa.chess.model.engine.specialmoves;

import com.vivianhonghoa.chess.model.Case;
import com.vivianhonghoa.chess.model.engine.Board;
import com.vivianhonghoa.chess.model.pieces.Piece;

public class EnPassantHandler {
    private final Board board;

    public EnPassantHandler(Board board) {
        this.board = board;
    }

    /**
     * If this move is an en passant capture, removes the captured pawn from the board
     * and returns it. Otherwise returns the original {@code captured} value unchanged.
     */
    public Piece resolveCapture(Piece piece, Case from, Case to, Piece captured, Piece[][] pieces) {
        if (piece.getType() == Piece.Type.PAWN && captured == null && from.col() != to.col()) {
            Piece passingCaptured = pieces[from.row()][to.col()];
            pieces[from.row()][to.col()] = null;
            return passingCaptured;
        }
        return captured;
    }

    /**
     * After a pawn move, updates the en passant target square on the board.
     */
    public void updateTarget(Piece piece, Case from, Case to) {
        if (piece.getType() == Piece.Type.PAWN && Math.abs(to.row() - from.row()) == 2) {
            int epRow = (from.row() + to.row()) / 2;
            board.setPassingCaptureTarget(new Case(epRow, to.col()));
        } else {
            board.setPassingCaptureTarget(null);
        }
    }

    /**
     * Undoes an en passant capture: restores the captured pawn and removes it from
     * the captured list.
     */
    public void undoCapture(Piece piece, Case from, Case to, Piece captured, Piece[][] pieces) {
        if (piece.getType() == Piece.Type.PAWN && captured == null && from.col() != to.col()) {
            Piece passingCaptured = pieces[from.row()][to.col()];
            pieces[from.row()][to.col()] = passingCaptured;
            if (passingCaptured != null) {
                if (piece.getColor() == Piece.Color.WHITE) {
                    board.getCapturedByWhite().remove(passingCaptured);
                } else {
                    board.getCapturedByBlack().remove(passingCaptured);
                }
            }
        }
    }
}
