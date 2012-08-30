/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjob.func.text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import myjob.func.general.GeneralFunc;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

/**
 *
 * @author guillermot
 */
public class TextFunc {

    public static String repeat(int cant, char c) {
        char[] arr = new char[cant];
        Arrays.fill(arr, c);
        return new String(arr);

    }

    /// <summary>
    /// Separa una cadena en sus respectivos tokens
    /// </summary>
    /// <param name="par_Tokens">cadena de caracteres a separar</param>
    /// <param name="par_Separador">cadena que indica separacion</param>
    /// <returns>Lista de tokens</returns>
    public static List<String> SeparaTokens(String par_Tokens, String par_Separador) {

        List<String> loc_List = new ArrayList<String>();

        par_Tokens = par_Tokens.trim();

        if (par_Separador.length() == 1) {
            myjob.func.general.GeneralFunc.addRange(loc_List, par_Tokens.split(par_Separador));
        } else {
            int loc_Pos = 0;
            int loc_Pos1 = 0;

            while (par_Tokens.length() > loc_Pos1) {

                loc_Pos = loc_Pos1;

                loc_Pos1 = par_Tokens.indexOf(par_Separador, loc_Pos + 1);
                if (loc_Pos1 < 0) {
                    loc_Pos1 = par_Tokens.length();
                }

                loc_List.add(par_Tokens.substring(loc_Pos, loc_Pos1).replace(par_Separador, ""));

            }
        }

        return loc_List;
    }

    /**
     * <para>Aplica una mascara a una secuencia</para>
     * <para>la mascara está en el formato [Cantidad_de_Caracteres][Relleno]...</para>
     * <para>por ejemplo una mascara 0###.0##.0## la mascara seria 403030</para>
     * <param name="par_Valor">valor a convertir</param>
     * <param name="par_Mascara">mascara a aplicar</param>
     * <returns>Valor con mascara aplicada</returns>
     */
    public static String AplicarMascara(String par_Valor, String par_Mascara) {
        return AplicarMascara(par_Valor, par_Mascara, ".");
    }

    public static String capitalize(String s) {
        if(s.length() == 0) return s;

        if(s.charAt(0) == s.toLowerCase().charAt(0)) {
            // letra en minuscula
            return s.substring(1,1).toUpperCase() + s.substring(2);
        }

        return s;

    }

    /// <summary>
    /// <para>Aplica una mascara a una secuencia</para>
    /// <para>la mascara está en el formato [Cantidad_de_Caracteres][Relleno]...</para>
    /// <para>por ejemplo una mascara 0###.0##.0## la mascara seria 403030</para>
    /// </summary>
    /// <param name="par_Valor">valor a convertir</param>
    /// <param name="par_Mascara">mascara a aplicar</param>
    /// <param name="par_Separador">cadena que funciona como separador</param>
    /// <returns>Valor con mascara aplicada</returns>
    /// <example>
    /// <code>AplicarMascara("1*1*1","403030","*")</code>
    /// </example>
    public static String AplicarMascara(String par_Valor, String par_Mascara, String par_Separador) {
        List<String> loc_List;
        List<String> loc_MascRelleno = new ArrayList<String>();
        String loc_Str;

        for (int loc_Pos = 0; loc_Pos < par_Mascara.length(); loc_Pos += 2) {
            loc_MascRelleno.add(TextFunc.repeat(Integer.parseInt(par_Mascara.substring(loc_Pos, loc_Pos)), par_Mascara.substring(loc_Pos + 1, loc_Pos + 1).charAt(0)));
        }

        loc_List = SeparaTokens(par_Valor, par_Separador);


        if (loc_MascRelleno.size() < loc_List.size()) {
            //hay menos mascara que items
            for (int loc_Pos = loc_MascRelleno.size(); loc_Pos < loc_List.size(); loc_Pos++) {
                loc_MascRelleno.add("");
            }
        }

        while (loc_MascRelleno.size() > loc_List.size()) {
            // hay mas mascara que items
            loc_List.add("");
        }


        loc_Str = "";
        for (int loc_Pos = 0; loc_Pos < loc_List.size(); loc_Pos++) {
            loc_List.set(loc_Pos, loc_MascRelleno.get(loc_Pos) + loc_List.get(loc_Pos)).substring((loc_MascRelleno.get(loc_Pos) + loc_List.get(loc_Pos)).length() - loc_MascRelleno.get(loc_Pos).length(), (loc_MascRelleno.get(loc_Pos) + loc_List.get(loc_Pos)).length());

            loc_Str += loc_List.get(loc_Pos);

            if (loc_Pos != (loc_List.size() - 1)) {
                loc_Str += par_Separador;
            }
        }

        while (loc_Str.substring(loc_Str.length() - 1, loc_Str.length() - 1).equals(par_Separador) && loc_Str.length() > 0) {
            loc_Str = loc_Str.substring(0, loc_Str.length() - 2);
        }

        return loc_Str;

    }

    /// <summary>
    /// Se aplica una mascara a par_Valor y luego se separa cada componente
    /// </summary>
    /// <param name="par_Valor">valor a aplicar mascara y separar</param>
    /// <param name="par_Mascara">mascara a aplicar</param>
    /// <param name="par_Separador">cadena de separacion</param>
    /// <returns>lista de elementos de par_Valor con la mascara aplicada</returns>
    public static List<String> SeparaTokensMasked(String par_Valor, String par_Mascara, String par_Separador) {
        List<String> loc_List;
        List<String> loc_MascRelleno = new ArrayList<String>();
        int loc_Pos, loc_Pos1;
        String loc_Str;

        for (loc_Pos = 0; loc_Pos < par_Mascara.length(); loc_Pos += 2) {
            loc_Str = "";
            for (loc_Pos1 = 0; loc_Pos1 < Integer.parseInt(par_Mascara.substring(loc_Pos, loc_Pos)); loc_Pos1++) {
                loc_Str += par_Mascara.substring(loc_Pos + 1, 1);
            }
            loc_MascRelleno.add(loc_Str);
        }

        loc_List = SeparaTokens(par_Valor, par_Separador);


        if (loc_MascRelleno.size() < loc_List.size()) {
            //hay menos mascara que items
            for (loc_Pos = loc_MascRelleno.size(); loc_Pos < loc_List.size(); loc_Pos++) {
                loc_MascRelleno.add("");
            }
        }


        loc_Str = "";
        for (loc_Pos = 0; loc_Pos < loc_List.size(); loc_Pos++) {
            loc_List.set(loc_Pos, (loc_MascRelleno.get(loc_Pos) + loc_List.get(loc_Pos)).substring((loc_MascRelleno.get(loc_Pos) + loc_List.get(loc_Pos)).length() - loc_MascRelleno.get(loc_Pos).length(), (loc_MascRelleno.get(loc_Pos) + loc_List.get(loc_Pos)).length()));
        }

        return loc_List;

    }

    /// <summary>
    /// quita una mascara de una cadena
    /// </summary>
    /// <param name="par_Valor">valor a quitar mascara</param>
    /// <param name="par_Mascara">mascara a quitar</param>
    /// <param name="par_Separador">cadena de separacion</param>
    /// <returns>valor con la mascara quitada</returns>
    public static String QuitarMascara(String par_Valor, String par_Mascara, String par_Separador) {
        List<String> loc_List;
        List<String> loc_MascRelleno = new ArrayList<String>();
        int loc_Pos;
        String loc_Str;

        if (par_Valor.length() == 0) {
            return "";
        }

        for (loc_Pos = 0; loc_Pos < par_Mascara.length(); loc_Pos += 2) {
            loc_MascRelleno.add(par_Mascara.substring(loc_Pos + 1, 1));
        }

        loc_List = SeparaTokens(par_Valor, par_Separador);

        if (loc_MascRelleno.size() < loc_List.size()) {
            //hay menos mascara que items
            for (loc_Pos = loc_MascRelleno.size(); loc_Pos < loc_List.size(); loc_Pos++) {
                loc_MascRelleno.add("");
            }
        }

        loc_Str = "";
        for (loc_Pos = 0; loc_Pos < loc_List.size(); loc_Pos++) {

            while (loc_List.get(loc_Pos).substring(0, 1).equals(loc_MascRelleno.get(loc_Pos)) && loc_List.get(loc_Pos).length() > 1) {
                loc_List.set(loc_Pos, loc_List.get(loc_Pos).substring(1, loc_List.get(loc_Pos).length() - 1));
            }

            loc_Str += loc_List.get(loc_Pos);

            if (loc_Pos != (loc_List.size() - 1)) {
                loc_Str += par_Separador;
            }
        }


        return loc_Str;


    }

    /// <summary>
    /// intenta transformar una palabra del castellano de singular a plural
    /// </summary>
    /// <param name="par_String">palabra en singular</param>
    /// <returns>palabra en plural</returns>
    public static String GetPlural(String par_String) {

        if (par_String.trim().endsWith("N")) {
            return par_String + "ES";
        }

        if (par_String.trim().endsWith("n")) {
            return par_String + "es";
        }

        if (par_String.trim().substring(par_String.length() - 1).toUpperCase().equals(par_String.trim().substring(par_String.length() - 1))) {
            return par_String + "S";
        }

        return par_String + "s";

    }

