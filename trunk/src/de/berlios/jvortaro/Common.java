/*
 * Common.java
 *
 * Created on 26 settembre 2005, 19.32
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.zip.GZIPOutputStream;
import javax.swing.JOptionPane;
import de.berlios.jvortaro.bean.Dictionary;
import de.berlios.jvortaro.bean.TableRow;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;





/**
 *
 * @author enrico
 */
public class Common {
    
    /**
     * Show application error
     */
    static public  void showError(Exception e){
        JOptionPane.showMessageDialog(null,e.getMessage(),"Application error",  JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }

    /**
     * Search for an item in dictionary
     */
    public ArrayList<TableRow> search(ArrayList<TableRow> data, String filter){
        
        ArrayList<TableRow> result = new ArrayList<TableRow>();

        TableRow row = new TableRow();
        row.setLang1(filter+"%");
        boolean found = false;
        
        while (!found){
            int i = Collections.binarySearch(data,row, new CompareSearch());
            
            if (i>=0){

                found = true;
                int start = i;
                
                //Search before
                for (int h = start; h >= 0; h--){
                    TableRow tmp = data.get(h);
                    String value = tmp.getLang1();
                    int reduction = filter.length() < value.length() ? filter.length() : value.length();
                    if (!filter.equalsIgnoreCase(value.substring(0,reduction)))
                        break;
                    result.add(tmp);
                }
                //reverse collected result
                Collections.reverse(result);
                //Search after
                for (int h = start+1; h < data.size(); h++){
                    TableRow tmp = data.get(h);
                    String value = tmp.getLang1();
                    int reduction = filter.length() < value.length() ? filter.length() : value.length();
                    if (!filter.equalsIgnoreCase(value.substring(0,reduction)))
                        break;
                    result.add(tmp);
                }
            }
            
            if (!found) {
                if (filter.length()>0){
                     filter = filter.substring(0, filter.length()-1);
                     row.setLang1(filter+"%");
                }else
                   found = true;
            }
        }
        return result;
    }
    
    /**
     * Search for a string in an array. Used to find "muffins" stored in local computer
     */
    static public boolean find(String[] dati, String nome){
        boolean trovato = false;
        for (String valore : dati){
            if (valore.equalsIgnoreCase(nome)){
                trovato = true;
                break;
            }
        }
        return trovato;
    }
    
    /**
     * Read from an InputSource a XML formatted database
     * */
    public Dictionary readXmlDatabase(InputStream is){
        
        SAXHandler handler = new SAXHandler();
        try {
            XMLReader parser = XMLReaderFactory.createXMLReader();
            parser.setContentHandler(handler);
            parser.parse( new InputSource(is));
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return handler.getDictionary();
    }    
    
    /**
     * Escape data 
     */
    private String escapeXML(String data){

        String result = data.replaceAll("&","&amp;");
        result = result.replaceAll("\"","&quot;");
        result = result.replaceAll("'","&apos;");
        result = result.replaceAll("<","&lt;");
        result = result.replaceAll(">","&gt;");
        return result;
    }
    
    
    /**
     * Write one direction of dictionary
     */
    private void writeDirection(PrintStream ps, String langFrom, String langTo, ArrayList<TableRow> data){
        
        Collections.sort(data,  new ItemCompare() );
        
        ps.println(String.format("<direction from=\"%s\" to=\"%s\" >", langFrom, langTo));
        for (TableRow row:data){
            String lang1 = escapeXML(row.getLang1());
            String lang2 = escapeXML(row.getLang2());
            ps.println(String.format("<item lang1=\"%s\" lang2=\"%s\"   />", lang1, lang2));
        }
        ps.println("</direction>");
    }
    
    /**
     * Write entire dicionary (call writeDirection)
     */
    public void writeDictionary(Dictionary dict, File file){
        try{
            GZIPOutputStream os = new GZIPOutputStream(new FileOutputStream(file));
            PrintStream ps = new PrintStream(os);
            ps.println("<?xml version='1.0' encoding='utf-8'?>");
            String lang = dict.getLang2Name();

            String sDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(dict.getDate());
            
            if (lang != null)
                ps.println(String.format("<dictionary lang=\"%s\" date=\"%s\" xmlns=\"http://jVortaro.berlios.ds/ns\" "+
            "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "+
            "xsi:schemaLocation=\"http://jVortaro.berlios.ds/ns http://jVortaro.berlios.de/ns/vortaro-dict.xsd\" >",lang,sDate));
            else
                ps.println("<dictionary>");
	    

            writeDirection(ps, "esperanto", dict.getLang2Name(), dict.getFromLang1());
            writeDirection(ps, dict.getLang2Name(), "esperanto", dict.getFromLang2());
            
            ps.println("</dictionary>"); 
            ps.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void sort(ArrayList<TableRow> data){
        Collections.sort(data, new ItemCompare());
    }
    
}


class ItemCompare implements Comparator<TableRow> {
    
    public int compare(TableRow a, TableRow b){
        
        String lang1 = a.getLang1();
        String lang2 = b.getLang1();
        if (lang1 == null)
            return -1;
        if (lang2 == null)
            return 1;
        return lang1.compareToIgnoreCase(lang2);
    }
    
}
 

class CompareSearch implements Comparator<TableRow> {
    
    public int compare(TableRow a, TableRow b){
        
        String lang1 = a.getLang1();
        String lang2 = b.getLang1();
        
        if (lang1 == null)
            return -1;
        if (lang2 == null)
            return 1;
        int factor = 1;
        if (lang1.charAt(lang1.length()-1) != '%'){
            lang1 = b.getLang1();
            lang2 = a.getLang1();
            factor = -1;
        }
        //System.out.print(lang1+" "+lang2+" ");
        lang1 = lang1.substring(0, lang1.length()-1);
        int reduction = lang1.length() < lang2.length() ? lang1.length() : lang2.length();

        String temp1 = lang1;
        String temp2 = lang2.substring(0, reduction);
        //System.out.println(temp1+" "+temp2+" "+factor);
        return factor * temp1.compareToIgnoreCase(temp2);
        
    }   
}
