package schoolink;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JDialog;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JTextField;
import java.sql.*;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.Icon;

@SuppressWarnings("serial")
public class Parent extends JDialog {
	List<Integer> CategoryComboIndexToId;
	JComboBox<String> parent_category;
	JTextField category_descr;
	public JFrame screen;

	
	public Parent() {	
		screen = new JFrame();
		screen.getContentPane().setBackground(new Color(0, 0, 153));
		screen.setTitle("Parent:" + db_interface.user_surname + ": " + db_interface.school_name);
		screen.setSize(new Dimension(500,500));
		
		ImageIcon bg = new ImageIcon(getClass().getResource("/images/main_bg.png"));
		Image bg_img = bg.getImage().getScaledInstance(screen.getWidth(), screen.getHeight(), Image.SCALE_DEFAULT);
		screen.setContentPane(new ImagePanel(bg_img));
		Container cnt = screen.getContentPane();
		cnt.setLayout(null);
		
		JLabel lbl1 = new JLabel("Parent :");
		lbl1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lbl1.setBounds(10, 100, 86, 14);
		lbl1.setForeground(Color.WHITE);
		cnt.add(lbl1);
		
		JButton exit_btn = new JButton((Icon) new ImageIcon(getClass().getResource("/images/exit.png")));
		exit_btn.setToolTipText("Exit");
		exit_btn.setMaximumSize(new Dimension(35, 35));
		exit_btn.setBounds(250, 400, 46, 35);
		exit_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				screen.dispose();
			}
		});
		cnt.add(exit_btn);
		
		screen.setLocationRelativeTo(null);
		screen.setVisible(true);
/*
		 super(owner,"openschool-parent : " + init_db.user_surname,true);

		db=init_db;
		CategoryComboIndexToId = new ArrayList<Integer>();
		
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Container cnt = this.getContentPane();
		cnt.setBackground(new Color(102, 0, 0));
		cnt.setLayout(null);
		
		JLabel lbl1 = new JLabel("Description :");
		lbl1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lbl1.setBounds(10, 100, 86, 14);
		lbl1.setForeground(Color.WHITE);
		cnt.add(lbl1);
		
		JLabel lbl2 = new JLabel("Parent Category :");
		lbl2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lbl2.setBounds(10, 140, 106, 14);
		lbl2.setForeground(Color.WHITE);
		cnt.add(lbl2);
		
		JTextField category_descr = new JTextField();
		category_descr.setBounds(126, 98, 247, 20);
		this.getContentPane().add(category_descr);
		category_descr.setColumns(10);
		
		parent_category = new JComboBox<String>();
		parent_category.setBounds(126, 138, 247, 20);
		parent_category.addItem("-1");
		this.getContentPane().add(parent_category);
		
		db.getCategories(parent_category, CategoryComboIndexToId);
	
		JButton save_btn = new JButton((Icon) new ImageIcon(getClass().getResource("/images/save.png")));
		save_btn.setToolTipText("Save category");
		save_btn.setMaximumSize(new Dimension(35, 35));
		save_btn.setBounds(10, 2, 46, 35);
		this.getContentPane().add(save_btn);
		
		JLabel lblName = new JLabel("Name :");
		lblName.setForeground(Color.WHITE);
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblName.setBounds(10, 60, 86, 14);
		getContentPane().add(lblName);
		
		NameTxtFld = new JTextField();
		NameTxtFld.setColumns(10);
		NameTxtFld.setBounds(126, 58, 247, 20);
		getContentPane().add(NameTxtFld);
		
		save_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Login Sucessful...");
			}
		});

		this.setSize(475, 250); 
		this.setLocationRelativeTo(null);
		this.setVisible(true);
*/
	}
}