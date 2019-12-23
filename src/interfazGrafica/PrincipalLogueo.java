package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.Window;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.awt.event.ActionEvent;

import capaControlador.EmpleadoCtrl;
import capaControlador.OperacionesTiendaCtrl;
import capaControlador.AutenticacionCtrl;
import capaControlador.ParametrosCtrl;
import capaControlador.PedidoCtrl;
import capaModelo.FechaSistema;
import capaModelo.Parametro;
import capaModelo.Usuario;

public class PrincipalLogueo extends JFrame implements Runnable{

	private JPanel contentPane;
	private JTextField textUsuario;
	private JPasswordField jpassClave;
	static int idUsuario = 0;
	private JTextField txtFechaSistema;
	private JTextField txtFechaUltCierre;
	private JTextField txtEstadoCierre;
	private JLabel lblFechaHora;
	public static boolean habilitaAuditoria = false;
	public static int idTienda = 0;
	String fecha,hora,minutos,segundos,ampm;
	OperacionesTiendaCtrl operTienda;
	Calendar calendario;    
	Thread h1;
//	Thread hiloValidacion;
	AutenticacionCtrl aut = new AutenticacionCtrl(PrincipalLogueo.habilitaAuditoria);
	ParametrosCtrl parCtrl = new ParametrosCtrl(PrincipalLogueo.habilitaAuditoria);
	PedidoCtrl pedCtrl = new PedidoCtrl(PrincipalLogueo.habilitaAuditoria);
	OperacionesTiendaCtrl operCtrl = new OperacionesTiendaCtrl(PrincipalLogueo.habilitaAuditoria);
	//Variable que almacenará que tipo de presentación manejará el sistema si 1 la clásica o 2 la vista modificada
	Window ventanaPadre;
	FechaSistema fechasSistema;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PrincipalLogueo frame = new PrincipalLogueo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PrincipalLogueo() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(0,0, 450, 382);
		int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	    int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		setBounds((ancho / 2) - (this.getWidth() / 2), (alto / 2) - (this.getHeight() / 2), 450, 400);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		ventanaPadre = this;
		//Realizamos un cargue inicial de la variable Sesion
		cargarEntornoInicial();
				
		boolean estaAperturado = pedCtrl.isSistemaAperturado();
		String fechaMayor = operCtrl.validarEstadoFechaSistema();
		
		//Traemos de base de datos el valor del parametro de auditoria
		Parametro parametroAud = parCtrl.obtenerParametro("AUDITORIA");
		//Extraemos el valor del campo de ValorTexto
		String strParam = parametroAud.getValorTexto();
		//Intentamos realizar el parseo para un dato tipo boolean sino se puede se deja como false
		try
		{
			habilitaAuditoria = Boolean.parseBoolean(strParam);
		}catch(Exception e)
		{
			habilitaAuditoria = false;
		}
		
		JLabel lblNombreSistema = new JLabel("SISTEMA TIENDA PIZZA AMERICANA");
		lblNombreSistema.setFont(new Font("Traditional Arabic", Font.BOLD, 17));
		lblNombreSistema.setBounds(64, 11, 338, 40);
		contentPane.add(lblNombreSistema);
		ImageIcon img = new ImageIcon("iconos\\LogoPequePizzaAmericana.jpg");
		setIconImage(img.getImage());
				
		JLabel lbUsuario = new JLabel("Usuario:");
		lbUsuario.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbUsuario.setBounds(205, 77, 59, 24);
		contentPane.add(lbUsuario);
		
		JLabel lblContrasena = new JLabel("Contrase\u00F1a:");
		lblContrasena.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblContrasena.setBounds(203, 115, 72, 14);
		contentPane.add(lblContrasena);
		
		textUsuario = new JTextField();
		textUsuario.setBounds(303, 76, 121, 20);
		contentPane.add(textUsuario);
		textUsuario.setColumns(10);
		
		jpassClave = new JPasswordField();
		jpassClave.setBounds(303, 112, 121, 19);
		contentPane.add(jpassClave);
		
		JButton btnAutenticar = new JButton("Autenticar");
		fechasSistema = pedCtrl.obtenerFechasSistema();
		
