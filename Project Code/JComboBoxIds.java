package schoolink;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JComboBox;

public class JComboBoxIds {
	public JComboBox<String> jcb;
	public ArrayList<Integer> ids;
	
	
	public JComboBoxIds(String query) {
		jcb = new JComboBox<String>();
		ids = new ArrayList<Integer>();
		setElements(query);
	}
	
	public int getSelectedId() {
		int k = jcb.getSelectedIndex();
		if (k>0) return ids.get(k);
		else return -1;
	}
	
	public String getSelectedText() {
		return jcb.getSelectedItem().toString();
	}
	
	public void setSelectedItem(int id) {
		if (id<0) jcb.setSelectedIndex(-1);
		else jcb.setSelectedIndex(ids.indexOf(id));
	}
	
	public void setElements(String query) {
		ids.clear();
		jcb.removeAllItems();
		ResultSet rs = null;
		ids.add(-1);
		jcb.addItem("");
		try {
			Statement st = db_interface.db_connection.createStatement();
	        rs = st.executeQuery(query);
	        if (rs != null)
	          while (rs.next()) {
	        	  ids.add(rs.getInt(1));
	        	  jcb.addItem(rs.getString(2));
	          }
	        jcb.setSelectedIndex(0);
	        st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
