/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package myjob.func.security;

/**
 *
 * @author guillermot
 */
public class KeyException extends GeneralSecurityException {
    public KeyException() {
	super();
    }

    public KeyException(String msg) {
	super(msg);
    }
}

