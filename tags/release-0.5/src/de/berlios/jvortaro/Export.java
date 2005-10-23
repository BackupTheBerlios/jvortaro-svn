/*
 * Export.java
 *
 * Created on 24 settembre 2005, 12.42
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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;
import java.util.zip.GZIPInputStream;
import de.berlios.jvortaro.bean.Dictionary;
import de.berlios.jvortaro.standalone.Service;

/**
 * 
 *
 * @author enrico
 */
public class Export {
    
    public  Export() {
        String directory = System.getProperty("jvortaro.directory");
        String exportDirectory = System.getProperty("jvortaro.exportDirectory","dist/jnlp/");
        HashMap<String,Dictionary> dicts = new HashMap<String,Dictionary>();
        try{
            if (directory == null){
                Service service = new Service();
                directory = service.getUserDirectory();
            }
            File dir = new File(directory);
            String[] files = dir.list();
            ArrayList<String> languageName = new ArrayList<String>();
            Common c = new Common();
            String d ="";
            for (String f: files){
                if (f.endsWith(".vortaro")){
                    File file = new File(directory+"/"+f);
                    languageName.add(f.substring(0, f.length()-8));
                    System.out.println("Reading "+f);
                    Dictionary dict = c.readXmlDatabase(new GZIPInputStream(new FileInputStream(file)));
                    long length = file.length();
                    d += String.format("<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%tD</td></tr>",
                            f,dict.getFromLang1().size(),dict.getFromLang2().size(), length,dict.getDate()); 
                    dict.setFromLang1(null);
                    dict.setFromLang2(null);
                    dicts.put(dict.getLang2Name(), dict);
                }
            }
            writePropertiesFile(languageName, dicts, exportDirectory);
            System.out.println(d);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void writeJNLPDescriptor(String templateName, String descriptorName, ArrayList<String> languageName){
        try {
            FileInputStream input = new FileInputStream(new File(templateName));
            FileOutputStream output = new FileOutputStream(new File(descriptorName));

            String template = "";
            byte[] buf = new byte[1024];
            while ( input.read(buf) > 0 ){
                String temp = new String(buf);
                template += temp;
                Arrays.fill(buf, (byte)0);
            }
            input.close();
            String resource = "";
            for (String lang : languageName){
                resource += "    <jar href=\""+lang+".jar\" download=\"lazy\"/>\n";
            }
            template = template.replaceAll("<template/>",resource);
            System.out.println(template);
            output.write(template.getBytes("UTF-8"));
            output.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void writePropertiesFile(ArrayList<String> languageName, HashMap<String,Dictionary> dicts, String exportDirectory){
            Properties properties = new Properties();
            try{

                String nameList = "";
                for (String name: languageName){
                    nameList += name+ " ";
                    properties.setProperty(name+".to","true");
                    properties.setProperty(name+".from","true");
                    properties.setProperty(name+".date", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(dicts.get(name).getDate()));
                }
                properties.setProperty("languages",nameList);
                properties.store(new FileOutputStream(exportDirectory+"/languages.properties"),"Languages data");
                System.out.println("Data written in  "+exportDirectory+"/languages.properties");
            }catch(IOException e){
                e.printStackTrace();
            }
        
    }
    
    public static void main(String[] args){
        Export export = new Export();
    }
}
