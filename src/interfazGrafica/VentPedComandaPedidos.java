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
import java.awt.Window;
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

import JTable.CellRenderTransaccional;
import capaControlador.EmpleadoCtrl;
import capaControlador.ReportesCtrl;
import capaControlador.PedidoCtrl;
import capaModelo.Cliente;
import capaModelo.FechaSistema;
import capaModelo.PedidoDescuento;
import capaModelo.TipoPedido;
import capaModelo.Usuario;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

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
	JLabel lblTipoFiltro;
	JLabel lblTipoEmpleado;
	JLabel lblEmpleado;
	private String fechaSis;
	private PedidoCtrl pedCtrl = new PedidoCtrl(PrincipalLogueo.habilitaAuditoria);
	private EmpleadoCtrl empCtrl = new EmpleadoCtrl(PrincipalLogueo.habilitaAuditoria);
	ArrayList<Usuario> domiciliarios;
	ArrayList<JButton> botDom  = new ArrayList();
	//Fijamos el tipo de empleado
	int idTipoEmpleado;
	//Prendemos el indicador si es un domiciliario
	boolean esDomiciliario;
	//Para esta pantalla es vital saber que usuario esta logueado en el sistema
	Usuario usuarioPantalla;
	//Variable booleana para saber si se está consultando un domiciliario
	boolean consDomi = false;
	int idDomiCon = 0;
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


	//Creamos constructor principal del JFrame de Comanda pedidos
	public VentPedComandaPedidos() {
		//Cuadramos la presentación del JFrame
		setTitle("COMANDA PEDIDOS");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1014, 700);
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		this.setExtendedState(MAXIMIZED_BOTH);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		lblTipoFiltro = new JLabel("");
		lblTipoEmpleado = new JLabel("");
		lblEmpleado = new JLabel("");
		//Igualamos el usuario del sistema
		usuarioPantalla = empCtrl.obtenerEmpleado(Sesion.getIdUsuario());
		//Obtenemos el tipo de empleado
		idTipoEmpleado = usuarioPantalla.getidTipoEmpleado();
		//Deberemos si el tipo de empleado es domicilio  y en caso de que lo sea
		esDomiciliario = empCtrl.esDomicilario(idTipoEmpleado);
		//En caso de que sea domiciliario o este consultando domiciliario fijamos el idDomiciliario
		if(esDomiciliario)
		{
			idDomiCon = Sesion.getIdUsuario();
			consDomi = true;
		}
		lblTipoFiltro.setText("EMPLEADO");
		lblEmpleado.setText(usuarioPantalla.getNombreLargo());
		//En caso de que sea domiciliario lo debemos de tener en cuenta para que muestre su vista
		
		JPanel panelFiltrosPedidos = new JPanel();
		panelFiltrosPedidos.setBounds(831, 11, 167, 189);
		contentPane.add(panelFiltrosPedidos);
		panelFiltrosPedidos.setLayout(new GridLayout(4, 1, 0, 0));
		
		JButton btnPuntoDeVenta = new JButton("Punto de venta");
		
		panelFiltrosPedidos.add(btnPuntoDeVenta);
		
		JButton btnDomicilio = new JButton("Domicilio");
		
		panelFiltrosPedidos.add(btnDomicilio);
		
		JButton btnParaLlevar = new JButton("Para Llevar");
		
		panelFiltrosPedidos.add(btnParaLlevar);
		
		JButton btnTotal = new JButton("Todos los Pedidos");
		btnTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				idTipoPedido = 0;
				consDomi = false;
				pintarPedidos();
				btnTotal.setBackground(Color.YELLOW);
				btnParaLlevar.setBackground(null);
				btnDomicilio.setBackground(null);
				btnPuntoDeVenta.setBackground(null);
				//Quitamos el color al filtro por domiciliario
				for(int i = 0; i < botDom.size(); i++)
				{
					//Quitamos el backgroung del botón 
					botDom.get(i).setBackground(null);
				}
				lblTipoFiltro.setText(((JButton)arg0.getSource()).getText());
				lblEmpleado.setText("");
			}
		});
		//btnTotal.setBackground(Color.YELLOW);
		panelFiltrosPedidos.add(btnTotal);
		
		btnParaLlevar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				idTipoPedido = 3;
				consDomi = false;
				pintarPedidos();
				btnTotal.setBackground(null);
				btnParaLlevar.setBackground(Color.YELLOW);
				btnDomicilio.setBackground(null);
				btnPuntoDeVenta.setBackground(null);
				//Quitamos el color al filtro por domiciliario
				for(int i = 0; i < botDom.size(); i++)
				{
					//Quitamos el backgroung del botón 
					botDom.get(i).setBackground(null);
				}
				lblTipoFiltro.setText(((JButton)e.getSource()).getText());
				lblEmpleado.setText("");
			}
		});
		
		btnDomicilio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				idTipoPedido = 1;
				consDomi = false;
				pintarPedidos();
				btnTotal.setBackground(null);
				btnParaLlevar.setBackground(null);
				btnDomicilio.setBackground(Color.YELLOW);
				btnPuntoDeVenta.setBackground(null);
				//Quitamos el color al filtro por domiciliario
				for(int i = 0; i < botDom.size(); i++)
				{
					//Quitamos el backgroung del botón 
					botDom.get(i).setBackground(null);
				}
				lblTipoFiltro.setText(((JButton)e.getSource()).getText());
				lblEmpleado.setText("");
			}
		});
		
		btnPuntoDeVenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				idTipoPedido = 2;
				consDomi = false;
				pintarPedidos();
				btnTotal.setBackground(null);
				btnParaLlevar.setBackground(null);
				btnDomicilio.setBackground(null);
				btnPuntoDeVenta.setBackground(Color.YELLOW);
				//Quitamos el color al filtro por domiciliario
				for(int i = 0; i < botDom.size(); i++)
				{
					//Quitamos el backgroung del botón 
					botDom.get(i).setBackground(null);
				}
				lblTipoFiltro.setText(((JButton)e.getSource()).getText());
				lblEmpleado.setText("");
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 50, 811, 540);
		contentPane.add(scrollPane);
		
		tblMaestroPedidos =   new JTable();
		//le cambiamos la fuente al jtable de pedidos para hacerla más grande y visible
		tblMaestroPedidos.setFont(new java.awt.Font("Tahoma", 0, 14)); 
		//Aumentamos el tamaño de las celdas para que quede más amplia la información
		tblMaestroPedidos.setRowHeight(25);
		tblMaestroPedidos.setEnabled(true);
		//Se realiza el pintar pedidos con lo que se tiene inicialmente
		pintarPedidos();
		scrollPane.setViewportView(tblMaestroPedidos);
		
		
		//Manejamos el evento de cuando damos doble clic sobre el pedido para avanzar de estado o cuando damos 3 veces para retroceder de estado
		tblMaestroPedidos.addMouseListener(new java.awt.event.MouseAdapter() {
		      public void mouseClicked(java.awt.event.MouseEvent e) {
		    	  Window ventanaPadre = SwingUtilities.getWindowAncestor(
	                        (Component) e.getSource());
		      if(e.getClickCount()==2){
		    	  avanzarEstado((JFrame) ventanaPadre);
		        }
		 }
		});
		
		JButton btnAvanzarEstado = new JButton("Avanzar Estado");
		btnAvanzarEstado.setBounds(467, 602, 180, 44);
		btnAvanzarEstado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Window ventanaPadre = SwingUtilities.getWindowAncestor(
                        (Component) e.getSource());
				avanzarEstado((JFrame) ventanaPadre);
			}
		});
		contentPane.add(btnAvanzarEstado);
		
		JButton btnRetrocederEstado = new JButton("Devolver Estado");
		btnRetrocederEstado.setBounds(277, 601, 180, 46);
		btnRetrocederEstado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Window ventanaPadre = SwingUtilities.getWindowAncestor(
                        (Component) e.getSource());
				devolverEstado((JFrame) ventanaPadre);
				
			}
		});
		contentPane.add(btnRetrocederEstado);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.setBounds(826, 602, 172, 44);
		contentPane.add(btnSalir);
		btnSalir.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JLabel lblFechaSistema = new JLabel("FECHA SISTEMA");
		lblFechaSistema.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblFechaSistema.setBounds(10, 616, 133, 29);
		contentPane.add(lblFechaSistema);
		
		txtFechaSistema = new JTextField();
		FechaSistema fecha = pedCtrl.obtenerFechasSistema();
		fechaSis = fecha.getFechaApertura();
		txtFechaSistema.setForeground(Color.RED);
		txtFechaSistema.setEditable(false);
		txtFechaSistema.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtFechaSistema.setBounds(147, 616, 120, 29);
		contentPane.add(txtFechaSistema);
		txtFechaSistema.setColumns(10);
		txtFechaSistema.setText(fechaSis);
		
		JButton btnVerDomicilios = new JButton("<html><center>Ver Ubicaci\u00F3n Domicilios</center></html>");
		btnVerDomicilios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final WebEngine webEngine = new WebEngine(getClass().getResource("googlemap.html").toString());
				final WebView webView = new WebView();
				
			}
		});
		btnVerDomicilios.setBounds(657, 601, 164, 44);
		contentPane.add(btnVerDomicilios);
		
		JPanel panelFiltroDom = new JPanel();
		panelFiltroDom.setBounds(831, 211, 167, 379);
		contentPane.add(panelFiltroDom);
		panelFiltroDom.setLayout(new GridLayout(10, 0, 0, 0));
		
		JButton btnQuitarFiltros = new JButton("Quitar Filtros");
		btnQuitarFiltros.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//En este método realizaremos el reinicio de los filtros del sistema,cuando se inicia la pantalla que es por el usuario logueado
				//Quitamos posible color Amarillo a los botones
				btnTotal.setBackground(null);
				btnParaLlevar.setBackground(null);
				btnDomicilio.setBackground(null);
				btnPuntoDeVenta.setBackground(null);
				//Quitamos el color al filtro por domiciliario
				for(int i = 0; i < botDom.size(); i++)
				{
					//Quitamos el backgroung del botón 
					botDom.get(i).setBackground(null);
				}
				//Igualamos el usuario del sistema
				usuarioPantalla = empCtrl.obtenerEmpleado(Sesion.getIdUsuario());
				//Obtenemos el tipo de empleado
				idTipoEmpleado = usuarioPantalla.getidTipoEmpleado();
				//Deberemos si el tipo de empleado es domicilio  y en caso de que lo sea
				esDomiciliario = empCtrl.esDomicilario(idTipoEmpleado);
				//En caso de que sea domiciliario o este consultando domiciliario fijamos el idDomiciliario
				if(esDomiciliario)
				{
					idDomiCon = Sesion.getIdUsuario();
				}
				//En caso de que sea domiciliario lo debemos de tener en cuenta para que muestre su vista
				lblTipoFiltro.setText("EMPLEADO");
				lblEmpleado.setText(usuarioPantalla.getNombreLargo());
				pintarPedidos();
			}
		});
		btnQuitarFiltros.setBounds(667, 11, 154, 28);
		contentPane.add(btnQuitarFiltros);
		
		
		lblTipoFiltro.setForeground(new Color(0, 0, 0));
		lblTipoFiltro.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblTipoFiltro.setBounds(148, 11, 172, 28);
		contentPane.add(lblTipoFiltro);
		
		JLabel lblFiltroPantalla = new JLabel("FILTRO PANTALLA:");
		lblFiltroPantalla.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblFiltroPantalla.setBounds(10, 11, 133, 28);
		contentPane.add(lblFiltroPantalla);
		
		lblEmpleado.setForeground(Color.BLACK);
		lblEmpleado.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEmpleado.setBounds(345, 11, 172, 28);
		contentPane.add(lblEmpleado);
		
		
		lblTipoEmpleado.setForeground(Color.BLACK);
		lblTipoEmpleado.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTipoEmpleado.setBounds(520, 11, 146, 28);
		contentPane.add(lblTipoEmpleado);
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				VentPrincipal ventPrincipal = new VentPrincipal();
				ventPrincipal.setVisible(true);
				
			}
		});
		
		
		
		//Vamos  a adicionar los domiciliarios del sistema
		domiciliarios = empCtrl.obtenerDomiciliarios();
		//Definimos las variables necesarias
		Usuario emp;
		JButton boton;
		//Recorremos los arreglo de los domiciliarios
		for(int i = 0; i < domiciliarios.size();i++)
		{
			//Llevamos el valor del domiciliario de turno a unavariable temporal
			emp = domiciliarios.get(i);
			//Creamos el botón
			boton = new JButton(emp.getNombreLargo());
			boton.setActionCommand(Integer.toString(emp.getIdUsuario()));
			//Adicionammos el botón al panel
			panelFiltroDom.add(boton);
			botDom.add(boton);
			boton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) 
				{
					//Extraemos el botón de la acción
					JButton botAccion = ((JButton)arg0.getSource());
					//Extraemos la acción del botón
					String actCom =  botAccion.getActionCommand();
					//Tomamos el color de fondo del botón
					Color colorBut = botAccion.getBackground();
					//Si el color es amarillo no debemos hacer nada
					if(colorBut.equals(Color.YELLOW))
					{
						
					}
					else
					{
						//Debemos volver a la normalidad los botones quitarles el background
						for(int i = 0; i < botDom.size(); i++)
						{
							//Quitamos el backgroung del botón 
							botDom.get(i).setBackground(null);
						}
						botAccion.setBackground(Color.YELLOW);
						//Prendemos el indicador de que es una consulta por domiciliario
						consDomi = true;
						//Debemos de ejecutar la consulta para el domiciliario en cuestión
						//guardamos en la variable el idUsuario del domiciliario
						idDomiCon = Integer.parseInt(actCom);
						//Llamamos método para pintar pedidos
						pintarPedidos();
						//Quitamos el color al filtro por tipo de pedido
						btnTotal.setBackground(null);
						btnParaLlevar.setBackground(null);
						btnDomicilio.setBackground(null);
						btnPuntoDeVenta.setBackground(null);
					}
					lblTipoFiltro.setText(((JButton)arg0.getSource()).getText());
					lblEmpleado.setText("");
				}
			});
		}
		
		//Despues de agregada la anterior información de los botones con los domiciliarios, verificamos si el empleado ingresado
		//es domiciliario, y en caso de que lo sea mostramos su vista, si no es así, mostramos la vista asociada al empleado
		if(esDomiciliario)
		{
			
		}
		
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
		idUsuario = Sesion.getIdUsuario();
		if(idUsuario == 0)
		{
			JOptionPane.showMessageDialog(null, "Aparentemente el usuario no está logueado en el sistema esta ventana se cerrará " , "Ingreso no permitido", JOptionPane.ERROR_MESSAGE);
			dispose();
		}
		//Luego realizamos la validación de que dicho idUsuario si exista.
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
        //La lógica de pintar pedidos debemos de dividirla en varios items
        // Hay filtros de tipo pedido, en cuyo caso se cumple condición para que el sistema filtre por ellos
        if(idTipoPedido >0)
        {
        	pedidos = pedCtrl.obtenerPedidosVentanaComandaTipPed(0, idTipoPedido);
        }
        // Hay filtros por domiciliario, esto significa que emepleado no es necesario domiciliario pero se debe ver 
        // lo qeu ve un domiciliario, para lo cual validamos si se está consultando un domiciliario
        if(consDomi)
        {
        	pedidos = pedCtrl.obtenerPedidosVentanaComandaDom(idDomiCon);
        }
        //La otra combinación de situaciones es que no se tenga filtro de tipo pedido y no filtre por domiciliario osea son todos los pedidos
        if((idTipoPedido == 0)&&(!consDomi))
        {
        	pedidos = pedCtrl.obtenerPedidosVentanaComanda(0);
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
	
	public void avanzarEstado(JFrame padre)
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
			VentPedCambioEstado cambioEstado = new VentPedCambioEstado(idPedidoAvanzar, false, true, padre, true, idDomiCon);
			cambioEstado.setVisible(true);
			pintarPedidos();
		}
		
	}
	
	public void devolverEstado(JFrame padre)
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
			VentPedCambioEstado cambioEstado = new VentPedCambioEstado(idPedidoDevolver, true, false, padre, true,idDomiCon);
			cambioEstado.setVisible(true);
			pintarPedidos();
		}
	}
}
