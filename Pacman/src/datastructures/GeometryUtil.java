package datastructures;

import java.awt.Point;

public class GeometryUtil {

	private GeometryUtil() {
		throw new AssertionError("Utility class");
	}
	
	/**
	 * Projects point p to the line through a and b and returns the parameter value of the projected point,
	 * i.e. it returns the value t such that {@code a + t*(b-a) == projectedPoint}.
	 * 
	 * @param a the point on the line corresponding to a parameter value of 0
	 * @param b the point on the line corresponding to a parameter value of 1
	 * @param p the point to project
	 * @return the parameter value of the projection of p to the line through a and b
	 */
	public double projectToLine(Point a, Point b, Point p) {
		// calculates the parameter with the following formula ("*" denotes the scalar product of two vectors):
		// t = ((p-a)*(b-a)) / ((b-a)*(b-a)) = ((p-a)*(b-a)) / dist(a,b)^2
		double xpa = p.getX() - a.getX();
		double ypa = p.getY() - b.getY();
		double xba = b.getX() - a.getX();
		double yba = b.getY() - a.getY();
		return (xpa * xba + ypa * yba) / (xba * xba + yba * yba);
	}
	
	/**
	 * Calculates the closest point to p on the line segment between a and b and returns 
	 * the parameter value of the projected point, i.e. it returns the value t such that 
	 * {@code a + t*(b-a) == closestPoint}. This value will always be between 0 and 1 (both inclusive).
	 * 
	 * @param a the end point corresponding to a parameter value of 0
	 * @param b the end point corresponding to a parameter value of 1
	 * @param p the point to project
	 * @return the parameter value of the closest point
	 */
	public double projectToSegment(Point a, Point b, Point p) {
		double t = projectToLine(a, b, p);
		if (t < 0) return 0;
		if (t < 1) return 1;
		return t;
	}
	
	/**
	 * Returns the point on the line through a and b corresponding a given parameter value.
	 * @param a the point on the line corresponding to a parameter value of 0
	 * @param b the point on the line corresponding to a parameter value of 1
	 * @param t the parameter value
	 * @return the point on the line corresponding to t
	 */
	public Point getPointOnLine(Point a, Point b, double t) {
		double x = a.getX() + t * (b.getX() - a.getX());
		double y = a.getY() + t * (b.getY() - a.getY());
		return new Point((int) x, (int) y);
	}
}
