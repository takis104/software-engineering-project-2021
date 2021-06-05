package schoolink;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.EmptyStackException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.AbstractCellEditor;

public class ButtonCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer{
	private static final long serialVersionUID = 1L;
	private JButton btn;
	static int row, col;
	ImageIcon dis_btn;
	private int column_id, column_cloud_id;

    ButtonCell(ImageIcon ic){
        btn = new JButton(ic);
        btn.setToolTipText("Edit");
        dis_btn = ic;
        
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	MultirowForm mr = Cval.multirow_instances_stack.peek();
            	mr.clicked_row = row;
            	int column_id = mr.column_id;
            	int column_cloud_id = mr.column_cloud_id;
            	int column_subject = mr.column_subject;
            	int column_sender_id = mr.column_sender_id;
            	//System.out.println("Pushed--> "+row + "," + column_id);
            	int clicked_id = (int) mr.db_table.getModel().getValueAt(row, column_id);
            	int prev_id_from_parent;
            	if (Cval.id_from_parent.isEmpty()) 	prev_id_from_parent=-1;
            	else {prev_id_from_parent = Cval.id_from_parent.peek();Cval.id_from_parent.pop();}
            	Cval.id_from_parent.push(clicked_id);
                //System.out.println("Pushed--> "+clicked_id);
                if (col==0) {
                	if (mr.edit_mode == Cval.OPEN_EDITOR) {
                		String msg_link = null;
                		if (column_cloud_id>0) msg_link = mr.db_table.getModel().getValueAt(row, column_cloud_id+1).toString();
                		//String html_msg_content = FileServer.GetHtmlFromFile(msg_link);
        				StringPair p = db_interface.getMsgRecipients(clicked_id);
        				db_interface.last_msg_to = p.id;
        				db_interface.last_msg_cc = p.link;
        				if (column_subject>0) db_interface.last_msg_subject =  mr.db_table.getModel().getValueAt(row, column_subject).toString();
        				if (column_sender_id>0) {
        					db_interface.reply_to_sender_id = Integer.parseInt(mr.db_table.getModel().getValueAt(row, column_cloud_id+2).toString());
        					db_interface.reply_to_sender_name = mr.db_table.getModel().getValueAt(row, column_cloud_id).toString();
        				}
        				System.out.println("<=>" + msg_link + ","+ db_interface.last_msg_subject + "," + db_interface.reply_to_sender_name + "," + db_interface.reply_to_sender_id);
        				Cval.multirow_instances_stack.peek().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        				db_interface.default_cursor = Cursor.getDefaultCursor();
        				Cval.reply_to=1;
                		new MessageFx(Cval.ScreenWidth, Cval.ScreenHeight, "Εισερχόμενο μήνυμα", msg_link, 0);
                	} else if  (mr.edit_mode== Cval.OPEN_MULTIROW) {
                		if (prev_id_from_parent>0)
                			System.out.println("---->" + prev_id_from_parent + "," + clicked_id);
                		else {
                			//String sql_from_parent = "SELECT m.id AS Κωδικός, CONCAT(u.surname,' ', u.firstname) AS Αποστολέας, m.msg_date AS Ημερομηνία,  m.deadline as Προθεσμία, m.msg_subject AS Θέμα, m.cloud_id AS link FROM msgs as m INNER JOIN msgs_details as d ON d.msg_id = m.id INNER JOIN users as u ON u.id=d.from_user_id WHERE m.deadline is not null and u.id=" + db_interface.user_id +" ORDER BY m.msg_date desc";
                			String sql_from_parent = "SELECT m.id AS Κωδικός, CONCAT(u.surname,' ', u.firstname) AS Αποστολέας, m.msg_date AS Ημερομηνία, m.deadline as Προθεσμία, m.msg_subject AS Θέμα, m.cloud_id AS link FROM msgs as m INNER JOIN msgs_details as md ON md.msg_id = m.id INNER JOIN users as u ON u.id = md.from_user_id WHERE m.parent_msg_id = " + Cval.id_from_parent.peek()  + " ORDER by m.msg_date DESC";
                			new MultirowForm("Απαντήσεις", sql_from_parent, true, true, true, Cval.OPEN_EDITOR); //last true = call view message on edit
                		}
                	} else {
                		System.out.println("1a>" + clicked_id);
                		//System.out.println("1b>" + Cval.multirow_parent_table_stack.peek());
                		//Cval.sql_from_parent.push("SELECT * FROM " + Cval.multirow_parent_table_stack.peek() + " WHERE id = " + clicked_id); 
                		new EditRow(mr.my_sql_tbl, clicked_id, db_interface.MODIFY, true);
                	}
                } else {
                	if (JOptionPane.showConfirmDialog(null, "Are you sure?", "WARNING",
                	        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                		try {
							db_interface.execute("DELETE FROM " +mr.my_sql_tbl + " WHERE id = " + clicked_id);
							Cval.multirow_instances_stack.peek().populate_jtable(false);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							JOptionPane.showMessageDialog(null, "Unable to delete record... ","Error",JOptionPane.ERROR_MESSAGE);
						}
                		
                	} 
                	
                }
/*
                switch (db_interface.calling) {
	                case db_interface.JOURNALIST:
		                if (col==0) {
		                	int article_id = (int) db_interface.jtbl.getModel().getValueAt(row, col+1);
		                	new EditArticle(Journalist.screen , true, Journalist.db ,article_id);
		                }
	                	break;
	                case db_interface.PUBLISHER:
		                if (col==0) {
		                	int newspaper_id = (int) db_interface.jtbl.getModel().getValueAt(row, col+1);
		                	new EditNewspaper(Publisher.screen , true, Publisher.db ,newspaper_id);
		                }
	                	break;
	                case db_interface.ADMINISTRATION_EDIT_EMPLOYEE:
	                	int employee_id = (int) db_interface.jtbl.getModel().getValueAt(row, col+1);
	                	new EditEmployee(Publisher.screen , true, Administration.db ,employee_id);
	                	break;
	                case db_interface.ADMINISTRATION_EDIT_ISSUE:
	                	int issue_no = (int) db_interface.jtbl.getModel().getValueAt(row, col+2);
	                	int newspaper_id = (int) db_interface.jtbl.getModel().getValueAt(row, col+1);
	                	try {
	                		int ret_issues = Integer.parseInt(db_interface.jtbl.getModel().getValueAt(row, col+6).toString());
	                		db_interface.UpdateIssue(newspaper_id, issue_no,ret_issues);
	                		JOptionPane.showMessageDialog(null, "Returned copies has been sucessfully stored...");
	                	} catch (Exception e1) {
	                		JOptionPane.showMessageDialog(null, "Failed to save the value of returned copies!!","Error",JOptionPane.ERROR_MESSAGE);
	                		e1.printStackTrace();
	                	}	
	                	break;
	                case db_interface.ADMINISTRATION_FINANCE:
	                	break;
	                case db_interface.CHIEF_EDITOR1:
	                	int article_id = (int) db_interface.jtbl.getModel().getValueAt(row, col+1);
	                	new EditArticle(ChiefEditor.screen , true, ChiefEditor.db ,article_id);
	                	break;
	                case db_interface.CHIEF_EDITOR2:
	                	int category_id = (int) db_interface.jtbl.getModel().getValueAt(row, col+1);
	                	new EditCategory(ChiefEditor.screen , true, ChiefEditor.db,category_id);
	                	break;
                }
                */
            }
        });
    }

    @Override 
    public Object getCellEditorValue() {
        return null;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if(value instanceof Icon){
            btn.setIcon((Icon) value);
        } else {
            btn.setIcon(dis_btn);
        }
        return btn;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if(value instanceof Icon){
            btn.setIcon((Icon) value);
        } else {
            btn.setIcon(dis_btn);
        }
        ButtonCell.row=row;
        ButtonCell.col=column;
        return btn;
    }

}