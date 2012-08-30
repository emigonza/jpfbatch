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
public class abrirFNC extends ifCommand {

    public String getCUIT_Comprador() {
        return params.get(Parametro.AFNC__CUIT_COMPRADOR);
    }

    public void setCUIT_Comprador(String CUIT_Comprador) {
        params.put(Parametro.AFNC__CUIT_COMPRADOR, CUIT_Comprador);
    }

    public String getComprador_linea1() {
        return params.get(Parametro.AFNC__NOMBRE_COMPRAD_L1);
    }

    public void setComprador_linea1(String Comprador_linea1) {
        params.put(Parametro.AFNC__NOMBRE_COMPRAD_L1, Comprador_linea1);
    }

    public String getComprador_linea2() {
        return params.get(Parametro.AFNC__NOMBRE_COMPRAD_L2);
    }

    public void setComprador_linea2(String Comprador_linea2) {
        params.put(Parametro.AFNC__NOMBRE_COMPRAD_L2, Comprador_linea2);
    }

    public String getDomicilioComprador_linea1() {
        return params.get(Parametro.AFNC__DOMIC_COMPRADOR_L1);
    }

    public void setDomicilioComprador_linea1(String DomicilioComprador_linea1) {
        params.put(Parametro.AFNC__DOMIC_COMPRADOR_L1, DomicilioComprador_linea1);
    }

    public String getDomicilioComprador_linea2() {
        return params.get(Parametro.AFNC__DOMIC_COMPRADOR_L2);
    }

    public void setDomicilioComprador_linea2(String DomicilioComprador_linea2) {
        params.put(Parametro.AFNC__DOMIC_COMPRADOR_L2, DomicilioComprador_linea2);
    }

    public String getDomicilioComprador_linea3() {
        return params.get(Parametro.AFNC__DOMIC_COMPRADOR_L3);
    }

    public void setDomicilioComprador_linea3(String DomicilioComprador_linea3) {
        params.put(Parametro.AFNC__DOMIC_COMPRADOR_L3, DomicilioComprador_linea3);
    }

    public String getRemitosRelacionados_linea1() {
        return params.get(Parametro.AFNC__REMITOS_RELAC_L1);
    }

    public void setRemitosRelacionados_linea1(String RemitosRelacionados_linea1) {
        params.put(Parametro.AFNC__REMITOS_RELAC_L1, RemitosRelacionados_linea1);
    }

    public String getRemitosRelacionados_linea2() {
        return params.get(Parametro.AFNC__REMITOS_RELAC_L2);
    }

    public void setRemitosRelacionados_linea2(String RemitosRelacionados_linea2) {
        params.put(Parametro.AFNC__REMITOS_RELAC_L2, RemitosRelacionados_linea2);
    }

    public Boolean getBienDeUso() {
        return params.get(Parametro.AFNC__LEYENDA_BIEN_USO).equals("B") ? true : false;
    }

    public void setBienDeUso(Boolean bienDeUso) {
        params.put(Parametro.AFNC__LEYENDA_BIEN_USO, bienDeUso ? "B" : "N");
    }

    public Integer getCopias() {
        return Integer.parseInt(params.get(Parametro.AFNC__CANT_COPIAS));
    }

    public void setCopias(Integer copias) {
        params.put(Parametro.AFNC__CANT_COPIAS, copias.toString());
    }

    public DensidadImpresion getCpi() {
        return DensidadImpresion.parseLetra(params.get(Parametro.AFNC__DENSIDAD_LETRA));
    }

    public void setCpi(DensidadImpresion cpi) {
        params.put(Parametro.AFNC__DENSIDAD_LETRA, cpi.getCpi());
    }

    public FormatoDatos getFormatoDatos() {
        return FormatoDatos.parseLetra(params.get(Parametro.AFNC__FORMATO_ALMAC_DAT));
    }

    public void setFormatoDatos(FormatoDatos formatoDatos) {
        params.put(Parametro.AFNC__FORMATO_ALMAC_DAT, formatoDatos.getLetra());
    }

