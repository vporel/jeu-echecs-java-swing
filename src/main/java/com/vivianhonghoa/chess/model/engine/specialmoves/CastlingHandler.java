package com.vivianhonghoa.chess.model.engine.specialmoves;

import com.vivianhonghoa.chess.model.Case;
import com.vivianhonghoa.chess.model.pieces.Piece;

public class CastlingHandler {

    public boolean applies(Piece piece, Case from, Case to) {
        return piece.getType() == Piece.Type.KING && Math.abs(to.col() - from.col()) == 2;
    }

    public void apply(Piece piece, Case from, Case to, Piece[][] pieces) {
        if (!applies(piece, from, to)) return;
        int rookFromCol, rookToCol;
        if (to.col() < from.col()) {
            rookFromCol = 0;
            rookToCol = 2;
        } else {
            rookFromCol = 7;
            rookToCol = 4;
        }
        Piece rook = pieces[from.row()][rookFromCol];
        pieces[from.row()][rookToCol] = rook;
        pieces[from.row()][rookFromCol] = null;
        rook.setPosition(from.row(), rookToCol);
        rook.setHasMoved(true);
    }

    public void undo(Piece piece, Case from, Case to, Piece[][] pieces) {
        if (!applies(piece, from, to)) return;
        int rookFromCol, rookToCol;
        if (to.col() < from.col()) {
            rookFromCol = 0;
            rookToCol = 2;
        } else {
            rookFromCol = 7;
            rookToCol = 4;
        }
        Piece rook = pieces[from.row()][rookToCol];
        pieces[from.row()][rookFromCol] = rook;
        pieces[from.row()][rookToCol] = null;
        if (rook != null) {
            rook.setPosition(from.row(), rookFromCol);
        }
    }
}
