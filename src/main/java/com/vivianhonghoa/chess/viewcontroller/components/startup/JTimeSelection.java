package com.vivianhonghoa.chess.viewcontroller.components.startup;

import com.vivianhonghoa.chess.viewcontroller.Colors;
import com.vivianhonghoa.chess.viewcontroller.border.RoundedBorder;
import com.vivianhonghoa.chess.viewcontroller.components.lib.*;
import com.vivianhonghoa.chess.viewcontroller.helpers.JComponentHelper;
import com.vivianhonghoa.chess.viewcontroller.utils.Orientation;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.function.BiConsumer;

public class JTimeSelection extends JCustomPanel {
    private static final String NO_LIMIT_TEXT = "No limit";
    private static final String FIVE_MIN_TEXT = "5 min";
    private static final String TEN_MIN_TEXT = "10 min";
    private static final String CUSTOM_TEXT = "Custom";
    private static final int DEFAULT_TIME_LIMIT = 5; // Default time limit in minutes

    private final BiConsumer<Boolean, Integer> onTimeLimitChange;

    public JTimeSelection(BiConsumer<Boolean, Integer> onTimeLimitChange) {
        super();
        this.onTimeLimitChange = onTimeLimitChange;
        build();
    }

    private void build(){
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
                switch(jButton.getText()){
                    case NO_LIMIT_TEXT:
                        onTimeLimitChange.accept(false, 5);
                        break;
                    case FIVE_MIN_TEXT:
                        onTimeLimitChange.accept(true, 5);
                        break;
                    case TEN_MIN_TEXT:
                        onTimeLimitChange.accept(true, 10);
                        break;
                }
                if(jButton.getText().equals(CUSTOM_TEXT)) {
                    JTimeSelection.this.add(jTimeInputPane);
                    JTimeSelection.this.revalidate();
                }else{
                    JTimeSelection.this.remove(jTimeInputPane);
                    JTimeSelection.this.revalidate();
                }
            });
            jButtonsWrapper.add(jButton);
            if(i < buttons.size() - 1) {
                jButtonsWrapper.add(new JDivider(Orientation.VERTICAL));
            }
        }

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(jButtonsWrapper);
        this.add(Box.createVerticalStrut(10));
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
        jTimeSelectionInput.setValue(DEFAULT_TIME_LIMIT);
        jTimeSelectionInput.setHorizontalAlignment(JFormattedTextField.CENTER);
        JComponentHelper.setFixedSize(jTimeSelectionInput, 40, 30);
        jTimeSelectionInput.addPropertyChangeListener("value", evt -> {
            onTimeLimitChange.accept(true, ((Number) jTimeSelectionInput.getValue()).intValue());
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
