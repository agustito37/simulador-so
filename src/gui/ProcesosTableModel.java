/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import simuladorso.Proceso;

/**
 *
 * @author agustin
 */
public class ProcesosTableModel extends AbstractTableModel{
   private final String[] columnNames = { "Id", "Programa" };
   private List<Proceso> lista;
   
   public ProcesosTableModel() {
      lista = new ArrayList();
   }
   
   @Override
   public int getColumnCount() {
      return columnNames.length;
   }
   
   @Override
   public int getRowCount() {
      return lista.size();
   }
   
   @Override
   public Object getValueAt(int row, int col) {
      Object temp = null;
      
      if (col == 0) {
         temp = lista.get(row).id;
      }
      else if (col == 1) {
         temp = lista.get(row).programa;
      }
      
      return temp;
   }
   
   @Override
   public String getColumnName(int col) {
      return columnNames[col];
   }
   
   public void getProceso(int index) {
       lista.get(index);
   }
   
   public void addRow(Proceso proceso) {
       lista.add(proceso);
       this.fireTableStructureChanged();
   }
   
   public void removeRow(int index) {
       lista.remove(index);
       this.fireTableStructureChanged();
   }
   
   public List<Proceso> toList() {
       return lista;
   }
}
