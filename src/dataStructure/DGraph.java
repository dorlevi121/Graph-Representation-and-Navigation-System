package dataStructure;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import utils.Point3D;

public class DGraph implements graph,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private HashMap<Node id, Node> vertices;
	private HashMap<Integer, node_data> vertices;
	//private HashMap< src vertex(Node), HashMap<dest vertex(Node), edge(Edge)> > edges;
	private HashMap< node_data, HashMap<node_data, edge_data> > edges;
	private int numOfEdges, modeCount ;

	public DGraph() {
		vertices = new HashMap<>();
		edges = new HashMap<>();
		numOfEdges=0;
		modeCount = 0;
	}
	
	
	public DGraph(DGraph dg) {
		this();
		this.vertices.putAll(dg.vertices);
		this.edges.putAll(dg.edges);
		this.numOfEdges = dg.numOfEdges;
		this.modeCount = dg.modeCount;
	}

	@Override
	public node_data getNode(int key) {
		return this.vertices.get(key);
	}
	

	@Override
	public edge_data getEdge(int src, int dest) {
		return edges.get(vertices.get(src)).get(vertices.get(dest));
	}
	

	//If an existing node key is passed then the previous node_data gets replaced by the n. 
	//If a new pair is passed, then the pair gets inserted as a whole.
	@Override
	public void addNode(node_data n) {
		this.vertices.put(n.getKey(), n);
		modeCount++;
	}


	@Override
	public void connect(int src, int dest, double w) {

		if(this.vertices.get(src) == null) {
			Node s = new Node(src);
			this.vertices.put(src, s);
		}
		if(this.vertices.get(dest) == null) {
			Node d = new Node(dest);
			this.vertices.put(dest, d);
		}

		Node tempS = (Node) this.vertices.get(src);
		Node tempD = (Node) this.vertices.get(dest);
		Edge e = new Edge(tempS, tempD, w);
		
		if(this.edges.get(this.vertices.get(src)) == null) {
			this.edges.put(tempS, new HashMap<node_data, edge_data>());
			this.edges.get(tempS).put(tempD, e);
		}

		else 
			this.edges.get(tempS).put(tempD , e);
		
		numOfEdges++;
		modeCount++;
	}


	//The method return a collection view containing all the Nodes of the vertices map.
	@Override
	public Collection<node_data> getV() {
		return this.vertices.values();
	}

	
	@Override
	public Collection<edge_data> getE(int node_id) {
		if(this.vertices.get(node_id) == null) return null;
		
		else if(this.edges.get(this.vertices.get(node_id)) == null) return null;
		
		return this.edges.get(this.vertices.get(node_id)).values();
	}

	
	@Override
	public node_data removeNode(int key) {
		Node temp = (Node) this.vertices.get(key);
		if(temp == null) return null;

		else if(this.edges.get(temp) == null) {
			for (Map.Entry<node_data, HashMap<node_data, edge_data>> entry : edges.entrySet()) {
				entry.getValue().remove(temp); 
				if(entry.getValue().isEmpty()) this.edges.remove(entry.getKey());
			}
		}

		else {
			this.edges.remove(temp);
			for (Map.Entry<node_data, HashMap<node_data, edge_data>> entry : edges.entrySet()) {
				entry.getValue().remove(temp);
				if(entry.getValue().isEmpty()) this.edges.remove(entry.getKey());
			}
		}
		this.vertices.remove(key);
		modeCount++;
		return temp;
	}
	

	@Override
	public edge_data removeEdge(int src, int dest) {
		Node s = (Node) this.vertices.get(src);
		Node d = (Node) this.vertices.get(dest);
		if(this.edges.get(s) == null || this.edges.get(s).get(d) == null)
			return null;
		
		else {
			Edge temp = (Edge) this.edges.get(s).get(d);
			this.edges.get(s).remove(d);
			if(this.edges.get(s).isEmpty()) {
				this.edges.remove(s);
			}
			numOfEdges--;
			modeCount++;
			return temp;
		}
	}
	
	

	@Override
	public int nodeSize() {
		return this.vertices.size();
	}

	
	@Override
	public int edgeSize() {
		return this.numOfEdges;
	}

	
	@Override
	public int getMC() {
		return this.modeCount;
	}

	
	public void init(String g) {
		// TODO Auto-generated method stub
	try {
			JSONObject Jobj = new JSONObject(g);
			JSONArray JEdges = Jobj.getJSONArray("Edges");
			JSONArray JVerticies = Jobj.getJSONArray("Nodes");
			
			

			for (int i = 0; i < JVerticies.length(); i++) 
			{
				JSONObject jvertex= (JSONObject) JVerticies.get(i);
				String location = (String) jvertex.getString("pos");
				String[] points = location.split(",");
				double x = Double.parseDouble(points[0]);
				double y = Double.parseDouble(points[1]);	
				double z = Double.parseDouble(points[2]);
				
				int id = jvertex.getInt("id");
				Point3D p = new Point3D(x,y,z);
				node_data n = new Node(p, id);
				
				this.addNode(n);
			}
			
			for (int i = 0; i < JEdges.length(); i++) 
			{
				JSONObject edgeE= (JSONObject) JEdges.get(i);
				int src = edgeE.getInt("src");
				int dest = edgeE.getInt("dest");
				double weight = edgeE.getDouble("w");
				this.connect(src, dest, weight);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			
		} 
	}
	
	
	@Override
	public String toString() {
		String node = "";
		String edge = "";
		for (int i = 0; i < this.vertices.size(); i++) {
			node += " {" + this.vertices.get(i) + "} ,";
			if( this.edges.get(this.vertices.get(i)) != null ) {
			    for (edge_data value : this.edges.get(this.vertices.get(i)).values()) {
			    	edge += " {" + value + "} ,";
			    }
			}
		}
		
		return "Edges" + " { " + edge + " } " + " Nodes " + "{ " + node + " } " ;
	}
}
