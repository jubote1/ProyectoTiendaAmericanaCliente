package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
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
import capaControlador.ReportesCtrl;
import capaModelo.Cliente;
import capaModelo.DetallePedido;
import capaModelo.FechaSistema;
import capaModelo.PedidoDescuento;
import capaModelo.TipoPedido;
import renderTable.CellRenderTransaccional;

import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class VentPedTransaccional extends JFrame implements Runnable{

	private JPanel contentPane;
	private JTable tblMaestroPedidos;
	private static int idTipoPedido = 0;
	private JTable tableEstadosPedido;
	private JTable tableDetallePedido;
	private String fechaSis;
	private PedidoCtrl pedCtrl = new PedidoCtrl();
	Thread h1;
	private JTextField txtFechaSistema;
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
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		this.setExtendedState(MAXIMIZED_BOTH);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelBotones = new JPanel();
		panelBotones.setBounds(10, 603, 978, 92);
		contentPane.add(panelBotones);
		panelBotones.setLayout(new GridLayout(0, 6, 0, 0));
		
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
					//Deberemos Rellenar las variables est�ticas de la pantalla VentPedTomarPedidos
					VentPedTomarPedidos ventTomaPedidos = new VentPedTomarPedidos();
					VentPedTomarPedidos.idPedido = idPedido;
					boolean tieneDescuento = pedCtrl.existePedidoDescuento(idPedido);
					if(tieneDescuento)
					{
						VentPedTomarPedidos.tieneDescuento = true;
						PedidoDescuento desPedido = pedCtrl.obtenerPedidoDescuento(idPedido);
						VentPedTomarPedidos.descuento = desPedido.getDescuentoPesos();
						
					}
					double totalBruto = pedCtrl.obtenerTotalBrutoPedido(idPedido);
					VentPedTomarPedidos.totalPedido = totalBruto;
					//Recuperamos una informaci�n bastante importante para reabrir el pedido como lo es el ArrayList de DetallePedido.
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
					//buscamos en el arreglo que posici�n tiene
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
					ventTomaPedidos.esReabierto = true;
					ventTomaPedidos.setVisible(true);
					dispose();
				}
			}
		});
		btnReabrirFactura.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelBotones.add(btnReabrirFactura);
		
		JButton btnReimpresion = new JButton("Reimprimir Factura");
		btnReimpresion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Acci�n para la generaci�n de reporte general de ventas
				int filaSeleccionada = tblMaestroPedidos.getSelectedRow();
				if(filaSeleccionada == -1)
				{
					JOptionPane.showMessageDialog(null, "Debe Seleccionar un pedido para Reimprimir la factura " , "No ha Seleccionado Pedido", JOptionPane.ERROR_MESSAGE);
					return;
				}
				else
				{
					int idPedido = Integer.parseInt(tblMaestroPedidos.getValueAt(filaSeleccionada, 0).toString());
					ReportesCtrl repCtrl = new ReportesCtrl();
					repCtrl.generarFactura(idPedido);
				}
			}
		});
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
		
		JButton btnTomarPedido = new JButton("Tomar Pedido");
		btnTomarPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentPedTomarPedidos ventPedido = new VentPedTomarPedidos();
				ventPedido.setVisible(true);
				dispose();
			}
		});
		btnTomarPedido.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelBotones.add(btnTomarPedido);
		
		JPanel panelFiltrosPedidos = new JPanel();
		panelFiltrosPedidos.setBounds(997, 11, 208, 280);
		contentPane.add(panelFiltrosPedidos);
		panelFiltrosPedidos.setLayout(new GridLayout(5, 1, 0, 0));
		
		JButton btnPuntoDeVenta = new JButton("Punto de venta");
		
		panelFiltrosPedidos.add(btnPuntoDeVenta);
		
		JButton btnDomicilio = new JButton("Domicilio");
		
		panelFiltrosPedidos.add(btnDomicilio);
		
		JButton btnParaLlevar = new JButton("Para Llevar");
		
		panelFiltrosPedidos.add(btnParaLlevar);
		
		JButton btnTotal = new JButton("Total Sin Pedidos Finalizados");
		
		JButton btnTotalConPedidos = new JButton("Total Con Pedidos Finalizados");
		btnTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				idTipoPedido = 0;
				pintarPedidos();
				btnTotal.setBackground(Color.YELLOW);
				btnParaLlevar.setBackground(null);
				btnDomicilio.setBackground(null);
				btnPuntoDeVenta.setBackground(null);
				btnTotalConPedidos.setBackground(null);
			}
		});
		
		btnTotalConPedidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Prendemos un indicador que buscar� traer todos los tipos de tipos pedidos sin importar el estado
				idTipoPedido = -1;
				pintarPedidos();
				btnTotalConPedidos.setBackground(Color.YELLOW);
				btnTotal.setBackground(null);
				btnParaLlevar.setBackground(null);
				btnDomicilio.setBackground(null);
				btnPuntoDeVenta.setBackground(null);
			}
		});
		panelFiltrosPedidos.add(btnTotalConPedidos);
		btnTotal.setBackground(Color.YELLOW);
		panelFiltrosPedidos.add(btnTotal);
		
		btnParaLlevar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				idTipoPedido = 3;
				pintarPedidos();
				btnTotal.setBackground(null);
				btnParaLlevar.setBackground(Color.YELLOW);
				btnDomicilio.setBackground(null);
				btnPuntoDeVenta.setBackground(null);
				btnTotalConPedidos.setBackground(null);
			}
		});
		
		btnDomicilio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				idTipoPedido = 1;
				pintarPedidos();
				btnTotal.setBackground(null);
				btnParaLlevar.setBackground(null);
				btnDomicilio.setBackground(Color.YELLOW);
				btnPuntoDeVenta.setBackground(null);
				btnTotalConPedidos.setBackground(null);
			}
		});
		
		btnPuntoDeVenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				idTipoPedido = 2;
				pintarPedidos();
				btnTotal.setBackground(null);
				btnParaLlevar.setBackground(null);
				btnDomicilio.setBackground(null);
				btnPuntoDeVenta.setBackground(Color.YELLOW);
				btnTotalConPedidos.setBackground(null);
			}
		});
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Calibri", Font.PLAIN, 15));
		tabbedPane.setBounds(10, 409, 978, 183);
		contentPane.add(tabbedPane);
		
		JPanel panelEstadosPedido = new JPanel();
		tabbedPane.addTab("Estado del pedido", null, panelEstadosPedido, null);
		panelEstadosPedido.setLayout(null);
		
		JScrollPane scrollPaneEstPedido = new JScrollPane();
		scrollPaneEstPedido.setBounds(86, 11, 779, 128);
		panelEstadosPedido.add(scrollPaneEstPedido);
		
		tableEstadosPedido = new JTable();
		scrollPaneEstPedido.setViewportView(tableEstadosPedido);
		
		JPanel panelDetallePedido = new JPanel();
		tabbedPane.addTab("Detalle del pedido", null, panelDetallePedido, null);
		panelDetallePedido.setLayout(null);
		
		JScrollPane scrollDetallePedido = new JScrollPane();
		scrollDetallePedido.setBounds(25, 11, 805, 105);
		panelDetallePedido.add(scrollDetallePedido);
		
		tableDetallePedido = new JTable();
		scrollDetallePedido.setViewportView(tableDetallePedido);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 978, 314);
		contentPane.add(scrollPane);
		
		tblMaestroPedidos =   new JTable();
		//le cambiamos la fuente al jtable de pedidos para hacerla m�s grande y visible
		tblMaestroPedidos.setFont(new java.awt.Font("Tahoma", 0, 14)); 
		//Aumentamos el tama�o de las celdas para que quede m�s amplia la informaci�n
		tblMaestroPedidos.setRowHeight(25);
		tblMaestroPedidos.setEnabled(true);
		pintarPedidos();
		scrollPane.setViewportView(tblMaestroPedidos);
		
		
		tblMaestroPedidos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		    @Override
		    public void valueChanged(ListSelectionEvent event) {
		    	if( tblMaestroPedidos.getSelectedRows().length == 1 ) { 
		    		  
		    		  int filaSeleccionada = tblMaestroPedidos.getSelectedRow();
					  int idPedidoHistoria = Integer.parseInt(tblMaestroPedidos.getValueAt(filaSeleccionada, 0).toString());
					  pintarHistoriaEstado(idPedidoHistoria);
					  pintarDetallePedido(idPedidoHistoria);
		         }
		    }
		});
		
		//Manejamos el evento de cuando damos doble clic sobre el pedido para avanzar de estado o cuando damos 3 veces para retroceder de estado
		tblMaestroPedidos.addMouseListener(new java.awt.event.MouseAdapter() {
		      public void mouseClicked(java.awt.event.MouseEvent e) {
		      if(e.getClickCount()==2){
		    	  avanzarEstado();
		        }
		      if(e.getClickCount()==3){
		    	  devolverEstado();
		       }
		 }
		});
		
		JButton btnAvanzarEstado = new JButton("Avanzar Estado");
		btnAvanzarEstado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				avanzarEstado();
			}
		});
		btnAvanzarEstado.setBounds(517, 336, 180, 47);
		contentPane.add(btnAvanzarEstado);
		
		JButton btnRetrocederEstado = new JButton("Devolver Estado");
		btnRetrocederEstado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				devolverEstado();
				
			}
		});
		btnRetrocederEstado.setBounds(302, 336, 180, 49);
		contentPane.add(btnRetrocederEstado);
		
		JLabel lblFechaSistema = new JLabel("FECHA SISTEMA");
		lblFechaSistema.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblFechaSistema.setBounds(20, 343, 139, 40);
		contentPane.add(lblFechaSistema);
		
		FechaSistema fecha = pedCtrl.obtenerFechasSistema();
		fechaSis = fecha.getFechaApertura();
		txtFechaSistema = new JTextField();
		txtFechaSistema.setForeground(Color.RED);
		txtFechaSistema.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtFechaSistema.setEditable(false);
		txtFechaSistema.setBounds(169, 349, 123, 34);
		txtFechaSistema.setText(fechaSis);
		contentPane.add(txtFechaSistema);
		txtFechaSistema.setColumns(10);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				pintarPedidos();
			}
		});
		//h1 = new Thread(this);
		//h1.start();
	}
	
	public void pintarPedidos()
	{
		Object[] columnsName = new Object[9];
        
        columnsName[0] = "Id Pedido Tienda";
        columnsName[1] = "Fecha Pedido";
        columnsName[2] = "Nombres";
        columnsName[3] = "Tipo Pedido";
        columnsName[4] = "Estado Pedido";
        columnsName[5] = "Direcci�n";
        columnsName[6] = "id Tipo Pedido";
        columnsName[7] = "idestado";
        columnsName[8] = "Tiempo";
        ArrayList<Object> pedidos = new ArrayList();
        if(idTipoPedido == -1)
        {
        	//Se invoca de la capa Controladora la forma de observar los pedidos por un total
        	pedidos = pedCtrl.obtenerPedidosTableConFinales();
        }else if(idTipoPedido == 0)
        {
        	//Se invoca de la capa Controladora la forma de observar los pedidos por un total
        	pedidos = pedCtrl.obtenerPedidosTableSinFinales();
        }
        else if(idTipoPedido > 0)
        {
        	//Se invoca de la capa controladora la forma de ver los pedidos por tipo
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
		tblMaestroPedidos.getColumnModel().getColumn(0).setMaxWidth(70);
		tblMaestroPedidos.getColumnModel().getColumn(0).setMinWidth(70);
		//Ocultamos la conlumna de FechaPedido, dado que es la misma dependiendo del d�a aperturado
		tblMaestroPedidos.getColumnModel().getColumn(1).setMaxWidth(0);
		tblMaestroPedidos.getColumnModel().getColumn(1).setMinWidth(0);
		//Modificamos el ancho de la columna nombre
		tblMaestroPedidos.getColumnModel().getColumn(2).setMinWidth(170);
		tblMaestroPedidos.getColumnModel().getColumn(2).setMaxWidth(170);
		//Modificamos ancho del tipo de pedido
		tblMaestroPedidos.getColumnModel().getColumn(3).setMinWidth(110);
		tblMaestroPedidos.getColumnModel().getColumn(3).setMaxWidth(110);
		//Modificamos ancho del estado pedido
		tblMaestroPedidos.getColumnModel().getColumn(4).setMinWidth(110);
		tblMaestroPedidos.getColumnModel().getColumn(4).setMaxWidth(110);
		tblMaestroPedidos.getColumnModel().getColumn(6).setMaxWidth(0);
		tblMaestroPedidos.getColumnModel().getColumn(6).setMinWidth(0);
		tblMaestroPedidos.getColumnModel().getColumn(7).setMaxWidth(0);
		tblMaestroPedidos.getColumnModel().getColumn(7).setMinWidth(0);
		//Modificamos el ancho del la muestra del tiempo
		tblMaestroPedidos.getColumnModel().getColumn(8).setMinWidth(180);
		tblMaestroPedidos.getColumnModel().getColumn(8).setMaxWidth(180);
		
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(70);
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(0).setMinWidth(70);
		//Ocultamos la conlumna de FechaPedido, dado que es la misma dependiendo del d�a aperturado
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(0);
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(1).setMinWidth(0);
		//Modificamos el ancho de la columna nombre
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(2).setMinWidth(170);
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(170);
		//Modificamos ancho del tipo de pedido
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(3).setMinWidth(110);
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(110);
		//Modificamos ancho del estado pedido
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(4).setMinWidth(110);
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(4).setMaxWidth(110);
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(6).setMaxWidth(0);
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(6).setMinWidth(0);
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(7).setMaxWidth(0);	
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(7).setMinWidth(0);
		//Modificamos el ancho del la muestra del tiempo
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(8).setMinWidth(180);
		tblMaestroPedidos.getTableHeader().getColumnModel().getColumn(8).setMaxWidth(180);
		setCellRender(tblMaestroPedidos);
		
	}
	
	public void setCellRender(JTable table) {
        Enumeration<TableColumn> en = table.getColumnModel().getColumns();
        while (en.hasMoreElements()) {
            TableColumn tc = en.nextElement();
            tc.setCellRenderer(new CellRenderTransaccional());
        }
    }
	
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
	
	
	public void run(){
		 Thread ct = Thread.currentThread();
		 while(ct == h1) 
		 {   
			 //Ejecutamos el pintado de los pedidos en el JTable de la pantalla.
			 pintarPedidos();
		  //Realizamos la ejecuci�n cada 30 segundos
			 try {
				 	Thread.sleep(30000);
			 }catch(InterruptedException e) 
			 {}
		 }
	}
	
	/**
	 * M�todo encargado de la accion de devolver estado para un pedido seleccionado
	 */
	public void devolverEstado()
	{
		int filaSeleccionada = tblMaestroPedidos.getSelectedRow();
		if(filaSeleccionada == -1)
		{
			JOptionPane.showMessageDialog(null, "Debe Seleccionar alg�n pedido para Devolver Estado " , "No ha Seleccionado Pedido", JOptionPane.ERROR_MESSAGE);
			return;
		}
		// Se captura el valor del idDetalle que se desea eliminar
		int idPedidoDevolver= Integer.parseInt(tblMaestroPedidos.getValueAt(filaSeleccionada, 0).toString());
		int idTipoPedido = Integer.parseInt(tblMaestroPedidos.getValueAt(filaSeleccionada, 6).toString());
		int idEstado = Integer.parseInt(tblMaestroPedidos.getValueAt(filaSeleccionada, 7).toString());
		boolean esEstadoInicial = pedCtrl.esEstadoInicial(idTipoPedido, idEstado);
		if(esEstadoInicial)
		{
			JOptionPane.showMessageDialog(null, "El estado actual es un estado Inicial no se puede retroceder" , "No hay estado anterior", JOptionPane.ERROR_MESSAGE);
			return;
		}
		else
		{
			VentPedCambioEstado cambioEstado = new VentPedCambioEstado(idPedidoDevolver, true, false, null, true);
			cambioEstado.setVisible(true);
			pintarPedidos();
		}
	}
	
	/**
	 * M�todo encargado de desplegar la accion con nueva ventana incluida para la toma de nuevos pedidos
	 */
	public void avanzarEstado()
	{
		int filaSeleccionada = tblMaestroPedidos.getSelectedRow();
		if(filaSeleccionada == -1)
		{
			JOptionPane.showMessageDialog(null, "Debe Seleccionar alg�n pedido para Avanzar Estado " , "No ha Seleccionado Pedido", JOptionPane.ERROR_MESSAGE);
			return;
		}
		// Se captura el valor del idDetalle que se desea eliminar
		int idPedidoAvanzar= Integer.parseInt(tblMaestroPedidos.getValueAt(filaSeleccionada, 0).toString());
		int idTipoPedido = Integer.parseInt(tblMaestroPedidos.getValueAt(filaSeleccionada, 6).toString());
		int idEstado = Integer.parseInt(tblMaestroPedidos.getValueAt(filaSeleccionada, 7).toString());
		boolean esEstadoFinal = pedCtrl.esEstadoFinal(idTipoPedido, idEstado);
		if(esEstadoFinal)
		{
			JOptionPane.showMessageDialog(null, "El estado actual es un estado Final no se puede avanzar m�s" , "No hay estado posterior", JOptionPane.ERROR_MESSAGE);
			return;
		}
		else
		{
			VentPedCambioEstado cambioEstado = new VentPedCambioEstado(idPedidoAvanzar, false, true, null, true);
			cambioEstado.setVisible(true);
			pintarPedidos();
		}
	}
}