    public LetraDocumento getLetra() {
        return LetraDocumento.parseLetra(params.get(Parametro.AFNC__LETRA_DOCUMENTO));
    }

    public void setLetra(LetraDocumento letra) {
        params.put(Parametro.AFNC__LETRA_DOCUMENTO, letra.getLetra());
    }

    public ResponsabilidadAFIPComprador getRespComprador() {
        return ResponsabilidadAFIPComprador.parseLetra(params.get(Parametro.AFNC__RESP_AFIP_COMPRADOR));
    }

    public void setRespComprador(ResponsabilidadAFIPComprador respComprador) {
        params.put(Parametro.AFNC__RESP_AFIP_COMPRADOR, respComprador.getLetra());
    }

    public ResponsabilidadAFIPVendedor getRespVendedor() {
        return ResponsabilidadAFIPVendedor.parseLetra(params.get(Parametro.AFNC__RESP_AFIP_VENDEDOR));
    }

    public void setRespVendedor(ResponsabilidadAFIPVendedor respVendedor) {
        params.put(Parametro.AFNC__RESP_AFIP_VENDEDOR, respVendedor.getLetra());
    }

    public TipoComprobante getTipoComprobante() {
        return TipoComprobante.parseLetra(params.get(Parametro.AFNC__TIPO_DOC_FISCAL));
    }

    public void setTipoComprobante(TipoComprobante tipoComprobante) {
        params.put(Parametro.AFNC__TIPO_DOC_FISCAL, tipoComprobante.getLetra());
    }

    public TipoDocComprador getTipoDNIComprador() {
        return TipoDocComprador.parseLetra(params.get(Parametro.AFNC__TIPO_DOC_COMPRADOR));
    }

    public void setTipoDNIComprador(TipoDocComprador tipoDNIComprador) {
        params.put(Parametro.AFNC__TIPO_DOC_COMPRADOR, tipoDNIComprador.getTipo());
    }

    public TipoFormulario getTipoFormulario() {
        return TipoFormulario.parseLetra(params.get(Parametro.AFNC__TIPO_FORMULARIO));
    }

    public void setTipoFormulario(TipoFormulario tipoFormulario) {
        params.put(Parametro.AFNC__TIPO_FORMULARIO, tipoFormulario.getLetra());
    }

    public TipoSalida getTipoSalida() {
        return TipoSalida.parseLetra(params.get(Parametro.AFNC__TIPO_SALIDA_IMPRESA));
    }

    public void setTipoSalida(TipoSalida tipoSalida) {
        params.put(Parametro.AFNC__TIPO_SALIDA_IMPRESA, tipoSalida.getLetra());
    }

