package datastructures;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

public class BoundingBoxTest {
	@Test
	public void testIntersection() {
		BoundingBox bBox = new BoundingBox(new Point(2,1), new Point(5, 5));
		assertFalse(bBox.intersects(new Point(1, 0), new Point(7, 0))); // horizontal line above top border		
		assertFalse(bBox.intersects(new Point(0, 3), new Point(1, 3))); // horizontal line left of bBox
		assertTrue(bBox.intersects(new Point(1, 1), new Point(7, 1))); // horizontal line equal to top border
		assertTrue(bBox.intersects(new Point(1, 3), new Point(2, 3))); // horizontal line intersecting
		assertTrue(bBox.intersects(new Point(1, 3), new Point(3, 3))); // horizontal line intersecting
		assertTrue(bBox.intersects(new Point(1, 3), new Point(7, 3))); // horizontal line intersecting
		assertTrue(bBox.intersects(new Point(4, 3), new Point(6, 3))); // horizontal line intersecting
		assertTrue(bBox.intersects(new Point(5, 3), new Point(6, 3))); // horizontal line intersecting
		assertTrue(bBox.intersects(new Point(1, 5), new Point(7, 5))); // horizontal line equal to bottom border
		assertFalse(bBox.intersects(new Point(1, 6), new Point(7, 6))); // horizontal line below bottom border
		assertFalse(bBox.intersects(new Point(6, 3), new Point(7, 3))); // horizontal line right of bBox
		
		
		assertFalse(bBox.intersects(new Point(1, 0), new Point(1, 7))); // vertical line left of bBox		
		assertTrue(bBox.intersects(new Point(2, 0), new Point(2, 7))); // vertical line on left border
		assertTrue(bBox.intersects(new Point(4, 0), new Point(4, 1))); // vertical line intersecting
		assertTrue(bBox.intersects(new Point(4, 0), new Point(4, 3))); // vertical line intersecting
		assertTrue(bBox.intersects(new Point(4, 0), new Point(4, 7))); // vertical line intersecting
		assertTrue(bBox.intersects(new Point(4, 4), new Point(4, 7))); // vertical line intersecting
		assertTrue(bBox.intersects(new Point(4, 5), new Point(4, 7))); // vertical line intersecting		
		assertFalse(bBox.intersects(new Point(4, 6), new Point(4, 9))); // vertical line below bBox
		assertTrue(bBox.intersects(new Point(5, 0), new Point(5, 7))); // vertical line on right border
		assertFalse(bBox.intersects(new Point(7, 0), new Point(7, 7))); // vertical line right of bBox
		
		assertFalse(bBox.intersects(new Point(1, 2), new Point(2, 0)));
		assertFalse(bBox.intersects(new Point(1, 6), new Point(10, 4)));
		assertFalse(bBox.intersects(new Point(2, 0), new Point(6, 1)));
		assertFalse(bBox.intersects(new Point(0, 5), new Point(1, 2)));
		
		assertFalse(bBox.intersects(new Point(0, 2), new Point(1, 4)));
		assertFalse(bBox.intersects(new Point(6, 3), new Point(8, 2)));
		
		assertFalse(bBox.intersects(new Point(3, 6), new Point(4, 7)));
		
		
		assertTrue(bBox.intersects(new Point(0, 2), new Point(2, 2)));
		assertTrue(bBox.intersects(new Point(1, 2), new Point(4, 0)));
		assertTrue(bBox.intersects(new Point(1, 6), new Point(9, 4)));
		assertTrue(bBox.intersects(new Point(4, 6), new Point(6, 4)));
		assertTrue(bBox.intersects(new Point(1, 4), new Point(4, 6)));		
	}
}
