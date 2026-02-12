package com.vivianhonghoa.chess.viewcontroller;

import com.vivianhonghoa.chess.model.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

public class Box extends JPanel {
    private static final int SIZE = 5; // Size of the box
    private final int row;
    private final int col;
    private final BoxesManager boxesManager;
    private JLabel pieceLabel;

    public Box(int row, int col, BoxesManager boxesManager) {
        this.row = row;
        this.col = col;
        this.boxesManager = boxesManager;
        build();
    }

    private void build(){
        this.setMaximumSize(new Dimension(SIZE, SIZE));
        this.setLayout(new BorderLayout());
        setBackground((row + col) % 2 == 0 ? Color.WHITE : Color.BLACK);
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        this.setBorder(border);

        // Create label for piece display
        pieceLabel = new JLabel("", SwingConstants.CENTER);
        pieceLabel.setFont(new Font("Serif", Font.PLAIN, 50));
        this.add(pieceLabel, BorderLayout.CENTER);

        // Initialize piece display
        updatePieceDisplay();

        handleClick();
        //Listen the manager
        boxesManager.addListener(new BoxesManagerListener() {
            @Override
            public void boxStateUpdated(BoxDataEvent event) {
                BoxData boxData = (BoxData) event.getSource();
                if(!boxData.checkPosition(row, col)) return;
                switch(boxData.getState()){
                    case CLICKED: setBackground(Color.RED); break;
                    case HOVERED: setBackground(Color.GREEN); break;
                    default: setBackground((row + col) % 2 == 0 ? Color.WHITE : Color.BLACK);
                }
                updatePieceDisplay();
            }
        });
    }

    private void updatePieceDisplay() {
        BoxData boxData = boxesManager.getBoxesData().get(boxesManager.generateBoxKey(row, col));
        if (boxData != null && boxData.getPiece() != null) {
            Piece piece = boxData.getPiece();
            String unicodeSymbol = getUnicodeSymbol(piece);
            pieceLabel.setIcon(null);
            pieceLabel.setText(unicodeSymbol);
            pieceLabel.setForeground(piece.getCouleur() == Couleur.BLANC ? Color.WHITE : Color.BLACK);
        } else {
            pieceLabel.setIcon(null);
            pieceLabel.setText("");
        }
    }

    private String getUnicodeSymbol(Piece piece) {
        boolean isWhite = piece.getCouleur() == Couleur.BLANC;
        switch (piece.getType()) {
            case ROI: return isWhite ? "\u2654" : "\u265A";      // ♔ ♚
            case DAME: return isWhite ? "\u2655" : "\u265B";     // ♕ ♛
            case TOUR: return isWhite ? "\u2656" : "\u265C";     // ♖ ♜
            case FOU: return isWhite ? "\u2657" : "\u265D";      // ♗ ♝
            case CAVALIER: return isWhite ? "\u2658" : "\u265E"; // ♘ ♞
            case PION: return isWhite ? "\u2659" : "\u265F";     // ♙ ♟
            default: return "";
        }
    }

    private void handleClick(){
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                boxesManager.boxClicked(row, col);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                boxesManager.boxHovered(row, col);
            }
        });
    }
}
