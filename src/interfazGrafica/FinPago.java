package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.html.parser.ParserDelegator;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class FinPago extends JFrame {

	private JPanel contentenorFinPago;
	private final Action action = new SwingAction();
	private JTextField displayPago;
	private JTextField txtCantidadAdeudada;
	
	public static double Efectivo = 0, Tarjeta = 0, Cambio = 0 , Total = 100000/*TomarPedidos.totalPedido*/ ;
	private JTable tablePago;
	private JTextField displayTotal;
	
		
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FinPago frame = new FinPago();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static boolean existePunto (String Valor){
		boolean resultado = false;
		int i = 0;
		do {
			if (Valor.substring(i,i+1).equals(".")) {
				resultado = true;
				i = Valor.length();
			}else {
				i++;
			}
		} while (i < Valor.length());
		
		return resultado;
	}
	
	public static String Formato (double valor){
		String StrResultado = "";
		NumberFormat Formatter = NumberFormat.getInstance(Locale.ENGLISH);
		StringBuilder StrTotal = null;
		
		Formatter.setMaximumFractionDigits(2);
		StrTotal = new StringBuilder(Formatter.format(valor));
		StrTotal.insert(0, "$ ");
		
		StrResultado = StrTotal.toString();
		
		return StrResultado;
	}
	
//	public void limiteDisplay(KeyEvent evt){
//		int limite = 12;
//		if (displayPago.getText().length() >= limite) {
//			evt.consume();
//		}
//	}

	/**
	 * Create the frame.
	 */
	public FinPago() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 961, 636);
		contentenorFinPago = new JPanel();
		contentenorFinPago.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentenorFinPago);
		contentenorFinPago.setLayout(null);
				
		JButton btnNum_1 = new JButton("1");
		btnNum_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayPago.setText(displayPago.getText()+"1");
			}
		});
		btnNum_1.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_1.setBounds(222, 371, 70, 70);
		contentenorFinPago.add(btnNum_1);
		
		JButton btnNum_2 = new JButton("2");
		btnNum_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayPago.setText(displayPago.getText()+"2");
			}
		});
		btnNum_2.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_2.setBounds(302, 371, 70, 70);
		contentenorFinPago.add(btnNum_2);
		
		JButton btnNum_3 = new JButton("3");
		btnNum_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayPago.setText(displayPago.getText()+"3");
			}
		});
		btnNum_3.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_3.setBounds(382, 371, 70, 70);
		contentenorFinPago.add(btnNum_3);
		
		JButton btnNum_4 = new JButton("4");
		btnNum_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayPago.setText(displayPago.getText()+"4");
			}
		});
		btnNum_4.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_4.setBounds(222, 292, 70, 70);
		contentenorFinPago.add(btnNum_4);
		
		JButton btnNum_5 = new JButton("5");
		btnNum_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayPago.setText(displayPago.getText()+"5");
			}
		});
		btnNum_5.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_5.setBounds(302, 292, 70, 70);
		contentenorFinPago.add(btnNum_5);
		
		JButton btnNum_6 = new JButton("6");
		btnNum_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayPago.setText(displayPago.getText()+"6");
			}
		});
		btnNum_6.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_6.setBounds(382, 292, 70, 70);
		contentenorFinPago.add(btnNum_6);
		
		JButton btnNum_7 = new JButton("7");
		btnNum_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				displayPago.setText(displayPago.getText()+"7");
			}
		});
		btnNum_7.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_7.setBounds(222, 211, 70, 70);
		contentenorFinPago.add(btnNum_7);
		
		JButton btnNum_8 = new JButton("8");
		btnNum_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayPago.setText(displayPago.getText()+"8");
			}
		});
		btnNum_8.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_8.setBounds(302, 211, 70, 70);
		contentenorFinPago.add(btnNum_8);
		
		JButton btnNum_9 = new JButton("9");
		btnNum_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayPago.setText(displayPago.getText()+"9");
			}
		});
		btnNum_9.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_9.setBounds(382, 211, 70, 70);
		contentenorFinPago.add(btnNum_9);
		
		JButton btnNum_0 = new JButton("0");
		btnNum_0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayPago.setText(displayPago.getText()+"0");
			}
		});
		btnNum_0.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_0.setBounds(222, 452, 70, 70);
		contentenorFinPago.add(btnNum_0);
		
		JButton btnNum_000 = new JButton("000");
		btnNum_000.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayPago.setText(displayPago.getText()+"000");
			}
		});
		btnNum_000.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_000.setBounds(302, 452, 70, 70);
		contentenorFinPago.add(btnNum_000);
		
		JButton btnPoint = new JButton(".");
		btnPoint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean conPunto = false;
				
				conPunto = existePunto(displayPago.getText());
				if (conPunto) {
					displayPago.setText(displayPago.getText());
				}else {
					displayPago.setText(displayPago.getText()+".");
				}							
			}
		});
		btnPoint.setFont(new Font("Calibri", Font.BOLD, 24));
		btnPoint.setBounds(382, 452, 70, 70);
		contentenorFinPago.add(btnPoint);
		
		displayPago = new JTextField();
		displayPago.setEditable(false);
		displayPago.setFont(new Font("Calibri", Font.BOLD, 30));
		displayPago.setForeground(Color.YELLOW);
		displayPago.setBackground(Color.BLACK);
		displayPago.setHorizontalAlignment(SwingConstants.RIGHT);
		displayPago.setBounds(222, 138, 230, 50);
		contentenorFinPago.add(displayPago);
		displayPago.setColumns(10);
		
		JButton btnFinalizar = new JButton("Finalizar");
		btnFinalizar.setFont(new Font("Calibri", Font.BOLD, 40));
		btnFinalizar.setBounds(733, 495, 202, 70);
		contentenorFinPago.add(btnFinalizar);
		
		txtCantidadAdeudada = new JTextField();
		txtCantidadAdeudada.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtCantidadAdeudada.setBackground(Color.YELLOW);
		txtCantidadAdeudada.setForeground(Color.RED);
		txtCantidadAdeudada.setHorizontalAlignment(SwingConstants.CENTER);
		txtCantidadAdeudada.setEditable(false);
		txtCantidadAdeudada.setText("Cantidad Adeudada");
		txtCantidadAdeudada.setBounds(222, 119, 230, 20);
		contentenorFinPago.add(txtCantidadAdeudada);
		txtCantidadAdeudada.setColumns(10);
		
		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String BorrarValor = displayPago.getText();
				if (BorrarValor.length() > 0) {
					displayPago.setText(BorrarValor.substring(0, BorrarValor.length()-1));
				}
			}
		});
		btnBorrar.setFont(new Font("Calibri", Font.BOLD, 24));
		btnBorrar.setBounds(462, 452, 100, 70);
		contentenorFinPago.add(btnBorrar);
		
		JButton btnAplicar = new JButton("Aplicar");
		btnAplicar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAplicar.setText("Aplicar");
			}
		});
		btnAplicar.setFont(new Font("Calibri", Font.BOLD, 24));
		btnAplicar.setBounds(606, 394, 202, 70);
		contentenorFinPago.add(btnAplicar);
		
		JButton btnEfectivo = new JButton("Efectivo");
		btnEfectivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAplicar.setText(btnAplicar.getText()+" "+btnEfectivo.getText());
			}
		});
		btnEfectivo.setFont(new Font("Calibri", Font.BOLD, 40));
		btnEfectivo.setBounds(606, 211, 202, 70);
		contentenorFinPago.add(btnEfectivo);
		
		JButton btnTarjeta = new JButton("Tarjeta");
		btnTarjeta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAplicar.setText(btnAplicar.getText()+" "+btnTarjeta.getText());
			}
		});
		btnTarjeta.setFont(new Font("Calibri", Font.BOLD, 40));
		btnTarjeta.setBounds(606, 292, 202, 70);
		contentenorFinPago.add(btnTarjeta);
		
		tablePago = new JTable();
		tablePago.setEnabled(false);
		tablePago.setFont(new Font("Calibri", Font.PLAIN, 20));
		tablePago.setModel(new DefaultTableModel(
			new Object[][] {
				{"Total", Formato(Total)},
				{"Efectivo", Formato(Efectivo)},
				{"Tarjeta", Formato(Tarjeta)},
				{"Cambio", Formato(Cambio)},
			},
			new String[] {
				"New column", "New column"
			}
		));
		tablePago.setBounds(10, 366, 202, 156);
		contentenorFinPago.add(tablePago);
		
		displayTotal = new JTextField();
		displayTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		displayTotal.setForeground(Color.BLUE);
		displayTotal.setFont(new Font("Calibri", Font.BOLD, 32));
		displayTotal.setEditable(false);
		displayTotal.setColumns(10);
		displayTotal.setBackground(Color.WHITE);
		displayTotal.setBounds(222, 50, 230, 50);
		contentenorFinPago.add(displayTotal);
		displayTotal.setText(Formato(Total));
		
	}
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
