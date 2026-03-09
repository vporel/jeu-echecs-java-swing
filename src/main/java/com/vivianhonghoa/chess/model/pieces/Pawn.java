package com.vivianhonghoa.chess.model.pieces;

import com.vivianhonghoa.chess.model.Board;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece{

    public Pawn(Color color) {
        super(color);
    }

    @Override
    public List<Board.Case> getAccessibleCases() {
        List<Board.Case> cases = new ArrayList<>();

        // White pawns move up (+1), black pawns move down (-1)
        int direction;
        int startRow;
        if (getColor() == Color.BLANC) {
            direction = 1;
            startRow = 1;
        } else {
            direction = -1;
            startRow = 6;
        }

        // One square forward
        int nextRow = row + direction;
        if (Board.Case.isValid(nextRow, col) && board.getPiece(nextRow, col) == null) {
            cases.add(new Board.Case(nextRow, col));

            // Two squares forward (only from starting position)
            int twoAheadRow = row + 2 * direction;
            if (row == startRow && board.getPiece(twoAheadRow, col) == null) {
                cases.add(new Board.Case(twoAheadRow, col));
            }
        }

        // Capture diagonally to the left
        int leftCol = col - 1;
        if (Board.Case.isValid(nextRow, leftCol)) {
            Piece target = board.getPiece(nextRow, leftCol);
            if (target != null && target.getColor() != getColor()) {
                cases.add(new Board.Case(nextRow, leftCol));
            }
        }

        // Capture diagonally to the right
        int rightCol = col + 1;
        if (Board.Case.isValid(nextRow, rightCol)) {
            Piece target = board.getPiece(nextRow, rightCol);
            if (target != null && target.getColor() != getColor()) {
                cases.add(new Board.Case(nextRow, rightCol));
            }
        }

        return cases;
    }
}
