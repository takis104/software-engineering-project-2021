package schoolink;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Feedback {
	int grade;
	
	private JTextField comments;
	
	public Feedback() {
		JFrame screen = new JFrame("");
		
		screen.setTitle("Rating schoolink");
		screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		screen.setSize(400, 300);
		ImageIcon bg = new ImageIcon(getClass().getResource("/images/main_bg.png"));
		Image bg_img = bg.getImage().getScaledInstance(screen.getWidth(), screen.getHeight(), Image.SCALE_DEFAULT);
		screen.setContentPane(new ImagePanel(bg_img));
		
		screen.getContentPane().setLayout(null);
		Container cnt = screen.getContentPane();
		
		grade = Cval.NoStars;
		//=================================================================
		JLabel lblUsername = new JLabel("Αξιολογήστε την εφαρμογή μας!");
		lblUsername.setForeground(Color.CYAN);
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblUsername.setBounds(20, 10, 250, 44);
		cnt.add(lblUsername);
		
		JButton exit_btn = new JButton((Icon) new ImageIcon(getClass().getResource("/images/exit.png")));
		exit_btn.setToolTipText("Exit");
		exit_btn.setMaximumSize(new Dimension(32, 32));
		exit_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				screen.dispose();
			}
		});
		exit_btn.setBounds(100, 200, 32, 32);
		cnt.add(exit_btn);
		
		JLabel lbl1 = new JLabel("Σχόλια");
		lbl1.setForeground(Color.CYAN);
		lbl1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lbl1.setBounds(20, 120, 250, 44);
		cnt.add(lbl1);
		
		comments = new JTextField();
		comments.setFont(new Font("Tahoma", Font.PLAIN, 12));
		comments.setBounds(100, 130, 200, 23);
		comments.setColumns(100);
		cnt.add(comments);
		
		JButton save_exit_btn = new JButton((Icon) new ImageIcon(getClass().getResource("/images/save_exit.png")));
		save_exit_btn.setToolTipText("Save&Exit");
		save_exit_btn.setMaximumSize(new Dimension(32, 32));
		save_exit_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String sql_query = "INSERT INTO feedback VALUES (DEFAULT, " + grade + ",'" + comments.getText() + "', CURDATE(),"+ db_interface.school_id + "," +db_interface.user_id+ ")";
					System.out.println(sql_query);
					db_interface.execute(sql_query);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				screen.dispose();
			}
		});
		save_exit_btn.setBounds(50, 200, 32, 32);
		cnt.add(save_exit_btn);
		
		JSlider slider = new JSlider(JSlider.HORIZONTAL, 1, 10, Cval.NoStars); 
		
		slider.setMajorTickSpacing(1);  
		slider.setMinorTickSpacing(10);
		slider.setPaintTicks(true);  
		slider.setPaintLabels(true); 
		
	    slider.addChangeListener(new ChangeListener() {
	          public void stateChanged(ChangeEvent e) {
	             grade = ((JSlider)e.getSource()).getValue();
	          }
	       });
	    
	    slider.setBounds(20, 50, 200, 50);
		cnt.add(slider);
		

		//=================================================================
		
		screen.setLocationRelativeTo(null);
		//screen.pack();
		screen.setVisible(true);
	}
}
