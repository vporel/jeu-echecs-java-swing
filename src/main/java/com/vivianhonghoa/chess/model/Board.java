package com.vivianhonghoa.chess.model;

import com.vivianhonghoa.chess.model.events.BoardEvent;
import com.vivianhonghoa.chess.model.events.BoardObserver;
import com.vivianhonghoa.chess.model.pieces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class Board {
    public static final int TAILLE = 8;

    private final List<BoardObserver> observers;

    private Piece[][] pieces;
    private Case selectedCase;
    private final List<Piece> capturedByWhite = new ArrayList<>();
    private final List<Piece> capturedByBlack = new ArrayList<>();

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

    // ── Check detection ──────────────────────────────────────────────

    public Case findKing(Piece.Color color) {
        for (int r = 0; r < TAILLE; r++) {
            for (int c = 0; c < TAILLE; c++) {
                Piece p = pieces[r][c];
                if (p instanceof King && p.getColor() == color) {
                    return new Case(r, c);
                }
            }
        }
        return null;
    }

    public boolean isSquareAttackedBy(int row, int col, Piece.Color attackerColor) {
        for (int r = 0; r < TAILLE; r++) {
            for (int c = 0; c < TAILLE; c++) {
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
            int direction = (piece.getColor() == Piece.Color.BLANC) ? 1 : -1;
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
        Piece.Color opponent = (color == Piece.Color.BLANC) ? Piece.Color.NOIR : Piece.Color.BLANC;
        return isSquareAttackedBy(kingCase.row(), kingCase.col(), opponent);
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
        piece.setPosition(this, to.row(), to.col());

        boolean inCheck = isKingInCheck(piece.getColor());

        // Undo the move
        pieces[from.row()][from.col()] = piece;
        pieces[to.row()][to.col()] = captured;
        piece.setPosition(this, from.row(), from.col());

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
        if (captured != null) {
            if (piece.getColor() == Piece.Color.BLANC) {
                capturedByWhite.add(captured);
            } else {
                capturedByBlack.add(captured);
            }
        }

        // Move the piece
        pieces[to.row()][to.col()] = piece;
        pieces[from.row()][from.col()] = null;
        piece.setPosition(this, to.row(), to.col());
        piece.setHasMoved(true);

        // Handle castling rook movement (King moved 2 columns)
        if (piece instanceof King && Math.abs(to.col() - from.col()) == 2) {
            int rookFromCol, rookToCol;
            if (to.col() < from.col()) {
                // Queenside: Rook from col 0 → col 2
                rookFromCol = 0;
                rookToCol = 2;
            } else {
                // Kingside: Rook from col 7 → col 4
                rookFromCol = 7;
                rookToCol = 4;
            }
            Piece rook = pieces[from.row()][rookFromCol];
            pieces[from.row()][rookToCol] = rook;
            pieces[from.row()][rookFromCol] = null;
            rook.setPosition(this, from.row(), rookToCol);
            rook.setHasMoved(true);
        }

        // Notify observers of the move
        notifyObservers(to, BoardObserver::onPieceMoved);
        if (captured != null) {
            notifyObservers(to, BoardObserver::onPieceCaptured);
        }
        return true;
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
