package com.vivianhonghoa.chess.model.pieces;

import com.vivianhonghoa.chess.model.Case;
import com.vivianhonghoa.chess.model.engine.Board;
import com.vivianhonghoa.chess.model.engine.BoardHelper;

import java.util.List;

public final class PieceUtils {

    private PieceUtils() {
        // Utility class, prevent instantiation
    }

    /**
     * Helper for non-sliding pieces (king, knight).
     * Adds the square if it is on the board and empty or occupied by an opponent.
     */
    public static void addIfAccessible(Board board, Piece piece, List<Case> cases, Case target) {
        if (!BoardHelper.isValid(target)) return;
        Piece targetPiece = board.getPieceAt(target.row(), target.col());
        if (targetPiece == null || targetPiece.getColor() != piece.getColor()) {
            cases.add(target);
        }
    }
}
