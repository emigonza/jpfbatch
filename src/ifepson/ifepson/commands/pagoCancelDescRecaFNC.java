/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ifepson.commands;

import ifepson.IFException;
import ifepson.IFReturnValue;
import ifepson.ifCommand;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import ifepson.doc.IndexedOut;
import ifepson.doc.Parametro;
import myjob.func.general.GeneralFunc;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author guillermot
 */
public class pagoCancelDescRecaFNC extends ifCommand {

    public Calificador getCalificador() {
        return Calificador.parseString(this.params.get(Parametro.PDRTCFNC__CALIFICADOR));
    }

    public void setCalificador(Calificador calificador) {
        this.params.put(Parametro.PDRTCFNC__CALIFICADOR, calificador.getLetra());
    }

    public String getDescripcionEnTique() {
        return params.get(Parametro.PDRTCFNC__DESCRIPCION);
    }

    public void setDescripcionEnTique(String descripcionEnTique) {
        params.put(Parametro.PDRTCFNC__DESCRIPCION, descripcionEnTique);
    }

    public Double getMonto() {
        return Double.parseDouble(this.params.get(Parametro.PDRTCFNC__MONTO)) / 100;
    }

    public void setMonto(Double monto) {
        this.params.put(Parametro.PDRTCFNC__MONTO, GeneralFunc.stringFormat("{0:000000000}", monto * 100));
    }

    public pagoCancelDescRecaFNC() {
        this.commandId = 0x64;
        this.name = "pagoCancelarDecuentoFNC";
        this.descripcionComando = "Se rechazará este comando si no hay un comprobante fiscal abierto. Se rechazará si los montos acumulados generan un desbordamiento de total. En Impresoras de Tique y Tique-Factura/TNC se rechazará si hay un papel en las estaciones de slip o validación, si no hay papel en la estación de rollo, o si se usó la máxima cantidad de pagos permitida. Se usa este comando para imprimir información del total del pago y vuelto de la transacción. Cuando se envía un PAGO al Impresor Fiscal, se almacena y se imprimen junto con el TOTAL cuando se cierra la Factura / Nota de Crédito / TF / TNC. Después de este comando, no se pueden emitir nuevos comandos de impresión ítem de línea. Una vez enviado un PAGO, sólo se aceptan los comandos Pago, Cerrar Factura / Nota de Crédito / TF / TNC o CANCELAR. Sólo serán aceptados 5 (cinco) pagos en total por cada Factura / Nota de Crédito / TF / TNC.";
        this.nombreA = "FACTPAGO";

        this.params.put(Parametro.PDRTCFNC__DESCRIPCION, GeneralFunc.padRight("", 26, ' '));
        this.params.put(Parametro.PDRTCFNC__MONTO, GeneralFunc.stringFormat("{0:00000000000}", 0));
        this.params.put(Parametro.PDRTCFNC__CALIFICADOR, Calificador.SUMA_IMPORTE_PAGADO.getLetra());
    }

    @Override
    public IFReturnValue ejecutar(myjob.func.io.PortConfig pc, int secuencia) throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException, IFException {

        String descr = "";
        String monto = "";

        descr = this.params.get(Parametro.PDRTCFNC__DESCRIPCION);
        monto = this.params.get(Parametro.PDRTCFNC__MONTO);

        //if (getCalificador() == Calificador.CANCELAR_COMPROBANTE) {
        //    this.params.remove(Parametro.PDRTCFNC__DESCRIPCION);
        //    this.params.remove(Parametro.PDRTCFNC__MONTO);
        //}

        return super.ejecutar(pc, secuencia);

        //if (getCalificador() == Calificador.CANCELAR_COMPROBANTE) {
        //    this.params.put(Parametro.PDRTCFNC__DESCRIPCION, descr);
        //    this.params.put(Parametro.PDRTCFNC__MONTO, monto);
        //}

    }

    @Override
    public boolean interpretaRespuesta(byte[] resp) {
        try {
            if (getCalificador() != Calificador.CANCELAR_COMPROBANTE) {
                respuesta.put(IndexedOut.PDRTCFNC__RESTO_FALTA_PAGAR, GeneralFunc.extractVarStrFromArray(resp, 14, (char) 0x1C, (char) 0x03));
            }
        } catch (Exception e) {
            Logger.getLogger(pagoCancelDescRecaFNC.class).log(Level.ERROR, "", e);
            return false;
        }
        return true;
    }

    public static enum Calificador {

        CANCELAR_COMPROBANTE("C"),
        SUMA_IMPORTE_PAGADO("T"),
        ANULA_IMPORTE_PAGADO("t"),
        DESCUENTO("D"),
        RECARGO("R");

        Calificador(String letra) {
            this.letra = letra;
        }
        String letra = "";

        public String getLetra() {
            return letra;
        }

        public static Calificador parseString(String letra) {

            switch (letra.charAt(0)) {
                case 'C':
                    return Calificador.CANCELAR_COMPROBANTE;
                case 'T':
                    return Calificador.SUMA_IMPORTE_PAGADO;
                case 't':
                    return Calificador.ANULA_IMPORTE_PAGADO;
                case 'D':
                    return Calificador.DESCUENTO;
                case 'R':
                    return Calificador.RECARGO;
            }

            System.out.println("no se reconoce " + letra + "como calificador en pago de factura");

            return null;
        }
    }
}
