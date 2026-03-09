package com.vivianhonghoa.chess.viewcontroller.contexts;

import com.vivianhonghoa.chess.viewcontroller.Square;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class SquaresContext {
    private static SquaresContext instance;
    public static final String SELECTED_SQUARE_PROPERTY = "selectedSquare";
    public static final String MARKED_ACCESSIBLE_SQUARES_PROPERTY = "markedAccessibleSquares";

    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    private Square.Position selectedSquare = null;
    private List<Square.Position> markedAccessibleSquares = new ArrayList<>();

    public Square.Position getSelectedSquare() {
        return selectedSquare;
    }

    public void setSelectedSquare(Square.Position selectedSquare) {
        Square.Position oldValue = this.selectedSquare;
        this.selectedSquare = selectedSquare;
        propertyChangeSupport.firePropertyChange(SELECTED_SQUARE_PROPERTY, oldValue, selectedSquare);
    }

    public List<Square.Position> getMarkedAccessibleSquares() {
        return markedAccessibleSquares;
    }

    public void setMarkedAccessibleSquares(List<Square.Position> positions) {
        List<Square.Position> oldValue = this.markedAccessibleSquares;
        this.markedAccessibleSquares = positions;
        propertyChangeSupport.firePropertyChange(MARKED_ACCESSIBLE_SQUARES_PROPERTY, oldValue, positions);
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
    }

    public static SquaresContext getInstance() {
        if (instance == null) {
            instance = new SquaresContext();
        }
        return instance;
    }
}
