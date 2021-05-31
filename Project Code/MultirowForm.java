package schoolink;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
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
	private JTable db_table;
	static db_interface db;
	private DefaultTableModel model;
	static ImageIcon edit_icon;
	static ImageIcon del_icon;
	static int ROW_HEIGHT=35;
	static int PREFERRED_ROWS=12;
	int w,h;
	
	public MultirowForm(boolean allow_edit, boolean allow_delete, boolean allow_new_record) {	
		Container cnt;
		
		model = new DefaultTableModel(){
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        if (column>1) return false;
		        else return true;
		    }
		};
		model.addColumn(""); //for edit btn
		edit_icon = new ImageIcon(getClass().getResource("/images/edit.png"));
		model.addColumn("");//for del btn
        del_icon = new ImageIcon(getClass().getResource("/images/delete.png"));
       
		screen = new JFrame();
		db_interface.parent_window = screen;
		screen.getContentPane().setBackground(new Color(0, 0, 153));
		screen.setTitle("multirow_form:" + db_interface.user_surname + ": " + db_interface.school_name);
		cnt = screen.getContentPane();
		String query_str = db_interface.sql_from_parent;
	    int n = query_str.indexOf("FROM");
	    String q1 = query_str.substring(n+4).trim();
	    int table_end = q1.indexOf(" ");
	    db_interface.table_from_parent = q1.substring(0, table_end).trim();
	    //System.out.println(">>>>"+db_interface.table_from_parent+"<");
	    try {    	
	    	db_interface.getQueryResults(query_str);
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
			db_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);;
			db_interface.jtbl = db_table;
			java.sql.ResultSetMetaData rsmdt = db_interface.rs.getMetaData();
	        int columns = rsmdt.getColumnCount();
			for (int i = 1; i <= columns; i++ ) {
		        model.addColumn(rsmdt.getColumnLabel(i));
		        //System.out.println(rsmdt.getColumnLabel(i) + ":" +rsmdt.getColumnType(i));
		        db_table.getColumnModel().getColumn(i+1).setPreferredWidth(300);
			}
			if (allow_edit) {
				db_table.getColumnModel().getColumn(0).setCellRenderer(new ButtonCell(edit_icon));
		        db_table.getColumnModel().getColumn(0).setCellEditor(new ButtonCell(edit_icon));
		        db_table.getColumnModel().getColumn(0).setMaxWidth(35);
			}
			if (allow_delete) {
				db_table.getColumnModel().getColumn(1).setCellRenderer(new ButtonCell(del_icon));
				db_table.getColumnModel().getColumn(1).setCellEditor(new ButtonCell(del_icon));
				db_table.getColumnModel().getColumn(1).setMaxWidth(35);
			}
			db_table.setRowHeight(ROW_HEIGHT);
			// The column count starts from 1
			ArrayList<ArrayList<String>> fkeys;
			ArrayList<JComboBox<String>> cboxes = new ArrayList<JComboBox<String>>();
			ArrayList<Integer> combo_columns = new ArrayList<Integer>();
			for (int i = 1; i <= columns; i++ ) {
		        fkeys = db_interface.GetForeignKeyReferences(rsmdt.getColumnName(i));
		        //System.out.println(rsmdt.getColumnName(i) + "=>" + rsmdt.getColumnLabel(i) + ":" +rsmdt.getColumnType(i));
		        if (fkeys!=null) {
		        	combo_columns.add(i+1);
		        	//TableColumn indexColumn = db_table.getColumnModel().getColumn(i+1);
		        	JComboBox<String> comboBox = new JComboBox<String>();
		        	for (int k=0;k<fkeys.size();k++)
      		        	comboBox.addItem(fkeys.get(k).get(1));
		        	cboxes.add(comboBox);
		        	//indexColumn.setCellEditor(new DefaultCellEditor(comboBox));
		        }
		        db_table.getColumnModel().getColumn(i+1).setPreferredWidth(300);
			}
			db_table.removeColumn(db_table.getColumnModel().getColumn(2));

			db_interface.jtbl = db_table;
			model.setRowCount(0);
			
			while(db_interface.rs.next()){
				Object[] row = new Object[columns+2];
				int next_el=0;
				if (allow_edit) row[next_el++] = edit_icon;
				if (allow_delete) row[next_el++] = del_icon;
				int m=0;
				for (int j = 1; j <= columns; j++ )
					if (db_interface.listOf_int_fields.contains(rsmdt.getColumnType(j)))
						if (combo_columns.contains(j+1)) {
							cboxes.get(m).setSelectedIndex(db_interface.rs.getInt(j)-1);
							row[next_el++] = cboxes.get(m).getSelectedItem().toString();
						}
						else row[next_el++] = db_interface.rs.getInt(j); 
					else row[next_el++] = db_interface.rs.getString(j); 
				model.addRow(row);
			}
			db_interface.last_stmt.close();	              
			db_interface.rs.close();
			
	        JScrollPane pg = new JScrollPane(db_table,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	        db_table.setFillsViewportHeight(true);
	        w = resizeColumnWidth(db_table);
	        h = ROW_HEIGHT * PREFERRED_ROWS;
			screen.setSize(new Dimension(w + 40,h + 100));
			
			ImageIcon bg = new ImageIcon(getClass().getResource("/images/main_bg.png"));
			Image bg_img = bg.getImage().getScaledInstance(screen.getWidth(), screen.getHeight(), Image.SCALE_DEFAULT);
			screen.setContentPane(new ImagePanel(bg_img));
			cnt = screen.getContentPane();
			cnt.setLayout(null);

	        //System.out.println(h + "---> " + w);
	        pg.setBounds(10, 55, w,h);
	        cnt.add(pg);
    	} catch (Exception e) {
    		e.printStackTrace();
    		JOptionPane.showMessageDialog(null, "Unable to connect a valid database... ","Error",JOptionPane.ERROR_MESSAGE);
    		System.exit(ERROR);
    	}
	    
		JButton new_btn = new JButton((Icon) new ImageIcon(getClass().getResource("/images/new_category.png")));
		new_btn.setToolTipText("Add new");
	    new_btn.setMaximumSize(new Dimension(35,35));
	    new_btn.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent arg0) {
	        	EditRow edit_scr = new EditRow(db_interface.ADD, true);
	        }
	    });
	    new_btn.setBounds(10, 10, 35, 35);
	    cnt.add(new_btn);
	    
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

		screen.setLocationRelativeTo(null);
		screen.setVisible(true);
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
	        if(width > 300)
	            width=300;
            total_width+=width;
	        columnModel.getColumn(column).setPreferredWidth(width);
	    }
	    return(total_width);
	}
	
}
