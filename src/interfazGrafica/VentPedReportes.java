package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Window;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import capaControlador.BiometriaCtrl;
import capaControlador.InventarioCtrl;
import capaControlador.PedidoCtrl;
import capaControlador.ReportesCtrl;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.awt.event.ActionEvent;

public class VentPedReportes extends JDialog {

	private final JPanel contentPanelReportes = new JPanel();
	private JTable tableReporte;
	private InventarioCtrl invCtrl = new InventarioCtrl(PrincipalLogueo.habilitaAuditoria);
	private PedidoCtrl pedCtrl = new PedidoCtrl(PrincipalLogueo.habilitaAuditoria);
	private BiometriaCtrl bioCtrl = new BiometriaCtrl(PrincipalLogueo.habilitaAuditoria);
	private String fechaActual;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VentPedReportes dialog = new VentPedReportes(null, false, "", 0);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VentPedReportes(JDialog parent, boolean modal, String fechaActual, int tipoReporte) {
		super(parent, modal);
		this.fechaActual = fechaActual;
		if(tipoReporte == 1)
		{
			setTitle("REPORTE DE INVENTARIO CONSUMIDO");
		}else if(tipoReporte == 2)
		{
			setTitle("REPORTE DE INVENTARIO ACTUAL");
		}else if(tipoReporte == 3)
		{
			setTitle("REPORTE DE CAJA");
		}else if(tipoReporte == 4)
		{
			setTitle("REPORTE DE CAJA DETALLADO");
		}else if(tipoReporte == 5)
		{
			setTitle("REPORTE DE PORCIONES VENDIDAS");
		}else if(tipoReporte == 6)
		{
			setTitle("REPORTE ENTRADA/SALIDA EMPLEADOS");
		}
		
		setBounds(100, 100, 582, 488);
		getContentPane().setLayout(new BorderLayout());
		contentPanelReportes.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanelReportes, BorderLayout.CENTER);
		contentPanelReportes.setLayout(null);
		
		JScrollPane scrollPaneReporte = new JScrollPane();
		scrollPaneReporte.setBounds(10, 11, 546, 379);
		contentPanelReportes.add(scrollPaneReporte);
		
