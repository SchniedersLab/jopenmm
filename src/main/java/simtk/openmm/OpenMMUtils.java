package simtk.openmm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.nio.file.Files;
import java.nio.file.Path;

public class OpenMMUtils {

    public static String JNA_LIBRARY_PATH = "";
    public static String OPENMM_PLUGIN_DIR = "";

    public synchronized static void init() {
        try {
            JarFile jarFile = jarForClass(simtk.openmm.OpenMMLibrary.class, null);

            String os;
            if (com.sun.jna.Platform.isMac()) {
                os = "darwin";
            } else if (com.sun.jna.Platform.isLinux()) {
                os = "linux_x64";
            } else {
                os = "windows";
            }

            String directory = "lib/" + os;

            Path path = Files.createTempDirectory("openmm");
            File toDir = path.toFile();
            String toDirString = toDir.getAbsolutePath() + "/" + os;

            copyResourcesToDirectory(jarFile, directory, toDirString);

            JNA_LIBRARY_PATH = System.getProperty("jna.library.path", "");
            if (!JNA_LIBRARY_PATH.equalsIgnoreCase("")) {
                JNA_LIBRARY_PATH = toDirString + File.pathSeparator + JNA_LIBRARY_PATH;
            } else {
                JNA_LIBRARY_PATH = toDirString;
            }
            System.setProperty("jna.library.path", JNA_LIBRARY_PATH);

            OPENMM_PLUGIN_DIR = toDirString + "/plugins";
        } catch (Exception e) {
            System.err.println(" Exception configuring OpenMM: " + e.toString());
        }
    }

    public static JarFile jarForClass(Class<?> clazz, JarFile defaultJar) {
        String path = "/" + clazz.getName().replace('.', '/') + ".class";
        URL jarUrl = clazz.getResource(path);
        if (jarUrl == null) {
            return defaultJar;
        }

        String url = jarUrl.toString();
        int bang = url.indexOf("!");
        String JAR_URI_PREFIX = "jar:file:";
        if (url.startsWith(JAR_URI_PREFIX) && bang != -1) {
            try {
                return new JarFile(url.substring(JAR_URI_PREFIX.length(), bang));
            } catch (IOException e) {
                throw new IllegalStateException("Error loading jar file.", e);
            }
        } else {
            return defaultJar;
        }
    }

    public static void copyResourcesToDirectory(JarFile fromJar, String jarDir, String destDir)
            throws java.io.IOException {
        for (Enumeration<JarEntry> entries = fromJar.entries(); entries.hasMoreElements(); ) {
            JarEntry entry = entries.nextElement();
            if (entry.getName().startsWith(jarDir + "/") && !entry.isDirectory()) {
                File dest = new java.io.File(destDir + "/" + entry.getName().substring(jarDir.length() + 1));
                File parent = dest.getParentFile();
                if (parent != null) {
                    parent.mkdirs();
                }

                FileOutputStream out = new FileOutputStream(dest);
                InputStream in = fromJar.getInputStream(entry);

                try {
                    byte[] buffer = new byte[8 * 1024];

                    int s;
                    while ((s = in.read(buffer)) > 0) {
                        out.write(buffer, 0, s);
                    }
                } catch (java.io.IOException e) {
                    throw new java.io.IOException("Could not copy asset from jar file", e);
                } finally {
                    try {
                        in.close();
                    } catch (java.io.IOException ignored) {
                    }
                    try {
                        out.close();
                    } catch (java.io.IOException ignored) {
                    }
                }
            }
        }
    }


}