package interfazGrafica;


import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import capaModelo.DetallePedido;
import capaModelo.EleccionForzada;
import capaModelo.EleccionForzadaBoton;
import capaModelo.EleccionForzadaTemporal;
import capaModelo.Pregunta;
import capaModelo.Producto;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.border.LineBorder;

import capaControlador.ParametrosProductoCtrl;
import capaControlador.PedidoCtrl;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class VentProEleccionForzada extends JDialog {

	//Variables est�ticas
	private static int numPreguntas;
	private static int preguntaActual = 0;
	//Otras variables
	private JPanel contenedorPrincipal;
	JLabel lblDescripcionPregunta;
	private JPanel panelRespuestas1;
	private JPanel panelRespuestas2;
	JButton jButElecciones1;
	JButton jButElecciones2;
	private JLabel lblRespuestas1;
	private JLabel lblRespuestas2;
	private ArrayList<EleccionForzadaBoton> arregloBotPan1;
	private ArrayList<EleccionForzadaBoton> arregloBotPan2;
	private ArrayList<EleccionForzada> elecciones;
	private ArrayList<Pregunta> preguntasPantalla;
	private ArrayList<EleccionForzadaTemporal> eleccionesTemporales = new ArrayList();
	private Pregunta pregActual;
	private JButton btnRetornarPregunta;
	//Par�metros valiosos para la Pregunta
	int numMaxElecciones = 0;
	int permDividir = 0;
	boolean obligEleccion = false;
	int selMitad1 = 0;
	int selMitad2 = 0;
	boolean permiteDividir = false;
	private PedidoCtrl pedCtrl = new PedidoCtrl(PrincipalLogueo.habilitaAuditoria);
	private ParametrosProductoCtrl parProductoCtrl = new ParametrosProductoCtrl(PrincipalLogueo.habilitaAuditoria);
	ArrayList<Producto> productos =parProductoCtrl.obtenerProductosCompleto();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//No tiene sentido instanciarlos de por si mismo.
					//VentEleccionForzada frame = new VentEleccionForzada();
					//frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * M�todo que se encarga de la instanciaci�n de la ventana de Elecci�n Forzada
	 * @param preguntas se recibe un ArrayList con las preguntas forzadas que se van a controlar
	 * @param idProducto se recibe el producto al cual se le est�n aplicando la Elecci�n Forzada.
	 */
	public VentProEleccionForzada(java.awt.Frame parent, boolean modal,ArrayList<Pregunta> preguntas, int idProducto) {
		super(parent, modal);
		/**
		 * Se realiza la definici�n para cuando se cierre la ventana de las elecciones forzadas se realice el borrado del detallePedidoMaster del producto
		 * al cual se le est�n agregando las preguntas forzadas.
		 */
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				//Trabajamos el evento de se cierre la ventan y no se haga la elecci�n
				//Con base en la pantalla de Toma pedidos del idDetallePedidoMaster realizamos la eliminaci�n
				pedCtrl.eliminarDetallePedido(VentPedTomarPedidos.idDetallePedidoMaster);
				int idDetalleEliminar = VentPedTomarPedidos.idDetallePedidoMaster;
				for(int j = 0; j < VentPedTomarPedidos.detallesPedido.size(); j++)
				{
					DetallePedido detCadaPedido = VentPedTomarPedidos.detallesPedido.get(j);
					// Cambiamos para la eliminaci�n que se tenga el iddetalle_pedido o el iddetalle_pedido_master
					if(detCadaPedido.getIdDetallePedido() == idDetalleEliminar || detCadaPedido.getIdDetallePedidoMaster() == idDetalleEliminar)
					{
						double valorItem = detCadaPedido.getValorTotal();
						VentPedTomarPedidos.detallesPedido.remove(j);
						VentPedTomarPedidos.totalPedido = VentPedTomarPedidos.totalPedido - valorItem;
					}
				}
				preguntaActual = 0;
			}
		});
		setTitle("ELECCI\u00D3N");
		numPreguntas = preguntas.size();
		preguntaActual = 0;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0,0, 950, 685);
		int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	    int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		setBounds((ancho / 2) - (this.getWidth() / 2), (alto / 2) - (this.getHeight() / 2), 950, 685);
		contenedorPrincipal = new JPanel();
		contenedorPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contenedorPrincipal);
		contenedorPrincipal.setLayout(null);
		arregloBotPan2 = new ArrayList();
		arregloBotPan1 = new ArrayList();
		panelRespuestas1 = new JPanel();
		panelRespuestas1.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		panelRespuestas1.setBounds(25, 35, 899, 264);
		contenedorPrincipal.add(panelRespuestas1);
		panelRespuestas1.setLayout(new GridLayout(0, 7, 0, 0));
		
		lblDescripcionPregunta = new JLabel("New label");
		lblDescripcionPregunta.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblDescripcionPregunta.setBounds(10, 594, 318, 32);
		contenedorPrincipal.add(lblDescripcionPregunta);
		
		panelRespuestas2 = new JPanel();
		panelRespuestas2.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		panelRespuestas2.setBounds(25, 335, 899, 264);
		contenedorPrincipal.add(panelRespuestas2);
		panelRespuestas2.setLayout(new GridLayout(0, 7, 0, 0));
		
		lblRespuestas1 = new JLabel("New label");
		lblRespuestas1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblRespuestas1.setBounds(25, 11, 621, 14);
		contenedorPrincipal.add(lblRespuestas1);
		
		lblRespuestas2 = new JLabel("New label");
		lblRespuestas2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblRespuestas2.setBounds(25, 310, 621, 14);
		contenedorPrincipal.add(lblRespuestas2);
		
		JButton btnConfirmarPregunta = new JButton("");
		btnConfirmarPregunta.setBackground(Color.BLACK);
		btnConfirmarPregunta.setIcon(new ImageIcon(VentProEleccionForzada.class.getResource("/icons/preguntaSiguiente.jpg")));
		btnConfirmarPregunta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(obligEleccion == true)
				{
					if(permiteDividir)
					{
						if((numMaxElecciones == selMitad1)&(numMaxElecciones == selMitad2))
						{
							
						}
						else
						{
							JOptionPane.showMessageDialog(null, "La pregunta obliga la selecci�n de  " + numMaxElecciones + " opci�n/es por Divisi�n y tienes seleccionado " + selMitad1 +" opci�n/es en la mitad1 y " + selMitad2 +" opci�n/es. en la mitad 2 , esto �ltimo no se est� respetando MITAD/MITAD." , " Falta Selecci�n de Opciones", JOptionPane.ERROR_MESSAGE);
							return;
						}
					}
					else
					{
						if((numMaxElecciones == selMitad1))
						{
							
						}
						else
						{
							JOptionPane.showMessageDialog(null, "La pregunta obliga la selecci�n de  " + numMaxElecciones + " opci�n/es y solo tienes seleccionado " + selMitad1 + " opci�n/es." , " Falta Selecci�n de Opciones", JOptionPane.ERROR_MESSAGE);
							return;
						}
					}
				}
				incluirProductos();
				if(preguntaActual == preguntasPantalla.size())
				{
					AdicionarTomarPedidos();
					if(VentPedTomarPedidos.esAnulado)
					{
						VentPedTomarPedidos.esAnulado = false;
					}
					preguntaActual = 0;	
					VentPedTomarPedidos.contadorDetallePedido++;
					dispose();
				}
				CargarEleccionForzada();
			}
		});
		btnConfirmarPregunta.setBounds(627, 601, 147, 35);
		contenedorPrincipal.add(btnConfirmarPregunta);
		
		btnRetornarPregunta = new JButton("");
		btnRetornarPregunta.setBackground(Color.BLACK);
		btnRetornarPregunta.setIcon(new ImageIcon(VentProEleccionForzada.class.getResource("/icons/preguntaAnterior.jpg")));
		btnRetornarPregunta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Realizamos actividad para guardar la elecci�n actual para cuando regresemos
				incluirProductos();
				//L�gica para retornar a la anterior pregunta forzada
				int preguntaTemp = preguntaActual - 1;
				if (preguntaTemp == 0)
				{
					
				}
				if (preguntaTemp > 0)
				{
					preguntaActual = preguntaActual -2;
					CargarEleccionForzada();
				}
			}
		});
		btnRetornarPregunta.setBounds(440, 601, 147, 35);
		contenedorPrincipal.add(btnRetornarPregunta);
		//Debemos de definir las cosas para el cargue de la primera pregunta
		numPreguntas = preguntas.size();
		preguntasPantalla = preguntas;
		CargarEleccionForzada();
	}
	
	/**
	 * M�todo principal del esta clase qeu se encarga del despliegue de las preguntas forzadas y todo el control y adici�n de botones para la secci�n.
	 * @param pregunta, se recibe el n�mero de pregunta que se va a controlar, teniendo en cuenta que pueden ser hasta 5 y se deben llevar de manera secuencial.
	 */
	public void CargarEleccionForzada()
	{
		//Realizamos un limpiado de la informaci�n de los paneles
		panelRespuestas1.removeAll();
		panelRespuestas1.repaint();
		panelRespuestas2.removeAll();
		panelRespuestas2.repaint();
		//Es necesario clarear las selecciones mitad
		selMitad1 = 0;
		selMitad2 = 0;
		//Realizamos modificaciones para adicionar al pedido
		//En caso de cumplirse esto deberemos de pasar el pedido a la pantalla tomador de pedidos
		if (preguntaActual == numPreguntas)
		{
			//el �ltimo paso a realizar es liberar el objeto
			preguntaActual = 0;
			dispose();
			return;
		}
		//Se realizan las acciones de display para elecci�n de la informaci�n
		pregActual = preguntasPantalla.get(preguntaActual);
		numMaxElecciones =  pregActual.getNumeroMaximoEleccion();
		permDividir = pregActual.getPermiteDividir();
		int intObligEleccion = pregActual.getObligaEleccion();
		if (intObligEleccion == 0)
		{
			obligEleccion = false;
		}
		else
		{
			obligEleccion = true;
		}
		//Personalizamos el comportamiento de la pantalla
		//Titulo
		setTitle(pregActual.getTituloPregunta());
		lblDescripcionPregunta.setText(pregActual.getDescripcion());
		//Tomamos l�gica para validar si la Pregunta permite Division o no
		if(permDividir >= 2)
		{
			permiteDividir = true;
		}else
		{
			permiteDividir = false;
		}
		elecciones = parProductoCtrl.obtEleccionesForzadas(pregActual.getIdPregunta());
		boolean esGaseosa = false;
		//Adicionamos los botones con las preguntas forzadas
		if(permiteDividir)
		{
			
			lblRespuestas1.setText("Elecci�n Mitad 1");
			lblRespuestas2.setText("Elecci�n Mitad 2");
			EleccionForzadaBoton boton1;
			EleccionForzadaBoton boton2;
			for(int j = 0; j < elecciones.size(); j++)
			{
				EleccionForzada eleccion = elecciones.get(j);
				jButElecciones1 = new JButton("<html><center>" + eleccion.getDescripcion() + "</center></html>");
				//En este punto nos traemos el producto para verfiicar si es un gaseosa
				//Realizamos optimizacion para evitar la ida  base de datos por cada producto
				//definimos el objeto Receptor
				Producto prodPintar = new Producto();
				//Recorremos el arrayList con los productos tra�dos de la base de datos
				for(int z = 0; z < productos.size(); z++)
				{
					Producto proTemp = productos.get(z);
					//Si se encuentra el objeto en el arrelgo tomamos el objeto y salimos del ciclo for
					if(proTemp.getIdProducto() == eleccion.getIdProducto())
					{
						prodPintar = proTemp;
						break;
					}
				}
				//Producto prodPintar = parProductoCtrl.obtenerProducto(eleccion.getIdProducto());
				//Validamos si el producto es tipo gaseosa para prender el indicador
				String command1 = Integer.toString(eleccion.getIdProducto());
				String strCommand1 = Integer.toString(eleccion.getIdProducto()) + "-" + eleccion.getDescripcion();
				jButElecciones2 = new JButton("<html><center>" +eleccion.getDescripcion() + "</center></html>");
				boton1 = new EleccionForzadaBoton(jButElecciones1,preguntaActual+1,1, Integer.parseInt(command1),eleccion.getDescripcion());
				boton2 = new EleccionForzadaBoton(jButElecciones2,preguntaActual+1,2, Integer.parseInt(command1),eleccion.getDescripcion());
				// Si es gaseosa entonces se adicionaran los iconos a los botones
				if(prodPintar.getTipoProducto().equals(new String("G")))
				{
					esGaseosa = true;
				}
				if(esGaseosa)
				{
					try
					{
						//Nos traemos las imagen y se la ponemos como icono a los botones
						BufferedImage image = null;
						InputStream in = new ByteArrayInputStream(prodPintar.getImagen());
						image = ImageIO.read(in);
						ImageIcon imgi = new ImageIcon(image.getScaledInstance(30, 30, 0));
						jButElecciones1.setIcon(imgi);
						jButElecciones2.setIcon(imgi);
					}catch(Exception e)
					{
						System.out.println("ERROR CARGANDO LA IMAGEN");
					}
					
				}
				arregloBotPan1.add(boton1);
				arregloBotPan2.add(boton2);
				/*
				 * Definimos la acci�n cuando damos clic sobre los botones
				 */
				jButElecciones1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						/*
						 * Traemos el objeto bot�n sobre el cual se dio clic
						 */
						String actCom =arg0.getActionCommand();
						JButton selButton = (JButton) arg0.getSource();
						String texto = selButton.getText();
						texto = texto.replaceAll("<html>", "");
						texto = texto.replaceAll("</html>", "");
						texto = texto.replaceAll("<center>", "");
						texto = texto.replaceAll("</center>", "");
						String elSelButton = actCom + "-" + texto;
						Color colSelButton = selButton.getBackground();
						if(selMitad1 == numMaxElecciones)
						{
							if(colSelButton.equals(Color.YELLOW))
							{
								selButton.setBackground(null);
								selMitad1--;
								/*
								 * Tratamos de eliminar en caso de que se quite la seleccion para evitar duplicar productos
								 */
								for(int i = 0 ; i < eleccionesTemporales.size(); i++)
								{
									EleccionForzadaTemporal elTemp = eleccionesTemporales.get(i);
									String elTempSel = elTemp.getIdProducto() + "-" +elTemp.getDescProducto();;
									if((elTempSel.equals(new String(elSelButton))) &&  (elTemp.getNumeroPregunta() == preguntaActual-1) && (elTemp.getNumeroMitad() ==1))
									{
										eleccionesTemporales.remove(i);
										break;
									}
								}
							}
							else
							{
								/**
								 * En este punto preguntamos si las selecciones son solo para 1 tomaremos una acci�n especial
								 * que ser� deseleccionar la seleccionado y seleccionar el nuevo
								 */
								if(numMaxElecciones == 1)
								{
									/**
									 * Se realiza la b�squeda del bot�n que esta marcado para desmarcarlo
									 */
									for(int i = 0; i < arregloBotPan1.size(); i ++)
									{
										if(arregloBotPan1.get(i).getBoton().getBackground() == Color.YELLOW)
										{
											arregloBotPan1.get(i).getBoton().setBackground(null);
											EleccionForzadaBoton  elForBotTemp = arregloBotPan1.get(i);
											String strElForBotTemp = elForBotTemp.getIdProducto() + "-" + elForBotTemp.getDescProducto();
											/*
											 * Realizamos b�squeda de la elecci�n temporal y la eliminamos ya que se ten�a y se quito, con la nueva seleccion
											 */
											for(int j = 0 ; j < eleccionesTemporales.size(); j++)
											{
												EleccionForzadaTemporal elTemp = eleccionesTemporales.get(j);
												String strElTemp = elTemp.getIdProducto() + "-" +elTemp.getDescProducto();;
												if((strElTemp.equals(strElForBotTemp)) &&  (elTemp.getNumeroPregunta() == preguntaActual-1) && (elTemp.getNumeroMitad() ==1))
												{
													eleccionesTemporales.remove(j);
													break;
												}
											}
											break;
										}
									}
									selButton.setBackground(Color.YELLOW);
								}
								else
								{
									JOptionPane.showMessageDialog(null, "No se deben seleccionar m�s opciones " , " M�ximo de Elecciones", JOptionPane.ERROR_MESSAGE);
									return;
								}
							}
						}
						else
						{
							
							
							if(colSelButton.equals(new Color(238, 238, 238)))
							{
								selButton.setBackground(Color.YELLOW);
								selMitad1++;
							}
							else if(colSelButton.equals(Color.YELLOW))
							{
								selButton.setBackground(null);
								selMitad1--;
								/*
								 * Realizamos b�squeda de la elecci�n temporal y la eliminamos ya que se ten�a y se quito
								 */
								for(int i = 0 ; i < eleccionesTemporales.size(); i++)
								{
									EleccionForzadaTemporal elTemp = eleccionesTemporales.get(i);
									String strElTemp = elTemp.getIdProducto() + "-" +elTemp.getDescProducto();;
									if((strElTemp.equals(elSelButton)) &&  (elTemp.getNumeroPregunta() == preguntaActual-1) && (elTemp.getNumeroMitad() ==1))
									{
										eleccionesTemporales.remove(i);
										break;
									}
								}
							}
							
							
						}
					}
				});
				jButElecciones2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						String actCom =arg0.getActionCommand();
						JButton selButton = (JButton) arg0.getSource();
						String texto = selButton.getText();
						texto = texto.replaceAll("<html>", "");
						texto = texto.replaceAll("</html>", "");
						texto = texto.replaceAll("<center>", "");
						texto = texto.replaceAll("</center>", "");
						String elSelButton = actCom + "-" + texto;
						Color colSelButton = selButton.getBackground();
						if(selMitad2 == numMaxElecciones)
						{
							if(colSelButton.equals(Color.YELLOW))
							{
								selButton.setBackground(null);
								selMitad2--;
								/*
								 * Realizamos b�squeda de la elecci�n temporal y la eliminamos ya que se ten�a y se quito
								 */
								for(int i = 0 ; i < eleccionesTemporales.size(); i++)
								{
									EleccionForzadaTemporal elTemp = eleccionesTemporales.get(i);
									String elTempSel = elTemp.getIdProducto() + "-" +elTemp.getDescProducto();;
									if((elTempSel.equals(elSelButton)) &&  (elTemp.getNumeroPregunta() == preguntaActual-1) && (elTemp.getNumeroMitad() ==2))
									{
										eleccionesTemporales.remove(i);
										break;
									}
								}
							}
							else
							{
								/**
								 * En este punto preguntamos si las selecciones son solo para 1 tomaremos una acci�n especial
								 * que ser� deseleccionar la seleccionado y seleccionar el nuevo
								 */
								if(numMaxElecciones == 1)
								{
									/**
									 * Se realiza la b�squeda del bot�n que esta marcado para desmarcarlo
									 */
									for(int i = 0; i < arregloBotPan2.size(); i ++)
									{
										if(arregloBotPan2.get(i).getBoton().getBackground() == Color.YELLOW)
										{
											arregloBotPan2.get(i).getBoton().setBackground(null);
											EleccionForzadaBoton  elForBotTemp = arregloBotPan2.get(i);
											String strElForBotTemp = elForBotTemp.getIdProducto() + "-" + elForBotTemp.getDescProducto();
											/*
											 * Realizamos b�squeda de la elecci�n temporal y la eliminamos ya que se ten�a y se quito, con la nueva seleccion
											 */
											for(int j = 0 ; j < eleccionesTemporales.size(); j++)
											{
												EleccionForzadaTemporal elTemp = eleccionesTemporales.get(j);
												String strElTemp = elTemp.getIdProducto() + "-" +elTemp.getDescProducto();;
												if((strElTemp.equals(strElForBotTemp)) &&  (elTemp.getNumeroPregunta() == preguntaActual-1) && (elTemp.getNumeroMitad() ==2))
												{
													eleccionesTemporales.remove(j);
													break;
												}
											}
											break;
										}
									}
									selButton.setBackground(Color.YELLOW);
								}
								else
								{
									JOptionPane.showMessageDialog(null, "No se deben seleccionar m�s opciones " , " M�ximo de Elecciones", JOptionPane.ERROR_MESSAGE);
									return;
								}
							}
						}
						else
						{
							
							
							if(colSelButton.equals(new Color(238, 238, 238)))
							{
								selButton.setBackground(Color.YELLOW);
								selMitad2++;
							}
							else if(colSelButton.equals(Color.YELLOW))
							{
								selButton.setBackground(null);
								selMitad2--;
								/*
								 * Realizamos b�squeda de la elecci�n temporal y la eliminamos ya que se ten�a y se quito
								 */
								for(int i = 0 ; i < eleccionesTemporales.size(); i++)
								{
									EleccionForzadaTemporal elTemp = eleccionesTemporales.get(i);
									String strElTemp = elTemp.getIdProducto() + "-" +elTemp.getDescProducto();;
									if((strElTemp.equals(new String(elSelButton))) &&  (elTemp.getNumeroPregunta() == preguntaActual-1) && (elTemp.getNumeroMitad() ==2))
									{
										eleccionesTemporales.remove(i);
										break;
									}
								}
							}
						}
						
					}
				});
				//validar si el bot�n est� seleccionado y con esto mirar para colocarle color .... y eliminarlo del temporal
				
				panelRespuestas1.add(jButElecciones1);
				jButElecciones1.setActionCommand(command1);
				panelRespuestas2.add(jButElecciones2);
				jButElecciones2.setActionCommand(command1);
				for(int i = 0 ; i < eleccionesTemporales.size(); i++)
				{
					EleccionForzadaTemporal elTemp = eleccionesTemporales.get(i);
					String strElTemp = elTemp.getIdProducto() + "-" +elTemp.getDescProducto();;
					if((strElTemp.equals(strCommand1)) &&  (elTemp.getNumeroPregunta() == preguntaActual) && (elTemp.getNumeroMitad() ==1))
					{
						jButElecciones1.setBackground(Color.YELLOW);
						selMitad1++;
						
					}
					if((strElTemp.equals(strCommand1)) &&  (elTemp.getNumeroPregunta() == preguntaActual) && (elTemp.getNumeroMitad() ==2))
					{
						jButElecciones2.setBackground(Color.YELLOW);
						selMitad2++;
						
					}
					
				}
			}
			preguntaActual++;
		}
		else
		{
			lblRespuestas1.setText("Elecci�n Entera");
			lblRespuestas2.setText("");
			EleccionForzadaBoton boton1;
			for(int j = 0; j < elecciones.size(); j++)
			{
				EleccionForzada eleccion = elecciones.get(j);
				jButElecciones1 = new JButton(eleccion.getDescripcion());
				String command1 = Integer.toString(eleccion.getIdProducto());
				//Realizamos optimizacion para evitar la ida  base de datos por cada producto
				//definimos el objeto Receptor
				Producto prodPintar = new Producto();
				//Recorremos el arrayList con los productos tra�dos de la base de datos
				for(int z = 0; z < productos.size(); z++)
				{
					Producto proTemp = productos.get(z);
					//Si se encuentra el objeto en el arrelgo tomamos el objeto y salimos del ciclo for
					if(proTemp.getIdProducto() == eleccion.getIdProducto())
					{
						prodPintar = proTemp;
						break;
					}
				}
				//Producto prodPintar = parProductoCtrl.obtenerProducto(eleccion.getIdProducto());
				String strCommand1 = Integer.toString(eleccion.getIdProducto()) + "-" + eleccion.getDescripcion();
				boton1 = new EleccionForzadaBoton(jButElecciones1,preguntaActual+1,1,Integer.parseInt(command1),eleccion.getDescripcion() );
				// Si es gaseosa entonces se adicionaran los iconos a los botones
				if(prodPintar.getTipoProducto().equals(new String("G")))
				{
					esGaseosa = true;
				}
				if(esGaseosa)
				{
					try
					{
						//Nos traemos las imagen y se la ponemos como icono a los botones
						BufferedImage image = null;
						InputStream in = new ByteArrayInputStream(prodPintar.getImagen());
						image = ImageIO.read(in);
						ImageIcon imgi = new ImageIcon(image.getScaledInstance(30, 30, 0));
						jButElecciones1.setText("<html><p></p><p><center>" + eleccion.getDescripcion() + "</center></p></html>");
						jButElecciones1.setIcon(imgi);
						jButElecciones2.setIcon(imgi);
					}catch(Exception e)
					{
						System.out.println("ERROR CARGANDO LA IMAGEN");
					}
					
				}
				arregloBotPan1.add(boton1);
				jButElecciones1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						String actCom =arg0.getActionCommand();
						JButton selButton = (JButton) arg0.getSource();
						String elSelButton = actCom + "-" + selButton.getText();
						Color colSelButton = selButton.getBackground();
						if(selMitad1 == numMaxElecciones)
						{
							if(colSelButton.equals(Color.YELLOW))
							{
								selButton.setBackground(null);
								selMitad1--;
								/*
								 * Realizamos b�squeda de la elecci�n temporal y la eliminamos ya que se ten�a y se quito
								 */
								for(int i = 0 ; i < eleccionesTemporales.size(); i++)
								{
									EleccionForzadaTemporal elTemp = eleccionesTemporales.get(i);
									String strElTemp = elTemp.getIdProducto() + "-" +elTemp.getDescProducto();;
									if((strElTemp.equals(elSelButton)) &&  (elTemp.getNumeroPregunta() == preguntaActual-1) && (elTemp.getNumeroMitad() ==1))
									{
										eleccionesTemporales.remove(i);
										break;
									}
								}
								
							}
							else
							{
								/**
								 * En este punto preguntamos si las selecciones son solo para 1 tomaremos una acci�n especial
								 * que ser� deseleccionar la seleccionado y seleccionar el nuevo
								 */
								if(numMaxElecciones == 1)
								{
									/**
									 * Se realiza la b�squeda del bot�n que esta marcado para desmarcarlo
									 */
									for(int i = 0; i < arregloBotPan1.size(); i ++)
									{
										if(arregloBotPan1.get(i).getBoton().getBackground() == Color.YELLOW)
										{
											arregloBotPan1.get(i).getBoton().setBackground(null);
											EleccionForzadaBoton  elForBotTemp = arregloBotPan1.get(i);
											String strElForBotTemp = elForBotTemp.getIdProducto() + "-" + elForBotTemp.getDescProducto();
											/*
											 * Realizamos b�squeda de la elecci�n temporal y la eliminamos ya que se ten�a y se quito, con la nueva seleccion
											 */
											for(int j = 0 ; j < eleccionesTemporales.size(); j++)
											{
												EleccionForzadaTemporal elTemp = eleccionesTemporales.get(j);
												String strElTemp = elTemp.getIdProducto() + "-" +elTemp.getDescProducto();;
												if((strElTemp.equals(strElForBotTemp)) &&  (elTemp.getNumeroPregunta() == preguntaActual-1) && (elTemp.getNumeroMitad() ==1))
												{
													eleccionesTemporales.remove(j);
													break;
												}
											}
											break;
										}
									}
									selButton.setBackground(Color.YELLOW);
								}
								else
								{
									JOptionPane.showMessageDialog(null, "No se deben seleccionar m�s opciones " , " M�ximo de Elecciones", JOptionPane.ERROR_MESSAGE);
									return;
								}
							}
						}
						else
						{
							
							
							if(colSelButton.equals(new Color(238, 238, 238)))
							{
								selButton.setBackground(Color.YELLOW);
								selMitad1++;
							}
							else if(colSelButton.equals(Color.YELLOW))
							{
								selButton.setBackground(null);
								selMitad1--;
								/*
								 * Realizamos b�squeda de la elecci�n temporal y la eliminamos ya que se ten�a y se quito
								 */
								for(int i = 0 ; i < eleccionesTemporales.size(); i++)
								{
									EleccionForzadaTemporal elTemp = eleccionesTemporales.get(i);
									String strElTemp = elTemp.getIdProducto() + "-" +elTemp.getDescProducto();
									if((strElTemp.equals(elSelButton)) &&  (elTemp.getNumeroPregunta() == preguntaActual-1) && (elTemp.getNumeroMitad() ==1))
									{
										eleccionesTemporales.remove(i);
										break;
									}
								}
							}
						}
						
					}
				});
				panelRespuestas1.add(jButElecciones1);
				jButElecciones1.setActionCommand(command1);
				for(int i = 0 ; i < eleccionesTemporales.size(); i++)
				{
					EleccionForzadaTemporal elTemp = eleccionesTemporales.get(i);
					String strElTemp = elTemp.getIdProducto() + "-" + elTemp.getDescProducto();
					if((strElTemp.equals(strCommand1)) &&  (elTemp.getNumeroPregunta() == preguntaActual) && (elTemp.getNumeroMitad() ==1))
					{
						jButElecciones1.setBackground(Color.YELLOW);
						selMitad1++;
						
					}
				}
				
			}
			preguntaActual++;
		}
		
	}
	
	
