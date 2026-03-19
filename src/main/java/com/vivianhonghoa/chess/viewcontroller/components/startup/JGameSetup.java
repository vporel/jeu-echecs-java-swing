package com.vivianhonghoa.chess.viewcontroller.components.startup;

import com.vivianhonghoa.chess.model.engine.GameEngine;
import com.vivianhonghoa.chess.model.pieces.Piece;
import com.vivianhonghoa.chess.model.players.Player;
import com.vivianhonghoa.chess.model.players.ComputerDifficulty;
import com.vivianhonghoa.chess.model.players.LoggingPlayerDecorator;
import com.vivianhonghoa.chess.players.ComputerPlayer;
import com.vivianhonghoa.chess.players.ConsolePlayer;
import com.vivianhonghoa.chess.players.GraphicalPlayer;
import com.vivianhonghoa.chess.viewcontroller.Colors;
import com.vivianhonghoa.chess.viewcontroller.components.lib.*;
import com.vivianhonghoa.chess.viewcontroller.console.JConsoleFrame;
import com.vivianhonghoa.chess.viewcontroller.helpers.FontHelper;
import com.vivianhonghoa.chess.viewcontroller.helpers.JComponentHelper;
import com.vivianhonghoa.chess.viewcontroller.border.RoundedBorder;
import com.vivianhonghoa.chess.viewcontroller.utils.Orientation;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.List;

public class JGameSetup extends JSection {
    private static final String NO_LIMIT_TEXT = "No limit";
    private static final String FIVE_MIN_TEXT = "5 min";
    private static final String TEN_MIN_TEXT = "10 min";
    private static final String CUSTOM_TEXT = "Custom";

    private final GameEngine gameEngine;
    private boolean isLimitedTimeSelected = true;
    private int selectedTimeLimit = 5; // Default time limit in minutes

    public JGameSetup() {
        super();
        this.gameEngine = GameEngine.getInstance();
        build();
    }

    private void build(){
        jContentPane.setBackground(Colors.PRIMARY_DARK_2);
        jContentPane.setBorder(new RoundedBorder(Colors.SECONDARY, 2, 20));
        jContentPane.setShadow(5);

        jContentPane.add(getTitlePane());
        jContentPane.add(Box.createVerticalStrut(20));
        jContentPane.add(getSectionTitlePane("WHO IS PLAYING?"));
        jContentPane.add(getStartButtonsPane());
        jContentPane.add(Box.createVerticalStrut(10));
        jContentPane.add(getSectionTitlePane("TIME FORMAT"));
        jContentPane.add(Box.createVerticalStrut(10));
        jContentPane.add(getTimeSelectionPane());
        jContentPane.add(Box.createVerticalStrut(20));

    }

    private JPanel getTitlePane(){
        JLabel jTitleLabel = new JLabel("GAME SETUP");
        JComponentHelper.setFontSize(jTitleLabel, 23);
        JComponentHelper.setFontWeightBold(jTitleLabel);
        jTitleLabel.setForeground(Colors.SECONDARY);
        JLabelWrapper jTitleLabelWrapper = new JLabelWrapper(jTitleLabel, true);
        jTitleLabelWrapper.setBackground(Colors.PRIMARY_DARK_1);
        jTitleLabelWrapper.setRadius(20);
        jTitleLabelWrapper.setPaddingVertical(10);

        return jTitleLabelWrapper;
    }

    private JPanel getSectionTitlePane(String title){
        JLabel jTitleLabel = new JLabel(title);
        JComponentHelper.setFontSize(jTitleLabel, 18);
        JComponentHelper.setFontWeightBold(jTitleLabel);
        jTitleLabel.setForeground(Colors.SECONDARY);

        return new JLabelWrapper(jTitleLabel, true);
    }

