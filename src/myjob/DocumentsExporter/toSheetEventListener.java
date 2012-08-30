/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package myjob.DocumentsExporter;

import java.util.EventListener;

/**
 *
 * @author guillermot
 */
public interface toSheetEventListener extends EventListener {

    public boolean onEvent(toSheetEventArgs evt);

}
