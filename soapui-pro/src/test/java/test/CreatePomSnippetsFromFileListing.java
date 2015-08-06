package test;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a utility used to create the two long jar file listings in pom.xml of this module
 * from the jar files in the lib and bin directories of the SoapUI Pro installation.
 *
 * You need this when a new SoapUI version is to be supported.
 *
 * 1) Update the default location of soapuiDir.
 * 2) Build and run, copy-and-paste from the output of this app.
 * 3) Copy the first section into the maven-install-plugin executions.
 * 4) Copy the second section to dependencies.
 */
public class CreatePomSnippetsFromFileListing {

    private static class JarfileEntry {
        String subdir;
        String filename;

        public JarfileEntry(String subdir, String filename) {
            this.subdir = subdir;
            this.filename = filename;
        }
    }

    public static void main(String[] args) throws IOException {
        String soapuiDir;
        if (args.length == 0) {
            soapuiDir = "C:\\Program Files\\SmartBear\\SoapUI-Pro-5.1.2";
        } else {
            soapuiDir = args[0];
        }
        File libDir = new File(soapuiDir + File.separator + "lib");
        File binDir = new File(soapuiDir + File.separator + "bin");

        FilenameFilter jarFileNameFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".jar");
            }
        };

        File[] libFiles = libDir.listFiles(jarFileNameFilter);
        if (libFiles == null) {
            System.out.println("Cannot find directory " + libDir);
            return;
        }

        File[] binFiles = binDir.listFiles(jarFileNameFilter);
        if (binFiles == null) {
            System.out.println("Cannot find directory " + binDir);
            return;
        }

        List<JarfileEntry> files = new ArrayList<JarfileEntry>();
        for (File f : libFiles) {
            files.add(new JarfileEntry("lib", f.getName()));
        }
        for (File f : binFiles) {
            files.add(new JarfileEntry("bin", f.getName()));
        }

        printInstallFiles(files);

        System.out.println();
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println();

        printDependencies(files);
    }

    private static void printInstallFiles(List<JarfileEntry> files) {
        int n = 0;
        for (JarfileEntry f : files) {
            System.out.printf(
                    "<execution>\n" +
                            "    <id>file%d</id>\n" +
                            "    <phase>initialize</phase>\n" +
                            "    <goals>\n" +
                            "        <goal>install-file</goal>\n" +
                            "    </goals>\n" +
                            "    <configuration>\n" +
                            "        <groupId>se.caglabs.soapui-pro</groupId>\n" +
                            "        <artifactId>lib%d</artifactId>\n" +
                            "        <version>1</version>\n" +
                            "        <packaging>jar</packaging>\n" +
                            "        <file>${soapuidir}/%s/%s</file>\n" +
                            "    </configuration>\n" +
                            "</execution>\n",
                    n, n, f.subdir, f.filename);
            ++n;
        }
    }

    private static void printDependencies(List<JarfileEntry> files) {
        int n = 0;
        for (JarfileEntry f : files) {
            System.out.printf(
                    "<dependency>\n" +
                            "    <groupId>se.caglabs.soapui-pro</groupId>\n" +
                            "    <artifactId>lib%d</artifactId>\n" +
                            "    <version>1</version>\n" +
                            "    <scope>system</scope>\n" +
                            "    <systemPath>${soapuidir}/%s/%s</systemPath>\n" +
                            "</dependency>\n",
                    n, f.subdir, f.filename);
            ++n;
        }
    }
}
