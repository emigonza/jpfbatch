/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjob.func.security;

/**
 *
 * @author guillermot
 */
import java.util.Random;

/**
 * This class uses the scheduler to generate random numbers. It counts the
 * number of times a loop is repeated before a thread has slept for a specified
 * number of milliseconds. These numbers are then fed to a hash function to mask
 * any possible correlations.<p>
 */
public class SecureRandom extends Random {

    MD5 md5 = new MD5();
    private int t;
    public Thread updater;
    public static int secureLevel = 0;

    public SecureRandom() {
        t = Spinner.guessTime(1024);

        int paranoia = ((secureLevel > 0) ? 2 : 1);

        for (int i = 0; i < paranoia; i++) {
            // Estimate about 4 bits of entropy per call to spinner
            for (int j = md5.buffer.length - 1; j >= 0; j--) {
                // Fill the buffer with spin-counts from the Spinner class.
                md5.buffer[j] = (byte) Spinner.spin(t);
                if (secureLevel < 2) {
                    md5.buffer[--j] = (byte) System.currentTimeMillis();
                }
            }
            md5.transform(md5.buffer, 0);
        }
        unused = new byte[16];
        unusedPos = 16;
        unusedLock = new Object();
    }

    public SecureRandom(byte[] seed) {
        try {
            MD5 loc_md5 = new MD5();
            loc_md5.update(seed);
            this.md5 = loc_md5;
        } catch (Exception e) {
            // !!!
            System.out.println("Can't operate, MD5 not available...");
        }
        t = Spinner.guessTime(1024);
        unused = new byte[16];
        unusedPos = 16;
        unusedLock = new Object();
    }
    /** unused[unusedPos..15] is unused pseudo-random numbers. */
    byte[] unused;
    int unusedPos;
    final Object unusedLock;
    int poolSweep = 0;

    /** Get new unused bytes. */
    protected synchronized void update() {
        // Inject entropy into the pool
        //
        if (secureLevel > 1) {
            md5.buffer[poolSweep++] += Spinner.spin(t) + 1;
            md5.buffer[poolSweep++] += Spinner.spin(t) + 1;
        } else {
            md5.buffer[poolSweep++] += Spinner.bogusSpin();
            md5.buffer[poolSweep] += md5.buffer[poolSweep - 1];
            poolSweep++;
        }

        poolSweep %= 64;

        byte[] newUnused = new byte[16];
        md5.transform(md5.buffer, 0);
        writeBytes(md5.hash[0], newUnused, 0, 4);
        writeBytes(md5.hash[1], newUnused, 4, 4);
        writeBytes(md5.hash[2], newUnused, 8, 4);
        writeBytes(md5.hash[3], newUnused, 12, 4);

        synchronized (unusedLock) {
            unused = newUnused;
            unusedPos = 0;
        }
    }

    /** Generates the next random number. */
    @Override
    protected synchronized int next(int bits) {
        //System.out.println(bits);
        int r = 0;
        synchronized (unusedLock) {
            for (int b = 0; b < bits; b += 8) {
                if (unusedPos == 16) {
                    update();
                }
                r = (r << 8) + unused[unusedPos++];
            }
        }
        return r;
    }

    public synchronized void startUpdater() {
        if (updater != null) {
            return;
        }
        updater = (new Thread(new Runnable() {

            public void run() {
                SecureRandom.this.updater.setPriority(SecureRandom.this.updater.getPriority() - 1);
                while (true) {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        // !!!
                    }
                    SecureRandom.this.update();
                }
            }
        }));
        updater.start();
    }

    public void nextPadBytes(byte[] bytes, int len) {
        nextPadBytes(bytes, 0, len);
    }

    public void nextPadBytes(byte[] bytes, int off, int len) {
        byte[] ub;
        int ui;
        synchronized (unusedLock) {
            for (int i = 0; i < len; i++) {
                unusedPos %= 16;
                bytes[off + i] = unused[unusedPos++];
            }
        }
    }

    public static final void writeBytes(long a, byte[] dest, int i, int length) {
        for (int j = i + length - 1; j >= i; j--) {
            dest[j] = (byte) a;
            a = a >>> 8;
        }
    }
}

