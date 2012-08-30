/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ifepson.commands;

/**
 *
 * @author guillermot
 */
public class cierreZ extends cierreXZ {

    public cierreZ() {
        super();
        this.descripcionComando = "Realiza un cierre Z, no es necesario enviar ningun parametro";
        this.nombreA = "CIERREZ";
        this.setTipoCierre(TipoCierre.Z);
    }


}
