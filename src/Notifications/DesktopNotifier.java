package Notifications;

import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Luis Detlefsen <lgdetlef@gmail.com>
 */
public class DesktopNotifier implements Notifier {

    private TrayIcon trayIcon;
    private boolean displayNotifications = false;

    public DesktopNotifier() {
        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();

            InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream("Images/TrayIcon.png");

            try {
                BufferedImage img = ImageIO.read(inputStream);
                trayIcon = new TrayIcon(img, "Roboter");
            } catch (IOException ex) {
                Logger.getLogger(DesktopNotifier.class.getName()).log(Level.SEVERE, null, ex);
            }

            trayIcon.setImageAutoSize(true);
            try {
                tray.add(trayIcon);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setDisplayNotifications(boolean b) {
        displayNotifications = b;
    }

    @Override
    public void displayMessage(String msg) {
        if (displayNotifications) {
            trayIcon.displayMessage(msg, null, TrayIcon.MessageType.INFO);
        }
    }
}
