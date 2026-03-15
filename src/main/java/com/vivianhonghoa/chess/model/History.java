package com.vivianhonghoa.chess.model;

import com.vivianhonghoa.chess.model.events.HistoryEvent;
import com.vivianhonghoa.chess.model.events.HistoryObserver;
import com.vivianhonghoa.chess.model.pieces.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class History {

    private final List<Entry> entries = new ArrayList<>();
    private final CopyOnWriteArrayList<HistoryObserver> observers = new CopyOnWriteArrayList<>();

    void add(Entry entry) {
        entries.add(entry);
        notifyObservers();
    }

    public boolean isEmpty() {
        return entries.isEmpty();
    }

    Entry removeLast() {
        if (entries.isEmpty()) return null;
        Entry last = entries.removeLast();
        notifyObservers();
        return last;
    }

    void clear() {
        entries.clear();
        notifyObservers();
    }

    public List<Entry> getEntries() {
        return Collections.unmodifiableList(entries);
    }

    /**
     * Returns the history as a list of strings in standard algebraic notation.
     * Examples: "e4", "Nf3", "exd5", "Bxe5"
     */
    public List<String> getFormattedList() {
        List<String> formatted = new ArrayList<>();
        for (Entry entry : entries) {
            formatted.add(entry.format());
        }
        return Collections.unmodifiableList(formatted);
    }


    public void addObserver(HistoryObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers() {
        HistoryEvent event = new HistoryEvent();
        for (HistoryObserver observer : observers) {
            observer.onChange(event);
        }
    }

    /**
     * Represents a single move in the history.
     *
     * @param piece    The piece that moved.
     * @param from     The square the piece moved from.
     * @param to       The square the piece moved to.
     * @param captured The piece that was captured, or null if no capture.
     */
    public record Entry(Piece piece, Case from, Case to, Piece captured) {

        private String format() {
            StringBuilder sb = new StringBuilder();

            // Piece letter — omitted for pawns
            String piecePrefix = getPiecePrefix(this.piece());
            sb.append(piecePrefix);

            // For pawns that capture, add the origin file
            if (this.piece() instanceof Pawn && this.captured() != null) {
                sb.append(colToFile(this.from.col()));
            }

            // Capture indicator
            if (this.captured() != null) {
                sb.append("x");
            }

            // Destination square
            sb.append(colToFile(this.to.col()));
            sb.append(rowToRank(this.to.row()));

            return sb.toString();
        }


        /** Converts 0-based column index to chess file letter (0 → a, 7 → h). */
        private char colToFile(int col) {
            return (char) ('a' + col);
        }

        /** Converts 0-based row index to chess rank number (0 → 1, 7 → 8). */
        private int rowToRank(int row) {
            return row + 1;
        }

        private String getPiecePrefix(Piece piece) {
            return switch (piece) {
                case King k   -> "K";
                case Queen q  -> "Q";
                case Rook r   -> "R";
                case Bishop b -> "B";
                case Knight k -> "N";
                default       -> ""; // Pawn has no prefix
            };
        }
    }
}
