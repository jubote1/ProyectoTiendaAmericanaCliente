package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.EventObject;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.JTextComponent;

import JTable.CellRenderIngInventario;
import JTable.NextCellActioinRetInventarios;
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
import javax.swing.JProgressBar;

public class VentInvRetirarInventario extends JDialog {

	private JPanel contentPanePrincipal;
	private JTable tableRetInventarios;
	private JTextField txtFechaInventario;
	String fechaSis;
	ArrayList<ModificadorInventario> inventarioRetirar = new ArrayList();
	InventarioCtrl invCtrl = new InventarioCtrl(PrincipalLogueo.habilitaAuditoria);
	//Hilo para el JProgressBar
	Thread hiloProgressBar = new Thread();
	//Tendremos la definición de las variables de cantidadItems de Inventario y Cantidad Insertados que servirán
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
					VentInvRetirarInventario frame = new VentInvRetirarInventario(null, true);
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
	public VentInvRetirarInventario(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		setTitle("RETIRAR INVENTARIOS");
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setDefaultCloseOperation(0);
		setBounds(0,0, 800, 500);
		int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	    int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		setBounds((ancho / 2) - (this.getWidth() / 2), (alto / 2) - (this.getHeight() / 2), 800, 470);
		contentPanePrincipal = new JPanel();
		contentPanePrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPanePrincipal);
		contentPanePrincipal.setLayout(null);
		
		JScrollPane scrollPaneIngInventarios = new JScrollPane();
		scrollPaneIngInventarios.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneIngInventarios.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPaneIngInventarios.setBounds(10, 11, 764, 297);
		contentPanePrincipal.add(scrollPaneIngInventarios);
		
		tableRetInventarios = new JTable(){
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

		scrollPaneIngInventarios.setColumnHeaderView(tableRetInventarios);
		scrollPaneIngInventarios.setViewportView(tableRetInventarios);
		pintarItemsInventario();
		
		//Adicionamos acciones para el comportamiento de la tabla tab y enter
		InputMap im = tableRetInventarios.getInputMap();
		//Definimos que el enter será para la siguiente celda
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Action.NextCell");
        //Definimos que el tab será para la siguiente celda
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "Action.NextCell");
        ActionMap am = tableRetInventarios.getActionMap();
        //Definimos el action map de nextcell para el jtable e implementamos clase
        am.put("Action.NextCell", new NextCellActioinRetInventarios(tableRetInventarios));
		
		JButton btnConfirmarRetiro = new JButton("Confirmar Retiro");
		btnConfirmarRetiro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cantAvance = 1;
				hiloProgressBar.start();
				//Realizamos la desactivación de la edición del JTable
				if(tableRetInventarios.isEditing())
				{
					tableRetInventarios.getCellEditor().stopCellEditing();
				}
				cantAvance = 10;
				//Definición de variables necesarias para el proceso
				int idItem;
				int controladorIngreso = 0;
				//Se defienen las variables cantidad de tipo string y double para capturar el valor 
				double cantidad;
				String strCantidad;
				
