package datastructures;

import java.awt.Point;

public class BoundingBox {
	private Point topLeft;
	private Point bottomRight;
	private Point centroid;
	
	public BoundingBox(Point topLeft, Point bottomRight) {
		this.topLeft = topLeft;
		this.bottomRight = bottomRight;
		centroid = new Point((topLeft.x + bottomRight.x)/2, (topLeft.y + bottomRight.y)/2); 
	}
	
	public BoundingBox(BoundingBox boundingBox) {
		this.topLeft = new Point(boundingBox.topLeft);
		this.bottomRight = new Point(boundingBox.bottomRight);
	}
	
	public Point getCentroid() {
		return centroid;
	}
	
	public float getSurfaceArea() {
		return (bottomRight.x - topLeft.x) * (bottomRight.y - topLeft.y);
	}
	
	public void extend(BoundingBox other) {
		topLeft.x = Math.min(topLeft.x, other.topLeft.x);
		topLeft.y = Math.min(topLeft.y, other.topLeft.y);
		
		bottomRight.x = Math.max(bottomRight.x, other.bottomRight.x);
		bottomRight.y = Math.max(bottomRight.y, other.bottomRight.y);	
	}
	
	public boolean intersects(Point a, Point b) {
		assert(!a.equals(b)); // a != b, otherwise we do not have a line
		if (Math.max(a.x, b.x) < topLeft.x || bottomRight.x < Math.min(a.x, b.x)) return false;
		if (Math.max(a.y, b.y) < topLeft.y || bottomRight.y < Math.min(a.y, b.y)) return false;
		
		if (a.x == b.x) return topLeft.x <= a.x && a.x <= bottomRight.x && Math.max(a.y, b.y) >= topLeft.y && Math.min(a.y, b.y) <= bottomRight.y;
		if (a.y == b.y) return topLeft.y <= a.y && a.y <= bottomRight.y && Math.max(a.x, b.x) >= topLeft.x && Math.min(a.x, b.x) <= bottomRight.x;
		double m = (double) (b.y - a.y) / (double) (b.x - a.x);
		double c = a.y - m * a.x;
		
		int numLeft = 0;		
		int numRight = 0;
		
		double[] positions = {bottomRight.y - (m * bottomRight.x + c), bottomRight.y - (m * topLeft.x + c), 
												topLeft.y - (m * bottomRight.x + c), topLeft.y - (m * topLeft.x + c)}; 
		
		for (double p : positions) {
			if (p > 0) {
				numLeft++;
			} else if (p < 0) {
				numRight++;
			}
		}
		
		return numLeft < 4 && numRight < 4;
	}	
}
