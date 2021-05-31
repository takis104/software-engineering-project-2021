package schoolink;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AboutAppDialog {
	private static JDialog about;
	
	AboutAppDialog() {
		JFrame newFrame = new JFrame();
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JPanel panel2 = new JPanel();
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
		about = new JDialog(newFrame, "Σχετικά με την εφαρμογή", true);
		JButton exitBtn = new JButton ("Κλείσιμο"); 
		exitBtn.addActionListener (new ActionListener() {  
            public void actionPerformed( ActionEvent e )  
            {  
            	AboutAppDialog.about.setVisible(false);  
            }
        });
		
		//logo image label
		JLabel logo = new JLabel("");
		logo.setHorizontalAlignment(SwingConstants.CENTER);
		ImageIcon logoIcon = new ImageIcon(getClass().getResource("/images/logo256px.jpg"));
		logo.setIcon(logoIcon);
		logo.setBounds(20,20,256,256);
		panel.add(logo);
		
		//add components to panel
		JLabel text1 = new JLabel("Σχετικά με την εφαρμογή");
		text1.setFont(new Font("Tahoma", Font.BOLD, 20));
		panel.add(text1);
		
		JLabel text2 = new JLabel("Σκοπός της εφαρμογής είναι η μηχανογράφηση της λειτουργίας ενός σχολικού περιβάλλοντος.");
		panel2.add(text2);
		panel2.add( new JLabel("\n"));
		
		JLabel text3 = new JLabel("Η εφαρμογή αναπτύχθηκε στα πλαίσια του μαθήματος της Τεχνολογίας Λογισμικού.");
		panel2.add(text3);
		JLabel text4 = new JLabel("Τμήμα Μηχανικών Η/Υ και Πληροφορικής, Πανεπιστήμιο Πατρών.");
		panel2.add(text4);
		panel2.add( new JLabel("\n"));
		
		JLabel text5 = new JLabel("Μέλη Ομάδας: ");
		panel2.add(text5);
		panel2.add( new JLabel("ΧΡΗΣΤΟΣ-ΠΑΝΑΓΙΩΤΗΣ ΜΠΑΛΑΤΣΟΥΡΑΣ, (Α.Μ. : 1054335)"));
		panel2.add( new JLabel("ΕΙΡΗΝΗ ΡΟΥΧΩΤΑ, (Α.Μ. : 1059654)"));
		panel2.add( new JLabel("ΧΡΙΣΤΙΝΑ ΠΑΠΑΣΤΑΥΡΟΥ, (Α.Μ. : 1059621)"));
		panel2.add( new JLabel("ΓΙΑΝΝΗΣ ΧΑΤΖΗΜΙΧΑΛΗΣ, (Α.Μ. : 1059613)"));
		panel2.add( new JLabel("ΓΙΩΡΓΟΣ ΜΗΤΣΕΑΣ, (Α.Μ. : 1002345)"));
		panel2.add( new JLabel("\n"));
		
		panel2.add(exitBtn);
		
		
		about.add(panel);
		about.add(panel2);
		about.setSize(800, 600);
		about.setLayout(new FlowLayout());
		about.setVisible(true);
	}
}
