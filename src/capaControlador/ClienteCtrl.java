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
	private boolean auditoria;
	public ArrayList<Cliente> obtenerClientes(String telefono)
	{
		ArrayList<Cliente>  clientes = ClienteDAO.obtenerCliente(telefono, auditoria);
		return(clientes);
	}
	
	public Cliente obtenerClientePorId(int idCliente)
	{
		Cliente clienteCons = ClienteDAO.obtenerClienteporID(idCliente, auditoria);
		return(clienteCons);
	}
	
	public int insertarCliente(Cliente clienteInsertar)
	{
		int idCliInser = ClienteDAO.insertarCliente(clienteInsertar, auditoria);
		return(idCliInser);
	}
	
	public ClienteCtrl(boolean auditoria)
	{
		this.auditoria = auditoria;
	}
	
	public boolean  existeCliente(String telefono)
	{
		boolean respuesta = ClienteDAO.existeCliente(telefono, auditoria);
		return(respuesta);
	}
	

}
