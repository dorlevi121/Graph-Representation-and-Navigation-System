package gameClient;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

import org.json.JSONObject;

import Server.game_service;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.ExtendedData;
import de.micromata.opengis.kml.v_2_2_0.Feature;
import de.micromata.opengis.kml.v_2_2_0.Folder;
import de.micromata.opengis.kml.v_2_2_0.Icon;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Style;
import utils.Point3D;

public class KML_Logger {

	private game_service game;
	private graph graph;
	private Kml kml;
	private Document doc;
	private String kmlPath;

	public KML_Logger(graph g) {
		this.graph = g;
	}



	public void setGame(game_service game){
		this.game = game;
	}



	public void BuildGraph(){
		kml = new Kml();
		doc = kml.createAndSetDocument().withName("KML").withOpen(true);
		Folder folder = doc.createAndAddFolder();
		folder.withName("Folder").withOpen(true);

		//Set icon location
		Icon icon = new Icon().withHref("http://maps.google.com/mapfiles/kml/paddle/ylw-blank.png");
		Style placeMarkStyle = doc.createAndAddStyle();
		placeMarkStyle.withId("placemarkid").createAndSetIconStyle().withIcon(icon).withScale(1);

		Collection<node_data> nodes = graph.getV();
		for (node_data node : nodes) {
			Placemark p = doc.createAndAddPlacemark();
			p.createAndSetPoint().addToCoordinates(node.getLocation().x(), node.getLocation().y());
			p.withStyleUrl("#placemarkid");
			p.withName(node.getKey() + ""); //node id

			//Set edge style
			Style edgeStyle= doc.createAndAddStyle();
			edgeStyle.withId("edgestyle").createAndSetLineStyle().withColor("ff0000ff").setWidth(3.0);;

			Collection<edge_data> ed = graph.getE(node.getKey());
			for (edge_data edgess : ed) {
				Placemark p2 = doc.createAndAddPlacemark();
				p2.withStyleUrl("#edgestyle");

				Point3D loc  =graph.getNode(edgess.getSrc()).getLocation();
				Point3D locNext = graph.getNode(edgess.getDest()).getLocation();

				p2.createAndSetLineString().withTessellate(true).addToCoordinates(loc.x(),loc.y()).addToCoordinates(locNext.x(),locNext.y());
			}
		}
	}



	public void setFruits(String time , String end){
		//Set green icon
		Icon iconGreen = new Icon().withHref("http://maps.google.com/mapfiles/kml/pal3/icon53.png");
		Style greenStyle = doc.createAndAddStyle();
		greenStyle.withId("greenI").createAndSetIconStyle().withIcon(iconGreen).withScale(0.7);

		//Set yellow icon
		Icon iconYellow = new Icon().withHref("http://maps.google.com/mapfiles/kml/pal3/icon49.png");
		Style yelloStyle = doc.createAndAddStyle();
		yelloStyle.withId("yellowI").createAndSetIconStyle().withIcon(iconYellow).withScale(0.7);


		List<String> frus = game.getFruits();
		for (String json : frus) {
			try {
				JSONObject obj = new JSONObject(json);
				JSONObject CurrFruit = (JSONObject) obj.get("Fruit");
				String pos = CurrFruit.getString("pos");
				String[] arr = pos.split(",");
				double x = Double.parseDouble(arr[0]);
				double y = Double.parseDouble(arr[1]);
				double z = Double.parseDouble(arr[2]);
				Point3D p = new Point3D(x, y, z);
				int type = CurrFruit.getInt("type");

				Placemark fr = doc.createAndAddPlacemark();
				if(type == -1) fr.setStyleUrl("#greenI");
				else fr.setStyleUrl("#yellowI");

				fr.createAndSetPoint().addToCoordinates(x, y);
				fr.createAndSetTimeSpan().withBegin(time).withEnd(end);
			}

			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}



	public void setRobots(String time , String end){
		//Set robot icon
		Icon BusIcon = new Icon().withHref("http://maps.google.com/mapfiles/kml/shapes/man.png");
		Style busStyle = doc.createAndAddStyle();
		busStyle.withId("Bus").createAndSetIconStyle().withIcon(BusIcon).withScale(0.7);

		List<String> robos = game.getRobots();
		for (String string : robos) {
			try {
				JSONObject obj = new JSONObject(string);
				JSONObject CurrBot = (JSONObject) obj.get("Robot");
				String pos = CurrBot.getString("pos");
				String[] arr = pos.split(",");
				double x = Double.parseDouble(arr[0]);
				double y = Double.parseDouble(arr[1]);
				double z = Double.parseDouble(arr[2]);
				Point3D posP = new Point3D(x, y, z);
				int id = CurrBot.getInt("id");
				Placemark bot = doc.createAndAddPlacemark();
				bot.setStyleUrl("#Bus");
				bot.createAndSetPoint().addToCoordinates(x, y);
				//bot.createAndSetTimeStamp().withWhen(time);
				bot.createAndSetTimeSpan().withBegin(time).withEnd(end);
			}
			catch (Exception e) {
				e.printStackTrace();
			}	
		}
	}



	public void saveToFile(int gameNumber , String gameInfo){
		try {
			File file = new File("data/"+gameNumber+".kml");
			kmlPath = file.getPath();

			JSONObject obj = new JSONObject(gameInfo);
			JSONObject results = (JSONObject) obj.get("GameServer");
			int grade = results.getInt("grade");
			int moves = results.getInt("moves");
			ExtendedData ed = doc.createAndSetExtendedData();
			ed.createAndAddData(grade+"").setName("grade");
			ed.createAndAddData(moves+"").setName("moves");
			kml.marshal(file);
		}
		catch (Exception e) {
			e.printStackTrace();		}
		System.out.println("Created KML file");

	}



	public String getKmlFile() throws IOException {
		File file = new File(kmlPath);
		StringBuilder fileContents = new StringBuilder((int)file.length());        

		try (Scanner scanner = new Scanner(file)) {
			while(scanner.hasNextLine()) 
				fileContents.append(scanner.nextLine() + System.lineSeparator());

			return fileContents.toString();
		}
	}
}