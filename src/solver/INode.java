package solver;

import java.util.LinkedList;

public interface INode {

	/*
	 * set the number of node
	 */
	public void setNext(Integer next);

	/*
	 * return Linked list of next nodes
	 */
	public LinkedList<Integer> getNext();

	/*
	 * set value to each next node
	 */
	public void setValue(Integer next , Double value);

	/*
	 * get value for each node
	 */
	public Double getValue(Integer next);

	public int getName();

}