    /// <summary>
    /// intenta transformar una palabra del castellano de singular a plural
    /// </summary>
    /// <param name="par_String">palabra en singular</param>
    /// <returns>palabra en plural</returns>
    public static String GetSingular(String par_String) {

        if (par_String.trim().toLowerCase().endsWith("tes")) {
            return par_String.substring(0, par_String.length() - 1);
        }

        if (par_String.trim().toLowerCase().endsWith("es")) {
            return par_String.substring(0, par_String.length() - 2);
        }

        if (par_String.trim().toLowerCase().endsWith("s")) {
            return par_String.substring(0, par_String.length() - 1);
        }

        return par_String;

    }

    /// <summary>
    /// Transforma una letra en una linea de su equivalente formado por *
    /// </summary>
    /// <param name="par_Letra">letra</param>
    /// <param name="par_Linea">linea de 0 a 7</param>
    /// <returns>linea formada por * de la letra</returns>
    public static String BigLetter(String par_Letra, int par_Linea) {

        switch (par_Letra.charAt(0)) {
            case ' ':
                if (par_Linea == 0) {
                    return "          ";
                }
                if (par_Linea == 1) {
                    return "          ";
                }
                if (par_Linea == 2) {
                    return "          ";
                }
                if (par_Linea == 3) {
                    return "          ";
                }
                if (par_Linea == 4) {
                    return "          ";
                }
                if (par_Linea == 5) {
                    return "          ";
                }
                if (par_Linea == 6) {
                    return "          ";
                }
                if (par_Linea == 7) {
                    return "          ";
                }
                break;
            case 'A':

                if (par_Linea == 0) {
                    return "   XX     ";
                }
                if (par_Linea == 1) {
                    return "  X  X    ";
                }
                if (par_Linea == 2) {
                    return " X    X   ";
                }
                if (par_Linea == 3) {
                    return "X      X  ";
                }
                if (par_Linea == 4) {
                    return "XXXXXXXX  ";
                }
                if (par_Linea == 5) {
                    return "X      X  ";
                }
                if (par_Linea == 6) {
                    return "X      X  ";
                }
                if (par_Linea == 7) {
                    return "X      X  ";
                }
                break;
            case 'a':

                if (par_Linea == 0) {
                    return "         ";
                }
                if (par_Linea == 1) {
                    return "         ";
                }
                if (par_Linea == 2) {
                    return " XXXXX   ";
                }
                if (par_Linea == 3) {
                    return "      X  ";
                }
                if (par_Linea == 4) {
                    return "  XXXXX  ";
                }
                if (par_Linea == 5) {
                    return " X    X  ";
                }
                if (par_Linea == 6) {
                    return "X     X  ";
                }
                if (par_Linea == 7) {
                    return " XXXXXX  ";
                }
                break;

            case 'B':

                if (par_Linea == 0) {
                    return "XXXXXXX   ";
                }
                if (par_Linea == 1) {
                    return "X      X  ";
                }
                if (par_Linea == 2) {
                    return "X      X  ";
                }
                if (par_Linea == 3) {
                    return "XXXXXXX   ";
                }
                if (par_Linea == 4) {
                    return "X      X  ";
                }
                if (par_Linea == 5) {
                    return "X      X  ";
                }
                if (par_Linea == 6) {
                    return "X      X  ";
                }
                if (par_Linea == 7) {
                    return "XXXXXXX   ";
                }
                break;
            case 'b':

                if (par_Linea == 0) {
                    return "X        ";
                }
                if (par_Linea == 1) {
                    return "X        ";
                }
                if (par_Linea == 2) {
                    return "X        ";
                }
                if (par_Linea == 3) {
                    return "XXXXXX   ";
                }
                if (par_Linea == 4) {
                    return "X     X  ";
                }
                if (par_Linea == 5) {
                    return "X     X  ";
                }
                if (par_Linea == 6) {
                    return "X     X  ";
                }
                if (par_Linea == 7) {
                    return "XXXXXX   ";
                }
                break;
            case 'C':
                if (par_Linea == 0) {
                    return " XXXXXX   ";
                }
                if (par_Linea == 1) {
                    return "X      X  ";
                }
                if (par_Linea == 2) {
                    return "X         ";
                }
                if (par_Linea == 3) {
                    return "X         ";
                }
                if (par_Linea == 4) {
                    return "X         ";
                }
                if (par_Linea == 5) {
                    return "X         ";
                }
                if (par_Linea == 6) {
                    return "X      X  ";
                }
                if (par_Linea == 7) {
                    return " XXXXXX   ";
                }
                break;
            case 'c':
                if (par_Linea == 0) {
                    return "         ";
                }
                if (par_Linea == 1) {
                    return "         ";
                }
                if (par_Linea == 2) {
                    return "         ";
                }
                if (par_Linea == 3) {
                    return " XXXXX   ";
                }
                if (par_Linea == 4) {
                    return "X     X  ";
                }
                if (par_Linea == 5) {
                    return "X        ";
                }
                if (par_Linea == 6) {
                    return "X     X  ";
                }
                if (par_Linea == 7) {
                    return " XXXXX   ";
                }
                break;
            case 'D':
                if (par_Linea == 0) {
                    return "XXXXXX    ";
                }
                if (par_Linea == 1) {
                    return "X     X   ";
                }
                if (par_Linea == 2) {
                    return "X      X  ";
                }
                if (par_Linea == 3) {
                    return "X      X  ";
                }
                if (par_Linea == 4) {
                    return "X      X  ";
                }
                if (par_Linea == 5) {
                    return "X      X  ";
                }
                if (par_Linea == 6) {
                    return "X     X   ";
                }
                if (par_Linea == 7) {
                    return "XXXXXX    ";
                }
                break;
            case 'd':
                if (par_Linea == 0) {
                    return "      X  ";
                }
                if (par_Linea == 1) {
                    return "      X  ";
                }
                if (par_Linea == 2) {
                    return "      X  ";
                }
                if (par_Linea == 3) {
                    return " XXXXXX  ";
                }
                if (par_Linea == 4) {
                    return "X     X  ";
                }
                if (par_Linea == 5) {
                    return "X     X  ";
                }
                if (par_Linea == 6) {
                    return "X     X  ";
                }
                if (par_Linea == 7) {
                    return " XXXXXX  ";
                }
                break;
            case 'E':
                if (par_Linea == 0) {
                    return "XXXXXXXX  ";
                }
                if (par_Linea == 1) {
                    return "X      X  ";
                }
                if (par_Linea == 2) {
                    return "X         ";
                }
                if (par_Linea == 3) {
                    return "XXXXX     ";
                }
                if (par_Linea == 4) {
                    return "X         ";
                }
                if (par_Linea == 5) {
                    return "X         ";
                }
                if (par_Linea == 6) {
                    return "X      X  ";
                }
                if (par_Linea == 7) {
                    return "XXXXXXXX  ";
                }
                break;
            case 'e':
                if (par_Linea == 0) {
                    return "         ";
                }
                if (par_Linea == 1) {
                    return "         ";
                }
                if (par_Linea == 2) {
                    return "         ";
                }
                if (par_Linea == 3) {
                    return " XXXXX   ";
                }
                if (par_Linea == 4) {
                    return "X     X  ";
                }
                if (par_Linea == 5) {
                    return "X  XXXX  ";
                }
                if (par_Linea == 6) {
                    return "X        ";
                }
                if (par_Linea == 7) {
                    return " XXXXX   ";
                }
                break;
            case 'F':
                if (par_Linea == 0) {
                    return "XXXXXXXX  ";
                }
                if (par_Linea == 1) {
                    return "X      X  ";
                }
                if (par_Linea == 2) {
                    return "X         ";
                }
                if (par_Linea == 3) {
                    return "XXXXX     ";
                }
                if (par_Linea == 4) {
                    return "X         ";
                }
                if (par_Linea == 5) {
                    return "X         ";
                }
                if (par_Linea == 6) {
                    return "X         ";
                }
                if (par_Linea == 7) {
                    return "X         ";
                }
                break;
            case 'f':
                if (par_Linea == 0) {
                    return "        ";
                }
                if (par_Linea == 1) {
                    return "        ";
                }
                if (par_Linea == 2) {
                    return "  XX    ";
                }
                if (par_Linea == 3) {
                    return " X      ";
                }
                if (par_Linea == 4) {
                    return "XXXX    ";
                }
                if (par_Linea == 5) {
                    return " X      ";
                }
                if (par_Linea == 6) {
                    return " X      ";
                }
                if (par_Linea == 7) {
                    return " X      ";
                }
                break;
            case 'G':
                if (par_Linea == 0) {
                    return " XXXXXX   ";
                }
                if (par_Linea == 1) {
                    return "X      X  ";
                }
                if (par_Linea == 2) {
                    return "X         ";
                }
                if (par_Linea == 3) {
                    return "X         ";
                }
                if (par_Linea == 4) {
                    return "X   XXXX  ";
                }
                if (par_Linea == 5) {
                    return "X   X  X  ";
                }
                if (par_Linea == 6) {
                    return "X      X  ";
                }
                if (par_Linea == 7) {
                    return " XXXXXX   ";
                }
                break;
            case 'g':
                if (par_Linea == 0) {
                    return "         ";
                }
                if (par_Linea == 1) {
                    return "         ";
                }
                if (par_Linea == 2) {
                    return " XXXXXX  ";
                }
                if (par_Linea == 3) {
                    return "X     X  ";
                }
                if (par_Linea == 4) {
                    return "X     X  ";
                }
                if (par_Linea == 5) {
                    return " XXXXXX  ";
                }
                if (par_Linea == 6) {
                    return "      X  ";
                }
                if (par_Linea == 7) {
                    return " XXXXX   ";
                }
                break;
            case 'H':
                if (par_Linea == 0) {
                    return "X      X  ";
                }
                if (par_Linea == 1) {
                    return "X      X  ";
                }
                if (par_Linea == 2) {
                    return "X      X  ";
                }
                if (par_Linea == 3) {
                    return "XXXXXXXX  ";
                }
                if (par_Linea == 4) {
                    return "X      X  ";
                }
                if (par_Linea == 5) {
                    return "X      X  ";
                }
                if (par_Linea == 6) {
                    return "X      X  ";
                }
                if (par_Linea == 7) {
                    return "X      X  ";
                }
                break;
            case 'h':
                if (par_Linea == 0) {
                    return "X        ";
                }
                if (par_Linea == 1) {
                    return "X        ";
                }
                if (par_Linea == 2) {
                    return "X        ";
                }
                if (par_Linea == 3) {
                    return "XXXXXX   ";
                }
                if (par_Linea == 4) {
                    return "X     X  ";
                }
                if (par_Linea == 5) {
                    return "X     X  ";
                }
                if (par_Linea == 6) {
                    return "X     X  ";
                }
                if (par_Linea == 7) {
                    return "X     X  ";
                }
                break;
            case 'I':
                if (par_Linea == 0) {
                    return "X  ";
                }
                if (par_Linea == 1) {
                    return "X  ";
                }
                if (par_Linea == 2) {
                    return "X  ";
                }
                if (par_Linea == 3) {
                    return "X  ";
                }
                if (par_Linea == 4) {
                    return "X  ";
                }
                if (par_Linea == 5) {
                    return "X  ";
                }
                if (par_Linea == 6) {
                    return "X  ";
                }
                if (par_Linea == 7) {
                    return "X  ";
                }
                break;
            case 'i':
                if (par_Linea == 0) {
                    return "   ";
                }
                if (par_Linea == 1) {
                    return "X  ";
                }
                if (par_Linea == 2) {
                    return "   ";
                }
                if (par_Linea == 3) {
                    return "X  ";
                }
                if (par_Linea == 4) {
                    return "X  ";
                }
                if (par_Linea == 5) {
                    return "X  ";
                }
                if (par_Linea == 6) {
                    return "X  ";
                }
                if (par_Linea == 7) {
                    return "X  ";
                }
                break;
            case 'J':
                if (par_Linea == 0) {
                    return "     XXX  ";
                }
                if (par_Linea == 1) {
                    return "      X   ";
                }
                if (par_Linea == 2) {
                    return "      X   ";
                }
                if (par_Linea == 3) {
                    return "      X   ";
                }
                if (par_Linea == 4) {
                    return "      X   ";
                }
                if (par_Linea == 5) {
                    return "X     X   ";
                }
                if (par_Linea == 6) {
                    return " X   X    ";
                }
                if (par_Linea == 7) {
                    return "  XXX     ";
                }
                break;
            case 'j':
                if (par_Linea == 0) {
                    return "        ";
                }
                if (par_Linea == 1) {
                    return "     X  ";
                }
                if (par_Linea == 2) {
                    return "        ";
                }
                if (par_Linea == 3) {
                    return "     X  ";
                }
                if (par_Linea == 4) {
                    return "     X  ";
                }
                if (par_Linea == 5) {
                    return "X    X  ";
                }
                if (par_Linea == 6) {
                    return "X   X   ";
                }
                if (par_Linea == 7) {
                    return " XXX    ";
                }
                break;
            case 'K':
                if (par_Linea == 0) {
                    return "X     XX  ";
                }
                if (par_Linea == 1) {
                    return "X    X    ";
                }
                if (par_Linea == 2) {
                    return "X   X     ";
                }
                if (par_Linea == 3) {
                    return "XXXX      ";
                }
                if (par_Linea == 4) {
                    return "X   X     ";
                }
                if (par_Linea == 5) {
                    return "X    X    ";
                }
                if (par_Linea == 6) {
                    return "X     X   ";
                }
                if (par_Linea == 7) {
                    return "X      X  ";
                }
                break;
            case 'k':
                if (par_Linea == 0) {
                    return "         ";
                }
                if (par_Linea == 1) {
                    return "         ";
                }
                if (par_Linea == 2) {
                    return "X    X   ";
                }
                if (par_Linea == 3) {
                    return "X   X    ";
                }
                if (par_Linea == 4) {
                    return "XXXX     ";
                }
                if (par_Linea == 5) {
                    return "X   X    ";
                }
                if (par_Linea == 6) {
                    return "X    X   ";
                }
                if (par_Linea == 7) {
                    return "X     X  ";
                }
                break;
            case 'L':
                if (par_Linea == 0) {
                    return "X         ";
                }
                if (par_Linea == 1) {
                    return "X         ";
                }
                if (par_Linea == 2) {
                    return "X         ";
                }
                if (par_Linea == 3) {
                    return "X         ";
                }
                if (par_Linea == 4) {
                    return "X         ";
                }
                if (par_Linea == 5) {
                    return "X         ";
                }
                if (par_Linea == 6) {
                    return "X      X  ";
                }
                if (par_Linea == 7) {
                    return "XXXXXXXX  ";
                }
                break;
            case 'l':
                if (par_Linea == 0) {
                    return "       ";
                }
                if (par_Linea == 1) {
                    return "       ";
                }
                if (par_Linea == 2) {
                    return "X      ";
                }
                if (par_Linea == 3) {
                    return "X      ";
                }
                if (par_Linea == 4) {
                    return "X      ";
                }
                if (par_Linea == 5) {
                    return "X      ";
                }
                if (par_Linea == 6) {
                    return "X      ";
                }
                if (par_Linea == 7) {
                    return " XXXX  ";
                }
                break;
            case 'M':
                if (par_Linea == 0) {
                    return "X      X  ";
                }
                if (par_Linea == 1) {
                    return "XX    XX  ";
                }
                if (par_Linea == 2) {
                    return "X X  X X  ";
                }
                if (par_Linea == 3) {
                    return "X  XX  X  ";
                }
                if (par_Linea == 4) {
                    return "X      X  ";
                }
                if (par_Linea == 5) {
                    return "X      X  ";
                }
                if (par_Linea == 6) {
                    return "X      X  ";
                }
                if (par_Linea == 7) {
                    return "X      X  ";
                }
                break;
            case 'm':
                if (par_Linea == 0) {
                    return "         ";
                }
                if (par_Linea == 1) {
                    return "         ";
                }
                if (par_Linea == 2) {
                    return "XXX XXX  ";
                }
                if (par_Linea == 3) {
                    return "X  X  X  ";
                }
                if (par_Linea == 4) {
                    return "X  X  X  ";
                }
                if (par_Linea == 5) {
                    return "X  X  X  ";
                }
                if (par_Linea == 6) {
                    return "X  X  X  ";
                }
                if (par_Linea == 7) {
                    return "X  X  X  ";
                }
                break;
            case 'N':
                if (par_Linea == 0) {
                    return "X      X  ";
                }
                if (par_Linea == 1) {
                    return "XX     X  ";
                }
                if (par_Linea == 2) {
                    return "X X    X  ";
                }
                if (par_Linea == 3) {
                    return "X  X   X  ";
                }
                if (par_Linea == 4) {
                    return "X   X  X  ";
                }
                if (par_Linea == 5) {
                    return "X    X X  ";
                }
                if (par_Linea == 6) {
                    return "X     XX  ";
                }
                if (par_Linea == 7) {
                    return "X      X  ";
                }
                break;
            case 'n':
                if (par_Linea == 0) {
                    return "        ";
                }
                if (par_Linea == 1) {
                    return "        ";
                }
                if (par_Linea == 2) {
                    return "XXXXXX  ";
                }
                if (par_Linea == 3) {
                    return "X    X  ";
                }
                if (par_Linea == 4) {
                    return "X    X  ";
                }
                if (par_Linea == 5) {
                    return "X    X  ";
                }
                if (par_Linea == 6) {
                    return "X    X  ";
                }
                if (par_Linea == 7) {
                    return "X    X  ";
                }
                break;
            case 'Ñ':
                if (par_Linea == 0) {
                    return "  XXXX    ";
                }
                if (par_Linea == 1) {
                    return "          ";
                }
                if (par_Linea == 2) {
                    return "X      X  ";
                }
                if (par_Linea == 3) {
                    return "XX     X  ";
                }
                if (par_Linea == 4) {
                    return "X XX   X  ";
                }
                if (par_Linea == 5) {
                    return "X   XX X  ";
                }
                if (par_Linea == 6) {
                    return "X     XX  ";
                }
                if (par_Linea == 7) {
                    return "X      X  ";
                }
                break;
            case 'ñ':
                if (par_Linea == 0) {
                    return " XXXX   ";
                }
                if (par_Linea == 1) {
                    return "        ";
                }
                if (par_Linea == 2) {
                    return "XXXXXX  ";
                }
                if (par_Linea == 3) {
                    return "X    X  ";
                }
                if (par_Linea == 4) {
                    return "X    X  ";
                }
                if (par_Linea == 5) {
                    return "X    X  ";
                }
                if (par_Linea == 6) {
                    return "X    X  ";
                }
                if (par_Linea == 7) {
                    return "X    X  ";
                }
                break;
            case 'O':
                if (par_Linea == 0) {
                    return " XXXXXX   ";
                }
                if (par_Linea == 1) {
                    return "X      X  ";
                }
                if (par_Linea == 2) {
                    return "X      X  ";
                }
                if (par_Linea == 3) {
                    return "X      X  ";
                }
                if (par_Linea == 4) {
                    return "X      X  ";
                }
                if (par_Linea == 5) {
                    return "X      X  ";
                }
                if (par_Linea == 6) {
                    return "X      X  ";
                }
                if (par_Linea == 7) {
                    return " XXXXXX   ";
                }
                break;
            case 'o':
                if (par_Linea == 0) {
                    return "         ";
                }
                if (par_Linea == 1) {
                    return "         ";
                }
                if (par_Linea == 2) {
                    return " XXXXX   ";
                }
                if (par_Linea == 3) {
                    return "X     X  ";
                }
                if (par_Linea == 4) {
                    return "X     X  ";
                }
                if (par_Linea == 5) {
                    return "X     X  ";
                }
                if (par_Linea == 6) {
                    return "X     X  ";
                }
                if (par_Linea == 7) {
                    return " XXXXX   ";
                }
                break;
            case 'P':
                if (par_Linea == 0) {
                    return "XXXXXXX   ";
                }
                if (par_Linea == 1) {
                    return "X      X  ";
                }
                if (par_Linea == 2) {
                    return "X      X  ";
                }
                if (par_Linea == 3) {
                    return "XXXXXXX   ";
                }
                if (par_Linea == 4) {
                    return "X         ";
                }
                if (par_Linea == 5) {
                    return "X         ";
                }
                if (par_Linea == 6) {
                    return "X         ";
                }
                if (par_Linea == 7) {
                    return "X         ";
                }
                break;
            case 'p':
                if (par_Linea == 0) {
                    return "         ";
                }
                if (par_Linea == 1) {
                    return "         ";
                }
                if (par_Linea == 2) {
                    return "XXXXXX   ";
                }
                if (par_Linea == 3) {
                    return "X     X  ";
                }
                if (par_Linea == 4) {
                    return "X     X  ";
                }
                if (par_Linea == 5) {
                    return "XXXXXX   ";
                }
                if (par_Linea == 6) {
                    return "X        ";
                }
                if (par_Linea == 7) {
                    return "X        ";
                }
                break;
            case 'Q':
                if (par_Linea == 0) {
                    return " XXXXXX   ";
                }
                if (par_Linea == 1) {
                    return "X      X  ";
                }
                if (par_Linea == 2) {
                    return "X      X  ";
                }
                if (par_Linea == 3) {
                    return "X      X  ";
                }
                if (par_Linea == 4) {
                    return "X   XX X  ";
                }
                if (par_Linea == 5) {
                    return "X    XXX  ";
                }
                if (par_Linea == 6) {
                    return "X     XX  ";
                }
                if (par_Linea == 7) {
                    return " XXXXXX X ";
                }
                break;
            case 'q':
                if (par_Linea == 0) {
                    return "         ";
                }
                if (par_Linea == 1) {
                    return "         ";
                }
                if (par_Linea == 2) {
                    return " XXXXX   ";
                }
                if (par_Linea == 3) {
                    return "X    X   ";
                }
                if (par_Linea == 4) {
                    return " XXXXX   ";
                }
                if (par_Linea == 5) {
                    return "     X   ";
                }
                if (par_Linea == 6) {
                    return "   XXXX  ";
                }
                if (par_Linea == 7) {
                    return "     X   ";
                }
                break;
            case 'R':
                if (par_Linea == 0) {
                    return " XXXXXX   ";
                }
                if (par_Linea == 1) {
                    return "X      X  ";
                }
                if (par_Linea == 2) {
                    return "X      X  ";
                }
                if (par_Linea == 3) {
                    return "XXXXXXX   ";
                }
                if (par_Linea == 4) {
                    return "X   X     ";
                }
                if (par_Linea == 5) {
                    return "X    X    ";
                }
                if (par_Linea == 6) {
                    return "X     X   ";
                }
                if (par_Linea == 7) {
                    return "X      X  ";
                }
                break;
            case 'r':
                if (par_Linea == 0) {
                    return "         ";
                }
                if (par_Linea == 1) {
                    return "         ";
                }
                if (par_Linea == 2) {
                    return "XXXXXX   ";
                }
                if (par_Linea == 3) {
                    return "X     X  ";
                }
                if (par_Linea == 4) {
                    return "X        ";
                }
                if (par_Linea == 5) {
                    return "X        ";
                }
                if (par_Linea == 6) {
                    return "X        ";
                }
                if (par_Linea == 7) {
                    return "X        ";
                }
                break;
            case 'S':
                if (par_Linea == 0) {
                    return " XXXXXX   ";
                }
                if (par_Linea == 1) {
                    return "X      X  ";
                }
                if (par_Linea == 2) {
                    return "X         ";
                }
                if (par_Linea == 3) {
                    return " XXXXX    ";
                }
                if (par_Linea == 4) {
                    return "      X   ";
                }
                if (par_Linea == 5) {
                    return "       X  ";
                }
                if (par_Linea == 6) {
                    return "X      X  ";
                }
                if (par_Linea == 7) {
                    return " XXXXXX   ";
                }
                break;
            case 's':
                if (par_Linea == 0) {
                    return "         ";
                }
                if (par_Linea == 1) {
                    return "         ";
                }
                if (par_Linea == 2) {
                    return "         ";
                }
                if (par_Linea == 3) {
                    return " XXXXX   ";
                }
                if (par_Linea == 4) {
                    return "X        ";
                }
                if (par_Linea == 5) {
                    return " XXXXX   ";
                }
                if (par_Linea == 6) {
                    return "      X  ";
                }
                if (par_Linea == 7) {
                    return " XXXXX   ";
                }
                break;
            case 'T':
                if (par_Linea == 0) {
                    return "XXXXXXX  ";
                }
                if (par_Linea == 1) {
                    return "   X     ";
                }
                if (par_Linea == 2) {
                    return "   X     ";
                }
                if (par_Linea == 3) {
                    return "   X     ";
                }
                if (par_Linea == 4) {
                    return "   X     ";
                }
                if (par_Linea == 5) {
                    return "   X     ";
                }
                if (par_Linea == 6) {
                    return "   X     ";
                }
                if (par_Linea == 7) {
                    return "   X     ";
                }
                break;
            case 't':
                if (par_Linea == 0) {
                    return "      ";
                }
                if (par_Linea == 1) {
                    return "      ";
                }
                if (par_Linea == 2) {
                    return "  X   ";
                }
                if (par_Linea == 3) {
                    return " XXX  ";
                }
                if (par_Linea == 4) {
                    return "  X   ";
                }
                if (par_Linea == 5) {
                    return "  X   ";
                }
                if (par_Linea == 6) {
                    return "  X   ";
                }
                if (par_Linea == 7) {
                    return "   X  ";
                }
                break;
            case 'U':
                if (par_Linea == 0) {
                    return "X      X  ";
                }
                if (par_Linea == 1) {
                    return "X      X  ";
                }
                if (par_Linea == 2) {
                    return "X      X  ";
                }
                if (par_Linea == 3) {
                    return "X      X  ";
                }
                if (par_Linea == 4) {
                    return "X      X  ";
                }
                if (par_Linea == 5) {
                    return "X      X  ";
                }
                if (par_Linea == 6) {
                    return "X      X  ";
                }
                if (par_Linea == 7) {
                    return " XXXXXX   ";
                }
                break;
            case 'u':
                if (par_Linea == 0) {
                    return "         ";
                }
                if (par_Linea == 1) {
                    return "         ";
                }
                if (par_Linea == 2) {
                    return "X     X  ";
                }
                if (par_Linea == 3) {
                    return "X     X  ";
                }
                if (par_Linea == 4) {
                    return "X     X  ";
                }
                if (par_Linea == 5) {
                    return "X     X  ";
                }
                if (par_Linea == 6) {
                    return "X     X  ";
                }
                if (par_Linea == 7) {
                    return " XXXXXX  ";
                }
                break;
            case 'V':
                if (par_Linea == 0) {
                    return "X      X  ";
                }
                if (par_Linea == 1) {
                    return "X      X  ";
                }
                if (par_Linea == 2) {
                    return "X      X  ";
                }
                if (par_Linea == 3) {
                    return "X      X  ";
                }
                if (par_Linea == 4) {
                    return "X      X  ";
                }
                if (par_Linea == 5) {
                    return " X    X   ";
                }
                if (par_Linea == 6) {
                    return "  X  X    ";
                }
                if (par_Linea == 7) {
                    return "   XX     ";
                }
                break;
            case 'v':
                if (par_Linea == 0) {
                    return "         ";
                }
                if (par_Linea == 1) {
                    return "         ";
                }
                if (par_Linea == 2) {
                    return "X     X  ";
                }
                if (par_Linea == 3) {
                    return "X     X  ";
                }
                if (par_Linea == 4) {
                    return "X     X  ";
                }
                if (par_Linea == 5) {
                    return " X   X   ";
                }
                if (par_Linea == 6) {
                    return "  X X    ";
                }
                if (par_Linea == 7) {
                    return "   X     ";
                }
                break;
            case 'W':
                if (par_Linea == 0) {
                    return "X      X  ";
                }
                if (par_Linea == 1) {
                    return "X      X  ";
                }
                if (par_Linea == 2) {
                    return "X      X  ";
                }
                if (par_Linea == 3) {
                    return "X      X  ";
                }
                if (par_Linea == 4) {
                    return "X      X  ";
                }
                if (par_Linea == 5) {
                    return "X  X   X  ";
                }
                if (par_Linea == 6) {
                    return "X X X X   ";
                }
                if (par_Linea == 7) {
                    return " X   X    ";
                }
                break;
            case 'w':
                if (par_Linea == 0) {
                    return "         ";
                }
                if (par_Linea == 1) {
                    return "         ";
                }
                if (par_Linea == 2) {
                    return "X     X  ";
                }
                if (par_Linea == 3) {
                    return "X     X  ";
                }
                if (par_Linea == 4) {
                    return "X     X  ";
                }
                if (par_Linea == 5) {
                    return "X     X  ";
                }
                if (par_Linea == 6) {
                    return "X XX X   ";
                }
                if (par_Linea == 7) {
                    return " X  X    ";
                }
                break;
            case 'X':
                if (par_Linea == 0) {
                    return "X      X  ";
                }
                if (par_Linea == 1) {
                    return "X      X  ";
                }
                if (par_Linea == 2) {
                    return " X    X   ";
                }
                if (par_Linea == 3) {
                    return "  X  X    ";
                }
                if (par_Linea == 4) {
                    return "   XX     ";
                }
                if (par_Linea == 5) {
                    return "  X  X    ";
                }
                if (par_Linea == 6) {
                    return " X    X   ";
                }
                if (par_Linea == 7) {
                    return "X      X  ";
                }
                break;
            case 'x':
                if (par_Linea == 0) {
                    return "         ";
                }
                if (par_Linea == 1) {
                    return "         ";
                }
                if (par_Linea == 2) {
                    return " X   X   ";
                }
                if (par_Linea == 3) {
                    return "  X X    ";
                }
                if (par_Linea == 4) {
                    return "   X     ";
                }
                if (par_Linea == 5) {
                    return "  X X    ";
                }
                if (par_Linea == 6) {
                    return " X   X   ";
                }
                if (par_Linea == 7) {
                    return "X     X  ";
                }
                break;
            case 'Y':
                if (par_Linea == 0) {
                    return "X     X  ";
                }
                if (par_Linea == 1) {
                    return " X   X   ";
                }
                if (par_Linea == 2) {
                    return "  X X    ";
                }
                if (par_Linea == 3) {
                    return "   X     ";
                }
                if (par_Linea == 4) {
                    return "   X     ";
                }
                if (par_Linea == 5) {
                    return "   X     ";
                }
                if (par_Linea == 6) {
                    return "   X     ";
                }
                if (par_Linea == 7) {
                    return "   X     ";
                }
                break;
            case 'y':
                if (par_Linea == 0) {
                    return "        ";
                }
                if (par_Linea == 1) {
                    return "        ";
                }
                if (par_Linea == 2) {
                    return "X    X  ";
                }
                if (par_Linea == 3) {
                    return " X  X   ";
                }
                if (par_Linea == 4) {
                    return "  XX    ";
                }
                if (par_Linea == 5) {
                    return "  X     ";
                }
                if (par_Linea == 6) {
                    return " X      ";
                }
                if (par_Linea == 7) {
                    return "X       ";
                }
                break;
            case 'Z':
                if (par_Linea == 0) {
                    return "XXXXXXXX  ";
                }
                if (par_Linea == 1) {
                    return "      X   ";
                }
                if (par_Linea == 2) {
                    return "     X    ";
                }
                if (par_Linea == 3) {
                    return "    X     ";
                }
                if (par_Linea == 4) {
                    return "   X      ";
                }
                if (par_Linea == 5) {
                    return "  X       ";
                }
                if (par_Linea == 6) {
                    return " X        ";
                }
                if (par_Linea == 7) {
                    return "XXXXXXXX  ";
                }
                break;
            case 'z':
                if (par_Linea == 0) {
                    return "        ";
                }
                if (par_Linea == 1) {
                    return "        ";
                }
                if (par_Linea == 2) {
                    return "XXXXXXX ";
                }
                if (par_Linea == 3) {
                    return "    X   ";
                }
                if (par_Linea == 4) {
                    return "   X    ";
                }
                if (par_Linea == 5) {
                    return "  X     ";
                }
                if (par_Linea == 6) {
                    return " X      ";
                }
                if (par_Linea == 7) {
                    return "XXXXXXX ";
                }
                break;
            case '0':
                if (par_Linea == 0) {
                    return " XXXXXX   ";
                }
                if (par_Linea == 1) {
                    return "X     XX  ";
                }
                if (par_Linea == 2) {
                    return "X    X X  ";
                }
                if (par_Linea == 3) {
                    return "X   X  X  ";
                }
                if (par_Linea == 4) {
                    return "X  X   X  ";
                }
                if (par_Linea == 5) {
                    return "X X    X  ";
                }
                if (par_Linea == 6) {
                    return "XX     X  ";
                }
                if (par_Linea == 7) {
                    return " XXXXXX   ";
                }
                break;
            case '1':
                if (par_Linea == 0) {
                    return "  XX  ";
                }
                if (par_Linea == 1) {
                    return " X X  ";
                }
                if (par_Linea == 2) {
                    return "X  X  ";
                }
                if (par_Linea == 3) {
                    return "   X  ";
                }
                if (par_Linea == 4) {
                    return "   X  ";
                }
                if (par_Linea == 5) {
                    return "   X  ";
                }
                if (par_Linea == 6) {
                    return "   X  ";
                }
                if (par_Linea == 7) {
                    return "   X  ";
                }
                break;
            case '2':
                if (par_Linea == 0) {
                    return " XXXXXX   ";
                }
                if (par_Linea == 1) {
                    return "X      X  ";
                }
                if (par_Linea == 2) {
                    return "      X   ";
                }
                if (par_Linea == 3) {
                    return "     X    ";
                }
                if (par_Linea == 4) {
                    return "    X     ";
                }
                if (par_Linea == 5) {
                    return "   X      ";
                }
                if (par_Linea == 6) {
                    return "  X    X  ";
                }
                if (par_Linea == 7) {
                    return "XXXXXXX   ";
                }
                break;
            case '3':
                if (par_Linea == 0) {
                    return " XXXXXX   ";
                }
                if (par_Linea == 1) {
                    return "X      X  ";
                }
                if (par_Linea == 2) {
                    return "       X  ";
                }
                if (par_Linea == 3) {
                    return "   XXXX   ";
                }
                if (par_Linea == 4) {
                    return "       X  ";
                }
                if (par_Linea == 5) {
                    return "       X  ";
                }
                if (par_Linea == 6) {
                    return "X      X  ";
                }
                if (par_Linea == 7) {
                    return " XXXXXX   ";
                }
                break;
            case '4':
                if (par_Linea == 0) {
                    return "    XX    ";
                }
                if (par_Linea == 1) {
                    return "   X X    ";
                }
                if (par_Linea == 2) {
                    return "  X  X    ";
                }
                if (par_Linea == 3) {
                    return " X   X    ";
                }
                if (par_Linea == 4) {
                    return "XXXXXXXX  ";
                }
                if (par_Linea == 5) {
                    return "     X    ";
                }
                if (par_Linea == 6) {
                    return "     X    ";
                }
                if (par_Linea == 7) {
                    return "     X    ";
                }
                break;
            case '5':
                if (par_Linea == 0) {
                    return "XXXXXXXX  ";
                }
                if (par_Linea == 1) {
                    return "X      X  ";
                }
                if (par_Linea == 2) {
                    return "X         ";
                }
                if (par_Linea == 3) {
                    return "XXXXXX    ";
                }
                if (par_Linea == 4) {
                    return "      X   ";
                }
                if (par_Linea == 5) {
                    return "       X  ";
                }
                if (par_Linea == 6) {
                    return "X      X  ";
                }
                if (par_Linea == 7) {
                    return " XXXXXX   ";
                }
                break;
            case '6':
                if (par_Linea == 0) {
                    return "   XXXXX  ";
                }
                if (par_Linea == 1) {
                    return "  X       ";
                }
                if (par_Linea == 2) {
                    return " X        ";
                }
                if (par_Linea == 3) {
                    return " XXXXX    ";
                }
                if (par_Linea == 4) {
                    return "X     X   ";
                }
                if (par_Linea == 5) {
                    return "X      X  ";
                }
                if (par_Linea == 6) {
                    return "X      X  ";
                }
                if (par_Linea == 7) {
                    return " XXXXXX   ";
                }
                break;
            case '7':
                if (par_Linea == 0) {
                    return " XXXXXX   ";
                }
                if (par_Linea == 1) {
                    return "X      X  ";
                }
                if (par_Linea == 2) {
                    return "      X   ";
                }
                if (par_Linea == 3) {
                    return "     X    ";
                }
                if (par_Linea == 4) {
                    return "  XXXXX   ";
                }
                if (par_Linea == 5) {
                    return "   X      ";
                }
                if (par_Linea == 6) {
                    return "  X       ";
                }
                if (par_Linea == 7) {
                    return " X        ";
                }
                break;
            case '8':
                if (par_Linea == 0) {
                    return " XXXXXX   ";
                }
                if (par_Linea == 1) {
                    return "X      X  ";
                }
                if (par_Linea == 2) {
                    return "X      X  ";
                }
                if (par_Linea == 3) {
                    return " XXXXXX   ";
                }
                if (par_Linea == 4) {
                    return "X      X  ";
                }
                if (par_Linea == 5) {
                    return "X      X  ";
                }
                if (par_Linea == 6) {
                    return "X      X  ";
                }
                if (par_Linea == 7) {
                    return " XXXXXX   ";
                }
                break;
            case '9':
                if (par_Linea == 0) {
                    return " XXXXXX   ";
                }
                if (par_Linea == 1) {
                    return "X      X  ";
                }
                if (par_Linea == 2) {
                    return "X      X  ";
                }
                if (par_Linea == 3) {
                    return " XXXXXX   ";
                }
                if (par_Linea == 4) {
                    return "     X    ";
                }
                if (par_Linea == 5) {
                    return "    X     ";
                }
                if (par_Linea == 6) {
                    return "   X      ";
                }
                if (par_Linea == 7) {
                    return "  X       ";
                }
                break;
            case '.':
                if (par_Linea == 0) {
                    return "     ";
                }
                if (par_Linea == 1) {
                    return "     ";
                }
                if (par_Linea == 2) {
                    return "     ";
                }
                if (par_Linea == 3) {
                    return "     ";
                }
                if (par_Linea == 4) {
                    return "     ";
                }
                if (par_Linea == 5) {
                    return "XXX  ";
                }
                if (par_Linea == 6) {
                    return "XXX  ";
                }
                if (par_Linea == 7) {
                    return "     ";
                }
                break;
            case ',':
                if (par_Linea == 0) {
                    return "     ";
                }
                if (par_Linea == 1) {
                    return "     ";
                }
                if (par_Linea == 2) {
                    return "     ";
                }
                if (par_Linea == 3) {
                    return "     ";
                }
                if (par_Linea == 4) {
                    return "     ";
                }
                if (par_Linea == 5) {
                    return "XXX  ";
                }
                if (par_Linea == 6) {
                    return " XX  ";
                }
                if (par_Linea == 7) {
                    return "  X  ";
                }
                break;
            case '\"':
                if (par_Linea == 0) {
                    return "X  X  ";
                }
                if (par_Linea == 1) {
                    return "X  X  ";
                }
                if (par_Linea == 2) {
                    return "      ";
                }
                if (par_Linea == 3) {
                    return "      ";
                }
                if (par_Linea == 4) {
                    return "      ";
                }
                if (par_Linea == 5) {
                    return "      ";
                }
                if (par_Linea == 6) {
                    return "      ";
                }
                if (par_Linea == 7) {
                    return "      ";
                }
                break;
            case '´':
                if (par_Linea == 0) {
                    return "X  ";
                }
                if (par_Linea == 1) {
                    return "X  ";
                }
                if (par_Linea == 2) {
                    return "   ";
                }
                if (par_Linea == 3) {
                    return "   ";
                }
                if (par_Linea == 4) {
                    return "   ";
                }
                if (par_Linea == 5) {
                    return "   ";
                }
                if (par_Linea == 6) {
                    return "   ";
                }
                if (par_Linea == 7) {
                    return "   ";
                }
                break;
            case '`':
                if (par_Linea == 0) {
                    return "X  ";
                }
                if (par_Linea == 1) {
                    return "X  ";
                }
                if (par_Linea == 2) {
                    return "   ";
                }
                if (par_Linea == 3) {
                    return "   ";
                }
                if (par_Linea == 4) {
                    return "   ";
                }
                if (par_Linea == 5) {
                    return "   ";
                }
                if (par_Linea == 6) {
                    return "   ";
                }
                if (par_Linea == 7) {
                    return "   ";
                }
                break;
            case '\'':
                if (par_Linea == 0) {
                    return "X  ";
                }
                if (par_Linea == 1) {
                    return "X  ";
                }
                if (par_Linea == 2) {
                    return "   ";
                }
                if (par_Linea == 3) {
                    return "   ";
                }
                if (par_Linea == 4) {
                    return "   ";
                }
                if (par_Linea == 5) {
                    return "   ";
                }
                if (par_Linea == 6) {
                    return "   ";
                }
                if (par_Linea == 7) {
                    return "   ";
                }
                break;
            case '-':
                if (par_Linea == 0) {
                    return "          ";
                }
                if (par_Linea == 1) {
                    return "          ";
                }
                if (par_Linea == 2) {
                    return "          ";
                }
                if (par_Linea == 3) {
                    return "XXXXXXXX  ";
                }
                if (par_Linea == 4) {
                    return "          ";
                }
                if (par_Linea == 5) {
                    return "          ";
                }
                if (par_Linea == 6) {
                    return "          ";
                }
                if (par_Linea == 7) {
                    return "          ";
                }
                break;
            case '_':
                if (par_Linea == 0) {
                    return "          ";
                }
                if (par_Linea == 1) {
                    return "          ";
                }
                if (par_Linea == 2) {
                    return "          ";
                }
                if (par_Linea == 3) {
                    return "          ";
                }
                if (par_Linea == 4) {
                    return "          ";
                }
                if (par_Linea == 5) {
                    return "          ";
                }
                if (par_Linea == 6) {
                    return "          ";
                }
                if (par_Linea == 7) {
                    return "XXXXXXXX  ";
                }
                break;
            case '+':
                if (par_Linea == 0) {
                    return "          ";
                }
                if (par_Linea == 1) {
                    return "   X      ";
                }
                if (par_Linea == 2) {
                    return "   X      ";
                }
                if (par_Linea == 3) {
                    return "XXXXXXXX  ";
                }
                if (par_Linea == 4) {
                    return "   X      ";
                }
                if (par_Linea == 5) {
                    return "   X      ";
                }
                if (par_Linea == 6) {
                    return "          ";
                }
                if (par_Linea == 7) {
                    return "          ";
                }
                break;
            case '*':
                if (par_Linea == 0) {
                    return "          ";
                }
                if (par_Linea == 1) {
                    return "X  X  X   ";
                }
                if (par_Linea == 2) {
                    return " X X X    ";
                }
                if (par_Linea == 3) {
                    return "XXXXXXXX  ";
                }
                if (par_Linea == 4) {
                    return " X X X    ";
                }
                if (par_Linea == 5) {
                    return "X  X  X   ";
                }
                if (par_Linea == 6) {
                    return "          ";
                }
                if (par_Linea == 7) {
                    return "          ";
                }
                break;
            case ')':
                if (par_Linea == 0) {
                    return "X    ";
                }
                if (par_Linea == 1) {
                    return " X   ";
                }
                if (par_Linea == 2) {
                    return "  X  ";
                }
                if (par_Linea == 3) {
                    return "  X  ";
                }
                if (par_Linea == 4) {
                    return "  X  ";
                }
                if (par_Linea == 5) {
                    return "  X  ";
                }
                if (par_Linea == 6) {
                    return " X   ";
                }
                if (par_Linea == 7) {
                    return "X    ";
                }
                break;
            case '/':
                if (par_Linea == 0) {
                    return "       X    ";
                }
                if (par_Linea == 1) {
                    return "      X     ";
                }
                if (par_Linea == 2) {
                    return "     X      ";
                }
                if (par_Linea == 3) {
                    return "    X       ";
                }
                if (par_Linea == 4) {
                    return "   X        ";
                }
                if (par_Linea == 5) {
                    return "  X         ";
                }
                if (par_Linea == 6) {
                    return " X          ";
                }
                if (par_Linea == 7) {
                    return "X           ";
                }
                break;
            case '(':
                if (par_Linea == 0) {
                    return "  X  ";
                }
                if (par_Linea == 1) {
                    return " X   ";
                }
                if (par_Linea == 2) {
                    return "X    ";
                }
                if (par_Linea == 3) {
                    return "X    ";
                }
                if (par_Linea == 4) {
                    return "X    ";
                }
                if (par_Linea == 5) {
                    return "X    ";
                }
                if (par_Linea == 6) {
                    return " X   ";
                }
                if (par_Linea == 7) {
                    return "  X  ";
                }
                break;
        }

        return "          ";


    }

