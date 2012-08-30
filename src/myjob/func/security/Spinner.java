/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjob.func.security;

/**
 *
 * @author guillermot
 */
public class Spinner extends Thread {

    /** Return the number of spins performed in t milliseconds. */
    public static int spin(long t) {
        int counter = 0;
        Thread s = new Spinner(t);
        s.start();
        do {
            ++counter;
            Thread.yield();
        } while (s.isAlive());
        return counter;
    }

    /* This one is completely bogus, but after the initial seeding we trust the
    milliseconds to be "random-enough"...
     */
    public static int bogusSpin() {
        Runtime rt = Runtime.getRuntime();
        int bogus;
        Thread.yield();
        rt.gc();
        bogus = (int) System.currentTimeMillis();
        bogus = ((bogus & 0xff) + ((bogus & 0xff00) >>> 8)) & 0xff;
        return bogus;
    }
    private long t;

    private Spinner(long t) {
        this.t = t;
    }

    public void run() {
        try {
            Thread.sleep(t);
        } catch (InterruptedException ex) {
        }
    }

    /**
     * Returns t such that spin(t) is larger than n. This value may change as
     * the load of the system changes.
     */
    public static int guessTime(int n) {
        int t = 5;
        while (spin(t) < n) {
            t = (t * 3) / 2;
        }
        return t;
    }
}

