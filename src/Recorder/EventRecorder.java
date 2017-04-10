package Recorder;

import Events.DelayEvent;
import Notifications.Notifier;
import Trackers.DelayTracker;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeInputEvent;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.mouse.NativeMouseEvent;

/**
 *
 * @author Luis Detlefsen <lgdetlef@gmail.com>
 */
public class EventRecorder {

    private final List<? super Object> recordedEvents = new ArrayList<>();
    private boolean isRecording = false;
    private boolean recordDelays = true;
    private final DelayTracker delayTracker = new DelayTracker();
    private ExecutorService executor;
    private boolean isPlaying = false;
    private int delayAfterIteration = 0;
    private int numberOfIterations = 1;
    private Notifier notifier;
    
    
    public void setNotifier(Notifier n){
        notifier = n;
    }
    
    public void setRecordDelays(boolean b) {
        recordDelays = b;
    }

    public void clearRecordedEvents() {
        if (executor != null) {
            executor.shutdownNow();
        }
        isRecording = false;
        isPlaying = false;
        recordedEvents.clear();
        delayTracker.reset();
        notifier.displayMessage("Events cleared");
    }

    private void reproduceSteps(int iterations, int delayAfterIteration) {
        if (isPlaying) {
            return;
        }
        notifier.displayMessage("Reproducing recorded activity");
        isPlaying = true;
        try {
            Robot robot = new Robot();
            for (int i = 0; i < iterations; i++) {
                recordedEvents.stream().forEach(x -> {
                    if (x instanceof NativeKeyEvent) {
                        GlobalScreen.postNativeEvent((NativeKeyEvent) x);
                    } else if (x instanceof NativeMouseEvent) {
                        NativeMouseEvent nme = (NativeMouseEvent) x;
                        //GlobalScreen.postNativeEvent(nme); //Not accurate

                        int buttonId = nme.getButton();

                        robot.mouseMove(nme.getX(), nme.getY());
                        if (NativeMouseEvent.BUTTON1 == buttonId) {
                            if (nme.getID() == NativeMouseEvent.NATIVE_MOUSE_PRESSED) {
                                robot.mousePress(InputEvent.BUTTON1_MASK);
                            } else if (nme.getID() == NativeMouseEvent.NATIVE_MOUSE_RELEASED) {
                                robot.mouseRelease(InputEvent.BUTTON1_MASK);
                            }
                        } else if (NativeMouseEvent.BUTTON2 == buttonId) {
                            if (nme.getID() == NativeMouseEvent.NATIVE_MOUSE_PRESSED) {
                                robot.mousePress(InputEvent.BUTTON3_MASK);
                            } else if (nme.getID() == NativeMouseEvent.NATIVE_MOUSE_RELEASED) {
                                robot.mouseRelease(InputEvent.BUTTON3_MASK);
                            }
                        }
                        // System.out.println("Reproducing: " + ((NativeMouseEvent) x).paramString());
                    } else if (x instanceof DelayEvent) {
                        try {
                            TimeUnit.MILLISECONDS.sleep(((DelayEvent) x).getDelay());
                        } catch (InterruptedException ex) {
                            Logger.getLogger(EventRecorder.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        Logger.getLogger(EventRecorder.class.getName()).log(Level.SEVERE, "Unsupported event: " + x.toString());
                    }
                });
                try {
                    TimeUnit.SECONDS.sleep(delayAfterIteration);
                } catch (InterruptedException ex) {
                    Logger.getLogger(EventRecorder.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (AWTException ex) {
            Logger.getLogger(EventRecorder.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            isPlaying = false;
            notifier.displayMessage("Completed");
        }
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void reproduce() {
        reproduce(numberOfIterations, delayAfterIteration);
    }

    public void reproduce(int iterations, int delayAfterIteration) {
        executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            isRecording = false; //make sure it is not recording.            
            reproduceSteps(iterations, delayAfterIteration);
        });
    }

    public void addEvent(NativeInputEvent e) {
        if (!isRecording) {
            return;
        }
        if (recordDelays) {
            recordedEvents.add(new DelayEvent(delayTracker.getMillisFromLastEvent()));
        }
        recordedEvents.add(e);
        if (recordDelays) {
            delayTracker.markEvent();
        }
    }

    public boolean isRecording() {
        return isRecording;
    }

    public void stopRecording() {
        isRecording = false;
        notifier.displayMessage("Stopped recording");
    }

    public void startRecording() {
        isRecording = true;
        notifier.displayMessage("Started recording");
    }

    /**
     * @param delayAfterIteration the delayAfterIteration to set
     */
    public void setDelayAfterIteration(int delayAfterIteration) {
        this.delayAfterIteration = delayAfterIteration;
    }

    /**
     * @param numberOfIterations the numberOfIterations to set
     */
    public void setNumberOfIterations(int numberOfIterations) {
        this.numberOfIterations = numberOfIterations;
    }

}
