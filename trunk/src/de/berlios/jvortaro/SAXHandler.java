/*
 * SAXHandler.java
 *
 * Created on 15 ottobre 2005, 12.30
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


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import de.berlios.jvortaro.bean.Dictionary;
import de.berlios.jvortaro.bean.TableRow;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author enrico
 */
public class SAXHandler extends DefaultHandler {
    
    private Dictionary dictionary;
    private ArrayList<TableRow> currentDirection;
    private int id = 0;
    
    /** Creates a new instance of SAXHandler */
    public SAXHandler() {
        dictionary = new Dictionary();
    }

    public Dictionary getDictionary(){
        return dictionary;
    }
    
    public void startElement(String str, String str1, String str2, org.xml.sax.Attributes attributes) throws org.xml.sax.SAXException {
       // System.out.println("Namespace "+str+" localName "+str1+" qualifiedName "+str2);
        if (str2.equalsIgnoreCase("dictionary")){
            dictionary = new Dictionary();
            String name = attributes.getValue("lang");
            String sDate = attributes.getValue("date");
            Date date;
            try {
                date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").parse(sDate);
            }catch(Exception e){
                System.out.println("Data "+sDate+" non formattata correttamente");
                date = new Date(System.currentTimeMillis());
            }
            dictionary.setDate(date);
            System.out.println(name);
            if (name != null)
                dictionary.setLang2Name(name);
            dictionary.setFromLang1(new ArrayList<TableRow>());
            dictionary.setFromLang2(new ArrayList<TableRow>());
        }
        
        if (str2.equalsIgnoreCase("direction")){
            String from = attributes.getValue("from");
            if (from.equalsIgnoreCase("esperanto"))
                currentDirection = dictionary.getFromLang1();
            else
                currentDirection = dictionary.getFromLang2();
        }
        
        if (str2.equalsIgnoreCase("item")){
            String lang1 = attributes.getValue("lang1");
            String lang2 = attributes.getValue("lang2");
            //System.out.println("Lang1 "+lang1+" lang2 "+lang2);
            TableRow row = new TableRow();
            row.setLang1(lang1);
            row.setLang2(lang2);
            row.setId(id++);
            currentDirection.add(row);
        }
        super.startElement(str, str1, str2, attributes);
    }
    
}
