package com.vivianhonghoa.chess.viewcontroller.contexts;

import com.vivianhonghoa.chess.viewcontroller.Square;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class SquaresContext {
    private static SquaresContext instance;
    public static final String SELECTED_SQUARE_POS_PROPERTY = "selectedSquarePos";

    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private Square.Position selectedSquarePos = null;

    public Square.Position getSelectedSquarePos() {
        return selectedSquarePos;
    }

    public void setSelectedSquarePos(Square.Position selectedSquarePos) {
        Square.Position oldValue = this.selectedSquarePos;
        this.selectedSquarePos = selectedSquarePos;
        propertyChangeSupport.firePropertyChange("selectedSquarePos", oldValue, selectedSquarePos);
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
