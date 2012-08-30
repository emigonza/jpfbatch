/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ifepson.doc;

import ifepson.commands.*;

/**
 *
 * @author Administrador
 */
public enum Parametro {

    SE__TIPO_SOLICITUD          (   1, solEstado.class, DataType.Alfa,      1, "Tipo de información solicitada. ‘N’ Información Normal o compatible con modelos anteriores. ‘P’ Información sobre las Características del Controlador Fiscal. ‘C’ Información sobre el contribuyente. ‘A’ Información sobre los contadores de documentos fiscales y no fiscales.‘D’ Información sobre el documento que se esta emitiendo. ‘S’ devuelve las preferencias del sistema", 0),
    XZ__TIPO_CIERRE             ( 100, cierreXZ.class, DataType.Alfa,      1, "Si se envía ‘Z’ 0x5a, ASCII (90 Decimal):Grabar Totales Diarios en la Memoria Fiscal y borrar los Totales Diarios de la Memoria de Trabajo. Se hace un Cierre ‘Z’. Si se envía ‘X’ 0x58, ASCII (88 Decimal): Realizar un cambio de Cajero. Se hace un Cierre ‘X’.", 0),
    XZ__IMPRIMIR                ( 101, cierreXZ.class, DataType.Alfa,      1, "Si se envía 'P' ó 0x50 ó ASCII (80 Decimal) el Reporte ‘X’ sale impreso.", true, 1),
    RF__FECHA_INICIO            ( 201, repoMemFiscFecha.class, DataType.Fecha,     6, "Fecha de inicio de selección AAMMDD", 0),
    RF__FECHA_FIN               ( 202, repoMemFiscFecha.class, DataType.Fecha,     6, "Fecha de fin de selección AAMMDD", 1),
    RF__TIPO_REPORTE           ( 203, repoMemFiscFecha.class, DataType.Alfa,      1, "Se envía ´T´ 0x54 para un Total General, sin detalle diario como Documento No Fiscal con centavos (“Reporte de Contador” resumido). Se envía ´D´ 0x44 para un reporte detallado como Documento No Fiscal con centavos (“Reporte de Contador” con detalles). Se envía ´t´ 0x74 para un Total General, sin detalle diario como Documento Fiscal con redondeo al peso (“Informe de Auditoría” resumido). Se envía ´d´ 0x64 para un reporte detallado como Documento Fiscal con redondeo al peso (“Informe de Auditoría” con detalles).", 2),
    RZ__Z_INICIO                 ( 301, repoMemFiscZ.class, DataType.Integer,   4, "Número de Cierre ´Z´ inicial del rango elegido.", 0),
    RZ__Z_FIN                    ( 302, repoMemFiscZ.class, DataType.Integer,   4, "Número de Cierre ´Z´ final del rango elegido.", 1),
    RZ__TIPO_REPORTE           ( 303, repoMemFiscZ.class, DataType.Alfa,      1, "Se envía ´T´ 0x54 para un Total General, sin detalle diario como Documento No iscal con centavos (“Reporte de Contador” resumido). Se envía ´D´ 0x44 para un reporte detallado como Documento No iscal con centavos (“Reporte de Contador” con detalles). Se envía ´t´ 0x74 para un Total General, sin detalle diario como Documento iscal con redondeo al peso (“Informe de Auditoría” resumido). Se envía ´d´ 0x64 para un reporte detallado como Documento iscal con redondeo al peso (“Informe de Auditoría” con detalles).", 2),
    ABT__FORMATO_DATOS        (401, abrirTique.class, DataType.Alfa,            1, "Formato para almacenar los datos <a> ‘C’ ASCII(67) 0x43 Luego del Tique, no se va a realizar un Documento No Fiscal Homologado para Farmacias. ‘G’ ASCII(71) 0x47 Se prepara el equipo para la impresión del D.N.F.H. para Farmacias, cuando se termina de emitir el Tique.", true, 0),
    ITFT__DESCRIP_EXTRA         (501, imprTxtFiscTique.class, DataType.Alfa,           26, "Línea de Descripción Extra en Tique: Texto Fiscal a Imprimir como línea de descripción extra previa a un ítem de línea en un Tique Fiscal. (Máximo 26 caracteres).", 0),
    IIT__DESCRIPCION_PROD       (600, imprItemTique.class, DataType.Alfa,           26, "Descripción del ítem a vender. Máximo 26 caracteres de Texto Fiscal. En caso de emitirse con tasa de IVA no estándar, el máximo será de 18 caracteres. En caso de, además, poseer impuestos internos, el máximo que se imprimirá será de 11 caracteres, truncando el resto.", 0),
    IIT__CANT_UNIDADES         (601, imprItemTique.class, DataType.Num3Dec,      8, "Cantidad de unidades. (nnnnn.nnn) Nota: Manejo de 5 enteros y 3 decimales, se debe enviar sin el punto.", 1),
    IIT__PRECIO_UNITARIO        (602, imprItemTique.class, DataType.Num4Dec,      12, "Precio Unitario del Item. (nnnnnnn.nnnn) Nota 1: Manejo de 7 enteros y 4 decimales, se debe enviar con el punto decimal. Nota 2: Compatible con manejo de 2 decimales (nnnnnnn.nn), en este caso se debe enviar 7 enteros y 2 decimales sin el punto decimal.", 2),
    IIT__TASA_IMPOSITIVA         (603, imprItemTique.class, DataType.Num2Dec,      4, "Tasa Impositiva. Alícuota de IVA (nn.nn) Nota: Se envía sin el punto decimal, por ejemplo 21,00 % de IVA se debe enviar “2100”.", 3),
    IIT__CALIF_ITEM               (604, imprItemTique.class, DataType.Alfa,       1, "Calificador de Item de Línea: 'M'= 0x4d, Monto agregado de mercadería o ítem de venta, SUMA. 'm'= 0x6d, anula el ítem vendido, RESTA. 'R'= 0x52, Bonificación, RESTA. 'r'= 0x72, anula una Bonificación, SUMA En el caso de Bonificación, se antepone automáticamente la leyenda “BONIF.” a la descripción del artículo. ", 4),
    IIT__CANTIDAD_BULTOS       (605, imprItemTique.class, DataType.Integer,   5, "Unidades o Bultos Vendidos. (nnnnn.)", 5),
    IIT__TASA_AJUSTE_VAR        (606, imprItemTique.class, DataType.Num8Dec,     8, "Tasa de Ajuste Variable. (.nnnnnnnn) Nota: Se debe enviar sólo los decimales, hasta 8 dígitos, sin el punto. Ver documentación de modelos anteriores por detalles del cálculo.", 6),
    IIT__IMPUESTO_INTERNO      (607, imprItemTique.class, DataType.Num8Dec,     15, "Monto Impuestos Internos Fijos. <nnnnnnn.nnnnnnnn>", 7),
    STT__IMPRIMIR               (608, subtotalTique.class, DataType.Alfa,       1, "Impresión del Subtotal: Si se envía ‘P’ (0x50) se imprimirá el Subtotal. Si se envía ‘N’ (0x4E) no se imprimirá el Subtotal (sólo se retornará la            información a la PC en este caso).", 0),
    STT__DESCRIPCION           (609, subtotalTique.class, DataType.Alfa,       25, "NO SE UTILIZA", 1),
    PDRC__DESCRIPCION         (700, pagoCancelDescRecaTique.class, DataType.Alfa,      26, "Descripción: Texto Fiscal Variable. Máximo de 26 caracteres.", 0),
    PDRC__MONTO               (701, pagoCancelDescRecaTique.class, DataType.Num2Dec,      9, "Monto de Pago / Descuento / Recargo (nnnnnnnnn.nn) Nota: Manejo de 9 enteros y 2 decimales, se debe enviar sin el punto", 1),
    PDRC__CALIFICADOR         (702, pagoCancelDescRecaTique.class, DataType.Alfa,        1, "Calificador de Pago / Descuento / Recargo / Cancelar:  'C'= 0x43, Cancelar Comprobante.  'T'= 0x54, Suma al Importe Pagado.  't'= 0x74, Anula un Pago hecho con ‘T’.  'D'= 0x44, Realiza un descuento global por monto fijo.  'R'= 0x44, Realiza un recargo global por monto fijo.", 2),
    CT__CORTE_TOTAL           (800, cerrarTique.class, DataType.Alfa,       1, "Tipo de Corte sobre el papel al finalizar la impresión ‘T’ Corte Total (Recomendado). ‘P’ Corte Parcial. Nota: Si no fuera soportada esta opción el IF efectuará un Corte Total", 0),
    ITDNF__DESCRIPCION        (900, imprTxtDNF.class, DataType.Alfa,      40, "Hasta 40 Caracteres de Texto No Fiscal", 0),
    CDNF__CORTE_TOTAL        (1000, cerrarDNF.class, DataType.Alfa,       1, "Tipo de Corte sobre el papel al finalizar la impresión ‘T’ Corte Total (Recomendado). ‘P’ Corte Parcial. Nota: Si no fuera soportada esta opción el IF efectuará un Corte Total", 0),
    CNFHTC__01                 (1100, DNFHTarjetaCredito.class, DataType.Integer,    2, "‘01’ Comprobante No Fiscal Homologado Voucher Tarjeta de Crédito <0x30,0x31> <nn>.", 0),
    CNFHTC__NOMBRE_TARJETA  (1101, DNFHTarjetaCredito.class, DataType.Alfa,      34, "Descripción de Texto Fiscal Variable para identificar el nombre de la tarjeta de crédito. Longitud máxima 34 Caracteres. Si se envía el Caracter DEL (0x7F) ó (127 en decimal) no se imprime esta línea.", 1),
    CNFHTC__NRO_TARJETA      (1102, DNFHTarjetaCredito.class, DataType.Alfa,      31, "Descripción de Texto Fiscal Variable para identificar el número de la tarjeta de crédito. Longitud máxima 31 Caracteres. Si se envía el Caracter DEL (0x7F) ó (127 en decimal) no se imprime esta línea.", 2),
    CNFHTC__USUARIO           (1103, DNFHTarjetaCredito.class, DataType.Alfa,      31, "Descripción de Texto Fiscal Variable para identificar el nombre del usuario de la tarjeta de crédito. Longitud máxima 31 Caracteres. Si se envía el Caracter DEL (0x7F) ó (127 en decimal) no se imprime esta línea.", 3),
    CNFHTC__VENC_TARJ         (1104, DNFHTarjetaCredito.class, DataType.Fecha,      6, "Fecha de vencimiento de la tarjeta de crédito. El formato es AAMMDD (Año, mes, día). Dado que las tarjetas de crédito no tienen el día de vencimiento, se debe enviar el día 01. Si se envía ‘000000’no se imprime esta línea. <AAMMDD>", 4),
    CNFHTC__NRO_ESTAB        (1105, DNFHTarjetaCredito.class, DataType.Alfa,       26, "Descripción de Texto Fiscal Variable para identificar el número de establecimiento. Longitud máxima 26 Caracteres. Si se envía el Caracter DEL (0x7F) ó (127 en decimal) no se imprime esta línea.", 5),
    CNFHTC__NRO_CUPON       (1106, DNFHTarjetaCredito.class, DataType.Alfa,      28, "Descripción de Texto Fiscal Variable para identificar el número de cupón. Longitud máxima 28 Caracteres. Si se envía el Caracter DEL (0x7F) ó (127 en decimal) no se imprime esta línea.", 6),
    CNFHTC__NRO_INT_COMP    (1107, DNFHTarjetaCredito.class, DataType.Alfa,      25, "Descripción de Texto Fiscal Variable para identificar el número interno del comprobante que se esta emitiendo. Es un número de referencia interna que se utiliza en algunos supermercados. Longitud máxima 25 Caracteres. Si se envía el Caracter DEL (0x7F) ó (127 en decimal) no se imprime esta línea.", 7),
    CNFHTC__COD_AUTORIZ     (1108, DNFHTarjetaCredito.class, DataType.Alfa,      25, "Descripción de Texto Fiscal Variable para identificar el código autorización de la transacción electrónica. Longitud máxima 25 Caracteres. Si se envía el Caracter DEL (0x7F) ó (127 en decimal) no se imprime esta línea.", 8),
    CNFHTC__TIPO_OPERAC      (1109, DNFHTarjetaCredito.class, DataType.Alfa,      29, "Descripción de Texto Fiscal Variable para identificar el tipo de operación. Longitud máxima 29 Caracteres. Si se envía el Caracter DEL (0x7F) ó (127 en decimal) no se imprime esta línea.", 9),
    CNFHTC__IMPORTE          (1110, DNFHTarjetaCredito.class, DataType.Num4Dec,  11, "Importe que se debe pagar. Formato <nnnnnnnnn.nn> (se debe enviar sin punto decimal). En caso de enviar un dato erróneo o no enviar un dato se imprimirá importe nulo.", 10),
    CNFHTC__CANT_CUOTAS     (1111, DNFHTarjetaCredito.class, DataType.Alfa,       26, "Descripción de Texto Fiscal Variable para identificar la cantidad de cuotas.Longitud máxima 26 Caracteres. Si se envía el Caracter DEL (0x7F) ó (127 en decimal) no se imprime esta línea.", 11),
    CNFHTC__MONEDA          (1112, DNFHTarjetaCredito.class, DataType.Alfa,       32, "Descripción de Texto Fiscal Variable para identificar el tipo de moneda en que se ha realizado la transacción. Longitud máxima 32 Caracteres. Si se envía el Caracter DEL (0x7F) ó (127 en decimal) no se imprime esta línea.", 12),
    CNFHTC__NRO_TERMINAL    (1113, DNFHTarjetaCredito.class, DataType.Alfa,       25, "Descripción de Texto Fiscal Variable para identificar el número de terminal. Es un número interno que se usa en algunos supermercados. Longitud máxima 25 Caracteres. Si se envía el Caracter DEL (0x7F) ó (127 en decimal) no se imprime esta línea.", 13),
    CNFHTC__NRO_LOTE         (1114, DNFHTarjetaCredito.class, DataType.Alfa,       29, "Descripción de Texto Fiscal Variable para identificar el número de lote. Es un número interno que se usa en algunos supermercados. Longitud máxima 29 Caracteres. Si se envía el Caracter DEL (0x7F) ó (127 en decimal) no se imprime esta línea.", 14),
    CNFHTC__NRO_TERM_ELEC   (1115, DNFHTarjetaCredito.class, DataType.Alfa,       27, "Descripción de Texto Fiscal Variable para identificar el número de terminal electrónica. Es un número interno que se usa uso en algunos supermercados. Longitud máxima 27 Caracteres. Si se envía el Caracter DEL (0x7F) ó (127 en decimal) no se imprime esta línea.", 15),
    CNFHTC__NRO_SUCURSAL    (1116, DNFHTarjetaCredito.class, DataType.Alfa,       25, "Descripción de Texto Fiscal Variable para identificar el número de sucursal. Longitud máxima 25 Caracteres. Si se envía el Caracter DEL (0x7F) ó (127 en decimal) no se imprime esta línea.", 16),
    CNFHTC__OPERADOR        (1117, DNFHTarjetaCredito.class, DataType.Alfa,       30, "Descripción de Texto Fiscal Variable para identificar el número o nombre del operador. Longitud máxima 30 Caracteres. Si se envía el Caracter DEL (0x7F) ó (127 en decimal) no se imprime esta línea.", 17),
    CNFHTC__NRO_FACTURA     (1118, DNFHTarjetaCredito.class, DataType.Alfa,      29, "Descripción de Texto Fiscal Variable para identificar el número de Documento Fiscal al que se hace referencia. Longitud máxima 29 Caracteres. Si se envía el Caracter DEL (0x7F) ó (127 en decimal) no se imprime esta línea.", 18),
    CNFHTC__LINEA_FIRMA      (1119, DNFHTarjetaCredito.class, DataType.Alfa,       1, "Si se envía el Caracter letra ‘P’ (0x50) (80 Decimal) se deja un espacio para que el cliente firme <a>. Si se envía el Caracter DEL (0x7F) ó (127 en decimal) no se imprime esta línea.", 19),
    CNFHTC__LINEA_ACLARA     (1120, DNFHTarjetaCredito.class, DataType.Alfa,       1, "Si se envía el Caracter letra ‘P’ (0x50) (80 Decimal) se deja un espacio para que el cliente ponga un número de teléfono. Si se envía el Caracter DEL (0x7F) ó (127 en decimal) no se imprime esta línea.", 20),
    CNFHTC__LINEA_TELEFONO   (1121, DNFHTarjetaCredito.class, DataType.Alfa,       1, "Si se envía el Caracter letra ‘P’ (0x50) (80 Decimal) se deja un espacio para que el cliente ponga su número de Documento <a>.Si se envía el Caracter DEL (0x7F) ó (127 en decimal) no se imprime", 21),
    SPU__IMPRESORA            (1300, selPrefUsuario.class, DataType.Alfa,       1, "Se debe enviar ‘P’ 0x50 - ASCII (80 Decimal) para indicar que se establecerán las preferencias de la impresora.", 0),
    SPU__TIPO_SETEO            (1301, selPrefUsuario.class, DataType.Alfa,       1, "Posibles valores: ‘D’ 0x44 ASCII (68 Decimal) se envía para indicar que se setearán las preferencias del dispositivo a utilizar para la impresión. ‘P’ 0x50 ASCII (80 Decimal) se envía para indicar que se establecerán las preferencias del papel a utilizar. ‘T’ 0x54 ASCII (84 Decimal) se envía para indicar que se establecerán las preferencias de los comprobantes fiscales.", 1),
    SPU__OPCIONES1            (1302, selPrefUsuario.class, DataType.Alfa,       1, "Si en el Campo 02 se envío ‘D’: ‘S’ 0x53 ASCII (83 Decimal)para indicar que se utilizará como estación seleccionada por el usuario el slip paper (hoja suelta). ‘R’ 0x52 ASCII (82 Decimal)para indicar que se utilizará como estación seleccionada por el usuario la de rollo de papel. Si en el Campo 02 se envío ‘P’: ‘S’ 0x53 ASCII(83 Decimal)indica que se establecerá el tamaño de papel. Si en el Campo 02 se envío ‘T’: ‘P’ 0x50 ASCII (80 Decimal) se envía para indicar que se establecerán las preferencias de imprimir las leyendas “Suma de sus pagos” y “Su Vuelto”. ‘Q’ 0x51 ASCII (81 Decimal) se envía para indicar que se establecerán las preferencias de imprimir Precio por Cantidad en cada ítem facturado.", 2),
    SPU__OPCIONES2            (1303, selPrefUsuario.class, DataType.Alfa,       1, "Si en el Campo 02 se envió ‘D’: ‘O’ 0x4F ASCII (79 Decimal) establece que se imprimirán los Documentos No Fiscales por la estación seleccionada en el Campo 03. Si en el Campo 03 se envió ‘S’: ‘U’ 0x55 ASCII (85 Decimal) establece que se utilizará un tamaño definido por el usuario. Si en el campo 03 se envió ‘P’ o ‘Q’: ‘N’ 0x4E ASCII(78 Decimal) deselecciona la preferencia respectiva. ‘S’ 0x53 ASCII(83 Decimal) selecciona la preferencia respectiva. ", 3),
    SPU__CANT_COLUMNAS      (1304, selPrefUsuario.class, DataType.Integer,     1, "Sólo si en el Campo 04 se envió ‘U’: Establece la cantidad de líneas que mide el papel (slip) a utilizar. Para el EPSON TM-950F se consideran 6 líneas por cada 2,54 cm. (mínimo 10. máximo 999). Para el EPSON TM-U675F este seteo es ignorado y sólo acepta un valor de 2 dígitos, es decir hasta 99). Nota 1: Se debe enviar el largo del papel en líneas. El Impresor Fiscal descuenta las líneas que no pueden ser impresas por las Características de la impresora utilizada. Nota 2: En el caso de las impresoras tipo TM-U950, se descuentan cuatro líneas. Nota 3: Si el usuario ingresa un papel de menor tamaño al enviado en este campo, la impresión del documento puede ser abortada. ", 4),
    SPU__CANT_FILAS            (1305, selPrefUsuario.class, DataType.Integer,    1, "Sólo si en el Campo 04 se envió ‘U’: Establece la cantidad de líneas que mide el papel (slip) a utilizar. Para el EPSON TM-950F se consideran 6 líneas por cada 2,54 cm. mínimo 10. máximo 999). Para el EPSON TM-U675F este seteo es ignorado y sólo acepta un valor de 2 dígitos, es decir hasta 99). Nota 1: Se debe enviar el largo del papel en líneas. El Impresor Fiscal descuenta las líneas que no pueden ser impresas por las Características de la impresora utilizada. Nota 2: En el caso de las impresoras tipo TM-U950, se descuentan cuatro líneas. Nota 3: Si el usuario ingresa un papel de menor tamaño al enviado en este campo, la impresión del documento puede ser abortada. ", 5),
    LPU__IMPRESORA            (1400, leePrefUsuario.class, DataType.Alfa,       1, "Se debe enviar ‘P’ 0x50 - ASCII (80 Decimal) para indicar que se leerán las preferencias de la impresora.", 0),
    LPU__TIPO_SETEO            (1401, leePrefUsuario.class, DataType.Alfa,       1, "Posibles valores: ‘D’ 0x44 ASCII (68 Decimal) para indicar que se leerán las preferencias del dispositivo a utilizar para la impresión. ‘P’ 0x50 ASCII (80 Decimal) para indicar que se leerán las preferencias del papel a utilizar para la impresión. ‘T’ 0x54 ASCII (84 Decimal) para indicar que se leerán las preferencias de los comprobantes fiscales.", 1),
    LPU__OPCIONES1            (1402, leePrefUsuario.class, DataType.Alfa,       1, "Si en el Campo 02 se envió ‘P’: Enviar ‘S’ 0x53 ASCII (83 Decimal) para indicar que se leerán las preferencias del tamaño del papel a utilizar en la impresión. Si en el Campo 02 se envió ‘T’: Enviar ‘P’ 0x50 ASCII (80 Decimal) para indicar que se leerán las preferencias de imprimir las leyendas “Suma de sus pagos” y “Su Vuelto”. Enviar ‘Q’ 0x51 ASCII (81 Decimal) para indicar que se leerán las preferencias de imprimir Precio por Cantidad en cada ítem facturado.", 2),
    PEP__MANEJO_DOCUMENTO  (1500, prepararEstacion.class, DataType.Alfa,       1, "Se debe enviar ‘D’ 0x44 - ASCII (68 Decimal) para indicar que se enviará un comando de manejo de documentos. ", 0),
    PEP__IMPRESION            (1501, prepararEstacion.class, DataType.Alfa,       1, "Se debe enviar ‘P’ 0x50 - ASCII (80 Decimal) para indicar que se enviará un comando para la impresión.", 1),
    PEP__IMPRESION1           (1502, prepararEstacion.class, DataType.Alfa,       1, "Se debe enviar ‘P’ 0x50 - ASCII (80 Decimal) para indicar que deberá preparar para la impresión, la estación seleccionada en el Campo 04.", 2),
    PEP__IMP_DNF_HOJA_SUEL   (1503, prepararEstacion.class, DataType.Alfa,       1, "U’ 0x55 ASCII (85 Decimal) Prepara la estación definida en las preferencias del usuario. Para imprimir Documentos No Fiscales por hoja suelta, se debe utilizar esta opción.", 3),
    PEP__IMP_DNF              (1504, prepararEstacion.class, DataType.Alfa,       1, "‘O’ 0x4F ASCII (79 Decimal) indica que deberá preparar la estación seleccionada se acuerdo al Campo 04 para la impresión de un Documento No Fiscal.", 4),
    AVT__TIPO_AVANCE          (1501, null, DataType.Hexa,                       1, "Comando 0x50 - ASCII '(80 Decimal), Comando 0x51 - ASCII (81 Decimal) ó Comando 0x52 - ASCII (82 Decimal).", 0),
    AVT__CANTIDAD_LINEAS      (1502, avanzaTique.class, DataType.Integer,       2, "Cantidad de lineas", 0),
    AHS__CANTIDAD_LINEAS      (1600, avanzaHojaSuelta.class, DataType.Integer,    2, "Cantidad de lineas", 0),
    EFH__FECHA                 (1700, setFechaHora.class, DataType.Fecha,       6, "Fecha AAMMDD", 0),
    EFH__HORA                  (1701, setFechaHora.class, DataType.Hora,       6, "Hora HHMMSS", 1),
    AFNC__TIPO_DOC_FISCAL     (1800, abrirFNC.class, DataType.Alfa,      1, "Tipo de Documento Fiscal que se va a realizar: <a> (Según modelo) ‘F’= 0x46 Factura Fiscal ‘N’= 0x4E Nota de Crédito Fiscal ‘T’= 0x54 Tique-Factura Fiscal ‘M’= 0x4D Tique-Nota de Crédito Fiscal", 0),
    AFNC__TIPO_SALIDA_IMPRESA (1801, abrirFNC.class, DataType.Alfa,     1,"Tipo de Salida Impresa <a> para Factura Fiscal o Recibo – Factura ‘C’= Formulario Continuo. ‘S’= Hoja Suelta ó Impresora Slip. NOTA: Si el Campo 01 es ‘T’ o ‘M’ este valor es ignorado.", true, 1),
    AFNC__LETRA_DOCUMENTO   (1802, abrirFNC.class, DataType.Alfa,     1, "Letra del Documento Fiscal <a> ‘A’= 0x41 ó ‘B’= 0x42 ó ‘C’= 0x43 ó ‘X’=0x58 Cualquier otra letra origina que se rechace el comando.", true, 2),
    AFNC__CANT_COPIAS         (1803, abrirFNC.class, DataType.Integer,  1, "Cantidad de Copias que se deben IMPRIMIR.<n> Si usa copia con Carbónico, debe enviar ‘1’, pero si desea utilizar solo hojas en original, debe enviar como mínimo ‘2’. Este campo sólo es necesario para Facturas Fiscales en hoja suelta o formulario continuo. IMPORTANTE: En el caso de impresión en rollo (T, TF, TNC) éste valor es ignorado.", true, 3),
    AFNC__TIPO_FORMULARIO    (1804, abrirFNC.class, DataType.Alfa,     1, "Tipo de Formulario que se utiliza para la Factura emitidas en hoja suelta o formulario continuo. <a> Indica si la impresora fiscal debe o no dibujar las líneas de las facturas emitidas en hoja suelta o formulario continuo. Este campo sólo es necesario para Facturas Fiscales. En el caso de Tique-Factura / Tique-Nota de Crédito, este dato es ignorado. Los valores posibles son: ‘F’= 0x46 : Se utiliza formulario pre-impreso con las líneas de la factura dibujadas.‘P’= 0x50: La impresora fiscal debe dibujar las líneas de la factura Impreso por la Impresora. ‘A’= 0x41: Autoimpresor, no imprimir todo el encabezado.", true, 4),
    AFNC__DENSIDAD_LETRA      (1805, abrirFNC.class, DataType.Integer,  2, "Densidad de Impresión de los caracteres que se va a utilizar. Válido para Factura emitida en hoja suelta o formulario continuo. <nn> La Densidad o Resolución de Impresión no puede ser modificada en el transcurso de una Factura en hoja suelta o formulario continuo.Se han redondeado los CPI, por lo tanto para imprimir a 16,7 CPI, se debe enviar el número 17 y no 16,7. Valores aceptados para facturas en hoja suelta o formulario continuo, según modelo: ‘12’ CPI = 0x31 0x32 ‘17’ CPI = 0x31 0x37 En Tique-Factura / Tique-Nota de Crédito este valor es ignorado y se imprime siempre a 16,7 CPI. En caso de enviar un valor no aceptado por el equipo, el mismo es ignorado y no reporta error.", true, 5),
    AFNC__RESP_AFIP_VENDEDOR (1806, abrirFNC.class, DataType.Alfa,     1, "Responsabilidad Frente al IVA del EMISOR en el modo entrenamiento En modo entrenamiento, este campo era obligatorio que tenga una opción válida, en cambio en la nueva línea de impresoras fiscales es obligatorio configurar los datos fiscales para poder emitir documentos fiscales en modo entrenamiento, por lo que se utilizará la categoría del emisor configurada junto con el resto de los datos fiscales. Los valores posibles son: ‘I’= 0x49 IVA RESPONSABLE INSCRIPTO ‘R’= 0x52 IVA RESPONSABLE NO INSCRIPTO ‘N’= 0x4E NO RESPONSABLE ‘E’= 0x45 IVA EXENTO ‘M’= 0x4D RESPONSABLE MONOTRIBUTO ‘T’= 0x54 MONOTRIBUTISTA SOCIAL Cualquier letra distinta de I,R,N,E,M,T será rechazada.", true, 6),
    AFNC__RESP_AFIP_COMPRADOR(1807, abrirFNC.class, DataType.Alfa,    1, "Responsabilidad Frente al IVA del COMPRADOR. Este campo es obligatorio que tenga siempre una opción válida. Los valores posibles son: ‘I’= 0x49 IVA RESPONSABLE INSCRIPTO ‘R’= 0x52 IVA RESPONSABLE NO INSCRIPTO ‘N’= 0x4E NO RESPONSABLE ‘E’= 0x45 IVA EXENTO ‘M’= 0x4D RESPONSABLE MONOTRIBUTO ‘F’= 0x46 CONSUMIDOR FINAL ‘S’= 0x53 SUJETO NO CATEGORIZADO ‘T’= 0x54 MONOTRIBUTISTA SOCIAL ‘C’= 0x43 PEQUEÑO CONTRIBUYENTE EVENTUAL ‘V’= 0x56 PEQUEÑO CONTRIBUYENTE EVENTUAL SOCIAL Cualquier letra distinta de I,R,N,E,M,F,S,T,C,V será rechazada. Si el sujeto es “No Categorizado”, se deberá realizar la percepción que corresponde por RG212. Importante: Si se intenta generar un documento que no es posible por la RG-259, por ejemplo tratar de realizar una venta de responsable Inscripto a responsable Inscripto una factura tipo ‘C’, se informa error en este campo.", 7),
    AFNC__NOMBRE_COMPRAD_L1 (1808, abrirFNC.class, DataType.Alfa,    40, "Nombre Comercial Comprador Primer Línea de Texto Fiscal Variable Cantidad máxima de caracteres según modelo (según densidad de impresión seleccionada y configuración de las zonas de impresión). El máximo cuando se emiten T/TF/TNC es de 40 caracteres impresos. Si por ejemplo se imprime en modo doble ancho, se reducirá la cantidad de caracteres impresos a la mitad.", 8),
    AFNC__NOMBRE_COMPRAD_L2 (1809, abrirFNC.class, DataType.Alfa,    40, "Nombre Comercial Comprador Segunda Línea de Texto FiscalVariable – Cantidad máxima de caracteres según modelo. Ver campo anterior con más detalles.", true, 9),
    AFNC__TIPO_DOC_COMPRADOR(1810, abrirFNC.class, DataType.Alfa,     6, "Tipo de Documento del Comprador. <aaaaaa> Texto ASCII de hasta 6 Caracteres; si dice ‘CUIT’ o ‘CUIL’, se verifica el número enviado. Se debe enviar un CUIT ó CUIL siempre que el comprador no sea un Consumidor Final.", 10),
    AFNC__CUIT_COMPRADOR     (1811, abrirFNC.class, DataType.Integer, 11, "Número de C.U.I.T. o de Documento del Comprador. Nro.: XX-XXXXXXXX-X: <nnnnnnnnnnn> Se aceptan 11 números sin los guiones. Será validado en CUIT ó CUIL (Si se emite un comprobante Tipo A). ", 11),
    AFNC__LEYENDA_BIEN_USO    (1812, abrirFNC.class, DataType.Boolean, 1, "Leyenda OPCIONAL Bien de USO ‘B’= 0x42 -Se imprime la leyenda “VTA. BIENES DE USO”. ‘N’= 0x4E -No se imprime la leyenda anterior. Esta opción sólo era válida si la responsabilidad del emisor era “Responsable Inscripto”, la del comprador era “Responsable No Inscripto” y se emitía un Documento Fiscal tipo Tique-Factura B ó Factura B. NOTA: Dado que por RG1697 / ley 25865 se ha eliminado la categoría “Responsable No Inscripto”, este dato ya no es requerido por lo que será ignorado en caso de ser enviado. ", true, 12),
    AFNC__DOMIC_COMPRADOR_L1(1813, abrirFNC.class, DataType.Alfa,    40, "Domicilio Comprador, Primera Línea de Texto Fiscal Variable Cantidad máxima de caracteres según modelo (según densidad de impresión seleccionada y configuración de las zonas de impresión). El máximo cuando se emiten T/TF/TNC es de 40 caracteres impresos. Si por ejemplo se imprime en modo doble ancho, se reducirá la cantidad de caracteres impresos a la mitad.", 13),
    AFNC__DOMIC_COMPRADOR_L2(1814, abrirFNC.class, DataType.Alfa,    40, "Domicilio Comprador, Segunda Línea de Texto Fiscal Variable Cantidad máxima de caracteres según modelo. Ver campo anterior", true, 14),
    AFNC__DOMIC_COMPRADOR_L3(1815, abrirFNC.class, DataType.Alfa,    40, "Domicilio Comprador, Tercera Línea de Texto Fiscal Variable Cantidad máxima de caracteres según modelo. Ver campo anterior", true, 15),
    AFNC__REMITOS_RELAC_L1    (1816, abrirFNC.class, DataType.Alfa,    40, "Remitos Relacionados, Primer línea - Texto Fiscal Variable con Datos sobre los remitos relacionados con la operación de venta. Cantidad máxima de caracteres según modelo (según densidad de impresión seleccionada y configuración de las zonas de impresión). El máximo cuando se emiten T/TF/TNC es de 40 caracteres impresos. Si por ejemplo se imprime en modo doble ancho, se reducirá la cantidad de caracteres impresos a la mitad. En Nota de Crédito y Tique-Nota de Crédito este campo se usa para indicar Tipo y Número de Comprobante de Venta de Origen. En este caso el máximo cuando se emite TNC es de 21 caracteres impresos.", 16),
    AFNC__REMITOS_RELAC_L2    (1817, abrirFNC.class, DataType.Alfa,    40, "Remitos Relacionados, Segunda Línea de Texto Fiscal Variable con datos sobre los remitos relacionados. Cantidad máxima de caracteres según modelo. Ver campo anterior con más detalles.", true, 17),
    AFNC__FORMATO_ALMAC_DAT (1818, abrirFNC.class, DataType.Alfa,     1, "Formato para almacenar los datos ‘C’= 0x43 -Luego del Tique/Tique-Factura, no se va a realizar un Documento No Fiscal Homologado para Farmacias. IMPORTANTE: Normalmente se debe usar esta opción ‘C’ , excepto cuando se emiten Tiques en FARMACIAS. ‘G’= 0x47 -Se prepara el equipo para la impresión del D.N.F.H. para Farmacias, cuando se termina de emitir el Tique/TF. IMPORTANTE: Usar la opción ‘G’, UNICAMENTE CUANDO ES NECESARIO EMITIR un D.N.F.H. para las Farmacias.", true, 18),
    IIFNC__DESCRIPCION_PROD   (1900, imprItemFNC.class, DataType.Alfa,    18, "Descripción del producto o bien facturado. Línea de Texto Fiscal Variable – Cantidad máxima de caracteres según modelo. El máximo cuando se emite TF/TNC es de 18 caracteres impresos cuando no existen impuestos internos y 11 caracteres en los casos en que existen impuestos internos. En el caso de las facturas en hoja suelta, este valor depende de la resolución a la que se imprime (CPIs) y del tamaño del papel utilizado.", 0),
    IIFNC__CANT_UNIDADES      (1901, imprItemFNC.class, DataType.Num3Dec,     8, "Cantidad de unidades. (nnnnn.nnn) Nota: Manejo de 5 enteros y 3 decimales, se debe enviar sin el punto.", 1),
    IIFNC__PRECIO_UNITARIO     (1902, imprItemFNC.class, DataType.Num4Dec,    12, "Precio Unitario del Item. (nnnnnnn.nnnn) Para FACTURAS A, SE ESPERAN VALORES SIN IVA. Para FACTURAS B, SE ESPERAN VALORES CON IVA. Para FACTURAS C, SE ESPERAN VALORES CON IVA. Nota 1: Manejo de 7 enteros y 4 decimales, se debe enviar con el punto decimal. Nota 2: Compatible con manejo de 2 decimales (nnnnnnn.nn), en este caso se debe enviar 7 enteros y 2 decimales sin el punto decimal.", 2),
    IIFNC__TASA_IVA             (1903, imprItemFNC.class, DataType.Num2Dec,     4, "Tasa Impositiva. Alícuota de IVA (nn.nn) Nota: Se envía sin el punto decimal, por ejemplo 21,00 % de IVA se debe enviar “2100”.", 3),
    IIFNC__CALIF_ITEM           (1904, imprItemFNC.class, DataType.Alfa,      1, "Calificador de Item de Línea: 'M'= 0x4d, Monto agregado de mercadería o ítem de venta, SUMA. 'm'= 0x6d, anula el ítem vendido, RESTA. 'R'= 0x52, Bonificación, RESTA. 'r'= 0x72, anula una Bonificación, SUMA En el caso de Bonificación, se antepone automáticamente la leyenda “BONIF.” a la descripción del artículo.", 4),
    IIFNC__CANTIDAD_BULTOS    (1905, imprItemFNC.class, DataType.Integer,   5, "Cantidad de bultos: (no se usa) (nnnnn.)", 5),
    IIFNC__TASA_AJUSTE_VAR     (1906, imprItemFNC.class, DataType.Num8Dec,     8, "Tasa de Ajuste Variable. (.nnnnnnnn) IMPORTANTE: La tasa de ajuste Variable para Documentos Fiscales tipo ‘A’; se calcula en forma diferente que los Documentos Fiscales tipo ‘B’, ver documentación de modelos anteriores por detalles. Nota: Se debe enviar sólo los decimales, hasta 8 dígitos, sin el punto.", 6),
    IIFNC__DESCRIP_EXTRA_1     (1907, imprItemFNC.class, DataType.Alfa,     26, "Descripción Extra Línea Número 1. Texto Fiscal Variable - Cantidad máxima de caracteres según modelo, 26 caracteres para TF/TNC.", true, 7),
    IIFNC__DESCRIP_EXTRA_2     (1908, imprItemFNC.class, DataType.Alfa,     26, "Descripción Extra Línea Número 2. Texto Fiscal Variable - Cantidad máxima de caracteres según modelo, 26 caracteres para TF/TNC.", true, 8),
    IIFNC__DESCRIP_EXTRA_3     (1909, imprItemFNC.class, DataType.Alfa,     26, "Descripción Extra Línea Número 3. Texto Fiscal Variable - Cantidad máxima de caracteres según modelo, 26 caracteres para TF/TNC.", true, 9),
    IIFNC__TASA_ACRECENTAM    (1910, imprItemFNC.class, DataType.Num2Dec,     4, "Tasa de Acrecentamiento. 10,50 % (cuando un Responsable Inscripto vende a un Responsable NO INSCRIPTO) (nnnn) . NOTA: Dado que por RG1697 / ley 25865 se ha eliminado la categoría “Responsable No Inscripto”, este dato ya no es requerido por lo que será ignorado en caso de ser enviado.", true, 10),
    IIFNC__MONTO_IMP_INT_FIJO  (1911, imprItemFNC.class, DataType.Num8Dec,     15, "Monto Impuestos Internos Fijos. <nnnnnnn.nnnnnnnn> Nota: Manejo de 7 enteros y 8 decimales, se debe enviar sin el punto. Se soporta 8 decimales por compatibilidad con modelos anteriores pero se realiza el cálculo con el valor enviado (unitario) redondeado a 4 decimales y se imprime el resultado con dos decimales. ", true, 11),
    STFNC__IMPRIMIR            (2000, subtotalFNC.class, DataType.Boolean,   1, "Impresión del Subtotal: Si se envía ‘P’ (0x50) se imprimirá el Subtotal. Si se envía ‘N’ (0x4E) no se imprimirá el Subtotal (sólo se retornará la información a la PC en este caso).", 0),
    STFNC__DESCRIPCION        (2001, subtotalFNC.class, DataType.Alfa,       8, "Descripción del Subtotal, Línea de Texto Fiscal Variable - Este campo se mantiene por compatibilidad con modelos anteriores pero el mismo será ignorado, la descripción será siempre “Subtotal”.", true, 1),
    PDRTCFNC__DESCRIPCION    (2100, pagoCancelDescRecaFNC.class, DataType.Alfa,       26, "Descripción: Texto Fiscal Variable. Máximo de 26 caracteres en equipos de Tique-Factura / TNC, variable en equipos de Factura / NC.", 0),
    PDRTCFNC__MONTO          (2101, pagoCancelDescRecaFNC.class, DataType.Num2Dec,      11, "Monto del Pago / Descuento / Recargo (nnnnnnnnn.nn) Nota: Manejo de 9 enteros y 2 decimales, se debe enviar sin el punto.", 1),
    PDRTCFNC__CALIFICADOR    (2102, pagoCancelDescRecaFNC.class, DataType.Alfa,        1,"Calificador de Pago / Descuento / Recargo / Cancelar:'C'= 0x43, Cancelar Comprobante. 'T'= 0x54, Suma al Importe Pagado. 't'= 0x74, Anula un Pago hecho con ‘T’. 'D'= 0x44, Realiza un Descuento Global de monto fijo. 'R'= 0x44, Realiza un Recargo Global de monto fijo.", 2),
    PFNC__DESCRIPCION        (2200, percepcionFNC.class, DataType.Alfa,       25, "Descripción: Texto fiscal con descripción del motivo de la percepción. Máximo de 25 Caracteres <aaa25aaa> completados con espacio a la derecha en impresión de Tique-Factura. Longitud variable en impresoras de Factura con hoja suelta o formulario continuo. ", 0),
    PFNC__TIPO_PERCEPCION    (2201, percepcionFNC.class, DataType.Alfa,       1, "Tipo de Percepción: Indica si es una Percepción sobre IVA o es otro tipo de Percepción <a>. ‘O’= 0x4F (Decimal 79) Otro tipo de Percepción (Perc. II.BB.). ‘I’ = 0x49 (Decimal 73) Percepción Global de IVA. ‘T’= 0x54 (Decimal 84) Percepción de IVA a una tasa determinada. Cualquier otra letra origina que se rechace el comando. Nota: En Comprobantes tipo ‘A’ se aceptan percepciones tipo ‘O’, ‘I’ y ‘T’ En Comprobantes tipo ‘B’ se aceptan percepciones tipo ‘O’ e ‘I’. En Comprobantes tipo ‘C’ se aceptan percepciones tipo ‘O’.", 1),
    PFNC__MONTO_PERCEPCION (2202, percepcionFNC.class, DataType.Num2Dec,      10, "Monto de la Percepción <nnnnnnnn.nn>. (Manejo de 8 enteros y 2 decimales, se debe enviar sin el punto). Sólo en el caso de que en el Campo 02 se coloque la opción “T” aquí se debe enviar la Tasa de IVA a la que se aplica la Percepción (nn.nn) ", 2),
    PFNC__TASA_IVA            (2203, percepcionFNC.class, DataType.Num2Dec,       4, "Tasa de IVA a la que se aplica la Percepción. (nn.nn). (Manejo de 2 enteros y 2 decimales, se debe enviar sin el punto). S ólo en el caso de que en el Campo 02 se coloque la opción “T” aquí se debe enviar el Monto de la Percepción <nnnnnnnn.nn>.", 3),
    CFNC__TIPO_DOCUMENTO   (2300, cerrarFNC.class, DataType.Alfa,       1, "Tipo de Documento Fiscal que se va a cerrar <a> que debe coincidir con el utilizado al abrir la Factura, Tique-Factura, NC ó Tique-NC. ‘F’= 0x46 Factura Fiscal ‘N’= 0x4E Nota de Crédito Fiscal ‘T’= 0x54 Tique-Factura Fiscal ‘M’= 0x4D Tique-Nota de Crédito Fiscal Cualquier letra no aceptada por el modelo de controlador fiscal, origina que se rechace el comando.", 0),
    CFNC__LETRA_DOCUMENTO  (2301, cerrarFNC.class, DataType.Alfa,       1, "Letra del Documento Fiscal <a> que debe coincidir con el utilizado al A abrir una Factura ó Tique-Factura. ‘A’= 0x41 ó ‘B’= 0x42 ó ‘C’= 0x43 Cualquier otra letra origina que se rechace el comando.", 1),
    CFNC__DESCRIP_EN_TOTAL  (2302, cerrarFNC.class, DataType.Alfa,       10, "Descripción que se imprime en la línea de TOTAL. Se debe considerar que los últimos 10 caracteres de la factura en hoja suelta o formulario continuo, están reservados para la impresión de la palabra TOTAL. Esta descripción no es utilizada en Tique-Factura, sólo se utiliza para impresión de facturas en hoja suelta o formulario continuo. Para asegurar la compatibilidad se debe enviar en este campo el caracter ASCII DEL (0x7F o Dec 127) ", 2),
    EDF__NRO_LINEA            (2400, setDatoFijo.class, DataType.Integer, 5, "Número de Línea de Datos Fijos de encabezado o cola", 0),
    EDF__DATOS_FIJOS         (2401, setDatoFijo.class, DataType.Alfa, 40, "Datos Fijos: Hasta 40 bytes de Texto Fiscal", 1);


