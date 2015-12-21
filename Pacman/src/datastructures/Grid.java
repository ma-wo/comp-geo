package datastructures;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Grid {
	private List<List<List<Ghost>>> grid;
	
	private int cellLength;
	
	private int width;
	private int height;
	
	private int nRows;
	private int nColumns;	
	
	private List<Ghost> ghosts;
	
	public Grid(int width, int height, int cellLength, List<Ghost> ghosts) {
		this.width = width;
		this.height = height;
		this.cellLength = cellLength;
		
		nRows = (int) Math.ceil((double) height / (double) cellLength);
		nColumns = (int) Math.ceil((double) width / (double) cellLength);
		
		this.ghosts = ghosts;
		build(ghosts);
	}
	
	/**
	 * Returns the ghosts in the cell of the line segment ab and the ghosts of this
	 * cell's 8-neighborhood.
	 * @param a Start point of the line segment.
	 * @param b End point of the line segment.
	 * @return List of ghosts.
	 */
	public Set<Ghost> getGhosts(Point a, Point b) {
		Cell cA = getCell(a);
		Cell cB = getCell(b);
		
		Set<Ghost> ghosts = new HashSet<Ghost>();
		ghosts.addAll(grid.get(cA.row).get(cA.column));
		ghosts.addAll(get8Neighborhood(cA));
		
		if (!cB.equals(cA)) {
			ghosts.addAll(grid.get(cB.row).get(cB.column));
			ghosts.addAll(get8Neighborhood(cB));
		}
		
		return ghosts;
	}
	
	public void update(List<Point> ghostPositions) {
		for (int i = 0; i < ghosts.size(); ++i) {
			Ghost ghost = ghosts.get(i);
			Cell currentCell = getCell(ghost.getPosition());
			Cell newCell = getCell(ghostPositions.get(i));
			if (!currentCell.equals(newCell)) {
				grid.get(currentCell.row).get(currentCell.column).remove(ghost);				
				grid.get(newCell.row).get(newCell.column).add(ghost);
			}
		}
	}
	
	private Set<Ghost> get8Neighborhood(Cell cell) {
		Set<Ghost> ghosts = new HashSet<Ghost>();		
		for (int i = cell.row-1; i <= cell.row+1; ++i) {
			if (0 <= i && i < nRows) {
				for (int j = cell.column-1; j <= cell.column+1; ++j) {
					if (0 <= j && j < nColumns) {
						ghosts.addAll(grid.get(i).get(j));
					}
				}
			}			
		}
		
		return ghosts;		
	}
	
	private void build(List<Ghost> ghosts) {
		grid = new ArrayList<List<List<Ghost>>>();
		for (int i = 0; i < nRows; ++i) {
			List<List<Ghost>> columns = new ArrayList<List<Ghost>>();
			for (int j = 0; j < nColumns; ++j) {
				columns.add(new LinkedList<Ghost>());				
			}
			grid.add(columns);
		}
		
		for (Ghost ghost : ghosts) {
			Cell c = getCell(ghost.getPosition());			
			grid.get(c.row).get(c.column).add(ghost);
		}		
	}
	
	private Cell getCell(Point p) {
		assert(0 <= p.x && p.x <= width);
		assert(0 <= p.y && p.y <= height);
		
		return new Cell(Math.min(p.y / cellLength, nRows-1), Math.min(p.x / cellLength, nColumns-1));
	}
	
	private class Cell {
		public int row;
		public int column;
		
		public Cell(int row, int column) {
			this.row = row;
			this.column = column;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj == null) return false;
			Cell other = (Cell) obj;
			return other.row == row && other.column == column;
		}
	}
	
}
