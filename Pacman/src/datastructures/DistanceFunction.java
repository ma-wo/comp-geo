package datastructures;

@FunctionalInterface
public interface DistanceFunction {
	
	/**
	 * Calculates whether a ghost is close enough to a trajectory edge to be inside the fragrance
	 * @param fragrance Pacman's fragrance
	 * @param lineSegmentIndex the smaller index of the two end points of the line segment
	 * @param ghost the ghost
	 * @return whether the ghost is close enough to be in the fragrance
	 */
	boolean isCloseEnough(Fragrance fragrance, int lineSegmentIndex, Ghost ghost);

}
