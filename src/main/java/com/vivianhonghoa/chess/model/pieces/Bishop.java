package com.vivianhonghoa.chess.model.pieces;

import com.vivianhonghoa.chess.model.Board;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece{

    public Bishop(Color color) {
        super(color);
    }

    @Override
    public List<Board.Case> getAccessibleCases() {
        List<Board.Case> cases = new ArrayList<>();

        // Move up-right
        List<Board.Case> upRight = getCasesInDirection(1, 1);
        cases.addAll(upRight);

        // Move up-left
        List<Board.Case> upLeft = getCasesInDirection(1, -1);
        cases.addAll(upLeft);

        // Move down-right
        List<Board.Case> downRight = getCasesInDirection(-1, 1);
        cases.addAll(downRight);

        // Move down-left
        List<Board.Case> downLeft = getCasesInDirection(-1, -1);
        cases.addAll(downLeft);

        return cases;
    }
}