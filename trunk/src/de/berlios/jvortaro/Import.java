/*
 * Import.java
 *
 * Created on 21 settembre 2005, 18.46
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
 *
 */

package de.berlios.jvortaro;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import de.berlios.jvortaro.bean.TableRow;

/**
 *
 * @author enrico
 */
public class Import {
    
   /**
    * Legge una coppia di valori dal file di import di la-vortaro.net
    */
   private TableRow leggiCoppia(FileInputStream fi) throws Exception{
        byte[] buff1 = new byte[31];
        int i = fi.read(buff1);
        if (i < 0)
            return null;
        byte[] buff2 = new byte[53];
        i = fi.read(buff2);
        if (i<0)
            return null;
        String l1 = new String(buff1,"iso-8859-3");
        int idx = l1.indexOf('\0');
        if (idx != -1)
            l1 = l1.substring(0, idx);
        String l2 = new String(buff2,"iso-8859-3");
        idx = l2.indexOf('\0');
        if (idx != -1)
            l2 = l2.substring(0, idx);
        //System.out.println(l1+" "+l2);
        TableRow result = new TableRow();
        result.setLang1(l1);
        result.setLang2(l2);
        return result;
    }

    /**
     *Legge un file di tipo vortaro.net
     */
    public  ArrayList<TableRow>  importFile(File file){
        ArrayList<TableRow> dati = new ArrayList<TableRow>();
        try {
            FileInputStream fi = new FileInputStream(file);
            TableRow coppia = leggiCoppia(fi);
            while( coppia != null) {
                dati.add(coppia);
                coppia = leggiCoppia(fi);
            }
         
        }catch(Exception e){
            e.printStackTrace();
        }
        return dati;
    }
    

}
