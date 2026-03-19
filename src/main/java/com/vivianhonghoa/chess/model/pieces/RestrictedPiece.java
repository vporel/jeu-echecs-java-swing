package com.vivianhonghoa.chess.model.pieces;

import com.vivianhonghoa.chess.model.engine.Case;

import java.util.List;
import java.util.function.Predicate;

public class RestrictedPiece extends PieceDecorator {
    private final Predicate<Case> filter;

    public RestrictedPiece(Piece wrapped, Predicate<Case> filter) {
        super(wrapped);
        this.filter = filter;
    }

    @Override
    public List<Case> getAccessibleCases() {
        return super.getAccessibleCases().stream()
                .filter(filter)
                .toList();
    }
}
