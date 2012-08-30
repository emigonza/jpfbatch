/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjob.func.general;

import java.awt.Dimension;
import java.awt.Point;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

/**
 *
 * @author Guillermo
 */
public class GeneralFunc {
    /// <summary>
    /// Devuelve true si par_Number es un numero
    /// </summary>
    /// <param name="par_Numer">valor que se quiere saber si es un numero o no</param>
    /// <returns>verdadero en caso de que par_Number sea un numero, falso en otro caso</returns>

    public static boolean IsNumber(int par_Numer) {
        return true;
    }

    /// <summary>
    /// Devuelve true si par_Number es un numero
    /// </summary>
    /// <param name="par_Numer">valor que se quiere saber si es un numero o no</param>
    /// <returns>verdadero en caso de que par_Number sea un numero, falso en otro caso</returns>
    public static boolean IsNumber(double par_Number) {
        return true;
    }

    /// <summary>
    /// Devuelve true si par_Number es un numero
    /// </summary>
    /// <param name="par_Numer">valor que se quiere saber si es un numero o no</param>
    /// <returns>verdadero en caso de que par_Number sea un numero, falso en otro caso</returns>
    public static boolean IsNumber(long par_Number) {
        return true;
    }

    /// <summary>
    /// Devuelve true si par_Number es un numero
    /// </summary>
    /// <param name="par_Numer">valor que se quiere saber si es un numero o no</param>
    /// <returns>verdadero en caso de que par_Number sea un numero, falso en otro caso</returns>
    public static boolean IsNumber(String par_Number) {

        java.util.regex.Pattern p = java.util.regex.Pattern.compile("^\\-?\\d*.?\\d+$");
        java.util.regex.Matcher m = p.matcher(par_Number);

        return m.matches() || IsInteger(par_Number);
    }

    /// <summary>
    /// Devuelve true si par_Number es un numero entero
    /// </summary>
    /// <param name="par_Numer">valor que se quiere saber si es un numero entero o no</param>
    /// <returns>verdadero en caso de que par_Number sea un numero entero, falso en otro caso</returns>
    public static boolean IsInteger(String par_Number) {

        java.util.regex.Pattern p = java.util.regex.Pattern.compile("^[\\d|\\-]\\d*$");
        java.util.regex.Matcher m = p.matcher(par_Number);

        return m.matches();

    }

    public static <T extends Object> boolean ContainKey(Enumeration<T> list, T key) {
        return Collections.list(list).contains(key);
        /*
        for (java.util.Enumeration<T> loc_s = list; list.hasMoreElements();) {
        //props.addKeyword(solverNames.nextElement());
        if (loc_s.nextElement().equals(key)) {
        return true;
        }
        }
        return false;
         */
    }

    public static boolean typeIsInteger(Class type) {
        if (type == Integer.class
                || type == int.class
                || type == long.class
                || type == Long.class
                || type == java.math.BigInteger.class
                || type == Byte.class
                || type == Short.class) {
            return true;
        }


        return false;
    }

    public static boolean typeIsNumber(Class type) {
        if (typeIsInteger(type)
                || type == Double.class
                || type == double.class
                || type == Float.class
                || type == float.class
                || type == java.math.BigDecimal.class
                || type == Number.class) {
            return true;
        }


        return false;
    }

    public static String getExecutionPath() {
        return System.getProperty("user.dir");
    }

    public static Object getDefaultValue(Class type) {
        if (type.equals(Integer.class)) {
            return 0;
        }

        if (type.equals(Double.class)) {
            return 0d;
        }

        if (type.equals(Float.class)) {
            return 0f;
        }

        if (type.equals(String.class)) {
            return "";
        }

        if (type.equals(Date.class)) {
            return new Date();
        }

        if (type.equals(Calendar.class)) {
            return Calendar.getInstance();
        }

        if (type.equals(Boolean.class)) {
            return false;
        }


        return null;
    }

