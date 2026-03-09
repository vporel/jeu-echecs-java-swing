package com.vivianhonghoa.chess.viewcontroller.components;

import com.vivianhonghoa.chess.model.GameEngine;
import com.vivianhonghoa.chess.model.Player;
import com.vivianhonghoa.chess.viewcontroller.Colors;
import com.vivianhonghoa.chess.viewcontroller.helpers.JComponentHelper;

import javax.swing.*;
import java.awt.*;

public class JStartupPane extends JPanel {
    private final GameEngine gameEngine;
    private boolean isLimitedTimeSelected = true;
    private int selectedTimeLimit = 5; // Default time limit in minutes

    public JStartupPane(GameEngine gameEngine) {
        super();
        this.gameEngine = gameEngine;
        build();
    }

    private void build(){
        // Simple BoxLayout + glue centers the content vertically with very few lines
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Colors.PRIMARY);

        JLabel jTitleLabel = new JLabel("Chess");
        jTitleLabel.setForeground(Colors.WHITE);
        jTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JComponentHelper.setFontSize(jTitleLabel, 50);
        JComponentHelper.setBold(jTitleLabel);

        JLabel jAuthorsLabel = new JLabel("By Vivian and Hong Hoa");
        jAuthorsLabel.setForeground(Colors.WHITE);
        jAuthorsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JComponentHelper.setFontSize(jAuthorsLabel, 20);
        JComponentHelper.setItalic(jAuthorsLabel);

        this.add(Box.createVerticalGlue());
        this.add(jTitleLabel);
        this.add(Box.createVerticalStrut(10));
        this.add(jAuthorsLabel);
        this.add(Box.createVerticalStrut(25));
        this.add(getTimeSelectionPane());
        this.add(Box.createVerticalStrut(15));
        this.add(getStartButtonsPane());
        this.add(Box.createVerticalGlue());
    }

    private JPanel getTimeSelectionPane() {
        JPanel jTimeSelectionPane = new JPanel();
        jTimeSelectionPane.setLayout(new BoxLayout(jTimeSelectionPane, BoxLayout.Y_AXIS));
        jTimeSelectionPane.setBackground(Colors.PRIMARY);

        JLabel jTitleLabel = new JLabel("Temps de jeu");
        jTitleLabel.setForeground(Colors.SECONDARY_LIGHT_1);
        jTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JComponentHelper.setFontSize(jTitleLabel, 20);
        JComponentHelper.setBold(jTitleLabel);

        JPanel jTimeSelectionButtonsPane = new JPanel();
        jTimeSelectionButtonsPane.setBackground(Colors.PRIMARY);
        jTimeSelectionButtonsPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

        JCustomButton jLimitedTimeButton = new JCustomButton("Temps limité");
        jLimitedTimeButton.setBackground(Colors.SECONDARY);
        jLimitedTimeButton.setForeground(Colors.WHITE);
        jLimitedTimeButton.setBorder(BorderFactory.createLineBorder(Colors.SECONDARY, 2));
        JCustomButton jUnlimitedTimeButton = new JCustomButton("Temps illimité");
        jUnlimitedTimeButton.setBorder(BorderFactory.createLineBorder(Colors.SECONDARY, 2));
        jLimitedTimeButton.addActionListener(e -> {
            isLimitedTimeSelected = true;
            jLimitedTimeButton.setBackground(Colors.SECONDARY);
            jLimitedTimeButton.setForeground(Colors.WHITE);
            jUnlimitedTimeButton.setBackground(Colors.WHITE);
            jUnlimitedTimeButton.setForeground(Colors.BLACK);
        });

        jUnlimitedTimeButton.addActionListener(e -> {
            isLimitedTimeSelected = false;
            jLimitedTimeButton.setBackground(Colors.WHITE);
            jLimitedTimeButton.setForeground(Colors.BLACK);
            jUnlimitedTimeButton.setBackground(Colors.SECONDARY);
            jUnlimitedTimeButton.setForeground(Colors.WHITE);
        });

        jTimeSelectionButtonsPane.add(jLimitedTimeButton);
        jTimeSelectionButtonsPane.add(jUnlimitedTimeButton);

        JFormattedTextField jTimeSelectionInput = new JFormattedTextField(String.valueOf(selectedTimeLimit));
        JComponentHelper.setFixedSize(jTimeSelectionInput, 210, 40);

        jTimeSelectionPane.add(jTitleLabel);
        jTimeSelectionPane.add(Box.createVerticalStrut(10));
        jTimeSelectionPane.add(jTimeSelectionButtonsPane);
        jTimeSelectionPane.add(Box.createVerticalStrut(10));
        jTimeSelectionPane.add(jTimeSelectionInput);
        return jTimeSelectionPane;
    }

    private JPanel getStartButtonsPane() {
        JPanel jStartButtonsPane = new JPanel();
        jStartButtonsPane.setBackground(Colors.PRIMARY);

        JCustomButton jPlayerVsPlayerButton = new JCustomButton("Joueur vs Joueur");
        JComponentHelper.setPreferredSize(jPlayerVsPlayerButton, 200, 60);
        JComponentHelper.setFontSize(jPlayerVsPlayerButton, 20);
        jPlayerVsPlayerButton.addActionListener(e -> {
            gameEngine.start(Player.HUMAN, Player.HUMAN, isLimitedTimeSelected ? selectedTimeLimit * 60 : null);
        });

        JCustomButton jPlayerVsComputerButton = new JCustomButton("Joueur vs Ordinateur");
        JComponentHelper.setPreferredSize(jPlayerVsComputerButton, 200, 60);
        JComponentHelper.setFontSize(jPlayerVsComputerButton, 20);
        jPlayerVsComputerButton.addActionListener(e -> {
            gameEngine.start(Player.HUMAN, Player.COMPUTER, isLimitedTimeSelected ? selectedTimeLimit * 60 : null);
        });

        jStartButtonsPane.add(jPlayerVsPlayerButton);
        jStartButtonsPane.add(jPlayerVsComputerButton);
        return jStartButtonsPane;
    }
}
