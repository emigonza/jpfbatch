/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ifepson.doc;

/**
 *
 * @author guillermot
 */
public enum DataType {

        Alfa("A", "Alfanumerico"),
        Integer("I", "Numero Entero"),
        Num8Dec("W", "Numero con 8 decimales"),
        Num4Dec("N", "Numero con 4 decimales"),
        Num3Dec("M", "Numero con 3 decimales"),
        Num2Dec("D", "Numero con 2 decimales"),
        Boolean("B", "Numero booleano de 1 o mas de largo"),
        Hexa("H", "Hexadecimal"),
        Byte("Y", "Byte, debe tomarse el valor numerico del byte"),
        Hora("T", "Hora, el formato es HHMMSS"),
        Fecha("F", "Fecha, el formato es AAMMDD");
        String type = "A";
        String desc = "";

        DataType(String letter, String desc) {
            type = letter;
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
