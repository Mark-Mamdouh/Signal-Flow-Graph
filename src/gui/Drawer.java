package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.util.LinkedList;
import solver.SignalFlowGraphs;

@SuppressWarnings("serial")
public class Drawer extends JFrame {

	private LinkedList<String> forward, loops, deltas;
	private Double delta, deltaNodes;
	private JPanel drawPanel1, drawPanel2, drawPanel3, drawPanel4, drawPanel5;
	private int width, height;
	private JTextField firstNodeField, gainField, secondNodeField, textField, textField_1;
	private Color color = null;

	public Drawer() {
		
		setTitle("Signal Flow Graph");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		Toolkit toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int) screenSize.getWidth() - 120;
		height = (int) screenSize.getHeight() - 120;
		Data.width = width;
		Data.height = height;
		setBounds(0, 0, 1246, 718);
		getContentPane().setLayout(null);

		JMenuBar menuBar = new JMenuBar();
		ImageIcon iconNew = new ImageIcon("new.png");
		ImageIcon iconOpen = new ImageIcon("open.png");
		ImageIcon iconSave = new ImageIcon("save.png");
		ImageIcon iconExit = new ImageIcon("exit.png");
		JMenu file = new JMenu("File");
		JMenuItem itemNew = new JMenuItem("New", iconNew);
		JMenuItem itemSave = new JMenuItem("Save", iconSave);
		JMenuItem itemLoad = new JMenuItem("Load", iconOpen);
		JMenuItem itemExit = new JMenuItem("Exit", iconExit);
		JMenu prefrences = new JMenu("Prefrences");
		JMenu changeColor = new JMenu("Change Theme");
		JMenuItem color1= new JMenuItem("Black(Default)");
		JMenuItem color2 = new JMenuItem("Blue");
		JMenuItem color3 = new JMenuItem("Cyan");
		JMenuItem color4 = new JMenuItem("Gray");
		JMenuItem color5 = new JMenuItem("Magenta");
		changeColor.add(color1);
		changeColor.add(color2);
		changeColor.add(color3);
		changeColor.add(color4);
		changeColor.add(color5);
		file.add(itemNew);
		file.add(itemSave);
		file.add(itemLoad);
		file.add(itemExit);
		prefrences.add(changeColor);
		setJMenuBar(menuBar);
		menuBar.add(file);
		menuBar.add(prefrences);
		
		drawPanel1 = new NodesDrawerBlack();
		drawPanel1.setBounds(0, 0, 1246, 487);
		drawPanel1.setBackground(Color.WHITE);
		getContentPane().add(drawPanel1);
		
		drawPanel2 = new NodesDrawerBlue();
		drawPanel2.setBounds(0, 0, 1246, 487);
		drawPanel2.setBackground(Color.GRAY);
		
		drawPanel3 = new NodesDrawerCyan();
		drawPanel3.setBounds(0, 0, 1246, 487);
		drawPanel3.setBackground(Color.PINK);
		
		drawPanel4 = new NodesDrawerGray();
		drawPanel4.setBounds(0, 0, 1246, 487);
		drawPanel4.setBackground(Color.WHITE);
		
		drawPanel5 = new NodesDrawerMagenta();
		drawPanel5.setBounds(0, 0, 1246, 487);
		drawPanel5.setBackground(Color.YELLOW);

		firstNodeField = new JTextField();
		firstNodeField.setBounds(38, 517, 78, 45);
		getContentPane().add(firstNodeField);

		secondNodeField = new JTextField();
		secondNodeField.setBounds(480, 517, 78, 45);
		getContentPane().add(secondNodeField);
		
		gainField = new JTextField();
		gainField.setBounds(255, 517, 78, 45);
		getContentPane().add(gainField);

		JLabel lblFromNode = new JLabel("From Node");
		lblFromNode.setFont(new Font("Dialog", Font.ITALIC, 16));
		lblFromNode.setBounds(38, 481, 89, 45);
		getContentPane().add(lblFromNode);

