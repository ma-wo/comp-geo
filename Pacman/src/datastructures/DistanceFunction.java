package datastructures;

import java.awt.Point;

@FunctionalInterface
public interface DistanceFunction {
	
	boolean isCloseEnough(Point p1, double radius1, Point p2, double radius2, Ghost ghost);

}
