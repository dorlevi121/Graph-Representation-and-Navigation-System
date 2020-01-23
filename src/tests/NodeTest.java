package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import dataStructure.Node;

class NodeTest {

	@Test
	void constructor() {
		Node n1 = new Node();
		Node n2 = new Node(1);
		Node n3 = new Node(2);
		Node n4 = new Node(4);	
		assertEquals(n1.getKey(),0);
		assertEquals(n2.getKey(),1);
		assertEquals(n3.getKey(),2);
		assertEquals(n4.getKey(),4);
	}
	
	@Test
	void print() {
		Node n1 = new Node();
		Node n2 = new Node(1);
			
		assertEquals(n1.toString() ,"key: 0, weight: 0.0");
		assertEquals(n2.toString(),"key: 1, weight: 0.0");

	}

}
