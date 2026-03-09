package com.vivianhonghoa.chess.model.pieces;

import com.vivianhonghoa.chess.model.Board;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece{

    public Rook(Color color) {
        super(color);
    }

    @Override
    public List<Board.Case> getAccessibleCases() {

        // Move up
        List<Board.Case> up = getCasesInDirection(1, 0);
        List<Board.Case> cases = new ArrayList<>(up);

        // Move down
        List<Board.Case> down = getCasesInDirection(-1, 0);
        cases.addAll(down);

        // Move right
        List<Board.Case> right = getCasesInDirection(0, 1);
        cases.addAll(right);

        // Move left
        List<Board.Case> left = getCasesInDirection(0, -1);
        cases.addAll(left);

        return cases;
    }
}
