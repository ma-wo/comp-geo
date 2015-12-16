package datastructures;

import java.awt.Point;
import java.util.LinkedList;

public class Fragrance {
	
	private LinkedList<Point> points;
	private final double maxSmellRadius;
	
	public Fragrance(int numberOfPoints, Point initialPoint, double maxSmellRadius) {
		points = new LinkedList<>();
		for (int i = 0; i < numberOfPoints; i++) {
			points.add(initialPoint);
		}
		this.maxSmellRadius = maxSmellRadius;
	}
	
	/**
	 * Adds the point to the trajectory and removes the oldest point.
	 * @param p the point to add
	 */
	public void addPoint(Point p) {
		points.addLast(p);
		points.removeFirst();
	}
	
	public int getNumberOfPoints() {
		return points.size();
	}
	
	public Point getPoint(int index) {
		// this might be inefficient since it might traverse the whole list
		return points.get(index);
	}
	
	public double getSmellRadiusAtPoint(int index) {
		if (index < 0 || index >= points.size()) throw new IllegalArgumentException();
		return (maxSmellRadius * index) / (points.size() - 1);
	}
}
