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
import JTable.NextCellActioinRetInventarios;
import JTable.NextCellActioinVarianza;
import capaControlador.InventarioCtrl;
import capaControlador.PedidoCtrl;
import capaModelo.FechaSistema;
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

import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class VentIngresarVarianza extends JDialog {

	private JPanel contentPanePrincipal;
	private JTable tableIngVarianza;
	private JTextField txtFechaInventario;
	String fechaSis;
	ArrayList<ModificadorInventario> inventarioIngresar = new ArrayList();
	InventarioCtrl invCtrl = new InventarioCtrl(PrincipalLogueo.habilitaAuditoria);
	//Hilo para el JProgressBar
	Thread hiloProgressBar = new Thread();
	//Tendremos la definici�n de las variables de cantidadItems de Inventario y Cantidad Insertados que servir�n
	//para el JProgressBar
	private int cantidadItems = 100;
	private int cantAvance = 0 ;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentIngresarVarianza frame = new VentIngresarVarianza(null, true);
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
	public VentIngresarVarianza(java.awt.Frame parent, boolean modal) {
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
		setTitle("INGRESAR VARIANZA");
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setDefaultCloseOperation(0);
		setBounds(0,0, 984, 470);
		int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	    int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		setBounds((ancho / 2) - (this.getWidth() / 2), (alto / 2) - (this.getHeight() / 2), 984, 470);
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
			//En la creaci�n del JTable nos permite que con el double clic nos remarque el contenido y as� podamos editar m�s facil
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
		pintarResumenVarianza(fechaSis);
		//Adicionamos acciones para el comportamiento de la tabla tab y enter
		InputMap im = tableIngVarianza.getInputMap();
		//Definimos que el enter ser� para la siguiente celda
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Action.NextCell");
        //Definimos que el tab ser� para la siguiente celda
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "Action.NextCell");
        ActionMap am = tableIngVarianza.getActionMap();
        //Definimos el action map de nextcell para el jtable e implementamos clase
        am.put("Action.NextCell", new NextCellActioinVarianza(tableIngVarianza));
		
		JButton btnConfirmarIngreso = new JButton("Confirmar Ingreso");
		//Creamos la barra de progreso que va a funcionar cuando ingresemos los inventarios
		JProgressBar progressBar = new JProgressBar();
		//Creamos el hilo encargado de llenar el ProgressBar
		hiloProgressBar = new Thread()
		{
			public void run()
			{
				//Definimos los par�metros de m�nimo y m�ximo del JProgressBar
				progressBar.setMinimum(0);
				progressBar.setMaximum(cantidadItems);
				
				try
				{
					progressBar.setIndeterminate(true);
					progressBar.setStringPainted(true);
					progressBar.setBorderPainted(true);
					//El ciclo que manejar� se ejecutar� mientras la cantidad de items sea menor igual a los insertados
					// estas variables cambiar�n en el hilo principal
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
		progressBar.setBounds(124, 390, 292, 31);
		contentPanePrincipal.add(progressBar);
		btnConfirmarIngreso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//La variable que controla los ingresados
				cantAvance = 1;
				hiloProgressBar.start();
				//Realizamos la desactivaci�n de la edici�n del JTable
				if(tableIngVarianza.isEditing())
				{
					tableIngVarianza.getCellEditor().stopCellEditing();
				}
				//Recorre el jtable para ver si se modifico
				int idItem;
				double cantidad;
				int controladorIngreso = 0;
				cantAvance = 20;
				//Ponemos a correr el hilo que actualizar� el JProgressBar
				
				for(int i = 0; i < tableIngVarianza.getRowCount(); i++)
				{
					cantAvance = 40;
					//Capturamos el idItem	
					idItem =Integer.parseInt((String)tableIngVarianza.getValueAt(i, 0));
					//Capturamos la cantidad
					cantidad = Double.parseDouble((String)tableIngVarianza.getValueAt(i, 8));
					ModificadorInventario mod = new ModificadorInventario(idItem,cantidad);
					inventarioIngresar.add(mod);
				}
				cantAvance = 50;
					//Realizamos la invocaci�n para la inclusi�n de la informaci�n de inventarios
					int idInvVarianza = invCtrl.insertarVarianzaInventarios(inventarioIngresar, fechaSis);
					cantAvance = 90;
					if(idInvVarianza > 0)
					{
						cantAvance = 100;
						JOptionPane.showMessageDialog(null, "La Varianza " + idInvVarianza + " fue ingresado correctamente." , "Ingreso de Varianza", JOptionPane.INFORMATION_MESSAGE);
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
		btnConfirmarIngreso.setBounds(54, 319, 152, 37);
		contentPanePrincipal.add(btnConfirmarIngreso);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				dispose();
			}
		});
		btnCancelar.setBounds(380, 316, 146, 37);
		contentPanePrincipal.add(btnCancelar);
		
		JLabel lblFechaIngresoVarianza = new JLabel("FECHA INGRESO VARIANZA");
		lblFechaIngresoVarianza.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblFechaIngresoVarianza.setBounds(64, 373, 240, 14);
		contentPanePrincipal.add(lblFechaIngresoVarianza);
		
		txtFechaInventario = new JTextField();
		txtFechaInventario.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtFechaInventario.setEnabled(false);
		txtFechaInventario.setEditable(false);
		txtFechaInventario.setBounds(352, 367, 135, 20);
		contentPanePrincipal.add(txtFechaInventario);
		txtFechaInventario.setColumns(10);
		
		//Vamos a recuperar la fecha del sistema y la vamos a mostrar en el campo correspondiente
		
		txtFechaInventario.setText(fechaSis);
		
		JLabel lblImagen = new JLabel("");
		lblImagen.setBounds(576, 306, 198, 126);
		ImageIcon icon = new ImageIcon(getClass().getResource("/imagenes/LogoPizzaAmericana.png"));
		Image imagen = icon.getImage();
		ImageIcon iconoEscalado = new ImageIcon (imagen.getScaledInstance(198,126,Image.SCALE_SMOOTH));
		lblImagen.setIcon(iconoEscalado);
		contentPanePrincipal.add(lblImagen);
		
	}
	
	public void pintarResumenVarianza( String fecha)
	{
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
        ArrayList itemsResumen = invCtrl.obtenerItemInventarioVarianza(fecha);
		//Se crea el default table model y all� esperamos poder digitar los valores
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
			
			//En la definici�n del JTable definimos los tipos de cada una de las columnas
			 Class[] types = new Class [] {
	       	            //Defines el tipo que admitir� la COLUMNA, cada uno con el �ndice correspondiente
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
			fila[9] = Double.toString(invCalculado - Double.parseDouble(fila[7]));
			modeloItemResumen.addRow(fila);
			
		}
		tableIngVarianza.setModel(modeloItemResumen);
		setCellRender(tableIngVarianza);
		
	}
	
	public void setCellRender(JTable table) {
        Enumeration<TableColumn> en = table.getColumnModel().getColumns();
        while (en.hasMoreElements()) {
            TableColumn tc = en.nextElement();
            tc.setCellRenderer(new CellRenderIngVarianza());
        }
    }
}
