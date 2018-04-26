package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JLayeredPane;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import capaControlador.PedidoCtrl;

import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import java.awt.Font;

public class VentanaTransaccional extends JFrame {

	private JPanel contentPane;
	private JTable tblMaestroPedidos;
	private static int idTipoPedido = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaTransaccional frame = new VentanaTransaccional();
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
	public VentanaTransaccional() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1014, 771);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 629, 978, 92);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 4, 0, 0));
		
		JButton btnBuscar = new JButton("Buscar");
		panel.add(btnBuscar);
		
		JButton btnCambioFactura = new JButton("Cambiar Factura");
		panel.add(btnCambioFactura);
		
		JButton btnReimpresion = new JButton("Reimprimir Factura");
		panel.add(btnReimpresion);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panel.add(btnSalir);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(780, 11, 208, 346);
		contentPane.add(panel_2);
		panel_2.setLayout(new GridLayout(4, 1, 0, 0));
		
		JButton btnPuntoDeVenta = new JButton("Punto de venta");
		
		panel_2.add(btnPuntoDeVenta);
		
		JButton btnDomicilio = new JButton("Domicilio");
		
		panel_2.add(btnDomicilio);
		
		JButton btnParaLlevar = new JButton("Para Llevar");
		
		panel_2.add(btnParaLlevar);
		
		JButton btnTotal = new JButton("Total");
		btnTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				idTipoPedido = 0;
				DefaultTableModel modelo = pintarPedidos();
				tblMaestroPedidos.setModel(modelo);
				btnTotal.setBackground(Color.YELLOW);
				btnParaLlevar.setBackground(new Color(240,240,240));
				btnDomicilio.setBackground(new Color(240,240,240));
				btnPuntoDeVenta.setBackground(new Color(240,240,240));
			}
		});
		btnTotal.setBackground(Color.YELLOW);
		panel_2.add(btnTotal);
		
		btnParaLlevar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				idTipoPedido = 3;
				DefaultTableModel modelo = pintarPedidos();
				tblMaestroPedidos.setModel(modelo);
				btnTotal.setBackground(new Color(240,240,240));
				btnParaLlevar.setBackground(Color.YELLOW);
				btnDomicilio.setBackground(new Color(240,240,240));
				btnPuntoDeVenta.setBackground(new Color(240,240,240));
			}
		});
		
		btnDomicilio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				idTipoPedido = 1;
				DefaultTableModel modelo = pintarPedidos();
				tblMaestroPedidos.setModel(modelo);
				btnTotal.setBackground(new Color(240,240,240));
				btnParaLlevar.setBackground(new Color(240,240,240));
				btnDomicilio.setBackground(Color.YELLOW);
				btnPuntoDeVenta.setBackground(new Color(240,240,240));
			}
		});
		
		btnPuntoDeVenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				idTipoPedido = 2;
				DefaultTableModel modelo = pintarPedidos();
				tblMaestroPedidos.setModel(modelo);
				btnTotal.setBackground(new Color(240,240,240));
				btnParaLlevar.setBackground(new Color(240,240,240));
				btnDomicilio.setBackground(new Color(240,240,240));
				btnPuntoDeVenta.setBackground(Color.YELLOW);
			}
		});
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Calibri", Font.PLAIN, 15));
		tabbedPane.setBounds(10, 368, 978, 250);
		contentPane.add(tabbedPane);
		
		JPanel panelDetallePedido = new JPanel();
		tabbedPane.addTab("Detalle del pedido", null, panelDetallePedido, null);
		
		JPanel panel_4 = new JPanel();
		tabbedPane.addTab("Estado del pedido", null, panel_4, null);
		panel_4.setLayout(null);
		
		JPanel panel_5 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_5, null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 762, 314);
		contentPane.add(scrollPane);
		
		tblMaestroPedidos =   new JTable();
		tblMaestroPedidos.setEnabled(true);
		DefaultTableModel modelo = pintarPedidos();
		tblMaestroPedidos.setModel(modelo);
		scrollPane.setViewportView(tblMaestroPedidos);
		
		JButton btnAvanzarEstado = new JButton("Avanzar Estado");
		btnAvanzarEstado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = tblMaestroPedidos.getSelectedRow();
				if(filaSeleccionada == -1)
				{
					JOptionPane.showMessageDialog(null, "Debe Seleccionar algún pedido para Avanzar Estado " , "No ha Seleccionado Pedido", JOptionPane.ERROR_MESSAGE);
					return;
				}
				// Se captura el valor del idDetalle que se desea eliminar
				int idPedidoAvanzar= (int)tblMaestroPedidos.getValueAt(filaSeleccionada, 0);
			}
		});
		btnAvanzarEstado.setBounds(425, 336, 180, 23);
		contentPane.add(btnAvanzarEstado);
		
		JButton btnRetrocederEstado = new JButton("Devolver Estado");
		btnRetrocederEstado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int filaSeleccionada = tblMaestroPedidos.getSelectedRow();
				if(filaSeleccionada == -1)
				{
					JOptionPane.showMessageDialog(null, "Debe Seleccionar algún pedido para Devolver Estado " , "No ha Seleccionado Pedido", JOptionPane.ERROR_MESSAGE);
					return;
				}
				// Se captura el valor del idDetalle que se desea eliminar
				int idPedidoDevolver = (int)tblMaestroPedidos.getValueAt(filaSeleccionada, 0);
			}
		});
		btnRetrocederEstado.setBounds(164, 334, 180, 23);
		contentPane.add(btnRetrocederEstado);
	}
	
	public DefaultTableModel pintarPedidos()
	{
		Object[] columnsName = new Object[5];
        
        columnsName[0] = "Id Pedido Tienda";
        columnsName[1] = "Fecha Pedido";
        columnsName[2] = "Nombres";
        columnsName[3] = "Tipo Pedido";
        columnsName[4] = "Dirección";
        PedidoCtrl pedCtrl = new  PedidoCtrl();
        ArrayList<Object> pedidos = new ArrayList();
        if(idTipoPedido == 0)
        {
        	pedidos = pedCtrl.obtenerPedidosTable();
        }
        else
        {
        	pedidos = pedCtrl.obtenerPedidosPorTipo(idTipoPedido);
        }
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.setColumnIdentifiers(columnsName);
		for(int y = 0; y < pedidos.size();y++)
		{
			String[] fila =(String[]) pedidos.get(y);
			modelo.addRow(fila);
		}
		return(modelo);
		
	}
}
