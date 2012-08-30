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
public class avanzaTique extends ifCommand {

    public TipoAvance getTipoAvance() {
        return TipoAvance.parseLetra(commandId);
    }

    public void setTipoAvance(TipoAvance tipoAvance) {
        this.commandId = tipoAvance.getComando();
    }

    public Integer getLineas() {
        return Integer.parseInt(params.get(Parametro.AVT__CANTIDAD_LINEAS));
    }

    public void setLineas(Integer lineas) {
        this.params.put(Parametro.AVT__CANTIDAD_LINEAS, GeneralFunc.stringFormat("{0:00}", lineas));
    }

    public avanzaTique() {
        this.commandId = 0x4B;
        this.name = "avanzaTicket";
        this.nombreA = "AVANZATIQUE";
        this.descripcionComando = "Avanza el papel del ticket";

        this.commandId = TipoAvance.AMBOS.getComando();
        this.params.put(Parametro.AVT__CANTIDAD_LINEAS, GeneralFunc.stringFormat("{0:00}", 1));
    }

    @Override
    public boolean interpretaRespuesta(byte[] resp) {
        return true;
    }

    public enum TipoAvance {

        PAPEL_COMPROBANTE((byte) 0x50),
        PAPEL_CINTA_TESTIGO((byte) 0X51),
        AMBOS((byte) 0X52);
        byte comando = 0x52;

        TipoAvance(byte comando) {
            this.comando = comando;
        }

        public byte getComando() {
            return comando;
        }

        public static TipoAvance parseLetra(byte comando) {

            switch (comando) {
                case (byte) 0x50:
                    return TipoAvance.PAPEL_COMPROBANTE;
                case (byte) 0x51:
                    return TipoAvance.PAPEL_CINTA_TESTIGO;
                case (byte) 0x52:
                    return TipoAvance.AMBOS;
            }

            return null;
        }
    }
}
