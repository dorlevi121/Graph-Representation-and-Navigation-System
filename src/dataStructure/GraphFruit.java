package dataStructure;

import org.json.JSONObject;

import utils.Point3D;

public class GraphFruit {
	private int value, type;
	private Point3D pos, guiLocation;

	private edge_data edge;
	private boolean visited;

	public GraphFruit(){
		this.value = 0;
		this.type = 1;
		this.pos = null;
		this.edge = null;
		this.visited = false;
	}

	
	public GraphFruit(int val,int type, Point3D pos, edge_data edge){
		this.value = val;
		this.type = type;
		this.pos = pos;
		this.edge = edge;
		this.visited = false;
	}

	
	public boolean getVisited() {
		return visited;
	}

	
	public void setVisited(boolean visited) {
		this.visited=  visited;
	}


	public int getValue(){
		return value;
	}

	
	public void setValue(int value){
		this.value = value;
	}



	public void setType(int type){
		this.type = type;
	}


	public int getType(){
		return type;
	}


	public Point3D getPos(){
		return pos;
	}


	public void setPos(Point3D pos){
		this.pos = pos;
	}


	public edge_data getEdge(){
		return edge;
	}



	public void setEdge(edge_data edge) {
		this.edge = edge;
	}


	public void initFruit(String g){
		if(!g.isEmpty()){
			try{
				JSONObject obj = new JSONObject(g);
				JSONObject fruit = (JSONObject) obj.get("Fruit");
				int value = fruit.getInt("value");
				this.value = value;
				int type = fruit.getInt("type");
				this.type = type;
				String pos = fruit.getString("pos");
				String[] point = pos.split(",");
				double x = Double.parseDouble(point[0]);
				double y = Double.parseDouble(point[1]);
				double z = Double.parseDouble(point[2]);
				this.pos = new Point3D(x, y, z);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	@Override
	public String toString() {
		return "{ " + "value:" + value + ", type:" + type + ", pos:" + pos + " }";
	}


	public Point3D getGuiLocation() {
		return guiLocation;
	}


	public void setGuiLocation(Point3D guiLocation) {
		this.guiLocation = guiLocation;
	}

}
