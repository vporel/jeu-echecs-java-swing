package com.vivianhonghoa.chess.model.engine;

import com.vivianhonghoa.chess.model.Case;
import com.vivianhonghoa.chess.model.events.BoardEvent;
import com.vivianhonghoa.chess.model.events.BoardObserver;
import com.vivianhonghoa.chess.model.pieces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class Board {
    public static final int SIZE = 8;

    private final CopyOnWriteArrayList<BoardObserver> observers = new CopyOnWriteArrayList<>();

    private Piece[][] pieces;
    private Case selectedCase;
    private final List<Piece> capturedByWhite = new ArrayList<>();
    private final List<Piece> capturedByBlack = new ArrayList<>();
    private Case passingCaptureTarget = null;
    private PromotionHandler promotionHandler = Queen::new;
    private final MoveExecutor moveExecutor = new MoveExecutor(this);

    @FunctionalInterface
    public interface PromotionHandler {
        Piece choosePiece(Piece.Color color);
    }

    Board() {
        pieces = new Piece[SIZE][SIZE];
        BoardSetup.initialize(this);
    }

    public Piece[][] getPieces() {
        return pieces;
    }

    public void setPassingCaptureTarget(Case target) {
        this.passingCaptureTarget = target;
    }

    public List<Piece> getPiecesAsList() {
        List<Piece> list = new ArrayList<>();
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (pieces[r][c] != null) {
                    list.add(pieces[r][c]);
                }
            }
        }
        return list;
    }

    void setPieces(Piece[][] pieces) {
        Piece[][] copy = new Piece[pieces.length][];
        for (int i = 0; i < pieces.length; i++) {
            copy[i] = pieces[i].clone();
            //Set the board reference for each piece
            for (int j = 0; j < copy[i].length; j++) {
                if (copy[i][j] != null) {
                    copy[i][j].setBoard(this);
                }
            }
        }
        this.pieces = copy;
    }

    void reset() {
        pieces = new Piece[SIZE][SIZE];
        // Use the setter so observers are notified about the selection change
        setSelectedCase(null);
        capturedByWhite.clear();
        capturedByBlack.clear();
        passingCaptureTarget = null;
        BoardSetup.initialize(this);
        notifyObservers(null, BoardObserver::onPieceMoved);
        notifyObservers(null, BoardObserver::onPieceCaptured);
    }


    public Piece getPieceAt(int row, int col) {
        if (!BoardHelper.isValid(row, col)) {
            return null;
        }
        return pieces[row][col];
    }

    public Piece getPieceAt(Case c) {
        return getPieceAt(c.row(), c.col());
    }

    public Case getPassingCaptureTarget() {
        return passingCaptureTarget;
    }

    public void setPromotionHandler(PromotionHandler handler) {
        this.promotionHandler = handler;
    }

    public PromotionHandler getPromotionHandler() {
        return promotionHandler;
    }

    public <T> T withSimulatedMove(Case from, Case to, Supplier<T> evaluator) {
        Piece piece = pieces[from.row()][from.col()];
        Piece captured = pieces[to.row()][to.col()];
        pieces[to.row()][to.col()] = piece;
        pieces[from.row()][from.col()] = null;
        piece.setPosition(to.row(), to.col());

        Piece passingCapture = null;
        if (piece.getType() == Piece.Type.PAWN && captured == null && from.col() != to.col()) {
            passingCapture = pieces[from.row()][to.col()];
            pieces[from.row()][to.col()] = null;
        }

        T result = evaluator.get();

        // Undo
        pieces[from.row()][from.col()] = piece;
        pieces[to.row()][to.col()] = captured;
        piece.setPosition(from.row(), from.col());
        if (passingCapture != null) {
            pieces[from.row()][to.col()] = passingCapture;
        }

        return result;
    }

    // ── Legal moves (simulation-based filtering) ─────────────────────
    public List<Case> getLegalMoves(Piece piece) {
        List<Case> legal = new ArrayList<>();
        Case from = new Case(piece.getRow(), piece.getCol());
        for (Case to : piece.getAccessibleCases()) {
            if (isMoveLegal(from, to)) {
                legal.add(to);
            }
        }
        return legal;
    }

    boolean isMoveLegal(Case from, Case to) {
        if (from == null || to == null) return false;
        Piece piece = getPieceAt(from.row(), from.col());
        if (piece == null) return false;
        return withSimulatedMove(from, to, () -> !BoardHelper.isKingInCheck(this, piece.getColor()));
    }

    MoveExecutor getMoveExecutor() {
        return moveExecutor;
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

    public List<Piece> getCapturedByWhite() {
        return capturedByWhite;
    }

    public List<Piece> getCapturedByBlack() {
        return capturedByBlack;
    }

    public void addObserver(BoardObserver observer){
        observers.add(observer);
    }

    void notifyObservers(Case relatedCase, BiConsumer<BoardObserver, BoardEvent> action){
        BoardEvent event = new BoardEvent(relatedCase);
        for (BoardObserver listener : observers) {
            action.accept(listener, event);
        }
    }

}
