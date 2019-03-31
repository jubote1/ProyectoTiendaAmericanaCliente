package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import capaConexion.ConexionBaseDatos;
import capaControlador.AutenticacionCtrl;
import capaControlador.InventarioCtrl;
import capaControlador.OperacionesTiendaCtrl;
import capaControlador.ParametrosCtrl;
import capaControlador.ReportesCtrl;
import capaControlador.PedidoCtrl;
import capaModelo.FechaSistema;
import capaModelo.ModificadorInventario;
import capaModelo.Parametro;
import reportes.AbstractJasperReports;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.awt.event.ActionEvent;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Window;

import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JTabbedPane;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Component;

public class VentPedFinalizarDia extends JDialog  implements Runnable{

	private JPanel contentPanePrincipal;
	private JTextField txtFechaInventario;
	String fechaSis;
	String fechaUltimoCierre;
	ArrayList<ModificadorInventario> inventarioIngresar = new ArrayList();
	private JTable tableResTipoPedido;
	private JTextField txtTotalVendido;
	private double totalVendido = 0;
	private double totalIngresos = 0;
	private double totalEgresos = 0;
	private PedidoCtrl pedCtrl = new PedidoCtrl(PrincipalLogueo.habilitaAuditoria);
	private InventarioCtrl invCtrl = new InventarioCtrl(PrincipalLogueo.habilitaAuditoria);
	AutenticacionCtrl autCtrl = new AutenticacionCtrl(PrincipalLogueo.habilitaAuditoria);
	OperacionesTiendaCtrl operTiendaCtrl = new OperacionesTiendaCtrl(PrincipalLogueo.habilitaAuditoria);
	private JTextField txtTotalVendidoConIE;
	//Creamos un hilo que se encargará de la reportería una vez finalice el cierre
	Thread hiloReporteria;
	Thread hiloReportSemanal;
	//Creamos un hilo que se encargará de la reportería semanal una vez finalice el cierre y se cierre de semanal
	private JTextField txtVentaSemana;
	private JTable tableResFormaPago;
	JDialog jDialogPadre;
	DecimalFormat formatea = new DecimalFormat("###,###");
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentPedFinalizarDia frame = new VentPedFinalizarDia(null, true);
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
	public VentPedFinalizarDia(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		this.setAlwaysOnTop(true);
		setTitle("FINALIZAR DIA");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	    int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		//setBounds((ancho / 2) - (this.getWidth() / 2), (alto / 2) - (this.getHeight() / 2), 800, 450);
	    this.setSize(this.getToolkit().getScreenSize());
		contentPanePrincipal = new JPanel();
		contentPanePrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPanePrincipal);
		contentPanePrincipal.setLayout(null);
		ImageIcon img = new ImageIcon("iconos\\LogoPequePizzaAmericana.jpg");
		setIconImage(img.getImage());
		DefaultTableModel modelo = pintarItemsInventario();
		jDialogPadre = this;
		JButton btnCierreDia = new JButton("Finalizar D\u00EDa");
		btnCierreDia.setEnabled(false);
		btnCierreDia.setBackground(Color.ORANGE);
		
		btnCierreDia.setBounds(176, 280, 152, 37);
		contentPanePrincipal.add(btnCierreDia);
		
