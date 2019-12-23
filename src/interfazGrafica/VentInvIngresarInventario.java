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
import capaControlador.PedidoCtrl;
import capaModelo.FechaSistema;
import capaModelo.InventariosTemporal;
import capaModelo.ModificadorInventario;

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

public class VentInvIngresarInventario extends JDialog {

	private JPanel contentPanePrincipal;
	private JTable tableIngInventarios;
	private JTextField txtFechaInventario;
	String fechaSis;
	ArrayList<ModificadorInventario> inventarioIngresar = new ArrayList();
	InventarioCtrl invCtrl = new InventarioCtrl(PrincipalLogueo.habilitaAuditoria);
	//Hilo para el JProgressBar
	Thread hiloProgressBar = new Thread();
	private JProgressBar progressBar;
	//Tendremos la definición de las variables de cantidadItems de Inventario y Cantidad Insertados que servirán
	//para el JProgressBar
	private int cantidadItems = 100;
	private int cantAvance = 0 ;
	private JTextArea textAreaObservacion;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentInvIngresarInventario frame = new VentInvIngresarInventario(null, true);
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
	public VentInvIngresarInventario(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		setTitle("INGRESAR INVENTARIOS");
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setDefaultCloseOperation(0);
		setBounds(0,0, 800, 600);
		int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	    int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		setBounds((ancho / 2) - (this.getWidth() / 2), (alto / 2) - (this.getHeight() / 2), 800, 600);
		contentPanePrincipal = new JPanel();
		contentPanePrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPanePrincipal);
		contentPanePrincipal.setLayout(null);
		
