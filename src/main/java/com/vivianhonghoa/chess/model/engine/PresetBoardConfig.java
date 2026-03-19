package com.vivianhonghoa.chess.model.engine;

import com.vivianhonghoa.chess.model.pieces.*;

/**
 * Provides different configurations to help testing or presenting the game.
 * For example a board where a pawn is about to reach promotion, or a board where the king will be checkmated in the next move, etc.
 */
public class PresetBoardConfig {

    private static void place(Piece[][] pieces, Piece piece, int row, int col) {
        pieces[row][col] = piece;
        piece.setPosition(row, col);
    }

    /**
     * White pawn on row 6 (one step from promotion on row 7).
     * Both kings are present. Black has minimal pieces.
     */
    public static Piece[][] pawnReachesPromotion() {
        Piece[][] pieces = new Piece[Board.SIZE][Board.SIZE];

        place(pieces, new King(Piece.Color.WHITE), 0, 4);
        place(pieces, new King(Piece.Color.BLACK), 7, 4);
        place(pieces, new Pawn(Piece.Color.WHITE), 6, 3);

        return pieces;
    }

    /**
     * One move from checkmate: white plays Rook (0,7) → (7,7) to deliver back-rank mate.
     * The black king at (7,4) is blocked forward by its own pawns on row 6.
     * After white's move, r1 at (7,7) checks the king; r2 at (1,3) covers col 3 so (7,3) is also attacked.
     * The black king is NOT in check at the start of this preset.
     */
    public static Piece[][] checkMate() {
        Piece[][] pieces = new Piece[Board.SIZE][Board.SIZE];

        place(pieces, new King(Piece.Color.WHITE), 0, 4);
        place(pieces, new King(Piece.Color.BLACK), 7, 4);

        // Black pawns block all forward escape squares
        Pawn p1 = new Pawn(Piece.Color.BLACK); p1.setHasMoved(true);
        Pawn p2 = new Pawn(Piece.Color.BLACK); p2.setHasMoved(true);
        Pawn p3 = new Pawn(Piece.Color.BLACK); p3.setHasMoved(true);
        place(pieces, p1, 6, 3);
        place(pieces, p2, 6, 4);
        place(pieces, p3, 6, 5);

        // White rook at (0,7): white moves it to (7,7) → delivers check along row 7
        Rook r1 = new Rook(Piece.Color.WHITE); r1.setHasMoved(true);
        place(pieces, r1, 0, 7);

        // White rook at (1,3): covers col 3, so after r1 moves, (7,3) remains covered
        Rook r2 = new Rook(Piece.Color.WHITE); r2.setHasMoved(true);
        place(pieces, r2, 1, 3);

        return pieces;
    }

    /**
     * White king is in check from a black rook. White must move the king or block.
     */
    public static Piece[][] check() {
        Piece[][] pieces = new Piece[Board.SIZE][Board.SIZE];

        place(pieces, new King(Piece.Color.WHITE), 0, 4);
        place(pieces, new Rook(Piece.Color.BLACK), 0, 7);
        place(pieces, new King(Piece.Color.BLACK), 7, 4);

        return pieces;
    }

    /**
     * Both sides have their king and rooks unmoved, ready to castle.
     */
    public static Piece[][] castling() {
        Piece[][] pieces = new Piece[Board.SIZE][Board.SIZE];

        place(pieces, new King(Piece.Color.WHITE), 0, 4);
        place(pieces, new Rook(Piece.Color.WHITE), 0, 0);
        place(pieces, new Rook(Piece.Color.WHITE), 0, 7);

        place(pieces, new King(Piece.Color.BLACK), 7, 4);
        place(pieces, new Rook(Piece.Color.BLACK), 7, 0);
        place(pieces, new Rook(Piece.Color.BLACK), 7, 7);

        return pieces;
    }

    /**
     * En passant scenario: a black pawn has just moved two squares and is
     * sitting next to a white pawn, which can capture it en passant.
     */
    public static Piece[][] enPassant() {
        Piece[][] pieces = new Piece[Board.SIZE][Board.SIZE];

        place(pieces, new King(Piece.Color.WHITE), 0, 4);
        place(pieces, new King(Piece.Color.BLACK), 7, 4);

        Pawn whitePawn = new Pawn(Piece.Color.WHITE); whitePawn.setHasMoved(true);
        place(pieces, whitePawn, 4, 3);

        Pawn blackPawn = new Pawn(Piece.Color.BLACK); blackPawn.setHasMoved(true);
        place(pieces, blackPawn, 4, 4);

        return pieces;
    }
}
