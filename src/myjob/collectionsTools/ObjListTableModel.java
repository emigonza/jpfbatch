/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjob.collectionsTools;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.EventListenerList;
import javax.swing.table.AbstractTableModel;
import myjob.func.classutils.ReflectionFunc;

/**
 *
 * @author Administrador
 */
public final class ObjListTableModel<T> extends AbstractTableModel {

    Class tipo = null;
    Collection<T> data = null;
    String[] properties = null;
    Map<Integer, Integer> indexes = new HashMap<Integer, Integer>();
    boolean readOnly = false;

    public ObjListTableModel(Class<T> tipo, String[] properties, Collection<T> data) {
        this.setData(data);
        this.setTipo(tipo);
        this.setProperties(properties);
    }

    protected EventListenerList NuevolistenerList = new EventListenerList();

    public void addNuevoEventListener(CollectionTableModelNewObjectListener listener) {
        NuevolistenerList.add(CollectionTableModelNewObjectListener.class, listener);
    }

    public void removeNuevoEventListener(CollectionTableModelNewObjectListener listener) {
        NuevolistenerList.remove(CollectionTableModelNewObjectListener.class, listener);
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public Collection<T> getData() {
        return data;
    }

    public void setData(Collection<T> data) {
        this.data = data;
    }

    public String[] getProperties() {
        return properties;
    }

    public void setProperties(String[] properties) {
        this.properties = properties;

      
        for (Integer loc_Conta1 = 0; loc_Conta1 < properties.length; loc_Conta1++) {
            for (Integer loc_Conta = 0; loc_Conta < tipo.getDeclaredFields().length; loc_Conta++) {

                if (tipo.getDeclaredFields()[loc_Conta].getName().equals(properties[loc_Conta1])) {
                    indexes.put(loc_Conta1, loc_Conta);
                    break;
                }
            }
        }

    }

    int indexCorregido(Integer index) {

        try {
            return indexes.get(index);
        } catch (Exception e) {
            Logger.getLogger(ObjListTableModel.class.getName()).log(Level.SEVERE, "indice: " + index.toString(), e);
        }

        return -1;
    }

    public Class getTipo() {
        return tipo;
    }

    public void setTipo(Class tipo) {
        this.tipo = tipo;
    }

    public int getRowCount() {
        return data.size() + 1;
    }

    public int getColumnCount() {
        if (properties == null) {
            return 1;
        }
        return this.properties.length;
    }

    @Override
    public String getColumnName(int arg0) {
        return tipo.getDeclaredFields()[indexCorregido(arg0)].getName();
    }

    @Override
    public Class<?> getColumnClass(int arg0) {

        Class<?> loc_c = tipo.getDeclaredFields()[indexCorregido(arg0)].getType();

        if(loc_c.equals(float.class)) {
            loc_c = Float.class;
        }
        if(loc_c.equals(double.class)) {
            loc_c = Double.class;
        }

        return loc_c;
    }

    @Override
    public boolean isCellEditable(int arg0, int arg1) {
        return !readOnly;
    }

    @Override
    public Object getValueAt(int arg0, int arg1) {

        if(data.size() == arg0) {
            return null;
        }

        return ReflectionFunc.evaluateProperty(((List<T>) data).get(arg0), properties[arg1]);
    }

    @Override
    public void setValueAt(Object arg0, int arg1, int arg2) {

        if(data.size() == arg1) {
            if(this.NuevolistenerList.getListenerList().length > 0) {
                //data.add(((CollectionTableModelNewObjectListener<T>) this.NuevolistenerList.getListenerList()[1]).newObj(this));
                ((CollectionTableModelNewObjectListener<T>) this.NuevolistenerList.getListenerList()[1]).newObj(this);
            } else {
                return;
            }
        }

        ReflectionFunc.setProperty(((List<T>) data).get(arg1), properties[arg2], arg0);
    }
}
