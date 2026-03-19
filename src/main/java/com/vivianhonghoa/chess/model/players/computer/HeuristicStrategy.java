package com.vivianhonghoa.chess.model.players.computer;

import com.vivianhonghoa.chess.model.engine.Board;
import com.vivianhonghoa.chess.model.engine.Case;
import com.vivianhonghoa.chess.model.pieces.*;
import com.vivianhonghoa.chess.model.players.ComputerMove;
import com.vivianhonghoa.chess.model.players.ComputerStrategy;

import java.util.Random;

public class HeuristicStrategy implements ComputerStrategy {
    private final Random random = new Random();

    @Override
    public ComputerMove chooseMove(Board board, Piece.Color color) {
        ComputerMove bestMove = null;
        int bestScore = Integer.MIN_VALUE;
        Piece.Color opponent = (color == Piece.Color.WHITE) ? Piece.Color.BLACK : Piece.Color.WHITE;

        for (int r = 0; r < Board.SIZE; r++) {
            for (int c = 0; c < Board.SIZE; c++) {
                Piece piece = board.getPiece(r, c);
                if (piece == null || piece.getColor() != color) continue;

                Case from = new Case(r, c);
                for (Case to : board.getLegalMoves(piece)) {
                    int score = evaluateMove(board, piece, from, to, opponent);
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = new ComputerMove(from, to);
                    }
                }
            }
        }
        return bestMove;
    }

    private int evaluateMove(Board board, Piece piece, Case from, Case to, Piece.Color opponent) {
        int score = 0;

        // Captures: MVV-LVA (Most Valuable Victim - Least Valuable Attacker)
        Piece victim = board.getPiece(to.row(), to.col());
        if (victim != null) {
            score += pieceValue(victim) * 10 - pieceValue(piece);
        }

        // Check bonus
        boolean givesCheck = board.withSimulatedMove(from, to, () -> board.isKingInCheck(opponent));
        if (givesCheck) {
            score += 500;
        }

        // Center control bonus
        if ((to.row() == 3 || to.row() == 4) && (to.col() == 3 || to.col() == 4)) {
            score += 30;
        }

        // Danger penalty: moving to a square attacked by opponent
        if (board.isSquareAttackedBy(to.row(), to.col(), opponent)) {
            score -= pieceValue(piece) / 2;
        }

        // Pawn advancement bonus
        if (piece instanceof Pawn) {
            int advancement = (piece.getColor() == Piece.Color.WHITE) ? to.row() : (7 - to.row());
            score += advancement * 10;
        }

        // Small random factor to avoid predictability
        score += random.nextInt(10);

        return score;
    }

    static int pieceValue(Piece piece) {
        if (piece instanceof Pawn) return 100;
        if (piece instanceof Knight) return 320;
        if (piece instanceof Bishop) return 330;
        if (piece instanceof Rook) return 500;
        if (piece instanceof Queen) return 900;
        if (piece instanceof King) return 20000;
        return 0;
    }
}
