/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package myjob.func.security;

/**
 *
 * @author guillermot
 */
public class AccessDeniedException extends Exception {
    public AccessDeniedException(String message) {
	super(message);
    }

    public AccessDeniedException() {
	super();
    }
}