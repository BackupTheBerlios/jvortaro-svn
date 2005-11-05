/*
 * Database.java
 *
 * Created on 19 settembre 2005, 19.50
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
import de.berlios.jvortaro.bean.Dictionary;
import java.util.ArrayList;
import de.berlios.jvortaro.bean.LanguageInformation;
import de.berlios.jvortaro.bean.TableRow;

/**
 *
 * @author enrico
 */
public interface Database {
  /** return list of available languages **/
  ArrayList<LanguageInformation> getLanguagesAvailable() throws Exception;
  
  /** search for a word in database */
  ArrayList<TableRow> search(String valore) throws Exception;
  
  /** Change current language */
  void changeLanguage(String from, String to);
  
  /** Close database connection (if any) */
  void close();
  
  /** import items */
  void importLanguages (ArrayList<TableRow> dati,String langForm, String langTo, boolean isChached) throws Exception;
  
  /** database */
  Dictionary getDictionary();
}