    /// <summary>
    /// transforma un texto en uno con letras grandes formadas por *
    /// </summary>
    /// <param name="Prefix">prefijo de cada linea, por ejemplo // para comentario</param>
    /// <param name="SmallText">texto a transformar</param>
    /// <param name="Sufix">sufijo de cada linea, por ejemplo --</param>
    /// <returns>texto grande formado por *</returns>
    public static String GetBigText(String Prefix, String SmallText, String Sufix) {

        int loc_Conta;
        int loc_Linea;

        String loc_Sale;

        loc_Sale = "";

        for (loc_Linea = 0; loc_Linea <= 7; loc_Linea++) {

            loc_Sale += Prefix;

            for (loc_Conta = 0; loc_Conta <= SmallText.length() - 1; loc_Conta++) {
                loc_Sale += BigLetter(SmallText.substring(loc_Conta, 1), loc_Linea);
            }

            loc_Sale += Sufix + "\n";

        }

        return loc_Sale;

    }

    /// <summary>
    /// Quita caracteres no imprimibles de la cadena
    /// </summary>
    /// <param name="entra">cadena de caracteres</param>
    /// <returns>cadena de caracteres sin caracteres imprimibles</returns>
    public static String NormalizeString(String entra) {

        char[] sale = entra.toCharArray();


        for (int loc_Conta = 0; loc_Conta < entra.length(); loc_Conta++) {
            if (sale[loc_Conta] < 32) {
                if (sale[loc_Conta] == '\r' ||
                        sale[loc_Conta] == '\0') {
                    sale[loc_Conta] = ' ';
                }
            }
        }

        StringBuilder loc_sb = new StringBuilder();

        loc_sb.append(sale);

        entra = loc_sb.toString().trim();

        return entra;

    }

