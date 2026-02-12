package com.vivianhonghoa.chess.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class BoxesManager {
    private Map<String, BoxData> boxesData = new HashMap<>();
    private List<BoxesManagerListener> listeners = new ArrayList<>();
    private Plateau plateau;
    public static final int NUM_BOXES_PER_ROW = 8;
    public static final int NUM_BOXES_PER_COL = 8;

    public BoxesManager(){
        plateau = new Plateau();
        init();
    }

    private void init(){
        for (int row = 1; row <= NUM_BOXES_PER_ROW; row++) {
            for (int col = 1; col <= NUM_BOXES_PER_COL; col++) {
                BoxData boxData = new BoxData(row, col);
                // Sync piece from plateau (convert 1-indexed to 0-indexed)
                Piece piece = plateau.getPiece(row - 1, col - 1);
                boxData.setPiece(piece);
                boxesData.put(this.generateBoxKey(row, col), boxData);
            }
        }
    }

    public Map<String, BoxData> getBoxesData() {
        return boxesData;
    }

    public void boxClicked(int row, int col) {
        // Reset all boxes to DEFAULT, then set the clicked one
        BoxData target = boxesData.get(this.generateBoxKey(row, col));
        if (target != null) target.setState(BoxData.BoxState.CLICKED);

        // Broadcast current state for all boxes to listeners
        notifyListeners(target, BoxesManagerListener::boxStateUpdated);
    }

    public void boxHovered(int row, int col) {
        // Clear previous hovered states (but keep CLICKED states)
        BoxData target = boxesData.get(this.generateBoxKey(row, col));
        if (target != null && target.getState() != BoxData.BoxState.CLICKED) {
            target.setState(BoxData.BoxState.HOVERED);
        }

        // Broadcast current state for all boxes to listeners
        notifyListeners(target, BoxesManagerListener::boxStateUpdated);
    }

    public synchronized void addListener(BoxesManagerListener listener){
        listeners.add(listener);
    }

    private void notifyListeners(BoxData boxData, BiConsumer<BoxesManagerListener, BoxDataEvent> action){
        BoxDataEvent event = new BoxDataEvent(boxData);
        List<BoxesManagerListener> snapshot;
        synchronized(this) {
            for (BoxesManagerListener listener : listeners) {
                action.accept(listener, event);
            }
        }
    }

    public String generateBoxKey(int row, int col) {
        return row + "-" + col;
    }

    public Plateau getPlateau() {
        return plateau;
    }
}
