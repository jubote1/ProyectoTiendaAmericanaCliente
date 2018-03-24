package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import capaControlador.AutenticacionCtrl;
import capaControlador.ParametrosProductoCtrl;
import capaModelo.Impuesto;
import capaModelo.MenuAgrupador;

public class VentCRUDImpuesto extends JFrame {

	private JPanel contentPane;
	private JTextField jTextIDImpuesto;
	private JTextField jTextDescripcion;
	private JTextField jTextValorPorcentaje;
	private JScrollPane scrollPane;
	private JTable jTableImpuesto;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentCRUDImpuesto frame = new VentCRUDImpuesto();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * M�todo que se encarga de retornar los menus agrupadores y de pintarlos en el Jtable correspondiente la informaci�n
	 * retornada de base de datos.
	 */
	public DefaultTableModel pintarImpuesto()
	{
		Object[] columnsName = new Object[3];
        
        columnsName[0] = "Id ";
        columnsName[1] = "Descripcion";
        columnsName[2] = "Valor Porcentaje";
        ParametrosProductoCtrl par = new ParametrosProductoCtrl();
		ArrayList<Object> impuestos = par.obtenerImpuestos();
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.setColumnIdentifiers(columnsName);
		for(int y = 0; y < impuestos.size();y++)
		{
			String[] fila =(String[]) impuestos.get(y);
			System.out.println(fila);
			modelo.addRow(fila);
		}
		return(modelo);
		
	}
	
	/**
	 * Create the frame.
	 * Se documentan todas las acciones  a seguir cuando se instancia el frame para el CRUD de impuestor.
	 */
	public VentCRUDImpuesto() {
		setTitle("MAESTRO DE IMPUESTOS");
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 773, 392);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Object[] columnsName = new Object[3];
        
        //columnsName[0] = "Id ";
        //columnsName[1] = "Menu";
        //columnsName[2] = "Descripcion";
        //AutenticacionCtrl aut = new AutenticacionCtrl();
		//ArrayList<Object> menusAgrupador = aut.obtenerMenusAgrupador();
		//DefaultTableModel modelo = new DefaultTableModel();
		//modelo.setColumnIdentifiers(columnsName);
		//for(int y = 0; y < menusAgrupador.size();y++)
		//{
		//	String[] fila =(String[]) menusAgrupador.get(y);
		//	modelo.addRow(fila);
		//}
		
		
		
		JPanel panelDatos = new JPanel();
		panelDatos.setBounds(22, 11, 699, 125);
		contentPane.add(panelDatos);
		panelDatos.setLayout(null);
		
		JLabel lblImpuesto = new JLabel("Id Impuesto");
		lblImpuesto.setBounds(29, 11, 93, 14);
		panelDatos.add(lblImpuesto);
		
		JLabel lblValorPorcentaje = new JLabel("Valor Porcentaje");
		lblValorPorcentaje.setBounds(29, 78, 93, 14);
		panelDatos.add(lblValorPorcentaje);
		
		JLabel lblDescripcion = new JLabel("Descripcion");
		lblDescripcion.setBounds(29, 46, 114, 14);
		panelDatos.add(lblDescripcion);
		
		jTextValorPorcentaje = new JTextField();
		jTextValorPorcentaje.setBounds(130, 75, 88, 20);
		panelDatos.add(jTextValorPorcentaje);
		jTextValorPorcentaje.setColumns(100);
		
		jTextDescripcion = new JTextField();
		jTextDescripcion.setBounds(132, 43, 430, 20);
		panelDatos.add(jTextDescripcion);
		jTextDescripcion.setColumns(50);
		
		jTextIDImpuesto = new JTextField();
		jTextIDImpuesto.setBounds(132, 8, 86, 20);
		panelDatos.add(jTextIDImpuesto);
		jTextIDImpuesto.setEnabled(false);
		jTextIDImpuesto.setColumns(10);
		
		//Se crea Panel que  contendr� el Jtable y los botones
		JPanel panelJtable = new JPanel();
		panelJtable.setBounds(20, 147, 701, 167);
		contentPane.add(panelJtable);
		panelJtable.setLayout(null);
		// Instanciamos el jtable
		jTableImpuesto = new JTable();
		jTableImpuesto.setForeground(Color.black);
		jTableImpuesto.setBounds(52, 25, 512, 58);
		panelJtable.add(jTableImpuesto);
		jTableImpuesto.setBorder(new LineBorder(new Color(0, 0, 0)));
		jTableImpuesto.setBackground(Color.WHITE);
		DefaultTableModel modelo = pintarImpuesto();
		this.jTableImpuesto.setModel(modelo);
		//Adicionar manejo para el evento de seleccion
		
		
		
