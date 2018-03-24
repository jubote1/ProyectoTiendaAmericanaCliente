package capaControlador;

import java.util.ArrayList;

import capaDAO.MunicipioDAO;
import capaModelo.Municipio;

public class ParametrosDireccionCtrl {
	
	/**
	 * M�todo de capa controladora para el retorno de los municipios
	 * @return ArrayList con tipo de datos gen�rico de municipio.
	 */
	public ArrayList<Object> obtenerMunicipios()
	{
		ArrayList<Object>  municipios = MunicipioDAO.obtenerMunicipios();
		return(municipios);
	}
	
	
	public ArrayList<Municipio> obtenerMunicipiosObjeto()
	{
		ArrayList<Municipio>  municipios = MunicipioDAO.obtenerMunicipiosObjeto();
		return(municipios);
	}
	
	/**
	 * M�todo de la capa controladora que se encarga de retornar un objeto de tipo Municipio con base en el id recibido
	 * como par�mertro.
	 * @param idMunicipio
	 * @return retorna un Municipio encapsulado en un objeto tipo municipio.
	 */
	public Municipio obtenerMunicipio(int idMunicipio)
	{
		Municipio  municipio = MunicipioDAO.obtenerMunicipio(idMunicipio);
		return(municipio);
	}
	
	/**
	 * M�todo de capa controladora encargado de la inserci�n de un nuevo municipio
	 * @param municipio Se recibe el objeto de la entidad municipio con los datos para insertar
	 * @return Se retorna valor entero con el idmunicipio creado en el sistema.
	 */
	public int insertarMunicipio(Municipio municipio)
	{
		int idMunicipio = MunicipioDAO.insertarMunicipio(municipio);
		return(idMunicipio);
	}
	
	
	/**
	 * M�todo de la capa controladora que se encarga de eliminar un determinado Municipio
	 * @param idMunicipio Se recibe el idMunicipio por producto que se desea eliminar
	 * @return Se retorna un valor booleano que indica el resultado del proceso
	 */
	public boolean eliminarMunicipio(int idMunicipio)
	{
		boolean respuesta = MunicipioDAO.eliminarMunicipio(idMunicipio);
		return(respuesta);
	}
	
	/**
	 * M�todo de la capa controladora que se encarga de la edici�nr un determinado Municipio
	 * @param municipio Se recibe el Municipio con los para�metros para la edici�n.
	 * @return Se retorna un valor booleano que indica el resultado del proceso
	 */
	public boolean editarMunicipio(Municipio municipio)
	{
		boolean respuesta = MunicipioDAO.editarMunicipio(municipio);
		return(respuesta);
	}

}
