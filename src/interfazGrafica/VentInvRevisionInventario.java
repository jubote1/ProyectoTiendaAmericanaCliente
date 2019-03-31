package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import capaControlador.InventarioCtrl;
import capaControlador.ParametrosProductoCtrl;
import capaControlador.PedidoCtrl;
import capaModelo.ItemInventario;
import capaModelo.MotivoAnulacionPedido;

import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Color;
import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;

public class VentInvRevisionInventario extends JDialog {

	private JPanel contentPaneRevInventario;
	private JTextField txtInicio;
	private JTextField txtIngInventario;
	private JTextField txtSalidaInventario;
	private JTextField txtConsumoInventario;
	private JTextField txtTotalCalculado;
	private JTextField txtVarianzaIngresada;
	private JButton btnIngresos;
	private JButton btnRetiros;
	private JButton btnDecProductos;
	private JButton btnPedidosAsociadoItem;
	private JButton btnItemsAnuladosEn;
	private InventarioCtrl invCtrl = new InventarioCtrl(PrincipalLogueo.habilitaAuditoria);
	private PedidoCtrl pedCtrl = new PedidoCtrl(PrincipalLogueo.habilitaAuditoria);
	private ParametrosProductoCtrl parProductoCtrl = new ParametrosProductoCtrl(PrincipalLogueo.habilitaAuditoria);
	JComboBox cmbItemInventario;
	JDateChooser dateChooserInv;
	JDialog jDialogPadre;
	private String fechaRevision;
	private int idItemRevision;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentInvRevisionInventario frame = new VentInvRevisionInventario(null, false);
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
	public VentInvRevisionInventario(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		setTitle("REVISI\u00D3N DETALLADA ITEMS INVENTARIO");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 550);
		int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	    int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		setBounds((ancho / 2) - (this.getWidth() / 2), (alto / 2) - (this.getHeight() / 2), 800, 550);
		contentPaneRevInventario = new JPanel();
		contentPaneRevInventario.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPaneRevInventario);
		contentPaneRevInventario.setLayout(null);
		jDialogPadre = this;
		JPanel panel = new JPanel();
		panel.setBounds(26, 11, 748, 489);
		contentPaneRevInventario.add(panel);
		panel.setLayout(null);
		
		JLabel lblItemDeInventario = new JLabel("ITEM DE INVENTARIO");
		lblItemDeInventario.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblItemDeInventario.setBounds(88, 11, 175, 25);
		panel.add(lblItemDeInventario);
		
		JLabel lblFecha = new JLabel("FECHA");
		lblFecha.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblFecha.setBounds(429, 11, 77, 25);
		panel.add(lblFecha);
		
		cmbItemInventario = new JComboBox();
		cmbItemInventario.setBounds(62, 39, 236, 33);
		panel.add(cmbItemInventario);
		
		JButton btnMostrarInfo = new JButton("Mostrar Informaci\u00F3n");
		btnMostrarInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ItemInventario itemSel = (ItemInventario) cmbItemInventario.getSelectedItem();
				Date dateFecha = dateChooserInv.getDate();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				fechaRevision = format.format(dateFecha);
				idItemRevision = itemSel.getIdItem();
				//Con los datos obtenemos comenzamos a realizar el análisis de la informacion.
				//Capturamos la cantidad con la que inicio el día
				double cantidadInicioDia = invCtrl.obtenerItemInventarioHistorico(idItemRevision, fechaRevision);
				//Fijamos el valor en el campo de jText
				txtInicio.setText(Double.toString(cantidadInicioDia));
				//Continuamos con los ingresos de inventario, para lo cual extraemos un total de lo que llegó
				double cantidadIngreso = invCtrl.ingresadoItemInvenario(idItemRevision, fechaRevision);
				if(cantidadIngreso > 0)
				{
					btnIngresos.setEnabled(true);
				}else
				{
					btnIngresos.setEnabled(false);
				}
				txtIngInventario.setText(Double.toString(cantidadIngreso));
				double cantidadRetiro = invCtrl.retiradoItemInvenario(idItemRevision, fechaRevision);
				if(cantidadRetiro > 0)
				{
					btnRetiros.setEnabled(true);
				}
				else
				{
					btnRetiros.setEnabled(false);
				}
				txtSalidaInventario.setText(Double.toString(cantidadRetiro));
				btnDecProductos.setEnabled(true);
				double consumo = invCtrl.obtenerConsumoItemInventario(idItemRevision, fechaRevision);
				if(consumo > 0 )
				{
					btnPedidosAsociadoItem.setEnabled(true);
					btnItemsAnuladosEn.setEnabled(true);
				}
				else
				{
					btnPedidosAsociadoItem.setEnabled(false);
					btnItemsAnuladosEn.setEnabled(false);
				}
				txtConsumoInventario.setText(Double.toString(consumo));
				//Realizamos el cálculo del total que debe tener en el inventario
				double totalCalculado = cantidadInicioDia + cantidadIngreso - cantidadRetiro - consumo;
				txtTotalCalculado.setText(Double.toString(totalCalculado));
				
				//Revisamos si hay varianza ingresada e indica cuanto sería
				double varianzaIngresada = 0;
				boolean ingVarianza = invCtrl.seIngresoVarianza(fechaRevision);
				if(ingVarianza)
				{
					varianzaIngresada = invCtrl.obtenerVarianzaItemInventario(idItemRevision, fechaRevision);
				}
				txtVarianzaIngresada.setText(Double.toString(varianzaIngresada));
			}
		});
		btnMostrarInfo.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnMostrarInfo.setBounds(239, 96, 180, 23);
		panel.add(btnMostrarInfo);
		
		JLabel lblNewLabel = new JLabel("<html><center>INICIASTE EL D\u00CDA Y TU VARIANZA DEL CIERRE DE AYER FUE</center><html>");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(46, 131, 133, 78);
		panel.add(lblNewLabel);
		
		txtInicio = new JTextField();
		txtInicio.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtInicio.setEditable(false);
		txtInicio.setBounds(46, 208, 133, 42);
		panel.add(txtInicio);
		txtInicio.setColumns(10);
		
		JLabel lblSumaIngresoInv = new JLabel("+");
		lblSumaIngresoInv.setFont(new Font("Tahoma", Font.BOLD, 42));
		lblSumaIngresoInv.setBounds(189, 178, 41, 70);
		panel.add(lblSumaIngresoInv);
		
		JLabel lblteLlegoDe = new JLabel("<html><center>TE LLEGO DE BODEGA</center> </html>");
		lblteLlegoDe.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblteLlegoDe.setBounds(239, 130, 133, 78);
		panel.add(lblteLlegoDe);
		
		txtIngInventario = new JTextField();
		txtIngInventario.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtIngInventario.setEditable(false);
		txtIngInventario.setColumns(10);
		txtIngInventario.setBounds(239, 208, 133, 42);
		panel.add(txtIngInventario);
		
		JLabel lblRestaSalInventario = new JLabel("-");
		lblRestaSalInventario.setFont(new Font("Tahoma", Font.BOLD, 42));
		lblRestaSalInventario.setBounds(382, 178, 41, 70);
		panel.add(lblRestaSalInventario);
		
		JLabel lblretirasteDeInventario = new JLabel("<html><center>RETIRASTE DE INVENTARIO(p.e. prestaste a otra tienda)</center><html>");
		lblretirasteDeInventario.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblretirasteDeInventario.setBounds(417, 131, 133, 78);
		panel.add(lblretirasteDeInventario);
		
		txtSalidaInventario = new JTextField();
		txtSalidaInventario.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtSalidaInventario.setEditable(false);
		txtSalidaInventario.setColumns(10);
		txtSalidaInventario.setBounds(417, 208, 133, 42);
		panel.add(txtSalidaInventario);
		
		JLabel lblConsumoInv = new JLabel("-");
		lblConsumoInv.setFont(new Font("Tahoma", Font.BOLD, 42));
		lblConsumoInv.setBounds(88, 287, 41, 70);
		panel.add(lblConsumoInv);
		
		JLabel lblconsumoDeInventario = new JLabel("<html><center>CONSUMO DE INVENTARIO EN EL D\u00CDA</center> </html>");
		lblconsumoDeInventario.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblconsumoDeInventario.setBounds(121, 261, 133, 78);
		panel.add(lblconsumoDeInventario);
		
		txtConsumoInventario = new JTextField();
		txtConsumoInventario.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtConsumoInventario.setEditable(false);
		txtConsumoInventario.setColumns(10);
		txtConsumoInventario.setBounds(121, 325, 133, 42);
		panel.add(txtConsumoInventario);
		
		JLabel lblIgual = new JLabel("=");
		lblIgual.setFont(new Font("Tahoma", Font.BOLD, 42));
		lblIgual.setBounds(264, 287, 41, 70);
		panel.add(lblIgual);
		
		JLabel lblTotalCalculado = new JLabel("<html><center>TOTAL CALCULADO</center> </html>");
		lblTotalCalculado.setForeground(Color.RED);
		lblTotalCalculado.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblTotalCalculado.setBounds(329, 261, 133, 78);
		panel.add(lblTotalCalculado);
		
		txtTotalCalculado = new JTextField();
		txtTotalCalculado.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtTotalCalculado.setEditable(false);
		txtTotalCalculado.setColumns(10);
		txtTotalCalculado.setBounds(315, 325, 133, 42);
		panel.add(txtTotalCalculado);
		
		JLabel lblVarianzaIngresada = new JLabel("<html><center>VARIANZA INGRESADA SI LA HAY</center> </html>");
		lblVarianzaIngresada.setForeground(Color.RED);
		lblVarianzaIngresada.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblVarianzaIngresada.setBounds(497, 261, 133, 78);
		panel.add(lblVarianzaIngresada);
		
		txtVarianzaIngresada = new JTextField();
		txtVarianzaIngresada.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtVarianzaIngresada.setEditable(false);
		txtVarianzaIngresada.setColumns(10);
		txtVarianzaIngresada.setBounds(497, 325, 133, 42);
		panel.add(txtVarianzaIngresada);
		
		btnPedidosAsociadoItem = new JButton("Pedidos Asociados al Item");
		btnPedidosAsociadoItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentInvRevisionInventarioInformacion ventReporte = new VentInvRevisionInventarioInformacion(jDialogPadre,true, idItemRevision, fechaRevision, 4);
				ventReporte.setVisible(true);
			}
		});
		btnPedidosAsociadoItem.setEnabled(false);
		btnPedidosAsociadoItem.setBounds(72, 378, 212, 42);
		panel.add(btnPedidosAsociadoItem);
		
		btnItemsAnuladosEn = new JButton("Items Anulados en que pedidos");
		btnItemsAnuladosEn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentInvRevisionInventarioInformacion ventReporte = new VentInvRevisionInventarioInformacion(jDialogPadre,true, idItemRevision, fechaRevision, 5);
				ventReporte.setVisible(true);
			}
		});
		btnItemsAnuladosEn.setEnabled(false);
		btnItemsAnuladosEn.setBounds(72, 431, 212, 42);
		panel.add(btnItemsAnuladosEn);
		
		dateChooserInv = new JDateChooser();
		dateChooserInv.setBounds(353, 39, 236, 32);
		panel.add(dateChooserInv);
		
		JButton buttonSalir = new JButton("SALIR");
		buttonSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		buttonSalir.setFont(new Font("Tahoma", Font.BOLD, 12));
		buttonSalir.setBounds(389, 410, 212, 42);
		panel.add(buttonSalir);
		
		btnIngresos = new JButton("Ingresos");
		btnIngresos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				VentInvRevisionInventarioInformacion ventReporte = new VentInvRevisionInventarioInformacion(jDialogPadre,true, idItemRevision, fechaRevision, 2);
				ventReporte.setVisible(true);
			}
		});
		btnIngresos.setEnabled(false);
		btnIngresos.setBounds(249, 253, 123, 23);
		panel.add(btnIngresos);
		
		btnRetiros = new JButton("Retiros");
		btnRetiros.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				VentInvRevisionInventarioInformacion ventReporte = new VentInvRevisionInventarioInformacion(jDialogPadre,true, idItemRevision, fechaRevision, 3);
				ventReporte.setVisible(true);
			}
		});
		btnRetiros.setEnabled(false);
		btnRetiros.setBounds(417, 253, 123, 23);
		panel.add(btnRetiros);
		
		btnDecProductos = new JButton("<html><center>Productos que descuentan de inventario</center></html>");
		btnDecProductos.setEnabled(false);
		btnDecProductos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentInvRevisionInventarioInformacion ventReporte = new VentInvRevisionInventarioInformacion(jDialogPadre,true, idItemRevision, fechaRevision, 1);
				ventReporte.setVisible(true);
			}
		});
		btnDecProductos.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnDecProductos.setBounds(560, 135, 133, 68);
		panel.add(btnDecProductos);
		
		//Realizamos la inicialización del combo
		initcmbMotivoAnulacion();
	}
	
	
	public void initcmbMotivoAnulacion()
	{
		ArrayList<ItemInventario> itemsInventario = parProductoCtrl.obtenerItemsInventariosObj();
		for(int i = 0; i < itemsInventario.size();i++)
		{
			ItemInventario fila = (ItemInventario)  itemsInventario.get(i);
			cmbItemInventario.addItem(fila);
		}
	}
}
