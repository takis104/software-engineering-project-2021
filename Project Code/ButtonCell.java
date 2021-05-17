package schoolink;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.AbstractCellEditor;

public class ButtonCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer{
	private static final long serialVersionUID = 1L;
	private JButton btn;
	public static int row,col;
	ImageIcon dis_btn;

    ButtonCell(ImageIcon ic){
        btn = new JButton(ic);
        btn.setToolTipText("Edit");
        dis_btn = ic;       
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	int clicked_id = (int) db_interface.jtbl.getModel().getValueAt(row, 2);
            	db_interface.id_from_parent=clicked_id;
                System.out.println("clicked--> "+row+","+col + "," + clicked_id);
                if (col==0) {
                	db_interface.sql_from_parent = "SELECT * FROM" + db_interface.table_from_parent + " WHERE id = " + clicked_id;
                	EditRow edit_scr = new EditRow(db_interface.MODIFY, true);
                } else {
                	JOptionPane.showMessageDialog(null, "Delete row...");
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