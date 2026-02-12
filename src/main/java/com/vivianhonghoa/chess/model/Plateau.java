package com.vivianhonghoa.chess.model;

public class Plateau {
    private static final int TAILLE = 8;
    private Piece[][] cases;

    public Plateau() {
        cases = new Piece[TAILLE][TAILLE];
        initialiserPositionDepart();
    }

    private void initialiserPositionDepart() {
        // Pions blancs (row 2, index 1)
        for (int col = 0; col < TAILLE; col++) {
            cases[1][col] = new Piece(Couleur.BLANC, TypePiece.PION);
        }

        // Pions noirs (row 7, index 6)
        for (int col = 0; col < TAILLE; col++) {
            cases[6][col] = new Piece(Couleur.NOIR, TypePiece.PION);
        }

        // Pieces blanches (row 1, index 0)
        cases[0][0] = new Piece(Couleur.BLANC, TypePiece.TOUR);
        cases[0][1] = new Piece(Couleur.BLANC, TypePiece.CAVALIER);
        cases[0][2] = new Piece(Couleur.BLANC, TypePiece.FOU);
        cases[0][3] = new Piece(Couleur.BLANC, TypePiece.DAME);
        cases[0][4] = new Piece(Couleur.BLANC, TypePiece.ROI);
        cases[0][5] = new Piece(Couleur.BLANC, TypePiece.FOU);
        cases[0][6] = new Piece(Couleur.BLANC, TypePiece.CAVALIER);
        cases[0][7] = new Piece(Couleur.BLANC, TypePiece.TOUR);

        // Pieces noires (row 8, index 7)
        cases[7][0] = new Piece(Couleur.NOIR, TypePiece.TOUR);
        cases[7][1] = new Piece(Couleur.NOIR, TypePiece.CAVALIER);
        cases[7][2] = new Piece(Couleur.NOIR, TypePiece.FOU);
        cases[7][3] = new Piece(Couleur.NOIR, TypePiece.DAME);
        cases[7][4] = new Piece(Couleur.NOIR, TypePiece.ROI);
        cases[7][5] = new Piece(Couleur.NOIR, TypePiece.FOU);
        cases[7][6] = new Piece(Couleur.NOIR, TypePiece.CAVALIER);
        cases[7][7] = new Piece(Couleur.NOIR, TypePiece.TOUR);
    }

    public Piece getPiece(int row, int col) {
        if (!estDansLimites(row, col)) {
            return null;
        }
        return cases[row][col];
    }

    public void setPiece(int row, int col, Piece piece) {
        if (estDansLimites(row, col)) {
            cases[row][col] = piece;
        }
    }

    public boolean estDansLimites(int row, int col) {
        return row >= 0 && row < TAILLE && col >= 0 && col < TAILLE;
    }

    public static int getTaille() {
        return TAILLE;
    }
}
