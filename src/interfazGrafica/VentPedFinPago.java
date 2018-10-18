package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.html.parser.ParserDelegator;

import capaControlador.InventarioCtrl;
import capaControlador.PedidoCtrl;
import capaModelo.TipoPedido;

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
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.GridLayout;
import javax.swing.border.LineBorder;
import javax.swing.ImageIcon;

public class VentPedFinPago extends JDialog {

	private JPanel contentenorFinPago;
	private final Action action = new SwingAction();
	private JTextField displayPago;
	private JTextField txtCantidadAdeudada;
	
	public double Efectivo = 0, Tarjeta = 0, Cambio = 0 , Total = VentPedTomarPedidos.totalPedido - VentPedTomarPedidos.descuento ,Deuda = Total ;
	public  boolean boolEfectivo = true, boolTarjeta = false; 
	private JTable tablePago;
	private JTextField displayTotal;
	private boolean hayFormaPago = false;
	private PedidoCtrl pedCtrl = new PedidoCtrl(PrincipalLogueo.habilitaAuditoria);
	public void clarearVarEstaticas()
	{
		Efectivo = 0;
		Tarjeta = 0;
		Cambio = 0;
		boolEfectivo = true;
		boolTarjeta = false;
	}
		
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentPedFinPago frame = new VentPedFinPago(false, null, true);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static boolean existePunto (String Valor){
		boolean resultado = false;
		int i = 0;
		do {
			if (Valor.substring(i,i+1).equals(".")) {
				resultado = true;
				i = Valor.length();
			}else {
				i++;
			}
		} while (i < Valor.length());
		
		return resultado;
	}
	
	public static String Formato (double valor){
		String StrResultado = "";
		NumberFormat Formatter = NumberFormat.getInstance(Locale.ENGLISH);
		StringBuilder StrTotal = null;
		
		Formatter.setMaximumFractionDigits(2);
		StrTotal = new StringBuilder(Formatter.format(valor));
		StrTotal.insert(0, "$ ");
		
		StrResultado = StrTotal.toString();
		
		return StrResultado;
	}
	
//	public void limiteDisplay(KeyEvent evt){
//		int limite = 12;
//		if (displayPago.getText().length() >= limite) {
//			evt.consume();
//		}
//	}

