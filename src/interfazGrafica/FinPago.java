package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.awt.event.ActionListener;
import javax.swing.JTextField;

public class FinPago extends JFrame {

	private JPanel contentenorFinPago;
	private final Action action = new SwingAction();
	private JTextField displayPago;
	
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

	/**
	 * Create the frame.
	 */
	public FinPago() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 724, 474);
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
		btnNum_1.setBounds(180, 260, 70, 70);
		contentenorFinPago.add(btnNum_1);
		
		JButton btnNum_2 = new JButton("2");
		btnNum_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayPago.setText(displayPago.getText()+"2");
			}
		});
		btnNum_2.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_2.setBounds(260, 260, 70, 70);
		contentenorFinPago.add(btnNum_2);
		
		JButton btnNum_3 = new JButton("3");
		btnNum_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayPago.setText(displayPago.getText()+"3");
			}
		});
		btnNum_3.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_3.setBounds(340, 260, 70, 70);
		contentenorFinPago.add(btnNum_3);
		
		JButton btnNum_4 = new JButton("4");
		btnNum_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayPago.setText(displayPago.getText()+"4");
			}
		});
		btnNum_4.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_4.setBounds(180, 180, 70, 70);
		contentenorFinPago.add(btnNum_4);
		
		JButton btnNum_5 = new JButton("5");
		btnNum_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayPago.setText(displayPago.getText()+"5");
			}
		});
		btnNum_5.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_5.setBounds(260, 180, 70, 70);
		contentenorFinPago.add(btnNum_5);
		
		JButton btnNum_6 = new JButton("6");
		btnNum_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayPago.setText(displayPago.getText()+"6");
			}
		});
		btnNum_6.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_6.setBounds(340, 180, 70, 70);
		contentenorFinPago.add(btnNum_6);
		
		JButton butNum_7 = new JButton("7");
		butNum_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				displayPago.setText(displayPago.getText()+"7");
			}
		});
		butNum_7.setFont(new Font("Calibri", Font.BOLD, 24));
		butNum_7.setBounds(180, 100, 70, 70);
		contentenorFinPago.add(butNum_7);
		
		JButton butNum_8 = new JButton("8");
		butNum_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayPago.setText(displayPago.getText()+"8");
			}
		});
		butNum_8.setFont(new Font("Calibri", Font.BOLD, 24));
		butNum_8.setBounds(260, 100, 70, 70);
		contentenorFinPago.add(butNum_8);
		
		JButton btnNum_9 = new JButton("9");
		btnNum_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayPago.setText(displayPago.getText()+"9");
			}
		});
		btnNum_9.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_9.setBounds(340, 100, 70, 70);
		contentenorFinPago.add(btnNum_9);
		
		JButton btnNum_0 = new JButton("0");
		btnNum_0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayPago.setText(displayPago.getText()+"0");
			}
		});
		btnNum_0.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_0.setBounds(180, 340, 70, 70);
		contentenorFinPago.add(btnNum_0);
		
		JButton btnNum_000 = new JButton("000");
		btnNum_000.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayPago.setText(displayPago.getText()+"000");
			}
		});
		btnNum_000.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_000.setBounds(260, 340, 70, 70);
		contentenorFinPago.add(btnNum_000);
		
		JButton btnPoint = new JButton(".");
		btnPoint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayPago.setText(displayPago.getText()+".");
			}
		});
		btnPoint.setFont(new Font("Calibri", Font.BOLD, 24));
		btnPoint.setBounds(340, 340, 70, 70);
		contentenorFinPago.add(btnPoint);
		
		displayPago = new JTextField();
		displayPago.setText("$ ");
		displayPago.setFont(new Font("Calibri", Font.BOLD, 40));
		displayPago.setForeground(Color.YELLOW);
		displayPago.setBackground(Color.BLACK);
		displayPago.setHorizontalAlignment(SwingConstants.RIGHT);
		displayPago.setBounds(180, 27, 230, 70);
		contentenorFinPago.add(displayPago);
		displayPago.setColumns(10);
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
