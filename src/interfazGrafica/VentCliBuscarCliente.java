package interfazGrafica;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;

import capaControlador.ClienteCtrl;
import capaControlador.ParametrosDireccionCtrl;
import capaModelo.Cliente;

import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class VentCliBuscarCliente extends JFrame {
	private JTable tableClienteBusqueda;
	private int idCliente;
	
	public int getIdCliente()
	{
		return(this.idCliente);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentCliBuscarCliente frame = new VentCliBuscarCliente("");
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
	public VentCliBuscarCliente(String Telefono) {
		idCliente = 0;
		setTitle("BUSCAR CLIENTE");
		setBounds(0,0, 450, 300);
		int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	    int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		setBounds((ancho / 2) - (this.getWidth() / 2), (alto / 2) - (this.getHeight() / 2), 450, 300);
		getContentPane().setLayout(null);
		JButton btnSeleccionar = new JButton("Seleccionar");
		
		btnSeleccionar.setBounds(66, 176, 89, 23);
		getContentPane().add(btnSeleccionar);
		
		JButton btnRegresar = new JButton("Regresar");
		btnRegresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentCliCliente ventCliente = new VentCliCliente(0);
				ventCliente.setVisible(true);
				dispose();
			}
		});
		btnRegresar.setBounds(248, 176, 89, 23);
		getContentPane().add(btnRegresar);
		
		tableClienteBusqueda = new JTable();
		tableClienteBusqueda.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		tableClienteBusqueda.setBounds(74, 38, 271, 100);
		getContentPane().add(tableClienteBusqueda);
		DefaultTableModel modelo = pintarClientes(Telefono);
		this.tableClienteBusqueda.setModel(modelo);
		
		
		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//Definición de las acciones cuando se seleccione un cliente
				int filaSeleccionada = tableClienteBusqueda.getSelectedRow();
				if(filaSeleccionada == -1)
				{
					JOptionPane.showMessageDialog(null, "Debe Seleccionar algún Cliente para regresar a la pantalla de clientes " , "No ha Seleccionado Cliente ", JOptionPane.ERROR_MESSAGE);
					return;
				}
				//Capturamos el idcliente seleccionado
				int idClien =  Integer.parseInt((String)tableClienteBusqueda.getValueAt(filaSeleccionada, 0));
				idCliente = idClien;
				VentCliCliente ventCliente = new VentCliCliente(idClien);
				ventCliente.setVisible(true);
				dispose();
			}
		});

	}
	
	public DefaultTableModel pintarClientes(String Telefono)
	{
		Object[] columnsName = new Object[3];
        
        columnsName[0] = "Id Cliente";
        columnsName[1] = "Nombre Cliente";
        columnsName[2] = "Direccion";
        
        ClienteCtrl clie = new ClienteCtrl();
		ArrayList<Cliente> clientes = clie.obtenerClientes(Telefono);
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.setColumnIdentifiers(columnsName);
		for(int y = 0; y < clientes.size();y++)
		{
			Cliente cliente =(Cliente) clientes.get(y);
			String[] fila = {Integer.toString(cliente.getIdcliente()), cliente.getNombres() + " " + cliente.getApellidos(), cliente.getDireccion()};
			modelo.addRow(fila);
		}
		return(modelo);
		
	}
}
