package com.vivianhonghoa.chess.viewcontroller.console;

import com.vivianhonghoa.chess.model.engine.Board;
import com.vivianhonghoa.chess.model.engine.GameEngine;
import com.vivianhonghoa.chess.model.events.GameEngineEvent;
import com.vivianhonghoa.chess.model.events.GameEngineObserver;
import com.vivianhonghoa.chess.model.pieces.Piece;

import javax.swing.*;
import java.awt.*;

public class JBoardDisplay extends JScrollPane {
    private final GameEngine gameEngine;
    private final JTextArea jDisplay;

    public JBoardDisplay() {
        this.gameEngine = GameEngine.getInstance();
        this.jDisplay = new JTextArea();
        build();
    }

    private void build(){
        jDisplay.setEditable(false);
        jDisplay.setFont(new Font("Monospaced", Font.PLAIN, 20));
        this.setViewportView(jDisplay);

        refresh();

        gameEngine.addObserver(new GameEngineObserver() {

            @Override
            public void onPlayerTurnChanged(GameEngineEvent event) {
                JBoardDisplay.this.refresh();
            }
        });
    }

    private void refresh() {
        // Display board
        Board board = gameEngine.getBoard();
        //Top cols letters
        jDisplay.setText("      ");
        for(int col = 0; col < Board.SIZE; col++){
            char colLetter = (char) ('a' + col);
            jDisplay.append(colLetter + " ");
        }
        jDisplay.append("\n");
        for(int row = 0; row < Board.SIZE; row++){
            jDisplay.append("    " + (row + 1) + " ");
            for(int col = 0; col < Board.SIZE; col++){
                Piece piece = board.getPieceAt(row, col);
                String letter = piece == null ? null : String.valueOf(piece.getLetter());
                String cell = piece == null ? "." : piece.getColor() == Piece.Color.WHITE ? letter.toUpperCase() : letter.toLowerCase();
                jDisplay.append(cell + " ");
            }
            jDisplay.append("\n");
        }
    }
}