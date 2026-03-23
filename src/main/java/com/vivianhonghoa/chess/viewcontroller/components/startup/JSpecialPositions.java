package com.vivianhonghoa.chess.viewcontroller.components.startup;

import com.vivianhonghoa.chess.model.engine.GameEngine;
import com.vivianhonghoa.chess.model.engine.PresetBoardConfig;
import com.vivianhonghoa.chess.model.pieces.Piece;
import com.vivianhonghoa.chess.players.GraphicalPlayer;
import com.vivianhonghoa.chess.viewcontroller.Colors;
import com.vivianhonghoa.chess.viewcontroller.border.RoundedBorder;
import com.vivianhonghoa.chess.viewcontroller.components.lib.JCustomButtonWithIcon;
import com.vivianhonghoa.chess.viewcontroller.components.lib.JCustomPanel;
import com.vivianhonghoa.chess.viewcontroller.components.lib.JDivider;
import com.vivianhonghoa.chess.viewcontroller.helpers.FontHelper;
import com.vivianhonghoa.chess.viewcontroller.helpers.JComponentHelper;
import com.vivianhonghoa.chess.viewcontroller.layouts.WrapLayout;
import com.vivianhonghoa.chess.viewcontroller.utils.Orientation;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class JSpecialPositions extends JSection {
    private static final String PAWN_PROMOTION = "Pawn promotion";
    private static final String EN_PASSANT = "En Passant";
    private static final String CASTLING = "Castling";
    private static final String CHECK = "Check";
    private static final String CHECKMATE = "Checkmate in 1";
    private static final String STALEMATE = "Stalemate";

    private final GameEngine gameEngine;

    public JSpecialPositions() {
        super();
        this.gameEngine = GameEngine.getInstance();
        build();
    }

    private void build(){
        JLabel jTitleLabel = new JLabel("SPECIAL POSITIONS");
        JComponentHelper.setFontSize(jTitleLabel, 18);
        JComponentHelper.setFontWeightBold(jTitleLabel);
        jTitleLabel.setForeground(new Color(140, 138, 120));

        JCustomPanel jTitlePane = new JCustomPanel();
        jTitlePane.setLayout(new BoxLayout(jTitlePane, BoxLayout.X_AXIS));
        jTitlePane.add(new JDivider(Colors.shadeOfGray(80)));
        jTitlePane.add(jTitleLabel);
        jTitlePane.add(new JDivider(Colors.shadeOfGray(80)));

        JCustomPanel jButtonsPane = new JCustomPanel();
        jButtonsPane.setLayout(new WrapLayout(WrapLayout.CENTER, 10, 10));

        //Configurations
        Map<String, Piece[][]> presetBoards = new HashMap<>();
        presetBoards.put(PAWN_PROMOTION, PresetBoardConfig.pawnPromotion());
        presetBoards.put(EN_PASSANT, PresetBoardConfig.enPassant());
        presetBoards.put(CASTLING, PresetBoardConfig.castling());
        presetBoards.put(CHECK, PresetBoardConfig.check());
        presetBoards.put(CHECKMATE, PresetBoardConfig.checkMate());
        presetBoards.put(STALEMATE, PresetBoardConfig.stalemate());

        Map<String, String> fontAwesomeIconsCodes = Map.of(
            PAWN_PROMOTION, "\uf443",
            EN_PASSANT, "\uf05e",
            CASTLING, "\uf337",
            CHECK, "\uf06a",
            CHECKMATE, "\uf43f",
            STALEMATE, "\uf256"
        );

        for (Map.Entry<String, Piece[][]> entry : presetBoards.entrySet()) {
            JCustomButtonWithIcon jButton = new JCustomButtonWithIcon(
                    entry.getKey(),
                    new JLabel(fontAwesomeIconsCodes.get(entry.getKey())),
                    Orientation.VERTICAL
            );
            jButton.getIcon().setFont(FontHelper.fontAwesome());
            jButton.getIcon().setForeground(Colors.SECONDARY);
            jButton.setSpacing(10);
            jButton.setForeground(Colors.WHITE);
            jButton.setBorder(new RoundedBorder(Colors.SECONDARY, 2, 20));
            jButton.setBackground(Colors.PRIMARY_DARK_2);
            JComponentHelper.changeBackgroundOnMouseHover(jButton, Colors.PRIMARY_DARK_2, Colors.PRIMARY);
            JComponentHelper.setFixedSize(jButton, 120, 70);
            jButton.addActionListener(e -> {
                gameEngine.start(
                        "Player 1",
                        new GraphicalPlayer(Piece.Color.WHITE),
                        "Player 2",
                        new GraphicalPlayer(Piece.Color.BLACK),
                        null,
                        entry.getValue()
                );
            });
            jButtonsPane.add(jButton);
        }
        jContentPane.add(jTitlePane);
        jContentPane.add(Box.createVerticalStrut(10));
        jContentPane.add(jButtonsPane);
    }
}
