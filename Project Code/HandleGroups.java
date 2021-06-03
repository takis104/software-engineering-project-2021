package schoolink;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.PatternSyntaxException;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

@SuppressWarnings("serial")
public class HandleGroups extends JDialog {
	private JDialog screen;
	private JTextField group_name;
	private JTextArea comments;
	private JCheckBox is_subclass;
	private JButton save_group_btn, new_group_btn;
	private JComboBoxIds sub_group;
	private JComboBoxIds teacher;
	private JLabel sub_group_lbl;
	private JTable group, groups;
	static db_interface db;
	//private DefaultTableModel model;
	static ImageIcon edit_icon;
	static ImageIcon del_icon;
	static int ROW_HEIGHT=35;
	static int PREFERRED_ROWS=12;
	int start,w,h, state;
	static int NEW_GROUP=0;
	static int UPDATE_GROUP=1;
	private int selected_group_id;
	private boolean dirty;
	private Icon NewIcon, CancelNewIcon;
	
	private static String all_users_query = "SELECT id AS Κωδικός, surname AS Επώνυμο, firstname AS Όνομα,  fathername AS Πατρώνυμο, mothername AS Μητρώνυμο, role_id FROM "+ "users WHERE id>0 ORDER BY surname";			

	
	public HandleGroups(String Title, JFrame parent, boolean add_new_rec) {	
		Container cnt;
		start=210;
		state = UPDATE_GROUP;
		screen = new JDialog(parent);
		
		//db_interface.parent_window = screen;
		screen.getContentPane().setBackground(new Color(0, 0, 153));
		screen.setTitle(Title + ":(" + db_interface.user_surname + ": " + db_interface.school_name + ")");
		cnt = screen.getContentPane();
		
		String sql_str1 = "SELECT id AS Ομάδα, gname AS Όνομα FROM groups ORDER BY gname";			
		groups = DBFillJTable(sql_str1, new Color(255, 255, 200), false);
		state = UPDATE_GROUP;
	    JScrollPane pg1 = new JScrollPane(groups, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		//String sql_str2 = "SELECT id AS Κωδικός, surname AS Επώνυμο, firstname AS Όνομα,  fathername AS Πατρώνυμο, mothername AS Μητρώνυμο, role_id FROM "+ "users WHERE id>0 ORDER BY surname";			
		group = DBFillJTable(all_users_query, new Color(111, 164, 160), true);
	    JScrollPane pg2 = new JScrollPane(group, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	    group.setFillsViewportHeight(true);
        w = resizeColumnWidth(group);
        h = ROW_HEIGHT * PREFERRED_ROWS;
		screen.setSize(new Dimension(200 + w + 30, h + 180));

	    groups.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent e) {
	        	if (!e.getValueIsAdjusting() && groups.getSelectedRow() != -1) {
	        		DefaultTableModel mdl = (DefaultTableModel)groups.getModel();
	        		selected_group_id = Integer.parseInt(mdl.getValueAt(groups.getSelectedRow(), 0).toString());
	        		//System.out.println(selected_group_id);
	        		LoadGroup(selected_group_id, group);
	        	}
	        }
	    });
		
		ImageIcon bg = new ImageIcon(getClass().getResource("/images/main_bg.png"));
		Image bg_img = bg.getImage().getScaledInstance(screen.getWidth(), screen.getHeight(), Image.SCALE_DEFAULT);
		screen.setContentPane(new ImagePanel(bg_img));
		cnt = screen.getContentPane();
		cnt.setLayout(null);
		
        pg1.setBounds(10, 90, 180, h);
        cnt.add(pg1);
		
        pg2.setBounds(start, 90, w, h);
        cnt.add(pg2);
	    
		JButton exit_btn = new JButton((Icon) new ImageIcon(getClass().getResource("/images/exit.png")));
		exit_btn.setToolTipText("Exit");
		exit_btn.setMaximumSize(new Dimension(32, 32));

