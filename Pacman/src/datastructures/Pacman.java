package datastructures;

import java.awt.Point;
import java.util.Objects;

public class Pacman {
	
	private Point position;
	private Fragrance fragrance;
	
	/**
	 * Creates Pacman at the given position.
	 * @param position Pacman's position
	 * @param fragrancePoints the number of points of the trajectory inside the fragrance
	 * @param maxSmellRadius the radius of the circle around Pacman in which ghost can smell him
	 */
	public Pacman(Point position, int fragrancePoints, int maxSmellRadius) {
		this.position = Objects.requireNonNull(position);
		this.fragrance = new Fragrance(fragrancePoints, position, maxSmellRadius);
	}
	
	/**
	 * Changes Pacman's position and updates the fragrance.
	 * @param newPosition Pacman's position
	 */
	public void updatePosition(Point newPosition) {
		position = newPosition;
		fragrance.addPoint(newPosition);
	}
	
	public Point getPosition() {
		return position;
	}
	
	public Fragrance getFragrance() {
		return fragrance;
	}

}
