/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jpfbatch.tests;

import ifepson.commands.abrirTique;
import ifepson.commands.cerrarTique;
import ifepson.commands.imprItemTique;
import ifepson.commands.imprTxtFiscTique;
import ifepson.commands.pagoCancelDescRecaTique;
import ifepson.commands.subtotalTique;
import ifepson.doc.IndexedOut;
import ifepson.ifCommand;
import java.io.IOException;
import java.util.Map;
import jpfbatch.IFBatch;
import myjob.func.io.PortConfig;

/**
 *
 * @author guillermot
 */
public class IFTest {
    public static void doTiquetTest() throws IOException {
        
        IFBatch.InitRxTx("rxtx/linux_x86_64/");
        
        IFBatch ifb = new IFBatch();
        
        PortConfig pf = new PortConfig();
        
        pf.setPortName("/dev/ttyS0");
        pf.setStopBits(1);
        pf.setParity(0);
        pf.setBaudRate(9600);
        pf.setDataBits(8);
        
        ifb.setPortConfig(pf);
        
        ifCommand c = null;

        c = new abrirTique();

        ifb.addCommand(c);

        c = new imprTxtFiscTique();

        ((imprTxtFiscTique) c).setLineaExtra("linea extra");

        ifb.addCommand(c);

        c = new imprItemTique();

        ((imprItemTique) c).setBultos(1);
        ((imprItemTique) c).setCalificador(imprItemTique.CalificadorItem.MONTO_AGREGADO_O_VENTA_SUMA);
        ((imprItemTique) c).setCantidad(0.01d);
        ((imprItemTique) c).setDescripcionProducto("item de linea");
        ((imprItemTique) c).setPrecioUnitario(10);
        ((imprItemTique) c).setIva(21d);

        ifb.addCommand(c);

        c = new subtotalTique();

        ifb.addCommand(c);

        c = new pagoCancelDescRecaTique();

        ((pagoCancelDescRecaTique) c).setCalificador(pagoCancelDescRecaTique.Calificador.DESCUENTO);

        ((pagoCancelDescRecaTique) c).setDescripcionEnTique("POR SER MUY FEO");

        ((pagoCancelDescRecaTique) c).setMonto(0.05d);

        ifb.addCommand(c);

        c = new pagoCancelDescRecaTique();

        ((pagoCancelDescRecaTique) c).setCalificador(pagoCancelDescRecaTique.Calificador.SUMA_IMPORTE_PAGADO);

        ((pagoCancelDescRecaTique) c).setDescripcionEnTique("EFECTIVO");

        ((pagoCancelDescRecaTique) c).setMonto(0.05d);

        ifb.addCommand(c);

        c = new cerrarTique();

        ifb.addCommand(c);
        
        ifb.run();
        
        
        // acá vienen todas las respuestas
        // que pueden ser cualquiera de ifepson.doc.IndexedOut
        // hay que analizarlas para ver si viene con algun error
        
        Map<IndexedOut, String> respuesta = ifb.getRespuesta();
    }
}
