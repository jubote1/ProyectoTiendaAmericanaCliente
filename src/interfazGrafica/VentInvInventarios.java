package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import capaControlador.InventarioCtrl;
import capaControlador.PedidoCtrl;
import capaModelo.FechaSistema;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class VentInvInventarios extends JFrame {

	private JPanel contentPaneInventario;
	private JTable tableItemsInv;
	private JButton btnIngresarInventario;
	private JButton btnRealizarPedidoEspecial;
	private JButton btnRealizarVarianzaCierre;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentInvInventarios frame = new VentInvInventarios();
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
	public VentInvInventarios() {
		setTitle("VENTANA INVENTARIOS");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 774, 571);
		contentPaneInventario = new JPanel();
		contentPaneInventario.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPaneInventario);
		contentPaneInventario.setLayout(null);
		
				
		JScrollPane scrPaneItemsInv = new JScrollPane();
		scrPaneItemsInv.setBounds(10, 11, 537, 270);
		contentPaneInventario.add(scrPaneItemsInv);
		
		tableItemsInv = new JTable();
		scrPaneItemsInv.setColumnHeaderView(tableItemsInv);
		scrPaneItemsInv.setViewportView(tableItemsInv);
		
		
		JButton btnAdministrarItems = new JButton("Adm Items Inventario");
		btnAdministrarItems.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				VentCRUDItemInventario itemInv = new VentCRUDItemInventario();
				itemInv.setVisible(true);
			}
		});
		btnAdministrarItems.setBounds(557, 11, 191, 61);
		contentPaneInventario.add(btnAdministrarItems);
		
		btnIngresarInventario = new JButton("Ingresar Inventario");
		btnIngresarInventario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentInvIngresarInventario ingInventario = new VentInvIngresarInventario();
				ingInventario.setVisible(true);
			}
		});
		btnIngresarInventario.setBounds(557, 83, 191, 61);
		contentPaneInventario.add(btnIngresarInventario);
		
		JButton btnRetirarInventario = new JButton("Retirar Inventario");
		btnRetirarInventario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				VentInvRetirarInventario ingInventario = new VentInvRetirarInventario();
				ingInventario.setVisible(true);
			}
		});
		btnRetirarInventario.setBounds(557, 155, 191, 61);
		contentPaneInventario.add(btnRetirarInventario);
		
		btnRealizarPedidoEspecial = new JButton("Realizar Pedido Especial");
		btnRealizarPedidoEspecial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentInvPedidoEspecial pedEspecial = new VentInvPedidoEspecial();
				pedEspecial.setVisible(true);
			}
		});
		btnRealizarPedidoEspecial.setBounds(557, 226, 191, 61);
		contentPaneInventario.add(btnRealizarPedidoEspecial);
		
		btnRealizarVarianzaCierre = new JButton("Realizar Varianza Cierre");
		btnRealizarVarianzaCierre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentIngresarVarianza ingVarianza = new VentIngresarVarianza();
				ingVarianza.setVisible(true);
			}
		});
		btnRealizarVarianzaCierre.setBounds(10, 292, 191, 61);
		contentPaneInventario.add(btnRealizarVarianzaCierre);
		PedidoCtrl pedCtrl = new PedidoCtrl();
		FechaSistema fechSistema = pedCtrl.obtenerFechasSistema();
		String fecha = fechSistema.getFechaApertura();
		pintarResumenInventario(fecha);
	}
	
	public void pintarResumenInventario( String fecha)
	{
		Object[] columnsName = new Object[6];
        
		columnsName[0] = "Id Item";
		columnsName[1] = "Nombre Item";
        columnsName[2] = "Inicio";
        columnsName[3] = "Retiro";
        columnsName[4] = "Ingreso";
        columnsName[5] = "Consumo";
        InventarioCtrl invCtrl = new InventarioCtrl();
		ArrayList itemsResumen = invCtrl.obtenerItemInventarioResumen(fecha);
		DefaultTableModel modeloItemResumen = new DefaultTableModel(){
       	    public boolean isCellEditable(int rowIndex,int columnIndex){
       	    	return false;
       	    }
       	};
        modeloItemResumen.setColumnIdentifiers(columnsName);
		for(int y = 0; y < itemsResumen.size();y++)
		{
			String[] fila =(String[]) itemsResumen.get(y);
			modeloItemResumen.addRow(fila);
		}
		tableItemsInv.setModel(modeloItemResumen);
	}
}
