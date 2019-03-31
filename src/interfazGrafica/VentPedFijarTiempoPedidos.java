package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import capaControlador.OperacionesTiendaCtrl;
import capaControlador.ParametrosCtrl;
import capaControlador.PedidoCtrl;
import capaDAO.GeneralDAO;
import capaModelo.Correo;
import capaModelo.FechaSistema;
import capaModelo.Parametro;
import capaModelo.Tienda;
import utilidades.ControladorEnvioCorreo;

import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Desktop;

import javax.swing.JLabel;

public class VentPedFijarTiempoPedidos extends JDialog implements Runnable {

	private JPanel contentPane;
	private JTextField txtTiempoPedido;
	private ParametrosCtrl parCtrl = new ParametrosCtrl(PrincipalLogueo.habilitaAuditoria);
	private OperacionesTiendaCtrl operTiendaCtrl = new OperacionesTiendaCtrl(PrincipalLogueo.habilitaAuditoria);
	private PedidoCtrl pedCtrl = new PedidoCtrl(PrincipalLogueo.habilitaAuditoria);
	private int tiempoPedido = 0;
	Thread h1;
	private boolean banderaEjecucion = true;
	JButton btn10;
	JButton btn15;
	JButton btn20;
	JButton btn25;
	JButton btn30;
	JButton btn35;
	JButton btn40;
	JButton btn45;
	JButton btn50;
	JButton btn55;
	JButton btn60;
	JButton btn65;
	JButton btn70;
	JButton btn75;
	JButton btn80;
	JButton btn85;
	JButton btn90;
	JButton btn95;
	JButton btn100;
	JButton btn105;
	private JButton btnSalir;
	private JTextField txtEstadoTienda;
	boolean estadoTienda;
	private JButton lblColorEstadoTienda;
	private JTextField txtEnEspera;
	private JTextField txtEnRuta;
	private JTextField txtPuntoVenta;
	//Definición de los estados de producto
	//variable para estado en ruta domicilios
	private final long estEnRutaDom;
	//variable para estado pendiente domicilios
	private final long estPenDomicilio;
	private FechaSistema fecha;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentPedFijarTiempoPedidos frame = new VentPedFijarTiempoPedidos(null, true);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public void fijarValorTiempoPedido()
	{
		//Tomamos el valor del parámetro relacionado la interface gráfica
		Parametro parametro = parCtrl.obtenerParametro("TIEMPOPEDIDO");
		try
		{
			tiempoPedido = parametro.getValorNumerico();
		}catch(Exception e)
		{
			System.out.println("SE TUVO ERROR TOMANDO EL TIEMPO PEDIDO DEL SISTEMA");
			tiempoPedido = 0;
		}
		if(tiempoPedido == 0)
		{
			tiempoPedido =50;
		}
		//Luego de obtenido el tiempo, es necesario dejarlo en la pantalla puesto
		txtTiempoPedido.setText(Integer.toString(tiempoPedido));
		if (tiempoPedido == 10)
		{
			btn10.setBackground(Color.GREEN);
		}else if(tiempoPedido == 15)
		{
			btn15.setBackground(Color.GREEN);
		}else if(tiempoPedido == 20)
		{
			btn20.setBackground(Color.GREEN);
		}else if(tiempoPedido == 25)
		{
			btn25.setBackground(Color.GREEN);
		}else if(tiempoPedido == 30)
		{
			btn30.setBackground(Color.GREEN);
		}else if(tiempoPedido == 35)
		{
			btn35.setBackground(Color.GREEN);
		}else if(tiempoPedido == 40)
		{
			btn40.setBackground(Color.GREEN);
		}else if(tiempoPedido == 45)
		{
			btn45.setBackground(Color.GREEN);
		}else if(tiempoPedido == 50)
		{
			btn50.setBackground(Color.GREEN);
		}else if(tiempoPedido == 55)
		{
			btn55.setBackground(Color.GREEN);
		}else if(tiempoPedido == 60)
		{
			btn60.setBackground(Color.ORANGE);
		}else if(tiempoPedido == 65)
		{
			btn65.setBackground(Color.ORANGE);
		}else if(tiempoPedido == 70)
		{
			btn70.setBackground(Color.ORANGE);
		}else if(tiempoPedido == 75)
		{
			btn75.setBackground(Color.ORANGE);
		}else if(tiempoPedido == 80)
		{
			btn80.setBackground(Color.ORANGE);
		}else if(tiempoPedido == 85)
		{
			btn85.setBackground(Color.RED);
		}else if(tiempoPedido == 90)
		{
			btn90.setBackground(Color.RED);
		}else if(tiempoPedido == 95)
		{
			btn95.setBackground(Color.RED);
		}else if(tiempoPedido == 100)
		{
			btn100.setBackground(Color.RED);
		}else if(tiempoPedido == 105)
		{
			btn105.setBackground(Color.RED);
		}
	}
	