		JButton btnCancelar = new JButton("Cancelar/Salir");
		btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				dispose();
			}
		});
		btnCancelar.setBounds(342, 278, 167, 38);
		contentPanePrincipal.add(btnCancelar);
		
		JLabel lblFechaSistema = new JLabel("FECHA SISTEMA");
		lblFechaSistema.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblFechaSistema.setBounds(372, 11, 167, 14);
		contentPanePrincipal.add(lblFechaSistema);
		
		txtFechaInventario = new JTextField();
		txtFechaInventario.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtFechaInventario.setEnabled(false);
		txtFechaInventario.setEditable(false);
		txtFechaInventario.setBounds(569, 8, 135, 20);
		contentPanePrincipal.add(txtFechaInventario);
		txtFechaInventario.setColumns(10);
		
		//Vamos a recuperar la fecha del sistema y la vamos a mostrar en el campo correspondiente
		FechaSistema fecha = pedCtrl.obtenerFechasSistema();
		fechaSis = fecha.getFechaApertura();
		txtFechaInventario.setText(fechaSis);
		
		JScrollPane scrollResCierre = new JScrollPane();
		scrollResCierre.setBounds(2, 354, 348, 79);
		contentPanePrincipal.add(scrollResCierre);
		
		JTextPane txtPaneResCierre = new JTextPane();
		txtPaneResCierre.setEditable(false);
		scrollResCierre.setViewportView(txtPaneResCierre);
		
		JLabel lblTotalesPorTipo = new JLabel("TOTALES POR TIPO DE PEDIDO");
		lblTotalesPorTipo.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTotalesPorTipo.setBounds(55, 11, 240, 14);
		contentPanePrincipal.add(lblTotalesPorTipo);
		
		JScrollPane scrollPaneTipoPedido = new JScrollPane();
		scrollPaneTipoPedido.setBounds(45, 36, 243, 112);
		contentPanePrincipal.add(scrollPaneTipoPedido);
		
		tableResTipoPedido = new JTable();
		scrollPaneTipoPedido.setViewportView(tableResTipoPedido);
		
		JLabel lblTotalVendido = new JLabel("TOTAL VENDIDO");
		lblTotalVendido.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTotalVendido.setBounds(11, 159, 88, 14);
		contentPanePrincipal.add(lblTotalVendido);
		
		txtTotalVendido = new JTextField();
		txtTotalVendido.setEditable(false);
		txtTotalVendido.setBounds(176, 156, 112, 20);
		contentPanePrincipal.add(txtTotalVendido);
		txtTotalVendido.setColumns(10);
		
		JPanel panelReportes = new JPanel();
		panelReportes.setBorder(new LineBorder(new Color(0, 0, 0), 5));
		panelReportes.setBounds(351, 351, 400, 239);
		contentPanePrincipal.add(panelReportes);
		panelReportes.setLayout(null);
		
		JButton btnReporteGeneralVentas = new JButton("Reporte General Ventas");
		btnReporteGeneralVentas.setBounds(10, 116, 182, 23);
		panelReportes.add(btnReporteGeneralVentas);
		
		JButton btnReporteUsoInventario = new JButton("Reporte uso Inventario");
		btnReporteUsoInventario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				VentPedReportes ventReporte = new VentPedReportes(jDialogPadre,true, fechaSis, 1);
				ventReporte.setVisible(true);
			}
		});
		btnReporteUsoInventario.setBounds(10, 45, 182, 23);
		panelReportes.add(btnReporteUsoInventario);
		
		JButton btnCorteDeCaja = new JButton("Corte de Caja");
		btnCorteDeCaja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentPedReportes ventReporte = new VentPedReportes(jDialogPadre,true, fechaSis, 3);
				ventReporte.setVisible(true);
			}
		});
		btnCorteDeCaja.setBounds(204, 45, 184, 23);
		panelReportes.add(btnCorteDeCaja);
		
		JButton btnRepCajDet = new JButton("Corte de Caja Detallada");
		btnRepCajDet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentPedReportes ventReporte = new VentPedReportes(jDialogPadre,true, fechaSis, 4);
				ventReporte.setVisible(true);
			}
		});
		btnRepCajDet.setBounds(204, 79, 184, 23);
		panelReportes.add(btnRepCajDet);
		
		JButton btnReporteInvActual = new JButton("Inventario Actual");
		btnReporteInvActual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentPedReportes ventReporte = new VentPedReportes(jDialogPadre,true, fechaSis, 2);
				ventReporte.setVisible(true);
			}
		});
		btnReporteInvActual.setBounds(10, 79, 182, 23);
		panelReportes.add(btnReporteInvActual);
		
		JLabel lblInventario = new JLabel("REPORTE INVENTARIOS");
		lblInventario.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblInventario.setBounds(12, 11, 133, 14);
		panelReportes.add(lblInventario);
		
		JLabel lblReporteVentas = new JLabel("REPORTE VENTAS");
		lblReporteVentas.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblReporteVentas.setBounds(204, 11, 110, 14);
		panelReportes.add(lblReporteVentas);
		
		JButton btnImpResumenGenVentas = new JButton("Imprimir Resumen General Ventas");
		btnImpResumenGenVentas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pedCtrl.imprimirResumenGeneralVentas();
				
			}
		});
		btnImpResumenGenVentas.setBackground(Color.LIGHT_GRAY);
		btnImpResumenGenVentas.setBounds(86, 192, 228, 23);
		panelReportes.add(btnImpResumenGenVentas);
		
		JButton btnVentaSemanal = new JButton("Venta Semanal");
		btnVentaSemanal.setBounds(202, 116, 186, 23);
		panelReportes.add(btnVentaSemanal);
		
		JButton btnValidarCierre = new JButton("Validar Cierre");
		btnValidarCierre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				OperacionesTiendaCtrl operTienda = new OperacionesTiendaCtrl(PrincipalLogueo.habilitaAuditoria);
				//Validamos que el día no se haya cerrado ya
				ParametrosCtrl parCtrl = new ParametrosCtrl(PrincipalLogueo.habilitaAuditoria);
				Parametro parametro = parCtrl.obtenerParametro("INDICADORCIERRE");
				String fechaParametro = parametro.getValorTexto();
				FechaSistema fecha = pedCtrl.obtenerFechasSistema();
				fechaSis = fecha.getFechaApertura();
				if(fechaSis.contentEquals(fechaParametro))
				{
					System.exit(0);
				}
				String resp = operTienda.validacionesPreCierre(fechaSis);
				Window ventanaPadre = SwingUtilities.getWindowAncestor(
                        (Component) arg0.getSource());
				if(resp.equals(new String("")))
				{
					((JButton)arg0.getSource()).getParent();
					JOptionPane.showMessageDialog(ventanaPadre, "El cierre ha superado las prevalidaciones" , "Puede iniciar el cierre", JOptionPane.INFORMATION_MESSAGE);
					btnCierreDia.setEnabled(true);
				}
				else
				{
					JOptionPane.showMessageDialog(ventanaPadre, "El cierre presenta inconvenientes, se deben de validar temas adicionales" , "El cierre NO puede Iniciar.", JOptionPane.ERROR_MESSAGE);
				}
				txtPaneResCierre.setText(resp);
			}
		});
		btnValidarCierre.setBounds(11, 280, 152, 37);
		contentPanePrincipal.add(btnValidarCierre);
		
		JButton btnNewButton_2 = new JButton("Adm Ingresos/Egresos");
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean tienePermiso = autCtrl.validarAccesoOpcion("PED_015", Sesion.getAccesosOpcion());
				if (tienePermiso)
				{
					JDialog ventanaPadre = (JDialog) SwingUtilities.getWindowAncestor(
	                        (Component) e.getSource());
					VentPedIngEgrDiarios ingEgr = new VentPedIngEgrDiarios(ventanaPadre, true);
					ingEgr.setVisible(true);
				}else
				{
					JOptionPane.showMessageDialog(null, "Su perfil de usuario no tiene acceso a esta opción/pantalla" , "Ingreso no permitido", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnNewButton_2.setBounds(531, 275, 189, 42);
		contentPanePrincipal.add(btnNewButton_2);
		
		JLabel lblTotalVendidoCon = new JLabel("TOTAL VENDIDO CON I/E");
		lblTotalVendidoCon.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTotalVendidoCon.setBounds(11, 196, 152, 14);
		contentPanePrincipal.add(lblTotalVendidoCon);
		
		txtTotalVendidoConIE = new JTextField();
		txtTotalVendidoConIE.setText("0.0");
		txtTotalVendidoConIE.setEditable(false);
		txtTotalVendidoConIE.setColumns(10);
		txtTotalVendidoConIE.setBounds(176, 193, 112, 20);
		contentPanePrincipal.add(txtTotalVendidoConIE);
		
		txtVentaSemana = new JTextField();
		txtVentaSemana.setText("0.0");
		txtVentaSemana.setEditable(false);
		txtVentaSemana.setColumns(10);
		txtVentaSemana.setBounds(176, 224, 112, 20);
		contentPanePrincipal.add(txtVentaSemana);
		txtVentaSemana.setText(formatea.format(pedCtrl.obtenerTotalesPedidosSemanaEnCurso()));
		JLabel lblVentanaSemana = new JLabel("VENTA SEMANA");
		lblVentanaSemana.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblVentanaSemana.setBounds(11, 229, 152, 14);
		contentPanePrincipal.add(lblVentanaSemana);
		
		JLabel lblTotalesPorForma = new JLabel("TOTALES POR FORMA DE PAGO");
		lblTotalesPorForma.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTotalesPorForma.setBounds(410, 37, 240, 14);
		contentPanePrincipal.add(lblTotalesPorForma);
		
		JScrollPane scrollPaneFormaPago = new JScrollPane();
		scrollPaneFormaPago.setBounds(407, 61, 243, 112);
		contentPanePrincipal.add(scrollPaneFormaPago);
		
		tableResFormaPago = new JTable();
		scrollPaneFormaPago.setViewportView(tableResFormaPago);
		
		JButton btnImprimirCorteCaja = new JButton("Imprimir Corte de Caja");
		btnImprimirCorteCaja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Se realiza la impresión del corte de caja a petición.
				pedCtrl.imprimirResumenCorteCaja(fechaSis);
			}
		});
		btnImprimirCorteCaja.setBounds(432, 204, 189, 23);
		contentPanePrincipal.add(btnImprimirCorteCaja);
		btnReporteGeneralVentas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Window ventanaPadre = SwingUtilities.getWindowAncestor(
                        (Component) arg0.getSource());
				//Acción para la generación de reporte general de ventas
				ReportesCtrl repCtrl = new ReportesCtrl(PrincipalLogueo.habilitaAuditoria);
				repCtrl.generarReporteVentasDiario(ventanaPadre);
								
			}
		});
		btnCierreDia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				OperacionesTiendaCtrl operTienda = new OperacionesTiendaCtrl(PrincipalLogueo.habilitaAuditoria);
				//Primero realizamos validación de si el sistema ya fue cerrado
				ParametrosCtrl parCtrl = new ParametrosCtrl(PrincipalLogueo.habilitaAuditoria);
				Parametro parametro = parCtrl.obtenerParametro("INDICADORCIERRE");
				String fechaParametro = parametro.getValorTexto();
				FechaSistema fecha = pedCtrl.obtenerFechasSistema();
				fechaSis = fecha.getFechaApertura();
				if(fechaSis.contentEquals(fechaParametro))
				{
					System.exit(0);
				}
				
				String resp = operTienda.finalizarDia(fechaSis);
				Window ventanaPadre = SwingUtilities.getWindowAncestor(
                        (Component) arg0.getSource());
				if(resp.equals(new String("PROCESO EXITOSO")))
				{
					//Se realiza la impresión del corte de caja a petición.
					pedCtrl.imprimirResumenCorteCaja(fechaSis);
					//Se implementa hilo para generar la reportería
					hiloReporteria.start();
					if(operTiendaCtrl.validarCierreSemanal())
					{
						hiloReportSemanal.start();
					}
					//Se realiza la impresión de la comanda con el resumen de las ventas
					pedCtrl.imprimirResumenGeneralVentas();
					//Se envía mensaje en pantalla con el resultado del cierre
					JOptionPane.showMessageDialog(ventanaPadre, "El cierre ha finalizado correctamente " , "El cierre ha finalizado, la aplicación se cerrará.", JOptionPane.INFORMATION_MESSAGE);
					//Se espera a que el hilo de reportería termine
					while(hiloReporteria.isAlive() || hiloReportSemanal.isAlive())
					{
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					//Se sale del sistema completamente.
					System.exit(0);
				}
				else
				{
					JOptionPane.showMessageDialog(ventanaPadre, "El cierre no ha finalizado se presentaron errores" , "El cierre NO ha finalizado", JOptionPane.ERROR_MESSAGE);
				}
				txtPaneResCierre.setText(resp);
			}
		});
		// Se llena Datatable con la información
		
		pintarTotTipoPedido();
		//Instanciamos el hilo
		hiloReporteria = new Thread(this);
		hiloReportSemanal = new Thread(this);
	}
	
	public DefaultTableModel pintarItemsInventario()
	{
		Object[] columnsName = new Object[6];
        
        columnsName[0] = "Id Item";
        columnsName[1] = "Nombre";
        columnsName[2] = "Unidad Medida";
        columnsName[3] = "Cantidad x Canasta";
        columnsName[4] = "Nombre Contenedor";
        columnsName[5] = "Cantidad Ingresar";
        ArrayList<Object> itemsIng = new ArrayList();
        itemsIng = invCtrl.obtenerItemInventarioIngresar();
       	DefaultTableModel modelo = new DefaultTableModel(){
       	    public boolean isCellEditable(int rowIndex,int columnIndex){
       	    	if(columnIndex < 5)
       	    	{
       	    		return false;
       	    	}
       	    	return true;
       	    }
       	};
		modelo.setColumnIdentifiers(columnsName);
		for(int y = 0; y < itemsIng.size();y++)
		{
			String[] fila =(String[]) itemsIng.get(y);
			modelo.addRow(fila);
		}
		return(modelo);
		
	}
	
	public void pintarTotTipoPedido()
	{
		Object[] columnsName = new Object[3];
        
        columnsName[0] = "Tipo Pedido/Inf";
        columnsName[1] = "TOTAL";
        columnsName[2] = "Cantidad";
        ArrayList totTipoPedido = new ArrayList();
        totTipoPedido = pedCtrl.obtenerTotalesPedidosPorTipo(fechaSis);
        DefaultTableModel modelo = new DefaultTableModel(){
       	    public boolean isCellEditable(int rowIndex,int columnIndex){
       	    	return false;
       	    }
       	    
       	    
       	};
		modelo.setColumnIdentifiers(columnsName);
		totalVendido = 0;
		for(int y = 0; y < totTipoPedido.size();y++)
		{
			String[] fila =(String[]) totTipoPedido.get(y);
			modelo.addRow(fila);
			totalVendido = totalVendido + Double.parseDouble(fila[1]);
		}
		totalIngresos = operTiendaCtrl.TotalizarIngreso(fechaSis);
		String[] filaIngreso = {"Ingresos", Double.toString(totalIngresos)};
		modelo.addRow(filaIngreso);
		totalEgresos = operTiendaCtrl.TotalizarEgreso(fechaSis);
		String[] filaEgreso = {"Egresos", Double.toString(totalEgresos)};
		modelo.addRow(filaEgreso);
		txtTotalVendido.setText(formatea.format(totalVendido));
		txtTotalVendidoConIE.setText(formatea.format(totalVendido + totalIngresos - totalEgresos));
		tableResTipoPedido.setModel(modelo);
				
		//Llenamos el modelo para el table de forma de pago
		
		columnsName = new Object[3];
        
        columnsName[0] = "Forma de Pago";
        columnsName[1] = "TOTAL";
        columnsName[2] = "Cantidad";
        ArrayList totFormaPago = new ArrayList();
        totFormaPago = pedCtrl.obtenerTotalesPedidosPorFormaPago(fechaSis);
        DefaultTableModel modeloFormaPago = new DefaultTableModel(){
       	    public boolean isCellEditable(int rowIndex,int columnIndex){
       	    	return false;
       	    }
       	    
       	    
       	};
       	modeloFormaPago.setColumnIdentifiers(columnsName);
		for(int y = 0; y < totFormaPago.size();y++)
		{
			String[] fila =(String[]) totFormaPago.get(y);
			modeloFormaPago.addRow(fila);
		}
		
		tableResFormaPago.setModel(modeloFormaPago);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Thread ct = Thread.currentThread();
		//Validamos si el hilo es de reportería para que realice la ejecución de los reportes
		if(ct == hiloReporteria) 
		{ 
			pedCtrl.enviarCorreoResumenGeneralVentas();
			pedCtrl.enviarCorreoResumenInventario();
		}else if(ct == hiloReportSemanal) 
		{ 
			pedCtrl.enviarCorreoDescuentosSemanal();
			pedCtrl.enviarCorreoDomiciliosSemanal();
			pedCtrl.enviarCorreoAnulacionesSemanal();
			pedCtrl.enviarCorreoAnulacionesDescuentaSemanal();
			pedCtrl.enviarCorreoEgresosSemanal();
			//Realizamos la generación de la varianza  y enviamos el indicador de resumida en true
			invCtrl.enviarCorreoVarianzaSemanal(true);
		}
	}
}
