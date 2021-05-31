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
		
		JButton btn1 = Cval.AddButton(screen, 1, 1, "/images/mn_im04.png", "Χρήστες");
		JButton btn2 = Cval.AddButton(screen, 1, 2, "/images/mn_im05.png", "Τάξεις");
		JButton btn3 = Cval.AddButton(screen, 1, 3, "/images/mn_im09.png", "Τμήματα");
		JButton btn4 = Cval.AddButton(screen, 2, 1, "/images/mn_im12.png", "Έξοδα");
		JButton btn5 = Cval.AddButton(screen, 2, 2, "/images/mn_im16.png", "Πληρωμές");
		JButton btn6 = Cval.AddButton(screen, 3, 1, "/images/mn_im11.png", "Ανακοινώσεις");
		JButton btn7 = Cval.AddButton(screen, 3, 2, "/images/mn_im14.png", "Εισερχόμενα");
		JButton btn8 = Cval.AddButton(screen, 3, 3, "/images/mn_im03.png", "Απεσταλμένα");
		JButton btn9 = Cval.AddButton(screen, 4, 1, "/images/mn_im02.png", "Διαχείριση ομάδων");
		JButton btn10 = Cval.AddButton(screen, 2, 3, "/images/entries_book.png", "Διαχείριση Συνδρομών");
		
		btn1.addActionListener(new ActionListener() { //users
			public void actionPerformed(ActionEvent arg0) {
				sql_from_parent = "SELECT id AS Κωδικός, surname AS Επώνυμο, firstname AS Όνομα, fathername AS Πατρώνυμο, mothername AS Μητρώνυμο FROM "+ "users WHERE id>0 ORDER BY surname";
				new MultirowForm("Χρήστες", sql_from_parent, true, true, true, Cval.OPEN_EDIT_ROW);
			}
		});
		btn2.addActionListener(new ActionListener() { //classes
			public void actionPerformed(ActionEvent arg0) {
				sql_from_parent = "SELECT id AS Κωδικός, cname AS Όνομα, comments AS Σχόλια,  fees AS Δίδακτρα FROM "+ "classes WHERE id>0 ORDER BY cname";
				new MultirowForm("Τάξεις", sql_from_parent, true, true, true, Cval.OPEN_EDIT_ROW);
			}
		});
		btn3.addActionListener(new ActionListener() { //subclasses
			public void actionPerformed(ActionEvent arg0) {
				sql_from_parent = "SELECT id AS Κωδικός, gname AS Όνομα, teacher_id AS ΥπΚαθηγητής,  class_id AS Τάξη, comments AS Σχόλια FROM groups WHERE sub_class=1 ORDER BY gname";
				new MultirowForm("Τμήματα", sql_from_parent, true, true, true, Cval.OPEN_EDIT_ROW);
			}
		});
		btn4.addActionListener(new ActionListener() { //expenses
			public void actionPerformed(ActionEvent arg0) {
				sql_from_parent = "SELECT expenses.id AS Κωδικός, expenses.expense_type AS Είδος, expenses.amount AS Ποσό, expense_payment_methods.method AS Μέθοδος_Πληρωμής, expenses.edate AS Ημερομηνία, expenses.comments AS Σχόλια FROM expenses INNER JOIN expense_payment_methods ON expenses.payment_method = expense_payment_methods.id ORDER BY edate desc";
				new MultirowForm("Έξοδα", sql_from_parent, true, true, true, Cval.OPEN_EDIT_ROW);
			}
		});
		btn5.addActionListener(new ActionListener() { //payments
			public void actionPerformed(ActionEvent arg0) {
				sql_from_parent = "SELECT id AS Κωδικός, pdate AS Ημερομηνία, amount AS Ποσό,  user_id AS Κηδεμόνας, comments AS Σχόλια FROM payments WHERE 1 ORDER BY pdate desc";
				new MultirowForm("Πληρωμές", sql_from_parent, true, true, true, Cval.OPEN_EDIT_ROW);
			}
		});
		btn6.addActionListener(new ActionListener() { //announcements
			public void actionPerformed(ActionEvent arg0) {
				sql_from_parent = "SELECT m.id AS Κωδικός, m.msg_date AS Ημερομηνία, m.msg_subject AS Θέμα, m.cloud_id as online_id FROM msgs as m INNER JOIN msgs_details as md on m.id= md.msg_id WHERE kind=2 and md.from_user_id = " + db_interface.user_id + " ORDER BY msg_date desc";
				System.out.println("6>" + sql_from_parent);
				new MultirowForm("Ανακοινώσεις", sql_from_parent, true, true, true, Cval.OPEN_EDITOR);
			}
		});
		btn7.addActionListener(new ActionListener() { //Incomimg msgs
			public void actionPerformed(ActionEvent arg0) {
				sql_from_parent = "SELECT m.id AS Κωδικός, m.msg_date AS Ημερομηνία, m.msg_subject AS Θέμα, m.cloud_id as online_id FROM msgs as m INNER JOIN msgs_details as md on m.id= md.msg_id WHERE kind=0 and md.to_user_id = " + db_interface.user_id + " ORDER BY msg_date desc";
				System.out.println("7>" + sql_from_parent);
				new MultirowForm("Εισερχόμενα", sql_from_parent, false, true, true, Cval.OPEN_EDITOR);
			}
		});
		btn8.addActionListener(new ActionListener() { //Outgoing msgs
			public void actionPerformed(ActionEvent arg0) {
				sql_from_parent = "SELECT distinct(m.id) AS Κωδικός, m.msg_date AS Ημερομηνία, m.msg_subject AS Θέμα, m.cloud_id as online_id FROM msgs as m INNER JOIN msgs_details as md on m.id= md.msg_id WHERE kind=0 and md.from_user_id = " + db_interface.user_id + " ORDER BY msg_date desc";
				System.out.println("8>" + sql_from_parent);
				new MultirowForm("Απεσταλμένα", sql_from_parent, true, true, true,  Cval.OPEN_EDITOR);
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
				//new MultirowForm("Διαχείριση συνδρομών", sql_from_parent, true, true, true, Cval.OPEN_EDIT_ROW);
			}
		});
		
		JButton exit_btn = new JButton((Icon) new ImageIcon(getClass().getResource("/images/exit.png")));
		exit_btn.setToolTipText("Exit");
		exit_btn.setMaximumSize(new Dimension(35, 35));
		exit_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (JOptionPane.showConfirmDialog(null, "Θέλετε να αξιολογήσετε την εφαρμογή", "Αξιολόγηση",
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