		exit_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				screen.dispose();
			}
		});
		NewIcon = (Icon) new ImageIcon(getClass().getResource("/images/new.png")); 
		CancelNewIcon = (Icon) new ImageIcon(getClass().getResource("/images/cancel_new.png"));
		new_group_btn = new JButton(NewIcon);
		new_group_btn.setToolTipText("Create a new group");
		new_group_btn.setMaximumSize(new Dimension(32, 32));
		new_group_btn.setVisible(add_new_rec);
		
		JLabel new_group_lbl = new JLabel("Όνομα ομάδας:");
		new_group_lbl.setForeground(Color.YELLOW);
		new_group_lbl.setBounds(start + 10, 10, 90, 20);
		
		group_name = new JTextField(100);
		group_name.setToolTipText("Give the name of a new group");
		group_name.setBounds(start + 100, 10, 100, 20);
		
		JLabel teacher_group_lbl = new JLabel("Υπεύθυνος:");
		teacher_group_lbl.setForeground(Color.YELLOW);
		teacher_group_lbl.setBounds(start + 210, 10, 90, 20);
		cnt.add(teacher_group_lbl);
		
		teacher = new JComboBoxIds("SELECT id, CONCAT(surname, ' ', firstname) as name from users where id>0 order by surname");
		teacher.jcb.setToolTipText("Person in charge");
		teacher.jcb.setBounds(start + 280, 10, 150, 20);
		cnt.add(teacher.jcb);
		
		JLabel is_subclass_lbl = new JLabel("Τμήμα μαθητών");
		is_subclass_lbl.setForeground(Color.YELLOW);
		is_subclass_lbl.setBounds(start + 10, 40, 90, 20);
		cnt.add(is_subclass_lbl);
		
		is_subclass = new JCheckBox();
		is_subclass.setSelected(false);
		is_subclass.setBounds(start + 100, 40, 20, 20);

		cnt.add(is_subclass);
		
		
		sub_group_lbl = new JLabel("Τάξη:");
		sub_group_lbl.setForeground(Color.YELLOW);
		sub_group_lbl.setBounds(start + 10, 68, 90, 20);
		cnt.add(sub_group_lbl);
		
		sub_group = new JComboBoxIds("SELECT id, cname  from classes order by cname");
		sub_group.jcb.setToolTipText("");
		sub_group.jcb.setBounds(start + 100, 68, 100, 20);
		cnt.add(sub_group.jcb);
		sub_group.jcb.setVisible(false);
		sub_group_lbl.setVisible(false);
		is_subclass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sub_group.jcb.setVisible(is_subclass.isSelected());
				sub_group_lbl.setVisible(is_subclass.isSelected());
				if (state==NEW_GROUP) 
					if (is_subclass.isSelected()) FilterGroup("Μαθητής", 6);
					else FilterGroup(null, 6);
				
			}
		});
		
		save_group_btn = new JButton((Icon) new ImageIcon(getClass().getResource("/images/save.png")));
		save_group_btn.setToolTipText("Save group");
		save_group_btn.setMaximumSize(new Dimension(32, 32));
		save_group_btn.setBounds(start + 440, 2, 32, 32);
		save_group_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (SaveGroup(group)&&state==NEW_GROUP) {
					Object[] row = new Object[2];
					row[0] = selected_group_id;
					row[1] = group_name.getText();
					((DefaultTableModel)groups.getModel()).addRow(row);
					new_group_btn.setIcon(NewIcon);
					state = NEW_GROUP;
				}
				save_group_btn.setEnabled(false);
				dirty = false;
			}
		});
		save_group_btn.setEnabled(false);
		exit_btn.setBounds(30, h+100, 32, 32);
		new_group_btn.setBounds(80, 50, 32, 32);
		cnt.add(new_group_btn);
		cnt.add(exit_btn);
		cnt.add(save_group_btn);
		cnt.add(group_name);
		cnt.add(new_group_lbl);
		
		JLabel commentslbl = new JLabel("Comments:");
		commentslbl.setForeground(Color.YELLOW);
		commentslbl.setBounds(start + 210, 40, 90, 20);
		cnt.add(commentslbl);
		comments = new JTextArea();
		comments.setToolTipText("Comments");
		comments.setWrapStyleWord(true);
		comments.setLineWrap(true);
		//comments.setEnabled(true);
		JScrollPane com_scroll = new JScrollPane(comments, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		//comments.setBorder(BorderFactory.createLineBorder(Color.black));
		com_scroll.setBounds(start + 280, 40, 200, 40);
		cnt.add(com_scroll);
		comments.addKeyListener(new KeyAdapter() {
	        @Override
	        public void keyPressed(KeyEvent e) {
	        	dirty=true;
	        	if (state==UPDATE_GROUP) save_group_btn.setEnabled(dirty && !group_name.getText().isEmpty());
	        }
	    });
		group_name.addKeyListener(new KeyAdapter() {
	        @Override
	        public void keyPressed(KeyEvent e) {
	        	dirty=true;
	        	save_group_btn.setEnabled(dirty &&!group_name.getText().isEmpty());
	        }
	    });
		
		teacher.jcb.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	dirty=true;
		    	if (state==UPDATE_GROUP) save_group_btn.setEnabled(!group_name.getText().isEmpty());
		    }
		});
		
		sub_group.jcb.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	dirty=true;
		    	if (state==UPDATE_GROUP) save_group_btn.setEnabled(!group_name.getText().isEmpty());
		    }
		});
		
		new_group_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (state==UPDATE_GROUP) {
					state = NEW_GROUP;
					new_group_btn.setToolTipText("Cancel new group's creation");
					new_group_btn.setIcon(CancelNewIcon);
					group_name.setText("");
					comments.setText("");
					teacher.setSelectedItem(-1);
					sub_group.setSelectedItem(-1);
					is_subclass.setSelected(false);
		    		ResetSelections((DefaultTableModel)group.getModel());
		    		dirty = true;
				} else {
					state = NEW_GROUP;
					new_group_btn.setToolTipText("Create a new group");
					new_group_btn.setIcon(NewIcon);	
					FilterGroup(null, 6);
					is_subclass.setSelected(false);
	        		DefaultTableModel mdl = (DefaultTableModel)groups.getModel();
	        		selected_group_id = Integer.parseInt(mdl.getValueAt(0, 0).toString());
	        		LoadGroup(selected_group_id, group);
					groups.setRowSelectionInterval(0, 0);
					dirty = false;
				}
			}
		});
		
		groups.setRowSelectionInterval(0, 0);
		screen.setModal(true);
		screen.setLocationRelativeTo(null);
		screen.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	//Cval.multirow_state_stack.pop();
		    	Cval.jtbl_stack.pop();
		    }
		});
		screen.setVisible(true);
	}
	
	public int resizeColumnWidth(JTable table) {
		int total_width=0;
	    final TableColumnModel columnModel = table.getColumnModel();
	    for (int column = 1; column < table.getColumnCount(); column++) {
	        int width = 15; // Min width
	        for (int row = 1; row < table.getRowCount(); row++) {
	            TableCellRenderer renderer = table.getCellRenderer(row, column);
	            Component comp = table.prepareRenderer(renderer, row, column);
	            width = Math.max(comp.getPreferredSize().width +1 , width);
	        }
	        if(width > 300)
	            width=300;
            total_width+=width;
	        columnModel.getColumn(column).setPreferredWidth(width);
	    }
	    return(total_width);
	}
	
	private JTable DBFillJTable(String query, Color clr, boolean with_select) {
		JTable result=null;
		DefaultTableModel local_model;
		local_model = new DefaultTableModel(){
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        if (with_select && (column==0)) return true;
		        else return false;
		    }
		    @Override
		    public Class<?> getColumnClass(int columnIndex) {
		       if ((with_select) && (columnIndex == 0)) {
		          return Boolean.class;
		        }
		        return super.getColumnClass(columnIndex);
		    }
		};
		int minus;
		if (with_select) {
			local_model.addColumn("Select"); //for edit btn
			minus = 0;
		} else minus = 1;
		
		
		String query_str = query;
	    int n = query_str.indexOf("FROM");
	    String q1 = query_str.substring(n+4).trim();
	    int table_end = q1.indexOf(" ");
	    String my_tbl = q1.substring(0, table_end).trim();
	    
	    //db_interface.table_from_parent = 
	    try {    	
	    	db_interface.getQueryResults(query_str);
	    	result = new JTable(local_model);
	    	/* {
			    @Override
			       public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
		        	   Component component = super.prepareRenderer(renderer, row, column);
		        	   int rendererWidth = component.getPreferredSize().width;
			           TableColumn tableColumn = getColumnModel().getColumn(column);
			       	   tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
			       	   return component;
			        }
			    };*/
			//db_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);;
	    	result.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	    	result.setBackground(clr);
			Cval.jtbl_stack.push(result);
			java.sql.ResultSetMetaData rsmdt = db_interface.rs.getMetaData();
	        int columns = rsmdt.getColumnCount();
	        if (with_select) result.getColumnModel().getColumn(0).setMaxWidth(35);

			for (int i = 1; i <= columns; i++ ) {
		        local_model.addColumn(rsmdt.getColumnLabel(i));
		        result.getColumnModel().getColumn(i-minus).setPreferredWidth(300);
			}
			
			result.setRowHeight(ROW_HEIGHT);
			// The column count starts from 1
			ArrayList<ArrayList<String>> fkeys;
			ArrayList<JComboBox<String>> cboxes = new ArrayList<JComboBox<String>>();
			ArrayList<Integer> combo_columns = new ArrayList<Integer>();
			for (int i = 1; i <= columns; i++ ) {
		        fkeys = db_interface.GetForeignKeyReferences(rsmdt.getColumnName(i), my_tbl);
		        if (fkeys!=null) {
		        	combo_columns.add(i+1);
		        	JComboBox<String> comboBox = new JComboBox<String>();
		        	for (int k=0;k<fkeys.size();k++)
      		        	comboBox.addItem(fkeys.get(k).get(1));
		        	cboxes.add(comboBox);
		        }
		        result.getColumnModel().getColumn(i-minus).setPreferredWidth(300);
			}
			result.removeColumn(result.getColumnModel().getColumn(1-minus));

			//db_interface.jtbl = result;
			Cval.jtbl_stack.push(result);
			
			local_model.setRowCount(0);
			
			while(db_interface.rs.next()){
				Object[] row = new Object[columns+2];
				int next_el=(with_select ? 1: 0 );
				int m=0;
				for (int j = 1; j <= columns; j++ )
					if (db_interface.listOf_int_fields.contains(rsmdt.getColumnType(j)))
						if (combo_columns.contains(j+1)) {
							cboxes.get(m).setSelectedIndex(db_interface.rs.getInt(j)-1);
							row[next_el++] = cboxes.get(m).getSelectedItem().toString();
						}
						else row[next_el++] = db_interface.rs.getInt(j); 
					else {						row[next_el++] = db_interface.rs.getString(j); 
						//if (!with_select) System.out.println("===>"+db_interface.rs.getString(j));
					}
				local_model.addRow(row);
			}
			db_interface.last_stmt.close();	              
			db_interface.rs.close();
	    } catch (SQLException e) {
    		e.printStackTrace();
    		JOptionPane.showMessageDialog(null, "Unable to connect a valid database... ","Error",JOptionPane.ERROR_MESSAGE);
    		System.exit(ERROR);
	    }
	    return result;
	}
	private boolean SaveGroup(JTable tbl) {
		String sql_group;
		String empty_participants="";
		System.out.println("state = " + state);
		if (state == NEW_GROUP) { 
			sql_group = "INSERT INTO groups VALUES(DEFAULT,\"" + group_name.getText() + "\",";
			sql_group += (teacher.getSelectedId()>0 ? teacher.getSelectedId() :  "NULL") +"," ;
			sql_group += ((is_subclass.isSelected() && sub_group.getSelectedId()>0) ? sub_group.getSelectedId() + ", " : "NULL, ");
			sql_group += (comments.getText().isEmpty()? "NULL)" : "\"" + comments.getText() + "\"");
			sql_group += (is_subclass.isSelected() ?  ",1)" : ",0)");
		} else { 
			sql_group = "UPDATE groups SET gname = '" + group_name.getText() + "', ";
			sql_group += "teacher_id = " + (teacher.getSelectedId()>0 ? teacher.getSelectedId() : "NULL") + ", ";
			sql_group += "class_id = " + ((is_subclass.isSelected() && sub_group.getSelectedId()>0) ? sub_group.getSelectedId() + ", " : "NULL, ");
			sql_group += "comments = " + (comments.getText().isEmpty() ? "NULL" : "\"" + comments.getText() + "\", ");
			sql_group += "sub_class = " + (is_subclass.isSelected() ?  "1" : "0");
			sql_group += " WHERE id = " + selected_group_id;
			empty_participants = "DELETE FROM participates where group_id = " + selected_group_id;
		}
		System.out.println("sql_group = " + sql_group);
		String add_members_new_group = "INSERT INTO participates VALUES ";
		String new_row_sql;
		try {
			db_interface.db_connection.setAutoCommit(false);
			Statement st = db_interface.db_connection.createStatement();
			st.execute(sql_group);
			if (state==UPDATE_GROUP) st.execute(empty_participants);
			if (state==NEW_GROUP) db_interface.getAux2QueryResults("SELECT max(id) as max_id from groups");
			int new_group_id;
			if (state==UPDATE_GROUP || db_interface.sql_success_aux2) {
				if (state==NEW_GROUP) {
					if (db_interface.rs_aux2.next() && (db_interface.rs_aux2.getString("max_id")!=null)) new_group_id=db_interface.rs_aux2.getInt("max_id");
					else new_group_id = 1;
					selected_group_id = new_group_id;
				} else new_group_id = selected_group_id;
				DefaultTableModel mdl = (DefaultTableModel)tbl.getModel();
				int m=0;
				for(int row = 0;row < tbl.getModel().getRowCount();row++)
					if ((mdl.getValueAt(row, 0)!=null) && ((Boolean)mdl.getValueAt(row, 0)==true)) {
						//System.out.println("Id = " + mdl.getValueAt(row, 1) + "name ="+ mdl.getValueAt(row, 2));
						m++;
						new_row_sql = "(" +  mdl.getValueAt(row, 1) +  "," + new_group_id  +"),";
						add_members_new_group += new_row_sql;
					}
				if (m>0) {
					add_members_new_group = add_members_new_group.substring(0,add_members_new_group.length()-1) + ";";
					System.out.println("2)" + add_members_new_group);
					st.execute(add_members_new_group);
				}
				st.close(); 
				db_interface.db_connection.commit();
				if (state==NEW_GROUP) JOptionPane.showMessageDialog(null, "New group has been stored");
				else JOptionPane.showMessageDialog(null, "Group info has been updated");
				db_interface.db_connection.setAutoCommit(true);	
				
				new_group_btn.setIcon(NewIcon);
				return(true);
			} else {
				db_interface.db_connection.rollback();
				JOptionPane.showMessageDialog(null, "Error! Unable to create new group1");
				return(false);
			}
		} catch (SQLException e) {
			//e.printStackTrace();
			if (e.getErrorCode()==1062) //duplicate group name
				JOptionPane.showMessageDialog(null, "Error! Duplicate group name");
			else  {
				JOptionPane.showMessageDialog(null, "Error! Unable to create new group (#"+e.getErrorCode()+")");
				System.err.println("--->" + e.getMessage());
			}
			return(false);
		}
	}
	
	private void LoadGroup(int group_id, JTable tbl) {
		String group_sql = "SELECT p.user_id AS id FROM groups AS g JOIN participates AS p ON g.id = p.group_id WHERE g.id = " + group_id;
		db_interface.getAux2QueryResults(group_sql);
		if (db_interface.sql_success_aux2) {
			int k;
    		DefaultTableModel mdl = (DefaultTableModel)tbl.getModel();
    		ResetSelections(mdl);
			try {
				Set<Integer> group_ids = new HashSet<Integer>();
				while (db_interface.rs_aux2.next())
					group_ids.add(db_interface.rs_aux2.getInt(1));
				db_interface.last_stmt_aux2.close();
				for (k=0;k<mdl.getRowCount();k++)
					mdl.setValueAt(group_ids.contains(Integer.parseInt(mdl.getValueAt(k, 1).toString())),k,0);
				//sub_group.setElements("SELECT id, gname  from groups  WHERE id<>" + group_id + " order by gname");
				String group_details_sql = "SELECT id, gname, teacher_id, class_id, sub_class, comments FROM groups WHERE id =" + group_id;
				db_interface.getAux2QueryResults(group_details_sql);
				if (db_interface.sql_success_aux2) {
					if (db_interface.rs_aux2.next()) {
						group_name.setText(db_interface.rs_aux2.getString("gname"));
						String loc_teacher_id = db_interface.rs_aux2.getString("teacher_id");
						if (loc_teacher_id!=null) teacher.setSelectedItem(Integer.parseInt(loc_teacher_id));
						else teacher.setSelectedItem(-1);
						String loc_class_id = db_interface.rs_aux2.getString("class_id");
						if (loc_class_id!=null) sub_group.setSelectedItem(Integer.parseInt(loc_class_id));
						else sub_group.setSelectedItem(-1);
						String loc_comments = db_interface.rs_aux2.getString("comments");
						if (loc_comments!=null) comments.setText(loc_comments);
						else  comments.setText("");
						if (db_interface.rs_aux2.getInt("sub_class")==1) {
							is_subclass.setSelected(true);
							sub_group_lbl.setVisible(true);
							sub_group.jcb.setVisible(true);
							sub_group.setSelectedItem(Integer.parseInt(loc_class_id));
						} else {
							is_subclass.setSelected(false);
							sub_group_lbl.setVisible(false);
							sub_group.jcb.setVisible(false);
						}
					}
				}
				db_interface.last_stmt_aux2.close();
				dirty = false;
				save_group_btn.setEnabled(false);
				state = UPDATE_GROUP;
			} catch (SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error! Unable to load group");
			}
		}
	}
	
	private void ResetSelections(DefaultTableModel mdl) {
		for (int k=0;k<mdl.getRowCount();k++)
			mdl.setValueAt(false, k, 0);
	}
	
	private void FilterGroup(String lookup, Integer SearchColumnIndex) {

        DefaultTableModel model = (DefaultTableModel) group.getModel();

        final TableRowSorter< DefaultTableModel> sorter = new TableRowSorter< DefaultTableModel>(model);
        group.setRowSorter(sorter);
        if (lookup==null)  sorter.setRowFilter(null);
        else sorter.setRowFilter(RowFilter.regexFilter("^(?i)" + lookup, SearchColumnIndex));
        /*
        txtSearch.addKeyListener(new KeyAdapter() {
	        @Override
	        public void keyPressed(KeyEvent e) {
                String txt = txtSearch.getText().toLowerCase();
                if (txt.length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    try {
                        sorter.setRowFilter(RowFilter.regexFilter("^(?i)" + txt, SearchColumnIndex));
                    } catch (PatternSyntaxException pse) {
                        System.out.println("Bad regex pattern");
                    }
                }
	        }
	    });*/
    }
}
