package com.vivianhonghoa.chess.model.pieces;

import com.vivianhonghoa.chess.model.Case;
import com.vivianhonghoa.chess.model.engine.Board;
import com.vivianhonghoa.chess.model.pieces.accessiblecases.AccessibleCasesCalculator;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece{

    public Knight(Color color) {
        super(color, (board, piece) -> {
            List<Case> cases = new ArrayList<>();
            int row = piece.getPosition().row();
            int col = piece.getPosition().col();

            // The knight moves in an "L" shape: 2 squares + 1 square
            PieceUtils.addIfAccessible(board, piece, cases, new Case(row - 2, col - 1));
            PieceUtils.addIfAccessible(board, piece, cases, new Case(row - 2, col + 1));
            PieceUtils.addIfAccessible(board, piece, cases, new Case(row - 1, col - 2));
            PieceUtils.addIfAccessible(board, piece, cases, new Case(row - 1, col + 2));
            PieceUtils.addIfAccessible(board, piece, cases, new Case(row + 1, col - 2));
            PieceUtils.addIfAccessible(board, piece, cases, new Case(row + 1, col + 2));
            PieceUtils.addIfAccessible(board, piece, cases, new Case(row + 2, col - 1));
            PieceUtils.addIfAccessible(board, piece, cases, new Case(row + 2, col + 1));

            return cases;
        });
    }

    @Override
    public Type getType() {
        return Type.KNIGHT;
    }

    @Override
    public char getLetter() {
        return 'N';
    }
}
