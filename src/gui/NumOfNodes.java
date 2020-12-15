package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class NumOfNodes extends JFrame {

	private JLabel l1;
	private JTextField inputField;
	private JButton enter;

	public NumOfNodes() {

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(400, 200, 600, 250);
		setTitle("Signal-Flow-Graph                         by Fady Ashraf & Mark Mamdouh");
		getContentPane().setLayout(null);
		setResizable(true);

		l1 = new JLabel("Enter number of nodes");
		l1.setBounds(46, 64, 300, 40);

		inputField = new JTextField();
		inputField.setBounds(347, 64, 78, 50);

		enter = new JButton("Enter");
		enter.setBounds(199, 142, 171, 50);
		enter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (checkValid(inputField.getText())) {
					int n = 1;
					n = Integer.parseInt(inputField.getText());
					if (n < 2) {
						Notification error = new Notification("Number of nodes must be > 0 ! please try again", "Error Message!");
						error.setVisible(true);
					}else{
						Data.numOfNodes = n;
						Data.gains = new double[n][n];
						Drawer view = new Drawer();
						view.setVisible(true);
						dispose();
					}
				} else {
					Notification error = new Notification("Invalid! please try again", "Error Message!");
					error.setVisible(true);
				}
			}
		});

		Font font = new Font("Serif", Font.ROMAN_BASELINE, 24);
		l1.setFont(font);
		inputField.setFont(font);
		enter.setFont(font);

		getContentPane().add(l1);
		getContentPane().add(inputField);
		getContentPane().add(enter);
	}

	public boolean checkValid(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}

