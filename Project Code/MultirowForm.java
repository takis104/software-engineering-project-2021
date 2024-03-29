package schoolink;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

@SuppressWarnings("serial")
public class MultirowForm extends JFrame {
	private JFrame screen;
	public JTable db_table;
	private DefaultTableModel model;
	private boolean allow_edit, allow_delete;

	private int w,h;
	private JScrollPane pg;
	private java.sql.ResultSetMetaData rsmdt;
	private int  columns;
	private ArrayList<JComboBox<String>> cboxes;
	private ArrayList<Integer> combo_columns;
	private String query_str;
	private int init_width;
	public int edit_mode;
	
	public String my_sql_tbl;
	
	public int column_id=-1;
	public int column_cloud_id=-1;
	public int column_subject=-1;
	public int column_sender_id = -1;
	public int clicked_row = -1;
	
	private String sql_string_multirow;
	
	static ImageIcon edit_icon;
	static ImageIcon del_icon;
	static int ROW_HEIGHT=35;
	static int PREFERRED_ROWS=12;
	
	public MultirowForm(String title, String sql, boolean allow_addnew, boolean allow_edit, boolean allow_delete, int edit_mode) {	
		Container cnt;
		sql_string_multirow = sql;
		pg = null;
		this.allow_edit = allow_edit;
		this.allow_delete = allow_delete;
		this.edit_mode = edit_mode;
		
		Cval.multirow_instances_stack.push(this);
		
		
		model = new DefaultTableModel(){
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        if (column>1) return false;
		        else return true;
		    }
		};
		if (allow_edit) model.addColumn(""); //for edit btn
		edit_icon = new ImageIcon(getClass().getResource("/images/edit.png"));
		if (allow_delete) model.addColumn("");//for del btn
        del_icon = new ImageIcon(getClass().getResource("/images/delete.png"));
       
		screen = new JFrame();
		//db_interface.parent_window = screen;
		
		screen.getContentPane().setBackground(new Color(0, 0, 153));
		screen.setTitle("(" + db_interface.user_role + ": " + db_interface.user_surname + ": " + db_interface.school_name + ")");
		cnt = screen.getContentPane();
		
