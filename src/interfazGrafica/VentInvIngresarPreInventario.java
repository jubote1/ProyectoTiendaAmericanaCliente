package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.EventObject;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.JTextComponent;

import JTable.CellRenderIngInventario;
import JTable.NextCellActioinRetInventarios;
import capaControlador.InventarioCtrl;
import capaControlador.OperacionesTiendaCtrl;
import capaControlador.PedidoCtrl;
import capaDAO.GeneralDAO;
import capaModelo.Correo;
import capaModelo.FechaSistema;
import capaModelo.InventariosTemporal;
import capaModelo.ModificadorInventario;
import capaModelo.Tienda;
import utilidades.ControladorEnvioCorreo;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.awt.Window;

import javax.swing.JTextField;
import javax.swing.KeyStroke;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;

public class VentInvIngresarPreInventario extends JDialog {

	private JPanel contentPanePrincipal;
	private JTable tableIngInventarios;
	private JTextField txtFechaInventario;
	String fechaSis;
	ArrayList<ModificadorInventario> inventarioIngresar = new ArrayList();
	InventarioCtrl invCtrl = new InventarioCtrl(PrincipalLogueo.habilitaAuditoria);
	OperacionesTiendaCtrl tiendaCtrl = new OperacionesTiendaCtrl(PrincipalLogueo.habilitaAuditoria);
	//Hilo para el JProgressBar
	Thread hiloProgressBar = new Thread();
	//Tendremos la definición de las variables de cantidadItems de Inventario y Cantidad Insertados que servirán
	//para el JProgressBar
	private int cantidadItems = 100;
	private int cantAvance = 0 ;
	private JTextField txtIdDespacho;
	JButton btnConfirmarIngreso;
	JButton btnRechazarIngresoInventario;
	JTextArea textAreaObsBodega;
	private JTextArea textAreaObservacion;
	//Arreglo donde se almacenarán los despachos a procesar
	ArrayList<Integer> despachos;
	private JProgressBar progressBar;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentInvIngresarPreInventario frame = new VentInvIngresarPreInventario(null, true);
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
	public VentInvIngresarPreInventario(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		setTitle("INGRESAR PREINVENTARIOS DE BODEGA");
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setDefaultCloseOperation(0);
		setBounds(0,0, 900, 600);
		int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	    int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		setBounds((ancho / 2) - (this.getWidth() / 2), (alto / 2) - (this.getHeight() / 2), 900, 600);
		contentPanePrincipal = new JPanel();
		contentPanePrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPanePrincipal);
		contentPanePrincipal.setLayout(null);
		
		JScrollPane scrollPaneIngInventarios = new JScrollPane();
		scrollPaneIngInventarios.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneIngInventarios.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPaneIngInventarios.setBounds(10, 11, 864, 287);
		contentPanePrincipal.add(scrollPaneIngInventarios);
		
