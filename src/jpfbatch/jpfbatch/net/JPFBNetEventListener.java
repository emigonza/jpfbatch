/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jpfbatch.net;

// Declare the listener class. It must extend EventListener.
import java.util.EventListener;
import java.util.List;

// A class must implement this interface to get MyEvents.
public interface JPFBNetEventListener extends EventListener {

    public List<String> onEjecutar(JPFBNetEventArgs evt);
}
