import javax.swing.*;
import java.awt.BorderLayout;

public class ScreenSaverFrame extends JFrame {
    public ScreenSaverFrame() {
        super("ScreenSaver");

        ScreenSaverPanel panel = new ScreenSaverPanel();
        add(panel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Center the window on screen
        setVisible(true);
    }
}