/**
 * M�todo que se encarga de adicionar al pedido toda la informaci�n que se ha recaudado en las diferentes preguntas forzadas que tiene asociado el producto.
 */
		public void AdicionarTomarPedidos()
		{
			/**
			 * Se realiza la adici�n con base en el arrayList que es a donde se han ido adicionando las diferentes selecciones de preguntas forzadas.
			 */
			//Realizamos un ordenamiento del las elecciones de manera qeu primero lo de pregunta 1, luego la 2 y asi sucesitamvente
			ordenarEleccionesTemporales();
			double precioProducto = 0, cantidad = 0;
			int idProducto = 0;
			//Se hace un recorrido de las elecciones forzadas seleccionadas por mitad 
			for(int m = 0; m < eleccionesTemporales.size(); m++)
			{
				//Se trae cada elecci�n de manera temporal
				EleccionForzadaTemporal elTemp = eleccionesTemporales.get(m);
				//Obtenemos el bot�n seleccionado por el usuario
				JButton jButTemp= elTemp.getBoton();
				//Tomamos el color del bot�n seleccionado con el fin de terminar de constatar que si sea seleccionado
				Color colSelButton = jButTemp.getBackground();
				if(colSelButton.equals(Color.YELLOW))
				{
					//Traemos el precio seleccionado
					precioProducto = elTemp.getPrecioProducto();
					//Traemos la cantidad del producto
					cantidad = elTemp.getCantidad();
					idProducto = elTemp.getIdProducto();
					//Creamos el objeto detalle pedido para la inserci�n
					DetallePedido detPedido = new DetallePedido(0,VentPedTomarPedidos.idPedido,idProducto,cantidad,precioProducto, cantidad*precioProducto, "", VentPedTomarPedidos.idDetallePedidoMaster,"N","", VentPedTomarPedidos.contadorDetallePedido);
					//Obtenemos el idDetalle luego de la inserci�n
					int idDetalle = pedCtrl.insertarDetallePedido(detPedido);
					//Se lo asignamos al objeto para posteriormente llenar el arrayList de la pantalla de toma pedidos
					detPedido.setIdDetallePedido(idDetalle);
					if(idDetalle > 0)
					{
						VentPedTomarPedidos.detallesPedido.add(detPedido);
						VentPedTomarPedidos.totalPedido = VentPedTomarPedidos.totalPedido + detPedido.getValorTotal();
						//Para pintar la nueva adici�n de producto y fijar el nuevo valor se ejecutar� cuando se active la ventana
					}
					
				}
			
			}
			
		}
	
	/**
	 * M�todo que se encargar� de la adici�n de los productos seleccionados dentro de la metolog�a de preguntas forzadas, se hace esta adici�n cuando avanzamos o retrocedemos
	 * en las preguntas.
	 */
	public void incluirProductos()
	{
		/*
		 * Cada vez que voy a incluir productos debemos de validar par aborrar los productos de la pregunta correspondiente para que la adici�n no duplique lo no marcado
		 */
		
		//Realizamos validaciones para revisar las elecciones, se valida si idPregunta es diferente de cero dado qeu si es as� se est� pulsando el bot�n en elecciones, 
		// en caso de preguntaActual sea igual a cero, es porque es la primera ejecuci�n
		if(preguntaActual != 0)
		{
			//Realizamos adici�n de los productos, lo primero es validar si esta dividida
			for(int m = 0; m < arregloBotPan1.size(); m++)
			{
				JButton jButTemp= arregloBotPan1.get(m).getBoton();
				String strjButTemp = arregloBotPan1.get(m).getIdProducto() + "-" + jButTemp.getText();
				Color colSelButton = jButTemp.getBackground();
				if(colSelButton.equals(Color.YELLOW))
				{
					/*
					 * Debemos de realizar una b�squeda para saber si el bot�n que vamos a agregar ya estaba agregado, de ser as� no deber�amos
					 * de agregar el b�ton pues estar�amos duplicando la informaci�n.
					 */
					boolean existe = false;
					for(int j = 0; j < eleccionesTemporales.size();j++)
					{
						EleccionForzadaTemporal eleTemp = eleccionesTemporales.get(j);
						String strEleTemp = eleTemp.getIdProducto() + "-" + eleTemp.getBoton().getText();
						/**
						 * En esta condici�n validamos si el bot�n ya est� adicionado teniendo en cuenta el rpoducto y el n�mero de la pregunta.
						 */
						if((strEleTemp.equals(new String(strjButTemp))) && (eleTemp.getNumeroPregunta()+1 == arregloBotPan1.get(m).getNumPregunta())&&(eleTemp.getNumeroMitad() == 1))
						{
							existe = true;
							break;
						}
					}
					/**
					 * En caso de que el producto no este adicionado, entonces se har� la adici�n a la elecci�n temporal
					 */
						if(!existe)
						{
							String txtJBut = jButTemp.getActionCommand();
//							StringTokenizer StrTokenProducto = new StringTokenizer(txtJBut,"-");
//							String strIdProducto = StrTokenProducto.nextToken();
							int idProducto = Integer.parseInt(txtJBut);
							//Para obtener el precio deber�amos recorrer las elecciones de la pregunta y capturar el precio
							double precioProducto = parProductoCtrl.obtenerPrecioEleccion(elecciones, idProducto);
							double cantidad = 1/(double)permDividir;
							EleccionForzadaTemporal eleTemp = new EleccionForzadaTemporal();
							eleTemp.setBoton(jButTemp);
							eleTemp.setCantidad(cantidad);
							eleTemp.setPrecioProducto(precioProducto);
							eleTemp.setIdProducto(idProducto);
							eleTemp.setNumeroPregunta(preguntaActual-1);
							eleTemp.setNumeroMitad(1);
							Producto prod = parProductoCtrl.obtenerProducto(idProducto);
							eleTemp.setDescProducto(prod.getDescripcion());
							eleTemp.setTipoProducto(prod.getTipoProducto());
							eleccionesTemporales.add(eleTemp);
							
							
						}
						arregloBotPan1.remove(m);
						m--;
					
				}
				//Si no est� marcada con color, es porque no se selecciono y se puede eliminar del ArrayList
				else
				{
					arregloBotPan1.remove(m);
					m--;
				}
			}
			/*
			 * Si permite dividir es porque la divisi�n es de dos y tendremos casi que hacer los mismo para los botones del panel2
			 */
			if(permiteDividir)
			{
				// Se realiza para el primer arreglo de productos
				for(int m = 0; m < arregloBotPan2.size(); m++)
				{
					JButton jButTemp= arregloBotPan2.get(m).getBoton();
					String strjButTemp = arregloBotPan2.get(m).getIdProducto() + "-" + jButTemp.getText();
					Color colSelButton = jButTemp.getBackground();
					if(colSelButton.equals(Color.YELLOW))
					{
						/*
						 * Debemos de realizar una b�squeda para saber si el bot�n que vamos a agregar ya estaba agregado, de ser as� no deber�amos
						 * de agregar el b�ton pues estar�amos duplicando la informaci�n.
						 */
						boolean existe = false;
						for(int j = 0; j < eleccionesTemporales.size();j++)
						{
							EleccionForzadaTemporal eleTemp = eleccionesTemporales.get(j);
							String strEleTemp = eleTemp.getIdProducto() + "-" + eleTemp.getBoton().getText();
							/**
							 * En esta condici�n validamos si el bot�n ya est� adicionado teniendo en cuenta el rpoducto y el n�mero de la pregunta, esto con el objetivo de prender
							 * la variable existe, es decir si ya el producto esta adicionado, no deber�amos volver a adicionarlo.
							 */
							if((strEleTemp.equals(strjButTemp)) && (eleTemp.getNumeroPregunta()+1 == arregloBotPan2.get(m).getNumPregunta())&&(eleTemp.getNumeroMitad() == 2))
							{
								existe = true;
								break;
							}
						}
							if(!existe)
							{
								String txtJBut = jButTemp.getActionCommand();
//								StringTokenizer StrTokenProducto = new StringTokenizer(txtJBut,"-");
//								String strIdProducto = StrTokenProducto.nextToken();
								int idProducto = Integer.parseInt(txtJBut);
								//Para obtener el precio deber�amos recorrer las elecciones de la pregunta y capturar el precio
								double precioProducto = parProductoCtrl.obtenerPrecioEleccion(elecciones, idProducto);
								double cantidad = 1/(double)permDividir;
								EleccionForzadaTemporal eleTemp = new EleccionForzadaTemporal();
								eleTemp.setBoton(jButTemp);
								eleTemp.setCantidad(cantidad);
								eleTemp.setPrecioProducto(precioProducto);
								eleTemp.setIdProducto(idProducto);
								eleTemp.setNumeroPregunta(preguntaActual-1);
								eleTemp.setNumeroMitad(2);
								Producto prod = parProductoCtrl.obtenerProducto(idProducto);
								eleTemp.setDescProducto(prod.getDescripcion());
								eleTemp.setTipoProducto(prod.getTipoProducto());
								eleccionesTemporales.add(eleTemp);
								
							}
							arregloBotPan2.remove(m);
							m--;
					}
					//Si no est� marcada con color, es porque no se selecciono y se puede eliminar del ArrayList
					else
					{
						arregloBotPan2.remove(m);
						m--;
					}
				}
			}else
			{
				
			}
		}
		
		
	}
	
	/**
	 * M�todo que se encarga de ordenar el arrelgo de eleccionesTemporales, para luego realizar la adici�n al detalle pedido, este m�todo es la base para insertar el 
	 * pedido de una forma ordenanada, dejando la relaci�n junta de producto adicion y dejando el l�quido en �ltima posici�n.
	 */
	public void ordenarEleccionesTemporales()
	{
		//Creamos un arreglo para recorrer e ir borrando
		ArrayList<EleccionForzadaTemporal> eleTemp =(ArrayList)eleccionesTemporales.clone();
		//Este arreglo ser� el final quien al final sustituiera el arreglo inicial
		ArrayList<EleccionForzadaTemporal> eleFinal = new ArrayList();
		int contPregunta = 0;
		/**
		 * Se ejecutar� mientras el contador de preguntas sea menor o igual y se recorrera agregando las mitade uno en orden sin agregar gaseosa
		 */
		while(contPregunta <= numPreguntas)
		{
			/*
			 * Recorremos primero buscando el contador de preguntas y la mitad 1
			 */
			for(int i = 0; i < eleTemp.size(); i++)
			{
				EleccionForzadaTemporal elemenTemp = eleTemp.get(i);
				if((elemenTemp.getNumeroPregunta() == contPregunta)&&elemenTemp.getNumeroMitad() == 1 &&(!elemenTemp.getTipoProducto().equals(new String("G"))))
				{
					eleFinal.add(elemenTemp);
					eleTemp.remove(i);
					i--;
				}
			}
			contPregunta++;
		}
		
		contPregunta = 0;
		/**
		 * Se ejecutar� mientras el contador de preguntas sea menor o igual y se recorrera agregando las mitade dos en orden sin agregar gaseosa
		 */
		while(contPregunta <= numPreguntas)
		{
			/*
			 * Recorremos primero buscando el contador de preguntas y la mitad 2
			 */
			for(int i = 0; i < eleTemp.size(); i++)
			{
				EleccionForzadaTemporal elemenTemp = eleTemp.get(i);
				if((elemenTemp.getNumeroPregunta() == contPregunta)&&elemenTemp.getNumeroMitad() == 2 &&(!elemenTemp.getTipoProducto().equals(new String("G"))))
				{
					eleFinal.add(elemenTemp);
					eleTemp.remove(i);
					i--;
				}
			}
			//Avanzamos la pregunta.
			contPregunta++;
		}
		
		contPregunta = 0;
		//En el �ltimo ciclo agregamos los productos tipo gaseosa con el fin de que queden en el orden correcto
		//Realizaremos el recorrido pero no haremos ninguna validaci�n dado que son las gaseosas las �nicos que faltan
		// y se deber�n agregar a lo �ltimo.
		for(int i = 0; i < eleTemp.size(); i++)
		{
			EleccionForzadaTemporal elemenTemp = eleTemp.get(i);
			eleFinal.add(elemenTemp);
			eleTemp.remove(i);
			i--;
		}

	/*
	 * llevamos al arreglo importante el arreglo ya ordenado.
	 */
		eleccionesTemporales = (ArrayList) eleFinal.clone();
	}
	
	}
