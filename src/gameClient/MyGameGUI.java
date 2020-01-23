package gameClient;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.json.JSONException;
import org.json.JSONObject;

import Server.Game_Server;
import Server.game_service;
import dataStructure.DGraph;
import dataStructure.GraphFruit;
import dataStructure.GraphRobot;
import dataStructure.edge_data;
import dataStructure.node_data;
import dataStructure.sortFruitsByValue;
import utils.Point3D;
import utils.Range;

public class MyGameGUI extends JFrame implements ActionListener, MouseListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DGraph graph;
	private List<GraphFruit> fruits;
	private List<GraphRobot> robots;
	private game_service game;
	private Range rx = new Range(Integer.MAX_VALUE,Integer.MIN_VALUE);
	private Range ry = new Range(Integer.MAX_VALUE,Integer.MIN_VALUE);
	private ManualGame manualGame;
	private AutoGame autoGame;
	private Graphics2D g2d;
	private KML_Logger kml;
	private int gameNumber, ID;
	Thread t;

	public MyGameGUI() {
		this.graph = new DGraph();
		fruits = new ArrayList<>();
		robots = new ArrayList<>();
		setVisible(true);
		initGame();
		this.ID = -1;

	}



	/**
	 * Initialized size of screen, title, etc.
	 */
	private void initGame() {
		this.setSize(1400, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setTitle("Graph Maker");
		initMenu();

	}



	//Init UI menu
	private void initMenu() {
		MenuBar menuBar = new MenuBar();
		this.setMenuBar(menuBar);

		Menu op = new Menu("Game");
		menuBar.add(op);

		MenuItem manual = new MenuItem("Manual");
		manual.addActionListener(this);
		op.add(manual);

		MenuItem auto = new MenuItem("Auto");
		auto.addActionListener(this);
		op.add(auto);


		Menu db = new Menu("DataBase");
		menuBar.add(db);

		MenuItem info = new MenuItem("Game Info");
		info.addActionListener(this);
		db.add(info);

		MenuItem place = new MenuItem("Place");
		place.addActionListener(this);
		db.add(place);

		this.addMouseListener(this);

	}



	@Override
	public void paint(Graphics d) {

		BufferedImage bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		g2d = bufferedImage.createGraphics();
		super.paint(g2d);
		g2d.setColor(Color.BLUE);
		if(game != null && game.isRunning()) {
			g2d.drawString("Time to end: " + game.timeToEnd()/1000, 200, 550);
		}

		if(graph != null) { setRange(); paintNodes(g2d);}
		if(!fruits.isEmpty()) paintFruites(g2d);
		if(!robots.isEmpty()) paintRobots(g2d);

		Graphics2D g2dComponent = (Graphics2D) d;
		g2dComponent.drawImage(bufferedImage, null, 0, 0);
	}



	//Draw Graph
	private void paintNodes(Graphics d) {
		Collection<node_data> nodes = graph.getV();

		for (node_data n : nodes) {

			Point3D p = n.getLocation(); //Hold node location

			double offsetx = (p.x() - rx.get_min())/(rx.get_max() - rx.get_min());
			double x = 1200 * offsetx + 100; 
			double offsety = (p.y() - ry.get_min())/(ry.get_max() - ry.get_min());
			double y = 400 * offsety;
			y = (400 - y) + 100;

			d.setColor(Color.BLUE); //Node color
			d.fillOval((int)x,(int)y+20,10,10); //Draw Vertex

			d.drawString(""+n.getKey(), ((int)x)-4, ((int)y)+20); //Draw node key

			//check if there are edges
			if (graph.edgeSize()==0) continue;
			if ((graph.getE(n.getKey())!=null)){
				Collection<edge_data> edges = graph.getE(n.getKey()); //All edges of mode n

				for (edge_data e : edges) {
					Point3D p2 = graph.getNode(e.getDest()).getLocation();

					double offsetx1 = (p2.x() - rx.get_min())/(rx.get_max() - rx.get_min());
					double x1 = 1200 * offsetx1 + 100; 
					double offsety1 = (p2.y() - ry.get_min())/(ry.get_max() - ry.get_min());
					double y1 = 400 * offsety1;
					y1 = (400 - y1) + 100;

					d.setColor(Color.RED); //Edge color
					d.drawLine(((int)x)+5, ((int)y)+25, ((int)x1)+5, ((int)y1)+25); //Draw Line

					d.setColor(Color.YELLOW); //Direction Color
					d.fillOval((int)((p.ix() * 0.1) + (0.9 * p2.ix())) + 4, 24 + (int)((p.iy() * 0.1) + 
							(0.9 * p2.iy())), 7 , 7);
					d.fillOval((int)((((int)x) * 0.1) + (0.9 * ((int)x1))) + 4, 24 + 
							(int)((((int)y) * 0.1) + (0.9 * ((int)y1))) , 7, 7);

					//draw weight
					d.setColor(Color.BLACK);
					String whight = String.valueOf(e.getWeight());
					d.drawString(whight.substring(0,3), 1 + (int)(((int)x * 0.7) + (0.3 * (int)x1) + 7)
							, (int)(((int)y * 0.7) + (0.3 * (int)y1)) + 22);
				}
			}	
		}
	}



	//Draw Fruits
	private void paintFruites(Graphics d)  {

		for(GraphFruit f : fruits) {
			Point3D pBefore = new Point3D(f.getPos());
			double offsetx = (pBefore.x() - rx.get_min())/(rx.get_max() - rx.get_min());
			double x = 1200 * offsetx + 100; 
			double offsety = (pBefore.y() - ry.get_min())/(ry.get_max() - ry.get_min());
			double y = 400 * offsety;
			y = (400 - y) + 100;

			Point3D pAfter = new Point3D(x, y);

			if(f.getType()<0)
				d.setColor(Color.ORANGE);
			else 
				d.setColor(Color.ORANGE);

			d.fillOval((int)pAfter.x()+5 , (int)pAfter.y() + 18 , 15, 15); //Draw fruit
		}
	}



	//Draw Robots
	private void paintRobots(Graphics d) {
		d.setFont(new Font("Ariel", Font.BOLD, 16));
		for (GraphRobot r : robots) {
			Point3D pBefore = new Point3D(r.getPos());
			double offsetx = (pBefore.x() - rx.get_min())/(rx.get_max() - rx.get_min());
			double x = 1200 * offsetx + 100; 
			double offsety = (pBefore.y() - ry.get_min())/(ry.get_max() - ry.get_min());
			double y = 400 * offsety;
			y = (400 - y) + 100;

			Point3D pAfter = new Point3D(x, y);
			d.setColor(Color.BLACK);
			d.drawOval((int)pAfter.x()-5, (int)pAfter.y() + 17, 18, 18); //Draw Robot
		}

		d.setColor(Color.RED);
		List<String> r = game.getRobots();
		int c = 0;
		for(String s : r) {
			d.drawString( s.toString(), 200, 80 + c);
			d.drawString("\n",200, 100);
			c+= 20;
		}

	}



	//Initialized the fruits list with all the game fruits
	private void initFruitsList() {
		this.fruits.clear();
		List<String> f = game.getFruits();
		for(String s : f) {
			GraphFruit fruit = new GraphFruit();
			fruit.initFruit(s);
			findFruitEdge(fruit);
			this.fruits.add(fruit);
		}
		Collections.sort(fruits, new sortFruitsByValue());
	}



	/**
	 * Initialized the robot list 
	 * 
	 */
	private void initRobotList() {

		String info = game.toString();
		try {
			JSONObject line = new JSONObject(info);
			JSONObject ttt = line.getJSONObject("GameServer");
			int rs = ttt.getInt("robots");
			for(int a = 0;a<rs;a++) {
				game.addRobot(a);
			}
		}
		catch (JSONException e) {e.printStackTrace();}

		List<String> r = game.getRobots();
		for(String s : r) {
			GraphRobot robot = new GraphRobot();
			robot.initRobot(s);
			this.robots.add(robot);
		}

	}



	/**
	 * Initialized game, graph, fruits and robots.
	 * 
	 * @param gameNumber - game number
	 */
	private void initGame(int gameNumber) {
		Game_Server.login(ID); //Connect to DB

		game = Game_Server.getServer(gameNumber);
		graph.init(game.getGraph()); //Init graph
		initFruitsList();//Init Fruits
		initRobotList();//Init Robots
		repaint();

	}




	/**
	 * find the edge the fruit is on.
	 * 
	 * @param f - fruit
	 */
	private void findFruitEdge(GraphFruit f) {
		Collection<node_data> v = graph.getV();
		for(node_data n : v) {
			Collection<edge_data> e = graph.getE(n.getKey());
			for(edge_data ed: e) {
				Point3D srcLocation =graph.getNode(ed.getSrc()).getLocation();
				Point3D destLocation =graph.getNode(ed.getDest()).getLocation();
				//check if the fruit is on the edge
				if(Math.abs((srcLocation.distance2D(destLocation) - (Math.abs((f.getPos().distance2D(srcLocation))) + 
						(Math.abs((f.getPos().distance2D(destLocation))))))) <= 0.0000001){
					int src = n.getKey();
					int dest = ed.getDest();
					if(src < dest) {
						src = ed.getDest();
						dest= n.getKey();
					}
					if(f.getType() == 1) {
						edge_data edF = graph.getEdge(dest, src);
						if(edF!= null) f.setEdge(edF);
					}
					//the reverse edge is the way to eat the fruit
					if(f.getType() == -1) {
						edge_data edF = graph.getEdge(src,dest);
						if(edF!= null)f.setEdge(edF);
					}
				}
			}
		}
	}



	/**
	 * Listening to user choice from menu bar
	 * 
	 * @param event - event listener
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		String str = event.getActionCommand();

		//Manual Game
		if(str.equals("Manual")) {
			clearGame();

			String userId = JOptionPane.showInputDialog("Enter your ID please");
			int id = Integer.parseInt(userId);
			this.ID = id;

			String scenario = JOptionPane.showInputDialog("Pick a scenario number between 0 to 23");
			int scanerioNumber = Integer.parseInt(scenario);
			gameNumber = scanerioNumber;

			initGame(scanerioNumber);
			this.manualGame = new ManualGame();

			manualGame.setNodesGuiLocations(this.graph);
			manualGame.setFruitsGuiLocations(this.fruits);
			manualGame.setRobotGuiLocations(this.robots);

			addMouseListener(this);

			game.startGame();
			threadListeningToKml();

			Runnable move = new Runnable() {

				@Override
				public void run() {
					while(game.isRunning()) {

						try {
							moveManualRobot();
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						System.out.println(game.toString());

					}

				}
			};
			Thread thread = new Thread(move);
			thread.start();

		}

		//Automatic Game
		else if(str.equals("Auto")) {
			clearGame();
			String userId = JOptionPane.showInputDialog("Enter your ID please");
			int id = Integer.parseInt(userId);
			this.ID = id;
			String scenario = JOptionPane.showInputDialog("Pick a scenario number between 0 to 23");
			int scanerioNumber = Integer.parseInt(scenario);
			gameNumber = scanerioNumber;
			initGame(scanerioNumber);

			autoGame = new AutoGame(this.graph);
			autoGame.buildRobotsPath(this.robots, this.fruits);

			game.startGame();
			threadListeningToKml();

			Thread thread = new Thread(){
				public void run(){
					while(game.isRunning()) {
						try {
							Thread.sleep(103);
							moveAutoRobots();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					System.out.println(game.toString());
				}
			};
			thread.start();
		}


		else if(str.equals("Game Info")) {
			String userId = JOptionPane.showInputDialog("Enter your ID please");
			int id = Integer.parseInt(userId);

			Database db = new Database(id);
			JOptionPane.showMessageDialog(null, "You played " + db.getNumberOfGames() + " game. \n"
					+ "Your level is " + db.getLevel());

		}

		else if(str.equals("Place")) {
			String userId = JOptionPane.showInputDialog("Enter your ID please");
			int id = Integer.parseInt(userId);

			Database db = new Database(id);
			String s = "";
			HashMap<Integer, Integer> map = db.classLocation();
			for (int i = 0; i < 12; i++) {
				if(i == 10) continue;
				s += "Level:" + db.getStage(i) + 
						" , Place: "  + map.get(db.getStage(i)) + "\n";
			}
			System.out.println(s);
			JOptionPane.showMessageDialog(null, s );

		}
	}



	/**
	 * Function that moves the robots automatically
	 */
	private void moveAutoRobots() {
		game.move();
		for(GraphRobot r : robots) {
			while(!r.getPath().isEmpty()) {
				game.chooseNextEdge(r.getId(), r.getPath().get(0).getKey());
				r.getPath().remove(0);
			}
			r.initRobot(game.getRobots().get(r.getId()));
			r.setOnTheWay(false);
		}	
		initFruitsList();
		autoGame.buildRobotsPath(this.robots, this.fruits);
		repaint();
	} 




	/**
	 * Function that listening to mouse pressed and find the mouse pressed location(Manual mode)
	 * 
	 * @param m - Mouse Event
	 */
	static int robotID = -1, nodeID = -1, clicked = 0;
	@Override
	public void mousePressed(MouseEvent m) {

		int x = m.getX();
		int y = m.getY();
		Point3D p = new Point3D(x, y);
		clicked ++;

		if (clicked == 2) {
			robotID = -1;
			nodeID = -1;
			GraphRobot r = manualGame.findRobotByLocation(p, this.robots);
			if (r != null) 
				robotID = r.getId();
		}
		else if(clicked == 4) {
			//If clicked not on robot
			if(robotID == -1 ) {
				clicked = 0;
				robotID = -1;
				nodeID = -1;
				return;
			}
			GraphRobot r = robots.get(robotID);
			node_data n = manualGame.findNodeByLocation(p, r.getSrc(), this.graph);
			if (n != null) 
				nodeID = n.getKey();
			clicked = 0;
		}
	}




	/**
	 * Function that move the robot to next node (Manual Mode)
	 * 
	 * @param m - Mouse Event
	 */
	private void moveManualRobot() throws InterruptedException {
		GraphRobot r = null;
		List<String> log= game.move();
		if(log!= null) {
			if(nodeID!= -1 && robotID != -1) {
				r = robots.get(robotID);
				if(r!= null) {
					game.chooseNextEdge(r.getId(), nodeID);
					Thread.sleep(300);
					game.move();
					initRobotList();
					repaint();

				}
			}
		}
		repaint();

		if(r!= null && r.getDest() == -1) {
			initFruitsList();
			manualGame.setFruitsGuiLocations(this.fruits);
			manualGame.setRobotGuiLocations(this.robots);

		}

	}



	private void clearGame() {
		this.graph = new DGraph();
		this.rx = new Range(Integer.MAX_VALUE,Integer.MIN_VALUE);
		this.ry = new Range(Integer.MAX_VALUE,Integer.MIN_VALUE);
		this.robots.clear();
		this.fruits.clear();
		autoGame = null;
		manualGame = null;
	}



	private void setRange() {
		Collection<node_data> c = graph.getV();
		Iterator<node_data> itrV = c.iterator();
		while(itrV.hasNext()) {
			node_data n = itrV.next();
			Point3D p = n.getLocation();
			double x = p.x();
			double y = p.y();
			if(x < rx.get_min()) rx.set_min(x);
			else if(x > rx.get_max()) rx.set_max(x);
			if(y < ry.get_min()) ry.set_min(y);
			else if(y > ry.get_max()) ry.set_max(y);
		}
	}



	private void threadListeningToKml() {
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				kml = new KML_Logger(graph);
				kml.BuildGraph();
				while(game.isRunning()) {
					if(graph != null) {
						long timeToSleep = 100;
						try {
							Thread.sleep(timeToSleep);
							String startTime  = java.time.LocalDate.now()+"T"+java.time.LocalTime.now();

							LocalTime test = LocalTime.now();
							test = test.plusNanos(timeToSleep*1000000);
							String endTime = java.time.LocalDate.now()+"T"+test;

							kml.setGame(game);
							kml.setFruits(startTime,endTime);
							kml.setRobots(startTime, endTime);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

				if(dialogKML() == 0) {
					String results = game.toString();
					g2d.setColor(Color.RED);
					g2d.drawString("Game Over: " + results, 200, 550);
					repaint();

					kml.saveToFile(gameNumber, game.toString());
					try {
						game.sendKML(kml.getKmlFile());
					} catch (IOException e) {
						System.out.println("didnt find kml file");
					}
				}

			}
		});
		t.start();
	}



	/**
	 * we let the user decide if he wants to save a kml file of his session
	 * @return
	 */
	public int dialogKML(){


		try {
			Object[] options = {"Yes", "No"};
			int x = JOptionPane.showOptionDialog(null, "Do you want to save this game as KML file?\n" 
					+ "" + "", "",
					JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

			if(x == -1)
				return 1;

			return x;

		}catch(Exception err) {
			return 1;
		}
	}


	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}




}