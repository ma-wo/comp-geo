package datastructures;

import java.awt.Point;

public class ApproximateDistanceFunction implements DistanceFunction {

	@Override
	public boolean isCloseEnough(Fragrance fragrance, int index, Ghost ghost) {
		if (index < 0 || index > fragrance.getNumberOfPoints() - 2) {
			throw new IndexOutOfBoundsException();
		}
		Point first = fragrance.getPoint(index);
		Point second = fragrance.getPoint(index + 1);
		
		if (first.equals(second)) {
			return ghost.getPosition().distance(second) < fragrance.getSmellRadiusAtPoint(index + 1);
		}
		
		double t = GeometryUtil.projectToSegment(first, second, ghost.getPosition());
		Point projection = GeometryUtil.getPointOnLine(first, second, t);
		double distance = projection.distance(ghost.getPosition());
		double smellRadius = (1 - t) * fragrance.getSmellRadiusAtPoint(index) 
				+ t * fragrance.getSmellRadiusAtPoint(index + 1);
		
		if (distance > smellRadius) return false;
		if (0 < t && t < 1) return true;
		if (t == 0 && (index == 0 || !fragrance.hasPositiveLength(index - 1))) return true; // first line segment
		if (t == 1 && (index == fragrance.getNumberOfPoints() - 2 || !fragrance.hasPositiveLength(index + 1))) return true; // last line segment
		
		// checks the neighboring line segment: If the closest point to the neighbor is the common point,
		// p has to be reported.
		if (t == 0) {
			double tprev = GeometryUtil.projectToSegment(fragrance.getPoint(index - 1), first, ghost.getPosition());
			return tprev == 1;
		} else {
			double tnext = GeometryUtil.projectToSegment(second, fragrance.getPoint(index + 2), ghost.getPosition());
			return tnext == 0;
		}
	}
}
