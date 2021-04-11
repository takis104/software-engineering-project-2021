import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JPanel;

public class Secretary {

	private JFrame frmSchoolinkSecretary;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Secretary window = new Secretary();
					window.frmSchoolinkSecretary.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Secretary() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSchoolinkSecretary = new JFrame();
		frmSchoolinkSecretary.setResizable(false);
		frmSchoolinkSecretary.setTitle("SchooLink - Secretary view");
		frmSchoolinkSecretary.setBounds(100, 100, 800, 600);
		frmSchoolinkSecretary.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblNewLabel = new JLabel("\u039A\u03B1\u03BB\u03C9\u03C3\u03AE\u03BB\u03B8\u03B1\u03C4\u03B5 \u03C3\u03C4\u03BF SchooLink!");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JLabel lblNewLabel_1 = new JLabel("\u039A\u03B5\u03BD\u03C4\u03C1\u03B9\u03BA\u03CC \u03BC\u03B5\u03BD\u03BF\u03CD \u03B5\u03C0\u03B9\u03BB\u03BF\u03B3\u03CE\u03BD \u03B3\u03C1\u03B1\u03BC\u03BC\u03B1\u03C4\u03B5\u03AF\u03B1\u03C2");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		JPanel panel = new JPanel();
		
		JPanel panel_1 = new JPanel();
		
		JLabel lblNewLabel_2_1 = new JLabel("\u039B\u03BF\u03B3\u03B9\u03C3\u03C4\u03AE\u03C1\u03B9\u03BF");
		
		JButton btnNewButton_2 = new JButton("\u0394\u03B9\u03B1\u03C7\u03B5\u03AF\u03C1\u03B9\u03C3\u03B7 \u03C3\u03C5\u03BD\u03B4\u03C1\u03BF\u03BC\u03CE\u03BD");
		
		JButton btnNewButton_1_2 = new JButton("\u039C\u03B9\u03C3\u03B8\u03BF\u03B4\u03BF\u03C3\u03AF\u03B1");
		
