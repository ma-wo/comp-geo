package datastructures;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class GridTest {
	@Test
	public void gridTest() {
		List<Ghost> ghosts = new ArrayList<Ghost>();
		ghosts.add(new Ghost(new Point(5,5)));
		ghosts.add(new Ghost(new Point(60,40)));
		ghosts.add(new Ghost(new Point(0,0)));
		ghosts.add(new Ghost(new Point(40,50)));
		ghosts.add(new Ghost(new Point(15,15)));
		ghosts.add(new Ghost(new Point(70,0)));
		ghosts.add(new Ghost(new Point(45,35)));
		ghosts.add(new Ghost(new Point(55,25)));
		ghosts.add(new Ghost(new Point(35,0)));
		ghosts.add(new Ghost(new Point(35,25)));
			
		Grid grid = new Grid(70, 50, 10, ghosts);
		
		Set<Ghost> g = grid.getGhosts(new Point(2,0), new Point(4,8));
		assertTrue(g.size() == 3);
		assertTrue(g.contains(ghosts.get(0)));
		assertTrue(g.contains(ghosts.get(2)));
		assertTrue(g.contains(ghosts.get(4)));
		
		g = grid.getGhosts(new Point(21,21), new Point(23,23));
		assertTrue(g.size() == 2);
		assertTrue(g.contains(ghosts.get(4)));
		assertTrue(g.contains(ghosts.get(9)));
		
		
		g = grid.getGhosts(new Point(21,21), new Point(31,23));
		assertTrue(g.size() == 3);
		assertTrue(g.contains(ghosts.get(4)));
		assertTrue(g.contains(ghosts.get(6)));
		assertTrue(g.contains(ghosts.get(9)));
		
		g = grid.getGhosts(new Point(15,45), new Point(16,47));
		assertTrue(g.size() == 0);
		
		g = grid.getGhosts(new Point(55,35), new Point(56,36));		
		assertTrue(g.size() == 4);
		assertTrue(g.contains(ghosts.get(1)));
		assertTrue(g.contains(ghosts.get(3)));		
		assertTrue(g.contains(ghosts.get(6)));
		assertTrue(g.contains(ghosts.get(7)));
		
		g = grid.getGhosts(new Point(55,45), new Point(56,46));
		assertTrue(g.size() == 3);
		assertTrue(g.contains(ghosts.get(1)));
		assertTrue(g.contains(ghosts.get(3)));		
		assertTrue(g.contains(ghosts.get(6)));
		
		g = grid.getGhosts(new Point(45,5), new Point(46,5));
		assertTrue(g.size() == 1);
		assertTrue(g.contains(ghosts.get(8)));
		
		g = grid.getGhosts(new Point(65,10), new Point(65,15));
		assertTrue(g.size() == 2);
		assertTrue(g.contains(ghosts.get(5)));
		assertTrue(g.contains(ghosts.get(7)));		
	}
}
