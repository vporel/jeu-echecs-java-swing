package com.vivianhonghoa.chess.viewcontroller.components.startup;

import com.vivianhonghoa.chess.model.pieces.Piece;
import com.vivianhonghoa.chess.model.players.Player;
import com.vivianhonghoa.chess.players.ConsolePlayer;
import com.vivianhonghoa.chess.players.GraphicalPlayer;
import com.vivianhonghoa.chess.players.computer.ComputerDifficulty;
import com.vivianhonghoa.chess.players.computer.ComputerPlayer;
import com.vivianhonghoa.chess.utils.QuadConsumer;
import com.vivianhonghoa.chess.viewcontroller.Colors;
import com.vivianhonghoa.chess.viewcontroller.components.lib.*;
import com.vivianhonghoa.chess.viewcontroller.helpers.FontHelper;
import com.vivianhonghoa.chess.viewcontroller.helpers.JComponentHelper;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class JStartButtons extends JCustomPanel {
    private final QuadConsumer <String, Player, String, Player> startGameCallback;

    public JStartButtons(QuadConsumer<String, Player, String, Player> startGameCallback) {
        super();
        this.startGameCallback = startGameCallback;
        build();
    }

    private void build(){
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

            startGameCallback.accept(player1Name, player1, player2Name, player2);
        });

        JCustomButtonWithIcon jPlayerVsComputerButton = new JCustomButtonWithIcon("Player vs Computer", new JLabel("\uf2db"));
        jPlayerVsComputerButton.addActionListener(e -> {
            String player1Name = askPlayerNameInputDialog(1);
            if(player1Name == null) return;
            Player player1 = createHumanPlayer(1);
            if(player1 == null) return;
            ComputerDifficulty difficulty = askComputerDifficultySelectionDialog();
            if(difficulty == null) return;
            startGameCallback.accept(player1Name, player1, "Computer", new ComputerPlayer(Piece.Color.BLACK, difficulty));
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

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(Box.createHorizontalStrut(20));
        this.add(jPlayerVsPlayerButton);
        this.add(Box.createHorizontalStrut(20));
        this.add(jPlayerVsComputerButton);
        this.add(Box.createHorizontalStrut(20));
        JComponentHelper.setFixedHeight(this, 100);
    }

    private ComputerDifficulty askComputerDifficultySelectionDialog(){
        String[] options = {"\u2605 Easy", "\u2605\u2605 Medium", "\u2605\u2605\u2605 Hard"};
        int choice = JOptionPane.showOptionDialog(
                this, "Select computer difficulty:", "Computer Difficulty",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[1]);
        if (choice < 0) return null; // User cancelled
        return switch (choice) {
            case 0 -> ComputerDifficulty.EASY;
            case 2 -> ComputerDifficulty.HARD;
            default -> ComputerDifficulty.MEDIUM;
        };
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

    private enum PlayerMovesMode {
        GRAPHICAL,
        CONSOLE
    }

}
