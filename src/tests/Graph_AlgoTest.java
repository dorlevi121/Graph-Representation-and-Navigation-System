package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import algorithms.Graph_Algo;
import dataStructure.Node;

class Graph_AlgoTest {
	Graph_Algo test = new Graph_Algo();
	@Test
	void addGraph() {
		test.getG().addNode(new Node());
		test.getG().addNode(new Node(1));
		test.getG().addNode(new Node(2));
		test.getG().addNode(new Node(3));

		test.getG().connect(0, 1, 1);
		test.getG().connect(0, 3, 3);
		test.getG().connect(3, 0, 3);
		test.getG().connect(1, 2, 1);
		test.getG().connect(2, 0, 2);	
		assertEquals(test.getG().getNode(0).getKey(), new Node().getKey());
	}
	
	@Test
	void save() {
		test.getG().addNode(new Node());
		test.getG().addNode(new Node(1));
		test.getG().addNode(new Node(2));
		test.getG().addNode(new Node(3));

		test.getG().connect(0, 1, 1);
		test.getG().connect(0, 3, 3);
		test.getG().connect(3, 0, 3);
		test.getG().connect(1, 2, 1);
		test.getG().connect(2, 0, 2);	
		test.save("graphTest.txt");
	}
	
	@Test
	void initFromFile() {
		test.init("graphTest.txt");
	}
	
	@Test
	void isConnected() {
		test.getG().addNode(new Node());
		test.getG().addNode(new Node(1));
		test.getG().addNode(new Node(2));
		test.getG().addNode(new Node(3));

		test.getG().connect(0, 1, 1);
		test.getG().connect(0, 3, 3);
		test.getG().connect(3, 0, 3);
		test.getG().connect(1, 2, 1);
		test.getG().connect(2, 0, 2);	
		
		assertEquals(test.isConnected(), true);
		test.getG().removeEdge(2, 0);
		assertEquals(test.isConnected(), false);

		
	}
	
	@Test
	void shortestPathDist() {
		test.getG().addNode(new Node());
		test.getG().addNode(new Node(1));
		test.getG().addNode(new Node(2));
		test.getG().addNode(new Node(3));

		test.getG().connect(0, 1, 1);
		test.getG().connect(0, 3, 3);
		test.getG().connect(3, 0, 3);
		test.getG().connect(1, 2, 1);
		test.getG().connect(2, 0, 2);	
		assertEquals(test.shortestPathDist(0, 2), 2);
	}
	
	
	@Test
	void shortestPath() {
		test.getG().addNode(new Node());
		test.getG().addNode(new Node(1));
		test.getG().addNode(new Node(2));
		test.getG().addNode(new Node(3));

		test.getG().connect(0, 1, 1);
		test.getG().connect(0, 3, 3);
		test.getG().connect(3, 0, 3);
		test.getG().connect(1, 2, 1);
		test.getG().connect(2, 0, 2);	
		assertEquals(test.shortestPath(0, 2).toString(), 
		"[key: 2, weight: 2.0, key: 1, weight: 1.0, key: 0, weight: 0.0]");	
		}
	
	
	@Test
	void TSP() {
		test.getG().addNode(new Node());
		test.getG().addNode(new Node(1));
		test.getG().addNode(new Node(2));
		test.getG().addNode(new Node(3));

		test.getG().connect(0, 1, 1);
		test.getG().connect(0, 3, 3);
		test.getG().connect(3, 0, 3);
		test.getG().connect(1, 2, 1);
		test.getG().connect(2, 0, 2);	
		
		List<Integer> t = new ArrayList<>();
		t.add(0);
		t.add(2);
		
		assertEquals(test.TSP(t).toString(), 
		"[key: 2, weight: 2.0, key: 1, weight: 1.0, key: 0, weight: 0.0]");
		}

}
