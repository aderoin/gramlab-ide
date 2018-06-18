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
package fr.umlv.unitex.leximir.strategy;

import fr.umlv.unitex.config.PreferencesManager;
import fr.umlv.unitex.io.UnicodeIO;
import fr.umlv.unitex.leximir.delas.EditorDelas;
import fr.umlv.unitex.leximir.helper.DelacHelper;
import fr.umlv.unitex.leximir.helper.DelasHelper;
import fr.umlv.unitex.leximir.helper.GridHelper;
import java.awt.Desktop;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import fr.umlv.unitex.leximir.model.DictionaryPath;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import fr.umlv.unitex.leximir.util.Utils;
import fr.umlv.unitex.leximir.util.cRuleMatcher;
import java.awt.HeadlessException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;

/**
 *
 * @author rojo
 */
public class Strategy extends javax.swing.JInternalFrame {

    private Object[][] delasData;
    private String delafBin;

    /**
     * Creates new form Strategy
     *
     * @param delafBin
     * @param delasDic
     */
    public Strategy(String delafBin, String delasDic) {
        super("Encoding Delac", true, true, true, true);
        initComponents();
        this.delafBin = delafBin;
        jCheckBox1.setEnabled(false);
        jCheckBox2.setEnabled(false);
        File f = new File(DictionaryPath.sntFolderAbsPath);
        if (!f.exists()) {
            f.mkdir();
        }

        try {
            delasData = (delasDic != null) ? DelasHelper.getAllDelasFromDicToObject(false, new File(delasDic))
                    : DelasHelper.getAllDelasFromDicToObject(true, null);

            this.jTextFieldSearchfile.setText(PreferencesManager.getUserPreferences().getRecentStrategyTxt());
            this.jTextFieldStrategy.setText(PreferencesManager.getUserPreferences().getRecentStrategyXml());

            jTextField5.setText(PreferencesManager.getUserPreferences().getRecentStrategyDelac());
            if (!jTextField5.getText().equals("")) {
                jTextField5.setColumns(11);
                jCheckBox1.setEnabled(true);
                jCheckBox2.setEnabled(true);
            }
        } catch (IOException ex) {
            Logger.getLogger(Strategy.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.jCheckBox1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                int state = e.getStateChange();
                if (Strategy.this.jTablePredict.getRowCount() > 0 && state == ItemEvent.SELECTED) {
                    notInDictionary();
                } else if (state == ItemEvent.DESELECTED) {
                    jTablePredict.setRowSorter(null);
                }
            }
        });

