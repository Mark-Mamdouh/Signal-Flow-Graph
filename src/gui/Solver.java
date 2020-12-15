package gui;

import java.awt.Font;
import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import java.awt.Color;

@SuppressWarnings("serial")
public class Solver extends JFrame{

	private JLabel forwardLabel, loopsLabel, labelelta, labelDeltaNode, labelDeltas, labelGain, label1, label2, label3, label4, label5, label6;

	public Solver(LinkedList<String> forward ,LinkedList<String> loops , Double delta , Double deltaNode, LinkedList<String> deltas ){
		
		getContentPane().setBackground(UIManager.getColor("TabbedPane.shadow"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		if(loops.size() == 0){
			setBounds(400, 200, 500, 754);
		}else{
			setBounds(400, 200, loops.size() * 150, 754);
		}
		setTitle("Solution");
		getContentPane().setLayout(null);
		setResizable(true);

		forwardLabel = new JLabel("Forward paths :");
		forwardLabel.setForeground(UIManager.getColor("TabbedPane.selectHighlight"));
		forwardLabel.setBounds(50, 32, 164, 63);
        Font font = new Font("Serif", Font.ITALIC, 24);
        forwardLabel.setFont(font);
		getContentPane().add(forwardLabel);
		
	    label1 = new JLabel("");
		label1.setFont(new Font("Serif", Font.ITALIC, 24));
		label1.setBounds(224, 32, forward.size() * 80, 63);
		getContentPane().add(label1);
		
		loopsLabel = new JLabel("Loops :");
		loopsLabel.setForeground(UIManager.getColor("TabbedPane.selectHighlight"));
		loopsLabel.setFont(new Font("Serif", Font.ITALIC, 24));
		loopsLabel.setBounds(50, 139, 115, 63);
		getContentPane().add(loopsLabel);
		
		label2 = new JLabel("");
		label2.setFont(new Font("Serif", Font.ITALIC, 24));
		label2.setBounds(132, 139, loops.size() * 80, 63);
		getContentPane().add(label2);
		
		labelelta = new JLabel("Delta :");
		labelelta.setForeground(UIManager.getColor("TabbedPane.selectHighlight"));
		labelelta.setFont(new Font("Serif", Font.ITALIC, 24));
		labelelta.setBounds(50, 244, 115, 63);
		getContentPane().add(labelelta);
		
		label3 = new JLabel(delta.toString());
		label3.setFont(new Font("Serif", Font.ITALIC, 24));
		label3.setBounds(177, 244, 459, 63);
		getContentPane().add(label3);
		
		labelDeltaNode = new JLabel("DeltaNode :");
		labelDeltaNode.setForeground(Color.WHITE);
		labelDeltaNode.setFont(new Font("Serif", Font.ITALIC, 24));
		labelDeltaNode.setBounds(50, 348, 156, 63);
		getContentPane().add(labelDeltaNode);
		
		label4 = new JLabel(deltaNode.toString());
		label4.setFont(new Font("Serif", Font.ITALIC, 24));
		label4.setBounds(204, 348, 459, 63);
		getContentPane().add(label4);
		
		labelDeltas = new JLabel("Deltas :");
		labelDeltas.setForeground(Color.WHITE);
		labelDeltas.setFont(new Font("Serif", Font.ITALIC, 24));
		labelDeltas.setBounds(50, 455, 101, 63);
		getContentPane().add(labelDeltas);
		
		label5 = new JLabel("");
		label5.setFont(new Font("Serif", Font.ITALIC, 24));
		label5.setBounds(147, 455, 459, 63);
		getContentPane().add(label5);
		
		labelGain = new JLabel("Gain :");
		labelGain.setForeground(Color.WHITE);
		labelGain.setFont(new Font("Serif", Font.ITALIC, 24));
		labelGain.setBounds(50, 574, 101, 63);
		getContentPane().add(labelGain);
		
		Double gain = deltaNode/delta;
		label6 = new JLabel(gain.toString());
		label6.setFont(new Font("Serif", Font.ITALIC, 24));
		label6.setBounds(147, 574, 459, 63);
		getContentPane().add(label6);
		
		for(int i=0 ; i<forward.size() ; i++){
			if(i == forward.size() - 1){
				label1.setText("\n" + label1.getText() +forward.get(i));
			}else{
				label1.setText("\n" + label1.getText() +forward.get(i)+", ");
			}
			getContentPane().add(label1);
		}

		for(int i=0 ; i<loops.size() ; i++){
			if(i == loops.size() - 1){
				label2.setText("\n" + label2.getText() +loops.get(i));
			}else{
				label2.setText("\n" + label2.getText() +loops.get(i)+", ");
			}
			getContentPane().add(label2);
		}
		
		for(int i=0 ; i<deltas.size() ; i++){
			if(i == deltas.size() - 1){
				label5.setText("\n" + label5.getText() +deltas.get(i));
			}else{
				label5.setText("\n" + label5.getText() +deltas.get(i)+", ");
			}
			getContentPane().add(label5);
		}
		
        JScrollPane pane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        getContentPane().add(pane);
	}
}
