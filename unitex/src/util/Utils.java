/*
 * Unitex
 *
 * Copyright (C) 2001-2018 Université Paris-Est Marne-la-Vallée <unitex@univ-mlv.fr>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA.
 *
 */
package util;

import fr.umlv.unitex.common.project.manager.GlobalProjectManager;
import fr.umlv.unitex.config.Config;
import fr.umlv.unitex.config.ConfigManager;
import fr.umlv.unitex.frames.InflectFrame;
import fr.umlv.unitex.frames.InternalFrameManager;
import fr.umlv.unitex.frames.UnitexInternalFrameManager;
import fr.umlv.unitex.io.Encoding;
import fr.umlv.unitex.process.Launcher;
import fr.umlv.unitex.process.ToDo;
import fr.umlv.unitex.process.commands.MultiCommands;
import fr.umlv.unitex.process.commands.MultiFlexCommand;
import fr.umlv.unitex.process.commands.SortTxtCommand;
import fr.umlv.unitex.utils.CharsetDetector;
import helper.DelacHelper;
import helper.DelasHelper;
import java.awt.Desktop;
import java.awt.HeadlessException;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import leximir.delac.menu.MenuDelac;
import model.DictionaryPath;

/**
 *
 * @author Rojo Rabelisoa
 * @author Anas Ait cheikh
 */
public class Utils {

