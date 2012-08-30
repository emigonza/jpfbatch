/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ifepson.commands;

import ifepson.ifCommand;
import ifepson.doc.Parametro;

/**
 *
 * @author guillermot
 */
public class prepararEstacion extends ifCommand {

    public prepararEstacion() {
        this.commandId = 0x5C;
        this.name = "prepararEstacionPrincipal";
        this.descripcionComando = "Este comando se utiliza para preparar la estación indicada en el comando, para la impresión del próximo documento.";

        this.params.put(Parametro.PEP__MANEJO_DOCUMENTO, "D");
        this.params.put(Parametro.PEP__IMPRESION, "P");
        this.params.put(Parametro.PEP__IMPRESION1, "P");
        this.params.put(Parametro.PEP__IMP_DNF_HOJA_SUEL, "U");
        this.params.put(Parametro.PEP__IMP_DNF, "O");
    }


    @Override
    public boolean interpretaRespuesta(byte[] resp) {
        return true;
    }
}
