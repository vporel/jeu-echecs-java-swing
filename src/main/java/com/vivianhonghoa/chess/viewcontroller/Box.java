package com.vivianhonghoa.chess.viewcontroller;

import com.vivianhonghoa.chess.model.*;
import com.vivianhonghoa.chess.model.events.GameEngineObserver;
import com.vivianhonghoa.chess.model.events.PieceEvent;
import com.vivianhonghoa.chess.model.pieces.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Box extends JPanel {
    private static final int SIZE = 5; // Size of the box
    private final int row;
    private final int col;
    private final GameEngine gameEngine;
    private JLabel pieceLabel;

    public Box(int row, int col, GameEngine gameEngine) {
        this.row = row;
        this.col = col;
        this.gameEngine = gameEngine;
        build();
    }

    private void build(){
        this.setMaximumSize(new Dimension(SIZE, SIZE));
        this.setLayout(new BorderLayout());
        setBackground((row + col) % 2 == 0 ? Color.WHITE : Color.LIGHT_GRAY);

        // Create label for piece display
        pieceLabel = new JLabel("", SwingConstants.CENTER);
        pieceLabel.setFont(new Font("Serif", Font.PLAIN, 50));
        this.add(pieceLabel, BorderLayout.CENTER);

        // Initialize piece display
        updatePieceDisplay();

        handleClick();
        //Listen the manager
        gameEngine.addObserver(new GameEngineObserver() {
            @Override
            public void boxStateUpdated(PieceEvent event) {

                updatePieceDisplay();
            }
        });
        JLabel jLabel = new JLabel();
        jLabel.setText("\u2654");

    }

    private void updatePieceDisplay() {
        Piece piece = gameEngine.getBoard().getPiece(row, col);
        if (piece != null) {
            String unicodeSymbol = getUnicodeSymbol(piece);
            pieceLabel.setIcon(null);
            pieceLabel.setText(unicodeSymbol);
            pieceLabel.setForeground(piece.getColor() == Piece.Color.BLANC ? java.awt.Color.WHITE : java.awt.Color.BLACK);
        } else {
            pieceLabel.setIcon(null);
            pieceLabel.setText("");
        }
    }

    private String getUnicodeSymbol(Piece piece) {

        return  switch(piece) {
            case King k -> "\u265A";
            case Queen q -> "\u265B";
            case Rook r -> "\u265C";
            case Bishop b -> "\u265D";
            case Knight k -> "\u265E";
            case Pawn p -> "\u265F";
            default -> "";
        };
    }

    private void handleClick(){
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ;
            }
            @Override
            public void mouseEntered(MouseEvent e) {

            }
        });
    }
}