    public abrirFNC() {
        this.commandId = 0x60;
        this.name = "AbrirComprobanteFactura_NotaCredito_TF_TNC";
        this.nombreA = "FACTABRE";
        this.descripcionComando = "Este comando es el primer paso para producir un Comprobante Fiscal tipo Factura, Nota de Crédito, Tique-Factura (TF) o Tique-Nota de Crédito (TNC) (según modelo del equipo). Se rechazará el comando si hay otro comprobante fiscal abierto.";

        this.params.put(Parametro.AFNC__TIPO_DOC_FISCAL, TipoComprobante.FACTURA.getLetra());
        this.params.put(Parametro.AFNC__TIPO_SALIDA_IMPRESA, TipoSalida.FORMULARIO_CONTINUO.getLetra());
        this.params.put(Parametro.AFNC__LETRA_DOCUMENTO, LetraDocumento.A.getLetra());
        this.params.put(Parametro.AFNC__CANT_COPIAS, "1");
        this.params.put(Parametro.AFNC__TIPO_FORMULARIO, TipoFormulario.PRE_IMPRESO.getLetra());
        this.params.put(Parametro.AFNC__DENSIDAD_LETRA, DensidadImpresion.CPI12.getCpi());
        this.params.put(Parametro.AFNC__RESP_AFIP_VENDEDOR, ResponsabilidadAFIPVendedor.RESPONSABLE_INSCRIPTO.getLetra());
        this.params.put(Parametro.AFNC__RESP_AFIP_COMPRADOR, ResponsabilidadAFIPComprador.CONSUMIDOR_FINAL.getLetra());
        this.params.put(Parametro.AFNC__NOMBRE_COMPRAD_L1, " ");
        this.params.put(Parametro.AFNC__NOMBRE_COMPRAD_L2, " ");
        this.params.put(Parametro.AFNC__TIPO_DOC_COMPRADOR, TipoDocComprador.CUIT.getTipo());
        this.params.put(Parametro.AFNC__CUIT_COMPRADOR, " ");
        this.params.put(Parametro.AFNC__LEYENDA_BIEN_USO, "N");
        this.params.put(Parametro.AFNC__DOMIC_COMPRADOR_L1, " ");
        this.params.put(Parametro.AFNC__DOMIC_COMPRADOR_L2, " ");
        this.params.put(Parametro.AFNC__DOMIC_COMPRADOR_L3, " ");
        this.params.put(Parametro.AFNC__REMITOS_RELAC_L1, "_");
        this.params.put(Parametro.AFNC__REMITOS_RELAC_L2, "_");
        this.params.put(Parametro.AFNC__FORMATO_ALMAC_DAT, FormatoDatos.NO_DNFH.getLetra());
    }

    
    @Override
    public boolean interpretaRespuesta(byte[] resp) {
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

            switch (letra.charAt(0)) {
                case 'F':
                    return TipoComprobante.FACTURA;
                case 'N':
                    return TipoComprobante.NOTA_CREDITO;
                case 'T':
                    return TipoComprobante.TIQUE_FACTURA;
                case 'M':
                    return TipoComprobante.TIQUE_NOTA_CREDITO;
            }

            return null;
        }
    }

    public enum TipoSalida {

        FORMULARIO_CONTINUO("C"),
        HOJA_SUELTA("S");
        String letra = "C";

        TipoSalida(String letra) {
            this.letra = letra;
        }

        public String getLetra() {
            return letra;
        }

        public static TipoSalida parseLetra(String letra) {
            switch (letra.charAt(0)) {
                case 'C':
                    return TipoSalida.FORMULARIO_CONTINUO;
                case 'S':
                    return TipoSalida.HOJA_SUELTA;

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
            switch (letra.charAt(0)) {
                case 'A':
                    return LetraDocumento.A;
                case 'B':
                    return LetraDocumento.B;
                case 'C':
                    return LetraDocumento.C;
                case 'X':
                    return LetraDocumento.X;
            }
            return null;
        }
    }

    public enum TipoFormulario {

        PRE_IMPRESO("F"),
        BLANCO("P"),
        AUTOIMPRESOR("A");
        String letra = "";

        TipoFormulario(String letra) {
            this.letra = letra;
        }

        public String getLetra() {
            return letra;
        }

        public static TipoFormulario parseLetra(String letra) {

            switch (letra.charAt(0)) {
                case 'F':
                    return TipoFormulario.PRE_IMPRESO;
                case 'P':
                    return TipoFormulario.BLANCO;
                case 'A':
                    return TipoFormulario.AUTOIMPRESOR;
            }
            return null;
        }
    }

    public enum DensidadImpresion {

        CPI12("12"),
        CPI17("17");
        String cpi = "12";

        DensidadImpresion(String cpi) {
            this.cpi = cpi;
        }

        public String getCpi() {
            return cpi;
        }

        public static DensidadImpresion parseLetra(String letra) {
            if (letra.equalsIgnoreCase("12")) {
                return DensidadImpresion.CPI12;
            }

            return DensidadImpresion.CPI17;
        }
    }

    public enum ResponsabilidadAFIPVendedor {

        RESPONSABLE_INSCRIPTO("I"),
        RESPONSABLE_NO_INSCRIPTO("R"),
        NO_RESPONSABLE("N"),
        EXCENTO("E"),
        MONOTRIBUTO("M"),
        MONOTRIBUTISTA_SOCIAL("T");
        String letra = "";

        ResponsabilidadAFIPVendedor(String letra) {
            this.letra = letra;
        }

        public String getLetra() {
            return letra;
        }

        public static ResponsabilidadAFIPVendedor parseLetra(String letra) {

            switch (letra.charAt(0)) {
                case 'I':
                    return ResponsabilidadAFIPVendedor.RESPONSABLE_INSCRIPTO;
                case 'R':
                    return ResponsabilidadAFIPVendedor.RESPONSABLE_NO_INSCRIPTO;
                case 'N':
                    return ResponsabilidadAFIPVendedor.NO_RESPONSABLE;
                case 'E':
                    return ResponsabilidadAFIPVendedor.EXCENTO;
                case 'M':
                    return ResponsabilidadAFIPVendedor.MONOTRIBUTO;
                case 'T':
                    return ResponsabilidadAFIPVendedor.MONOTRIBUTISTA_SOCIAL;
            }

            return null;
        }
    }

    public enum ResponsabilidadAFIPComprador {

        RESPONSABLE_INSCRIPTO("I"),
        RESPONSABLE_NO_INSCRIPTO("R"),
        NO_RESPONSABLE("N"),
        EXCENTO("E"),
        MONOTRIBUTO("M"),
        CONSUMIDOR_FINAL("F"),
        SUJETO_NO_CATEGORIZADO("S"),
        MONOTRIBUTISTA_SOCIAL("T"),
        PEQUE_CONTRIB_EVENTUAL("C"),
        PEQUE_CONTRIB_EVENTUAL_SOCIAL("V");
        String letra = "";

        ResponsabilidadAFIPComprador(String letra) {
            this.letra = letra;
        }

        public String getLetra() {
            return letra;
        }

        public static ResponsabilidadAFIPComprador parseLetra(String letra) {
            switch (letra.charAt(0)) {
                case 'I':
                    return ResponsabilidadAFIPComprador.RESPONSABLE_INSCRIPTO;
                case 'R':
                    return ResponsabilidadAFIPComprador.RESPONSABLE_NO_INSCRIPTO;
                case 'N':
                    return ResponsabilidadAFIPComprador.NO_RESPONSABLE;
                case 'E':
                    return ResponsabilidadAFIPComprador.EXCENTO;
                case 'M':
                    return ResponsabilidadAFIPComprador.MONOTRIBUTO;
                case 'F':
                    return ResponsabilidadAFIPComprador.CONSUMIDOR_FINAL;
                case 'S':
                    return ResponsabilidadAFIPComprador.SUJETO_NO_CATEGORIZADO;
                case 'T':
                    return ResponsabilidadAFIPComprador.MONOTRIBUTISTA_SOCIAL;
                case 'C':
                    return ResponsabilidadAFIPComprador.PEQUE_CONTRIB_EVENTUAL;
                case 'V':
                    return ResponsabilidadAFIPComprador.PEQUE_CONTRIB_EVENTUAL_SOCIAL;
            }

            return null;
        }
    }

    public enum TipoDocComprador {

        CUIT("CUIT"),
        CUIL("CUIL");
        String tipo = "";

        TipoDocComprador(String tipo) {
            this.tipo = tipo;
        }

        public String getTipo() {
            return tipo;
        }

        public static TipoDocComprador parseLetra(String letra) {

            if (letra.equalsIgnoreCase("CUIT")) {
                return TipoDocComprador.CUIT;
            }

            return TipoDocComprador.CUIL;
        }
    }

    public static enum FormatoDatos {

        NO_DNFH("C"), SI_DNFH("G");

        FormatoDatos(String letra) {
            this.letra = letra;
        }
        String letra = "";

        public String getLetra() {
            return letra;
        }

        public static FormatoDatos parseLetra(String letra) {
            switch (letra.charAt(0)) {
                case 'C':
                    return FormatoDatos.NO_DNFH;
                case 'G':
                    return FormatoDatos.SI_DNFH;
            }
            return null;
        }
    }
}
