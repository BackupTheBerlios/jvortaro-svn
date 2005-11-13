/*
 * DictChange.java
 *
 * Created on 31 ottobre 2005, 20.57
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

/**
 *
 * @author enrico
 */
public class DataChange {
    
    public enum State {DELETE, INSERT};
    public Dictionary.Direction direction;
    public TableRow row;
    private State state;
    
    /********** state *********/
    public void setState (State state){
        this.state = state;
    }
    
    public State getState(){
        return state;
    }
    
    /********** direction *********/
    public void setDirection (Dictionary.Direction state){
        this.direction = direction;
    }
    
    public Dictionary.Direction getDirection(){
        return direction;
    }
    
    /********** row *********/
    public void setRow (TableRow row){
        this.row = row;
    }
    
    public TableRow getRow(){
        return row;
    }
    
}
