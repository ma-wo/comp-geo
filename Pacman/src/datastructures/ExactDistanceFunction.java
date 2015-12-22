package datastructures;

import java.awt.Point;

public class ExactDistanceFunction implements DistanceFunction {

	@Override
	public boolean isCloseEnough(Fragrance fragrance, int index, Ghost ghost) {
		Point first = fragrance.getPoint(index);
		Point second = fragrance.getPoint(index + 1);
		
		if (first.equals(second)) {
			return ghost.getPosition().distance(second) < fragrance.getSmellRadiusAtPoint(index + 1);
		}
		
		double dist = first.distance(second);
		double smallSmellRadius = fragrance.getSmellRadiusAtPoint(index);
		double largeSmellRadius = fragrance.getSmellRadiusAtPoint(index + 1);
		
		double angle = Math.asin((largeSmellRadius - smallSmellRadius) / dist);
		double t = GeometryUtil.projectToLine(first, second, ghost.getPosition());
		
		double smallThreshold = - smallSmellRadius / dist * Math.sin(angle); 
		double largeThreshold = 1.0 - largeSmellRadius / dist * Math.sin(angle);
		
		if (t <= smallThreshold) {
			return first.distance(ghost.getPosition()) <= smallSmellRadius;
		} else if (t >= largeThreshold) {
			return second.distance(ghost.getPosition()) <= largeSmellRadius;
		} else {
			Point projection = GeometryUtil.getPointOnLine(first, second, t);
			double smellRadiusAtProjection = ((1 - t) * fragrance.getSmellRadiusAtPoint(index) 
					+ t * fragrance.getSmellRadiusAtPoint(index + 1)) / Math.cos(angle);
			return projection.distance(ghost.getPosition()) <= smellRadiusAtProjection;
		}
	}
	
	@Override
	public Point getPointOnSegment(Fragrance fragrance, int index, Ghost ghost) {
		// TODO: This returns the closest point on the line segment which might not be appropriate here.
		if (index < 0 || index > fragrance.getNumberOfPoints() - 2) {
			throw new IndexOutOfBoundsException();
		}
		Point first = fragrance.getPoint(index);
		Point second = fragrance.getPoint(index + 1);
		
		if (first.equals(second)) {
			return first;
		}
		double t = GeometryUtil.projectToSegment(first, second, ghost.getPosition());
		return GeometryUtil.getPointOnLine(first, second, t);
	}

}
