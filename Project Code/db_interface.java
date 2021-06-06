package schoolink;
import java.awt.Cursor;
import java.awt.Window;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class db_interface {
	   // JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://localhost:3306/schoolink?characterEncoding=utf8&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Athens";
	   static final String USER = "root";
	   static final String PASS = "";
	   
	   static final String year="";
	   static boolean status=true;
	   
	   //static MultirowForm multirow_form;
	      
	   //static final int JOURNALIST = 1;
	   //static final int PUBLISHER = 2;
	   //static final int ADMINISTRATION_EDIT_EMPLOYEE = 31;
	   //static final int ADMINISTRATION_EDIT_ISSUE = 32;
	   //static final int ADMINISTRATION_FINANCE= 33;
	   //static final int CHIEF_EDITOR1 = 41;
	   //static final int CHIEF_EDITOR2 = 42;
	   
	   static final int DIRECTOR = 1;
	   static final int ADMINISTATION = 2;
	   static final int TEACHER = 3;
	   static final int PUPIL = 4;
	   static final int PARENT= 5;
	   
	   static Connection db_connection = null;
	   static DatabaseMetaData meta;
	   
	   static ResultSet rs = null;
	   static Statement last_stmt = null;
	   static boolean sql_success = false;
	   static ResultSet rs_aux = null;
	   static Statement last_stmt_aux = null;
	   static boolean sql_success_aux = false;
	   static ResultSet rs_aux2 = null;
	   static Statement last_stmt_aux2 = null;
	   static boolean sql_success_aux2 = false;
	   	   
	   public static int user_id;
	   public static String user_email;
	   public static String user_firstname;
	   public static String user_surname;
	   public static String user_role;
	   public static int user_auth_level;
	   
	   public static String school_name;
	   public static String school_email;
	   public static String school_phone;
	   public static String school_fax;
	   public static String schoool_web;
	   public static int school_director_id;
	   public static int school_id;
	   
	   
	   public static String last_msg_to;
	   public static String last_msg_cc;
	   public static String last_msg_subject;
	   public static String reply_to_sender_name;
	   public static int reply_to_sender_id;
	   
	   public static Cursor default_cursor;
	   public static int ADD=0;
	   public static int MODIFY=1;
	   
	   //public static JFrame parent_window;
	   //public static String sql_from_parent;
	   //public static String table_from_parent;
	   //public static int id_from_parent;
	   
	   //public static String sql_string_multirow;
	   
	   public static ArrayList<Integer> listOf_int_fields;
	   
	   /*TO BE REMOVED*/
	   public int employee_id;
	   public String employee_name;
	   public int newspaper_id;
	   public int user_level;
	   /*TO BE REMOVED*/
	   
	   //public static JTable jtbl;
	   public static int last_id;
	   public static int calling;
	   
	   public boolean isDbConnected() {
		    //final String CHECK_SQL_QUERY = "SELECT 1";
		    try {
		        if(db_connection.isClosed() || db_connection!=null){
		            return true;
		        }
		    } catch (SQLException e) {
		        return false;
		    }
		    return false;
		}
	   
	   public static boolean EstablishConnection() {
        
	        try {
	            //STEP 2: Register JDBC driver
	        	System.out.println("Connecting to database...");
	            //Class.forName("com.mysql.jdbc.Driver");
	        	db_connection = DriverManager.getConnection(DB_URL,USER,PASS);
	            System.out.println("Connected....");
	            DatabaseMetaData meta = db_connection.getMetaData();
				listOf_int_fields = new ArrayList<Integer>();
				listOf_int_fields.add(java.sql.Types.TINYINT);
				listOf_int_fields.add(java.sql.Types.INTEGER);
				listOf_int_fields.add(java.sql.Types.SMALLINT);
				listOf_int_fields.add(java.sql.Types.BIGINT);
				return true;
			} catch (Exception e) {
			       //e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Failed to establish a connection to a valid database!","Error",JOptionPane.ERROR_MESSAGE);
				return false;
	        }
	   }
	   
	   static void CloseConnection() {
		   try {
			   db_connection.close();
		   } catch(Exception e){
		       //Handle errors 
		       e.printStackTrace();
		  }
	   }
	    
	   static void getQueryResults(String QueryStr) { 
		   last_stmt = null;
		   try {
		      if (db_connection==null) {
		    	  System.out.println("Not Connected....");
		    	  return;
		      }
	          //System.out.println("Creating statement..." + QueryStr);
	          last_stmt = db_connection.createStatement();
	          rs = last_stmt.executeQuery(QueryStr);
	          if (rs != null) {
			         sql_success=true;
	          } else {
	        	  sql_success=false;
	          }
	      } catch(SQLException se){
		          //Handle errors for JDBC
		          se.printStackTrace();
		          sql_success=false;
		          return;
		  }catch(Exception e){
		       //Handle other errors
		       e.printStackTrace();
		       sql_success=false;
		       return;
		  } finally {
		  }
	   }
	   
	   static void getAuxQueryResults(String QueryStr) { 
		   last_stmt_aux = null;
		   try {
		      if (db_connection==null) {
		    	  System.out.println("Not Connected....");
		    	  return;
		      }
	          //System.out.println("Creating statement:> " + QueryStr);
	          last_stmt_aux = db_connection.createStatement();
	          rs_aux = last_stmt_aux.executeQuery(QueryStr);
	          if (rs_aux != null) {
			         sql_success_aux=true;
	          } else {
	        	  sql_success_aux=false;
	          }
	      } catch(SQLException se){
		          //Handle errors for JDBC
		          se.printStackTrace();
		          sql_success_aux=false;
		          return;
		  }catch(Exception e){
		       //Handle other errors
		       e.printStackTrace();
		       sql_success_aux=false;
		       return;
		  } finally {
		  }
	   }
	   
	   static void getAux2QueryResults(String QueryStr) { 
		   last_stmt_aux2 = null;
		   try {
		      if (db_connection==null) {
		    	  System.out.println("Not Connected....");
		    	  return;
		      }
	          //System.out.println("Creating statement:> " + QueryStr);
	          last_stmt_aux2 = db_connection.createStatement();
	          rs_aux2 = last_stmt_aux2.executeQuery(QueryStr);
	          if (rs_aux2 != null) {
			         sql_success_aux2=true;
	          } else {
	        	  sql_success_aux2=false;
	          }
	      } catch(SQLException se){
		          //Handle errors for JDBC
		          se.printStackTrace();
		          sql_success_aux2=false;
		          return;
		  }catch(Exception e){
		       //Handle other errors
		       e.printStackTrace();
		       sql_success_aux2=false;
		       return;
		  } finally {
		  }
	   }
	   
	   static void getLoginData(String uname) {
		   String sql_str = "SELECT id, firstname, surname, email, role_id, password FROM "+ "users"+ year + " WHERE username='" + uname+"'";
		   getQueryResults(sql_str);
	   }
	   
	   static void getSchoolParams() {
		   String sql_str = "SELECT school_name,email, phone, fax, web, director_id, school_id FROM "+ "school_params"+ year;
		   getAuxQueryResults(sql_str);		
			try {
				if (rs_aux.next()){
					school_name = rs_aux.getString("school_name");
					school_email = rs_aux.getString("email");
					school_phone = rs_aux.getString("phone"); 
					school_fax = rs_aux.getString("fax");
					schoool_web = rs_aux.getString("web");
					school_director_id =rs_aux.getInt("director_id");
					school_id =rs_aux.getInt("school_id");
				}
				rs_aux.close();
				last_stmt_aux.close();
			} catch(SQLException se){
		          //Handle errors for JDBC
		          se.printStackTrace();
		          return;
		  }
	   }
	   
	   public void RetrieveCategories() {
		   String sql_str = "SELECT * FROM ArticlesCategory WHERE id!=-1";
		   getAuxQueryResults(sql_str);   
	   }
	   
		void getCategories(JComboBox<String> art_category, List<Integer> CategoryComboIndexToId) {
			try {
				List<String[]> ctg = new ArrayList<String[]>();
				RetrieveCategories();
				while (rs_aux.next()){
					ctg.add(new String[] {rs_aux.getString("id"),rs_aux.getString("cname"), rs_aux.getString(3)});
					CategoryComboIndexToId.add(rs_aux.getInt("Id"));
				}
				rs_aux.close();
				last_stmt_aux.close();
				String tmp="";
				int n;
				for (int k=0;k<ctg.size();k++) {
					tmp = ctg.get(k)[1];
					n=Integer.parseInt(ctg.get(k)[2]);
					while (n!=-1) {
						tmp = ctg.get(n-1)[1] + "->"+ tmp;
						n = Integer.parseInt(ctg.get(n-1)[2]);
					}
					art_category.addItem(tmp);
				}		
				ctg.clear();
			} catch(SQLException se){
		          //Handle errors for JDBC
		          se.printStackTrace();
		          return;
		  }
		} 
	   
	   public void RetrieveJournalists() {
		   String sql_str = "SELECT Journalists.Employee_id as Id, Employees.firstname,Employees.surname FROM Employees INNER JOIN Journalists on Employees.id=Journalists.Employee_id;";
		   getAuxQueryResults(sql_str);   
	   }
	   
	   
	   void getJournalists(DefaultListModel<String> journ_listModel, List<Integer> JournalistComboIndexToId) {
			try {
				RetrieveJournalists();
				while (rs_aux.next()){
					journ_listModel.addElement(rs_aux.getString(2)+" "+ rs_aux.getString(3));
					JournalistComboIndexToId.add(rs_aux.getInt(1));
					//System.out.println(rs_aux.getString("Id")+") "+rs_aux.getString(2)+" "+ rs_aux.getString(3));
				}
				rs_aux.close();
				last_stmt_aux.close();
			} catch(SQLException se){
		          //Handle errors for JDBC
		          se.printStackTrace();
		          return;
		  }
		} 
	   
	   
	   public void RetrieveNewsPapers() {
		   String sql_str = "SELECT * FROM Newspapers";
		   getAuxQueryResults(sql_str);   
	   }
	   
		void getNewsPapers(JComboBox<String> art_newspaper, List<Integer> NewpaperIndexToId) {
			try {
				RetrieveNewsPapers();
				while (rs_aux.next()){
					//System.ot.println(db.rs_aux.getString(1));
					//ctg.add(new String[] {db.rs_aux.getString(1),db.rs_aux.getString("name")), db.rs_aux.getString(3)});
					art_newspaper.addItem(rs_aux.getString("name"));
					NewpaperIndexToId.add(rs_aux.getInt("Id"));
				}
			} catch(SQLException se){
		          //Handle errors for JDBC
		          se.printStackTrace();
		          return;
		  }
		} 
	   
	public db_interface() {
		status = db_interface.EstablishConnection();
	}
	
	void printrs(ResultSet rs) throws SQLException {
		while (rs_aux.next()){
			System.out.print("---> " + rs_aux.getString(1));
		}
	}

	static void UpdateIssue(int newspaper_id, int issue_no, int ret_issues) {
		String sql_upd = "UPDATE Issues SET returned_copies = " + ret_issues;
		sql_upd += " WHERE newspaper_id = " + newspaper_id + " AND issue_no = " + issue_no;
		try {
			Statement st = db_connection.createStatement();
			st.execute(sql_upd);
		} catch (Exception e1) {
    		JOptionPane.showMessageDialog(null, "Failed to save the value of the returned copies!","Error",JOptionPane.ERROR_MESSAGE);
    		e1.printStackTrace();
    	}
	}
	
	
	public void StringToRows(String tbl,  String src) {
		if (src.length()==0) return;
		String sql_st = "INSERT INTO " + tbl + " VALUES";
		StringTokenizer sttok = new StringTokenizer(src,",");
		try {
			Statement st = db_connection.createStatement();
			while (sttok.hasMoreTokens()) {  
				sql_st = "INSERT INTO " + tbl + " VALUES(LAST_INSERT_ID(),'" + sttok.nextToken() + "');"; 
				System.out.println("aaaa>" + sql_st); 
				st.execute(sql_st);
			}  
		} catch (Exception e1) {
    		JOptionPane.showMessageDialog(null, "Failed to save some values in table "+ tbl,"Error",JOptionPane.ERROR_MESSAGE);
    		e1.printStackTrace();
    	}
	}
	
	public static  ArrayList<String> getSubClasses() {
		ArrayList<String> ret_val = new ArrayList<String>();
		String sql_st = "SELECT g.id AS gid, g.gname AS gname, c.id AS cid, c.cname AS cname FROM groups AS g INNER JOIN classes AS c ON g.class_id = c.id WHERE g.sub_class=1 ORDER BY c.cname, g.gname";
		getAuxQueryResults(sql_st);
		String res = "";
		if (sql_success_aux) {
			try {
				int m=0;
				while (rs_aux.next()) {
					res = rs_aux.getString(1) + "," + rs_aux.getString(2) + "," + rs_aux.getString(3) + "," + rs_aux.getString(4);
					//System.out.println("Subclass" + (m++) + " = " + res);
					ret_val.add(res);
				}
				rs_aux.close();
				last_stmt_aux.close();
				return ret_val;
			} catch (Exception e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Wrong Inputs", "Please Check", JOptionPane.WARNING_MESSAGE);
				return null;
			}
		}
		return ret_val;
	}

	public static  ArrayList<String> getClasses() {
		ArrayList<String> ret_val = new ArrayList<String>();
		String sql_st = "SELECT id, cname FROM classes WHERE id>0 ORDER BY cname";
		getAuxQueryResults(sql_st);
		String res = "";
		if (sql_success_aux) {
			try {
				int m=0;
				while (rs_aux.next()) {
					res = rs_aux.getString(1) + "," + rs_aux.getString(2);
					//System.out.println("Class" + (m++) + " = " + res);
					ret_val.add(res);
				}
				rs_aux.close();
				last_stmt_aux.close();
				return ret_val;
			} catch (Exception e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Wrong Inputs", "Please Check", JOptionPane.WARNING_MESSAGE);
				return null;
			}
		}
		return ret_val;
	}
	
	public static ArrayList<String> getParents() {
		ArrayList<String> ret_val = new ArrayList<String>();
		String sql_st = "SELECT u1.id AS ChildId, CONCAT(u1.surname, \" \", u1.firstname) as ChildName, u2.id AS ParenId, CONCAT(u2.surname, \" \", u2.firstname) AS ParentName from users AS u1 INNER JOIN users as u2 on u1.parent_id = u2.id";
		getAuxQueryResults(sql_st);
		String res = "";
		if (sql_success_aux) {
			try {
				int m=0;
				while (rs_aux.next()) {
					res = rs_aux.getString(1) + "," + rs_aux.getString(2)+"," + rs_aux.getString(3)+"," + rs_aux.getString(4);
					System.out.println("Parent" + (m++) + " = " + res);
					ret_val.add(res);
				}
				rs_aux.close();
				last_stmt_aux.close();
				return ret_val;
			} catch (Exception e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Wrong Inputs", "Please Check", JOptionPane.WARNING_MESSAGE);
				return null;
			}
		}
		return ret_val;
	}

	public HashMap<Integer, String> GetRoles(){
		HashMap<Integer, String> ret_val = new HashMap<Integer, String>();
		String sql_st = "SELECT id, description FROM roles order by id";
		getAuxQueryResults(sql_st);
		if (sql_success_aux) {
			try {
				while (rs_aux.next())
					ret_val.put(rs_aux.getInt(1), rs_aux.getString(2));
				rs_aux.close();
				last_stmt_aux.close();
				return ret_val;
			} catch (Exception e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Wrong Inputs", "Please Check", JOptionPane.WARNING_MESSAGE);
				return null;
			}
		}
		return ret_val;
	}
	
	
	public String RowsToString(String tbl, String IdRowName, int  rows_id) {
		String sql_st = "SELECT * FROM " + tbl + " WHERE " + IdRowName + " =  " + rows_id;
		getAuxQueryResults(sql_st);
		String res = "";
		if (sql_success_aux) {
			try {
				while (rs_aux.next())
					res += rs_aux.getString(2) + ",";
				rs_aux.close();
				last_stmt_aux.close();
				res = res.substring(0,res.length()-1);
			} catch (Exception e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Wrong Inputs", "Please Check", JOptionPane.WARNING_MESSAGE);
			}
		}
		return res;	
	}
	
	public static int last_inserted_id(String tbl) {
		int res=-1;
		try {
			String sql_st = "SELECT max(id) FROM " + tbl;
			Statement stmt = db_connection.createStatement();
			ResultSet rset =  stmt.executeQuery(sql_st);
			if (rset.next()) res = rset.getInt(1);
			rset.close();
			stmt.close(); 
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		};
		return res;	
	}
	
	public static int getRecordCount(ResultSet rs) {
		int size = 0;
		try {
			while (rs.next())
				if ((rs.getString("REMARKS")!=null)&& rs.getString("REMARKS").length()>0) size++;
		    rs.beforeFirst();
		}
		catch(Exception ex) {
		    return 0;
		}
		return size;
	}
	
	public static ArrayList<ArrayList<String>> GetForeignKeyReferences(String column_name, String tbl) {
		ArrayList<ArrayList<String>> ret_val=null;
		ArrayList<String> row;
		String sql_str;
		//System.out.println("------>" + tbl);
		sql_str = "select fks.REFERENCED_TABLE_NAME ";
		sql_str += "from information_schema.referential_constraints fks ";
		sql_str += "join information_schema.key_column_usage kcu ";
		sql_str += "on fks.constraint_schema = kcu.table_schema ";
		sql_str += "and fks.table_name = kcu.table_name ";
		sql_str += "and fks.constraint_name = kcu.constraint_name ";
		sql_str += "and kcu.table_name='" + tbl + "' and kcu.column_name='" + column_name + "'";
		//System.out.println("sqlfkeys\n" + sql_str);
		try {
			getAux2QueryResults(sql_str);
			if ((sql_success_aux2) && rs_aux2.next()) {
				ret_val = new ArrayList<ArrayList<String>>();
				String ref_table = rs_aux2.getString(1);
				rs_aux2.close();
				//System.out.print("===:>" + ref_table);
				getAux2QueryResults("Select * from " + ref_table);
				rs_aux2.getMetaData().getColumnCount();
				int columns = rs_aux2.getMetaData().getColumnCount();
				while (rs_aux2.next()) {
					row = new ArrayList<String>();
					for (int i = 1; i <= columns; i++ )
						row.add(rs_aux2.getString(i));
					ret_val.add(row);
				}
			}
			return ret_val;
		} catch(SQLException se){
	          //Handle errors for JDBC
	          se.printStackTrace();
	          return null;
	  }
	}
	public static void execute(String sql_query) throws SQLException {
        Statement st=null;
        st = db_connection.createStatement();
		st.execute(sql_query);
		st.close();
	}
	
	public static void execute(String sql_query1, String sql_query2) throws SQLException {
        Statement st=null;
        st = db_connection.createStatement();
		st.execute(sql_query1);
		st.execute(sql_query2);
		st.close();
	}
	
	public static StringPair getMsgRecipients(int msg_id) {
		StringPair ret_val = null;
		try {
			String sql_st = "SELECT CONCAT(u.surname, ' ', u.firstname), to_or_cc FROM msgs_details as md INNER JOIN users as u ON u.id = md.to_user_id WHERE msg_id =" + msg_id;
			Statement stmt = db_connection.createStatement();
			ResultSet rset =  stmt.executeQuery(sql_st);
			String to_list = "";
			String cc_list = "";
			while (rset.next())
				if (rset.getInt("to_or_cc")==1) to_list += "," + rset.getString(1);
				else cc_list += "," + rset.getString(1);
			
			rset.close();
			stmt.close(); 
			if (cc_list.length()>1)	ret_val = new StringPair(to_list.substring(1),cc_list.substring(1));
			else ret_val = new StringPair(to_list.substring(1),cc_list);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		};
		return ret_val;
	}
	
	public static int getAbsences(int pupil_id) {
		int res=0;
		try {
			String sql_st = "SELECT sum(tcount) FROM absences WHERE pupil_id = " + pupil_id;
			Statement stmt = db_connection.createStatement();
			ResultSet rset =  stmt.executeQuery(sql_st);
			if (rset.next()) res = rset.getInt(1);
			rset.close();
			stmt.close(); 
		} catch (SQLException e1) {
			e1.printStackTrace();
		};
		return res;	
	}
}