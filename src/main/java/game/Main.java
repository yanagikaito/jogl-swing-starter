package game;

import java.awt.*;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("大三角形3枚でトライフォース");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            TriforceGLPanel panel = new TriforceGLPanel();
            panel.setPreferredSize(new Dimension(900, 700));
            frame.getContentPane().add(panel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            panel.start();
        });
    }
}