		tableIngInventarios = new JTable(){
			//En la creación del JTable nos permite que con el double clic nos remarque el contenido y así podamos editar más facil
			 @Override // Always selectAll()
			    public boolean editCellAt(int row, int column, EventObject e) {
			        boolean result = super.editCellAt(row, column, e);
			        final Component editor = getEditorComponent();
			        if (editor == null || !(editor instanceof JTextComponent)) {
			            return result;
			        }
			        if (e instanceof MouseEvent) {
			            EventQueue.invokeLater(() -> {
			                ((JTextComponent) editor).selectAll();
			            });
			        } else {
			            ((JTextComponent) editor).selectAll();
			        }
			        return result;
			    }
		};
		scrollPaneIngInventarios.setColumnHeaderView(tableIngInventarios);
		scrollPaneIngInventarios.setViewportView(tableIngInventarios);
		
		
		//Adicionamos acciones para el comportamiento de la tabla tab y enter
				InputMap im = tableIngInventarios.getInputMap();
				//Definimos que el enter será para la siguiente celda
		        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Action.NextCell");
		        //Definimos que el tab será para la siguiente celda
		        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "Action.NextCell");
		        ActionMap am = tableIngInventarios.getActionMap();
		        //Definimos el action map de nextcell para el jtable e implementamos clase
		        am.put("Action.NextCell", new NextCellActioinRetInventarios(tableIngInventarios));
		
		
		btnConfirmarIngreso = new JButton("Ingresar a Inventario");
		btnConfirmarIngreso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Window ventanaPadre = SwingUtilities.getWindowAncestor(
                        (Component) arg0.getSource());
				cantAvance = 1;
				inicializarHiloBarra();
				hiloProgressBar.start();
				//Realizamos la desactivación de la edición del JTable
				if(tableIngInventarios.isEditing())
				{
					tableIngInventarios.getCellEditor().stopCellEditing();
				}
				cantAvance = 10;
				//Definición de variables necesarias para el proceso
				int idItem;
				int controladorIngreso = 0;
				//Se defienen las variables cantidad de tipo string y double para capturar el valor 
				double cantidad;
				double cantidadBodega;
				String strCantidad;
				cantAvance =  30;
				//Variable para indicar si hay valores que se cambiaron
				boolean hayDiferencias = false;
				//Recorre el jtable para ver si se modifico
				for(int i = 0; i < tableIngInventarios.getRowCount(); i++)
				{
					//Capturamos el idItem	
					idItem =Integer.parseInt((String)tableIngInventarios.getValueAt(i, 0));
					//Capturamos la cantidad
					try
					{
						cantidad = Double.parseDouble((String)tableIngInventarios.getValueAt(i, 6));
						cantidadBodega = Double.parseDouble((String)tableIngInventarios.getValueAt(i, 5));
					}catch(Exception e)
					{
						cantidad = 0;
						cantidadBodega = 0;
					}
					
					if(cantidad > 0 )
					{
						//Validamos si hay diferencias en algunos items dentro de lo enviado en la bodega y lo real
						if(!hayDiferencias)
						{
							if(cantidad != cantidadBodega)
							{
								//Prendemos el indicador de que hay diferencias.
								hayDiferencias = true;
							}
						}
						//Creamos el objeto y lo ingresamos al ArrayList
						controladorIngreso++;
						ModificadorInventario mod = new ModificadorInventario(idItem,cantidad);
						inventarioIngresar.add(mod);
					}
					
					
				}
				//Hacemos la validación de la observacion
				boolean valObs = validarCampoObservacion(hayDiferencias);
				if(!valObs)
				{
					JOptionPane.showMessageDialog(ventanaPadre, "La Observación del PreIngreso de inventario debe tener más de 20 carácteres y menos de 500. Actualmente tiene " + textAreaObservacion.getText().length() + " carácteres." , "Error Validación Datos", JOptionPane.ERROR_MESSAGE);
					cantAvance = 100;
					return;
				}
				String observacion = textAreaObservacion.getText();
				//Preguntamos si encontró diferencias para mostrar mensaje de si estamos seguros
				if(hayDiferencias)
				{
					int resp = JOptionPane.showConfirmDialog(ventanaPadre, "¿Se tienen diferencias entre lo despachado en la bodega y lo confirmado en este momento, ESTA SEGURO?", "Confirmación Diferencias" , JOptionPane.YES_NO_OPTION);
					if (resp == 0)
					{
						
					}else
					{
						cantAvance = 100;
						return;
					}
				}
				
				cantAvance = 40;
				//Validamos si por lo menos hubo un ingreso
				if(controladorIngreso>0)
				{
					//Realizamos la invocación para la inclusión de la información de inventarios
					int idIngreso = invCtrl.insertarIngresosInventarios(inventarioIngresar, observacion,  fechaSis);
					if(idIngreso > 0)
					{
						cantAvance = 100;
						JOptionPane.showMessageDialog(null, "El inventario " + idIngreso + " fue ingresado correctamente." , "Ingreso de Inventario", JOptionPane.INFORMATION_MESSAGE);
						//Si se confirma el ingreso debemos de borrar de la tabla temporal
						invCtrl.limpiarTipoInventariosTemporal(fechaSis,"I");
						//Si todo se logro confirmar en el envío, deberemos de borrar los detalles
						for(int j = 0; j < despachos.size(); j++) 
						{
							//Se van borrando los detalles del despacho
							int despachoTemp = (Integer)despachos.get(j);
							invCtrl.borrarIngresoInventarioDetalleTmp(despachoTemp);
							//Se cambia el estado del despacho según el resultado
							String estadoNuevo = "";
							String asuntoCorreo = "";
							Tienda tienda = tiendaCtrl.obtenerTienda();
							if(hayDiferencias)
							{
								estadoNuevo = "INGRESADONOV";
								asuntoCorreo = "INVENTARIO CON NOVEDADES " + tienda.getNombretienda() + " : Despacho número " + despachoTemp + " Observación: " + textAreaObservacion.getText();
							}else
							{
								estadoNuevo = "INGRESADOOK";
								asuntoCorreo = "INVENTARIO OK " + tienda.getNombretienda() + " : Despacho número " + despachoTemp + " Observación: " + textAreaObservacion.getText() ;
							}
							invCtrl.cambiarEstadoIngresoInventarioTmp(despachoTemp,estadoNuevo);
							//En este punto hacemos envío del correo
							Correo correo = new Correo();
							correo.setAsunto(asuntoCorreo);
							correo.setContrasena("Pizzaamericana2017");
							ArrayList correos = GeneralDAO.obtenerCorreosParametro("INFODESPACHOS", false);
							correo.setUsuarioCorreo("alertaspizzaamericana@gmail.com");
							correo.setMensaje("Se ha ingresado el despacho  " + despachoTemp + ". " + asuntoCorreo);
							ControladorEnvioCorreo contro = new ControladorEnvioCorreo(correo, correos);
							contro.enviarCorreo();
						}
						dispose();
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Se tuvo inconvenientes al ingresar el inventarios" , "Error al ingresar inventarios", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "No ingreso ningun Item al inventario, si desea salir de clic sobre el botón cancelar " , "No Ha Ingresado nada al Inventario", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		});
		btnConfirmarIngreso.setBounds(27, 424, 152, 37);
		contentPanePrincipal.add(btnConfirmarIngreso);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Window ventanaPadre = SwingUtilities.getWindowAncestor(
                        (Component) arg0.getSource());
				int resp = JOptionPane.showConfirmDialog(ventanaPadre, "¿Está seguro que desea salir sin confirmar el ingreso de inventario de bodega?", "Confirmación de Salida de Pantalla" , JOptionPane.YES_NO_OPTION);
				if (resp == 0)
				{
					dispose();
				}
			}
		});
		btnCancelar.setBounds(226, 424, 146, 37);
		contentPanePrincipal.add(btnCancelar);
		
		JLabel lblFechaIngresoInventario = new JLabel("FECHA INGRESO INVENTARIO");
		lblFechaIngresoInventario.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblFechaIngresoInventario.setBounds(10, 483, 240, 14);
		contentPanePrincipal.add(lblFechaIngresoInventario);
		
		txtFechaInventario = new JTextField();
		txtFechaInventario.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtFechaInventario.setEnabled(false);
		txtFechaInventario.setEditable(false);
		txtFechaInventario.setBounds(237, 480, 135, 20);
		contentPanePrincipal.add(txtFechaInventario);
		txtFechaInventario.setColumns(10);
		
		//Vamos a recuperar la fecha del sistema y la vamos a mostrar en el campo correspondiente
		PedidoCtrl pedCtrl = new PedidoCtrl(PrincipalLogueo.habilitaAuditoria);
		FechaSistema fecha = pedCtrl.obtenerFechasSistema();
		fechaSis = fecha.getFechaApertura();
		txtFechaInventario.setText(fechaSis);
		
		JLabel lblImagen = new JLabel("");
		lblImagen.setBounds(676, 424, 198, 126);
		ImageIcon icon = new ImageIcon(getClass().getResource("/imagenes/LogoPizzaAmericana.png"));
		Image imagen = icon.getImage();
		ImageIcon iconoEscalado = new ImageIcon (imagen.getScaledInstance(198,126,Image.SCALE_SMOOTH));
		lblImagen.setIcon(iconoEscalado);
		contentPanePrincipal.add(lblImagen);
		//Creamos la barra de progreso que va a funcionar cuando ingresemos los inventarios
				progressBar = new JProgressBar();
				//Creamos el hilo encargado de llenar el ProgressBar
				
				//Inicializamos el hilo
				inicializarHiloBarra();
				
				progressBar.setBounds(231, 519, 292, 31);
				contentPanePrincipal.add(progressBar);
				
				btnRechazarIngresoInventario = new JButton("Rechazar Inventario");
				btnRechazarIngresoInventario.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						Window ventanaPadre = SwingUtilities.getWindowAncestor(
		                        (Component) arg0.getSource());
						int resp = 0;
						if(despachos.size() > 0)
						{
							resp = JOptionPane.showConfirmDialog(ventanaPadre, "¿"
									+ "Vas a decartar el inventario despachado desde la bodega, ESTA SEGURO?", "Confirmación de no Aceptación de Despacho" , JOptionPane.YES_NO_OPTION);
						}
						for(int j = 0; j < despachos.size(); j++) 
						{
							if (resp == 0)
							{
								//Se van borrando los detalles del despacho
								int despachoTemp = (Integer)despachos.get(j);
								invCtrl.cambiarEstadoIngresoInventarioTmp(despachoTemp,"INGRESADONOK");
								Correo correo = new Correo();
								Tienda tienda = tiendaCtrl.obtenerTienda();
								correo.setAsunto("INVENTARIO RECHAZADO " + tienda.getNombretienda()+" : " + " Despacho número " + despachoTemp + fechaSis);
								correo.setContrasena("Pizzaamericana2017");
								ArrayList correos = GeneralDAO.obtenerCorreosParametro("INFODESPACHOS", false);
								correo.setUsuarioCorreo("alertaspizzaamericana@gmail.com");
								correo.setMensaje("Se ha rechazado el despacho número " + despachoTemp + " para la tienda " + tienda.getNombretienda() + " Observación: " + textAreaObservacion.getText());
								ControladorEnvioCorreo contro = new ControladorEnvioCorreo(correo, correos);
								contro.enviarCorreo();
							}else
							{
								return;
							}
							
							
						}
						if(resp == 0)
						{
							dispose();
						}
					}
				});
				btnRechazarIngresoInventario.setBounds(413, 424, 152, 37);
				contentPanePrincipal.add(btnRechazarIngresoInventario);
				
				JLabel lblIdDespachoBodega = new JLabel("ID DESPACHO BODEGA");
				lblIdDespachoBodega.setFont(new Font("Tahoma", Font.BOLD, 14));
				lblIdDespachoBodega.setBounds(387, 483, 190, 14);
				contentPanePrincipal.add(lblIdDespachoBodega);
				
				txtIdDespacho = new JTextField();
				txtIdDespacho.setText((String) null);
				txtIdDespacho.setFont(new Font("Tahoma", Font.BOLD, 14));
				txtIdDespacho.setEnabled(false);
				txtIdDespacho.setEditable(false);
				txtIdDespacho.setColumns(10);
				txtIdDespacho.setBounds(557, 480, 109, 20);
				contentPanePrincipal.add(txtIdDespacho);
				
				JLabel lblObservacinGeneral = new JLabel("OBSERVACI\u00D3N GENERAL");
				lblObservacinGeneral.setFont(new Font("Tahoma", Font.BOLD, 14));
				lblObservacinGeneral.setBounds(0, 383, 198, 14);
				contentPanePrincipal.add(lblObservacinGeneral);
				
				JScrollPane scrollPaneObservacion = new JScrollPane();
				scrollPaneObservacion.setBounds(204, 366, 642, 50);
				contentPanePrincipal.add(scrollPaneObservacion);
				
				textAreaObservacion = new JTextArea();
				scrollPaneObservacion.setViewportView(textAreaObservacion);
				textAreaObservacion.setRows(3);
				
				JLabel lblObservacinBodega = new JLabel("OBSERVACI\u00D3N BODEGA");
				lblObservacinBodega.setFont(new Font("Tahoma", Font.BOLD, 14));
				lblObservacinBodega.setBounds(0, 325, 198, 14);
				contentPanePrincipal.add(lblObservacinBodega);
				
				JScrollPane scrollPanObsBodega = new JScrollPane();
				scrollPanObsBodega.setBounds(204, 309, 642, 50);
				contentPanePrincipal.add(scrollPanObsBodega);
				
				textAreaObsBodega = new JTextArea();
				textAreaObsBodega.setEditable(false);
				textAreaObsBodega.setRows(3);
				scrollPanObsBodega.setViewportView(textAreaObsBodega);
				pintarItemsInventario();
	}
	
	public void pintarItemsInventario()
	{
		//Tomamos los despachos
		despachos = invCtrl.obtenerInventariosPreIngresar(fechaSis);
		//Validamos los despachos si no hay mostramos mensajes y cerramos la ventana
		if(despachos.size() == 0)
		{
			JOptionPane.showMessageDialog(null, "NO HAY DESPACHOS de Bodega registrados, si deseas ingresar al inventario realizarlo por la opción convencional." , "No hay despachos de Bodega", JOptionPane.ERROR_MESSAGE);
			btnConfirmarIngreso.setEnabled(false);
			btnRechazarIngresoInventario.setEnabled(false);
			return;
		}
		//Este es el método encargado de pintar el grid con los items de inventario a ingresar de inventario
		
		Object[] columnsName = new Object[7];
        
        columnsName[0] = "Id Item";
        columnsName[1] = "Nombre";
        columnsName[2] = "Unidad Medida";
        columnsName[3] = "Cantidad x Canasta";
        columnsName[4] = "Nombre Contenedor";
        columnsName[5] = "Cantidad/Bodega";
        columnsName[6] = "Cantidad Real";
        ArrayList<Object> itemsIng = new ArrayList();
        
       	itemsIng = invCtrl.obtenerInventarioPreIngresar();
        
        DefaultTableModel modelo = new DefaultTableModel(){
       	    public boolean isCellEditable(int rowIndex,int columnIndex){
       	    	if(columnIndex < 6)
       	    	{
       	    		return false;
       	    	}
       	    	return true;
       	    }
       	};
		modelo.setColumnIdentifiers(columnsName);
		for(int y = 0; y < itemsIng.size();y++)
		{
			String[] fila =(String[]) itemsIng.get(y);
			modelo.addRow(fila);
		}
		tableIngInventarios.setModel(modelo);
		setCellRender(tableIngInventarios);
		
		//Inicializamos el txtIdDespacho con los idDespachos del día en cuestión
		
		String strDespachos = "";
		String observacionBodega = "";
		for(int i = 0; i < despachos.size(); i++)
		{
			strDespachos = strDespachos + " " + Integer.toString((Integer)despachos.get(i));
			observacionBodega = observacionBodega + " " + invCtrl.obtenerObsInventarioPreIngresar((Integer)despachos.get(i));
		}
		txtIdDespacho.setText(strDespachos);
		textAreaObsBodega.setText(observacionBodega);
		//Llenaremos la observación de los Preingresos
		
	}
	
	public void setCellRender(JTable table) {
        Enumeration<TableColumn> en = table.getColumnModel().getColumns();
        while (en.hasMoreElements()) {
            TableColumn tc = en.nextElement();
            tc.setCellRenderer(new CellRenderIngInventario());
        }
    }
	
	public boolean validarCampoObservacion(boolean hayDif)
	{
		boolean respuesta = true;
		if(hayDif)
		{
			String observacion = textAreaObservacion.getText();
			if((observacion.length() < 20)||(observacion.length() > 500))
			{
				respuesta = false;
			}else
			{
				respuesta = true;
			}
		}
		
		return(respuesta);
	}
	
	public void inicializarHiloBarra()
	{
		hiloProgressBar = new Thread()
		{
			public void run()
			{
				//Definimos los parámetros de mínimo y máximo del JProgressBar
				progressBar.setMinimum(0);
				progressBar.setMaximum(cantidadItems);
				
				try
				{
					progressBar.setIndeterminate(true);
					progressBar.setStringPainted(true);
					progressBar.setBorderPainted(true);
					//El ciclo que manejará se ejecutará mientras la cantidad de items sea menor igual a los insertados
					// estas variables cambiarán en el hilo principal
					while((cantAvance > 0) && (cantAvance < 100))
					{
						//Actualizamos el valor del JProgressBar
		                SwingUtilities.invokeLater(new Runnable() {
		                    public void run() {
		                    	progressBar.setValue(cantAvance);
		                    }
		                  });
						//Dormimos el hilo por un momento
						hiloProgressBar.sleep(5);
						
					}
					progressBar.setStringPainted(false);
					progressBar.setBorderPainted(true);
					progressBar.setIndeterminate(false);
				}catch(Exception exc)
				{
					System.out.println(exc.toString());
				}
			}
		};
	}
}
