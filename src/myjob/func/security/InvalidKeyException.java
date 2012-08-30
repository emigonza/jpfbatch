/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package myjob.func.security;

/**
 *
 * @author guillermot
 */
public class InvalidKeyException extends KeyException {
    public InvalidKeyException() {
	super();
    }

    public InvalidKeyException(String msg) {
	super(msg);
    }
}
