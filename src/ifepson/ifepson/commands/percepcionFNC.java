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
import ifepson.doc.Parametro;
import myjob.func.general.GeneralFunc;

/**
 *
 * @author guillermot
 */
public class percepcionFNC extends ifCommand {

    public String getDescripcionPercepcion() {
        return params.get(Parametro.PFNC__DESCRIPCION);
    }

    public void setDescripcionPercepcion(String descripcionPercepcion) {
        params.put(Parametro.PFNC__DESCRIPCION, descripcionPercepcion);
    }

    public double getMonto() {
        return Double.parseDouble(this.params.get(Parametro.PFNC__MONTO_PERCEPCION)) / 100;
    }

    public void setMonto(double monto) {
        this.params.put(Parametro.PFNC__MONTO_PERCEPCION, GeneralFunc.stringFormat("{0:0000000000}", monto * 100));
    }

    public double getTasa() {
        return Double.parseDouble(this.params.get(Parametro.PFNC__TASA_IVA)) / 100;
    }

    public void setTasa(double tasa) {
        this.params.put(Parametro.PFNC__TASA_IVA, GeneralFunc.stringFormat("{0:0000}", tasa * 100));
    }

    public TipoPercepcion getTipo() {
        return TipoPercepcion.parseString(this.params.get(Parametro.PFNC__TIPO_PERCEPCION));
    }

    public void setTipo(TipoPercepcion tipo) {
        this.params.put(Parametro.PFNC__TIPO_PERCEPCION, tipo.getLetra());
    }

    public percepcionFNC() {
        this.commandId = 0x66;
        this.name = "percepcionFNC";
        this.descripcionComando = "Se rechazará este comando si no hay una Factura, Nota de Crédito, Tique-Factura o Tique-Nota de Crédito abierto y al menos un ítem de venta facturado o si los montos acumulados generan un desbordamiento de total. Se usa este comando para imprimir información sobre percepciones Globales o de IVA. Si se envía una Percepción de IVA y no se han facturado productos a dicha tasa, el comando será rechazado. Importante: Las percepciones no van impresas entre productos facturados. Las percepciones se imprimen por descripción en el cierre de la Factura, Nota de Crédito, Tique-Factura ó Tique-Nota de Crédito y en el Cierre Z.";
        this.nombreA = "FACTPERCEP";

        this.params.put(Parametro.PFNC__DESCRIPCION, "");
        this.params.put(Parametro.PFNC__TIPO_PERCEPCION, TipoPercepcion.OtroTipo.getLetra());
        this.params.put(Parametro.PFNC__MONTO_PERCEPCION, GeneralFunc.stringFormat("{0:0000000000}", 0));
        this.params.put(Parametro.PFNC__TASA_IVA, GeneralFunc.stringFormat("{0:0000}", 0));
    }

    @Override
    public IFReturnValue ejecutar(myjob.func.io.PortConfig pc, int secuencia) throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException, IFException  {

            String monto = params.get(Parametro.PFNC__MONTO_PERCEPCION);
            String tasa = params.get(Parametro.PFNC__TASA_IVA);

            if (this.getTipo() != TipoPercepcion.IVATasaDeterminada) {
                this.params.put(Parametro.PFNC__MONTO_PERCEPCION, tasa);
                this.params.put(Parametro.PFNC__TASA_IVA, monto);
            }

            IFReturnValue retVal = super.ejecutar(pc, secuencia);

            if (this.getTipo() != TipoPercepcion.IVATasaDeterminada) {
                this.params.put(Parametro.PFNC__MONTO_PERCEPCION, monto);
                this.params.put(Parametro.PFNC__TASA_IVA, tasa);
            }

            return retVal;
    }

    @Override
    public boolean interpretaRespuesta(byte[] resp) {
        return true;
    }

    public enum TipoPercepcion {

        OtroTipo("O"),
        GlobalIVA("I"),
        IVATasaDeterminada("T");
        String letra = "";

        TipoPercepcion(String letra) {
            this.letra = letra;
        }

        public String getLetra() {
            return letra;
        }

        public static TipoPercepcion parseString(String letra) {
            switch (letra.charAt(0)) {
                case 'O':
                    return TipoPercepcion.OtroTipo;
                case 'I':
                    return TipoPercepcion.GlobalIVA;
                case 'T':
                    return TipoPercepcion.IVATasaDeterminada;
            }
            return null;
        }
    }
}
