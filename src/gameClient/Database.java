package gameClient;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import dataStructure.node_data;


/**
 * This class connect to the database and get information 
 * 
 */
public class Database {

	public static final String jdbcUrl="jdbc:mysql://db-mysql-ams3-67328-do-user-4468260-0.db.ondigitalocean.com:25060/oop?useUnicode=yes&characterEncoding=UTF-8&useSSL=false";
	public static final String jdbcUser="student";
	public static final String jdbcUserPassword="OOP2020student";

	private int id;

	public Database(int id) {
		this.id = id;
	}


	/**
	 * Function that return the number of game
	 * @return m - number of game that user played
	 */
	public int getNumberOfGames() {
		int numberOfGames = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection =  DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcUserPassword);
			Statement statement = connection.createStatement();
			String allCustomersQuery = "SELECT * FROM Logs WHERE UserID="+this.id+";";
			ResultSet resultSet = statement.executeQuery(allCustomersQuery);

			while(resultSet.next())
				numberOfGames++;

			resultSet.close();
			statement.close();		
			connection.close();		
		}

		catch (SQLException sqle) {
			System.out.println("SQLException: " + sqle.getMessage());
			System.out.println("Vendor Error: " + sqle.getErrorCode());
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return numberOfGames;
	}


	/**
	 * Function that return the level of user
	 * @return m - User level
	 */
	public int getLevel() {
		int level = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection =  DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcUserPassword);
			Statement statement = connection.createStatement();
			String allCustomersQuery = "SELECT * FROM Logs WHERE UserID="+this.id+";";
			ResultSet resultSet = statement.executeQuery(allCustomersQuery);

			while(resultSet.next()) {
				int curLevel = resultSet.getInt("levelID");
				if(curLevel > level) level = curLevel;
			}

			resultSet.close();
			statement.close();		
			connection.close();		
		}

		catch (SQLException sqle) {
			System.out.println("SQLException: " + sqle.getMessage());
			System.out.println("Vendor Error: " + sqle.getErrorCode());
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return level;
	}


	/**
	 * Function that return the best score game of any level
	 * @return m - HashMap that the key is the level and the value is score
	 */
	public HashMap<Integer, int[]> bestGame(){
		HashMap<Integer, int[]> best = new HashMap<>();
		for (int i = 0; i < 12; i++) {
			int [] arr  = {0,Integer.MIN_VALUE};
			best.put(i, arr );
		}

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection =  DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcUserPassword);
			Statement statement = connection.createStatement();
			String allCustomersQuery = "SELECT * FROM Logs WHERE UserID="+this.id+";";
			ResultSet resultSet = statement.executeQuery(allCustomersQuery);

			while(resultSet.next()) {
				int curLevel = resultSet.getInt("levelID");
				int moves = resultSet.getInt("moves");
				int points = resultSet.getInt("score");
				if(moves <= getNumberOfMoves(curLevel)) {
					if(best.get(curLevel)[1] < points) {
						best.get(curLevel)[0] = points;
						best.get(curLevel)[1] = moves;
					}
				}
			}

			resultSet.close();
			statement.close();		
			connection.close();		
		}

		catch (SQLException sqle) {
			System.out.println("SQLException: " + sqle.getMessage());
			System.out.println("Vendor Error: " + sqle.getErrorCode());
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return best;

	}


	/**
	 * Function that return the user's location relative to the rest of the class
	 * @return m - HashMap that the key is the level and the value is place
	 */
	public HashMap<Integer, Integer> classLocation(){
		HashMap<Integer, Integer> location = new HashMap<>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = 
					DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcUserPassword);
			Statement statement = connection.createStatement();

			HashMap<Integer, Integer> temp;
			for (int i = 0; i < 12; i++) {
				if(i == 10) continue;
				temp = new HashMap<>();
				String allCustomersQuery = "SELECT * FROM Logs WHERE levelID=" + getStage(i) + ";";
				ResultSet resultSet = statement.executeQuery(allCustomersQuery);

				while(resultSet.next()) {
					temp.put(resultSet.getInt("UserID"), resultSet.getInt("score"));
				}
				HashMap<Integer, Integer> temp1 = sortByValue(temp);

				int place = -1 , k = 0;
				for(Integer key : temp1.keySet()) {
					if(key == id) {
						place = k;
						break;
					}
					k++;
				}

				location.put(getStage(i), place);
			}
			statement.close();		
			connection.close();		
		}

		catch (SQLException sqle) {
			System.out.println("SQLException: " + sqle.getMessage());
			System.out.println("Vendor Error: " + sqle.getErrorCode());
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return location;
	}



	private static HashMap<Integer, Integer> sortByValue(HashMap<Integer, Integer> hm) { 
		// Create a list from elements of HashMap 
		List<Map.Entry<Integer, Integer> > list = 
				new LinkedList<Map.Entry<Integer, Integer> >(hm.entrySet()); 

		// Sort the list 
		Collections.sort(list, new Comparator<Map.Entry<Integer, Integer> >() { 
			public int compare(Map.Entry<Integer, Integer> o1,  
					Map.Entry<Integer, Integer> o2) 
			{ 
				return (o1.getValue()).compareTo(o2.getValue()); 
			} 
		}); 

		// put data from sorted list to hashmap  
		HashMap<Integer, Integer> temp = new LinkedHashMap<Integer, Integer>(); 
		for (Map.Entry<Integer, Integer> aa : list) { 
			temp.put(aa.getKey(), aa.getValue()); 
		} 
		return temp; 
	} 




	public static int getNumberOfMoves(int num) {
		switch(num) {
		case 0:
			return 290;
		case 1:
			return 580;
		case 3:
			return 580;
		case 5:
			return 500;
		case 9:
			return 580;
		case 11:
			return 580;
		case 13:
			return 580;
		case 16:
			return 290;
		case 19:
			return 580;
		case 20: 
			return 290;
		case 23:
			return 1140;
		default:
			return 10;
		}
	}



	public static int getStage(int index) {
		switch (index) {
		case 0 : 
			return 0;
		case 1 : 
			return 1;
		case 2 : 
			return 3;
		case 3 : 
			return 5;
		case 4 : 
			return 9;
		case 5 : 
			return 11;
		case 6 : 
			return 13;
		case 7 : 
			return 16;
		case 8 : 
			return 19;
		case 9 : 
			return 20;
		case 11 : 
			return 23;
		default :
			return -1;
		}
	}



}
