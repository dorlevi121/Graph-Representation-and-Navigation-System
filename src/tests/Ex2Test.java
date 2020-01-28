package tests;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import algorithms.Graph_Algo;
import algorithms.graph_algorithms;
import dataStructure.DGraph;
import dataStructure.graph;
import programManager.GraphManager;
import utils.Point3D;

/**
 * EX2 Structure test:
 * 1. make sure your code compile with this dummy test (DO NOT Change a thing in this test). 
 * 2. the only change require is to run your GUI window (see line 64).
 * 3. EX2 auto-test will be based on such file structure.
 * 4. Do include this test-case in you submitted repository, in folder Tests (under src).
 * 5. Do note that all the required packages are imported - do NOT use other 
 * 
 * @author boaz.benmoshe
 *
 */
class Ex2Test {
	private static graph _graph;
	private static graph_algorithms _alg;
	public static final double EPS = 0.001; 

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		_graph = tinyGraph();
	}
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testConnectivity() {
		_alg = new Graph_Algo(_graph);
		boolean con = _alg.isConnected();
		if(!con) {
			fail("shoulbe be connected");
		}
	}
	@Test
	void testgraph() {
		boolean ans = drawGraph(_graph);
		assertTrue(ans);
	}
	
	private static graph tinyGraph() {
		graph ans = new DGraph();
		return ans;
	}
	
	boolean drawGraph(graph g) { 
		GraphManager a = new GraphManager(g);
		return true;	
	}
	
	///////Q: WHY DOES ENVIROMENTAL ACTIVISTS CODE ONLY WITH JAVA ?
   ////////A: BECUASE IT HAS GARBAGE COLLECTOR 

}
