package com.vivianhonghoa.chess.model.players.computer;

import com.vivianhonghoa.chess.model.engine.Board;
import com.vivianhonghoa.chess.model.engine.Case;
import com.vivianhonghoa.chess.model.pieces.Piece;
import com.vivianhonghoa.chess.model.players.ComputerMove;
import com.vivianhonghoa.chess.model.players.ComputerStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class TimeLimitedStrategy extends StrategyDecorator {
    private final long timeoutMs;
    private final Random random = new Random();

    public TimeLimitedStrategy(ComputerStrategy wrapped, long timeoutMs) {
        super(wrapped);
        this.timeoutMs = timeoutMs;
    }

    @Override
    public ComputerMove chooseMove(Board board, Piece.Color color) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<ComputerMove> future = executor.submit(() -> wrapped.chooseMove(board, color));
        try {
            return future.get(timeoutMs, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            future.cancel(true);
            System.out.printf("[TimeLimited] %s timed out after %d ms, falling back to random move%n",
                    wrapped.getClass().getSimpleName(), timeoutMs);
            return randomMove(board, color);
        } catch (Exception e) {
            System.out.printf("[TimeLimited] %s failed: %s%n",
                    wrapped.getClass().getSimpleName(), e.getMessage());
            return randomMove(board, color);
        } finally {
            executor.shutdownNow();
        }
    }

    private ComputerMove randomMove(Board board, Piece.Color color) {
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
