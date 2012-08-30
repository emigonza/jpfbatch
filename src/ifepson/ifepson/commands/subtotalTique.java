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
public class subtotalTique extends ifCommand {

    public boolean isImprimir() {
        return this.params.get(Parametro.STT__IMPRIMIR).equals("P");
    }

    public void setImprimir(boolean imprimir) {
        this.params.put(Parametro.STT__IMPRIMIR, imprimir ? "P" : "N");
    }

    public subtotalTique() {
        this.commandId = 0x43;
        this.name = "subtotalTiqueFiscal";
        this.nombreA = "TIQUESUBTOTAL";
        this.descripcionComando = "Este comando será rechazado si no hay un comprobante Tique fiscal abierto. Se rechazará si la acumulación de montos genera un desborde de totales. Se usa este comando para enviar los totales de transacciones al Host e imprimir, opcionalmente, el subtotal.";

        this.params.put(Parametro.STT__IMPRIMIR, "P");
        this.params.put(Parametro.STT__DESCRIPCION, "                         ");
    }

    @Override
    public boolean interpretaRespuesta(byte[] resp) {
        try {
            respuesta.put(IndexedOut.STT__CANT_ITEMS, GeneralFunc.extractVarStrFromArray(resp, 16, (char) 0x1C));
            respuesta.put(IndexedOut.STT__TOTAL_A_PAGAR_BRUTO, GeneralFunc.extractVarStrFromArray(resp, 22, (char) 0x1C));
            respuesta.put(IndexedOut.STT__TOTAL_IVA, GeneralFunc.extractVarStrFromArray(resp, 35, (char) 0x1C));
            respuesta.put(IndexedOut.STT__TOTAL_PAGO, GeneralFunc.extractVarStrFromArray(resp, 48, (char) 0x1C));
            respuesta.put(IndexedOut.STT__TOTAL_IMP_INT_PORCENT, GeneralFunc.extractVarStrFromArray(resp, 61, (char) 0x1C));
            respuesta.put(IndexedOut.STT__TOTAL_IMP_INT_FIJO, GeneralFunc.extractVarStrFromArray(resp, 74, (char) 0x1C));
            respuesta.put(IndexedOut.STT__MONTO_NETO, GeneralFunc.extractVarStrFromArray(resp, 87, (char) 0x1C, (char) 0x03));
        } catch (Exception ex) {
            Logger.getLogger(subtotalTique.class).log(Level.ERROR, "", ex);
            return false;
        }

        return true;
    }
}
