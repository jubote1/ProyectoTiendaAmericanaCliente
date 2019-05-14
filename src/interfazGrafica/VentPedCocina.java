package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import JTable.CellRenderTransaccional2;
import capaControlador.AutenticacionCtrl;
import capaControlador.ParametrosCtrl;
import capaControlador.PedidoCtrl;
import capaModelo.Parametro;
import ds.desktop.notify.DesktopNotify;

import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Window;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class VentPedCocina extends JFrame implements Runnable {
	//Está será una primera versión de la pantalla
	//Definimos el idEstado que identifica cuando los pedidos están en la cocina
	final int cocinaDomicilio = 2;
	final Color colorDomicilio = Color.GREEN;
	final int cocinaPuntoVenta = 9;
	final Color colorPuntoVenta = Color.gray;
	final int cocinaLlevar = 13;
	final Color colorLlevar = Color.magenta;
	
	private JPanel contentPane;
	public JTable tableMontadorTodos;
	public JTable tablePuntoVenta;
	public JTable tableDomicilio;
	JScrollPane scrollPanePedPintar;
	JPanel panelPintar;
	private int oldVPos = 0;
    private int oldHPos = 0;
	JPanel panelInfPizzero;
	private PedidoCtrl pedCtrl = new PedidoCtrl(PrincipalLogueo.habilitaAuditoria);
	private ParametrosCtrl parCtrl = new ParametrosCtrl(PrincipalLogueo.habilitaAuditoria);
	private AutenticacionCtrl autCtrl = new AutenticacionCtrl(PrincipalLogueo.habilitaAuditoria);
	int valPresentacion;
	private VentPedCocina  ventPedidoCocina;
	//Variable idPedido que almacenará el pedido seleccionado en el momento
	int idPedido = 0;
	int idEstado = 0;
	int idTipoPedido = 0;
	//Definimos el hilo que hará la actualizacion
	Thread hiloActualizacion;
	//Variables para almacenar la información de los pedidos
    ArrayList<Object> pedidos = new ArrayList();
    ArrayList<Integer> todosEstados = new ArrayList();
    //Variable que va a controlar los itemsPedido que tiene un pedido seleccionado en un momento dado
    int itemsPintar = 0;
    //Variable en la que almacenaremos los botones que indicarán si ya se elaboró el item de pedido correspondiente
    ArrayList<JButton> botItemsPedido = new ArrayList();
    //Botón base para agregar los botones que controlaran la elaboración de los itemspedido
    JButton btnItemPedido = new JButton();
    //Variable de JTabbePane del montador de pedidos
    JTabbedPane tabbedPaneMontador;
    //Variable del Panel donde pintamos los próximos pedidos
    JScrollPane scrollPaneInfPizzero;
    //Variable booleana que marca un cambio de pedido
    boolean cambioPedido = false;
    Object[] columnsName;
    boolean[] editarCampos = {false, false, false, false, false, false};
    DefaultTableModel modelo;
    private JTextField txtNumPedidoSel;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentPedCocina frame = new VentPedCocina();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//Estos métodos de AdjustmentListener se encargan de repintar la pantalla al momento de mover el scroll bar
	 AdjustmentListener adjustmentListener = new AdjustmentListener() {
		 
	        @Override
	        public void adjustmentValueChanged(AdjustmentEvent e) {
	            int vPos = scrollPanePedPintar.getVerticalScrollBar().getValue();
	                
	 
	            if (e.getSource().equals(scrollPanePedPintar.getVerticalScrollBar()) 
	                    && vPos != oldVPos) {
	                oldVPos = vPos;
	                repaint();
	                
	            }
	        }
	    };
	    
	    
	    AdjustmentListener adjustmentListenerProxPedidos = new AdjustmentListener() {
			 
	        @Override
	        public void adjustmentValueChanged(AdjustmentEvent e) {
	            int vPos = scrollPaneInfPizzero.getVerticalScrollBar().getValue();
	                
	 
	            if (e.getSource().equals(scrollPaneInfPizzero.getVerticalScrollBar()) 
	                    && vPos != oldVPos) {
	                oldVPos = vPos;
	                repaint();
	            }
	        }
	    };
	
	
	public void fijarValorPresentacion()
	{
		//Tomamos el valor del parámetro relacionado la interface gráfica
		Parametro parametro = parCtrl.obtenerParametro("PRESENTACION");
		try
		{
			valPresentacion = parametro.getValorNumerico();
		}catch(Exception e)
		{
			System.out.println("SE TUVO ERROR TOMANDO LA CONSTANTE DE PRESENTACIÓN SISTEMA");
			valPresentacion = 0;
		}
		if(valPresentacion == 0)
		{
			valPresentacion =1;
		}
	}
	
	/**
	 * Create the frame.
	 */
	public VentPedCocina() {
		setTitle("PEDIDOS PENDIENTES EN COCINA");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setExtendedState(MAXIMIZED_BOTH);
		//setBounds(100, 100, 450, 300);
		setBounds(100, 100, 1024, 770);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tabbedPaneMontador = new JTabbedPane(JTabbedPane.TOP);
		tabbedPaneMontador.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				repaint();
			}
		});
		tabbedPaneMontador.setBounds(10, 11, 727, 292);
		contentPane.add(tabbedPaneMontador);
		
		JPanel panelTodos = new JPanel();
		tabbedPaneMontador.addTab("Todos", null, panelTodos, "Todos los pedidos");
		panelTodos.setLayout(null);
		ventPedidoCocina = this;
		JScrollPane scrollPaneMontadorTodos = new JScrollPane();
		scrollPaneMontadorTodos.setBounds(10, 11, 702, 182);
		panelTodos.add(scrollPaneMontadorTodos);
		fijarValorPresentacion();
		tableMontadorTodos = new JTable();
		tableMontadorTodos.setFont(new java.awt.Font("Tahoma", 0, 14)); 
		scrollPaneMontadorTodos.setViewportView(tableMontadorTodos);
		tabbedPaneMontador.setForegroundAt(0, Color.BLACK);
		tabbedPaneMontador.setEnabledAt(0, true);
		tabbedPaneMontador.setBackgroundAt(0, Color.GRAY);
		tabbedPaneMontador.setUI(new ShapeTabbedPaneUI());
		
		JPanel panelPuntoVenta = new JPanel();
		tabbedPaneMontador.addTab("Punto de Venta", new ImageIcon(VentPedCocina.class.getResource("/icons/recogida.jpg")), panelPuntoVenta, "Pedidos de Punto de Venta");
		panelPuntoVenta.setLayout(null);
		
		JScrollPane scrollPanePuntoVenta = new JScrollPane();
		scrollPanePuntoVenta.setBounds(10, 11, 702, 185);
		panelPuntoVenta.add(scrollPanePuntoVenta);
		
		tablePuntoVenta = new JTable();
		tablePuntoVenta.setFont(new java.awt.Font("Tahoma", 0, 14)); 
		scrollPanePuntoVenta.setViewportView(tablePuntoVenta);
		
		JPanel panelDomicilio = new JPanel();
		tabbedPaneMontador.addTab("Domicilio", new ImageIcon(VentPedCocina.class.getResource("/icons/domicilio.jpg")), panelDomicilio, "Pedidos de Domicilio");
		panelDomicilio.setLayout(null);
		
		JScrollPane scrollPaneDomicilio = new JScrollPane();
		scrollPaneDomicilio.setBounds(10, 11, 702, 185);
		panelDomicilio.add(scrollPaneDomicilio);
		
		tableDomicilio = new JTable();
		tableDomicilio.setFont(new java.awt.Font("Tahoma", 0, 14)); 
		scrollPaneDomicilio.setViewportView(tableDomicilio);
		
		JButton btnAvanzarPedido = new JButton("Avanzar Pedido");
		btnAvanzarPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(idPedido == 0)
				{
					JOptionPane.showMessageDialog(null, "Debe Seleccionar algún pedido para Avanzar Estado " , "No ha Seleccionado Pedido", JOptionPane.ERROR_MESSAGE);
					return;
				}
				boolean esEstadoFinal = pedCtrl.esEstadoFinal(idTipoPedido, idEstado);
				if(esEstadoFinal)
				{
					JOptionPane.showMessageDialog(null, "El estado actual es un estado Final no se puede avanzar más" , "No hay estado posterior", JOptionPane.ERROR_MESSAGE);
					return;
				}
				else
				{
					boolean tienePermiso = autCtrl.validarAccesoOpcion("PED_002", Sesion.getAccesosOpcion());
					if (tienePermiso)
					{
						cambioPedido = true;
						VentPedCambioEstado cambioEstado = new VentPedCambioEstado(idPedido, false, true, null, true,/* OJO HAY QUE NORMALIZAR*/0,50);
						cambioEstado.setVisible(true);
						//Realizamos una refijación del pedido
						repaint();

						
					}else
					{
						JOptionPane.showMessageDialog(null, "Su perfil de usuario no tiene acceso a esta opción/pantalla" , "Ingreso no permitido", JOptionPane.ERROR_MESSAGE);
					}
					
				}
			}
		});
		btnAvanzarPedido.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnAvanzarPedido.setBounds(396, 621, 204, 45);
		contentPane.add(btnAvanzarPedido);
		
		JPanel panelProximosPedidos = new JPanel();
		panelProximosPedidos.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panelProximosPedidos.setBounds(747, 11, 251, 667);
		contentPane.add(panelProximosPedidos);
		panelProximosPedidos.setLayout(null);
		
		JLabel lblPizzero = new JLabel("PIZZERO");
		lblPizzero.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblPizzero.setBounds(83, 11, 105, 14);
		panelProximosPedidos.add(lblPizzero);
		panelInfPizzero = new JPanel();
		scrollPaneInfPizzero = new JScrollPane(panelInfPizzero);
		scrollPaneInfPizzero.setBounds(10, 32, 231, 624);
		panelProximosPedidos.add(scrollPaneInfPizzero);
		scrollPaneInfPizzero.setViewportView(panelInfPizzero);
		//A continuación las personalizaciones del scroll bar
		scrollPaneInfPizzero.getVerticalScrollBar().addAdjustmentListener(
				adjustmentListenerProxPedidos); 
		scrollPaneInfPizzero.getVerticalScrollBar().setUnitIncrement(30);
		scrollPaneInfPizzero.getVerticalScrollBar().setPreferredSize(new Dimension(40, 0));
		
		JButton btnRegresar = new JButton("Regresar");
		btnRegresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(valPresentacion == 1)
				{
					VentPrincipal ventPrincipal = new VentPrincipal();
					ventPrincipal.setVisible(true);
				}else if(valPresentacion == 2)
				{
					VentPrincipalModificada ventPrincipal = new VentPrincipalModificada();
					ventPrincipal.lblInformacionUsuario.setText("USUARIO: " + Sesion.getUsuario());
					ventPrincipal.setVisible(true);
				}
				dispose();
			}
		});
		btnRegresar.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnRegresar.setBounds(123, 621, 204, 45);
		contentPane.add(btnRegresar);
		
		JPanel panelExtPintarPedido = new JPanel();
		panelExtPintarPedido.setBounds(10, 305, 727, 313);
		contentPane.add(panelExtPintarPedido);
		panelExtPintarPedido.setLayout(null);
		//Agregamos para todos los pedidos
		scrollPanePedPintar = new JScrollPane();
		scrollPanePedPintar.setBounds(10, 11, 702, 292);
		
		
		
		scrollPanePedPintar = new JScrollPane(panelPintar);
		//scrollPanePedPintar = new JScrollPane();
		scrollPanePedPintar.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPanePedPintar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPanePedPintar.setBounds(10, 34, 707, 268);
		//scrollPanePedPintar.getViewport().setPreferredSize(new Dimension(450, 3050));
		scrollPanePedPintar.getVerticalScrollBar().addAdjustmentListener(
                adjustmentListener);
		panelPintar = new JPanel();
		//Posiblemente hacemos esto para cambie la forma de pintar sin embargo no vemos cambio
		scrollPanePedPintar.setDoubleBuffered(true);
		panelPintar.setDoubleBuffered(true);
		scrollPanePedPintar.setViewportView(panelPintar);
		//Inicialmente fijamos en este tamaño pero posteriormente luego de pintar los fijamos en el valor correcto.
		panelPintar.setPreferredSize(new Dimension(350, 3050));
		panelPintar.setLayout(null);
		//Fijamos un aumento mayor al momento de mover el scrollbar y en cuanto al ancho del SCROLLBAR
		scrollPanePedPintar.getVerticalScrollBar().setUnitIncrement(200);
		scrollPanePedPintar.getVerticalScrollBar().setPreferredSize(new Dimension(40, 0));
		panelExtPintarPedido.add(scrollPanePedPintar);
		
		txtNumPedidoSel = new JTextField();
		txtNumPedidoSel.setForeground(Color.RED);
		txtNumPedidoSel.setEditable(false);
		txtNumPedidoSel.setFont(new Font("Tahoma", Font.BOLD, 21));
		txtNumPedidoSel.setBounds(185, 0, 101, 31);
		panelExtPintarPedido.add(txtNumPedidoSel);
		txtNumPedidoSel.setColumns(10);
		
		JLabel lblPedSel = new JLabel("Pedido Seleccionado");
		lblPedSel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPedSel.setBounds(10, 11, 165, 19);
		panelExtPintarPedido.add(lblPedSel);
		
		ImageIcon img = new ImageIcon("iconos\\LogoPequePizzaAmericana.jpg");
		setIconImage(img.getImage());
		
		//Se define la acción para el cuando se de doble click en el pedido
		tableMontadorTodos.addMouseListener(new java.awt.event.MouseAdapter() {
		      public void mouseClicked(java.awt.event.MouseEvent e) 
		      {
		    	  int idPedidoFP = 0;
			    if( tableMontadorTodos.getSelectedRows().length == 1 ) {
				   	  int filaSeleccionada = tableMontadorTodos.getSelectedRow();
					  idPedidoFP = Integer.parseInt(tableMontadorTodos.getValueAt(filaSeleccionada, 0).toString());
					  idEstado = Integer.parseInt(tableMontadorTodos.getValueAt(filaSeleccionada, 5).toString());
					  idTipoPedido = Integer.parseInt(tableMontadorTodos.getValueAt(filaSeleccionada, 6).toString());
					  txtNumPedidoSel.setText(Integer.toString(idPedidoFP));
					  scrollPanePedPintar.getVerticalScrollBar().setValue(0);
				}
				Window ventanaPadre = SwingUtilities.getWindowAncestor(
		                        (Component) e.getSource());
			      if(e.getClickCount()==1){
			    	  idPedido = idPedidoFP;
			    	  botItemsPedido = new ArrayList();
			    	  repaint();
			    	 			    	  
			        }
		      
		      
		      }
		});
		//Definimos las mismas opciones para los otros Jtable
		tablePuntoVenta.addMouseListener(new java.awt.event.MouseAdapter() {
		      public void mouseClicked(java.awt.event.MouseEvent e) 
		      {
		    	  int idPedidoFP = 0;
			    if( tablePuntoVenta.getSelectedRows().length == 1 ) {
				   	  int filaSeleccionada = tablePuntoVenta.getSelectedRow();
					  idPedidoFP = Integer.parseInt(tablePuntoVenta.getValueAt(filaSeleccionada, 0).toString());
					  idEstado = Integer.parseInt(tablePuntoVenta.getValueAt(filaSeleccionada, 5).toString());
					  idTipoPedido = Integer.parseInt(tablePuntoVenta.getValueAt(filaSeleccionada, 6).toString());
					  txtNumPedidoSel.setText(Integer.toString(idPedidoFP));
					  scrollPanePedPintar.getVerticalScrollBar().setValue(0);
				}
				Window ventanaPadre = SwingUtilities.getWindowAncestor(
		                        (Component) e.getSource());
			      if(e.getClickCount()==1){
			    	  idPedido = idPedidoFP;
			    	  botItemsPedido = new ArrayList();
			    	  repaint();
			    	 			    	  
			        }
		      
		      
		      }
		});
		tableDomicilio.addMouseListener(new java.awt.event.MouseAdapter() {
		      public void mouseClicked(java.awt.event.MouseEvent e) 
		      {
		    	  int idPedidoFP = 0;
			    if( tableDomicilio.getSelectedRows().length == 1 ) {
				   	  int filaSeleccionada = tableDomicilio.getSelectedRow();
					  idPedidoFP = Integer.parseInt(tableDomicilio.getValueAt(filaSeleccionada, 0).toString());
					  idEstado = Integer.parseInt(tableDomicilio.getValueAt(filaSeleccionada, 5).toString());
					  idTipoPedido = Integer.parseInt(tableDomicilio.getValueAt(filaSeleccionada, 6).toString());
					  txtNumPedidoSel.setText(Integer.toString(idPedidoFP));
					  scrollPanePedPintar.getVerticalScrollBar().setValue(0);
				}
				Window ventanaPadre = SwingUtilities.getWindowAncestor(
		                        (Component) e.getSource());
			      if(e.getClickCount()==1){
			    	  idPedido = idPedidoFP;
			    	  botItemsPedido = new ArrayList();
			    	  repaint();
			    	 			    	  
			        }
		      
		      
		      }
		});
		hiloActualizacion = new Thread(this);
		hiloActualizacion.start();
	}
	
	/**
	 * Tiene definido el método cambiarPedido(), el cual tiene como objetivo ejecutarse cuando damos clic sobre el botón 
	 * avanzar pedido, de manera que cuando se regrese a la pantalla principal luego de avanzar o no el pedido, se haga 
	 * un repintado del pedido más antiguo según la pestaña en la que estemos
	 */
	public void cambiarPedido()
	{
			scrollPanePedPintar.getVerticalScrollBar().setValue(0);
			int tabSel = tabbedPaneMontador.getSelectedIndex();
			llenarJTables();
	        //CAMBIAMOS EL IDPEDIDO EN EL QUE DEBERÍAMOS QUEDAR
			idPedido = 0;
			idEstado = 0;
			idTipoPedido = 0;
			if(tabSel == 0)
	        {
				if(tableMontadorTodos.getRowCount() > 0)
				{
					idPedido = Integer.parseInt(tableMontadorTodos.getValueAt(0, 0).toString());
					idEstado = Integer.parseInt(tableMontadorTodos.getValueAt(0, 5).toString());
					idTipoPedido = Integer.parseInt(tableMontadorTodos.getValueAt(0, 6).toString());
					txtNumPedidoSel.setText(Integer.toString(idPedido));
					
				}
	        }else if(tabSel == 1)
	        {
				if(tablePuntoVenta.getRowCount() > 0)
				{
					idPedido = Integer.parseInt(tablePuntoVenta.getValueAt(0, 0).toString());
					idEstado = Integer.parseInt(tablePuntoVenta.getValueAt(0, 5).toString());
					idTipoPedido = Integer.parseInt(tablePuntoVenta.getValueAt(0, 6).toString());
					txtNumPedidoSel.setText(Integer.toString(idPedido));
				}
	        }else if(tabSel ==2)
	        {
				if(tableDomicilio.getRowCount() > 0)
				{
					idPedido = Integer.parseInt(tableDomicilio.getValueAt(0, 0).toString());
					idEstado = Integer.parseInt(tableDomicilio.getValueAt(0, 5).toString());
					idTipoPedido = Integer.parseInt(tableDomicilio.getValueAt(0, 6).toString());
					txtNumPedidoSel.setText(Integer.toString(idPedido));
				}
	        }
			System.out.println("FINALMENTE CON QUE PEDIDO SALÍ " + idPedido);
	}
	
	/**
	 * Se tiene definido el método pintarPedidos(), que tiene como objetivo ser la base del repintado de toda la pantalla,
	 *  repintando los pedidos para el pizzero y repintar el pedido que se tiene seleccionado para quien está preparando/montando 
	 *  los productos.
	 */
	public void pintarPedidos()
	{
		if(cambioPedido)
		{
			panelPintar.removeAll();
        	txtNumPedidoSel.setText("");
			cambiarPedido();
			cambioPedido = false;
		}

		//Para saber como pintaremos obtendremose l TabbPaneSeleccionado
		int tabSel = tabbedPaneMontador.getSelectedIndex();
		llenarJTables();
		//Para pintar la información del pizzero
        Graphics2D g2d = (Graphics2D) panelInfPizzero.getGraphics();
        //Para pintar la información del montador
        Graphics2D g2dMontador = (Graphics2D) panelPintar.getGraphics();
        //Graphics2D g2dMontador = (Graphics2D) scrollPanePedPintar.getGraphics();
        //Debemos de revisar si hay algo seleccionado y si no es así debemos de pintar el último pedido del Jtable
        int largoPanel = pedCtrl.pintarPedidos(g2d , pedidos);
        //Con el valor de largo panel intentaremos fijar el preferred size del panel y así tomar acción sobre el Scroll bar
        panelInfPizzero.setPreferredSize(new Dimension(231, largoPanel));
        //panelInfPizzero.setBounds(10, 32, 231, largoPanel);
        if (idPedido != 0)
        {
        	//Debemos de remover todos los elementos del panelPintar
        	//panelPintar.removeAll();
        	itemsPintar = pedCtrl.pintarPedido(idPedido, g2dMontador);
        	
        	
        	 //En este punto ya se pinto la pantalla y se se sabe el número de itemspintar aqui agregaremos los botones
// DESMONTAMOS LA ADICIÓN DE BOTONES PARA LOS PEDIDOS
//	    	  for(int i = 1; i <= itemsPintar; i++)
//	    	  {
//	    		  btnItemPedido = new JButton();
//	    		  btnItemPedido.setText("PENDIENTE");
//	    		  btnItemPedido.setBackground(Color.red);
//	    		  btnItemPedido.setActionCommand(Integer.toString(i));
//	    		  btnItemPedido.setFont(new Font("Tahoma", Font.BOLD, 18));
//	    		  btnItemPedido.setBounds(500, 200*(itemsPintar)-50, 200, 45);
//	    		  panelPintar.add(btnItemPedido);
//	    		  botItemsPedido.add(btnItemPedido);
//	    		  btnItemPedido.addActionListener(new ActionListener() {
//						public void actionPerformed(ActionEvent arg0) {
//							
//							JButton boton = (JButton)arg0.getSource();
//							//Cambiamos el color del botón según la situación actual de este
//							if(boton.getBackground() == Color.red)
//							{
//								boton.setBackground(Color.green);
//							}else
//							{
//								boton.setBackground(Color.red);
//							}
//							//Recorremos los botones para detectar de cual es y sobreescribimos el estado de este
//							for(int i = 0; i < botItemsPedido.size(); i++)
//							{
//								JButton butTemp = botItemsPedido.get(i);
//								//Si los actionCommand coinciden entonces realizamos el reemplazo del objeto en el arrayList de los botones
//								//Esto con el objetivo de posteriormente controlar qeu se vaya avanzar el estado del pedido y ya se hayan chequeado todos los estados
//								if(butTemp.getActionCommand().equals(boton.getActionCommand()))
//								{
//									botItemsPedido.set(i, boton);
//								}
//							}
//						}
//					});
//	    	  }
        	//panelPintar.paintComponents(g2dMontador);
        	panelPintar.setPreferredSize(new Dimension(350, (itemsPintar+1)*200));
        }
        else
        {
        	panelPintar.removeAll();
        	txtNumPedidoSel.setText("");
        }
        
      
        
	}
	
	public void paint(Graphics g) {
		//Agregando super.paint(g) se solucionaron la mayoría de problemas
		super.paintComponents(g);
//		super.paint(g);
		paintComponents(g);
		pintarPedidos();
						
	}
	
	/**
	 * El método llenarJTables(), tiene como objetivo refrescar el Jtable según la pestaña que esté seleccionada, 
	 * con los pedidos que se encuentran en el estado y sean del tipo que indique la pestaña.
	 */
	public void llenarJTables()
	{
		//Para saber como pintaremos obtendremose l TabbPaneSeleccionado
		int tabSel = tabbedPaneMontador.getSelectedIndex();
		
		Object[] columnsName = new Object[8];
        
		columnsName[0] = "Id Pedido";
        columnsName[1] = "Nombre Cliente";
        columnsName[2] = "Tipo de Pedido";
        columnsName[3] = "Estado";
        columnsName[4] = "Tiempo Dado Cliente";
        columnsName[5] = "idestado";
        columnsName[6] = "idtipopedido";
        columnsName[7] = "Tiempo";

        //Vamos a fijar la adición de los estados dependiendo del JTabbeSeleccionado
        todosEstados = new ArrayList();
        if(tabSel == 0)
        {
        	todosEstados.add(new Integer(cocinaDomicilio));
            todosEstados.add(new Integer(cocinaPuntoVenta));
            todosEstados.add(new Integer(cocinaLlevar));
        }else if(tabSel == 1)
        {
        	todosEstados.add(new Integer(cocinaPuntoVenta));
            todosEstados.add(new Integer(cocinaLlevar));
        }else if(tabSel ==2)
        {
        	todosEstados.add(new Integer(cocinaDomicilio));
        }
        pedidos = pedCtrl.obtenerPedidosVentanaCocina(todosEstados);
        //Definimos los tipos de objetos que se manejarán en el jtable en cada columna
        
        modelo = new DefaultTableModel(){
       	    public boolean isCellEditable(int rowIndex,int columnIndex){
       	    	return editarCampos[columnIndex];
       	    }
       	    
       	   
       	};
		modelo.setColumnIdentifiers(columnsName);
		for(int y = 0; y < pedidos.size();y++)
		{
			String[] fila =(String[]) pedidos.get(y);
			modelo.addRow(fila);
		}
		if(tabSel == 0)
        {
			tableMontadorTodos.setModel(modelo);
			//Tamaño para número de pedido
			tableMontadorTodos.getColumnModel().getColumn(0).setMaxWidth(90);
			tableMontadorTodos.getColumnModel().getColumn(0).setMinWidth(90);
			
			tableMontadorTodos.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(90);
			tableMontadorTodos.getTableHeader().getColumnModel().getColumn(0).setMinWidth(90);
			//Tamaño para nombre de cliene
			tableMontadorTodos.getColumnModel().getColumn(1).setMaxWidth(0);
			tableMontadorTodos.getColumnModel().getColumn(1).setMinWidth(0);
			
			tableMontadorTodos.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(0);
			tableMontadorTodos.getTableHeader().getColumnModel().getColumn(1).setMinWidth(0);		
			//Tamaño para tipo de pedido
			tableMontadorTodos.getColumnModel().getColumn(2).setMaxWidth(100);
			tableMontadorTodos.getColumnModel().getColumn(2).setMinWidth(100);
			
			tableMontadorTodos.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(100);
			tableMontadorTodos.getTableHeader().getColumnModel().getColumn(2).setMinWidth(100);
			
			//Tamaño para idestado
			tableMontadorTodos.getColumnModel().getColumn(5).setMaxWidth(0);
			tableMontadorTodos.getColumnModel().getColumn(5).setMinWidth(0);
			
			tableMontadorTodos.getTableHeader().getColumnModel().getColumn(5).setMaxWidth(0);
			tableMontadorTodos.getTableHeader().getColumnModel().getColumn(5).setMinWidth(0);
			//Tamaño para idtipoPedido
			tableMontadorTodos.getColumnModel().getColumn(6).setMaxWidth(0);
			tableMontadorTodos.getColumnModel().getColumn(6).setMinWidth(0);
			
			tableMontadorTodos.getTableHeader().getColumnModel().getColumn(6).setMaxWidth(0);
			tableMontadorTodos.getTableHeader().getColumnModel().getColumn(6).setMinWidth(0);			
        }else if(tabSel == 1)
        {
        	tablePuntoVenta.setModel(modelo);
    		//Tamaño para número de pedido
        	tablePuntoVenta.getColumnModel().getColumn(0).setMaxWidth(90);
        	tablePuntoVenta.getColumnModel().getColumn(0).setMinWidth(90);
    		
    		tablePuntoVenta.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(90);
    		tablePuntoVenta.getTableHeader().getColumnModel().getColumn(0).setMinWidth(90);
    		//Tamaño para nombre de cliene
    		tablePuntoVenta.getColumnModel().getColumn(1).setMaxWidth(0);
    		tablePuntoVenta.getColumnModel().getColumn(1).setMinWidth(0);
    		
    		tablePuntoVenta.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(0);
    		tablePuntoVenta.getTableHeader().getColumnModel().getColumn(1).setMinWidth(0);		
    		//Tamaño para tipo de pedido
    		tablePuntoVenta.getColumnModel().getColumn(2).setMaxWidth(100);
    		tablePuntoVenta.getColumnModel().getColumn(2).setMinWidth(100);
    		
    		tablePuntoVenta.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(100);
    		tablePuntoVenta.getTableHeader().getColumnModel().getColumn(2).setMinWidth(100);
    		
			//Tamaño para idestado
    		tablePuntoVenta.getColumnModel().getColumn(5).setMaxWidth(0);
    		tablePuntoVenta.getColumnModel().getColumn(5).setMinWidth(0);
			
    		tablePuntoVenta.getTableHeader().getColumnModel().getColumn(5).setMaxWidth(0);
    		tablePuntoVenta.getTableHeader().getColumnModel().getColumn(5).setMinWidth(0);
			//Tamaño para idtipoPedido
    		tablePuntoVenta.getColumnModel().getColumn(6).setMaxWidth(0);
    		tablePuntoVenta.getColumnModel().getColumn(6).setMinWidth(0);
			
    		tablePuntoVenta.getTableHeader().getColumnModel().getColumn(6).setMaxWidth(0);
    		tablePuntoVenta.getTableHeader().getColumnModel().getColumn(6).setMinWidth(0);		
        }else if(tabSel ==2)
        {
        	tableDomicilio.setModel(modelo);
    		//Tamaño para número de pedido
    		tableDomicilio.getColumnModel().getColumn(0).setMaxWidth(90);
    		tableDomicilio.getColumnModel().getColumn(0).setMinWidth(90);
    		
    		tableDomicilio.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(90);
    		tableDomicilio.getTableHeader().getColumnModel().getColumn(0).setMinWidth(90);
    		//Tamaño para nombre de cliene
    		tableDomicilio.getColumnModel().getColumn(1).setMaxWidth(0);
    		tableDomicilio.getColumnModel().getColumn(1).setMinWidth(0);
    		
    		tableDomicilio.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(0);
    		tableDomicilio.getTableHeader().getColumnModel().getColumn(1).setMinWidth(0);		
    		//Tamaño para tipo de pedido
    		tableDomicilio.getColumnModel().getColumn(2).setMaxWidth(100);
    		tableDomicilio.getColumnModel().getColumn(2).setMinWidth(100);
    		
    		tableDomicilio.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(100);
    		tableDomicilio.getTableHeader().getColumnModel().getColumn(2).setMinWidth(100);	
    		
			//Tamaño para idestado
    		tableDomicilio.getColumnModel().getColumn(5).setMaxWidth(0);
    		tableDomicilio.getColumnModel().getColumn(5).setMinWidth(0);
			
    		tableDomicilio.getTableHeader().getColumnModel().getColumn(5).setMaxWidth(0);
    		tableDomicilio.getTableHeader().getColumnModel().getColumn(5).setMinWidth(0);
			//Tamaño para idtipoPedido
    		tableDomicilio.getColumnModel().getColumn(6).setMaxWidth(0);
    		tableDomicilio.getColumnModel().getColumn(6).setMinWidth(0);
			
    		tableDomicilio.getTableHeader().getColumnModel().getColumn(6).setMaxWidth(0);
    		tableDomicilio.getTableHeader().getColumnModel().getColumn(6).setMinWidth(0);		
        }
	}
	
	
	public void run(){
		 Thread ct = Thread.currentThread();
		 while(ct == hiloActualizacion) 
		 {   
			 try {
				 	Thread.sleep(30000);
			 }catch(InterruptedException e) 
			 {}
			 //Ejecutamos el pintado de los pedidos en el JTable de la pantalla.
			 actualizarFuenteInformacion();
			 pintarPedidos();
			 //DesktopNotify.showDesktopMessage("EJECUTANDO REPINTADO", "REPINTANDO LOS PEDIDOS DE COCINA", DesktopNotify.SUCCESS);
		  //Realizamos la ejecución cada 30 segundos
			
		 }
	}
	
	public void actualizarFuenteInformacion()
	{
		int tabSel = tabbedPaneMontador.getSelectedIndex();
		//Vamos a fijar la adición de los estados dependiendo del JTabbeSeleccionado
        todosEstados = new ArrayList();
        if(tabSel == 0)
        {
        	todosEstados.add(new Integer(cocinaDomicilio));
            todosEstados.add(new Integer(cocinaPuntoVenta));
            todosEstados.add(new Integer(cocinaLlevar));
        }else if(tabSel == 1)
        {
        	todosEstados.add(new Integer(cocinaPuntoVenta));
            todosEstados.add(new Integer(cocinaLlevar));
        }else if(tabSel ==2)
        {
        	todosEstados.add(new Integer(cocinaDomicilio));
        }
        pedidos = pedCtrl.obtenerPedidosVentanaCocina(todosEstados);
        
	}
}
