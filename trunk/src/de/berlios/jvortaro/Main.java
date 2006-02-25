/*
 * Main.java
 *
 * Created on 9 settembre 2005, 18.16
 *
 * Copyright (C) 2005  Enrico Fracasso <efracasso@users.berlios.de>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

package de.berlios.jvortaro;

import de.berlios.jvortaro.bean.Dictionary;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;
import javax.imageio.ImageIO;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.table.TableColumnModel;
import de.berlios.jvortaro.bean.LanguageInformation;
import de.berlios.jvortaro.bean.TableRow;
import de.berlios.jvortaro.interfaces.Database;
import de.berlios.jvortaro.interfaces.Service;
import java.util.Vector;
import javax.swing.DefaultCellEditor;

/**
 *
 * @author  enrico
 */
public class Main extends javax.swing.JFrame {
    
    private ArrayList<LanguageInformation> languages;
    private Timer timer;
    private String lastClipboard = null;
    public Properties prop;
    Database database;
    Service service;
    static public Main main;
    private boolean webstart = false;
    private JTextField tableEditor;
    private JPopupMenu menu;
            
    public Main() {
        initComponents();
        
        tableEditor = new JTextField();
        tableEditor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jEditorFieldKeyReleased(evt);
            }
        });
        
        mainTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        
        main = this;

        if (jScrollPane.getComponentPopupMenu() != null)
            menu = jScrollPane.getComponentPopupMenu();
        else 
            menu = mainTable.getComponentPopupMenu();
        jScrollPane.setComponentPopupMenu(null);
        mainTable.setComponentPopupMenu(null);
        
        if (System.getProperty("javaws.debug")!= null){
            database = new de.berlios.jvortaro.jnlp.Database();
            service = new de.berlios.jvortaro.jnlp.Service();
            webstart = true;
        }else {
            database = new de.berlios.jvortaro.standalone.Database();
            service = new de.berlios.jvortaro.standalone.Service();
        }
        /** Imposto immagine dell'applicazione **/
        Image image = null;
        try {
            image = ImageIO.read(getClass().getResource("/icon.png"));
        }
        catch (Exception e) {
            Common.showError(e);
        }
        
        if (image != null)
            setIconImage(image);
        
        buttonGroup.add(jLang1Radio);
        buttonGroup.add(jLang2Radio);
        getRootPane().setDefaultButton(jSearchButton);

        
        int delay = 1000; //milliseconds
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                followClipboard();
            }
        };
        timer = new Timer(delay, taskPerformer);
        if (jFollowCheck.isSelected())
            timer.start();
        
        /** Update properties file */
        try {
            if (service.isPropertiesFileOld()) {
                //WaitWindow window = new WaitWindow();
                //window.setVisible(true);
                System.out.println("Updating properties file");
                service.updatePropertiesFiles();
                //window.setVisible(false);
            }
        } catch (Exception e){
            Common.showError(e);
        }
        
        try {
            prop = service.loadProperties();

            ComboBoxModel model = getLanguagesAvailable();
            jLanguagesCombo.setModel(model);
            if (jLanguagesCombo.getModel().getSize() > 0){
                jLanguagesCombo.setEnabled(true);
                jLanguagesCombo.setSelectedIndex(0);
            }
            
        }catch(Exception e){
            Common.showError(e);
        }
        if (prop == null)
            prop = new Properties();
        if (prop.getProperty("selected")!= null){
            String selected = prop.getProperty("selected");
            ComboBoxModel model = jLanguagesCombo.getModel();
            for (int i = 0 ; i < model.getSize(); i++){ 
                String data = (String) model.getElementAt(i);
                if (data.equalsIgnoreCase(selected)){
                    jLanguagesCombo.setSelectedItem(data);
                    break;
                }
            }
        } //else JOptionPane.showMessageDialog(this,"This is the first time you run jVortaro...", "Warning", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup = new javax.swing.ButtonGroup();
        jPopupMenu = new javax.swing.JPopupMenu();
        jInsertMenuItem = new javax.swing.JMenuItem();
        jRemoveMenuItem = new javax.swing.JMenuItem();
        jImportMenuItem = new javax.swing.JMenuItem();
        jLanguagesCombo = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jTextField = new javax.swing.JTextField();
        jSearchButton = new javax.swing.JButton();
        jFollowCheck = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        jLang1Radio = new javax.swing.JRadioButton();
        jLang2Radio = new javax.swing.JRadioButton();
        jScrollPane = new javax.swing.JScrollPane();
        mainTable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jStatusBarLabel = new javax.swing.JLabel();
        jStatusBarProgress = new javax.swing.JProgressBar();
        jPanel3 = new javax.swing.JPanel();
        jEditButton = new javax.swing.JToggleButton();
        jButtonAbout = new javax.swing.JButton();
        jButtonExit = new javax.swing.JButton();

        jPopupMenu.setInvoker(mainTable);
        jInsertMenuItem.setText("Insert");
        jInsertMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jInsertMenuItemActionPerformed(evt);
            }
        });

        jPopupMenu.add(jInsertMenuItem);

        jRemoveMenuItem.setText("Remove");
        jRemoveMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRemoveMenuItemActionPerformed(evt);
            }
        });

        jPopupMenu.add(jRemoveMenuItem);

        jImportMenuItem.setText("Import ...");
        jImportMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jImportMenuItemActionPerformed(evt);
            }
        });

        jPopupMenu.add(jImportMenuItem);

        getContentPane().setLayout(new java.awt.GridBagLayout());

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("jVortaro");
        setName("mainFrame");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLanguagesCombo.setEnabled(false);
        jLanguagesCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jLanguagesComboActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        getContentPane().add(jLanguagesCombo, gridBagConstraints);

        jLabel1.setText("Ero:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 3);
        getContentPane().add(jLabel1, gridBagConstraints);

        jTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldKeyReleased(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jTextField, gridBagConstraints);

        jSearchButton.setText("Ser\u0109u!!");
        jSearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSearchButtonActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        getContentPane().add(jSearchButton, gridBagConstraints);

        jFollowCheck.setText("Sekvu clipboard");
        jFollowCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFollowCheckActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jFollowCheck, gridBagConstraints);

        jPanel1.setLayout(new java.awt.GridLayout(2, 1));

        jPanel1.setBorder(new javax.swing.border.TitledBorder("De:"));
        jLang1Radio.setText("Esperanto");
        jLang1Radio.setEnabled(false);
        jLang1Radio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jLang1RadioActionPerformed(evt);
            }
        });

        jPanel1.add(jLang1Radio);

        jLang2Radio.setText("lingvo 2");
        jLang2Radio.setEnabled(false);
        jLang2Radio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jLang2RadioActionPerformed(evt);
            }
        });

        jPanel1.add(jLang2Radio);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        getContentPane().add(jPanel1, gridBagConstraints);

        jScrollPane.setComponentPopupMenu(jPopupMenu);
        jScrollPane.setPreferredSize(new java.awt.Dimension(453, 103));
        mainTable.setComponentPopupMenu(jPopupMenu);
        mainTable.setModel(new de.berlios.jvortaro.TableModel());
        mainTable.setInheritsPopupMenu(true);
        mainTable.setMinimumSize(new java.awt.Dimension(30, 164));
        jScrollPane.setViewportView(mainTable);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        getContentPane().add(jScrollPane, gridBagConstraints);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setMinimumSize(new java.awt.Dimension(20, 20));
        jPanel2.setPreferredSize(new java.awt.Dimension(20, 20));
        jPanel2.add(jStatusBarLabel, java.awt.BorderLayout.CENTER);

        jPanel2.add(jStatusBarProgress, java.awt.BorderLayout.EAST);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jEditButton.setText("Editu");
        jEditButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jEditButtonActionPerformed(evt);
            }
        });

        jPanel3.add(jEditButton);

        jButtonAbout.setText("Koncerne...");
        jButtonAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAboutActionPerformed(evt);
            }
        });

        jPanel3.add(jButtonAbout);

        jButtonExit.setText("Eliru...");
        jButtonExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExitActionPerformed(evt);
            }
        });

        jPanel3.add(jButtonExit);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel3, gridBagConstraints);

        pack();
    }
    // </editor-fold>//GEN-END:initComponents

    private void jInsertMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jInsertMenuItemActionPerformed
        ListSelectionModel selectionModel = mainTable.getSelectionModel();
        int max = selectionModel.getMaxSelectionIndex();

        TableModel model = (TableModel) mainTable.getModel();
        model.insertRow(++max, new TableRow());
           
    }//GEN-LAST:event_jInsertMenuItemActionPerformed

    private void jRemoveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRemoveMenuItemActionPerformed

        ListSelectionModel selectionModel = mainTable.getSelectionModel();
        int max = selectionModel.getMaxSelectionIndex();
        int min = selectionModel.getMinSelectionIndex();
        if (min != -1){
            System.out.println("Selecion ["+min+","+max+"]");
            TableModel model = (TableModel) mainTable.getModel();
            for (int i = min; i <= max; i++){
                
                System.out.println("Riga "+i+" id:"+model.getValueAt(i).getId());
                model.removeRow(min);
            }
        }

    }//GEN-LAST:event_jRemoveMenuItemActionPerformed

    private void jEditButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jEditButtonActionPerformed

        ((TableModel)mainTable.getModel()).setEditable(jEditButton.isSelected());
        JPopupMenu newMenu;
        if (jEditButton.isSelected())
            newMenu = menu;
        else
            newMenu = null;

        jScrollPane.setComponentPopupMenu(newMenu);
        mainTable.setComponentPopupMenu(newMenu);
        
    }//GEN-LAST:event_jEditButtonActionPerformed

    private void jButtonExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExitActionPerformed

        askBeforeExit();
    }//GEN-LAST:event_jButtonExitActionPerformed

    private void jButtonAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAboutActionPerformed

        About about = new About(this,true);
        about.updateDictionaryList();
        about.setVisible(true);
    }//GEN-LAST:event_jButtonAboutActionPerformed
            
    private void jTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldKeyReleased
        
     
        if (!jLang1Radio.isSelected())
            return;
        int carret = jTextField.getCaretPosition();
        String text = jTextField.getText();
        String text2 = replaceChars(text);
        
        if (!text.equalsIgnoreCase(text2)){
            jTextField.setText(text2);
            int a = text2.length();
            carret = text2.length()<carret?text2.length():carret;
            jTextField.setCaretPosition(carret);
        }
    }//GEN-LAST:event_jTextFieldKeyReleased
    
    private void jFollowCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFollowCheckActionPerformed
        if (jFollowCheck.isSelected())
            timer.start();
        else
            timer.stop();
        
    }//GEN-LAST:event_jFollowCheckActionPerformed
        
    private void jImportMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jImportMenuItemActionPerformed

        
        JFileChooser fc = new JFileChooser();
        fc.setMultiSelectionEnabled(true);
        int ret = fc.showOpenDialog(this);
        
        if (ret == JFileChooser.APPROVE_OPTION) {
            File[] files = fc.getSelectedFiles();
            //This is where a real application would open the file.
            for (int i=0; i<files.length; i++){
                File file = files[i];
                System.err.println("Opening file "+file.getName());

                String name = file.getName();
                String[] names = name.split("\\.");
                if (names.length != 2)
                    return;

                Import imp = new Import();
                ArrayList<TableRow> dati = imp.importFile(file);

                String[] langs = names[0].split("_");
            try{
                database.importLanguages(dati, langs[0], langs[1], true);
            }catch(Exception e){
                Common.showError(e);
            }
        }

            
        } else
            System.err.println("Cancel");
        
    }//GEN-LAST:event_jImportMenuItemActionPerformed
    
    private void jSearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSearchButtonActionPerformed
        search();
    }//GEN-LAST:event_jSearchButtonActionPerformed
    
    private void jLang2RadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jLang2RadioActionPerformed
        
        String lang1 = jLang1Radio.getText();
        String lang2 = jLang2Radio.getText();

        changeColumnHeaders(lang2, lang1);
        resetTableAndTextField();
        database.changeLanguage(lang2,lang1);
        jScrollPane.repaint();
    }//GEN-LAST:event_jLang2RadioActionPerformed
    
    private void jLang1RadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jLang1RadioActionPerformed
        
        final String lang1 = jLang1Radio.getText();
        final String lang2 = jLang2Radio.getText();
        
        changeColumnHeaders(lang1, lang2);

        final SwingWorker swingWorker = new SwingWorker(){
          public Object construct()  {
              database.changeLanguage(lang1,lang2);
              return null;
          }
          public void finished(){
            StatusBarManager statusBar = new StatusBarManager(Main.main);
            statusBar.setMessage("Preta");
            statusBar.setPosition(0);
            statusBar.setStatus(StatusBarManager.Status.NOTHING);
            updateStatusBar(statusBar); 
          }
        };
        swingWorker.start();
        resetTableAndTextField();
        jScrollPane.repaint();
        
    }//GEN-LAST:event_jLang1RadioActionPerformed
    
    private void jLanguagesComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jLanguagesComboActionPerformed
        
        String langSelected = (String)jLanguagesCombo.getSelectedItem();
        if (langSelected.equals(""))
            return;
        
        StatusBarManager statusBar = new StatusBarManager(this);
        statusBar.setMessage("\u015Dan\u011Di datenbanko");
        statusBar.setPosition(-1);
        statusBar.setStatus(StatusBarManager.Status.WAIT);
        updateStatusBar(statusBar);
        
        jLanguagesCombo.setEnabled(false);
        jLang1Radio.setEnabled(false);
        jLang2Radio.setEnabled(false);
        
        LanguageInformation lang = null;
        for (LanguageInformation l: languages){
            if (l.getName().equalsIgnoreCase(langSelected)){
                lang = l;
                break;
            }
        }
        
        if (lang == null)
            return;
        
        String lang1 = "Esperanto";
        String lang2 = lang.getName();
        if (lang.isFromEsperanto()){
            jLang1Radio.setSelected(true);
        } else
            jLang2Radio.setSelected(true);
        
        jLang1Radio.setText(lang1);
        jLang2Radio.setText(lang2);
        jLang1RadioActionPerformed(null);
        
        jLanguagesCombo.setEnabled(true);
        if (lang.isFromEsperanto())
            jLang1Radio.setEnabled(true);
        
        if (lang.isFromLanguage()) 
            jLang2Radio.setEnabled(true);
        
    }//GEN-LAST:event_jLanguagesComboActionPerformed
    
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
        askBeforeExit();
    }//GEN-LAST:event_formWindowClosing
    
    private void askBeforeExit (){
        
        try {
            String langSelected = (String)jLanguagesCombo.getSelectedItem();
            prop.setProperty("selected", langSelected);
            service.saveProperties(prop);
        } catch(Exception e){
            Common.showError(e);
        }
        
        if (JOptionPane.showConfirmDialog(this,"\u0108u vi volas eliri?","Eliri",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            
            if (database != null)
                database.close();
            System.exit(0);
        }
    }
    
    /**
     * update gui with available languages
     */
    private ComboBoxModel getLanguagesAvailable() throws Exception{
        
        languages = database.getLanguagesInformation();
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        
        ArrayList<String> names = new ArrayList<String>();
        for(LanguageInformation lang: languages){
            names.add(lang.getName());
        }
        
        Collections.sort(names);
        if (webstart)
            model.addElement("");
        for(String n: names){
           model.addElement(n);
        }
        return model;
    }
    
    /**
     * Clear field changing language
     */
    private void  resetTableAndTextField(){
        TableModel model = (TableModel)mainTable.getModel();
        model.clear();
        jTextField.setText("");
    }

    /**
     * Metodo richiamato per gestire la ricerca da clipboard
     */
    private void followClipboard(){
       String result = service.getClipboard();
       if (result == null) {
           jFollowCheck.setSelected(false);
           timer.stop();   
           return;
       }
       if (lastClipboard == null || !lastClipboard.equalsIgnoreCase(result)){
        lastClipboard = result;
        jTextField.setText(result);
        setVisible(true);
        search(); 
       }
    }
    
    /**
     * search for a word in database
     */
    private void search(){
        try {
            String lang1 = jLang1Radio.getText();
            String lang2 = jLang2Radio.getText();
            String table;
            Dictionary.Direction direction;
            if (jLang1Radio.isSelected()){
                table = lang1+"_"+lang2;
                direction = Dictionary.Direction.FROM_ESPERANTO;
            }else{
                table = lang2+"_"+lang1;
                direction = Dictionary.Direction.TO_ESPERANTO;
            }
            TableModel model = (TableModel) mainTable.getModel();
            model.clear();
            
            ArrayList<TableRow> data = database.search(jTextField.getText());
            
            model.addRows(data);
            model.setDictionary(database.getDictionary());
            
            model.setDirection(direction);
            
            mainTable.setModel(model);
            
        }catch(Exception e){
           Common.showError(e);
        }
    }
    
    /**
     *  Change chars to Esperanto in cell editor
     * */
    private void jEditorFieldKeyReleased(java.awt.event.KeyEvent evt) {

        JTextField field = (JTextField) evt.getComponent();
        
        int carret = field.getCaretPosition();
        String text = field.getText();
        String text2 = replaceChars(text);
        
        if (!text.equalsIgnoreCase(text2)){
            field.setText(text2);
            int a = text2.length();
            carret = text2.length()<carret?text2.length():carret;
            field.setCaretPosition(carret);
        }
    }   
          
    /**
     * Change column headers and editor
     */
    private void changeColumnHeaders(String lang1, String lang2){
        
        TableColumnModel columnModel = mainTable.getColumnModel();
        
        columnModel.getColumn(0).setHeaderValue(lang1);
        columnModel.getColumn(1).setHeaderValue(lang2);

        int first = 0;
        int second = 1;
        if (!lang1.equalsIgnoreCase("esperanto")){
            first = 1;
            second = 0;
        }
        columnModel.getColumn(first).setCellEditor(new DefaultCellEditor(tableEditor));
        columnModel.getColumn(second).setCellEditor(new DefaultCellEditor(new JTextField()));
        
        mainTable.setColumnModel(columnModel);

    }
    
    /** Replace combination of chars with esperanto one */
    private String replaceChars(String text){
        
        text = text.replaceAll("s^","sx");
        text = text.replaceAll("u^","ux");
        text = text.replaceAll("g^","gx");
        text = text.replaceAll("j^","jx");
        text = text.replaceAll("c^","cx");
        text = text.replaceAll("h^","hx");
        
        text = text.replaceAll("hh","hx");
        text = text.replaceAll("uh","ux");
        text = text.replaceAll("w","ux");
        text = text.replaceAll("gh","gx");
        text = text.replaceAll("jh","jx");
        text = text.replaceAll("ch","cx");
        text = text.replaceAll("sh","sx");
        
        text = text.replaceAll("sx","\u015D");
        text = text.replaceAll("ux","\u016D");
        text = text.replaceAll("gx","\u011D");
        text = text.replaceAll("jx","\u0135");
        text = text.replaceAll("cx","\u0109");
        text = text.replaceAll("hx","\u0125");
        
        return text;
    }
    
     public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) { }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
    
    public Database getDatabase(){
        return database;
    } 
     
    public Service getService(){
        return service;
    } 
    
    /**
     * Update status bar
     * */
    public void updateStatusBar(StatusBarManager manager){
        jStatusBarLabel.setText(manager.getMessage());
        float i = manager.getPosition();
        if (i < 0)
            jStatusBarProgress.setIndeterminate(true);
        else {
            
            jStatusBarProgress.setIndeterminate(false);
            jStatusBarProgress.setMaximum(100);
            jStatusBarProgress.setValue((int) (i*100));
            jStatusBarProgress.repaint();
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup;
    private javax.swing.JButton jButtonAbout;
    private javax.swing.JButton jButtonExit;
    private javax.swing.JToggleButton jEditButton;
    private javax.swing.JCheckBox jFollowCheck;
    private javax.swing.JMenuItem jImportMenuItem;
    private javax.swing.JMenuItem jInsertMenuItem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JRadioButton jLang1Radio;
    private javax.swing.JRadioButton jLang2Radio;
    private javax.swing.JComboBox jLanguagesCombo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu;
    private javax.swing.JMenuItem jRemoveMenuItem;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JButton jSearchButton;
    private javax.swing.JLabel jStatusBarLabel;
    private javax.swing.JProgressBar jStatusBarProgress;
    private javax.swing.JTextField jTextField;
    private javax.swing.JTable mainTable;
    // End of variables declaration//GEN-END:variables
    
}
