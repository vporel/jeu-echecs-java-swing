package com.vivianhonghoa.chess.model.pieces;

import com.vivianhonghoa.chess.model.pieces.accessiblecases.AccessibleCasesDiagDecorator;

public class Bishop extends Piece{

    public Bishop(Color color) {
        super(color, new AccessibleCasesDiagDecorator());
    }

    @Override
    public Type getType() {
        return Type.BISHOP;
    }

}