package game;

import gui.MainWindow;

public class GameController {
	
	private GameState state;

	public static void main(String[] args) {
		// TODO generate city, ...
		
		MainWindow mainWindow = new MainWindow();
		mainWindow.setSize(600, 500);
		mainWindow.setVisible(true);
	}
}
