package datastructures;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class BoundingVolumeHierarchyTest {
	@Test
	public void testBVHQuery() {
		List<Polygon> buildings = new ArrayList<Polygon>();
		
		List<Point> points = new ArrayList<Point>();
		points.add(new Point(10, 20));
		points.add(new Point(10, 30));
		points.add(new Point(30, 30));
		points.add(new Point(30, 20));		
		buildings.add(new Polygon(points));
		
		points = new ArrayList<Point>();
		points.add(new Point(40, 20));
		points.add(new Point(40, 30));
		points.add(new Point(60, 30));
		points.add(new Point(60, 20));
		buildings.add(new Polygon(points));
		
		points = new ArrayList<Point>();
		points.add(new Point(70, 20));
		points.add(new Point(70, 30));
		points.add(new Point(90, 30));
		points.add(new Point(90, 20));
		buildings.add(new Polygon(points));
		
		points = new ArrayList<Point>();
		points.add(new Point(10, 50));
		points.add(new Point(10, 70));
		points.add(new Point(20, 70));
		points.add(new Point(20, 50));
		buildings.add(new Polygon(points));
		
		points = new ArrayList<Point>();
		points.add(new Point(10, 90));
		points.add(new Point(10, 110));
		points.add(new Point(20, 110));
		points.add(new Point(20, 90));
		buildings.add(new Polygon(points));
		
		points = new ArrayList<Point>();
		points.add(new Point(60, 90));
		points.add(new Point(60, 110));
		points.add(new Point(100, 110));
		points.add(new Point(100, 70));
		points.add(new Point(90, 70));
		buildings.add(new Polygon(points));
		
		BoundingVolumeHierarchy bvh = new BoundingVolumeHierarchy(buildings);
		
		assertFalse(bvh.intersects(new Point(30, 90), new Point(50, 60)));		
		assertFalse(bvh.intersects(new Point(70, 50), new Point(70, 70)));
		assertFalse(bvh.intersects(new Point(30, 90), new Point(50, 90)));
		assertFalse(bvh.intersects(new Point(25, 55), new Point(30, 65)));
		assertFalse(bvh.intersects(new Point(65, 15), new Point(65, 35)));
		assertFalse(bvh.intersects(new Point(30, 10), new Point(50, 10)));
		assertFalse(bvh.intersects(new Point(100, 150), new Point(100, 200)));
		assertFalse(bvh.intersects(new Point(100, 150), new Point(150, 200)));
		assertFalse(bvh.intersects(new Point(15, 95), new Point(15, 105)));
		assertFalse(bvh.intersects(new Point(15, 25), new Point(25, 25)));
		
		assertTrue(bvh.intersects(new Point(70, 90), new Point(80, 70)));		
		assertTrue(bvh.intersects(new Point(35, 25), new Point(45, 35)));
		assertTrue(bvh.intersects(new Point(80, 30), new Point(90, 40)));
		assertTrue(bvh.intersects(new Point(15, 45), new Point(15, 75)));
		assertTrue(bvh.intersects(new Point(95, 60), new Point(95, 75)));		
	}
}
