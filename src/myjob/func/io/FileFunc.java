/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjob.func.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author guillermot
 */
public class FileFunc {

    public static boolean deleteDirectory(String path) {
        File f = new File(path);
        if (f.exists()) {
            return deleteDirectory(f);
        }
        return false;
    }

    public static boolean deleteDirectory(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return (path.delete());
    }

    public static boolean createDir(String path) {
        File f = new File(path);
        if (!f.exists()) {
            return createDir(f);
        }

        return true;
    }

    public static boolean createDir(File path) {
        return path.mkdirs();
    }

    /**
     * Busca archivos en un directorio
     * el parametro files es una lista separada por coma de los archivos, soporta * y ?
     * @param files
     * @param curPath
     * @return
     */
    public static List<File> findAll(String files, String curPath) {

        if (files.startsWith("\"") && files.endsWith("\"")) {
            files = files.substring(1, files.length() - 1);
        }

        List<File> retVal = new ArrayList<File>();

        String[] sep_files = files.split(",");

        String ind_file = "";

        for (String jar : sep_files) {
            if (jar.trim().length() > 0) {

                ind_file = jar.trim();

                if (ind_file.startsWith("..")) {
                    ind_file = curPath + "/" + ind_file.substring(0);
                } else if (ind_file.startsWith(".")) {
                    ind_file = curPath + ind_file.substring(1);
                }

                for (File inJar : myjob.func.io.FileFunc.find(ind_file)) {
                    retVal.add(inJar);
                }
            }
        }

        return retVal;

    }

    public static List<File> find(String fileName) {
        return find(fileName, false);
    }

    public static List<File> find(String fileName, boolean recursive) {

        if (!fileName.contains("*") && !fileName.contains("?")) {
            File f = new File(fileName);
            if (f.exists()) {
                List<File> retVal = new ArrayList<File>();
                retVal.add(f);
                return retVal;
            }
        }

        int ast = fileName.indexOf("*");
        int preg = fileName.indexOf("?");

        String startDir = "";

        if (ast > preg && preg >= 0) {
            // el ? está antes
            startDir = fileName.substring(0, preg);
        } else {
            // el * está antes
            startDir = fileName.substring(0, ast);
        }

        if (startDir.lastIndexOf("/") >= 0) {
            startDir = startDir.substring(0, startDir.lastIndexOf("/"));
        } else if (startDir.lastIndexOf("\\") >= 0) {
            startDir = startDir.substring(0, startDir.lastIndexOf("\\"));
        }

        return find(fileName, startDir, recursive);

    }

    public static List<File> find(String fileName, String startDir, boolean recursive) {
        return find(fileName, new File(startDir), recursive);
    }

    public static List<File> find(String fileName, File startDir, boolean recursive) {
        List<File> retVal = new ArrayList<File>();

        if (!startDir.exists()) {
            return null;
        }

        File[] files = startDir.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                if (recursive) {
                    for (File newFile : find(fileName, files[i], recursive)) {
                        retVal.add(newFile);
                    }
                }
            } else {

                if (MatchFile(fileName, files[i].getAbsolutePath())) {
                    retVal.add(files[i]);
                }
            }
        }




        return retVal;
    }

    public static boolean MatchFile(String pattern, String fileName) {
        List<File> retVal = new ArrayList<File>();

        if (!pattern.startsWith("\\") && !pattern.startsWith("/")) {
            pattern = "*" + pattern;
        }

        pattern = pattern.replace("\\", "\\\\");
        pattern = pattern.replace(".", "\\.");
        pattern = pattern.replace("$", "\\$");
        pattern = pattern.replace("^", "\\^");
        pattern = pattern.replace("[", "\\[");
        pattern = pattern.replace("]", "\\]");
        pattern = pattern.replace("(", "\\(");
        pattern = pattern.replace(")", "\\)");
        pattern = pattern.replace("*", ".*");
        pattern = "^" + pattern + "$";

        Pattern p = Pattern.compile(pattern);

        return p.matcher(fileName).matches();

    }

    public static File copyFile(String in, String out) throws IOException {
        File fIn = new File(in);
        File fOut = new File(out);
        return copyFile(fIn, fOut);
    }

    public static File copyFile(File in, File out) throws IOException {
        return copyFile(in, out, true);
    }

    public static File copyFile(File in, File out, boolean replace) throws IOException {

        if(out.exists()) {
            if(replace) {
                if(!out.delete()) {
                    throw new IOException("El archivo destino ya existe y no se puede sobreescribir");
                }
            } else {
                throw new IOException("El archivo destino ya existe");
            }
        }

        FileChannel inChannel = new FileInputStream(in).getChannel();
        FileChannel outChannel = new FileOutputStream(out).getChannel();
        try {
            inChannel.transferTo(0, inChannel.size(),
                    outChannel);
        } catch (IOException e) {
            throw e;
        } finally {
            if (inChannel != null) {
                inChannel.close();
            }
            if (outChannel != null) {
                outChannel.close();
            }
        }

        return out;
    }
    
}