    Parametro(int codigo, Class comando, DataType type, int largo, String descripcion, Integer indice) {
        this.codigo = codigo;
        this.type = type;
        this.largo = largo;
        this.descripcion = descripcion;
        this.indice = indice;
        this.comando = comando;
    }

    Parametro(int codigo, Class comando, DataType type, int largo, String descripcion, boolean opcional, Integer indice) {
        this.codigo = codigo;
        this.type = type;
        this.largo = largo;
        this.descripcion = descripcion;
        this.opcional = opcional;
        this.indice = indice;
        this.comando = comando;
    }

    Class comando = null;
    Integer codigo = -1;
    String descripcion = "";
    DataType type = null;
    Integer largo = -1;
    boolean opcional = false;
    Integer indice = -1;

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public DataType getType() {
        return type;
    }

    public Integer getLargo() {
        return largo;
    }

    public Integer getIndice() {
        return indice;
    }

    public boolean isOpcional() {
        return opcional;
    }

    public Class getComando() {
        return comando;
    }

    public Parametro solveParam(Class comando, int index) {
        for(Parametro p : Parametro.values()) {
            if(p.getComando() == comando) {
                return p;
            }
        }

        return null;
    }

    public void setLargo(Integer largo) {
        this.largo = largo;
    }
    
}
