package solver;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;

public class SignalFlowGraphs implements ISFG {

	// ***********************************************************************************
	public class Node implements INode {

		private int name;
		private Map<Integer, Double> value;
		private LinkedList<Integer> next;

		public Node(int name) {
			this.name = name;
			this.value = new HashMap<Integer, Double>();
			this.next = new LinkedList<Integer>();
		}

		public void setNext(Integer next) {
			this.next.add(next);
		}

		public LinkedList<Integer> getNext() {
			return this.next;
		}

		public void setValue(Integer next, Double value) {
			this.value.put(next, value);
		}

		public Double getValue(Integer next) {
			return value.get(next);
		}

		public int getName() {
			return name;
		}
	}
	// ***********************************************************************************

	private LinkedList<INode> nodes = new LinkedList<INode>();
	private int numberOfNodes = 0;
	private LinkedList<LinkedList<Integer>> forwardPaths = new LinkedList<LinkedList<Integer>>();
	private LinkedList<LinkedList<Integer>> loops = new LinkedList<LinkedList<Integer>>();
	private Map<LinkedList<Integer>, Double> mapForwardPaths = new HashMap<LinkedList<Integer>, Double>();
	private Map<LinkedList<Integer>, Double> mapLoops = new HashMap<LinkedList<Integer>, Double>();
	private Map<LinkedList<Integer>, Double> auxMap = new HashMap<LinkedList<Integer>, Double>();
	private LinkedList<LinkedList<Integer>> mixed = new LinkedList<LinkedList<Integer>>();
	private LinkedList<LinkedList<Integer>> remain = new LinkedList<LinkedList<Integer>>();
	private LinkedList<String> deltas = new LinkedList<String>();

	@Override
	public LinkedList<INode> getMainNodes() {
		return nodes;
	}

	@Override
	public void setNext(int next) {
		nodes.get(numberOfNodes - 1).setNext(next);
	}

	@Override
	public void setValue(Integer next, Double value) {
		nodes.get(numberOfNodes - 1).setValue(next, value);
	}

	@Override
	public void createNode() {
		INode newNode = new Node(numberOfNodes++);
		nodes.add(newNode);
	}

	@Override
	public LinkedList<LinkedList<Integer>> forwardPath() {

		LinkedList<Integer> seq = new LinkedList<Integer>();
		LinkedList<Integer> visited = new LinkedList<Integer>();
		Stack<Integer> stack = new Stack<Integer>();
		for (Integer next : nodes.getFirst().getNext()) {
			stack.push(next);
		}
		visited.add(0);
		while (!stack.isEmpty()) {
			Integer neNode = stack.peek();
			if (!visited.contains(neNode)) {
				visited.add(neNode);
				if (neNode.equals(nodes.getLast().getName())) {
					for (Integer i : visited) {
						seq.add(i);
					}
					forwardPaths.add(seq);
					seq = new LinkedList<Integer>();
					visited.remove(stack.pop());
				} else {
					for (Integer next : nodes.get(neNode).getNext()) {
						if (neNode < next)
							stack.push(next);
					}
				}
			} else {
				visited.remove(stack.pop());
			}
		}
		return forwardPaths;
	}

	public LinkedList<LinkedList<Integer>> loops() {

		Stack<Integer> stack = new Stack<Integer>();
		LinkedList<Integer> visited = new LinkedList<Integer>();
		LinkedList<Integer> seq = new LinkedList<Integer>();
		for (INode node : nodes) {
			for (int i = 0; i < node.getNext().size(); i++) {
				while (!stack.isEmpty()) {
					stack.pop();
				}
				visited = new LinkedList<Integer>();
				stack.push(node.getNext().get(i));
				if (stack.peek().equals(node.getName())) {
					seq.add(node.getName());
					while (!stack.isEmpty()) {
						seq.add(stack.pop());
					}
					loops.add(seq);
					seq = new LinkedList<Integer>();
				}
				while (!stack.isEmpty()) {
					INode node2 = nodes.get(stack.peek());
					if (!visited.contains(node2.getName())) {
						visited.add(node2.getName());
						for (int j = 0; j < node2.getNext().size(); j++) {
							if (!stack.contains(node2.getNext().get(j))) {
								stack.push(node2.getNext().get(j));
								if (stack.peek().equals(node.getName())) {
									seq.add(node.getName());
									for (int e = 0; e < visited.size(); e++) {
										seq.add(visited.get(e));
									}
									seq.add(node.getName());
									loops.add(seq);
									stack.pop();
									seq = new LinkedList<Integer>();
								}
							}
						}
					} else {

						visited.remove(stack.pop());
					}
				}
			}
		}
		removeRedantant();
		return loops;
	}

