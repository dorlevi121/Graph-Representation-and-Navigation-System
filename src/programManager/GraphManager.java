package programManager;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import algorithms.Graph_Algo;
import algorithms.graph_algorithms;
import dataStructure.DGraph;
import dataStructure.Node;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import utils.Point3D;

public final class GraphManager   {
	private graph graph;
	int mc;
	
	
	/**
	 * Constructor
	 * @param graph
	 */
	public GraphManager(graph g){
		this.graph = g;
		this.mc = g.getMC();
	}

	
	
	public GraphManager(){
		this.graph = new DGraph();
	}


		
	public void paint(Graphics d){
		if (graph != null) {
			//get nodes
			Collection<node_data> nodes = graph.getV();

			for (node_data n : nodes) {
				//draw nodes
				Point3D p = n.getLocation();
				d.setColor(Color.BLUE);
				d.fillOval(p.ix(), p.iy(), 11, 11);

				//draw nodes-key's
				d.setColor(Color.BLUE);
				d.drawString(""+n.getKey(), p.ix()-4, p.iy()-4);

				//check if there is edges
				if (graph.edgeSize()==0) { continue; }
				if ((graph.getE(n.getKey())!=null)) {
					//get edges
					Collection<edge_data> edges = graph.getE(n.getKey());
					for (edge_data e : edges) {
						//draw edges
						d.setColor(Color.RED);
						((Graphics2D) d).setStroke(new BasicStroke(2,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
						Point3D p2 = graph.getNode(e.getDest()).getLocation();
						d.drawLine(p.ix()+5, p.iy()+5, p2.ix()+5, p2.iy()+5);
						
						//draw direction x0*0.1+x1*0.9, y0*0.1+y1*0.9
						d.setColor(Color.YELLOW);
						d.fillOval((int)((p.ix()*0.7)+(0.3*p2.ix()))+2, (int)((p.iy()*0.7)+(0.3*p2.iy())), 9, 9);
						
						//draw weight
						d.setColor(Color.BLACK);
						String sss = ""+String.valueOf(e.getWeight());
						d.drawString(sss, 1+(int)((p.ix()*0.7)+(0.3*p2.ix())), (int)((p.iy()*0.7)+(0.3*p2.iy()))-2);
					}
				}	
			}
		}	
	}
	
	
	
	public void save() {
		Graph_Algo g =new Graph_Algo((DGraph)this.graph);		
		JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView());
		int userSelection1 = j.showSaveDialog(null);
		if (userSelection1 == JFileChooser.APPROVE_OPTION) {
			System.out.println("Saved as file - " + j.getSelectedFile().getAbsolutePath());
			g.save(j.getSelectedFile().getAbsolutePath());
			JOptionPane.showMessageDialog(null, "Saved graph in - " + j.getSelectedFile().getAbsolutePath());

		}
	}
	
	
	
	public void load() {
		Graph_Algo g =new Graph_Algo((DGraph)this.graph);		
		JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView());

		j = new JFileChooser(FileSystemView.getFileSystemView());
		j.setDialogTitle("Save graph to file..");

		int userSelection1 = j.showSaveDialog(null);
		if (userSelection1 == JFileChooser.APPROVE_OPTION) {
			g.save(j.getSelectedFile().getAbsolutePath());
			JOptionPane.showMessageDialog(null, "Init file from: " + j.getSelectedFile().getAbsolutePath());
		}

	}
	
	
	
	public void saveImg() {
		new Graph_Algo((DGraph)this.graph);		
		JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView());
		FileNameExtensionFilter filter = new FileNameExtensionFilter(" .png","png");
		j.setFileFilter(filter);

