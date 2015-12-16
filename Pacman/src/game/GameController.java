package game;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.SwingUtilities;

import gui.MainWindow;

public class GameController {
	
	private GameState state;
	
	private Timer timer = new Timer(true);
	
	public void start() {
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				update();
			}
		}, 0, 40);
	}
	
	public void update() {
		// calculate new positions
		// state.update(...);
		// update GUI
		TimerTask t;
	}
	
	
	

	public static void main(String[] args) {
		// TODO generate city, ...
		
		MainWindow mainWindow = new MainWindow();
		mainWindow.setSize(600, 500);
		mainWindow.setVisible(true);
	}
}
