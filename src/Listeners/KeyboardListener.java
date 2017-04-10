package Listeners;

import Recorder.EventRecorder;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;


/**
 *
 * @author Luis Detlefsen <lgdetlef@gmail.com>
 */
public class KeyboardListener extends Listener implements NativeKeyListener {
    
    public KeyboardListener(EventRecorder er){
        eventRecorder = er; 
    }
    
    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeEvent) {
        //eventRecorder.addEvent(nativeEvent);  
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
       // System.out.println(nativeEvent.getKeyCode());
        if(nativeEvent.getKeyCode() == 66){ //F8
            if(eventRecorder.isRecording()){
                eventRecorder.stopRecording();
                System.out.println("Stopped recording.");
            }                
            else {
                eventRecorder.startRecording();
                System.out.println("Started recording.");
            }
            return;
        }
              
        if(nativeEvent.getKeyCode() == 67){ //f9
            reproduceSteps();
            return;
        }
        
        if(nativeEvent.getKeyCode() == 68){ //f10
            eventRecorder.clearRecordedEvents();
            return;
        }
              
        eventRecorder.addEvent(nativeEvent);
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeEvent) {
        int i = nativeEvent.getKeyCode();
        if(i==66 || i==87 || i == 67 || i ==68)
            return;
        eventRecorder.addEvent(nativeEvent);         
    }
        
    private void reproduceSteps(){
       eventRecorder.reproduce();
    }
}
