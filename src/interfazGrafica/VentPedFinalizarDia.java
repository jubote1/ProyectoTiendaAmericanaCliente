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
import capaControlador.ReportesCtrl;
import capaControlador.PedidoCtrl;
import capaModelo.FechaSistema;
import capaModelo.ModificadorInventario;
import reportes.AbstractJasperReports;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
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

public class VentPedFinalizarDia extends JDialog {

	private JPanel contentPanePrincipal;
	private JTextField txtFechaInventario;
	String fechaSis;
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
		
		JButton btnCierreDia = new JButton("Finalizar D\u00EDa");
		
		btnCierreDia.setBounds(110, 272, 152, 37);
		contentPanePrincipal.add(btnCierreDia);
		
		JButton btnCancelar = new JButton("Cancelar/Salir");
		btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				dispose();
			}
		});
		btnCancelar.setBounds(455, 239, 201, 37);
		contentPanePrincipal.add(btnCancelar);
		
		JLabel lblFechaSistema = new JLabel("FECHA SISTEMA");
		lblFechaSistema.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblFechaSistema.setBounds(110, 403, 240, 14);
		contentPanePrincipal.add(lblFechaSistema);
		
		txtFechaInventario = new JTextField();
		txtFechaInventario.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtFechaInventario.setEnabled(false);
		txtFechaInventario.setEditable(false);
		txtFechaInventario.setBounds(427, 400, 135, 20);
		contentPanePrincipal.add(txtFechaInventario);
		txtFechaInventario.setColumns(10);
		
		//Vamos a recuperar la fecha del sistema y la vamos a mostrar en el campo correspondiente
		FechaSistema fecha = pedCtrl.obtenerFechasSistema();
		fechaSis = fecha.getFechaApertura();
		txtFechaInventario.setText(fechaSis);
		
		JScrollPane scrollResCierre = new JScrollPane();
		scrollResCierre.setBounds(45, 320, 680, 79);
		contentPanePrincipal.add(scrollResCierre);
		
		JTextPane txtPaneResCierre = new JTextPane();
		txtPaneResCierre.setEditable(false);
		scrollResCierre.setViewportView(txtPaneResCierre);
		
		JLabel lblTotalesPorTipo = new JLabel("TOTALES POR TIPO DE PEDIDO");
		lblTotalesPorTipo.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTotalesPorTipo.setBounds(89, 11, 240, 14);
		contentPanePrincipal.add(lblTotalesPorTipo);
		
		JScrollPane scrollPaneTipoPedido = new JScrollPane();
		scrollPaneTipoPedido.setBounds(99, 36, 189, 79);
		contentPanePrincipal.add(scrollPaneTipoPedido);
		
		tableResTipoPedido = new JTable();
		scrollPaneTipoPedido.setViewportView(tableResTipoPedido);
		
		JLabel lblTotalVendido = new JLabel("TOTAL VENDIDO");
		lblTotalVendido.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTotalVendido.setBounds(10, 138, 88, 14);
		contentPanePrincipal.add(lblTotalVendido);
		
		txtTotalVendido = new JTextField();
		txtTotalVendido.setEditable(false);
		txtTotalVendido.setBounds(175, 135, 86, 20);
		contentPanePrincipal.add(txtTotalVendido);
		txtTotalVendido.setColumns(10);
		
		JPanel panelReportes = new JPanel();
		panelReportes.setBorder(new LineBorder(new Color(0, 0, 0), 5));
		panelReportes.setBounds(341, 21, 400, 207);
		contentPanePrincipal.add(panelReportes);
		panelReportes.setLayout(null);
		
		JButton btnReporteGeneralVentas = new JButton("Reporte General Ventas");
		btnReporteGeneralVentas.setBounds(10, 116, 182, 23);
		panelReportes.add(btnReporteGeneralVentas);
		
		JButton btnReporteUsoInventario = new JButton("Reporte uso Inventario");
		btnReporteUsoInventario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Window ventanaPadre = SwingUtilities.getWindowAncestor(
                        (Component) arg0.getSource());
				//Acción para la generación inventario consumido
				//dispose();
				ReportesCtrl repCtrl = new ReportesCtrl(PrincipalLogueo.habilitaAuditoria);
				repCtrl.generarReporteInventarioCon(ventanaPadre);
			}
		});
		btnReporteUsoInventario.setBounds(10, 45, 182, 23);
		panelReportes.add(btnReporteUsoInventario);
		
		JButton btnCorteDeCaja = new JButton("Corte de Caja");
		btnCorteDeCaja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Window ventanaPadre = SwingUtilities.getWindowAncestor(
                        (Component) e.getSource());
				//Acción para la generación de reporte de caja
				//dispose();
				ReportesCtrl repCtrl = new ReportesCtrl(PrincipalLogueo.habilitaAuditoria);
				repCtrl.generarReporteCaja(ventanaPadre);
			}
		});
		btnCorteDeCaja.setBounds(204, 45, 184, 23);
		panelReportes.add(btnCorteDeCaja);
		
		JButton btnNewButton = new JButton("Corte de Caja Detallada");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Window ventanaPadre = SwingUtilities.getWindowAncestor(
                        (Component) e.getSource());
				//Acción para la generación de reporte de caja detallado
				//dispose();
				ReportesCtrl repCtrl = new ReportesCtrl(PrincipalLogueo.habilitaAuditoria);
				repCtrl.generarReporteCajaDet(ventanaPadre);
			}
		});
		btnNewButton.setBounds(204, 79, 184, 23);
		panelReportes.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Inventario Actual");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Window ventanaPadre = SwingUtilities.getWindowAncestor(
                        (Component) arg0.getSource());
				//Acción para la generación de reporte inventario actual
				//dispose();
				ReportesCtrl repCtrl = new ReportesCtrl(PrincipalLogueo.habilitaAuditoria);
				repCtrl.generarReporteInventarioAct(ventanaPadre);
			}
		});
		btnNewButton_1.setBounds(10, 79, 182, 23);
		panelReportes.add(btnNewButton_1);
		
		JLabel lblInventario = new JLabel("REPORTE INVENTARIOS");
		lblInventario.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblInventario.setBounds(12, 11, 133, 14);
		panelReportes.add(lblInventario);
		
		JLabel lblReporteVentas = new JLabel("REPORTE VENTAS");
		lblReporteVentas.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblReporteVentas.setBounds(204, 11, 110, 14);
		panelReportes.add(lblReporteVentas);
		
		JButton btnValidarCierre = new JButton("Validar Cierre");
		btnValidarCierre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				OperacionesTiendaCtrl operTienda = new OperacionesTiendaCtrl(PrincipalLogueo.habilitaAuditoria);
				String resp = operTienda.validacionesPreCierre(fechaSis);
				Window ventanaPadre = SwingUtilities.getWindowAncestor(
                        (Component) arg0.getSource());
				if(resp.equals(new String("")))
				{
					((JButton)arg0.getSource()).getParent();
					JOptionPane.showMessageDialog(ventanaPadre, "El cierre ha superado las prevalidaciones" , "Puede iniciar el cierre", JOptionPane.INFORMATION_MESSAGE);
				}
				else
				{
					JOptionPane.showMessageDialog(ventanaPadre, "El cierre presenta inconvenientes, se deben de validar temas adicionales" , "El cierre NO puede Iniciar.", JOptionPane.ERROR_MESSAGE);
				}
				txtPaneResCierre.setText(resp);
			}
		});
		btnValidarCierre.setBounds(110, 224, 152, 37);
		contentPanePrincipal.add(btnValidarCierre);
		
		JButton btnNewButton_2 = new JButton("Adm Ingresos/Egresos");
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
		btnNewButton_2.setBounds(89, 428, 189, 47);
		contentPanePrincipal.add(btnNewButton_2);
		
		JLabel lblTotalVendidoCon = new JLabel("TOTAL VENDIDO CON I/E");
		lblTotalVendidoCon.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTotalVendidoCon.setBounds(10, 175, 152, 14);
		contentPanePrincipal.add(lblTotalVendidoCon);
		
		txtTotalVendidoConIE = new JTextField();
		txtTotalVendidoConIE.setText("0.0");
		txtTotalVendidoConIE.setEditable(false);
		txtTotalVendidoConIE.setColumns(10);
		txtTotalVendidoConIE.setBounds(175, 172, 86, 20);
		contentPanePrincipal.add(txtTotalVendidoConIE);
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
				String resp = operTienda.finalizarDia(fechaSis);
				Window ventanaPadre = SwingUtilities.getWindowAncestor(
                        (Component) arg0.getSource());
				if(resp.equals(new String("PROCESO EXITOSO")))
				{
					JOptionPane.showMessageDialog(ventanaPadre, "El cierre ha finalizado correctamente " , "El cierre ha finalizado, la aplicación se cerrará.", JOptionPane.INFORMATION_MESSAGE);
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
		Object[] columnsName = new Object[2];
        
        columnsName[0] = "Tipo Pedido/Inf";
        columnsName[1] = "TOTAL";
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
		txtTotalVendido.setText(Double.toString(totalVendido));
		txtTotalVendidoConIE.setText(Double.toString(totalVendido + totalIngresos - totalEgresos));
		tableResTipoPedido.setModel(modelo);
				
	}
}
