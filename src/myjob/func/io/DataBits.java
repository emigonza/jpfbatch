/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package myjob.func.io;

/**
 *
 * @author guillermot
 */
public enum DataBits {

    DATABIT_5("5", 5),
    DATABIT_6("6", 6),
    DATABIT_7("7", 7),
    DATABIT_8("8", 8);
    private String name;
    private int dataBits;
    private static DataBits[] values = {DATABIT_5, DATABIT_6, DATABIT_7, DATABIT_8};

    private DataBits(String name, int dataBits) {
        this.name = name;
        this.dataBits = dataBits;
    }

    public String getName() {
        return name;
    }

    public int getDataBits() {
        return dataBits;
    }

    public static DataBits[] getValues() {
        return values;
    }
}

