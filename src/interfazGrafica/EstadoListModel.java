package interfazGrafica;

import java.util.ArrayList;
import javax.swing.AbstractListModel;
import capaModelo.Estado;

public class EstadoListModel extends AbstractListModel{
	 
    private ArrayList<Estado> lista = new ArrayList<>();
 
    @Override
    public int getSize() {
        return lista.size();
    }
 
    @Override
    public Object getElementAt(int index) {
        Estado p = lista.get(index);
        return p.getDescripcionCorta();
    }
    public void addEstado(Estado p){
        lista.add(p);
        this.fireIntervalAdded(this, getSize(), getSize()+1);
    }
    public void eliminarEstado(int index0){
        lista.remove(index0);
        this.fireIntervalRemoved(index0, getSize(), getSize()+1);
    }
    public Estado getEstado(int index){
        return lista.get(index);
    }
}