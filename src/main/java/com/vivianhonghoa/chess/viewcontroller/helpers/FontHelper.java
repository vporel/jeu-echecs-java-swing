package com.vivianhonghoa.chess.viewcontroller.helpers;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class FontHelper {

    public static Font fontAwesome() {
        try {
            InputStream stream = FontHelper.class.getResourceAsStream("/fonts/Font Awesome 7 Free-Solid-900.otf");
            if (stream == null) throw new IOException("Font file not found in classpath: /fonts/Font Awesome 7 Free-Solid-900.otf");
            return Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(20f);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
