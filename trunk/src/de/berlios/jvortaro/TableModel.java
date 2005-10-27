/*
 * TableModel.java
 *
 * Created on 27 ottobre 2005, 19.23
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

import javax.swing.table.DefaultTableModel;


/**
 *
 * @author enrico
 */
public class TableModel extends DefaultTableModel {
    
    boolean editable = false;

    public TableModel() {
        super(new Object [][] {},new String [] {"Lingvo 1", "Lingvo 2"});
    }

    public void setEditable(boolean editable){
        this.editable = editable;
    }
    
    public boolean isCellEditable(int param, int param1) {
        return editable;
    }

    public Class getColumnClass(int param) {
        return String.class;
    }

    
}
