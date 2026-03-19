package com.vivianhonghoa.chess.model.pieces;

import com.vivianhonghoa.chess.model.engine.Case;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece{

    public Queen(Color color) {
        super(color);
    }

    @Override
    public PieceType getType() {
        return PieceType.QUEEN;
    }

    @Override
    public char getLetter() {
        return 'Q';
    }

    @Override
    public List<Case> getAccessibleCases() {
        List<Case> cases = new ArrayList<>();

        // Straight directions (like a rook)
        cases.addAll(getCasesInDirection(1, 0));   // up
        cases.addAll(getCasesInDirection(-1, 0));  // down
        cases.addAll(getCasesInDirection(0, 1));   // right
        cases.addAll(getCasesInDirection(0, -1));  // left

        // Diagonal directions (like a bishop)
        cases.addAll(getCasesInDirection(1, 1));   // up-right
        cases.addAll(getCasesInDirection(1, -1));  // up-left
        cases.addAll(getCasesInDirection(-1, 1));  // down-right
        cases.addAll(getCasesInDirection(-1, -1)); // down-left

        return cases;
    }
}
