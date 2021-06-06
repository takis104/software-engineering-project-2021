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
public class Parent extends JDialog {
	List<Integer> CategoryComboIndexToId;
	JComboBox<String> parent_category;
	JTextField category_descr;
	public JFrame screen;
	int button_size;
	String sql_from_parent;

	
	public Parent() {	
		screen = new JFrame();
		db_interface.user_role="Κηδεμόνας";

		screen.setTitle("Κηδεμόνας:" + db_interface.user_surname + ":" + db_interface.school_name);
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
		lbl1.setForeground(Color.WHITE);
		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.gridx = 0;  gbc2.gridy = 0; gbc2.gridwidth=2;
		screen.add(lbl1, gbc2);
		
		JButton btn1 = Cval.AddButton(screen, 1, 1, "/images/mn_im10.png", "Αποστολή μηνύματος");
		JButton btn2 = Cval.AddButton(screen, 1, 2, "/images/mn_im07.png", "Πληρωμή");
		JButton btn3 = Cval.AddButton(screen, 1, 3, "/images/mn_im05.png", "Ανακοινώσεις");
		JButton btn4 = Cval.AddButton(screen, 2, 1, "/images/mn_im02.png", "Εισερχόμενα");
		JButton btn5 = Cval.AddButton(screen, 2, 2, "/images/mn_im06.png", "Απεσταλμένα");
		JButton btn6 = Cval.AddButton(screen, 2, 3, "/images/mn_im11.png", "Απουσίες παιδιών");
		

		btn1.addActionListener(new ActionListener() { //new message
			public void actionPerformed(ActionEvent arg0) {
				newMessage();
			}
		});
		
		btn2.addActionListener(new ActionListener() { //new payment
			public void actionPerformed(ActionEvent arg0) { 
				newPayment();
			}
		});
		
		btn3.addActionListener(new ActionListener() { //announcements
			public void actionPerformed(ActionEvent arg0) {
				showAnnouncements();
			}
		});
		
		btn4.addActionListener(new ActionListener() { //Incoming msgs
			public void actionPerformed(ActionEvent arg0) {
				showIncomingMsgs();
			}
		});
		
		btn5.addActionListener(new ActionListener() { //Outgoing msgs
			public void actionPerformed(ActionEvent arg0) {
				showOutgoingMsgs();
			}
		});

		btn6.addActionListener(new ActionListener() { //absences of my children
			public void actionPerformed(ActionEvent arg0) {
				retrieveAbsencesOfChildren();
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
	
	public void newMessage() {
		new MessageFx(Cval.ScreenWidth, Cval.ScreenHeight, "Μήνυμα",null, 0);
	}
	
	public void newPayment() {
		sql_from_parent = "SELECT id AS Κωδικός, pdate AS Ημερομηνία, amount AS Ποσό, comments as Σχόλια FROM payments WHERE user_id = " + db_interface.user_id + " ORDER BY pdate desc";
		new MultirowForm("Πληρωμές", sql_from_parent, true, true, true, Cval.OPEN_EDIT_ROW);
	}
	
	public void showAnnouncements() {
		Cval.id_from_parent.push(db_interface.user_id);
		sql_from_parent = "SELECT m.id AS Κωδικός, m.msg_date AS Ημερομηνία, m.msg_subject AS Θέμα, m.cloud_id as online_id FROM msgs as m INNER JOIN msgs_details as md on m.id= md.msg_id WHERE kind=2 and md.to_user_id = " + db_interface.user_id + " ORDER BY msg_date desc";
		new MultirowForm("Ανακοινώσεις", sql_from_parent, false, true, true, Cval.OPEN_EDITOR);		
	}
	
	public void showIncomingMsgs() {
		sql_from_parent = "SELECT m.id AS Κωδικός, m.msg_date AS Ημερομηνία, m.msg_subject AS Θέμα, m.cloud_id as online_id FROM msgs as m INNER JOIN msgs_details as md on m.id= md.msg_id WHERE kind=2 and md.to_user_id = " + db_interface.user_id + " ORDER BY msg_date desc";
		new MultirowForm("Εισερχόμενα", sql_from_parent, false, true, true, Cval.OPEN_EDITOR);
	}
	
	public void showOutgoingMsgs() {
		sql_from_parent = "SELECT m.id AS Κωδικός, m.msg_date AS Ημερομηνία, m.msg_subject AS Θέμα, m.cloud_id as online_id FROM msgs as m INNER JOIN msgs_details as md on m.id= md.msg_id WHERE kind=0 and md.to_user_id = " + db_interface.user_id + " ORDER BY msg_date desc";
		new MultirowForm("Απεσταλμένα", sql_from_parent, true, true, false, Cval.OPEN_EDITOR);
	}
	
	public void retrieveAbsencesOfChildren() {
		sql_from_parent = "SELECT CONCAT(pup.surname, ' ', pup.firstname) AS Ονοματεπώνυμο, sum(ab.tcount) as ΣύνολοΑπουσιών FROM users as par INNER JOIN users as pup on pup.parent_id = par.id INNER JOIN absences as ab on ab.pupil_id = pup.id WHERE par.id=" + db_interface.user_id + " GROUP BY ab.pupil_id";
		new MultirowForm("Απουσίες", sql_from_parent, false, false, false,  Cval.OPEN_EDITOR);
	}
}
