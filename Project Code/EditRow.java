package schoolink;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.jdbc.DatabaseMetaData;

@SuppressWarnings("serial")
public class EditRow extends JDialog {

	static ImageIcon edit_icon;
	static ImageIcon del_icon;
	static int ROW_HEIGHT=35;
	static int PREFERRED_ROWS=12;
	private int mode;
	private RowObject remove_image_object;
	private boolean delete_old_photo;
	private String parent_tbl;
	int w,h;
	

	List<Integer> CategoryComboIndexToId;
	JComboBox<String> parent_category;
	JTextField category_descr;
	
    ArrayList<String> Labels = new ArrayList<String>();
    ArrayList<JTextField> Values = new ArrayList<JTextField>();
    ArrayList<RowObject>  Smr = new ArrayList<RowObject>();
	
	public EditRow(String parent_tbl, int id, int mode, boolean modal) {//mode = db_interface.ADD || mode=<record id> to MODIFY
		super(Cval.multirow_instances_stack.peek(), (mode==db_interface.ADD ? "Add new record":"Edit record"), modal);
		this.mode=mode;
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Container cnt = this.getContentPane();
		this.parent_tbl = parent_tbl;
		
        int col=10;
        int line=10;
        int dh=35;
        int w=200;
        int h=30;
        
        java.awt.Font font = new Font("Tahoma", Font.PLAIN, 12);
	    try {    	
			//java.sql.ResultSetMetaData rsmdt = db_interface.rs_aux.getMetaData();
			DatabaseMetaData dbmd = (DatabaseMetaData) db_interface.db_connection.getMetaData();
			ResultSet rs=dbmd.getColumns(null, db_interface.USER, parent_tbl, null);
			int columns = db_interface.getRecordCount(rs);
	        int screen_width = 2 * w + 80;
	        int screen_height = columns * dh + 100 + 128 - dh;
			this.setSize(new Dimension(screen_width, screen_height)); 
			ImageIcon bg = new ImageIcon(getClass().getResource("/images/main_bg.png"));
			Image bg_img = bg.getImage().getScaledInstance(screen_width, screen_height, Image.SCALE_DEFAULT);
			
			this.setContentPane(new ImagePanel(bg_img));
			cnt = this.getContentPane();
			cnt.setLayout(null);
	        Labels = new ArrayList<String>();
	        Values = new ArrayList<JTextField>();

			JLabel lbl1;	
			if (mode==db_interface.MODIFY) {
				//System.out.println("---->SELECT * FROM " + parent_tbl + " WHERE id = " + id);
				db_interface.getAuxQueryResults("SELECT * FROM " + parent_tbl + " WHERE id = " + id);				
				db_interface.rs_aux.next();
			}
			ArrayList<ArrayList<String>> fkeys;
			int add_height;
			int m=0;
			while (rs.next()) {
				add_height = 0;
				if ((rs.getString("REMARKS")!=null)&& rs.getString("REMARKS").length()>0) {
					if (rs.getInt("NULLABLE")>0) { 
						if (rs.getString("REMARKS").equals("photo")) {
							lbl1 = new JLabel("Φωτογραφία");
							lbl1.setForeground(Color.WHITE);
							
						} else {
							lbl1 = new JLabel(rs.getString("REMARKS"));
							lbl1.setForeground(Color.WHITE);
						}
					}else {
						lbl1 = new JLabel(rs.getString("REMARKS")+"*"); 
						lbl1.setForeground(Color.RED);
					}
					Labels.add(lbl1.getText());
					lbl1.setFont(font);
					lbl1.setBounds(col, line, w, h); 
					cnt.add(lbl1);
					JTextField txt_field;
					if (db_interface.listOf_int_fields.contains(rs.getInt("DATA_TYPE"))) {
						JComboBox<String> combobox;
						fkeys = db_interface.GetForeignKeyReferences(rs.getString("COLUMN_NAME"), parent_tbl);
						if (fkeys!=null) {
							combobox = new JComboBox<String>();
							String combo_name="";
							for (int k=0;k<fkeys.size();k++) {
								combobox.addItem(fkeys.get(k).get(1));
								combo_name += "," + fkeys.get(k).get(0); //ids stored in the name of the combobox.
							}
							combobox.setName(combo_name.substring(1));
							combobox.setBounds(col + w + h, line, w, h);
							int indx=-1;
							if (mode==db_interface.MODIFY) {
								indx = db_interface.rs_aux.getInt(rs.getString("COLUMN_NAME"));
								indx = Arrays.asList(combo_name.split(",")).indexOf(indx+"");
								combobox.setSelectedIndex(indx-1);
							}
							Smr.add(new RowObject(lbl1, combobox, rs.getString("COLUMN_NAME"), Integer.toString(indx-1) , rs.getInt("DATA_TYPE")));
							cnt.add(combobox);
						} else {
							txt_field = new JTextField();
							if (mode==db_interface.MODIFY)
								txt_field.setText(db_interface.rs_aux.getString(rs.getString("COLUMN_NAME")));							
								txt_field.setBounds(col + w + h, line, w, h);
								Values.add(txt_field);
								Smr.add(new RowObject(lbl1, txt_field, rs.getString("COLUMN_NAME"), txt_field.getText(), rs.getInt("DATA_TYPE")));
								cnt.add(txt_field);
						}
					} else {
						txt_field = new JTextField();
						if (mode==db_interface.MODIFY)
							txt_field.setText(db_interface.rs_aux.getString(rs.getString("COLUMN_NAME")));
						txt_field.setBounds(col + w + h, line, w, h);
						Values.add(txt_field);
						RowObject t;
						Smr.add(t = new RowObject(lbl1, txt_field, rs.getString("COLUMN_NAME"), txt_field.getText(), rs.getInt("DATA_TYPE"))); 
						cnt.add(txt_field);
						JButton photo_btn;
						if (rs.getString("REMARKS").equals("photo")) {
							remove_image_object = t;
							//String photo = db_interface.rs_aux.getString(rs.getString("COLUMN_NAME"));
							String photo="";
							//System.out.println("--->" + photo);
							if (isEmptyStr(photo)) 
								photo_btn = new JButton((Icon) new ImageIcon(getClass().getResource("/images/File-Photo-icon.png")));
							else {
								String photo_id = photo;
								ImageIcon ph = FileServer.get_image_to_icon(photo_id);
								Image bg_img1 = ph.getImage().getScaledInstance(128, 128, Image.SCALE_DEFAULT);
								photo_btn = new JButton();
								photo_btn.setIcon(new ImageIcon(bg_img1));
							}
							photo_btn.setToolTipText("Click to load user photo");
							photo_btn.setMaximumSize(new Dimension(128,128));
							photo_btn.setBounds(col + w + h, line, 128, 128);
							photo_btn.addActionListener(new ActionListener() {
						        public void actionPerformed(ActionEvent arg0) {
						        	ImageIcon ic = FileServer.PickAPhotoFile();
						        	delete_old_photo = false;
						        	if (ic!=null) {
						        		Image bg_img2 = ic.getImage().getScaledInstance(128, 128, Image.SCALE_DEFAULT);
						        		//txt_field.setText("to_be_saved");
						        		photo_btn.setIcon(new ImageIcon(bg_img2));
						        		txt_field.setText(FileServer.store_file_online(FileServer.LastSelectedImage, true).id);
						        		delete_old_photo=true;
						        	}
						        }
							});
							cnt.add(photo_btn);
							add_height = 128 - dh;
							txt_field.setVisible(false);
						}
					}
					//category_descr.setColumns(10);				
					line+=dh+add_height;
				}
			}        
    	} catch (Exception e) {
    		e.printStackTrace();
    		JOptionPane.showMessageDialog(null, "Unable to connect a valid database... ","Error",JOptionPane.ERROR_MESSAGE);
    		System.exit(ERROR);
    	}
	    
		JButton save_btn = new JButton((Icon) new ImageIcon(getClass().getResource("/images/save.png")));
		save_btn.setToolTipText("Save record");
		save_btn.setMaximumSize(new Dimension(35,35));
		save_btn.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent arg0) {
	        	String success_msg="";
	        	String fail_msg="";
	        	if (!Required()) {
	        		fail_msg = "All fields with an asterisk should be provided";
	        		JOptionPane.showMessageDialog(null, fail_msg ,"Error",JOptionPane.ERROR_MESSAGE);
	        	}
				try {
					String sql_stm1 = PrepareSqlStatement();
					//System.out.println("Sql1:"+sql_stm1);
					//db_interface.db_connection.setAutoCommit(false);
					db_interface.execute(sql_stm1);
					//db_interface.db_connection.commit();
					//db_interface.db_connection.setAutoCommit(true);
					success_msg = (mode==db_interface.ADD) ? "New record has been stored" : "Record has been updated";
					JOptionPane.showMessageDialog(null, success_msg);
					if (delete_old_photo) 
						FileServer.delete_file(remove_image_object.init_value);
					//Categories.populate_jtable();
					Cval.multirow_instances_stack.peek().populate_jtable(false);
					dispose();
				} catch (SQLException se) {
					fail_msg = (mode==db_interface.ADD) ? "Failed to create new record!" : "Failed to update record...";
					JOptionPane.showMessageDialog(null, fail_msg ,"Error",JOptionPane.ERROR_MESSAGE);
			        se.printStackTrace();
			        return;
				}
	        }
	    });
		save_btn.setBounds(10, line+10, 35, 35);
		save_btn.setEnabled(false);
	    cnt.add(save_btn);
	    
	    OnChangeRecordEvent(save_btn);

		JButton exit_btn = new JButton((Icon) new ImageIcon(getClass().getResource("/images/exit.png")));
		exit_btn.setToolTipText("Exit");
		exit_btn.setMaximumSize(new Dimension(35, 35));
		exit_btn.setBounds(150, line+10, 35, 35);
		exit_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		cnt.add(exit_btn);

		PrepareSqlStatement();

		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	private String PrepareSqlStatement() {
		int k;
		String query="";
		//System.out.println(java.sql.Types.VARCHAR);
		//for (k=0;k<Smr.size();k++) {
		//	System.out.println(Smr.get(k).label.getText() + "--->" + Smr.get(k).getText() + ":" + Smr.get(k).init_value + ":" + Smr.get(k).data_type);
		//}
		MultirowForm mr = Cval.multirow_instances_stack.peek();
		if (mode==db_interface.ADD) {
			String values="DEFAULT";
			mr = Cval.multirow_instances_stack.peek();
			query = "INSERT INTO " + mr.my_sql_tbl + "(id,";
			String fields="";
			for (k=0;k<Smr.size();k++) {
				fields += "," + Smr.get(k).field;
				String r = Smr.get(k).getText();
				if (r!=null) values += ",\"" + Smr.get(k).getText() + "\"";
				else values += ",NULL";
			}
			query +=  fields.substring(1) + ") VALUES(" + values + ");";
		} else {
			query = "UPDATE " + mr.my_sql_tbl + " SET ";
			String upd="";
			String val;
			for (k=0;k<Smr.size();k++) {
				RowObject t = Smr.get(k);
				String current_value = t.getText();
				//System.out.println(">" + t.label.getText() + " : " + t.init_value + "-->" + current_value+"<");
				if ((t.init_value!=null) && (current_value!=null) && (!t.init_value.equals(current_value))) {
					val = t.getText();
					if (t.data_type==java.sql.Types.VARCHAR)
						upd += ((val==null) ? ", " + t.field +  " = NULL "  : ", " + t.field +  " = '" + t.getText() + "'");
					else upd +=((val==null) ? ", " + t.field +  " = NULL "  : ", " + t.field +  " = " + String.valueOf(t.getText()) + ""); 
				}
			}
			if (upd.length()>0)
				query = query + upd.substring(1) + " WHERE id = " + Cval.id_from_parent.peek();
			else return "";
		} 
		return query;
	}
	
	private void OnChangeRecordEvent(JButton btn) {
	 
		for (int k=0;k<Smr.size();k++) {
			RowObject t = Smr.get(k);
			if (t.ctrl.getClass().getName().equals("javax.swing.JTextField"))
				t.ctrl.addKeyListener(new KeyAdapter() {
					public void keyPressed(KeyEvent e) {
						btn.setEnabled(true);
					}
				});
			else if (t.ctrl.getClass().getName().equals("javax.swing.JComboBox")) {
				@SuppressWarnings("unchecked")
				JComboBox<String> jComboBox = (JComboBox<String>)(t.ctrl);
				jComboBox.addActionListener(new ActionListener () {
				    public void actionPerformed(ActionEvent e) {
				    	btn.setEnabled(true);
				    }
				});
			}
		}
	}

	
	private boolean isEmptyStr(String str) {
		if (str==null) return true;
		if (str.length()==0) return true;
		return false;
	}
	
	private boolean Required() {
		for (int k=0;k<Smr.size();k++) {
			RowObject t = Smr.get(k);
			if (t.label.getText().endsWith("*") && isEmptyStr(t.getText())) 
				{System.out.println(t.label.getText() + "<>" + t.getText());return false;} 
		}
		return true;
	}
}
