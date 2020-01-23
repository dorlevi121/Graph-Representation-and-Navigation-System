package dataStructure;

import java.io.Serializable;

import utils.Point3D;

public class Node implements node_data,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int key, tag; 
	private double weight;
	private String info;
	private Point3D location, guiLocation;



	//Empty Constructor
	public Node() {
		this.key = 0;
		this.tag = 0;
		this.weight = 0;
		this.info = "";
		this.location = Point3D.ORIGIN;
	}
	
	
	public Node(int key) {
		this.key = key;
		this.tag = 0;
		this.weight = 0;
		this.info = "";
		this.location = Point3D.ORIGIN;
	}
	
	
	public Node(Node n) {
		this.key = n.key;
		this.weight = n.weight;
		this.info = n.info;
		this.location = n.location;
		this.tag = n.tag;
	}
	
	
	public Node(Point3D l, int key, double weight){
		this.location=l;
		this.key=key;
		this.weight=weight;
		this.tag = 0;
		this.info = "";
	}	
	
	public Node(Point3D l, int key){
		this.location=l;
		this.key=key;
		this.tag = 0;
		this.weight = 0;
		this.info = "";
		}	
	
	
	@Override
	public int getKey() {
		return this.key;
	}

	
	@Override
	public Point3D getLocation() {
		return this.location;
	}

	
	@Override
	public void setLocation(Point3D p) {
		this.location = p;
	}

	
	@Override
	public double getWeight() {
		return this.weight;
	}

	
	@Override
	public void setWeight(double w) {
		this.weight = w;
	}

	
	@Override
	public String getInfo() {
		return this.info;
	}

	
	@Override
	public void setInfo(String s) {
		this.info = s;
	}

	
	@Override
	public int getTag() {
		return this.tag;
	}

	
	@Override
	public void setTag(int t) {
		this.tag = t;
	}

	
	public String toString() {
		return "key: " + this.key + " , loc: " + this.getLocation();
	}


	@Override
	public Point3D getGuiLocation() {
		return this.guiLocation;
		
	}



	@Override
	public void setGuiLocation(Point3D p) {
		this.guiLocation = p;
		
	}
}
