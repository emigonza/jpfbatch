/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ifepson;

import ifepson.doc.IndexedOut;
import java.util.EnumMap;
import java.util.Map;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author guillermot
 */
public class RespuestaCheck {

    Map<IndexedOut, String> checkList = new EnumMap<IndexedOut, String>(IndexedOut.class);
    IFAction errorAction = IFAction.NOTHING;

    public RespuestaCheck(IFAction errorAction, Object[] checkList) {
        this.errorAction = errorAction;
        for(int i = 0; i < checkList.length; i += 2) {
            this.checkList.put((IndexedOut) checkList[i], (String) checkList[i + 1]);
        } 
    }
    
    public Map<IndexedOut, String> getCheckList() {
        return checkList;
    }

    public void setCheckList(Map<IndexedOut, String> checkList) {
        this.checkList = checkList;
    }

    public IFAction getErrorAction() {
        return errorAction;
    }

    public void setErrorAction(IFAction errorAction) {
        this.errorAction = errorAction;
    }

    public IFAction check(Map<IndexedOut, String> respuesta) {
        IFAction retVal = IFAction.NOTHING;

        boolean mal = true;
        
        String tmpLog = "";
        
        for (IndexedOut io : checkList.keySet()) {
            if (respuesta.containsKey(io)) {
                Logger.getLogger(RespuestaCheck.class).log(Level.DEBUG, "Checkeando respuesta " + io + ": " + respuesta.get(io) + " =? " + checkList.get(io));
                tmpLog += "respuesta " + io + ": " + respuesta.get(io) + " =? " + checkList.get(io) + "\n";
                if (respuesta.get(io).equals(checkList.get(io))) {
                    mal = false;
                    break;
                }
            } else {
                tmpLog += "respuesta " + io + ": null =? " + checkList.get(io) + "\n";
            }
        }

        if (mal) {
            Logger.getLogger(RespuestaCheck.class).log(Level.ERROR, "El checkeo de la respuesta dio mal\n" + tmpLog);
            return errorAction;
        } 
        
        Logger.getLogger(RespuestaCheck.class).log(Level.DEBUG, "El checkeo de la respuesta dio ok");

        return retVal;
    }
}
