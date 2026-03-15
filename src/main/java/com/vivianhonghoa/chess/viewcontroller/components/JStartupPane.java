package com.vivianhonghoa.chess.viewcontroller.components;

import com.vivianhonghoa.chess.model.GameEngine;
import com.vivianhonghoa.chess.model.PlayerType;
import com.vivianhonghoa.chess.model.PresetBoardConfig;
import com.vivianhonghoa.chess.model.pieces.Piece;
import com.vivianhonghoa.chess.viewcontroller.Colors;
import com.vivianhonghoa.chess.viewcontroller.helpers.JComponentHelper;
import com.vivianhonghoa.chess.viewcontroller.misc.RoundedBorder;
import com.vivianhonghoa.chess.viewcontroller.layouts.WrapLayout;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

public class JStartupPane extends JPanel {
    private final static Color STARTUP_BACKGROUND = new Color(230, 230, 230);
    private final static Color SECTION_BACKGROUND = new Color(210, 210, 210);

    private final GameEngine gameEngine;
    private boolean isLimitedTimeSelected = true;
    private int selectedTimeLimit = 5; // Default time limit in minutes

    public JStartupPane(GameEngine gameEngine) {
        super();
        this.gameEngine = gameEngine;
        build();
    }

    private void build(){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(STARTUP_BACKGROUND);
        this.add(Box.createVerticalGlue());
        this.add(getAppNameAndAuthorsPane());
        this.add(Box.createVerticalStrut(40));
        this.add(getPlayPane());
        this.add(Box.createVerticalStrut(20));
        this.add(getPresetBoardsConfigurationsPane());
        this.add(Box.createVerticalGlue());
    }

    private JPanel getAppNameAndAuthorsPane(){
        JLabel jTitleLabel = new JLabel("Chess");
        jTitleLabel.setForeground(Colors.PRIMARY);
        jTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JComponentHelper.setFontSize(jTitleLabel, 50);
        JComponentHelper.setBold(jTitleLabel);

        JLabel jAuthorsLabel = new JLabel("By Vivian and Hong Hoa");
        jAuthorsLabel.setForeground(Colors.PRIMARY);
        jAuthorsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JComponentHelper.setFontSize(jAuthorsLabel, 20);
        JComponentHelper.setItalic(jAuthorsLabel);

        JPanel jWrapper = new JPanel();
        jWrapper.setLayout(new BoxLayout(jWrapper, BoxLayout.Y_AXIS));
        jWrapper.setBackground(STARTUP_BACKGROUND);
        jWrapper.add(jTitleLabel);
        jWrapper.add(Box.createVerticalStrut(10));
        jWrapper.add(jAuthorsLabel);

        return jWrapper;
    }

    private JPanel getPlayPane(){
        JSection jPlaySection = new JSection("Play");
        JCustomPanel jPlayContentPane = jPlaySection.getContentPane();

        JCustomPanel jWrapper = new JCustomPanel();

        jWrapper.setLayout(new BoxLayout(jWrapper, BoxLayout.X_AXIS));
        jWrapper.add(Box.createHorizontalStrut(20));
        jWrapper.add(getTimeSelectionPane());
        jWrapper.add(Box.createHorizontalGlue());
        jWrapper.add(new JDivider(JDivider.Orientation.VERTICAL).setPadding(20));
        jWrapper.add(Box.createHorizontalGlue());
        jWrapper.add(getStartButtonsPane());
        jWrapper.add(Box.createHorizontalStrut(20));

        jPlayContentPane.add(jWrapper);

        return jPlaySection;
    }

