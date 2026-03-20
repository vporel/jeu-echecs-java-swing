package com.vivianhonghoa.chess.model.engine;

import com.vivianhonghoa.chess.model.Case;
import com.vivianhonghoa.chess.model.pieces.*;

import java.util.ArrayList;
import java.util.List;

public class BoardHelper {

    public static boolean isValid(int row, int col) {
        return row >= 0 && row < Board.SIZE && col >= 0 && col < Board.SIZE;
    }

    public static boolean isValid(Case c) {
        return isValid(c.row(), c.col());
    }

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


    public static boolean isKingInCheck(Board board, Piece.Color color) {
        Case kingCase = findKingPosition(board, color);
        if (kingCase == null) return false;
        Piece.Color opponent = (color == Piece.Color.WHITE) ? Piece.Color.BLACK : Piece.Color.WHITE;
        return isSquareAttackedBy(board, kingCase.row(), kingCase.col(), opponent);
    }

    public static boolean isKingInCheckmate(Board board, Piece.Color color) {
        if (!isKingInCheck(board, color)) return false;
        Case kingCase = findKingPosition(board, color);
        if (kingCase == null) return false;
        Piece king = board.getPieceAt(kingCase);
        for (Case move : king.getAccessibleCases()) {
            if (board.isMoveLegal(kingCase, move)) {
                return false; // King can escape
            }
        }
        for (int r = 0; r < Board.SIZE; r++) {
            for (int c = 0; c < Board.SIZE; c++) {
                Piece p = board.getPieceAt(r, c);
                if (p != null && p.getColor() == color) {
                    for (Case move : p.getAccessibleCases()) {
                        if (board.isMoveLegal(new Case(r, c), move)) {
                            return false; // A piece can block or capture
                        }
                    }
                }
            }
        }
        return true; // No escape, it's checkmate
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

    public static boolean isSquareAttackedBy(Board board, int row, int col, Piece.Color attackerColor) {
        for (int r = 0; r < Board.SIZE; r++) {
            for (int c = 0; c < Board.SIZE; c++) {
                Piece p = board.getPieceAt(r, c);
                if (p != null && p.getColor() == attackerColor && canPieceAttack(board, p, r, c, row, col)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean canPieceAttack(Board board, Piece piece, int fromRow, int fromCol, int toRow, int toCol) {
        int dr = toRow - fromRow;
        int dc = toCol - fromCol;
        int absDr = Math.abs(dr);
        int absDc = Math.abs(dc);

        return switch (piece.getType()) {
            case PAWN -> {
                int direction = (piece.getColor() == Piece.Color.WHITE) ? 1 : -1;
                yield dr == direction && absDc == 1;
            }
            case KNIGHT -> (absDr == 2 && absDc == 1) || (absDr == 1 && absDc == 2);
            case KING -> absDr <= 1 && absDc <= 1 && (absDr + absDc > 0);
            case ROOK -> (dr == 0 || dc == 0) && isPathClear(board, fromRow, fromCol, toRow, toCol);
            case BISHOP -> absDr == absDc && absDr > 0 && isPathClear(board, fromRow, fromCol, toRow, toCol);
            case QUEEN -> ((dr == 0 || dc == 0) || (absDr == absDc && absDr > 0))
                    && isPathClear(board, fromRow, fromCol, toRow, toCol);
        };
    }

    private static boolean isPathClear(Board board, int fromRow, int fromCol, int toRow, int toCol) {
        int dr = Integer.signum(toRow - fromRow);
        int dc = Integer.signum(toCol - fromCol);
        int r = fromRow + dr;
        int c = fromCol + dc;
        while (r != toRow || c != toCol) {
            if (board.getPieceAt(r, c) != null) return false;
            r += dr;
            c += dc;
        }
        return true;
    }

    public static boolean isStalemate(Board board, Piece.Color color) {
        if (BoardHelper.isKingInCheck(board, color)) return false;
        for (int r = 0; r < Board.SIZE; r++) {
            for (int c = 0; c < Board.SIZE; c++) {
                Piece p = board.getPieceAt(r, c);
                if (p != null && p.getColor() == color) {
                    for (Case move : p.getAccessibleCases()) {
                        if (board.isMoveLegal(new Case(r, c), move)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

}
