package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
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

import capaControlador.ParametrosProductoCtrl;
import capaControlador.AutenticacionCtrl;
import capaControlador.ParametrosCtrl;
import capaModelo.Impuesto;
import capaModelo.MenuAgrupador;
import capaModelo.Parametro;

public class VentPedAdmParametros extends JDialog {

	private JPanel contentPane;
	private JTextField jTextValorParametro;
	private JTextField jTextValorNumerico;
	private JTextField jTextValorTexto;
	private JScrollPane scrollPane;
	private JTable jTableParametro;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentPedAdmParametros frame = new VentPedAdmParametros(null, true);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Método que se encarga de retornar los menus agrupadores y de pintarlos en el Jtable correspondiente la información
	 * retornada de base de datos.
	 */
	public DefaultTableModel pintarParametro()
	{
		Object[] columnsName = new Object[3];
        
        columnsName[0] = "Valor Parametro";
        columnsName[1] = "Valor Numerico";
        columnsName[2] = "Valor Texto";
        ParametrosCtrl par = new ParametrosCtrl();
		ArrayList<Object> parametros = par.obtenerParametros();
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.setColumnIdentifiers(columnsName);
		for(int y = 0; y < parametros.size();y++)
		{
			String[] fila =(String[]) parametros.get(y);
			modelo.addRow(fila);
		}
		return(modelo);
		
	}
	
	/**
	 * Create the frame.
	 * Se documentan todas las acciones  a seguir cuando se instancia el frame para el CRUD de impuestor.
	 */
	public VentPedAdmParametros(java.awt.Frame frame, boolean modal) {
		super(frame, modal);
		setTitle("ADMINISTRACI\u00D3N DE PAR\u00C1METROS");
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0,0, 773, 392);
		int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	    int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		setBounds((ancho / 2) - (this.getWidth() / 2), (alto / 2) - (this.getHeight() / 2), 773, 392);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelDatos = new JPanel();
		panelDatos.setBounds(22, 11, 699, 125);
		contentPane.add(panelDatos);
		panelDatos.setLayout(null);
		
		JLabel lblParametro = new JLabel("Valor Par\u00E1metro");
		lblParametro.setBounds(29, 11, 93, 14);
		panelDatos.add(lblParametro);
		
		JLabel lblValorTexto = new JLabel("Valor Texto");
		lblValorTexto.setBounds(29, 78, 93, 14);
		panelDatos.add(lblValorTexto);
		
		JLabel lblValorNumerico = new JLabel("Valor Num\u00E9rico");
		lblValorNumerico.setBounds(29, 46, 114, 14);
		panelDatos.add(lblValorNumerico);
		
		jTextValorTexto = new JTextField();
		jTextValorTexto.setBounds(132, 75, 171, 20);
		panelDatos.add(jTextValorTexto);
		jTextValorTexto.setColumns(100);
		
		jTextValorNumerico = new JTextField();
		jTextValorNumerico.setBounds(132, 43, 171, 20);
		panelDatos.add(jTextValorNumerico);
		jTextValorNumerico.setColumns(50);
		
		jTextValorParametro = new JTextField();
		jTextValorParametro.setBounds(132, 8, 171, 20);
		panelDatos.add(jTextValorParametro);
		jTextValorParametro.setColumns(10);
		
		//Se crea Panel que  contendrá el Jtable y los botones
		JPanel panelJtable = new JPanel();
		panelJtable.setBounds(20, 147, 701, 167);
		contentPane.add(panelJtable);
		panelJtable.setLayout(null);
		DefaultTableModel modelo = pintarParametro();
		//Adicionar manejo para el evento de seleccion
		
		
		
		//Adicionamos los botones para las acciones del GRID
		JButton btnInsertar = new JButton("Insertar");
		/**
		 * Método que implementará la acción cuando se de click sobre el botón Insertar
		 */
		btnInsertar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Se pulso botón para adicionar un nuevo Menú Agrupador
				validarDatos();
				String valorParametro = jTextValorParametro.getText();
				String valorTexto = jTextValorTexto.getText();
				int valorNumerico = Integer.parseInt(jTextValorNumerico.getText());
				ParametrosCtrl parCtrl = new ParametrosCtrl();
				Parametro par = new Parametro(valorParametro,valorNumerico, valorTexto);
				parCtrl.insertarParametro(par);
				DefaultTableModel modelo = pintarParametro();
				jTableParametro.setModel(modelo);
				//Limpiamos el contenido de los campos
				
			
			}
		});
		btnInsertar.setBounds(52, 133, 89, 23);
		panelJtable.add(btnInsertar);
		JButton btnEliminar = new JButton("Eliminar");
		//Evento para definir las acciones para eliminar
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int filaSeleccionada = jTableParametro.getSelectedRow();
				//Hacemos la validación para decidir si se elimina o no
				String parametroEliminar = (String) jTableParametro.getValueAt(filaSeleccionada, 0);
				JOptionPane.showMessageDialog(null, "Esta seguro que se desea eliminar el parámetro " +  parametroEliminar, "Eliminacion Impuesto ", JOptionPane.YES_NO_OPTION);
				ParametrosCtrl parEliminar = new ParametrosCtrl();
				parEliminar.eliminarParametro(parametroEliminar);
				DefaultTableModel modelo = pintarParametro();
				jTableParametro.setModel(modelo);
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
		
		JScrollPane scrollPaneParametros = new JScrollPane();
		scrollPaneParametros.setBounds(52, 30, 512, 92);
		panelJtable.add(scrollPaneParametros);
		// Instanciamos el jtable
		jTableParametro = new JTable();
		scrollPaneParametros.setViewportView(jTableParametro);
		jTableParametro.setForeground(Color.black);
		jTableParametro.setBorder(new LineBorder(new Color(0, 0, 0)));
		jTableParametro.setBackground(Color.WHITE);
		this.jTableParametro.setModel(modelo);
		jTextValorNumerico.setText("");
		jTextValorTexto.setText("");
		
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = jTableParametro.getSelectedRow();
				if(filaSeleccionada == -1)
				{
					JOptionPane.showMessageDialog(null, "Debe Seleccionar algún Parametro para editar " , "No ha Seleccionado para edición ", JOptionPane.ERROR_MESSAGE);
					return;
				}
				filaSeleccionada = jTableParametro.getSelectedRow();
				jTextValorParametro.setText((String)jTableParametro.getValueAt(filaSeleccionada, 0));
				jTextValorNumerico.setText((String)jTableParametro.getValueAt(filaSeleccionada, 1));
				jTextValorTexto.setText((String)jTableParametro.getValueAt(filaSeleccionada, 2));
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
					Parametro parametroEditado = new Parametro(jTextValorParametro.getText(), Integer.parseInt(jTextValorNumerico.getText()), jTextValorTexto.getText());
					ParametrosCtrl parCtrl = new ParametrosCtrl();
					boolean respuesta = parCtrl.EditarParametro(parametroEditado);
					if (respuesta)
					{
						JOptionPane.showMessageDialog(null, "Se ha editado correctamente el registro " , "Confirmación Edición", JOptionPane.OK_OPTION);
						DefaultTableModel modelo = pintarParametro();
						jTableParametro.setModel(modelo);
						jTextValorNumerico.setText("");
						jTextValorTexto.setText("");
						jTextValorParametro.setText("");
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
	String valorNumerico = jTextValorNumerico.getText();
	if(valorNumerico.length() > 0)
	{
		try{
			Integer.parseInt(valorNumerico);
			return(true);
		}catch(Exception e)
		{
			return(false);
		}
	}
	return(true);
}
}