	public void cargarEstadoTienda()
	{
		estadoTienda = operTiendaCtrl.retornarEstadoTienda();
		if(estadoTienda)
		{
			txtEstadoTienda.setText("DISPONIBLE");
			lblColorEstadoTienda.setBackground(Color.GREEN);
		}else
		{
			txtEstadoTienda.setText("NO DISPONIBLE");
			lblColorEstadoTienda.setBackground(Color.RED);
		}
	}
	
	public void actualizarEstadoPedidosTienda()
	{
		txtEnEspera.setText(Integer.toString(pedCtrl.obtenerCantidadPedidoPorEstado(fecha.getFechaApertura(), (int)estPenDomicilio)));
		txtEnRuta.setText(Integer.toString(pedCtrl.obtenerCantidadPedidoPorEstado(fecha.getFechaApertura(), (int)estEnRutaDom)));
	}
	
	/**
	 * Create the frame.
	 */
	public VentPedFijarTiempoPedidos(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		setTitle("ADMINISTRACI\u00D3N DE TIEMPOS DE PEDIDO");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 859, 600);
		int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	    int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		setBounds((ancho / 2) - (this.getWidth() / 2), (alto / 2) - (this.getHeight() / 2), 870, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtTiempoPedido = new JTextField();
		txtTiempoPedido.setEditable(false);
		txtTiempoPedido.setFont(new Font("Tahoma", Font.BOLD, 20));
		txtTiempoPedido.setBounds(159, 12, 82, 51);
		contentPane.add(txtTiempoPedido);
		txtTiempoPedido.setColumns(10);
		
		JPanel panelFijarTiempos = new JPanel();
		panelFijarTiempos.setBounds(77, 73, 418, 361);
		contentPane.add(panelFijarTiempos);
		panelFijarTiempos.setLayout(new GridLayout(0, 5, 0, 0));
		//Inicilizamos las variables con constantes de los valores de pedidos
		Parametro parametro = parCtrl.obtenerParametro("ENRUTADOMICILIO");
		long valNum = 0;
		try
		{
			valNum = (long) parametro.getValorNumerico();
		}catch(Exception e)
		{
			System.out.println("SE TUVO ERROR TOMANDO LA CONSTANTE DE PEDIDOS EN RUTA");
			valNum = 0;
		}
		estEnRutaDom = valNum;
		//
		parametro = parCtrl.obtenerParametro("EMPACADODOMICILIO");
		try
		{
			valNum = (long) parametro.getValorNumerico();
		}catch(Exception e)
		{
			System.out.println("SE TUVO ERROR TOMANDO LA CONSTANTE DE PEDIDOS PENDIENTES");
			valNum = 0;
		}
		estPenDomicilio = valNum;
		fecha = pedCtrl.obtenerFechasSistema();
		
		btn10 = new JButton("10");
		btn10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quitarBackgroundBotones();
				btn10.setBackground(Color.GREEN);
				txtTiempoPedido.setText("10");
			}
		});
		btn10.setFont(new Font("Tahoma", Font.BOLD, 23));
		panelFijarTiempos.add(btn10);
		
		btn15 = new JButton("15");
		btn15.setFont(new Font("Tahoma", Font.BOLD, 22));
		btn15.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quitarBackgroundBotones();
				btn15.setBackground(Color.GREEN);
				txtTiempoPedido.setText("15");
			}
		});
		panelFijarTiempos.add(btn15);
		
		btn20 = new JButton("20");
		btn20.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quitarBackgroundBotones();
				btn20.setBackground(Color.GREEN);
				txtTiempoPedido.setText("20");
			}
		});
		btn20.setFont(new Font("Tahoma", Font.BOLD, 22));
		panelFijarTiempos.add(btn20);
		
		btn25 = new JButton("25");
		btn25.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quitarBackgroundBotones();
				btn25.setBackground(Color.GREEN);
				txtTiempoPedido.setText("25");
			}
		});
		btn25.setFont(new Font("Tahoma", Font.BOLD, 22));
		panelFijarTiempos.add(btn25);
		
		btn30 = new JButton("30");
		btn30.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quitarBackgroundBotones();
				btn30.setBackground(Color.GREEN);
				txtTiempoPedido.setText("30");
			}
		});
		btn30.setFont(new Font("Tahoma", Font.BOLD, 22));
		panelFijarTiempos.add(btn30);
		
		btn35 = new JButton("35");
		btn35.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quitarBackgroundBotones();
				btn35.setBackground(Color.GREEN);
				txtTiempoPedido.setText("35");
			}
		});
		btn35.setFont(new Font("Tahoma", Font.BOLD, 22));
		panelFijarTiempos.add(btn35);
		
		btn40 = new JButton("40");
		btn40.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quitarBackgroundBotones();
				btn40.setBackground(Color.GREEN);
				txtTiempoPedido.setText("40");
			}
		});
		btn40.setFont(new Font("Tahoma", Font.BOLD, 22));
		panelFijarTiempos.add(btn40);
		
		btn45 = new JButton("45");
		btn45.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quitarBackgroundBotones();
				btn45.setBackground(Color.GREEN);
				txtTiempoPedido.setText("45");
			}
		});
		btn45.setFont(new Font("Tahoma", Font.BOLD, 22));
		panelFijarTiempos.add(btn45);
		
		btn50 = new JButton("50");
		btn50.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quitarBackgroundBotones();
				btn50.setBackground(Color.GREEN);
				txtTiempoPedido.setText("50");
			}
		});
		btn50.setFont(new Font("Tahoma", Font.BOLD, 22));
		panelFijarTiempos.add(btn50);
		
		btn55 = new JButton("55");
		btn55.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quitarBackgroundBotones();
				btn55.setBackground(Color.GREEN);
				txtTiempoPedido.setText("55");
			}
		});
		btn55.setFont(new Font("Tahoma", Font.BOLD, 22));
		panelFijarTiempos.add(btn55);
		
		btn60 = new JButton("60");
		btn60.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quitarBackgroundBotones();
				btn60.setBackground(Color.ORANGE);
				txtTiempoPedido.setText("60");
			}
		});
		btn60.setFont(new Font("Tahoma", Font.BOLD, 22));
		panelFijarTiempos.add(btn60);
		
		btn65 = new JButton("65");
		btn65.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quitarBackgroundBotones();
				btn65.setBackground(Color.ORANGE);
				txtTiempoPedido.setText("65");
			}
		});
		btn65.setFont(new Font("Tahoma", Font.BOLD, 22));
		panelFijarTiempos.add(btn65);
		
		btn70 = new JButton("70");
		btn70.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quitarBackgroundBotones();
				btn70.setBackground(Color.ORANGE);
				txtTiempoPedido.setText("70");
			}
		});
		btn70.setFont(new Font("Tahoma", Font.BOLD, 22));
		panelFijarTiempos.add(btn70);
		
		btn75 = new JButton("75");
		btn75.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quitarBackgroundBotones();
				btn75.setBackground(Color.ORANGE);
				txtTiempoPedido.setText("75");
			}
		});
		btn75.setFont(new Font("Tahoma", Font.BOLD, 22));
		panelFijarTiempos.add(btn75);
		
		btn80 = new JButton("80");
		btn80.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quitarBackgroundBotones();
				btn80.setBackground(Color.ORANGE);
				txtTiempoPedido.setText("80");
			}
		});
		btn80.setFont(new Font("Tahoma", Font.BOLD, 22));
		panelFijarTiempos.add(btn80);
		
		btn85 = new JButton("85");
		btn85.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quitarBackgroundBotones();
				btn85.setBackground(Color.RED);
				txtTiempoPedido.setText("85");
			}
		});
		btn85.setFont(new Font("Tahoma", Font.BOLD, 22));
		panelFijarTiempos.add(btn85);
		
		btn90 = new JButton("90");
		btn90.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quitarBackgroundBotones();
				btn90.setBackground(Color.RED);
				txtTiempoPedido.setText("90");
			}
		});
		btn90.setFont(new Font("Tahoma", Font.BOLD, 22));
		panelFijarTiempos.add(btn90);
		
		btn95 = new JButton("95");
		btn95.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quitarBackgroundBotones();
				btn95.setBackground(Color.RED);
				txtTiempoPedido.setText("95");
			}
		});
		btn95.setFont(new Font("Tahoma", Font.BOLD, 22));
		panelFijarTiempos.add(btn95);
		
		btn100 = new JButton("100");
		btn100.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quitarBackgroundBotones();
				btn100.setBackground(Color.RED);
				txtTiempoPedido.setText("100");
			}
		});
		btn100.setFont(new Font("Tahoma", Font.BOLD, 22));
		panelFijarTiempos.add(btn100);
		
		btn105 = new JButton("105");
		btn105.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quitarBackgroundBotones();
				btn105.setBackground(Color.RED);
				txtTiempoPedido.setText("105");
			}
		});
		btn105.setFont(new Font("Tahoma", Font.BOLD, 22));
		panelFijarTiempos.add(btn105);
		
		JButton btnNivel1 = new JButton("");
		btnNivel1.setBackground(Color.GREEN);
		btnNivel1.setBounds(27, 72, 39, 90);
		contentPane.add(btnNivel1);
		
		JButton btnNivel2 = new JButton("");
		btnNivel2.setBackground(Color.GREEN);
		btnNivel2.setBounds(27, 162, 39, 90);
		contentPane.add(btnNivel2);
		
		JButton btnNivel3 = new JButton("");
		btnNivel3.setBackground(Color.ORANGE);
		btnNivel3.setBounds(27, 253, 39, 90);
		contentPane.add(btnNivel3);
		
		JButton btnNivel4 = new JButton("");
		btnNivel4.setBackground(Color.RED);
		btnNivel4.setBounds(27, 344, 39, 90);
		contentPane.add(btnNivel4);
		
		JLabel lblMinutos = new JLabel("MINUTOS");
		lblMinutos.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblMinutos.setBounds(269, 24, 138, 23);
		contentPane.add(lblMinutos);
		
		JButton btnFijarTiempo = new JButton("FIJAR TIEMPO");
		btnFijarTiempo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String strTiempoPedido = txtTiempoPedido.getText();
				//Fijamos el tiempo en base de datos actualizando la tabla de parámetros
				Parametro parametroEditado = new Parametro("TIEMPOPEDIDO", Integer.parseInt(strTiempoPedido), "");
				boolean respuesta = parCtrl.EditarParametro(parametroEditado);				
				//Buscamos la URL que nos servirá como buscaador
				//Traemos de base de datos el valor del parametro de auditoria
				Tienda tienda = parCtrl.obtenerTiendaObj();
				//Extraemos el valor del campo de ValorTexto
				String rutaURL = tienda.getUrlContact() + "CRUDTiempoPedido?idoperacion=1&nuevotiempo=" + strTiempoPedido+ "&idtienda=" + tienda.getIdTienda();
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
				//Verificamos el nuevo tiempo si esté está por encima de un parámetro se envía correo eletrónico
				int valNuevoTiempo = Integer.parseInt(strTiempoPedido);
				//Recuperamos el valor de la variable máximo tiempo para alertar tiempos pedidos
				Parametro parametro = parCtrl.obtenerParametro("TIEMPOMAXIMOALERTA");
				long valNum = 0;
				try
				{
					valNum = (long) parametro.getValorNumerico();
				}catch(Exception e)
				{
					System.out.println("SE TUVO ERROR TOMANDO LA CONSTANTE DE TIEMPOMAXIMOALERTA");
					valNum = 0;
				}
				if(valNuevoTiempo >= valNum)
				{
					Correo correo = new Correo();
					correo.setAsunto("ALERTA TIEMPOS PEDIDO");
					ArrayList correos = GeneralDAO.obtenerCorreosParametro("TIEMPOPEDIDO", PrincipalLogueo.habilitaAuditoria);
					correo.setContrasena("Pizzaamericana2017");
					correo.setUsuarioCorreo("alertaspizzaamericana@gmail.com");
					correo.setMensaje("La tienda " + tienda.getNombretienda() + " está aumentando el tiempo de entrega a " + valNuevoTiempo + " minutos");
					ControladorEnvioCorreo contro = new ControladorEnvioCorreo(correo, correos);
					contro.enviarCorreo();
				}
				dispose();
				
			}
		});
		btnFijarTiempo.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnFijarTiempo.setBounds(60, 460, 220, 44);
		contentPane.add(btnFijarTiempo);
		
		btnSalir = new JButton("SALIR");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				banderaEjecucion = false;
				dispose();
			}
		});
		btnSalir.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnSalir.setBounds(354, 460, 138, 44);
		contentPane.add(btnSalir);
		
		JLabel lblEstadoTienda = new JLabel("ESTADO DE LA TIENDA");
		lblEstadoTienda.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblEstadoTienda.setBounds(505, 87, 247, 23);
		contentPane.add(lblEstadoTienda);
		
		txtEstadoTienda = new JTextField();
		txtEstadoTienda.setEditable(false);
		txtEstadoTienda.setFont(new Font("Tahoma", Font.BOLD, 20));
		txtEstadoTienda.setBounds(515, 121, 209, 41);
		contentPane.add(txtEstadoTienda);
		txtEstadoTienda.setColumns(10);
		
		JButton btnActDesTienda = new JButton("<html><center>Activar/Desactivar TIenda</center></html>");
		btnActDesTienda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Traemos de base de datos el valor del parametro de auditoria
				Tienda tienda = parCtrl.obtenerTiendaObj();
				if(estadoTienda)
				{
					boolean resultado  = operTiendaCtrl.desactivarEstadoTienda(tienda.getNombretienda());
					if(resultado)
					{
						estadoTienda = false;
						txtEstadoTienda.setText("NO DISPONIBLE");
						lblColorEstadoTienda.setBackground(Color.RED);
						//Consumo el servicio en el contact Center
						//Consumo el servicio en el contact Center
						
						//Extraemos el valor del campo de ValorTexto
						String rutaURL = tienda.getUrlContact() + "CRUDTiendaBloqueada?idoperacion=1&idtienda=" + tienda.getIdTienda() + "&comentario=";
						URL url=null;
						try {
						    url = new URL(rutaURL);
						    try {
						        Desktop.getDesktop().browse(url.toURI());
						    } catch (IOException e) {
						        e.printStackTrace();
						        System.out.println(e.toString());
						    } catch (URISyntaxException e) {
						        e.printStackTrace();
						        System.out.println(e.toString());
						    }
						} catch (MalformedURLException e1) {
						    e1.printStackTrace();
						    System.out.println(e1.toString());
						}

					}
				}else
				{
					boolean resultado  = operTiendaCtrl.activarEstadoTienda(tienda.getNombretienda());
					if(resultado)
					{
						estadoTienda = true;
						txtEstadoTienda.setText("DISPONIBLE");
						lblColorEstadoTienda.setBackground(Color.GREEN);
						//Consumo el servicio en el contact Center
						//Extraemos el valor del campo de ValorTexto
						String rutaURL = tienda.getUrlContact() + "CRUDTiendaBloqueada?idoperacion=3&idtienda=" + tienda.getIdTienda();
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
				}
			}
		});
		btnActDesTienda.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnActDesTienda.setBounds(515, 253, 209, 97);
		contentPane.add(btnActDesTienda);
		
		lblColorEstadoTienda = new JButton("");
		lblColorEstadoTienda.setBounds(513, 172, 211, 51);
		contentPane.add(lblColorEstadoTienda);
		
		JPanel panelInfoPedidos = new JPanel();
		panelInfoPedidos.setBounds(515, 373, 329, 177);
		contentPane.add(panelInfoPedidos);
		panelInfoPedidos.setLayout(null);
		
		JLabel lblPedidosEspera = new JLabel("# Pedidos En Espera");
		lblPedidosEspera.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPedidosEspera.setBounds(10, 22, 156, 23);
		panelInfoPedidos.add(lblPedidosEspera);
		
		JLabel lblPedidosRuta = new JLabel("# Pedidos En Ruta");
		lblPedidosRuta.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPedidosRuta.setBounds(10, 69, 156, 23);
		panelInfoPedidos.add(lblPedidosRuta);
		
		JLabel lblPedidosPV = new JLabel("# Pedidos punto de venta (\u00FAlt 30 min)");
		lblPedidosPV.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPedidosPV.setBounds(10, 111, 221, 23);
		panelInfoPedidos.add(lblPedidosPV);
		
		txtEnEspera = new JTextField();
		txtEnEspera.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtEnEspera.setBounds(233, 11, 86, 32);
		panelInfoPedidos.add(txtEnEspera);
		txtEnEspera.setColumns(10);
		
		txtEnRuta = new JTextField();
		txtEnRuta.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtEnRuta.setColumns(10);
		txtEnRuta.setBounds(233, 54, 86, 36);
		panelInfoPedidos.add(txtEnRuta);
		
		txtPuntoVenta = new JTextField();
		txtPuntoVenta.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtPuntoVenta.setColumns(10);
		txtPuntoVenta.setBounds(233, 101, 86, 31);
		panelInfoPedidos.add(txtPuntoVenta);
		fijarValorTiempoPedido();
		cargarEstadoTienda();
		actualizarEstadoPedidosTienda();
		h1 = new Thread(this);
		h1.start();
	}
	
	public void run(){
		 Thread ct = Thread.currentThread();
		 while(banderaEjecucion) 
		 {   
			 try {
				 	Thread.sleep(15000);
			 }catch(InterruptedException e) 
			 {}
			 fijarValorTiempoPedido();
			 cargarEstadoTienda();
			 actualizarEstadoPedidosTienda();
		 }
	}
	
	public void quitarBackgroundBotones()
	{
		 btn10.setBackground(null);
		 btn15.setBackground(null);
		 btn20.setBackground(null);
		 btn25.setBackground(null);
		 btn30.setBackground(null);
		 btn35.setBackground(null);
		 btn40.setBackground(null);
		 btn45.setBackground(null);
		 btn50.setBackground(null);
		 btn55.setBackground(null);
		 btn60.setBackground(null);
		 btn65.setBackground(null);
		 btn70.setBackground(null);
		 btn75.setBackground(null);
		 btn80.setBackground(null);
		 btn85.setBackground(null);
		 btn90.setBackground(null);
		 btn95.setBackground(null);
		 btn100.setBackground(null);
		 btn105.setBackground(null);
	}
}
