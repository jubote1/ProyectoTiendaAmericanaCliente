package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.html.parser.ParserDelegator;

import capaControlador.PedidoCtrl;
import capaControlador.PromocionesCtrl;
import capaModelo.Oferta;
import capaModelo.OfertaCliente;
import capaModelo.PedidoDescuento;
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
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class VentPedDescuento extends JDialog {

	private JPanel contentenorDescuento;
	private final Action action = new SwingAction();
	private JTextField txtTotalFinal;
	PromocionesCtrl promoCtrl = new PromocionesCtrl();
	public static double DescuentoEfectivo = 0, DescuentoPorcentaje = 0, Total = VentPedTomarPedidos.totalPedido,nuevoTotal = Total ;
	public static boolean boolEfectivo = true, boolPorcentaje = false; 
	public static int idPedido = VentPedTomarPedidos.idPedido;
	private JTextField txtTotal;
	private JTextField txtValorPesos;
	private JTextField txtValorPorcen;
	JTextArea textAreaObservacion;
	JDialog ventanaActual;
	public JLabel lblNombreUsuarioAut;
	public static String usuarioAutorizo;
	private JTextField txtCodigoPromocion;
	//Variable en la cual almacenaremos la ofertaCliente,según el código promocional
	OfertaCliente ofertaCliente;
	JButton btnAplicar;
	JButton btnDesPorcentaje;
	JButton btnDescuentoEfectivo;
	
	public static void clarearVarEstaticas()
	{
		Total = 0;
		nuevoTotal = 0;
		DescuentoEfectivo = 0;
		DescuentoPorcentaje = 0;
		boolEfectivo = true;
		boolPorcentaje = false;
		idPedido = 0;
		
	}
		
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentPedDescuento frame = new VentPedDescuento(null, false);
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
	


	public VentPedDescuento(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		setTitle("APLICAR DESCUENTOS");
		ventanaActual = this;
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setDefaultCloseOperation(0);
		setBounds(0,0, 961, 670);
		int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	    int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		setBounds((ancho / 2) - (this.getWidth() / 2), (alto / 2) - (this.getHeight() / 2), 961, 670);
		contentenorDescuento = new JPanel();
		contentenorDescuento.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentenorDescuento);
		contentenorDescuento.setLayout(null);
		ImageIcon img = new ImageIcon("iconos\\LogoPequePizzaAmericana.jpg");
		setIconImage(img.getImage());
		
		JButton btnNum_1 = new JButton("1");
		btnNum_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(boolEfectivo)
				{
					txtValorPesos.setText(txtValorPesos.getText()+"1");
				}
				else if(boolPorcentaje)
				{
					txtValorPorcen.setText(txtValorPorcen.getText()+"1");
				}
				
			}
		});
		btnNum_1.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_1.setBounds(222, 371, 70, 70);
		btnNum_1.setBackground(new Color(45,107,113));
		btnNum_1.setForeground(new Color(255,255,255));
		contentenorDescuento.add(btnNum_1);
		
		JButton btnNum_2 = new JButton("2");
		btnNum_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(boolEfectivo)
				{
					txtValorPesos.setText(txtValorPesos.getText()+"2");
				}
				else if(boolPorcentaje)
				{
					txtValorPorcen.setText(txtValorPorcen.getText()+"2");
				}
			}
		});
		btnNum_2.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_2.setBounds(302, 371, 70, 70);
		btnNum_2.setBackground(new Color(45,107,113));
		btnNum_2.setForeground(new Color(255,255,255));
		contentenorDescuento.add(btnNum_2);
		
		JButton btnNum_3 = new JButton("3");
		btnNum_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(boolEfectivo)
				{
					txtValorPesos.setText(txtValorPesos.getText()+"3");
				}
				else if(boolPorcentaje)
				{
					txtValorPorcen.setText(txtValorPorcen.getText()+"3");
				}
			}
		});
		btnNum_3.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_3.setBounds(382, 371, 70, 70);
		btnNum_3.setBackground(new Color(45,107,113));
		btnNum_3.setForeground(new Color(255,255,255));
		contentenorDescuento.add(btnNum_3);
		
		JButton btnNum_4 = new JButton("4");
		btnNum_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(boolEfectivo)
				{
					txtValorPesos.setText(txtValorPesos.getText()+"4");
				}
				else if(boolPorcentaje)
				{
					txtValorPorcen.setText(txtValorPorcen.getText()+"4");
				}
			}
		});
		btnNum_4.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_4.setBounds(222, 292, 70, 70);
		btnNum_4.setBackground(new Color(45,107,113));
		btnNum_4.setForeground(new Color(255,255,255));
		contentenorDescuento.add(btnNum_4);
		
		JButton btnNum_5 = new JButton("5");
		btnNum_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(boolEfectivo)
				{
					txtValorPesos.setText(txtValorPesos.getText()+"5");
				}
				else if(boolPorcentaje)
				{
					txtValorPorcen.setText(txtValorPorcen.getText()+"5");
				}
			}
		});
		btnNum_5.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_5.setBounds(302, 292, 70, 70);
		btnNum_5.setBackground(new Color(45,107,113));
		btnNum_5.setForeground(new Color(255,255,255));
		contentenorDescuento.add(btnNum_5);
		
		JButton btnNum_6 = new JButton("6");
		btnNum_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(boolEfectivo)
				{
					txtValorPesos.setText(txtValorPesos.getText()+"6");
				}
				else if(boolPorcentaje)
				{
					txtValorPorcen.setText(txtValorPorcen.getText()+"6");
				}
			}
		});
		btnNum_6.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_6.setBounds(382, 292, 70, 70);
		btnNum_6.setBackground(new Color(45,107,113));
		btnNum_6.setForeground(new Color(255,255,255));
		contentenorDescuento.add(btnNum_6);
		
		JButton btnNum_7 = new JButton("7");
		btnNum_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(boolEfectivo)
				{
					txtValorPesos.setText(txtValorPesos.getText()+"7");
				}
				else if(boolPorcentaje)
				{
					txtValorPorcen.setText(txtValorPorcen.getText()+"7");
				}
			}
		});
		btnNum_7.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_7.setBounds(222, 211, 70, 70);
		btnNum_7.setBackground(new Color(45,107,113));
		btnNum_7.setForeground(new Color(255,255,255));
		contentenorDescuento.add(btnNum_7);
		
		JButton btnNum_8 = new JButton("8");
		btnNum_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(boolEfectivo)
				{
					txtValorPesos.setText(txtValorPesos.getText()+"8");
				}
				else if(boolPorcentaje)
				{
					txtValorPorcen.setText(txtValorPorcen.getText()+"8");
				}
			}
		});
		btnNum_8.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_8.setBounds(302, 211, 70, 70);
		btnNum_8.setBackground(new Color(45,107,113));
		btnNum_8.setForeground(new Color(255,255,255));
		contentenorDescuento.add(btnNum_8);
		
		JButton btnNum_9 = new JButton("9");
		btnNum_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(boolEfectivo)
				{
					txtValorPesos.setText(txtValorPesos.getText()+"9");
				}
				else if(boolPorcentaje)
				{
					txtValorPorcen.setText(txtValorPorcen.getText()+"9");
				}
			}
		});
		btnNum_9.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_9.setBounds(382, 211, 70, 70);
		btnNum_9.setBackground(new Color(45,107,113));
		btnNum_9.setForeground(new Color(255,255,255));
		contentenorDescuento.add(btnNum_9);
		
		JButton btnNum_0 = new JButton("0");
		btnNum_0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(boolEfectivo)
				{
					txtValorPesos.setText(txtValorPesos.getText()+"0");
				}
				else if(boolPorcentaje)
				{
					txtValorPorcen.setText(txtValorPorcen.getText()+"0");
				}
			}
		});
		btnNum_0.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_0.setBounds(222, 452, 70, 70);
		btnNum_0.setBackground(new Color(45,107,113));
		btnNum_0.setForeground(new Color(255,255,255));
		contentenorDescuento.add(btnNum_0);
		
		JButton btnNum_000 = new JButton("000");
		btnNum_000.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(boolEfectivo)
				{
					txtValorPesos.setText(txtValorPesos.getText()+"000");
				}
				else if(boolPorcentaje)
				{
					txtValorPorcen.setText(txtValorPorcen.getText()+"000");
				}
			}
		});
		btnNum_000.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_000.setBounds(302, 452, 70, 70);
		btnNum_000.setBackground(new Color(45,107,113));
		btnNum_000.setForeground(new Color(255,255,255));
		contentenorDescuento.add(btnNum_000);
		
		JButton btnPoint = new JButton(".");
		btnPoint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean conPunto = false;
				
				conPunto = existePunto(txtTotalFinal.getText());
				if (conPunto) {
					txtTotalFinal.setText(txtTotalFinal.getText());
				}else {
					txtTotalFinal.setText(txtTotalFinal.getText()+".");
				}							
			}
		});
		btnPoint.setFont(new Font("Calibri", Font.BOLD, 24));
		btnPoint.setBounds(382, 452, 70, 70);
		btnPoint.setBackground(new Color(45,107,113));
		btnPoint.setForeground(new Color(255,255,255));
		contentenorDescuento.add(btnPoint);
		
		txtTotalFinal = new JTextField();
		txtTotalFinal.setEditable(false);
		txtTotalFinal.setFont(new Font("Calibri", Font.BOLD, 30));
		txtTotalFinal.setForeground(Color.YELLOW);
		txtTotalFinal.setBackground(Color.BLACK);
		txtTotalFinal.setHorizontalAlignment(SwingConstants.RIGHT);
		txtTotalFinal.setBounds(222, 150, 230, 38);
		contentenorDescuento.add(txtTotalFinal);
		txtTotalFinal.setColumns(10);
		
		
		JButton btnFinalizar = new JButton("Finalizar");
		btnFinalizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Tomamos la información para insertar la forma de pago
				if(validarObservacion())
				{
					PedidoCtrl pedCtrl = new PedidoCtrl(PrincipalLogueo.habilitaAuditoria);
					// Se envían datos para la inserción del descuento
					double valorPorcen;
					try
					{
						valorPorcen = Double.parseDouble(txtValorPorcen.getText());
					}catch(Exception e)
					{
						valorPorcen = 0;
					}
					PedidoDescuento descuento = new PedidoDescuento(idPedido, Total -  nuevoTotal, valorPorcen, textAreaObservacion.getText(), "",0,0, Sesion.getUsuario(), usuarioAutorizo );
					boolean resp = pedCtrl.insertarPedidoDescuento(descuento);
					VentPedTomarPedidos.descuento = Total -  nuevoTotal;
					System.out.println("le estamos poniendo descuento " + (VentPedTomarPedidos.descuento));
					txtTotal.setText("");
					txtValorPesos.setText("");
					txtValorPorcen.setText("");
					VentPedTomarPedidos.tieneDescuento = true;
					clarearVarEstaticas();
					dispose();
				}
			}
		});
		btnFinalizar.setEnabled(false);
		btnFinalizar.setFont(new Font("Calibri", Font.BOLD, 40));
		btnFinalizar.setBounds(592, 550, 195, 70);
		contentenorDescuento.add(btnFinalizar);
		
		JButton btnBorrar = new JButton("Borrar");
		
		btnBorrar.setFont(new Font("Calibri", Font.BOLD, 24));
		btnBorrar.setBounds(462, 452, 100, 70);
		contentenorDescuento.add(btnBorrar);
		
		btnAplicar = new JButton("Aplicar Descuento");
		btnAplicar.setFont(new Font("Calibri", Font.BOLD, 17));
		btnAplicar.setBounds(627, 407, 202, 70);
		btnAplicar.setBackground(new Color(86,106,187));
		btnAplicar.setForeground(new Color(255,255,255));
		contentenorDescuento.add(btnAplicar);
		
		txtTotal = new JTextField();
		txtTotal.setText("0");
		txtTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		txtTotal.setForeground(Color.BLUE);
		txtTotal.setFont(new Font("Calibri", Font.BOLD, 32));
		txtTotal.setEditable(false);
		txtTotal.setColumns(10);
		txtTotal.setBackground(Color.WHITE);
		txtTotal.setBounds(222, 30, 230, 38);
		contentenorDescuento.add(txtTotal);
		txtTotal.setText(Double.toString(Total));
		txtTotalFinal.setText(Double.toString(Total));
		JPanel panelMetodosPagos = new JPanel();
		panelMetodosPagos.setBounds(493, 254, 431, 126);
		contentenorDescuento.add(panelMetodosPagos);
		panelMetodosPagos.setLayout(null);
		
		btnDesPorcentaje = new JButton("Descuento Porcentaje");
		btnDesPorcentaje.setBounds(217, 11, 204, 102);
		panelMetodosPagos.add(btnDesPorcentaje);
		btnDesPorcentaje.setBackground(new Color(230,230,230));
		btnDesPorcentaje.setFont(new Font("Calibri", Font.BOLD, 17));
		
		btnDescuentoEfectivo = new JButton("Descuento Efectivo");
		btnDescuentoEfectivo.setBounds(3, 11, 204, 102);
		panelMetodosPagos.add(btnDescuentoEfectivo);
		btnDescuentoEfectivo.setBackground(new Color(230,230,230));
		btnDescuentoEfectivo.setBackground(new Color(86,106,187));
		btnDescuentoEfectivo.setForeground(new Color(255,255,255));
		btnDescuentoEfectivo.setFont(new Font("Calibri", Font.BOLD, 17));
		
		btnDescuentoEfectivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aplicarPesos();
			}
		});
		
		txtValorPesos = new JTextField();
		txtValorPesos.setEditable(false);
		txtValorPesos.setFont(new Font("Calibri", Font.BOLD, 30));
		txtValorPesos.setForeground(Color.BLACK);
		txtValorPesos.setHorizontalAlignment(SwingConstants.RIGHT);
		txtValorPesos.setBounds(222, 68, 230, 38);
		contentenorDescuento.add(txtValorPesos);
		txtValorPesos.setColumns(10);
		
		txtValorPorcen = new JTextField();
		txtValorPorcen.setEditable(false);
		txtValorPorcen.setFont(new Font("Calibri", Font.BOLD, 30));
		txtValorPorcen.setForeground(Color.BLACK);
		txtValorPorcen.setHorizontalAlignment(SwingConstants.RIGHT);
		txtValorPorcen.setBounds(222, 107, 230, 38);
		contentenorDescuento.add(txtValorPorcen);
		txtValorPorcen.setColumns(10);
		
		JLabel lblValorTotalInicial = new JLabel("Valor Total Inicial");
		lblValorTotalInicial.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblValorTotalInicial.setBounds(36, 44, 137, 14);
		contentenorDescuento.add(lblValorTotalInicial);
		
		JLabel lblValorDescuentoPesos = new JLabel("Valor Descuento Pesos");
		lblValorDescuentoPesos.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblValorDescuentoPesos.setBounds(36, 78, 176, 14);
		contentenorDescuento.add(lblValorDescuentoPesos);
		
		JLabel lblValorDescuento = new JLabel("Valor Descuento %");
		lblValorDescuento.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblValorDescuento.setBounds(36, 117, 176, 14);
		contentenorDescuento.add(lblValorDescuento);
		
		JLabel lblValorTotalFinal = new JLabel("Valor Total Final");
		lblValorTotalFinal.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblValorTotalFinal.setBounds(36, 155, 137, 14);
		contentenorDescuento.add(lblValorTotalFinal);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnSalir.setFont(new Font("Calibri", Font.BOLD, 40));
		btnSalir.setBounds(797, 550, 141, 70);
		contentenorDescuento.add(btnSalir);
		
		textAreaObservacion = new JTextArea();
		textAreaObservacion.setLineWrap(true);
		textAreaObservacion.setBounds(493, 129, 336, 115);
		contentenorDescuento.add(textAreaObservacion);
		
		JLabel lblObservacin = new JLabel("Observaci\u00F3n");
		lblObservacin.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblObservacin.setBounds(493, 102, 195, 16);
		contentenorDescuento.add(lblObservacin);
		
		JLabel lblQuienAutoriza = new JLabel("Quien Autoriza");
		lblQuienAutoriza.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblQuienAutoriza.setBounds(127, 533, 154, 41);
		contentenorDescuento.add(lblQuienAutoriza);
		
		lblNombreUsuarioAut = new JLabel("");
		lblNombreUsuarioAut.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNombreUsuarioAut.setBounds(302, 533, 230, 41);
		contentenorDescuento.add(lblNombreUsuarioAut);
		
		JLabel lblCodigoPromocional = new JLabel("C\u00F3digo Promocional");
		lblCodigoPromocional.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCodigoPromocional.setBounds(510, 0, 163, 27);
		contentenorDescuento.add(lblCodigoPromocional);
		
		txtCodigoPromocion = new JTextField();
		txtCodigoPromocion.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtCodigoPromocion.setBounds(493, 30, 195, 38);
		contentenorDescuento.add(txtCodigoPromocion);
		txtCodigoPromocion.setColumns(10);
		
		JButton btnValidarCodigo = new JButton("Validar C\u00F3digo");
		btnValidarCodigo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				validarCodigoPromocional();
			}
		});
		btnValidarCodigo.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnValidarCodigo.setBounds(723, 11, 201, 50);
		contentenorDescuento.add(btnValidarCodigo);
		
		JButton btnUsarCodigo = new JButton("Usar C\u00F3digo");
		btnUsarCodigo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String resultado = validarCodigoPromocional();
				if(resultado.equals(new String ("OK")))
				{
					promoCtrl.actualizarUsoOferta(ofertaCliente.getIdOfertaCliente(), Sesion.getUsuario());
					//La idea en este punto realizar en pantalla automaticamente el descuento
					//Nos debemos de traer los valores de la oferta
					Oferta oferta = promoCtrl.retornarOferta(ofertaCliente.getIdOferta());
					if(oferta.getDescuentoFijoPorcentaje() > 0)
					{
						txtValorPorcen.setText(Double.toString(oferta.getDescuentoFijoPorcentaje()));
						aplicarPorcentaje();
					}else if(oferta.getDescuentoFijoValor() > 0)
					{
						txtValorPesos.setText(Double.toString(oferta.getDescuentoFijoValor()));
						aplicarPesos();
					}
					textAreaObservacion.setText("Descuento proveniente de un código promocional " + ofertaCliente.getCodigoPromocion());
					btnValidarCodigo.setEnabled(false);
					btnUsarCodigo.setEnabled(false);
				}
			}
		});
		btnUsarCodigo.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnUsarCodigo.setBounds(723, 68, 201, 50);
		contentenorDescuento.add(btnUsarCodigo);
		
		btnDesPorcentaje.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aplicarPorcentaje();
			}
		});
		
		btnAplicar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAplicar.setText("Aplicar");
				if (boolEfectivo) {
					if (!txtValorPesos.getText().equals("")) {
						DescuentoEfectivo = Double.parseDouble(txtValorPesos.getText());
						nuevoTotal =  Double.parseDouble(txtTotal.getText()) - DescuentoEfectivo;
						
					} else {
						JOptionPane.showMessageDialog(ventanaActual, "El valor de descuento en pesos está vacío " , "Valor Descuento Vacío ", JOptionPane.ERROR_MESSAGE);
						return;
					}
					txtTotalFinal.setText(Double.toString(nuevoTotal));
					
					if (Total < DescuentoEfectivo) {
						JOptionPane.showMessageDialog(ventanaActual, "El valor de descuento no puede ser mayor al valor de la factura " , "Valor Descuento mayor. ", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}else if (boolPorcentaje) {
					if (!txtValorPorcen.getText().equals("")) {
						DescuentoPorcentaje = Double.parseDouble(txtValorPorcen.getText());
						if (DescuentoPorcentaje > 100)
						{
							JOptionPane.showMessageDialog(ventanaActual, "El porcentaje de decuento no puede ser mayor a 100 " , "Valor Descuento Porcentaj mayor. ", JOptionPane.ERROR_MESSAGE);
							return;
						}
						nuevoTotal =  Double.parseDouble(txtTotal.getText()) - (DescuentoPorcentaje/100)* Double.parseDouble(txtTotal.getText());
						
					} else {
						JOptionPane.showMessageDialog(ventanaActual, "El valor de descuento en porcentaje está vacío " , "Valor Descuento Vacío ", JOptionPane.ERROR_MESSAGE);
						return;
					}
					txtTotalFinal.setText(Double.toString(nuevoTotal));
					
					
				
				}
				btnFinalizar.setEnabled(true);
				btnDescuentoEfectivo.setEnabled(false);
				btnDesPorcentaje.setEnabled(false);
				btnAplicar.setEnabled(false);
				
			}
		});
		
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtTotal.setText(Double.toString(Total));
				txtTotalFinal.setText(Double.toString(Total));
				txtValorPesos.setText("");
				txtValorPorcen.setText("");
				btnFinalizar.setEnabled(false);
				btnDescuentoEfectivo.setEnabled(true);
				btnDesPorcentaje.setEnabled(true);
				btnAplicar.setEnabled(true);
			
			}
		});
		
	}
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
	
	public boolean validarObservacion()
	{
		String observacion = textAreaObservacion.getText();
		if(observacion.equals(new String("")))
		{
			JOptionPane.showMessageDialog(ventanaActual, "La observación del descuento esta en blanco." , "Descuento sin Observación", JOptionPane.ERROR_MESSAGE);
			return(false);
		}
		if(observacion.length() > 100)
		{
			JOptionPane.showMessageDialog(ventanaActual, "La observación del descuento esta demasiado larga, debe tener máximo 100 carácteres." , "Observación demasiado larga", JOptionPane.ERROR_MESSAGE);
			return(false);
		}
		return(true);
	}
	
	public String validarCodigoPromocional()
	{
		String codigoPromocion = txtCodigoPromocion.getText();
		ofertaCliente = new OfertaCliente(0, 0, 0, "", 0, "",
			"", "", "");
		ofertaCliente.setEstadoOferta("");
		if(codigoPromocion.length() > 0)
		{
			ofertaCliente = promoCtrl.retornarOfertaCodigoPromocional(codigoPromocion);
			if(ofertaCliente.getEstadoOferta().equals(new String("NOK")))
			{
				JOptionPane.showMessageDialog(ventanaActual, "El código promocional no existe." , "Error en código no existente.", JOptionPane.ERROR_MESSAGE);	
			}else if(ofertaCliente.getEstadoOferta().equals(new String("VEN")))
			{
				JOptionPane.showMessageDialog(ventanaActual, "El código promocional esta vencido o ya fue utilizado." , "Error en código vencido.", JOptionPane.ERROR_MESSAGE);
			}else if(ofertaCliente.getEstadoOferta().equals(new String("OK")))
			{
				JOptionPane.showMessageDialog(ventanaActual, "El código promocional PUEDE SER USADO." , "Validar Código Promocional", JOptionPane.WARNING_MESSAGE);
			}
		}else
		{
			JOptionPane.showMessageDialog(ventanaActual, "No ha ingresado ningún código promocional." , "Error en código promocional ", JOptionPane.ERROR_MESSAGE);
		}
		return(ofertaCliente.getEstadoOferta());
	}
	
	public void aplicarPorcentaje()
	{
		btnAplicar.setText("Aplicar "+btnDesPorcentaje.getText());
		btnAplicar.setBackground(new Color(255,106,98));
		btnAplicar.setForeground(new Color(255,255,255));
		btnDesPorcentaje.setBackground(new Color(255,106,98));
		btnDesPorcentaje.setForeground(new Color(255,255,255));
		btnDescuentoEfectivo.setBackground(new Color(230,230,230));
		btnAplicar.setEnabled(true);
		boolEfectivo = false;
		boolPorcentaje = true;
		txtValorPesos.setText("");
	}
	
	public void aplicarPesos()
	{
		btnAplicar.setText("Aplicar "+btnDescuentoEfectivo.getText());
		btnAplicar.setBackground(new Color(86,106,187));
		btnAplicar.setForeground(new Color(255,255,255));
		btnDescuentoEfectivo.setBackground(new Color(86,106,187));
		btnDescuentoEfectivo.setForeground(new Color(255,255,255));
		btnDesPorcentaje.setBackground(new Color(230,230,230));
		btnAplicar.setEnabled(true);
		boolEfectivo = true;
		boolPorcentaje = false;
		txtValorPorcen.setText("");
	}
}
