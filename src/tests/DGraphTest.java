package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import dataStructure.DGraph;
import dataStructure.Edge;
import dataStructure.Node;

class DGraphTest {

	@Test
	void addNode() {
		DGraph test = new DGraph();
		Node n1 = new Node();

		test.addNode(n1);
		assertEquals(test.getNode(0),n1);

	}

	@Test
	void addEdge() {
		DGraph test = new DGraph();
		Node n0 = new Node();
		Node n1 = new Node(1);
		
		test.connect(0, 1, 5);
		assertEquals(test.getEdge(0, 1).getDest(),1);
		assertEquals(test.getEdge(0, 1).getSrc(),0);
		assertEquals(test.getEdge(0, 1).getWeight(),5);
	}
	
	@Test
	void getVertices() {
		DGraph test = new DGraph();
		Node n0 = new Node();
		Node n1 = new Node(1);
		test.addNode(n0);
		test.addNode(n1);

		assertEquals(test.getV().size(),2);
	}
	
	
	@Test
	void getEdges() {
		DGraph test = new DGraph();
		test.connect(0, 1, 5);
		test.connect(0, 3, 5);

		assertEquals(test.getE(0).size(),2);
	}
	
	@Test
	void removeNode() {
		DGraph test = new DGraph();
		Node n0 = new Node();
		Node n1 = new Node(1);
		test.addNode(n0);
		test.addNode(n1);
		test.removeNode(0);
		assertEquals(test.getV().size(),1);
	}
	
	
	@Test
	void removeEdges() {
		DGraph test = new DGraph();
		test.connect(0, 1, 5);
		test.connect(0, 3, 5);
		test.removeEdge(0, 1);
		assertEquals(test.getE(0).size(),1);
	}
}
