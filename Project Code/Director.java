package schoolink;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JDialog;
import javax.swing.JFileChooser;

import java.awt.Font;
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

	
	public Director() {
		screen = new JFrame();

		screen.setTitle("Director:" + db_interface.user_surname + ":" + db_interface.school_name);
		screen.setSize(new Dimension(500,500));
		System.out.println("<<<<<=> :" + getClass().getResource("/images/main_bg.png").getPath());
		ImageIcon bg = new ImageIcon(getClass().getResource("/images/main_bg.png"));
		Image bg_img = bg.getImage().getScaledInstance(screen.getWidth(), screen.getHeight(), Image.SCALE_DEFAULT);
		screen.setContentPane(new ImagePanel(bg_img));
		Container cnt = screen.getContentPane();
		cnt.setLayout(null);
		
		JLabel lbl1 = new JLabel("Director :");
		lbl1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lbl1.setBounds(10, 100, 86, 14);
		lbl1.setForeground(Color.WHITE);
		cnt.add(lbl1);
		
		JButton exit_btn = new JButton((Icon) new ImageIcon(getClass().getResource("/images/exit.png")));
		exit_btn.setToolTipText("Exit");
		exit_btn.setMaximumSize(new Dimension(32, 32));
		exit_btn.setBounds(250, 400, 32, 32);
		exit_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				screen.dispose();
			}
		});
		cnt.add(exit_btn);
		
		JButton edit_btn = new JButton((Icon) new ImageIcon(getClass().getResource("/images/edit.png")));
		edit_btn.setToolTipText("Edit");
		edit_btn.setMaximumSize(new Dimension(35, 35));
		edit_btn.setBounds(150, 150, 46, 35);
		edit_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				db_interface.sql_from_parent = "SELECT id AS Κωδικός, surname AS Επώνυμο, firstname AS Όνομα,  fathername AS Πατρώνυμο, mothername AS Μητρώνυμο, role_id FROM "+ "users WHERE id>0 ORDER BY surname";
				new MultirowForm(true, true, false);
			}
		});
		cnt.add(edit_btn);
		
		JLabel lbl_im = new JLabel("im :");
		lbl_im.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lbl_im.setBounds(10, 100, 200, 200);
		lbl_im.setForeground(Color.WHITE);
		cnt.add(lbl_im);
		
        UtilDateModel dmodel = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(dmodel,p);
        JDatePickerImpl IssueDatePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        IssueDatePicker.setBounds(116, 208, 150, 20);
        cnt.add(IssueDatePicker);
		
		
		JButton test_btn = new JButton((Icon) new ImageIcon(getClass().getResource("/images/down.png")));
		test_btn.setToolTipText("test");
		test_btn.setMaximumSize(new Dimension(35, 35));
		test_btn.setBounds(150, 350, 46, 35);
		test_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					EditorFx e = new EditorFx(700, 500, "HTML Editor",dropbox_interface.getHtmlText(getClass().getResource("/test.htm")));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		cnt.add(test_btn);
		screen.setLocationRelativeTo(null);
		screen.setVisible(true);
	}
}