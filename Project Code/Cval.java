package schoolink;

import java.awt.Dimension;
import java.awt.GridBagConstraints;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Cval {
	static int button_size = 128;
	static int button_dx = 20;
	static int button_dy = 20;
	static String bg_image = "/images/main_bg.png";
	static int ScreenWidth = 700;
	static int ScreenHeight = 500;
	static int TitleFontSize = 14;
	
	static JButton AddButton(JFrame screen, int row, int col, String img_res, String tooltip) {
		Dimension btn_dim = new Dimension(button_size, button_size);
		JButton btn = new JButton((Icon) new ImageIcon(Cval.class.getResource(img_res)));
		btn.setSize(btn_dim); btn.setToolTipText(tooltip);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = col;  gbc.gridy = row;  gbc.ipady=button_dx; gbc.ipadx=button_dy;
		screen.add(btn, gbc);
		return btn;
	}
}