		//Fijamos el idTienda del sistema
		operTienda = new OperacionesTiendaCtrl(PrincipalLogueo.habilitaAuditoria);
		try{
			idTienda = operTienda.obtenerIdTienda();
		}catch(Exception exc)
		{
			idTienda = 1;
		}
		btnAutenticar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				char[] clave = jpassClave.getPassword();
				String claveFinal = new String(clave);
				String usuario = textUsuario.getText();
				//Incluimos una validación de si el usuario y lave son vacíos se ingresa con un usuario genérico CAJA
				if(usuario.equals(new String("")) && claveFinal.equals(new String("")))
				{
					//Se asignan las variables para poder ingresar y validar en la aplicación
					usuario = "caja";
					claveFinal = "caja";
				}
				Usuario objUsuario;
				if(usuario.equals(""))
				{
					JOptionPane.showMessageDialog(null, "Usuario en Blanco", "Debes Ingresar el nombre de Usuario", JOptionPane.ERROR_MESSAGE);
				}else
				{
					
					objUsuario = new Usuario(0,usuario, claveFinal, "", 0,"" , false);
					boolean  resultado = aut.autenticarUsuario(objUsuario);
					
										
					if(resultado)
					{
						System.out.println("CADUCADO " + objUsuario.getCaducado());
						// Antes de validar el resultado del logueo validamos si la contraseña esta caducadada o no
						if(objUsuario.getCaducado() == 1)
						{
							JOptionPane.showMessageDialog(ventanaPadre, "El usuario está caducado deberá asignar de nuevo la clave rápida!!", "Renovación de clave rápida", JOptionPane.OK_OPTION);
							VentSegAsignacionClaveRapida asigClaveRapida = new VentSegAsignacionClaveRapida((Frame)ventanaPadre, objUsuario, true); 
							asigClaveRapida.setVisible(true);
							// Se desplegará una nueva pantalla que tomará la asignación de Clave
						}
//						hiloValidacion.start();
						//Cuando se supera el logueo asignamos la variable estática de idUsuario.
						idUsuario = objUsuario.getIdUsuario();
						JOptionPane.showConfirmDialog(null,  "Bienvenido al Sistema " + objUsuario.getNombreLargo() , "Ingresaste al Sistema!!", JOptionPane.OK_OPTION);
						if(!estaAperturado)
						{
							//Llamamos método para validar el estado de la fecha respecto a la última apertura.
							
							//String fechaAumentada = operCtrl.aumentarFecha(fechasSistema.getFechaApertura());
							Object seleccion = JOptionPane.showInputDialog(
									   null,
									   "¿El día no ha sido aperturado, desea abrirlo? Seleccione opcion",
									   "Selector de opciones",
									   JOptionPane.QUESTION_MESSAGE,
									   null,  // null para icono defecto
									   new Object[] { fechaMayor }, 
									   fechaMayor);
							if(seleccion == null)
							{
								dispose();
								return;
							}
							else
							{
								//Vamos a realizar la validación si el día ya está abierto
								boolean sistemaAbierto = operCtrl.verificarSistemaYaAperturado(seleccion.toString());
								if(!sistemaAbierto)
								{
									//Realizar cambio de la fecha
									operCtrl.realizarAperturaDia(seleccion.toString());
								}else
								{
									int resp = JOptionPane.showConfirmDialog(null, "¿Está seguro de Reabrir el sistema, pues el día ya fue abierto anteriormente y posiblemente tenga ventas. Si contesta si, recuerde volver a cerrar el sistema.", "Confirmación Reapertura Sistema" , JOptionPane.YES_NO_OPTION);
									if (resp == 0)
									{
										operCtrl.realizarReaperturaDia(seleccion.toString());
									}
									else
									{
										dispose();
										return;
									}
								}
								//Realizamos actualización de la fecha del sistema con el fin de que no aparezcan validaciones innecesarias
								fechasSistema = pedCtrl.obtenerFechasSistema();
							}
						}
						//En este punto del ingreso validaremos el estado de las fechas
						if(fechaMayor.equals(fechasSistema.getFechaApertura()))
						{
							
						}else
						{
							JOptionPane.showMessageDialog(ventanaPadre, "La fecha actual del sistema " + fechasSistema.getFechaApertura() + ",difiere de la fecha real que es " + fechaMayor + 
									". \n Si esto no es normal por favor comunicarse con el administrador del sistema.",  "Atención", JOptionPane.ERROR_MESSAGE);
						}
						//Fijamos el usuario que se está loguendo
						aut.cargarEntornoFinal(usuario, idUsuario, objUsuario, objUsuario.getidTipoEmpleado());
												
						if(objUsuario.getTipoInicio().equals("Ventana Menús"))
						{
							if(Sesion.getPresentacion() == 1)
							{
								VentPrincipal ventPrincipal = new VentPrincipal();
								ventPrincipal.setVisible(true);
							}else if(Sesion.getPresentacion() == 2)
							{
								VentPrincipalModificada ventPrincipal = new VentPrincipalModificada();
								ventPrincipal.lblInformacionUsuario.setText("USUARIO: " + Sesion.getUsuario());
								ventPrincipal.setVisible(true);
							}
						}else if(objUsuario.getTipoInicio().equals("Maestro Pedidos"))
						{
							VentPedTransaccional transacciones = new VentPedTransaccional();
							transacciones.setVisible(true);
						}else if(objUsuario.getTipoInicio().equals("Comanda Pedidos"))
						{
							VentPedComandaPedidos ventPrincipal = new VentPedComandaPedidos();
							ventPrincipal.setVisible(true);
						}
						dispose();
					}else
					{
						JOptionPane.showMessageDialog(null, "Usuario y/o Clave Incorrecta", "Usuario y/o Clave Incorrecta", JOptionPane.ERROR_MESSAGE);
						textUsuario.setText("");
						jpassClave.setText("");
					}
				}
			}
		});
		btnAutenticar.setBounds(64, 277, 134, 46);
		contentPane.add(btnAutenticar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnCancelar.setBounds(217, 277, 132, 46);
		contentPane.add(btnCancelar);
		
		JLabel lblImagen = new JLabel("");
		lblImagen.setBounds(0, 62, 198, 126);
		//Adicionamos imagen
		ImageIcon icon = new ImageIcon(getClass().getResource("/imagenes/LogoPizzaAmericana.png"));
		Image imagen = icon.getImage();
		ImageIcon iconoEscalado = new ImageIcon (imagen.getScaledInstance(198,126,Image.SCALE_SMOOTH));
		lblImagen.setIcon(iconoEscalado);
		contentPane.add(lblImagen); 
		
		JLabel lblFechaSistema = new JLabel("Fecha Sistema");
		lblFechaSistema.setBounds(10, 202, 96, 14);
		contentPane.add(lblFechaSistema);
		
		JLabel lblFechaUltCierre = new JLabel("Fecha Ult Cierre");
		lblFechaUltCierre.setBounds(226, 202, 92, 14);
		contentPane.add(lblFechaUltCierre);
		lblFechaUltCierre.setVisible(false);
		
		txtFechaSistema = new JTextField();
		txtFechaSistema.setEditable(false);
		txtFechaSistema.setBounds(112, 199, 104, 20);
		contentPane.add(txtFechaSistema);
		txtFechaSistema.setColumns(10);
		if(estaAperturado)
		{
			txtFechaSistema.setText(fechasSistema.getFechaApertura());
		}else
		{
			txtFechaSistema.setText("");
		}
		
		
		txtFechaUltCierre = new JTextField();
		txtFechaUltCierre.setEditable(false);
		txtFechaUltCierre.setColumns(10);
		txtFechaUltCierre.setBounds(320, 199, 104, 20);
		txtFechaUltCierre.setText(fechasSistema.getFechaUltimoCierre());
		contentPane.add(txtFechaUltCierre);
		txtFechaUltCierre.setVisible(false);
		
		txtEstadoCierre = new JTextField();
		txtEstadoCierre.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtEstadoCierre.setEditable(false);
		txtEstadoCierre.setBounds(20, 227, 404, 20);
		contentPane.add(txtEstadoCierre);
		txtEstadoCierre.setColumns(10);
		
		lblFechaHora = new JLabel("");
		lblFechaHora.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblFechaHora.setBounds(64, 258, 296, 19);
		contentPane.add(lblFechaHora);
		
		JButton btnIngresoRapido = new JButton("Ingreso R\u00E1pido");
		btnIngresoRapido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Window ventanaPadre = SwingUtilities.getWindowAncestor(
                        (Component) arg0.getSource());
				VentPrincipalLogueRapido ventLogueoRapido = new VentPrincipalLogueRapido((JFrame)ventanaPadre, true);
				ventLogueoRapido.setVisible(true);
			}
		});
		btnIngresoRapido.setBounds(226, 154, 176, 23);
		contentPane.add(btnIngresoRapido);
		
		JButton btnConfigSeguridad = new JButton("");
		btnConfigSeguridad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentSegConfSeguridad ventConfSeguridad= new VentSegConfSeguridad((Frame)ventanaPadre, true);
				ventConfSeguridad.setVisible(true);
				//Evento de apertura para ventana para configuración de seguridad
			}
		});
		btnConfigSeguridad.setIcon(new ImageIcon(PrincipalLogueo.class.getResource("/icons/ConfSeg.jpg")));
		btnConfigSeguridad.setBounds(359, 258, 65, 53);
		contentPane.add(btnConfigSeguridad);
		
		JButton btnEventoEmpleados = new JButton("REGISTRO");
		btnEventoEmpleados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentSegRegEventoEmpleado ventRegEmpleado = new VentSegRegEventoEmpleado(null, true);
				ventRegEmpleado.setVisible(true);
			}
		});
		btnEventoEmpleados.setFont(new Font("Tahoma", Font.PLAIN, 8));
		btnEventoEmpleados.setBounds(352, 322, 72, 35);
		contentPane.add(btnEventoEmpleados);
		if(estaAperturado)
		{
			txtEstadoCierre.setText("El día en cuestión ya se encuentra abierto.");
		}else
		{
			txtEstadoCierre.setText("El día en cuestión no se encuentra abierto.");
		}
		h1 = new Thread(this);