		JLabel lblToNode = new JLabel("To Node");
		lblToNode.setFont(new Font("Dialog", Font.ITALIC, 16));
		lblToNode.setBounds(490, 481, 68, 44);
		getContentPane().add(lblToNode);


		JLabel lblGain = new JLabel("Gain");
		lblGain.setFont(new Font("Dialog", Font.ITALIC, 16));
		lblGain.setBounds(271, 484, 43, 38);
		getContentPane().add(lblGain);

		JButton btnClearAll = new JButton("Clear All");
		btnClearAll.setBounds(859, 549, 117, 47);
		getContentPane().add(btnClearAll);

		JButton btnEnter = new JButton("Enter");
		btnEnter.setBounds(671, 514, 113, 50);
		getContentPane().add(btnEnter);

		JButton btnSolve = new JButton("Solve");
		btnSolve.setBounds(1036, 526, 153, 93);
		getContentPane().add(btnSolve);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setBounds(671, 600, 117, 50);
		getContentPane().add(btnClear);
		
		JLabel label = new JLabel("From Node");
		label.setFont(new Font("Dialog", Font.ITALIC, 16));
		label.setBounds(38, 561, 89, 45);
		getContentPane().add(label);
		
		textField = new JTextField();
		textField.setBounds(38, 603, 78, 45);
		getContentPane().add(textField);
		
