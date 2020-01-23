package dataStructure;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import utils.Point3D;

public class GraphRobot {
	private int id, dest, src;
	private Point3D pos, guiLocation;
	private double speed, value;
	private edge_data edge;
	private List<node_data> path;
	private Boolean onTheWay = false;

	
	public GraphRobot(){
		this.id = -1;
		this.pos = null;
		this.speed = 1;
		this.edge = null;
		this.src = -1;
		this.dest = -1;
		this.path = new ArrayList<>();
		this.value = 0;
	}

	

	public GraphRobot(int id,Point3D pos,double speed,int node,edge_data edge){
		this.id = id;
		this.pos = pos;
		this.speed = speed;
		this.edge = edge;
		this.src = node;
		this.dest = -1;
		this.value = 0;
		this.path = new ArrayList<>();
	}
	 
	
	public Boolean getOnTheWay() {
		return onTheWay;
	}

	
	public void setOnTheWay(Boolean onTheWay) {
		this.onTheWay = onTheWay;
	}

	
	public int getDest() {
		return dest;
	}

	
	public void setDest(int dest) {
		this.dest = dest;
	}
	

	public int getId(){
		return id;
	}

	
	public void setId(int id){
		this.id = id;
	}

	
	public Point3D getPos() {
		return pos;
	}

	
	public void setPos(Point3D pos) {
		this.pos = pos;
	}

	
	public double getSpeed() {
		return speed;
	}

	
	public void setSpeed(double speed) {
		this.speed = speed;
	}

	
	public int getSrc() {
		return src;
	}

	
	public void setSrc(int node) {
		this.src = node;
	}

	
	public edge_data getEdge() {
		return edge;
	}

	
	public void setEdge(edge_data edge) {
		this.edge = edge;
	}

	
	public List<node_data> getPath(){
		return path;
	}

	
	public void setPath(List<node_data> path) {
		this.path.clear();
		this.path = path;
		reversePath();
	}

	
	public double getValue() {
		return value;
	}

	
	public void setValue(double value) {
		this.value = value;
	}

	
	
	public Point3D getGuiLocation() {
		return this.guiLocation;
	}



	public void setGuiLocation(Point3D guiLocation) {
		this.guiLocation = guiLocation;
	}
	
	

	public void initRobot(String g)
	{
		if(!g.isEmpty())
		{
			try
			{
				JSONObject obj = new JSONObject(g);
				JSONObject robot = (JSONObject) obj.get("Robot");
				String pos = robot.getString("pos");
				String[] arr = pos.split(",");
				double x = Double.parseDouble(arr[0]);
				double y = Double.parseDouble(arr[1]);
				double z = Double.parseDouble(arr[2]);
				this.pos = new Point3D(x, y, z);
				int id = robot.getInt("id");
				this.id = id;
				int speed = robot.getInt("speed");
				this.speed = speed;
				this.src  = robot.getInt("src");
				this.dest = robot.getInt("dest");
			}
			catch (Exception e) {
				System.out.println("fail to init robot");
			}
		}
	}


	@Override
	public String toString() {
		return "{ " + "id:" + id + ", value:" + value + ", src:" + src + ", dest:" + dest
				+ ", speed:" + speed + ", pos:" + pos + "}";
	}


	private void reversePath() {
		List<node_data> temp = new ArrayList<>();
		for (int i = this.path.size()-1; i >= 0 ; i--) {
			temp.add(this.path.get(i));
		}
		this.path = temp;
	}

	

	
	
	public void clear() {
		this.id = 0;
		this.pos = null;
		this.speed = 1;
		this.edge = null;
		this.src = 0;
		this.dest = -1;
		this.path = new ArrayList<>();
		this.value = 0;
		this.onTheWay = false;
	}
}
