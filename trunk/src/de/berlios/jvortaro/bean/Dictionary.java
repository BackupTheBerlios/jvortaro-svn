/*
 * Dictionary.java
 *
 * Created on 15 ottobre 2005, 8.17
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

import de.berlios.jvortaro.Common;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Store information about dictionary in both direction.
 * Language -> Esperanto and Esperanto -> Language
 * @author enrico
 */
public class Dictionary {
    
    public enum Direction {FROM_ESPERANTO, TO_ESPERANTO, UNSPECIFIED};
    
    private String lang2Name;
    private ArrayList<TableRow>  fromLang1;
    private ArrayList<TableRow>  fromLang2;
    private ArrayList<DataChange> changes = new ArrayList<DataChange>();
    private Date date;
    private long maxId;
    private String credits;
    
    /*************** lang2Name ***************/
    public void setLang2Name(String name){
        this.lang2Name = name;
    }
    
    public String getLang2Name(){
        return lang2Name;
    }
    
    /*************** lang2Name ***************/
    public void setFromLang1(ArrayList<TableRow> fromLang1){
        this.fromLang1 = fromLang1;
    }
    
    public ArrayList<TableRow> getFromLang1(){
        return fromLang1;
    }
    
    /*************** lang2Name ***************/
    public void setFromLang2(ArrayList<TableRow> fromLang2){
        this.fromLang2 = fromLang2;
    }
    
    public ArrayList<TableRow> getFromLang2(){
        return fromLang2;
    }
    
    /*************** date ***************/
    public void setDate(Date date){
        this.date = date;
    }
    
    public Date getDate(){
        return date;
    }
    
    /*************** maxId ***************/
    public void setMaxId(long maxId){
        this.maxId = maxId;
    }
    
    public long getMaxId(){
        return maxId;
    }
    
    /*************** credits ***************/
    public void setCredits(String credits){
        this.credits = credits;
    }
    
    public String getCredits(){
        return credits;
    }
    
    
   public TableRow updateRow(Direction direction, TableRow newRow, TableRow oldRow){
        if (direction == Direction.UNSPECIFIED){
            System.err.println("Direction unspecified ");
            return null;
        }

        ArrayList<TableRow> dict;
         if (direction == Direction.FROM_ESPERANTO)
             dict = fromLang1;
         else
             dict = fromLang2;

        DataChange dataChange = new DataChange();
        dataChange.setDirection(direction);
        
        if (newRow == null){ // remove
             if (!dict.remove(oldRow))
                System.err.println("Removing not existent item");
             else {
                 dataChange.setRow(oldRow.clone());
                 dataChange.setState(DataChange.State.DELETE);
             }
        } else { 
             int index;
             if (oldRow != null){ // update
                 index = dict.indexOf(oldRow);
                 if (index < 0){
                     System.err.println("Removing not existent item");
                     return null;
                 } else {
                     dataChange.setRow(oldRow.clone());
                     dataChange.setState(DataChange.State.DELETE);
                   }
              dict.set(index,newRow);
             } else { // insert
                dict.add(newRow);
                newRow.setId(-1);
             }
             if (dataChange.getRow() != null)
                 changes.add(dataChange);
             
             dataChange = new DataChange();
             dataChange.setRow(newRow.clone());
             dataChange.setState(DataChange.State.INSERT);
             changes.add(dataChange);
             
             Common common = new Common();
             common.sort(dict);
        }
        return null;
    }
   
   public void normailizaChanges(){
       
   }
}
