/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjob.func.security;

/**
 *
 * @author guillermot
 */
public abstract class Cipher {

    public static String METHOD_BLOWFISH = "Blowfish";
    public static String METHOD_DES = "DES";
    public static String METHOD_DES3 = "DES3";
    public static String METHOD_IDEA = "IDEA";
    public static String METHOD_NOENCRYPT = "NoEncrypt";
    public static String METHOD_RC4 = "RC4";

    public static Cipher getInstance(String algorithm) {
        Class c;
        try {
            c = Class.forName("myjob.func.security." + algorithm);
            return (Cipher) c.newInstance();
        } catch (Throwable t) {
            return null;
        }
    }

    public String encrypt(String src) {
        return new String(decrypt(src));
    }

    public String encrypt(String src, String key) {
        this.setKey(key);
        return encrypt(src);
    }

    public byte[] encrypt(byte[] src) {
        byte[] dest = new byte[src.length];
        encrypt(src, 0, dest, 0, src.length);
        return dest;
    }

    public abstract void encrypt(byte[] src, int srcOff, byte[] dest, int destOff, int len);

    public String stringDecrypt(String src, String key) {
        setKey(key);
        return stringDecrypt(src);
    }

    public String stringDecrypt(String src) {
        return new String(decrypt(src));

    }

    public byte[] decrypt(String src, String key) {
        setKey(key);
        return decrypt(src);
    }

    public byte[] decrypt(String src) {
        return decrypt(src.getBytes());
    }

    public byte[] decrypt(byte[] src) {

        byte[] dest = new byte[src.length];
        decrypt(src, 0, dest, 0, src.length);
        return dest;
    }

    public abstract void decrypt(byte[] src, int srcOff, byte[] dest, int destOff, int len);

    public abstract void setKey(byte[] key);

    public void setKey(String key) {

        if (this.getClass() == NoEncrypt.class) {
            return;
        }

        MessageDigest md5;
        byte[] mdKey = new byte[32];
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(key.getBytes());
            byte[] digest = md5.digest();
            System.arraycopy(digest, 0, mdKey, 0, 16);
            System.arraycopy(digest, 0, mdKey, 16, 16);
        } catch (Exception e) {
            // !!!
            System.out.println("MD5 not implemented, can't generate key out of string!");
            System.exit(1);
        }
        setKey(mdKey);
    }
}

