package com.vivianhonghoa.chess.model;

public class Piece {
    private final Couleur couleur;
    private final TypePiece type;

    public Piece(Couleur couleur, TypePiece type) {
        this.couleur = couleur;
        this.type = type;
    }

    public Couleur getCouleur() {
        return couleur;
    }

    public TypePiece getType() {
        return type;
    }

    @Override
    public String toString() {
        String symbol = "";
        switch (type) {
            case ROI: symbol = "R"; break;
            case DAME: symbol = "D"; break;
            case TOUR: symbol = "T"; break;
            case FOU: symbol = "F"; break;
            case CAVALIER: symbol = "C"; break;
            case PION: symbol = "P"; break;
        }
        return couleur == Couleur.BLANC ? symbol : symbol.toLowerCase();
    }
}
