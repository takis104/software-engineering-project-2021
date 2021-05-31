package schoolink;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;

public class RowObject {
	JLabel label;
	JComponent ctrl;
	String field;
	String init_value;
	int data_type;

	public RowObject(JLabel label, JComponent ctrl, String field, String init_value, int data_type) {
		this.label = label;
		this.ctrl = ctrl;
		this.field = field;
		this.init_value = init_value;
		this.data_type = data_type;
	}
	
	@SuppressWarnings("rawtypes")
	public String getText() {
		switch (ctrl.getClass().getName()) {
			case "javax.swing.JTextField": 
				return ((JTextField)ctrl).getText();
			case "javax.swing.JComboBox":
				String combo_text = ((JComboBox)ctrl).getSelectedItem().toString();
				if (combo_text.equals("-")) return null;
				String combo_name = ((JComboBox)ctrl).getName();
				int combo_selected_index = ((JComboBox)ctrl).getSelectedIndex();
				System.out.println("name = " +combo_name + "indx = " + combo_selected_index);
				String[] result = combo_name.split(",");
				return result[combo_selected_index];
		}
		return "";
	}
}