		JLabel label_1 = new JLabel("To Node");
		label_1.setFont(new Font("Dialog", Font.ITALIC, 16));
		label_1.setBounds(490, 561, 68, 44);
		getContentPane().add(label_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(480, 603, 78, 45);
		getContentPane().add(textField_1);

		btnEnter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				int from = 0;
				try {
					from = Integer.parseInt(firstNodeField.getText());
				} catch (NumberFormatException e) {
					Notification error = new Notification("Input Must be Integer", "Error Message!");
					error.setVisible(true);
				}
				int to = 0;
				try {
					to = Integer.parseInt(secondNodeField.getText());
				} catch (NumberFormatException e) {
					Notification error = new Notification("Input Must be Integer", "Error Message!");
					error.setVisible(true);
				}
				if(from >= Data.numOfNodes || to >= Data.numOfNodes || from < 0 || to < 0){
					Notification error = new Notification("Input Must be in range from 0 to " + (Data.numOfNodes - 1), "Error Message!");
					error.setVisible(true);
				}else{
					Data.gains[from][to] = Integer.parseInt(gainField.getText());
					firstNodeField.setText("");
					secondNodeField.setText("");
					gainField.setText("");
					if(color == null || color == Color.BLACK){
						getContentPane().remove(drawPanel2);
						getContentPane().remove(drawPanel3);
						getContentPane().remove(drawPanel4);
						getContentPane().remove(drawPanel5);
						getContentPane().add(drawPanel1);
						drawPanel1.repaint();
					}
					else if(color == Color.BLUE){
						getContentPane().remove(drawPanel1);
						getContentPane().remove(drawPanel3);
						getContentPane().remove(drawPanel4);
						getContentPane().remove(drawPanel5);
						getContentPane().add(drawPanel2);
						drawPanel2.repaint();
					}
					else if(color == Color.CYAN){
						getContentPane().remove(drawPanel2);
						getContentPane().remove(drawPanel1);
						getContentPane().remove(drawPanel4);
						getContentPane().remove(drawPanel5);
						getContentPane().add(drawPanel3);
						drawPanel3.repaint();
					}
					else if(color == Color.GRAY){
						getContentPane().remove(drawPanel2);
						getContentPane().remove(drawPanel3);
						getContentPane().remove(drawPanel1);
						getContentPane().remove(drawPanel5);
						getContentPane().add(drawPanel4);
						drawPanel4.repaint();
					}
					else if(color == Color.MAGENTA){
						getContentPane().remove(drawPanel2);
						getContentPane().remove(drawPanel3);
						getContentPane().remove(drawPanel4);
						getContentPane().remove(drawPanel1);
						getContentPane().add(drawPanel5);
						drawPanel5.repaint();
					}
				}
			}
		});
		
		btnClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				int from = 0;
				try {
					from = Integer.parseInt(textField.getText());
				} catch (NumberFormatException e) {
					Notification error = new Notification("Input Must be Integer", "Error Message!");
					error.setVisible(true);
				}
				int to = 0;
				try {
					to = Integer.parseInt(textField_1.getText());
				} catch (NumberFormatException e) {
					Notification error = new Notification("Input Must be Integer", "Error Message!");
					error.setVisible(true);
				}
				if(from >= Data.numOfNodes || to >= Data.numOfNodes || from < 0 || to < 0){
					Notification error = new Notification("Input Must be in range from 0 to " + (Data.numOfNodes - 1), "Error Message!");
					error.setVisible(true);
				}else{
					Data.gains[from][to] = 0;
					textField.setText("");
					textField_1.setText("");
					if(color == null || color == Color.BLACK){
						getContentPane().remove(drawPanel2);
						getContentPane().remove(drawPanel3);
						getContentPane().remove(drawPanel4);
						getContentPane().remove(drawPanel5);
						getContentPane().add(drawPanel1);
						drawPanel1.repaint();
					}
					else if(color == Color.BLUE){
						getContentPane().remove(drawPanel1);
						getContentPane().remove(drawPanel3);
						getContentPane().remove(drawPanel4);
						getContentPane().remove(drawPanel5);
						getContentPane().add(drawPanel2);
						drawPanel2.repaint();
					}
					else if(color == Color.CYAN){
						getContentPane().remove(drawPanel2);
						getContentPane().remove(drawPanel1);
						getContentPane().remove(drawPanel4);
						getContentPane().remove(drawPanel5);
						getContentPane().add(drawPanel3);
						drawPanel3.repaint();
					}
					else if(color == Color.GRAY){
						getContentPane().remove(drawPanel2);
						getContentPane().remove(drawPanel3);
						getContentPane().remove(drawPanel1);
						getContentPane().remove(drawPanel5);
						getContentPane().add(drawPanel4);
						drawPanel4.repaint();
					}
					else if(color == Color.MAGENTA){
						getContentPane().remove(drawPanel2);
						getContentPane().remove(drawPanel3);
						getContentPane().remove(drawPanel4);
						getContentPane().remove(drawPanel1);
						getContentPane().add(drawPanel5);
						drawPanel5.repaint();
					}
				}
			}
		});
		
		btnSolve.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				SignalFlowGraphs sfg = new SignalFlowGraphs();
				System.out.println(Data.numOfNodes);
				for(int i=0 ; i< Data.numOfNodes ; i++){
					sfg.createNode();
					for(int j=0 ; j< Data.numOfNodes ; j++){
						if(Data.gains[i][j] != 0){
							sfg.setNext(j);
							sfg.setValue(j, Data.gains[i][j]);
						}
					}
				}
				LinkedList<LinkedList<Integer>> forward2 = sfg.forwardPath();
				forward = string(forward2);
				
				LinkedList<LinkedList<Integer>> loops2 = sfg.loops();
				loops = string(loops2);
				
				deltas = sfg.getDeltes();
				
				sfg.getLoops();
				sfg.getForwardPaths();
				delta = sfg.getDelta();
				deltaNodes = sfg.deltaForEachForwardPath();
				sfg.getDelta();
				Solver t = new Solver(forward , loops , delta , deltaNodes, deltas);
				t.setVisible(true);
			}
		});
		
		btnClearAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				SignalFlowGraphs sfg = new SignalFlowGraphs();
				sfg.clear();
				Data.gains = new double[Data.numOfNodes][Data.numOfNodes];
				if(color == null || color == Color.BLACK){
					getContentPane().remove(drawPanel2);
					getContentPane().remove(drawPanel3);
					getContentPane().remove(drawPanel4);
					getContentPane().remove(drawPanel5);
					getContentPane().add(drawPanel1);
					drawPanel1.repaint();
				}
				else if(color == Color.BLUE){
					getContentPane().remove(drawPanel1);
					getContentPane().remove(drawPanel3);
					getContentPane().remove(drawPanel4);
					getContentPane().remove(drawPanel5);
					getContentPane().add(drawPanel2);
					drawPanel2.repaint();
				}
				else if(color == Color.CYAN){
					getContentPane().remove(drawPanel2);
					getContentPane().remove(drawPanel1);
					getContentPane().remove(drawPanel4);
					getContentPane().remove(drawPanel5);
					getContentPane().add(drawPanel3);
					drawPanel3.repaint();
				}
				else if(color == Color.GRAY){
					getContentPane().remove(drawPanel2);
					getContentPane().remove(drawPanel3);
					getContentPane().remove(drawPanel1);
					getContentPane().remove(drawPanel5);
					getContentPane().add(drawPanel4);
					drawPanel4.repaint();
				}
				else if(color == Color.MAGENTA){
					getContentPane().remove(drawPanel2);
					getContentPane().remove(drawPanel3);
					getContentPane().remove(drawPanel4);
					getContentPane().remove(drawPanel1);
					getContentPane().add(drawPanel5);
					drawPanel5.repaint();
				}
			}
		});
		
		itemNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				NumOfNodes gui = new NumOfNodes();
				gui.setVisible(true);
				dispose();
			}
		});
		
		itemSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
		        String fileName = "temp.txt";
		        try {
		            FileWriter fileWriter = new FileWriter(fileName);
		            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		            bufferedWriter.write(Data.numOfNodes + "\n");
		            if(color == null || color == Color.BLACK){
		            	bufferedWriter.write("BLACK\n");
		            }
		            else if(color == Color.BLUE){
		            	bufferedWriter.write("BLUE\n");
		            }
		            else if(color == Color.CYAN){
		            	bufferedWriter.write("CYAN\n");
		            }
		            else if(color == Color.GRAY){
		            	bufferedWriter.write("GRAY\n");
		            }
		            else if(color == Color.MAGENTA){
		            	bufferedWriter.write("MAGENTA\n");
		            }
		            for(int i=0 ; i<Data.numOfNodes ; i++){
		            	for(int j=0 ; j<Data.numOfNodes ; j++){
		            		 bufferedWriter.write(Data.gains[i][j] + " ");
		            	}
		            	 bufferedWriter.write("\n");
		            }
		            bufferedWriter.close();
		            Notification save = new Notification("Saved", "Notification");
					save.setVisible(true);
		        }
				catch (Exception ex) {
					Notification error = new Notification("An error occured while saving!", "Error Message!");
					error.setVisible(true);
				}
			}
		});
		
		itemLoad.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String[] gain;
		        String fileName = "temp.txt";
		        String line = null;
		        try {
		            FileReader fileReader = new FileReader(fileName);
		            BufferedReader bufferedReader = new BufferedReader(fileReader);
		            line = bufferedReader.readLine();
		            int nodesNum = Integer.parseInt(line);
		            Data.numOfNodes = nodesNum;
		            gain = new String[nodesNum];
		            line = bufferedReader.readLine();
		            String colorTemp = line;
		            for(int i=0 ; i<nodesNum ; i++){
		            	line = bufferedReader.readLine();
		            	gain[i] = line;
		            }
		            bufferedReader.close();
		            Data.gains = new double[nodesNum][nodesNum];
		            for(int i=0 ; i<nodesNum ; i++){
		            	String[] temp = gain[i].split(" ");
		            	for(int j=0 ; j<nodesNum ; j++){
		            		Data.gains[i][j] = Double.parseDouble(temp[j]);
						}
					}
					if (colorTemp.equals("BLACK")) {
						color = Color.BLACK;
						getContentPane().remove(drawPanel2);
						getContentPane().remove(drawPanel3);
						getContentPane().remove(drawPanel4);
						getContentPane().remove(drawPanel5);
						getContentPane().add(drawPanel1);
						drawPanel1.repaint();
					} else if (colorTemp.equals("BLUE")) {
						color = Color.BLUE;
						getContentPane().remove(drawPanel1);
						getContentPane().remove(drawPanel3);
						getContentPane().remove(drawPanel4);
						getContentPane().remove(drawPanel5);
						getContentPane().add(drawPanel2);
						drawPanel2.repaint();
					} else if (colorTemp.equals("CYAN")) {
						color = Color.CYAN;
						getContentPane().remove(drawPanel2);
						getContentPane().remove(drawPanel1);
						getContentPane().remove(drawPanel4);
						getContentPane().remove(drawPanel5);
						getContentPane().add(drawPanel3);
						drawPanel3.repaint();
					} else if (colorTemp.equals("GRAY")) {
						color = Color.GRAY;
						getContentPane().remove(drawPanel2);
						getContentPane().remove(drawPanel3);
						getContentPane().remove(drawPanel1);
						getContentPane().remove(drawPanel5);
						getContentPane().add(drawPanel4);
						drawPanel4.repaint();
					} else if (colorTemp.equals("MAGENTA")) {
						color = Color.MAGENTA;
						getContentPane().remove(drawPanel2);
						getContentPane().remove(drawPanel3);
						getContentPane().remove(drawPanel4);
						getContentPane().remove(drawPanel1);
						getContentPane().add(drawPanel5);
						drawPanel5.repaint();
					}
		        }
		        catch(Exception e1){
		        	Notification error = new Notification("An error occured while loading from file!", "Error Message!");
					error.setVisible(true);     
		        }
			}
		});
		 
		itemExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		
		color1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				color = Color.BLACK;
				getContentPane().remove(drawPanel2);
				getContentPane().remove(drawPanel3);
				getContentPane().remove(drawPanel4);
				getContentPane().remove(drawPanel5);
				getContentPane().add(drawPanel1);
				drawPanel1.repaint();
			}
		});
		
		color2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				color = Color.BLUE;
				getContentPane().remove(drawPanel1);
				getContentPane().remove(drawPanel3);
				getContentPane().remove(drawPanel4);
				getContentPane().remove(drawPanel5);
				getContentPane().add(drawPanel2);
				drawPanel2.repaint();
			}
		});
		
		color3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				color = Color.CYAN;
				getContentPane().remove(drawPanel2);
				getContentPane().remove(drawPanel1);
				getContentPane().remove(drawPanel4);
				getContentPane().remove(drawPanel5);
				getContentPane().add(drawPanel3);
				drawPanel3.repaint();
			}
		});
		
		color4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				color = Color.GRAY;
				getContentPane().remove(drawPanel2);
				getContentPane().remove(drawPanel3);
				getContentPane().remove(drawPanel1);
				getContentPane().remove(drawPanel5);
				getContentPane().add(drawPanel4);
				drawPanel4.repaint();
			}
		});
		
		color5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				color = Color.MAGENTA;
				getContentPane().remove(drawPanel2);
				getContentPane().remove(drawPanel3);
				getContentPane().remove(drawPanel4);
				getContentPane().remove(drawPanel1);
				getContentPane().add(drawPanel5);
				drawPanel5.repaint();
			}
		});
	}
	
	public LinkedList<String> string(LinkedList<LinkedList<Integer>> test){
		LinkedList<String> temp = new LinkedList<String>();
		for(LinkedList<Integer> node : test){
			String s = "";
			for(Integer i : node){
				s += i;
			}
			temp.add(s);
		}
		return temp;
	}
}
