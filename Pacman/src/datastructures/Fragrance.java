package datastructures;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

public class Fragrance {
	
	private List<Point> points;
	private double maxSmellRadius;
	
	public Fragrance(int numberOfPoints, Point initialPoint, double maxSmellRadius) {
		points = new LinkedList<>();
		for (int i = 0; i < numberOfPoints; i++) {
			points.add(initialPoint);
		}
	}
	
	public int getNumberOfPoints() {
		return points.size();
	}
	
	public Point getPoint(int index) {
		return points.get(index);
	}
	
	public double getSmellRadiusAtPoint(int index) {
		if (index < 0 || index >= points.size()) throw new IllegalArgumentException();
		return maxSmellRadius * index / (points.size() - 1);
	}
}
