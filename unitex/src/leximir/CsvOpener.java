/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leximir;

import javax.swing.JFrame;

import fr.umlv.unitex.common.project.manager.GlobalProjectManager;
import fr.umlv.unitex.frames.UnitexInternalFrameManager;
import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.DictionaryPath;
import util.Utils;

/**
 *
 * @author anas
 */
public class CsvOpener extends javax.swing.JInternalFrame {

    private String csvfile;

    public CsvOpener(String csvfile) {
        super("Csv Opener", true, true, true, true);
		this.csvfile = csvfile;
        initComponents();
    }

    private Vector<String> header() {
        try {
            BufferedReader fichier_source = new BufferedReader(new FileReader(csvfile));
            String chaine;
            if ((chaine = fichier_source.readLine()) != null) {
                return new Vector<String>(Arrays.asList(chaine.split(";")));
            }
        } catch (Exception e) {
            System.out.println("Le fichier est introuvable !");
        }
        return null;
    }

    private Vector<Vector> read() {
        Vector<Vector> tmp = new Vector<Vector>();
        try {
            BufferedReader fichier_source = new BufferedReader(new FileReader(csvfile));
            String chaine;
            int i = 1;
            while ((chaine = fichier_source.readLine()) != null) {
                if (i > 1) {
                    tmp.add(new Vector<String>(Arrays.asList(chaine.split(";"))));
                }
                i++;
            }
            fichier_source.close();
        } catch (Exception e) {
                e.printStackTrace();
        }
        return tmp;

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        Vector<String> header = this.header();
        Vector<Vector> data = this.read();
		System.out.println(data.size());
		for(Vector<String> v : data)
			for(String s: v)
				System.out.println(s);
		
        JTable tableau = new JTable(data, header);
		tableau.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        getContentPane().add(new JScrollPane(tableau));
        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
	
}