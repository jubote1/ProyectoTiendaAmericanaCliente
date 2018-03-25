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

public class FinPago extends JFrame {

	private JPanel contentenorFinPago;

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
		
		JTextPane textPaneTotal = new JTextPane();
		textPaneTotal.setBounds(179, 11, 282, 44);
		contentenorFinPago.add(textPaneTotal);
		
		JButton btnNum_1 = new JButton("1");
		btnNum_1.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_1.setBounds(180, 240, 70, 70);
		contentenorFinPago.add(btnNum_1);
		
		JButton btnNum_2 = new JButton("2");
		btnNum_2.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_2.setBounds(260, 240, 70, 70);
		contentenorFinPago.add(btnNum_2);
		
		JButton btnNum_3 = new JButton("3");
		btnNum_3.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_3.setBounds(340, 240, 70, 70);
		contentenorFinPago.add(btnNum_3);
		
		JButton btnNum_4 = new JButton("4");
		btnNum_4.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_4.setBounds(180, 160, 70, 70);
		contentenorFinPago.add(btnNum_4);
		
		JButton btnNum_5 = new JButton("5");
		btnNum_5.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_5.setBounds(260, 160, 70, 70);
		contentenorFinPago.add(btnNum_5);
		
		JButton btnNum_6 = new JButton("6");
		btnNum_6.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_6.setBounds(340, 160, 70, 70);
		contentenorFinPago.add(btnNum_6);
		
		JButton butNum_7 = new JButton("7");
		butNum_7.setFont(new Font("Calibri", Font.BOLD, 24));
		butNum_7.setBounds(180, 80, 70, 70);
		contentenorFinPago.add(butNum_7);
		
		JButton butNum_8 = new JButton("8");
		butNum_8.setFont(new Font("Calibri", Font.BOLD, 24));
		butNum_8.setBounds(260, 80, 70, 70);
		contentenorFinPago.add(butNum_8);
		
		JButton btnNum_9 = new JButton("9");
		btnNum_9.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_9.setBounds(340, 80, 70, 70);
		contentenorFinPago.add(btnNum_9);
		
		JButton btnNum_0 = new JButton("0");
		btnNum_0.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_0.setBounds(180, 320, 70, 70);
		contentenorFinPago.add(btnNum_0);
		
		JButton btnNum_00 = new JButton("00");
		btnNum_00.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_00.setBounds(260, 320, 70, 70);
		contentenorFinPago.add(btnNum_00);
		
		JButton btnPoint = new JButton(".");
		btnPoint.setFont(new Font("Calibri", Font.BOLD, 24));
		btnPoint.setBounds(340, 320, 70, 70);
		contentenorFinPago.add(btnPoint);
	}
}
