package observer;

import filehandler.Filehandler;
import java.util.*;

/**
 * Class used to notify on event of interest.
 * @author Micke
 */
public class Notifier extends Observable {

    Filehandler fh = new Filehandler();

    /**
     * sets changed on events.
     */
    public synchronized void setChanged() {
        super.setChanged();
    }

    /**
     * Notify observers.
     * @param args The message that is going to be print and logged.
     */
    public synchronized void notifyObservers(Object args) {
        System.out.println(args);
        fh.writeLogToFile(args);
    }
}
