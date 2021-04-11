package guiApp;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextPane;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import java.awt.SystemColor;
import javax.swing.SpringLayout;

public class Student {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Student window = new Student();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Student() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(SystemColor.controlHighlight);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTextPane txtpnStudentsName = new JTextPane();
		txtpnStudentsName.setBackground(SystemColor.controlHighlight);
		txtpnStudentsName.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 16));
		txtpnStudentsName.setText("\u039A\u03B1\u03BB\u03CE\u03C2 \u03AE\u03BB\u03B8\u03B1\u03C4\u03B5 \u03C3\u03C4\u03BF SchooLink! \r\n\r\n\u039A\u03B5\u03BD\u03C4\u03C1\u03B9\u03BA\u03CC \u039C\u03B5\u03BD\u03BF\u03CD \u0395\u03C0\u03B9\u03BB\u03BF\u03B3\u03CE\u03BD \u039C\u03B1\u03B8\u03B7\u03C4\u03AE");
		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.setBackground(Color.BLUE);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\User\\Desktop\\\u03A0\u03B1\u03BD\u03B5\u03C0\u03B9\u03C3\u03C4\u03AE\u03BC\u03B9\u03BF\\8\u03BF \u03B5\u03BE\u03AC\u03BC\u03B7\u03BD\u03BF\\\u03A4\u03B5\u03C7\u03BD\u03BF\u03BB\u03BF\u03B3\u03AF\u03B1 \u039B\u03BF\u03B3\u03B9\u03C3\u03BC\u03B9\u03BA\u03BF\u03CD\\img\\homework14.png"));
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\User\\Desktop\\\u03A0\u03B1\u03BD\u03B5\u03C0\u03B9\u03C3\u03C4\u03AE\u03BC\u03B9\u03BF\\8\u03BF \u03B5\u03BE\u03AC\u03BC\u03B7\u03BD\u03BF\\\u03A4\u03B5\u03C7\u03BD\u03BF\u03BB\u03BF\u03B3\u03AF\u03B1 \u039B\u03BF\u03B3\u03B9\u03C3\u03BC\u03B9\u03BA\u03BF\u03CD\\img\\library.png"));
		
		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setIcon(new ImageIcon("C:\\Users\\User\\Desktop\\\u03A0\u03B1\u03BD\u03B5\u03C0\u03B9\u03C3\u03C4\u03AE\u03BC\u03B9\u03BF\\8\u03BF \u03B5\u03BE\u03AC\u03BC\u03B7\u03BD\u03BF\\\u03A4\u03B5\u03C7\u03BD\u03BF\u03BB\u03BF\u03B3\u03AF\u03B1 \u039B\u03BF\u03B3\u03B9\u03C3\u03BC\u03B9\u03BA\u03BF\u03CD\\img\\announcment1.png"));
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel lblNewLabel_5 = new JLabel("New label");
		lblNewLabel_5.setIcon(new ImageIcon("C:\\Users\\User\\Desktop\\\u03A0\u03B1\u03BD\u03B5\u03C0\u03B9\u03C3\u03C4\u03AE\u03BC\u03B9\u03BF\\8\u03BF \u03B5\u03BE\u03AC\u03BC\u03B7\u03BD\u03BF\\\u03A4\u03B5\u03C7\u03BD\u03BF\u03BB\u03BF\u03B3\u03AF\u03B1 \u039B\u03BF\u03B3\u03B9\u03C3\u03BC\u03B9\u03BA\u03BF\u03CD\\img\\email1.png"));
		SpringLayout springLayout = new SpringLayout();
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel_5, 180, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel_5, 162, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, lblNewLabel_5, 247, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblNewLabel_5, 261, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel_4, 180, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel_4, 15, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, lblNewLabel_4, 253, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblNewLabel_4, 122, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel, 96, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel, 140, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, lblNewLabel, 174, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblNewLabel, 274, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel_1, 72, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel_1, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, lblNewLabel_1, 174, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblNewLabel_1, 122, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, btnNewButton, 0, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnNewButton, 0, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, txtpnStudentsName, 0, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, txtpnStudentsName, 91, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, txtpnStudentsName, 369, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().setLayout(springLayout);
		frame.getContentPane().add(txtpnStudentsName);
		frame.getContentPane().add(btnNewButton);
		frame.getContentPane().add(lblNewLabel_1);
		frame.getContentPane().add(lblNewLabel);
		frame.getContentPane().add(lblNewLabel_4);
		frame.getContentPane().add(lblNewLabel_5);
	}

}
