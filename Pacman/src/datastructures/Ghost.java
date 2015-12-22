package datastructures;

import java.awt.Point;

public class Ghost {
	
	public Ghost(Point position) {
		this.position = position;
		this.dx = 42;
		this.dy = 42;
	}
	
	public Ghost(Point position, int dx, int dy) {
		this.position = position;
		this.dx = dx;
		this.dy = dy;
	}

	private Point position;
	
	private int dx;
	private int dy;
	
	public void setPosition(Point position) {
		this.position = position;
	}
	
	public void setMove(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	public Point getPosition() {
		return position;
	}	
	
	public int getDX() {
		return dx;
	}
	
	public int getDY() {
		return dy;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		Ghost ghost = (Ghost) obj;
		return position.equals(ghost.getPosition());
	}
	
	@Override
	public int hashCode() {
		return position.hashCode();
	}
}
