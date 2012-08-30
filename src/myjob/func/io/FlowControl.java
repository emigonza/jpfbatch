/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjob.func.io;

/**
 *
 * @author guillermot
 */
public enum FlowControl {

    NONE("5", 0),
    RTSCTS_IN("6", 1),
    RTSCTS_OUT("7", 2),
    XONXOFF_IN("8", 4),
    XONXOFF_OUT("8", 8);
    private String name;
    private int flowControl;
    private static FlowControl[] values = {NONE, RTSCTS_IN, RTSCTS_OUT, XONXOFF_IN, XONXOFF_OUT};

    private FlowControl(String name, int flowControl) {
        this.name = name;
        this.flowControl = flowControl;
    }

    public String getName() {
        return name;
    }

    public int getFlowControl() {
        return flowControl;
    }

    public static FlowControl[] getValues() {
        return values;
    }
}
