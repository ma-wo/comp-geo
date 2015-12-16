package datastructures;

import java.awt.Point;

public class Ghost {
	
	public Ghost(Point position) {
		this.position = position;
	}

	private Point position;
	
	public Point getPosition() {
		return position;
	}
}
