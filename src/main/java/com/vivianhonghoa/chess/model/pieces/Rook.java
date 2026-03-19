package com.vivianhonghoa.chess.model.pieces;

import com.vivianhonghoa.chess.model.engine.Case;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece{

    public Rook(Color color) {
        super(color);
    }

    @Override
    public PieceType getType() {
        return PieceType.ROOK;
    }

    @Override
    public char getLetter() {
        return 'R';
    }

    @Override
    public List<Case> getAccessibleCases() {

        // Move up
        List<Case> up = getCasesInDirection(1, 0);
        List<Case> cases = new ArrayList<>(up);

        // Move down
        List<Case> down = getCasesInDirection(-1, 0);
        cases.addAll(down);

        // Move right
        List<Case> right = getCasesInDirection(0, 1);
        cases.addAll(right);

        // Move left
        List<Case> left = getCasesInDirection(0, -1);
        cases.addAll(left);

        return cases;
    }
}
