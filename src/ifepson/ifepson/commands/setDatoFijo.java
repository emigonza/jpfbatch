/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ifepson.commands;

import ifepson.ifCommand;
import ifepson.doc.Parametro;
import myjob.func.general.GeneralFunc;

/**
 *
 * @author guillermot
 */
public class setDatoFijo extends ifCommand {

    public String getDatoFijo() {
        return params.get(Parametro.EDF__DATOS_FIJOS);
    }

    public setDatoFijo(String datoFijo) {
        this.params.put(Parametro.EDF__DATOS_FIJOS, datoFijo);
    }

    public int getNroLinea() {
        return Integer.parseInt(this.params.get(Parametro.EDF__NRO_LINEA));
    }

    public void setNroLinea(int nroLinea) {
        this.params.put(Parametro.EDF__NRO_LINEA, GeneralFunc.stringFormat("{0:00000}", nroLinea));
    }
    
    public setDatoFijo() {
        this.commandId = 0x5D;
        this.name = "setDatoFijo";
        this.nombreA = "PONEENCABEZADO";
        this.descripcionComando = "Este comando almacena una línea de Datos Fijos de encabezado o cola en la Memoria de Trabajo. Este comando permite almacenar un código de barras a ser impreso, el cual sólo es permitido en las últimas líneas de un comprobante (colas), ver Apéndice A por detalles.";

        this.params.put(Parametro.EDF__NRO_LINEA, GeneralFunc.stringFormat("{0:00000}", 1));
        this.params.put(Parametro.EDF__DATOS_FIJOS, "");

    }

    @Override
    public boolean interpretaRespuesta(byte[] resp) {
        return true;
    }

}
