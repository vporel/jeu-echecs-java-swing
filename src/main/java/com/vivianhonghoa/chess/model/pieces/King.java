package com.vivianhonghoa.chess.model.pieces;

import com.vivianhonghoa.chess.model.Board;
import com.vivianhonghoa.chess.model.Case;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece{

    public King(Color color) {
        super(color);
    }

    private void addIfAccessible(List<Case> cases, int r, int c) {
        if (Case.isValid(r, c)) {
            Piece target = board.getPiece(r, c);
            if (target == null || target.getColor() != getColor()) {
                cases.add(new Case(r, c));
            }
        }
    }

    @Override
    public List<Case> getAccessibleCases() {
        List<Case> cases = new ArrayList<>();

        // The king can move 1 square in any direction
        addIfAccessible(cases, row - 1, col - 1); // down-left
        addIfAccessible(cases, row - 1, col);      // down
        addIfAccessible(cases, row - 1, col + 1);  // down-right
        addIfAccessible(cases, row, col - 1);      // left
        addIfAccessible(cases, row, col + 1);      // right
        addIfAccessible(cases, row + 1, col - 1);  // up-left
        addIfAccessible(cases, row + 1, col);      // up
        addIfAccessible(cases, row + 1, col + 1);  // up-right

        return cases;
    }
}
