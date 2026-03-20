package com.vivianhonghoa.chess.model.engine;

import com.vivianhonghoa.chess.model.Case;
import com.vivianhonghoa.chess.model.pieces.Piece;

import java.util.Map;

public class BoardEvaluator {
    private static final int MATERIAL_WEIGHT = 100;
    private static final int MOBILITY_WEIGHT = 10;
    private static final int KING_SAFETY_WEIGHT = 50;

    private static final Map<Piece.Type, Integer> PIECE_VALUES = Map.of(
        Piece.Type.PAWN, 1,
        Piece.Type.KNIGHT, 3,
        Piece.Type.BISHOP, 3,
        Piece.Type.ROOK, 5,
        Piece.Type.QUEEN, 9,
        Piece.Type.KING, 0
    );

    private final Board board;

    public BoardEvaluator(Board board) {
        this.board = board;
    }

    public int evaluate(Piece.Color playerColor) {
        int score = 0;

        score += evaluateMaterial(playerColor) * MATERIAL_WEIGHT;
        score += evaluateMobility(playerColor) * MOBILITY_WEIGHT;
        score += evaluateKingSafety(playerColor) * KING_SAFETY_WEIGHT;
        score += evaluatePawnStructure(playerColor);

        return score;
    }

    private int evaluateMaterial(Piece.Color playerColor) {
        int materialScore = 0;
        for (Piece piece : board.getPiecesAsList()) {
            if (piece.getColor() == playerColor) {
                materialScore += PIECE_VALUES.get(piece.getType());
            }
        }
        return materialScore;
    }

    private int evaluateMobility(Piece.Color playerColor) {
        int mobilityScore = 0;
        for (Piece piece : board.getPiecesAsList()) {
            if (piece.getColor() == playerColor) {
                mobilityScore += piece.getAccessibleCases().size();
            }
        }
        return mobilityScore;
    }

    private int evaluateKingSafety(Piece.Color playerColor) {
        // Simple heuristic: count how many pieces are attacking the opponent's king
        Piece.Color opponentColor = (playerColor == Piece.Color.WHITE) ? Piece.Color.BLACK : Piece.Color.WHITE;
        Case opponentKingPos = BoardHelper.findKingPosition(board, opponentColor);
        if (opponentKingPos == null) return 0; // Should not happen in a valid game

        int attackers = BoardHelper.countAttackers(board, opponentKingPos.row(), opponentKingPos.col(), playerColor);
        return -attackers; // More attackers means worse safety for the opponent's king
    }

    private int evaluatePawnStructure(Piece.Color playerColor) {
        // Simple heuristic: penalize doubled, isolated, and backward pawns
        int penalty = 0;
        for (Piece piece : board.getPiecesAsList()) {
            if (piece.getColor() == playerColor && piece.getType() == Piece.Type.PAWN) {
                int col = piece.getCol();
                int row = piece.getRow();
                // Check for doubled pawns
                if (BoardHelper.hasPieceTypeAt(board, row, col, Piece.Type.PAWN) && BoardHelper.hasPieceTypeAt(board, row + 1, col, Piece.Type.PAWN)) {
                    penalty += 1; // Doubled pawn
                }
                // Check for isolated pawns
                if (!BoardHelper.hasPieceTypeAt(board, row, col - 1, Piece.Type.PAWN) && !BoardHelper.hasPieceTypeAt(board, row, col + 1, Piece.Type.PAWN)) {
                    penalty += 1; // Isolated pawn
                }
                // Check for backward pawns (simplified)
                if (BoardHelper.hasPieceTypeAt(board, row - 1, col, Piece.Type.PAWN) && !BoardHelper.hasPieceTypeAt(board, row + 1, col, Piece.Type.PAWN)) {
                    penalty += 1; // Backward pawn
                }
            }
        }
        return -penalty; // Penalties reduce the score
    }
}
