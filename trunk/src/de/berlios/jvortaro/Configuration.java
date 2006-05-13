/*
 * Configuration.java
 *
 * Created on 13 maggio 2006, 16.13
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

import de.berlios.jvortaro.interfaces.Database;
import de.berlios.jvortaro.interfaces.Service;
import java.util.Properties;

/**
 *
 * @author enrico
 */
public class Configuration {
    
    static private Database database = null;
    static private Service service = null;
    static private Configuration configuration = null;
    private Properties properties;
    
    public Configuration(Database database, Service service) throws Exception{
        this.database = database;
        this.service = service;
        properties = service.loadProperties();
    }
    
    synchronized public static Configuration getInstance(Database database, Service service) throws Exception {
        
        if (configuration == null)
            configuration = new Configuration(database, service);
        return configuration;
    }
    
    synchronized  public static Configuration getInstance(){
        if (configuration == null)
            throw new Error("Wrong init sequence");
        return configuration;
    }
    
    public String getProperty(String name){
        return properties.getProperty(name);
    }
    
    public void setProperty(String name, String value){
        properties.setProperty(name, value);
    }
    
    public void save() throws Exception {
        service.saveProperties(properties);
    }
}

