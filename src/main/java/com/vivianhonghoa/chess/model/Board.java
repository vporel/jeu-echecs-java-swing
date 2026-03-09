package com.vivianhonghoa.chess.model;

import com.vivianhonghoa.chess.model.events.BoardEvent;
import com.vivianhonghoa.chess.model.events.BoardObserver;
import com.vivianhonghoa.chess.model.events.GameEngineEvent;
import com.vivianhonghoa.chess.model.events.GameEngineObserver;
import com.vivianhonghoa.chess.model.pieces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class Board {
    public static final int TAILLE = 8;

    private final List<BoardObserver> observers;

    private Piece[][] pieces;
    private Case selectedCase;

    public Board() {
        observers = new ArrayList<>();
        pieces = new Piece[TAILLE][TAILLE];
        initPositions();
    }

    private void placePiece(Piece piece, int row, int col) {
        pieces[row][col] = piece;
        piece.setPosition(this, row, col);
    }

    private void initPositions() {
        // White pawns (row 2, index 1)
        for (int col = 0; col < TAILLE; col++) {
            placePiece(new Pawn(Piece.Color.BLANC), 1, col);
        }

        // Black pawns (row 7, index 6)
        for (int col = 0; col < TAILLE; col++) {
            placePiece(new Pawn(Piece.Color.NOIR), 6, col);
        }

        // White pieces (row 1, index 0)
        placePiece(new Rook(Piece.Color.BLANC), 0, 0);
        placePiece(new Knight(Piece.Color.BLANC), 0, 1);
        placePiece(new Bishop(Piece.Color.BLANC), 0, 2);
        placePiece(new King(Piece.Color.BLANC), 0, 3);
        placePiece(new Queen(Piece.Color.BLANC), 0, 4);
        placePiece(new Bishop(Piece.Color.BLANC), 0, 5);
        placePiece(new Knight(Piece.Color.BLANC), 0, 6);
        placePiece(new Rook(Piece.Color.BLANC), 0, 7);

        // Black pieces (row 8, index 7)
        placePiece(new Rook(Piece.Color.NOIR), 7, 0);
        placePiece(new Knight(Piece.Color.NOIR), 7, 1);
        placePiece(new Bishop(Piece.Color.NOIR), 7, 2);
        placePiece(new King(Piece.Color.NOIR), 7, 3);
        placePiece(new Queen(Piece.Color.NOIR), 7, 4);
        placePiece(new Bishop(Piece.Color.NOIR), 7, 5);
        placePiece(new Knight(Piece.Color.NOIR), 7, 6);
        placePiece(new Rook(Piece.Color.NOIR), 7, 7);
    }

     public Piece getPiece(int row, int col) {
        if (!Case.isValid(row, col)) {
            return null;
        }
        return pieces[row][col];
    }

    public Piece getSelectedPiece() {
        if (selectedCase == null) {
            return null;
        }
        return getPiece(selectedCase.row(), selectedCase.col());
    }

    void movePiece(Case from, Case to) {
        Piece piece = getPiece(from.row(), from.col());
        if (piece != null && piece.canMoveTo(to)) {
            // Move the piece
            pieces[to.row()][to.col()] = piece;
            pieces[from.row()][from.col()] = null;
            piece.setPosition(this, to.row(), to.col());

            // Notify observers of the move
            notifyObservers(to, BoardObserver::onPieceMoved);
        }
    }

    public Case getSelectedCase() {
        return selectedCase;
    }

    /**
     * Package-private setter for selectedCase. Notifies observers of the change.
     * @param selectedCase
     */
    void setSelectedCase(Case selectedCase) {
        this.selectedCase = selectedCase;
        notifyObservers(selectedCase, BoardObserver::onCaseSelected);
    }

    public synchronized void addObserver(BoardObserver observer){
        observers.add(observer);
    }

    private void notifyObservers(Case relatedCase, BiConsumer<BoardObserver, BoardEvent> action){
        BoardEvent event = new BoardEvent(relatedCase);
        synchronized(this) {
            for (BoardObserver listener : observers) {
                action.accept(listener, event);
            }
        }
    }

}
