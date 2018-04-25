package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import capaControlador.PedidoCtrl;

import java.awt.Color;

public class MaestroPedidos extends JFrame {

	private JPanel maestroPrincipal;
	private JTable tblMaestroPedidos;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MaestroPedidos frame = new MaestroPedidos();
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
	public MaestroPedidos() {
		setTitle("MAESTRO DE PEDIDOS");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 899, 600);
		maestroPrincipal = new JPanel();
		maestroPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(maestroPrincipal);
		maestroPrincipal.setLayout(null);
		
		JScrollPane scrollPedidos = new JScrollPane();
		scrollPedidos.setBounds(10, 11, 651, 405);
		maestroPrincipal.add(scrollPedidos);
		
		tblMaestroPedidos = new JTable();
		scrollPedidos.setColumnHeaderView(tblMaestroPedidos);
		
		JPanel pnlManejoEstados = new JPanel();
		pnlManejoEstados.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		pnlManejoEstados.setBounds(671, 11, 202, 110);
		maestroPrincipal.add(pnlManejoEstados);
		pnlManejoEstados.setLayout(null);
		
		JButton btnAvanzarEstado = new JButton("Avanzar Estado");
		btnAvanzarEstado.setBounds(20, 11, 159, 23);
		pnlManejoEstados.add(btnAvanzarEstado);
		
		JButton btnDevolverEstado = new JButton("Devolver Estado");
		btnDevolverEstado.setBounds(20, 59, 159, 23);
		pnlManejoEstados.add(btnDevolverEstado);
	}
	
	public void traerPedidos()
	{
		
	}
	
	public DefaultTableModel pintarPedidos()
	{
		Object[] columnsName = new Object[5];
        
        columnsName[0] = "Id Pedido Tienda";
        columnsName[1] = "Descripcion";
        columnsName[2] = "Tipo Pedido";
        columnsName[3] = "Id Tipo Pedido";
        columnsName[4] = "Descripción Corta";
        PedidoCtrl pedCtrl = new  PedidoCtrl();
        ArrayList<Object> estados =  pedCtrl.obtenerEstado();
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.setColumnIdentifiers(columnsName);
		for(int y = 0; y < estados.size();y++)
		{
			String[] fila =(String[]) estados.get(y);
			modelo.addRow(fila);
		}
		return(modelo);
		
	}
}
