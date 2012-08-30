/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjob.func.security;

/**
 *
 * @author guillermot
 */
public final class NoEncrypt extends Cipher {

    public void encrypt(byte[] src, int srcOff, byte[] dest, int destOff, int len) {
        System.arraycopy(src, srcOff, dest, destOff, len);
    }

    public void decrypt(byte[] src, int srcOff, byte[] dest, int destOff, int len) {
        System.arraycopy(src, srcOff, dest, destOff, len);
    }

    public void setKey(byte[] key) {
    }
}
