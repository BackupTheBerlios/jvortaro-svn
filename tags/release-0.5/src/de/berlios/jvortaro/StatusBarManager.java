/*
 * StatusBarManager.java
 *
 * Created on 18 ottobre 2005, 18.47
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

/**
 *
 * @author enrico
 */
public class StatusBarManager implements Runnable{
    
    public enum Status{ NOTHING, SEARCH, DOWNLOAD, WAIT};
    
    Main window;
    String message;
    float position;
    Status status;
    
    /** Creates a new instance of StatusBarManager */
    public StatusBarManager(Main window) {
        this.window = window;
    }

    public void run() {
        window.updateStatusBar(this);
    }
    
    /***** Message ****/
    public void setMessage(String message){
        this.message = message;
    }
    
    public String getMessage(){
        return message;
    }
    
    /***** position ****/
    public void setPosition(float position){
        this.position = position;
    }
    
    public float getPosition(){
        return position;
    }
    
    /***** position ****/
    public void setStatus(Status status){
        this.status = status;
    }
    
    public Status getStatus(){
        return status;
    }    
}