    private JPanel getTimeSelectionPane() {
        JLabel jTitleLabel = new JLabel("Game time limit");
        jTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JComponentHelper.setFontSize(jTitleLabel, 18);
        JComponentHelper.setBold(jTitleLabel);

        JCustomButton jLimitedTimeButton = new JCustomButton("Limited time");
        jLimitedTimeButton.setBackground(Colors.SECONDARY);
        jLimitedTimeButton.setForeground(Colors.WHITE);
        jLimitedTimeButton.setBorder(new RoundedBorder(Colors.SECONDARY, 0, 20));
        jLimitedTimeButton.setWidth(130);

        JCustomButton jUnlimitedTimeButton = new JCustomButton("Unlimited time");
        jUnlimitedTimeButton.setBackground(Colors.WHITE);
        jUnlimitedTimeButton.setBorder(new RoundedBorder(Colors.SECONDARY, 0, 20));
        jUnlimitedTimeButton.setWidth(130);

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


        JPanel jTimeSelectionButtonsPane = new JPanel();
        jTimeSelectionButtonsPane.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        jTimeSelectionButtonsPane.setBackground(SECTION_BACKGROUND);
        jTimeSelectionButtonsPane.setOpaque(false);
        jTimeSelectionButtonsPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        jTimeSelectionButtonsPane.add(jLimitedTimeButton);
        jTimeSelectionButtonsPane.add(jUnlimitedTimeButton);

        NumberFormat format = NumberFormat.getIntegerInstance();
        JCustomFormattedTextField jTimeSelectionInput = new JCustomFormattedTextField(format);
        jTimeSelectionInput.setBorder(null);
        JComponentHelper.setFontSize(jTimeSelectionInput, 16);
        jTimeSelectionInput.setValue(selectedTimeLimit);
        jTimeSelectionInput.setHorizontalAlignment(JFormattedTextField.CENTER);
        JComponentHelper.setFixedSize(jTimeSelectionInput, 40, 40);
        jTimeSelectionInput.addPropertyChangeListener("value", evt -> {
            selectedTimeLimit = ((Number) jTimeSelectionInput.getValue()).intValue();
        });

        JLabel jMinLabel = new JLabel("min");
        JComponentHelper.setFontSize(jMinLabel, 16);

        JCustomPanel jTimeInputRow = new JCustomPanel();
        jTimeInputRow.setRadius(20);
        jTimeInputRow.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 0));
        jTimeInputRow.setBackground(Colors.WHITE);
        JComponentHelper.setFixedSize(jTimeInputRow, 270, 40);
        jTimeInputRow.add(jTimeSelectionInput);
        jTimeInputRow.add(jMinLabel);

        JCustomPanel jWrapper = new JCustomPanel();
        jWrapper.setLayout(new BoxLayout(jWrapper, BoxLayout.Y_AXIS));
        jWrapper.setBackground(SECTION_BACKGROUND);
        jWrapper.setOpaque(false);
        jWrapper.add(Box.createVerticalGlue());
        jWrapper.add(jTitleLabel);
        jWrapper.add(Box.createVerticalStrut(20));
        jWrapper.add(jTimeSelectionButtonsPane);
        jWrapper.add(Box.createVerticalStrut(10));
        jWrapper.add(jTimeInputRow);
        jWrapper.add(Box.createVerticalGlue());
        JComponentHelper.setFixedSize(jWrapper, 400, 200);

        return jWrapper;
    }

    private JPanel getStartButtonsPane() {
        JCustomButton jPlayerVsPlayerButton = new JCustomButton("Player vs Player");
        JComponentHelper.setFixedSize(jPlayerVsPlayerButton, 220, 60);
        JComponentHelper.setFontSize(jPlayerVsPlayerButton, 18);
        jPlayerVsPlayerButton.addActionListener(e -> {
            gameEngine.start(PlayerType.HUMAN, PlayerType.HUMAN, isLimitedTimeSelected ? selectedTimeLimit * 60 : null, null);
        });

        JCustomButton jPlayerVsComputerButton = new JCustomButton("Player vs Computer");
        JComponentHelper.setFixedSize(jPlayerVsComputerButton, 220, 60);
        JComponentHelper.setFontSize(jPlayerVsComputerButton, 18);
        jPlayerVsComputerButton.addActionListener(e -> {
            gameEngine.start(PlayerType.HUMAN, PlayerType.COMPUTER, isLimitedTimeSelected ? selectedTimeLimit * 60 : null, null);
        });


        JCustomPanel jWrapper = new JCustomPanel();
        jWrapper.setLayout(new BoxLayout(jWrapper, BoxLayout.Y_AXIS));
        jWrapper.add(Box.createVerticalGlue());
        jWrapper.add(jPlayerVsPlayerButton);
        jWrapper.add(Box.createVerticalStrut(15));
        jWrapper.add(jPlayerVsComputerButton);
        jWrapper.add(Box.createVerticalGlue());
        JComponentHelper.setFixedHeight(jWrapper, 200);

        return jWrapper;
    }

    private JPanel getPresetBoardsConfigurationsPane(){
        JSection jPresetBoardsSection = new JSection("Preset boards configurations");
        JCustomPanel jPresetBoardsContentPane = jPresetBoardsSection.getContentPane();

        JPanel jButtonsPane = new JPanel();
        jButtonsPane.setBackground(SECTION_BACKGROUND);
        jButtonsPane.setOpaque(false);
        jButtonsPane.setLayout(new WrapLayout(WrapLayout.LEFT, 10, 10));

        //Configurations
        Map<String, Piece[][]> presetBoards = new HashMap<>();
        presetBoards.put("Pawn reaches promotion", PresetBoardConfig.pawnReachesPromotion());
        presetBoards.put("En Passant", PresetBoardConfig.enPassant());
        presetBoards.put("Castling", PresetBoardConfig.castling());
        presetBoards.put("Check", PresetBoardConfig.check());
        presetBoards.put("Checkmate in 1", PresetBoardConfig.checkMate());

        for (Map.Entry<String, Piece[][]> entry : presetBoards.entrySet()) {
            JCustomButton jPresetBoardButton = new JCustomButton(entry.getKey());
            jPresetBoardButton.setWidth(180);
            jPresetBoardButton.addActionListener(e -> {
                gameEngine.start(PlayerType.HUMAN, PlayerType.HUMAN, null, entry.getValue());
            });
            jButtonsPane.add(jPresetBoardButton);
        }

        jPresetBoardsContentPane.add(Box.createVerticalStrut(15));
        jPresetBoardsContentPane.add(jButtonsPane);

        return jPresetBoardsSection;
    }

    private static class JSection extends JPanel {
        private final JCustomPanel jContentPane;

        public JSection(String title) {
            super();
            JLabel jTitleLabel = new JLabel(title);
            JComponentHelper.setFontSize(jTitleLabel, 23);
            JComponentHelper.setBold(jTitleLabel);
            jTitleLabel.setForeground(Colors.SECONDARY_LIGHT_1);
            JLabelWrapper jTitleLabelWrapper = new JLabelWrapper(jTitleLabel, true);
            jTitleLabelWrapper.setBackground(Colors.PRIMARY);
            jTitleLabelWrapper.setRadius(10);
            jTitleLabelWrapper.setPaddingVertical(10);

            jContentPane = new JCustomPanel();
            jContentPane.setRadius(10);
            jContentPane.setLayout(new BoxLayout(jContentPane, BoxLayout.Y_AXIS));
            jContentPane.setBackground(SECTION_BACKGROUND);
            jContentPane.add(jTitleLabelWrapper);

            this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            this.setBackground(STARTUP_BACKGROUND);
            this.add(Box.createHorizontalStrut(100));
            this.add(jContentPane);
            this.add(Box.createHorizontalStrut(100));
        }

        public JCustomPanel getContentPane() {
            return jContentPane;
        }
    }
}
