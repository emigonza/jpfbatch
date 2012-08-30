/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ifepson.commands;

/**
 *
 * @author guillermot
 */
public class cierreX extends cierreXZ {

    public cierreX() {
        super();
        this.descripcionComando = "Realiza un cierre X, no es necesario enviar ningun parametro";
        this.nombreA = "CIERREX";
        this.setTipoCierre(TipoCierre.X);
    }



}
