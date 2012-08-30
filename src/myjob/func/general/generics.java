/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjob.func.general;

import java.util.List;
import java.util.Map;

/**
 *
 * @author guillermot
 */
public class generics {

    public static <T, S> Map<T, S> CloneMap(Map<T, S> in, Map<T, S> out) {
        out.clear();

        for (T key : in.keySet()) {
            out.put(key, in.get(key));
        }

        return out;
    }

    public static <T> List<T> CloneList(List<T> in, List<T> out) {
        out.clear();
        for (T obj : in) {
            out.add(obj);
        }

        return out;
    }

    public static <T> int getIndexOf(List<T> array, T obj) {
        return getIndexOf(array.toArray(), obj);
    }

    public static <T> int getIndexOf(T[] array, T obj) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(obj)) {
                return i;
            }
        }

        return -1;
    }
}
