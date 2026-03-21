package com.vivianhonghoa.chess.model.engine;

import com.vivianhonghoa.chess.model.pieces.*;

class BoardSetup {

    static void initialize(Board board) {
        Piece[][] pieces = board.getPieces();

        // White pawns (row 2, index 1)
        for (int col = 0; col < Board.SIZE; col++) {
            placePiece(pieces, new Pawn(Piece.Color.WHITE).setBoard(board), 1, col);
        }

        // Black pawns (row 7, index 6)
        for (int col = 0; col < Board.SIZE; col++) {
            placePiece(pieces, new Pawn(Piece.Color.BLACK).setBoard(board), 6, col);
        }

        // White pieces (row 1, index 0)
        placePiece(pieces, new Rook(Piece.Color.WHITE).setBoard(board), 0, 0);
        placePiece(pieces, new Knight(Piece.Color.WHITE).setBoard(board), 0, 1);
        placePiece(pieces, new Bishop(Piece.Color.WHITE).setBoard(board), 0, 2);
        placePiece(pieces, new King(Piece.Color.WHITE).setBoard(board), 0, 3);
        placePiece(pieces, new Queen(Piece.Color.WHITE).setBoard(board), 0, 4);
        placePiece(pieces, new Bishop(Piece.Color.WHITE).setBoard(board), 0, 5);
        placePiece(pieces, new Knight(Piece.Color.WHITE).setBoard(board), 0, 6);
        placePiece(pieces, new Rook(Piece.Color.WHITE).setBoard(board), 0, 7);

        // Black pieces (row 8, index 7)
        placePiece(pieces, new Rook(Piece.Color.BLACK).setBoard(board), 7, 0);
        placePiece(pieces, new Knight(Piece.Color.BLACK).setBoard(board), 7, 1);
        placePiece(pieces, new Bishop(Piece.Color.BLACK).setBoard(board), 7, 2);
        placePiece(pieces, new King(Piece.Color.BLACK).setBoard(board), 7, 3);
        placePiece(pieces, new Queen(Piece.Color.BLACK).setBoard(board), 7, 4);
        placePiece(pieces, new Bishop(Piece.Color.BLACK).setBoard(board), 7, 5);
        placePiece(pieces, new Knight(Piece.Color.BLACK).setBoard(board), 7, 6);
        placePiece(pieces, new Rook(Piece.Color.BLACK).setBoard(board), 7, 7);
    }

    private static void placePiece(Piece[][] pieces, Piece piece, int row, int col) {
        pieces[row][col] = piece;
        piece.setPosition(row, col);
    }
}