		//Adicionamos los botones para las acciones del GRID
		JButton btnInsertar = new JButton("Insertar");
		/**
		 * M�todo que implementar� la acci�n cuando se de click sobre el bot�n Insertar
		 */
		btnInsertar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Se pulso bot�n para adicionar un nuevo Men� Agrupador
				validarDatos();
				String descripcion = jTextDescripcion.getText();
				double valorPorcentaje = Double.parseDouble(jTextValorPorcentaje.getText());
				Impuesto impuestoNuevo = new Impuesto(0,descripcion, valorPorcentaje); 
				ParametrosProductoCtrl parCtrl = new ParametrosProductoCtrl();
				int idImpuesto = parCtrl.insertarImpuesto(impuestoNuevo);
				DefaultTableModel modelo = pintarImpuesto();
				jTableImpuesto.setModel(modelo);
				//Limpiamos el contenido de los campos
				
			
			}
		});
		btnInsertar.setBounds(52, 133, 89, 23);
		panelJtable.add(btnInsertar);
		JButton btnEliminar = new JButton("Eliminar");
		//Evento para definir las acciones para eliminar
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int filaSeleccionada = jTableImpuesto.getSelectedRow();
				//Hacemos la validaci�n para decidir si se elimina o no
				String impuestoEliminar = (String) jTableImpuesto.getValueAt(filaSeleccionada, 1);
				int idImpuesto = Integer.parseInt((String)jTableImpuesto.getValueAt(filaSeleccionada, 0));
				JOptionPane.showMessageDialog(null, "Esta seguro que se desea eliminar el impuesto " +  impuestoEliminar , "Eliminacion Impuesto ", JOptionPane.YES_NO_OPTION);
				ParametrosProductoCtrl parEliminar = new ParametrosProductoCtrl();
				parEliminar.eliminarImpuesto(idImpuesto);
				DefaultTableModel modelo = pintarImpuesto();
				jTableImpuesto.setModel(modelo);
			}
		});
		btnEliminar.setBounds(175, 133, 89, 23);
		panelJtable.add(btnEliminar);
		
		JButton btnEditar = new JButton("Editar");
		
		btnEditar.setBounds(298, 133, 89, 23);
		panelJtable.add(btnEditar);
		
		JButton btnGrabarEdicion = new JButton("Grabar Edicion");
		
		btnGrabarEdicion.setBounds(427, 133, 123, 23);
		panelJtable.add(btnGrabarEdicion);
		btnGrabarEdicion.setEnabled(false);
		jTextDescripcion.setText("");
		jTextValorPorcentaje.setText("");
		
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = jTableImpuesto.getSelectedRow();
				if(filaSeleccionada == -1)
				{
					JOptionPane.showMessageDialog(null, "Debe Seleccionar alg�n Impuesto para editar " , "No ha Seleccionado para edici�n ", JOptionPane.ERROR_MESSAGE);
					return;
				}
				filaSeleccionada = jTableImpuesto.getSelectedRow();
				jTextIDImpuesto.setText((String)jTableImpuesto.getValueAt(filaSeleccionada, 0));
				jTextDescripcion.setText((String)jTableImpuesto.getValueAt(filaSeleccionada, 1));
				jTextValorPorcentaje.setText((String)jTableImpuesto.getValueAt(filaSeleccionada, 2));
				btnEliminar.setEnabled(false);
				btnInsertar.setEnabled(false);
				btnGrabarEdicion.setEnabled(true);
			}
		});
		
		btnGrabarEdicion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean validar = validarDatos();
				if (validar)
				{
					Impuesto impuestoEditado = new Impuesto(Integer.parseInt(jTextIDImpuesto.getText()),jTextDescripcion.getText(),Double.parseDouble(jTextValorPorcentaje.getText())); 
					ParametrosProductoCtrl parCtrl = new ParametrosProductoCtrl();
					boolean respuesta = parCtrl.editarImpuesto(impuestoEditado);
					if (respuesta)
					{
						JOptionPane.showMessageDialog(null, "Se ha editado correctamente el registro " , "Confirmaci�n Edici�n", JOptionPane.OK_OPTION);
						DefaultTableModel modelo = pintarImpuesto();
						jTableImpuesto.setModel(modelo);
						jTextDescripcion.setText("");
						jTextValorPorcentaje.setText("");
						jTextIDImpuesto.setText("");
						btnEliminar.setEnabled(true);
						btnInsertar.setEnabled(true);
						btnGrabarEdicion.setEnabled(false);
					}
				}
				
			}
		});
		
	}
	
	
public boolean validarDatos()
{
	String descripcion = jTextDescripcion.getText();
	String valorPorcentaje = jTextValorPorcentaje.getText();
	if(descripcion == "")
	{
		JOptionPane.showMessageDialog(null, "Valor del campo DESCRIPCI�N es necesario", "Falta Informaci�n", JOptionPane.ERROR_MESSAGE);
		return(false);
	}
	if(valorPorcentaje == "")
	{
		JOptionPane.showMessageDialog(null, "Valor del campo VALOR PORCENTAJE  es necesario", "Falta Informaci�n", JOptionPane.ERROR_MESSAGE);
		return(false);
	}
	
	return(true);
}


}
