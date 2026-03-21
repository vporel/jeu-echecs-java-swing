package com.vivianhonghoa.chess.model.pieces.accessiblecases;

import com.vivianhonghoa.chess.model.Case;
import com.vivianhonghoa.chess.model.engine.Board;
import com.vivianhonghoa.chess.model.pieces.Piece;
import com.vivianhonghoa.chess.model.pieces.PieceUtils;

import java.util.ArrayList;
import java.util.List;

public class KnightAccessibleCasesCalculator implements AccessibleCasesCalculator {

    @Override
    public List<Case> getAccessibleCases(Board board, Piece piece) {
        List<Case> cases = new ArrayList<>();
        int row = piece.getPosition().row();
        int col = piece.getPosition().col();

        PieceUtils.addIfAccessible(board, piece, cases, new Case(row - 2, col - 1));
        PieceUtils.addIfAccessible(board, piece, cases, new Case(row - 2, col + 1));
        PieceUtils.addIfAccessible(board, piece, cases, new Case(row - 1, col - 2));
        PieceUtils.addIfAccessible(board, piece, cases, new Case(row - 1, col + 2));
        PieceUtils.addIfAccessible(board, piece, cases, new Case(row + 1, col - 2));
        PieceUtils.addIfAccessible(board, piece, cases, new Case(row + 1, col + 2));
        PieceUtils.addIfAccessible(board, piece, cases, new Case(row + 2, col - 1));
        PieceUtils.addIfAccessible(board, piece, cases, new Case(row + 2, col + 1));

        return cases;
    }
}
