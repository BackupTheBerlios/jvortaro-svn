/*
 * TableRow.java
 *
 * Created on 19 settembre 2005, 21.43
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
public class TableRow  implements Cloneable{
    
    private String lang1;
    private String lang2;
    private Integer id;
    private Date timestamp;
    
    /********** Id **************/
    public void setId(Integer id){
        this.id = id;
    }
    
    public Integer getId(){
        return id;
    }

    /********** Name **************/
    public void setLang1(String lang1){
        this.lang1 = lang1;
    }
    
    public String getLang1(){
        return lang1;
    }
    
    /********** Name **************/
    public void setLang2(String lang2){
        this.lang2 = lang2;
    }
    
    public String getLang2(){
        return lang2;
    }

    /********** timestamp **************/
    public void setTimestamp(Date timestamp){
        this.timestamp = timestamp;
    }
    
    public Date getTimestamp(){
        return timestamp;
    }
  
    public TableRow clone(){
        Object o = null;
        try {
            o = super.clone();
        } catch(Exception e){
            e.printStackTrace();
        }
        return (TableRow)o;
    }
}
