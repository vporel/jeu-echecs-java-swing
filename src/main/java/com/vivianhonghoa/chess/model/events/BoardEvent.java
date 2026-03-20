package com.vivianhonghoa.chess.model.events;

import com.vivianhonghoa.chess.model.Case;
import com.vivianhonghoa.chess.model.pieces.Piece;

public class BoardEvent {
    private final Case relatedCase;
    private final Piece relatedPiece;

    public BoardEvent(){
        this(null);
    }

    public BoardEvent(Case relatedCase) {
        this(relatedCase, null);
    }

    public BoardEvent(Case relatedCase, Piece relatedPiece) {
        this.relatedCase = relatedCase;
        this.relatedPiece = relatedPiece;
    }

    public Case getRelatedCase() {
        return relatedCase;
    }
}
