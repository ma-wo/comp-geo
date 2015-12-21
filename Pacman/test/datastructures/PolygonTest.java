package datastructures;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class PolygonTest {
	@Test
	public void testIntersection() {
		List<Point> points = new ArrayList<Point>();
		points.add(new Point(2,1));
		points.add(new Point(2,5));
		points.add(new Point(5,5));
		points.add(new Point(5,1));
		
		Polygon p = new Polygon(points);
		assertFalse(p.intersects(new Point(1, 0), new Point(7, 0))); // horizontal line above top border		
		assertFalse(p.intersects(new Point(0, 3), new Point(1, 3))); // horizontal line left of p
		assertTrue(p.intersects(new Point(1, 1), new Point(7, 1))); // horizontal line equal to top border
		assertTrue(p.intersects(new Point(1, 3), new Point(2, 3))); // horizontal line intersecting
		assertTrue(p.intersects(new Point(1, 3), new Point(3, 3))); // horizontal line intersecting
		assertTrue(p.intersects(new Point(1, 3), new Point(7, 3))); // horizontal line intersecting
		assertTrue(p.intersects(new Point(4, 3), new Point(6, 3))); // horizontal line intersecting
		assertTrue(p.intersects(new Point(5, 3), new Point(6, 3))); // horizontal line intersecting
		assertTrue(p.intersects(new Point(1, 5), new Point(7, 5))); // horizontal line equal to bottom border
		assertFalse(p.intersects(new Point(1, 6), new Point(7, 6))); // horizontal line below bottom border
		assertFalse(p.intersects(new Point(6, 3), new Point(7, 3))); // horizontal line right of p
		
		
		assertFalse(p.intersects(new Point(1, 0), new Point(1, 7))); // vertical line left of p		
		assertTrue(p.intersects(new Point(2, 0), new Point(2, 7))); // vertical line on left border
		assertTrue(p.intersects(new Point(4, 0), new Point(4, 1))); // vertical line intersecting
		assertTrue(p.intersects(new Point(4, 0), new Point(4, 3))); // vertical line intersecting
		assertTrue(p.intersects(new Point(4, 0), new Point(4, 7))); // vertical line intersecting
		assertTrue(p.intersects(new Point(4, 4), new Point(4, 7))); // vertical line intersecting
		assertTrue(p.intersects(new Point(4, 5), new Point(4, 7))); // vertical line intersecting		
		assertFalse(p.intersects(new Point(4, 6), new Point(4, 9))); // vertical line below p
		assertTrue(p.intersects(new Point(5, 0), new Point(5, 7))); // vertical line on right border
		assertFalse(p.intersects(new Point(7, 0), new Point(7, 7))); // vertical line right of p
		
		assertFalse(p.intersects(new Point(1, 2), new Point(2, 0)));
		assertFalse(p.intersects(new Point(1, 6), new Point(10, 4)));
		assertFalse(p.intersects(new Point(2, 0), new Point(6, 1)));
		assertFalse(p.intersects(new Point(0, 5), new Point(1, 2)));
		
		assertTrue(p.intersects(new Point(0, 2), new Point(2, 2)));
		assertTrue(p.intersects(new Point(1, 2), new Point(4, 0)));
		assertTrue(p.intersects(new Point(1, 6), new Point(9, 4)));
		assertTrue(p.intersects(new Point(4, 6), new Point(6, 4)));
		assertTrue(p.intersects(new Point(1, 4), new Point(4, 6)));	
		
		// TODO: add more complex polygon tests
	}
}
