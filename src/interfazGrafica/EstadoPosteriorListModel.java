package interfazGrafica;

import java.util.ArrayList;
import javax.swing.AbstractListModel;
import capaModelo.Estado;
import capaModelo.EstadoAnterior;
import capaModelo.EstadoPosterior;

public class EstadoPosteriorListModel extends AbstractListModel{
	 
    private ArrayList<EstadoPosterior> lista = new ArrayList<>();
 
    @Override
    public int getSize() {
        return lista.size();
    }
 
    @Override
    public Object getElementAt(int index) {
        EstadoPosterior p = lista.get(index);
        return p.getDescEstadoPosterior();
    }
    public void addEstadoPosterior(EstadoPosterior p){
        lista.add(p);
        this.fireIntervalAdded(this, getSize(), getSize()+1);
    }
    public void eliminarEstadoPosterior(int index0){
        lista.remove(index0);
        this.fireIntervalRemoved(index0, getSize(), getSize()+1);
    }
    public EstadoPosterior getEstadoPosterior(int index){
        return lista.get(index);
    }
}