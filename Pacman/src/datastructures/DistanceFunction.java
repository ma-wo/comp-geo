package datastructures;

import java.awt.Point;

public interface DistanceFunction {
	
	/**
	 * Calculates whether a ghost is close enough to a trajectory edge to be inside the fragrance
	 * @param fragrance Pacman's fragrance
	 * @param lineSegmentIndex the smaller index of the two end points of the trajectory edge
	 * @param ghost the ghost
	 * @return whether the ghost is close enough to be in the fragrance
	 */
	boolean isCloseEnough(Fragrance fragrance, int lineSegmentIndex, Ghost ghost);
	
	/**
	 * Returns the point on the trajectory edge that "can be smelled" by the ghost.
	 * @param fragrance Pacman's fragrance
	 * @param lineSegmentIndex the smallest index of the two end points of the trajectory edge
	 * @param ghost the ghost
	 * @return the witness point
	 */
	Point getPointOnSegment(Fragrance fragrance, int lineSegmentIndex, Ghost ghost);

}
