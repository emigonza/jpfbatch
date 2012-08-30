/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package myjob.func.general;

/**
 *
 * @author guillermot
 */
public class exceptionFuncs {
    public static String getStackTrace(Exception ex) {
        String retVal = "";

        for(StackTraceElement el : ex.getStackTrace()) {
            retVal += el.toString() + "\n";
        }

        return retVal;
    }
}
