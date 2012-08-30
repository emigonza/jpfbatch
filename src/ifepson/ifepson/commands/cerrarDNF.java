/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ifepson.commands;

import ifepson.ifCommand;
import ifepson.doc.IndexedOut;
import ifepson.doc.Parametro;
import myjob.func.general.GeneralFunc;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author guillermot
 */
public class cerrarDNF extends ifCommand {

    public boolean isCorteTotal() {
        return this.params.get(Parametro.PDRC__DESCRIPCION).equals("T");
    }

    public void setCorteTotal(boolean corteTotal) {
        if (corteTotal) {
            this.params.put(Parametro.PDRC__DESCRIPCION, "T");
        } else {
            this.params.put(Parametro.PDRC__DESCRIPCION, "P");
        }
    }

    public cerrarDNF() {
        this.commandId = 0x4a;
        this.name = "cerrarDNF";
        this.descripcionComando = " l comando será rechazado si un comprobante no fiscal no está abierto. Se lo rechazará si hay formularios en las entradas para impresión o validación de hojas sueltas.";
        this.nombreA = "CERRARDNF";
        this.params.put(Parametro.PDRC__DESCRIPCION, "T");
    }

    @Override
    public boolean interpretaRespuesta(byte[] resp) {
        try {
            respuesta.put(IndexedOut.CDNF__NRO_TIQUE, GeneralFunc.extractVarStrFromArray(resp, 14, (char) 0x1C, (char) 0x03));
        } catch (Exception e) {
            Logger.getLogger(cerrarDNF.class).log(Level.ERROR, "", e);
            return false;
        }
        return true;
    }
}
