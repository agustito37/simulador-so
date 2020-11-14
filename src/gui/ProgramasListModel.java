/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;
import simuladorso.Programa;

/**
 *
 * @author agustin
 */
public class ProgramasListModel extends AbstractListModel{
    public final List<Programa> lista;

    public ProgramasListModel() {
        this.lista = new ArrayList();
    }
    
    public ProgramasListModel(List<Programa> pLista) {
        this.lista = pLista;
    }

    @Override
    public int getSize() {
        return lista.size();
    }

    @Override
    public Object getElementAt(int index) {
        return lista.get(index);
    }
    
    public void add(String element) {
        lista.add(new Programa(element));
    }
    
    public void remove(Programa programa) {
        lista.remove(programa);
    }
}
