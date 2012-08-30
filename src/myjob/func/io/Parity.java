/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjob.func.io;

/**
 *
 * @author guillermot
 */
public enum Parity {

    EVEN("Even", 2),
    MARK("Mark", 3),
    NONE("None", 0),
    ODD("Odd", 1),
    SPACE("Space", 4);
    private String name;
    private int parity;
    private static Parity[] values = {EVEN, MARK, NONE, ODD, SPACE};

    private Parity(String name, int parity) {
        this.name = name;
        this.parity = parity;
    }

    public String getName() {
        return name;
    }

    public int getParity() {
        return parity;
    }

    public static Parity[] getValues() {
        return values;
    }
}