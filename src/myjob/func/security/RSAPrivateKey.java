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

public class RSAPrivateKey extends RSAKey implements PrivateKey {

    private BigInteger d;
    private BigInteger u;
    private BigInteger p;
    private BigInteger q;

    public RSAPrivateKey(BigInteger e, BigInteger n,
            BigInteger d, BigInteger u,
            BigInteger p, BigInteger q) {
        super(e, n);
        this.d = d;
        this.u = u;
        this.p = p;
        this.q = q;
    }

    public BigInteger getD() {
        return d;
    }

    public BigInteger getU() {
        return u;
    }

    public BigInteger getP() {
        return p;
    }

    public BigInteger getQ() {
        return q;
    }
}