		int userSelection2 = j.showSaveDialog(null);
		if (userSelection2 == JFileChooser.APPROVE_OPTION) {
			try {
				BufferedImage i = new BufferedImage(1400, 1600 + 45, BufferedImage.TYPE_INT_RGB);
				Graphics d = i.getGraphics();
				paint(d);
				if (j.getSelectedFile().getName().endsWith(".png")) {
					ImageIO.write(i, "png", new File(j.getSelectedFile().getAbsolutePath()));
				}
				else {
					ImageIO.write(i, "png", new File(j.getSelectedFile().getAbsolutePath()+".png"));
				}
				System.out.println("Saved as png - " + j.getSelectedFile().getAbsolutePath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	public void isConnect() {
		Graph_Algo g =new Graph_Algo((DGraph)this.graph);		
		g.init(graph);
		boolean ans = g.isConnected();
		if(ans)
			JOptionPane.showMessageDialog(null,"The graph is connected", "isConnected", JOptionPane.QUESTION_MESSAGE);
		else
			JOptionPane.showMessageDialog(null, "The graph is not connected", "isConnected", JOptionPane.INFORMATION_MESSAGE);
	}
	
	

	public void addEdge(){
		
		String src =  JOptionPane.showInputDialog("Please input the src");
		String dst =  JOptionPane.showInputDialog("Please input the dest");
		String w =  JOptionPane.showInputDialog("Please input the wahit");
		try {
			this.graph.connect(Integer.parseInt(src), Integer.parseInt(dst), Integer.parseInt(w));
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	
	
	public void addVertex() {
		String numOfVertex=  JOptionPane.showInputDialog("Please input the vertex key");
		Node n = new Node(Integer.parseInt(numOfVertex));
		this.graph.addNode(n);
		this.setLocations(n);
	}
	
	
	
	public void SP() {
		Graph_Algo g =new Graph_Algo((DGraph)this.graph);		
		String src=  JOptionPane.showInputDialog("Please input the source vretex");
		String dst=  JOptionPane.showInputDialog("Please input the destination vertex");
		g.init(graph);
		String path = "";
		List <node_data> ans = g.shortestPath(Integer.parseInt(src),Integer.parseInt(dst));
		System.out.println(ans);
		if (ans == null){
			JOptionPane.showMessageDialog(null,"There is no path between the points :", "shortest path points "+src+"-"+dst, JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		for (int d=ans.size()-1; d>=0; d--)
			path += "->"+ ans.get(d).getKey();
		JOptionPane.showMessageDialog(null, path, "Shortest path", JOptionPane.INFORMATION_MESSAGE);

	}
	

	
	public void SPD() {
		String src=  JOptionPane.showInputDialog("Please input a starting point");
		String dst=  JOptionPane.showInputDialog("Please input a ending point");
		graph_algorithms g = new Graph_Algo();
		g.init(graph);
		double ans =g.shortestPathDist(Integer.parseInt(src),Integer.parseInt(dst));
		if(ans == Double.MAX_VALUE || ans == -1) {
			JOptionPane.showMessageDialog(null,"There is no path between the points :", "shortest path points "+src+"-"+dst, JOptionPane.ERROR_MESSAGE);
			return;
		}
		else 
			JOptionPane.showMessageDialog(null,"The shortest path dist is:\n "+ans,"shortest path points "+src+"-"+dst, JOptionPane.INFORMATION_MESSAGE);

	}
	
	
	
	public void TSP() {
		List <Integer> targets =new ArrayList<Integer>();
		graph_algorithms g = new Graph_Algo();
		g.init(graph);
		String input = JOptionPane.showInputDialog("How many points do you want? ");
		String s;
		for (int i = 0; i < Integer.parseInt(input); i++) {
			s = JOptionPane.showInputDialog("Enter vertex number " + i);
			if(Integer.parseInt(s) > this.graph.getV().size()) {
				JOptionPane.showMessageDialog(null,"There is no" + Integer.parseInt(s) + "vertices in the graph", "", JOptionPane.ERROR_MESSAGE);
				return;
			}
			targets.add(Integer.parseInt(s));
		}
		String path = "";
		List <node_data> ans = g.TSP(targets);
		for (int d=ans.size()-1; d>=0; d--)
			path += "->"+ ans.get(d).getKey();;
		JOptionPane.showMessageDialog(null,path, "",JOptionPane.INFORMATION_MESSAGE);

	}

	
	 
	private void setLocations(Node node) {
	        Random rand = new Random();
	            double x = rand.nextInt((int) (1400 / 1.5)) + 50;
	            double y = rand.nextInt((int) (600 / 1.5)) + 70;
	            Point3D p = new Point3D(x, y);
	            node.setLocation(p);
	        
	    }
	 
	
	
	public graph getGraph() {
		return graph;
	}


	
	public void setGraph(graph grp) {
		this.graph = grp;
	}
	
//	public static void main(String[] args) {
//		graph g=new DGraph();
////		Point3D p1 = new Point3D(100, 300);
////		Point3D p2 = new Point3D(0, 0);
////		Point3D p3 = new Point3D(5, 0);
////		Point3D p4 = new Point3D(5, 5);
//
//		Point3D p1 = new Point3D(100, 90);
//		Point3D p2 = new Point3D(203, 96);
//		Point3D p3 = new Point3D(154, 152);
//		Point3D p4 = new Point3D(455, 151);
//		Point3D p5 = new Point3D(687, 206);
//		Point3D p6 = new Point3D(500, 306);
//		Point3D p7 = new Point3D(230, 350);
//		Point3D p8 = new Point3D(290, 320);
//		Point3D p9 = new Point3D(550, 430);
//		Point3D p10 = new Point3D(600, 306);
//
//		Node n1 = new Node(p1, 0);
//		Node n2 = new Node(p2, 1);
//		Node n3 = new Node(p3, 2);
//		Node n4 = new Node(p4, 3);
//		Node n5 = new Node(p5, 4);
//		Node n6 = new Node(p6, 5);
//		Node n7 = new Node(p7, 6);
//		Node n8 = new Node(p8, 7);
//		Node n9 = new Node(p9, 8);
//		Node n10 = new Node(p10, 9);
//
//		
//		g.addNode(n1);
//		g.addNode(n2);
//		g.addNode(n3);
//		g.addNode(n4);
//		g.addNode(n5);
//		g.addNode(n6);
//		g.addNode(n7);
//		g.addNode(n8);
//		g.addNode(n9);
//		g.addNode(n10);
//
//		g.connect(0, 1, 1);
//		g.connect(0, 3, 3);
//		g.connect(1, 2, 1);
//		g.connect(2, 0, 2);
//		g.connect(3, 0, 3);
//		g.connect(4, 3, 3);
//		g.connect(5, 4, 3);
//		g.connect(6, 5, 3);
//		g.connect(7, 6, 3);
//		g.connect(8, 7, 3);
//		g.connect(9, 8, 3);
//		g.connect(9, 0, 3);
//		g.connect(7, 2, 3);
//		g.connect(2, 6, 3);
//		g.connect(9, 7, 3);
//
//
//
//		GraphGUI app = new GraphGUI(g);
//	}


}