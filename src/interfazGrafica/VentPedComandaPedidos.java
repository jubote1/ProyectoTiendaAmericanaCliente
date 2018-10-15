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

import capaControlador.EmpleadoCtrl;
import capaControlador.PedidoCtrl;
import capaControlador.ReportesCtrl;
import capaModelo.Cliente;
import capaModelo.FechaSistema;
import capaModelo.PedidoDescuento;
import capaModelo.TipoPedido;
import renderTable.CellRenderTransaccional;

import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class VentPedComandaPedidos extends JFrame implements Runnable{

	private JPanel contentPane;
	private JTable tblMaestroPedidos;
	private static int idTipoPedido = 0;
	private static int idUsuario = 0;
	Thread h1;
	private JTextField txtFechaSistema;
	private String fechaSis;
	private PedidoCtrl pedCtrl = new PedidoCtrl();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentPedComandaPedidos frame = new VentPedComandaPedidos();
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
	public VentPedComandaPedidos() {
		setTitle("COMANDA PEDIDOS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1014, 700);
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		this.setExtendedState(MAXIMIZED_BOTH);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelFiltrosPedidos = new JPanel();
		panelFiltrosPedidos.setBounds(1087, 11, 208, 295);
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
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 1051, 616);
		contentPane.add(scrollPane);
		
		tblMaestroPedidos =   new JTable();
		//le cambiamos la fuente al jtable de pedidos para hacerla más grande y visible
		tblMaestroPedidos.setFont(new java.awt.Font("Tahoma", 0, 14)); 
		//Aumentamos el tamaño de las celdas para que quede más amplia la información
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
					  DefaultTableModel modeloEstado = pintarHistoriaEstado(idPedidoHistoria);
					 
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
		btnAvanzarEstado.setBounds(559, 656, 180, 44);
		btnAvanzarEstado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				avanzarEstado();
			}
		});
		contentPane.add(btnAvanzarEstado);
		
		JButton btnRetrocederEstado = new JButton("Devolver Estado");
		btnRetrocederEstado.setBounds(298, 654, 180, 46);
		btnRetrocederEstado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				devolverEstado();
				
			}
		});
		contentPane.add(btnRetrocederEstado);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.setBounds(1101, 656, 195, 44);
		contentPane.add(btnSalir);
		btnSalir.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JLabel lblFechaSistema = new JLabel("FECHA SISTEMA");
		lblFechaSistema.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblFechaSistema.setBounds(10, 656, 133, 29);
		contentPane.add(lblFechaSistema);
		
		txtFechaSistema = new JTextField();
		FechaSistema fecha = pedCtrl.obtenerFechasSistema();
		fechaSis = fecha.getFechaApertura();
		txtFechaSistema.setForeground(Color.RED);
		txtFechaSistema.setEditable(false);
		txtFechaSistema.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtFechaSistema.setBounds(157, 656, 120, 29);
		contentPane.add(txtFechaSistema);
		txtFechaSistema.setColumns(10);
		txtFechaSistema.setText(fechaSis);
		
		JButton btnNewButton = new JButton("<html><center>Ver Ubicaci\u00F3n Domicilios</center></html>");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			   
			}
		});
		btnNewButton.setBounds(840, 656, 164, 44);
		contentPane.add(btnNewButton);
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentPrincipal ventPrincipal = new VentPrincipal();
				ventPrincipal.setVisible(true);
				dispose();
			}
		});
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				pintarPedidos();
			}
		});
		
		//Para este punto de la ventana de pedidos realizaremos una validación de si el usuario esta logueado o no en cuyo caso no sea así 
		//la aplicación nos sacara
		validarLogueo();
		//Luego de pasada la validación del logueo realizamos el cargue de la página
		h1 = new Thread(this);
		h1.start();
	}
	
	public void validarLogueo()
	{
		idUsuario = PrincipalLogueo.idUsuario;
		if(idUsuario == 0)
		{
			JOptionPane.showMessageDialog(null, "Aparentemente el usuario no está logueado en el sistema esta ventana se cerrará " , "Ingreso no permitido", JOptionPane.ERROR_MESSAGE);
			dispose();
		}
		//Luego realizamos la validación de que dicho idUsuario si exista.
		EmpleadoCtrl empCtrl = new EmpleadoCtrl();
		boolean validacion = empCtrl.validarExistenciaEmpleado(idUsuario);
		if(!validacion)
		{
			JOptionPane.showMessageDialog(null, "El usuario no existe en base de datos" , "Usuario no existe", JOptionPane.ERROR_MESSAGE);
			dispose();
		}
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
        ArrayList<Object> pedidos = new ArrayList();
        if(idTipoPedido == 0)
        {
        	pedidos = pedCtrl.obtenerPedidosVentanaComanda(idUsuario);
        }
        else
        {
        	pedidos = pedCtrl.obtenerPedidosVentanaComandaTipPed(idUsuario, idTipoPedido);
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
		//Ocultamos la conlumna de FechaPedido, dado que es la misma dependiendo del día aperturado
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
		//Ocultamos la conlumna de FechaPedido, dado que es la misma dependiendo del día aperturado
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
	
	public DefaultTableModel pintarHistoriaEstado(int idPedido)
	{
		Object[] columnsName = new Object[3];
        
        columnsName[0] = "Fecha Cambio Estado";
        columnsName[1] = "Estado Anterior";
        columnsName[2] = "Estado Posterior";
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
	
	public void run(){
		 Thread ct = Thread.currentThread();
		 while(ct == h1) 
		 {   
			 //Ejecutamos el pintado de los pedidos en el JTable de la pantalla.
			 pintarPedidos();
		  //Realizamos la ejecución cada 30 segundos
			 try {
				 	Thread.sleep(30000);
			 }catch(InterruptedException e) 
			 {}
		 }
	}
	
	public void avanzarEstado()
	{
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
		boolean esEstadoFinal = pedCtrl.esEstadoFinal(idTipoPedido, idEstado);
		if(esEstadoFinal)
		{
			JOptionPane.showMessageDialog(null, "El estado actual es un estado Final no se puede avanzar más" , "No hay estado posterior", JOptionPane.ERROR_MESSAGE);
			return;
		}
		else
		{
			VentPedCambioEstado cambioEstado = new VentPedCambioEstado(idPedidoAvanzar, false, true, null, true);
			cambioEstado.setVisible(true);
			pintarPedidos();
		}
		
	}
	
	public void devolverEstado()
	{
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
}
