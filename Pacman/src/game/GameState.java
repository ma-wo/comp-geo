package game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import datastructures.City;
import datastructures.DistanceFunction;
import datastructures.Fragrance;
import datastructures.Ghost;
import datastructures.Grid;
import datastructures.Pacman;

public class GameState {
	
	private static final int FRAGRANCE_POINTS = 30;
	
	private static final int SMELL_RADIUS = 50;
	
	private static final int CELL_LENGTH = SMELL_RADIUS;
	
	private City city;
	private List<Ghost> ghosts;
	private Pacman pacman;
	private Grid grid;
	private DistanceFunction distFunc;
	
	public GameState(City city, DistanceFunction distFunc, int ghostCount, int width, int height) {
		this.city = city;
		this.distFunc = distFunc;
		ghosts = new ArrayList<>(ghostCount);
		for (int i = 0; i < ghostCount; i++) {
			ghosts.add(new Ghost(new Point()));
		}
		pacman = new Pacman(new Point(), FRAGRANCE_POINTS, SMELL_RADIUS);
		grid = new Grid(width, height, CELL_LENGTH, ghosts);
	}
	
	/**
	 * Updates the positions of ghosts and Pacman and evaluates which ghosts can smell Pacman
	 * @param ghostPositions
	 * @param pacmanPosition
	 * @return list of ghosts smelling Pacman
	 */
	public List<Ghost> update(List<Point> ghostPositions, Point pacmanPosition) {
		grid.update(ghostPositions);
		for (int i = 0; i < ghosts.size(); ++i) {
			ghosts.get(i).setPosition(ghostPositions.get(i));
		}
		
		pacman.updatePosition(pacmanPosition);
		
		Set<Ghost> ghostsSmellingPacman = new HashSet<Ghost>();
		Fragrance fragrance = pacman.getFragrance();
		for (int i = 0; i < fragrance.getNumberOfPoints()-1; ++i) {
			for (Ghost ghost : grid.getGhosts(fragrance.getPoint(i), fragrance.getPoint(i+1))) {
				if (!ghostsSmellingPacman.contains(ghost) && distFunc.isCloseEnough(fragrance, i, ghost)) {
					Point pointOnSegment = distFunc.getPointOnSegment(fragrance, i, ghost);
					if (!city.intersectBuilding(ghost.getPosition(), pointOnSegment)) {
						ghostsSmellingPacman.add(ghost);
					}
				}
			}			
		}
		
		return new LinkedList<Ghost>(ghostsSmellingPacman);
	}

	public Pacman getPacman() {
		return pacman;
	}
	
	public List<Ghost> getGhosts() {
		return Collections.unmodifiableList(ghosts);
	}
	

}