    /// <summary>
    ///  devuelve la cantidad de apariciones de Subcadena en Cadena
    /// </summary>
    /// <param name="cadena">cadena</param>
    /// <param name="subCadena">cadena a encontrar</param>
    /// <returns>cantidad de apariciones</returns>
    public static int countStr(String cadena, String subCadena) {
        int loc_RetVal = 0;

        if (subCadena.length() > cadena.length()) {
            return 0;
        }

        int loc_Pos = 0;

        while (true) {
            if (cadena.indexOf(subCadena, loc_Pos) < 0) {
                break;
            } else {
                loc_RetVal++;
                loc_Pos = cadena.indexOf(subCadena, loc_Pos) + subCadena.length();
            }
        }

        return loc_RetVal;
    }

    /// <summary>
    /// Convierte un numero entero en una cadena de bits
    /// </summary>
    /// <param name="number">numero entero</param>
    /// <returns>cadena de caracteres formada por los bits</returns>
    public static String NumToBits(int number) {

        String retVal = "";
        Integer loc_Rem = 0;

        while (number > 1) {
            loc_Rem = GeneralFunc.DivRem(number, 2);

            retVal = loc_Rem.toString().trim() + retVal;

            number = number / 2;
        }

        retVal = ((Integer) number).toString().trim() + retVal;

        return retVal;

    }

