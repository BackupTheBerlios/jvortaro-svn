/*
 * LanguageInformation.java
 *
 * Created on 19 settembre 2005, 20.52
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

package de.berlios.jvortaro.bean;

import java.util.Date;

/**
 *
 * @author enrico
 */
public class LanguageInformation {
    
    private String name;
    private boolean fromEsperanto;
    private boolean fromLanguage;
    private int fromEsperantoNumber;
    private int fromLanguageNumber;
    private int databaseSize;
    private Date lastChangeLocal;
    private Date lastChangeRemote;
    
    /********** Name **************/
    public void setName(String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
    
    /********** toEsperanto **************/
    public void setFromEsperanto(boolean fromEsperanto){
        this.fromEsperanto = fromEsperanto;
    }
    
    public boolean isFromEsperanto(){
        return fromEsperanto;
    }
    
    /********** toLanguage **************/
    public void setFromLanguage(boolean fromLanguage){
        this.fromLanguage = fromLanguage;
    }
    
    public boolean isFromLanguage(){
        return fromLanguage;
    }
    
    /********** toEsperantoNumber **************/
    public void setFromEsperantoNumber(int fromEsperantoNumber){
        this.fromEsperantoNumber = fromEsperantoNumber;
    }
    
    public int getFromEsperantoNumber(){
        return fromEsperantoNumber;
    }
    
    /********** toLanguageNumber **************/
    public void setFromoLanguageNumber(int fromLanguageNumber){
        this.fromLanguageNumber = fromLanguageNumber;
    }
    
    public int getFromLanguageNumber(){
        return fromLanguageNumber;
    }

    /********** databaseSize **************/
    public void setDatabaseSize(int databaseSize){
        this.databaseSize = databaseSize;
    }
    
    public int getDatabaseSize(){
        return databaseSize;
    }

    /********** lastChangeLocal **************/
    public void setLastChangeLocal(Date lastChangeLocal){
        this.lastChangeLocal = lastChangeLocal;
    }
    
    public Date getLastChangeLocal(){
        return lastChangeLocal;
    }
    
    /********** lastChangeRemote **************/
    public void setLastChangeRemote(Date lastChangeRemote){
        this.lastChangeRemote = lastChangeRemote;
    }
    
    public Date getLastChangeRemote(){
        return lastChangeRemote;
    }
    
}
