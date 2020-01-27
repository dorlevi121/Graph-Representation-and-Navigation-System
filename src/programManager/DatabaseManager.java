package programManager;

import java.util.HashMap;

import javax.swing.JOptionPane;

import database.Database;

public class DatabaseManager {



	/**
	 * This function print on the screen the user place in every level
	 */
	public void myLocationInTable() {
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



	/**
	 * This function print on the screen the number of games that user played and user level.
	 */
	public void gameInfo() {
		String userId = JOptionPane.showInputDialog("Enter your ID please");
		int id = Integer.parseInt(userId);

		Database db = new Database(id);

		System.out.println("You played " + db.getNumberOfGames() + " game. \n"
				+ "Your level is " + db.getLevel());

		JOptionPane.showMessageDialog(null, "You played " + db.getNumberOfGames() + " game. \n"
				+ "Your level is " + db.getLevel());
	}




}
