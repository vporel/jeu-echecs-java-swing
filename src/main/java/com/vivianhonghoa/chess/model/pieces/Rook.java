package com.vivianhonghoa.chess.model.pieces;

import com.vivianhonghoa.chess.model.pieces.accessiblecases.AccessibleCasesHorizDecorator;
import com.vivianhonghoa.chess.model.pieces.accessiblecases.AccessibleCasesVertDecorator;

public class Rook extends Piece{

    public Rook(Color color) {
        super(color, new AccessibleCasesHorizDecorator(
            new AccessibleCasesVertDecorator()
        ));
    }

    @Override
    public Type getType() {
        return Type.ROOK;
    }

}
