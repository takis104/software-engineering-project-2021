package schoolink;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

class ButtonRenderer extends JButton implements  TableCellRenderer{
	private static final long serialVersionUID = 1L;
	  public ButtonRenderer() {
	    setOpaque(true);
	  }
	  @Override
	  public Component getTableCellRendererComponent(JTable table, Object obj, boolean selected, boolean focused, int row, int col) {
		setIcon((ImageIcon)obj);
	    return this;
	  }
	}