    private JPanel getStartButtonsPane() {
        JCustomButtonWithIcon jPlayerVsPlayerButton = new JCustomButtonWithIcon("Player vs Player", new JLabel("\uf0c0"));
        jPlayerVsPlayerButton.addActionListener(e -> {
            String player1Name = askPlayerNameInputDialog(1);
            if(player1Name == null) return;
            Player player1 = createHumanPlayer(1);
            if(player1 == null) return;
            String player2Name = askPlayerNameInputDialog(2);
            if(player2Name == null) return;
            Player player2 = createHumanPlayer(2);
            if(player2 == null) return;

            if(player1 instanceof ConsolePlayer) showConsolePlayerFrame((ConsolePlayer) player1, 1);
            if(player2 instanceof ConsolePlayer) showConsolePlayerFrame((ConsolePlayer) player2, 2);

            gameEngine.start(player1Name, player1, player2Name, player2, isLimitedTimeSelected ? selectedTimeLimit * 60 : null, null);
        });

        JCustomButtonWithIcon jPlayerVsComputerButton = new JCustomButtonWithIcon("Player vs Computer", new JLabel("\uf2db"));
        jPlayerVsComputerButton.addActionListener(e -> {
            String player1Name = askPlayerNameInputDialog(1);
            if(player1Name == null) return;
            Player player1 = createHumanPlayer(1);
            if(player1 == null) return;

            if(player1 instanceof ConsolePlayer) showConsolePlayerFrame((ConsolePlayer) player1, 1);

            String[] options = {"\u2605 Easy", "\u2605\u2605 Medium", "\u2605\u2605\u2605 Hard"};
            int choice = JOptionPane.showOptionDialog(
                this, "Select computer difficulty:", "Computer Difficulty",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[1]);
            if (choice < 0) return; // User cancelled
            ComputerDifficulty difficulty = switch (choice) {
                case 0 -> ComputerDifficulty.EASY;
                case 2 -> ComputerDifficulty.HARD;
                default -> ComputerDifficulty.MEDIUM;
            };
            gameEngine.start(player1Name, player1, "Computer", new LoggingPlayerDecorator(new ComputerPlayer(Piece.Color.BLACK, difficulty)), isLimitedTimeSelected ? selectedTimeLimit * 60 : null, null);
        });

        List<JCustomButtonWithIcon> buttons = List.of(jPlayerVsPlayerButton, jPlayerVsComputerButton);
        for(JCustomButtonWithIcon jButton : buttons) {
            jButton.getIcon().setFont(FontHelper.fontAwesome());
            jButton.getIcon().setForeground(Colors.shadeOfGray(20));
            jButton.setSpacing(10);
            jButton.setRadius(20);
            jButton.setBackground(Colors.SECONDARY_LIGHT_1);
            jButton.setForeground(Colors.shadeOfGray(20));
            JComponentHelper.changeBackgroundOnMouseHover(jButton, Colors.SECONDARY_LIGHT_1, Colors.SECONDARY);
            JComponentHelper.setFixedSize(jButton, 240, 50);
            JComponentHelper.setFontSize(jButton, 20);
        }

        JCustomPanel jWrapper = new JCustomPanel();
        jWrapper.setLayout(new BoxLayout(jWrapper, BoxLayout.X_AXIS));
        jWrapper.add(Box.createHorizontalStrut(20));
        jWrapper.add(jPlayerVsPlayerButton);
        jWrapper.add(Box.createHorizontalStrut(20));
        jWrapper.add(jPlayerVsComputerButton);
        jWrapper.add(Box.createHorizontalStrut(20));
        JComponentHelper.setFixedHeight(jWrapper, 100);

        return jWrapper;
    }

    private String askPlayerNameInputDialog(int playerNumber){
        //Add a default name in the input dialog based on the player number and selected mode
        String defaultName = "Player " + playerNumber;
        String name = (String) JOptionPane.showInputDialog(
                this,
                "Enter name for Player " + playerNumber + ":",
                "Player " + playerNumber + ": Name Input",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                defaultName
        );
        if(name == null) return null;
        name = name.trim();
        return name.isEmpty() ? defaultName : name;
    }

    private void showConsolePlayerFrame(ConsolePlayer player, int playerNumber){
        JConsoleFrame consoleFrame = new JConsoleFrame(player, playerNumber);
        consoleFrame.setVisible(true);
    }

    private Player createHumanPlayer(int playerNumber){
        PlayerMovesMode selectedMode = askPlayerModeSelectionDialog(playerNumber);
        Player player;
        if(selectedMode == PlayerMovesMode.GRAPHICAL) {
            player = new GraphicalPlayer(playerNumber == 1 ? Piece.Color.WHITE : Piece.Color.BLACK);
        } else if (selectedMode == PlayerMovesMode.CONSOLE) {
            player = new ConsolePlayer(playerNumber == 1 ? Piece.Color.WHITE : Piece.Color.BLACK);
        }else{
            return null;
        }
        return player;
    }

    private PlayerMovesMode askPlayerModeSelectionDialog(int playerNumber){
        String[] options = {"Graphical", "Console"};
        int choice = JOptionPane.showOptionDialog(
                this,
                "Select how you want to input your moves:",
                "Player" + playerNumber + ": Mode Selection",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );
        return choice == 0 ? PlayerMovesMode.GRAPHICAL : PlayerMovesMode.CONSOLE;
    }

