package gameClient;

import java.util.List;

import dataStructure.DGraph;
import dataStructure.GraphFruit;
import dataStructure.GraphRobot;
import dataStructure.Node;
import dataStructure.edge_data;
import dataStructure.node_data;
import utils.Point3D;

public class ManualGame {


	/**
	 * Functions for finding the elements on the screen
	 */
	private final int X_SCALE_TMIN = 15;
	private final int Y_SCALE_TMIN = 200;
	private final int Y_SCALE_TMAX = 50;
	double x_scale[];
	double y_scale[];
	/**
	 * Function that set robot location on the screen
	 * 
	 * @param robots - list of game robots
	 * @return
	 */
	public void setRobotGuiLocations(List<GraphRobot> robots) {
		if (!robots.isEmpty()) {
			for (GraphRobot r : robots) {
				double xR = scale(r.getPos().x(), x_scale[0], x_scale[1], X_SCALE_TMIN,
						1400 - Y_SCALE_TMAX);
				double yR = scale(r.getPos().y(), y_scale[1], y_scale[0], Y_SCALE_TMIN,
						600 - Y_SCALE_TMAX);
				Point3D pR = new Point3D(xR, yR);
				r.setGuiLocation(pR);
			}
		}
	}


	/**
	 * Function that set fruits location on the screen
	 * 
	 * @param fruits - list of game fruits
	 * @return
	 */
	public void setFruitsGuiLocations(List<GraphFruit> fruits) {

		if (!fruits.isEmpty()) {
			for (GraphFruit f : fruits) {
				double xF = scale(f.getPos().x(), x_scale[0], x_scale[1], X_SCALE_TMIN,
						1400 - Y_SCALE_TMAX);
				double yF = scale(f.getPos().y(), y_scale[1], y_scale[0], Y_SCALE_TMIN,
						600 - Y_SCALE_TMAX);
				Point3D pF = new Point3D(xF, yF);
				f.setGuiLocation(pF);
			}
		}
	}


	/**
	 * Function that set nodes location on the screen
	 * 
	 * @param graph - graph of the game
	 * @return
	 */
	public void setNodesGuiLocations(DGraph graph) {
		x_scale = xAxis_Min_Max(graph);
		y_scale = yAxis_Min_Max(graph);
		for (node_data n : graph.getV()) {
			double x = scale(n.getLocation().x(), x_scale[0], x_scale[1], X_SCALE_TMIN, 1400 - Y_SCALE_TMAX);
			double y = scale(n.getLocation().y(), y_scale[1], y_scale[0], Y_SCALE_TMIN,
					600 - Y_SCALE_TMAX);
			Point3D p = new Point3D(x, y);
			n.setGuiLocation(p);
		}
	}



	/**
	 * Function to get min and max of xAxis , used on scale function
	 * 
	 * @param graph - graph of the game
	 * @return
	 */
	private double[] xAxis_Min_Max(DGraph graph) {
		double arr[] = { Double.MAX_VALUE, Double.MIN_VALUE }; // min [0] max [1]
		for (node_data n : graph.getV()) {
			Point3D p = n.getLocation();
			if (p.x() < arr[0])
				arr[0] = p.x();
			if (p.x() > arr[1])
				arr[1] = p.x();

		}
		return arr;
	}



	/**
	 * Function to get min and max of yAxis , used on scale function
	 * 
	 * @param graph - graph of the game
	 * @return
	 */
	private double[] yAxis_Min_Max(DGraph graph) {
		double arr[] = { Double.MAX_VALUE, Double.MIN_VALUE }; // min [0] max [1]
		for (node_data n : graph.getV()) {
			Point3D p = n.getLocation();
			if (p.y() < arr[0])
				arr[0] = p.y();
			if (p.y() > arr[1])
				arr[1] = p.y();

		}
		return arr;
	}



	private double scale(double data, double r_min, double r_max, double t_min, double t_max) {
		double res = ((data - r_min) / (r_max - r_min)) * (t_max - t_min) + t_min;
		return res;
	}



	/**
	 * Function to find node by location
	 * 
	 * @param p - location of second mouse click
	 * @param nodeRobot - the node key of the robot
	 * @param graph - graph of the game
	 * @return
	 */
	public node_data findNodeByLocation(Point3D p, int nodeRobot, DGraph graph) {
		node_data curNode = new Node(-1);
		double curDistance = Double.MAX_VALUE;
		for (edge_data edge : graph.getE(nodeRobot)) {
			double distance = graph.getNode(edge.getDest()).getGuiLocation().distance2D(p)/10;
			if (distance >= 0 && distance <= 12 && distance < curDistance) {
				curDistance = distance;
				curNode = graph.getNode(edge.getDest());
			}
		}
		if(curNode.getKey() == -1)
			System.out.println("You can choose Just neighbors nodes");
		return curNode;
	}



	/**
	 * Function to find robot by location
	 * 
	 * @param p - location of first mouse click
	 * @param robots - list of game fruits
	 * @return
	 */
	public GraphRobot findRobotByLocation(Point3D p, List<GraphRobot> robots) {
		GraphRobot r = new GraphRobot();
		double curDistance = Double.MAX_VALUE;
		for (GraphRobot robot : robots) {
			double distance = robot.getGuiLocation().distance2D(p)/10;
			//System.out.println("robot: " + robot.getId() + " " + distance);
			if (distance >= 0 && distance <= 15 && curDistance > distance) {
				curDistance = distance;
				r = robot;
			}
		}
		return r;
	}

}
