package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class NodesDrawerBlack extends JPanel {
	
	@Override
	public void paint(Graphics canvas) {
		
		Graphics2D g = (Graphics2D)canvas;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		super.paint(g);
		
		int numOfNodes = Data.numOfNodes;
		double[][] gains = Data.gains;
		int xDistance = Data.width / (numOfNodes + 1);
		int yDistance = (Data.height - 120) / 2;
		int nodeRadius = 20;

		int tanBaseUp = (int) (yDistance - nodeRadius);
		int tanBaseDown = (int) (yDistance + nodeRadius);
		float selfLoopC1 = yDistance - 4 * nodeRadius;
		float selfLoopC2 = yDistance + 4 * nodeRadius;
		Path2D.Double path = new Path2D.Double();
		Graphics2D g2 = (Graphics2D) g;
		
		//set font
		Font font = new Font("Serif", Font.ITALIC, 18);
		g.setFont(font);

		////////////////////////////////draw R(s), C(s)////////////////////////////
		g.setColor(Color.RED);
		g.fillOval(xDistance - nodeRadius, yDistance - nodeRadius, nodeRadius * 2, nodeRadius * 2);
		g.fillOval(xDistance * (numOfNodes) - nodeRadius, yDistance - nodeRadius, nodeRadius * 2, nodeRadius * 2);
		g.setColor(Color.black);
		g.drawString("R(s)", xDistance - nodeRadius, yDistance - 2 * nodeRadius);
		g.drawString("C(s)", xDistance * (numOfNodes) - nodeRadius, yDistance - 2 * nodeRadius);
		////////////////////////////////draw R(s), C(s)////////////////////////////
		
		////////////////////////////////draw other Nodes///////////////////////////
		g.setColor(Color.black);
		for (int i = 1; i < numOfNodes - 1; i++){
			g.fillOval(xDistance * (i + 1) - nodeRadius, yDistance - nodeRadius, nodeRadius * 2, nodeRadius * 2);
		}
		g.setColor(Color.white);
		for (int i = 0; i < numOfNodes; i++){
			g.drawString("" + (i), xDistance * (i + 1) - nodeRadius + 18, yDistance + 8);
		}
        ////////////////////////////////draw other Nodes///////////////////////////
		
		////////////////////////////////draw gains///////////////////////////////
		int x;
		for (int i = 0; i < numOfNodes; i++) {
			for (int j = 0; j < numOfNodes; j++) {
				if (gains[i][j] != 0) {
					// drawing self loops
					if (i == j) {
						// drawing arc
						g.setColor(Color.GREEN);
						path = new Path2D.Double();
						path.moveTo(xDistance * (i + 1), tanBaseUp);
						x = xDistance * (i + 1) - 3 * nodeRadius;
						path.curveTo(x, selfLoopC1, x, selfLoopC2, xDistance * (i + 1), tanBaseDown);
						g2.draw(path);

						x = x + nodeRadius - 5;
						// drawing arrow
						path = new Path2D.Double();
						path.moveTo(x + 12, yDistance - 10);
						path.lineTo(x, yDistance + 12);
						path.lineTo(x - 12, yDistance - 10);
						g2.fill(path);
						// drawing gain text
						g.setColor(Color.black);
						g.drawString(gains[i][j] + "", xDistance * (i + 1) - nodeRadius, yDistance - 2 * nodeRadius);

					} else if (j - i == 1) {
						// drawing arc
						g.setColor(Color.BLUE);
						g.drawLine((i + 1) * xDistance + nodeRadius, yDistance, (j + 1) * xDistance - nodeRadius, yDistance);
						// drawing arrow
						x = (j + i + 2) * xDistance / 2;
						path = new Path2D.Double();
						path.moveTo(x, yDistance - 12);
						path.lineTo(x, yDistance + 12);
						path.lineTo(x + 24, yDistance);
						g2.fill(path);
						// drawing gain text
						g.setColor(Color.black);
						g.drawString(gains[i][j] + "", x, yDistance - 20);

						// feedback
					} else if (i > j) {
						// drawing arc
						g.setColor(Color.green);
						path = new Path2D.Double();
						path.moveTo(xDistance * (i + 1), tanBaseDown);
						x = xDistance * (j + i + 2) / 2;
						path.quadTo(x, yDistance + (i - j) * xDistance / 2, xDistance * (j + 1), tanBaseDown);
						g2.draw(path);

						// drawing arrow
						path = new Path2D.Double();
						path.moveTo(x - 12, yDistance + (i - j) * xDistance / 4 + 12);
						path.lineTo(x + 12, yDistance + (i - j) * xDistance / 4);
						path.lineTo(x + 12, yDistance + (i - j) * xDistance / 4 + 24);
						g2.fill(path);

						// drawing gain text
						g.setColor(Color.black);
						g.drawString(gains[i][j] + "", x - 12, yDistance + (i - j) * xDistance / 4 - 6);

					} else {
						// drawing arc
						g.setColor(Color.BLUE);
						path = new Path2D.Double();
						path.moveTo(xDistance * (i + 1), tanBaseUp);
						x = xDistance * (j + i + 2) / 2;
						path.quadTo(x, yDistance - (j - i) * xDistance / 2, xDistance * (j + 1), tanBaseUp);
						g2.draw(path);

						// drawing arrow
						path = new Path2D.Double();
						path.moveTo(x + 12, yDistance - (j - i) * xDistance / 4 - 12);
						path.lineTo(x - 12, yDistance - (j - i) * xDistance / 4);
						path.lineTo(x - 12, yDistance - (j - i) * xDistance / 4 - 24);
						g2.fill(path);
						
						// drawing gain text
						g.setColor(Color.black);
						g.drawString(gains[i][j] + "", x - 12, yDistance - (j - i) * xDistance / 4 + 24);
					}
				}
			}

		}
		////////////////////////////////draw gains///////////////////////////////
	}
}
