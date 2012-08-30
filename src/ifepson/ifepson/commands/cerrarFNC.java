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
public class cerrarFNC extends ifCommand {

    public String getDescripcionTotal() {
        return params.get(Parametro.CFNC__DESCRIP_EN_TOTAL);
    }

    public void setDescripcionTotal(String descripcionTotal) {
       params.put(Parametro.CFNC__DESCRIP_EN_TOTAL, descripcionTotal);
    }

    public LetraDocumento getLetra() {
        return LetraDocumento.parseLetra(params.get(Parametro.CFNC__LETRA_DOCUMENTO));
    }

    public void setLetra(LetraDocumento letra) {
        params.put(Parametro.CFNC__LETRA_DOCUMENTO, letra.getLetra());
    }

    public TipoComprobante getTipoComprobante() {
        return TipoComprobante.parseLetra(params.get(Parametro.CFNC__TIPO_DOCUMENTO));
    }

    public void setTipoComprobante(TipoComprobante tipoComprobante) {
        params.put(Parametro.CFNC__TIPO_DOCUMENTO, tipoComprobante.getLetra());
    }

    public cerrarFNC() {
        this.commandId = 0x65;
        this.name = "cerrarFNC";
        this.nombreA = "FACTCIERRA";
        this.descripcionComando = "Se rechazará este comando si no hay un comprobante fiscal abierto. Se rechazará si los montos acumulados generan un desbordamiento de total.";

        this.params.put(Parametro.CFNC__TIPO_DOCUMENTO, TipoComprobante.FACTURA.getLetra());
            this.params.put(Parametro.CFNC__LETRA_DOCUMENTO, LetraDocumento.A.getLetra());
            this.params.put(Parametro.CFNC__DESCRIP_EN_TOTAL, Character.toString((char) 0x7F));
    }


    @Override
    public boolean interpretaRespuesta(byte[] resp) {
        try {
            respuesta.put(IndexedOut.CFNC__NRO_COMPROBANTE, GeneralFunc.extractVarStrFromArray(resp, 14, (char) 0x1C, (char) 0x03));
        } catch (Exception e) {
            Logger.getLogger(cerrarFNC.class).log(Level.ERROR, "", e);
            return false;
        }
        return true;
    }

    public enum TipoComprobante {

        FACTURA("F"),
        NOTA_CREDITO("N"),
        TIQUE_FACTURA("T"),
        TIQUE_NOTA_CREDITO("M");

        TipoComprobante(String letra) {
            this.letra = letra;
        }
        String letra = "F";

        public String getLetra() {
            return letra;
        }

        public static TipoComprobante parseLetra(String letra) {
            switch(letra.charAt(0)) {
                case 'F': return TipoComprobante.FACTURA;
                case 'N': return TipoComprobante.NOTA_CREDITO;
                case 'T': return TipoComprobante.TIQUE_FACTURA;
                case 'M': return TipoComprobante.TIQUE_NOTA_CREDITO;
            }
            return null;
        }
    }

    public enum LetraDocumento {

        A("A"),
        B("B"),
        C("C"),
        X("X");
        String letra = "";

        LetraDocumento(String letra) {
            this.letra = letra;
        }

        public String getLetra() {
            return letra;
        }

       public static LetraDocumento parseLetra(String letra) {

           switch(letra.charAt(0)) {
               case 'A': return LetraDocumento.A;
               case 'B': return LetraDocumento.B;
               case 'C': return LetraDocumento.C;
               case 'X': return LetraDocumento.X;
           }

           return null;
       }
    }
}
