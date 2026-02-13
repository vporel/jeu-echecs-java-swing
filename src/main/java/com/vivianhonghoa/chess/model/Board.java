package com.vivianhonghoa.chess.model;

import com.vivianhonghoa.chess.model.pieces.*;

public class Board {
    public static final int TAILLE = 8;
    private Piece[][] pieces;

    public Board() {
        pieces = new Piece[TAILLE][TAILLE];
        initPositions();
    }

    private void initPositions() {
        // Pions blancs (row 2, index 1)
        for (int col = 0; col < TAILLE; col++) {
            pieces[1][col] = new Pawn(Piece.Color.BLANC);
        }

        // Pions noirs (row 7, index 6)
        for (int col = 0; col < TAILLE; col++) {
            pieces[6][col] = new Pawn(Piece.Color.NOIR);
        }

        // Pieces blanches (row 1, index 0)
        pieces[0][0] = new Rook(Piece.Color.BLANC);
        pieces[0][1] = new Knight(Piece.Color.BLANC);
        pieces[0][2] = new Bishop(Piece.Color.BLANC);
        pieces[0][3] = new King(Piece.Color.BLANC);
        pieces[0][4] = new Queen(Piece.Color.BLANC);
        pieces[0][5] = new Bishop(Piece.Color.BLANC);
        pieces[0][6] = new Knight(Piece.Color.BLANC);
        pieces[0][7] = new Rook(Piece.Color.BLANC);

        // Pieces noires (row 8, index 7)
        pieces[7][0] = new Rook(Piece.Color.NOIR);
        pieces[7][1] = new Knight(Piece.Color.NOIR);
        pieces[7][2] = new Bishop(Piece.Color.NOIR);
        pieces[7][3] = new King(Piece.Color.NOIR);
        pieces[7][4] = new Queen(Piece.Color.NOIR);
        pieces[7][5] = new Bishop(Piece.Color.NOIR);
        pieces[7][6] = new Knight(Piece.Color.NOIR);
        pieces[7][7] = new Rook(Piece.Color.NOIR);
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
