/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package myjob.func.db;

import java.util.EventListener;

/**
 *
 * @author guillermot
 */
public interface exportEventListener extends EventListener {

    public boolean onEvent(exportEventArgs evt);

}
