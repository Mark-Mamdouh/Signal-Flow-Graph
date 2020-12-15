package solver;

import java.util.LinkedList;
import java.util.Map;

public interface ISFG {

	public LinkedList<INode> getMainNodes() ;
	
	public void setNext(int next) ;
	
	public void setValue(Integer next , Double value) ;
	
	public void createNode();
	
	public LinkedList<LinkedList<Integer> > forwardPath();
	
	public Map<LinkedList<Integer>, Double> getLoops();
	
	public Map<LinkedList<Integer>, Double> getForwardPaths();
	
	
}
