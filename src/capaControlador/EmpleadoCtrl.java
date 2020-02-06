package capaControlador;

import java.util.ArrayList;

import capaDAO.EmpleadoTemporalDiaDAO;
import capaDAO.TipoEmpleadoDAO;
import capaDAO.UsuarioDAO;
import capaModelo.EmpleadoTemporalDia;
import capaModelo.Parametro;
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
	
	public ArrayList<TipoEmpleado> obtenerTipoEmpleadoGeneralObj()
	{
		ParametrosCtrl parCtrl = new ParametrosCtrl(auditoria);
		Parametro parBase = parCtrl.obtenerParametro("BDGENERAL");
		ArrayList<TipoEmpleado> tiposEmpleado = TipoEmpleadoDAO.obtenerTipoEmpleadoGeneralObj(parBase.getValorTexto(), auditoria);
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
	//De cada m�todo tendremos un par que se conecta a la base de datos general
	public  ArrayList obtenerEmpleados()
	{
		ArrayList empleados = UsuarioDAO.obtenerEmpleados(auditoria);
		return(empleados);
	}
	
	public  ArrayList obtenerEmpleadosGeneral()
	{
		ParametrosCtrl parCtrl = new ParametrosCtrl(auditoria);
		Parametro parBase = parCtrl.obtenerParametro("BDGENERAL");
		ArrayList empleados = UsuarioDAO.obtenerEmpleadosGeneral(parBase.getValorTexto(), auditoria);
		return(empleados);
	}
	
	public  ArrayList obtenerEmpleadosGeneralFiltroNom(String filtroNom)
	{
		ParametrosCtrl parCtrl = new ParametrosCtrl(auditoria);
		Parametro parBase = parCtrl.obtenerParametro("BDGENERAL");
		ArrayList empleados = UsuarioDAO.obtenerEmpleadosGeneralFiltroNom(filtroNom, parBase.getValorTexto(), auditoria);
		return(empleados);
	}
	
	
	public  Usuario obtenerEmpleado(int idEmpleado)
	{
		Usuario empleado = UsuarioDAO.obtenerEmpleado(idEmpleado, auditoria);
		return(empleado);
	}
	
	public  Usuario obtenerEmpleadoGeneral(int idEmpleado)
	{
		ParametrosCtrl parCtrl = new ParametrosCtrl(auditoria);
		Parametro parBase = parCtrl.obtenerParametro("BDGENERAL");
		Usuario empleado = UsuarioDAO.obtenerEmpleadoGeneral(idEmpleado, parBase.getValorTexto(), auditoria);
		return(empleado);
	}
	
	public int insertarEmpleado(Usuario empleado)
	{
		int idEmpIns = UsuarioDAO.insertarEmpleado(empleado, auditoria);
		return(idEmpIns);
	}
	
	public int insertarEmpleadoGeneral(Usuario empleado)
	{
		ParametrosCtrl parCtrl = new ParametrosCtrl(auditoria);
		Parametro parBase = parCtrl.obtenerParametro("BDGENERAL");
		int idEmpIns = UsuarioDAO.insertarEmpleadoGeneral(empleado, parBase.getValorTexto(),  auditoria);
		return(idEmpIns);
	}
	
	public boolean eliminarEmpleado(int idEmpleado)
	{
		boolean respuesta = UsuarioDAO.eliminarEmpleado(idEmpleado, auditoria);
		return(respuesta);
	}
	
	// -- No eliminaremos empleados desde pantalla para la base de datos general
	
	public boolean editarEmpleado(Usuario empleadoEdi)
	{
		boolean respuesta = UsuarioDAO.editarEmpleado(empleadoEdi, auditoria);
		return(respuesta);
	}
	
	public boolean editarEmpleadoGeneral(Usuario empleadoEdi)
	{
		ParametrosCtrl parCtrl = new ParametrosCtrl(auditoria);
		Parametro parBase = parCtrl.obtenerParametro("BDGENERAL");
		boolean respuesta = UsuarioDAO.editarEmpleadoGeneral(empleadoEdi, parBase.getValorTexto(), auditoria);
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
	
	public void entradaDomiciliario(int idEmpleado)
	{
		UsuarioDAO.entradaDomiciliario(idEmpleado, auditoria);
	}

	public void salidaDomiciliario(int idEmpleado)
	{
		UsuarioDAO.salidaDomiciliario(idEmpleado, auditoria);
	}
	
	public EmpleadoTemporalDia consultarEmpleadoTemporal(String identificacion )
	{
		EmpleadoTemporalDia empRespuesta =  EmpleadoTemporalDiaDAO.consultarEmpleadoTemporal(identificacion, auditoria );
		return(empRespuesta);
	}
	
	public EmpleadoTemporalDia consultarEmpleadoTemporalFecha(int id, String fechaSistema)
	{
		EmpleadoTemporalDia empRespuesta = EmpleadoTemporalDiaDAO.consultarEmpleadoTemporalFecha(id, fechaSistema, auditoria );
		return(empRespuesta);
	}
	
	public ArrayList<Usuario> obtenerUsuariosTemporalesDisponibles(String fechaSistema)
	{
		ArrayList<Usuario> usuariosTemporales =  UsuarioDAO.obtenerUsuariosTemporalesDisponibles(fechaSistema, auditoria);
		return(usuariosTemporales);
		
	}
	
	public boolean InsertarEmpleadoTemporalDia(EmpleadoTemporalDia empTemporal)
	{
		boolean respuesta =  EmpleadoTemporalDiaDAO.InsertarEmpleadoTemporalDia(empTemporal,auditoria);
		return(respuesta);
	}
	
	public boolean editarEmpleadoTemporalDia(EmpleadoTemporalDia empTemporal)
	{
		boolean respuesta =  EmpleadoTemporalDiaDAO.editarEmpleadoTemporalDia(empTemporal, auditoria );
		return(respuesta);
	}
	
	public ArrayList<EmpleadoTemporalDia> obtenerEmpleadoTemporalFecha(String fecha)
	{
		ArrayList<EmpleadoTemporalDia> empleadosTemp =  EmpleadoTemporalDiaDAO.obtenerEmpleadoTemporalFecha(fecha, auditoria );
		return(empleadosTemp);
	}
	
	public boolean eliminarEmpleadoTemporalFecha(int id, String fechaSistema)
	{
		boolean respuesta = EmpleadoTemporalDiaDAO.eliminarEmpleadoTemporalFecha(id, fechaSistema, auditoria );
		return(respuesta);
	}
	
	public boolean validarHoraSalidaEmpTemporal(String fechaSistema)
	{
		boolean respuesta = EmpleadoTemporalDiaDAO.validarHoraSalidaEmpTemporal(fechaSistema, auditoria );
		return(respuesta);
	}
	
}
