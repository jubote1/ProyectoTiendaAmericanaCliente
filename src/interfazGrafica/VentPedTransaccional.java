package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import java.util.ArrayList;
import java.util.Enumeration;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import JTable.CellRenderTransaccional;
import JTable.CellRenderTransaccional2;
import JTable.CellRenderTransaccionalTiempos;
import JTable.CheckBoxRenderer;
import capaControlador.AutenticacionCtrl;
import capaControlador.EmpleadoCtrl;
import capaControlador.ParametrosCtrl;
import capaControlador.ReportesCtrl;
import capaDAO.ImprimirAdmDAO;
import capaControlador.PedidoCtrl;
import capaModelo.Cliente;
import capaModelo.DetallePedido;
import capaModelo.FechaSistema;
import capaModelo.Parametro;
import capaModelo.PedidoDescuento;
import capaModelo.TipoPedido;
import capaModelo.Usuario;

import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class VentPedTransaccional extends JFrame implements Runnable{

	private JPanel contentPane;
	private JTable tblMaestroPedidos;
	private static int idTipoPedido = 0;
	private String fechaSis;
	private PedidoCtrl pedCtrl = new PedidoCtrl(PrincipalLogueo.habilitaAuditoria);
	private EmpleadoCtrl empCtrl = new EmpleadoCtrl(PrincipalLogueo.habilitaAuditoria);
	AutenticacionCtrl autCtrl = new AutenticacionCtrl(PrincipalLogueo.habilitaAuditoria);
	private ParametrosCtrl parCtrl = new ParametrosCtrl(PrincipalLogueo.habilitaAuditoria);
	Thread h1;
	private JFrame ventanaPadre;
	ArrayList<Usuario> domiciliarios;
	ArrayList<JButton> botDom  = new ArrayList();
	private JTextField txtFechaSistema;
	//Variable booleana para saber si se está consultando un domiciliario
	boolean consDomi = false;
	int idDomiCon = 0;
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
					VentPedTransaccional frame = new VentPedTransaccional();
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
	/**
	 * Create the frame.
	 */
	public VentPedTransaccional() {
		setTitle("VENTANA TRANSACCIONAL");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1014, 720);
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		this.setExtendedState(MAXIMIZED_BOTH);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		ImageIcon img = new ImageIcon("iconos\\LogoPequePizzaAmericana.jpg");
		setIconImage(img.getImage());
		JPanel panelBotones = new JPanel();
		panelBotones.setBounds(10, 603, 978, 61);
		contentPane.add(panelBotones);
		panelBotones.setLayout(new GridLayout(0, 6, 0, 0));
		
		//Llenamos con la ventana Padre la variable Window
		ventanaPadre = this;
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelBotones.add(btnBuscar);
		fijarValorPresentacion();
		JButton btnReabrirFactura = new JButton("Reabrir Factura");
		btnReabrirFactura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int filaSeleccionada = tblMaestroPedidos.getSelectedRow();
				if(filaSeleccionada == -1)
				{
					JOptionPane.showMessageDialog(null, "Debe Seleccionar un pedido para Reabrir " , "No ha Seleccionado Pedido", JOptionPane.ERROR_MESSAGE);
					return;
				}
				else
				{
					int idPedido = Integer.parseInt(tblMaestroPedidos.getValueAt(filaSeleccionada, 0).toString());
					//Deberemos Rellenar las variables estáticas de la pantalla VentPedTomarPedidos
					VentPedTomarPedidos ventTomaPedidos = new VentPedTomarPedidos();
					VentPedTomarPedidos.idPedido = idPedido;
					boolean tieneDescuento = pedCtrl.existePedidoDescuento(idPedido);
					if(tieneDescuento)
					{
						VentPedTomarPedidos.tieneDescuento = true;
						PedidoDescuento desPedido = pedCtrl.obtenerPedidoDescuento(idPedido);
						VentPedTomarPedidos.descuento = desPedido.getDescuentoPesos();
						
					}
					double totalBruto = pedCtrl.obtenerTotalBrutoPedido(idPedido);
					VentPedTomarPedidos.totalPedido = totalBruto;
					//Recuperamos una información bastante importante para reabrir el pedido como lo es el ArrayList de DetallePedido.
					VentPedTomarPedidos.detallesPedido = pedCtrl.obtenerDetallePedido(idPedido);
					Cliente clientePedido = pedCtrl.obtenerClientePedido(idPedido);
					VentPedTomarPedidos.idCliente = clientePedido.getIdcliente();
					VentPedTomarPedidos.nombreCliente = clientePedido.getNombres() + " " + clientePedido.getApellidos();
					VentPedTomarPedidos.direccion = clientePedido.getDireccion();
					//System.out.println("OJO LOS DATOS DE CLIENTE QUE ESTAMOS FIJANDO " + VentPedTomarPedidos.direccion );
					boolean tieneFormaPago = pedCtrl.existeFormaPago(idPedido);
					if(tieneFormaPago)
					{
						VentPedTomarPedidos.tieneFormaPago = true;
					}
					//Continuamos con el tipo de pedido para desplegarlo correctamente
					ArrayList<TipoPedido>tiposPedidos = pedCtrl.obtenerTiposPedidoNat();
					int idTipoPedido = pedCtrl.obtenerTipoDePedido(idPedido);
					//buscamos en el arreglo que posición tiene
					int numTipoPedido = 0;
					for(int i = 0; i< tiposPedidos.size(); i++)
					{
						TipoPedido tipPedidoTemp = tiposPedidos.get(i);
						if(tipPedidoTemp.getIdTipoPedido() == idTipoPedido)
						{
							numTipoPedido = i;
							break;
						}
					}
					VentPedTomarPedidos.numTipoPedidoAct = numTipoPedido;
					//Retornamos el tipo de Pedido
					ventTomaPedidos.esReabierto = true;
					ventTomaPedidos.setVisible(true);
					dispose();
				}
			}
		});
		btnReabrirFactura.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelBotones.add(btnReabrirFactura);
		
		JButton btnReimpresion = new JButton("Reimprimir Factura");
		btnReimpresion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Acción para la generación de reporte general de ventas
				int filaSeleccionada = tblMaestroPedidos.getSelectedRow();
				if(filaSeleccionada == -1)
				{
					JOptionPane.showMessageDialog(null, "Debe Seleccionar un pedido para Reimprimir la factura " , "No ha Seleccionado Pedido", JOptionPane.ERROR_MESSAGE);
					return;
				}
				else
				{
					int idPedidoTienda = Integer.parseInt(tblMaestroPedidos.getValueAt(filaSeleccionada, 0).toString());
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
		btnReimpresion.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelBotones.add(btnReimpresion);
		
		JButton btnHistoriaDetalle = new JButton("Historia Detalle Pedido");
		btnHistoriaDetalle.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnHistoriaDetalle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int filaValida = tblMaestroPedidos.getSelectedRow();
				if(filaValida == -1)
				{
					JOptionPane.showMessageDialog(null, "Debe Seleccionar algún pedido para ver su Historia." , "No ha Seleccionado Pedido", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if( tblMaestroPedidos.getSelectedRows().length == 1 ) { 
		    		  
					boolean tienePermiso = autCtrl.validarAccesoOpcion("PED_009", Sesion.getAccesosOpcion());
					if (tienePermiso)
					{
						int filaSeleccionada = tblMaestroPedidos.getSelectedRow();
						  int idPedidoHistoria = Integer.parseInt(tblMaestroPedidos.getValueAt(filaSeleccionada, 0).toString());
						  VentPedHisDetPedido ventHistoria = new VentPedHisDetPedido(ventanaPadre, true, idPedidoHistoria);
						  ventHistoria.setVisible(true);
					}else
					{
						JOptionPane.showMessageDialog(null, "Su perfil de usuario no tiene acceso a esta opción/pantalla" , "Ingreso no permitido", JOptionPane.ERROR_MESSAGE);
					}
		    		  
		         }
			}
		});
		panelBotones.add(btnHistoriaDetalle);
		
		JButton btnRegresarMenu = new JButton("Regresar Men\u00FA");
		btnRegresarMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
				dispose();
			}
		});
		btnRegresarMenu.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelBotones.add(btnRegresarMenu);
		
		JButton btnTomarPedido = new JButton("Tomar Pedido");
		btnTomarPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean tienePermiso = autCtrl.validarAccesoOpcion("PED_013", Sesion.getAccesosOpcion());
				if (tienePermiso)
				{
					VentPedTomarPedidos ventPedido = new VentPedTomarPedidos();
					ventPedido.setVisible(true);
					dispose();
				}else
				{
					JOptionPane.showMessageDialog(null, "Su perfil de usuario no tiene acceso a esta opción/pantalla" , "Ingreso no permitido", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		btnTomarPedido.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelBotones.add(btnTomarPedido);
		
		JPanel panelFiltrosPedidos = new JPanel();
		panelFiltrosPedidos.setBounds(839, 11, 161, 222);
		contentPane.add(panelFiltrosPedidos);
		panelFiltrosPedidos.setLayout(new GridLayout(5, 1, 0, 0));
		
		JButton btnPuntoDeVenta = new JButton("<html><center>Punto de venta</center></html>");
		
		panelFiltrosPedidos.add(btnPuntoDeVenta);
		
		JButton btnDomicilio = new JButton("<html><center>Domicilio</center></html>");
		
		panelFiltrosPedidos.add(btnDomicilio);
		
		JButton btnParaLlevar = new JButton("<html><center>Para Llevar</center></html>");
		
		panelFiltrosPedidos.add(btnParaLlevar);
		JButton btnTotalConPedidos = new JButton("<html><center>TODOS LOS PEDIDOS</center></html>");
		JButton btnTotal = new JButton("<html><center>Total Sin Pedidos Finalizados</center></html>");
		
		btnTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				idDomiCon = 0;
				consDomi = false;
				idTipoPedido = 0;
				pintarPedidos();
				btnTotal.setBackground(Color.YELLOW);
				btnParaLlevar.setBackground(null);
				btnDomicilio.setBackground(null);
				btnPuntoDeVenta.setBackground(null);
				btnTotalConPedidos.setBackground(null);
				for(int i = 0; i < botDom.size(); i++)
				{
					//Quitamos el backgroung del botón 
					botDom.get(i).setBackground(null);
				}
			}
		});
		
		

		
		btnTotalConPedidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Prendemos un indicador que buscará traer todos los tipos de tipos pedidos sin importar el estado
				idDomiCon = 0;
				consDomi = false;
				idTipoPedido = -1;
				pintarPedidos();
				btnTotalConPedidos.setBackground(Color.YELLOW);
				btnTotal.setBackground(null);
				btnParaLlevar.setBackground(null);
				btnDomicilio.setBackground(null);
				btnPuntoDeVenta.setBackground(null);
				for(int i = 0; i < botDom.size(); i++)
				{
					//Quitamos el backgroung del botón 
					botDom.get(i).setBackground(null);
				}
			}
		});
		panelFiltrosPedidos.add(btnTotalConPedidos);
		btnTotal.setBackground(Color.YELLOW);
		panelFiltrosPedidos.add(btnTotal);
		
		btnParaLlevar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				idDomiCon = 0;
				consDomi = false;
				idTipoPedido = 3;
				pintarPedidos();
				btnTotal.setBackground(null);
				btnParaLlevar.setBackground(Color.YELLOW);
				btnDomicilio.setBackground(null);
				btnPuntoDeVenta.setBackground(null);
				btnTotalConPedidos.setBackground(null);
				for(int i = 0; i < botDom.size(); i++)
				{
					//Quitamos el backgroung del botón 
					botDom.get(i).setBackground(null);
				}
			}
		});
		
		btnDomicilio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				idDomiCon = 0;
				consDomi = false;
				idTipoPedido = 1;
				pintarPedidos();
				btnTotal.setBackground(null);
				btnParaLlevar.setBackground(null);
				btnDomicilio.setBackground(Color.YELLOW);
				btnPuntoDeVenta.setBackground(null);
				btnTotalConPedidos.setBackground(null);
				for(int i = 0; i < botDom.size(); i++)
				{
					//Quitamos el backgroung del botón 
					botDom.get(i).setBackground(null);
				}
			}
		});
		
		btnPuntoDeVenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				idDomiCon = 0;
				consDomi = false;
				idTipoPedido = 2;
				pintarPedidos();
				btnTotal.setBackground(null);
				btnParaLlevar.setBackground(null);
				btnDomicilio.setBackground(null);
				btnPuntoDeVenta.setBackground(Color.YELLOW);
				btnTotalConPedidos.setBackground(null);
				for(int i = 0; i < botDom.size(); i++)
				{
					//Quitamos el backgroung del botón 
					botDom.get(i).setBackground(null);
				}
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 819, 521);
		contentPane.add(scrollPane);
		
		tblMaestroPedidos =   new JTable();
		//le cambiamos la fuente al jtable de pedidos para hacerla más grande y visible
		tblMaestroPedidos.setFont(new java.awt.Font("Tahoma", 0, 14)); 
		//Aumentamos el tamaño de las celdas para que quede más amplia la información
		tblMaestroPedidos.setRowHeight(25);
		tblMaestroPedidos.setEnabled(true);
		pintarPedidos();
		scrollPane.setViewportView(tblMaestroPedidos);
		
		
		tblMaestroPedidos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		    @Override
		    public void valueChanged(ListSelectionEvent event) {
		    	
		    }
		});
		
		//Manejamos el evento de cuando damos doble clic sobre el pedido para avanzar de estado o cuando damos 3 veces para retroceder de estado
		tblMaestroPedidos.addMouseListener(new java.awt.event.MouseAdapter() {
		      public void mouseClicked(java.awt.event.MouseEvent e) {
		      //Revisamos el tema para mostrar la forma del pago del pedido
		      if( tblMaestroPedidos.getSelectedRows().length == 1 ) {
		    	  int filaSeleccionada = tblMaestroPedidos.getSelectedRow();
				  int idPedidoFP = Integer.parseInt(tblMaestroPedidos.getValueAt(filaSeleccionada, 0).toString());
				  //Intervenimos esta parte para retornar la forma de pago
				  //txtFormaPago.setText(pedCtrl.consultarFormaPago(idPedidoFP));
				  pintarFormaPagoPedido(idPedidoFP);
		      }
		      else
		      {
		    	  pintarFormaPagoPedidoBlanco();
		      }
		      if(e.getClickCount()==2){
		    	  avanzarEstado();
		        }
		      if(e.getClickCount()==3){
		    	  devolverEstado();
		       }
		 }
		});
		
		JButton btnAvanzarEstado = new JButton("Avanzar Estado");
		btnAvanzarEstado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				avanzarEstado();
			}
		});
		btnAvanzarEstado.setBounds(586, 543, 180, 47);
		contentPane.add(btnAvanzarEstado);
		
		JButton btnRetrocederEstado = new JButton("Devolver Estado");
		btnRetrocederEstado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				devolverEstado();
				
			}
		});
		btnRetrocederEstado.setBounds(371, 543, 180, 49);
		contentPane.add(btnRetrocederEstado);
		
		JLabel lblFechaSistema = new JLabel("FECHA SISTEMA");
		lblFechaSistema.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblFechaSistema.setBounds(89, 550, 139, 40);
		contentPane.add(lblFechaSistema);
		
		FechaSistema fecha = pedCtrl.obtenerFechasSistema();
		fechaSis = fecha.getFechaApertura();
		txtFechaSistema = new JTextField();
		txtFechaSistema.setForeground(Color.RED);
		txtFechaSistema.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtFechaSistema.setEditable(false);
		txtFechaSistema.setBounds(238, 556, 123, 34);
		txtFechaSistema.setText(fechaSis);
		contentPane.add(txtFechaSistema);
		txtFechaSistema.setColumns(10);
		
		JScrollPane scrollPaneDomiciliarios = new JScrollPane();
		scrollPaneDomiciliarios.setBounds(842, 244, 158, 348);
		contentPane.add(scrollPaneDomiciliarios);
		
		JPanel panelDomiciliario = new JPanel();
		scrollPaneDomiciliarios.setViewportView(panelDomiciliario);
		panelDomiciliario.setLayout(new GridLayout(11, 0, 0, 0));
		
		JLabel lblFormaDePago = new JLabel("FORMA DE PAGO");
		lblFormaDePago.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblFormaDePago.setBounds(20, 674, 131, 21);
		contentPane.add(lblFormaDePago);
		
		JScrollPane scrollPaneFormaPago = new JScrollPane();
		scrollPaneFormaPago.setBounds(184, 675, 214, 45);
		contentPane.add(scrollPaneFormaPago);
		
		tableFormaPago = new JTable();
		scrollPaneFormaPago.setViewportView(tableFormaPago);
		
		JButton btnReimprimirComanda = new JButton("Reimprimir Comanda");
		btnReimprimirComanda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Acción para reimpresión de factura
				int filaSeleccionada = tblMaestroPedidos.getSelectedRow();
				if(filaSeleccionada == -1)
				{
					JOptionPane.showMessageDialog(null, "Debe Seleccionar un pedido para Reimprimir la comanda " , "No ha Seleccionado Pedido", JOptionPane.ERROR_MESSAGE);
					return;
				}
				else
				{
					int idPedidoTienda = Integer.parseInt(tblMaestroPedidos.getValueAt(filaSeleccionada, 0).toString());
					//La nueva impresión de la factura se realiza de la siguiente manera
					String strComanda = pedCtrl.generarStrImpresionComanda(idPedidoTienda);
					if(Sesion.getModeloImpresion() != 1)
					{
						ImprimirAdmDAO.insertarImpresion(strComanda, false);
					}
					else
					{
						Impresion.main(strComanda);
					}
				}
			
			}
		});
		btnReimprimirComanda.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnReimprimirComanda.setBounds(458, 674, 163, 35);
		contentPane.add(btnReimprimirComanda);
		
		//Agregamos los botones dinámicos de domiciliarios
		//Vamos  a adicionar los domiciliarios del sistema
				domiciliarios = empCtrl.obtenerDomiciliarios();
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
					panelDomiciliario.add(boton);
					botDom.add(boton);
					boton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) 
						{
							idTipoPedido = 0;
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
								for(int i = 0; i < botDom.size(); i++)
								{
									//Quitamos el backgroung del botón 
									botDom.get(i).setBackground(null);
								}
								botAccion.setBackground(Color.YELLOW);
								//Prendemos el indicador de que es una consulta por domiciliario
								consDomi = true;
								//Debemos de ejecutar la consulta para el domiciliario en cuestión
								//guardamos en la variable el idUsuario del domiciliario
								idDomiCon = Integer.parseInt(actCom);
								//Llamamos método para pintar pedidos
								pintarPedidos();
								//Quitamos el color al filtro por tipo de pedido
								btnTotal.setBackground(null);
								btnParaLlevar.setBackground(null);
								btnDomicilio.setBackground(null);
								btnPuntoDeVenta.setBackground(null);
								btnTotalConPedidos.setBackground(null);
							}
							
						}
					});
				}
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				//De aqui puede venir nuestro desangre, por temas de tiempos y consultas a la base de datos
				//Por el momento lo suspenderemos, mientras optimizamos
				//pintarPedidos();
			}
		});
		//Realizamos un refrescamiento de la pantalla cada 30 segundos
		h1 = new Thread(this);
		h1.start();
		btnRetrocederEstado.setVisible(false);
		btnAvanzarEstado.setVisible(false);
	}
	
	public void pintarPedidos()
	{
		Object[] columnsName = new Object[11];
        
		columnsName[0] = "Id Pedido";
        columnsName[1] = "Fecha Pedido";
        columnsName[2] = "Nombres";
        columnsName[3] = "Tipo Pedido";
        columnsName[4] = "Estado Pedido";
        columnsName[5] = "Dirección";
        columnsName[6] = "id Tipo Pedido";
        columnsName[7] = "idestado";
        columnsName[8] = "Domiciliario";
        columnsName[9] = "TP";
        columnsName[10] = "Tiempo";
        ArrayList<Object> pedidos = new ArrayList();
        if(idTipoPedido == -1)
        {
        	//Se invoca de la capa Controladora la forma de observar los pedidos por un total
        	pedidos = pedCtrl.obtenerPedidosTableConFinales();
        }else if(idTipoPedido > 0)
        {
        	//Se invoca de la capa controladora la forma de ver los pedidos por tipo
        	pedidos = pedCtrl.obtenerPedidosPorTipo(idTipoPedido);
        }else if((idTipoPedido == 0)&&(idDomiCon == 0))
        {
        	//Se invoca de la capa Controladora la forma de observar los pedidos por un total
        	pedidos = pedCtrl.obtenerPedidosTableSinFinales();
        }else if((idTipoPedido == 0)&&(idDomiCon > 0))
        {
        	//Se invoca de la capa Controladora la forma de observar los pedidos por un domiciliario
        	pedidos = pedCtrl.obtenerPedidosVentanaComandaDom(idDomiCon);
        }
        //Definimos cuales serán las columnas editables dentro de la tabla
        boolean[] editarCampos = {false, false, false, false, false, false, false, false, false, false, false};
      
        //Definimos los tipos de objetos que se manejarán en el jtable en cada columna
        
        DefaultTableModel modelo = new DefaultTableModel(){
       	    public boolean isCellEditable(int rowIndex,int columnIndex){
       	    	return editarCampos[columnIndex];
       	    }
       	    
       	    Class[] types = new Class[] {java.lang.Long.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.Long.class,java.lang.Long.class,java.lang.String.class,java.lang.String.class,java.lang.String.class};
         
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
			Object[] filaFinal = {Long.parseLong(fila[0]), fila[1], fila[2], fila[3], fila[4], fila[5], Long.parseLong(fila[6]),Long.parseLong(fila[7]),fila[8], fila[9], fila[10]};
			modelo.addRow(filaFinal);
		}
		tblMaestroPedidos.setModel(modelo);
		tblMaestroPedidos.getColumnModel().getColumn(0).setMaxWidth(60);
		tblMaestroPedidos.getColumnModel().getColumn(0).setMinWidth(60);
		//Ocultamos la conlumna de FechaPedido, dado que es la misma dependiendo del día aperturado
		tblMaestroPedidos.getColumnModel().getColumn(1).setMaxWidth(0);
		tblMaestroPedidos.getColumnModel().getColumn(1).setMinWidth(0);
		//Modificamos el ancho de la columna nombre
		tblMaestroPedidos.getColumnModel().getColumn(2).setMinWidth(120);
		tblMaestroPedidos.getColumnModel().getColumn(2).setMaxWidth(120);
		//Modificamos ancho del tipo de pedido
		tblMaestroPedidos.getColumnModel().getColumn(3).setMinWidth(110);
		tblMaestroPedidos.getColumnModel().getColumn(3).setMaxWidth(110);
		//Modificamos ancho del estado pedido
		tblMaestroPedidos.getColumnModel().getColumn(4).setMinWidth(110);
		tblMaestroPedidos.getColumnModel().getColumn(4).setMaxWidth(110);
		tblMaestroPedidos.getColumnModel().getColumn(6).setMaxWidth(0);
		tblMaestroPedidos.getColumnModel().getColumn(6).setMinWidth(0);
		tblMaestroPedidos.getColumnModel().getColumn(7).setMaxWidth(0);
		tblMaestroPedidos.getColumnModel().getColumn(7).setMinWidth(0);
		tblMaestroPedidos.getColumnModel().getColumn(8).setMinWidth(90);
		tblMaestroPedidos.getColumnModel().getColumn(8).setMaxWidth(90);
		//Modificamos el ancho del la Tiempo Pedido
		tblMaestroPedidos.getColumnModel().getColumn(9).setMinWidth(20);
		tblMaestroPedidos.getColumnModel().getColumn(9).setMaxWidth(20);
		//Modificamos el ancho del la muestra del tiempo
		tblMaestroPedidos.getColumnModel().getColumn(10).setMinWidth(150);
		tblMaestroPedidos.getColumnModel().getColumn(10).setMaxWidth(150);
		
		
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(60);
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(0).setMinWidth(60);
		//Ocultamos la conlumna de FechaPedido, dado que es la misma dependiendo del día aperturado
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(0);
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(1).setMinWidth(0);
		//Modificamos el ancho de la columna nombre
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(2).setMinWidth(120);
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(120);
		//Modificamos ancho del tipo de pedido
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(3).setMinWidth(110);
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(110);
		//Modificamos ancho del estado pedido
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(4).setMinWidth(110);
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(4).setMaxWidth(110);
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(6).setMaxWidth(0);
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(6).setMinWidth(0);
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(7).setMaxWidth(0);	
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(7).setMinWidth(0);
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(8).setMinWidth(90);
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(8).setMaxWidth(90);
		//Modificamos el ancho del tiempo dado pedido
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(9).setMinWidth(20);
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(9).setMaxWidth(20);
		//Modificamos el ancho del la muestra del tiempo
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(10).setMinWidth(150);
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(10).setMaxWidth(150);

		tblMaestroPedidos.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tblMaestroPedidos.getColumnModel().getColumn(3).setCellRenderer( new CellRenderTransaccional2());
		tblMaestroPedidos.getColumnModel().getColumn(4).setCellRenderer( new CellRenderTransaccional2());
		tblMaestroPedidos.getColumnModel().getColumn(2).setCellRenderer( new CellRenderTransaccional2());
		tblMaestroPedidos.getColumnModel().getColumn(5).setCellRenderer( new CellRenderTransaccional2());
		tblMaestroPedidos.getColumnModel().getColumn(10).setCellRenderer( new CellRenderTransaccionalTiempos());
		//setCellRender(tblMaestroPedidos);
		
	}
	
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
				 	Thread.sleep(25000);
			 }catch(InterruptedException e) 
			 {}
			 //Ejecutamos el pintado de los pedidos en el JTable de la pantalla.
			 pintarPedidos();
		  //Realizamos la ejecución cada 30 segundos
			
		 }
	}
	
	/**
	 * Método encargado de la accion de devolver estado para un pedido seleccionado
	 */
	public void devolverEstado()
	{
		int filaSeleccionada = tblMaestroPedidos.getSelectedRow();
		if(filaSeleccionada == -1)
		{
			JOptionPane.showMessageDialog(null, "Debe Seleccionar algún pedido para Devolver Estado " , "No ha Seleccionado Pedido", JOptionPane.ERROR_MESSAGE);
			return;
		}
		// Se captura el valor del idDetalle que se desea eliminar
		int idPedidoDevolver= Integer.parseInt(tblMaestroPedidos.getValueAt(filaSeleccionada, 0).toString());
		int idTipoPedido = Integer.parseInt(tblMaestroPedidos.getValueAt(filaSeleccionada, 6).toString());
		int idEstado = Integer.parseInt(tblMaestroPedidos.getValueAt(filaSeleccionada, 7).toString());
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
				VentPedCambioEstado cambioEstado = new VentPedCambioEstado(idPedidoDevolver, true, false, null, true,/* OJO HAY QUE NORMALIZAR*/0,50);
				cambioEstado.setVisible(true);
				pintarPedidos();
			}else
			{
				JOptionPane.showMessageDialog(null, "Su perfil de usuario no tiene acceso a esta opción/pantalla" , "Ingreso no permitido", JOptionPane.ERROR_MESSAGE);
			}
			
		}
	}
	
	/**
	 * Método encargado de desplegar la accion con nueva ventana incluida para la toma de nuevos pedidos
	 */
	public void avanzarEstado()
	{
		int filaSeleccionada = tblMaestroPedidos.getSelectedRow();
		if(filaSeleccionada == -1)
		{
			JOptionPane.showMessageDialog(null, "Debe Seleccionar algún pedido para Avanzar Estado " , "No ha Seleccionado Pedido", JOptionPane.ERROR_MESSAGE);
			return;
		}
		// Se captura el valor del idDetalle que se desea eliminar
		int idPedidoAvanzar= Integer.parseInt(tblMaestroPedidos.getValueAt(filaSeleccionada, 0).toString());
		int idTipoPedido = Integer.parseInt(tblMaestroPedidos.getValueAt(filaSeleccionada, 6).toString());
		int idEstado = Integer.parseInt(tblMaestroPedidos.getValueAt(filaSeleccionada, 7).toString());
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
				VentPedCambioEstado cambioEstado = new VentPedCambioEstado(idPedidoAvanzar, false, true, null, true,/* OJO HAY QUE NORMALIZAR*/0,50);
				cambioEstado.setVisible(true);
				pintarPedidos();
			}else
			{
				JOptionPane.showMessageDialog(null, "Su perfil de usuario no tiene acceso a esta opción/pantalla" , "Ingreso no permitido", JOptionPane.ERROR_MESSAGE);
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