//		hiloValidacion = new Thread(this);
		h1.start();
	}
	
	public void run(){
		 Thread ct = Thread.currentThread();
		 if(ct == h1)
		 {
			 while(ct == h1) {   
				  calcula();
				  lblFechaHora.setText(fecha + " " + hora + ":" + minutos + ":" + segundos + " "+ampm);
				  try {
				   Thread.sleep(1000);
				  }catch(InterruptedException e) {}
				 }
		 }
//		 else if (ct == hiloValidacion) 
//		 {
//			while(true)
//			{
//				boolean estaAperturado = pedCtrl.isSistemaAperturado();
//				if(!estaAperturado)
//				{
//					System.exit(0);
//				}
//				try {
//					   Thread.sleep(20000);
//					  }catch(InterruptedException e) {}
//			}
//		 }
		
		}
	
	public void calcula () {        
			Calendar calendario = new GregorianCalendar();
			Date fechaHoraActual = new Date();
			SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
			fecha = formatoFecha.format(fechaHoraActual);
	
			calendario.setTime(fechaHoraActual);
			ampm = calendario.get(Calendar.AM_PM)==Calendar.AM?"AM":"PM";
	
			if(ampm.equals("PM")){
				int h = calendario.get(Calendar.HOUR_OF_DAY)-12;
				hora = h>9?""+h:"0"+h;
			}else{
				hora = calendario.get(Calendar.HOUR_OF_DAY)>9?""+calendario.get(Calendar.HOUR_OF_DAY):"0"+calendario.get(Calendar.HOUR_OF_DAY);            
			}
			minutos = calendario.get(Calendar.MINUTE)>9?""+calendario.get(Calendar.MINUTE):"0"+calendario.get(Calendar.MINUTE);
			segundos = calendario.get(Calendar.SECOND)>9?""+calendario.get(Calendar.SECOND):"0"+calendario.get(Calendar.SECOND); 
		}
	
	public void cargarEntornoInicial()
	{
		//Leemos archivos properties para fijar el valor de host
				Properties prop = new Properties();
				InputStream is = null;
				
				try {
					is = new FileInputStream("C:\\Program Files\\POSPM\\pospm.properties");
					prop.load(is);
				} catch(IOException e) {
					System.out.println(e.toString());
				}
				Sesion.setHost((String)prop.getProperty("host"));
				Sesion.setEstacion((String)prop.getProperty("estacion"));		
	}
}
	
