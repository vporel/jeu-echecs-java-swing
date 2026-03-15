package com.vivianhonghoa.chess.viewcontroller.components.game.playerpane;

import com.vivianhonghoa.chess.model.GameEngine;
import com.vivianhonghoa.chess.model.events.BoardEvent;
import com.vivianhonghoa.chess.model.events.BoardObserver;
import com.vivianhonghoa.chess.model.pieces.Piece;
import com.vivianhonghoa.chess.viewcontroller.Colors;
import com.vivianhonghoa.chess.viewcontroller.components.lib.JCustomPanel;
import com.vivianhonghoa.chess.viewcontroller.components.lib.JDivider;
import com.vivianhonghoa.chess.viewcontroller.components.lib.JLabelWrapper;
import com.vivianhonghoa.chess.viewcontroller.helpers.JComponentHelper;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class JCapturedPieces extends JCustomPanel {
    private final int playerNumber;
    private final GameEngine gameEngine;

    public JCapturedPieces(int playerNumber, GameEngine gameEngine) {
        super();
        this.playerNumber = playerNumber;
        this.gameEngine = gameEngine;
        build();
    }

    private void build(){
        JLabel jCapturedLabel = new JLabel("Captured pieces", SwingConstants.CENTER);
        jCapturedLabel.setForeground(Colors.SECONDARY);
        JComponentHelper.setFontWeightBold(jCapturedLabel);
        JLabelWrapper jCapturedLabelWrapper = new JLabelWrapper(jCapturedLabel, true);
        jCapturedLabelWrapper.setPaddingVertical(10);

        JCustomPanel capturedPiecesPanel = new JCustomPanel();
        capturedPiecesPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 2));
        capturedPiecesPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JComponentHelper.setFixedHeight(capturedPiecesPanel, 50);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(jCapturedLabelWrapper);
        this.add(new JDivider(Colors.shadeOfGray(70)));
        this.add(capturedPiecesPanel);

        // Board events - update captured pieces display
        gameEngine.getBoard().addObserver(new BoardObserver() {
            @Override
            public void onPieceCaptured(BoardEvent event) {
                updateCapturedPieces(capturedPiecesPanel);
            }
        });
    }

    private List<Piece> getCapturedPieces() {
        if (playerNumber == 1) {
            return gameEngine.getBoard().getCapturedByWhite();
        } else {
            return gameEngine.getBoard().getCapturedByBlack();
        }
    }

    private void updateCapturedPieces(JPanel panel) {
        panel.removeAll();
        for (Piece piece : getCapturedPieces()) {
            JLabel label = new JLabel(piece.getUnicodeSymbol());
            JComponentHelper.setFontSize(label, 30);
            label.setForeground(piece.getColor() == Piece.Color.WHITE ? Colors.WHITE : Colors.BLACK);
            panel.add(label);
        }
        panel.revalidate();
        panel.repaint();
    }
}
