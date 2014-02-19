/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observer;

import filehandler.Filehandler;
import java.util.*;

public class Notifier extends Observable {

    Filehandler fh = new Filehandler();

    public synchronized void setChanged() {
        super.setChanged();
    }

    public synchronized void notifyObservers(Object args) {
        System.out.println(args);
        fh.log(args);
    }
}
