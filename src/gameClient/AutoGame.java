package gameClient;

import java.util.List;

import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.GraphFruit;
import dataStructure.GraphRobot;

public class AutoGame {

	private Graph_Algo algoGraph;
	private DGraph graph;

	public AutoGame(DGraph graph) {
		this.graph = graph;
		this.algoGraph = new Graph_Algo();
		this.algoGraph.init(graph);

	}

	/**
	 * Finds the robot closest to each fruit and  build a path.
	 * 
	 * @param graph - graph 
	 * @param robots - list of game fruits
	 * @param fruits - list of game fruits
	 */
//	public void buildRobotsPath(List<GraphRobot> robots, List<GraphFruit> fruits) {
//		GraphRobot temp;
//		for(GraphFruit f : fruits) {
//			temp = null;
//			double dist = Double.MAX_VALUE;
//			for(GraphRobot r : robots) {
//				if(dist > (algoGraph.shortestPathDist(r.getSrc(), f.getEdge().getSrc()) + f.getEdge().getWeight()) 
//						) {
//					dist = algoGraph.shortestPathDist(r.getSrc(), f.getEdge().getSrc()) + f.getEdge().getWeight();
//					temp = r;
//				}
//			}
//			if(temp != null && temp.getId() != -1) {
//				temp.setOnTheWay(true);
//				temp.setPath(algoGraph.shortestPath(temp.getSrc(), f.getEdge().getSrc()));
//				temp.getPath().remove(0); //Remove robot current node
//				temp.getPath().add(graph.getNode(f.getEdge().getDest()));
//
//				for(GraphRobot robot : robots) {
//					if(robot.getPos() == robot.getPos()) 
//						robot = temp;
//				}
//			}
//		}
//
//	}
	
	static int src, dest ,i=0;
	public void buildRobotsPath(List<GraphRobot> robots, List<GraphFruit> fruits) {
		setFruitVisitable(fruits);
		GraphFruit temp;
		for(GraphRobot r : robots) {
			temp = fruits.get(0);
			double dist = Double.MAX_VALUE;
			for(GraphFruit f : fruits) {
				if(((dist - algoGraph.shortestPathDist(r.getSrc(), f.getEdge().getSrc()) < 2) 
						&& !f.getVisited() && temp.getValue() < f.getValue() ) || 
						((dist - algoGraph.shortestPathDist(r.getSrc(), f.getEdge().getSrc()) >= 2) 
								&& !f.getVisited()) ) {
					dist = algoGraph.shortestPathDist(r.getSrc(), f.getEdge().getSrc());
					temp = f;
				}
			}
			
			if(r != null && r.getId() != -1) {
				r.setPath(algoGraph.shortestPath(r.getSrc(), temp.getEdge().getSrc()));
				r.getPath().remove(0); //Remove robot current node
				r.getPath().add(graph.getNode(temp.getEdge().getDest()));

				for(GraphFruit fruit : fruits) {
					if(fruit.getPos() == temp.getPos()) 
						fruit.setVisited(true);
				}
			}
		}
	}
	
	
	private void setFruitVisitable(List<GraphFruit> fruits) {
		for(GraphFruit f : fruits) {
			f.setVisited(false);
		}
	}
}