	/**
	 * Create the frame.
	 */
	public VentPedFinPago(boolean existeFormaPago,java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		setTitle("FORMA DE PAGO DEL PEDIDO");
		hayFormaPago = existeFormaPago;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0,0, 961, 636);
		int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	    int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		setBounds((ancho / 2) - (this.getWidth() / 2), (alto / 2) - (this.getHeight() / 2), 961, 636);
		contentenorFinPago = new JPanel();
		contentenorFinPago.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentenorFinPago);
		contentenorFinPago.setLayout(null);
				
		JButton btnNum_1 = new JButton("1");
		btnNum_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayPago.setText(displayPago.getText()+"1");
			}
		});
		btnNum_1.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_1.setBounds(222, 371, 70, 70);
		btnNum_1.setBackground(new Color(45,107,113));
		btnNum_1.setForeground(new Color(255,255,255));
		contentenorFinPago.add(btnNum_1);
		
		JButton btnNum_2 = new JButton("2");
		btnNum_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayPago.setText(displayPago.getText()+"2");
			}
		});
		btnNum_2.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_2.setBounds(302, 371, 70, 70);
		btnNum_2.setBackground(new Color(45,107,113));
		btnNum_2.setForeground(new Color(255,255,255));
		contentenorFinPago.add(btnNum_2);
		
		JButton btnNum_3 = new JButton("3");
		btnNum_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayPago.setText(displayPago.getText()+"3");
			}
		});
		btnNum_3.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_3.setBounds(382, 371, 70, 70);
		btnNum_3.setBackground(new Color(45,107,113));
		btnNum_3.setForeground(new Color(255,255,255));
		contentenorFinPago.add(btnNum_3);
		
		JButton btnNum_4 = new JButton("4");
		btnNum_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayPago.setText(displayPago.getText()+"4");
			}
		});
		btnNum_4.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_4.setBounds(222, 292, 70, 70);
		btnNum_4.setBackground(new Color(45,107,113));
		btnNum_4.setForeground(new Color(255,255,255));
		contentenorFinPago.add(btnNum_4);
		
		JButton btnNum_5 = new JButton("5");
		btnNum_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayPago.setText(displayPago.getText()+"5");
			}
		});
		btnNum_5.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_5.setBounds(302, 292, 70, 70);
		btnNum_5.setBackground(new Color(45,107,113));
		btnNum_5.setForeground(new Color(255,255,255));
		contentenorFinPago.add(btnNum_5);
		
		JButton btnNum_6 = new JButton("6");
		btnNum_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayPago.setText(displayPago.getText()+"6");
			}
		});
		btnNum_6.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_6.setBounds(382, 292, 70, 70);
		btnNum_6.setBackground(new Color(45,107,113));
		btnNum_6.setForeground(new Color(255,255,255));
		contentenorFinPago.add(btnNum_6);
		
		JButton btnNum_7 = new JButton("7");
		btnNum_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				displayPago.setText(displayPago.getText()+"7");
			}
		});
		btnNum_7.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_7.setBounds(222, 211, 70, 70);
		btnNum_7.setBackground(new Color(45,107,113));
		btnNum_7.setForeground(new Color(255,255,255));
		contentenorFinPago.add(btnNum_7);
		
		JButton btnNum_8 = new JButton("8");
		btnNum_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayPago.setText(displayPago.getText()+"8");
			}
		});
		btnNum_8.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_8.setBounds(302, 211, 70, 70);
		btnNum_8.setBackground(new Color(45,107,113));
		btnNum_8.setForeground(new Color(255,255,255));
		contentenorFinPago.add(btnNum_8);
		
		JButton btnNum_9 = new JButton("9");
		btnNum_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayPago.setText(displayPago.getText()+"9");
			}
		});
		btnNum_9.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_9.setBounds(382, 211, 70, 70);
		btnNum_9.setBackground(new Color(45,107,113));
		btnNum_9.setForeground(new Color(255,255,255));
		contentenorFinPago.add(btnNum_9);
		
		JButton btnNum_0 = new JButton("0");
		btnNum_0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayPago.setText(displayPago.getText()+"0");
			}
		});
		btnNum_0.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_0.setBounds(222, 452, 70, 70);
		btnNum_0.setBackground(new Color(45,107,113));
		btnNum_0.setForeground(new Color(255,255,255));
		contentenorFinPago.add(btnNum_0);
		
		JButton btnNum_000 = new JButton("000");
		btnNum_000.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayPago.setText(displayPago.getText()+"000");
			}
		});
		btnNum_000.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_000.setBounds(302, 452, 70, 70);
		btnNum_000.setBackground(new Color(45,107,113));
		btnNum_000.setForeground(new Color(255,255,255));
		contentenorFinPago.add(btnNum_000);
		
		JButton btnPoint = new JButton(".");
		btnPoint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean conPunto = false;
				
				conPunto = existePunto(displayPago.getText());
				if (conPunto) {
					displayPago.setText(displayPago.getText());
				}else {
					displayPago.setText(displayPago.getText()+".");
				}							
			}
		});
		btnPoint.setFont(new Font("Calibri", Font.BOLD, 24));
		btnPoint.setBounds(382, 452, 70, 70);
		btnPoint.setBackground(new Color(45,107,113));
		btnPoint.setForeground(new Color(255,255,255));
		contentenorFinPago.add(btnPoint);
		
		displayPago = new JTextField();
		displayPago.setEditable(false);
		displayPago.setFont(new Font("Calibri", Font.BOLD, 30));
		displayPago.setForeground(Color.YELLOW);
		displayPago.setBackground(Color.BLACK);
		displayPago.setHorizontalAlignment(SwingConstants.RIGHT);
		displayPago.setBounds(222, 138, 230, 50);
		contentenorFinPago.add(displayPago);
		displayPago.setColumns(10);
		
		JButton btnFinalizar = new JButton("Finalizar");
		btnFinalizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				//REALIZAMOS CONFIRMACIÓN DEL PEDIDO
