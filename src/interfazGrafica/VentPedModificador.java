package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import capaModelo.DetallePedido;
import capaModelo.EleccionForzada;
import capaModelo.EleccionForzadaTemporal;
import capaModelo.Pregunta;
import capaModelo.ProductoModificadorCon;
import capaModelo.ProductoModificadorSin;

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

/**
 * Clase de la capa IntefazGrafica que se encarga de la presentacón de los modificadores de producto de CON y SIN en caso
 * de que a un producto le apliquen o no.
 * @author Juan David Botero Duque
 *
 */
public class VentPedModificador extends JDialog {

	/**
	 * Se definen las variables de presentación como variables privadas
	 */
	private JPanel contenedorPrincipal;
	private JPanel panelModificadores;
	JButton jButElecciones1;
	private JLabel lblModificador;
	private String selProducto1;
	private ArrayList<JButton> arregloBotPan1;
	private ArrayList<JButton> arregloBotPan1Final = new ArrayList();
	/**
	 * Variables importantes y públicas para el manejo y la coumunicación de los modificadores
	 */
	int idDetallePedido = 0;
	int maxSelecciones = 0;
	int seleccion = 0;
	boolean modCon = false;
	boolean modSin = false;
	private int contadorDetallePedido;
	private PedidoCtrl pedCtrl = new PedidoCtrl(PrincipalLogueo.habilitaAuditoria);
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
	public VentPedModificador(java.awt.Frame parent, boolean modal, int idDetPedido, boolean modificadorCon, boolean modificadorSin, int contadorDetPedido) {
		super(parent, modal);
		contadorDetallePedido = contadorDetPedido;
		if(modificadorCon)
		{
			setTitle("SELECCIÓN MODIFICADORES CON");
		}else if(modificadorSin)
		{
			setTitle("SELECCIÓN MODIFICADORES SIN");
		}

		ImageIcon img = new ImageIcon("iconos\\LogoPequePizzaAmericana.jpg");
		setIconImage(img.getImage());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0,0, 684, 610);
		int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	    int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		setBounds((ancho / 2) - (this.getWidth() / 2), (alto / 2) - (this.getHeight() / 2), 684, 610);
		contenedorPrincipal = new JPanel();
		contenedorPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contenedorPrincipal);
		contenedorPrincipal.setLayout(null);
		arregloBotPan1 = new ArrayList();
		panelModificadores = new JPanel();
		panelModificadores.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		panelModificadores.setBounds(25, 41, 621, 471);
		contenedorPrincipal.add(panelModificadores);
		panelModificadores.setLayout(new GridLayout(0, 4, 0, 0));
		
		lblModificador = new JLabel("Modificadores Producto");
		lblModificador.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblModificador.setBounds(25, 11, 621, 14);
		contenedorPrincipal.add(lblModificador);
		
		JButton btnConfirmar = new JButton("Confirmar Selección");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				incluirProductos();
			}
		});
		btnConfirmar.setBounds(342, 547, 304, 23);
		contenedorPrincipal.add(btnConfirmar);
		//Cargamos con la primera sección enviada
		idDetallePedido = idDetPedido;
		modCon = modificadorCon;
		modSin = modificadorSin;
		cargarModificadores();
		
		
	}
	
	/**
	 * Método que se encargará de obtener los modificadores sea CON o SIN que el aplican al producto en cuestión
	 * y con base en esto se encargará de adicionarlos para l aacción en el panel del presentación.
	 */
	public void cargarModificadores()
	{
		//Iniciamos limpiando todo lo que se pueda tener en el panel de modificadores.
		panelModificadores.removeAll();
		panelModificadores.repaint();
		//Definimos las variables necesarias para el proceso
		ArrayList<ProductoModificadorCon> prodModificadores;
		ArrayList<ProductoModificadorSin> prodModificadoresSin;
		if(modCon)
		{
			prodModificadores = pedCtrl.obtenerModificadoresCon(idDetallePedido);
			maxSelecciones = pedCtrl.obtenerMaxModificadorCon(idDetallePedido);
			for(int i = 0; i < prodModificadores.size(); i++)
			{
				ProductoModificadorCon modTemp = prodModificadores.get(i);
				jButElecciones1 = new JButton("<html><center>" + modTemp.getNombreProductoCon() + "</center></html>");
				jButElecciones1.setActionCommand(Integer.toString(modTemp.getIdProductoCon()));
				arregloBotPan1.add(jButElecciones1);
				jButElecciones1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						JButton selButton = (JButton) arg0.getSource();
						Color colSelButton = selButton.getBackground();
						if(maxSelecciones != 0)
						{
							if(seleccion == maxSelecciones)
							{
								if(colSelButton.equals(Color.YELLOW))
								{
									selButton.setBackground(new Color(238, 238, 238));
									seleccion--;
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
									seleccion++;
								}
								else if(colSelButton.equals(Color.YELLOW))
								{
									selButton.setBackground(new Color(238, 238, 238));
									seleccion--;
								}
								
							}
						}else
						{
							if(colSelButton.equals(new Color(238, 238, 238)))
							{
								selButton.setBackground(Color.YELLOW);
								seleccion++;
							}
							else if(colSelButton.equals(Color.YELLOW))
							{
								selButton.setBackground(new Color(238, 238, 238));
								seleccion--;
							}
						}
						
					}
				});
				panelModificadores.add(jButElecciones1);
			}
		}else if (modSin)
		{
			prodModificadoresSin = pedCtrl.obtenerModificadoresSin(idDetallePedido);
			maxSelecciones = pedCtrl.obtenerMaxModificadorSin(idDetallePedido);
			for(int i = 0; i < prodModificadoresSin.size(); i++)
			{
				ProductoModificadorSin modTemp = prodModificadoresSin.get(i);
				jButElecciones1 = new JButton("<html><center>" + modTemp.getNombreProductoSin() + "</center></html>");
				jButElecciones1.setActionCommand(Integer.toString(modTemp.getIdProductoSin()));
				arregloBotPan1.add(jButElecciones1);
				jButElecciones1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						JButton selButton = (JButton) arg0.getSource();
						Color colSelButton = selButton.getBackground();
						if(maxSelecciones != 0)
						{
							if(seleccion == maxSelecciones)
							{
								if(colSelButton.equals(Color.YELLOW))
								{
									selButton.setBackground(new Color(238, 238, 238));
									seleccion--;
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
									seleccion++;
								}
								else if(colSelButton.equals(Color.YELLOW))
								{
									selButton.setBackground(new Color(238, 238, 238));
									seleccion--;
								}
								
					
							}
						}else
						{
							if(colSelButton.equals(new Color(238, 238, 238)))
							{
								selButton.setBackground(Color.YELLOW);
								seleccion++;
							}
							else if(colSelButton.equals(Color.YELLOW))
							{
								selButton.setBackground(new Color(238, 238, 238));
								seleccion--;
							}
						}
						
					}
				});
				panelModificadores.add(jButElecciones1);
			}
		}
		
		
	}
	
	

	

	public void incluirProductos()
	{
		//Realizamos adición de los productos, lo primero es validar si esta dividida
			for(int m = 0; m < arregloBotPan1.size(); m++)
			{
				JButton jButTemp= arregloBotPan1.get(m);
				Color colSelButton = jButTemp.getBackground();
				if(colSelButton.equals(Color.YELLOW))
				{
					
						String txtJBut = jButTemp.getActionCommand();
//						StringTokenizer StrTokenProducto = new StringTokenizer(txtJBut,"-");
//						String strIdProducto = StrTokenProducto.nextToken();
						int idProducto = Integer.parseInt(txtJBut);
						ParametrosProductoCtrl parProducto = new ParametrosProductoCtrl(PrincipalLogueo.habilitaAuditoria);
						//Para obtener el precio deberíamos recorrer las elecciones de la pregunta y capturar el precio
						double precioProducto = parProducto.obtenerPrecioPilaProducto(idProducto);
						double cantidad = 0.5;
						//OJO ACA SERÁ NECEASARIO INTERVENCIÓN
						int idDetalleMaster = pedCtrl.obtenerIdDetalleMaster(idDetallePedido);
						DetallePedido detPedido = new DetallePedido(0,VentPedTomarPedidos.idPedido,idProducto,cantidad,precioProducto, cantidad*precioProducto, "", idDetalleMaster,"N","",contadorDetallePedido);
						detPedido.setIdDetalleModificador(idDetallePedido);
						int idDetalle = pedCtrl.insertarDetallePedido(detPedido);
						detPedido.setIdDetallePedido(idDetalle);
						if(idDetalle > 0)
						{
							VentPedTomarPedidos.detallesPedido.add(detPedido);
							
							VentPedTomarPedidos.totalPedido = VentPedTomarPedidos.totalPedido + detPedido.getValorTotal();
							//Para pintar la nueva adición de producto y fijar el nuevo valor se ejecutará cuando se active la ventana
						}
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
			//Al retornar la idea sería organizar detallesPedido
			VentPedTomarPedidos.detallesPedido = pedCtrl.ordenarDetallePedido(VentPedTomarPedidos.detallesPedido);
			dispose();

	}
}
