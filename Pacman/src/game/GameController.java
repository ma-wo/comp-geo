package game;

import io.OpenStreetMapImporter;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import datastructures.City;
import datastructures.Polygon;
import gui.MainWindow;

public class GameController extends JPanel {
	
	private GameState state;
	
	private City city;
	
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
	
	public GameController(City city) {
		this.city = city;
	}
	

	public static void main(String[] args) {
		// TODO generate city, ...
		OpenStreetMapImporter importer = new OpenStreetMapImporter();
		List<Polygon> polygons = importer.parseFile("input/map.osm.xml", 1024);		
		City city = new City(polygons);
		
		GameController controller = new GameController(city);
		
		MainWindow mainWindow = new MainWindow();		
		mainWindow.setSize(1024, importer.getHeight(1024));
		mainWindow.setContentPane(controller);
		
		
		mainWindow.setVisible(true);
	}
	
	
	public void paintComponent(Graphics g){
		g.setColor(Color.LIGHT_GRAY);		
		Iterator<Polygon> it = city.iterator();			
		while (it.hasNext()) {
			java.awt.Polygon p = new java.awt.Polygon();
			Polygon polygon = it.next();
			for (int i = 0; i < polygon.getNumberOfPoints(); ++i) {
				p.addPoint(polygon.getPoint(i).x, polygon.getPoint(i).y);					
			}
			g.fillPolygon(p);
		}

	}

}
