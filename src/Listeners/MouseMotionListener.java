package Listeners;

import Recorder.EventRecorder;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseMotionListener;

/**
 *
 * @author Luis Detlefsen <lgdetlef@gmail.com>
 */
public class MouseMotionListener extends Listener implements NativeMouseMotionListener {

    public MouseMotionListener(EventRecorder e) {
        this.eventRecorder = e;
    }

    @Override
    public void nativeMouseMoved(NativeMouseEvent nativeEvent) {       
        eventRecorder.addEvent(nativeEvent);
    }

    @Override
    public void nativeMouseDragged(NativeMouseEvent nativeEvent) {
        eventRecorder.addEvent(nativeEvent);
    }

}
