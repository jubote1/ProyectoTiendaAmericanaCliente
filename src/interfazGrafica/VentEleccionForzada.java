package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import capaModelo.DetallePedido;
import capaModelo.EleccionForzada;
import capaModelo.EleccionForzadaTemporal;
import capaModelo.Pregunta;

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

public class VentEleccionForzada extends JFrame {

	private JPanel contenedorPrincipal;
	JLabel lblDescripcionPregunta;
	private JPanel panelRespuestas1;
	private JPanel panelRespuestas2;
	JButton jButElecciones1;
	JButton jButElecciones2;
	private JLabel lblRespuestas1;
	private JLabel lblRespuestas2;
	private String selProducto1;
	private String selProducto2;
	private ArrayList<JButton> arregloBotPan1;
	private ArrayList<JButton> arregloBotPan1Final = new ArrayList();
	private ArrayList<JButton> arregloBotPan2;
	private ArrayList<JButton> arregloBotPan2Final = new ArrayList();
	private static int numPreguntas;
	private static int preguntaActual = 0;
	private ArrayList<EleccionForzada> elecciones;
	private ArrayList<Pregunta> preguntasPantalla;
	private ArrayList<EleccionForzadaTemporal> eleccionesTemporales = new ArrayList();
	private Pregunta pregActual;
	//Parámetros valiosos para la Pregunta
	int numMaxElecciones = 0;
	int permDividir = 0;
	boolean obligEleccion = false;
	int selMitad1 = 0;
	int selMitad2 = 0;
	boolean permiteDividir = false;
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
	 * Método que se encarga de la instanciación de la ventana de Elección Forzada
	 * @param preguntas se recibe un ArrayList con las preguntas forzadas que se van a controlar
	 * @param idProducto se recibe el producto al cual se le están aplicando la Elección Forzada.
	 */
	public VentEleccionForzada(ArrayList<Pregunta> preguntas, int idProducto) {
		setTitle("ELECCI\u00D3N");
		numPreguntas = preguntas.size();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 684, 610);
		contenedorPrincipal = new JPanel();
		contenedorPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contenedorPrincipal);
		contenedorPrincipal.setLayout(null);
		arregloBotPan2 = new ArrayList();
		arregloBotPan1 = new ArrayList();
		panelRespuestas1 = new JPanel();
		panelRespuestas1.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		panelRespuestas1.setBounds(25, 41, 621, 224);
		contenedorPrincipal.add(panelRespuestas1);
		panelRespuestas1.setLayout(new GridLayout(0, 4, 0, 0));
		
		lblDescripcionPregunta = new JLabel("New label");
		lblDescripcionPregunta.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblDescripcionPregunta.setBounds(10, 540, 318, 32);
		contenedorPrincipal.add(lblDescripcionPregunta);
		
		panelRespuestas2 = new JPanel();
		panelRespuestas2.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		panelRespuestas2.setBounds(25, 299, 621, 241);
		contenedorPrincipal.add(panelRespuestas2);
		panelRespuestas2.setLayout(new GridLayout(0, 4, 0, 0));
		
		lblRespuestas1 = new JLabel("New label");
		lblRespuestas1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblRespuestas1.setBounds(25, 11, 621, 14);
		contenedorPrincipal.add(lblRespuestas1);
		
		lblRespuestas2 = new JLabel("New label");
		lblRespuestas2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblRespuestas2.setBounds(25, 274, 621, 14);
		contenedorPrincipal.add(lblRespuestas2);
		
		JButton btnConfirmarPregunta = new JButton("Confirmar Pregunta");
		btnConfirmarPregunta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(obligEleccion == true)
				{
					if((numMaxElecciones == selMitad1)&(numMaxElecciones == selMitad2))
					{
						
					}
					else
					{
						JOptionPane.showMessageDialog(null, "La pregunta obliga la selección de  " + numMaxElecciones + " por División, esto último no se está respetando." , " Falta Selección de Opciones", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				incluirProductos();
				if(preguntaActual == preguntasPantalla.size())
				{
					AdicionarTomarPedidos();
					dispose();
				}
				CargarEleccionForzada(preguntaActual);
			}
		});
		btnConfirmarPregunta.setBounds(342, 547, 304, 23);
		contenedorPrincipal.add(btnConfirmarPregunta);
		//Debemos de definir las cosas para el cargue de la primera pregunta
		numPreguntas = preguntas.size();
		preguntasPantalla = preguntas;
		CargarEleccionForzada(preguntaActual);
	}
	
	/**
	 * Método principal del esta clase qeu se encarga del despliegue de las preguntas forzadas y todo el control y adición de botones para la sección.
	 * @param pregunta, se recibe el número de pregunta que se va a controlar, teniendo en cuenta que pueden ser hasta 5 y se deben llevar de manera secuencial.
	 */
	public void CargarEleccionForzada(int pregunta)
	{
		//Realizamos un limpiado de la información de los paneles
		panelRespuestas1.removeAll();
		panelRespuestas1.repaint();
		panelRespuestas2.removeAll();
		panelRespuestas2.repaint();
		//Realizamos modificaciones para adicionar al pedido
		//En caso de cumplirse esto deberemos de pasar el pedido a la pantalla tomador de pedidos
		if (preguntaActual == numPreguntas)
		{
			//el último paso a realizar es liberar el objeto
			dispose();
			return;
		}
		//Se realizan las acciones de display para elección de la información
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
		//Tomamos lógica para validar si la Pregunta permite Division o no
		if(permDividir >= 2)
		{
			permiteDividir = true;
		}else
		{
			permiteDividir = false;
		}
		elecciones = new ArrayList();
		ParametrosProductoCtrl parProductoCtrl = new ParametrosProductoCtrl();
		elecciones = parProductoCtrl.obtEleccionesForzadas(pregActual.getIdPregunta());
		//Adicionamos los botones con las preguntas forzadas
		if(permiteDividir)
		{
			
			lblRespuestas1.setText("Elección Mitad 1");
			lblRespuestas2.setText("Elección Mitad 2");
			for(int j = 0; j < elecciones.size(); j++)
			{
				EleccionForzada eleccion = elecciones.get(j);
				jButElecciones1 = new JButton(eleccion.getIdProducto()+"-"+eleccion.getDescripcion());
				jButElecciones2 = new JButton(eleccion.getIdProducto()+"-"+eleccion.getDescripcion());
				arregloBotPan1.add(jButElecciones1);
				arregloBotPan2.add(jButElecciones2);
				jButElecciones1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						JButton selButton = (JButton) arg0.getSource();
						Color colSelButton = selButton.getBackground();
						if(selMitad1 == numMaxElecciones)
						{
							if(colSelButton.equals(Color.YELLOW))
							{
								selButton.setBackground(new Color(238, 238, 238));
								selMitad1--;
							}
							else
							{
								JOptionPane.showMessageDialog(null, "No se deben seleccionar más opciones " , " Máximo de Elecciones", JOptionPane.ERROR_MESSAGE);
								return;
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
								selButton.setBackground(new Color(238, 238, 238));
								selMitad1--;
							}
							
//							if(antjButElecciones1 != null)
//							{
//								antjButElecciones1.setBackground(new Color(238, 238, 238));
//							}
//							antjButElecciones1 = selButton;
//							selButton.setBackground(Color.YELLOW);
//							selProducto1 = selButton.getText();
							//Actividad cuando se selecciona la primera mitad;
							
						}
					}
				});
				jButElecciones2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						JButton selButton = (JButton) arg0.getSource();
						Color colSelButton = selButton.getBackground();
						if(selMitad2 == numMaxElecciones)
						{
							if(colSelButton.equals(Color.YELLOW))
							{
								selButton.setBackground(new Color(238, 238, 238));
								selMitad2--;
							}
							else
							{
								JOptionPane.showMessageDialog(null, "No se deben seleccionar más opciones " , " Máximo de Elecciones", JOptionPane.ERROR_MESSAGE);
								return;
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
								selButton.setBackground(new Color(238, 238, 238));
								selMitad2--;
							}
						}
						
					}
				});
				panelRespuestas1.add(jButElecciones1);
				panelRespuestas2.add(jButElecciones2);
			}
			preguntaActual++;
		}
		else
		{
			lblRespuestas1.setText("Elección Entera");
			lblRespuestas2.setText("");
			for(int j = 0; j < elecciones.size(); j++)
			{
				EleccionForzada eleccion = elecciones.get(j);
				jButElecciones1 = new JButton(eleccion.getIdProducto()+"-"+eleccion.getDescripcion());
				arregloBotPan1.add(jButElecciones1);
				jButElecciones1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						JButton selButton = (JButton) arg0.getSource();
						Color colSelButton = selButton.getBackground();
						if(selMitad1 == numMaxElecciones)
						{
							if(colSelButton.equals(Color.YELLOW))
							{
								selButton.setBackground(new Color(238, 238, 238));
								selMitad1--;
							}
							else
							{
								JOptionPane.showMessageDialog(null, "No se deben seleccionar más opciones " , " Máximo de Elecciones", JOptionPane.ERROR_MESSAGE);
								return;
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
								selButton.setBackground(new Color(238, 238, 238));
								selMitad1--;
							}
						}
						
					}
				});
				panelRespuestas1.add(jButElecciones1);
			}
			preguntaActual++;
		}
		
	}
	
	
		public void AdicionarTomarPedidos()
		{
			for(int m = 0; m < arregloBotPan1Final.size(); m++)
			{
				JButton jButTemp= arregloBotPan1Final.get(m);
				Color colSelButton = jButTemp.getBackground();
				if(colSelButton.equals(Color.YELLOW))
				{
					
						double precioProducto = 0, cantidad = 0;
						int idProducto = 0;
						PedidoCtrl pedCtrl = new PedidoCtrl();
						//Recorremos el arreglo con las elecciones para recuperar la cantidad y el precio
						for(int i = 0 ; i < eleccionesTemporales.size(); i++)
						{
							EleccionForzadaTemporal elTemp = eleccionesTemporales.get(i);
							if(elTemp.getBoton().equals(jButTemp))
							{
								precioProducto = elTemp.getPrecioProducto();
								cantidad = elTemp.getCantidad();
								idProducto = elTemp.getIdProducto();
							}
						}
						DetallePedido detPedido = new DetallePedido(0,TomarPedidos.idPedido,idProducto,cantidad,precioProducto, cantidad*precioProducto, "", TomarPedidos.idDetallePedidoMaster);
						int idDetalle = pedCtrl.insertarDetallePedido(detPedido);
						detPedido.setIdDetallePedido(idDetalle);
						if(idDetalle > 0)
						{
							TomarPedidos.detallesPedido.add(detPedido);
							TomarPedidos.totalPedido = TomarPedidos.totalPedido + detPedido.getValorTotal();
							//Para pintar la nueva adición de producto y fijar el nuevo valor se ejecutará cuando se active la ventana
						}
					
				}
				
			}
			
			for(int m = 0; m < arregloBotPan2Final.size(); m++)
			{
				JButton jButTemp= arregloBotPan2Final.get(m);
				Color colSelButton = jButTemp.getBackground();
				if(colSelButton.equals(Color.YELLOW))
				{
						double precioProducto = 0, cantidad = 0;
						int idProducto = 0;
						PedidoCtrl pedCtrl = new PedidoCtrl();
						//Recorremos el arreglo con las elecciones para recuperar la cantidad y el precio
						for(int i = 0 ; i < eleccionesTemporales.size(); i++)
						{
							EleccionForzadaTemporal elTemp = eleccionesTemporales.get(i);
							if(elTemp.getBoton().equals(jButTemp))
							{
								precioProducto = elTemp.getPrecioProducto();
								cantidad = elTemp.getCantidad();
								idProducto = elTemp.getIdProducto();
							}
						}
						DetallePedido detPedido = new DetallePedido(0,TomarPedidos.idPedido,idProducto,cantidad,precioProducto, cantidad*precioProducto, "", TomarPedidos.idDetallePedidoMaster);
						int idDetalle = pedCtrl.insertarDetallePedido(detPedido);
						detPedido.setIdDetallePedido(idDetalle);
						if(idDetalle > 0)
						{
							TomarPedidos.detallesPedido.add(detPedido);
							TomarPedidos.totalPedido = TomarPedidos.totalPedido + detPedido.getValorTotal();
							//Para pintar la nueva adición de producto y fijar el nuevo valor se ejecutará cuando se active la ventana
						}
					}
			}
		}
	
	/**
	 * Método que se encargará de la adición de los productos seleccionados dentro de la metología de preguntas forzadas.
	 */
	public void incluirProductos()
	{
		//Realizamos validaciones para revisar las elecciones, se valida si idPregunta es diferente de cero dado qeu si es así se está pulsando el botón en elecciones, 
		// en caso de preguntaActual sea igual a cero, es porque es la primera ejecución desde 
		System.out.println(" valor de pregunta " + preguntaActual );
		if(preguntaActual != 0)
		{
			//Realizamos adición de los productos, lo primero es validar si esta dividida
			for(int m = 0; m < arregloBotPan1.size(); m++)
			{
				JButton jButTemp= arregloBotPan1.get(m);
				Color colSelButton = jButTemp.getBackground();
				if(colSelButton.equals(Color.YELLOW))
				{
					
						String txtJBut = jButTemp.getText();
						StringTokenizer StrTokenProducto = new StringTokenizer(txtJBut,"-");
						String strIdProducto = StrTokenProducto.nextToken();
						int idProducto = Integer.parseInt(strIdProducto);
						ParametrosProductoCtrl parProducto = new ParametrosProductoCtrl();
						PedidoCtrl pedCtrl = new PedidoCtrl();
						//Para obtener el precio deberíamos recorrer las elecciones de la pregunta y capturar el precio
						double precioProducto = parProducto.obtenerPrecioEleccion(elecciones, idProducto);
						System.out.println("precio producto " + idProducto + " " + precioProducto);
						double cantidad = 1/(double)permDividir;
						EleccionForzadaTemporal eleTemp = new EleccionForzadaTemporal();
						eleTemp.setBoton(jButTemp);
						eleTemp.setCantidad(cantidad);
						eleTemp.setPrecioProducto(precioProducto);
						eleTemp.setIdProducto(idProducto);
						eleccionesTemporales.add(eleTemp);
						arregloBotPan1Final.add(jButTemp);
						arregloBotPan1.remove(m);
						m--;
					
				}
				//Si no está marcada con color, es porque no se selecciono y se puede eliminar del ArrayList
				else
				{
					arregloBotPan1.remove(m);
					m--;
				}
			}
			if(permiteDividir)
			{
				// Se realiza para el primer arreglo de productos
				for(int m = 0; m < arregloBotPan2.size(); m++)
				{
					JButton jButTemp= arregloBotPan2.get(m);
					Color colSelButton = jButTemp.getBackground();
					if(colSelButton.equals(Color.YELLOW))
					{
							String txtJBut = jButTemp.getText();
							StringTokenizer StrTokenProducto = new StringTokenizer(txtJBut,"-");
							String strIdProducto = StrTokenProducto.nextToken();
							int idProducto = Integer.parseInt(strIdProducto);
							ParametrosProductoCtrl parProducto = new ParametrosProductoCtrl();
							PedidoCtrl pedCtrl = new PedidoCtrl();
							//Para obtener el precio deberíamos recorrer las elecciones de la pregunta y capturar el precio
							double precioProducto = parProducto.obtenerPrecioEleccion(elecciones, idProducto);
							double cantidad = 1/(double)permDividir;
							EleccionForzadaTemporal eleTemp = new EleccionForzadaTemporal();
							eleTemp.setBoton(jButTemp);
							eleTemp.setCantidad(cantidad);
							eleTemp.setPrecioProducto(precioProducto);
							eleTemp.setIdProducto(idProducto);
							eleccionesTemporales.add(eleTemp);
							arregloBotPan2Final.add(jButTemp);
							arregloBotPan2.remove(m);
							m--;
					}
					//Si no está marcada con color, es porque no se selecciono y se puede eliminar del ArrayList
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
}