    /// <summary>
    /// <para>Convierte un numero entero en una cadena de bits de largo fijo</para>
    /// <para>si el largo es menor se relleno con 0, si es mayor se devuelve el valor completo</para>
    /// </summary>
    /// <param name="number">numero entero</param>
    /// <returns>cadena de caracteres formada por los bits</returns>
    public static String NumToBits(int number, int lenght) {

        String loc_str = NumToBits(number);

        if (lenght < loc_str.length()) {
            lenght = loc_str.length();
        }

        return TextFunc.repeat(lenght - loc_str.length(), '0') + loc_str;

    }

    /// <summary>
    /// <para>Retorna una lista de numeros enteros conteniendo todos</para>
    /// <para>los numeros en el rango desde 0 a 999</para>
    /// <para>GetRango("*-5,50-55,995-",",",0,999);</para>
    /// <para>retorna</para>
    /// <para>0,1,2,3,4,50,51,52,53,54,55,995,996,997,998,999</para>
    /// </summary>
    /// <param name="rango">cadena que representa el rango</param>
    /// <param name="separador">separador usado por el rango</param>
    /// <param name="min">minimo valor del rango</param>
    /// <param name="max">maximo valor del rango</param>
    /// <returns>lista de numeros enteros</returns>
    public static List<Integer> GetRango(String rango) {
        return GetRango(rango, ",", 0, 999);
    }

