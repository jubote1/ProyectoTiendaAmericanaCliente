package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import capaControlador.InventarioCtrl;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class VentInvRevisionInventarioInformacion extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable tableInformacion;
	private JLabel lblInformacion;
	private InventarioCtrl invCtrl = new InventarioCtrl(PrincipalLogueo.habilitaAuditoria);
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VentInvRevisionInventarioInformacion dialog = new VentInvRevisionInventarioInformacion(null, false, 0, "", 0);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VentInvRevisionInventarioInformacion(JDialog parent, boolean modal, int idItem, String fechaActual, int tipoReporte) {
		super(parent, modal);
		setTitle("INFORMACI\u00D3N REVISI\u00D3N DE INVENTARIOS");
		setBounds(100, 100, 547, 415);
		int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	    int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		setBounds((ancho / 2) - (this.getWidth() / 2), (alto / 2) - (this.getHeight() / 2), 547, 415);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		lblInformacion = new JLabel("");
		lblInformacion.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblInformacion.setBounds(72, 26, 385, 40);
		contentPanel.add(lblInformacion);
		
		JScrollPane scrollPaneInformacion = new JScrollPane();
		scrollPaneInformacion.setBounds(35, 103, 471, 216);
		contentPanel.add(scrollPaneInformacion);
		
		tableInformacion = new JTable();
		scrollPaneInformacion.setViewportView(tableInformacion);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
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
		//Informacion con los productos que están configurados para descontar de inventario
		if(tipoReporte == 1)
		{
			lblInformacion.setText("<html><center>INFORMACIÓN DE PRODUCTOS QUE DESCUENTAN ESTE ITEM DE INVENTARIO</html></center>");
			pintarProductosDescuentanInventario(idItem);
			//SI el reporte es para ver que ingresos con que hora real y cantidades se tuvieron ese día con el item de inventario en cuestión
		}else if(tipoReporte == 2)
		{
			lblInformacion.setText("<html><center>INFORMACIÓN DE INGRESOS AL INVENTARIO DEL ITEM</html></center>");
			pintarIngresosInventario(idItem,fechaActual);
		}
		//Si el reporte es para qeu los retiros fueron realizados
		else if(tipoReporte == 3)
		{
			lblInformacion.setText("<html><center>INFORMACIÓN DE RETIROS AL INVENTARIO DEL ITEM</html></center>");
			pintarRetirosInventario(idItem,fechaActual);
		}
		//Si el reporte es para ver los pedidos
		else if(tipoReporte == 4)
		{
			lblInformacion.setText("<html><center>INFORMACIÓN DE PEDIDOS QUE DESCONTARON INVENTARIO</html></center>");
			pintarPedidosDescInv(idItem,fechaActual);
		}
		//Si el reporte es de los pedidos donde hubo anulacion
		else if(tipoReporte == 5)
		{
			lblInformacion.setText("<html><center>PEDIDOS DONDE HUBO ANULACIÓN DEL ITEM</html></center>");
			pintarPedidosAnulacionItem(idItem,fechaActual);
		}
	}
	
	public void pintarProductosDescuentanInventario(int idItem)
	{
		Object[] columnsName = new Object[2];
		columnsName[0] = "Nombre Producto";
        columnsName[1] = "Cantidad Descuento";
        ArrayList invDescPro = new ArrayList();
        invDescPro = invCtrl.obtenerProductoDescuentoItemInventario(idItem);
       	DefaultTableModel modelo = new DefaultTableModel(){
       	    public boolean isCellEditable(int rowIndex,int columnIndex){
       	    	return(false);
       	    }
       	};
		modelo.setColumnIdentifiers(columnsName);
		for(int y = 0; y < invDescPro.size();y++)
		{
			String[] fila =(String[]) invDescPro.get(y);
			modelo.addRow(fila);
		}
		tableInformacion.setModel(modelo);
	}
	
	public void pintarIngresosInventario(int idItem, String fechaActual)
	{
		Object[] columnsName = new Object[4];
		columnsName[0] = "Id Ing Inventario";
        columnsName[1] = "Fecha Real";
        columnsName[2] = "Fecha Sistema";
        columnsName[3] = "Cantidad";
        ArrayList invDescPro = new ArrayList();
        invDescPro = invCtrl.obtenerIngresosItemInventario(idItem, fechaActual);
       	DefaultTableModel modelo = new DefaultTableModel(){
       	    public boolean isCellEditable(int rowIndex,int columnIndex){
       	    	return(false);
       	    }
       	};
		modelo.setColumnIdentifiers(columnsName);
		for(int y = 0; y < invDescPro.size();y++)
		{
			String[] fila =(String[]) invDescPro.get(y);
			modelo.addRow(fila);
		}
		tableInformacion.setModel(modelo);
	}
	
	public void pintarRetirosInventario(int idItem, String fechaActual)
	{
		Object[] columnsName = new Object[4];
		columnsName[0] = "Id Ret Inventario";
        columnsName[1] = "Fecha Real";
        columnsName[2] = "Fecha Sistema";
        columnsName[3] = "Cantidad";
        ArrayList invDescPro = new ArrayList();
        invDescPro = invCtrl.obtenerRetirosItemInventario(idItem, fechaActual);
       	DefaultTableModel modelo = new DefaultTableModel(){
       	    public boolean isCellEditable(int rowIndex,int columnIndex){
       	    	return(false);
       	    }
       	};
		modelo.setColumnIdentifiers(columnsName);
		for(int y = 0; y < invDescPro.size();y++)
		{
			String[] fila =(String[]) invDescPro.get(y);
			modelo.addRow(fila);
		}
		tableInformacion.setModel(modelo);
	}
	
	public void pintarPedidosDescInv(int idItem, String fechaActual)
	{
		Object[] columnsName = new Object[2];
		columnsName[0] = "Id Pedido";
        columnsName[1] = "Cantidad Descontada";
        ArrayList invDescPro = new ArrayList();
        invDescPro = invCtrl.obtenerPedidosDescItemInventario(idItem, fechaActual);
       	DefaultTableModel modelo = new DefaultTableModel(){
       	    public boolean isCellEditable(int rowIndex,int columnIndex){
       	    	return(false);
       	    }
       	};
		modelo.setColumnIdentifiers(columnsName);
		for(int y = 0; y < invDescPro.size();y++)
		{
			String[] fila =(String[]) invDescPro.get(y);
			modelo.addRow(fila);
		}
		tableInformacion.setModel(modelo);
	}
	
	public void pintarPedidosAnulacionItem(int idItem, String fechaActual)
	{
		Object[] columnsName = new Object[1];
		columnsName[0] = "Id Pedido";
        ArrayList invDescPro = new ArrayList();
        invDescPro = invCtrl.obtenerPedidosAnulItemInventario(idItem, fechaActual);
       	DefaultTableModel modelo = new DefaultTableModel(){
       	    public boolean isCellEditable(int rowIndex,int columnIndex){
       	    	return(false);
       	    }
       	};
		modelo.setColumnIdentifiers(columnsName);
		for(int y = 0; y < invDescPro.size();y++)
		{
			String[] fila =(String[]) invDescPro.get(y);
			modelo.addRow(fila);
		}
		tableInformacion.setModel(modelo);
	}

}
