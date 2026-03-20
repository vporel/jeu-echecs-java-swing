package com.vivianhonghoa.chess.viewcontroller.components.game;

import com.vivianhonghoa.chess.model.engine.GameEngine;
import com.vivianhonghoa.chess.model.events.GameEngineEvent;
import com.vivianhonghoa.chess.model.events.GameEngineObserver;
import com.vivianhonghoa.chess.viewcontroller.Colors;
import com.vivianhonghoa.chess.viewcontroller.components.lib.JCustomLabel;
import com.vivianhonghoa.chess.viewcontroller.components.lib.JCustomPanel;
import com.vivianhonghoa.chess.viewcontroller.components.lib.JLabelWrapper;
import com.vivianhonghoa.chess.viewcontroller.helpers.JBorderLayoutHelper;
import com.vivianhonghoa.chess.viewcontroller.helpers.JComponentHelper;

import javax.swing.*;
import java.awt.*;

public class JEvaluation extends JCustomPanel {
    private static final int SIZE = 15;

    private int whiteWidth = 255;

    public JEvaluation() {
        super();
        build();
    }

    private void build(){
        JCustomLabel jPlayer1Evaluation = new JCustomLabel("50", SwingConstants.CENTER);
        jPlayer1Evaluation.setForeground(Colors.WHITE);
        JCustomLabel jPlayer2Evaluation = new JCustomLabel("50", SwingConstants.CENTER);
        jPlayer2Evaluation.setForeground(Colors.WHITE);
        JLabelWrapper jPlayer1EvaluationWrapper = new JLabelWrapper(jPlayer1Evaluation, true);
        JComponentHelper.setFixedWidth(jPlayer1EvaluationWrapper, 40);
        JLabelWrapper jPlayer2EvaluationWrapper = new JLabelWrapper(jPlayer2Evaluation, true);
        JComponentHelper.setFixedWidth(jPlayer2EvaluationWrapper, 40);

        JPanel jRatingPane = getRatingPane();

        this.setLayout(new BorderLayout(5, 5));
        JComponentHelper.setFixedHeight(this, SIZE);
        this.add(jPlayer1EvaluationWrapper, BorderLayout.WEST);
        this.add(jPlayer2EvaluationWrapper, BorderLayout.EAST);
        this.add(jRatingPane, BorderLayout.CENTER);

        GameEngine.getInstance().addObserver(new GameEngineObserver() {
            @Override
            public void onPlayerTurnChanged(GameEngineEvent event) {
                int player1Eval = GameEngine.getInstance().getPlayerContext(1).player().getEvaluation();
                int player2Eval = GameEngine.getInstance().getPlayerContext(2).player().getEvaluation();
                //Normalize the evaluations to a scale of 0 to 100
                int totalEval = player1Eval + player2Eval;
                if(totalEval == 0){
                    player1Eval = 50;
                    player2Eval = 50;
                } else {
                    player1Eval = (int) (((double) player1Eval / totalEval) * 100);
                    player2Eval = (int) (((double) player2Eval / totalEval) * 100);
                }
                jPlayer1Evaluation.setText(String.valueOf(player1Eval));
                jPlayer2Evaluation.setText(String.valueOf(player2Eval));
                //Get the rating pane width and calculate the left margin for the ball
                int ratingPaneWidth = jRatingPane.getWidth();
                whiteWidth = (int) ((player1Eval / 100.0) * ratingPaneWidth);
                JBorderLayoutHelper.replaceComponent(JEvaluation.this, BorderLayout.CENTER, getRatingPane());
            }
        });
    }

    private JPanel getRatingPane(){
        JCustomPanel JWhiteBar = new JCustomPanel();
        JWhiteBar.setBackground(Colors.WHITE);
        JComponentHelper.setFixedSize(JWhiteBar, whiteWidth, SIZE);

        JCustomPanel jWrapper = new JCustomPanel();
        jWrapper.setLayout(new BoxLayout(jWrapper, BoxLayout.X_AXIS));
        jWrapper.setBackground(Colors.BLACK);
        jWrapper.add(JWhiteBar);
        return jWrapper;
    }
}
