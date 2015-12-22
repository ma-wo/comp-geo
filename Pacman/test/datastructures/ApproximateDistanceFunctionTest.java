package datastructures;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ApproximateDistanceFunctionTest {
	
	
	
	@Parameter(value = 0)
	public double ghostX;
	
	@Parameter(value = 1)
	public double ghostY;
	
	@Parameter(value = 2)
	public int index;
	
	@Parameter(value = 3)
	public boolean expectedResult;
	
	private Fragrance fragrance;
	
	private Ghost ghost;
	
	private ApproximateDistanceFunction distFunc = new ApproximateDistanceFunction();
	
	@Parameters(name="{index}: p=({0}, {1}), index={2}")
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] {
			// points on trajectory
			{-1, 8, 0, true},
			{-1, 7, 0, true},
			{-1, 6, 0, true},
			{-1, 5, 0, true},
			{-1, 5, 1, true},
			{0, 4, 1, true},
			{1, 3, 1, true},
			{2, 2, 1, true},
			{3, 1, 1, true},
			{3, 1, 2, true},
			{4, 0, 2, true},
			{5, -1, 2, true},
			{5, -1, 3, true},
			{6, 0, 3, true},
			{7, 1, 3, true},
			{8, 2, 3, true},
			
			// near Pacman
			{8, 5, 4, false},
			{8, 4, 4, true},
			{8, 3, 4, true},
			{9, 2, 4, true},
			{10, 2, 4, true},
			{11, 2, 4, false},
			{6, 4, 4, false},
			{7, 3, 4, true},
			{9, 1, 4, true},
			{10, 0, 4, false},
			
			// segment 0
			{-1.2, 7, 0, false},
			{-1.1, 7, 0, true},
			{-0.9, 7, 0, true},
			{-0.8, 7, 0, false},
			{-1.4, 6, 0, false},
			{-1.3, 6, 0, true},
			{-1.2, 6, 0, true},
			{-1.1, 6, 0, true},
			{-0.9, 6, 0, true},
			{-0.8, 6, 0, true},
			{-0.7, 6, 0, true},
			{-0.6, 6, 0, false},
			
			// segment 0 to 1
			{-1.5, 5, 0, true},
			{-1.25, 5, 0, true},
			{0.75, 5, 0, false},
			{0.5, 5, 0, false},
			{-1.25, 4.75, 0, true},
			{-1.5, 4.5, 0, false}
			
		});
	}
	
	
	@Before
	public void setUp() {
		// all points are multiplied with 100 to get work around integer limitations
		ghost = new Ghost(new Point((int)(ghostX * 100), (int)(ghostY * 100)));
		fragrance = new Fragrance(6, new Point(-100, 800), 250);
		fragrance.addPoint(new Point(-100, 500));
		fragrance.addPoint(new Point(300, 100));
		fragrance.addPoint(new Point(500, -100));
		fragrance.addPoint(new Point(800, 200));
		fragrance.addPoint(new Point(800, 200));
	}
	
	

	@Test
	public void test() {
		assertEquals(expectedResult, distFunc.isCloseEnough(fragrance, index, ghost));
	}

}
