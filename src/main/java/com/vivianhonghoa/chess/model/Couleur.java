package com.vivianhonghoa.chess.model;

public enum Couleur {
    BLANC,
    NOIR;

    public Couleur opposee() {
        return this == BLANC ? NOIR : BLANC;
    }
}
