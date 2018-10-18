package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import capaControlador.PedidoCtrl;
import capaModelo.DetallePedido;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class VentPedHisDetPedido extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private int idPedidoTienda;
	private JTable tableEstadosPedido;
	private JTable tableDetallePedido;
	private PedidoCtrl pedCtrl = new PedidoCtrl(PrincipalLogueo.habilitaAuditoria);
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VentPedHisDetPedido dialog = new VentPedHisDetPedido(null, false, 4);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VentPedHisDetPedido(java.awt.Frame parent, boolean modal, int idPedido ) {
		super(parent, modal);
		this.idPedidoTienda = idPedido;
		setTitle("HISTORIA Y DETALLE PEDIDO");
		setBounds(100, 100, 800, 400);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Calibri", Font.PLAIN, 15));
		tabbedPane.setBounds(10, 73, 764, 245);
		contentPanel.add(tabbedPane);
		
		JPanel panelEstadosPedido = new JPanel();
		tabbedPane.addTab("Estado del pedido", null, panelEstadosPedido, null);
		panelEstadosPedido.setLayout(null);
		
		JScrollPane scrollPaneEstPedido = new JScrollPane();
		scrollPaneEstPedido.setBounds(34, 11, 715, 190);
		panelEstadosPedido.add(scrollPaneEstPedido);
		
		tableEstadosPedido = new JTable();
		scrollPaneEstPedido.setViewportView(tableEstadosPedido);
		
		JPanel panelDetallePedido = new JPanel();
		tabbedPane.addTab("Detalle del pedido", null, panelDetallePedido, null);
		panelDetallePedido.setLayout(null);
		
		JScrollPane scrollDetallePedido = new JScrollPane();
		scrollDetallePedido.setBounds(25, 11, 724, 190);
		panelDetallePedido.add(scrollDetallePedido);
		
		tableDetallePedido = new JTable();
		scrollDetallePedido.setViewportView(tableDetallePedido);
		
		JLabel lblIdPedido = new JLabel("Id Pedido");
		lblIdPedido.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblIdPedido.setBounds(182, 28, 131, 34);
		contentPanel.add(lblIdPedido);
		
		JLabel lblPedido = new JLabel("");
		lblPedido.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblPedido.setBounds(339, 28, 106, 28);
		lblPedido.setText(Integer.toString(idPedidoTienda));
		contentPanel.add(lblPedido);
		pintarDetallePedido(idPedidoTienda);
		pintarHistoriaEstado(idPedidoTienda);
	}
	
	
	/**
	 * Método que se encarga de pintar para el pedido en cuestión la historia de los pasos por cada estado pedido
	 * @param idPedido Se recibe como parámetro el idPedido sobre el cual se va a traer la historia del pedido.
	 */
	public void pintarHistoriaEstado(int idPedido)
	{
		Object[] columnsName = new Object[3];
        
        columnsName[0] = "Fecha Cambio Estado";
        columnsName[1] = "Estado Anterior";
        columnsName[2] = "Estado Posterior";
        ArrayList<Object> estadosPedido = new ArrayList();
        estadosPedido = pedCtrl.obtenerHistoriaEstadoPedido(idPedido);
        DefaultTableModel modelo = new DefaultTableModel(){
       	    public boolean isCellEditable(int rowIndex,int columnIndex){
       	    	return false;
       	    }
       	    
       	    
       	};
		modelo.setColumnIdentifiers(columnsName);
		for(int y = 0; y < estadosPedido.size();y++)
		{
			String[] fila =(String[]) estadosPedido.get(y);
			modelo.addRow(fila);
		}
		tableEstadosPedido.setModel(modelo);
	}

	/**
	 * Método que se encarga de pintar para el pedido en cuestión el detalle de dicho pedido de los pasos por cada estado pedido
	 * @param idPedido Se recibe como parámetro el idPedido sobre el cual se va a traer el detalle del pedido.
	 */
	public void pintarDetallePedido(int idPedido)
	{
		Object[] columnsName = new Object[5];
        
        columnsName[0] = "Id Detalle";
        columnsName[1] = "Desc Producto";
        columnsName[2] = "Cantidad";
        columnsName[3] = "Valor Unitario";
        columnsName[4] = "Valor Total";
        ArrayList<DetallePedido> detsPedido = new ArrayList();
        detsPedido = pedCtrl.obtenerDetallePedidoPintar(idPedido);
        DefaultTableModel modelo = new DefaultTableModel(){
       	    public boolean isCellEditable(int rowIndex,int columnIndex){
       	    	return false;
       	    }
       	    
       	    
       	};
		modelo.setColumnIdentifiers(columnsName);
		String[] fila = new String[5];
		DetallePedido detPedTemp;
		for(int y = 0; y < detsPedido.size();y++)
		{
			detPedTemp = detsPedido.get(y);
			fila = new String[6];
			fila[0] = Integer.toString(detPedTemp.getIdDetallePedido());
			fila[1] = detPedTemp.getDescripcioProducto();
			fila[2] = Double.toString(detPedTemp.getCantidad());
			fila[3] = Double.toString(detPedTemp.getValorUnitario());
			fila[4] = Double.toString(detPedTemp.getValorTotal());
			modelo.addRow(fila);
		}
		tableDetallePedido.setModel(modelo);
	}
}
