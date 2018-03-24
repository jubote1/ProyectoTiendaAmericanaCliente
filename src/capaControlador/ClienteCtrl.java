package capaControlador;

import java.util.ArrayList;

import capaDAO.ClienteDAO;
import capaModelo.Cliente;

public class ClienteCtrl {
	
	/**
	 * Método en la capa controladora que se encarga de la busqueda de clientes dado un telefóno determinado y devolver un arrayList con objetos tipo cliente
	 * @param telefono Se recibe como parámetro un String con el valor del telefono que se desea buscar.
	 * @return Se retorna un arraylist con objetos de tipo cliente.
	 */
	public ArrayList<Cliente> obtenerClientes(String telefono)
	{
		ArrayList<Cliente>  clientes = ClienteDAO.obtenerCliente(telefono);
		return(clientes);
	}
	
	public Cliente obtenerClientePorId(int idCliente)
	{
		Cliente clienteCons = ClienteDAO.obtenerClienteporID(idCliente);
		return(clienteCons);
	}

}
