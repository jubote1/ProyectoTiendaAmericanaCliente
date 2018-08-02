package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import capaConexion.ConexionBaseDatos;
import capaControlador.InventarioCtrl;
import capaControlador.OperacionesTiendaCtrl;
import capaControlador.PedidoCtrl;
import capaControlador.ReportesCtrl;
import capaModelo.FechaSistema;
import capaModelo.ModificadorInventario;
import reportes.AbstractJasperReports;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class VentPedFinalizarDia extends JFrame {

	private JPanel contentPanePrincipal;
	private JTextField txtFechaInventario;
	String fechaSis;
	ArrayList<ModificadorInventario> inventarioIngresar = new ArrayList();
	private JTable tableResTipoPedido;
	private JTextField txtTotalVendido;
	private double totalVendido = 0;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentPedFinalizarDia frame = new VentPedFinalizarDia();
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
	public VentPedFinalizarDia() {
		this.setAlwaysOnTop(true);
		setTitle("FINALIZAR DIA");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 450);
		contentPanePrincipal = new JPanel();
		contentPanePrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPanePrincipal);
		contentPanePrincipal.setLayout(null);
		DefaultTableModel modelo = pintarItemsInventario();
		
		JButton btnCierreDia = new JButton("Finalizar D\u00EDa");
		
		btnCierreDia.setBounds(110, 239, 152, 37);
		contentPanePrincipal.add(btnCierreDia);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				dispose();
			}
		});
		btnCancelar.setBounds(481, 239, 146, 37);
		contentPanePrincipal.add(btnCancelar);
		
		JLabel lblFechaSistema = new JLabel("FECHA SISTEMA");
		lblFechaSistema.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblFechaSistema.setBounds(110, 387, 240, 14);
		contentPanePrincipal.add(lblFechaSistema);
		
		txtFechaInventario = new JTextField();
		txtFechaInventario.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtFechaInventario.setEnabled(false);
		txtFechaInventario.setEditable(false);
		txtFechaInventario.setBounds(429, 384, 135, 20);
		contentPanePrincipal.add(txtFechaInventario);
		txtFechaInventario.setColumns(10);
		
		//Vamos a recuperar la fecha del sistema y la vamos a mostrar en el campo correspondiente
		PedidoCtrl pedCtrl = new PedidoCtrl();
		FechaSistema fecha = pedCtrl.obtenerFechasSistema();
		fechaSis = fecha.getFechaApertura();
		txtFechaInventario.setText(fechaSis);
		
		JScrollPane scrollResCierre = new JScrollPane();
		scrollResCierre.setBounds(45, 289, 680, 79);
		contentPanePrincipal.add(scrollResCierre);
		
		JTextPane txtPaneResCierre = new JTextPane();
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
		lblTotalVendido.setBounds(77, 138, 88, 14);
		contentPanePrincipal.add(lblTotalVendido);
		
		txtTotalVendido = new JTextField();
		txtTotalVendido.setEditable(false);
		txtTotalVendido.setBounds(175, 135, 86, 20);
		contentPanePrincipal.add(txtTotalVendido);
		txtTotalVendido.setColumns(10);
		
		JButton btnReporteGeneralVentas = new JButton("Reporte General Ventas");
		btnReporteGeneralVentas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Acción para la generación de reporte general de ventas
				ReportesCtrl repCtrl = new ReportesCtrl();
				repCtrl.generarReporteVentasDiario();
								
			}
		});
		btnReporteGeneralVentas.setBounds(455, 33, 255, 23);
		contentPanePrincipal.add(btnReporteGeneralVentas);
		btnCierreDia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				OperacionesTiendaCtrl operTienda = new OperacionesTiendaCtrl();
				String resp = operTienda.finalizarDia(fechaSis);
				if(resp.equals(new String("PROCESO EXITOSO")))
				{
					JOptionPane.showMessageDialog(null, "El cierre ha finalizado correctamente " , "El cierre ha finalizado", JOptionPane.INFORMATION_MESSAGE);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "El cierre no ha finalizado se presentaron errores" , "El cierre NO ha finalizado", JOptionPane.ERROR_MESSAGE);
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
        InventarioCtrl invCtrl = new  InventarioCtrl();
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
        
        columnsName[0] = "Tipo Pedido";
        columnsName[1] = "TOTAL";
        PedidoCtrl pedCtrl = new  PedidoCtrl();
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
		txtTotalVendido.setText(Double.toString(totalVendido));
		tableResTipoPedido.setModel(modelo);
				
	}
}
