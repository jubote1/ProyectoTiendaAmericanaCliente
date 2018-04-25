package interfazGrafica;

import java.util.ArrayList;
import javax.swing.AbstractListModel;
import capaModelo.Estado;
import capaModelo.EstadoAnterior;

public class EstadoAnteriorListModel extends AbstractListModel{
	 
    private ArrayList<EstadoAnterior> lista = new ArrayList<>();
 
    @Override
    public int getSize() {
        return lista.size();
    }
 
    @Override
    public Object getElementAt(int index) {
        EstadoAnterior p = lista.get(index);
        return p.getDescEstadoAnterior();
    }
    public void addEstadoAnterior(EstadoAnterior p){
        lista.add(p);
        this.fireIntervalAdded(this, getSize(), getSize()+1);
    }
    public void eliminarEstadoAnterior(int index0){
        lista.remove(index0);
        this.fireIntervalRemoved(index0, getSize(), getSize()+1);
    }
    public EstadoAnterior getEstadoAnterior(int index){
        return lista.get(index);
    }
}