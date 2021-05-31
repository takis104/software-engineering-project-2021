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
public class Secretary extends JDialog {
	private static db_interface db;
	List<Integer> CategoryComboIndexToId;
	private JTextField NameTxtFld;
	JComboBox<String> parent_category;
	JTextField category_descr;
	public static JFrame screen;
	int button_size;
	String sql_from_parent;

	
	public Secretary() {
		screen = new JFrame();
		db_interface.user_role = "Secretary";
		screen.setTitle("Secretary:" + db_interface.user_surname + ":" + db_interface.school_name);
		ImageIcon bg = new ImageIcon(getClass().getResource("/images/main_bg.png"));
		screen.setSize(new Dimension(Cval.ScreenWidth + 100,Cval.ScreenHeight+350));
		Image bg_img = bg.getImage().getScaledInstance(screen.getWidth(), screen.getHeight(), Image.SCALE_DEFAULT);
		screen.setContentPane(new ImagePanel(bg_img));

		GridBagLayout grid = new GridBagLayout();
		screen.setLayout(grid);
		GridBagConstraints gbc1 = new GridBagConstraints();
		gbc1.gridx = 6;  gbc1.gridy = 6; screen.add(new JLabel(""), gbc1);
		
		JLabel lbl1 = new JLabel(Cval.sayHello());
		lbl1.setFont(new Font("Tahoma", Font.PLAIN, Cval.TitleFontSize));
		lbl1.setForeground(Color.WHITE);
		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.gridx = 0;  gbc2.gridy = 0; gbc2.gridwidth=3;
		screen.add(lbl1, gbc2);
		
		JButton btn1 = Cval.AddButton(screen, 1, 1, "/images/mn_im04.png", "�������");
		JButton btn2 = Cval.AddButton(screen, 1, 2, "/images/mn_im05.png", "������");
		JButton btn3 = Cval.AddButton(screen, 1, 3, "/images/mn_im09.png", "�������");
		JButton btn4 = Cval.AddButton(screen, 2, 1, "/images/mn_im12.png", "�����");
		JButton btn5 = Cval.AddButton(screen, 2, 2, "/images/mn_im16.png", "��������");
		JButton btn6 = Cval.AddButton(screen, 2, 3, "/images/mn_im11.png", "������������");
		JButton btn7 = Cval.AddButton(screen, 3, 1, "/images/mn_im14.png", "�����������");
		JButton btn8 = Cval.AddButton(screen, 3, 2, "/images/mn_im03.png", "�����������");
		JButton btn9 = Cval.AddButton(screen, 3, 3, "/images/mn_im02.png", "���������� ������");
		JButton btn10 = Cval.AddButton(screen, 4, 1, "/images/entries_book.png", "���������� ���������");
		
		btn1.addActionListener(new ActionListener() { //users
			public void actionPerformed(ActionEvent arg0) {
				sql_from_parent = "SELECT id AS �������, surname AS �������, firstname AS �����, fathername AS ���������, mothername AS ��������� FROM "+ "users WHERE id>0 ORDER BY surname";
				new MultirowForm("�������", sql_from_parent, true, true, true, Cval.OPEN_EDIT_ROW);
			}
		});
		btn2.addActionListener(new ActionListener() { //classes
			public void actionPerformed(ActionEvent arg0) {
				sql_from_parent = "SELECT id AS �������, cname AS �����, comments AS ������,  fees AS �������� FROM "+ "classes WHERE id>0 ORDER BY cname";
				new MultirowForm("������", sql_from_parent, true, true, true, Cval.OPEN_EDIT_ROW);
			}
		});
		btn3.addActionListener(new ActionListener() { //subclasses
			public void actionPerformed(ActionEvent arg0) {
				sql_from_parent = "SELECT id AS �������, gname AS �����, teacher_id AS �����������,  class_id AS ����, comments AS ������ FROM groups WHERE sub_class=1 ORDER BY gname";
				new MultirowForm("�������", sql_from_parent, true, true, true, Cval.OPEN_EDIT_ROW);
			}
		});
		btn4.addActionListener(new ActionListener() { //expenses
			public void actionPerformed(ActionEvent arg0) {
				sql_from_parent = "SELECT expenses.id AS �������, expenses.expense_type AS �����, expenses.amount AS ����, expense_payment_methods.method AS �������_��������, expenses.edate AS ����������, expenses.comments AS ������ FROM expenses INNER JOIN expense_payment_methods ON expenses.payment_method = expense_payment_methods.id ORDER BY edate desc";
				new MultirowForm("�����", sql_from_parent, true, true, true, Cval.OPEN_EDIT_ROW);
			}
		});
		btn5.addActionListener(new ActionListener() { //payments
			public void actionPerformed(ActionEvent arg0) {
				sql_from_parent = "SELECT id AS �������, pdate AS ����������, amount AS ����,  user_id AS ���������, comments AS ������ FROM payments WHERE 1 ORDER BY pdate desc";
				new MultirowForm("��������", sql_from_parent, true, true, true, Cval.OPEN_EDIT_ROW);
			}
		});
		btn6.addActionListener(new ActionListener() { //announcements
			public void actionPerformed(ActionEvent arg0) {
				sql_from_parent = "SELECT m.id AS �������, m.msg_date AS ����������, m.msg_subject AS ����, m.cloud_id as online_id FROM msgs as m INNER JOIN msgs_details as md on m.id= md.msg_id WHERE kind=2 and md.from_user_id = " + db_interface.user_id + " ORDER BY msg_date desc";
				System.out.println("6>" + sql_from_parent);
				new MultirowForm("������������", sql_from_parent, true, true, true, Cval.OPEN_EDITOR);
			}
		});
		btn7.addActionListener(new ActionListener() { //Incomimg msgs
			public void actionPerformed(ActionEvent arg0) {
				sql_from_parent = "SELECT m.id AS �������, m.msg_date AS ����������, m.msg_subject AS ����, m.cloud_id as online_id FROM msgs as m INNER JOIN msgs_details as md on m.id= md.msg_id WHERE kind=0 and md.to_user_id = " + db_interface.user_id + " ORDER BY msg_date desc";
				System.out.println("7>" + sql_from_parent);
				new MultirowForm("�����������", sql_from_parent, false, true, true, Cval.OPEN_EDITOR);
			}
		});
		btn8.addActionListener(new ActionListener() { //Outgoing msgs
			public void actionPerformed(ActionEvent arg0) {
				sql_from_parent = "SELECT distinct(m.id) AS �������, m.msg_date AS ����������, m.msg_subject AS ����, m.cloud_id as online_id FROM msgs as m INNER JOIN msgs_details as md on m.id= md.msg_id WHERE kind=0 and md.from_user_id = " + db_interface.user_id + " ORDER BY msg_date desc";
				System.out.println("8>" + sql_from_parent);
				new MultirowForm("�����������", sql_from_parent, true, true, true,  Cval.OPEN_EDITOR);
			}
		});
		btn9.addActionListener(new ActionListener() { //groups
			public void actionPerformed(ActionEvent arg0) {
				new HandleGroups("Edit groups", screen, false);
			}
		});
		btn10.addActionListener(new ActionListener() { //subscription management
			public void actionPerformed(ActionEvent arg0) {
				sql_from_parent = "";
				//new MultirowForm("���������� ���������", sql_from_parent, true, true, true, Cval.OPEN_EDIT_ROW);
			}
		});
		
		JButton exit_btn = new JButton((Icon) new ImageIcon(getClass().getResource("/images/exit.png")));
		exit_btn.setToolTipText("Exit");
		exit_btn.setMaximumSize(new Dimension(35, 35));
		exit_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (JOptionPane.showConfirmDialog(null, "������ �� ������������ ��� ��������", "����������",
				        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					new Feedback();
				} 
				screen.dispose();
			}
		});
		GridBagConstraints gbc3 = new GridBagConstraints();
		gbc3.gridx = 5;  gbc3.gridy = 5; screen.add(exit_btn, gbc3);
		screen.setLocationRelativeTo(null);
		screen.setVisible(true);

	}	
}