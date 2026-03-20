package com.vivianhonghoa.chess.model.pieces;

import com.vivianhonghoa.chess.model.Case;
import com.vivianhonghoa.chess.model.pieces.accessiblecases.AccessibleCasesCalculator;
import com.vivianhonghoa.chess.model.pieces.accessiblecases.AccessibleCasesDiagDecorator;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece{

    public Bishop(Color color) {
        super(color, new AccessibleCasesDiagDecorator());
    }

    @Override
    public Type getType() {
        return Type.BISHOP;
    }

    @Override
    public char getLetter() {
        return 'B';
    }
}