		JScrollPane scrollPaneIngInventarios = new JScrollPane();
		scrollPaneIngInventarios.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneIngInventarios.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPaneIngInventarios.setBounds(10, 11, 764, 297);
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
		
		
		JButton btnConfirmarIngreso = new JButton("Confirmar Ingreso");
		btnConfirmarIngreso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Window ventanaPadre = SwingUtilities.getWindowAncestor(
                        (Component) arg0.getSource());
				//Realizamos validación de que si se tenga observacion
				boolean valObs = validarCampoObservacion();
				if(!valObs)
				{
					JOptionPane.showMessageDialog(ventanaPadre, "La Observación del ingreso de inventario debe tener más de 20 carácteres y menos de 500. Actualmente tiene " + textAreaObservacion.getText().length() + " carácteres." , "Error Validación Datos", JOptionPane.ERROR_MESSAGE);
					return;
				}
				String observacion = textAreaObservacion.getText();
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
				String strCantidad;
				cantAvance =  30;
				//Recorre el jtable para ver si se modifico
				for(int i = 0; i < tableIngInventarios.getRowCount(); i++)
				{
					//Capturamos el idItem	
					idItem =Integer.parseInt((String)tableIngInventarios.getValueAt(i, 0));
					//Capturamos la cantidad
					try
					{
						cantidad = Double.parseDouble((String)tableIngInventarios.getValueAt(i, 5));
					}catch(Exception e)
					{
						cantidad = 0;
					}
					
					if(cantidad > 0 )
					{
						//Creamos el objeto y lo ingresamos al ArrayList
						controladorIngreso++;
						ModificadorInventario mod = new ModificadorInventario(idItem,cantidad);
						inventarioIngresar.add(mod);
					}
					
					
				}
				cantAvance = 40;
				//Validamos si por lo menos hubo un ingreso
				if(controladorIngreso>0)
				{
					//Realizamos la invocación para la inclusión de la información de inventarios
					int idIngreso = invCtrl.insertarIngresosInventarios(inventarioIngresar, observacion , fechaSis);
					if(idIngreso > 0)
					{
						cantAvance = 100;
						JOptionPane.showMessageDialog(null, "El inventario " + idIngreso + " fue ingresado correctamente." , "Ingreso de Inventario", JOptionPane.INFORMATION_MESSAGE);
						//Si se confirma el ingreso debemos de borrar de la tabla temporal
						invCtrl.limpiarTipoInventariosTemporal(fechaSis,"I");
						dispose();
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Se tuvo inconvenientes al ingresar el inventarios" , "Error al ingresar inventarios", JOptionPane.ERROR_MESSAGE);
						cantAvance = 100;
						return;
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "No ingreso ningun Item al inventario, si desea salir de clic sobre el botón cancelar " , "No Ha Ingresado nada al Inventario", JOptionPane.ERROR_MESSAGE);
					cantAvance = 100;
					return;
				}
			}
		});
		btnConfirmarIngreso.setBounds(32, 421, 152, 37);
		contentPanePrincipal.add(btnConfirmarIngreso);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Window ventanaPadre = SwingUtilities.getWindowAncestor(
                        (Component) arg0.getSource());
				int resp = JOptionPane.showConfirmDialog(ventanaPadre, "¿Está seguro que desea salir sin confirmar el ingreso o sin guardar temporalmente?", "Confirmación de Salida de Pantalla" , JOptionPane.YES_NO_OPTION);
				if (resp == 0)
				{
					dispose();
				}
			}
		});
		btnCancelar.setBounds(207, 421, 146, 37);
		contentPanePrincipal.add(btnCancelar);
		
		JLabel lblFechaIngresoInventario = new JLabel("FECHA INGRESO INVENTARIO");
		lblFechaIngresoInventario.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblFechaIngresoInventario.setBounds(62, 472, 240, 14);
		contentPanePrincipal.add(lblFechaIngresoInventario);
		
		txtFechaInventario = new JTextField();
		txtFechaInventario.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtFechaInventario.setEnabled(false);
		txtFechaInventario.setEditable(false);
		txtFechaInventario.setBounds(355, 469, 135, 20);
		contentPanePrincipal.add(txtFechaInventario);
		txtFechaInventario.setColumns(10);
		
		//Vamos a recuperar la fecha del sistema y la vamos a mostrar en el campo correspondiente
		PedidoCtrl pedCtrl = new PedidoCtrl(PrincipalLogueo.habilitaAuditoria);
		FechaSistema fecha = pedCtrl.obtenerFechasSistema();
		fechaSis = fecha.getFechaApertura();
		txtFechaInventario.setText(fechaSis);
		
		JLabel lblImagen = new JLabel("");
		lblImagen.setBounds(576, 424, 198, 126);
		ImageIcon icon = new ImageIcon(getClass().getResource("/imagenes/LogoPizzaAmericana.png"));
		Image imagen = icon.getImage();
		ImageIcon iconoEscalado = new ImageIcon (imagen.getScaledInstance(198,126,Image.SCALE_SMOOTH));
		lblImagen.setIcon(iconoEscalado);
		contentPanePrincipal.add(lblImagen);
		//Creamos la barra de progreso que va a funcionar cuando ingresemos los inventarios
				progressBar = new JProgressBar();
				progressBar.setBounds(161, 497, 292, 31);
				//Inicializamos el hilo
				inicializarHiloBarra();
				contentPanePrincipal.add(progressBar);
				
				JButton btnGuardarSinConfirmar = new JButton("Guardar sin Confirmar");
				btnGuardarSinConfirmar.setBackground(Color.ORANGE);
				btnGuardarSinConfirmar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						Window ventanaPadre = SwingUtilities.getWindowAncestor(
		                        (Component) arg0.getSource());
						ArrayList<InventariosTemporal> inventariosTemp = new ArrayList();
						InventariosTemporal invTemp;
						int resp = JOptionPane.showConfirmDialog(ventanaPadre, "¿Desea almacenar temporalmente la información del ingreso de inventario?", "Confirmación de Salida de Pantalla" , JOptionPane.YES_NO_OPTION);
						if (resp == 0)
						{
							//Si se confirma el ingreso debemos de borrar de la tabla temporal
							invCtrl.limpiarTipoInventariosTemporal(fechaSis,"I");
							cantAvance = 1;
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
							String strCantidad;
							
							cantAvance =  30;
							//Recorre el jtable para ver si se modifico
							for(int i = 0; i < tableIngInventarios.getRowCount(); i++)
							{
								//Capturamos el idItem	
								idItem =Integer.parseInt((String)tableIngInventarios.getValueAt(i, 0));
								//Capturamos la cantidad
								try
								{
									cantidad = Double.parseDouble((String)tableIngInventarios.getValueAt(i, 5));
								}catch(Exception e)
								{
									cantidad = 0;
								}
								
								controladorIngreso++;
								invTemp = new InventariosTemporal(fechaSis,"I", idItem, cantidad);
								inventariosTemp.add(invTemp);
							}
							cantAvance = 40;
							//Validamos si por lo menos hubo un ingreso
							if(controladorIngreso>0)
							{
								//Realizamos la invocación para la inclusión de la información de inventarios
								boolean respuesta  = invCtrl.insertarInventariosTemp(inventariosTemp);
								if(respuesta)
								{
									cantAvance = 100;
									JOptionPane.showMessageDialog(ventanaPadre, "El ingreso de inventario se ha guardado temporalmente, recuerde confirmar." , "Ingreso de Inventario", JOptionPane.INFORMATION_MESSAGE);
									dispose();
								}
								else
								{
									JOptionPane.showMessageDialog(ventanaPadre, "Se tuvo inconvenientes al guardar temporalmente el ingreso de inventario." , "Error al ingresar inventarios", JOptionPane.ERROR_MESSAGE);
									return;
								}
							}
							else
							{
								JOptionPane.showMessageDialog(null, "No ingreso ningun Item al inventario, si desea salir de clic sobre el botón cancelar " , "No Ha Ingresado nada al Inventario", JOptionPane.ERROR_MESSAGE);
								return;
							}
						}
					}
				});
				btnGuardarSinConfirmar.setBounds(379, 421, 168, 37);
				contentPanePrincipal.add(btnGuardarSinConfirmar);
				
				JLabel label = new JLabel("OBSERVACI\u00D3N GENERAL");
				label.setFont(new Font("Tahoma", Font.BOLD, 14));
				label.setBounds(20, 319, 198, 14);
				contentPanePrincipal.add(label);
				
				JScrollPane scrollPaneObservacion = new JScrollPane();
				scrollPaneObservacion.setBounds(30, 346, 764, 64);
				contentPanePrincipal.add(scrollPaneObservacion);
				
				textAreaObservacion = new JTextArea();
				textAreaObservacion.setRows(3);
				scrollPaneObservacion.setViewportView(textAreaObservacion);
				pintarItemsInventario();
	}
	
	public void pintarItemsInventario()
	{
		//Este es el método encargado de pintar el grid con los items de inventario a ingresar
		//Se valida si existe algo en las tablas temporales
		boolean existePreIngreso = invCtrl.existeInventariosTemporal(fechaSis, "I");
		Object[] columnsName = new Object[6];
        
        columnsName[0] = "Id Item";
        columnsName[1] = "Nombre";
        columnsName[2] = "Unidad Medida";
        columnsName[3] = "Cantidad x Canasta";
        columnsName[4] = "Nombre Contenedor";
        columnsName[5] = "Cantidad Ingresar";
        ArrayList<Object> itemsIng = new ArrayList();
        //En este punto hacemos la diferenciación de si hay algo en base de datos pre ingresado o no
        if(existePreIngreso)
        {
        	itemsIng = invCtrl.obtenerItemInventarioIngresar(fechaSis, "I");
        }else
        {
        	itemsIng = invCtrl.obtenerItemInventarioIngresar();
        }
        DefaultTableModel modelo = new DefaultTableModel(){
       	    public boolean isCellEditable(int rowIndex,int columnIndex){
       	    	if(columnIndex < 5)
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
		
	}
	
	public void setCellRender(JTable table) {
        Enumeration<TableColumn> en = table.getColumnModel().getColumns();
        while (en.hasMoreElements()) {
            TableColumn tc = en.nextElement();
            tc.setCellRenderer(new CellRenderIngInventario());
        }
    }
	
	public boolean validarCampoObservacion()
	{
		boolean respuesta = false;
		String observacion = textAreaObservacion.getText();;
		if((observacion.length() < 20)||(observacion.length() > 500))
		{
			respuesta = false;
		}else
		{
			respuesta = true;
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
