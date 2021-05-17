package schoolink;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JTextField;
import javax.swing.SwingWorker;

import com.dropbox.core.DbxException;


import java.awt.Color;
import java.awt.Cursor;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class LoginPage extends JFrame {
	private static JTextField username;
	private static JPasswordField password;
	private static JFrame screen;
	//private JournalistWindow journalist;
	private static db_interface db;
	private boolean see_btn_state=false;
	
	public LoginPage() {	
		
		screen = new JFrame("");
		screen.setTitle("");
		screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		screen.getContentPane().setBackground(new Color(0, 102, 153));
		screen.getContentPane().setLayout(null);
		
		screen.setSize(400, 300); 
		ImageIcon bg = new ImageIcon(getClass().getResource("/images/main_bg.png"));
		Image bg_img = bg.getImage().getScaledInstance(screen.getWidth(), screen.getHeight(), Image.SCALE_DEFAULT);
		screen.setContentPane(new ImagePanel(bg_img));
		
		see_btn_state=false;
		
		JLabel img_lbl = new JLabel("");
		ImageIcon logo_icon = new ImageIcon(getClass().getResource("/images/logo1.png"));
		ImageIcon see_icon = new ImageIcon(getClass().getResource("/images/eye.png"));
		ImageIcon no_see_icon = new ImageIcon(getClass().getResource("/images/eye_c.png"));
	    img_lbl.setIcon(logo_icon);

		img_lbl.setBounds(10, 11, 387, 58);
		screen.getContentPane().add(img_lbl);
		
		JLabel lblUsername = new JLabel("Password :");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblUsername.setForeground(Color.CYAN);
		lblUsername.setBounds(20, 105, 111, 44);
		screen.getContentPane().add(lblUsername);
		 
		JLabel lblUsername_1 = new JLabel("Username :");
		lblUsername_1.setForeground(Color.CYAN);
		lblUsername_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblUsername_1.setBounds(20, 66, 111, 44);
		screen.getContentPane().add(lblUsername_1);
		
		JLabel gifLabel = new JLabel("");
		gifLabel.setIcon(new ImageIcon(LoginPage.class.getResource("/images/loading.gif")));
		gifLabel.setBounds(91, 66, 213, 159);
		gifLabel.setVisible(false);
		screen.getContentPane().add(gifLabel);
		
		JButton btnLogin = new JButton("Login");
	    btnLogin.addActionListener(new ActionListener() {
	        //@SuppressWarnings("deprecation")
	        public void actionPerformed(ActionEvent arg0) {
	        	screen.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	        	try {
	        		Connect2Db();
	        	} catch (Exception e) {
	        		e.printStackTrace();
	        		JOptionPane.showMessageDialog(null, "Unable to connect a valid database... ","Error",JOptionPane.ERROR_MESSAGE);
	        		System.exit(ERROR);
	        	}
	       	}
	    });
	    btnLogin.setBounds(164, 184, 89, 23);
	    screen.getContentPane().add(btnLogin);
		
		username = new JTextField();
		username.setFont(new Font("Tahoma", Font.PLAIN, 12));
		username.setBounds(119, 80, 150, 23);
		screen.getContentPane().add(username);
		username.setColumns(10);
		
		password= new JPasswordField();
		password.setBounds(119, 119, 150, 23);
		AbstractAction action2 = new AbstractAction()	{
		    @Override
		    public void actionPerformed(ActionEvent e)  {
		    	btnLogin.doClick();
		    }
		};
		password.addActionListener( action2 );
		screen.getContentPane().add(password);

		AbstractAction action1 = new AbstractAction()	{
		    @Override
		    public void actionPerformed(ActionEvent e)  {
		    	password.requestFocus();
		    }
		};
		username.addActionListener(action1);
		
		
		JButton btnreveal_password = new JButton();
		btnreveal_password.setIcon(see_icon);
		btnreveal_password.addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent arg0) {
		        	if (see_btn_state==false) {
		        		btnreveal_password.setIcon(no_see_icon);
		        		password.setEchoChar((char)0);
		        		see_btn_state = true;
		        	} else {
		        		btnreveal_password.setIcon(see_icon);
		        		see_btn_state = false;
		        		password.setEchoChar('\u2022');
		        	}
		        
		        }
		 });
		btnreveal_password.setBounds(279, 118, 25, 25);
		screen.getContentPane().add(btnreveal_password);

		screen.setLocationRelativeTo(null);
		screen.setVisible(true);
		
		try {
			dropbox_interface i=new dropbox_interface();
		} catch (DbxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
    private static void Connect2Db() throws InterruptedException, ExecutionException, TimeoutException  { 
    	SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
    		  @Override
    		  protected Boolean doInBackground() throws Exception {
    		    // Background work
    			  db = new db_interface();
    			  if (!db_interface.status) this.cancel(true);
    			  new dropbox_interface();
    		    // Value transmitted to done()
    		    return true;
    		  }

    		  @SuppressWarnings("deprecation")
			@Override
    		  protected void done() {
    			  screen.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    	          if (db==null)  {
    	        	  JOptionPane.showMessageDialog(null, "Wait for a db connection... ","Error",JOptionPane.ERROR_MESSAGE);
    	        	  return;
    	          }
    	          
    	          if (username.getText().length()==0) {
    	        	  JOptionPane.showMessageDialog(null, "Give a valid username ","Error",JOptionPane.ERROR_MESSAGE);
    	        	  return;
    	          }
    	          try { 
    	        	  db_interface.getLoginData(username.getText());
    	        	  if (db_interface.rs.next())
    	        		  if (password.getText().equals(db_interface.rs.getString("password"))) {
    	        		  		JOptionPane.showMessageDialog(null, "Login Sucessful...");
    	        		  		db_interface.user_id = db_interface.rs.getInt("id");
    	        		  		db_interface.user_email = db_interface.rs.getString("email");
    	        		  		db_interface.user_firstname = db_interface.rs.getString("firstname");
    	        		  		db_interface.user_surname = db_interface.rs.getString("surname");
    	        		  		db_interface.user_auth_level = db_interface.rs.getInt("role_id");
    	        		  		db_interface.getSchoolParams();    	        			    
    	        			   
    	        		  		switch (db_interface.user_auth_level) {
    		        		  		case 1: //Director
    		        		  			new Director();
    		        		  			break;
    		        		  		case 2: //Secretary
    		        		  			new Secretary();
    		        		  			break;
    		        		  		case 3: //Teacher
    		        		  			new Teacher();
    		        		  			break;
    		        		  		case 4: //Pupil
    		        		  			new Pupil();
    		        		  			break;
    		        		  		case 5: //Parent
    		        		  			new Parent();
    		        		  			break;    	        		  		
    		        		  		}
    	        		  		db_interface.rs.close();
    	        		  		db_interface.last_stmt.close();
    	        		  		screen.dispose();
    	        		  } else  {
    	        			  JOptionPane.showMessageDialog(null, "Incorrect username or password...Try again", "Please Check", JOptionPane.WARNING_MESSAGE);
    	        			  return;
    	        		  }
    	        	  else {
	        			  JOptionPane.showMessageDialog(null, "Unknown user", "Please Check", JOptionPane.WARNING_MESSAGE);
	        			  return;
    	        	  }
    	          } catch(Exception e){
    			       //Handle errors for Class.forName
    			       e.printStackTrace();
    			       return;
    			  }
    		   
    		  } 
    		};
    		worker.execute();
    		worker.get(30, TimeUnit.SECONDS);
    }

	  public static void main(String[] args) {
		    try {
		    	//NativeInterface.open();
		    	new LoginPage();
		    	//Connect2Db(); //db = new db_interface();
		    } catch (Exception e) {
		    	e.printStackTrace();
		    }
		  }
	  
 
}
