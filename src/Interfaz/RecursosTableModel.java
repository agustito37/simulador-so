/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import Simulador.Recurso;

/**
 *
 * @author agustin
 */
public class RecursosTableModel extends AbstractTableModel{
   private final String[] columnNames = { "Recurso", "Permiso" };
   public List<Recurso> lista;
   
   public RecursosTableModel() {
      lista = new ArrayList();
   }
   
   public RecursosTableModel(List<Recurso> pLista) {
      lista = pLista;
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
         temp = lista.get(row).idRecurso;
      }
      else if (col == 1) {
         temp = lista.get(row).permiso;
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
   
   public void addRow(Recurso recurso) {
       lista.add(recurso);
       this.fireTableStructureChanged();
   }
   
   public void removeRow(int index) {
       lista.remove(index);
       this.fireTableStructureChanged();
   }
   
   public List<Recurso> toList() {
       return lista;
   }
}
