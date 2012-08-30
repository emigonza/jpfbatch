/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package myjob.func.db;

import java.util.HashMap;

/**
 *
 * @author guillermot
 */
public class DataParameterCollection extends HashMap<String, IDbCommandParameter> {

    public DataParameterCollection() {
    }

    public void addWithValue(String parName, Object value) {
        IDbCommandParameter loc_p = (IDbCommandParameter) new DbCommandParameter();
        loc_p.setParameterName(parName);
        loc_p.setValue(value);
        this.put(parName, loc_p);
    }
}
