package interfazGrafica;

import java.util.ArrayList;
import javax.swing.AbstractListModel;
import capaModelo.Estado;
import capaModelo.Usuario;

public class DomiciliarioListModel extends AbstractListModel{
	 
    private ArrayList<Usuario> lista = new ArrayList<>();
 
    @Override
    public int getSize() {
        return lista.size();
    }
 
    @Override
    public Object getElementAt(int index) {
        Usuario p = lista.get(index);
        return p.getNombreLargo();
    }
    public void addDomiciliario(Usuario p){
        lista.add(p);
        this.fireIntervalAdded(this, getSize(), getSize()+1);
    }
    public void eliminarDomiciliario(int index0){
        lista.remove(index0);
        this.fireIntervalRemoved(index0, getSize(), getSize()+1);
    }
    public Usuario getDomiciliario(int index){
        return lista.get(index);
    }
}