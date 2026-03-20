package com.vivianhonghoa.chess.model.pieces.accessiblecases;

import com.vivianhonghoa.chess.model.Case;
import com.vivianhonghoa.chess.model.engine.Board;
import com.vivianhonghoa.chess.model.pieces.Piece;

import java.util.List;

public class AccessibleCasesDiagDecorator extends AccessibleCasesDecorator {

    public AccessibleCasesDiagDecorator(AccessibleCasesCalculator decorated) {
        super(decorated);
    }

    public AccessibleCasesDiagDecorator() {
        this(new BaseAccessibleCasesCalculator());
    }

    @Override
    public List<Case> getAccessibleCases(Board board, Piece piece) {
        List<Case> cases = super.getAccessibleCases(board, piece);

        // Add diagonal moves (1, 1), (1, -1), (-1, 1), (-1, -1)
        cases.addAll(getCasesInDirection(board, piece.getPosition(), 1, 1));
        cases.addAll(getCasesInDirection(board, piece.getPosition(), 1, -1));
        cases.addAll(getCasesInDirection(board, piece.getPosition(), -1, 1));
        cases.addAll(getCasesInDirection(board, piece.getPosition(), -1, -1));

        return cases;
    }
}