        this.jCheckBox2.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                int state = e.getStateChange();
                if (Strategy.this.jTablePredict.getRowCount() > 0 && state == ItemEvent.SELECTED) {
                    inDictionary();
                } else if (state == ItemEvent.DESELECTED) {
                    jTablePredict.setRowSorter(null);
                }
            }

        });

        this.jCheckBox3.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                int state = e.getStateChange();
                if (Strategy.this.jTablePredict.getRowCount() > 0 && state == ItemEvent.SELECTED) {
                    onlyFirstPrediction();
                } else if (state == ItemEvent.DESELECTED) {
                    jTablePredict.setRowSorter(null);
                }
            }

        });
    }

    private void updateStrategyLinks() {
        PreferencesManager.getUserPreferences().setRecentStrategyTxt(jTextFieldSearchfile.getText());
        PreferencesManager.getUserPreferences().setRecentStrategyXml(jTextFieldStrategy.getText());

    }

    private void updateDelacLink() {
        PreferencesManager.getUserPreferences().setRecentStrategyDelac(jTextField5.getText());

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane5 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jTextFieldSearchfile = new javax.swing.JTextField();
        jButtonSearchFile = new javax.swing.JButton();
        jButtonOpenFile = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jTextFieldStrategy = new javax.swing.JTextField();
        jButtonStrategySearch = new javax.swing.JButton();
        jButtonStrategyOpen = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jButton2 = new javax.swing.JButton();
        jTextField5 = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jCheckBox4 = new javax.swing.JCheckBox();
        jPanel7 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTablePredict = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableDlf = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableRule = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuPrediction = new javax.swing.JMenu();
        jMenuExport = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();
        jMenuExit = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("List of compounds"));

        jTextFieldSearchfile.setEditable(false);

        jButtonSearchFile.setText("Browse");
        jButtonSearchFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSearchFileActionPerformed(evt);
            }
        });

        jButtonOpenFile.setText("Open");
        jButtonOpenFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOpenFileActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTextFieldSearchfile, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonSearchFile)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonOpenFile)
                .addGap(110, 110, 110))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldSearchfile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonSearchFile)
                    .addComponent(jButtonOpenFile))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Strategy "));

        jTextFieldStrategy.setEditable(false);

        jButtonStrategySearch.setText("Browse");
        jButtonStrategySearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStrategySearchActionPerformed(evt);
            }
        });

        jButtonStrategyOpen.setText("Open");
        jButtonStrategyOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStrategyOpenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextFieldStrategy, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonStrategySearch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonStrategyOpen)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldStrategy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonStrategySearch)
                    .addComponent(jButtonStrategyOpen))
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Result selection"));

        jCheckBox1.setText("Compounds that are not in existing Delac");

        jCheckBox2.setText("Compounds that are in existing Delac");

        jCheckBox3.setText("Only first prediction for each compound");

        jButton2.setText("Set Existing Delac");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTextField5.setEditable(false);
        jTextField5.setMaximumSize(new java.awt.Dimension(6, 20));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBox1)
                            .addComponent(jCheckBox2)
                            .addComponent(jCheckBox3))
                        .addGap(0, 112, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jCheckBox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox3))
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jCheckBox4.setText("Automatically export results as ...");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCheckBox4)
                .addContainerGap(73, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jCheckBox4)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(15, 15, 15)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );

        jTablePredict.setAutoCreateRowSorter(true);
        jTablePredict.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jTablePredict);

        jLabel2.setText("MWU");

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
        });

        jLabel3.setText("CFLX");

        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField2KeyPressed(evt);
            }
        });

        jLabel4.setText("SynSem");

        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField4KeyPressed(evt);
            }
        });

        jLabel5.setText("OK/NOT OK");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "OK", "NOT OK" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jButton1.setText("Clear Search");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(78, Short.MAX_VALUE))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField4)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)
                        .addComponent(jLabel5)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Delac Results", jPanel8);

        jTableDlf.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTableDlf.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(jTableDlf);

        jTableRule.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTableRule.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(jTableRule);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 673, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(0, 25, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Delas Results", jPanel9);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1081, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 28, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jScrollPane5.setViewportView(jPanel1);

        jMenuPrediction.setText("Prediction");
        jMenuPrediction.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuPredictionMouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenuPrediction);

        jMenuExport.setText("Export results");
        jMenuExport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuExportMouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenuExport);

        jMenu1.setText("Dictionary production");
        jMenu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu1MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu1);

        jMenuExit.setText("Exit");
        jMenuExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuExitMouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenuExit);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSearchFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSearchFileActionPerformed

        JFileChooser theFileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
        theFileChooser.setFileFilter(filter);
        int result = theFileChooser.showOpenDialog(this);
        theFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (result == JFileChooser.APPROVE_OPTION) {
            File f = theFileChooser.getSelectedFile();
            jTextFieldSearchfile.setText(f.getAbsolutePath());
        }
    }//GEN-LAST:event_jButtonSearchFileActionPerformed

    private void jButtonOpenFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOpenFileActionPerformed
        try {
            String filename = jTextFieldSearchfile.getText();
            Desktop.getDesktop().open(new File(filename));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
    }//GEN-LAST:event_jButtonOpenFileActionPerformed

    private void jButtonStrategySearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStrategySearchActionPerformed
        JFileChooser theFileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("XML FILES", "xml");
        theFileChooser.setFileFilter(filter);
        int result = theFileChooser.showOpenDialog(this);
        theFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (result == JFileChooser.APPROVE_OPTION) {
            File f = theFileChooser.getSelectedFile();
            jTextFieldStrategy.setText(f.getAbsolutePath());
        }
    }//GEN-LAST:event_jButtonStrategySearchActionPerformed

    private void jButtonStrategyOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStrategyOpenActionPerformed
        try {
            String filename = jTextFieldStrategy.getText();
            Desktop.getDesktop().open(new File(filename));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
    }//GEN-LAST:event_jButtonStrategyOpenActionPerformed

    public List<String> getDlfInFile(String value, String delafBin) throws IOException, HeadlessException {
        Utils.generateDelaf(value, delafBin);
        String path = DictionaryPath.text_sntAbsPath;
        ArrayList<String> readFile = Utils.readFile(path);
        return readFile;
    }

    private void jMenuPredictionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuPredictionMouseClicked
        updateStrategyLinks();
        try {
            ArrayList<String> corpus = Utils.readFile(jTextFieldSearchfile.getText());
            List<String> lema = new ArrayList<>();
            List<String> flx = new ArrayList<>();
            List<String> predict = new ArrayList<>();
            List<Integer> noWord = new ArrayList<>();
            List<String[]> wordsOfLema = new ArrayList<>();
            List<String> result = new ArrayList<>();

            int size = corpus.size();
            boolean succes = true;
            for (int i = 0; i < size; i++) {
                if (!corpus.get(i).contains("+")) {
                    JOptionPane.showMessageDialog(null, "Syntaxe error in Compounds list ", "Erreur", JOptionPane.ERROR_MESSAGE);
                    succes = false;
                    break;
                }
                int indexLema = corpus.get(i).indexOf("+");
                lema.add(i, indexLema != -1 ? corpus.get(i).substring(0, indexLema - 1) : corpus.get(i));
                flx.add(i, indexLema != -1 ? corpus.get(i).substring(indexLema + 1, corpus.get(i).length()) : corpus.get(i));
                char separator = lema.get(i).split("-").length > 1 ? '-' : ' ';
                noWord.add(i, lema.get(i).split(String.valueOf(separator)).length);
                String[] wordSplit = lema.get(i).split(String.valueOf(separator));
                wordsOfLema.add(i, wordSplit);
                result.addAll(getDlfInFile(lema.get(i), delafBin));
            }
            if (succes) {
                jTableDlf.setModel(GridHelper.getDataforjTableDlf(result));
                jTableDlf.repaint();

                Object[][] allDelasImportant = new Object[jTableDlf.getRowCount()][5];
                for (Object[] allDela : delasData) {

                    for (int i = 0; i < jTableDlf.getRowCount(); i++) {
                        String text = (String) jTableDlf.getModel().getValueAt(i, 1);
                        String compare = (String) allDela[1];
                        try {
                            if (text.equals(compare)) {
                                String word = (String) jTableDlf.getModel().getValueAt(i, 0);
                                word = word.split(",")[0];
                                allDelasImportant[i][0] = allDela[1];// Lemma
                                allDelasImportant[i][1] = allDela[2];// FLX CODE
                                allDelasImportant[i][2] = allDela[0];// POS
                                allDelasImportant[i][3] = jTableDlf.getModel().getValueAt(i, 3);// Features
                                allDelasImportant[i][4] = word;// words
                            }
                        } catch (java.lang.NullPointerException e) {
                            continue;
                        }
                    }
                }

                String[] enteteRule = {"Lemma", "FST Code", "POS", "Features", "Word"};
                jTableRule.setModel((new JTable(new DefaultTableModel(allDelasImportant, enteteRule))).getModel());
                jTableRule.repaint();

                for (int i = 0; i < size; i++) {
                    char separator = lema.get(i).split("-").length > 1 ? '-' : ' ';
                    predict.addAll(getLemaFromXmlRule(Arrays.asList(wordsOfLema.get(i)), separator));
                }

//            System.out.println(predict.size());
//            for (String p : predict) {
//                System.out.println(p);
//            }
                DefaultTableModel model = new DefaultTableModel() {
                    @Override
                    public Class<?> getColumnClass(int column) {
                        switch (column) {
                            case 0:
                                return String.class;
                            case 1:
                                return String.class;
                            case 2:
                                return String.class;
                            case 3:
                                return String.class;
                            case 4:
                                return String.class;
                            case 5:
                                return Boolean.class;
                            case 6:
                                return Boolean.class;
                            case 7:
                                return Boolean.class;
                            case 8:
                                return String.class;
                            case 9:
                                return String.class;
                            default:
                                return String.class;
                        }
                    }
                };
                model.addColumn("Clemma");
                model.addColumn("CFLX");
                model.addColumn("Word no.");
                model.addColumn("SpecCond Id");
                model.addColumn("Rule Id");
                model.addColumn("Lemma OK");
                model.addColumn("CFLX OK");
                model.addColumn("ALL OK");
                model.addColumn("MWU");
                model.addColumn("SynSem");

                int j = 0;
                for (int i = 0; i < predict.size(); i++) {
                    String[] str = predict.get(i).split(",");
                    if (str[0].contains("(")) {
                        while (!str[0].replaceAll("\\(.*?\\)", "").trim().equals(lema.get(j).trim()) && j < lema.size()) {
                            j++;
                        }
                    } else {
                        while (!str[0].trim().equals(lema.get(j).trim()) && j < lema.size()) {
                            j++;
                        }
                    }
                    Object line[] = {str[0], str[1], noWord.get(j), str.length != 4 ? null : str[3], str[2], false, false,
                        false, lema.get(j), flx.get(j)};
                    model.addRow(line);
                }
                jTablePredict.setModel(model);
                jTablePredict.getColumnModel().getColumn(2).setPreferredWidth(10);
                jTablePredict.getColumnModel().getColumn(4).setPreferredWidth(10);
                jTablePredict.getColumnModel().getColumn(5).setPreferredWidth(10);
                jTablePredict.getColumnModel().getColumn(6).setPreferredWidth(10);
                jTablePredict.getColumnModel().getColumn(7).setPreferredWidth(10);

                if (jCheckBox1.isSelected()) {
                    notInDictionary();
                }
                if (jCheckBox2.isSelected()) {
                    inDictionary();
                }
                if (jCheckBox3.isSelected()) {
                    onlyFirstPrediction();
                }
                if (jCheckBox4.isSelected()) {
                    saveTxtResult();
                }
            }
        } catch (IOException | ParserConfigurationException | SAXException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);

        }


    }//GEN-LAST:event_jMenuPredictionMouseClicked

    private List<String> getLemaFromXmlRule(List<String> words, char separator) throws ParserConfigurationException, SAXException, IOException {
        List<String> retval = new ArrayList<>();
        String lemma = "", cflx = "", ruleId = "", specId = "";
        Document d = getNodeList();
        final NodeList Rules = d.getElementsByTagName("Rules");
        for (int i = 0; i < Rules.getLength(); i++) {
            final Element rules_elem = (Element) Rules.item(i); // <Rules>
            if (rules_elem.getAttribute("WordNo").equals(String.valueOf(words.size()))) { // we found the right <Rules>
                final NodeList Rule = rules_elem.getElementsByTagName("Rule");
                for (int j = 0; j < Rule.getLength(); j++) {
                    final Element rule_elem = (Element) Rule.item(j); //<Rule>

                    // RuleGenCond
                    final NodeList RuleGenCond = rule_elem.getElementsByTagName("RuleGenCond");
                    for (int k = 0; k < RuleGenCond.getLength(); k++) {
                        final Element ruleGenCond_elem = (Element) RuleGenCond.item(k); //<RuleGenCond>
                        final NodeList Word = ruleGenCond_elem.getElementsByTagName("Word");
                        for (int m = 0; m < Word.getLength(); m++) {
                            final Element word_elem = (Element) Word.item(m); //<Word>
                            cRuleMatcher matcher = new cRuleMatcher(Word, words, separator, jTableRule, jTableDlf);
                            if (matcher.match()) {
                                ruleId = rule_elem.getAttribute("ID");
                                cflx = rule_elem.getAttribute("CFLX");
                                if (word_elem.hasAttribute("Flex") && word_elem.getAttribute("Flex").equals("true")) {
                                    String flex = getFlex(words.get(m), word_elem.getAttribute("POS"));
                                    lemma += words.get(m) + flex;
                                } else {
                                    lemma += words.get(m);
                                }
                                if (m != Word.getLength() - 1) {
                                    lemma += separator;
                                }
                            }
                        }
                    }

                    // RuleSpecCond
                    final NodeList RuleSpecCond = rule_elem.getElementsByTagName("RuleSpecCond");
                    for (int s = 0; s < RuleSpecCond.getLength(); s++) {
                        final Element ruleSpecCond_elem = (Element) RuleSpecCond.item(s); //<RuleSpecCond>
                        final NodeList Word = ruleSpecCond_elem.getElementsByTagName("Word"); //Word
                        for (int w = 0; w < Word.getLength(); w++) {
                            cRuleMatcher matcher = new cRuleMatcher(Word, words, separator, jTableRule, jTableDlf);
                            if (matcher.match()) {
                                specId = ruleSpecCond_elem.getAttribute("ID");
                            }
                        }
                    }
                    if (!lemma.equals("") && !cflx.equals("") && !ruleId.equals("") && (!specId.equals("") || RuleSpecCond.getLength() == 0)) {
                        retval.add(lemma + "," + cflx + "," + ruleId + "," + specId);
                    }
                    lemma = "";
                    cflx = "";
                    ruleId = "";
                    specId = "";
                }
            }
        }

        return retval;

    }

    private Document getNodeList() throws SAXException, ParserConfigurationException, IOException {
        File inputFile = new File(jTextFieldStrategy.getText());
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();
        return doc;
        //return doc.getElementsByTagName("Rules");
    }

    private String getFlex(String words, String poss) {
        String flexion = "";
        for (int k = 0; k < jTableRule.getRowCount(); k++) {
            if (jTableRule.getValueAt(k, 1) != null) {
                if (words.equals((String) jTableRule.getValueAt(k, 4)) && (poss.equals((String) jTableRule.getValueAt(k, 2)) || poss.equals("MOT"))) {//get lemma in Table
                    String lema = (String) jTableRule.getValueAt(k, 0);
                    String flex = (String) jTableRule.getValueAt(k, 1);
                    String gramcat = (String) jTableRule.getValueAt(k, 3);
                    flexion = "(" + lema + "." + flex + ":" + gramcat + ")";
                }
            }
        }
        return flexion;
    }

    private void inDictionary() {
        RowFilter<Object, Object> filter = new RowFilter<Object, Object>() {
            public boolean include(Entry entry) {
                String lemma = (String) entry.getValue(8);
                String cflx = (String) entry.getValue(1);
                return verifDictionary(lemma, cflx);
            }
        };
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(jTablePredict.getModel());
        sorter.setRowFilter(filter);
        jTablePredict.setRowSorter(sorter);

    }

    private void notInDictionary() {
        RowFilter<Object, Object> filter = new RowFilter<Object, Object>() {
            @Override
            public boolean include(RowFilter.Entry entry) {
                String lemma = (String) entry.getValue(0);
                String cflx = (String) entry.getValue(1);
                return !verifDictionary(lemma, cflx);
            }
        };

        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(jTablePredict.getModel());
        sorter.setRowFilter(filter);
        jTablePredict.setRowSorter(sorter);

    }

    private boolean verifDictionary(String word, String cflx) {
        try {
            Object[][] data;
            if (jTextField5.getText().equals(DictionaryPath.allDelac)) {
                data = DelacHelper.getAllDelacFromDicToObject(true, null);
            } else {
                data = DelacHelper.getAllDelacFromDicToObject(false, new File(jTextField5.getText()));
            }

            for (Object[] allDelac : data) {
                String compareLemma = (String) allDelac[1];
                String compareCflx = (String) allDelac[3];
                compareLemma = compareLemma.replaceAll("\\(.*?\\)", "");
                try {
                    if (word.trim().equals(compareLemma.trim()) && cflx.equals(compareCflx)) {
                        return true;
                    }
                } catch (java.lang.NullPointerException e) {
                    continue;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Strategy.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;

    }

    private void onlyFirstPrediction() {
        final List<String> tmp = new ArrayList<>();
        RowFilter<Object, Object> filter = new RowFilter<Object, Object>() {
            @Override
            public boolean include(RowFilter.Entry entry) {
                String lemma = (String) entry.getValue(0);
                boolean ret = tmp.contains(lemma);
                tmp.add(lemma);
                return !ret;
            }
        };

        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(jTablePredict.getModel());
        sorter.setRowFilter(filter);
        jTablePredict.setRowSorter(sorter);
    }

    private void saveTxtResult() {
        File file = null;
        String path = "";
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Save");
        chooser.setApproveButtonText("Save");
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
            path = file.getPath();
            String filename = path + ".txt";
            try (OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(filename))) {
                String data = "Clemma\tCFLX\tWord NO\tSpecCond\tId Rule\tId Lema OK\tCFLX OK\tALL OK\tMWU\tSynSem\r\n";
                for (int row = 0; row < jTablePredict.getRowCount(); row++) {
                    for (int col = 0; col < jTablePredict.getColumnCount(); col++) {
                        data += jTablePredict.getModel().getValueAt(row, col) + "\t";
                    }
                    data += "\r\n";
                }
                UnicodeIO.writeString(out,data);
                Desktop.getDesktop().open(new File(filename));

            } catch (IOException ex) {
                Logger.getLogger(EditorDelas.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    private void jMenuExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuExitMouseClicked
        this.setVisible(false);

    }//GEN-LAST:event_jMenuExitMouseClicked

    private void jMenuExportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuExportMouseClicked
        saveTxtResult();
    }//GEN-LAST:event_jMenuExportMouseClicked

    private void jMenu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu1MouseClicked
        File file = null;
        String path = "";
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Save");
        chooser.setApproveButtonText("Save");
        chooser.setCurrentDirectory(new File(DictionaryPath.allDela));
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
            path = file.getPath();
            String filename = path + ".dic";            
            try (OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(filename))) {
                String data = "";
                for (int row = 0; row < jTablePredict.getRowCount(); row++) {
                    data += jTablePredict.getModel().getValueAt(row, 0) + "," + jTablePredict.getModel().getValueAt(row, 1) + "\n";
                }
                UnicodeIO.writeString(out,data);
                Desktop.getDesktop().open(new File(filename));

            } catch (IOException ex) {
                Logger.getLogger(EditorDelas.class.getName()).log(Level.SEVERE, null, ex);
            }

        }    }//GEN-LAST:event_jMenu1MouseClicked

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            JTextField textField = (JTextField) evt.getSource();
            String text = textField.getText();
            if (text.trim().length() != 0) {
                if (!text.contains(".") && !text.contains("$")) {
                    text = "^" + text;
                }
                RowFilter filter = RowFilter.regexFilter(text, 8);// recherche avec la colonne indice 8
                TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(jTablePredict.getModel());
                sorter.setRowFilter(filter);
                jTablePredict.setRowSorter(sorter);
            }
        }
    }//GEN-LAST:event_jTextField1KeyPressed

    private void jTextField2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            JTextField textField = (JTextField) evt.getSource();
            String text = textField.getText();
            if (text.trim().length() != 0) {
                if (!text.contains(".") && !text.contains("$")) {
                    text = "^" + text;
                }
                RowFilter filter = RowFilter.regexFilter(text, 1);// recherche avec la colonne indice 1
                TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(jTablePredict.getModel());
                sorter.setRowFilter(filter);
                jTablePredict.setRowSorter(sorter);
            }
        }
    }//GEN-LAST:event_jTextField2KeyPressed

    private void jTextField4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            JTextField textField = (JTextField) evt.getSource();
            String text = textField.getText();
            if (text.trim().length() != 0) {
                if (!text.contains(".") && !text.contains("$")) {
                    text = "^" + text;
                }
                RowFilter filter = RowFilter.regexFilter(text, 9);// recherche avec la colonne indice 9
                TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(jTablePredict.getModel());
                sorter.setRowFilter(filter);
                jTablePredict.setRowSorter(sorter);
            }
        }
    }//GEN-LAST:event_jTextField4KeyPressed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        JComboBox jComboBox = (JComboBox) evt.getSource();
        String text = String.valueOf(jComboBox.getSelectedItem());
        if ("OK".equals(text) || "NOT OK".equals(text)) {
            final boolean ok = "OK".equals(text);

            RowFilter<Object, Object> filter = new RowFilter<Object, Object>() {
                @Override
                public boolean include(Entry<? extends Object, ? extends Object> entry) {
                    if (ok) {
                        if (entry.getValue(7).equals(true) || (entry.getValue(5).equals(true) && entry.getValue(6).equals(true))) {
                            return true;
                        }
                    } else {
                        if (entry.getValue(7).equals(false) && (entry.getValue(5).equals(false) || entry.getValue(6).equals(false))) {
                            return true;
                        }
                    }
                    return false;
                }
            };
            TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(jTablePredict.getModel());
            sorter.setRowFilter(filter);
            jTablePredict.setRowSorter(sorter);

        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jTablePredict.setRowSorter(null);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        JCheckBox checkbox1 = new JCheckBox("All Delac in Delac folder");
        JCheckBox checkbox2 = new JCheckBox("Set a specific Delac");
        Object[] params = {checkbox1, checkbox2};
        int n = JOptionPane.showConfirmDialog(this, params, "Set Existing Delac", JOptionPane.OK_CANCEL_OPTION);
        boolean all = checkbox1.isSelected();
        boolean link = checkbox2.isSelected();
        if (n == 0 && all) {
            jTextField5.setText(DictionaryPath.allDelac);
            jCheckBox1.setEnabled(true);
            jCheckBox2.setEnabled(true);
            updateDelacLink();
        } else if (n == 0 && link) {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("*.dic", "dic");
            fileChooser.setFileFilter(filter);
            fileChooser.setCurrentDirectory(new File(DictionaryPath.allDela));
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                jTextField5.setText(selectedFile.getAbsolutePath());
                jCheckBox1.setEnabled(true);
                jCheckBox2.setEnabled(true);
                updateDelacLink();
            }
        }


    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButtonOpenFile;
    private javax.swing.JButton jButtonSearchFile;
    private javax.swing.JButton jButtonStrategyOpen;
    private javax.swing.JButton jButtonStrategySearch;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuExit;
    private javax.swing.JMenu jMenuExport;
    private javax.swing.JMenu jMenuPrediction;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableDlf;
    private javax.swing.JTable jTablePredict;
    private javax.swing.JTable jTableRule;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextFieldSearchfile;
    private javax.swing.JTextField jTextFieldStrategy;
    // End of variables declaration//GEN-END:variables

}
