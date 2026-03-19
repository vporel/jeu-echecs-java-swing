package com.vivianhonghoa.chess.model.pieces;

import com.vivianhonghoa.chess.model.engine.Case;

import java.util.List;

public class LoggingPiece extends PieceDecorator {

    public LoggingPiece(Piece wrapped) {
        super(wrapped);
    }

    @Override
    public List<Case> getAccessibleCases() {
        List<Case> cases = super.getAccessibleCases();
        System.out.printf("[Piece] %s at (%d,%d) has %d accessible cases%n",
                getType(), getRow(), getCol(), cases.size());
        return cases;
    }
}
