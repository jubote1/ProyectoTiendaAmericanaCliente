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
import java.awt.Frame;
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
import java.awt.Window;

import javax.swing.border.LineBorder;
import javax.swing.ImageIcon;

//Implementamos runnable para crear un hilo que al momento de laconfirmación del pedido se cree un hilo paralelo
//que se encargue de agregar la información de consumo de inventario
public class VentPedMultiplicador extends JDialog{

	private JPanel contentenorFinPago;
	private final Action action = new SwingAction();
	private AutenticacionCtrl autCtrl = new AutenticacionCtrl(PrincipalLogueo.habilitaAuditoria);
	ParametrosCtrl parCtrl = new ParametrosCtrl(PrincipalLogueo.habilitaAuditoria);
	PedidoCtrl pedCtrl = new PedidoCtrl(PrincipalLogueo.habilitaAuditoria);
	OperacionesTiendaCtrl operCtrl = new OperacionesTiendaCtrl(PrincipalLogueo.habilitaAuditoria);
	Thread hiloValidacion;
	//Variable que almacena el tipo de presnetación qeu tiene actualmente el sistema.
		FechaSistema fechasSistema;
		String fechaMayor;
		Window ventanaPadre;
		private JTextField txtValMultiplicador;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentPedMultiplicador frame = new VentPedMultiplicador( null, true);
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
	public VentPedMultiplicador(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		setTitle("MULTIPLICADOR DE PEDIDO");
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setDefaultCloseOperation(0);
		//setBounds(0,0, 563, 300);
		int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	    int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		//setBounds((ancho / 2) - (this.getWidth() / 2), (alto / 2) - (this.getHeight() / 2), 400, 520);
	    setBounds(0, 0, 267, 497);
		contentenorFinPago = new JPanel();
		contentenorFinPago.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentenorFinPago);
		contentenorFinPago.setLayout(null);
		ImageIcon img = new ImageIcon("iconos\\LogoPequePizzaAmericana.jpg");
		setIconImage(img.getImage());
		ventanaPadre = this;
		boolean estaAperturado = pedCtrl.isSistemaAperturado();
		fechasSistema = pedCtrl.obtenerFechasSistema();
		fechaMayor = operCtrl.validarEstadoFechaSistema();
		JButton btnNum_1 = new JButton("1");
		btnNum_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtValMultiplicador.setText(txtValMultiplicador.getText()+"1");
			}
		});
		btnNum_1.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_1.setBounds(10, 234, 70, 70);
		btnNum_1.setBackground(new Color(45,107,113));
		btnNum_1.setForeground(new Color(255,255,255));
		contentenorFinPago.add(btnNum_1);
		
		JButton btnNum_2 = new JButton("2");
		btnNum_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtValMultiplicador.setText(txtValMultiplicador.getText()+"2");
			}
		});
		btnNum_2.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_2.setBounds(90, 234, 70, 70);
		btnNum_2.setBackground(new Color(45,107,113));
		btnNum_2.setForeground(new Color(255,255,255));
		contentenorFinPago.add(btnNum_2);
		
		JButton btnNum_3 = new JButton("3");
		btnNum_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtValMultiplicador.setText(txtValMultiplicador.getText()+"3");
			}
		});
		btnNum_3.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_3.setBounds(170, 234, 70, 70);
		btnNum_3.setBackground(new Color(45,107,113));
		btnNum_3.setForeground(new Color(255,255,255));
		contentenorFinPago.add(btnNum_3);
		
		JButton btnNum_4 = new JButton("4");
		btnNum_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtValMultiplicador.setText(txtValMultiplicador.getText()+"4");
			}
		});
		btnNum_4.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_4.setBounds(10, 153, 70, 70);
		btnNum_4.setBackground(new Color(45,107,113));
		btnNum_4.setForeground(new Color(255,255,255));
		contentenorFinPago.add(btnNum_4);
		
		JButton btnNum_5 = new JButton("5");
		btnNum_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtValMultiplicador.setText(txtValMultiplicador.getText()+"5");
			}
		});
		btnNum_5.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_5.setBounds(90, 153, 70, 70);
		btnNum_5.setBackground(new Color(45,107,113));
		btnNum_5.setForeground(new Color(255,255,255));
		contentenorFinPago.add(btnNum_5);
		
		JButton btnNum_6 = new JButton("6");
		btnNum_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtValMultiplicador.setText(txtValMultiplicador.getText()+"6");
			}
		});
		btnNum_6.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_6.setBounds(170, 153, 70, 70);
		btnNum_6.setBackground(new Color(45,107,113));
		btnNum_6.setForeground(new Color(255,255,255));
		contentenorFinPago.add(btnNum_6);
		
		JButton btnNum_7 = new JButton("7");
		btnNum_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtValMultiplicador.setText(txtValMultiplicador.getText()+"7");
			}
		});
		btnNum_7.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_7.setBounds(10, 72, 70, 70);
		btnNum_7.setBackground(new Color(45,107,113));
		btnNum_7.setForeground(new Color(255,255,255));
		contentenorFinPago.add(btnNum_7);
		
		JButton btnNum_8 = new JButton("8");
		btnNum_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtValMultiplicador.setText(txtValMultiplicador.getText()+"8");
			}
		});
		btnNum_8.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_8.setBounds(90, 72, 70, 70);
		btnNum_8.setBackground(new Color(45,107,113));
		btnNum_8.setForeground(new Color(255,255,255));
		contentenorFinPago.add(btnNum_8);
		
		JButton btnNum_9 = new JButton("9");
		btnNum_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtValMultiplicador.setText(txtValMultiplicador.getText()+"9");
			}
		});
		btnNum_9.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_9.setBounds(170, 72, 70, 70);
		btnNum_9.setBackground(new Color(45,107,113));
		btnNum_9.setForeground(new Color(255,255,255));
		contentenorFinPago.add(btnNum_9);
		
		JButton btnNum_0 = new JButton("0");
		btnNum_0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtValMultiplicador.setText(txtValMultiplicador.getText()+"0");
			}
		});
		btnNum_0.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_0.setBounds(10, 315, 70, 70);
		btnNum_0.setBackground(new Color(45,107,113));
		btnNum_0.setForeground(new Color(255,255,255));
		contentenorFinPago.add(btnNum_0);
		//Instanciamos el hilo que se va a encargar del descuento de inventario
		JButton btnMultiplicar = new JButton("Multiplicar");
		btnMultiplicar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String valMultiplicador = txtValMultiplicador.getText();
				int valMultPedido = 0;
				try
				{
					valMultPedido = Integer.parseInt(valMultiplicador);
				}catch(Exception e)
				{
					valMultPedido = 0;
				}
				VentPedTomarPedidos VentPed = (VentPedTomarPedidos) parent;
				VentPed.multiplicadorPed = valMultPedido;
				dispose();
			}
		});
		btnMultiplicar.setFont(new Font("Calibri", Font.BOLD, 20));
		btnMultiplicar.setBounds(90, 321, 153, 64);
		contentenorFinPago.add(btnMultiplicar);
		
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentPedTomarPedidos VentPed = (VentPedTomarPedidos) parent;
				VentPed.multiplicadorPed = 0;
				dispose();
			}
		});
		btnSalir.setFont(new Font("Calibri", Font.BOLD, 20));
		btnSalir.setBounds(10, 396, 114, 53);
		contentenorFinPago.add(btnSalir);
		
		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtValMultiplicador.setText("");
			}
		});
		btnLimpiar.setFont(new Font("Calibri", Font.BOLD, 20));
		btnLimpiar.setBounds(134, 396, 104, 53);
		contentenorFinPago.add(btnLimpiar);
		
		txtValMultiplicador = new JTextField();
		txtValMultiplicador.setFont(new Font("Tahoma", Font.BOLD, 23));
		txtValMultiplicador.setBounds(10, 11, 230, 50);
		contentenorFinPago.add(txtValMultiplicador);
		txtValMultiplicador.setColumns(10);
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
