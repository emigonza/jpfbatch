/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjob.func.security;

/**
 *
 * @author guillermot
 */
import java.math.BigInteger;

public class RSAPublicKey extends RSAKey implements PublicKey {

    public RSAPublicKey(BigInteger e, BigInteger n) {
        super(e, n);
    }
}

