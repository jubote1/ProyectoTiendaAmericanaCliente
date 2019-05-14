package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.html.parser.ParserDelegator;

import capaControlador.InventarioCtrl;
import capaControlador.ParametrosCtrl;
import capaControlador.PedidoCtrl;
import capaModelo.DetallePedido;
import capaModelo.Estado;
import capaModelo.FormaPago;
import capaModelo.Parametro;
import capaModelo.Pedido;
import capaModelo.TipoPedido;
import capaModelo.FormaPagoIng;

import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.Font;
import java.awt.Color;
import java.awt.Component;

import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

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
public class VentPedFinPago extends JDialog implements Runnable {

	private JPanel contentenorFinPago;
	private final Action action = new SwingAction();
	private JTextField displayPago;
	private JTextField txtCantidadAdeudada;
	//Tendremos un arreglo con los botones de las formas de pago para procesarlos en los momentos de click
	private ArrayList<JButton> btnFormasPago = new ArrayList();
	//Tendremos un arreglo con las formas de pago recuperadas de la base de datos
	private ArrayList<FormaPago> formasPago;
	//Tendremos un entero con el id de la forma de pago seleccionada
	private int idFormaPagoSel;
	//Tendremos un arreglo para el control de la manera como estamos pagando el pedido
	private ArrayList<FormaPagoIng> formasPagoIng = new ArrayList();
	//Definimos un arreglo con los colores que manejará las formas de pago
	private ArrayList<Color> coloresFormaPago = new ArrayList();
	