    /// <summary>
    /// <para>Retorna una lista de numeros enteros conteniendo todos</para>
    /// <para>los numeros en el rango desde 0 a 999</para>
    /// <para>GetRango("*-5,50-55,995-",",",0,999);</para>
    /// <para>retorna</para>
    /// <para>0,1,2,3,4,50,51,52,53,54,55,995,996,997,998,999</para>
    /// </summary>
    /// <param name="rango">cadena que representa el rango</param>
    /// <param name="separador">separador usado por el rango</param>
    /// <param name="min">minimo valor del rango</param>
    /// <param name="max">maximo valor del rango</param>
    /// <returns>lista de numeros enteros</returns>
    public static List<Integer> GetRango(String rango, String separador) {
        return GetRango(rango, separador, 0, 999);
    }

    /// <summary>
    /// <para>Retorna una lista de numeros enteros conteniendo todos</para>
    /// <para>los numeros en el rango desde min a max</para>
    /// <para>GetRango("*-5,50-55,995-",",",0,999);</para>
    /// <para>retorna</para>
    /// <para>0,1,2,3,4,50,51,52,53,54,55,995,996,997,998,999</para>
    /// </summary>
    /// <param name="rango">cadena que representa el rango</param>
    /// <param name="separador">separador usado por el rango</param>
    /// <param name="min">minimo valor del rango</param>
    /// <param name="max">maximo valor del rango</param>
    /// <returns>lista de numeros enteros</returns>
    public static List<Integer> GetRango(String rango, String separador, int min, int max) {
        List<Integer> loc_l = new ArrayList<Integer>();

        for (String loc_s : SeparaTokens(rango, separador)) {
            GeneralFunc.addRange(loc_l, (Integer[]) TextFunc.GetRangoAVCle(loc_s, min, max).toArray());
        }

        return loc_l;
    }

