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

import de.berlios.jvortaro.bean.Dictionary;
import de.berlios.jvortaro.bean.TableRow;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;



/**
 *
 * @author enrico
 */
public class TableModel extends AbstractTableModel {
    
    boolean editable = false;

    private String[] header = new String [] {"Lingvo 1", "Lingvo 2"};
    private ArrayList<TableRow> data = new ArrayList<TableRow>();
    private Dictionary dictionary = null;
    private Dictionary.Direction direction = Dictionary.Direction.UNSPECIFIED;
    
    public TableModel() {
        super();
       //new Object [][] {},);
    }

    public void setEditable(boolean editable){
        this.editable = editable;
    }
    
    public boolean isCellEditable(int param, int param1) {
        return editable;
    }

    public Class getColumnClass(int column) {
        return String.class;
    }

    public int getColumnCount() {
        return header.length;
    }

    public int getRowCount() {
        return data.size();
    }

    public Object getValueAt(int row, int column) {
        TableRow r = getValueAt(row);
        String[] result = new String[2];
        result[0] = r.getLang1();
        result[1] = r.getLang2();
        return result[column];
    }

    public TableRow getValueAt(int row) {
        return data.get(row);
    }

    public void clear(){
        
        data.clear();
    }
    
    public void addRow(TableRow row){
        
        data.add(row);
        fireTableRowsInserted(data.size()-1, data.size());
    }
    
    public void addRows(ArrayList<TableRow> rows){

        int before = data.size();
        data.addAll(rows);
        int after = data.size();
        fireTableRowsInserted(before, after);
    }
    
    public void removeRow(int row){

        dictionary.updateRow(direction, getValueAt(row), null);
        data.remove(row);
    }
    
    public void insertRow(int row, TableRow data){
        
        this.data.add(row,data);
        fireTableRowsInserted(row,row);
    }
    
    public void setValueAt(Object o, int row, int column){
        
        // warn dict, insert new row from returned by dict
        dictionary.updateRow(direction, getValueAt(row), null);
        String value = (String)o;
        TableRow oldRow = getValueAt(row);
        TableRow newRow = oldRow.clone();
        if (column == 0)
            newRow.setLang1(value);
        else
            newRow.setLang2(value);
        
        dictionary.updateRow(direction, newRow, oldRow);
        fireTableRowsUpdated(row,row);
    }
    
    public void setDictionary(Dictionary dict){
        this.dictionary = dict;
    } 
    
    public void setDirection(Dictionary.Direction direction){
        this.direction = direction;
    }
    
    public Dictionary.Direction getDirection(){
        return direction;
    }
    
}
