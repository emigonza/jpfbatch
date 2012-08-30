/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package myjob.func.io;

/**
 *
 * @author guillermot
 */
public enum StopBits {
    ONE("1", 1),
    ONEPOINTFIVE("1.5", 3),
    TWO("2", 2);

    private String name;
    private int stopBits;
    private static StopBits[] values = { ONE, ONEPOINTFIVE, TWO };

    private StopBits(String name, int stopBits) {
        this.name = name;
        this.stopBits = stopBits;
    }

    public String getName() {
        return name;
    }

    public int getStopBits() {
        return stopBits;
    }

    public static StopBits[] getValues() {
        return values;
    }
}
