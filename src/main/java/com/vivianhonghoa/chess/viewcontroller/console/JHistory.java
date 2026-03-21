package com.vivianhonghoa.chess.viewcontroller.console;

import com.vivianhonghoa.chess.model.engine.GameEngine;
import com.vivianhonghoa.chess.model.events.GameEngineEvent;
import com.vivianhonghoa.chess.model.events.GameEngineObserver;
import com.vivianhonghoa.chess.viewcontroller.helpers.JComponentHelper;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class JHistory extends JScrollPane {
    private final GameEngine gameEngine;
    private final int playerNumber;

    public JHistory(int playerNumber) {
        this.gameEngine = GameEngine.getInstance();
        this.playerNumber = playerNumber;
        build();
    }

    private void build(){
        JTextArea jHistory = new JTextArea();
        jHistory.setEditable(false);
        jHistory.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JPanel jHistoryWrapper = new JPanel(new BorderLayout(5, 5));
        jHistoryWrapper.add(new JLabel("History"), BorderLayout.NORTH);
        jHistoryWrapper.add(jHistory, BorderLayout.CENTER);
        this.setViewportView(jHistoryWrapper);
        JComponentHelper.setFixedWidth(this, 100);

        gameEngine.addObserver(new GameEngineObserver() {
            @Override
            public void onPlayerTurnChanged(GameEngineEvent event) {
                // Update history display when turn changes (after a move is made)
                List<String> player1History = gameEngine.getPlayerContext(1).history().getFormattedList();
                List<String> player2History = gameEngine.getPlayerContext(2).history().getFormattedList();
                StringBuilder historyText = new StringBuilder();
                for(int i = 0; i < player1History.size(); i++){
                    String move = (i + 1) + ". " + player1History.get(i) + "   " + (i < player2History.size() ? player2History.get(i) : "");
                    historyText.append(move).append("\n");
                }
                jHistory.setText(historyText.toString());
            }
        });
    }
}