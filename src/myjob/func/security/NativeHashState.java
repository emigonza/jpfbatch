/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package myjob.func.security;

/**
 *
 * @author guillermot
 */
public class NativeHashState extends MessageDigest {

    java.security.MessageDigest md;
    String                      myAlg;

    public NativeHashState() {
    }

    protected void init(String algorithm) throws Exception {
	myAlg = algorithm;
	if(algorithm.equals("SHA1"))
	    algorithm = "SHA";
	md = java.security.MessageDigest.getInstance(algorithm);
    }

    public String getName() {
	return myAlg;
    }

    public void reset() {
	md.reset();
    }

    public void update(byte[] buffer, int offset, int length) {
	md.update(buffer,offset,length);
    }

    public byte[] digest() {
	return md.digest();
    }

    public int blockSize() {
	return 64;
    }

    public int hashSize() {
	if(myAlg.equals("SHA")) {
	    return 20;
	} else {
	    return 16;
	}
    }
}

