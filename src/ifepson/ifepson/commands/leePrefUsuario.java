/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ifepson.commands;

import ifepson.ifCommand;
import ifepson.doc.IndexedOut;
import ifepson.doc.Parametro;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author guillermot
 */
public class leePrefUsuario extends ifCommand {

    public Opcion1 getOpcion1() {
        return Opcion1.parseString(params.get(Parametro.LPU__OPCIONES1));
    }

    public void setOpcion1(Opcion1 opcion1) {
        this.params.put(Parametro.LPU__OPCIONES1, opcion1.getLetra());
    }

    public TipoSeteo getTipoSeteo() {
        return TipoSeteo.parseString(params.get(Parametro.LPU__TIPO_SETEO));
    }

    public void setTipoSeteo(TipoSeteo tipoSeteo) {
        this.params.put(Parametro.LPU__TIPO_SETEO, tipoSeteo.getLetra());
    }

    public leePrefUsuario() {
        this.commandId = 0x5B;
        this.name = "leerPreferenciasUsuario";
        this.descripcionComando = "Este comando se utiliza para leer de la memoria de trabajo las preferencias del usuario establecidas con el comando Seleccionar Preferencias del Usuario.";


        this.params.put(Parametro.LPU__IMPRESORA, "P");
        this.params.put(Parametro.LPU__TIPO_SETEO, TipoSeteo.PREFERENCIAS_DISPOSITIVO_IMPRESION.getLetra());
        this.params.put(Parametro.LPU__OPCIONES1, Opcion1.TAMA_PAPEL.getLetra());

    }


    @Override
    public boolean interpretaRespuesta(byte[] resp) {
        try {
            if (getTipoSeteo() == TipoSeteo.PREFERENCIAS_DISPOSITIVO_IMPRESION) {
                if (resp[14] == 'R') {
                    respuesta.put(IndexedOut.LPU__IMPRESION_ROLLO_PAPEL, "1");
                }

                if (resp[14] == 'S') {
                    respuesta.put(IndexedOut.LPU__IMPRESION_HOJA_SUELTA, "1");
                }

                if (resp[15] == 'O') {
                    respuesta.put(IndexedOut.LPU__TIPO_DOC_NO_FISCAL, "1");
                }
            }

            if (getTipoSeteo() == TipoSeteo.PREFERENCIAS_DISPOSITIVO_IMPRESION && getOpcion1() == Opcion1.TAMA_PAPEL) {
                if (resp[14] == 'U') {
                    respuesta.put(IndexedOut.LPU__TAMA_DEFINIDO_POR_USR, "1");
                }
            }

            if (getTipoSeteo() == TipoSeteo.PREFERENCIAS_COMPROBANTES_FISCALES && (getOpcion1() == Opcion1.PREF_IMPRIMIR_PREC_X_CANTIDAD || getOpcion1() == Opcion1.PREF_IMPRIMIR_LEYENDA)) {
                if (resp[14] == 'N') {
                    respuesta.put(IndexedOut.LPU__PREF_SOL_NO_SETEADA, "1");
                }

                if (resp[14] == 'S') {
                    respuesta.put(IndexedOut.LPU__PREF_SOL_SETEADA, "1");
                }

                respuesta.put(IndexedOut.LPU__CANTIDAD_COLUMNAS, ((Integer) (int) resp[15]).toString());
                respuesta.put(IndexedOut.LPU__CANTIDAD_FILAS, ((Integer) (int) resp[16]).toString());

            }
        } catch (Exception e) {
            Logger.getLogger(leePrefUsuario.class).log(Level.ERROR, "", e);
            return false;
        }

        return true;
    }

    public enum TipoSeteo {

        PREFERENCIAS_DISPOSITIVO_IMPRESION("D"),
        PREFERENCIAS_PAPEL("P"),
        PREFERENCIAS_COMPROBANTES_FISCALES("T");
        String letra = "";

        TipoSeteo(String letra) {
            this.letra = letra;
        }

        public String getLetra() {
            return letra;
        }

        public static TipoSeteo parseString(String letra) {
            switch (letra.charAt(0)) {
                case 'D':
                    return TipoSeteo.PREFERENCIAS_DISPOSITIVO_IMPRESION;
                case 'P':
                    return TipoSeteo.PREFERENCIAS_PAPEL;
                case 'T':
                    return TipoSeteo.PREFERENCIAS_COMPROBANTES_FISCALES;
            }

            return null;
        }
    }

    public enum Opcion1 {

        TAMA_PAPEL("S"),
        PREF_IMPRIMIR_LEYENDA("P"),
        PREF_IMPRIMIR_PREC_X_CANTIDAD("Q");
        String letra = "";

        Opcion1(String letra) {
            this.letra = letra;
        }

        public String getLetra() {
            return letra;
        }

        public static Opcion1 parseString(String letra) {
            switch (letra.charAt(0)) {
                case 'S':
                    return Opcion1.TAMA_PAPEL;
                case 'P':
                    return Opcion1.PREF_IMPRIMIR_LEYENDA;
                case 'Q':
                    return Opcion1.PREF_IMPRIMIR_PREC_X_CANTIDAD;
            }

            return null;
        }
    }
}
