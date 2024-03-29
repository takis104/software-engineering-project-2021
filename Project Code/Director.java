package schoolink;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JDialog;
import javax.swing.JFileChooser;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Window;

import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.StyledDocument;
import javax.swing.text.html.HTMLDocument;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import java.sql.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.Icon;

@SuppressWarnings("serial")
public class Director extends JFrame {
	public static JFrame screen;
	List<Integer> CategoryComboIndexToId;
	JComboBox<String> parent_category;
	JTextField category_descr;
	int button_size;
	String sql_from_parent;
	
	public Director() {
		screen = new JFrame();
		db_interface.user_role = "Director";
		screen.setTitle("Director:" + db_interface.user_surname + ":" + db_interface.school_name);
		ImageIcon bg = new ImageIcon(getClass().getResource(Cval.bg_image));
		screen.setSize(new Dimension(Cval.ScreenWidth,Cval.ScreenHeight));
		Image bg_img = bg.getImage().getScaledInstance(screen.getWidth(), screen.getHeight(), Image.SCALE_DEFAULT);
		screen.setContentPane(new ImagePanel(bg_img));

		
		GridBagLayout grid = new GridBagLayout();
		screen.setLayout(grid);
		GridBagConstraints gbc1 = new GridBagConstraints();
		gbc1.gridx = 6;  gbc1.gridy = 6; screen.add(new JLabel(""), gbc1);
		
		JLabel lbl1 = new JLabel("Welcome...");
		lbl1.setFont(new Font("Tahoma", Font.PLAIN, Cval.TitleFontSize));
		lbl1.setForeground(Color.WHITE);
		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.gridx = 0;  gbc2.gridy = 0; gbc2.gridwidth=2;
		screen.add(lbl1, gbc2);
		
		JButton btn1 = Cval.AddButton(screen, 1, 1, "/images/mn_im10.png", "�������");
		JButton btn2 = Cval.AddButton(screen, 1, 2, "/images/mn_im11.png", "���������");
		JButton btn3 = Cval.AddButton(screen, 1, 3, "/images/mn_im05.png", "������");
		JButton btn4 = Cval.AddButton(screen, 2, 1, "/images/Users-icon.png", "���� �� �������");
		JButton btn5 = Cval.AddButton(screen, 2, 2, "/images/entries_book.png", "�������� �������");
		//JButton btn6 = Cval.AddButton(screen, 2, 3, "/images/mn_im14.png", "Call editor");
		

		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showPupilList();
			}
		});
		
		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showTeacherList();
			}
		});

		btn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showParentList();
			}
		});

		btn4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showAllUsersList();
			}
		});
		
		btn5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				manageAbsences();
			}
		});
		
		/*btn6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});*/
		
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
	
	public void showPupilList() {
		String sql_from_parent = "SELECT id AS �������, surname AS �������, firstname AS �����,  fathername AS ���������, mothername AS ��������� FROM "+ "users WHERE id>0 AND role_id = 4 ORDER BY surname";
		new MultirowForm("�������", sql_from_parent, true, true, true, Cval.OPEN_EDIT_ROW);
	}
	
	public void showTeacherList() {
		sql_from_parent = "SELECT id AS �������, surname AS �������, firstname AS �����,  fathername AS ���������, mothername AS ��������� FROM "+ "users WHERE id>0 AND role_id = 3 ORDER BY surname";
		new MultirowForm("���������", sql_from_parent, true, true, true, Cval.OPEN_EDIT_ROW);
	}
	
	public void showParentList() {
		sql_from_parent = "SELECT id AS �������, surname AS �������, firstname AS �����,  fathername AS ���������, mothername AS ��������� FROM "+ "users WHERE id>0 AND role_id = 5 ORDER BY surname";
		new MultirowForm("���������", sql_from_parent, true, true, true, Cval.OPEN_EDIT_ROW);
	}
	
	public void showAllUsersList() {
		sql_from_parent = "SELECT id AS �������, surname AS �������, firstname AS �����, fathername AS ���������, mothername AS ��������� FROM "+ "users WHERE id>0 ORDER BY surname";
		new MultirowForm("�������", sql_from_parent, true, true, true, Cval.OPEN_EDIT_ROW);
	}
	
	public void manageAbsences() {
		new Absenses();
	}
}