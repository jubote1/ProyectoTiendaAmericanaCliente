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

public class VentIngresarVarianza extends JFrame {

	private JPanel contentPanePrincipal;
	private JTable tableIngVarianza;
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
					VentIngresarVarianza frame = new VentIngresarVarianza();
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
	public VentIngresarVarianza() {
		PedidoCtrl pedCtrl = new PedidoCtrl();
		FechaSistema fecha = pedCtrl.obtenerFechasSistema();
		fechaSis = fecha.getFechaApertura();
		InventarioCtrl invCtrl = new InventarioCtrl();
		boolean ingVarianza = invCtrl.seIngresoVarianza(fechaSis);
		if(ingVarianza)
		{
			JOptionPane.showMessageDialog(null, "La varianza ya fue ingresada para la fecha Sistema" , "Error varinza ya ingresada", JOptionPane.ERROR_MESSAGE);
//			this.setVisible(false);
//			return;
		}
		setTitle("INGRESAR VARIANZA");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 450);
		contentPanePrincipal = new JPanel();
		contentPanePrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPanePrincipal);
		contentPanePrincipal.setLayout(null);
		
		JScrollPane scrollPaneIngVarianza = new JScrollPane();
		scrollPaneIngVarianza.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneIngVarianza.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPaneIngVarianza.setBounds(10, 11, 764, 297);
		contentPanePrincipal.add(scrollPaneIngVarianza);
		
		tableIngVarianza = new JTable();
		scrollPaneIngVarianza.setColumnHeaderView(tableIngVarianza);
		scrollPaneIngVarianza.setViewportView(tableIngVarianza);
		pintarResumenVarianza(fechaSis);
		JButton btnConfirmarIngreso = new JButton("Confirmar Ingreso");
		btnConfirmarIngreso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Recorre el jtable para ver si se modifico
				int idItem;
				double cantidad;
				int controladorIngreso = 0;
				for(int i = 0; i < tableIngVarianza.getRowCount(); i++)
				{
					//Capturamos el idItem	
					idItem =Integer.parseInt((String)tableIngVarianza.getValueAt(i, 0));
					//Capturamos la cantidad
					cantidad = Double.parseDouble((String)tableIngVarianza.getValueAt(i, 7));
					ModificadorInventario mod = new ModificadorInventario(idItem,cantidad);
					inventarioIngresar.add(mod);
				}
			
					//Realizamos la invocación para la inclusión de la información de inventarios
					InventarioCtrl invCtrl = new InventarioCtrl();
					int idInvVarianza = invCtrl.insertarVarianzaInventarios(inventarioIngresar, fechaSis);
					if(idInvVarianza > 0)
					{
						JOptionPane.showMessageDialog(null, "La Varianza " + idInvVarianza + " fue ingresado correctamente." , "Ingreso de Varianza", JOptionPane.INFORMATION_MESSAGE);
						
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Se tuvo inconvenientes al ingresar la Varianza" , "Error al ingresar Varianza inventarios", JOptionPane.ERROR_MESSAGE);
						return;
					}
				
				
			}
		});
		btnConfirmarIngreso.setBounds(121, 319, 152, 37);
		contentPanePrincipal.add(btnConfirmarIngreso);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				dispose();
			}
		});
		btnCancelar.setBounds(478, 319, 146, 37);
		contentPanePrincipal.add(btnCancelar);
		
		JLabel lblFechaIngresoVarianza = new JLabel("FECHA INGRESO VARIANZA");
		lblFechaIngresoVarianza.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblFechaIngresoVarianza.setBounds(110, 387, 240, 14);
		contentPanePrincipal.add(lblFechaIngresoVarianza);
		
		txtFechaInventario = new JTextField();
		txtFechaInventario.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtFechaInventario.setEnabled(false);
		txtFechaInventario.setEditable(false);
		txtFechaInventario.setBounds(429, 384, 135, 20);
		contentPanePrincipal.add(txtFechaInventario);
		txtFechaInventario.setColumns(10);
		
		//Vamos a recuperar la fecha del sistema y la vamos a mostrar en el campo correspondiente
		
		txtFechaInventario.setText(fechaSis);
	}
	
	public void pintarResumenVarianza( String fecha)
	{
		Object[] columnsName = new Object[8];
        
		columnsName[0] = "Id Item";
		columnsName[1] = "Nombre Item";
        columnsName[2] = "Inicio";
        columnsName[3] = "Retiro";
        columnsName[4] = "Ingreso";
        columnsName[5] = "Consumo";
        columnsName[6] = "A este Instante";
        columnsName[7] = "Ingrese Real";
        InventarioCtrl invCtrl = new InventarioCtrl();
		ArrayList itemsResumen = invCtrl.obtenerItemInventarioVarianza(fecha);
		DefaultTableModel modeloItemResumen = new DefaultTableModel(){
			public boolean isCellEditable(int rowIndex,int columnIndex){
       	    	if(columnIndex < 7)
       	    	{
       	    		return false;
       	    	}
       	    	return true;
       	    }
       	};
        modeloItemResumen.setColumnIdentifiers(columnsName);
		for(int y = 0; y < itemsResumen.size();y++)
		{
			String[] fila =(String[]) itemsResumen.get(y);
			modeloItemResumen.addRow(fila);
		}
		tableIngVarianza.setModel(modeloItemResumen);
	}
}
