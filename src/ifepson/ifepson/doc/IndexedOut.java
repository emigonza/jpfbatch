/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ifepson.doc;

import ifepson.ifCommand;
import ifepson.commands.*;

/**
 * Los codigos del 5000 al 5500 de salidas de estado fiscal
 * Los codigos del 5500 al 6000 de salidas de estado de impresora
 * Los códigos del 1 al 2000 son de
 * @author guillermot
 */
public enum IndexedOut {


    NRO_SECUENCIA                   ( 001, ifCommand.class,              DataType.Integer, 3, "Numero de secuencia"),
    COD_COMANDO                     ( 002, ifCommand.class,              DataType.Integer, 3, "Código de comando"),
    SALIDA_IMPRESOR                 ( 003, ifCommand.class,              DataType.Alfa,   -1, "Salida del impresor, solo se carga en caso de error"),
    PUERTO_SERIE                    ( 004, ifCommand.class,              DataType.Alfa,   -1, "Errores de puerto serie"),
    OTROS_ERRORES                   ( 005, ifCommand.class,              DataType.Alfa,   -1, "Errores varios"),
    SE__N_ULTIM_TIQ_O_FAC_B_C       ( 100, solEstado.class,              DataType.Integer, 8, "Número del último Comprobante Fiscal Tique o Factura B ,C o Tique-Factura B-C emitido (no cancelado) <nnnnnnnn>."),
    SE__N_FECH_PRIM_COMP            ( 101, solEstado.class,              DataType.Fecha,   6, "Fecha del primer Comprobante Fiscal y/o desde el inicio del primer Documento Fiscal, de la Jornada Fiscal en curso. <AAMMDD> <YYMMDD>"),
    SE__N_HORA_PRIM_COMP            ( 102, solEstado.class,              DataType.Hora,    6, "Hora del primer Comprobante Fiscal y/o desde el inicio del primer Documento Fiscal, de la Jornada Fiscal en curso <HHMMSS>"),
    SE__N_ULTIMA_Z                  ( 103, solEstado.class,              DataType.Integer, 5, "Número del último Cierre de la Jornada Fiscal <nnnnn>"),
    SE__N_DATO_AUDIT_PARC           ( 104, solEstado.class,              DataType.Integer, 8, "Dato de Auditoría parcial. Se recomienda ARCHIVAR diariamente este número antes de emitir el Cierre Z"),
    SE__N_DATO_AUDIT_TOTAL          ( 105, solEstado.class,              DataType.Integer, 8, "Dato de Auditoría Total. Se recomienda ARCHIVAR diariamente este número antes de emitir el Cierre Z"),
    SE__N_ID_TXT_AUDIT              ( 106, solEstado.class,              DataType.Alfa,   10, "Texto de Auditoría que Identifica el Impresor. Se recomienda ARCHIVAR diariamente este número antes de emitir el Cierre Z"),
    SE__N_TXT_AUDIT                 ( 107, solEstado.class,              DataType.Alfa,    2, "Texto de Auditoría. Se recomienda ARCHIVAR diariamente este número antes de emitir el Cierre Z"),
    SE__P_CANT_COL_10CPI            ( 110, solEstado.class,              DataType.Integer, 3, "Ancho de la Impresora en columnas a 10 CPI para Facturas. Si es Cero, no se puede imprimir a dicha resolución. <nnn> Los programadores deben utilizar esta información para ajustar la impresión, a la cantidad de columnas que están disponibles"),
    SE__P_CANT_COL_12CPI            ( 111, solEstado.class,              DataType.Integer, 3, "Ancho de la Impresora en columnas a 12 CPI para Facturas. Si es Cero, no se puede imprimir a dicha resolución. <nnn> Los programadores deben utilizar esta información para ajustar la impresión, a la cantidad de columnas que están disponibles."),
    SE__P_CANT_COL_17CPI            ( 112, solEstado.class,              DataType.Integer, 3, "Ancho de la Impresora en columnas a 17 CPI para Facturas. Si es Cero, no se puede imprimir a dicha resolución. <nnn> Los programadores deben utilizar esta información para ajustar la impresión, a la cantidad de columnas que están disponibles."),
    SE__P_CANT_COL_TIQUE            ( 113, solEstado.class,              DataType.Integer, 3, "Ancho en columnas para Tique, Tique-Factura o Tique-Nota de Crédito <nnn>"),
    SE__P_CANT_LIN_VALID            ( 114, solEstado.class,              DataType.Integer, 3, "Cantidad de líneas de Validación <nnn>Los programadores deben utilizar esta información para saber si la impresora fiscal puede hacer Validación Superior de documentos."),
    SE__P_PUEDE_TIQUET              ( 115, solEstado.class,              DataType.Boolean, 1, "‘1’ si la Impresora Fiscal puede hacer Tiques y ‘0’ si no esta habilitada a hacerlos. Los programadores deben utilizar esta información para saber si la impresora fiscal puede hacer Tiques."),
    SE__P_PUEDE_TIQUEFACT           ( 116, solEstado.class,              DataType.Boolean, 1, "‘1’ si la Impresora Fiscal puede hacer Tique-Factura y ‘0’ si no esta habilitada a hacerlos. Los programadores deben utilizar esta información para saber si la impresora fiscal puede hacer Tique-Factura."),
    SE__P_PUEDE_FACTURA             ( 117, solEstado.class,              DataType.Boolean, 1, "‘1’ Si la Impresora Fiscal puede hacer Facturas y ‘0’ si no esta habilitada a hacerlos. Los programadores deben utilizar esta información para saber si la impresora fiscal puede hacer Facturas en Hoja Suelta o formulario continuo."),
    SE__P_DIGIT_CENT_Z              ( 118, solEstado.class,              DataType.Integer, 1, "Cantidad de dígitos asignados para los centavos en el cierre Z o en el subtotal. Valor normal 2. <nn>"),
    SE__P_EST_PRINC_SELEC           ( 119, solEstado.class,              DataType.Integer, 2, "Estación principal actualmente seleccionada, donde se van a imprimir los datos. <nn> ‘R’ Impresión por salida de Tiques. ‘S’ Impresión por Hoja suelta o Formulario Continuo."),
    SE__P_MODELO_IMPRESORA          ( 120, solEstado.class,              DataType.Alfa,   15, "Modelo de la Impresora <aaaaaaaaaaaaaaa> Los programadores pueden utilizar esta información para saber que modelo de Impresora Fiscal tienen conectada."),
    SE__C_CUIT                      ( 130, solEstado.class,              DataType.Integer,11, "Número de Clave Única de Identificación Tributaria (CUIT) <nnnnnnnnnnn>"),
    SE__C_PTO_VTA                   ( 131, solEstado.class,              DataType.Integer, 4, "Número de Punto de Venta <nnnn>"),
    SE__C_TIPO_RESP                 ( 132, solEstado.class,              DataType.Alfa,    1, "Tipo de responsabilidad del contribuyente ‘I’ ASCII(73) (0x49) Responsable Inscripto ‘N’ ASCII(78) (0x4e) No Responsable ‘E’ ASCII(69) (0x45) Exento ‘M’ ASCII(77) (0x4d) Responsable Monotributo ‘T’ ASCII(84) (0x54) Monotributista Social"),
    SE__C_IVA_STD                   ( 133, solEstado.class,              DataType.Integer, 4, "Tasa Estándar de Impuesto al Valor Agregado (IVA) <.nnnn>"),
    SE__C_MONTO_MAX_TIQUEFAC        ( 134, solEstado.class,              DataType.Integer, 6, "Monto Máximo de un Tique-Factura (Igual que Tique-Nota de Crédito) <nnnnnnnnn.>"),
    SE__C_RAZON_SOC_COMPRADOR       ( 135, solEstado.class,              DataType.Alfa,   -1, "Razón Social del comprador. <aaa..40..aaa>"),
    SE__A_ULT_Z                     ( 140, solEstado.class,              DataType.Integer, 5, "Número del último Cierre de la Jornada Fiscal o cierre Z <nnnnn>"),
    SE__A_ULT_TIQUFAC_B_C           ( 141, solEstado.class,              DataType.Integer, 8, "Número del último Tique o Factura B,C o Tique-Factura B,C completamente emitido sin problemas <nnnnnnnn>."),
    SE__A_ULT_TIQFAC_B_C_IMPR       ( 142, solEstado.class,              DataType.Integer, 8, "Número del último Tique impreso o Factura B,C o Tique-Factura B,C <nnnnnnnn>"),
    SE__A_ULT_TIQFAC_A              ( 143, solEstado.class,              DataType.Integer, 8, "Número del último Tique-Factura A o Factura A completamente emitido sin problemas <nnnnnnnn>.,"),
    SE__A_ULT_TIQFAC_A_IMPR         ( 144, solEstado.class,              DataType.Integer, 8, "Número del último Tique-Factura A o Factura A impreso <nnnnnnnn>."),
    SE__A_ULT_NO_FISC               ( 145, solEstado.class,              DataType.Integer, 5, "Número del último Documento No Fiscal emitido <nnnnn>."),
    SE__A_ULT_NO_FISC_HOMO          ( 146, solEstado.class,              DataType.Integer, 5, "Número del último Documento No Fiscal Homologado emitido <nnnnn>. Este contador regresa a cero, luego de un Cierre Z."),
    SE__A_ULT_NRO_REF_DOC_NO_FISC   ( 147, solEstado.class,              DataType.Integer, 8, "Número del último número de referencia para Documentos No Fiscales o No Fiscales homologados emitido <nnnnnnnn>. Este contador es correlativo y no es modificado por el cierre ‘X’ o ‘Z’."),
    SE__A_ULT_TIQFAC_NOTA_CRED_A    ( 148, solEstado.class,              DataType.Integer, 8, "Número de último comprobante Tique-Nota de Crédito o Nota de Crédito ‘A’ emitido <nnnnnnnn>"),
    SE__A_ULT_TIQFAC_NOTA_CRED_B_C  ( 149, solEstado.class,              DataType.Integer, 8, "Número de último comprobante Tique-Nota de Crédito o Nota de Crédito ‘B’ o ‘C’ emitido <nnnnnnnn>"),
    SE__A_ULT_REMI                  ( 150, solEstado.class,              DataType.Integer, 8, "Número de último Remito emitido <nnnnnnnn>"),
    SE__D_TIPO_DOC                  ( 161, solEstado.class,              DataType.Alfa,    1, "Letra que indica el Tipo de Documento que se esta emitiendo <a>. ‘K’ Tique. ‘T’ Tique-Factura. ‘O’ Documento No Fiscal. ‘H’ Documento No Fiscal Homologado. ‘M’ Documento No Fiscal Homologado Tique Nota de Crédito"),
    SE__D_LETRA_DOC                 ( 162, solEstado.class,              DataType.Alfa,    1, "Letra que Caracteriza al documento <a>. ‘N’ No tiene una letra que identifique al documento. ‘A’ Documento emitido con letra A. ‘B’ Documento emitido con letra B. ‘C’ Documento emitido con letra C."),
    XZ__NRO_CIERRE                  ( 200, cierreXZ.class,               DataType.Integer, 5, "Número de cierre X /Z según corresponda <nnnnn>"),
    XZ__CANT_DOC_FISC_CANCEL        ( 201, solEstado.class,              DataType.Integer, 5, "Cuenta de Documentos Fiscales Cancelados <nnnnn>"),
    XZ__CANT_DOC_NO_FISC_HOMO       ( 202, solEstado.class,              DataType.Integer, 5, "Cuenta de documentos No Fiscales Homologados (D.N.F.H) <nnnnn>"),
    XZ__CANT_DOC_NO_FISC_NO_HOMO    ( 203, solEstado.class,              DataType.Integer, 5, "Cuenta de Documentos No Fiscales no homologados (D.N.F.) <nnnnn>"),
    XZ__CANT_TIQ_FAC_B_C            ( 204, solEstado.class,              DataType.Integer, 5, "Cuenta de Comprobantes Fiscales Tique, Factura B,C o Tique-Factura B,C emitidos <nnnnn>"),
    XZ__CANT_TIQ_FAC_A              ( 205, solEstado.class,              DataType.Integer, 5, "Cuenta de Comprobantes de Tique-Factura ‘A’ y Facturas ‘A’ emitidos. <nnnnn>"),
    XZ__ULT_TIQ_FAC_B_C             ( 206, solEstado.class,              DataType.Integer, 8, "Número de último comprobante de Tiques, Tique-Factura ‘B’ o ‘C’ y Facturas ‘B’ o ‘C’ emitidos <nnnnnnnn>"),
    XZ__TOTAL_FACT                  ( 207, solEstado.class,              DataType.Num4Dec,14, "Monto total Facturado <nnnnnnnnnnnn.nn>"),
    XZ__TOTAL_IVA                   ( 208, solEstado.class,              DataType.Num4Dec,14, "Monto total de IVA Cobrado <nnnnnnnnnnnn.nn>"),
    XZ__TOTAL_PERCEP                ( 209, solEstado.class,              DataType.Num4Dec,14, "Importe Total de las percepciones en Facturas o Tique-Facturas <nnnnnnnnnnnn.nn>"),
    XZ__ULT_TIQ_FAC_A               ( 210, solEstado.class,              DataType.Integer, 8, "Número de último comprobante Tique-Factura ‘A’ o Factura ‘A’ emitido <nnnnnnnn>"),
    XZ__ULT_TIQ_NOTA_CRED_A         ( 211, solEstado.class,              DataType.Integer, 8, "Número de último comprobante Tique-Nota de Crédito ‘A’ o Nota de Crédito ‘A’ emitido <nnnnnnnn>"),
    XZ__ULT_TIQ_NOTA_CRED_B_C       ( 212, solEstado.class,              DataType.Integer, 8, "Número de último comprobante Tique-Nota de Crédito o Nota de Crédito ‘B’ o ‘C’ emitido <nnnnnnnn>."),
    XZ__ULT_REMI                    ( 213, solEstado.class,              DataType.Integer, 8, "Número del último Remito emitido <nnnnnnnn> Disponible sólo en equipos homologados para formato Factura."),
    XZ__TOTAL_NOTA_CRED             ( 214, solEstado.class,              DataType.Num4Dec,14, "Importe Total de Notas de Crédito emitidas <nnnnnnnnnnnn.nn>"),
    XZ__TOTAL_IVA_NOTA_CRED         ( 215, solEstado.class,              DataType.Num4Dec,14, "Importe Total de IVA de Notas de Crédito <nnnnnnnnnnnn.nn>"),
    XZ__TOTAL_PRECEP_NOTA_CRED      ( 216, solEstado.class,              DataType.Num4Dec,14, "Importe Total de las Percepciones en Notas de Crédito o Tiques-Nota de Crédito <nnnnnnnnnnnn.nn>"),    STT__CANT_ITEMS                 (0300, subtotalTique.class,           DataType.Integer, 5, "Cantidad de ítems de línea facturados (nnnnn.)"),
    STT__TOTAL_A_PAGAR_BRUTO        ( 301, subtotalTique.class,          DataType.Num4Dec,12, "Total de mercadería o Total a pagar (nnnnnnnnnn.nn). se retorna el Importe Bruto"),
    STT__TOTAL_IVA                  ( 302, subtotalTique.class,          DataType.Num4Dec,12, "Total de impuesto IVA (nnnnnnnnnn.nn)"),
    STT__TOTAL_PAGO                 ( 303, subtotalTique.class,          DataType.Num4Dec,12, "Total pago (nnnnnnnnnn.nn)"),
    STT__TOTAL_IMP_INT_PORCENT      ( 304, subtotalTique.class,          DataType.Num4Dec,12, "Total de Impuestos Internos Porcentuales (nnnnnnnnnn.nn)"),
    STT__TOTAL_IMP_INT_FIJO         ( 305, subtotalTique.class,          DataType.Num4Dec,12, "Total de Impuestos Internos Fijos (nnnnnnnnnn.nn)"),
    STT__MONTO_NETO                 ( 306, subtotalTique.class,          DataType.Num4Dec,12, "Monto Neto o Total facturado sin Impuestos (nnnnnnnnnn.nn)"),
    PDRC__RESTO_FALTA_PAGAR         ( 400, pagoCancelDescRecaTique.class,DataType.Num4Dec,12, "Resto que Falta Pagar. (nnnnnnnnnn.nn)"),
    PDRC__TIQUE_CANCELADO           ( 401, pagoCancelDescRecaTique.class,DataType.Boolean, 1, "TIQUE CANCELADO"),
    CT__NRO_TIQUE                   ( 500, cerrarTique.class,            DataType.Integer, 8, "Número del Documento Fiscal recientemente emitido <nnnnnnnn>"),
    CDNF__NRO_TIQUE                 ( 600, cerrarDNF.class,              DataType.Integer, 7, "Número del Documento NO Fiscal recientemente emitido (nnnnnnn)"),
    LPU__IMPRESION_ROLLO_PAPEL      ( 700, leePrefUsuario.class,         DataType.Alfa,    1, "Seleccion de impresion en rollo de papel"),
    LPU__IMPRESION_HOJA_SUELTA      ( 701, leePrefUsuario.class,         DataType.Alfa,    1, "Seleccion de impresion en hoja suelta"),
    LPU__TAMA_DEFINIDO_POR_USR      ( 702, leePrefUsuario.class,         DataType.Alfa,    1, "Tamaño de papel definido por usuario"),
    LPU__PREF_SOL_NO_SETEADA        ( 703, leePrefUsuario.class,         DataType.Alfa,    1, "La preferencia solicitada no está seleccionada"),
    LPU__PREF_SOL_SETEADA           ( 704, leePrefUsuario.class,         DataType.Alfa,    1, "La preferencia solicitada está seleccionada"),
    LPU__TIPO_DOC_NO_FISCAL         ( 705, leePrefUsuario.class,         DataType.Alfa,    1, "Tipo de de documento a imprimir: documento no fiscal"),
    LPU__CANTIDAD_COLUMNAS          ( 706, leePrefUsuario.class,         DataType.Integer, 1, "Cantidad de columnas"),
    LPU__CANTIDAD_FILAS             ( 707, leePrefUsuario.class,         DataType.Integer, 1, "Cantidad de filas"),
    OFH__FECHA                      ( 800, getFechaHora.class,           DataType.Fecha,   6, "Fecha AAMMDD"),
    OFH__HORA                       ( 800, getFechaHora.class,           DataType.Hora,    6, "Hora HHMMSS"),
    EI__ERROR_EN_LA_IMPRESORA       (5000, ifCommand.class,              DataType.Boolean, 1, "Error y/o falla de la impresora"),
    EI__IMPRES_FUERA_LINEA          (5001, ifCommand.class,              DataType.Boolean, 1, "Impresora fuera de linea"),
    EI__BUFFER_LLENO                (5002, ifCommand.class,              DataType.Boolean, 1, "Buffer de Impresora lleno"),
    EI__BUFFER_VACIO                (5003, ifCommand.class,              DataType.Boolean, 1, "Buffer de impresora vacío"),
    EI__ENTRADA_HOJ_SUEL_FRONT_PREP (5004, ifCommand.class,              DataType.Boolean, 1, "Entrada de hojas sueltas frontal preparada"),
    EI__HOJ_SUEL_FROT_PREPAR        (5005, ifCommand.class,              DataType.Boolean, 1, "Hojas sueltas frontal preparada"),
    EI__SIN_PAPEL                   (5006, ifCommand.class,              DataType.Boolean, 1, "Impresora sin papel"),
    EI__ERROR_ESTADO_IMPRESORA      (5007, ifCommand.class,              DataType.Alfa,   16, "Hay un error en el checkeo del estado de la impresora, veerificar todos los indicadores"),
    EI__POCO_PAPEL                  (5008, ifCommand.class,              DataType.Boolean, 1, "Poco papel"),
    EF__ERR_COMPROB_MEM_FISC        (5101, ifCommand.class,              DataType.Boolean, 1, "Error de comprobacion de memoria fiscal"),
    EF__ERR_COMPROB_MEM_TRAB        (5102, ifCommand.class,              DataType.Boolean, 1, "Error de comprobacion de memoria de trabajo"),
    EF__POCA_BATERIA                (5103, ifCommand.class,              DataType.Boolean, 1, "Poca bateria"),
    EF__ERR_COMAND_NO_RECON         (5104, ifCommand.class,              DataType.Boolean, 1, "Comando no reconocido"),
    EF__ERR_CAMPO_DATOS_INVALID     (5105, ifCommand.class,              DataType.Boolean, 1, "Campo de datos invalido"),
    EF__ERR_COMANDO_INV_P_EST_FISC  (5106, ifCommand.class,              DataType.Boolean, 1, "Comando invalido para estado fiscal"),
    EF__ERR_DESBORD_TOTALES         (5107, ifCommand.class,              DataType.Boolean, 1, "Desbordamiento de totales"),
    EF__MEM_FISC_LLENA              (5108, ifCommand.class,              DataType.Boolean, 1, "Memoria fiscal llena"),
    EF__MEM_FISC_CASI_LLENA         (5109, ifCommand.class,              DataType.Boolean, 1, "Memoria fiscal casi llena"),
    EF__IMPRES_FISCAL_FISCALIZADO   (5110, ifCommand.class,              DataType.Boolean, 1, "Impresor fiscal fiscalizado"),
    EF__IMPRES_FISCAL_CERTIFICADO   (5111, ifCommand.class,              DataType.Boolean, 1, "Impresor fiscal Certificado"),
    EF__IMPRES_FISCAL_DESFISCALIZADO(5112, ifCommand.class,              DataType.Boolean, 1, "Impresor fiscal desfiscalizado por software"),
    EF__NECESITA_Z                  (5113, ifCommand.class,              DataType.Boolean, 1, "Es necesario hacer un cierre Z o se ha enviado un numero max de items y se necesita cerarlo"),
    EF__ERR_NECESITA_TRANSPORTE_HOJA(5114, ifCommand.class,              DataType.Boolean, 1, "Es necesario realizar un transporte de hoja"),
    EF__DOCUM_FISC_ABIERTO          (5115, ifCommand.class,              DataType.Boolean, 1, "Documento fiscal abierto"),
    EF__DOCUM_FISCAL_ABI_ROLLO_PAPEL(5116, ifCommand.class,              DataType.Boolean, 1, "Documento fiscal o no fiscal que se emite por rollo de papel abierto"),
    EF__DNF_ABIERTO_ROLLO_PAPEL     (5117, ifCommand.class,              DataType.Boolean, 1, "Documento no fiscal que se emite por rollo de papel abierto"),
    EF__DOCUM_ABIERTO_HOJA_SUELTA   (5118, ifCommand.class,              DataType.Boolean, 1, "Factura o impresion en hoja suelta inicializada"),
    EF__ERROR_ESTADO_FISCAL         (5119, ifCommand.class,              DataType.Alfa,   16, "Hay un error en el checkeo del estado fiscal, verificar todos los indicadores"),
    STFNC__CANT_ITEMS               (5200, subtotalFNC.class,            DataType.Integer, 5, "Cantidad de ítems de línea facturados (nnnnn.)"),
    STFNC__TOTAL_A_PAGAR_BRUTO      (5201, subtotalFNC.class,            DataType.Num4Dec,12, "Total de mercadería (nnnnnnnnnn.nn) Siempre se retorna el Importe Bruto (Base Imponible más losImpuestos)."),
    STFNC__TOTAL_IVA                (5202, subtotalFNC.class,            DataType.Num4Dec,12, "Total de impuesto IVA (nnnnnnnnnn.nn)"),
    STFNC__TOTAL_PAGO               (5203, subtotalFNC.class,            DataType.Num4Dec,12, "Total pago (nnnnnnnnnn.nn)"),
    STFNC__TOTAL_IMP_INT_PORCENT    (5204, subtotalFNC.class,            DataType.Num4Dec,12, "Total de Impuestos Internos Fijos (nnnnnnnnnn.nn)"),
    STFNC__TOTAL_IMP_INT_FIJO       (5205, subtotalFNC.class,            DataType.Num4Dec,12, "Monto Neto o Total facturado sin Impuestos (nnnnnnnnnn.nn)"),
    STFNC__MONTO_NETO               (5206, subtotalFNC.class,            DataType.Num4Dec,12, "Monto Neto o Total facturado sin Impuestos (nnnnnnnnnn.nn)"),
    PDRTCFNC__RESTO_FALTA_PAGAR     (6300, pagoCancelDescRecaFNC.class,  DataType.Num4Dec,12, "Resto que Falta Pagar. <nnnnnnnnnn.nn> Importante: En el caso de Cancelar un Documento, solo se retornan los campos de datos 1 y 2."),
    CFNC__NRO_COMPROBANTE           (6400, cerrarFNC.class,              DataType.Integer, 8, "Número del Documento Fiscal que se acaba de EMITIR.");


    IndexedOut(int cod, Class comando, DataType type, int length, String description) {
        this.cod = cod;
        this.type = type;
        this.description = description;
        this.length = length;
        this.comando = comando;
    }

    Integer cod = -1;
    DataType type = DataType.Alfa;
    String description = "";
    Integer length = 0;
    Class comando = null;

    public Integer getCod() {
        return cod;
    }

    public String getDescripcion() {
        return description;
    }

    public Integer getLargo() {
        return length;
    }

    public DataType getType() {
        return type;
    }

    public Class getComando() {
        return comando;
    }

}