    public static boolean EstaEnRango(int valor, String rango, String separador) {

        for (String loc_s : SeparaTokens(rango, separador)) {
            if (EstaEnRangoAVCle(valor, loc_s)) {
                return true;
            }
        }

        return false;
    }

    public static boolean EstaEnRango(int valor, String rango) {

        return EstaEnRango(valor, rango, ",");
    }

/// <summary>
/// <para>Retorna una lista de numeros enteros conteniendo todos</para>
/// <para>los numeros en el rango desde 0 a 999</para>
/// <para>GetRango("50-55"); retorna 50,51,52,53,54,55</para>
/// <para>GetRango("*-5"); retorna 0,1,2,3,4,5</para>
/// <para>GetRango("996-"); retorna 996,997,998,999</para>
/// /// <para>GetRango("80"); retorna 80</para>
/// </summary>
/// <param name="rango">cadena que representa el rango</param>
/// <returns>lista de numeros enteros</returns>
    public static List<Integer> GetRangoAVCle(String rango) {
        return GetRangoAVCle(rango, 0, 999);
    }

/// <summary>
/// Retorna Verdadero si valor está en rango
/// </summary>
/// <param name="valor"></param>
/// <param name="rango"></param>
/// <returns>booleanEAN</returns>
    public static boolean EstaEnRangoAVCle(int valor, String rango) {
        List<Integer> loc_Rango = GetRangoAVCle(rango, valor - 1, valor + 1);
        return loc_Rango.contains(valor);
    }

/// <summary>
/// <para>Retorna una lista de numeros enteros conteniendo todos</para>
/// <para>los numeros en el rango desde min a max</para>
/// <para>GetRango("50-55", 0, 999); retorna 50,51,52,53,54,55</para>
/// <para>GetRango("*-5", 0, 999); retorna 0,1,2,3,4,5</para>
/// <para>GetRango("996-", 0, 999); retorna 996,997,998,999</para>
/// /// <para>GetRango("80", 0, 999); retorna 80</para>
/// </summary>
/// <param name="rango">cadena que representa el rango</param>
/// <param name="min">minimo valor del rango</param>
/// <param name="max">maximo valor del rango</param>
/// <returns>lista de numeros enteros</returns>
    public static List<Integer> GetRangoAVCle(String rango, int min, int max) {
        Integer loc_Minimo = min;
        Integer loc_Maximo = max;

        List<Integer> loc_l = new ArrayList<Integer>();
        List<String> loc_ls;

        rango = rango.replace("*", "");

        if (!rango.equals("")) {
            if (rango.contains("-")) {
                if (!rango.equals("-")) {

                    loc_ls = SeparaTokens(rango, "-");

                    if (rango.startsWith("-")) {
                        loc_Maximo = Integer.parseInt(loc_ls.get(0));
                    } else if (rango.endsWith("-")) {
                        loc_Minimo = Integer.parseInt(loc_ls.get(0));
                    } else {
                        loc_Minimo = Integer.parseInt(loc_ls.get(0));
                        loc_Maximo = Integer.parseInt(loc_ls.get(1));
                    }

                }
            } else {
                loc_Minimo = Integer.parseInt(rango);
                loc_Maximo = Integer.parseInt(rango);
            }

        }

        for (int loc_Conta = loc_Minimo; loc_Conta <= loc_Maximo; loc_Conta++) {
            loc_l.add(loc_Conta);
        }

        return loc_l;
    }

