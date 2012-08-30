/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ifepson.commands;

import ifepson.ifCommand;
import ifepson.doc.Parametro;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import myjob.func.general.GeneralFunc;

/**
 *
 * @author guillermot
 */
public class DNFHTarjetaCredito extends ifCommand {

    public String getCodigoAutorizacion() {
        return params.get(Parametro.CNFHTC__COD_AUTORIZ);
    }

    public void setCodigoAutorizacion(String codigoAutorizacion) {
        params.put(Parametro.CNFHTC__COD_AUTORIZ, getStr(codigoAutorizacion));
    }

    public String getCuotas() {
        return params.get(Parametro.CNFHTC__CANT_CUOTAS);
    }

    public void setCuotas(String cuotas) {
        params.put(Parametro.CNFHTC__CANT_CUOTAS, getStr(cuotas));
    }

    public Double getImporte() {
        return Double.parseDouble(this.params.get(Parametro.CNFHTC__IMPORTE)) / 100;
    }

    public void setImporte(Double importe) {
        this.params.put(Parametro.CNFHTC__IMPORTE, GeneralFunc.stringFormat("{0:00000000000}", importe * 100));
    }

    public boolean isLineaAclaracion() {
        return this.params.get(Parametro.CNFHTC__LINEA_ACLARA).equals("P");
    }

    public void setLineaAclaracion(boolean lineaAclaracion) {
        this.params.put(Parametro.CNFHTC__LINEA_ACLARA, lineaAclaracion ? "P" : "");
    }

    public boolean isLineaFirma() {
        return this.params.get(Parametro.CNFHTC__LINEA_FIRMA).equals("P");
    }

    public void setLineaFirma(boolean lineaFirma) {
        this.params.put(Parametro.CNFHTC__LINEA_FIRMA, lineaFirma ? "P" : "");
    }

    public String getMoneda() {
        return params.get(Parametro.CNFHTC__MONEDA);
    }

    public void setMoneda(String moneda) {
        this.params.put(Parametro.CNFHTC__MONEDA, getStr(moneda));
    }

    public String getNombreTarjeta() {
        return params.get(Parametro.CNFHTC__NOMBRE_TARJETA);
    }

    public void setNombreTarjeta(String nombreTarjeta) {
        params.put(Parametro.CNFHTC__NOMBRE_TARJETA, getStr(nombreTarjeta));
    }

    public String getNroCupon() {
        return params.get(Parametro.CNFHTC__NRO_CUPON);
    }

    public void setNroCupon(String nroCupon) {
        params.put(Parametro.CNFHTC__NRO_CUPON, getStr(nroCupon));
    }

    public String getNroEstablecimiento() {
        return params.get(Parametro.CNFHTC__NRO_ESTAB);
    }

    public void setNroEstablecimiento(String nroEstablecimiento) {
        params.put(Parametro.CNFHTC__NRO_ESTAB, getStr(nroEstablecimiento));
    }

    public String getNroFactura() {
        return params.get(Parametro.CNFHTC__NRO_FACTURA);
    }

    public void setNroFactura(String nroFactura) {
        params.put(Parametro.CNFHTC__NRO_FACTURA, getStr(nroFactura));
    }

    public String getNroInterno() {
        return params.get(Parametro.CNFHTC__NRO_INT_COMP);
    }

    public void setNroInterno(String nroInterno) {
        params.put(Parametro.CNFHTC__NRO_INT_COMP, getStr(nroInterno));
    }

    public String getNroLote() {
        return params.get(Parametro.CNFHTC__NRO_LOTE);
    }

    public void setNroLote(String nroLote) {
        params.put(Parametro.CNFHTC__NRO_LOTE, getStr(nroLote));
    }

    public String getNroSucursal() {
        return params.get(Parametro.CNFHTC__NRO_SUCURSAL);
    }

    public void setNroSucursal(String nroSucursal) {
        params.put(Parametro.CNFHTC__NRO_SUCURSAL, getStr(nroSucursal));
    }

    public String getNroTarjeta() {
        return params.get(Parametro.CNFHTC__NRO_TARJETA);
    }

    public void setNroTarjeta(String nroTarjeta) {
        params.put(Parametro.CNFHTC__NRO_TARJETA, getStr(nroTarjeta));
    }

    public String getNroTerminalElectronica() {
        return params.get(Parametro.CNFHTC__NRO_TERM_ELEC);
    }

    public void setNroTerminalElectronica(String nroTerminalElectronica) {
        params.put(Parametro.CNFHTC__NRO_TERM_ELEC, getStr(nroTerminalElectronica));
    }

