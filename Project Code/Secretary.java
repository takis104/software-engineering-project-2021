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
		
		JButton btn1 = Cval.AddButton(screen, 1, 1, "/images/Users-icon.png", "�������");
		JButton btn2 = Cval.AddButton(screen, 1, 2, "/images/mn_im11.png", "������");
		JButton btn3 = Cval.AddButton(screen, 1, 3, "/images/mn_im10.png", "�������");
		JButton btn4 = Cval.AddButton(screen, 2, 1, "/images/mn_im12.png", "�����");
		JButton btn5 = Cval.AddButton(screen, 2, 2, "/images/mn_im16.png", "��������");
		JButton btn6 = Cval.AddButton(screen, 3, 1, "/images/mn_im03.png", "������������");
		JButton btn7 = Cval.AddButton(screen, 3, 2, "/images/mn_im18.png", "�����������");
		JButton btn8 = Cval.AddButton(screen, 3, 3, "/images/mn_im19.png", "�����������");
		JButton btn9 = Cval.AddButton(screen, 4, 1, "/images/mn_im14.png", "���������� ������");
		JButton btn10 = Cval.AddButton(screen, 2, 3, "/images/entries_book.png", "���������� ���������");
		JButton btn11 = Cval.AddButton(screen, 4, 2, "/images/clock.png", "���������� �������� ����������");
		JButton btn12 = Cval.AddButton(screen, 4, 3, "/images/salary_img.png", "����������� ������� ����������� ��� ��������");
		
		btn1.addActionListener(new ActionListener() { //users
			public void actionPerformed(ActionEvent arg0) {
				manageContacts();
			}
		});
		btn2.addActionListener(new ActionListener() { //classes
			public void actionPerformed(ActionEvent arg0) {
				showSchoolClasses();
			}
		});
		btn3.addActionListener(new ActionListener() { //subclasses
			public void actionPerformed(ActionEvent arg0) {
				showGroups();
			}
		});
		btn4.addActionListener(new ActionListener() { //expenses
			public void actionPerformed(ActionEvent arg0) {
				showExpenses();
			}
		});
		btn5.addActionListener(new ActionListener() { //payments
			public void actionPerformed(ActionEvent arg0) {
				showPayments();
			}
		});
		btn6.addActionListener(new ActionListener() { //announcements
			public void actionPerformed(ActionEvent arg0) {
				manageAnnouncements();
			}
		});
		btn7.addActionListener(new ActionListener() { //Incoming msgs
			public void actionPerformed(ActionEvent arg0) {
				showIncomingMsgs();
			}
		});
		btn8.addActionListener(new ActionListener() { //Outgoing msgs
			public void actionPerformed(ActionEvent arg0) {
				showOutgoingMsgs();
			}
		});
		btn9.addActionListener(new ActionListener() { //groups
			public void actionPerformed(ActionEvent arg0) {
				showTeams();
			}
		});
		btn10.addActionListener(new ActionListener() { //subscription management
			public void actionPerformed(ActionEvent arg0) {
				manageSubscriptions();
			}
		});
		btn11.addActionListener(new ActionListener() { //work time management
			public void actionPerformed(ActionEvent arg0) {
				manageWorkTime();
			}
		});
		btn12.addActionListener(new ActionListener() { //salary calculator management
			public void actionPerformed(ActionEvent arg0) {
				calculateSalary();
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
	
	public void manageContacts() {
		sql_from_parent = "SELECT id AS �������, surname AS �������, firstname AS �����, fathername AS ���������, mothername AS ��������� FROM "+ "users WHERE id>0 ORDER BY surname";
		new MultirowForm("�������", sql_from_parent, true, true, true, Cval.OPEN_EDIT_ROW);
	}
	
	public void showSchoolClasses() {
		sql_from_parent = "SELECT id AS �������, cname AS �����, comments AS ������,  fees AS �������� FROM "+ "classes WHERE id>0 ORDER BY cname";
		new MultirowForm("������", sql_from_parent, true, true, true, Cval.OPEN_EDIT_ROW);
	}
	
	public void showGroups() {
		sql_from_parent = "SELECT id AS �������, gname AS �����, teacher_id AS �����������,  class_id AS ����, comments AS ������ FROM groups WHERE sub_class=1 ORDER BY gname";
		new MultirowForm("�������", sql_from_parent, true, true, true, Cval.OPEN_EDIT_ROW);
	}
	
	public void showExpenses() {
		sql_from_parent = "SELECT expenses.id AS �������, expenses.expense_type AS �����, expenses.amount AS ����, expense_payment_methods.method AS �������_��������, expenses.edate AS ����������, expenses.comments AS ������ FROM expenses INNER JOIN expense_payment_methods ON expenses.payment_method = expense_payment_methods.id ORDER BY edate desc";
		new MultirowForm("�����", sql_from_parent, true, true, true, Cval.OPEN_EDIT_ROW);
	}
	
	public void showPayments() {
		sql_from_parent = "SELECT id AS �������, pdate AS ����������, amount AS ����,  user_id AS ���������, comments AS ������ FROM payments WHERE 1 ORDER BY pdate desc";
		new MultirowForm("��������", sql_from_parent, true, true, true, Cval.OPEN_EDIT_ROW);
	}
	
	public void manageAnnouncements() {
		sql_from_parent = "SELECT m.id AS �������, m.msg_date AS ����������, m.msg_subject AS ����, m.cloud_id as online_id FROM msgs as m INNER JOIN msgs_details as md on m.id= md.msg_id WHERE kind=2 and md.from_user_id = " + db_interface.user_id + " ORDER BY msg_date desc";
		System.out.println("6>" + sql_from_parent);
		new MultirowForm("������������", sql_from_parent, true, true, true, Cval.OPEN_EDITOR);
	}
	
	public void showIncomingMsgs() {
		sql_from_parent = "SELECT m.id AS �������, m.msg_date AS ����������, m.msg_subject AS ����, m.cloud_id as online_id FROM msgs as m INNER JOIN msgs_details as md on m.id= md.msg_id WHERE kind=0 and md.to_user_id = " + db_interface.user_id + " ORDER BY msg_date desc";
		System.out.println("7>" + sql_from_parent);
		new MultirowForm("�����������", sql_from_parent, false, true, true, Cval.OPEN_EDITOR);
	}
	
	public void showOutgoingMsgs() {
		sql_from_parent = "SELECT distinct(m.id) AS �������, m.msg_date AS ����������, m.msg_subject AS ����, m.cloud_id as online_id FROM msgs as m INNER JOIN msgs_details as md on m.id= md.msg_id WHERE kind=0 and md.from_user_id = " + db_interface.user_id + " ORDER BY msg_date desc";
		System.out.println("8>" + sql_from_parent);
		new MultirowForm("�����������", sql_from_parent, true, true, true,  Cval.OPEN_EDITOR);
	}
	
	public void showTeams() {
		new HandleGroups("Edit groups", screen, false);
	}
	
	public void manageSubscriptions() {
		sql_from_parent = "SELECT id AS �������, student_surname AS �������, student_name AS �����, student_class AS ����, subscription_monthly_price AS �������_������_EUR, payment_September AS �����������, payment_October AS ���������, payment_November AS ���������, payment_December AS ����������, payment_January AS ����������, payment_February AS �����������, payment_March AS �������, payment_April AS ��������, payment_May AS �����, payment_June AS �������, Comments AS ������ FROM subscriptions order by student_surname asc";
		new MultirowForm("���������� ���������", sql_from_parent, true, true, true, Cval.OPEN_EDIT_ROW);
	}
	
	public void manageWorkTime() {
		sql_from_parent = "SELECT id AS �������, employee_surname AS �������, employee_name AS �����, work_shift_date AS ����������, work_time AS ������_��������, hour_cost AS ������_����, payment_status AS ���������_�������� FROM work_time_salary ORDER BY work_shift_date DESC;";
		new MultirowForm("���������� �������� ����������", sql_from_parent, true, true, true, Cval.OPEN_EDIT_ROW);
	}
	
	public void calculateSalary() {
		sql_from_parent = "SELECT id AS �������, employee_surname AS �������, employee_name AS �����, MONTH(work_shift_date) AS �����, SUM(work_time*hour_cost) AS ������ FROM work_time_salary WHERE payment_status = '���' GROUP BY employee_surname, MONTH(work_shift_date) order by employee_surname asc, MONTH(work_shift_date) desc";
		new MultirowForm("���������� ��� ��������", sql_from_parent, false, true, true, Cval.OPEN_EDIT_ROW);
	}
}