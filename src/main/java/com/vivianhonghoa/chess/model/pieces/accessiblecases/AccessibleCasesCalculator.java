package com.vivianhonghoa.chess.model.pieces.accessiblecases;

import com.vivianhonghoa.chess.model.Case;
import com.vivianhonghoa.chess.model.engine.Board;
import com.vivianhonghoa.chess.model.pieces.Piece;

import java.util.List;

public interface AccessibleCasesCalculator {

    List<Case> getAccessibleCases(Board board, Piece piece);
}
