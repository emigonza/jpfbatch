/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjob.func.classutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import myjob.func.general.GeneralFunc;

public class ClassFunc {

    private static final Class[] parameters = new Class[]{URL.class};

    public static String getClassPath() {
        String retVal = "";
        
        
        //Get the System Classloader
        ClassLoader sysClassLoader = ClassLoader.getSystemClassLoader();
 
        //Get the URLs
        URL[] urls = ((URLClassLoader)sysClassLoader).getURLs();
 
        for(int i=0; i< urls.length; i++)
        {
            if(!retVal.isEmpty()) {
                retVal += ":";
            }
            retVal += urls[i].getFile();
        }     
        
        
        return retVal;
    }
    
    /**
     * Agrega un archivo al classpath en tiempo de ejecución
     * @param s
     * @throws IOException
     */
    public static void addFile(String s) throws IOException {
        if (s.trim().length() == 0) {
            return;
        }
        File f = new File(s);
        addFile(f);
    }//end method
    
    
    /**
     * Agrega una ruta al classpath en tiempo de ejecución
     * @param s
     * @throws IOException
     */
    public static void addPath(String s) throws IOException {
        addFile(s);
    }//end method

    /**
     * Agrega un archivo al classpath en tiempo de ejecución
     * @param cl
     * @param s
     * @throws IOException
     */
    public static void addFile(ClassLoader cl, String s) throws IOException {
        File f = new File(s);
        addFile(cl, f);
    }//end method

    /**
     * Agrega una rchivo al classpath en tiempo de ejecución
     * @param f
     * @throws IOException
     */
    public static void addFile(File f) throws IOException {
        addURL(f.toURI().toURL());
    }//end method

    /**
     * Agrega una rchivo al classpath en tiempo de ejecución
     * @param cl
     * @param u
     * @throws IOException
     */
    public static void addFile(ClassLoader cl, File f) throws IOException {
        addURL(cl, f.toURI().toURL());
    }//end method

    /**
     * Agrega una dirección URL al classpath en tiempo de ejecución
     * @param cl
     * @param u
     * @throws IOException
     */
    public static void addURL(URL u) throws IOException {

        addURL(ClassLoader.getSystemClassLoader(), u);

    }//end method

    /**
     * Agrega una dirección URL al classpath en tiempo de ejecución
     * @param cl
     * @param u
     * @throws IOException
     */
    public static void addURL(ClassLoader cl, URL u) throws IOException {

        URLClassLoader sysloader = (URLClassLoader) cl;

        URL urls[] = sysloader.getURLs();

        for (int i = 0; i < urls.length; i++) {
            if (urls[i].toString().equalsIgnoreCase(u.toString())) {
                //YA ESTÁ
                return;
            }
        }

        Class sysclass = URLClassLoader.class;

        try {
            Method method = sysclass.getDeclaredMethod("addURL", parameters);
            method.setAccessible(true);
            method.invoke(sysloader, new Object[]{u});
        } catch (Throwable t) {
            t.printStackTrace();
            throw new IOException("Error, could not add URL to system classloader");
        }//end try catch

    }//end method

    /**
     * Obtiene una instancia de clase a partir de un jar y un nombre de clase
     * @param jarFileName
     * @param className
     * @return
     */
    public static Object getInstanceFromClassJar(String jarFileName, String className) throws Exception {
        URLClassLoader clazzLoader;
        Class clazz;

        jarFileName = jarFileName.replace("$HOME", myjob.func.general.GeneralFunc.getExecutionPath());

        File jarFile = new File(jarFileName);

        if (!jarFile.exists()) {
            throw new Exception("el archivo " + jarFileName + " no existe");
        }

        URL url = jarFile.toURI().toURL();

        clazzLoader = new URLClassLoader(new URL[]{url});

        clazz = clazzLoader.loadClass(className);
        return clazz.newInstance();

    }//end class

    /**
     * Obtiene una instancia de clase a partir del nombre de la clase
     * @param className
     * @return
     */
    public static Object getInstanceFromClassName(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        ClassLoader clazzLoader;
        Class clazz;
        //try {
        clazzLoader = ClassLoader.getSystemClassLoader();

        clazz = clazzLoader.loadClass(className);
        return clazz.newInstance();
        //} catch (Exception ex) {
        //    System.err.println("getClassFromJar intentando obtener " + className + ": " + ex.getMessage());
        //}

    }


    public static Object getClassInstance(Class clazz) throws ClassNotFoundException, InstantiationException, IllegalAccessException {

        return clazz.newInstance();

    }

    /**
     * Obtiene una lista de las clases dentro del paquete packagename
     * @param jarName
     * @param packageName
     * @param debug
     * @return
     */
    public static Class[] getClasses(String pckgname) throws ClassNotFoundException {
        return getClasses(pckgname, false);
    }

