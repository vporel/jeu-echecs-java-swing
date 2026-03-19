package com.vivianhonghoa.chess.viewcontroller.components.game;

import com.vivianhonghoa.chess.model.engine.Board;
import com.vivianhonghoa.chess.model.engine.Case;
import com.vivianhonghoa.chess.model.engine.GameEngine;
import com.vivianhonghoa.chess.model.events.BoardEvent;
import com.vivianhonghoa.chess.model.events.BoardObserver;
import com.vivianhonghoa.chess.model.pieces.*;
import com.vivianhonghoa.chess.viewcontroller.Colors;
import com.vivianhonghoa.chess.viewcontroller.components.lib.JCustomLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JSquare extends JPanel {
    private static final int SIZE = 5; // Size of the box
    private final Position position;
    private final GameEngine gameEngine;
    private JCustomLabel jPieceLabel;

    public JSquare(int row, int col, GameEngine gameEngine) {
        super();
        this.position = new Position(row, col);
        this.gameEngine = gameEngine;
        build();
    }

    private void build(){
        this.setMaximumSize(new Dimension(SIZE, SIZE));
        this.setLayout(new BorderLayout());
        updateBackground(false);

        // Create label for piece display
        jPieceLabel = new JCustomLabel("", SwingConstants.CENTER);
        jPieceLabel.setFont(new Font("Serif", Font.PLAIN, 50));
        this.add(jPieceLabel, BorderLayout.CENTER);

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
                updateBackground(false);
            }
        });
    }

    private boolean isSelected(){
        Case selectedCase = gameEngine.getBoard().getSelectedCase();
        return selectedCase != null && selectedCase.row() == position.row() && selectedCase.col() == position.col();
    }

    private boolean isMarkedAccessible(){
        Case selectedCase = gameEngine.getBoard().getSelectedCase();
        Piece selectedPiece = selectedCase != null ? gameEngine.getBoard().getPieceAt(selectedCase.row(), selectedCase.col()) : null;
        if (selectedPiece == null) return false;
        return gameEngine.getBoard().getLegalMoves(selectedPiece).stream().anyMatch(c -> c.row() == position.row() && c.col() == position.col());
    }

    private Piece getPiece(){
        return gameEngine.getBoard().getPieceAt(position.row, position.col);
    }

    private boolean isKingInCheck() {
        Piece piece = getPiece();
        if(!(piece instanceof King)) return false;
        return gameEngine.getBoard().isKingInCheck(piece.getColor());
    }

    private void updateBackground(boolean isHovered) {
        if (isHovered) {
            setBackground(Colors.PRIMARY_LIGHT_1);
        } else if (isMarkedAccessible()) {
            setBackground(Colors.SECONDARY_LIGHT_1);
        } else if (isSelected()) {
            setBackground(Colors.PRIMARY_LIGHT_2);
        } else if (isKingInCheck()) {
            setBackground(Colors.DANGER);
        } else {
            setBackground((position.row + position.col) % 2 == 0 ? Colors.SQUARE_DEFAULT_BACKGROUND : Colors.PRIMARY);
        }
    }

    private void updatePieceDisplay() {
        Piece piece = getPiece();
        if (piece != null) {
            String unicodeSymbol = getUnicodeSymbol(piece);
            jPieceLabel.setText(unicodeSymbol);
            jPieceLabel.setForeground(piece.getColor() == Piece.Color.WHITE ? java.awt.Color.WHITE : java.awt.Color.BLACK);
            jPieceLabel.setShadow(piece.getColor() == Piece.Color.WHITE ? 1 : 0);
        } else {
            jPieceLabel.setText("");
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