		JButton btnNewButton_1_2_1 = new JButton("\u039B\u03B5\u03B9\u03C4\u03BF\u03C5\u03C1\u03B3\u03B9\u03BA\u03AC \u03AD\u03BE\u03BF\u03B4\u03B1");
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(btnNewButton_2, GroupLayout.PREFERRED_SIZE, 191, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_2_1)
						.addComponent(btnNewButton_1_2, GroupLayout.PREFERRED_SIZE, 191, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton_1_2_1, GroupLayout.PREFERRED_SIZE, 191, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(13, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel_2_1)
					.addGap(18)
					.addComponent(btnNewButton_2, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnNewButton_1_2, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnNewButton_1_2_1, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(37, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		
		JPanel panel_2 = new JPanel();
		
		JButton btnNewButton_3 = new JButton("\u03A0\u03C1\u03BF\u03B2\u03BF\u03BB\u03AE \u03B1\u03BD\u03B1\u03BA\u03BF\u03B9\u03BD\u03CE\u03C3\u03B5\u03C9\u03BD");
		
		JButton btnNewButton_1_3 = new JButton("\u0388\u03BA\u03B4\u03BF\u03C3\u03B7 \u03B1\u03BD\u03B1\u03BA\u03BF\u03AF\u03BD\u03C9\u03C3\u03B7\u03C2");
		
		JLabel lblNewLabel_2_2 = new JLabel("\u0391\u03BD\u03B1\u03BA\u03BF\u03B9\u03BD\u03CE\u03C3\u03B5\u03B9\u03C2");
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(lblNewLabel_2_2)
							.addContainerGap(134, Short.MAX_VALUE))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
								.addComponent(btnNewButton_3, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
								.addComponent(btnNewButton_1_3, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE))
							.addGap(14))))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel_2_2)
					.addGap(18)
					.addComponent(btnNewButton_3, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnNewButton_1_3, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(71, Short.MAX_VALUE))
		);
		panel_2.setLayout(gl_panel_2);
		
		JButton logoutbtn = new JButton("\u0388\u03BE\u03BF\u03B4\u03BF\u03C2 \u03B1\u03C0\u03CC \u03C4\u03BF \u03C3\u03CD\u03C3\u03C4\u03B7\u03BC\u03B1");
		GroupLayout groupLayout = new GroupLayout(frmSchoolinkSecretary.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 214, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 209, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(lblNewLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblNewLabel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED, 190, Short.MAX_VALUE)
							.addComponent(logoutbtn, GroupLayout.PREFERRED_SIZE, 237, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE))
						.addComponent(logoutbtn))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(216))
		);
		
		JButton btnNewButton = new JButton("\u03A0\u03C1\u03BF\u03B2\u03BF\u03BB\u03AE \u03C3\u03C4\u03BF\u03B9\u03C7\u03B5\u03AF\u03C9\u03BD \u03BC\u03B1\u03B8\u03B7\u03C4\u03CE\u03BD");
		
		JButton btnNewButton_1 = new JButton("\u03A0\u03C1\u03BF\u03B2\u03BF\u03BB\u03AE \u03C3\u03C4\u03BF\u03B9\u03C7\u03B5\u03AF\u03C9\u03BD \u03C0\u03C1\u03BF\u03C3\u03C9\u03C0\u03B9\u03BA\u03BF\u03CD");
		
		JLabel lblNewLabel_2 = new JLabel("\u0395\u03C0\u03B5\u03BE\u03B5\u03C1\u03B3\u03B1\u03C3\u03AF\u03B1 \u03B5\u03C0\u03B1\u03C6\u03CE\u03BD");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblNewLabel_2)
							.addContainerGap(153, Short.MAX_VALUE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addComponent(btnNewButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
								.addComponent(btnNewButton_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE))
							.addGap(14))))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel_2)
					.addGap(18)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(71, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		frmSchoolinkSecretary.getContentPane().setLayout(groupLayout);
		
		JMenuBar menuBar = new JMenuBar();
		frmSchoolinkSecretary.setJMenuBar(menuBar);
		
		JMenu secretaryOptions = new JMenu("\u039B\u03B5\u03B9\u03C4\u03BF\u03C5\u03C1\u03B3\u03AF\u03B5\u03C2 \u03B3\u03C1\u03B1\u03BC\u03BC\u03B1\u03C4\u03B5\u03AF\u03B1\u03C2");
		menuBar.add(secretaryOptions);
		
		JMenuItem studentContacts = new JMenuItem("\u03A3\u03C4\u03BF\u03B9\u03C7\u03B5\u03AF\u03B1 \u03B5\u03B3\u03B3\u03B5\u03B3\u03C1\u03B1\u03BC\u03BC\u03AD\u03BD\u03C9\u03BD \u03BC\u03B1\u03B8\u03B7\u03C4\u03CE\u03BD");
		secretaryOptions.add(studentContacts);
		
		JMenuItem staffContacts = new JMenuItem("\u03A3\u03C4\u03BF\u03B9\u03C7\u03B5\u03AF\u03B1 \u03C0\u03C1\u03BF\u03C3\u03C9\u03C0\u03B9\u03BA\u03BF\u03CD");
		secretaryOptions.add(staffContacts);
		
		JMenu accountingMenu = new JMenu("\u039B\u03BF\u03B3\u03B9\u03C3\u03C4\u03AE\u03C1\u03B9\u03BF");
		secretaryOptions.add(accountingMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("\u039C\u03B9\u03C3\u03B8\u03BF\u03B4\u03BF\u03C3\u03AF\u03B1");
		accountingMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("\u039B\u03B5\u03B9\u03C4\u03BF\u03C5\u03C1\u03B3\u03B9\u03BA\u03AC \u03AD\u03BE\u03BF\u03B4\u03B1");
		accountingMenu.add(mntmNewMenuItem_1);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("\u0394\u03B9\u03B1\u03C7\u03B5\u03AF\u03C1\u03B9\u03C3\u03B7 \u03C3\u03C5\u03BD\u03B4\u03C1\u03BF\u03BC\u03CE\u03BD");
		accountingMenu.add(mntmNewMenuItem_2);
		
		JMenu userOptions = new JMenu("Επιλογές χρήστη");
		menuBar.add(userOptions);
		
		JMenuItem editProfile = new JMenuItem("Επεξεργασία προφίλ");
		userOptions.add(editProfile);
	}
}
