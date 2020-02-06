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
import JTable.CellRenderIngVarianza;
import JTable.CellRenderIngVarianzaNA;
import JTable.NextCellActioinRetInventarios;
import JTable.NextCellActioinVarianza;
import JTable.NextCellActioinVarianzaNA;
import capaControlador.InventarioCtrl;
import capaControlador.ParametrosCtrl;
import capaControlador.PedidoCtrl;
import capaModelo.FechaSistema;
import capaModelo.InventariosTemporal;
import capaModelo.ModificadorInventario;
import capaModelo.Parametro;

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

public class VentInvIngresarVarianza extends JDialog {

	private JPanel contentPanePrincipal;
	private JTable tableIngVarianza;
	private JTextField txtFechaInventario;
	String fechaSis;
	ArrayList<ModificadorInventario> inventarioIngresar = new ArrayList();
	InventarioCtrl invCtrl = new InventarioCtrl(PrincipalLogueo.habilitaAuditoria);
	ParametrosCtrl parCtrl = new ParametrosCtrl(PrincipalLogueo.habilitaAuditoria);
	//Hilo para el JProgressBar
	Thread hiloProgressBar = new Thread();
	//Tendremos la definición de las variables de cantidadItems de Inventario y Cantidad Insertados que servirán
	//para el JProgressBar
	private int cantidadItems = 100;
	private int cantAvance = 0 ;
	String varianzaAyuda = "S";
	JTextArea textAreaObser;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentInvIngresarVarianza frame = new VentInvIngresarVarianza(null, true);
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
	public VentInvIngresarVarianza(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		PedidoCtrl pedCtrl = new PedidoCtrl(PrincipalLogueo.habilitaAuditoria);
		FechaSistema fecha = pedCtrl.obtenerFechasSistema();
		fechaSis = fecha.getFechaApertura();
		boolean ingVarianza = invCtrl.seIngresoVarianza(fechaSis);
		if(ingVarianza)
		{
			JOptionPane.showMessageDialog(null, "La varianza ya fue ingresada para la fecha Sistema" , "Error varinza ya ingresada", JOptionPane.ERROR_MESSAGE);
//			this.setVisible(false);
//			return;
		}
		//Validamos el estado de la variable ayuda Varianza
		Parametro parametroAud = parCtrl.obtenerParametro("VARIANZAAYUDA");
		varianzaAyuda = parametroAud.getValorTexto();
		
		setTitle("INGRESAR VARIANZA");
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setDefaultCloseOperation(0);
		setBounds(0,0, 984, 600);
		int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	    int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		setBounds((ancho / 2) - (this.getWidth() / 2), (alto / 2) - (this.getHeight() / 2), 984, 600);
		contentPanePrincipal = new JPanel();
		contentPanePrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPanePrincipal);
		contentPanePrincipal.setLayout(null);
		
		JScrollPane scrollPaneIngVarianza = new JScrollPane();
		scrollPaneIngVarianza.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneIngVarianza.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPaneIngVarianza.setBounds(10, 11, 948, 297);
		contentPanePrincipal.add(scrollPaneIngVarianza);
		
