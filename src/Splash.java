import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;

public class Splash extends JWindow {
    public Splash(String imagePath) {
        JLabel splashLabel = new JLabel(new ImageIcon(imagePath));
        getContentPane().add(splashLabel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
