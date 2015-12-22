package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import datastructures.City;
import datastructures.DistanceFunction;
import datastructures.ExactDistanceFunction;
import datastructures.Fragrance;
import datastructures.Ghost;
import datastructures.Polygon;
import gui.MainWindow;
import io.OpenStreetMapImporter;

public class GameController extends JPanel {
	
	private static final long serialVersionUID = 859694576603588204L;

	private static final int DEFAULT_GHOST_COUNT = 20;

	private static final int GHOST_RADIUS = 5;
	
	private static final int PACMAN_RADIUS = 10;
	
	private static final int DEFAULT_WIDTH = 1024;
	
	private static final int PACMAN_SPEED = 5;
	
	private static final int PACMAN_DIAGONAL_SPEED = (int)(PACMAN_SPEED / Math.sqrt(2));
		
	private GameState state;
	
	private City city;
	
	private List<Ghost> ghostsSmellingPacman = Collections.emptyList();
	
	private Timer timer = new Timer(true);
	
	private int dx;
	
	private int dy;
	
	private DistanceFunction distFunc = new ExactDistanceFunction();
		
	public GameController(City city, int width, int height, int ghostCount) {
		this.city = city;
		this.state = new GameState(city, distFunc, ghostCount, width, height);
	}

	public void start() {
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				SwingUtilities.invokeLater(GameController.this::update);
			}
		}, 0, 40);
	}
	
	public void update() {
		// TODO: calculate new positions (currently the ghosts are static)
		List<Point> positions = state.getGhosts().stream().map(Ghost::getPosition).collect(Collectors.toList());
		Point oldPosition = state.getPacman().getPosition();
		Point pacmanPosition = new Point(oldPosition.x + dx, oldPosition.y + dy);
		
		ghostsSmellingPacman = state.update(positions, pacmanPosition);
		repaint();
	}
	
	public void paintComponent(Graphics g){
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.LIGHT_GRAY);		
		for (Polygon polygon : city) {
			java.awt.Polygon p = new java.awt.Polygon();
			for (int i = 0; i < polygon.getNumberOfPoints(); ++i) {
				p.addPoint(polygon.getPoint(i).x, polygon.getPoint(i).y);					
			}
			g.fillPolygon(p);
		}
		
		// TODO: exclude buildings? 
		g.setColor(new Color(128, 255, 255, 255));
		Fragrance f = state.getPacman().getFragrance();
		for (int i = 0; i < f.getNumberOfPoints(); i++) {
			int rad = (int) f.getSmellRadiusAtPoint(i);
			Point p = f.getPoint(i);
			g.fillOval(p.x - rad, p.y - rad, 2 * rad, 2 * rad);
		}
		
		
		g.setColor(Color.BLUE);
		for (Ghost ghost : state.getGhosts()) {
			Point ghostPos = ghost.getPosition();
			g.fillOval(ghostPos.x - GHOST_RADIUS, ghostPos.y - GHOST_RADIUS, 2 * GHOST_RADIUS, 2 * GHOST_RADIUS);
		}
		
		g.setColor(Color.RED);
		for (Ghost ghost : ghostsSmellingPacman) {
			Point ghostPos = ghost.getPosition();
			g.fillOval(ghostPos.x - GHOST_RADIUS, ghostPos.y - GHOST_RADIUS, 2 * GHOST_RADIUS, 2 * GHOST_RADIUS);
		}
		
		g.setColor(Color.ORANGE);
		Point pacmanPos = state.getPacman().getPosition();
		g.fillOval(pacmanPos.x - PACMAN_RADIUS, pacmanPos.y - PACMAN_RADIUS, 2 * PACMAN_RADIUS, 2 * PACMAN_RADIUS);

	}

	public static void main(String[] args) {
		OpenStreetMapImporter importer = new OpenStreetMapImporter();
		List<Polygon> polygons = importer.parseFile("input/map.osm.xml", DEFAULT_WIDTH);		
		City city = new City(polygons);
		
		int height = importer.getHeight(DEFAULT_WIDTH);
		GameController controller = new GameController(city, DEFAULT_WIDTH, height, DEFAULT_GHOST_COUNT);
		
		MainWindow mainWindow = new MainWindow(controller);		
		mainWindow.setSize(DEFAULT_WIDTH, height);
		mainWindow.setContentPane(controller);
		mainWindow.setVisible(true);
		controller.start();
	}
	
	private void normalizePacmanSpeed() {
		if (dx == 0) {
			dy = (int) Math.signum(dy) * PACMAN_SPEED;
		} else if (dy == 0) {
			dx = (int) Math.signum(dx) * PACMAN_SPEED;
		} else {
			dx = (int) Math.signum(dx) * PACMAN_DIAGONAL_SPEED;
			dy = (int) Math.signum(dy) * PACMAN_DIAGONAL_SPEED;
		}
	}
	
	public void keyPressed(int keyCode) {
		switch (keyCode) {
		case KeyEvent.VK_LEFT:
			dx = -PACMAN_SPEED;
			break;
		case KeyEvent.VK_RIGHT:
			dx = PACMAN_SPEED;
			break;
		case KeyEvent.VK_UP:
			dy = -PACMAN_SPEED;
			break;
		case KeyEvent.VK_DOWN:
			dy = PACMAN_SPEED;
			break;
		}
		normalizePacmanSpeed();
	}
	
	public void keyReleased(int keyCode) {
		switch (keyCode) {
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_RIGHT:
			dx = 0;
			break;
		case KeyEvent.VK_UP:
		case KeyEvent.VK_DOWN:
			dy = 0;
			break;
		}
		normalizePacmanSpeed();
	}

}
