package com.vivianhonghoa.chess.model.pieces.accessiblecases;

import com.vivianhonghoa.chess.model.Case;
import com.vivianhonghoa.chess.model.engine.Board;
import com.vivianhonghoa.chess.model.pieces.Piece;

import java.util.List;

public class AccessibleCasesVertDecorator extends AccessibleCasesDecorator {

    public AccessibleCasesVertDecorator(AccessibleCasesCalculator decorated) {
        super(decorated);
    }

    public AccessibleCasesVertDecorator() {
        this(new BaseAccessibleCasesCalculator());
    }

    @Override
    public List<Case> getAccessibleCases(Board board, Piece piece) {
        List<Case> cases = super.getAccessibleCases(board, piece);

        // Add vertical moves (like a Rook)
        cases.addAll(getCasesInDirection(board, piece.getPosition(), 1, 0)); // Move up
        cases.addAll(getCasesInDirection(board, piece.getPosition(), -1, 0)); // Move down

        return cases;
    }
}
