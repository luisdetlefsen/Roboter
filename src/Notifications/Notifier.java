package Notifications;

/**
 *
 * @author Luis Detlefsen <lgdetlef@gmail.com>
 */
public interface Notifier {
  
    public void setDisplayNotifications(boolean b);
    public void displayMessage(String msg);
    
}
