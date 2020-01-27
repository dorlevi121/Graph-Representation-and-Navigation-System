package gui;

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

import javax.swing.JFrame;

import programManager.DatabaseManager;
import programManager.GameManager;
import programManager.GraphManager;

public class MainGUI extends JFrame implements ActionListener, MouseListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GraphManager graphGui;
	private GameManager gameGui;
	private DatabaseManager db;
	private Graphics2D g2d;
	private boolean isGraphGui = false;


	public MainGUI() {
		initGUI();
		setVisible(true);
		gameGui = new GameManager();
		graphGui = new GraphManager();
		db = new DatabaseManager();
	}



	private void initGUI() {
		this.setSize(1400, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setTitle("Maze Of Waze");
		initMenu();
	}



	private void initMenu() {
		MenuBar menuBar = new MenuBar();
		this.setMenuBar(menuBar);

		////////// Graph Menu //////////

		Menu menu = new Menu("Graph");
		Menu algo = new Menu("Algorithems");
		menuBar.add(menu);
		menuBar.add(algo);

		MenuItem save = new MenuItem("Save File");
		save.addActionListener(this);
		menu.add(save);

		MenuItem img = new MenuItem("Save as a image");
		img.addActionListener(this);
		menu.add(img);

		MenuItem load = new MenuItem("Load File");
		load.addActionListener(this);
		menu.add(load);

		MenuItem addEdge = new MenuItem("Add Edge");
		addEdge.addActionListener(this);
		menu.add(addEdge);

		MenuItem addNode = new MenuItem("Add Node");
		addNode.addActionListener(this);
		menu.add(addNode);

		//Algorithms
		MenuItem isconnect = new MenuItem("isConnect");
		isconnect.addActionListener(this);
		algo.add(isconnect);


		MenuItem SP = new MenuItem("Shortest Path");
		SP.addActionListener(this);
		algo.add(SP);

		MenuItem SPD = new MenuItem("Shortest Path Length");
		SPD.addActionListener(this);
		algo.add(SPD);

		MenuItem TSP = new MenuItem("TSP");
		TSP.addActionListener(this);
		algo.add(TSP);


		////////// Game Menu //////////

		Menu op = new Menu("Game");
		menuBar.add(op);

		MenuItem manual = new MenuItem("Manual");
		manual.addActionListener(this);
		op.add(manual);

		MenuItem auto = new MenuItem("Auto");
		auto.addActionListener(this);
		op.add(auto);


		////////// Database Menu //////////

		Menu db = new Menu("DataBase");
		menuBar.add(db);

		MenuItem info = new MenuItem("Game Info");
		info.addActionListener(this);
		db.add(info);

		MenuItem place = new MenuItem("My Place");
		place.addActionListener(this);
		db.add(place);

		this.addMouseListener(this);

	}



	@Override
	public void paint(Graphics d) {
		BufferedImage bufferedImage = new BufferedImage(1400, 600, BufferedImage.TYPE_INT_ARGB);
		g2d = bufferedImage.createGraphics();
		super.paint(g2d);

		if(isGraphGui)
			graphGui.paint(g2d);
		
		Graphics2D g2dComponent = (Graphics2D) d;
		g2dComponent.drawImage(bufferedImage, null, 0, 0);

	}




	/**
	 * Listening to user choice from menu bar
	 * 
	 * @param event - event listener
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String str = e.getActionCommand();

		switch (str){
		case "Save File"     :graphGui.save();
		break;
		case "Load File"     :graphGui.load();;
		break;
		case "Add Edge"     :graphGui.addEdge();
		break;
		case "Add Node"		:graphGui.addVertex();
		break;
		case "Save as a image" :graphGui.saveImg();
		break;
		case "isConnect":graphGui.isConnect();
		break;
		case "Shortest Path"       :graphGui.SP();
		break;
		case "Shortest Path Length"      :graphGui.SPD();
		break;
		case "TSP"      :graphGui.TSP();
		break;
		case "Manual"	:gameGui.playManual();
		break;
		case "Auto"     :gameGui.playAuto();;
		break;
		case "Game Info" : db.gameInfo();
		break;
		case "My Place" : db.myLocationInTable();
		break;
		}

		if(str == "Add Node" || str == "Add Edge") isGraphGui = true;
		repaint();
	}



	public static void main(String[] args) {
		MainGUI t = new MainGUI();

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
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