		tableIngVarianza = new JTable(){
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
		scrollPaneIngVarianza.setColumnHeaderView(tableIngVarianza);
		scrollPaneIngVarianza.setViewportView(tableIngVarianza);
		
		//Adicionamos acciones para el comportamiento de la tabla tab y enter
		InputMap im = tableIngVarianza.getInputMap();
		//Definimos que el enter será para la siguiente celda
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Action.NextCell");
        //Definimos que el tab será para la siguiente celda
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "Action.NextCell");
        ActionMap am = tableIngVarianza.getActionMap();
        //Definimos el action map de nextcell para el jtable e implementamos clase
        if(varianzaAyuda.equals(new String("S")))
		{
        	am.put("Action.NextCell", new NextCellActioinVarianza(tableIngVarianza));
		}else
		{
			am.put("Action.NextCell", new NextCellActioinVarianzaNA(tableIngVarianza));
		}
        JButton btnConfirmarIngreso = new JButton("Confirmar Ingreso");
		//Creamos la barra de progreso que va a funcionar cuando ingresemos los inventarios
		JProgressBar progressBar = new JProgressBar();
		//Creamos el hilo encargado de llenar el ProgressBar
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
						System.out.println("ESTOY DENTRO " + cantAvance );
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
		progressBar.setBounds(125, 508, 292, 31);
		contentPanePrincipal.add(progressBar);
		btnConfirmarIngreso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//La variable que controla los ingresados
				cantAvance = 1;
				hiloProgressBar.start();
				//Realizamos la desactivación de la edición del JTable
				if(tableIngVarianza.isEditing())
				{
					tableIngVarianza.getCellEditor().stopCellEditing();
				}
				//Recorre el jtable para ver si se modifico
				int idItem;
				double cantidad;
				int controladorIngreso = 0;
				cantAvance = 20;
				//Ponemos a correr el hilo que actualizará el JProgressBar
				if(varianzaAyuda.equals(new String("S")))
				{
					for(int i = 0; i < tableIngVarianza.getRowCount(); i++)
					{
						cantAvance = 40;
						//Capturamos el idItem	
						idItem =Integer.parseInt((String)tableIngVarianza.getValueAt(i, 0));
						//Capturamos la cantidad
						try
						{
							cantidad = Double.parseDouble((String)tableIngVarianza.getValueAt(i, 8));
						}catch(Exception e)
						{
							cantidad = 0;
						}
						
						ModificadorInventario mod = new ModificadorInventario(idItem,cantidad);
						inventarioIngresar.add(mod);
					}
				}else
				{
					for(int i = 0; i < tableIngVarianza.getRowCount(); i++)
					{
						cantAvance = 40;
						//Capturamos el idItem	
						idItem =Integer.parseInt((String)tableIngVarianza.getValueAt(i, 0));
						//Capturamos la cantidad
						try
						{
							cantidad = Double.parseDouble((String)tableIngVarianza.getValueAt(i, 5));
						}catch(Exception e)
						{
							cantidad = 0;
						}
						
						ModificadorInventario mod = new ModificadorInventario(idItem,cantidad);
						inventarioIngresar.add(mod);
					}
				}
				
				
				cantAvance = 50;
					//Realizamos la invocación para la inclusión de la información de inventarios
					String observacion = textAreaObser.getText();
					int idInvVarianza = invCtrl.insertarVarianzaInventarios(inventarioIngresar, fechaSis, observacion);
					cantAvance = 90;
					if(idInvVarianza > 0)
					{
						cantAvance = 100;
						JOptionPane.showMessageDialog(null, "La Varianza " + idInvVarianza + " fue ingresado correctamente." , "Ingreso de Varianza", JOptionPane.INFORMATION_MESSAGE);
						//Si se confirma el ingreso debemos de borrar de la tabla temporal
						invCtrl.limpiarTipoInventariosTemporal(fechaSis,"V");
						dispose();
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Se tuvo inconvenientes al ingresar la Varianza" , "Error al ingresar Varianza inventarios", JOptionPane.ERROR_MESSAGE);
						cantAvance = 0;
						return;
					}
				
				
			}
		});
		btnConfirmarIngreso.setBounds(21, 437, 152, 37);
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
		btnCancelar.setBounds(208, 437, 146, 37);
		contentPanePrincipal.add(btnCancelar);
		
		JLabel lblFechaIngresoVarianza = new JLabel("FECHA INGRESO VARIANZA");
		lblFechaIngresoVarianza.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblFechaIngresoVarianza.setBounds(114, 488, 240, 14);
		contentPanePrincipal.add(lblFechaIngresoVarianza);
		
		txtFechaInventario = new JTextField();
		txtFechaInventario.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtFechaInventario.setEnabled(false);
		txtFechaInventario.setEditable(false);
		txtFechaInventario.setBounds(353, 485, 135, 20);
		contentPanePrincipal.add(txtFechaInventario);
		txtFechaInventario.setColumns(10);
		
		//Vamos a recuperar la fecha del sistema y la vamos a mostrar en el campo correspondiente
		
		txtFechaInventario.setText(fechaSis);
		
		JLabel lblImagen = new JLabel("");
		lblImagen.setBounds(577, 424, 198, 126);
		ImageIcon icon = new ImageIcon(getClass().getResource("/imagenes/LogoPizzaAmericana.png"));
		Image imagen = icon.getImage();
		ImageIcon iconoEscalado = new ImageIcon (imagen.getScaledInstance(198,126,Image.SCALE_SMOOTH));
		lblImagen.setIcon(iconoEscalado);
		contentPanePrincipal.add(lblImagen);
		
		JButton btnGuardarSinConfirmar = new JButton("Guardar sin Confirmar");
		btnGuardarSinConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//La variable que controla los ingresados
				cantAvance = 1;
				InventariosTemporal invTemp;
				ArrayList<InventariosTemporal> inventariosTemp = new ArrayList();
				hiloProgressBar.start();
				//Realizamos la desactivación de la edición del JTable
				if(tableIngVarianza.isEditing())
				{
					tableIngVarianza.getCellEditor().stopCellEditing();
				}
				//Recorre el jtable para ver si se modifico
				//Si se confirma el ingreso debemos de borrar de la tabla temporal
				invCtrl.limpiarTipoInventariosTemporal(fechaSis,"V");
				int idItem;
				double cantidad;
				int controladorIngreso = 0;
				cantAvance = 20;
				//Ponemos a correr el hilo que actualizará el JProgressBar
				if(varianzaAyuda.equals(new String("S")))
				{
					for(int i = 0; i < tableIngVarianza.getRowCount(); i++)
					{
						cantAvance = 40;
						//Capturamos el idItem	
						idItem =Integer.parseInt((String)tableIngVarianza.getValueAt(i, 0));
						//Capturamos la cantidad
						try
						{
							cantidad = Double.parseDouble((String)tableIngVarianza.getValueAt(i, 8));
						}catch(Exception e)
						{
							cantidad = 0;
						}
						
						invTemp = new InventariosTemporal(fechaSis,"V", idItem, cantidad);
						inventariosTemp.add(invTemp);
					}
				}else
				{
					for(int i = 0; i < tableIngVarianza.getRowCount(); i++)
					{
						cantAvance = 40;
						//Capturamos el idItem	
						idItem =Integer.parseInt((String)tableIngVarianza.getValueAt(i, 0));
						//Capturamos la cantidad
						try
						{
							cantidad = Double.parseDouble((String)tableIngVarianza.getValueAt(i, 5));
						}catch(Exception e)
						{
							cantidad = 0;
						}
						
						invTemp = new InventariosTemporal(fechaSis,"V", idItem, cantidad);
						inventariosTemp.add(invTemp);
					}
				}
				cantAvance = 50;
				//Realizamos la invocación para la inclusión de la información de inventarios
				boolean respuesta  = invCtrl.insertarInventariosTemp(inventariosTemp);
				cantAvance = 90;
				if(respuesta)
				{
						cantAvance = 100;
						JOptionPane.showMessageDialog(null, "Se ha guardado temporalmente la varianza." , "Ingreso Temporal de Varianza", JOptionPane.INFORMATION_MESSAGE);
						dispose();
				}
				else
				{
						JOptionPane.showMessageDialog(null, "Se tuvieron inconvenientes al ingresar los datos temporales de la Varianza" , "Error al ingresar Varianza inventarios", JOptionPane.ERROR_MESSAGE);
						cantAvance = 0;
						return;
				}
			}
		});
		btnGuardarSinConfirmar.setBackground(Color.ORANGE);
		btnGuardarSinConfirmar.setBounds(386, 437, 181, 37);
		contentPanePrincipal.add(btnGuardarSinConfirmar);
		
		JScrollPane scrollPaneObser = new JScrollPane();
		scrollPaneObser.setBounds(33, 344, 883, 69);
		contentPanePrincipal.add(scrollPaneObser);
		
		textAreaObser = new JTextArea();
		textAreaObser.setColumns(3);
		textAreaObser.setText("");
		scrollPaneObser.setViewportView(textAreaObser);
		
		JLabel lblObservacion = new JLabel("OBSERVACION");
		lblObservacion.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblObservacion.setBounds(33, 319, 181, 14);
		contentPanePrincipal.add(lblObservacion);
		if(varianzaAyuda.equals(new String("S")))
		{
			pintarResumenVarianza(fechaSis);
		}else
		{
			pintarResumenVarianzaNA(fechaSis);
		}
		
	}
	
	public void pintarResumenVarianza( String fecha)
	{
		//Se valida si existe algo en las tablas temporales
		boolean existePreVarianza = invCtrl.existeInventariosTemporal(fechaSis, "V");		
		Object[] columnsName = new Object[10];
        
		columnsName[0] = "Id Item";
		columnsName[1] = "Nombre Item";
        columnsName[2] = "Inicio";
        columnsName[3] = "Retiro";
        columnsName[4] = "Ingreso";
        columnsName[5] = "Consumo";
        columnsName[6] = "Inv Calculado";
        columnsName[7] = "A este Instante";
        columnsName[8] = "Ingrese Real";
        columnsName[9] = "Diferencia";
        ArrayList itemsResumen = new ArrayList();
        if(existePreVarianza)
        {
        	JOptionPane.showMessageDialog(null, "Los datos de la varianza fueron recuperados de un proceso anterior de guardado temporal." , "Ingreso Temporal de Varianza", JOptionPane.INFORMATION_MESSAGE);
        	itemsResumen = invCtrl.obtenerItemInventarioVarianzaTemp(fecha);
        }else
        {
        	itemsResumen = invCtrl.obtenerItemInventarioVarianza(fecha);
        }
        //Se crea el default table model y allí esperamos poder digitar los valores
		DefaultTableModel modeloItemResumen = new DefaultTableModel(){
			
			
			public boolean isCellEditable(int rowIndex,int columnIndex){
       	    	if((columnIndex <= 7) ||(columnIndex > 8) )
       	    	{
       	    		return false;
       	    	}
       	    	return true;
       	    }
			
			@Override
            public Class getColumnClass(int col) {
                // return your actual type tokens
                return getValueAt(1, col).getClass();
            }
			
			//En la definición del JTable definimos los tipos de cada una de las columnas
			 Class[] types = new Class [] {
	       	            //Defines el tipo que admitirá la COLUMNA, cada uno con el índice correspondiente
	       	            //Codigo (Integer), Cantidad (Integer), Nombre (String), Precio(Double)
	       	            java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
	       	        };
			 
       	};
       	
        modeloItemResumen.setColumnIdentifiers(columnsName);
        
        double invCalculado = 0, invInicio = 0, invIngreso = 0, invRetiro = 0, invConsumo = 0; 
		for(int y = 0; y < itemsResumen.size();y++)
		{
			String[] fila =(String[]) itemsResumen.get(y);
			invInicio = Double.parseDouble(fila[2]);
			invRetiro = Double.parseDouble(fila[3]);
			invIngreso = Double.parseDouble(fila[4]);
			invConsumo = Double.parseDouble(fila[5]);
			invCalculado = invInicio - invRetiro + invIngreso - invConsumo;
			fila[6] = Double.toString(invCalculado);
			fila[9] = Double.toString(Double.parseDouble(fila[8])- invCalculado);
			modeloItemResumen.addRow(fila);
			
		}
		tableIngVarianza.setModel(modeloItemResumen);
		setCellRender(tableIngVarianza);
		
	}
	
	/**
	 * Este método realizará la diferenciación para la varianza cuando no se quiere la varianza con todos los detalles. Varianza NO AYUDA
	 * @param fecha
	 */
	public void pintarResumenVarianzaNA( String fecha)
	{
		//Se valida si existe algo en las tablas temporales
		boolean existePreVarianza = invCtrl.existeInventariosTemporal(fechaSis, "V");		
		Object[] columnsName = new Object[6];
        
		columnsName[0] = "Id Item";
		columnsName[1] = "Nombre Item";
        columnsName[2] = "Inicio";
        columnsName[3] = "Retiro";
        columnsName[4] = "Ingreso";
        columnsName[5] = "Ingrese Real";
        ArrayList itemsResumen = new ArrayList();
        if(existePreVarianza)
        {
        	JOptionPane.showMessageDialog(null, "Los datos de la varianza fueron recuperados de un proceso anterior de guardado temporal." , "Ingreso Temporal de Varianza", JOptionPane.INFORMATION_MESSAGE);
        	itemsResumen = invCtrl.obtenerItemInventarioVarianzaTemp(fecha);
        }else
        {
        	itemsResumen = invCtrl.obtenerItemInventarioVarianza(fecha);
        }
        //Se crea el default table model y allí esperamos poder digitar los valores
		DefaultTableModel modeloItemResumen = new DefaultTableModel(){
			
			
			public boolean isCellEditable(int rowIndex,int columnIndex){
       	    	if((columnIndex <= 4) ||(columnIndex > 5) )
       	    	{
       	    		return false;
       	    	}
       	    	return true;
       	    }
			
			@Override
            public Class getColumnClass(int col) {
                // return your actual type tokens
                return getValueAt(1, col).getClass();
            }
			
			//En la definición del JTable definimos los tipos de cada una de las columnas
			 Class[] types = new Class [] {
	       	            //Defines el tipo que admitirá la COLUMNA, cada uno con el índice correspondiente
	       	            //Codigo (Integer), Cantidad (Integer), Nombre (String), Precio(Double)
	       	            java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class,java.lang.Double.class
	       	        };
			 
       	};
       	
        modeloItemResumen.setColumnIdentifiers(columnsName);
        
        double invCalculado = 0, invInicio = 0, invIngreso = 0, invRetiro = 0, invConsumo = 0; 
        String[] fila = new String[6];
		for(int y = 0; y < itemsResumen.size();y++)
		{
			String[] filaTemp =(String[]) itemsResumen.get(y);
			fila[0] = filaTemp[0];
			fila[1] = filaTemp[1];
			fila[2] = filaTemp[2];
			fila[3] = filaTemp[3];
			fila[4] = filaTemp[4];
			if(existePreVarianza)
			{
				fila[5] = filaTemp[8];
			}else
			{
				fila[5] = Double.toString(0);
			}
			modeloItemResumen.addRow(fila);
			
		}
		tableIngVarianza.setModel(modeloItemResumen);
		setCellRender(tableIngVarianza);
		
	}
	
	public void setCellRender(JTable table) {
		if(varianzaAyuda.equals(new String("S")))
		{
	        Enumeration<TableColumn> en = table.getColumnModel().getColumns();
	        while (en.hasMoreElements()) {
	            TableColumn tc = en.nextElement();
	            tc.setCellRenderer(new CellRenderIngVarianza());
	        }
		}else
		{
			System.out.println("CELL RENDER NA");
			 Enumeration<TableColumn> en = table.getColumnModel().getColumns();
		     while (en.hasMoreElements()) {
		            TableColumn tc = en.nextElement();
		            tc.setCellRenderer(new CellRenderIngVarianzaNA());
		     }
		}

    }
}
