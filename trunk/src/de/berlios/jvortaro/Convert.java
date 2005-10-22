/*
 * Convert.java
 *
 * Created on 15 ottobre 2005, 14.50
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
import java.util.ArrayList;
import java.util.Date;
import de.berlios.jvortaro.bean.Dictionary;
import de.berlios.jvortaro.bean.TableRow;

/**
 *
 * @author enrico
 */
public class Convert {
    
    /** Creates a new instance of Convert */
    public Convert(String dirName) {
        File dir = new File(dirName);
        String[] dicts = dir.list();

        for (String dict: dicts){
                String[] names = dict.split("\\.");
                if (names.length != 2)
                    continue;

                String[] langs = names[0].split("_");
                if (langs[0].equalsIgnoreCase("esperanto")){
                    
                    String lang = langs[1];
                    System.out.println("Lang "+lang);
                    
                    Import imp = new Import();
                    String name1 = dirName+"/"+"Esperanto_"+lang+".wb";
                    String name2 = dirName+"/"+lang+"_Esperanto.wb";
                    ArrayList<TableRow> datiLang1 = imp.importFile(new File(name1));
                    ArrayList<TableRow> datiLang2 = imp.importFile(new File(name2));
                    Dictionary dictionary = new Dictionary();
                    dictionary.setFromLang1(datiLang1);
                    dictionary.setFromLang2(datiLang2);
                    dictionary.setLang2Name(lang);
                    dictionary.setDate(new Date(System.currentTimeMillis()));
                    Common common = new Common();
                    String vortaroName = dirName+"/"+lang+".vortaro";
                    common.writeDictionary(dictionary, new File(vortaroName));

                }    
        }
    }
            
    
     public static void main(String[] args){
         
       String dir = System.getProperty("jvortaro.importDatabase","/tmp/lists");
       System.out.println(dir);
       Convert export = new Convert(dir);
    }
    
}
