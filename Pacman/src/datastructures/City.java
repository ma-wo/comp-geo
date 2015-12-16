package datastructures;

import java.awt.Point;
import java.util.Iterator;
import java.util.List;

public class City implements Iterable<Polygon> {
	
	private List<Polygon> polygons;
	private BoundingVolumeHierarchy bvh;
	
	public boolean intersectBuilding(Point a, Point b) {
		return bvh.intersects(a, b);
	}

	@Override
	public Iterator<Polygon> iterator() {
		return polygons.iterator();
	}

}
