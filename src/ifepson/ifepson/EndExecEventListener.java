/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ifepson.ifepson;

import java.util.EventListener;

/**
 *
 * @author guillermot
 */
public interface EndExecEventListener extends EventListener {
    public Boolean onEvent(EndExecEventArgs evt);
}
