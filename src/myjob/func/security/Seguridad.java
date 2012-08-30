package myjob.func.security;

import myjob.func.text.TextFunc;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

public class Seguridad {

    /**
     * Revisa que alguna de las llaves abra la cerradura
     * @param cerraduras
     * @param llave
     * @return
     * @throws Exception
     */
    public static boolean HavePermiso(String cerradura, String[] llaves) throws Exception {
        
        
        for (String llave : llaves) {
            if (llave.length() > 0) {
                if (Seguridad.HavePermiso(cerradura, llave)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Controla si la llave habre la cerradura o no
     * también tiene la posibilidad de controlar grupos
     * de manera [grupo]cerradura == [grupo]llave
     * @param cerradura
     * @param llave
     * @return
     * @throws Exception
     */
    public static boolean HavePermiso(String cerradura, String llave) throws Exception {
        
        
        if (cerradura.length() == 0) {
            return true;
        }

        if (!ControlGrupo(llave, cerradura)) {
            return false;
        }

        return ControlSimple(llave, cerradura);

    }

    /**
     * conrola si coinciden los grupos entre la cerradura y la llave
     * @param llave
     * @param cerradura
     * @return
     * @throws Exception
     */
    public static boolean ControlGrupo(String llave, String cerradura) throws Exception {

        llave.replace("[]", "[*]");
        cerradura.replace("[]", "[*]");

        if (TextFunc.countStr(llave, "[") != TextFunc.countStr(llave, "]")) {
            throw new Exception("Llave con grupos desbalanceados (no hay la misma cantidad de [ que de ]");
        }

        if (TextFunc.countStr(cerradura, "[") != TextFunc.countStr(cerradura, "]")) {
            throw new Exception("Llave con grupos desbalanceados (no hay la misma cantidad de [ que de ]");
        }

        while (llave.startsWith("[") &&
                cerradura.startsWith("[")) {

            if (!ControlSimple(GetGrupo(llave), GetGrupo(cerradura))) {
                return false;
            }
        }

        // supongo que si la llave no tiene control de grupos es universal
        // puede pasar con
        // llave     = [SiMP]opt_algo
        // cerradura = [SiMP][Pesadas]opt_algo

        while (cerradura.startsWith("[")) {
            GetGrupo(cerradura);
        }

        return true;
    }

    /**
     * devuelve el grupo de cadena y a cadena le quita el grupo
     * @param cadena
     * @return
     */
    public static String GetGrupo(String cadena) {
        String loc_RetVal = "";

        if (cadena.startsWith("[")) {
            loc_RetVal = cadena.substring(1, cadena.indexOf("]") - 1);
            cadena = cadena.substring(cadena.indexOf("]") + 1);
        }

        return loc_RetVal;
    }

    /**
     * controla transformando en expresiones regulares
     * de manera simple una expresion
     * @param llave
     * @param cerradura
     * @return
     */
    public static boolean ControlSimple(String llave, String cerradura) {
        
        boolean retVal = false;
        
        if (llave.contains("*")) {
            //org.apache.regexp.RE loc_re = new org.apache.regexp.RE("^" + llave.replace("*", ".*"));

            java.util.regex.Pattern p = java.util.regex.Pattern.compile("^" + llave.replace("*", ".*"));

            java.util.regex.Matcher m = p.matcher(cerradura);

            if (m.find()) {
                retVal = true;
            }

        } else if (llave.equals(cerradura)) {
            retVal = true;
        }
        
        Logger.getLogger(Seguridad.class).log(Level.DEBUG, "la llave " + llave + " " + (retVal ? "" : "no ") + "abre la cerradura " + cerradura);

        return retVal;
    }
}
