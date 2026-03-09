package com.vivianhonghoa.chess.model;

import com.vivianhonghoa.chess.model.pieces.*;

public class Board {
    public static final int TAILLE = 8;
    private Piece[][] pieces;

    public Board() {
        pieces = new Piece[TAILLE][TAILLE];
        initPositions();
    }

    private void placePiece(Piece piece, int row, int col) {
        pieces[row][col] = piece;
        piece.setPosition(this, row, col);
    }

    private void initPositions() {
        // White pawns (row 2, index 1)
        for (int col = 0; col < TAILLE; col++) {
            placePiece(new Pawn(Piece.Color.BLANC), 1, col);
        }

        // Black pawns (row 7, index 6)
        for (int col = 0; col < TAILLE; col++) {
            placePiece(new Pawn(Piece.Color.NOIR), 6, col);
        }

        // White pieces (row 1, index 0)
        placePiece(new Rook(Piece.Color.BLANC), 0, 0);
        placePiece(new Knight(Piece.Color.BLANC), 0, 1);
        placePiece(new Bishop(Piece.Color.BLANC), 0, 2);
        placePiece(new King(Piece.Color.BLANC), 0, 3);
        placePiece(new Queen(Piece.Color.BLANC), 0, 4);
        placePiece(new Bishop(Piece.Color.BLANC), 0, 5);
        placePiece(new Knight(Piece.Color.BLANC), 0, 6);
        placePiece(new Rook(Piece.Color.BLANC), 0, 7);

        // Black pieces (row 8, index 7)
        placePiece(new Rook(Piece.Color.NOIR), 7, 0);
        placePiece(new Knight(Piece.Color.NOIR), 7, 1);
        placePiece(new Bishop(Piece.Color.NOIR), 7, 2);
        placePiece(new King(Piece.Color.NOIR), 7, 3);
        placePiece(new Queen(Piece.Color.NOIR), 7, 4);
        placePiece(new Bishop(Piece.Color.NOIR), 7, 5);
        placePiece(new Knight(Piece.Color.NOIR), 7, 6);
        placePiece(new Rook(Piece.Color.NOIR), 7, 7);
    }

    public Piece getPiece(int row, int col) {
        if (!Case.isValid(row, col)) {
            return null;
        }
        return pieces[row][col];
    }

    public record Case(
            int row,
            int col
    ){
        boolean isValid() {
            return Case.isValid(row, col);
        }

        public static boolean isValid(int row, int col) {
            return row >= 0 && row < Board.TAILLE && col >= 0 && col < Board.TAILLE;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Case aCase = (Case) o;
            return row == aCase.row && col == aCase.col;
        }

        @Override
        public String toString() {
            return "Position[" + row + "," + col + "]";
        }
    }
}
