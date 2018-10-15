package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import capaControlador.InventarioCtrl;
import capaControlador.PedidoCtrl;
import capaModelo.FechaSistema;
import capaModelo.ModificadorInventario;
import renderTable.CellRenderIngInventario;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.JTextField;

public class VentInvIngresarInventario extends JDialog {

	private JPanel contentPanePrincipal;
	private JTable tableIngInventarios;
	private JTextField txtFechaInventario;
	String fechaSis;
	ArrayList<ModificadorInventario> inventarioIngresar = new ArrayList();
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
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0,0, 800, 470);
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
		
		tableIngInventarios = new JTable();
		scrollPaneIngInventarios.setColumnHeaderView(tableIngInventarios);
		scrollPaneIngInventarios.setViewportView(tableIngInventarios);
		pintarItemsInventario();
		
		
		JButton btnConfirmarIngreso = new JButton("Confirmar Ingreso");
		btnConfirmarIngreso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Realizamos la desactivación de la edición del JTable
				if(tableIngInventarios.isEditing())
				{
					tableIngInventarios.getCellEditor().stopCellEditing();
				}
				//Definición de variables necesarias para el proceso
				int idItem;
				int controladorIngreso = 0;
				//Se defienen las variables cantidad de tipo string y double para capturar el valor 
				double cantidad;
				String strCantidad;
				//Realizamos validación de los datos para verificar que si son números
				for(int i = 0; i < tableIngInventarios.getRowCount(); i++)
				{
					strCantidad = (String)tableIngInventarios.getValueAt(i, 5);
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
				
				//Recorre el jtable para ver si se modifico
				for(int i = 0; i < tableIngInventarios.getRowCount(); i++)
				{
					//Capturamos el idItem	
					idItem =Integer.parseInt((String)tableIngInventarios.getValueAt(i, 0));
					//Capturamos la cantidad
					cantidad = Double.parseDouble((String)tableIngInventarios.getValueAt(i, 5));
					System.out.println(cantidad);
					if(cantidad > 0 )
					{
						//Creamos el objeto y lo ingresamos al ArrayList
						controladorIngreso++;
						ModificadorInventario mod = new ModificadorInventario(idItem,cantidad);
						inventarioIngresar.add(mod);
					}
					
					
				}
				//Validamos si por lo menos hubo un ingreso
				if(controladorIngreso>0)
				{
					//Realizamos la invocación para la inclusión de la información de inventarios
					InventarioCtrl invCtrl = new InventarioCtrl();
					int idIngreso = invCtrl.insertarIngresosInventarios(inventarioIngresar, fechaSis);
					if(idIngreso > 0)
					{
						JOptionPane.showMessageDialog(null, "El inventario " + idIngreso + " fue ingresado correctamente." , "Ingreso de Inventario", JOptionPane.INFORMATION_MESSAGE);
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
		btnConfirmarIngreso.setBounds(86, 319, 152, 37);
		contentPanePrincipal.add(btnConfirmarIngreso);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				dispose();
			}
		});
		btnCancelar.setBounds(355, 319, 146, 37);
		contentPanePrincipal.add(btnCancelar);
		
		JLabel lblFechaIngresoInventario = new JLabel("FECHA INGRESO INVENTARIO");
		lblFechaIngresoInventario.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblFechaIngresoInventario.setBounds(62, 387, 240, 14);
		contentPanePrincipal.add(lblFechaIngresoInventario);
		
		txtFechaInventario = new JTextField();
		txtFechaInventario.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtFechaInventario.setEnabled(false);
		txtFechaInventario.setEditable(false);
		txtFechaInventario.setBounds(355, 384, 135, 20);
		contentPanePrincipal.add(txtFechaInventario);
		txtFechaInventario.setColumns(10);
		
		//Vamos a recuperar la fecha del sistema y la vamos a mostrar en el campo correspondiente
		PedidoCtrl pedCtrl = new PedidoCtrl();
		FechaSistema fecha = pedCtrl.obtenerFechasSistema();
		fechaSis = fecha.getFechaApertura();
		txtFechaInventario.setText(fechaSis);
		
		JLabel lblImagen = new JLabel("");
		lblImagen.setBounds(576, 308, 198, 126);
		ImageIcon icon = new ImageIcon(getClass().getResource("/imagenes/LogoPizzaAmericana.png"));
		Image imagen = icon.getImage();
		ImageIcon iconoEscalado = new ImageIcon (imagen.getScaledInstance(198,126,Image.SCALE_SMOOTH));
		lblImagen.setIcon(iconoEscalado);
		contentPanePrincipal.add(lblImagen);
	}
	
	public void pintarItemsInventario()
	{
		Object[] columnsName = new Object[6];
        
        columnsName[0] = "Id Item";
        columnsName[1] = "Nombre";
        columnsName[2] = "Unidad Medida";
        columnsName[3] = "Cantidad x Canasta";
        columnsName[4] = "Nombre Contenedor";
        columnsName[5] = "Cantidad Ingresar";
        InventarioCtrl invCtrl = new  InventarioCtrl();
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
}