    public static String dateFormat(Calendar date) {
        return dateFormat(date.getTime(), "dd/MM/yyyy");
    }

    public static String dateFormat(Calendar date, String format) {
        return dateFormat(date.getTime(), format);
    }

    public static String dateFormat(Date date, String format) {
        DateFormat Formatter = new SimpleDateFormat(format);

        return Formatter.format(date);
    }

    public static String dateFormat(Date date) {
        return dateFormat(date, "dd/MM/yyyy");
    }

    public static double parseDouble(String s, int decimals) {
        s = s.substring(0, s.length() - decimals) + "." + s.substring(s.length() - decimals);

        return Double.parseDouble(s);
    }

    public static String toDoubleString(double number, int intLength, int decLength) {

        int conv = 10 * decLength;

        return GeneralFunc.stringFormat("{0:" + myjob.func.text.TextFunc.repeat(intLength + decLength, '0') + "}", Math.ceil(number * conv));
    }

    public static Date parseDate(String date) throws ParseException {
        return parseDate(date, "dd/MM/yyyy");
    }

    public static Date parseDate(String date, String inFormat) throws ParseException {
        DateFormat loc_df = new SimpleDateFormat(inFormat);

        if (date.trim().equals("00/00/0000")
                || date.trim().equals("00/00/00")
                || date.trim().length() == 0) {
            return loc_df.parse("01/01/1900");
        }

        if (inFormat.contains("/")) {
            date = date.replace("-", "/");
        }

        Date retVal;
        try {
            retVal = loc_df.parse(date.trim());
        } catch (ParseException ex) {
            System.out.println("imposible convertir " + date + " con mascara " + inFormat);
            throw ex;
        }

        return retVal;
    }

    public static String formatObject(Object o) {
        return formatObject(o, "", '.');
    }

    public static String formatObject(Object o, String format) {
        return formatObject(o, format, '.');
    }

