/*
 * Service.java
 *
 * Created on 19 settembre 2005, 19.52
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

package de.berlios.jvortaro.interfaces;

import java.util.Properties;


/**
 *
 * @author enrico
 */
public interface Service {
    
    /* Legge da clipboard informazioni */
    String getClipboard();
    void saveProperties(Properties pref) throws Exception;
    Properties loadProperties() throws Exception;
    boolean isPropertiesFileOld() throws Exception;
    void updatePropertiesFiles() throws Exception;
}
