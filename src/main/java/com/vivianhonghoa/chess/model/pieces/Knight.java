package com.vivianhonghoa.chess.model.pieces;

import com.vivianhonghoa.chess.model.engine.Case;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece{

    public Knight(Color color) {
        super(color);
    }

    @Override
    public PieceType getType() {
        return PieceType.KNIGHT;
    }

    @Override
    public char getLetter() {
        return 'N';
    }

    @Override
    public List<Case> getAccessibleCases() {
        List<Case> cases = new ArrayList<>();

        // The knight moves in an "L" shape: 2 squares + 1 square
        addIfAccessible(cases, row - 2, col - 1);
        addIfAccessible(cases, row - 2, col + 1);
        addIfAccessible(cases, row - 1, col - 2);
        addIfAccessible(cases, row - 1, col + 2);
        addIfAccessible(cases, row + 1, col - 2);
        addIfAccessible(cases, row + 1, col + 2);
        addIfAccessible(cases, row + 2, col - 1);
        addIfAccessible(cases, row + 2, col + 1);

        return cases;
    }
}
