/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package myjob.func.security;

/**
 *
 * @author guillermot
 */
public abstract class MessageDigest {
    public static boolean useNative = false;

    public static MessageDigest getInstance(String algorithm)
	throws InstantiationException, IllegalAccessException, ClassNotFoundException
    {
	if(useNative) {
	    try {
		Class c1, c2;
		c1 = Class.forName("java.security.MessageDigest");
		c2 = Class.forName("myjob.func.NativeHashState");
		MessageDigest md = (MessageDigest)c2.newInstance();
		md.init(algorithm);
		return md;
	    } catch (Throwable t) {
		// !!! Oh well, we're not too worried, the pure java
		// versions are pretty quick, we don't need no
		// steenking native code anyway... :-)
	    }
	}
	Class c;
	c = Class.forName("mindbright.security." + algorithm);
	return (MessageDigest)c.newInstance();
    }

    protected void init(String algorithm) throws Exception {
    }

    public abstract String getName();
    public abstract void reset();
    public abstract void update(byte[] buf, int offset, int length);
    public abstract byte[] digest();
    public abstract int blockSize();
    public abstract int hashSize();

    public final void update(byte[] buf) {
        update(buf, 0, buf.length);
    }

    public int digestInto(byte[] dest, int destOff) {
	byte[] dig = digest();
	System.arraycopy(dig, 0, dest, destOff, dig.length);
	return dig.length;
    }

    public Object clone() throws CloneNotSupportedException {
	if(this instanceof Cloneable) {
	    return super.clone();
	} else {
	    throw new CloneNotSupportedException();
	}
    }
}