	public double Cambio = 0 , Total = VentPedTomarPedidos.totalPedido - VentPedTomarPedidos.descuento ,Deuda = Total ;
	public  boolean boolEfectivo = true, boolTarjeta = false; 
	private JTable tablePago;
	private JTextField displayTotal;
	private JButton btnAplicar;
	private boolean hayFormaPago = false;
	private PedidoCtrl pedCtrl = new PedidoCtrl(PrincipalLogueo.habilitaAuditoria);
	ParametrosCtrl parCtrl = new ParametrosCtrl(PrincipalLogueo.habilitaAuditoria);
	Thread hiloDescInventario;
	private JButton btnFormaPago;
	private JPanel panelMetodosPagos;
	public void clarearVarEstaticas()
	{
		for(int i = 0; i < formasPagoIng.size(); i++)
		{
			FormaPagoIng formaTemp = formasPagoIng.get(i);
			formaTemp.setValorPago(0);
			formasPagoIng.set(i, formaTemp);
			
		}
		Cambio = 0;

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
	

	/**
	 * Create the frame.
	 */
	public VentPedFinPago(boolean existeFormaPago,java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		setTitle("FORMA DE PAGO DEL PEDIDO");
		hayFormaPago = existeFormaPago;
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setDefaultCloseOperation(0);
		setBounds(0,0, 961, 636);
		int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	    int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		setBounds((ancho / 2) - (this.getWidth() / 2), (alto / 2) - (this.getHeight() / 2), 961, 636);
		contentenorFinPago = new JPanel();
		contentenorFinPago.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentenorFinPago);
		contentenorFinPago.setLayout(null);
		ImageIcon img = new ImageIcon("iconos\\LogoPequePizzaAmericana.jpg");
		setIconImage(img.getImage());
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
		formasPago = parCtrl.obtenerFormasPago();
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
		//Instanciamos el hilo que se va a encargar del descuento de inventario
		hiloDescInventario = new Thread(this);
		JButton btnFinalizar = new JButton("Finalizar");
		btnFinalizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Window ventanaPadre = SwingUtilities.getWindowAncestor(
                        (Component) arg0.getSource());
				//Tomamos la información para insertar la forma de pago
				//Si el pedido tenía forma de pago, deberemos de eliminar la forma de pago anterior
				//Ingresamos lógica para tomar el tipo de pedido 
				//Este parámetro es general para el método
				int idTipoPedido;
				if(hayFormaPago)
				{
					pedCtrl.eliminarPedidoFormaPago( VentPedTomarPedidos.idPedido);
				}
				
				//Realizamos validaciones para revisar si se da la situación de un pedido que se va a finalizar sin ningún detalle y fue anulado
				//Validamos que el pedido no haya sido reabierto
				if(!VentPedTomarPedidos.esReabierto)
				{
					//Si el pedido  fue anulado sin descontar
					if(VentPedTomarPedidos.esAnuladoSinDescontar)
					{
						//Validamos si no hay ningún item de pedido, verificando que el tamaño del arreglo sea cero
						if(VentPedTomarPedidos.detallesPedido.size() == 0)
						{
							//Cumpliendo estás condiciones anulamos el pedido
							boolean anuDetallePedido = pedCtrl.anularPedidoSinDetalle( VentPedTomarPedidos.idPedido);
						}
						
					}
				}
				//Realizamos validaciones relacionadas con si el pedido es anulado y tiene items pedido
				if(VentPedTomarPedidos.esAnulado)
				{
					//Definimos variable que nos ayudará a determinar si debemos de quitar la anulación del pedido
					boolean noAnulado = false;
					//recorremos el arreglo de detalles pedido
					for(int j = 0; j < VentPedTomarPedidos.detallesPedido.size(); j++)
					{
						DetallePedido detCadaPedido = VentPedTomarPedidos.detallesPedido.get(j);
						//Verificamos si por lo menos un item del pedido no está anulado
						if(!detCadaPedido.getEstado().equals(new String("A")))
						{
							noAnulado = true;
							break;
						}
					}
					//Validamos si noAnulado está en true para quitar la anulación del pedido
					if(noAnulado)
					{
						pedCtrl.quitarAnulacionPedido(VentPedTomarPedidos.idPedido);
					}
				}
				// Se envían datos para la inserción de la forma de pago.
				boolean resFormaPago = pedCtrl.insertarPedidoFormaPago(formasPagoIng, Total, Cambio, VentPedTomarPedidos.idPedido);
				if(resFormaPago)
				{
					
					
					//Capturamos el tipoPedido seleccionado
					TipoPedido tipPedido = null;
					try
					{
						//Intentamos obtener los valores que vienen por pantalla
						tipPedido = VentPedTomarPedidos.tiposPedidos.get(VentPedTomarPedidos.numTipoPedidoAct);
						idTipoPedido = tipPedido.getIdTipoPedido();
					}catch(Exception e)
					{
						// Sino podemos obtener los valores les dejamos un valor lo que significa qeu posiblemente no es por pantalla la fijación
						idTipoPedido = 0;
						tipPedido = null;
					}
					//Validamos según como sea el tipo de pedido recuperado e intentamos tomar el tiempo que sea correspondiente si el punto de venta o el domicilio
					Parametro parametroAud;
					if(tipPedido == null)
					{
						parametroAud = parCtrl.obtenerParametro("TIEMPOPEDIDO");
					}
					else
					{
						if(tipPedido.isEsDomicilio())
						{
							parametroAud = parCtrl.obtenerParametro("TIEMPOPEDIDO");
						}else
						{
							parametroAud = parCtrl.obtenerParametro("TIEMPOPEDIDOTIENDA");
						}
					}
					//Extraemos el valor del campo de ValorTexto
					int tiempoPedido;
					try
					{
						tiempoPedido = parametroAud.getValorNumerico();
					}catch(Exception e)
					{
						tiempoPedido = 0;
					}
					
					//Se validar si el pedido es reabierto para mostrar JOptionPane con 3 opciones disponibles
					//Opción de impresión la fijamos en 1 que significa que se imprime todo normalmente
					int opcionImpresion = 1;
					boolean resFinPedido = true;
					boolean imprimeSiReabierto = false;
					if(VentPedTomarPedidos.esReabierto)
					{
						//En este punto verificamos si el pedido reabierto... le fueron anulados los detalles pedidos
						boolean esPedReabiertoAnulado = pedCtrl.verificarPedidoReabiertoAnulado(VentPedTomarPedidos.idPedido);
						//Si se cumple esta condición es porque el pedido reabierto esta anulado completamente por lo tanto deberemos llevar al estado
						//final de este estado de pedido y no deberemos de reimprimir ni nada
						if(esPedReabiertoAnulado)
						{
							//Finalizamos el pedido indicador que es un pedido reabierto, que no queremos que imprima
							resFinPedido = pedCtrl.finalizarPedido(VentPedTomarPedidos.idPedido, tiempoPedido, idTipoPedido, opcionImpresion, VentPedTomarPedidos.detallesPedidoNuevo, true, false, true, false, 0);
						}
						else // es la situación de un pedido normal
						{
							//Obtenemos el pedido para ver si ya fue impreso
							Pedido pedido = pedCtrl.obtenerPedido(VentPedTomarPedidos.idPedido);
							//Definimos variable para traernos el tipo de pedido que tiene en base de datos
							int idTipoPedidoBD = pedido.getIdTipoPedido();
							int idEstadoActual = 0;
							Estado estadoActual;
							boolean cambioEstadoPedido = false;
							if(idTipoPedidoBD != idTipoPedido)
							{
								idEstadoActual = pedCtrl.obtenerEstadoInicial(idTipoPedido);
								estadoActual = pedCtrl.obtenerEstado(idEstadoActual);
								cambioEstadoPedido = true;
							}
							else
							{
								//Validamos si el estado actual debe imprimir
								estadoActual = pedCtrl.obtenerEstadoPedido(VentPedTomarPedidos.idPedido);
							}
							
							
							//System.out.println("PEDIDO REABIERTO " + VentPedTomarPedidos.idPedido + " - " + estadoActual.getIdestado() + " " + estadoActual.isImpresion() );
							// Si se cumple cualquier de estas dos condiciones el sistema preguntará por la impresion
							if(estadoActual.isImpresion() || pedido.isImpresion())
							{
								imprimeSiReabierto = true;
								Object[] opcionesImpresion = {"Imprimir solo lo Nuevo",
						                "Imprimir todo" , "No imprimir nada(ni comanda ni factura)"};
										//Mostramos la pantalla de confirmación para validar si deseamos o no la anulación
										int resp = JOptionPane.showOptionDialog(ventanaPadre, "¿Que opción de impresión desea para la comanda, Imprimir lo nuevo del pedido, Imprimir todo de nuevo o no Imprimir nada?", "Confirmación impresión pantalla" , JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null,     //do not use a custom Icon
												opcionesImpresion,  //the titles of buttons
												opcionesImpresion[0]);
										// Opcion 0 impresión de lo nuevo
										// Opcion 1 impresión de todo
										// Opción 2 no impresion
										
										if (resp == 0)
										{
											opcionImpresion = 0;
										}else if(resp == 1)
										{	
											opcionImpresion = 1;
										}else if(resp ==2)
										{
											opcionImpresion = 2;
										}
							}
							
							resFinPedido = pedCtrl.finalizarPedido(VentPedTomarPedidos.idPedido, tiempoPedido, idTipoPedido, opcionImpresion, VentPedTomarPedidos.detallesPedidoNuevo, true, imprimeSiReabierto, false, cambioEstadoPedido, idEstadoActual);
						}
					}else
					{
						//System.out.println("PEDIDO NUEVO " + VentPedTomarPedidos.idPedido );
						resFinPedido = pedCtrl.finalizarPedido(VentPedTomarPedidos.idPedido, tiempoPedido, idTipoPedido, opcionImpresion, VentPedTomarPedidos.detallesPedidoNuevo, false, imprimeSiReabierto, false, false , 0);
					}
					
					// En este punto finalizamos el pedido
					
					if (resFinPedido)
					{
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
		
		btnAplicar = new JButton("Aplicar");
		btnAplicar.setFont(new Font("Calibri", Font.BOLD, 24));
		btnAplicar.setBounds(600, 452, 323, 70);
		btnAplicar.setBackground(new Color(86,106,187));
		btnAplicar.setForeground(new Color(255,255,255));
		contentenorFinPago.add(btnAplicar);
		
		tablePago = new JTable();
		tablePago.setShowGrid(false);
		tablePago.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		tablePago.setShowHorizontalLines(false);
		tablePago.setShowVerticalLines(false);
		tablePago.setEnabled(false);
		tablePago.setFont(new Font("Calibri", Font.PLAIN, 16));
		tablePago.setBounds(10, 366, 202, 156);
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
		
		panelMetodosPagos = new JPanel();
		panelMetodosPagos.setBounds(719, 66, 204, 317);
		contentenorFinPago.add(panelMetodosPagos);
		panelMetodosPagos.setLayout(new GridLayout(0, 1, 0, 0));
		
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
		
		JButton btnRetornar = new JButton("Retornar");
		btnRetornar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnRetornar.setFont(new Font("Calibri", Font.BOLD, 35));
		btnRetornar.setBounds(548, 534, 182, 53);
		contentenorFinPago.add(btnRetornar);
		
		JButton btnIngRetornar = new JButton("Ingresar y Retornar");
		btnIngRetornar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Tomamos la información para insertar la forma de pago
				// Se envían datos para la inserción de la forma de pago.
				boolean resFormaPago = pedCtrl.insertarPedidoFormaPago(formasPagoIng, Total, Cambio, VentPedTomarPedidos.idPedido);
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
		
				
		btnAplicar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Limpiamos el aplicar
				btnAplicar.setText("Aplicar");
				//Variable donde vamos a almacenar cuanto se ha pagado del pedido
				double totalRecalculado = 0;
				//Debemos de realizar la validación para la forma de pago seleccionada con base en la forma de pago seleccionada en la variable idFormaPagoSel
				for(int i = 0; i < formasPagoIng.size(); i++)
				{
					FormaPagoIng formaTemp = formasPagoIng.get(i);
					//Vamos valiando si el idformapago seleccionado es el mismo del recorrido de las formas de pago
					if(formaTemp.getIdFormaPago() == idFormaPagoSel)
					{
						//Variable para almacenar el valor
						double valorAnt = 0;
						//Validamos si no se ha ingresado valor para pagar.
						if (!displayPago.getText().equals("")) {
							//Tomamos el valor digitado y lo fijamos en el ArrayList
							formaTemp.setValorPago(Double.parseDouble(displayPago.getText()));
							valorAnt = Double.parseDouble(displayPago.getText());
						}else
						{
							//Tomamos el total del pedido
							formaTemp.setValorPago(Deuda);
							valorAnt = Deuda;
						}
						//Acumulamos en el Total Pedido Recalculado
						totalRecalculado = totalRecalculado + valorAnt;
						//Actualizamos la forma de pago dentro del arreglo que almacena esta información
						formasPagoIng.set(i, formaTemp);
						//Limpiamos el campo donde se almacena lo digitado
						displayPago.setText("");
					}else
					{
						totalRecalculado = totalRecalculado + formaTemp.getValorPago();
					}
				}
				Deuda = Total - totalRecalculado;
				System.out.println("TOTAL  " + Total  + " totalRecalculado " + totalRecalculado);
				if (Total < totalRecalculado) {
					Cambio = Deuda * - 1;
					Deuda = 0;
				}
				//Construida y fijado los valores de  Deuda y Cambio y el arreglo de las formas de pago pintamos el Jtable con el resumen
				//de la información ingresada
				pintarFormaPagoPedido();
				//Mostramos en pantalla el valor de total de deuda
				displayTotal.setText(Formato(Deuda));
				//Si deuda es igual a cero sea porque es menor o mayor se habilitan los botones y se funciona normal
				if (Deuda == 0) {
					//Se habilitan los botones
					btnFinalizar.setEnabled(true);
					btnIngRetornar.setEnabled(true);
					//Se inhabilitan los botones de forma de pago
					for(int j = 0; j < btnFormasPago.size(); j++)
					{
						JButton btnTemp = btnFormasPago.get(j);
						btnTemp.setEnabled(false);
					}
					//El botón aplicar se desactiva
					btnAplicar.setEnabled(false);
				}
			}
		});
		
