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
public class cerrarTique extends ifCommand {

    public boolean isCorteTotal() {
        return params.get(Parametro.CT__CORTE_TOTAL).equals("T");
    }

    public void setCorteTotal(boolean corteTotal) {
        params.put(Parametro.CT__CORTE_TOTAL, corteTotal ? "T" : "P");
    }

    public cerrarTique() {
        this.commandId = 0x45;
        this.name = "cerrarComprobanteTiqueFiscal";
        this.nombreA = "TIQUECIERRA";
        this.descripcionComando = "Se rechazará el comando si no hay un tique fiscal abierto. Se lo rechazará si no se completó alguna transacción de Venta con total mayor que cero ó si los montos acumulativos originan un desbordamiento del total. Se lo rechazará si hay formularios en las estaciones de slip ó validación ó si se hubiera agotado el papel de rollo. Este comando se usa para cerrar el comprobante fiscal, acumular totales en Totales Diarios en la Memoria de Trabajo, imprimir el Importe Total del Tique, el importe de los pagos, el vuelto, el logo fiscal y cortar el comprobante fiscal. ";

        this.params.put(Parametro.CT__CORTE_TOTAL, "T");
    }

    @Override
    public boolean interpretaRespuesta(byte[] resp) {
        try {
            respuesta.put(IndexedOut.CT__NRO_TIQUE, GeneralFunc.extractVarStrFromArray(resp, 14, (char) 0x1C, (char) 0x03));
        } catch (Exception e) {
            Logger.getLogger(cerrarTique.class).log(Level.ERROR, "", e);
            return false;
        }
        return true;
    }

}
