/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package myjob.func.security;

/**
 *
 * @author guillermot
 */
public final class KeyPair {

    PrivateKey privateKey;
    PublicKey  publicKey;

    public KeyPair(PublicKey publicKey, PrivateKey privateKey) {
	this.publicKey = publicKey;
	this.privateKey = privateKey;
    }

    public PublicKey getPublic() {
	return publicKey;
    }

   public PrivateKey getPrivate() {
	return privateKey;
    }
}
