package com.vivianhonghoa.chess.model.pieces.accessiblecases;

import com.vivianhonghoa.chess.model.Case;
import com.vivianhonghoa.chess.model.engine.Board;
import com.vivianhonghoa.chess.model.pieces.Piece;

import java.util.List;

public class AccessibleCasesHorizDecorator extends AccessibleCasesDecorator {

    public AccessibleCasesHorizDecorator(AccessibleCasesCalculator decorated) {
        super(decorated);
    }

    public AccessibleCasesHorizDecorator() {
        this(new BaseAccessibleCasesCalculator());
    }

    @Override
    public List<Case> getAccessibleCases(Board board, Piece piece) {
        List<Case> cases = super.getAccessibleCases(board, piece);

        // Add horizontal moves (like a Rook)
        cases.addAll(getCasesInDirection(board, piece.getPosition(), 0, 1)); // Move right
        cases.addAll(getCasesInDirection(board, piece.getPosition(), 0, -1)); // Move left

        return cases;
    }
}
