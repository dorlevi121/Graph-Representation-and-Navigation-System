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
	public void buildRobotsPath(List<GraphRobot> robots, List<GraphFruit> fruits) {
		GraphRobot temp;
		for(GraphFruit f : fruits) {
			temp = null;
			double dist = Double.MAX_VALUE;
			for(GraphRobot r : robots) {
				if(dist > (algoGraph.shortestPathDist(r.getSrc(), f.getEdge().getSrc()) + f.getEdge().getWeight()) 
						&& (!r.getOnTheWay())) {
					dist = algoGraph.shortestPathDist(r.getSrc(), f.getEdge().getSrc()) + f.getEdge().getWeight();
					temp = r;
				}
			}
			if(temp != null && temp.getId() != -1) {
				temp.setOnTheWay(true);
				temp.setPath(algoGraph.shortestPath(temp.getSrc(), f.getEdge().getSrc()));
				if(temp.getPath().size() > 1) temp.getPath().remove(0); //Remove robot current node
				temp.getPath().add(graph.getNode(f.getEdge().getDest()));

				for(GraphRobot robot : robots) {
					if(robot.getPos() == robot.getPos()) 
						robot = temp;
				}
			}
		}

	}

}
