package schoolink;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JDialog;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JTextField;
import java.sql.*;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.Icon;

@SuppressWarnings("serial")
public class Pupil extends JDialog {
	List<Integer> CategoryComboIndexToId;
	JComboBox<String> parent_category;
	JTextField category_descr;
	public JFrame screen;
	int button_size;
	String sql_from_parent;

	
	public Pupil() {	
		screen = new JFrame();
		db_interface.user_role = "���������";
		screen.setTitle("Pupil:" + db_interface.user_surname + ":" + db_interface.school_name);
		ImageIcon bg = new ImageIcon(getClass().getResource("/images/main_bg.png"));
		screen.setSize(new Dimension(Cval.ScreenWidth,Cval.ScreenHeight));
		Image bg_img = bg.getImage().getScaledInstance(screen.getWidth(), screen.getHeight(), Image.SCALE_DEFAULT);
		screen.setContentPane(new ImagePanel(bg_img));


		GridBagLayout grid = new GridBagLayout();
		screen.setLayout(grid);
		GridBagConstraints gbc1 = new GridBagConstraints();
		gbc1.gridx = 6;  gbc1.gridy = 6; screen.add(new JLabel(""), gbc1);
		
		JLabel lbl1 = new JLabel(Cval.sayHello());
		lbl1.setFont(new Font("Tahoma", Font.PLAIN, Cval.TitleFontSize));
		//lbl1.setBounds(10, 100, 86, 14);
		lbl1.setForeground(Color.WHITE);
		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.gridx = 0;  gbc2.gridy = 0; gbc2.gridwidth=2;
		screen.add(lbl1, gbc2);
		
		JButton btn1 = Cval.AddButton(screen, 1, 1, "/images/mn_im10.png", "�������� ���������");
		JButton btn2 = Cval.AddButton(screen, 1, 2, "/images/mn_im07.png", "��������");
		JButton btn3 = Cval.AddButton(screen, 1, 3, "/images/mn_im05.png", "������������");
		JButton btn4 = Cval.AddButton(screen, 2, 1, "/images/mn_im02.png", "�����������");
		JButton btn5 = Cval.AddButton(screen, 2, 2, "/images/mn_im06.png", "�����������");
		

		btn1.addActionListener(new ActionListener() { //new message
			public void actionPerformed(ActionEvent arg0) {
				new MessageFx(Cval.ScreenWidth, Cval.ScreenHeight, "������",null, 0);
			}
		});
		
		btn2.addActionListener(new ActionListener() { //my assignments
			public void actionPerformed(ActionEvent arg0) { 
				sql_from_parent = "SELECT m.id AS �������, m.msg_date AS ����������, m.msg_subject AS ����, m.cloud_id as online_id FROM msgs as m INNER JOIN msgs_details as md on m.id= md.msg_id WHERE kind=1 and md.to_user_id = " + db_interface.user_id + " ORDER BY msg_date desc";				
				new MultirowForm("�� �������� ���", sql_from_parent, true, true, true, Cval.OPEN_EDITOR);			
			}
		});
		
		btn3.addActionListener(new ActionListener() { //announcements
			public void actionPerformed(ActionEvent arg0) {
				Cval.id_from_parent.push(db_interface.user_id);
				sql_from_parent = "SELECT m.id AS �������, m.msg_date AS ����������, m.msg_subject AS ����, m.cloud_id as online_id FROM msgs as m INNER JOIN msgs_details as md on m.id= md.msg_id WHERE kind=2 and md.to_user_id = " + db_interface.user_id + " ORDER BY msg_date desc";
				new MultirowForm("������������", sql_from_parent, false, true, true, Cval.OPEN_EDITOR);			
			}
		});
		
		btn4.addActionListener(new ActionListener() { //Incomimg msgs
			public void actionPerformed(ActionEvent arg0) {
				sql_from_parent = "SELECT m.id AS �������, m.msg_date AS ����������, m.msg_subject AS ����, m.cloud_id as online_id FROM msgs as m INNER JOIN msgs_details as md on m.id= md.msg_id WHERE kind=2 and md.to_user_id = " + db_interface.user_id + " ORDER BY msg_date desc";
				new MultirowForm("�����������", sql_from_parent, false, true, true, Cval.OPEN_EDITOR);
			}
		});
		
		btn5.addActionListener(new ActionListener() { //Outgoing msgs
			public void actionPerformed(ActionEvent arg0) {
				sql_from_parent = "SELECT m.id AS �������, m.msg_date AS ����������, m.msg_subject AS ����, m.cloud_id as online_id FROM msgs as m INNER JOIN msgs_details as md on m.id= md.msg_id WHERE kind=0 and md.to_user_id = " + db_interface.user_id + " ORDER BY msg_date desc";
				new MultirowForm("�����������", sql_from_parent, true, true, false, Cval.OPEN_EDITOR);
			}
		});

		/*btn6.addActionListener(new ActionListener() { //Outgoing msgs
			public void actionPerformed(ActionEvent arg0) {
				sql_from_parent = "SELECT distinct(m.id) AS �������, m.msg_date AS ����������, m.msg_subject AS ����, m.cloud_id as online_id FROM msgs as m INNER JOIN msgs_details as md on m.id= md.msg_id WHERE kind=0 and md.from_user_id = " + db_interface.user_id + " ORDER BY msg_date desc";
				new MultirowForm("�����������", sql_from_parent, true, true, true,  Cval.OPEN_EDITOR);
			}
		});*/

				
		JButton exit_btn = new JButton((Icon) new ImageIcon(getClass().getResource("/images/exit.png")));
		exit_btn.setToolTipText("Exit");
		exit_btn.setMaximumSize(new Dimension(35, 35));
		exit_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				screen.dispose();
			}
		});
		GridBagConstraints gbc3 = new GridBagConstraints();
		gbc3.gridx = 5;  gbc3.gridy = 5; screen.add(exit_btn, gbc3);
		screen.setLocationRelativeTo(null);
		screen.setVisible(true);

	}
}