    private JPanel getTimeSelectionPane() {
        JCustomPanel jWrapper = new JCustomPanel();
        JPanel jTimeInputPane = getTimeInputPane();

        JCustomPanel jButtonsWrapper = new JCustomPanel();
        jButtonsWrapper.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        jButtonsWrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        jButtonsWrapper.setBorder(new RoundedBorder(Colors.shadeOfGray(150), 2, 20));
        jButtonsWrapper.setBackground(Colors.PRIMARY_DARK_2);
        JComponentHelper.setFixedSize(jButtonsWrapper, 500, 50);

        List<JCustomButtonWithIcon> buttons = getTimeButtons();

        for (int i = 0; i < buttons.size(); i++) {
            JCustomButtonWithIcon jButton = buttons.get(i);
            jButton.getIcon().setForeground(Colors.SECONDARY);
            JComponentHelper.setFontSize(jButton.getIcon(), 20);
            jButton.setForeground(jButton.getText().equals(FIVE_MIN_TEXT) ? Colors.SECONDARY : Colors.WHITE);
            if(jButton.getText().equals(FIVE_MIN_TEXT)) {
                jButton.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Colors.SECONDARY));
                JComponentHelper.setFontWeightBold(jButton);
            }
            jButton.setWidth(100);
            jButton.setOpaque(false);
            jButton.addActionListener(e -> {
                isLimitedTimeSelected = !jButton.getText().equals(NO_LIMIT_TEXT);
                jButton.setForeground(Colors.SECONDARY);
                jButton.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Colors.SECONDARY));
                JComponentHelper.setFontWeightBold(jButton);
                for (JCustomButtonWithIcon otherButton : buttons) {
                    if (otherButton != jButton) {
                        otherButton.setForeground(Colors.WHITE);
                        otherButton.setBorder(BorderFactory.createEmptyBorder());
                        JComponentHelper.setFontWeightNormal(otherButton);
                    }
                }
                if(jButton.getText().equals(FIVE_MIN_TEXT)) {
                    selectedTimeLimit = 5;
                } else if(jButton.getText().equals(TEN_MIN_TEXT)) {
                    selectedTimeLimit = 10;
                }
                if(jButton.getText().equals(CUSTOM_TEXT)) {
                    jWrapper.add(jTimeInputPane);
                    jWrapper.revalidate();
                }else{
                    jWrapper.remove(jTimeInputPane);
                    jWrapper.revalidate();
                }
            });
            jButtonsWrapper.add(jButton);
            if(i < buttons.size() - 1) {
                jButtonsWrapper.add(new JDivider(Orientation.VERTICAL));
            }
        }

        jWrapper.setLayout(new BoxLayout(jWrapper, BoxLayout.Y_AXIS));
        jWrapper.add(jButtonsWrapper);
        jWrapper.add(Box.createVerticalStrut(10));

        return jWrapper;
    }

    private static List<JCustomButtonWithIcon> getTimeButtons() {
        JCustomButtonWithIcon jNoLimitButton = new JCustomButtonWithIcon(NO_LIMIT_TEXT, new JLabel("∞"));
        JCustomButtonWithIcon j5MinButton = new JCustomButtonWithIcon(FIVE_MIN_TEXT, new JLabel("\u23F1"));
        JCustomButtonWithIcon j10MinButton = new JCustomButtonWithIcon(TEN_MIN_TEXT, new JLabel("\u23F1"));
        JCustomButtonWithIcon jCustomTimeButton = new JCustomButtonWithIcon(CUSTOM_TEXT, new JLabel("\u23F1"));

        return List.of(
                jNoLimitButton,
                j5MinButton,
                j10MinButton,
                jCustomTimeButton
        );
    }

    private JPanel getTimeInputPane(){
        NumberFormat format = NumberFormat.getIntegerInstance();
        JCustomFormattedTextField jTimeSelectionInput = new JCustomFormattedTextField(format);
        jTimeSelectionInput.setBorder(null);
        jTimeSelectionInput.setBackground(Colors.TRANSPARENT);
        jTimeSelectionInput.setForeground(Colors.WHITE);
        JComponentHelper.setFontSize(jTimeSelectionInput, 15);
        jTimeSelectionInput.setValue(selectedTimeLimit);
        jTimeSelectionInput.setHorizontalAlignment(JFormattedTextField.CENTER);
        JComponentHelper.setFixedSize(jTimeSelectionInput, 40, 30);
        jTimeSelectionInput.addPropertyChangeListener("value", evt -> {
            selectedTimeLimit = ((Number) jTimeSelectionInput.getValue()).intValue();
        });

        JLabel jMinLabel = new JLabel("min");
        JComponentHelper.setFontSize(jMinLabel, 16);
        jMinLabel.setForeground(Colors.WHITE);

        JCustomPanel jWrapper = new JCustomPanel();
        jWrapper.setRadius(20);
        jWrapper.setBackground(Colors.TRANSPARENT);
        jWrapper.setBorder(new RoundedBorder(Colors.SECONDARY, 1, 20));
        jWrapper.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 0));
        JComponentHelper.setFixedSize(jWrapper, 100, 40);
        jWrapper.add(jTimeSelectionInput);
        jWrapper.add(jMinLabel);

        return jWrapper;
    }

    private enum PlayerMovesMode {
        GRAPHICAL,
        CONSOLE
    }

}
