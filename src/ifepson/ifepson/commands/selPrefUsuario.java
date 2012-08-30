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
public class selPrefUsuario extends ifCommand {

    public int getCantColumnas() {
        return Integer.parseInt(params.get(Parametro.SPU__CANT_COLUMNAS));
    }

    public void setCantColumnas(int cantColumnas) {
        this.params.put(Parametro.SPU__CANT_COLUMNAS, ((Integer) cantColumnas).toString());
    }

    public int getCantFilas() {
        return Integer.parseInt(params.get(Parametro.SPU__CANT_FILAS));
    }

    public void setCantFilas(int cantFilas) {
        this.params.put(Parametro.SPU__CANT_FILAS, ((Integer) cantFilas).toString());
    }

    public Opcion1 getOpcion1() {
        return Opcion1.parseLetra(params.get(Parametro.SPU__OPCIONES1));
    }

    public void setOpcion1(Opcion1 opcion1) {
        this.params.put(Parametro.SPU__OPCIONES1, opcion1.getLetra());
    }

    public Opcion2 getOpcion2() {
        return Opcion2.parseLetra(params.get(Parametro.SPU__OPCIONES2));
    }

    public void setOpcion2(Opcion2 opcion2) {
        this.params.put(Parametro.SPU__OPCIONES2, opcion2.getLetra());
    }

    public TipoSeteo getTipoSeteo() {
        return TipoSeteo.parseLetra(params.get(Parametro.SPU__TIPO_SETEO));
    }

    public void setTipoSeteo(TipoSeteo tipoSeteo) {
        this.params.put(Parametro.SPU__TIPO_SETEO, tipoSeteo.getLetra());
    }

    public selPrefUsuario() {
        this.commandId = 0x5A;
        this.name = "seleccionarPreferenciasUsuario";
        this.descripcionComando = "Este comando permite realizar configuraciones sobre la impresión en Hoja Suelta y/o Formulario Continuo, así como también seleccionar determinadas preferencias en comprobantes. Para saber cual es la configuración actual, se puede utilizar el comando Leer Preferencias del Usuario.";

        this.params.put(Parametro.SPU__IMPRESORA, "P");
        this.params.put(Parametro.SPU__TIPO_SETEO, TipoSeteo.PREFERENCIAS_DISPOSITIVO_IMPRESION.getLetra());
        this.params.put(Parametro.SPU__OPCIONES1, Opcion1.PREF_IMPRIMIR_LEYENDA.getLetra());
        this.params.put(Parametro.SPU__OPCIONES2, Opcion2.SELECCIONAR.getLetra());
        this.params.put(Parametro.SPU__CANT_COLUMNAS, "0");
        this.params.put(Parametro.SPU__CANT_FILAS, "0");
    }

    @Override
    public boolean interpretaRespuesta(byte[] resp) {
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

        public static TipoSeteo parseLetra(String letra) {
            switch(letra.charAt(0)) {
                case 'D': return TipoSeteo.PREFERENCIAS_DISPOSITIVO_IMPRESION;
                case 'P': return TipoSeteo.PREFERENCIAS_PAPEL;
                case 'T': return TipoSeteo.PREFERENCIAS_COMPROBANTES_FISCALES;
            }
            return null;
        }
    }

    public enum Opcion1 {

        USO_HOJA_SUELTA("S"),
        USO_ROLLO_PAPEL("R"),
        TAMAÑO_PAPEL("S"),
        PREF_IMPRIMIR_LEYENDA("P"),
        PREF_IMPRIMIR_PREC_X_CANTIDAD("Q");
        String letra = "";

        Opcion1(String letra) {
            this.letra = letra;
        }

        public String getLetra() {
            return letra;
        }

        public static Opcion1 parseLetra(String letra) {
            switch (letra.charAt(0)) {
                case 'S':
                    return Opcion1.USO_HOJA_SUELTA;
                case 'R':
                    return Opcion1.USO_ROLLO_PAPEL;
                case 'P':
                    return Opcion1.PREF_IMPRIMIR_LEYENDA;
                case 'Q':
                    return Opcion1.PREF_IMPRIMIR_PREC_X_CANTIDAD;
            }
            return null;
        }
    }

    public enum Opcion2 {

        SE_IMPRIMEN_DOCUMENTOS_NO_FISC("O"),
        TAMA_DEFINIDO_POR_USUARIO("U"),
        DESELECCIONAR("N"),
        SELECCIONAR("S");
        String letra = "";

        Opcion2(String letra) {
            this.letra = letra;
        }

        public String getLetra() {
            return letra;
        }

        public static Opcion2 parseLetra(String letra) {
            switch (letra.charAt(0)) {
                case 'O':
                    return Opcion2.SE_IMPRIMEN_DOCUMENTOS_NO_FISC;
                case 'U':
                    return Opcion2.TAMA_DEFINIDO_POR_USUARIO;
                case 'N':
                    return Opcion2.DESELECCIONAR;
                case 'S':
                    return Opcion2.SELECCIONAR;
            }
            return null;
        }
    }
}
