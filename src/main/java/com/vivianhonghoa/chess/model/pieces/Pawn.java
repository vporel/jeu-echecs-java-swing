package com.vivianhonghoa.chess.model.pieces;

import com.vivianhonghoa.chess.model.Case;
import com.vivianhonghoa.chess.model.engine.Board;
import com.vivianhonghoa.chess.model.engine.BoardHelper;
import com.vivianhonghoa.chess.model.pieces.accessiblecases.AccessibleCasesCalculator;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece{


    public Pawn(Color color) {
        super(color, (board, piece) -> {
            List<Case> cases = new ArrayList<>();
            int row = piece.getPosition().row();
            int col = piece.getPosition().col();

            // White pawns move up (+1), black pawns move down (-1)
            int direction;
            int startRow;
            if (piece.getColor() == Color.WHITE) {
                direction = 1;
                startRow = 1;
            } else {
                direction = -1;
                startRow = 6;
            }

            // One square forward
            int nextRow = row + direction;
            if (BoardHelper.isValid(nextRow, col) && BoardHelper.isCaseEmpty(board, nextRow, col)) {
                cases.add(new Case(nextRow, col));

                // Two squares forward (only from starting position)
                int twoAheadRow = row + 2 * direction;
                if (row == startRow && BoardHelper.isCaseEmpty(board, twoAheadRow, col)) {
                    cases.add(new Case(twoAheadRow, col));
                }
            }

            // Capture diagonally (normal + passing capture)
            Case passingCaptureTarget = board.getPassingCaptureTarget();
            for (int dc = -1; dc <= 1; dc += 2) {
                int targetCol = col + dc;
                if (BoardHelper.isValid(nextRow, targetCol)) {
                    Piece target = board.getPieceAt(nextRow, targetCol);
                    if (target != null && target.getColor() != piece.getColor()) {
                        cases.add(new Case(nextRow, targetCol));
                    } else if (passingCaptureTarget != null
                            && passingCaptureTarget.row() == nextRow
                            && passingCaptureTarget.col() == targetCol) {
                        cases.add(new Case(nextRow, targetCol));
                    }
                }
            }

            return cases;
        });
    }

    @Override
    public Type getType() {
        return Type.PAWN;
    }

    @Override
    public char getLetter() {
        return 'P';
    }
}
