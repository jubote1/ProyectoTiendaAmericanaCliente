package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import capaControlador.AutenticacionCtrl;
import capaModelo.MenuAgrupador;

import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CRUDMenuAgrupador extends JFrame {

	private JPanel contentPane;
	private JTextField jTextIDAgrupador;
	private JTextField jTextMenu;
	private JTextField jTextDescripcion;
	private JScrollPane scrollPane;
	private JTable jTableMenuAgrupador;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CRUDMenuAgrupador frame = new CRUDMenuAgrupador();
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
	public DefaultTableModel pintarMenuAgrupador()
	{
		Object[] columnsName = new Object[3];
        
        columnsName[0] = "Id ";
        columnsName[1] = "Menu";
        columnsName[2] = "Descripcion";
        AutenticacionCtrl aut = new AutenticacionCtrl();
		ArrayList<Object> menusAgrupador = aut.obtenerMenusAgrupador();
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.setColumnIdentifiers(columnsName);
		for(int y = 0; y < menusAgrupador.size();y++)
		{
			String[] fila =(String[]) menusAgrupador.get(y);
			System.out.println(fila);
			modelo.addRow(fila);
		}
		return(modelo);
		
	}
	
	/**
	 * Create the frame.
	 * Se documentan todas las acciones  a seguir cuando se instancia el frame para el CRUD de men� Agrupador.
	 */
	public CRUDMenuAgrupador() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		
		JLabel lblNewLabel = new JLabel("Id Men\u00FA Agrupador");
		lblNewLabel.setBounds(29, 11, 93, 14);
		panelDatos.add(lblNewLabel);
		
		JLabel lblDescripcion = new JLabel("Descripcion");
		lblDescripcion.setBounds(29, 78, 74, 14);
		panelDatos.add(lblDescripcion);
		
		JLabel lblMenAgrupador = new JLabel("Men\u00FA Agrupador");
		lblMenAgrupador.setBounds(29, 46, 114, 14);
		panelDatos.add(lblMenAgrupador);
		
		jTextDescripcion = new JTextField();
		jTextDescripcion.setBounds(130, 75, 430, 20);
		panelDatos.add(jTextDescripcion);
		jTextDescripcion.setColumns(100);
		
		jTextMenu = new JTextField();
		jTextMenu.setBounds(132, 43, 114, 20);
		panelDatos.add(jTextMenu);
		jTextMenu.setColumns(50);
		
		jTextIDAgrupador = new JTextField();
		jTextIDAgrupador.setBounds(132, 8, 114, 20);
		panelDatos.add(jTextIDAgrupador);
		jTextIDAgrupador.setEnabled(false);
		jTextIDAgrupador.setColumns(10);
		
		//Se crea Panel que  contendr� el Jtable y los botones
		JPanel panelJtable = new JPanel();
		panelJtable.setBounds(20, 147, 701, 167);
		contentPane.add(panelJtable);
		panelJtable.setLayout(null);
		// Instanciamos el jtable
		jTableMenuAgrupador = new JTable();
		jTableMenuAgrupador.setForeground(Color.black);
		jTableMenuAgrupador.setBounds(52, 25, 512, 58);
		panelJtable.add(jTableMenuAgrupador);
		jTableMenuAgrupador.setBorder(new LineBorder(new Color(0, 0, 0)));
		jTableMenuAgrupador.setBackground(Color.WHITE);
		DefaultTableModel modelo = pintarMenuAgrupador();
		this.jTableMenuAgrupador.setModel(modelo);
		//Adicionar manejo para el evento de seleccion
		
		
		
		//Adicionamos los botones para las acciones del GRID
		JButton btnInsertar = new JButton("Insertar");
		/**
		 * M�todo que implementar� la acci�n cuando se de click sobre el bot�n Insertar
		 */
		btnInsertar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Se pulso bot�n para adicionar un nuevo Men� Agrupador
				String menu = jTextMenu.getText();
				String descripcionMenu = jTextDescripcion.getText();
				if(menu == "")
				{
					JOptionPane.showMessageDialog(null, "Valor del campo Men� es necesario", "Falta Informaci�n", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(descripcionMenu == "")
				{
					JOptionPane.showMessageDialog(null, "Valor del campo descripci�n Men� es necesario", "Falta Informaci�n", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				MenuAgrupador menuAgrNuevo = new MenuAgrupador(0,menu,descripcionMenu); 
				AutenticacionCtrl autCtrl = new AutenticacionCtrl();
				int idMenu = autCtrl.insertarMenuAgrupador(menuAgrNuevo);
				DefaultTableModel modelo = pintarMenuAgrupador();
				jTableMenuAgrupador.setModel(modelo);
				//Limpiamos el contenido de los campos
				
			
			}
		});
		btnInsertar.setBounds(52, 133, 89, 23);
		panelJtable.add(btnInsertar);
		JButton btnEliminar = new JButton("Eliminar");
		//Evento para definir las acciones para eliminar
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int filaSeleccionada = jTableMenuAgrupador.getSelectedRow();
				//Hacemos la validaci�n para decidir si se elimina o no
				String menuEliminar = (String) jTableMenuAgrupador.getValueAt(filaSeleccionada, 1);
				int idMenu = Integer.parseInt((String)jTableMenuAgrupador.getValueAt(filaSeleccionada, 0));
				JOptionPane.showMessageDialog(null, "Esta seguro que se desea eliminar el Men� Agrupador " +  menuEliminar , "Eliminacion Men� Agrupador ", JOptionPane.YES_NO_OPTION);
				AutenticacionCtrl auten = new AutenticacionCtrl();
				auten.eliminarMenuAgrupador(idMenu);
				DefaultTableModel modelo = pintarMenuAgrupador();
				jTableMenuAgrupador.setModel(modelo);
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
		jTextMenu.setText("");
		jTextDescripcion.setText("");
		
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = jTableMenuAgrupador.getSelectedRow();
				if(filaSeleccionada == -1)
				{
					JOptionPane.showMessageDialog(null, "Debe Seleccionar alg�n Men� Agrupador para editar " , "No ha Seleccionado para edici�n ", JOptionPane.ERROR_MESSAGE);
					return;
				}
				filaSeleccionada = jTableMenuAgrupador.getSelectedRow();
				jTextIDAgrupador.setText((String)jTableMenuAgrupador.getValueAt(filaSeleccionada, 0));
				jTextMenu.setText((String)jTableMenuAgrupador.getValueAt(filaSeleccionada, 1));
				jTextDescripcion.setText((String)jTableMenuAgrupador.getValueAt(filaSeleccionada, 2));
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
					MenuAgrupador menuAgrEditado = new MenuAgrupador(Integer.parseInt(jTextIDAgrupador.getText()),jTextMenu.getText(),jTextDescripcion.getText()); 
					AutenticacionCtrl autCtrl = new AutenticacionCtrl();
					boolean respuesta = autCtrl.editarMenuAgrupador(menuAgrEditado);
					if (respuesta)
					{
						JOptionPane.showMessageDialog(null, "Se ha editado correctamente el registro " , "Confirmaci�n Edici�n", JOptionPane.OK_OPTION);
						DefaultTableModel modelo = pintarMenuAgrupador();
						jTableMenuAgrupador.setModel(modelo);
						jTextMenu.setText("");
						jTextDescripcion.setText("");
						jTextIDAgrupador.setText("");
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
	String menu = jTextMenu.getText();
	String descripcionMenu = jTextDescripcion.getText();
	if(menu == "")
	{
		JOptionPane.showMessageDialog(null, "Valor del campo Men� es necesario", "Falta Informaci�n", JOptionPane.ERROR_MESSAGE);
		return(false);
	}
	if(descripcionMenu == "")
	{
		JOptionPane.showMessageDialog(null, "Valor del campo descripci�n Men� es necesario", "Falta Informaci�n", JOptionPane.ERROR_MESSAGE);
		return(false);
	}
	
	return(true);
}

}