		tableReporte = new JTable();
		scrollPaneReporte.setViewportView(tableReporte);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			
			JButton btnGenEstiloReporte = new JButton("GENERAR EN ESTILO REPORTE");
			btnGenEstiloReporte.setEnabled(false);
			btnGenEstiloReporte.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Window ventanaPadre = SwingUtilities.getWindowAncestor(
	                        (Component) arg0.getSource());
					//Acción para la generación inventario consumido
					//dispose();
					if(tipoReporte == 1)
					{
						ReportesCtrl repCtrl = new ReportesCtrl(PrincipalLogueo.habilitaAuditoria);
						repCtrl.generarReporteInventarioCon(ventanaPadre);
					}else if(tipoReporte == 2)
					{
						ReportesCtrl repCtrl = new ReportesCtrl(PrincipalLogueo.habilitaAuditoria);
						repCtrl.generarReporteInventarioAct(ventanaPadre);
					}else if(tipoReporte == 3)
					{
						ReportesCtrl repCtrl = new ReportesCtrl(PrincipalLogueo.habilitaAuditoria);
						repCtrl.generarReporteCaja(ventanaPadre);
					}
					else if(tipoReporte == 4)
					{
						ReportesCtrl repCtrl = new ReportesCtrl(PrincipalLogueo.habilitaAuditoria);
						repCtrl.generarReporteCajaDet(ventanaPadre);
					}
					
				}
			});
			buttonPane.add(btnGenEstiloReporte);
			{
				JButton okButton = new JButton("REGRESAR");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		if(tipoReporte == 1)
		{
			pintarItemsInventarioConsumido();
		} else if(tipoReporte == 2)
		{
			pintarItemsInventarioActual();
		}else if(tipoReporte == 3)
		{
			pintarReporteDeCaja();
		}else if(tipoReporte == 4)
		{
			pintarReporteDeCajaDetallado();
		}else if(tipoReporte == 5)
		{
			pintarReportepPorcionesVendidas();
		}else if(tipoReporte == 6)
		{
			pintarReporteEntradaSalidaEmp();
		}
	}
	
	public void pintarReporteEntradaSalidaEmp()
	{
		Object[] columnsName = new Object[5];
		columnsName[0] = "Empleado";
        columnsName[1] = "Fecha";
        columnsName[2] = "ENTRADA";
        columnsName[3] = "SALIDA";
        columnsName[4] = "HORAS";
        ArrayList repEntradasSalidas = new ArrayList();
        repEntradasSalidas = bioCtrl.obtenerEntradasSalidasEmpleados();
        DefaultTableModel modelo = new DefaultTableModel(){
       	    public boolean isCellEditable(int rowIndex,int columnIndex){
       	    	return(false);
       	    }
       	};
		modelo.setColumnIdentifiers(columnsName);
		for(int y = 0; y < repEntradasSalidas.size();y++)
		{
			String[] fila =(String[]) repEntradasSalidas.get(y);
			modelo.addRow(fila);
		}
		tableReporte.setModel(modelo);
		
	}
	
	public void pintarReportepPorcionesVendidas()
	{
		Object[] columnsName = new Object[3];
		columnsName[0] = "Tipo de Porción";
        columnsName[1] = "Cantidad";
        columnsName[2] = "Fecha";
        ArrayList porcionesVendidas = new ArrayList();
        porcionesVendidas = pedCtrl.obtenerResumenPorciones(fechaActual, fechaActual);
        DefaultTableModel modelo = new DefaultTableModel(){
       	    public boolean isCellEditable(int rowIndex,int columnIndex){
       	    	return(false);
       	    }
       	};
		modelo.setColumnIdentifiers(columnsName);
		for(int y = 0; y < porcionesVendidas.size();y++)
		{
			String[] fila =(String[]) porcionesVendidas.get(y);
			modelo.addRow(fila);
		}
		tableReporte.setModel(modelo);
		
	}
	
	public void pintarItemsInventarioConsumido()
	{
		Object[] columnsName = new Object[4];
		columnsName[0] = "Id Item";
        columnsName[1] = "Nombre Producto";
        columnsName[2] = "Cantidad Consumido";
        columnsName[3] = "Unidad";
        ArrayList invConsumido = new ArrayList();
        invConsumido = invCtrl.obtenerInventarioConsumido(fechaActual);
       	DefaultTableModel modelo = new DefaultTableModel(){
       	    public boolean isCellEditable(int rowIndex,int columnIndex){
       	    	return(false);
       	    }
       	};
		modelo.setColumnIdentifiers(columnsName);
		for(int y = 0; y < invConsumido.size();y++)
		{
			String[] fila =(String[]) invConsumido.get(y);
			modelo.addRow(fila);
		}
		tableReporte.setModel(modelo);
		
	}
	
	public void pintarItemsInventarioActual()
	{
		Object[] columnsName = new Object[4];
		columnsName[0] = "Id Item";
        columnsName[1] = "Nombre Producto";
        columnsName[2] = "Cantidad Existente";
        columnsName[3] = "Unidad";
        ArrayList invConsumido = new ArrayList();
        invConsumido = invCtrl.obtenerInventarioActualReporte(fechaActual);
       	DefaultTableModel modelo = new DefaultTableModel(){
       	    public boolean isCellEditable(int rowIndex,int columnIndex){
       	    	return(false);
       	    }
       	};
		modelo.setColumnIdentifiers(columnsName);
		for(int y = 0; y < invConsumido.size();y++)
		{
			String[] fila =(String[]) invConsumido.get(y);
			modelo.addRow(fila);
		}
		tableReporte.setModel(modelo);
		
	}
	
	public void pintarReporteDeCaja()
	{
		DecimalFormat formatea = new DecimalFormat("###,###");
		Object[] columnsName = new Object[3];
		columnsName[0] = "TOTAL PEDIDOS";
        columnsName[1] = "Empleado";
        columnsName[2] = "Forma de Pago";
        ArrayList repCaja = new ArrayList();
        repCaja = pedCtrl.obtenerReporteDeCaja(fechaActual);
       	DefaultTableModel modelo = new DefaultTableModel(){
       	    public boolean isCellEditable(int rowIndex,int columnIndex){
       	    	return(false);
       	    }
       	};
		modelo.setColumnIdentifiers(columnsName);
		for(int y = 0; y < repCaja.size();y++)
		{
			String[] fila =(String[]) repCaja.get(y);
			fila[0] = formatea.format(Double.parseDouble(fila[0]));
			modelo.addRow(fila);
		}
		tableReporte.setModel(modelo);
		
	}
	
	public void pintarReporteDeCajaDetallado()
	{
		DecimalFormat formatea = new DecimalFormat("###,###");
		Object[] columnsName = new Object[4];
		columnsName[0] = "Total Neto Pedido";
        columnsName[1] = "Empleado";
        columnsName[2] = "Número Factura";
        columnsName[3] = "Forma de Pago";
        ArrayList repCaja = new ArrayList();
        repCaja = pedCtrl.obtenerReporteDeCajaDetallado(fechaActual);
       	DefaultTableModel modelo = new DefaultTableModel(){
       	    public boolean isCellEditable(int rowIndex,int columnIndex){
       	    	return(false);
       	    }
       	};
		modelo.setColumnIdentifiers(columnsName);
		for(int y = 0; y < repCaja.size();y++)
		{
			String[] fila =(String[]) repCaja.get(y);
			fila[0] = formatea.format(Double.parseDouble(fila[0]));
			modelo.addRow(fila);
		}
		tableReporte.setModel(modelo);
		
	}
}
