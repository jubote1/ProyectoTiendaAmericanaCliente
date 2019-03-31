package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.html.parser.ParserDelegator;

import capaControlador.AutenticacionCtrl;
import capaControlador.InventarioCtrl;
import capaControlador.OperacionesTiendaCtrl;
import capaControlador.ParametrosCtrl;
import capaControlador.PedidoCtrl;
import capaModelo.DetallePedido;
import capaModelo.FechaSistema;
import capaModelo.Parametro;
import capaModelo.TipoPedido;
import capaModelo.Usuario;

import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Properties;

import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.GridLayout;
import javax.swing.border.LineBorder;
import javax.swing.ImageIcon;

//Implementamos runnable para crear un hilo que al momento de laconfirmación del pedido se cree un hilo paralelo
//que se encargue de agregar la información de consumo de inventario
public class VentPrincipalLogueRapido extends JDialog implements Runnable {

	private JPanel contentenorFinPago;
	private final Action action = new SwingAction();
	private JPasswordField contraRapida;
	private AutenticacionCtrl autCtrl = new AutenticacionCtrl(PrincipalLogueo.habilitaAuditoria);
	ParametrosCtrl parCtrl = new ParametrosCtrl(PrincipalLogueo.habilitaAuditoria);
	PedidoCtrl pedCtrl = new PedidoCtrl(PrincipalLogueo.habilitaAuditoria);
	Thread hiloValidacion;
	//Variable que almacena el tipo de presnetación qeu tiene actualmente el sistema.
		int valPresentacion;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentPrincipalLogueRapido frame = new VentPrincipalLogueRapido( null, true);
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
	public VentPrincipalLogueRapido(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		setTitle("INGRESO R\u00C1PIDO APLICACI\u00D3N");
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setDefaultCloseOperation(0);
		//setBounds(0,0, 563, 300);
		int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	    int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		//setBounds((ancho / 2) - (this.getWidth() / 2), (alto / 2) - (this.getHeight() / 2), 400, 520);
	    setBounds(0, 0, 400, 520);
		contentenorFinPago = new JPanel();
		contentenorFinPago.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentenorFinPago);
		contentenorFinPago.setLayout(null);
		ImageIcon img = new ImageIcon("iconos\\LogoPequePizzaAmericana.jpg");
		setIconImage(img.getImage());
		boolean estaAperturado = pedCtrl.isSistemaAperturado();
		FechaSistema fechasSistema = pedCtrl.obtenerFechasSistema();
		JButton btnNum_1 = new JButton("1");
		fijarValorPresentacion();
		btnNum_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contraRapida.setText(contraRapida.getText()+"1");
			}
		});
		btnNum_1.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_1.setBounds(66, 253, 70, 70);
		btnNum_1.setBackground(new Color(45,107,113));
		btnNum_1.setForeground(new Color(255,255,255));
		contentenorFinPago.add(btnNum_1);
		
		JButton btnNum_2 = new JButton("2");
		btnNum_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contraRapida.setText(contraRapida.getText()+"2");
			}
		});
		btnNum_2.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_2.setBounds(146, 253, 70, 70);
		btnNum_2.setBackground(new Color(45,107,113));
		btnNum_2.setForeground(new Color(255,255,255));
		contentenorFinPago.add(btnNum_2);
		
		JButton btnNum_3 = new JButton("3");
		btnNum_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contraRapida.setText(contraRapida.getText()+"3");
			}
		});
		btnNum_3.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_3.setBounds(226, 253, 70, 70);
		btnNum_3.setBackground(new Color(45,107,113));
		btnNum_3.setForeground(new Color(255,255,255));
		contentenorFinPago.add(btnNum_3);
		
		JButton btnNum_4 = new JButton("4");
		btnNum_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contraRapida.setText(contraRapida.getText()+"4");
			}
		});
		btnNum_4.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_4.setBounds(66, 174, 70, 70);
		btnNum_4.setBackground(new Color(45,107,113));
		btnNum_4.setForeground(new Color(255,255,255));
		contentenorFinPago.add(btnNum_4);
		
		JButton btnNum_5 = new JButton("5");
		btnNum_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contraRapida.setText(contraRapida.getText()+"5");
			}
		});
		btnNum_5.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_5.setBounds(146, 174, 70, 70);
		btnNum_5.setBackground(new Color(45,107,113));
		btnNum_5.setForeground(new Color(255,255,255));
		contentenorFinPago.add(btnNum_5);
		
		JButton btnNum_6 = new JButton("6");
		btnNum_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contraRapida.setText(contraRapida.getText()+"6");
			}
		});
		btnNum_6.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_6.setBounds(226, 174, 70, 70);
		btnNum_6.setBackground(new Color(45,107,113));
		btnNum_6.setForeground(new Color(255,255,255));
		contentenorFinPago.add(btnNum_6);
		
		JButton btnNum_7 = new JButton("7");
		btnNum_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				contraRapida.setText(contraRapida.getText()+"7");
			}
		});
		btnNum_7.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_7.setBounds(66, 93, 70, 70);
		btnNum_7.setBackground(new Color(45,107,113));
		btnNum_7.setForeground(new Color(255,255,255));
		contentenorFinPago.add(btnNum_7);
		
		JButton btnNum_8 = new JButton("8");
		btnNum_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contraRapida.setText(contraRapida.getText()+"8");
			}
		});
		btnNum_8.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_8.setBounds(146, 93, 70, 70);
		btnNum_8.setBackground(new Color(45,107,113));
		btnNum_8.setForeground(new Color(255,255,255));
		contentenorFinPago.add(btnNum_8);
		
		JButton btnNum_9 = new JButton("9");
		btnNum_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contraRapida.setText(contraRapida.getText()+"9");
			}
		});
		btnNum_9.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_9.setBounds(226, 93, 70, 70);
		btnNum_9.setBackground(new Color(45,107,113));
		btnNum_9.setForeground(new Color(255,255,255));
		contentenorFinPago.add(btnNum_9);
		
		JButton btnNum_0 = new JButton("0");
		btnNum_0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contraRapida.setText(contraRapida.getText()+"0");
			}
		});
		btnNum_0.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_0.setBounds(146, 334, 70, 70);
		btnNum_0.setBackground(new Color(45,107,113));
		btnNum_0.setForeground(new Color(255,255,255));
		contentenorFinPago.add(btnNum_0);
		//Instanciamos el hilo que se va a encargar del descuento de inventario
		JButton btnIngresar = new JButton("Ingresar");
		btnIngresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String claveRapida = contraRapida.getText();
				Usuario usuLogueado = autCtrl.validarAutenticacionRapida(claveRapida);
				if(usuLogueado.getIdUsuario() > 0)
				{
					//Cuando se supera el logueo asignamos la variable estática de idUsuario.
					PrincipalLogueo.idUsuario = usuLogueado.getIdUsuario();
					if(usuLogueado.getIngreso() == 0)
					{
						JOptionPane.showConfirmDialog(null,  "Bienvenido al Sistema " + usuLogueado.getNombreLargo() + ". Este es tu primer Ingreso de este día." , "Ingresaste al Sistema por primera vez en el día!!", JOptionPane.OK_OPTION);
					}else
					{
						JOptionPane.showConfirmDialog(null,  "Bienvenido al Sistema " + usuLogueado.getNombreLargo() , "Ingresaste al Sistema!!", JOptionPane.OK_OPTION);
					}
					if(!estaAperturado)
					{
						//Llamamos método para validar el estado de la fecha respecto a la última apertura.
						OperacionesTiendaCtrl operCtrl = new OperacionesTiendaCtrl(PrincipalLogueo.habilitaAuditoria);
						String fechaMayor = operCtrl.validarEstadoFechaSistema();
						//String fechaAumentada = operCtrl.aumentarFecha(fechasSistema.getFechaApertura());
						Object seleccion = JOptionPane.showInputDialog(
								   null,
								   "¿El día no ha sido aperturado, desea abrirlo? Seleccione opcion",
								   "Selector de opciones",
								   JOptionPane.QUESTION_MESSAGE,
								   null,  // null para icono defecto
								   new Object[] { fechaMayor }, 
								   fechaMayor );
						if(seleccion == null)
						{
							dispose();
							return;
						}
						else
						{
							//Realizar cambio de la fecha
							operCtrl.realizarAperturaDia(seleccion.toString());
						}
					}
					//Fijamos el usuario que se está loguendo
					Sesion.setUsuario(usuLogueado.getNombreUsuario());
					Sesion.setIdUsuario(usuLogueado.getIdUsuario());
					Sesion.setAccesosMenus(autCtrl.obtenerAccesosPorMenuUsuario(usuLogueado.getNombreUsuario()));
					Sesion.setAccesosOpcion(autCtrl.obtenerAccesosPorOpcionObj(usuLogueado.getIdTipoEmpleado()));
					Sesion.setIdTipoEmpleado(usuLogueado.getidTipoEmpleado());
										
					if(usuLogueado.getTipoInicio().equals("Ventana Menús"))
					{
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
					}else if(usuLogueado.getTipoInicio().equals("Maestro Pedidos"))
					{
						VentPedTransaccional transacciones = new VentPedTransaccional();
						transacciones.setVisible(true);
					}else if(usuLogueado.getTipoInicio().equals("Comanda Pedidos"))
					{
						VentPedComandaPedidos ventPrincipal = new VentPedComandaPedidos();
						ventPrincipal.setVisible(true);
					}
					dispose();
					parent.dispose();
				}else
				{
					JOptionPane.showMessageDialog(null, "Clave Rápida Incorrecta", "ERROR DE LOGUEO", JOptionPane.ERROR_MESSAGE);
					contraRapida.setText("");
				}
			}
		});
		btnIngresar.setFont(new Font("Calibri", Font.BOLD, 35));
		btnIngresar.setBounds(10, 413, 182, 53);
		contentenorFinPago.add(btnIngresar);
		
				
		contraRapida = new JPasswordField();
		contraRapida.setHorizontalAlignment(SwingConstants.RIGHT);
		contraRapida.setForeground(Color.BLUE);
		contraRapida.setFont(new Font("Calibri", Font.BOLD, 32));
		contraRapida.setEditable(false);
		contraRapida.setColumns(10);
		contraRapida.setBackground(Color.WHITE);
		contraRapida.setBounds(66, 32, 230, 50);
		contentenorFinPago.add(contraRapida);
		
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnSalir.setFont(new Font("Calibri", Font.BOLD, 35));
		btnSalir.setBounds(202, 413, 182, 53);
		contentenorFinPago.add(btnSalir);
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
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
	
	
	//Implementamos la acción enfocada para el descuento de inventarios luego de la finalización del pedido
	public void run(){
	
	}
	
	public void descontarInventario()
	{
		//En este punto es cuando clareamos las variables del tipo de pedido que son estáticas y sabiendo qeu se finalizó
		//el pedido es neceseario clarear las variables del jFrame de TomarPedidos
		InventarioCtrl invCtrl = new InventarioCtrl(PrincipalLogueo.habilitaAuditoria);
		int idPedidoDescontar = VentPedTomarPedidos.idPedido;
		boolean reintInv = invCtrl.descontarInventarioPedido(idPedidoDescontar);
		if(!reintInv)
		{
			JOptionPane.showMessageDialog(null, "Se presentaron inconvenientes en el descuento de los inventarios " , "Error en Descuento de Inventarios ", JOptionPane.ERROR_MESSAGE);
		}
	}
}
