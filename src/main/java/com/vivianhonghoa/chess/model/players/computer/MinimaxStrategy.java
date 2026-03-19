package com.vivianhonghoa.chess.model.players.computer;

import com.vivianhonghoa.chess.model.engine.Board;
import com.vivianhonghoa.chess.model.engine.Case;
import com.vivianhonghoa.chess.model.pieces.*;
import com.vivianhonghoa.chess.model.players.ComputerMove;
import com.vivianhonghoa.chess.model.players.ComputerStrategy;

import java.util.ArrayList;
import java.util.List;

public class MinimaxStrategy implements ComputerStrategy {
    private static final int MAX_DEPTH = 3;

    // Piece-square tables for positional evaluation (from White's perspective, row 0 = rank 1)
    private static final int[][] PAWN_TABLE = {
        {  0,  0,  0,  0,  0,  0,  0,  0},
        {  5, 10, 10,-20,-20, 10, 10,  5},
        {  5, -5,-10,  0,  0,-10, -5,  5},
        {  0,  0,  0, 20, 20,  0,  0,  0},
        {  5,  5, 10, 25, 25, 10,  5,  5},
        { 10, 10, 20, 30, 30, 20, 10, 10},
        { 50, 50, 50, 50, 50, 50, 50, 50},
        {  0,  0,  0,  0,  0,  0,  0,  0}
    };

    private static final int[][] KNIGHT_TABLE = {
        {-50,-40,-30,-30,-30,-30,-40,-50},
        {-40,-20,  0,  5,  5,  0,-20,-40},
        {-30,  5, 10, 15, 15, 10,  5,-30},
        {-30,  0, 15, 20, 20, 15,  0,-30},
        {-30,  5, 15, 20, 20, 15,  5,-30},
        {-30,  0, 10, 15, 15, 10,  0,-30},
        {-40,-20,  0,  0,  0,  0,-20,-40},
        {-50,-40,-30,-30,-30,-30,-40,-50}
    };

    private static final int[][] BISHOP_TABLE = {
        {-20,-10,-10,-10,-10,-10,-10,-20},
        {-10,  5,  0,  0,  0,  0,  5,-10},
        {-10, 10, 10, 10, 10, 10, 10,-10},
        {-10,  0, 10, 10, 10, 10,  0,-10},
        {-10,  5,  5, 10, 10,  5,  5,-10},
        {-10,  0,  5, 10, 10,  5,  0,-10},
        {-10,  0,  0,  0,  0,  0,  0,-10},
        {-20,-10,-10,-10,-10,-10,-10,-20}
    };

    private static final int[][] ROOK_TABLE = {
        {  0,  0,  0,  5,  5,  0,  0,  0},
        { -5,  0,  0,  0,  0,  0,  0, -5},
        { -5,  0,  0,  0,  0,  0,  0, -5},
        { -5,  0,  0,  0,  0,  0,  0, -5},
        { -5,  0,  0,  0,  0,  0,  0, -5},
        { -5,  0,  0,  0,  0,  0,  0, -5},
        {  5, 10, 10, 10, 10, 10, 10,  5},
        {  0,  0,  0,  0,  0,  0,  0,  0}
    };

    private static final int[][] QUEEN_TABLE = {
        {-20,-10,-10, -5, -5,-10,-10,-20},
        {-10,  0,  5,  0,  0,  0,  0,-10},
        {-10,  5,  5,  5,  5,  5,  0,-10},
        {  0,  0,  5,  5,  5,  5,  0, -5},
        { -5,  0,  5,  5,  5,  5,  0, -5},
        {-10,  0,  5,  5,  5,  5,  0,-10},
        {-10,  0,  0,  0,  0,  0,  0,-10},
        {-20,-10,-10, -5, -5,-10,-10,-20}
    };

    private static final int[][] KING_TABLE = {
        { 20, 30, 10,  0,  0, 10, 30, 20},
        { 20, 20,  0,  0,  0,  0, 20, 20},
        {-10,-20,-20,-20,-20,-20,-20,-10},
        {-20,-30,-30,-40,-40,-30,-30,-20},
        {-30,-40,-40,-50,-50,-40,-40,-30},
        {-30,-40,-40,-50,-50,-40,-40,-30},
        {-30,-40,-40,-50,-50,-40,-40,-30},
        {-30,-40,-40,-50,-50,-40,-40,-30}
    };

