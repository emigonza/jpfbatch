/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package myjob.func.security;

/**
 *
 * @author guillermot
 */
public class NoSuchProviderException extends GeneralSecurityException {
    public NoSuchProviderException() {
	super();
    }

    public NoSuchProviderException(String msg) {
	super(msg);
    }
}