    public static String formatObject(Object o, String format, char decimalPoint) {

        if (typeIsInteger(o.getClass())) {
            Double d = Double.parseDouble(o.toString());

            DecimalFormat df = null;

            if (format.trim().length() == 0) {
                //df = (DecimalFormat) DecimalFormat.getInstance();
                df = new DecimalFormat("######0");
            } else {
                df = new DecimalFormat(format);
            }

            java.text.DecimalFormatSymbols ds = java.text.DecimalFormatSymbols.getInstance();
            ds.setDecimalSeparator(decimalPoint);
            df.setDecimalFormatSymbols(ds);

            return df.format(d);
        } else if (typeIsNumber(o.getClass())) {
            Double d = Double.parseDouble(o.toString());

            DecimalFormat df = null;

            if (format.trim().length() == 0) {
                //df = (DecimalFormat) DecimalFormat.getInstance();
                df = new DecimalFormat("######0.00");
            } else {
                df = new DecimalFormat(format);
            }

            java.text.DecimalFormatSymbols ds = java.text.DecimalFormatSymbols.getInstance();
            ds.setDecimalSeparator(decimalPoint);
            df.setDecimalFormatSymbols(ds);

            return df.format(d);

        } else if (o.getClass() == Date.class || o.getClass() == java.sql.Date.class || o.getClass() == java.sql.Timestamp.class) {
            DateFormat df = null;
            if (format.trim().length() == 0) {
                df = new SimpleDateFormat("dd/MM/yyyy");
            } else {
                df = new SimpleDateFormat(format);
            }
            return df.format((Date) o);
        } else if (o.getClass() == Calendar.class || o.getClass() == GregorianCalendar.class) {
            DateFormat df = null;
            if (format.trim().length() == 0) {
                df = new SimpleDateFormat("dd/MM/yyyy");
            } else {
                df = new SimpleDateFormat(format);
            }
            return df.format(((Calendar) o).getTime());
        } else if (o.getClass() == boolean.class || o.getClass() == Boolean.class) {

            if (format.trim().length() == 0) {
                format = "01";
            }

            if (format.equals("yesno")) {
                if ((Boolean) o) {
                    return "yes";
                } else {
                    return "no";
                }
            }

            if (format.equals("YesNo")) {
                if ((Boolean) o) {
                    return "Yes";
                } else {
                    return "No";
                }
            }

            if (format.equals("YESNO")) {
                if ((Boolean) o) {
                    return "YES";
                } else {
                    return "NO";
                }
            }

            if (format.equals("sino")) {
                if ((Boolean) o) {
                    return "si";
                } else {
                    return "no";
                }
            }

            if (format.equals("SiNo")) {
                if ((Boolean) o) {
                    return "Si";
                } else {
                    return "No";
                }
            }

            if (format.equals("vf") || format.equals("fv")) {
                if ((Boolean) o) {
                    return "v";
                } else {
                    return "f";
                }
            }

            if (format.equals("VF") || format.equals("FV")) {
                if ((Boolean) o) {
                    return "V";
                } else {
                    return "F";
                }
            }

            if (format.equals("tf") || format.equals("ft")) {
                if ((Boolean) o) {
                    return "t";
                } else {
                    return "f";
                }
            }

            if (format.equals("TF") || format.equals("FT")) {
                if ((Boolean) o) {
                    return "T";
                } else {
                    return "F";
                }
            }

            if (format.equals("01") || format.equals("10")) {
                if ((Boolean) o) {
                    return "1";
                } else {
                    return "0";
                }
            }

            if (format.equals("verdaderofalso") || format.equals("falsoverdadero")) {
                if ((Boolean) o) {
                    return "verdadero";
                } else {
                    return "falso";
                }
            }

            if (format.equals("VerdaderoFalso") || format.equals("FalsoVerdadero")) {
                if ((Boolean) o) {
                    return "Verdadero";
                } else {
                    return "Falso";
                }
            }

            if (format.equals("VERDADEROFALSO") || format.equals("FALSOVERDADERO")) {
                if ((Boolean) o) {
                    return "VERDADERO";
                } else {
                    return "FALSO";
                }
            }

            if (format.equals("truefalse") || format.equals("falsetrue")) {
                if ((Boolean) o) {
                    return "true";
                } else {
                    return "false";
                }
            }

            if (format.equals("TrueFalse") || format.equals("FalseTrue")) {
                if ((Boolean) o) {
                    return "True";
                } else {
                    return "False";
                }
            }

            if (format.equals("TRUEFALSE") || format.equals("FALSETRUE")) {
                if ((Boolean) o) {
                    return "TRUE";
                } else {
                    return "FALSE";
                }
            }
        } else if (o.getClass() == String.class) {
            return (String) o;
        }

        System.out.println("Conversion de tipos desconocido en GeneralFunc.formatObject " + o.getClass().toString() + " " + o.toString());

        return "desconocido " + o.toString() + " " + o.getClass().toString();
    }

    public static String stringFormat(String format, Object... objs) {

        //Logger.getLogger(GeneralFunc.class).log(Level.DEBUG, "StringFormat con formato " + format);

        String retVal = "";

        Pattern p = Pattern.compile("([{][^}]*[}])");

        Matcher m = p.matcher(format);

        String individualFormat;
        int order = -1;
        int pos = -1;

        //loc_re.match(format);

        retVal = format;

        int loc_Conta = 0;

        while (m.find()) {

            if (m.group().length() == 0) {
                continue;
            }

            pos = m.group().indexOf(":");

            if (pos > 0) {
                individualFormat = m.group().substring(pos + 1, m.group().length() - pos + 1);

                order = Integer.parseInt(m.group().substring(1, pos));

                //Logger.getLogger(GeneralFunc.class).log(Level.DEBUG, "individualFormat " + individualFormat + "orden: " + order);

                retVal = retVal.replace(m.group(), formatObject(objs[order], individualFormat, '.'));
            } else {
                retVal = retVal.replace(m.group(), formatObject(objs[Integer.parseInt(m.group().substring(1, m.group().length() - 1))], "", '.'));
            }

            loc_Conta++;
        }


        return retVal;
    }

