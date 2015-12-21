package datastructures;

import java.awt.Point;

public class Ghost {
	
	public Ghost(Point position) {
		this.position = position;
	}

	private Point position;
	
	public void setPosition(Point position) {
		this.position = position;
	}
	
	public Point getPosition() {
		return position;
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
