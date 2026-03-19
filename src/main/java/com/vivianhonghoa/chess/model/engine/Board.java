package com.vivianhonghoa.chess.model.engine;

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

    @FunctionalInterface
    public interface PromotionHandler {
        Piece choosePiece(Piece.Color color);
    }

    Board() {
        pieces = new Piece[SIZE][SIZE];
        initPositions();
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
        selectedCase = null;
        capturedByWhite.clear();
        capturedByBlack.clear();
        passingCaptureTarget = null;
        initPositions();
        notifyObservers(null, BoardObserver::onPieceMoved);
    }

    void placePiece(Piece piece, int row, int col) {
        pieces[row][col] = piece;
        piece.setPosition(row, col);
    }

    private void initPositions() {
        // White pawns (row 2, index 1)
        for (int col = 0; col < SIZE; col++) {
            placePiece(new Pawn(Piece.Color.WHITE).setBoard(this), 1, col);
        }

        // Black pawns (row 7, index 6)
        for (int col = 0; col < SIZE; col++) {
            placePiece(new Pawn(Piece.Color.BLACK).setBoard(this), 6, col);
        }

        // White pieces (row 1, index 0)
        placePiece(new Rook(Piece.Color.WHITE).setBoard(this), 0, 0);
        placePiece(new Knight(Piece.Color.WHITE).setBoard(this), 0, 1);
        placePiece(new Bishop(Piece.Color.WHITE).setBoard(this), 0, 2);
        placePiece(new King(Piece.Color.WHITE).setBoard(this), 0, 3);
        placePiece(new Queen(Piece.Color.WHITE).setBoard(this), 0, 4);
        placePiece(new Bishop(Piece.Color.WHITE).setBoard(this), 0, 5);
        placePiece(new Knight(Piece.Color.WHITE).setBoard(this), 0, 6);
        placePiece(new Rook(Piece.Color.WHITE).setBoard(this), 0, 7);

        // Black pieces (row 8, index 7)
        placePiece(new Rook(Piece.Color.BLACK).setBoard(this), 7, 0);
        placePiece(new Knight(Piece.Color.BLACK).setBoard(this), 7, 1);
        placePiece(new Bishop(Piece.Color.BLACK).setBoard(this), 7, 2);
        placePiece(new King(Piece.Color.BLACK).setBoard(this), 7, 3);
        placePiece(new Queen(Piece.Color.BLACK).setBoard(this), 7, 4);
        placePiece(new Bishop(Piece.Color.BLACK).setBoard(this), 7, 5);
        placePiece(new Knight(Piece.Color.BLACK).setBoard(this), 7, 6);
        placePiece(new Rook(Piece.Color.BLACK).setBoard(this), 7, 7);
    }

    public Piece getPiece(int row, int col) {
        if (!Case.isValid(row, col)) {
            return null;
        }
        return pieces[row][col];
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
        if (piece instanceof Pawn && captured == null && from.col() != to.col()) {
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

    // ── Check detection ──────────────────────────────────────────────

    public Case findKing(Piece.Color color) {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                Piece p = pieces[r][c];
                if (p instanceof King && p.getColor() == color) {
                    return new Case(r, c);
                }
            }
        }
        return null;
    }

    public boolean isSquareAttackedBy(int row, int col, Piece.Color attackerColor) {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                Piece p = pieces[r][c];
                if (p != null && p.getColor() == attackerColor && canPieceAttack(p, r, c, row, col)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean canPieceAttack(Piece piece, int fromRow, int fromCol, int toRow, int toCol) {
        int dr = toRow - fromRow;
        int dc = toCol - fromCol;
        int absDr = Math.abs(dr);
        int absDc = Math.abs(dc);

        if (piece instanceof Pawn) {
            int direction = (piece.getColor() == Piece.Color.WHITE) ? 1 : -1;
            return dr == direction && absDc == 1;
        } else if (piece instanceof Knight) {
            return (absDr == 2 && absDc == 1) || (absDr == 1 && absDc == 2);
        } else if (piece instanceof King) {
            return absDr <= 1 && absDc <= 1 && (absDr + absDc > 0);
        } else if (piece instanceof Rook) {
            return (dr == 0 || dc == 0) && isPathClear(fromRow, fromCol, toRow, toCol);
        } else if (piece instanceof Bishop) {
            return absDr == absDc && absDr > 0 && isPathClear(fromRow, fromCol, toRow, toCol);
        } else if (piece instanceof Queen) {
            return ((dr == 0 || dc == 0) || (absDr == absDc && absDr > 0))
                    && isPathClear(fromRow, fromCol, toRow, toCol);
        }
        return false;
    }

    private boolean isPathClear(int fromRow, int fromCol, int toRow, int toCol) {
        int dr = Integer.signum(toRow - fromRow);
        int dc = Integer.signum(toCol - fromCol);
        int r = fromRow + dr;
        int c = fromCol + dc;
        while (r != toRow || c != toCol) {
            if (pieces[r][c] != null) return false;
            r += dr;
            c += dc;
        }
        return true;
    }

    public boolean isKingInCheck(Piece.Color color) {
        Case kingCase = findKing(color);
        if (kingCase == null) return false;
        Piece.Color opponent = (color == Piece.Color.WHITE) ? Piece.Color.BLACK : Piece.Color.WHITE;
        return isSquareAttackedBy(kingCase.row(), kingCase.col(), opponent);
    }

    public boolean isKingInCheckmate(Piece.Color color) {
        if (!isKingInCheck(color)) return false;
        Case kingCase = findKing(color);
        if (kingCase == null) return false;
        Piece king = pieces[kingCase.row()][kingCase.col()];
        for (Case move : king.getAccessibleCases()) {
            if (isMoveLegal(king, kingCase, move)) {
                return false; // King can escape
            }
        }
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                Piece p = pieces[r][c];
                if (p != null && p.getColor() == color) {
                    for (Case move : p.getAccessibleCases()) {
                        if (isMoveLegal(p, new Case(r, c), move)) {
                            return false; // A piece can block or capture
                        }
                    }
                }
            }
        }
        return true; // No escape, it's checkmate
    }

    public boolean isStalemate(Piece.Color color) {
        if (isKingInCheck(color)) return false;
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                Piece p = pieces[r][c];
                if (p != null && p.getColor() == color) {
                    for (Case move : p.getAccessibleCases()) {
                        if (isMoveLegal(p, new Case(r, c), move)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    // ── Legal moves (simulation-based filtering) ─────────────────────

    public List<Case> getLegalMoves(Piece piece) {
        List<Case> legal = new ArrayList<>();
        Case from = new Case(piece.getRow(), piece.getCol());
        for (Case to : piece.getAccessibleCases()) {
            if (isMoveLegal(piece, from, to)) {
                legal.add(to);
            }
        }
        return legal;
    }

    private boolean isMoveLegal(Piece piece, Case from, Case to) {
        // Simulate the move
        Piece captured = pieces[to.row()][to.col()];
        pieces[to.row()][to.col()] = piece;
        pieces[from.row()][from.col()] = null;
        piece.setPosition(to.row(), to.col());

        // Simulate passing capture (pawn moves diagonally to empty square)
        Piece passingCapture = null;
        if (piece instanceof Pawn && captured == null && from.col() != to.col()) {
            passingCapture = pieces[from.row()][to.col()];
            pieces[from.row()][to.col()] = null;
        }

        boolean inCheck = isKingInCheck(piece.getColor());

        // Undo the move
        pieces[from.row()][from.col()] = piece;
        pieces[to.row()][to.col()] = captured;
        piece.setPosition(from.row(), from.col());
        if (passingCapture != null) {
            pieces[from.row()][to.col()] = passingCapture;
        }

        return !inCheck;
    }

    public Piece getSelectedPiece() {
        if (selectedCase == null) {
            return null;
        }
        return getPiece(selectedCase.row(), selectedCase.col());
    }

    boolean movePiece(Case from, Case to) {
        if (from == null || to == null) return false;
        Piece piece = getPiece(from.row(), from.col());
        if (piece == null || !piece.canMoveTo(to)) return false;

        // Simulate-and-reject: ensure the move doesn't leave our King in check
        if (!isMoveLegal(piece, from, to)) return false;

        // Check for capture
        Piece captured = getPiece(to.row(), to.col());

        // Handle passing capture
        if (piece instanceof Pawn && captured == null && from.col() != to.col()) {
            captured = pieces[from.row()][to.col()];
            pieces[from.row()][to.col()] = null;
        }

        if (captured != null) {
            if (piece.getColor() == Piece.Color.WHITE) {
                capturedByWhite.add(captured);
            } else {
                capturedByBlack.add(captured);
            }
        }

        // Move the piece
        pieces[to.row()][to.col()] = piece;
        pieces[from.row()][from.col()] = null;
        piece.setPosition(to.row(), to.col());
        piece.setHasMoved(true);

        // Set passing target if pawn moved 2 squares
        if (piece instanceof Pawn && Math.abs(to.row() - from.row()) == 2) {
            int epRow = (from.row() + to.row()) / 2;
            passingCaptureTarget = new Case(epRow, to.col());
        } else {
            passingCaptureTarget = null;
        }

        // Handle castling rook movement (King moved 2 columns)
        if (piece instanceof King && Math.abs(to.col() - from.col()) == 2) {
            int rookFromCol, rookToCol;
            if (to.col() < from.col()) {
                rookFromCol = 0;
                rookToCol = 2;
            } else {
                rookFromCol = 7;
                rookToCol = 4;
            }
            Piece rook = pieces[from.row()][rookFromCol];
            pieces[from.row()][rookToCol] = rook;
            pieces[from.row()][rookFromCol] = null;
            rook.setPosition(from.row(), rookToCol);
            rook.setHasMoved(true);
        }

        // Handle pawn promotion
        if (piece instanceof Pawn) {
            int promotionRow = (piece.getColor() == Piece.Color.WHITE) ? 7 : 0;
            if (to.row() == promotionRow) {
                Piece promoted = promotionHandler.choosePiece(piece.getColor());
                promoted.setBoard(this);
                pieces[to.row()][to.col()] = promoted;
                promoted.setPosition(to.row(), to.col());
                promoted.setHasMoved(true);
            }
        }

        // Notify observers of the move
        notifyObservers(to, BoardObserver::onPieceMoved);
        if (captured != null) {
            notifyObservers(to, BoardObserver::onPieceCaptured);
        }
        return true;
    }

    public void undoMove(Case from, Case to, Piece captured) {
        Piece piece = getPiece(to.row(), to.col());
        if (piece == null) return;

        // Move the piece back
        pieces[from.row()][from.col()] = piece;
        pieces[to.row()][to.col()] = captured;
        piece.setPosition(from.row(), from.col());

        // Handle undoing passing capture
        if (piece instanceof Pawn && captured == null && from.col() != to.col()) {
            Piece passingCaptured = pieces[from.row()][to.col()];
            pieces[from.row()][to.col()] = passingCaptured;
            if (passingCaptured != null) {
                if (piece.getColor() == Piece.Color.WHITE) {
                    capturedByWhite.remove(passingCaptured);
                } else {
                    capturedByBlack.remove(passingCaptured);
                }
            }
        }

        // Handle undoing castling rook movement
        if (piece instanceof King && Math.abs(to.col() - from.col()) == 2) {
            int rookFromCol, rookToCol;
            if (to.col() < from.col()) {
                rookFromCol = 0;
                rookToCol = 2;
            } else {
                rookFromCol = 7;
                rookToCol = 4;
            }
            Piece rook = pieces[from.row()][rookToCol];
            pieces[from.row()][rookFromCol] = rook;
            pieces[from.row()][rookToCol] = null;
            if (rook != null) {
                rook.setPosition(from.row(), rookFromCol);
            }
        }

        // Handle undoing pawn promotion
        if (piece instanceof Pawn) {
            int promotionRow = (piece.getColor() == Piece.Color.WHITE) ? 7 : 0;
            if (to.row() == promotionRow) {
                Piece originalPawn = new Pawn(piece.getColor()).setBoard(this);
                pieces[to.row()][to.col()] = originalPawn;
                originalPawn.setPosition(to.row(), to.col());
            }
        }

        // Notify observers of the undo
        notifyObservers(from, BoardObserver::onPieceMoved);
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

    private void notifyObservers(Case relatedCase, BiConsumer<BoardObserver, BoardEvent> action){
        BoardEvent event = new BoardEvent(relatedCase);
        for (BoardObserver listener : observers) {
            action.accept(listener, event);
        }
    }

}