    /**
     * Obtiene una lista de las clases dentro del paquete packagename
     * @param jarName
     * @param packageName
     * @param debug
     * @return
     */
    public static Class[] getClasses(String pckgname, boolean debug) throws ClassNotFoundException {

        String dirName = "";

        if (debug) {
            System.out.println("looking in package " + pckgname);
        }

        // Get a File object for the package
        try {

            dirName = Thread.currentThread().getContextClassLoader().getResource(pckgname.replace(".", "/").replace("\\", "/")).getFile();

            if (debug) {
                System.out.println("resource name " + dirName);
            }

            if (GeneralFunc.getOS().toLowerCase().startsWith("windows")) {
                if (dirName.contains("file:/")) {
                    dirName = dirName.split("file:/")[1];
                }
            } else {
                // *nix OS

                if (dirName.startsWith("file:/")) {
                    dirName = dirName.substring(5);
                }

            }
        } catch (NullPointerException x) {
            throw new ClassNotFoundException(pckgname + " does not appear to be a valid package");
        }

        if (debug) {
            System.out.println("looking in dir/jar " + dirName + " os " + GeneralFunc.getOS());
        }

        if (dirName.contains(".jar!")) {

            String jarName = dirName.split("!")[0];

            return getClasseInPackage(jarName, pckgname, debug);
        } else {
            return getClassesInDir(new File(dirName), pckgname);
        }
    }

    /**
     * Obtiene una lista de las clases en el directorio especificado, dentro del paquete packagename
     * @param jarName
     * @param packageName
     * @param debug
     * @return
     */
    public static Class[] getClassesInDir(File directory, String pckgname) throws ClassNotFoundException {

        ArrayList classes = new ArrayList();

        if (directory.exists()) {
            // Get the list of the files contained in the package
            String[] files = directory.list();
            for (int i = 0; i < files.length; i++) {
                // we are only interested in .class files
                if (files[i].endsWith(".class")) {
                    // removes the .class extension
                    classes.add(Class.forName(pckgname + "." + files[i].substring(0, files[i].length() - 6)));
                }
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, directory.getAbsolutePath());

            throw new ClassNotFoundException(pckgname + " does not appear to be a valid package");
        }
        Class[] classesA = new Class[classes.size()];
        classes.toArray(classesA);
        return classesA;
    }

    /**
     * Obtiene una lista de las clases en el jar especificado, dentro del paquete packagename
     * @param jarName
     * @param packageName
     * @param debug
     * @return
     */
    public static Class[] getClasseInPackage(String jarName, String packageName) {
        return getClasseInPackage(jarName, packageName, false);
    }

    /**
     * Obtiene una lista de las clases en el jar especificado, dentro del paquete packagename
     * @param jarName
     * @param packageName
     * @param debug
     * @return
     */
    public static Class[] getClasseInPackage(String jarName, String packageName, boolean debug) {
        ArrayList classes = new ArrayList();

        ClassLoader clazzLoader = null;
        Class clazz;

        String className = "";

        try {
            clazzLoader = ClassLoader.getSystemClassLoader();
        } catch (Exception ex) {
            System.err.println("getClassFromJar: " + ex.getMessage());
        }

        packageName = packageName.replaceAll("\\.", "/");

        if (debug) {
            System.out.println("Jar " + jarName + " looking for " + packageName);
        }

        try {
            JarInputStream jarFile = new JarInputStream(new FileInputStream(jarName));
            JarEntry jarEntry;

            while (true) {
                jarEntry = jarFile.getNextJarEntry();
                if (jarEntry == null) {
                    break;
                }
                if ((jarEntry.getName().startsWith(packageName))
                        && (jarEntry.getName().endsWith(".class"))) {
                    if (debug) {
                        System.out.println("Found " + jarEntry.getName().replaceAll("/", "\\."));
                    }

                    className = jarEntry.getName().replaceAll("/", "\\.");

                    classes.add(clazzLoader.loadClass(className.substring(0, className.length() - 6)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Class[] classesA = new Class[classes.size()];

        for (int i = 0; i < classesA.length; i++) {
            classesA[i] = (Class) classes.get(i);
        }

        //classes.toArray(classesA);

        return classesA;
    }

    /**
     * Obtiene el nombre del archivo jar de la clase en ejecución actual
     * @param clazz
     * @return
     */
    public static String getJarPath(Class clazz) {
        String path = clazz.getProtectionDomain().getCodeSource().getLocation().getPath();

        System.out.println("jarpath " + path);

        if (path.contains(".jar!")) {
            path = path.substring(0, path.indexOf(".jar!") + 4);
        }

        System.out.println("jarpath " + path);

        if (path.startsWith("file:")) {
            path = path.substring(5);
        }

        System.out.println("jarpath " + path);

        if (path.endsWith(".jar")) {
            path = path.substring(0, path.lastIndexOf("/"));
        }


        return path;
    }

    /**
     * Añade una ruta al classpath en tiempo de ejecución
     * @param s
     * @throws IOException
     */
    public static void addLibPathDir(String s) throws IOException {
        try {
            Field field = ClassLoader.class.getDeclaredField("usr_paths");
            field.setAccessible(true);
            String[] paths = (String[]) field.get(null);
            for (int i = 0; i < paths.length; i++) {
                if (s.equals(paths[i])) {
                    return;
                }
            }
            String[] tmp = new String[paths.length + 1];
            System.arraycopy(paths, 0, tmp, 0, paths.length);
            tmp[paths.length] = s;
            field.set(null, tmp);

            System.setProperty("java.library.path", System.getProperty("java.library.path") + File.pathSeparator + s);

        } catch (IllegalAccessException e) {
            throw new IOException("Failed to get permissions to set library path");
        } catch (NoSuchFieldException e) {
            throw new IOException("Failed to get field handle to set library path");
        }
    }
}
