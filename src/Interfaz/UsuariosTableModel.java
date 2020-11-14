/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import Simulador.Usuario;

/**
 *
 * @author agustin
 */
public class UsuariosTableModel extends AbstractTableModel{
   private final String[] columnNames = { "Usuario", "Permiso" };
   public List<Usuario> lista;
   
   public UsuariosTableModel() {
      lista = new ArrayList();
   }
   
   public UsuariosTableModel(List<Usuario> pLista) {
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
         temp = lista.get(row).idUsuario;
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
   
   public void addRow(Usuario usuario) {
       lista.add(usuario);
       this.fireTableStructureChanged();
   }
   
   public void removeRow(int index) {
       lista.remove(index);
       this.fireTableStructureChanged();
   }
   
   public List<Usuario> toList() {
       return lista;
   }
}
