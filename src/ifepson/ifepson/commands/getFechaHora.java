/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ifepson.commands;

import ifepson.ifCommand;
import ifepson.doc.IndexedOut;
import myjob.func.general.GeneralFunc;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author guillermot
 */
public class getFechaHora extends ifCommand {

    public getFechaHora() {
        this.commandId = 0x59;
        this.name = "getFechaHora";
        this.descripcionComando = "Obtener fecha y hora";
    }

    @Override
    public boolean interpretaRespuesta(byte[] resp) {
        try {
            respuesta.put(IndexedOut.OFH__FECHA, GeneralFunc.extractConstStrFromArray(resp, 14, 6));
            respuesta.put(IndexedOut.OFH__HORA, GeneralFunc.extractConstStrFromArray(resp, 21, 6));
        } catch (Exception e) {
            Logger.getLogger(getFechaHora.class).log(Level.ERROR, "", e);
            return false;
        }
        return true;
    }

}