    public String getOperador() {
        return params.get(Parametro.CNFHTC__OPERADOR);
    }

    public void setOperador(String operador) {
        params.put(Parametro.CNFHTC__OPERADOR, getStr(operador));
    }

    public String getTerminal() {
        return params.get(Parametro.CNFHTC__NRO_TERMINAL);
    }

    public void setTerminal(String terminal) {
        params.put(Parametro.CNFHTC__NRO_TERMINAL, getStr(terminal));
    }

    public String getTipoOperacion() {
        return params.get(Parametro.CNFHTC__TIPO_OPERAC);
    }

    public void setTipoOperacion(String tipoOperacion) {
        params.put(Parametro.CNFHTC__TIPO_OPERAC, getStr(tipoOperacion));
    }

    public String getUsuarioTarjeta() {
        return params.get(Parametro.CNFHTC__USUARIO);
    }

    public void setUsuarioTarjeta(String usuarioTarjeta) {
        params.put(Parametro.CNFHTC__USUARIO, getStr(usuarioTarjeta));
    }

    public Date getVencimientoTarjeta() {
        try {
            return GeneralFunc.parseDate(params.get(Parametro.CNFHTC__VENC_TARJ), "yyMMdd");
        } catch (ParseException ex) {
            Logger.getLogger(DNFHTarjetaCredito.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public void setVencimientoTarjeta(Date vencimientoTarjeta) {
        params.put(Parametro.CNFHTC__VENC_TARJ, GeneralFunc.stringFormat("yyMMdd", vencimientoTarjeta));
    }

    public boolean isLineaTelefono() {
        return params.get(Parametro.CNFHTC__LINEA_TELEFONO).equals("P");
    }

    public void setLineaTelefono(boolean lineaTelefono) {
        params.put(Parametro.CNFHTC__LINEA_TELEFONO, lineaTelefono ? "P" : "");
    }

    public DNFHTarjetaCredito() {
        this.commandId = 0x4F;
        this.name = "DNFHTarjetaCredito";
        this.descripcionComando = "Comando Voucher Tarjeta de Crédito generado con un comprobante no fiscal homologado.";

        this.params.put(Parametro.CNFHTC__01, "01");
        this.params.put(Parametro.CNFHTC__NOMBRE_TARJETA, getStr(""));
        this.params.put(Parametro.CNFHTC__NRO_TARJETA, getStr(""));
        this.params.put(Parametro.CNFHTC__USUARIO, getStr(""));
        this.params.put(Parametro.CNFHTC__VENC_TARJ, GeneralFunc.stringFormat("{0:yyMMdd}", Calendar.getInstance()));
        this.params.put(Parametro.CNFHTC__NRO_ESTAB, getStr(""));
        this.params.put(Parametro.CNFHTC__NRO_CUPON, getStr(""));
        this.params.put(Parametro.CNFHTC__NRO_INT_COMP, getStr(""));
        this.params.put(Parametro.CNFHTC__COD_AUTORIZ, getStr(""));
        this.params.put(Parametro.CNFHTC__TIPO_OPERAC, getStr(""));
        this.params.put(Parametro.CNFHTC__IMPORTE, GeneralFunc.stringFormat("{0:00000000000}", 0));
        this.params.put(Parametro.CNFHTC__CANT_CUOTAS, getStr(""));
        this.params.put(Parametro.CNFHTC__MONEDA, getStr(""));
        this.params.put(Parametro.CNFHTC__NRO_TERMINAL, getStr(""));
        this.params.put(Parametro.CNFHTC__NRO_LOTE, getStr(""));
        this.params.put(Parametro.CNFHTC__NRO_TERM_ELEC, getStr(""));
        this.params.put(Parametro.CNFHTC__NRO_SUCURSAL, getStr(""));
        this.params.put(Parametro.CNFHTC__OPERADOR, getStr(""));
        this.params.put(Parametro.CNFHTC__NRO_FACTURA, getStr(""));
        this.params.put(Parametro.CNFHTC__LINEA_FIRMA, "P");
        this.params.put(Parametro.CNFHTC__LINEA_ACLARA, "P");
        this.params.put(Parametro.CNFHTC__LINEA_TELEFONO, "P");
    }

    String getStr(String valor) {
        if (valor.trim().length() > 0) {
            return valor;
        }

        StringBuilder sb = new StringBuilder();

        sb.append((char) 0x7F);

        return sb.toString();
    }

    @Override
    public boolean interpretaRespuesta(byte[] resp) {
        return true;
    }
}
