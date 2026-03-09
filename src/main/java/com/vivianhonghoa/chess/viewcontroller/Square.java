package com.vivianhonghoa.chess.viewcontroller;

import com.vivianhonghoa.chess.model.*;
import com.vivianhonghoa.chess.model.events.GameEngineObserver;
import com.vivianhonghoa.chess.model.events.PieceEvent;
import com.vivianhonghoa.chess.model.pieces.*;
import com.vivianhonghoa.chess.viewcontroller.contexts.SquaresContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Square extends JPanel {
    private static final int SIZE = 5; // Size of the box
    private final Position position;
    private final GameEngine gameEngine;
    private final SquaresContext squaresContext;
    private JLabel pieceLabel;

    public Square(int row, int col, GameEngine gameEngine) {
        this.position = new Position(row, col);
        this.gameEngine = gameEngine;
        this.squaresContext = SquaresContext.getInstance();
        build();
    }

    private void build(){
        this.setMaximumSize(new Dimension(SIZE, SIZE));
        this.setLayout(new BorderLayout());
        updateBackground(false);

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

        SquaresContext.getInstance().addPropertyChangeListener(SquaresContext.SELECTED_SQUARE_POS_PROPERTY, eventt -> {
            updateBackground(false);
        });
    }

    private boolean isSelected(){
        Position selectedPos = squaresContext.getSelectedSquarePos();
        return selectedPos != null && selectedPos.equals(position);
    }

    private void updateBackground(boolean isHovered) {
        if (isSelected()) {
            setBackground(Colors.PRIMARY_LIGHT_2);
        } else {
            if(isHovered)
                setBackground(Colors.PRIMARY_LIGHT_1);
            else
                setBackground((position.row + position.col) % 2 == 0 ? Colors.BACKGROUND : Colors.PRIMARY);
        }
    }

    private void updatePieceDisplay() {
        Piece piece = gameEngine.getBoard().getPiece(position.row, position.col);
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
                squaresContext.setSelectedSquarePos(position);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                updateBackground(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                updateBackground(false);
            }
        });
    }

    public record Position(int row, int col) {

    }
}

