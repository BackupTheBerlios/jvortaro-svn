/*
 * Database.java
 *
 * Created on 19 settembre 2005, 21.00
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

package de.berlios.jvortaro.standalone;


import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;
import de.berlios.jvortaro.Common;
import de.berlios.jvortaro.bean.Dictionary;
import de.berlios.jvortaro.bean.LanguageInformation;
import de.berlios.jvortaro.bean.TableRow;


/**
 *
 * @author enrico
 */
public class Database implements de.berlios.jvortaro.interfaces.Database {
    
    private String langFrom;
    private String langTo;
    private String userDir;
    private Dictionary dictionary;
    
    ArrayList<TableRow> data = null;
    
    public Database(){
        de.berlios.jvortaro.standalone.Service service = new de.berlios.jvortaro.standalone.Service();
        userDir = service.getUserDirectory();
    }
    
     public  ArrayList<LanguageInformation> getLanguagesInformation() throws Exception {
        
        ArrayList<LanguageInformation> result = new ArrayList<LanguageInformation>();
        File dir = new File(userDir);
        String[] files = dir.list();
        for (String f: files){
            if (f.endsWith(".vortaro")){
                LanguageInformation li = new LanguageInformation();
                li.setFromEsperanto(true);
                li.setFromLanguage(true);
                li.setName(f.substring(0, f.length()-8));
                result.add(li);
            }
        }
        return result;
    }
    
    
    public ArrayList<TableRow> search(String filter){
        
        String table = langFrom+"_"+langTo;
        Common common = new Common();
        ArrayList<TableRow> result = common.search(data,filter);
        return result;
    }
    
    public void close(){
    }
    
    public void importLanguages( ArrayList<TableRow> dati, String langFrom, String langTo, boolean isChached) throws Exception{
    }
    
    public void readDatabase(String lang){
        
    }
    
    public void changeLanguage(String from, String to) {
        
        if (to.equalsIgnoreCase(langTo) && from.equalsIgnoreCase(langFrom))
            return;
        if (to.equalsIgnoreCase(langFrom) && from.equalsIgnoreCase(langTo)){
            langTo = to;
            langFrom = from;
            if (langFrom.equalsIgnoreCase("esperanto"))
                data = dictionary.getFromLang1();
            else
                data = dictionary.getFromLang2();
            return;
        }
        langTo = to;
        langFrom = from;
        try {
            String name = from.equalsIgnoreCase("esperanto")?to:from;
            File file = new File(userDir+"/"+name+".vortaro");
            
            Common c = new Common();
            dictionary = c.readXmlDatabase(new GZIPInputStream(new FileInputStream(file)));
            if (langFrom.equalsIgnoreCase("esperanto"))
                data = dictionary.getFromLang1();
            else
                data = dictionary.getFromLang2();
            
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public Dictionary getDictionary() {
        return dictionary;
    }
    
}
