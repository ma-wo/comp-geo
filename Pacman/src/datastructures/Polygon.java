package datastructures;

import java.awt.Point;
import java.util.List;

public class Polygon {
	private List<Point> points;	
	private BoundingBox boundingBox;
	
	public Polygon(List<Point> points) {
		assert(points.size() >= 3);
		this.points = points;
			
		int minX = points.get(0).x;
		int minY = points.get(0).y;
		int maxX = points.get(0).x;
		int maxY = points.get(0).y;
		
		for (Point p : points) {
			if (p.x < minX) {
				minX = p.x;
			} else if (p.x > maxX) {
				maxX = p.x;
			}
			
			if (p.y < minY) {
				minY = p.y;
			} else if (p.y > maxY) {
				maxY = p.y;
			}			
		}
		
		boundingBox = new BoundingBox(new Point(minX, minY), new Point(maxX, maxY));	
	}
	
	public int getNumberOfPoints() {
		return points.size();
	}
	
	public Point getPoint(int index) {
		assert(0 <= index && index < points.size());
		return points.get(index);
	}
	
	public Point getCentroid() {
		return boundingBox.getCentroid();
	}
	
	public BoundingBox getBoundingBox() {
		return boundingBox;
	}
	
	public boolean contains(Point p) {
		int numIntersections = 0;
		Point rightBorder = new Point(boundingBox.getBottomRight().x + 50, p.y);
		for (int i = 0; i < points.size(); ++i) {
			if (linesIntersect(i, (i+1) % points.size(), p, rightBorder)) {
				numIntersections++;
			}
		}
		
		return numIntersections % 2 == 1;
	}
	
	public boolean intersects(Point a, Point b) {
		assert(!a.equals(b)); // a != b, otherwise we do not have a line
		
		for (int i = 0; i < points.size(); ++i) { // TODO: can be reduced to log(n) 
			if (linesIntersect(i, (i+1) % points.size(), a, b)) return true;
		}
			
		return false;	
	}
	
	public String toString() {
		String result = "(";
		for (Point p : points) {
			result += p.toString() + ",";
		}
		result = result.substring(0, result.length()-2);
		result += ")";
		return result;
	}
	
	private boolean linesIntersect(int v0, int v1, Point a, Point b) {
		Point x0 = points.get(v0);
		Point x1 = points.get(v1);
		
		if (x0.x == x1.x) { // vertical border segment			
			return intersectVerticalSegment(x0, x1, a, b);
		} else if (a.x == b.x) {
			return intersectVerticalSegment(a, b, x0, x1);
		} else {
			double gradAB = (double) (b.y - a.y) / (double) (b.x - a.x);
			double yOffsetAB = a.y - gradAB * a.x;
			
			double gradX = (double) (x1.y - x0.y) / (double) (x1.x - x0.x);
			double yOffsetX = x0.y - gradX * x0.x;
			
			double t = (yOffsetX - yOffsetAB) / (gradAB - gradX);
			return Math.min(x0.x, x1.x) <= t && t <= Math.max(x0.x, x1.x) && Math.min(a.x, b.x) <= t && t <= Math.max(a.x, b.x);
		}
	}
	
	private boolean intersectVerticalSegment(Point vertical1, Point vertical2, Point a, Point b) {
		if (a.x == b.x) {
			int minY = Math.min(vertical1.y, vertical2.y);
			int maxY = Math.max(vertical1.y, vertical2.y);
			return a.x == vertical1.x && ((minY <= a.y && a.y <= maxY) || (minY <= b.y && b.y <= maxY));
		} else {
			double gradAB = (double) (b.y - a.y) / (double) (b.x - a.x);
			double yOffsetAB = a.y - gradAB * a.x;
			
			double y = gradAB * vertical1.x + yOffsetAB;
			return Math.min(a.x, b.x) <= vertical1.x && vertical1.x <= Math.max(a.x, b.x) && Math.min(vertical1.y, vertical2.y) <= y && y <= Math.max(vertical1.y, vertical2.y);
		}		
	}
	

}
