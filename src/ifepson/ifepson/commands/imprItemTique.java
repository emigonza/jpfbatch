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
public class imprItemTique extends ifCommand {

    public Double getMontoImpuestoInterno() {

        Double entero = Double.parseDouble(params.get(Parametro.IIT__IMPUESTO_INTERNO).substring(0, 6));

        Double decimal = Double.parseDouble("0." + params.get(Parametro.IIT__IMPUESTO_INTERNO).substring(7));

        return entero + decimal;
    }

    public void setMontoImpuestoInterno(Double montoImpuestoInterno) {
        Double val = (montoImpuestoInterno - Math.floor(montoImpuestoInterno)) * 100000000;

        this.params.put(Parametro.IIT__IMPUESTO_INTERNO, GeneralFunc.stringFormat("{0:0000000}", montoImpuestoInterno) + GeneralFunc.stringFormat("{0:00000000}", val));
    }

    public Integer getBultos() {
        return Integer.parseInt(this.params.get(Parametro.IIT__CANTIDAD_BULTOS));
    }

    public void setBultos(Integer bultos) {
        this.params.put(Parametro.IIT__CANTIDAD_BULTOS, GeneralFunc.stringFormat("{0:00000}", bultos));
    }

    public CalificadorItem getCalificador() {
        return CalificadorItem.parseString(params.get(Parametro.IIT__CALIF_ITEM));
    }

    public void setCalificador(CalificadorItem calificadorItem) {
        params.put(Parametro.IIT__CALIF_ITEM, calificadorItem.getLetra());
    }

    public double getCantidad() {

        Double entero = Double.parseDouble(params.get(Parametro.IIT__CANT_UNIDADES).substring(0, 4));

        Double decimal = Double.parseDouble("0." + params.get(Parametro.IIT__CANT_UNIDADES).substring(5));

        return entero + decimal;
    }

    public void setCantidad(double cantidad) {
        Double val = (cantidad - Math.floor(cantidad)) * 1000;
        this.params.put(Parametro.IIT__CANT_UNIDADES, GeneralFunc.stringFormat("{0:00000}", cantidad) + GeneralFunc.stringFormat("{0:000}", val));
    }

    public String getDescripcionProducto() {
        return params.get(Parametro.IIT__DESCRIPCION_PROD);
    }

    public void setDescripcionProducto(String descripcionProducto) {
        params.put(Parametro.IIT__DESCRIPCION_PROD, descripcionProducto);
    }

    public double getIva() {
        return Double.parseDouble(params.get(Parametro.IIT__TASA_IMPOSITIVA)) / 100;
    }

    public void setIva(double iva) {
        this.params.put(Parametro.IIT__TASA_IMPOSITIVA, GeneralFunc.stringFormat("{0:0000}", iva * 100));
    }

    public double getPrecioUnitario() {
        if (this.params.get(Parametro.IIT__PRECIO_UNITARIO).contains(".")) {
            return Double.parseDouble(this.params.get(Parametro.IIT__PRECIO_UNITARIO));
        }

        Double entero = Double.parseDouble(params.get(Parametro.IIT__PRECIO_UNITARIO).substring(0, 6));

        Double decimal = Double.parseDouble("0." + params.get(Parametro.IIT__PRECIO_UNITARIO).substring(7));

        return entero + decimal;
    }

    public void setPrecioUnitario(double precioUnitario) {
        Double val = (precioUnitario - Math.floor(precioUnitario)) * 10000;
        this.params.put(Parametro.IIT__PRECIO_UNITARIO, GeneralFunc.stringFormat("{0:0000000}", precioUnitario) + "." + GeneralFunc.stringFormat("{0:0000}", val));
    }

    public double getTasaAjusteVariable() {
        return Double.parseDouble(this.params.get(Parametro.IIT__TASA_AJUSTE_VAR)) / 100000000;
    }

    public void setTasaAjusteVariable(double tasaAjusteVariable) {
        this.params.put(Parametro.IIT__TASA_AJUSTE_VAR, GeneralFunc.stringFormat("{0:00000000}", tasaAjusteVariable * 100000000));
    }

    public imprItemTique() {
        this.commandId = 0x42;
        this.name = "imprimirItemDeLineaEnTiqueFiscal";
        this.nombreA = "TIQUEITEM";
        this.descripcionComando = "No se aceptará el comando si no hay un comprobante fiscal abierto. Se lo rechazará si hay papel en la entrada para impresión o validación de hojas sueltas. Se rechazará si la acumulación de montos genera un desborde de totales. IMPORTANTE: Un ítem de línea no puede tener el ajuste de la base imponible e Impuestos Internos Fijos al mismo tiempo.";

        this.params.put(Parametro.IIT__DESCRIPCION_PROD, "");

        this.params.put(Parametro.IIT__CANT_UNIDADES, GeneralFunc.stringFormat("{0:00000000}", 0));

        this.params.put(Parametro.IIT__PRECIO_UNITARIO, GeneralFunc.stringFormat("{0:0000000.0000}", 0));

        this.params.put(Parametro.IIT__TASA_IMPOSITIVA, GeneralFunc.stringFormat("{0:0000}", 0));

        this.params.put(Parametro.IIT__CALIF_ITEM, CalificadorItem.MONTO_AGREGADO_O_VENTA_SUMA.getLetra());

        this.params.put(Parametro.IIT__CANTIDAD_BULTOS, GeneralFunc.stringFormat("{0:0000}", 0));

        this.params.put(Parametro.IIT__TASA_AJUSTE_VAR, GeneralFunc.stringFormat("{0:00000000}", 0));

        this.params.put(Parametro.IIT__IMPUESTO_INTERNO, GeneralFunc.stringFormat("{0:0000000}", 0) + GeneralFunc.stringFormat("{0:00000000}", 0));
    }


    @Override
    public boolean interpretaRespuesta(byte[] resp) {
        return true;
    }

    public static enum CalificadorItem {

        MONTO_AGREGADO_O_VENTA_SUMA("M"),
        ANULA_ITEM_RESTA("m"),
        BONIFICACION_RESTA("R"),
        ANULA_BONIFICACION("r");

        CalificadorItem(String letra) {
            this.letra = letra;
        }
        String letra = "";

        public String getLetra() {
            return letra;
        }

        public static CalificadorItem parseString(String letra) {

            switch (letra.charAt(0)) {
                case 'M':
                    return CalificadorItem.MONTO_AGREGADO_O_VENTA_SUMA;
                case 'm':
                    return CalificadorItem.ANULA_ITEM_RESTA;
                case 'R':
                    return CalificadorItem.BONIFICACION_RESTA;
                case 'r':
                    return CalificadorItem.ANULA_BONIFICACION;
            }

            return null;
        }
    }
}
