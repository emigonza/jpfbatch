/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjob.collectionsTools;

import java.util.EventListener;

/**
 *
 * @author Administrador
 */
public interface CollectionTableModelNewObjectListener<T> extends EventListener {

    public T newObj(ObjListTableModel<T> table);

}
