package com.vivianhonghoa.chess.model.events;

import com.vivianhonghoa.chess.model.Case;
import com.vivianhonghoa.chess.model.pieces.Piece;

public class BoardEvent {
    private final Case relatedCase;

    public BoardEvent(){
        this(null);
    }


    public BoardEvent(Case relatedCase) {
        this.relatedCase = relatedCase;
    }

    public Case getRelatedCase() {
        return relatedCase;
    }
}
