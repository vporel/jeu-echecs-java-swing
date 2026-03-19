package com.vivianhonghoa.chess.model.players.computer;

import com.vivianhonghoa.chess.model.engine.Board;
import com.vivianhonghoa.chess.model.engine.Case;
import com.vivianhonghoa.chess.model.pieces.Piece;
import com.vivianhonghoa.chess.model.players.ComputerMove;
import com.vivianhonghoa.chess.model.players.ComputerStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomStrategy implements ComputerStrategy {
    private final Random random = new Random();

    @Override
    public ComputerMove chooseMove(Board board, Piece.Color color) {
        List<ComputerMove> allMoves = new ArrayList<>();

        for (int r = 0; r < Board.SIZE; r++) {
            for (int c = 0; c < Board.SIZE; c++) {
                Piece piece = board.getPieceAt(r, c);
                if (piece != null && piece.getColor() == color) {
                    Case from = new Case(r, c);
                    for (Case to : board.getLegalMoves(piece)) {
                        allMoves.add(new ComputerMove(from, to));
                    }
                }
            }
        }

        if (allMoves.isEmpty()) return null;
        return allMoves.get(random.nextInt(allMoves.size()));
    }
}
