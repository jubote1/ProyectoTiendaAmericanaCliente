package capaControlador;

import java.util.ArrayList;

import capaDAO.TipoEmpleadoDAO;
import capaDAO.UsuarioDAO;
import capaModelo.TipoEmpleado;
import capaModelo.Usuario;

public class EmpleadoCtrl {
	
	public  ArrayList obtenerTipoEmpleado()
	{
		ArrayList tiposEmpleados = TipoEmpleadoDAO.obtenerTipoEmpleado();
		return(tiposEmpleados);
	}
	
	public ArrayList<TipoEmpleado> obtenerTipoEmpleadoObj()
	{
		ArrayList<TipoEmpleado> tiposEmpleado = TipoEmpleadoDAO.obtenerTipoEmpleadoObj();
		return(tiposEmpleado);
	}
	
	public int insertarTipoEmpleado(TipoEmpleado tipoEmpleado)
	{
		int idTipoEmpIns = TipoEmpleadoDAO.insertarTipoEmpleado(tipoEmpleado);
		return(idTipoEmpIns);
	}
	
	public boolean eliminarTipoEmpleado(int idTipoEmpleado)
	{
		boolean respuesta = TipoEmpleadoDAO.eliminarTipoEmpleado(idTipoEmpleado);
		return(respuesta);
	}
	
	public boolean editarTipoEmpleado(TipoEmpleado tipoEmpleadoEdi)
	{
		boolean respuesta = TipoEmpleadoDAO.editarTipoEmpleado(tipoEmpleadoEdi);
		return(respuesta);
	}
	
	//Capa controladora para entidad UsuarioDAO
	public  ArrayList obtenerEmpleados()
	{
		ArrayList empleados = UsuarioDAO.obtenerEmpleados();
		return(empleados);
	}
	
	public  Usuario obtenerEmpleado(int idEmpleado)
	{
		Usuario empleado = UsuarioDAO.obtenerEmpleado(idEmpleado);
		return(empleado);
	}
	
	public int insertarEmpleado(Usuario empleado)
	{
		int idEmpIns = UsuarioDAO.insertarEmpleado(empleado);
		return(idEmpIns);
	}
	
	public boolean eliminarEmpleado(int idEmpleado)
	{
		boolean respuesta = UsuarioDAO.eliminarEmpleado(idEmpleado);
		return(respuesta);
	}
	
	public boolean editarEmpleado(Usuario empleadoEdi)
	{
		boolean respuesta = UsuarioDAO.editarEmpleado(empleadoEdi);
		return(respuesta);
	}
	
	public boolean validarExistenciaEmpleado(int idUsuario)
	{
		boolean respuesta = UsuarioDAO.validarExistenciaUsuario(idUsuario);
		return(respuesta);
	}

}
