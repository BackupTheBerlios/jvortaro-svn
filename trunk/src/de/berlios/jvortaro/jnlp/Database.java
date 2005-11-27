/*
 * Database.java
 *
 * Created on 23 settembre 2005, 22.33
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
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Properties;
import java.util.zip.GZIPInputStream;
import javax.jnlp.BasicService;
import javax.jnlp.FileContents;
import javax.jnlp.PersistenceService;
import javax.jnlp.ServiceManager;
import javax.swing.SwingUtilities;
import de.berlios.jvortaro.Common;
import de.berlios.jvortaro.Main;
import de.berlios.jvortaro.StatusBarManager;
import de.berlios.jvortaro.bean.Dictionary;
import de.berlios.jvortaro.bean.LanguageInformation;
import de.berlios.jvortaro.bean.TableRow;
import java.util.Date;

/**
 *
 * @author enrico
 */
public class Database implements de.berlios.jvortaro.interfaces.Database {
    
    private String langTo = null;
    private String langFrom = null;
    private Dictionary dictionary;
    ArrayList<TableRow> data = null;
    
    public void changeLanguage(String from, String to) {
        
        /** Check if change is trivial **/
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
        String database = langTo.equalsIgnoreCase("esperanto")?langFrom:langTo;
        
        try {
            BasicService bs = (BasicService)ServiceManager.lookup("javax.jnlp.BasicService");
            PersistenceService ps = (PersistenceService)ServiceManager.lookup("javax.jnlp.PersistenceService");
            System.out.println("Persisten service: "+ps);
            URL codeBase = bs.getCodeBase();
            System.out.println("Codebase "+codeBase);
            
            URL url = new URL(codeBase+database+".vortaro");
            System.out.println("Url "+url);
            
            String[] names = new String[0];
            try {
                names = ps.getNames(codeBase);
            } catch( java.lang.NullPointerException e){
                System.out.println("Stupid webstart error: there aren't muffins");
            };
            
            System.out.println("Read "+url);
            
            boolean download = true;
            boolean nuovo = true;
            Properties properties = Main.main.prop;
            
            if (Common.find(names,  database+".vortaro") ) {
                // dict exists locally
                nuovo = false;
                FileContents fc = ps.get(url);
                Common c = new Common();
                dictionary = c.readXmlDatabase(new GZIPInputStream(fc.getInputStream()));
                
                System.out.println("Local copy date "+dictionary.getDate());
                ArrayList<LanguageInformation> li = getLanguagesInformation();
                
                LanguageInformation language = null;
                for (LanguageInformation l: li){
                    if (l.getName().equalsIgnoreCase(database)) {
                        language = l;
                        break;
                    }
                }
                if (language == null){
                    System.out.println("Database not found?");
                    return;
                }
                
                System.out.println("Remote copy "+language.getLastChangeRemote());
                if (dictionary.getDate().compareTo(language.getLastChangeRemote()) >=0){
                    System.out.println("There is a copy and it's up-to-date");
                    download = false;
                }
            }
            
            if (download){
                // dict doesn't exist  locallay
                System.out.println("Downloading a new copy");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                int length = connection.getContentLength();
                if (!nuovo)
                    ps.delete(url);
                ps.create(url, length);
                ps.setTag(url, PersistenceService.DIRTY);
                
                byte[] buffer = new byte[length];
                int  pos = 0;
                
                StatusBarManager statusBar = new StatusBarManager(Main.main);
                statusBar.setMessage("Downloading "+database);
                statusBar.setPosition(pos);
                statusBar.setStatus(StatusBarManager.Status.DOWNLOAD);
                SwingUtilities.invokeAndWait(statusBar);
                
                while((pos += inputStream.read(buffer, pos, buffer.length - pos)) != buffer.length) {
                    if (pos % 1024 == 0) {

                        statusBar.setPosition((float)pos/buffer.length);
                        SwingUtilities.invokeAndWait(statusBar);
                    }
                    // giro tondo (looping)
                }
                statusBar.setPosition(1);
                SwingUtilities.invokeAndWait(statusBar);
                inputStream.close();
                System.out.println("Downloaded");
                
                FileContents fc = ps.get(url);
                OutputStream os = fc.getOutputStream(false);
                os.write(buffer);
                os.close();
                Common c = new Common();
                dictionary = c.readXmlDatabase(new GZIPInputStream(fc.getInputStream()));
                properties.setProperty(database+".data", Long.toString(dictionary.getDate().getTime()));
            }
            
            FileContents fc = ps.get(url);
            
            
            if (langFrom.equalsIgnoreCase("esperanto"))
                data = dictionary.getFromLang1();
            else
                data = dictionary.getFromLang2();
            
        } catch(Exception e){
            e.printStackTrace();
        }
        
        
    }
    
    public void close() {
        
    }
    
    public ArrayList<LanguageInformation> getLanguagesInformation() throws Exception {
        ArrayList<LanguageInformation> result = new ArrayList<LanguageInformation>();
        Properties properties = new Properties();
        try{
            properties.load(getClass().getResourceAsStream("/languages.properties"));
        }catch(IOException e){
            Common.showError(e);
        }
        String langs = properties.getProperty("languages");
        if (langs != null && langs.length() > 0){
            String[] lang = langs.split(" ");
            for (int i = 0;  i<lang.length; i++){
                LanguageInformation info = new LanguageInformation();
                info.setName(lang[i]);
                
                if (properties.getProperty(lang[i]+".from") != null)
                    info.setFromLanguage(true);
                if (properties.getProperty(lang[i]+".to") != null)
                    info.setFromEsperanto(true);
                String sDate = properties.getProperty(lang[i]+".date");
                if (sDate != null){
                    info.setLastChangeRemote(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").parse(sDate));
                }
                if (info.isFromEsperanto() || info.isFromLanguage())
                    result.add(info);
            }
        }
        return result;
    }
    
    public void importLanguages(java.util.ArrayList<de.berlios.jvortaro.bean.TableRow> dati, java.lang.String langForm, java.lang.String langTo, boolean isCached) throws Exception {
    }
    
    public ArrayList<TableRow> search(String filter) throws Exception {
        ArrayList<TableRow> result = new ArrayList<TableRow>();
        if (langTo == null || langFrom == null)
            return result;
        String table = langFrom+"_"+langTo;
        Common common = new Common();
        result = common.search(data,filter);
        return result;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }
    
}
