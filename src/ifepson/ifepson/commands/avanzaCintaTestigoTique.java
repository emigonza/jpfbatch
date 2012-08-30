/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ifepson.commands;

/**
 *
 * @author guillermot
 */
public class avanzaCintaTestigoTique extends avanzaTique {

    public avanzaCintaTestigoTique() {
        super();
        this.setNombreA("AVANZATESTIGOTIQUE");
        this.setDescripcionComando("Avanza solo la cinta testigo del tique, spara los impresores que lo soporten");
        this.setTipoAvance(TipoAvance.PAPEL_CINTA_TESTIGO);
    }

    @Override
    public boolean interpretaRespuesta(byte[] resp) {
        return true;
    }


}
