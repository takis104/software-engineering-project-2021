package schoolink;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.text.NumberFormatter;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class Absenses {
	private JFrame screen;
	private int selected_subgroup_id;
	HashSet<Integer> selected_pupils_id;
	int lines;
	ArrayList<Integer> a1 = new ArrayList<Integer>();
	ArrayList<ArrayList<JCheckBox>> absList;
	ArrayList<JComboBoxIds> select_pupils;
	ArrayList<DataButton> remove_line;
	JPanel gpanel;
	JDatePickerImpl IssueDatePicker;
	ArrayList<JPanel> Pupils;
	
	public Absenses() {
		selected_pupils_id = new HashSet<Integer>();
		absList =  new ArrayList<ArrayList<JCheckBox>>();
		Pupils = new ArrayList<JPanel>();
		select_pupils = new ArrayList<JComboBoxIds>();
		lines=1;
		selected_subgroup_id=-1;
		
		screen = new JFrame("");
		screen.setTitle("Απουσίες τμήματος");
		screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		screen.setSize(600, 800); 
		ImageIcon bg = new ImageIcon(getClass().getResource("/images/main_bg.png"));
		Image bg_img = bg.getImage().getScaledInstance(screen.getWidth(), screen.getHeight(), Image.SCALE_DEFAULT);
		screen.setContentPane(new ImagePanel(bg_img));
		
	    NumberFormat format = NumberFormat.getInstance();
	    NumberFormatter formatter = new NumberFormatter(format);
	    formatter.setValueClass(Integer.class);
	    formatter.setMinimum(0);
	    formatter.setMaximum(Integer.MAX_VALUE);
	    formatter.setAllowsInvalid(false);
		
		GridBagLayout grid = new GridBagLayout();
		screen.setLayout(grid);
		
		JLabel lbl1 = new JLabel("Ημερομηνία : ");
		
		
		lbl1.setFont(new Font("Tahoma", Font.PLAIN, Cval.TitleFontSize));
		lbl1.setForeground(Color.YELLOW);
		GridBagConstraints gbc1 = new GridBagConstraints();
		gbc1.gridx = 0;  gbc1.gridy = 0; gbc1.gridwidth=1;
		screen.add(lbl1, gbc1);
		
        UtilDateModel dmodel = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(dmodel,p);
        IssueDatePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        Calendar cal = Calendar.getInstance();
        IssueDatePicker.getModel().setDate(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
        IssueDatePicker.getModel().setSelected(true);
        
        IssueDatePicker.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	LoadAbsenses();
				gpanel.revalidate();
		    }
		});
                
		gbc1.gridx = 1;  gbc1.gridy = 0; gbc1.gridwidth=1;
        screen.add(IssueDatePicker, gbc1);
        
		JLabel lbl2 = new JLabel("Τμήμα : ");
		lbl2.setFont(new Font("Tahoma", Font.PLAIN, Cval.TitleFontSize));
		lbl2.setForeground(Color.YELLOW);
		gbc1.gridx = 0;  gbc1.gridy = 1; gbc1.gridwidth=1;
		screen.add(lbl2, gbc1);
		
		gpanel = new JPanel(); 
		gpanel.setLayout(new BoxLayout(gpanel, BoxLayout.Y_AXIS));
		gpanel.add(Headers());
		
		JComboBoxIds sc = new JComboBoxIds("SELECT id, gname FROM groups WHERE sub_class=1 ORDER BY gname");
		gbc1.gridx = 1;  gbc1.gridy = 1; gbc1.gridwidth=1; gbc1.anchor = GridBagConstraints.WEST;
		sc.jcb.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	selected_subgroup_id = sc.getSelectedId();
		    	LoadAbsenses();
				gpanel.revalidate();
		    }
		});
		screen.add(sc.jcb, gbc1);
	
		JScrollPane scrollPane = new JScrollPane(gpanel);
		scrollPane.setPreferredSize(new Dimension(500,600));
		gbc1.gridx = 0; gbc1.gridy = 2; gbc1.gridwidth=7;
		screen.add(scrollPane, gbc1);
		
		gbc1.gridx = 0; gbc1.gridy = 1; gbc1.gridwidth=1; screen.add(new JLabel(" "), gbc1);
		

		JButton save_exit_btn = new JButton((Icon) new ImageIcon(getClass().getResource("/images/save_exit.png")));
		save_exit_btn.setToolTipText("Αποθήκευση & Έξοδος");
		save_exit_btn.setMaximumSize(new Dimension(35, 35));
		GridBagConstraints gbc4 = new GridBagConstraints();
		gbc4.gridx = 2;  gbc4.gridy = 6; gbc4.gridwidth=2; screen.add(save_exit_btn, gbc4);
		
		JButton exit_btn = new JButton((Icon) new ImageIcon(getClass().getResource("/images/exit.png")));
		exit_btn.setToolTipText("Έξοδος");
		exit_btn.setMaximumSize(new Dimension(35, 35));
		GridBagConstraints gbc3 = new GridBagConstraints();
		gbc3.gridx = 1;  gbc3.gridy = 6; gbc3.gridwidth=2; screen.add(exit_btn, gbc3);
		exit_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				screen.dispose();
			}
		});

		save_exit_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SaveAbsenses();
			}
			
		});
		
		screen.setLocationRelativeTo(null);
		screen.pack();
		screen.setVisible(true);
	}
	
	private String SelectedIds() {
		int k;
		String res = "";
        Iterator<Integer> pupil_id = selected_pupils_id.iterator();
        while (pupil_id.hasNext()) {
        	k=pupil_id.next();
        	res += "," + k;
            System.out.println(pupil_id.next());
        }
        if (res.isEmpty()) return "";
        else return "WHERE id in (" +  res.substring(1) + ")";
	}
	
	private JPanel NewLine(int pupil_id, int absences_code) {
		int k;
		JPanel loc_panel = new JPanel(); 
		BoxLayout boxlayout = new BoxLayout(loc_panel, BoxLayout.X_AXIS);
		loc_panel.setLayout(boxlayout);
		Border blackline = BorderFactory.createLineBorder(Color.black);
		loc_panel.setBorder(blackline);
		DataButton rem_btn = new DataButton(lines++, "minus.png");
		rem_btn.setMaximumSize(new Dimension(16,16));
		rem_btn.setPreferredSize(new Dimension(16,16));
		loc_panel.add(rem_btn);		
		loc_panel.add(Box.createRigidArea(new Dimension(10,16)));
		ArrayList<JCheckBox> abs_line =new ArrayList<JCheckBox>(Cval.HoursPerDay);
		String sql_string = "SELECT u.id as id, CONCAT(surname, ' ',firstname) FROM users as u INNER JOIN participates as p on u.id=p.user_id INNER JOIN GROUPS as g on p.group_id = g.id WHERE g.id = " + selected_subgroup_id;
		JComboBoxIds pupil_name = new JComboBoxIds(sql_string);	
		
		if (pupil_id>0) pupil_name.setSelectedItem(pupil_id);
		
		pupil_name.jcb.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
				gpanel.add(NewLine(-1,-1));
				gpanel.revalidate();
		    }
		});
		pupil_name.jcb.setMaximumSize(new Dimension(240,24));
		select_pupils.add(pupil_name);
		loc_panel.add(pupil_name.jcb);
		loc_panel.add(Box.createRigidArea(new Dimension(10,16)));
		Boolean[] bv=null;
		
		if (absences_code>0) bv = int2bin(absences_code);
		
		for (k=0;k<Cval.HoursPerDay;k++) {
			JCheckBox cb;
			if (absences_code>0)  cb = new JCheckBox("", bv[k]); 
			else cb = new JCheckBox("", false);
			abs_line.add(cb);
			loc_panel.add(cb);
			loc_panel.add(Box.createRigidArea(new Dimension(16,16)));
		}	
		absList.add(abs_line);
		Pupils.add(loc_panel);
		return loc_panel;
	}
	
	
	private JPanel Headers() {
		JPanel loc_panel = new JPanel(); 
		BoxLayout boxlayout = new BoxLayout(loc_panel, BoxLayout.X_AXIS);
		loc_panel.setLayout(boxlayout);
		JLabel lbl1a = new JLabel("     ");
		lbl1a.setFont(new Font("Verdana", Font.PLAIN, 18));
		lbl1a.setBorder(new BevelBorder(BevelBorder.RAISED));
		loc_panel.add(lbl1a);
		
		JLabel lbl1b = new JLabel("Μαθητής :                 ");
		lbl1b.setFont(new Font("Verdana", Font.PLAIN, 18));
		lbl1b.setBorder(new BevelBorder(BevelBorder.RAISED));
		loc_panel.add(lbl1b);
		for (int j=0;j<Cval.HoursPerDay;j++) {
			JLabel lbl1c = new JLabel((j+1)+"η  ");
			lbl1c.setFont(new Font("Verdana", Font.PLAIN, 18));
			lbl1c.setBorder(new BevelBorder(BevelBorder.RAISED));
			loc_panel.add(lbl1c);
		}
		return loc_panel;
	}
	
	private PairOfIntegers bin2int(int line) {
		int k, sum=0, count=0;
		int bit, pow;
		for (k=0;k<Cval.HoursPerDay;k++) {
			if (absList.get(line).get(k).isSelected()) {count++; bit=1;}
			else bit=0;
			pow = 1<< Cval.HoursPerDay-k-1;
			sum += (bit*pow);
		}
		return new PairOfIntegers(sum, count);
	}
	
	private Boolean[] int2bin(int arg) {
		Boolean ret_arg[] = new Boolean[Cval.HoursPerDay];
		int k, rem, c=arg, index = Cval.HoursPerDay-1;;
		
		for (k=0;k<Cval.HoursPerDay;k++) ret_arg[k]=false;
		while (c>0) {
			rem = c%2;
			ret_arg[index--] = (rem==1);
			c = c/2;
		}
		return ret_arg;
	}
	
	private void SaveAbsenses() {
		int k, pupil_id;
		String sql_string = "INSERT INTO absences VALUES ";
		String new_rows="";
		for (k=0;k<lines-1;k++) {
			pupil_id = select_pupils.get(k).getSelectedId();
			if (pupil_id>0) {
				PairOfIntegers p = bin2int(k);	
				System.out.println("Send message to " + pupil_id + "parents");
				new_rows += "," + "(" + selected_subgroup_id + "," + pupil_id + ",'" + getDate() + "'," + p.i1 + "," + p.i2 + ")";
			}
		}
		sql_string = sql_string + new_rows.substring(1);
		System.out.println(sql_string);
		try {
			db_interface.execute(sql_string);
			//for (k=0;k<lines-1;k++) {
			//	pupil_id = select_pupils.get(k).getSelectedId();
			//	System.out.println("Send message to " + pupil_id + "parents");
			//}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void LoadAbsenses() {
		if (selected_subgroup_id<0) return;
		try {
			String sql_string = "SELECT pupil_id,  absenses_code FROM absences WHERE subclass_id = " + selected_subgroup_id + " AND adate = '" + getDate() + "'";
			db_interface.getAux2QueryResults(sql_string);
			ResultSet rs = db_interface.rs_aux2;
			EmptyScreen();
			int cnt=0;
			while (rs.next()) {
				gpanel.add(NewLine(rs.getInt(1),  rs.getInt(2)));
				cnt++;
			}
			if (cnt==0) gpanel.add(NewLine(-1, -1));
			gpanel.revalidate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private String getDate() {
		return IssueDatePicker.getModel().getYear() + "/" + (IssueDatePicker.getModel().getMonth()+1) + "/" + IssueDatePicker.getModel().getDay();
	}
	
	private void EmptyScreen() {
		int k=0;
		for (k=0;k<Pupils.size();k++)
			gpanel.remove(Pupils.get(k));
	}
	
}
