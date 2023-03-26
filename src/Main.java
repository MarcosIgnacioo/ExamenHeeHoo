import javax.swing.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Splash splashScreen = new Splash("src/haza.gif");
        Thread.sleep(1); // Splash the start of the main application for 5 seconds
        SwingUtilities.invokeLater(() -> {
            // create and display the main application window here
        });
        splashScreen.dispose();
        ExamenVentana popo = new ExamenVentana();
    }
}
