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
public class subtotalFNC extends ifCommand {

    public boolean isImprimir() {
        return this.params.get(Parametro.STFNC__IMPRIMIR).equals("P");
    }

    public void setImprimir(boolean imprimir) {
        this.params.put(Parametro.STFNC__IMPRIMIR, imprimir ? "P" : "N");
    }

    public subtotalFNC() {
        this.commandId = 0x63;
        this.name = "subtotalTiqueFNC";
        this.nombreA = "FACTSUBTOTAL";
        this.descripcionComando = "Este comando será rechazado si no hay un comprobante fiscal abierto. Se usa este comando para enviar los totales de transacciones al Host.";

        this.params.put(Parametro.STFNC__IMPRIMIR, "P");
        this.params.put(Parametro.STFNC__DESCRIPCION, " ");
    }

    @Override
    public boolean interpretaRespuesta(byte[] resp) {
        try {
            respuesta.put(IndexedOut.STFNC__CANT_ITEMS, GeneralFunc.extractVarStrFromArray(resp, 16, (char) 0x1C));
            respuesta.put(IndexedOut.STFNC__TOTAL_A_PAGAR_BRUTO, GeneralFunc.extractVarStrFromArray(resp, 22, (char) 0x1C));
            respuesta.put(IndexedOut.STFNC__TOTAL_IVA, GeneralFunc.extractVarStrFromArray(resp, 35, (char) 0x1C));
            respuesta.put(IndexedOut.STFNC__TOTAL_PAGO, GeneralFunc.extractVarStrFromArray(resp, 48, (char) 0x1C));
            respuesta.put(IndexedOut.STFNC__TOTAL_IMP_INT_PORCENT, GeneralFunc.extractVarStrFromArray(resp, 61, (char) 0x1C));
            respuesta.put(IndexedOut.STFNC__TOTAL_IMP_INT_FIJO, GeneralFunc.extractConstStrFromArray(resp, 74, 12));
            respuesta.put(IndexedOut.STFNC__MONTO_NETO, GeneralFunc.extractConstStrFromArray(resp, 87, 12));
        } catch (Exception ex) {
            Logger.getLogger(subtotalFNC.class).log(Level.ERROR, "", ex);
            return false;
        }

        return true;
    }
}