	public Map<LinkedList<Integer>, Double> getLoops() {
		mapLoops = calulateValue(loops);
		return mapLoops;
	}

	public Map<LinkedList<Integer>, Double> calulateValue(LinkedList<LinkedList<Integer>> fl) {

		boolean first = true;
		Integer num1 = 0;
		Double sum = 1.0;
		for (int i = 0; i < fl.size(); i++) {
			for (int j = 0; j < fl.get(i).size(); j++) {
				if (!first) {
					INode fir = nodes.get(num1);
					sum *= fir.getValue(fl.get(i).get(j));
					num1 = fl.get(i).get(j);

				} else {
					first = false;
					num1 = fl.get(i).get(j);
				}
			}
			auxMap.put(fl.get(i), sum);
			sum = 1.0;
			first = true;
		}
		return auxMap;
	}

	public Map<LinkedList<Integer>, Double> getForwardPaths() {
		mapForwardPaths = calulateValue(forwardPaths);
		return mapForwardPaths;
	}

	public Double getDelta() {
		
		Double sum = 1.0;
		int counter = 0;
		boolean first = true;
		Double s;
		Double sum2 = 0.0;
		for (LinkedList<Integer> test2 : loops) {
			System.out.println(mapLoops.get(test2));
			sum2 += mapLoops.get(test2);
		}
		sum -= sum2;
		while (true) {
			if (first) {
				s = calculateDelta(loops);
				first = false;
			} else {
				s = calculateDelta(mixed);
			}
			if (s == 0.0) {
				break;
			} else {
				if (counter == 0) {
					sum += s;
					counter = 1;
				} else {
					sum -= s;
					counter = 0;
				}
			}
		}
		return sum;
	}

	public Double calculateDelta(LinkedList<LinkedList<Integer>> test) {

		mixed = new LinkedList<LinkedList<Integer>>();
		boolean tuish = false;
		Double sum = 0.0;
		for (LinkedList<Integer> test2 : loops) {
			for (int i = 0; i < test.size(); i++) {
				for (int j = 0; j < test.get(i).size(); j++) {
					if (test2.contains(test.get(i).get(j))) {
						tuish = true;
					}
				}
				if (!tuish) {
					System.out.println(mapLoops.get(test2));
					System.out.println(mapLoops.get(test.get(i)));
					LinkedList<Integer> auxList = new LinkedList<Integer>();
					auxList.addAll(test2);
					auxList.addAll(test.get(i));
					mixed.add(auxList);
					mapLoops.put(auxList, mapLoops.get(test2) * mapLoops.get(test.get(i)));
				}
				tuish = false;
			}
		}
		removeRedantant2();
		for (LinkedList<Integer> n : mixed) {
			sum += mapLoops.get(n);
		}
		return sum;
	}

	public void removeRedantant() {

		int counter = 0;
		LinkedList<LinkedList<Integer>> remove = new LinkedList<LinkedList<Integer>>();
		for (int i = 0; i < loops.size(); i++) {
			for (int j = i + 1; j < loops.size(); j++) {
				if (loops.get(i).size() == loops.get(j).size()) {
					for (int k = 0; k < loops.get(i).size(); k++) {
						if (loops.get(i).contains(loops.get(j).get(k))) {
							counter++;
						}
					}
					if (counter == loops.get(i).size()) {
						remove.add(loops.get(j));
					}
				}
				counter = 0;
			}
		}
		for (LinkedList<Integer> test : remove) {
			loops.remove(test);
		}
	}

