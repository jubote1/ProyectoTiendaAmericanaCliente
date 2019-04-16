package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JLayeredPane;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import JTable.CellRenderTransaccional;
import JTable.CheckBoxRenderer;
import capaControlador.AutenticacionCtrl;
import capaControlador.EmpleadoCtrl;
import capaControlador.ParametrosCtrl;
import capaControlador.ReportesCtrl;
import capaDAO.ImprimirAdmDAO;
import capaControlador.PedidoCtrl;
import capaModelo.Cliente;
import capaModelo.FechaSistema;
import capaModelo.FormaPagoIng;
import capaModelo.Parametro;
import capaModelo.PedidoDescuento;
import capaModelo.TipoPedido;
import capaModelo.Usuario;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class VentPedComandaPedidos extends JFrame implements Runnable{

	private JPanel contentPane;
	private JTable tblMaestroPedidos;
	private static int idTipoPedido = 0;
	private static int idUsuario = 0;
	Thread h1;
	private JTextField txtFechaSistema;
	JLabel lblTipoFiltro;
	JLabel lblTipoEmpleado;
	JLabel lblEmpleado;
	JButton btnSalidaConDomicilio;
	JButton btnLlegadaDeDomicilio;
	JButton btnDisponible;
	JButton btnTotal;
	JButton btnParaLlevar;
	JButton btnDomicilio;
	JButton btnPuntoDeVenta;
	JButton btnHistorial;
	private String fechaSis;
	private PedidoCtrl pedCtrl = new PedidoCtrl(PrincipalLogueo.habilitaAuditoria);
	private EmpleadoCtrl empCtrl = new EmpleadoCtrl(PrincipalLogueo.habilitaAuditoria);
	private ParametrosCtrl parCtrl = new ParametrosCtrl(PrincipalLogueo.habilitaAuditoria);
	AutenticacionCtrl autCtrl = new AutenticacionCtrl(PrincipalLogueo.habilitaAuditoria);
	ArrayList<Usuario> domiciliarios;
	ArrayList<JButton> botDom  = new ArrayList();
	//Fijamos el tipo de empleado
	int idTipoEmpleado;
	//Prendemos el indicador si es un domiciliario
	boolean esDomiciliario;
	//Para esta pantalla es vital saber que usuario esta logueado en el sistema
	Usuario usuarioPantalla;
	//Variable booleana para saber si se está consultando un domiciliario
	boolean consDomi = false;
	//Manejaremos una variable para indicar si el domiciliario está en ruta o no
	boolean consDomiEnRuta = false;
	int idDomiCon = 0;
	//Se definen variables constantes para el manejo de los estados de domicilios
	//variable para estado de domicilios empacados
	private final long estEmpDom;
	//variable para estado en ruta domicilios
	private final long estEnRutaDom;
	//variable que indica el estado cuando un domicilio es entregado
	private final long estEntregaDom;
	//Son variables creadas para alojar temporalmente los valores de domicialiario cuando otra persona está dandoles salida de los domicilios
	public static int idUsuarioTemp;
	public static  String usuarioTemp;
	//Esta variable inicará qeu se asignó correctamente el usuario para dar salida al domiciliario
	public static boolean indUsuarioTemp = false;
	private JLabel lblNewLabel;
	//Variable que almacena el tipo de presnetación qeu tiene actualmente el sistema.
	int valPresentacion;
	private JTable tableFormaPago;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentPedComandaPedidos frame = new VentPedComandaPedidos();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void fijarValorPresentacion()
	{
		//Tomamos el valor del parámetro relacionado la interface gráfica
		Parametro parametro = parCtrl.obtenerParametro("PRESENTACION");
		try
		{
			valPresentacion = parametro.getValorNumerico();
		}catch(Exception e)
		{
			System.out.println("SE TUVO ERROR TOMANDO LA CONSTANTE DE PRESENTACIÓN SISTEMA");
			valPresentacion = 0;
		}
		if(valPresentacion == 0)
		{
			valPresentacion =1;
		}
	}

	//Creamos constructor principal del JFrame de Comanda pedidos
	public VentPedComandaPedidos() {
		//Cuadramos la presentación del JFrame
		setTitle("COMANDA PEDIDOS");
		// Realizamos inicialización de las constantes
		Parametro parametro = parCtrl.obtenerParametro("EMPACADODOMICILIO");
		long valNum = 0;
		try
		{
			valNum = (long) parametro.getValorNumerico();
		}catch(Exception e)
		{
			System.out.println("SE TUVO ERROR TOMANDO LA CONSTANTE DE PEDIDOS EMPACADOS");
			valNum = 0;
		}
		estEmpDom = valNum;
		parametro = parCtrl.obtenerParametro("ENRUTADOMICILIO");
		try
		{
			valNum = (long) parametro.getValorNumerico();
		}catch(Exception e)
		{
			System.out.println("SE TUVO ERROR TOMANDO LA CONSTANTE DE PEDIDOS EN RUTA");
			valNum = 0;
		}
		estEnRutaDom = valNum;
		parametro = parCtrl.obtenerParametro("ENTREGADODOMICILIO");
		try
		{
			valNum = (long) parametro.getValorNumerico();
		}catch(Exception e)
		{
			System.out.println("SE TUVO ERROR TOMANDO LA CONSTANTE DE DOMICILIOS ENTREGADOS");
			valNum = 0;
		}
		estEntregaDom = valNum;
		fijarValorPresentacion();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1014, 700);
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		this.setExtendedState(MAXIMIZED_BOTH);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		ImageIcon img = new ImageIcon("iconos\\LogoPequePizzaAmericana.jpg");
		setIconImage(img.getImage());
		lblTipoFiltro = new JLabel("");
		lblTipoEmpleado = new JLabel("");
		lblEmpleado = new JLabel("");
		btnDisponible = new JButton("Disponible");
		btnDisponible.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnDisponible.setBackground(Color.ORANGE);
		btnDisponible.setBounds(627, 554, 216, 37);
		contentPane.add(btnDisponible);
		btnDisponible.setVisible(false);
		//Vamos  a adicionar los domiciliarios del sistema
		domiciliarios = empCtrl.obtenerDomiciliarios();
		btnLlegadaDeDomicilio = new JButton("Llegada de Domicilio");
		actualizarCondicionesPantallas();
		
		//Adicionamos el botón de retornar salida
		
		btnDisponible.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				empCtrl.entradaDomiciliario(idUsuario);
				//Debemos actualizar el arreglo con los estados domiciliarios
		    	llegadaDomiciliarioLocal(idUsuario);
				btnDisponible.setVisible(false);
				quitarRefrescarFiltrosPantalla();
			}
		});
		

		lblTipoFiltro.setText("EMPLEADO");
		lblEmpleado.setText(usuarioPantalla.getNombreLargo());
		//En caso de que sea domiciliario lo debemos de tener en cuenta para que muestre su vista
		
		JPanel panelFiltrosPedidos = new JPanel();
		panelFiltrosPedidos.setBounds(865, 11, 133, 221);
		contentPane.add(panelFiltrosPedidos);
		panelFiltrosPedidos.setLayout(new GridLayout(5, 1, 0, 0));
		
		btnPuntoDeVenta = new JButton("Punto de venta");
		btnPuntoDeVenta.setEnabled(false);
		
		panelFiltrosPedidos.add(btnPuntoDeVenta);
		
		btnDomicilio = new JButton("Domicilio");
		
		panelFiltrosPedidos.add(btnDomicilio);
		
		btnParaLlevar = new JButton("Para Llevar");
		btnParaLlevar.setEnabled(false);
		
		panelFiltrosPedidos.add(btnParaLlevar);
		
		btnHistorial = new JButton("Historial");
		panelFiltrosPedidos.add(btnHistorial);
		
		btnTotal = new JButton("Todos los Pedidos");
		btnTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				actualizarCondicionesPantallas();
				idTipoPedido = 0;
				consDomi = false;
				pintarPedidos();
				btnTotal.setBackground(Color.YELLOW);
				btnParaLlevar.setBackground(null);
				btnDomicilio.setBackground(null);
				btnPuntoDeVenta.setBackground(null);
				btnHistorial.setBackground(null);
				//Quitamos el color al filtro por domiciliario
				pintarBotonesDomiciliario();
				lblTipoFiltro.setText(((JButton)arg0.getSource()).getText());
				lblEmpleado.setText("");
			}
		});
		//btnTotal.setBackground(Color.YELLOW);
		panelFiltrosPedidos.add(btnTotal);
		
		
		btnHistorial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				actualizarCondicionesPantallas();
				//Le ponemos una marcación especial de idTipoPedido = 100:
				idTipoPedido = 100;
				consDomi = false;
				pintarPedidos();
				btnTotal.setBackground(null);
				btnParaLlevar.setBackground(null);
				btnDomicilio.setBackground(null);
				btnPuntoDeVenta.setBackground(null);
				btnHistorial.setBackground(Color.YELLOW);
				//Quitamos el color al filtro por domiciliario
				pintarBotonesDomiciliario();
				lblTipoFiltro.setText(((JButton)arg0.getSource()).getText());
				lblEmpleado.setText("");
			}
		});
		
		
		btnParaLlevar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				idTipoPedido = 3;
				consDomi = false;
				pintarPedidos();
				btnTotal.setBackground(null);
				btnParaLlevar.setBackground(Color.YELLOW);
				btnDomicilio.setBackground(null);
				btnPuntoDeVenta.setBackground(null);
				btnHistorial.setBackground(null);
				//Quitamos el color al filtro por domiciliario
				pintarBotonesDomiciliario();
				lblTipoFiltro.setText(((JButton)e.getSource()).getText());
				lblEmpleado.setText("");
			}
		});
		
		btnDomicilio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actualizarCondicionesPantallas();
				idTipoPedido = 1;
				consDomi = false;
				pintarPedidos();
				btnTotal.setBackground(null);
				btnParaLlevar.setBackground(null);
				btnDomicilio.setBackground(Color.YELLOW);
				btnPuntoDeVenta.setBackground(null);
				btnHistorial.setBackground(null);
				//Quitamos el color al filtro por domiciliario
				pintarBotonesDomiciliario();
				lblTipoFiltro.setText(((JButton)e.getSource()).getText());
				lblEmpleado.setText("");
			}
		});
		
		btnPuntoDeVenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actualizarCondicionesPantallas();
				idTipoPedido = 2;
				consDomi = false;
				pintarPedidos();
				btnTotal.setBackground(null);
				btnParaLlevar.setBackground(null);
				btnDomicilio.setBackground(null);
				btnPuntoDeVenta.setBackground(Color.YELLOW);
				btnHistorial.setBackground(null);
				//Quitamos el color al filtro por domiciliario
				pintarBotonesDomiciliario();
				lblTipoFiltro.setText(((JButton)e.getSource()).getText());
				lblEmpleado.setText("");
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 50, 845, 493);
		contentPane.add(scrollPane);
		
		tblMaestroPedidos =   new JTable();
		//le cambiamos la fuente al jtable de pedidos para hacerla más grande y visible
		tblMaestroPedidos.setFont(new java.awt.Font("Tahoma", 0, 13)); 
		//Aumentamos el tamaño de las celdas para que quede más amplia la información
		tblMaestroPedidos.setRowHeight(30);
		tblMaestroPedidos.setEnabled(true);
		//Se realiza el pintar pedidos con lo que se tiene inicialmente
		pintarPedidos();
		scrollPane.setViewportView(tblMaestroPedidos);
		
		
		//Manejamos el evento de cuando damos doble clic sobre el pedido para avanzar de estado o cuando damos 3 veces para retroceder de estado
		tblMaestroPedidos.addMouseListener(new java.awt.event.MouseAdapter() {
		      public void mouseClicked(java.awt.event.MouseEvent e) {
		    	 
		    if( tblMaestroPedidos.getSelectedRows().length == 1 ) {
			   	  int filaSeleccionada = tblMaestroPedidos.getSelectedRow();
				  int idPedidoFP = Integer.parseInt(tblMaestroPedidos.getValueAt(filaSeleccionada, 1).toString());
				  //Intervenimos esta parte para retornar la forma de pago
				  //txtFormaPago.setText(pedCtrl.consultarFormaPago(idPedidoFP));
				  pintarFormaPagoPedido(idPedidoFP);
				  //Agregar la acción de cuando uno da clic en cualquier parte de la columna poder
				  //chequear el pedido
				  boolean cheq = (boolean) tblMaestroPedidos.getValueAt(filaSeleccionada, 0);
				  //Debemos de capturar de donde proviene el evento, para lo cual capturamos la columna
				  int col= tblMaestroPedidos.columnAtPoint(e.getPoint());
				  //Validamos que la columna es diferente de cero para revisar el chequeo o no de la fila para las acciones que vienen
				  if(col != 0)
				  {
					  if(cheq)
					  {
						  tblMaestroPedidos.setValueAt(false,filaSeleccionada,0);
					  }else
					  {
						  tblMaestroPedidos.setValueAt(true,filaSeleccionada,0);
					  }
				  }
			}
			else
			{
				pintarFormaPagoPedidoBlanco();
			}  
		    Window ventanaPadre = SwingUtilities.getWindowAncestor(
	                        (Component) e.getSource());
		    	  //TEMPORALMENTE QUITAREMOS LA ACCIÓN DE EL DOBLE CLICK PARA AVANZAR ESTADO
//		      if(e.getClickCount()==2){
//		    	  avanzarEstado((JFrame) ventanaPadre);
//		        }
		      
		      //Validamos si se debe habilitar o no el botón de salida domicilios
		      boolean indBotSalida = false;
		      for(int i = 0; i < tblMaestroPedidos.getRowCount(); i ++)
		      {
		    	  //En este punto de manera predefinida vamos por el item de si está o no chequeado el pedido
		    	  boolean pedCheq =(boolean) tblMaestroPedidos.getValueAt(i, 0);
		    	  if(pedCheq)
		    	  {
		    		  //En caso de que el pedido este chequeado validamos si está en el estado correspondiente para dar salida
		    		  long idEstado = (long) tblMaestroPedidos.getValueAt(i, 8);
		    		  if(idEstado == estEmpDom)
		    		  {
		    			  indBotSalida = true;
		    			  btnSalidaConDomicilio.setVisible(true);
		    			  break;
		    		  }
		    	  }
		      }
		      if(!indBotSalida)
		      {
		    	  btnSalidaConDomicilio.setVisible(false);
		      }
		      boolean indBotLlegada = false;
		      for(int i = 0; i < tblMaestroPedidos.getRowCount(); i ++)
		      {
		    	  //En este punto de manera predefinida vamos por el item de si está o no chequeado el pedido
		    	  boolean pedCheq =(boolean) tblMaestroPedidos.getValueAt(i, 0);
		    	  if(pedCheq == true)
		    	  {
		    		  //En caso de que el pedido este chequeado validamos si está en el estado correspondiente para dar salida
		    		  long idEstado = (long) tblMaestroPedidos.getValueAt(i, 8);
		    		  if(idEstado == estEnRutaDom)
		    		  {
		    			  indBotLlegada = true;
		    			  btnLlegadaDeDomicilio.setVisible(true);
		    			  break;
		    		  }
		    	  }
		      }
		      if(!indBotLlegada)
		      {
		    	  btnLlegadaDeDomicilio.setVisible(false);
		      }

		      }
		});
		
		JButton btnAvanzarEstado = new JButton("Avanzar Estado");
		btnAvanzarEstado.setBounds(467, 602, 180, 37);
		btnAvanzarEstado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Window ventanaPadre = SwingUtilities.getWindowAncestor(
                        (Component) e.getSource());
				avanzarEstado((JFrame) ventanaPadre);
			}
		});
		contentPane.add(btnAvanzarEstado);
		
		JButton btnRetrocederEstado = new JButton("Devolver Estado");
		btnRetrocederEstado.setBounds(277, 601, 180, 37);
		btnRetrocederEstado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Window ventanaPadre = SwingUtilities.getWindowAncestor(
                        (Component) e.getSource());
				devolverEstado((JFrame) ventanaPadre);
				
			}
		});
		contentPane.add(btnRetrocederEstado);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.setBounds(865, 602, 133, 44);
		contentPane.add(btnSalir);
		btnSalir.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JLabel lblFechaSistema = new JLabel("FECHA SISTEMA");
		lblFechaSistema.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblFechaSistema.setBounds(10, 602, 133, 29);
		contentPane.add(lblFechaSistema);
		
		txtFechaSistema = new JTextField();
		FechaSistema fecha = pedCtrl.obtenerFechasSistema();
		fechaSis = fecha.getFechaApertura();
		txtFechaSistema.setForeground(Color.RED);
		txtFechaSistema.setEditable(false);
		txtFechaSistema.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtFechaSistema.setBounds(147, 602, 120, 29);
		contentPane.add(txtFechaSistema);
		txtFechaSistema.setColumns(10);
		txtFechaSistema.setText(fechaSis);
		
		JButton btnVerDomicilios = new JButton("<html><center>Ver Ubicaci\u00F3n Domicilios</center></html>");
		btnVerDomicilios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Buscamos la URL que nos servirá como buscaador
				//Inicializamos la variable de habilitaAuditoria
				//Traemos de base de datos el valor del parametro de auditoria
				Parametro parametro = parCtrl.obtenerParametro("RUTAUBICAPEDIDOS");
				//Extraemos el valor del campo de ValorTexto
				String rutaURL = parametro.getValorTexto();
