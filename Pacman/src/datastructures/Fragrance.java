package datastructures;

import java.awt.Point;
import java.util.LinkedList;

public class Fragrance {
	
	private LinkedList<Point> points;
	private final double maxSmellRadius;
	
	public Fragrance(int numberOfPoints, Point initialPoint, double maxSmellRadius) {
		if (initialPoint == null) {
			throw new NullPointerException();
		}
		if (numberOfPoints <= 0 || maxSmellRadius < 0) {
			throw new IllegalArgumentException();
		}
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
	
	/**
	 * Returns the radius of the fragrance circle at the point with given index.
	 * @param index the index of the point
	 * @return the smell radius
	 */
	public double getSmellRadiusAtPoint(int index) {
		if (index < 0 || index >= points.size()) throw new IndexOutOfBoundsException();
		return (maxSmellRadius * index) / (points.size() - 1);
	}
	
	/**
	 * Returns whether the line segment between the points at {@code index} and {@code index + 1} has
	 * positive length, i.e. these two points are different. 
	 * @param index the index of the line segment
	 * @return whether the line segment has a positive length.
	 * @throws IndexOutOfBoundsException if the index is negative or greater than {@code getNumberOfPoints() - 2}
	 */
	public boolean hasPositiveLength(int index) {
		return !getPoint(index).equals(getPoint(index + 1));
	}
}
