/*
 * Service.java
 *
 * Created on 21 settembre 2005, 18.30
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

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 *
 * @author enrico
 */
public class Service implements de.berlios.jvortaro.interfaces.Service {
    
    public String getUserDirectory(){
        String userDir = System.getProperty("user.home");
        String myDir = userDir+"/.jVortaro";
        File file = new File(myDir);
        if (!file.exists()) {
            JOptionPane.showMessageDialog(null,"Creating directory "+file.getAbsolutePath()+".",
                    "First time execution",
                    JOptionPane.INFORMATION_MESSAGE);
            file.mkdir();
        }
        return myDir;
    }

    public String getClipboard() {
        
        String result = null;
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        
        Transferable contents = clipboard.getContents(null);
        boolean hasTransferableText = (contents != null) &&
                contents.isDataFlavorSupported(DataFlavor.stringFlavor);
        if ( hasTransferableText ) {
            try {
                result = (String)contents.getTransferData(DataFlavor.stringFlavor);
                return result;
            } catch (UnsupportedFlavorException ex){
                
                System.out.println(ex);
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }   
        return result;
    }

    public Properties loadProperties() throws Exception{
        Properties properties = new Properties();
        try{
            InputStream is = new FileInputStream(getUserDirectory()+"/jVortaro.properties");
            properties.load(is);
        }catch(Exception e){
            e.printStackTrace();
        }
        return properties;
    }

    public void saveProperties(Properties prop) throws Exception {
            prop.store(new FileOutputStream(getUserDirectory()+"/jVortaro.properties"),"Default configuration");
    }
    
}
