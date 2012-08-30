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
public class imprTxtDNF extends ifCommand {

    public String getTexto() {
        return params.get(params.get(Parametro.ITDNF__DESCRIPCION));
    }

    public void setTexto(String texto) {
        if (texto.length() > 40) {
            texto = texto.substring(0, 39);
        }
        params.put(Parametro.ITDNF__DESCRIPCION, texto);
    }

    public imprTxtDNF() {
        this.commandId = 0x49;
        this.name = "imprimirTextoEnDocumNoFiscal";
        this.nombreA = "IMPRTXTDNF";
        this.descripcionComando = "El comando será rechazado si no está abierto un comprobante no fiscal. Se restringirá el texto al conjunto de Caracteres del Texto Fiscal.";

        this.params.put(Parametro.ITDNF__DESCRIPCION, GeneralFunc.padRight("", 40, ' '));
    }


    @Override
    public boolean interpretaRespuesta(byte[] resp) {
        return true;
    }
}
