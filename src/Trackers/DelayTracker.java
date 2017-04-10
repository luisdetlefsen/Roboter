package Trackers;

import java.time.Duration;
import java.time.Instant;

/**
 *
 * @author Luis Detlefsen <lgdetlef@gmail.com>
 */
public class DelayTracker {

    private Instant lastEvent;

    public void markEvent() {
        lastEvent = Instant.now();
    }

    public long getMillisFromLastEvent() {
        if (lastEvent == null) {
            return 0;
        }
       
        return Duration.between(lastEvent, Instant.now()).toMillis();
    }

    public void reset() {
        lastEvent = null;
    }

}