    public static String padObject(Object o, int n, char padChar) {
        if (typeIsInteger(o.getClass())) {
            return padLeft(o.toString(), n, padChar);
        } else if (typeIsNumber(o.getClass())) {
            Double d = Double.parseDouble(o.toString());

        } else if (o.getClass() == Date.class) {
            if (n == 8) {
                return GeneralFunc.dateFormat((Date) o, "dd/MM/yy");
            } else {
                return GeneralFunc.dateFormat((Date) o);
            }
        } else if (o.getClass() == Calendar.class) {
            if (n == 8) {
                return GeneralFunc.dateFormat((Calendar) o, "dd/MM/yy");
            } else {
                return GeneralFunc.dateFormat((Calendar) o);
            }
        } else if (o.getClass() == boolean.class || o.getClass() == Boolean.class) {
            if (n == 1) {
                if ((Boolean) o) {
                    return "1";
                } else {
                    return "0";
                }
            } else if (n == 2) {
                if ((Boolean) o) {
                    return "si";
                } else {
                    return "no";
                }
            } else if (n == 3) {
                if ((Boolean) o) {
                    return "yes";
                } else {
                    return "no";
                }
            } else if (n == 5) {
                if ((Boolean) o) {
                    return "true";
                } else {
                    return "false";
                }
            }
            if ((Boolean) o) {
                return "verdadero";
            } else {
                return "falso";
            }
        }

        return padRight(o.toString(), n, padChar);
    }

    public static String padRight(String s, int n, char padChar) {

        if (n == 0) {
            return "";
        }

        if (s.length() > n) {
            return s.substring(0, n - 1);
        }

        if (s.length() == n) {
            return s;
        }

        StringBuilder padded = new StringBuilder(s);

        while (padded.length() < n) {
            padded.append(padChar);
        }

        return padded.toString();
    }

    public static String padLeft(Object string, int n, char padChar) {

        String s = string.toString();

        if (n == 0) {
            return "";
        }

        if (n < 0) {
            return s;
        }

        if (s.length() > n) {
            return s.substring(0, n - 1);
        }

        if (s.length() == n) {
            return s;
        }

        while (s.length() < n) {
            s = ((Character) padChar).toString() + s;
        }

        return s;
    }

    /**
     * Devuelve un array de String tomando como fin el ultimo char de separators
     * @param array
     * @param start
     * @param separador
     * @param end
     * @return 
     */
    public static String[] bytesToStringArray(byte[] array, char start, char... separators) {
        List<String> retVal = new ArrayList<String>();

        StringBuilder token = new StringBuilder();

        boolean isStarted = false;

        Character end = separators[separators.length - 1];

        List<Character> separator = new ArrayList<Character>();
        
        for(int i = 0; i < (separators.length - 1); i++) {
            separator.add(separators[i]);
        }
        
        for (byte b : array) {
            if (b == end) {
                if (token.length() > 0) {
                    retVal.add(token.toString());
                }
                break;
            }

            if (isStarted) {

                if (separator.contains((Character) (char) b)) {
                    retVal.add(token.toString());
                    token = new StringBuilder();
                    continue;
                }

                token.append((char) b);
            }

            if (b == start) {
                isStarted = true;
            }
        }


        return retVal.toArray(new String[]{});
    }

    public static <T> boolean isIn(T[] array, T o) {
        for (T objInArray : array) {
            if (objInArray.equals(o)) {
                return true;
            }
        }

        return false;
    }