//				//Obtenemos es un String los pedido qeu deseamos geolocalizar
//				String pedidosJSON  = pedCtrl.obtenerPedidosEmpacadosDomicilio();
				//Habilitamos la consola del navegador para ver los posibles errores
				URL url=null;
				try {
				    url = new URL(rutaURL);
				    try {
				        Desktop.getDesktop().browse(url.toURI());
				    } catch (IOException e) {
				        e.printStackTrace();
				    } catch (URISyntaxException e) {
				        e.printStackTrace();
				    }
				} catch (MalformedURLException e1) {
				    e1.printStackTrace();
				}
			}
		});
		btnVerDomicilios.setBounds(657, 601, 164, 37);
		contentPane.add(btnVerDomicilios);
		
		JPanel panelFiltroDom = new JPanel();
		panelFiltroDom.setBounds(865, 233, 133, 357);
		contentPane.add(panelFiltroDom);
		panelFiltroDom.setLayout(new GridLayout(10, 0, 0, 0));
		
		JButton btnQuitarFiltros = new JButton("Quitar Filtros");
		btnQuitarFiltros.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				quitarRefrescarFiltrosPantalla();
			}
		});
		btnQuitarFiltros.setBounds(667, 11, 154, 28);
		contentPane.add(btnQuitarFiltros);
		
		
		lblTipoFiltro.setForeground(new Color(0, 0, 0));
		lblTipoFiltro.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblTipoFiltro.setBounds(148, 11, 172, 28);
		contentPane.add(lblTipoFiltro);
		
		JLabel lblFiltroPantalla = new JLabel("FILTRO PANTALLA:");
		lblFiltroPantalla.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblFiltroPantalla.setBounds(10, 11, 133, 28);
		contentPane.add(lblFiltroPantalla);
		
		lblEmpleado.setForeground(Color.BLACK);
		lblEmpleado.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEmpleado.setBounds(345, 11, 172, 28);
		contentPane.add(lblEmpleado);
		
		
		lblTipoEmpleado.setForeground(Color.BLACK);
		lblTipoEmpleado.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTipoEmpleado.setBounds(520, 11, 146, 28);
		contentPane.add(lblTipoEmpleado);
		
		btnSalidaConDomicilio = new JButton("Salida con Domicilio");
		btnSalidaConDomicilio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//La idea es que vamos a validar si no es domiciliario entonces nos muestra un modal con un list
				//para seleccionar el domiciliario que va a salir.
				Window ventanaPadre = SwingUtilities.getWindowAncestor(
                        (Component) arg0.getSource());
				if(esDomiciliario)
				{
					darSalidaDomiciliario(Sesion.getUsuario(), idDomiCon, idUsuario);
				}else
				{
					VentPedAsignarDom ventAsigDom = new VentPedAsignarDom((JFrame)ventanaPadre,true);
					ventAsigDom.setVisible(true);
					if(indUsuarioTemp)
					{
						darSalidaDomiciliario(usuarioTemp, idUsuarioTemp, idUsuarioTemp);
						idUsuarioTemp = 0;
						usuarioTemp = "";
						indUsuarioTemp = false;
					}
					
				}
				
				
			}
		});
		btnSalidaConDomicilio.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnSalidaConDomicilio.setBounds(175, 554, 216, 36);
		contentPane.add(btnSalidaConDomicilio);
		btnSalidaConDomicilio.setVisible(false);
		
		btnLlegadaDeDomicilio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int resp = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea dar llegada a todos los domicilios EN RUTA?", "Confirmación Llegada Domiciliario" , JOptionPane.YES_NO_OPTION);
				if (resp == 0)
				{
					if (idDomiCon   > 0)
					{
						//Para la acción de este botón lo que realizaremos es recorrer el Jtable y lo que esté marcado le intentaremos dar salida
						//Se tiene un indicador qeu nos dice si por lo menos se le dio llegada a un domiciliario
						boolean llegadaDomi = false;
						for(int i = 0; i < tblMaestroPedidos.getRowCount(); i ++)
					      {
					    	  //En caso de que el pedido este chequeado validamos si está en el estado correspondiente para dar salida
					    	  long idEstado = (long) tblMaestroPedidos.getValueAt(i, 8);
					    	  if(idEstado == estEnRutaDom)
					    	  {
					    		  //avanzamos de estado el pedido para lo cual tomamos el idpedido
					    		  int idPedidoAvanzar= Integer.parseInt(tblMaestroPedidos.getValueAt(i, 1).toString());
					    		  boolean respuesta = pedCtrl.ActualizarEstadoPedido((int)idPedidoAvanzar, (int) estEnRutaDom , (int) estEntregaDom,Sesion.getUsuario(),true, idDomiCon, false);
					    		  llegadaDomi = true;
					    	  }
					    	
					      }
						//Validamos si el indicador de si por lo menos un pedido se dio llegada está prendido
						if(llegadaDomi)
						{
							 //finalmente en este punto debemos de tener un domiciliario y a este es al que le vamos a cambiar el estado
					    	  empCtrl.entradaDomiciliario(idDomiCon);
					    	  //Debemos actualizar el arreglo con los estados domiciliarios
					    	  llegadaDomiciliarioLocal(idDomiCon);
						}
						btnLlegadaDeDomicilio.setVisible(false);
						consDomiEnRuta = false;
						//Comentamos el quitar los filtros porque la idea seguir con el comportamiento de la pantalla
						//porque el botón del domiciliario no se ha deseleccionado
						//quitarRefrescarFiltrosPantalla();
						pintarPedidos();
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Debe ser un domiciliario quien se de llegada, o seleccionar el domiciliario y darse llegada " , "Error", JOptionPane.ERROR_MESSAGE);
					}
					
				}
				else
				{
					return;
				}
				
			}
		});
		btnLlegadaDeDomicilio.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnLlegadaDeDomicilio.setBounds(401, 555, 216, 36);
		contentPane.add(btnLlegadaDeDomicilio);
		
		lblNewLabel = new JLabel("FORMA DE PAGO");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(10, 642, 120, 27);
		contentPane.add(lblNewLabel);
		
		JButton btnReimprimirFactura = new JButton("Reimprimir Factura");
		btnReimprimirFactura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int filaSeleccionada = tblMaestroPedidos.getSelectedRow();
				if(filaSeleccionada == -1)
				{
					JOptionPane.showMessageDialog(null, "Debe Seleccionar un pedido para Reimprimir la factura " , "No ha Seleccionado Pedido", JOptionPane.ERROR_MESSAGE);
					return;
				}
				else
				{
					int idPedidoTienda = Integer.parseInt(tblMaestroPedidos.getValueAt(filaSeleccionada, 1).toString());
					//La nueva impresión de la factura se realiza de la siguiente manera
					String strFactura = pedCtrl.generarStrImpresionFactura(idPedidoTienda);
					if(Sesion.getModeloImpresion() != 1)
					{
						ImprimirAdmDAO.insertarImpresion(strFactura, false);
					}
					else
					{
						Impresion.main(strFactura);
					}
				}
			}
		});
		btnReimprimirFactura.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnReimprimirFactura.setBounds(467, 652, 180, 37);
		contentPane.add(btnReimprimirFactura);
		
		JScrollPane scrollPaneFormaPago = new JScrollPane();
		scrollPaneFormaPago.setBounds(134, 642, 216, 58);
		contentPane.add(scrollPaneFormaPago);
		
		tableFormaPago = new JTable();
		scrollPaneFormaPago.setViewportView(tableFormaPago);
		

		btnLlegadaDeDomicilio.setVisible(false);
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				if(valPresentacion == 1)
				{
					VentPrincipal ventPrincipal = new VentPrincipal();
					ventPrincipal.setVisible(true);
				}else if(valPresentacion == 2)
				{
					VentPrincipalModificada ventPrincipal = new VentPrincipalModificada();
					ventPrincipal.lblInformacionUsuario.setText("USUARIO: " + Sesion.getUsuario());
					ventPrincipal.setVisible(true);
				}
			}
		});
		
		
		
		
		//Definimos las variables necesarias
		Usuario emp;
		JButton boton;
		//Recorremos los arreglo de los domiciliarios
		for(int i = 0; i < domiciliarios.size();i++)
		{
			//Llevamos el valor del domiciliario de turno a unavariable temporal
			emp = domiciliarios.get(i);
			//Creamos el botón
			boton = new JButton(emp.getNombreLargo());
			boton.setActionCommand(Integer.toString(emp.getIdUsuario()));
			//Adicionammos el botón al panel
			panelFiltroDom.add(boton);
			//validamos el estado domiciliario es 0 pintamos de verde el botón en caso contrario rojo
			if(emp.getEstadoDomiciliario() == 0)
			{
				boton.setBackground(Color.GREEN);
			}else
			{
				boton.setBackground(Color.RED);
			}
			botDom.add(boton);
			boton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) 
				{
					//Extraemos el botón de la acción
					JButton botAccion = ((JButton)arg0.getSource());
					//Extraemos la acción del botón
					String actCom =  botAccion.getActionCommand();
					//Tomamos el color de fondo del botón
					Color colorBut = botAccion.getBackground();
					//Si el color es amarillo no debemos hacer nada
					if(colorBut.equals(Color.YELLOW))
					{
						
					}
					else
					{
						//Debemos volver a la normalidad los botones quitarles el background
						pintarBotonesDomiciliario();
						botAccion.setBackground(Color.YELLOW);
						botAccion.setForeground(Color.BLACK);
						//Prendemos el indicador de que es una consulta por domiciliario
						consDomi = true;
						//Debemos de ejecutar la consulta para el domiciliario en cuestión
						//guardamos en la variable el idUsuario del domiciliario
						idDomiCon = Integer.parseInt(actCom);
						//Validamos si se deben o no pintar los domicilios EN RUTA
						
						for(int i = 0; i < domiciliarios.size(); i++)
						{
							Usuario domiTemp = domiciliarios.get(i);
							if(domiTemp.getIdUsuario() == idDomiCon)
							{
								//Validamos el estado domiciliario si está por fuera, habilitamos el indicador para ver los pedidos en ruta de dicho domiciliario.
								if(domiTemp.getEstadoDomiciliario() == 1)
								{
									consDomiEnRuta = true;
									btnDisponible.setVisible(true);
									btnLlegadaDeDomicilio.setVisible(true);
								}
								else
								{
									consDomiEnRuta = false;
									btnLlegadaDeDomicilio.setVisible(false);
								}
								break;
							}
						}
						
						//Llamamos método para pintar pedidos
						pintarPedidos();
						//Quitamos el color al filtro por tipo de pedido
						btnTotal.setBackground(null);
						btnParaLlevar.setBackground(null);
						btnDomicilio.setBackground(null);
						btnPuntoDeVenta.setBackground(null);
					}
					lblTipoFiltro.setText(((JButton)arg0.getSource()).getText());
					lblEmpleado.setText("");
					//Este método realiza una revisión de los filtros de la pantalla y los actualiza , lo comentamos de manera que no se quitn los filtros hasta que no se 
					//clic en otro botón
					//actualizarCondicionesPantallas();
				}
			});
		}
		
		//Despues de agregada la anterior información de los botones con los domiciliarios, verificamos si el empleado ingresado
		//es domiciliario, y en caso de que lo sea mostramos su vista, si no es así, mostramos la vista asociada al empleado
		if(esDomiciliario)
		{
			
		}
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				pintarPedidos();
			}
		});
		
		//Para este punto de la ventana de pedidos realizaremos una validación de si el usuario esta logueado o no en cuyo caso no sea así 
		//la aplicación nos sacara
		validarLogueo();
		//Se realiza un recargue de la página cada 30 segundos, con base en las variables 
		h1 = new Thread(this);
		//h1.start();
	}
	
	public void validarLogueo()
	{
		idUsuario = Sesion.getIdUsuario();
		if(idUsuario == 0)
		{
			JOptionPane.showMessageDialog(null, "Aparentemente el usuario no está logueado en el sistema esta ventana se cerrará " , "Ingreso no permitido", JOptionPane.ERROR_MESSAGE);
			dispose();
		}
		//Luego realizamos la validación de que dicho idUsuario si exista.
		boolean validacion = empCtrl.validarExistenciaEmpleado(idUsuario);
		if(!validacion)
		{
			JOptionPane.showMessageDialog(null, "El usuario no existe en base de datos" , "Usuario no existe", JOptionPane.ERROR_MESSAGE);
			dispose();
		}
	}
	
	public void pintarPedidos()
	{
		Object[] columnsName = new Object[12];
        
		columnsName[0] = "ACT";
		columnsName[1] = "Id Pedido";
        columnsName[2] = "Fecha Pedido";
        columnsName[3] = "Nombres";
        columnsName[4] = "Tipo Pedido";
        columnsName[5] = "Estado Pedido";
        columnsName[6] = "Dirección";
        columnsName[7] = "id Tipo Pedido";
        columnsName[8] = "idestado";
        columnsName[9] = "Domiciliario";
        columnsName[10] = "TP";
        columnsName[11] = "Tiempo";
        ArrayList<Object> pedidos = new ArrayList();
        //La lógica de pintar pedidos debemos de dividirla en varios items
        // Hay filtros de tipo pedido, en cuyo caso se cumple condición para que el sistema filtre por ellos
        if(idTipoPedido >0)
        {
        	pedidos = pedCtrl.obtenerPedidosVentanaComandaTipPed(0, idTipoPedido);
        }
        // Hay filtros por domiciliario, esto significa que emepleado no es necesario domiciliario pero se debe ver 
        // lo qeu ve un domiciliario, para lo cual validamos si se está consultando un domiciliario
        if(consDomi)
        {
        	//En este punto hacemos la verificación de si el domiciliario está en ruta para la visualización según la variable indicadora
        	if(consDomiEnRuta)
        	{
        		pedidos = pedCtrl.obtenerPedidosVentanaComandaDomEnRuta(idDomiCon);
        		btnLlegadaDeDomicilio.setVisible(true);
        	}else
        	{
        		pedidos = pedCtrl.obtenerPedidosVentanaComandaDom(idDomiCon);
        	}
        	
        }
        //La otra combinación de situaciones es que no se tenga filtro de tipo pedido y no filtre por domiciliario osea son todos los pedidos
        if((idTipoPedido == 0)&&(!consDomi))
        {
        	pedidos = pedCtrl.obtenerPedidosVentanaComanda(0);
        }
        
        //Definimos cuales serán las columnas editables dentro de la tabla
        boolean[] editarCampos = {true, false, false, false, false, false, false, false, false, false, false,false};
      
        //Definimos los tipos de objetos que se manejarán en el jtable en cada columna
        
        DefaultTableModel modelo = new DefaultTableModel(){
       	    public boolean isCellEditable(int rowIndex,int columnIndex){
       	    	return editarCampos[columnIndex];
       	    }
       	    
       	    Class[] types = new Class[] {java.lang.Boolean.class,java.lang.Long.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.Long.class,java.lang.Long.class,java.lang.String.class,java.lang.String.class,java.lang.String.class};
         
//       	    Class[] types = new Class[] {java.lang.Boolean.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class};
         
       	    public Class getColumnClass(int columnIndex)
       	    {
       	    	return types[columnIndex];
       	    }
       	};
		modelo.setColumnIdentifiers(columnsName);
		for(int y = 0; y < pedidos.size();y++)
		{
			String[] fila =(String[]) pedidos.get(y);
			//modelo.addRow(fila);
			Object[] filaFinal = {false,Long.parseLong(fila[1]), fila[2], fila[3], fila[4], fila[5], fila[6], Long.parseLong(fila[7]),Long.parseLong(fila[8]),fila[9], fila[10], fila[11]};
			modelo.addRow(filaFinal);
		}
		tblMaestroPedidos.setModel(modelo);
		//Checkbox
		tblMaestroPedidos.getColumnModel().getColumn(0).setMinWidth(70);
		tblMaestroPedidos.getColumnModel().getColumn(0).setMaxWidth(70);
		tblMaestroPedidos.getColumnModel().getColumn(1).setMaxWidth(60);
		tblMaestroPedidos.getColumnModel().getColumn(1).setMinWidth(60);
		//Ocultamos la conlumna de FechaPedido, dado que es la misma dependiendo del día aperturado
		tblMaestroPedidos.getColumnModel().getColumn(2).setMaxWidth(0);
		tblMaestroPedidos.getColumnModel().getColumn(2).setMinWidth(0);
		//Modificamos el ancho de la columna nombre
		tblMaestroPedidos.getColumnModel().getColumn(3).setMinWidth(120);
		tblMaestroPedidos.getColumnModel().getColumn(3).setMaxWidth(120);
		//Modificamos ancho del tipo de pedido
		tblMaestroPedidos.getColumnModel().getColumn(4).setMinWidth(90);
		tblMaestroPedidos.getColumnModel().getColumn(4).setMaxWidth(90);
		//Modificamos ancho del estado pedido
		tblMaestroPedidos.getColumnModel().getColumn(5).setMinWidth(90);
		tblMaestroPedidos.getColumnModel().getColumn(5).setMaxWidth(90);
		tblMaestroPedidos.getColumnModel().getColumn(7).setMaxWidth(0);
		tblMaestroPedidos.getColumnModel().getColumn(7).setMinWidth(0);
		tblMaestroPedidos.getColumnModel().getColumn(8).setMaxWidth(0);
		tblMaestroPedidos.getColumnModel().getColumn(8).setMinWidth(0);
		tblMaestroPedidos.getColumnModel().getColumn(9).setMinWidth(90);
		tblMaestroPedidos.getColumnModel().getColumn(9).setMaxWidth(90);
		//Modificamos el ancho del la Tiempo Pedido
		tblMaestroPedidos.getColumnModel().getColumn(10).setMinWidth(20);
		tblMaestroPedidos.getColumnModel().getColumn(10).setMaxWidth(20);
		//Modificamos el ancho del la muestra del tiempo
		tblMaestroPedidos.getColumnModel().getColumn(11).setMinWidth(135);
		tblMaestroPedidos.getColumnModel().getColumn(11).setMaxWidth(135);
		
		
		//Checkbox
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(0).setMinWidth(70);
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(70);
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(60);
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(1).setMinWidth(60);
		//Ocultamos la conlumna de FechaPedido, dado que es la misma dependiendo del día aperturado
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(0);
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(3).setMinWidth(0);
		//Modificamos el ancho de la columna nombre
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(3).setMinWidth(120);
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(120);
		//Modificamos ancho del tipo de pedido
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(4).setMinWidth(90);
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(4).setMaxWidth(90);
		//Modificamos ancho del estado pedido
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(5).setMinWidth(90);
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(5).setMaxWidth(90);
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(7).setMaxWidth(0);
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(7).setMinWidth(0);
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(8).setMaxWidth(0);	
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(8).setMinWidth(0);
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(9).setMinWidth(90);
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(9).setMaxWidth(90);
		//Modificamos el ancho del tiempo dado pedido
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(10).setMinWidth(20);
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(10).setMaxWidth(20);
		//Modificamos el ancho del Tiempo Pedido
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(11).setMinWidth(135);
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(11).setMaxWidth(135);

		tblMaestroPedidos.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tblMaestroPedidos.getColumnModel().getColumn(4).setCellRenderer( new CellRenderTransaccional());
		tblMaestroPedidos.getColumnModel().getColumn(5).setCellRenderer( new CellRenderTransaccional());
		tblMaestroPedidos.getColumnModel().getColumn(3).setCellRenderer( new CellRenderTransaccional());
		tblMaestroPedidos.getColumnModel().getColumn(6).setCellRenderer( new CellRenderTransaccional());
		tblMaestroPedidos.getColumnModel().getColumn(0).setCellRenderer( new CheckBoxRenderer());
		//setCellRender(tblMaestroPedidos);
		
	}
	
	/**
	 * Este es un método para renderizar todas las columnas no es lo idea pues la idea es que se tengan políticas de renderizado por columna
	 * @param table
	 */
	public void setCellRender(JTable table) {
        Enumeration<TableColumn> en = table.getColumnModel().getColumns();
        while (en.hasMoreElements()) {
            TableColumn tc = en.nextElement();
            tc.setCellRenderer(new CellRenderTransaccional());
        }
    }
	
		
	public void run(){
		 Thread ct = Thread.currentThread();
		 while(ct == h1) 
		 {   
			 try {
				 	Thread.sleep(60000);
			 }catch(InterruptedException e) 
			 {}
			 //Ejecutamos el pintado de los pedidos en el JTable de la pantalla.
			 pintarPedidos();
		  //Realizamos la ejecución cada 30 segundos
			 
		 }
	}
	
	public void avanzarEstado(JFrame padre)
	{
		int filaSeleccionada = tblMaestroPedidos.getSelectedRow();
		if(filaSeleccionada == -1)
		{
			JOptionPane.showMessageDialog(null, "Debe Seleccionar algún pedido para Avanzar Estado " , "No ha Seleccionado Pedido", JOptionPane.ERROR_MESSAGE);
			return;
		}
		// Se captura el valor del idDetalle que se desea eliminar
		int idPedidoAvanzar= Integer.parseInt(tblMaestroPedidos.getValueAt(filaSeleccionada, 1).toString());
		int idTipoPedido = Integer.parseInt(tblMaestroPedidos.getValueAt(filaSeleccionada, 7).toString());
		int idEstado = Integer.parseInt(tblMaestroPedidos.getValueAt(filaSeleccionada, 8).toString());
		boolean esEstadoFinal = pedCtrl.esEstadoFinal(idTipoPedido, idEstado);
		if(esEstadoFinal)
		{
			JOptionPane.showMessageDialog(null, "El estado actual es un estado Final no se puede avanzar más" , "No hay estado posterior", JOptionPane.ERROR_MESSAGE);
			return;
		}
		else
		{
			boolean tienePermiso = autCtrl.validarAccesoOpcion("PED_002", Sesion.getAccesosOpcion());
			if (tienePermiso)
			{
				VentPedCambioEstado cambioEstado = new VentPedCambioEstado(idPedidoAvanzar, false, true, padre, true, idDomiCon, (int) estEnRutaDom);
				cambioEstado.setVisible(true);
				pintarPedidos();
			}else
			{
				JOptionPane.showMessageDialog(null, "Su perfil de usuario no tiene acceso a esta opción/pantalla" , "Ingreso no permitido", JOptionPane.ERROR_MESSAGE);
			}
			
		}
		
	}
	
	public void devolverEstado(JFrame padre)
	{
		int filaSeleccionada = tblMaestroPedidos.getSelectedRow();
		if(filaSeleccionada == -1)
		{
			JOptionPane.showMessageDialog(null, "Debe Seleccionar algún pedido para Devolver Estado " , "No ha Seleccionado Pedido", JOptionPane.ERROR_MESSAGE);
			return;
		}
		// Se captura el valor del idDetalle que se desea eliminar
		int idPedidoDevolver= Integer.parseInt(tblMaestroPedidos.getValueAt(filaSeleccionada, 1).toString());
		int idTipoPedido = Integer.parseInt(tblMaestroPedidos.getValueAt(filaSeleccionada, 7).toString());
		int idEstado = Integer.parseInt(tblMaestroPedidos.getValueAt(filaSeleccionada, 8).toString());
		boolean esEstadoInicial = pedCtrl.esEstadoInicial(idTipoPedido, idEstado);
		if(esEstadoInicial)
		{
			JOptionPane.showMessageDialog(null, "El estado actual es un estado Inicial no se puede retroceder" , "No hay estado anterior", JOptionPane.ERROR_MESSAGE);
			return;
		}
		else
		{
			boolean tienePermiso = autCtrl.validarAccesoOpcion("PED_002", Sesion.getAccesosOpcion());
			if (tienePermiso)
			{
				VentPedCambioEstado cambioEstado = new VentPedCambioEstado(idPedidoDevolver, true, false, padre, true,idDomiCon, (int)estEnRutaDom);
				cambioEstado.setVisible(true);
				pintarPedidos();
			}else
			{
				JOptionPane.showMessageDialog(null, "Su perfil de usuario no tiene acceso a esta opción/pantalla" , "Ingreso no permitido", JOptionPane.ERROR_MESSAGE);
			}
			
		}
	}
	
	public void pintarBotonesDomiciliario()
	{
		//Recorremos todos los botones de domiciliario
		for(int i = 0; i < botDom.size(); i++)
		{
			//Capturamos el ActionCommand que contiene el idEmpleado, con dicho idEmpleado lo buscamos enelos domiciliarios, donde lo encontremos revisamos el estado domiciliario para pintar el botón
			JButton botTemp = botDom.get(i);
			int idEmpleado = Integer.parseInt((botTemp).getActionCommand());
			for(int j = 0 ; j < domiciliarios.size(); j++)
			{
				//Sacamos uno a uno los domiciliarios
				Usuario emp = domiciliarios.get(i);
				//Realizamos la comparación para validar si coincide el domiciliario con el asociado al botón
				if(emp.getIdUsuario() == idEmpleado)
				{
					//Realizamos la validación del estado del domiciliario para pintar el botón
					if(emp.getEstadoDomiciliario() == 0)
					{
						botTemp.setBackground(Color.GREEN);
						botTemp.setForeground(Color.BLACK);
					}
					else
					{
						botTemp.setBackground(Color.RED);
						botTemp.setForeground(Color.WHITE);
					}
					break;
				}
			}
		}
	}
	
	public void salidaDomiciliarioLocal(int idUsuario)
	{
		for(int i = 0; i < domiciliarios.size(); i++)
		{
			Usuario emp = domiciliarios.get(i);
			if(emp.getIdUsuario() == idUsuario)
			{
				emp.setEstadoDomiciliario(1);
			}
			domiciliarios.set(i, emp);
		}
	}
	
	public void llegadaDomiciliarioLocal(int idUsuario)
	{
		for(int i = 0; i < domiciliarios.size(); i++)
		{
			Usuario emp = domiciliarios.get(i);
			if(emp.getIdUsuario() == idUsuario)
			{
				emp.setEstadoDomiciliario(0);
			}
			domiciliarios.set(i, emp);
		}
	}
	
	/**
	 * Esté método es usado para refrescar la pantalla, mostrando la situacion actual y quitando los filtros que se pueda tener en la pantalla
	 */
	public void quitarRefrescarFiltrosPantalla()
	{
		//En este método realizaremos el reinicio de los filtros del sistema,cuando se inicia la pantalla que es por el usuario logueado
		//Quitamos posible color Amarillo a los botones
		btnTotal.setBackground(null);
		btnParaLlevar.setBackground(null);
		btnDomicilio.setBackground(null);
		btnPuntoDeVenta.setBackground(null);
		btnHistorial.setBackground(null);
		//Quitamos el color al filtro por domiciliario
		pintarBotonesDomiciliario();
		//Igualamos el usuario del sistema
		usuarioPantalla = empCtrl.obtenerEmpleado(Sesion.getIdUsuario());
		//Obtenemos el tipo de empleado
		idTipoEmpleado = usuarioPantalla.getidTipoEmpleado();
		//Deberemos si el tipo de empleado es domicilio  y en caso de que lo sea
		esDomiciliario = empCtrl.esDomicilario(idTipoEmpleado);
		//En caso de que sea domiciliario o este consultando domiciliario fijamos el idDomiciliario
		if(esDomiciliario)
		{
			idDomiCon = Sesion.getIdUsuario();
			consDomi = true;
			//Validaremos el estado del domiciliario para ver si se muestras los pedidos en ruta
			for(int i = 0; i < domiciliarios.size(); i++)
			{
				Usuario domiTemp = domiciliarios.get(i);
				if(domiTemp.getIdUsuario() == Sesion.getIdUsuario())
				{
					//Validamos el estado domiciliario si está por fuera, habilitamos el indicador para ver los pedidos en ruta de dicho domiciliario.
					if(domiTemp.getEstadoDomiciliario() == 1)
					{
						consDomiEnRuta = true;
						btnDisponible.setVisible(true);
						btnLlegadaDeDomicilio.setVisible(true);
					}
					else
					{
						consDomiEnRuta = false;
						btnLlegadaDeDomicilio.setVisible(false);
					}
				}
			}
		}
		else
		{
			//Cuando se refresca y en caso de que no sea un domiciliario se debe limpiar las variables en cuestión
			idDomiCon = 0;
			consDomi = false;
		}
		//En caso de que sea domiciliario lo debemos de tener en cuenta para que muestre su vista
		lblTipoFiltro.setText("EMPLEADO");
		lblEmpleado.setText(usuarioPantalla.getNombreLargo());
		pintarPedidos();
	}
	
	public void  darSalidaDomiciliario(String usuario, int idDomiciliario, int idUsu)
	{
		//Para la acción de este botón lo que realizaremos es recorrer el Jtable y lo que esté marcado le intentaremos dar salida
		//Se tiene un indicador qeu nos dice si por lo menos se le dio salida a un domiciliario
		boolean salidaDomi = false;
		for(int i = 0; i < tblMaestroPedidos.getRowCount(); i ++)
	      {
	    	  //En este punto de manera predefinida vamos por el item de si está o no chequeado el pedido
	    	  boolean pedCheq =(boolean) tblMaestroPedidos.getValueAt(i, 0);
	    	  if(pedCheq)
	    	  {
	    		//En caso de que el pedido este chequeado validamos si está en el estado correspondiente para dar salida
	    		  long idEstado = (long) tblMaestroPedidos.getValueAt(i, 8);
	    		  if(idEstado == estEmpDom)
	    		  {
	    			  //avanzamos de estado el pedido para lo cual tomamos el idpedido
	    			  int idPedidoAvanzar= Integer.parseInt(tblMaestroPedidos.getValueAt(i, 1).toString());
	    			  //Avanzamos de estado el pedido
	    			  boolean respuesta = pedCtrl.ActualizarEstadoPedido((int)idPedidoAvanzar, (int) estEmpDom , (int) estEnRutaDom,usuario,true, idDomiciliario,true);
	    			  //Prendemos el indicador de salida del domicilio
	    			  salidaDomi =  true;
	    		  }
	    	  }
	      }
		//Validamos si el indicador de si por lo menos un pedido se dio salida está prendido
		if(salidaDomi)
		{
			 //finalmente en este punto debemos de tener un domiciliario y a este es al que le vamos a cambiar el estado
	    	  empCtrl.salidaDomiciliario(idUsu);
	    	  //Debemos actualizar el arreglo con los estados domiciliarios
	    	  salidaDomiciliarioLocal(idUsu);
		}
		btnSalidaConDomicilio.setVisible(false);
		consDomiEnRuta = true;
		btnLlegadaDeDomicilio.setVisible(true);
		//Comentamos el quitar los filtros porque la idea seguir con el comportamiento de la pantalla
		//porque el botón del domiciliario no se ha deseleccionado
		//quitarRefrescarFiltrosPantalla();
		pintarPedidos();
	}
	
	/**
	 * Correponde al método con el cual se normaliza la situación de la pantalla una vez se realizan 
	 * las acciones correspondientes de otros usuarios.
	 */
	public void actualizarCondicionesPantallas()
	{
		//Igualamos el usuario del sistema
		usuarioPantalla = empCtrl.obtenerEmpleado(Sesion.getIdUsuario());
		//Obtenemos el tipo de empleado
		idTipoEmpleado = usuarioPantalla.getidTipoEmpleado();
		//Deberemos si el tipo de empleado es domicilio  y en caso de que lo sea
		esDomiciliario = empCtrl.esDomicilario(idTipoEmpleado);
		//En caso de que sea domiciliario o este consultando domiciliario fijamos el idDomiciliario
		consDomiEnRuta = false;
		if(esDomiciliario)
		{
			idDomiCon = Sesion.getIdUsuario();
			consDomi = true;
			//Validaremos el estado del domiciliario para ver si se muestras los pedidos en ruta
			for(int i = 0; i < domiciliarios.size(); i++)
			{
				Usuario domiTemp = domiciliarios.get(i);
				if(domiTemp.getIdUsuario() == Sesion.getIdUsuario())
				{
					//Validamos el estado domiciliario si está por fuera, habilitamos el indicador para ver los pedidos en ruta de dicho domiciliario.
					if(domiTemp.getEstadoDomiciliario() == 1)
					{
						consDomiEnRuta = true;
						btnDisponible.setVisible(true);
						btnLlegadaDeDomicilio.setVisible(true);
					}
					break;
				}
			}
			if(!consDomiEnRuta)
			{
				btnLlegadaDeDomicilio.setVisible(false);
			}
		}
	}
	
	public void pintarFormaPagoPedido(int idPedido)
	{
		Object[] columnsName = new Object[2];
		columnsName[0] = "FORMA PAGO";
		columnsName[1] = "VALOR";
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(columnsName);
        ArrayList<String[]> formasPagoPedido = pedCtrl.consultarFormaPagoArreglo(idPedido);
        String[] fila;
        for(int i = 0; i < formasPagoPedido.size(); i++)
        {
        	String[]  formaTemp = formasPagoPedido.get(i);
        	fila = new String[2];
        	fila[0] = formaTemp[0];
        	fila[1] = formaTemp[1];
        	modelo.addRow(fila);
        }
        tableFormaPago.setModel(modelo);
	}
	
	public void pintarFormaPagoPedidoBlanco()
	{
		Object[] columnsName = new Object[1];
		columnsName[0] = "FORMA PAGO";
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(columnsName);
        tableFormaPago.setModel(modelo);
	}
}
