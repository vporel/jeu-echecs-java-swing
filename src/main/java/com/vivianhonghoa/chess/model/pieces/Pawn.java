package com.vivianhonghoa.chess.model.pieces;

import com.vivianhonghoa.chess.model.pieces.accessiblecases.PawnAccessibleCasesCalculator;

public class Pawn extends Piece {

    public Pawn(Color color) {
        super(color, new PawnAccessibleCasesCalculator());
    }

    @Override
    public Type getType() {
        return Type.PAWN;
    }

}
