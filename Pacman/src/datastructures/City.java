package datastructures;

import java.awt.Point;
import java.util.Iterator;
import java.util.List;

public class City implements Iterable<Polygon> {
	
	private List<Polygon> polygons;
	private BoundingVolumeHierarchy bvh;
	
	public City(List<Polygon> polygons) {
		this.polygons = polygons;
		this.bvh = new BoundingVolumeHierarchy(polygons);
	}
	
	public boolean intersectBuilding(Point a, Point b) {
		return bvh.intersects(a, b);
	}
	
	public boolean isInsideBuilding(Point p) {
		return bvh.insidePolygon(p);
	}

	@Override
	public Iterator<Polygon> iterator() {
		return polygons.iterator();
	}

}