    public static String bytesToString(byte[] array) {

        if (array == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        for (byte b : array) {
            sb.append((char) b);
        }


        return sb.toString();
    }

    public static String bytesToStringDebug(byte[] array) {

        StringBuilder sb = new StringBuilder();

        sb.append("\n    ");

        for (byte b : array) {
            if (b > 31 && b < 127) {
                sb.append((char) b);
            } else {
                sb.append(".");
            }
        }

        sb.append("\n    ");

        Integer conta = 0;

        for (byte b : array) {
            sb.append("[").append(GeneralFunc.padLeft(conta, 3, ' ')).append("]");
            conta++;
        }

        sb.append("\n    ");

        char sale;

        for (byte b : array) {

            if (b > 31 && b < 127) {
                sale = (char) b;
            } else {
                sale = '.';
            }

            sb.append(GeneralFunc.padLeft(Character.toString(sale), 4, ' ')).append(" ");

        }

        sb.append("\ndec ");

        for (byte b : array) {
            sb.append("[").append(GeneralFunc.padLeft(((Byte) b).toString(), 3, ' ')).append("]");
        }

        sb.append("\nhex ");

        for (byte b : array) {
            sb.append("[").append(GeneralFunc.padLeft(Integer.toHexString((int) b), 3, ' ')).append("]");
        }

        return sb.toString();
    }

    public static byte[] writeInArray(byte[] array, String value, int pos) {

        //System.out.println("Escribiendo " + value + " en " + pos);

        byte[] strVal = value.getBytes();
        System.arraycopy(strVal, 0, array, pos, value.length());

        return array;
    }

    public static String extractConstStrFromArray(byte[] array, int from, int length) {

        StringBuilder sb = new StringBuilder();

        for (int loc_Conta = 0; loc_Conta < length; loc_Conta++) {
            sb.append((char) array[from + loc_Conta]);
        }

        return sb.toString();
    }

    public static String extractVarStrFromArray(byte[] array, int from, char... ends) {

        StringBuilder sb = new StringBuilder();

        boolean sale = false;

        for (int loc_Conta = 0; !sale; loc_Conta++) {
            for (int posend = 0; posend < ends.length; posend++) {
                if (ends[posend] == array[from + loc_Conta]) {
                    sale = true;
                    break;
                }
            }

            if (sale) {
                break;
            }

            sb.append((char) array[from + loc_Conta]);
        }

        return sb.toString();
    }

    public static <E> void addRange(List<E> lista, E[] valores) {
        lista.addAll(Arrays.asList(valores));
    }

    public static <E, F> void addRange(Dictionary<E, F> destino, Dictionary<E, F> valores) {
        List<E> keys = Collections.list(valores.keys());

        for (E key : keys) {
            destino.put(key, valores.get(key));
        }

    }

    public static <E, F> List<E> getDictKeys(Dictionary<E, F> dict) {
        return Collections.list(dict.keys());
    }

    public static <E, F> List<F> getDictValues(Dictionary<E, F> dict) {
        return Collections.list(dict.elements());
    }

    public static int DivRem(Integer a, Integer b) {
        Double d = new Double(a) / new Double(b);

        return a - (int) (Math.ceil(d) * b);
    }

    public static <E, T> Dictionary<E, T> FillDict(Dictionary<E, T> dict, Object... params) throws Exception {
        if (GeneralFunc.DivRem(params.length, 2) > 0) {
            throw new Exception("El numero de argunentos debe ser par");
        }

        for (Integer loc_Pos = 0; loc_Pos < params.length; loc_Pos += 2) {
            dict.put((E) params[loc_Pos], (T) params[loc_Pos + 1]);
        }

        return dict;
    }

    public static boolean Contain(Object[] objs, Object o) {
        for (Object ob : objs) {
            if (ob.equals(o)) {
                return true;
            }
        }

        return false;
    }

    public static Point CenterPoint(Dimension size) {
        Point loc_p = new Point();

        loc_p.setLocation(size.getWidth() / 2, size.getHeight() / 2);

        return loc_p;
    }

    public static Point CenterPoint(Dimension parentSize, Dimension windowSize) {
        Point loc_p = new Point();

        loc_p.setLocation((parentSize.getWidth() / 2) - (windowSize.getWidth() / 2), (parentSize.getHeight() / 2) - (windowSize.getHeight() / 2));

        return loc_p;
    }

    public static void EjecutarNativo(String cmd) {
        Runtime aplicacion = Runtime.getRuntime();
        try {
            aplicacion.exec(cmd);
        } catch (Exception e) {
        }

    }

    public static String getProps() {
        String retVal = "";

        for (Object prop : myjob.func.general.GeneralFunc.asList(System.getProperties().keys())) {
            retVal += ((String) prop) + " = " + System.getProperty((String) prop) + "\n";
        }

        return retVal;
    }

    public static String getOS() {
        return System.getProperty("os.name");
    }

    public static boolean is32OS() {
        return !is64OS();
    }

    public static boolean is64OS() {
        if (((String) System.getProperty("sun.arch.data.model")).equals("64")) {
            return true;
        }

        return false;
    }

    public static <E> List<E> asList(Enumeration<E> enu) {
        return Collections.list(enu);
    }

    public static <E> List<E> asList(E... objs) {
        List<E> retVal = new ArrayList<E>();
        retVal.addAll(Arrays.asList(objs));

        return retVal;
    }

    public static String getStackTrace(Exception ex) {
        String retVal = "Error: " + ex.getMessage() + "\n";

        if (ex != null) {
            for (StackTraceElement st : ex.getStackTrace()) {
                if (!st.isNativeMethod()) {
                    retVal += "File: " + st.getFileName() + " Class: " + st.getClassName() + " Method " + st.getMethodName() + " LineNo " + ((Integer) st.getLineNumber()).toString() + " \n";
                }
            }

        }

        return retVal;
    }

    public static boolean limit() {

        Calendar endTime1 = Calendar.getInstance();

        int year = 2013;
        int month = 11 - 1;
        int day = 25;

        if (endTime1.get(Calendar.YEAR) > year) {
            return true;
        } else if (endTime1.get(Calendar.YEAR) == year && endTime1.get(Calendar.MONTH) > month) {
            return true;
        } else if (endTime1.get(Calendar.YEAR) == year && endTime1.get(Calendar.MONTH) == month && endTime1.get(Calendar.DAY_OF_MONTH) > day) {
            return true;
        }

        return false;
    }

    /**
     * Transforma una letra en una columna, se usa para transformar direcciones de excel del tipo row, col en nro y letra
     * @param letter
     * @return
     */
    public static Integer SheetLetterToCol(String letter) {
        int loc_RetVal = 0;
        int loc_Val = 0;

        letter = letter.toUpperCase().trim();

        loc_RetVal = letter.charAt(letter.length() - 1) - 65;

        if (letter.length() > 1) {

            //loc_RetVal++;

            //A
            //AA
            //AB

            for (int loc_Conta = 1; loc_Conta < letter.length(); loc_Conta++) {

                loc_Val = letter.charAt((letter.length() - 1) - loc_Conta) - 64;

                loc_RetVal += loc_Val * (26 * (letter.length() - loc_Conta));

            }

        }

        return loc_RetVal;
    }

    /**
     * Transforma una columna en una letra, traduce una dirección de excel
     * @param col
     * @return
     */
    public static String SheetColToLetter(int col) {

        String loc_RetVal = "";

        while (col > 25) {

            int loc_Col = col / 26;
            loc_RetVal += ((Character) ((char) (loc_Col + 64))).toString();
            col = col - (loc_Col * 26);
        }

        loc_RetVal += ((Character) ((char) (col + 65))).toString();

        return loc_RetVal;
    }

    public static String SheetColRowToAddress(int col, int row) {
        return SheetColToLetter(col) + ((Integer) (row + 1)).toString();
    }
}
