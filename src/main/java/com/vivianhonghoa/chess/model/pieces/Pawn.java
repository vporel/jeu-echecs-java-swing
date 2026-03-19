package com.vivianhonghoa.chess.model.pieces;

import com.vivianhonghoa.chess.model.engine.Case;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece{

    public Pawn(Color color) {
        super(color);
    }

    @Override
    public char getLetter() {
        return 'P';
    }

    @Override
    public List<Case> getAccessibleCases() {
        List<Case> cases = new ArrayList<>();

        // White pawns move up (+1), black pawns move down (-1)
        int direction;
        int startRow;
        if (getColor() == Color.WHITE) {
            direction = 1;
            startRow = 1;
        } else {
            direction = -1;
            startRow = 6;
        }

        // One square forward
        int nextRow = row + direction;
        if (Case.isValid(nextRow, col) && board.getPieceAt(nextRow, col) == null) {
            cases.add(new Case(nextRow, col));

            // Two squares forward (only from starting position)
            int twoAheadRow = row + 2 * direction;
            if (row == startRow && board.getPieceAt(twoAheadRow, col) == null) {
                cases.add(new Case(twoAheadRow, col));
            }
        }

        // Capture diagonally (normal + passing capture)
        Case passingCaptureTarget = board.getPassingCaptureTarget();
        for (int dc = -1; dc <= 1; dc += 2) {
            int targetCol = col + dc;
            if (Case.isValid(nextRow, targetCol)) {
                Piece target = board.getPieceAt(nextRow, targetCol);
                if (target != null && target.getColor() != getColor()) {
                    cases.add(new Case(nextRow, targetCol));
                } else if (passingCaptureTarget != null
                        && passingCaptureTarget.row() == nextRow
                        && passingCaptureTarget.col() == targetCol) {
                    cases.add(new Case(nextRow, targetCol));
                }
            }
        }

        return cases;
    }
}