    /**
     * This function read file from path file and return an ArrayList<String>
     *
     * @param file path of file to open
     * @return
     * @throws IOException
     */
    public static ArrayList<String> readFile(String file) throws IOException {

        ArrayList<String> tmp;
        /*InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file));
        FileInputStream fs = new FileInputStream(new File(file));
        try (CRLFTerminatedReader reader = new CRLFTerminatedReader(fs)) {
            System.err.println(" e : "+file);
            String ligne;
            tmp = new ArrayList<>();
            while((ligne = reader.readLine()) != null){	
                System.err.println("err : "+ligne);
                tmp.add(ligne);
            }
        }
         */

        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file), CharsetDetector.detect(new File(file)));
        try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
            String ligne;
            tmp = new ArrayList<>();
            while ((ligne = reader.readLine()) != null) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < ligne.length(); i++) {
                    String str = String.valueOf(ligne.charAt(i));
                    if (str.matches("^[a-zA-Z0-9áàâäãåçéèêëíì=îïñóòôöõúùûüýÿæœÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒ._\\s-]+$||[$&+'*,:.;\\[?@#\\]/ |)_(-]")) {
                        sb.append(ligne.charAt(i));
                    }
                }
                if (!sb.toString().isEmpty()) {
                    tmp.add(sb.toString());
                }
            }
        }

        return tmp;
    }

    /**
     * This function causes a String to be inverted from right to left
     *
     * @param text is the String to reverse
     * @return
     */
    public static String reverseString(String text) {
        return new StringBuffer(text).reverse().toString();
    }

    /**
     * This function export the JTable into a csv file
     *
     * @param dicPos is the liste of the element to export
     * @param filename the name of the csv file
     */
    public static void exportJtableToCsv(List<Object[]> dicPos, String filename) throws IOException, FileNotFoundException {

        String creator = "";
        for (Object[] tab : dicPos) {
            for (Object obj : tab) {
                creator += obj.toString() + ";";
            }
            creator += "\n";
        }

        boolean isDone = false;
        try {
            FileWriter fileWriter = new FileWriter(filename, false);
            fileWriter.write(creator);
            fileWriter.close();
            isDone = true;
        } finally {
            if (isDone) {
                GlobalProjectManager.search(null).getFrameManagerAs(UnitexInternalFrameManager.class)
                        .newCsvOpener(filename);

            }
        }
    }

    public static void exportStatAllToCsv(Map<String, Object[]> simSem1, Map<String, Object[]> simSem2, String filename) throws IOException, FileNotFoundException {
        String creator = "";
        Set<String> keyset = simSem1.keySet();
        for (String key : keyset) {
            Object[] objArr = simSem1.get(key);
            for (Object obj : objArr) {
                creator += obj.toString() + ";";
            }
            creator += "\n";
        }
        creator = "POS;Semantic codes;SinSem;Count\n";
        Set<String> keysets = simSem2.keySet();
        for (String key : keysets) {
            Object[] objArr = simSem2.get(key);
            for (Object obj : objArr) {
                creator += obj.toString() + ";";
            }
            creator += "\n";
        }
        boolean isDone = false;
        try {
            FileWriter fileWriter = new FileWriter(filename, false);
            fileWriter.write(creator);
            fileWriter.close();
            isDone = true;
        } finally {
            if (isDone) {
                GlobalProjectManager.search(null).getFrameManagerAs(UnitexInternalFrameManager.class)
                        .newCsvOpener(filename);

            }
        }
    }

    public static Object[] delasToObject(String lemma, String fstCode, String sinSem, String comment, String Dicname, int valueSelected){
        //sinSem = sinSem+"="+fstCode;
//        String line = lemma + "," + fstCode + sinSem + "//" + comment;
//        String pOs = DelasHelper.getPosInDelas(line);
       
        String lemmas = lemma;
        String fSTCode = fstCode;
        String pOs=fSTCode.replaceAll("\\d","");
        String comments = comment;
        String lemmaInv = Utils.reverseString(lemma);
        String wn_SinSet = "";
        int lemmaId = valueSelected + 1;
        String dicFile = Dicname;
        int dicId = 0;
        return new Object[]{pOs, lemmas, fSTCode, sinSem, comments, lemmaInv, wn_SinSet, lemmaId, dicFile, dicId};
    }

    public static Object[] delacToObject(String lemma, String fstCode, String SinSem, String comment, String Dicname){
        String line = lemma + "," + fstCode + SinSem + "//" + comment;
        String pOs = DelacHelper.getPosInDelac(line);
        String lemaAll = lemma;
        String lema = DelacHelper.getLemaInLemaAllDelac(lemaAll);
        String fSTCode = fstCode;
        String sinSem = SinSem;
        String comments = comment;
        String wn_SinSet = "";
        int lemmaId = 10;
        String dicFile = Dicname;
        int dicId = 0;
        return new Object[]{pOs, lemaAll, lema, fSTCode, sinSem, comments, wn_SinSet, lemmaId, dicFile, dicId};
    }

    public static String getValueXml(String key) throws IOException, FileNotFoundException, IllegalArgumentException {
        File file = new File("configuration.xml");
        Properties properties;
        try (FileInputStream fileInput = new FileInputStream(file)) {
            properties = new Properties();
            properties.loadFromXML(fileInput);
        }
        Enumeration enuKeys = properties.keys();
        while (enuKeys.hasMoreElements()) {
            String keys = (String) enuKeys.nextElement();
            if (keys.equals(key)) {
                return properties.getProperty(keys);
            }
        }
        throw new IllegalArgumentException("Key not found in path");
    }

    /**
     * this fonction open terminal and run command
     *
     * @param command
     * @throws IOException
     */
    public static void runCommandTerminal(String[] command) throws IOException {
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        pb.redirectError(ProcessBuilder.Redirect.INHERIT);
        Process p = pb.start();
        while (p.isAlive()) {
        }
    }

    /**
     * This function inflect delas with the fst code which is give in parameter
     *
     * @param lemma delas entry
     * @param fst Fst code
     * @throws IOException
     * @throws FileNotFoundException
     */
    public static void InflectDelas(String lemma, String fst) throws IOException, FileNotFoundException {
        System.out.println("infect : " + DictionaryPath.inflectionPath + fst + ".grf");
        File delaffolder = new File(DictionaryPath.delafPath);
        if (!delaffolder.exists()) {
            delaffolder.mkdir();
        }

        if (new File(DictionaryPath.inflectionPath + fst + ".grf").exists()) {
            BufferedWriter bfw;
            bfw = new BufferedWriter(new FileWriter(DictionaryPath.allDelas + "/DelasTmp.dic"));
            bfw.write(lemma);
            bfw.write(",");
            bfw.write(fst);
            bfw.close();

            String[] command = {
                DictionaryPath.unitexLoggerPath, "MultiFlex",
                DictionaryPath.allDelas + "/DelasTmp.dic",
                "-o", DictionaryPath.delafPath + "/DelafTmp.dic",
                "-a", DictionaryPath.alphabetPath,
                "-d", DictionaryPath.inflectionPath
            };

            //for(String s:command)System.out.print(s+" ");
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            pb.redirectError(ProcessBuilder.Redirect.INHERIT);
            Process p = pb.start();
            while (p.isAlive()) {
            }
            new File(DictionaryPath.allDelas + "/DelasTmp.dic").delete();

            GlobalProjectManager.search(null).getFrameManagerAs(InternalFrameManager.class)
                    .newDelaFrame(new File(DictionaryPath.delafPath + "/DelafTmp.dic"));

        } else {
            throw new FileNotFoundException(" FST Graph doesn't exist");
        }

    }

    /**
     * This function generate delaf from an entry of delas(c) into snt_txt/dlf
     *
     * @param value entry of delas(c)
     * @throws IOException
     * @throws HeadlessException
     */
    public static void generateDelaf(String value) throws IOException, HeadlessException {
        String tempPath = DictionaryPath.delafTmpPathDelac;
        try (BufferedWriter bfw = new BufferedWriter(new FileWriter(tempPath))) {
            bfw.write(value + ".");
        }
        String snt = tempPath.replace(".txt", ".snt");
        try (BufferedWriter bfw = new BufferedWriter(new FileWriter(snt))) {
            bfw.write(value + ".");
        }
        String[] cmd1 = {DictionaryPath.unitexLoggerPath, "Normalize", DictionaryPath.delafTmpAbsPathDelac + "text.txt"};
        String[] cmd2 = {DictionaryPath.unitexLoggerPath, "Tokenize", DictionaryPath.delafTmpAbsPathDelac + "text.snt", "-a", DictionaryPath.alphabetPath};
        List<String> allDela = new ArrayList<>();
        File folder = new File(DictionaryPath.allDelafAbsPath);
        File[] listOfFiles = folder.listFiles();
        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                if (listOfFile.getName().endsWith(".bin")) {
                    allDela.add(DictionaryPath.allDelafAbsPath + listOfFile.getName());
                }
            }
        }
        String[] cmdTmp = {DictionaryPath.unitexLoggerPath, "Dico", "-t", DictionaryPath.delafTmpAbsPathDelac + "text.snt", "-a", DictionaryPath.alphabetPath};
        String[] cmd3 = new String[cmdTmp.length + allDela.size()];
        System.arraycopy(cmdTmp, 0, cmd3, 0, cmdTmp.length);
        int indiceCmd = cmdTmp.length;
        for (String alldela : allDela) {
            cmd3[indiceCmd] = alldela;
            indiceCmd++;
        }
        Utils.runCommandTerminal(cmd1);
        Utils.runCommandTerminal(cmd2);
        Utils.runCommandTerminal(cmd3);
    }

}