		String query_str = sql;
	    int n = query_str.indexOf("FROM");
	    String q1 = query_str.substring(n+4).trim();
	    int table_end = q1.indexOf(" ");
	    my_sql_tbl = q1.substring(0, table_end).trim();
	    //System.out.println(">>>>"+my_tbl+"<");
	    try {    	
			db_interface.getQueryResults(query_str);
			rsmdt = db_interface.rs.getMetaData();
	        columns = rsmdt.getColumnCount();
			for (int i = 1; i <= columns; i++ ) {
		        model.addColumn(rsmdt.getColumnLabel(i));
		        //System.out.println(rsmdt.getColumnLabel(i) + ":" +rsmdt.getColumnType(i));
		        //db_table.getColumnModel().getColumn(i+1).setPreferredWidth(300);
			}
			db_table = new JTable(model) {
			    @Override
			       public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
			           Component component = super.prepareRenderer(renderer, row, column);
			           int rendererWidth = component.getPreferredSize().width;
			           TableColumn tableColumn = getColumnModel().getColumn(column);
			           tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
			           return component;
			        }
			    };
			db_table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);//AUTO_RESIZE_OFF);
			db_table.setRowHeight(ROW_HEIGHT);
			
			// The column count starts from 1
			ArrayList<ArrayList<String>> fkeys;
			cboxes = new ArrayList<JComboBox<String>>();
			combo_columns = new ArrayList<Integer>();
			int base=-1;
	        if (allow_edit) base++;
	        if (allow_delete) base++;
			for (int i = 1; i <= columns; i++ ) {
		        fkeys = db_interface.GetForeignKeyReferences(rsmdt.getColumnName(i), my_sql_tbl);
		        //System.out.println(rsmdt.getColumnName(i) + "=>" + rsmdt.getColumnLabel(i) + ":" +rsmdt.getColumnType(i));
		        if (fkeys!=null) {
		        	combo_columns.add(i+base);
		        	//TableColumn indexColumn = db_table.getColumnModel().getColumn(i+1);
		        	JComboBox<String> comboBox = new JComboBox<String>();
		        	for (int k=0;k<fkeys.size();k++)
      		        	comboBox.addItem(fkeys.get(k).get(1));
		        	cboxes.add(comboBox);
		        	//indexColumn.setCellEditor(new DefaultCellEditor(comboBox));
		        }
		        db_table.getColumnModel().getColumn(i+base).setPreferredWidth(300);
			}
			int m=0;
			if (allow_edit) {
				db_table.getColumnModel().getColumn(m).setCellRenderer(new ButtonCell(edit_icon));
		        db_table.getColumnModel().getColumn(m).setCellEditor(new ButtonCell(edit_icon));
		        db_table.getColumnModel().getColumn(m++).setMaxWidth(35);
			}
			if (allow_delete) {
				db_table.getColumnModel().getColumn(m).setCellRenderer(new ButtonCell(del_icon));
				db_table.getColumnModel().getColumn(m).setCellEditor(new ButtonCell(del_icon));
				db_table.getColumnModel().getColumn(m++).setMaxWidth(35);
			}
			for (int k=0;k<db_table.getColumnModel().getColumnCount();k++) {
				if (db_table.getColumnName(k).equals("Κωδικός")) column_id = k;
				else if (db_table.getColumnName(k).equals("online_id")) column_cloud_id = k;
				else if (db_table.getColumnName(k).equals("sender_id")) column_sender_id = k;
				else if (db_table.getColumnName(k).equals("Θέμα")) column_subject=k;
			}		
			//TO DO : sort and remove from end to start
			if (column_id>0) {
				db_table.removeColumn(db_table.getColumnModel().getColumn(column_id));
				if (column_cloud_id>column_id) column_cloud_id--;
				if (column_sender_id>column_id) column_sender_id--;
			}
			if (column_cloud_id>0) {
				db_table.removeColumn(db_table.getColumnModel().getColumn(column_cloud_id));
				if (column_sender_id>column_cloud_id) column_sender_id--;
			}
			if (column_sender_id>0) db_table.removeColumn(db_table.getColumnModel().getColumn(column_sender_id));
			
			w = populate_jtable(true);
			db_interface.rs.close();
			pg = new JScrollPane(db_table,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	        db_table.setFillsViewportHeight(true);
    	} catch (Exception e) {
    		e.printStackTrace();
    		JOptionPane.showMessageDialog(null, "Unable to connect a valid database... ","Error",JOptionPane.ERROR_MESSAGE);
    		System.exit(ERROR);
    	}
	    
        h = ROW_HEIGHT * PREFERRED_ROWS;
        if (w<260) w=260;
		screen.setSize(new Dimension(w + 40,h + 100));
		
		ImageIcon bg = new ImageIcon(getClass().getResource("/images/main_bg.png"));
		Image bg_img = bg.getImage().getScaledInstance(screen.getWidth(), screen.getHeight(), Image.SCALE_DEFAULT);
		screen.setContentPane(new ImagePanel(bg_img));
		cnt = screen.getContentPane();
		cnt.setLayout(null);
		
		JLabel title_lbl = new JLabel();
		title_lbl.setText(title);
		title_lbl.setForeground(Color.YELLOW);
		title_lbl.setFont(new Font("Helvetica", Font.ITALIC, 16));
		if (allow_addnew) title_lbl.setBounds(60, 10, 400, 35);
		else title_lbl.setBounds(10, 10, 400, 35);
		cnt.add(title_lbl);

        //System.out.println(h + "---> " + w);
        pg.setBounds(10, 55, w,h);
        cnt.add(pg);
	    if (allow_addnew) {
			JButton new_btn = new JButton((Icon) new ImageIcon(getClass().getResource("/images/new_category.png")));
			new_btn.setToolTipText("Add new");
		    new_btn.setMaximumSize(new Dimension(35,35));
		    new_btn.addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent arg0) {
		        	if (edit_mode==Cval.OPEN_EDITOR) 
		        		new MessageFx(Cval.ScreenWidth, Cval.ScreenHeight, "Νέο μήνυμα",null, 0);
		        	else new EditRow(my_sql_tbl, -1,  db_interface.ADD, true);
		        }
		    });
		    new_btn.setBounds(10, 10, 35, 35);
		    cnt.add(new_btn);
	    }
		JButton exit_btn = new JButton((Icon) new ImageIcon(getClass().getResource("/images/exit.png")));
		exit_btn.setToolTipText("Exit");
		exit_btn.setMaximumSize(new Dimension(35, 35));
		exit_btn.setBounds(w-50, 10, 35, 35);
		exit_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				screen.dispose();
			}
		});
		cnt.add(exit_btn);
		screen.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	if (!Cval.multirow_instances_stack.isEmpty()) Cval.multirow_instances_stack.pop();

		    }
		});
		screen.setLocationRelativeTo(null);
		screen.setVisible(true);
	}
	
	public int populate_jtable(boolean form_init) {
	    //System.out.println("1>>>>"+db_interface.table_from_parent+"<");
	    //System.out.println("2>>>>"+db_interface.sql_from_parent+"<");
	    try {    				
			model.setRowCount(0);
			if (!form_init) db_interface.getQueryResults(sql_string_multirow);
			while(db_interface.rs.next()){
				Object[] row = new Object[columns+2];
				int next_el=0;
				if (allow_edit) row[next_el++] = edit_icon;
				if (allow_delete) row[next_el++] = del_icon;
				int m=0;
				for (int j = 1; j <= columns; j++ )
					if (db_interface.listOf_int_fields.contains(rsmdt.getColumnType(j)))
						if (combo_columns.contains(j+1)) {
							try {
								cboxes.get(m).setSelectedIndex(db_interface.rs.getInt(j));
							} catch (Exception e) {
								cboxes.get(m).setSelectedIndex(db_interface.rs.getInt(j)-1);
							}
							if (cboxes.get(m).getSelectedItem()!=null) row[next_el++] = cboxes.get(m).getSelectedItem().toString();
							else row[next_el++] = "----";
							m++;
						}
						else row[next_el++] = db_interface.rs.getInt(j); 
					else row[next_el++] = db_interface.rs.getString(j); 
				model.addRow(row);
			}
			db_interface.last_stmt.close();	              
			db_interface.rs.close();
	        //pg = new JScrollPane(db_table,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	        //db_table.setFillsViewportHeight(true);
	        if (form_init) {init_width = resizeColumnWidth(db_table)+100;  return (init_width);}
	        else return init_width;
    	} catch (Exception e) {
    		e.printStackTrace();
    		JOptionPane.showMessageDialog(null, "Unable to connect a valid database... ","Error",JOptionPane.ERROR_MESSAGE);
    		System.exit(ERROR);
    	}	
	    return (-1);
	}
	
	public int resizeColumnWidth(JTable table) {
		int total_width=0;
	    final TableColumnModel columnModel = table.getColumnModel();
	    for (int column = 1; column < table.getColumnCount(); column++) {
	        int width = 15; // Min width
	        for (int row = 0; row < table.getRowCount(); row++) {
	            TableCellRenderer renderer = table.getCellRenderer(row, column);
	            Component comp = table.prepareRenderer(renderer, row, column);
	            width = Math.max(comp.getPreferredSize().width +1 , width);
	        }
	        if(width > 300)  width=300;
	        if (width<40) width = 40;
            total_width+=width;
	        columnModel.getColumn(column).setPreferredWidth(width);
	    }
	    return(total_width);
	}
	
}