    public static String InDent(String origen) {

        origen = origen.trim().replace("\t", "").replace("\n", "").replace(";", ";\n").replace("{", "{\n").replace("[", "\n[").replace(" + \"", " + \n\"").replace("}", "}\n").replace("]", "]\n").replace("/*", "\n/*").replace("*/", "*/\n").replace("//", "\n//").replace("\nusing", "using").replace("using", "\nusing").replace(",\n", ",").replace(",", ",\n");

        //primero saco todos los espacios en blando adelante
        List<String> loc_Rengs = SeparaTokens(origen, "\n");
        List<String> loc_RengsNorm = new ArrayList<String>();

        String loc_r;

        for (String loc_Reng : loc_Rengs) {
            loc_RengsNorm.add(loc_Reng.trim());
        }

        loc_RengsNorm.remove("");

        int loc_t = 0;
        int loc_ut = 0;
        boolean loc_Cadena = false;
        StringBuilder loc_sb = new StringBuilder();

        for (String loc_Reng : loc_RengsNorm) {


            loc_t += TextFunc.countStr(loc_Reng, "{") - TextFunc.countStr(loc_Reng, "}");
            loc_t += TextFunc.countStr(loc_Reng, "(") - TextFunc.countStr(loc_Reng, ")");

            if (loc_Reng.endsWith("{") && !loc_Cadena) {
                loc_r = TextFunc.repeat(loc_t - 1, '\t') + loc_Reng;
            } else {
                loc_r = TextFunc.repeat(loc_t, '\t') + loc_Reng;
            }

            loc_ut = loc_t;

            if (loc_Reng.endsWith("}") && loc_t == loc_ut) {
                loc_r = "\n" + loc_r;
            }

            if (!loc_r.endsWith(";") && !loc_r.endsWith("{") && !loc_r.endsWith("}") && !loc_r.startsWith("//") && !loc_r.endsWith("]") && !loc_Cadena) {
                loc_Cadena = true;
                loc_t++;

            } else if ((loc_r.endsWith(";") || loc_r.endsWith("{")) && loc_Cadena) {
                loc_t--;
                loc_Cadena = false;
            }

            loc_sb.append(loc_r).append("\n");

            if (loc_Reng.endsWith("}")) {
                loc_sb.append("\n");
            }

        }



        return loc_sb.toString();

    }

    public static String CorrectedHashCode(Object o) {
        return "_" + ((Integer) o.hashCode()).toString().replace("-", "_");
    }

    /**
     * Separa una lista de parametros tipo\n
     * par1="algo", par2="otro algo", par3=4, par4='este, es "otro" algo'
     * le saca las " y las ' y devuelve una lista de los parametros
     * @param ingreso
     * @return
     */
    public static Map<String, String> SeparaParams(String ingreso) {
        Map<String, String> retVal = new HashMap<String, String>();

        String nombre = "";
        String valor = "";
        Character separa = '\"';
        boolean enCadena = false;
        boolean enNombre = true;

        ingreso = ingreso.trim();

        for(int i = 0; i < ingreso.length(); i++) {

            if(ingreso.charAt(i) == ',' && !enCadena) {
                if(enNombre) {
                    // esta la variable sin = ni valor
                    // supongo que es booleano
                    valor = "true";
                }
                // cargo la variable, vacio todo y paso a la siguiente

                retVal.put(nombre.trim(), valor.trim());
                nombre = valor = "";
                separa = ' ';
                enCadena = false;
                enNombre = true;
                continue;
            } else if(ingreso.charAt(i) == '=' && !enCadena && enNombre) {
                enNombre = false;
            } else if(ingreso.charAt(i) == '\'' && !enCadena && !enNombre) {
                enCadena = true;
                separa = '\'';
                valor = "";
            } else if(ingreso.charAt(i) == '\"' && !enCadena && !enNombre) {
                enCadena = true;
                separa = '\"';
                valor = "";
            } else if(ingreso.charAt(i) == separa && enCadena ) {
                enCadena = false;
                separa = ' ';
                retVal.put(nombre.trim(), valor.trim());
                nombre = valor = "";
                separa = ' ';
                enCadena = false;
                enNombre = true;
                continue;
            } else if(enNombre) {
                nombre += ingreso.substring(i, i);
            } else {
                valor += ingreso.substring(i, i);
            }

        }

        if(!ingreso.endsWith(",")) {
            retVal.put(nombre.trim(), valor.trim());
        }

        return retVal;
    }

    public static String TabRow(int[] tabs, Object... inObjects) {
        StringBuilder sb = new StringBuilder();

        int[] loc_Tabs;
        Object[] objects;

        if(inObjects[0].getClass() == Object[].class) {
            objects = (Object[]) inObjects[0];
        } else {
            objects = inObjects;
        }

        int curTab = 0;

        if(objects.length > tabs.length) {
            loc_Tabs = new int[objects.length];

            System.arraycopy(tabs, 0, loc_Tabs, 0, tabs.length);

            for(int i = tabs.length; i < objects.length; i++) {
                loc_Tabs[i] = -1;
            }
        } else {
            loc_Tabs = tabs;
        }

        for(int i = 0; i < objects.length; i++) {
            if(loc_Tabs[i] < 0) {
                sb.append(objects[i].toString());
            } else {
                if(sb.length() < (loc_Tabs[i] + objects[i].toString().length())) {
                    sb.append(TextFunc.repeat(loc_Tabs[i] + objects[i].toString().length() - sb.length(), ' '));
                }

                sb.insert(loc_Tabs[i], objects[i].toString());
            }
        }

        return sb.toString();
    }

    public static List<String> separaLinea(String linea, int... tabs) {
        List<String> retVal = new ArrayList<String>();
        Logger.getLogger(TextFunc.class).log(Level.DEBUG, "entrada: " + linea);

        int largo = tabs.length;

        if(linea.length() < tabs[largo - 1]) {
            Logger.getLogger(TextFunc.class).log(Level.DEBUG, "el largo de la fila: " + linea.length() + " es menor que el largo del ultimo parametro " + tabs[largo - 1]);
            largo--;
        }

        for(int i = 1; i < largo; i++) {
            Logger.getLogger(TextFunc.class).log(Level.DEBUG, "Agregando  " + linea.substring(tabs[i - 1], tabs[i]) + " " + tabs[i - 1] + "," + tabs[i]);
            retVal.add(linea.substring(tabs[i - 1], tabs[i]));
        }

        if(largo != tabs.length) {
            Logger.getLogger(TextFunc.class).log(Level.DEBUG, "agregando lo que queda de linea despues de " + tabs[largo - 1]);
            retVal.add(linea.substring(tabs[largo - 1]));
        } else if(linea.length() > tabs[largo - 1]) {
            Logger.getLogger(TextFunc.class).log(Level.DEBUG, "agregando lo que queda de linea despues de " + tabs[largo - 1]);
            retVal.add(linea.substring(tabs[largo - 1]));
        }
        
        return retVal;
    }
}


