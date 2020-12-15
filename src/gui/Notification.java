package gui;

import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Notification extends JFrame {

	private JLabel label;

	Notification(String error, String title) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(400, 200, 600, 200);
		setTitle(title);
		getContentPane().setLayout(null);
		setResizable(false);
		label = new JLabel(error);
		label.setBounds(49, 21, 500, 121);
        Font font = new Font("Serif", Font.PLAIN, 24);
        label.setFont(font);
		getContentPane().add(label);
	}
}
