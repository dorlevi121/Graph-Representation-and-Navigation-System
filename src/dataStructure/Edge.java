package dataStructure;

import java.io.Serializable;

public class Edge implements edge_data,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Node src, dest;
	private double weight;
	private String info;
	private int tag;
	
	//Empty Constructor
	public Edge() {
		this.dest = new Node();
		this.src = new Node();
		this.tag = 0;
		this.weight = 0;
		this.info = "";
	}
	
	public Edge(Node src, Node dest, double weight) {
		this.dest = dest;
		this.src = src;
		this.tag = 0;
		this.weight = weight;
		this.info = "";
	}
	
	public Edge(Edge e) {
		this.src = e.src;
		this.dest = e.dest;
		this.weight = e.weight;
		this.info = e.info;
		this.tag = e.tag;
	}

	@Override
	public int getSrc() {
		return this.src.getKey();
	}

	@Override
	public int getDest() {
		return this.dest.getKey();
	}

	@Override
	public double getWeight() {
		return this.weight;
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

	
	@Override
	public String toString() {
		return "Soruce Vretex: " + src.toString() + "\nDestination Vertex: " + dest.toString() + "\nEdge Weight: " 
				+ this.weight;
	}
}
