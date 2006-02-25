/*
 * Service.java
 *
 * Created on 21 settembre 2005, 21.22
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

package de.berlios.jvortaro.jnlp;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.net.URL;
import java.util.Properties;
import javax.jnlp.BasicService;
import javax.jnlp.ClipboardService;
import javax.jnlp.FileContents;
import javax.jnlp.PersistenceService;
import javax.jnlp.ServiceManager;
import javax.jnlp.UnavailableServiceException;
import de.berlios.jvortaro.Common;

/**
 *
 * @author enrico
 */
public class Service implements de.berlios.jvortaro.interfaces.Service {
    
    public String getClipboard() {
        
        ClipboardService cs;

        try {
            cs = (ClipboardService)ServiceManager.lookup("javax.jnlp.ClipboardService");
        } catch (UnavailableServiceException e) {
            cs = null;
        }

        if (cs != null) {
            
            Transferable tr = cs.getContents();
            if (tr.isDataFlavorSupported(DataFlavor.stringFlavor)) {
               try {
                    String s = (String)tr.getTransferData(DataFlavor.stringFlavor);
                    System.out.println("Clipboard contents: " + s);
                    return s;
                } catch (Exception e) {
                    Common.showError(e);
                }
            } 
        }
        return null;
    }


    public java.util.Properties loadProperties() throws Exception {
        Properties properties = new Properties();
        
        try { 
            PersistenceService ps = (PersistenceService)ServiceManager.lookup("javax.jnlp.PersistenceService"); 
            BasicService bs = (BasicService)ServiceManager.lookup("javax.jnlp.BasicService"); 

            URL codebase = bs.getCodeBase(); 

            String[] names = new String[0];
            try {
                names = ps.getNames(codebase);
            } catch( java.lang.NullPointerException e){
                System.out.println("Stupid webstart error: there aren't muffins");
            };
            
            if (!Common.find(names, "jVortaro.properties"))
                return properties;
            URL url = new URL(codebase+"jVortaro.properties");
            FileContents fc = ps.get(url);
            if (fc == null)
                return properties;
            properties.load(fc.getInputStream());
        } catch (Exception e) { 
            Common.showError(e);
        } 
        return properties;
    }

    
    public void saveProperties(java.util.Properties properties) throws Exception {
        try { 
            PersistenceService ps = (PersistenceService)ServiceManager.lookup("javax.jnlp.PersistenceService"); 
            BasicService bs = (BasicService)ServiceManager.lookup("javax.jnlp.BasicService"); 

            URL codebase = bs.getCodeBase(); 
            URL url = new URL(codebase+"jVortaro.properties");
            
            if (!Common.find(ps.getNames(codebase),"jVortaro.properties")){
                System.out.println("Creo muffin "+url);
                ps.create(url, 20*1024);
            }
            
            FileContents fc = ps.get(url);
            properties.store(fc.getOutputStream(true), "Default configuration");
            
        } catch (Exception e) { 
            Common.showError(e);
        } 
   }
    
   /** 
    * in JNLP context, properties file is always up2date
    */
   public boolean isPropertiesFileOld() throws Exception {
       return false;
   }
   
   public void updatePropertiesFiles() throws Exception{
       
   }
}