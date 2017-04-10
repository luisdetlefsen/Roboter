package Listeners;

import Recorder.EventRecorder;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;

/**
 *
 * @author Luis Detlefsen <lgdetlef@gmail.com>
 */
public class MouseListener extends Listener implements NativeMouseListener {

    public MouseListener(EventRecorder er) {
        this.eventRecorder = er;
    }

    @Override
    public void nativeMouseClicked(NativeMouseEvent nme) {
       // eventRecorder.addEvent(nme);
    }

    @Override
    public void nativeMousePressed(NativeMouseEvent nme) {
        eventRecorder.addEvent(nme);
    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent nme) {
        eventRecorder.addEvent(nme);
    }

}
