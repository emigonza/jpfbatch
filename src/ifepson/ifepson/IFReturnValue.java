/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ifepson;

/**
 *
 * @author guillermot
 */
public enum IFReturnValue {

    OK("todo bien"),
    NEED_RETRY("recesita reintentar"),
    COMMAND_TIMEOUT_ERROR("ERROR: tiempo de espera esta agotado en enviar comando"),
    RESPONSE_TIMEOUT_ERROR("ERROR: tiempo de espera esta agotado en recibir respuesta"),
    EMPTY_RESPONSE_ERROR("ERROR: respuesta es vacia"),
    UNKNOW_ERROR("ERROR: error desconocido"),
    FISCAL_ERROR("ERROR: error fiscal"),
    UNKNOW_RESPONSE_ERROR("ERROR: error al interpretar respuesta"),
    UNKNOW_SERIAL_PORT_ERROR("ERROR: no existe el puerto serie"),
    SERIAL_PORT_IN_USE_ERROR("ERROR: puerto serieen uso"),
    NAK_RECIVED_ERROR("ERROR: recibí un NAK"),
    PRINTER_ERROR("ERROR: error de impresion"),
    SERIAL_OUT_OF_RANGE_ERROR("ERROR: secuencia fuera de rango"),
    SERIAL_PORT_UNSOPORTED_OP_ERROR("ERROR: Operacion no soportada por el puerto serie");
    
    String description = "";

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    IFReturnValue(String description) {
        this.description = description;
    }
}
