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
		double firstSmellRadius = fragrance.getSmellRadiusAtPoint(index);
		double secondSmellRadius = fragrance.getSmellRadiusAtPoint(index + 1);
		
		double angle = Math.atan((secondSmellRadius - firstSmellRadius) / dist);
		double t = GeometryUtil.projectToLine(first, second, ghost.getPosition());
		
		double smallThreshold = - firstSmellRadius / dist * Math.sin(angle); 
		double largeThreshold = 1.0 - secondSmellRadius / dist * Math.sin(angle);
		
		if (t <= smallThreshold) {
			return first.distance(ghost.getPosition()) <= firstSmellRadius;
		} else if (t >= largeThreshold) {
			return second.distance(ghost.getPosition()) <= secondSmellRadius;
		} else {
			Point projection = GeometryUtil.getPointOnLine(first, second, t);
			double smellRadiusAtProjection = ((1 - t) * fragrance.getSmellRadiusAtPoint(index) 
					+ t * fragrance.getSmellRadiusAtPoint(index + 1)) / Math.cos(angle);
			return projection.distance(ghost.getPosition()) <= smellRadiusAtProjection;
		}
	}

}