    @Override
    public ComputerMove chooseMove(Board board, Piece.Color color) {
        ComputerMove bestMove = null;
        int bestScore = Integer.MIN_VALUE;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        List<ComputerMove> moves = generateAllMoves(board, color);
        orderMoves(moves, board);

        Piece.Color opponent = opponentOf(color);
        for (ComputerMove move : moves) {
            final int a = alpha;
            final int b = beta;
            int score = board.withSimulatedMove(move.from(), move.to(),
                () -> -negamax(board, MAX_DEPTH - 1, -b, -a, opponent));

            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
            alpha = Math.max(alpha, score);
        }
        return bestMove;
    }

    private int negamax(Board board, int depth, int alpha, int beta, Piece.Color color) {
        if (depth == 0) {
            return evaluateBoard(board, color);
        }

        List<ComputerMove> moves = generateAllMoves(board, color);

        if (moves.isEmpty()) {
            if (board.isKingInCheck(color)) {
                return -100000 - depth; // Checkmate: worse the earlier
            }
            return 0; // Stalemate
        }

        orderMoves(moves, board);

        Piece.Color opponent = opponentOf(color);
        int bestScore = Integer.MIN_VALUE;
        for (ComputerMove move : moves) {
            final int a = alpha;
            final int b = beta;
            int score = board.withSimulatedMove(move.from(), move.to(),
                () -> -negamax(board, depth - 1, -b, -a, opponent));

            bestScore = Math.max(bestScore, score);
            alpha = Math.max(alpha, score);
            if (alpha >= beta) {
                break; // Beta cutoff
            }
        }
        return bestScore;
    }

    private int evaluateBoard(Board board, Piece.Color color) {
        int score = 0;
        Piece.Color opponent = opponentOf(color);

        for (int r = 0; r < Board.SIZE; r++) {
            for (int c = 0; c < Board.SIZE; c++) {
                Piece piece = board.getPieceAt(r, c);
                if (piece == null) continue;

                int value = pieceValue(piece) + getPieceSquareValue(piece, r, c);

                if (piece.getColor() == color) {
                    score += value;
                } else {
                    score -= value;
                }
            }
        }

        if (board.isKingInCheck(opponent)) score += 50;
        if (board.isKingInCheck(color)) score -= 50;

        return score;
    }

    private static int getPieceSquareValue(Piece piece, int row, int col) {
        int tableRow = (piece.getColor() == Piece.Color.WHITE) ? row : (7 - row);
        int[][] table = getPieceTable(piece);
        return (table != null) ? table[tableRow][col] : 0;
    }

    private static int[][] getPieceTable(Piece piece) {
        if (piece instanceof Pawn) return PAWN_TABLE;
        if (piece instanceof Knight) return KNIGHT_TABLE;
        if (piece instanceof Bishop) return BISHOP_TABLE;
        if (piece instanceof Rook) return ROOK_TABLE;
        if (piece instanceof Queen) return QUEEN_TABLE;
        if (piece instanceof King) return KING_TABLE;
        return null;
    }

    private static int pieceValue(Piece piece) {
        if (piece instanceof Pawn) return 100;
        if (piece instanceof Knight) return 320;
        if (piece instanceof Bishop) return 330;
        if (piece instanceof Rook) return 500;
        if (piece instanceof Queen) return 900;
        if (piece instanceof King) return 20000;
        return 0;
    }

    private void orderMoves(List<ComputerMove> moves, Board board) {
        moves.sort((a, b) -> {
            Piece victimA = board.getPieceAt(a.to().row(), a.to().col());
            Piece victimB = board.getPieceAt(b.to().row(), b.to().col());
            int scoreA = victimA != null ? pieceValue(victimA) : 0;
            int scoreB = victimB != null ? pieceValue(victimB) : 0;
            return scoreB - scoreA;
        });
    }

    private List<ComputerMove> generateAllMoves(Board board, Piece.Color color) {
        List<ComputerMove> moves = new ArrayList<>();
        for (int r = 0; r < Board.SIZE; r++) {
            for (int c = 0; c < Board.SIZE; c++) {
                Piece piece = board.getPieceAt(r, c);
                if (piece != null && piece.getColor() == color) {
                    Case from = new Case(r, c);
                    for (Case to : board.getLegalMoves(piece)) {
                        moves.add(new ComputerMove(from, to));
                    }
                }
            }
        }
        return moves;
    }

    private static Piece.Color opponentOf(Piece.Color color) {
        return (color == Piece.Color.WHITE) ? Piece.Color.BLACK : Piece.Color.WHITE;
    }
}
