package com.vivianhonghoa.chess.viewcontroller;

import java.awt.*;

public final class Colors {
    public static final Color TRANSPARENT = new Color(0, 0, 0, 0);
    public static final Color APP_BACKGROUND = new Color(255, 255, 255);
    public static final Color SQUARE_DEFAULT_BACKGROUND = new Color(200, 200, 200);
    public static final Color PRIMARY = new Color(44, 44, 73);
    public static final Color PRIMARY_DARK_1 = new Color(20, 20, 35);
    public static final Color PRIMARY_DARK_2 = new Color(30, 30, 50);
    public static final Color PRIMARY_LIGHT_1 = new Color(115, 115, 156);
    public static final Color PRIMARY_LIGHT_2 = new Color(147, 147, 194);
    public static final Color SECONDARY = new Color(171, 137, 39);
    public static final Color SECONDARY_DARK_1 = new Color(163, 127, 20);
    public static final Color SECONDARY_LIGHT_1 = new Color(238, 189, 44);

    public static final Color WHITE = new Color(255, 255, 255);
    public static final Color BLACK = new Color(0, 0, 0);
    public static final Color DANGER = new Color(200, 50, 50);
    public static final Color LIGHTGRAY = new Color(230, 230, 230);

    public static Color shadeOfGray(int shade) {
        return new Color(shade, shade, shade);
    }
}
