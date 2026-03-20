package com.vivianhonghoa.chess.model.pieces;

import com.vivianhonghoa.chess.model.pieces.accessiblecases.AccessibleCasesCalculator;
import com.vivianhonghoa.chess.model.pieces.accessiblecases.AccessibleCasesDiagDecorator;
import com.vivianhonghoa.chess.model.pieces.accessiblecases.AccessibleCasesHorizDecorator;
import com.vivianhonghoa.chess.model.pieces.accessiblecases.AccessibleCasesVertDecorator;

public class Queen extends Piece{

    public Queen(Color color) {
        super(color, new AccessibleCasesHorizDecorator(
            new AccessibleCasesVertDecorator(
                new AccessibleCasesDiagDecorator()
            )
        ));
    }

    @Override
    public Type getType() {
        return Type.QUEEN;
    }

    @Override
    public char getLetter() {
        return 'Q';
    }
}