	public void removeRedantant2() {

		boolean enter = true;
		LinkedList<LinkedList<Integer>> remove = new LinkedList<LinkedList<Integer>>();
		int array1[] = new int[nodes.size()];
		int array2[] = new int[nodes.size()];
		for (int i = 0; i < mixed.size(); i++) {
			for (int j = i + 1; j < mixed.size(); j++) {
				if (mixed.get(i).size() == mixed.get(j).size()) {
					for (int k = 0; k < mixed.get(i).size(); k++) {
						array1[mixed.get(i).get(k)] += 1;
					}
					for (int k = 0; k < mixed.get(j).size(); k++) {
						array2[mixed.get(j).get(k)] += 1;
					}
					for (int k = 0; k < array1.length; k++) {
						if (array1[k] != array2[k]) {
							enter = false;
						}
					}
					if (enter) {
						remove.add(mixed.get(j));
					}
					enter = true;
					array1 = new int[nodes.size()];
					array2 = new int[nodes.size()];
				}
			}
		}
		for (LinkedList<Integer> test : remove) {
			mixed.remove(test);
		}
	}

	public Double deltaForEachForwardPath() {
		
		Double sum = 0.0;
		Double s = 0.0;
		boolean enter = false;
		for (LinkedList<Integer> forward : forwardPaths) {
			remain = new LinkedList<>();
			Double f = mapForwardPaths.get(forward);
			for (LinkedList<Integer> loop : loops) {
				for (int i = 0; i < loop.size(); i++) {
					if (forward.contains(loop.get(i))) {
						enter = true;
					}
				}
				if (!enter) {
					remain.add(loop);
				} else {
					enter = false;
				}
			}
			s = getDeltaForEachNode();
			deltas.add(s.toString());
			sum += s * f;
		}
		return sum;
	}

	public Double calculateDeltaForEachNode(LinkedList<LinkedList<Integer>> test) {

		mixed = new LinkedList<LinkedList<Integer>>();
		boolean tuish = false;
		Double sum = 0.0;
		for (LinkedList<Integer> test2 : remain) {
			for (int i = 0; i < test.size(); i++) {
				for (int j = 0; j < test.get(i).size(); j++) {
					if (test2.contains(test.get(i).get(j))) {
						tuish = true;
					}
				}
				if (!tuish) {
					System.out.println(mapLoops.get(test2));
					System.out.println(mapLoops.get(test.get(i)));
					LinkedList<Integer> auxList = new LinkedList<Integer>();
					auxList.addAll(test2);
					auxList.addAll(test.get(i));
					mixed.add(auxList);
					mapLoops.put(auxList, mapLoops.get(test2) * mapLoops.get(test.get(i)));

				}
				tuish = false;

			}
		}
		removeRedantant2();
		for (LinkedList<Integer> n : mixed) {
			sum += mapLoops.get(n);
		}
		return sum;
	}

	public Double getDeltaForEachNode() {

		Double sum = 1.0;
		int counter = 0;
		boolean first = true;
		Double s;
		Double sum2 = 0.0;
		for (LinkedList<Integer> test2 : remain) {
			System.out.println(mapLoops.get(test2));
			sum2 += mapLoops.get(test2);

		}
		sum -= sum2;
		while (true) {
			if (first) {
				s = calculateDeltaForEachNode(remain);
				first = false;
			} else {
				s = calculateDeltaForEachNode(mixed);
			}
			if (s == 0.0) {
				break;
			} else {
				if (counter == 0) {
					sum += s;
					counter = 1;
				} else {
					sum -= s;
					counter = 0;
				}
			}
		}
		return sum;
	}

	public LinkedList<String> getDeltes() {
		return deltas;
	}

	public void clear() {

		forwardPaths = new LinkedList<LinkedList<Integer>>();
		loops = new LinkedList<LinkedList<Integer>>();
		mapForwardPaths = new HashMap<LinkedList<Integer>, Double>();
		mapLoops = new HashMap<LinkedList<Integer>, Double>();
		auxMap = new HashMap<LinkedList<Integer>, Double>();
		mixed = new LinkedList<LinkedList<Integer>>();
		remain = new LinkedList<LinkedList<Integer>>();
		nodes = new LinkedList<INode>();
		numberOfNodes = 0;
		deltas = new LinkedList<String>();
	}
}