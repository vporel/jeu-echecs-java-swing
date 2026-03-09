package com.vivianhonghoa.chess.viewcontroller;

import com.vivianhonghoa.chess.model.*;
import com.vivianhonghoa.chess.model.events.BoardEvent;
import com.vivianhonghoa.chess.model.events.BoardObserver;
import com.vivianhonghoa.chess.model.events.GameEngineObserver;
import com.vivianhonghoa.chess.model.events.GameEngineEvent;
import com.vivianhonghoa.chess.model.pieces.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Square extends JPanel {
    private static final int SIZE = 5; // Size of the box
    private final Position position;
    private final GameEngine gameEngine;
    private JLabel pieceLabel;

    public Square(int row, int col, GameEngine gameEngine) {
        this.position = new Position(row, col);
        this.gameEngine = gameEngine;
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
        gameEngine.getBoard().addObserver(new BoardObserver() {
            @Override
            public void onCaseSelected(BoardEvent event) {
                updateBackground(false);
            }

            @Override
            public void onPieceMoved(BoardEvent event) {
                updatePieceDisplay();
            }
        });
    }

    private boolean isSelected(){
        Case selectedCase = gameEngine.getBoard().getSelectedCase();
        return selectedCase != null && selectedCase.row() == position.row() && selectedCase.col() == position.col();
    }

    private boolean isMarkedAccessible(){
        Case selectedCase = gameEngine.getBoard().getSelectedCase();
        Piece selectedPiece = selectedCase != null ? gameEngine.getBoard().getPiece(selectedCase.row(), selectedCase.col()) : null;
        if (selectedPiece == null) return false;
        return selectedPiece.getAccessibleCases().stream().anyMatch(c -> c.row() == position.row() && c.col() == position.col);
    }

    private Piece getPiece(){
        return gameEngine.getBoard().getPiece(position.row, position.col);
    }

    private void updateBackground(boolean isHovered) {
        if (isHovered) {
            setBackground(Colors.PRIMARY_LIGHT_1);
        }else if (isMarkedAccessible()) {
            setBackground(Colors.SECONDARY_LIGHT_1);
        } else if (isSelected()) {
            setBackground(Colors.PRIMARY_LIGHT_2);
        } else {
            setBackground((position.row + position.col) % 2 == 0 ? Colors.BACKGROUND : Colors.PRIMARY);
        }
    }

    private void updatePieceDisplay() {
        Piece piece = getPiece();
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
                Board board = gameEngine.getBoard();
                Case newSelectedCase = new Case(position.row, position.col);
                gameEngine.selectCase(newSelectedCase.equals(board.getSelectedCase()) ? null : newSelectedCase);
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