				//Realizamos validación de los datos para verificar que si son números
				for(int i = 0; i < tableRetInventarios.getRowCount(); i++)
				{
					strCantidad = (String)tableRetInventarios.getValueAt(i, 5);
					try
					{
						cantidad = Double.parseDouble(strCantidad);
					}catch(Exception e)
					{
						//En caso que se dispare la excepción arrojamos el mensaje de error y retornamos, no confitunarmos con el procesamiento.
						JOptionPane.showMessageDialog(null, "Alguna de las cantidades no cumple con las características de número por favor corrija " , "Error Validación Datos", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				cantAvance = 30;
				for(int i = 0; i < tableRetInventarios.getRowCount(); i++)
				{
					
					//Capturamos el idItem	
					idItem =Integer.parseInt((String)tableRetInventarios.getValueAt(i, 0));
					//Capturamos la cantidad
					cantidad = Double.parseDouble((String)tableRetInventarios.getValueAt(i, 5));
					System.out.println(cantidad);
					if(cantidad > 0 )
					{
						//Creamos el objeto y lo ingresamos al ArrayList
						controladorIngreso++;
						ModificadorInventario mod = new ModificadorInventario(idItem,cantidad);
						inventarioRetirar.add(mod);
					}
					
					
				}
				cantAvance = 50;
				//Validamos si por lo menos hubo un ingreso
				if(controladorIngreso>0)
				{
					//Realizamos la invocación para la inclusión de la información de inventarios
					int idRetiro = invCtrl.insertarRetirosInventarios(inventarioRetirar, fechaSis);
					if(idRetiro  > 0)
					{
						cantAvance = 100;
						JOptionPane.showMessageDialog(null, "El inventario " + idRetiro  + " fue retiroado correctamente." , "Retiro de Inventario", JOptionPane.INFORMATION_MESSAGE);
						dispose();
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Se tuvo inconvenientes al retirar el inventario" , "Error al Retirar inventarios", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "No retiro ningun Item al inventario, si desea salir de clic sobre el botón cancelar " , "No Ha retirado nada al Inventario", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		});
		btnConfirmarRetiro.setBounds(80, 319, 152, 37);
		contentPanePrincipal.add(btnConfirmarRetiro);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				dispose();
			}
		});
		btnCancelar.setBounds(382, 319, 146, 37);
		contentPanePrincipal.add(btnCancelar);
		
		JLabel lblFechaRetiroInventario = new JLabel("FECHA RETIRO INVENTARIO");
		lblFechaRetiroInventario.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblFechaRetiroInventario.setBounds(121, 370, 240, 14);
		contentPanePrincipal.add(lblFechaRetiroInventario);
		
		txtFechaInventario = new JTextField();
		txtFechaInventario.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtFechaInventario.setEnabled(false);
		txtFechaInventario.setEditable(false);
		txtFechaInventario.setBounds(393, 367, 135, 20);
		contentPanePrincipal.add(txtFechaInventario);
		txtFechaInventario.setColumns(10);
		
		//Vamos a recuperar la fecha del sistema y la vamos a mostrar en el campo correspondiente
		PedidoCtrl pedCtrl = new PedidoCtrl(PrincipalLogueo.habilitaAuditoria);
		FechaSistema fecha = pedCtrl.obtenerFechasSistema();
		fechaSis = fecha.getFechaApertura();
		txtFechaInventario.setText(fechaSis);
		
		JLabel lblImagen = new JLabel("");
		lblImagen.setBounds(574, 306, 198, 126);
		ImageIcon icon = new ImageIcon(getClass().getResource("/imagenes/LogoPizzaAmericana.png"));
		Image imagen = icon.getImage();
		ImageIcon iconoEscalado = new ImageIcon (imagen.getScaledInstance(198,126,Image.SCALE_SMOOTH));
		lblImagen.setIcon(iconoEscalado);
		contentPanePrincipal.add(lblImagen);
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
		progressBar.setBounds(91, 395, 292, 31);
		contentPanePrincipal.add(progressBar);
	}
	
	public void pintarItemsInventario()
	{
		Object[] columnsName = new Object[6];
        
        columnsName[0] = "Id Item";
        columnsName[1] = "Nombre";
        columnsName[2] = "Unidad Medida";
        columnsName[3] = "Cantidad x Canasta";
        columnsName[4] = "Nombre Contenedor";
        columnsName[5] = "Cantidad Retirar";
        ArrayList<Object> itemsIng = new ArrayList();
        itemsIng = invCtrl.obtenerItemInventarioIngresar();
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
		tableRetInventarios.setModel(modelo);
		setCellRender(tableRetInventarios);
		//tableRetInventarios.setCellEditor(anEditor);
	}
	
	public void setCellRender(JTable table) {
        Enumeration<TableColumn> en = table.getColumnModel().getColumns();
        while (en.hasMoreElements()) {
            TableColumn tc = en.nextElement();
            tc.setCellRenderer(new CellRenderIngInventario());
        }
    }
}
