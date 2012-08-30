/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package myjob.func.general;

/**
 *
 * @author guillermot
 */
public class Pair<E, F> {

    E key = null;
    F value = null;

    public E getKey() {
        return key;
    }

    public void setKey(E key) {
        this.key = key;
    }

    public F getValue() {
        return value;
    }

    public void setValue(F value) {
        this.value = value;
    }

    public Pair() {
    }

    public Pair(E key, F value) {
        this.key = key;
        this.value = value;
    }


}
