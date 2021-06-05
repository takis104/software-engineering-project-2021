package schoolink;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Cval {
	static int button_size = 128;
	static int button_dx = 20;
	static int button_dy = 20;
	static String bg_image = "/images/main_bg.png";
	static int ScreenWidth = 700;
	static int ScreenHeight = 500;
	static int TitleFontSize = 14;
	
	static int reply_to;

	static int OPEN_EDITOR=0;
	static int OPEN_MULTIROW=1;
	static int OPEN_EDIT_ROW=2;
	
	static String DROPBOX_ACCESS_TOKEN = "-wYkyO_4q_cAAAAAAAAAAVArvaVQtMxLXKluuDTsi0Mj2NAJnQ38RI_gxg99-3_H";
	static String PROJECT_NAME = "schoolink";
	static String EMPTY_HTML = "<html><body></body></html>";
	public static String LastSelectedImage;
	
	public static Stack<MultirowForm> multirow_instances_stack;
	
	public static Stack<JTable> jtbl_stack;
	public static Stack<Integer> id_from_parent;
	
	static JButton AddButton(JFrame screen, int row, int col, String img_res, String tooltip) {
		Dimension btn_dim = new Dimension(button_size, button_size);
		JButton btn = new JButton((Icon) new ImageIcon(Cval.class.getResource(img_res)));
		btn.setSize(btn_dim); btn.setToolTipText(tooltip);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = col;  gbc.gridy = row;  gbc.ipady=button_dx; gbc.ipadx=button_dy;
		screen.add(btn, gbc);
		return btn;
	}
	
	public static ImageIcon PickAPhotoFile() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG Images, png Images", "jpg", "png");
		chooser.setFileFilter(filter);
		while (true) {
			int returnVal = chooser.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("You chose to open this file: " +  chooser.getSelectedFile().getName());
				BufferedImage img = null;
				try {
					LastSelectedImage = chooser.getSelectedFile().getAbsolutePath();//   getName();
				    img = ImageIO.read(new File(LastSelectedImage));
				    if (img.getHeight()<=256 && img.getWidth()<=256) return(new ImageIcon(img));
				} 
				catch (IOException e) {
				    e.printStackTrace();
				}
			} else return null;
		}
	}
	
	public static String sayHello() {
		Calendar c = Calendar.getInstance();
		int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
		String greeting;

		if(timeOfDay >= 0 && timeOfDay < 12) greeting="Καλημέρα ";
		else if(timeOfDay >= 12 && timeOfDay < 16) greeting="Καλό απόγευμα ";
		else greeting= "Καλησπέρα ";
		return greeting + db_interface.user_firstname;
	}
}
