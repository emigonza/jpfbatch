/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjob.collectionsTools;

import java.util.Collection;

import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

public class ComboBoxCollectionModel extends AbstractListModel implements ComboBoxModel {
    
    private Object selectedItem;
    private Collection internalData;

    public ComboBoxCollectionModel(Collection items) {
        internalData = items;
    }

    public Object getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(Object newValue) {
        selectedItem = newValue;
    }

    public int getSize() {
        return internalData.size();
    }

    public Object getElementAt(int i) {
        return ((List) internalData).get(i);
    }
}
