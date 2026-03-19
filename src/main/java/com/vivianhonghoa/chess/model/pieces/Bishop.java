package com.vivianhonghoa.chess.model.pieces;

import com.vivianhonghoa.chess.model.engine.Case;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece{

    public Bishop(Color color) {
        super(color);
    }

    @Override
    public char getLetter() {
        return 'B';
    }

    @Override
    public List<Case> getAccessibleCases() {
        List<Case> cases = new ArrayList<>();

        // Move up-right
        List<Case> upRight = getCasesInDirection(1, 1);
        cases.addAll(upRight);

        // Move up-left
        List<Case> upLeft = getCasesInDirection(1, -1);
        cases.addAll(upLeft);

        // Move down-right
        List<Case> downRight = getCasesInDirection(-1, 1);
        cases.addAll(downRight);

        // Move down-left
        List<Case> downLeft = getCasesInDirection(-1, -1);
        cases.addAll(downLeft);

        return cases;
    }
}