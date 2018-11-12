package capaControlador;

import java.util.ArrayList;

import capaDAO.TipoEmpleadoDAO;
import capaDAO.UsuarioDAO;
import capaModelo.TipoEmpleado;
import capaModelo.Usuario;

public class EmpleadoCtrl {
	private boolean auditoria;
	public EmpleadoCtrl(boolean auditoria)
	{
		this.auditoria = auditoria;
	}
	
	public  ArrayList obtenerTipoEmpleado()
	{
		ArrayList tiposEmpleados = TipoEmpleadoDAO.obtenerTipoEmpleado( auditoria);
		return(tiposEmpleados);
	}
	
	public ArrayList<TipoEmpleado> obtenerTipoEmpleadoObj()
	{
		ArrayList<TipoEmpleado> tiposEmpleado = TipoEmpleadoDAO.obtenerTipoEmpleadoObj( auditoria);
		return(tiposEmpleado);
	}
	
	public int insertarTipoEmpleado(TipoEmpleado tipoEmpleado)
	{
		int idTipoEmpIns = TipoEmpleadoDAO.insertarTipoEmpleado(tipoEmpleado, auditoria);
		return(idTipoEmpIns);
	}
	
	public boolean eliminarTipoEmpleado(int idTipoEmpleado)
	{
		boolean respuesta = TipoEmpleadoDAO.eliminarTipoEmpleado(idTipoEmpleado, auditoria);
		return(respuesta);
	}
	
	public boolean editarTipoEmpleado(TipoEmpleado tipoEmpleadoEdi)
	{
		boolean respuesta = TipoEmpleadoDAO.editarTipoEmpleado(tipoEmpleadoEdi, auditoria);
		return(respuesta);
	}
	
	//Capa controladora para entidad UsuarioDAO
	public  ArrayList obtenerEmpleados()
	{
		ArrayList empleados = UsuarioDAO.obtenerEmpleados(auditoria);
		return(empleados);
	}
	
	public  Usuario obtenerEmpleado(int idEmpleado)
	{
		Usuario empleado = UsuarioDAO.obtenerEmpleado(idEmpleado, auditoria);
		return(empleado);
	}
	
	public int insertarEmpleado(Usuario empleado)
	{
		int idEmpIns = UsuarioDAO.insertarEmpleado(empleado, auditoria);
		return(idEmpIns);
	}
	
	public boolean eliminarEmpleado(int idEmpleado)
	{
		boolean respuesta = UsuarioDAO.eliminarEmpleado(idEmpleado, auditoria);
		return(respuesta);
	}
	
	public boolean editarEmpleado(Usuario empleadoEdi)
	{
		boolean respuesta = UsuarioDAO.editarEmpleado(empleadoEdi, auditoria);
		return(respuesta);
	}
	
	public boolean validarExistenciaEmpleado(int idUsuario)
	{
		boolean respuesta = UsuarioDAO.validarExistenciaUsuario(idUsuario, auditoria);
		return(respuesta);
	}
	
	public  ArrayList<Usuario> obtenerDomiciliarios()
	{
		ArrayList<Usuario> domiciliarios = UsuarioDAO.obtenerDomiciliarios(auditoria);
		return(domiciliarios);
	}
	
	public boolean esDomicilario(int idTipoEmpleado)
	{
		boolean respuesta = TipoEmpleadoDAO.esDomicilario(idTipoEmpleado, auditoria);
		return(respuesta);
	}

}
