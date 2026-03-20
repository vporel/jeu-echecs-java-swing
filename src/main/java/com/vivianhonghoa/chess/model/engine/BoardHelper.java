package com.vivianhonghoa.chess.model.engine;

import com.vivianhonghoa.chess.model.pieces.*;

import java.util.ArrayList;
import java.util.List;

public class BoardHelper {

    public static boolean hasPieceTypeAt(Board board, int row, int col, Piece.Type type) {
        Piece p = board.getPieceAt(row, col);
        return p != null && p.getType() == type;
    }

    public static List<Case> getCasesWithPieceType(Board board, Piece.Type type) {
        List<Case> cases = new ArrayList<>();
        for (int r = 0; r < Board.SIZE; r++) {
            for (int c = 0; c < Board.SIZE; c++) {
                if (hasPieceTypeAt(board, r, c, type)) {
                    cases.add(new Case(r, c));
                }
            }
        }
        return cases;
    }

    public static List<Case> getCasesWithPieceType(Board board, Piece.Type type, Piece.Color color) {
        List<Case> cases = new ArrayList<>();
        for (int r = 0; r < Board.SIZE; r++) {
            for (int c = 0; c < Board.SIZE; c++) {
                Piece p = board.getPieceAt(r, c);
                if (p != null && p.getType() == type && p.getColor() == color) {
                    cases.add(new Case(r, c));
                }
            }
        }
        return cases;
    }

    public static Case findKingPosition(Board board, Piece.Color color) {
        List<Case> kingCases = getCasesWithPieceType(board, Piece.Type.KING, color);
        return kingCases.isEmpty() ? null : kingCases.getFirst();
    }

    public static int countAttackers(Board board, int row, int col, Piece.Color opponent) {
        int count = 0;
        for (int r = 0; r < Board.SIZE; r++) {
            for (int c = 0; c < Board.SIZE; c++) {
                Piece p = board.getPieceAt(r, c);
                if (p != null && p.getColor() == opponent && p.canMoveTo(new Case(row, col))) {
                    count++;
                }
            }
        }
        return count;
    }

}
