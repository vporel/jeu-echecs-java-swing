package com.vivianhonghoa.chess.model.engine;

import com.vivianhonghoa.chess.model.pieces.Piece;

import java.util.Map;
import java.util.OptionalInt;

class GameStateChecker {

    /**
     * Checks whether the game is over after a move.
     *
     * @return an OptionalInt containing the winner's player number (1 or 2),
     *         or 0 for a draw, or empty if the game should continue.
     */
    static OptionalInt getWinner(Board board, Map<String, Integer> positionHistory) {
        if (positionHistory.values().stream().anyMatch(count -> count >= 3)) return OptionalInt.of(0);
        if (BoardHelper.isKingInCheckmate(board, Piece.Color.WHITE)) return OptionalInt.of(2);
        if (BoardHelper.isKingInCheckmate(board, Piece.Color.BLACK)) return OptionalInt.of(1);
        if (BoardHelper.isStalemate(board, Piece.Color.WHITE) || BoardHelper.isStalemate(board, Piece.Color.BLACK)) return OptionalInt.of(0);
        return OptionalInt.empty();
    }
}
