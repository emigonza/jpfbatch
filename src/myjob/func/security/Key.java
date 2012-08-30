/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package myjob.func.security;

/**
 *
 * @author guillermot
 */
public interface Key extends java.io.Serializable {
    public String getAlgorithm();
    public String getFormat();
    public byte[] getEncoded();
}
