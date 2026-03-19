package com.vivianhonghoa.chess.model.pieces;

import com.vivianhonghoa.chess.model.engine.Board;
import com.vivianhonghoa.chess.model.engine.Case;

import java.util.List;

public abstract class PieceDecorator extends Piece {
    protected final Piece wrapped;

    protected PieceDecorator(Piece wrapped) {
        super(wrapped.getColor());
        this.wrapped = wrapped;
    }

    @Override
    public PieceType getType() {
        return wrapped.getType();
    }

    @Override
    public char getLetter() {
        return wrapped.getLetter();
    }

    @Override
    public List<Case> getAccessibleCases() {
        return wrapped.getAccessibleCases();
    }

    @Override
    public boolean canMoveTo(Case targetCase) {
        return wrapped.canMoveTo(targetCase);
    }

    @Override
    public String getUnicodeSymbol() {
        return wrapped.getUnicodeSymbol();
    }

    @Override
    public Piece setBoard(Board board) {
        wrapped.setBoard(board);
        return super.setBoard(board);
    }

    @Override
    public void setPosition(int row, int col) {
        wrapped.setPosition(row, col);
        super.setPosition(row, col);
    }

    @Override
    public int getRow() {
        return wrapped.getRow();
    }

    @Override
    public int getCol() {
        return wrapped.getCol();
    }

    @Override
    public boolean hasMoved() {
        return wrapped.hasMoved();
    }

    @Override
    public void setHasMoved(boolean hasMoved) {
        wrapped.setHasMoved(hasMoved);
        super.setHasMoved(hasMoved);
    }
}
