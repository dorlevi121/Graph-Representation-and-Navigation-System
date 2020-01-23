package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import dataStructure.Edge;
import dataStructure.Node;

class EdgeTest {

	@Test
	void constructor() {
		Node n2 = new Node(1);
		Node n3 = new Node(2);
		Edge e1 = new Edge();
		Edge e2 = new Edge(n2, n3, 4);
		assertEquals(e1.getDest(),0);
		assertEquals(e1.getSrc(),0);
		assertEquals(e1.getWeight(),0);

		assertEquals(e2.getDest(),2);
		assertEquals(e2.getSrc(),1);
		assertEquals(e2.getWeight(),4);	
		}
	


}
