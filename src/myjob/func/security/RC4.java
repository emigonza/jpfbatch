/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjob.func.security;

/**
 *
 * @author guillermot
 */
public final class RC4 extends Cipher {

    int x;
    int y;
    byte[] state = new byte[256];

    final int arcfour_byte() {
        int x;
        int y;
        int sx, sy;

        x = (this.x + 1) & 0xff;
        sx = (int) state[x];
        y = (sx + this.y) & 0xff;
        sy = (int) state[y];
        this.x = x;
        this.y = y;
        state[y] = (byte) (sx & 0xff);
        state[x] = (byte) (sy & 0xff);
        return (int) state[((sx + sy) & 0xff)];
    }

    public synchronized void encrypt(byte[] src, int srcOff, byte[] dest, int destOff, int len) {
        int end = srcOff + len;
        for (int si = srcOff, di = destOff; si < end; si++, di++) {
            dest[di] = (byte) (((int) src[si] ^ arcfour_byte()) & 0xff);
        }
    }

    public void decrypt(byte[] src, int srcOff, byte[] dest, int destOff, int len) {
        encrypt(src, srcOff, dest, destOff, len);
    }

    public void setKey(byte[] key) {
        int t, u;
        int keyindex;
        int stateindex;
        int counter;

        for (counter = 0; counter < 256; counter++) {
            state[counter] = (byte) counter;
        }
        keyindex = 0;
        stateindex = 0;
        for (counter = 0; counter < 256; counter++) {
            t = (int) state[counter];
            stateindex = (stateindex + key[keyindex] + t) & 0xff;
            u = (int) state[stateindex];
            state[stateindex] = (byte) (t & 0xff);
            state[counter] = (byte) (u & 0xff);
            if (++keyindex >= key.length) {
                keyindex = 0;
            }
        }
    }
}

