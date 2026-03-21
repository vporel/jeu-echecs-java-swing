package com.vivianhonghoa.chess.model.pieces;

import com.vivianhonghoa.chess.model.pieces.accessiblecases.KnightAccessibleCasesCalculator;

public class Knight extends Piece {

    public Knight(Color color) {
        super(color, new KnightAccessibleCasesCalculator());
    }

    @Override
    public Type getType() {
        return Type.KNIGHT;
    }

}
