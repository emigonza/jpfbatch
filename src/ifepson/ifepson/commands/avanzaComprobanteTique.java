/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ifepson.commands;

/**
 *
 * @author guillermot
 */
public class avanzaComprobanteTique extends avanzaTique {

    public avanzaComprobanteTique() {
        super();
        this.setNombreA("AVANZACOMPROBANTETIQUE");
        this.setDescripcionComando("Avanza solo el papel del comprobante del tique, spara los impresores que lo soporten");
        this.setTipoAvance(TipoAvance.PAPEL_COMPROBANTE);
    }


    @Override
    public boolean interpretaRespuesta(byte[] resp) {
        return true;
    }
}
