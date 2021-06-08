package schoolink;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JDialog;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JTextField;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.sql.*;
import java.awt.Button;
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
public class Teacher extends JDialog {
	List<Integer> CategoryComboIndexToId;
	JComboBox<String> parent_category;
	JTextField category_descr;
	public static JFrame screen;
	int button_size;
	String sql_from_parent;

	
	public Teacher() {	
		screen = new JFrame();
		db_interface.user_role="Teacher";
		screen.setTitle("Teacher:" + db_interface.user_surname + ":" + db_interface.school_name);
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
		JButton btn2 = Cval.AddButton(screen, 1, 2, "/images/mn_im07.png", "Ανάθεση εργασίας");
		JButton btn3 = Cval.AddButton(screen, 1, 3, "/images/mn_im16.png", "Οι εργασίες που έχω αναθέσει");
		JButton btn4 = Cval.AddButton(screen, 2, 3, "/images/mn_im05.png", "Ανακοινώσεις");
		JButton btn5 = Cval.AddButton(screen, 2, 1, "/images/mn_im02.png", "Εισερχόμενα");
		JButton btn6 = Cval.AddButton(screen, 2, 2, "/images/mn_im06.png", "Απεσταλμένα");
		
		btn1.addActionListener(new ActionListener() { //new message
			public void actionPerformed(ActionEvent arg0) {
				new MessageFx(Cval.ScreenWidth, Cval.ScreenHeight, "Μήνυμα",null, 0);
			}
		});
		
		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) { //new project
				new MessageFx(Cval.ScreenWidth, Cval.ScreenHeight, "Νέα εργασία",null, 1);
			}
		});
		
		btn3.addActionListener(new ActionListener() { //old project
			public void actionPerformed(ActionEvent arg0) {
				Cval.id_from_parent.push(db_interface.user_id);
				sql_from_parent = "SELECT DISTINCT(m.id) AS Κωδικός, m.msg_date AS Ημερομηνία, m.msg_subject AS Θέμα, m.deadline as Προθεσμία, m.cloud_id AS link FROM msgs as m INNER JOIN msgs_details as md WHERE deadline is not null AND md.from_user_id = " + db_interface.user_id;
				new MultirowForm("Εργασίες που ανέθεσα", sql_from_parent, true, true, true, Cval.OPEN_MULTIROW); //last true = call view message on edit
			}
		});
		
		btn4.addActionListener(new ActionListener() { //new announcement
			public void actionPerformed(ActionEvent arg0) {
				new MessageFx(Cval.ScreenWidth, Cval.ScreenHeight, "Ανακοίνωση",null, 2);
			}
		});
		
		btn5.addActionListener(new ActionListener() { //Incomimg msgs
			public void actionPerformed(ActionEvent arg0) {
				sql_from_parent = "SELECT m.id AS Κωδικός, m.msg_date AS Ημερομηνία, m.msg_subject AS Θέμα, CONCAT(u.surname, ' ', u.firstname) as Αποστολέας, m.cloud_id as online_id, u.id As sender_id FROM msgs as m INNER JOIN msgs_details as md on m.id= md.msg_id INNER JOIN users as u on md.from_user_id=u.id WHERE kind=0 and md.to_user_id =" + db_interface.user_id + " ORDER BY msg_date desc";
				new MultirowForm("Εισερχόμενα", sql_from_parent, false, true, true, Cval.OPEN_EDITOR);
			}
		});

		btn6.addActionListener(new ActionListener() { //Outgoing msgs
			public void actionPerformed(ActionEvent arg0) {
				sql_from_parent = "SELECT distinct(m.id) AS Κωδικός, m.msg_date AS Ημερομηνία, m.msg_subject AS Θέμα, m.cloud_id as online_id FROM msgs as m INNER JOIN msgs_details as md on m.id= md.msg_id WHERE kind=0 and md.from_user_id = " + db_interface.user_id + " ORDER BY msg_date desc";
				new MultirowForm("Απεσταλμένα", sql_from_parent, true, true, true,  Cval.OPEN_EDITOR);
			}
		});
			
		JButton exit_btn = new JButton((Icon) new ImageIcon(getClass().getResource("/images/exit.png")));
		exit_btn.setToolTipText("Exit");
		exit_btn.setMaximumSize(new Dimension(35, 35));
		exit_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Mrat();
				screen.dispose();
			}
		});
		GridBagConstraints gbc3 = new GridBagConstraints();
		gbc3.gridx = 5;  gbc3.gridy = 5; screen.add(exit_btn, gbc3);
		screen.setLocationRelativeTo(null);
		
        // add menubar to frame
        //screen.setJMenuBar(PrepareMenu());
		
		screen.setVisible(true);

	}
	
	private JMenuBar PrepareMenu() {
	    // create a menubar
		JMenuBar mb = new JMenuBar();
	
	     // create a menu
		JMenu m1 = new JMenu("Menu");
		JMenu m14 = new JMenu("submenu");
	
	     // create menuitems
		JMenuItem m11 = new JMenuItem("MenuItem1");
	    m11.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent arg0){
	            System.out.println("You have clicked here");
	         }
	     });
		JMenuItem m12 = new JMenuItem("MenuItem2");
		JMenuItem m13 = new JMenuItem("MenuItem3");
		
		JMenuItem m141 = new JMenuItem("SubMenuItem1");
		JMenuItem m142 = new JMenuItem("SubMenuItem2");
	     // add menu items to menu
		m14.add(m141);m14.add(m142);
	   	m1.add(m11);m1.add(m12); m1.add(m13);m1.add(m14);  
	    // add menu to menu bar
	   	mb.add(m1);
	   	return mb;
	}
}