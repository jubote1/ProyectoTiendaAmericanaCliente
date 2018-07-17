package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JLayeredPane;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import capaControlador.PedidoCtrl;
import capaModelo.Cliente;
import capaModelo.PedidoDescuento;
import capaModelo.TipoPedido;
import renderTable.CellRenderTransaccional;

import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import java.awt.Font;

public class VentPedTransaccional extends JFrame {

	private JPanel contentPane;
	private JTable tblMaestroPedidos;
	private static int idTipoPedido = 0;
	private JTable tableEstadosPedido;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentPedTransaccional frame = new VentPedTransaccional();
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
	public VentPedTransaccional() {
		setTitle("VENTANA TRANSACCIONAL");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1014, 700);
		this.setExtendedState(MAXIMIZED_BOTH);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelBotones = new JPanel();
		panelBotones.setBounds(10, 562, 978, 92);
		contentPane.add(panelBotones);
		panelBotones.setLayout(new GridLayout(0, 5, 0, 0));
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelBotones.add(btnBuscar);
		
		JButton btnReabrirFactura = new JButton("Reabrir Factura");
		btnReabrirFactura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int filaSeleccionada = tblMaestroPedidos.getSelectedRow();
				if(filaSeleccionada == -1)
				{
					JOptionPane.showMessageDialog(null, "Debe Seleccionar un pedido para Reabrir " , "No ha Seleccionado Pedido", JOptionPane.ERROR_MESSAGE);
					return;
				}
				else
				{
					int idPedido = Integer.parseInt(tblMaestroPedidos.getValueAt(filaSeleccionada, 0).toString());
					//Deberemos Rellenar las variables estáticas de la pantalla VentPedTomarPedidos
					VentPedTomarPedidos ventTomaPedidos = new VentPedTomarPedidos();
					VentPedTomarPedidos.idPedido = idPedido;
					PedidoCtrl pedCtrl = new PedidoCtrl();
					boolean tieneDescuento = pedCtrl.existePedidoDescuento(idPedido);
					if(tieneDescuento)
					{
						VentPedTomarPedidos.tieneDescuento = true;
						PedidoDescuento desPedido = pedCtrl.obtenerPedidoDescuento(idPedido);
						VentPedTomarPedidos.descuento = desPedido.getDescuentoPesos();
						
					}
					double totalBruto = pedCtrl.obtenerTotalBrutoPedido(idPedido);
					VentPedTomarPedidos.totalPedido = totalBruto;
					VentPedTomarPedidos.detallesPedido = pedCtrl.obtenerDetallePedido(idPedido);
					Cliente clientePedido = pedCtrl.obtenerClientePedido(idPedido);
					VentPedTomarPedidos.idCliente = clientePedido.getIdcliente();
					VentPedTomarPedidos.nombreCliente = clientePedido.getNombres() + " " + clientePedido.getApellidos();
					boolean tieneFormaPago = pedCtrl.existeFormaPago(idPedido);
					if(tieneFormaPago)
					{
						VentPedTomarPedidos.tieneFormaPago = true;
					}
					//Continuamos con el tipo de pedido para desplegarlo correctamente
					ArrayList<TipoPedido>tiposPedidos = pedCtrl.obtenerTiposPedidoNat();
					int idTipoPedido = pedCtrl.obtenerTipoDePedido(idPedido);
					//buscamos en el arreglo que posición tiene
					int numTipoPedido = 0;
					for(int i = 0; i< tiposPedidos.size(); i++)
					{
						TipoPedido tipPedidoTemp = tiposPedidos.get(i);
						if(tipPedidoTemp.getIdTipoPedido() == idTipoPedido)
						{
							numTipoPedido = i;
							break;
						}
					}
					VentPedTomarPedidos.numTipoPedidoAct = numTipoPedido;
					//Retornamos el tipo de Pedido
					ventTomaPedidos.setVisible(true);
					dispose();
				}
			}
		});
		btnReabrirFactura.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelBotones.add(btnReabrirFactura);
		
		JButton btnReimpresion = new JButton("Reimprimir Factura");
		btnReimpresion.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelBotones.add(btnReimpresion);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		panelBotones.add(btnSalir);
		
		JButton btnRegresarMenu = new JButton("Regresar Men\u00FA");
		btnRegresarMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentPrincipal ventPrincipal = new VentPrincipal();
				ventPrincipal.setVisible(true);
				dispose();
			}
		});
		btnRegresarMenu.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelBotones.add(btnRegresarMenu);
		
		JPanel panelFiltrosPedidos = new JPanel();
		panelFiltrosPedidos.setBounds(780, 11, 208, 346);
		contentPane.add(panelFiltrosPedidos);
		panelFiltrosPedidos.setLayout(new GridLayout(4, 1, 0, 0));
		
		JButton btnPuntoDeVenta = new JButton("Punto de venta");
		
		panelFiltrosPedidos.add(btnPuntoDeVenta);
		
		JButton btnDomicilio = new JButton("Domicilio");
		
		panelFiltrosPedidos.add(btnDomicilio);
		
		JButton btnParaLlevar = new JButton("Para Llevar");
		
		panelFiltrosPedidos.add(btnParaLlevar);
		
		JButton btnTotal = new JButton("Total");
		btnTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				idTipoPedido = 0;
				pintarPedidos();
				btnTotal.setBackground(Color.YELLOW);
				btnParaLlevar.setBackground(new Color(240,240,240));
				btnDomicilio.setBackground(new Color(240,240,240));
				btnPuntoDeVenta.setBackground(new Color(240,240,240));
			}
		});
		btnTotal.setBackground(Color.YELLOW);
		panelFiltrosPedidos.add(btnTotal);
		
		btnParaLlevar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				idTipoPedido = 3;
				pintarPedidos();
				btnTotal.setBackground(new Color(240,240,240));
				btnParaLlevar.setBackground(Color.YELLOW);
				btnDomicilio.setBackground(new Color(240,240,240));
				btnPuntoDeVenta.setBackground(new Color(240,240,240));
			}
		});
		
		btnDomicilio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				idTipoPedido = 1;
				pintarPedidos();
				btnTotal.setBackground(new Color(240,240,240));
				btnParaLlevar.setBackground(new Color(240,240,240));
				btnDomicilio.setBackground(Color.YELLOW);
				btnPuntoDeVenta.setBackground(new Color(240,240,240));
			}
		});
		
		btnPuntoDeVenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				idTipoPedido = 2;
				pintarPedidos();
				btnTotal.setBackground(new Color(240,240,240));
				btnParaLlevar.setBackground(new Color(240,240,240));
				btnDomicilio.setBackground(new Color(240,240,240));
				btnPuntoDeVenta.setBackground(Color.YELLOW);
			}
		});
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Calibri", Font.PLAIN, 15));
		tabbedPane.setBounds(10, 368, 978, 183);
		contentPane.add(tabbedPane);
		
		JPanel panelEstadosPedido = new JPanel();
		tabbedPane.addTab("Estado del pedido", null, panelEstadosPedido, null);
		panelEstadosPedido.setLayout(null);
		
		JScrollPane scrollPaneEstPedido = new JScrollPane();
		scrollPaneEstPedido.setBounds(86, 11, 779, 180);
		panelEstadosPedido.add(scrollPaneEstPedido);
		
		tableEstadosPedido = new JTable();
		scrollPaneEstPedido.setColumnHeaderView(tableEstadosPedido);
		
		JPanel panelDetallePedido = new JPanel();
		tabbedPane.addTab("Detalle del pedido", null, panelDetallePedido, null);
		panelDetallePedido.setLayout(null);
		
		JScrollPane scrollDetallePedido = new JScrollPane();
		scrollDetallePedido.setBounds(25, 11, 805, 105);
		panelDetallePedido.add(scrollDetallePedido);
		
		JPanel panelDibujaDetalle = new JPanel();
		scrollDetallePedido.setViewportView(panelDibujaDetalle);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 762, 314);
		contentPane.add(scrollPane);
		
		tblMaestroPedidos =   new JTable();
		tblMaestroPedidos.setEnabled(true);
		pintarPedidos();
		scrollPane.setViewportView(tblMaestroPedidos);
		
		
		tblMaestroPedidos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		    @Override
		    public void valueChanged(ListSelectionEvent event) {
		    	if( tblMaestroPedidos.getSelectedRows().length == 1 ) { 
		    		  
		    		  int filaSeleccionada = tblMaestroPedidos.getSelectedRow();
					  int idPedidoHistoria = Integer.parseInt(tblMaestroPedidos.getValueAt(filaSeleccionada, 0).toString());
					  DefaultTableModel modeloEstado = pintarHistoriaEstado(idPedidoHistoria);
					  tableEstadosPedido.setModel(modeloEstado);
		         }
		    }
		});
		
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
				int idPedidoAvanzar= Integer.parseInt(tblMaestroPedidos.getValueAt(filaSeleccionada, 0).toString());
				int idTipoPedido = Integer.parseInt(tblMaestroPedidos.getValueAt(filaSeleccionada, 6).toString());
				int idEstado = Integer.parseInt(tblMaestroPedidos.getValueAt(filaSeleccionada, 7).toString());
				PedidoCtrl pedCtrl = new PedidoCtrl();
				boolean esEstadoFinal = pedCtrl.esEstadoFinal(idTipoPedido, idEstado);
				if(esEstadoFinal)
				{
					JOptionPane.showMessageDialog(null, "El estado actual es un estado Final no se puede avanzar más" , "No hay estado posterior", JOptionPane.ERROR_MESSAGE);
					return;
				}
				else
				{
					VentCambioEstado cambioEstado = new VentCambioEstado(idPedidoAvanzar, false, true);
					cambioEstado.setVisible(true);
					pintarPedidos();
				}
				
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
				int idPedidoDevolver= Integer.parseInt(tblMaestroPedidos.getValueAt(filaSeleccionada, 0).toString());
				int idTipoPedido = Integer.parseInt(tblMaestroPedidos.getValueAt(filaSeleccionada, 6).toString());
				int idEstado = Integer.parseInt(tblMaestroPedidos.getValueAt(filaSeleccionada, 7).toString());
				PedidoCtrl pedCtrl = new PedidoCtrl();
				boolean esEstadoInicial = pedCtrl.esEstadoInicial(idTipoPedido, idEstado);
				if(esEstadoInicial)
				{
					JOptionPane.showMessageDialog(null, "El estado actual es un estado Inicial no se puede retroceder" , "No hay estado anterior", JOptionPane.ERROR_MESSAGE);
					return;
				}
				else
				{
					VentCambioEstado cambioEstado = new VentCambioEstado(idPedidoDevolver, true, false);
					cambioEstado.setVisible(true);
					pintarPedidos();
				}
				
			}
		});
		btnRetrocederEstado.setBounds(164, 334, 180, 23);
		contentPane.add(btnRetrocederEstado);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				pintarPedidos();
			}
		});
	}
	
	public void pintarPedidos()
	{
		Object[] columnsName = new Object[9];
        
        columnsName[0] = "Id Pedido Tienda";
        columnsName[1] = "Fecha Pedido";
        columnsName[2] = "Nombres";
        columnsName[3] = "Tipo Pedido";
        columnsName[4] = "Estado Pedido";
        columnsName[5] = "Dirección";
        columnsName[6] = "id Tipo Pedido";
        columnsName[7] = "idestado";
        columnsName[8] = "Tiempo";
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
        DefaultTableModel modelo = new DefaultTableModel(){
       	    public boolean isCellEditable(int rowIndex,int columnIndex){
       	    	return false;
       	    }
       	    
       	    
       	};
		modelo.setColumnIdentifiers(columnsName);
		for(int y = 0; y < pedidos.size();y++)
		{
			String[] fila =(String[]) pedidos.get(y);
			modelo.addRow(fila);
		}
		tblMaestroPedidos.setModel(modelo);
		tblMaestroPedidos.getColumnModel().getColumn(6).setMaxWidth(0);
		tblMaestroPedidos.getColumnModel().getColumn(6).setMinWidth(0);
		tblMaestroPedidos.getColumnModel().getColumn(7).setMaxWidth(0);
		tblMaestroPedidos.getColumnModel().getColumn(7).setMinWidth(0);
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(6).setMaxWidth(0);
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(6).setMinWidth(0);
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(7).setMaxWidth(0);
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(7).setMinWidth(0);
		setCellRender(tblMaestroPedidos);
		
	}
	
	public void setCellRender(JTable table) {
        Enumeration<TableColumn> en = table.getColumnModel().getColumns();
        while (en.hasMoreElements()) {
            TableColumn tc = en.nextElement();
            tc.setCellRenderer(new CellRenderTransaccional());
        }
    }
	
	public DefaultTableModel pintarHistoriaEstado(int idPedido)
	{
		Object[] columnsName = new Object[3];
        
        columnsName[0] = "Fecha Cambio Estado";
        columnsName[1] = "Estado Anterior";
        columnsName[2] = "Estado Posterior";
        PedidoCtrl pedCtrl = new  PedidoCtrl();
        ArrayList<Object> estadosPedido = new ArrayList();
        estadosPedido = pedCtrl.obtenerHistoriaEstadoPedido(idPedido);
        DefaultTableModel modelo = new DefaultTableModel();
		modelo.setColumnIdentifiers(columnsName);
		for(int y = 0; y < estadosPedido.size();y++)
		{
			String[] fila =(String[]) estadosPedido.get(y);
			modelo.addRow(fila);
		}
		return(modelo);
		
	}
}
