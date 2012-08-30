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
public class pagoCancelDescRecaTique extends ifCommand {

    public Calificador getCalificador() {
        return Calificador.parseLetra(params.get(Parametro.PDRC__CALIFICADOR));
    }

    public void setCalificador(Calificador calificador) {
        params.put(Parametro.PDRC__CALIFICADOR, calificador.getLetra());
    }

    public String getDescripcionEnTique() {
        return params.get(Parametro.PDRC__DESCRIPCION);
    }

    public void setDescripcionEnTique(String descripcionEnTique) {
        this.params.put(Parametro.PDRC__DESCRIPCION, GeneralFunc.padRight(descripcionEnTique, 26, ' '));
    }

    public Double getMonto() {
        return Double.parseDouble(params.get(Parametro.PDRC__MONTO)) / 100;
    }

    public void setMonto(Double monto) {
        this.params.put(Parametro.PDRC__MONTO, GeneralFunc.stringFormat("{0:000000000}", monto * 100));
    }

    public pagoCancelDescRecaTique() {
        this.commandId = 0x44;
        this.name = "pagoCancelarDecuentoRecargo";
        this.nombreA = "TIQUEPAGO";
        this.descripcionComando = "Se rechazará este comando si no hay un comprobante fiscal abierto. Se rechazará si los montos acumulados generan un desbordamiento del total. Se rechazará si hay un papel en las estaciones de slip o validación, si no hay papel en la estación de rollo, o si se usó la máxima cantidad de pagos permitida. Se usa este comando para imprimir información del total y del pago de la transacción. Cuando se envía un PAGO al Impresor Fiscal, se almacena y se imprime junto con el TOTAL cuando se cierra el Tique. Las transacciones de ventas deben emitir comandos de pago y pagar el total completo de la transacción antes de emitir un comando de Cierre de Comprobante Fiscal. Después de éste, no se pueden emitir nuevos comandos de impresión de ítem de línea. Una vez enviado el PAGO, sólo se aceptan comandos PAGO, CERRAR Tique o CANCELAR. Serán aceptados 5 (cinco) pagos como máximo por cada tique.";

        this.params.put(Parametro.PDRC__DESCRIPCION, GeneralFunc.padRight(" ", 26, ' '));
        this.params.put(Parametro.PDRC__MONTO, GeneralFunc.stringFormat("{0:000000000}", 0));
        this.params.put(Parametro.PDRC__CALIFICADOR, Calificador.SUMA_IMPORTE_PAGADO.getLetra());
    }

    @Override
    public IFReturnValue ejecutar(myjob.func.io.PortConfig pc, int secuencia) throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException, IFException {
       

            String descripcion = this.getDescripcionEnTique();
            Double monto = this.getMonto();
/*
            if (getCalificador() == Calificador.CANCELAR_COMPROBANTE) {
                this.params.remove(Parametro.PDRC__DESCRIPCION);
                this.params.remove(Parametro.PDRC__MONTO);
            }
*/
            IFReturnValue retVal = super.ejecutar(pc, secuencia);

            if (getCalificador() == Calificador.CANCELAR_COMPROBANTE) {
                this.setDescripcionEnTique(descripcion);
                this.setMonto(monto);
            }

            return retVal;
      
    }

    @Override
    public boolean interpretaRespuesta(byte[] resp) {
        try {
            if (getCalificador() != Calificador.CANCELAR_COMPROBANTE) {
                respuesta.put(IndexedOut.PDRC__RESTO_FALTA_PAGAR, GeneralFunc.extractVarStrFromArray(resp, 14, (char) 0x1C, (char) 0x03));
            } else {
                respuesta.put(IndexedOut.PDRC__TIQUE_CANCELADO, "1");
            }
        } catch (Exception e) {
            Logger.getLogger(pagoCancelDescRecaTique.class).log(Level.ERROR, "", e);
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

        public static Calificador parseLetra(String letra) {

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

            return null;
        }
    }
}
