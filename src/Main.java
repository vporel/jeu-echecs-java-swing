import viewcontroller.AppFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                AppFrame appFrame = new AppFrame();
                appFrame.build();
                appFrame.setVisible(true);
            }
        });
    }
}