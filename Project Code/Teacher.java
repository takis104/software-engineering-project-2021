package guiApp;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.SystemColor;
import javax.swing.UIManager;

public class Teacher {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Teacher window = new Teacher();
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
	public Teacher() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTextPane txtpnTeachersName = new JTextPane();
		txtpnTeachersName.setBackground(UIManager.getColor("Button.background"));
		txtpnTeachersName.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
		txtpnTeachersName.setText("\u039A\u03B1\u03BB\u03CE\u03C2 \u03AE\u03BB\u03B8\u03B1\u03C4\u03B5 \u03C3\u03C4\u03BF SchooLink! \r\n\u039A\u03B5\u03BD\u03C4\u03C1\u03B9\u03BA\u03CC \u039C\u03B5\u03BD\u03BF\u03CD \u0395\u03C0\u03B9\u03BB\u03BF\u03B3\u03CE\u03BD \u039A\u03B1\u03B8\u03B7\u03B3\u03B7\u03C4\u03AE");
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\User\\Desktop\\\u03A0\u03B1\u03BD\u03B5\u03C0\u03B9\u03C3\u03C4\u03AE\u03BC\u03B9\u03BF\\8\u03BF \u03B5\u03BE\u03AC\u03BC\u03B7\u03BD\u03BF\\\u03A4\u03B5\u03C7\u03BD\u03BF\u03BB\u03BF\u03B3\u03AF\u03B1 \u039B\u03BF\u03B3\u03B9\u03C3\u03BC\u03B9\u03BA\u03BF\u03CD\\img\\mystudents1.png"));
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\User\\Desktop\\\u03A0\u03B1\u03BD\u03B5\u03C0\u03B9\u03C3\u03C4\u03AE\u03BC\u03B9\u03BF\\8\u03BF \u03B5\u03BE\u03AC\u03BC\u03B7\u03BD\u03BF\\\u03A4\u03B5\u03C7\u03BD\u03BF\u03BB\u03BF\u03B3\u03AF\u03B1 \u039B\u03BF\u03B3\u03B9\u03C3\u03BC\u03B9\u03BA\u03BF\u03CD\\img\\homework14.png"));
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setIcon(new ImageIcon("C:\\Users\\User\\Desktop\\\u03A0\u03B1\u03BD\u03B5\u03C0\u03B9\u03C3\u03C4\u03AE\u03BC\u03B9\u03BF\\8\u03BF \u03B5\u03BE\u03AC\u03BC\u03B7\u03BD\u03BF\\\u03A4\u03B5\u03C7\u03BD\u03BF\u03BB\u03BF\u03B3\u03AF\u03B1 \u039B\u03BF\u03B3\u03B9\u03C3\u03BC\u03B9\u03BA\u03BF\u03CD\\img\\library.png"));
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon("C:\\Users\\User\\Desktop\\\u03A0\u03B1\u03BD\u03B5\u03C0\u03B9\u03C3\u03C4\u03AE\u03BC\u03B9\u03BF\\8\u03BF \u03B5\u03BE\u03AC\u03BC\u03B7\u03BD\u03BF\\\u03A4\u03B5\u03C7\u03BD\u03BF\u03BB\u03BF\u03B3\u03AF\u03B1 \u039B\u03BF\u03B3\u03B9\u03C3\u03BC\u03B9\u03BA\u03BF\u03CD\\img\\salary1.png"));
		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.setBackground(Color.BLUE);
		
		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_4.setIcon(new ImageIcon("C:\\Users\\User\\Desktop\\\u03A0\u03B1\u03BD\u03B5\u03C0\u03B9\u03C3\u03C4\u03AE\u03BC\u03B9\u03BF\\8\u03BF \u03B5\u03BE\u03AC\u03BC\u03B7\u03BD\u03BF\\\u03A4\u03B5\u03C7\u03BD\u03BF\u03BB\u03BF\u03B3\u03AF\u03B1 \u039B\u03BF\u03B3\u03B9\u03C3\u03BC\u03B9\u03BA\u03BF\u03CD\\img\\announcment1.png"));
		
		JLabel lblNewLabel_5 = new JLabel("New label");
		lblNewLabel_5.setIcon(new ImageIcon("C:\\Users\\User\\Desktop\\\u03A0\u03B1\u03BD\u03B5\u03C0\u03B9\u03C3\u03C4\u03AE\u03BC\u03B9\u03BF\\8\u03BF \u03B5\u03BE\u03AC\u03BC\u03B7\u03BD\u03BF\\\u03A4\u03B5\u03C7\u03BD\u03BF\u03BB\u03BF\u03B3\u03AF\u03B1 \u039B\u03BF\u03B3\u03B9\u03C3\u03BC\u03B9\u03BA\u03BF\u03CD\\img\\email1.png"));
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblNewLabel_1)
								.addComponent(lblNewLabel_3, GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnNewButton)
							.addPreferredGap(ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
							.addComponent(txtpnTeachersName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblNewLabel_4, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_5, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(lblNewLabel_4, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
					.addGap(49)
					.addComponent(lblNewLabel_5, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
					.addGap(84))
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnNewButton)
						.addComponent(txtpnTeachersName, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(34)
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(29)
							.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		frame.getContentPane().setLayout(groupLayout);
	}
}
