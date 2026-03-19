package com.vivianhonghoa.chess.viewcontroller.console;

import com.vivianhonghoa.chess.model.engine.GameEngine;
import com.vivianhonghoa.chess.model.engine.History;
import com.vivianhonghoa.chess.model.events.GameEngineEvent;
import com.vivianhonghoa.chess.model.events.GameEngineObserver;
import com.vivianhonghoa.chess.model.events.HistoryEvent;
import com.vivianhonghoa.chess.model.events.HistoryObserver;
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
            public void onGameStarted(GameEngineEvent event) {
                //History events
                History history = gameEngine.getPlayerContext(playerNumber).history();
                history.addObserver(new HistoryObserver() {
                    @Override
                    public void onChange(HistoryEvent event) {
                        String historyText = "";
                        List<String> moves = history.getFormattedList();
                        for(int i = 0; i < moves.size(); i++){
                            String move = (i + 1) + ". " + moves.get(i);
                            historyText += move + "\n";
                        }
                        jHistory.setText(historyText);
                    }
                });

            }
        });
    }
}