//				int confirmado = JOptionPane.showConfirmDialog(
//						   null,"<html><center><b>A continuación la informacion para Confirmación del Pedido.</b><br>"
//					                 + "<p>CLIENTE: "+ VentPedTomarPedidos.nombreCliente+" </p>" +
//								   "<p>Por un valor TOTAL: " + Total  +"</p>" +
//								   "<p>Con un Descuento: " + VentPedTomarPedidos.descuento  +"</p>" +
//								   "<p>NÚMERO DE PEDIDO: " + VentPedTomarPedidos.idPedido +"</p>" 
//								   
//						   );
//				if (JOptionPane.OK_OPTION == confirmado)
//				{
//					//Tomamos la información para insertar la forma de pago
//					PedidoCtrl pedCtrl = new PedidoCtrl();
//					//Si el pedido tenía forma de pago, deberemos de eliminar la forma de pago anterior
//					if(hayFormaPago)
//					{
//						boolean respuesta = pedCtrl.eliminarPedidoFormaPago( VentPedTomarPedidos.idPedido);
//					}
//					// Se envían datos para la inserción de la forma de pago.
//					boolean resFormaPago = pedCtrl.insertarPedidoFormaPago(Efectivo, Tarjeta, Total, Cambio, VentPedTomarPedidos.idPedido);
//					if(resFormaPago)
//					{
//						//Ingresamos lógica para tomar el tipo de pedido 
//						int idTipoPedido;
//						try
//						{
//							TipoPedido tipPedido = VentPedTomarPedidos.tiposPedidos.get(VentPedTomarPedidos.numTipoPedidoAct);
//							idTipoPedido = tipPedido.getIdTipoPedido();
//						}catch(Exception e)
//						{
//							idTipoPedido = 0;
//						}
//						
//						// En este punto finalizamos el pedido
//						boolean resFinPedido = pedCtrl.finalizarPedido(VentPedTomarPedidos.idPedido, 30/*tiempoPedido*/, idTipoPedido);
//						if (resFinPedido)
//						{
//							//En este punto es cuando clareamos las variables del tipo de pedido que son estáticas y sabiendo qeu se finalizó
//							//el pedido es neceseario clarear las variables del jFrame de TomarPedidos
//							InventarioCtrl invCtrl = new InventarioCtrl();
//							boolean reintInv = invCtrl.descontarInventarioPedido(VentPedTomarPedidos.idPedido);
//							if(!reintInv)
//							{
//								JOptionPane.showMessageDialog(null, "Se presentaron inconvenientes en el descuento de los inventarios " , "Error en Descuento de Inventarios ", JOptionPane.ERROR_MESSAGE);
//							}
//							clarearVarEstaticas();
//							VentPedTomarPedidos.clarearVarEstaticas();
//							dispose();
//						}
//					}
//				}
//				
				//Tomamos la información para insertar la forma de pago
				//Si el pedido tenía forma de pago, deberemos de eliminar la forma de pago anterior
				if(hayFormaPago)
				{
					pedCtrl.eliminarPedidoFormaPago( VentPedTomarPedidos.idPedido);
				}
				// Se envían datos para la inserción de la forma de pago.
				boolean resFormaPago = pedCtrl.insertarPedidoFormaPago(Efectivo, Tarjeta, Total, Cambio, VentPedTomarPedidos.idPedido);
				if(resFormaPago)
				{
					//Ingresamos lógica para tomar el tipo de pedido 
					int idTipoPedido;
					try
					{
						TipoPedido tipPedido = VentPedTomarPedidos.tiposPedidos.get(VentPedTomarPedidos.numTipoPedidoAct);
						idTipoPedido = tipPedido.getIdTipoPedido();
					}catch(Exception e)
					{
						idTipoPedido = 0;
					}
					
					// En este punto finalizamos el pedido
					boolean resFinPedido = pedCtrl.finalizarPedido(VentPedTomarPedidos.idPedido, 30/*tiempoPedido*/, idTipoPedido);
					if (resFinPedido)
					{
						//En este punto es cuando clareamos las variables del tipo de pedido que son estáticas y sabiendo qeu se finalizó
						//el pedido es neceseario clarear las variables del jFrame de TomarPedidos
						InventarioCtrl invCtrl = new InventarioCtrl();
						boolean reintInv = invCtrl.descontarInventarioPedido(VentPedTomarPedidos.idPedido);
						if(!reintInv)
						{
							JOptionPane.showMessageDialog(null, "Se presentaron inconvenientes en el descuento de los inventarios " , "Error en Descuento de Inventarios ", JOptionPane.ERROR_MESSAGE);
						}
						clarearVarEstaticas();
						VentPedTomarPedidos.clarearVarEstaticas();
						parent.dispose();
						//Definimos que nos iremos para la ventana transaccional
						VentPedTransaccional transacciones = new VentPedTransaccional();
						transacciones.setVisible(true);
						dispose();
						
					}
				}
			}
		});
		btnFinalizar.setEnabled(false);
		btnFinalizar.setFont(new Font("Calibri", Font.BOLD, 35));
		btnFinalizar.setBounds(753, 534, 182, 53);
		contentenorFinPago.add(btnFinalizar);
		
		txtCantidadAdeudada = new JTextField();
		txtCantidadAdeudada.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtCantidadAdeudada.setBackground(Color.YELLOW);
		txtCantidadAdeudada.setForeground(Color.RED);
		txtCantidadAdeudada.setHorizontalAlignment(SwingConstants.CENTER);
		txtCantidadAdeudada.setEditable(false);
		txtCantidadAdeudada.setText("Cantidad Adeudada");
		txtCantidadAdeudada.setBounds(222, 66, 230, 20);
		contentenorFinPago.add(txtCantidadAdeudada);
		txtCantidadAdeudada.setColumns(10);
		
		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String BorrarValor = displayPago.getText();
				if (BorrarValor.length() > 0) {
					displayPago.setText(BorrarValor.substring(0, BorrarValor.length()-1));
				}
			}
		});
		btnBorrar.setFont(new Font("Calibri", Font.BOLD, 24));
		btnBorrar.setBounds(462, 452, 100, 70);
		contentenorFinPago.add(btnBorrar);
		
		JButton btnAplicar = new JButton("Aplicar Efectivo");
		btnAplicar.setFont(new Font("Calibri", Font.BOLD, 24));
		btnAplicar.setBounds(719, 394, 202, 70);
		btnAplicar.setBackground(new Color(86,106,187));
		btnAplicar.setForeground(new Color(255,255,255));
		contentenorFinPago.add(btnAplicar);
		
		tablePago = new JTable();
		tablePago.setShowGrid(false);
		tablePago.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		tablePago.setShowHorizontalLines(false);
		tablePago.setShowVerticalLines(false);
		tablePago.setEnabled(false);
		tablePago.setFont(new Font("Calibri", Font.PLAIN, 20));
		tablePago.setModel(new DefaultTableModel(
			new Object[][] {
				{"Total", Formato(Total)},
				{"Efectivo", Formato(Efectivo)},
				{"Tarjeta", Formato(Tarjeta)},
				{"Cambio", Formato(Cambio)},
			},
			new String[] {
				"New column", "New column"
			}
		));
		tablePago.setBounds(10, 366, 202, 120);
		contentenorFinPago.add(tablePago);
		tablePago.setRowHeight(30);
		
		displayTotal = new JTextField();
		displayTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		displayTotal.setForeground(Color.BLUE);
		displayTotal.setFont(new Font("Calibri", Font.BOLD, 32));
		displayTotal.setEditable(false);
		displayTotal.setColumns(10);
		displayTotal.setBackground(Color.WHITE);
		displayTotal.setBounds(222, 87, 230, 50);
		contentenorFinPago.add(displayTotal);
		displayTotal.setText(Formato(Deuda));
		
		JPanel panelMetodosPagos = new JPanel();
		panelMetodosPagos.setBounds(719, 178, 204, 205);
		contentenorFinPago.add(panelMetodosPagos);
		panelMetodosPagos.setLayout(new GridLayout(2, 1, 0, 0));
		
		JButton btnEfectivo = new JButton("Efectivo");
		btnEfectivo.setHorizontalAlignment(SwingConstants.TRAILING);
		btnEfectivo.setIcon(new ImageIcon(VentPedFinPago.class.getResource("/icons/efectivo.jpg")));
		panelMetodosPagos.add(btnEfectivo);
		btnEfectivo.setBackground(new Color(230,230,230));
		btnEfectivo.setBackground(new Color(86,106,187));
		btnEfectivo.setForeground(new Color(255,255,255));
		btnEfectivo.setFont(new Font("Calibri", Font.BOLD, 26));
		
		JButton btnTarjeta = new JButton("Tarjeta");
		btnTarjeta.setHorizontalAlignment(SwingConstants.TRAILING);
		btnTarjeta.setIcon(new ImageIcon(VentPedFinPago.class.getResource("/icons/credito.jpg")));
		panelMetodosPagos.add(btnTarjeta);
		btnTarjeta.setBackground(new Color(230,230,230));
		btnTarjeta.setFont(new Font("Calibri", Font.BOLD, 26));
		
		JPanel panelPagoRapido = new JPanel();
		panelPagoRapido.setBounds(462, 211, 247, 230);
		contentenorFinPago.add(panelPagoRapido);
		panelPagoRapido.setLayout(new GridLayout(4, 2, 0, 0));
		
		JButton btnPago50000 = new JButton("$ 50,000");
		btnPago50000.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayPago.setText("50000");
			}
		});
		btnPago50000.setFont(new Font("Calibri Light", Font.BOLD, 20));
		panelPagoRapido.add(btnPago50000);
		
		JButton btnPago100000 = new JButton("$ 100,000");
		btnPago100000.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayPago.setText("100000");
			}
		});
		btnPago100000.setFont(new Font("Calibri Light", Font.BOLD, 20));
		panelPagoRapido.add(btnPago100000);
		
		JButton btnPago10000 = new JButton("$ 10,000");
		btnPago10000.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayPago.setText("10000");
			}
		});
		btnPago10000.setFont(new Font("Calibri Light", Font.BOLD, 20));
		panelPagoRapido.add(btnPago10000);
		
		JButton btnPago20000 = new JButton("$ 20,000");
		btnPago20000.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayPago.setText("20000");
			}
		});
		btnPago20000.setFont(new Font("Calibri Light", Font.BOLD, 20));
		panelPagoRapido.add(btnPago20000);
		
		JButton btnPago2000 = new JButton("$ 2,000");
		btnPago2000.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayPago.setText("2000");
			}
		});
		btnPago2000.setFont(new Font("Calibri Light", Font.BOLD, 20));
		panelPagoRapido.add(btnPago2000);
		
		JButton btnPago5000 = new JButton("$ 5,000");
		btnPago5000.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayPago.setText("5000");
			}
		});
		btnPago5000.setFont(new Font("Calibri Light", Font.BOLD, 20));
		panelPagoRapido.add(btnPago5000);
		
		JButton btnPago1000 = new JButton("$ 1,000");
		btnPago1000.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayPago.setText("1000");
			}
		});
		btnPago1000.setFont(new Font("Calibri Light", Font.BOLD, 20));
		panelPagoRapido.add(btnPago1000);
		
		JButton btnIngRetornar = new JButton("Ingresar y Retornar");
		btnIngRetornar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Tomamos la información para insertar la forma de pago
				// Se envían datos para la inserción de la forma de pago.
				boolean resFormaPago = pedCtrl.insertarPedidoFormaPago(Efectivo, Tarjeta, Total, Cambio, VentPedTomarPedidos.idPedido);
				if(resFormaPago)
				{
					clarearVarEstaticas();
					VentPedTomarPedidos.tieneFormaPago= true;
					dispose();
				}
			}
		});
		btnIngRetornar.setEnabled(false);
		btnIngRetornar.setFont(new Font("Calibri", Font.BOLD, 30));
		btnIngRetornar.setBounds(432, 534, 311, 53);
		//Por un tiempo mientras corroboramos que no es necesario no lo adicionaremos, luego de esto lo eliminaremos
		//contentenorFinPago.add(btnIngRetornar);
		
		btnEfectivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAplicar.setText("Aplicar "+btnEfectivo.getText());
				btnAplicar.setBackground(new Color(86,106,187));
				btnAplicar.setForeground(new Color(255,255,255));
				btnEfectivo.setBackground(new Color(86,106,187));
				btnEfectivo.setForeground(new Color(255,255,255));
				btnTarjeta.setBackground(new Color(230,230,230));
				btnAplicar.setEnabled(true);
				boolEfectivo = true;
				boolTarjeta = false;
			}
		});
		
		btnTarjeta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAplicar.setText("Aplicar "+btnTarjeta.getText());
				btnAplicar.setBackground(new Color(255,106,98));
				btnAplicar.setForeground(new Color(255,255,255));
				btnTarjeta.setBackground(new Color(255,106,98));
				btnTarjeta.setForeground(new Color(255,255,255));
				btnEfectivo.setBackground(new Color(230,230,230));
				btnAplicar.setEnabled(true);
				boolEfectivo = false;
				boolTarjeta = true;
			}
		});
		
		btnAplicar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAplicar.setText("Aplicar");
				if (boolEfectivo) {
					if (!displayPago.getText().equals("")) {
						Deuda -= Double.parseDouble(displayPago.getText());
						Efectivo += Double.parseDouble(displayPago.getText()); 
					} else {
						Efectivo = Deuda;
						Deuda = 0;
					}
					displayPago.setText("");
					tablePago.setValueAt(Formato(Efectivo), 1, 1);
					if (Total < Efectivo + Tarjeta) {
						Cambio = Deuda * - 1;
						tablePago.setValueAt(Formato(Cambio), 3, 1);
						Deuda = 0;
					}
				}else if (boolTarjeta) {
					if (!displayPago.getText().equals("")) {
						Deuda -= Double.parseDouble(displayPago.getText());
						Tarjeta += Double.parseDouble(displayPago.getText());
					}else {
						Tarjeta = Deuda;
						Deuda = 0;
					}
					displayPago.setText("");
					tablePago.setValueAt(Formato(Tarjeta), 2, 1);
					if (Total < Efectivo + Tarjeta) {
						Cambio = Deuda * - 1;
						tablePago.setValueAt(Formato(Cambio), 3, 1);
						Deuda = 0;
					}
				}
				displayTotal.setText(Formato(Deuda));
				if (Deuda == 0) {
					btnFinalizar.setEnabled(true);
					btnIngRetornar.setEnabled(true);
					btnEfectivo.setEnabled(false);
					btnTarjeta.setEnabled(false);
					btnAplicar.setEnabled(false);
				}
			}
		});
		
		//Validamos si ya existe forma de pago para cargar la información en el frame
		if(hayFormaPago)
		{
			
			
		}
		
	}
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
