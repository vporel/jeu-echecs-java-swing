package com.vivianhonghoa.chess.model.engine;

import com.vivianhonghoa.chess.model.Case;
import com.vivianhonghoa.chess.model.engine.specialmoves.CastlingHandler;
import com.vivianhonghoa.chess.model.engine.specialmoves.EnPassantHandler;
import com.vivianhonghoa.chess.model.engine.specialmoves.PawnPromotionHandler;
import com.vivianhonghoa.chess.model.events.BoardObserver;
import com.vivianhonghoa.chess.model.pieces.Piece;

class MoveExecutor {
    private final Board board;
    private final EnPassantHandler enPassant;
    private final CastlingHandler castling;
    private final PawnPromotionHandler promotion;

    MoveExecutor(Board board) {
        this.board = board;
        this.enPassant = new EnPassantHandler(board);
        this.castling = new CastlingHandler();
        this.promotion = new PawnPromotionHandler(board);
    }

    boolean movePiece(Case from, Case to) {
        if (from == null || to == null) return false;
        Piece piece = board.getPieceAt(from.row(), from.col());
        if (piece == null || !piece.canMoveTo(to)) return false;
        if (!board.isMoveLegal(from, to)) return false;

        Piece[][] pieces = board.getPieces();

        Piece captured = board.getPieceAt(to.row(), to.col());
        captured = enPassant.resolveCapture(piece, from, to, captured, pieces);

        if (captured != null) {
            if (piece.getColor() == Piece.Color.WHITE) {
                board.getCapturedByWhite().add(captured);
            } else {
                board.getCapturedByBlack().add(captured);
            }
        }

        pieces[to.row()][to.col()] = piece;
        pieces[from.row()][from.col()] = null;
        piece.setPosition(to.row(), to.col());
        piece.setHasMoved(true);

        enPassant.updateTarget(piece, from, to);
        castling.apply(piece, from, to, pieces);
        promotion.apply(piece, to, pieces);

        board.notifyObservers(to, BoardObserver::onPieceMoved);
        if (captured != null) {
            board.notifyObservers(to, BoardObserver::onPieceCaptured);
        }
        return true;
    }

    void undoMove(Case from, Case to, Piece captured) {
        Piece piece = board.getPieceAt(to.row(), to.col());
        if (piece == null) return;

        Piece[][] pieces = board.getPieces();

        pieces[from.row()][from.col()] = piece;
        pieces[to.row()][to.col()] = captured;
        piece.setPosition(from.row(), from.col());

        enPassant.undoCapture(piece, from, to, captured, pieces);
        castling.undo(piece, from, to, pieces);
        promotion.undo(piece, from, to, pieces);

        board.notifyObservers(from, BoardObserver::onPieceMoved);
    }
}
