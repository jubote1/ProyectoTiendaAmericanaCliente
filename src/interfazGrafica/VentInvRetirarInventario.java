package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import capaControlador.InventarioCtrl;
import capaControlador.PedidoCtrl;
import capaModelo.FechaSistema;
import capaModelo.ModificadorInventario;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;

public class VentInvRetirarInventario extends JFrame {

	private JPanel contentPanePrincipal;
	private JTable tableRetInventarios;
	private JTextField txtFechaInventario;
	String fechaSis;
	ArrayList<ModificadorInventario> inventarioRetirar = new ArrayList();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentInvRetirarInventario frame = new VentInvRetirarInventario();
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
	public VentInvRetirarInventario() {
		setTitle("RETIRAR INVENTARIOS");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 450);
		contentPanePrincipal = new JPanel();
		contentPanePrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPanePrincipal);
		contentPanePrincipal.setLayout(null);
		
		JScrollPane scrollPaneIngInventarios = new JScrollPane();
		scrollPaneIngInventarios.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneIngInventarios.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPaneIngInventarios.setBounds(10, 11, 764, 297);
		contentPanePrincipal.add(scrollPaneIngInventarios);
		
		tableRetInventarios = new JTable();
		scrollPaneIngInventarios.setColumnHeaderView(tableRetInventarios);
		scrollPaneIngInventarios.setViewportView(tableRetInventarios);
		DefaultTableModel modelo = pintarItemsInventario();
		tableRetInventarios.setModel(modelo);
		
		JButton btnConfirmarRetiro = new JButton("Confirmar Retiro");
		btnConfirmarRetiro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Recorre el jtable para ver si se modifico
				int idItem;
				double cantidad;
				int controladorIngreso = 0;
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
				//Validamos si por lo menos hubo un ingreso
				if(controladorIngreso>0)
				{
					//Realizamos la invocación para la inclusión de la información de inventarios
					InventarioCtrl invCtrl = new InventarioCtrl();
					int idRetiro = invCtrl.insertarRetirosInventarios(inventarioRetirar, fechaSis);
					if(idRetiro  > 0)
					{
						JOptionPane.showMessageDialog(null, "El inventario " + idRetiro  + " fue retiroado correctamente." , "Retiro de Inventario", JOptionPane.INFORMATION_MESSAGE);
						
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
		btnConfirmarRetiro.setBounds(121, 319, 152, 37);
		contentPanePrincipal.add(btnConfirmarRetiro);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				dispose();
			}
		});
		btnCancelar.setBounds(478, 319, 146, 37);
		contentPanePrincipal.add(btnCancelar);
		
		JLabel lblFechaRetiroInventario = new JLabel("FECHA RETIRO INVENTARIO");
		lblFechaRetiroInventario.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblFechaRetiroInventario.setBounds(110, 387, 240, 14);
		contentPanePrincipal.add(lblFechaRetiroInventario);
		
		txtFechaInventario = new JTextField();
		txtFechaInventario.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtFechaInventario.setEnabled(false);
		txtFechaInventario.setEditable(false);
		txtFechaInventario.setBounds(429, 384, 135, 20);
		contentPanePrincipal.add(txtFechaInventario);
		txtFechaInventario.setColumns(10);
		
		//Vamos a recuperar la fecha del sistema y la vamos a mostrar en el campo correspondiente
		PedidoCtrl pedCtrl = new PedidoCtrl();
		FechaSistema fecha = pedCtrl.obtenerFechasSistema();
		fechaSis = fecha.getFechaApertura();
		txtFechaInventario.setText(fechaSis);
	}
	
	public DefaultTableModel pintarItemsInventario()
	{
		Object[] columnsName = new Object[6];
        
        columnsName[0] = "Id Item";
        columnsName[1] = "Nombre";
        columnsName[2] = "Unidad Medida";
        columnsName[3] = "Cantidad x Canasta";
        columnsName[4] = "Nombre Contenedor";
        columnsName[5] = "Cantidad Retirar";
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
		return(modelo);
		
	}
}
