package Events;

/**
 *
 * @author Luis Detlefsen <lgdetlef@gmail.com>
 */
public class DelayEvent {
    private long delay;
    
    public DelayEvent(long d){
        delay = d;
    }
    
    /**
     * @return the delay
     */
    public long getDelay() {
        return delay;
    }

    /**
     * @param delay the delay to set
     */
    public void setDelay(long delay) {
        this.delay = delay;
    }
}