		//Validamos si ya existe forma de pago para cargar la información en el frame
		if(hayFormaPago)
		{
			
			
		}
		//Realizamos el pintado en la pantalla y panel de las formas de pago parametrizadas en el sistema
		//Agregamos los colores de las forma de pago
		coloresFormaPago.add(new Color(86,106,187));
		coloresFormaPago.add(new Color(255,106,98));
		coloresFormaPago.add(new Color(50,106,98));
		//Adicionamos las formas de pago qeu existen en el sistema
		adicionarFormasPago();
		//Pintamos en el Jtable de resumen las formas de pago
		pintarFormaPagoPedido();
		
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
		 Thread ct = Thread.currentThread();
		 if(ct == hiloDescInventario) 
		 {   
			 descontarInventario();
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
	
	public void adicionarFormasPago()
	{
		
		for(int i = 0; i < formasPago.size(); i++)
		{
			FormaPago formaTemp = formasPago.get(i);
			btnFormaPago = new JButton(formaTemp.getNombre());
			btnFormaPago.setFont(new Font("Calibri", Font.BOLD, 18));
			btnFormaPago.setActionCommand(Integer.toString(formaTemp.getIdformapago()));
			btnFormaPago.setIcon(new ImageIcon(VentPedFinPago.class.getResource(formaTemp.getIcono())));
			//Inicializamos el botón aplicar
			if(i == 0)
			{
				btnAplicar.setText("Aplicar "+ btnFormaPago.getText());
				idFormaPagoSel = formaTemp.getIdformapago();
				btnAplicar.setBackground(coloresFormaPago.get(idFormaPagoSel-1));
			}
			panelMetodosPagos.add(btnFormaPago);
			btnFormasPago.add(btnFormaPago);
			FormaPagoIng formaIngTemp = new FormaPagoIng(formaTemp.getIdformapago(), 0, false, formaTemp.getNombre());
			formasPagoIng.add(formaIngTemp);
			btnFormaPago.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) 
				{
					JButton botAccion = ((JButton)arg0.getSource());
					//Extraemos la acción del botón
					String actCom =  botAccion.getActionCommand();
					idFormaPagoSel = Integer.parseInt(actCom);
					System.out.println("forma de pago seleccionada " + idFormaPagoSel);
					//Realizamos un recorrido por los botones para saber como debemos de colorearlo
					for(int j = 0; j < btnFormasPago.size(); j++)
					{
						JButton btnTemp = btnFormasPago.get(j);
						int idFormaPagoBtn = Integer.parseInt(btnTemp.getActionCommand());
						System.out.println("id forma pago cada boton " + idFormaPagoBtn);
						if(idFormaPagoBtn == idFormaPagoSel)
						{
							btnTemp.setForeground(Color.white);
							btnTemp.setBackground(coloresFormaPago.get(idFormaPagoSel-1));
							btnAplicar.setText("Aplicar "+botAccion.getText());
							btnAplicar.setBackground(coloresFormaPago.get(idFormaPagoSel-1));
							btnAplicar.setEnabled(true);
						}
						else
						{
							btnTemp.setForeground(Color.black);
							btnTemp.setBackground(null);
						}
					}
				}
			});
		}
	}
	
	public void pintarFormaPagoPedido()
	{
		Object[] columnsName = new Object[2];
		columnsName[0] = "**";
        columnsName[1] = "Valor";
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(columnsName);
        String[] fila = new String[2];
        fila[0] = "TOTAL";
        fila[1] = Formato(Total);
        modelo.addRow(fila);
        for(int i = 0; i < formasPagoIng.size(); i++)
        {
        	FormaPagoIng formaTemp = formasPagoIng.get(i);
        	fila = new String[2];
        	fila[0] = formaTemp.getNombreFormaPago();
        	fila[1] = Formato(formaTemp.getValorPago());
        	modelo.addRow(fila);
        }
        fila = new String[2];
    	fila[0] = "CAMBIO";
    	fila[1] = Formato(Cambio);
    	modelo.addRow(fila);
        tablePago.setModel(modelo);
	}
}
