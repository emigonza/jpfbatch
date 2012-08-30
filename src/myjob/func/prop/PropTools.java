/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjob.func.prop;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

/**
 *
 * @author guillermot
 */
public class PropTools {

    public static String getIndexed(Properties p, String key, int index) {
        key = key + "[" + index + "]";
        return p.getProperty(key);
    }

    public static Object[] getObjectArray(Properties p, String key) {
        Object[] retVal = new Object[PropTools.countIndexed(p, key)];

        for (int i = 0; i < retVal.length; i++) {
            retVal[i] = PropTools.getIndexedObject(p, key, i);
        }

        return retVal;
    }

    public static String[] getArray(Properties p, String key) {
        String[] retVal = new String[PropTools.countIndexed(p, key)];

        for (int i = 0; i < retVal.length; i++) {
            retVal[i] = PropTools.getIndexed(p, key, i);
        }

        return retVal;
    }
    
    
    public static Object getObject(Properties p, String key) {


        Class clazz = getPropertyClass(p, key);
        String value = p.getProperty(key, null);
                
        if (clazz == null) {
            return value;
        }

        try {

            if (clazz == Byte.class) {
                return Byte.parseByte(value);
            } else if (clazz == Integer.class) {
                return Integer.parseInt(value);
            } else if (clazz == Double.class) {
                return Double.parseDouble(value);
            } else if (clazz == Float.class) {
                return Float.parseFloat(value);
            } else if (clazz == java.lang.Boolean.class) {

                if (value.trim().equals("1")) {
                    return true;
                }
                if (value.trim().equals("0")) {
                    return false;
                }

                return Boolean.parseBoolean(value);
            } else if (clazz == String.class) {
                return value;
            }
        } catch (Exception ex) {
            Logger.getLogger(PropTools.class).log(Level.ERROR, "Error al obtener objeto de " + value + " con clave " + key, ex);
        }
        
        return value;
    }

    public static Object getIndexedObject(Properties p, String key, int index) {


        Class clazz = getPropertyClass(p, key);
        String value = getIndexed(p, key, index);

        if (clazz == null) {
            return value;
        }

        try {

            if (clazz == Byte.class) {
                return Byte.parseByte(value);
            } else if (clazz == Integer.class) {
                return Integer.parseInt(value);
            } else if (clazz == Double.class) {
                return Double.parseDouble(value);
            } else if (clazz == Float.class) {
                return Float.parseFloat(value);
            } else if (clazz == java.lang.Boolean.class) {

                if (value.trim().equals("1")) {
                    return true;
                }
                if (value.trim().equals("0")) {
                    return false;
                }

                return Boolean.parseBoolean(value);
            } else if (clazz == String.class) {
                return value;
            }
        } catch (Exception ex) {
            Logger.getLogger(PropTools.class).log(Level.ERROR, "Error al obtener objeto indexado de " + value + " con clave " + key + " con indice " + index, ex);
        }

        return value;
    }

    public static int countIndexed(Properties p, String key) {
        int retVal = 0;
        for (Object o : p.keySet()) {
            if (o.toString().startsWith(key + "[") && !o.toString().toUpperCase().startsWith(key.toUpperCase() + "[CLASS]")) {
                retVal++;
            }
        }
        return retVal;
    }

    public static Class getPropertyClass(Properties p, String key) {
        Class retVal = null;
        for (Object o : p.keySet()) {
            if (o.toString().toUpperCase().startsWith(key.toUpperCase() + "[CLASS]")) {
                try {
                    return Class.forName(p.getProperty((String) o));
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(PropTools.class.getName()).log(Level.ERROR, "Error al obtener clase de propiedad " + key, ex);
                }
            }
        }
        return retVal;
    }

    public static List<String> getNames(Properties p) {

        List<String> retVal = new ArrayList<String>();

        String key;

        for (Object o : p.keySet()) {

            key = o.toString();

            if (key.contains("[")) {
                key = key.split("\\[")[0];
            }

            if (!retVal.contains(key)) {
                retVal.add(key);
            }
        }
        return retVal;
    }


}
