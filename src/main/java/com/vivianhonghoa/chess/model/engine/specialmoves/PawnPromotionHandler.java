package com.vivianhonghoa.chess.model.engine.specialmoves;

import com.vivianhonghoa.chess.model.Case;
import com.vivianhonghoa.chess.model.engine.Board;
import com.vivianhonghoa.chess.model.pieces.Pawn;
import com.vivianhonghoa.chess.model.pieces.Piece;

public class PawnPromotionHandler {
    private final Board board;

    public PawnPromotionHandler(Board board) {
        this.board = board;
    }

    private boolean isValid(Piece piece, Case to) {
        if (piece.getType() != Piece.Type.PAWN) return false;
        int promotionRow = (piece.getColor() == Piece.Color.WHITE) ? 7 : 0;
        return to.row() == promotionRow;
    }

    public void apply(Piece piece, Case to, Piece[][] pieces) {
        if (!isValid(piece, to)) return;
        Piece promoted = board.getPromotionHandler().choosePiece(piece.getColor());
        promoted.setBoard(board);
        pieces[to.row()][to.col()] = promoted;
        promoted.setPosition(to.row(), to.col());
        promoted.setHasMoved(true);
    }

    public void undo(Piece piece, Case from, Case to, Piece[][] pieces) {
        if (!isValid(piece, to)) return;
        Piece originalPawn = new Pawn(piece.getColor()).setBoard(board);
        pieces[to.row()][to.col()] = originalPawn;
        originalPawn.setPosition(to.row(), to.col());
    }
}
