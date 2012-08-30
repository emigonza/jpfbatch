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
 * @author Administrador
 */
public class cierreXZ extends ifCommand {

    public TipoCierre getTipoCierre() {
        return TipoCierre.parseString(this.params.get(Parametro.XZ__TIPO_CIERRE));
    }

    public void setTipoCierre(TipoCierre tipoCierre) {
        this.params.put(Parametro.XZ__TIPO_CIERRE, Character.toString(tipoCierre.getLetra()));
    }

    public cierreXZ() {
        this.commandId = 0x39;
        this.name = "CierreXZ";
        this.descripcionComando = "Este comando imprime el reporte de Totales Diarios y, en forma opcional, transfiere los Totales Diarios desde la Memoria de Trabajo a la Memoria Fiscal. Por ello, la duración de la Memoria Fiscal es independiente de la cantidad de transacciones que se realizan en el día. IMPORTANTE: Se puede realizar más de un cierre fiscal por día, pero este procedimiento acorta la vida útil de la memoria fiscal, siendo responsabilidad del programador y/o usuario la disminución en la capacidad de almacenamiento de la Memoria Fiscal.";

        setTipoCierre(TipoCierre.Z);
        setImprimir(true);
    }

    public boolean isImprimir() {
        return this.params.get(Parametro.XZ__IMPRIMIR).equals("P");
    }

    public void setImprimir(boolean imprimir) {
        if (imprimir) {
            this.params.put(Parametro.XZ__IMPRIMIR, "P");
        } else {
            this.params.put(Parametro.XZ__IMPRIMIR, " ");
        }
    }

    @Override
    public boolean interpretaRespuesta(byte[] resp) {
        try {

            String[] vals = GeneralFunc.bytesToStringArray(resp, (char) 0x02, (char) 0x1c, (char) 0x03);
/*
            for (int i = 0; i < vals.length; i++) {
                Logger.getLogger(cierreXZ.class).log(Level.DEBUG, "[" + i + "] " + vals[i]);
            }
*/
            if (vals.length > 3) {

                respuesta.put(IndexedOut.XZ__NRO_CIERRE, GeneralFunc.extractConstStrFromArray(resp, 14, 5));
                respuesta.put(IndexedOut.XZ__CANT_DOC_FISC_CANCEL, GeneralFunc.extractConstStrFromArray(resp, 20, 5));
                respuesta.put(IndexedOut.XZ__CANT_DOC_NO_FISC_HOMO, GeneralFunc.extractConstStrFromArray(resp, 26, 5));
                respuesta.put(IndexedOut.XZ__CANT_DOC_NO_FISC_NO_HOMO, GeneralFunc.extractConstStrFromArray(resp, 32, 5));
                respuesta.put(IndexedOut.XZ__CANT_TIQ_FAC_B_C, GeneralFunc.extractConstStrFromArray(resp, 38, 5));
                respuesta.put(IndexedOut.XZ__CANT_TIQ_FAC_A, GeneralFunc.extractConstStrFromArray(resp, 44, 5));
                respuesta.put(IndexedOut.XZ__ULT_TIQ_FAC_B_C, GeneralFunc.extractConstStrFromArray(resp, 50, 8));
                respuesta.put(IndexedOut.XZ__TOTAL_FACT, GeneralFunc.extractConstStrFromArray(resp, 59, 14));
                respuesta.put(IndexedOut.XZ__TOTAL_IVA, GeneralFunc.extractConstStrFromArray(resp, 74, 14));
                respuesta.put(IndexedOut.XZ__TOTAL_PERCEP, GeneralFunc.extractConstStrFromArray(resp, 89, 14));
                respuesta.put(IndexedOut.XZ__ULT_TIQ_FAC_A, GeneralFunc.extractConstStrFromArray(resp, 104, 8));
                respuesta.put(IndexedOut.XZ__ULT_TIQ_NOTA_CRED_A, GeneralFunc.extractConstStrFromArray(resp, 113, 8));
                respuesta.put(IndexedOut.XZ__ULT_TIQ_NOTA_CRED_B_C, GeneralFunc.extractConstStrFromArray(resp, 122, 8));
                respuesta.put(IndexedOut.XZ__ULT_REMI, GeneralFunc.extractConstStrFromArray(resp, 131, 8));
                respuesta.put(IndexedOut.XZ__TOTAL_NOTA_CRED, GeneralFunc.extractConstStrFromArray(resp, 140, 14));
                respuesta.put(IndexedOut.XZ__TOTAL_IVA_NOTA_CRED, GeneralFunc.extractConstStrFromArray(resp, 155, 14));
                if (resp[169] != 0x03) {
                    respuesta.put(IndexedOut.XZ__TOTAL_PRECEP_NOTA_CRED, GeneralFunc.extractConstStrFromArray(resp, 170, 14));
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(cierreXZ.class).log(Level.ERROR, "", ex);
            return false;
        }
        return true;
    }

    public static enum TipoCierre {

        Z('Z'), X('X');
        char letra = 'Z';

        public char getLetra() {
            return letra;
        }

        TipoCierre(char letra) {
            this.letra = letra;
        }

        public static TipoCierre parseString(String letra) {
            if (letra.toUpperCase().equals("Z")) {
                return TipoCierre.Z;
            }
            return TipoCierre.X;
        }
    }
}
