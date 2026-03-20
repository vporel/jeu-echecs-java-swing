package com.vivianhonghoa.chess.model.pieces.accessiblecases;

import com.vivianhonghoa.chess.model.Case;
import com.vivianhonghoa.chess.model.engine.Board;
import com.vivianhonghoa.chess.model.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete base calculator that returns an empty list.
 * Useful as the terminal/decorated component when composing decorators.
 */
public class BaseAccessibleCasesCalculator implements AccessibleCasesCalculator {

    @Override
    public List<Case> getAccessibleCases(Board board, Piece piece) {
        // Return a mutable empty list so decorators can add to it safely
        return new ArrayList<>();
    }
}
