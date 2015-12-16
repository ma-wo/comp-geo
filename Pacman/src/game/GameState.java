package game;

import java.awt.Point;
import java.util.Collections;
import java.util.List;

import datastructures.City;
import datastructures.DistanceFunction;
import datastructures.Ghost;
import datastructures.Grid;
import datastructures.Pacman;

public class GameState {
	
	private City city;
	private List<Ghost> ghosts;
	private Pacman pacman;
	private Grid grid;
	private DistanceFunction distFunc;
	
	public GameState(City city, DistanceFunction distFunc) {
		this.city = city;
		this.distFunc = distFunc;
	}
	
	/**
	 * Updates the positions of ghosts and Pacman and evaluates which ghosts can smell Pacman
	 * @param ghostPositions
	 * @param pacmanPosition
	 * @return list of ghosts smelling Pacman
	 */
	public List<Ghost> update(List<Point> ghostPositions, Point pacmanPosition) {
		// TODO!
		return Collections.emptyList();
	}

	

}
