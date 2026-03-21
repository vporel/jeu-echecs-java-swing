package com.vivianhonghoa.chess.model.pieces;

import com.vivianhonghoa.chess.model.pieces.accessiblecases.KingAccessibleCasesCalculator;

public class King extends Piece {

    public King(Color color) {
        super(color, new KingAccessibleCasesCalculator());
    }

    @Override
    public Type getType() {
        return Type.KING;
    }

}
