package datastructures;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BoundingVolumeHierarchy {
	private class Node {
		public Node leftChild;
		public Node rightChild;
		public BoundingBox boundingBox;
		public Polygon p;
				
		public boolean isChild() {
			return leftChild == null && rightChild == null;
		}
		
	}
	
	private Node root;	
	
	public BoundingVolumeHierarchy(List<Polygon> polygons) {		
		root = new Node();		
		build(root, polygons, true);		
	}
	
	public boolean insidePolygon(Point p) {
		return insidePolygon(root, p);
	}
	
	public boolean intersects(Point a, Point b) {		
		return intersects(root, a, b);
	}
	
	public String toString() {
		return toString(root);
	}
	
	private String toString(Node node) {
		if (node == null) return "";
		if (node.isChild() && node.p != null) return node.p.toString();
		String result = "(left(";
		result += toString(node.leftChild) + ")";
		if (node.rightChild != null) {
			result += ", right(" + toString(node.rightChild) + ")";
		}
		result += ")";
		
		return result;
	}
	
	private boolean insidePolygon(Node node, Point p) {
		if (node == null) return false;
		if (node.isChild()) return node.p.contains(p);
		if (node.boundingBox.contains(p)) {
			return insidePolygon(node.leftChild, p) || insidePolygon(node.rightChild, p);		
		}
		
		return false;
	}
	
	private boolean intersects(Node node, Point a, Point b) {
		if (node == null) return false;
		if (node.isChild()) return node.p.intersects(a, b);
		if (node.boundingBox.intersects(a, b)) {
			return intersects(node.leftChild, a, b) || intersects(node.rightChild,  a, b);		
		}
		
		return false;
	}
	
	private void build(Node node, List<Polygon> polygons, boolean sortX) {		
		BoundingBox boundingBox = new BoundingBox(polygons.get(0).getBoundingBox());
		for (Polygon p : polygons) {
			boundingBox.extend(p.getBoundingBox());
		}
		
		node.boundingBox = boundingBox;
		
		Collections.sort(polygons, new Comparator<Polygon>(){
			@Override
			public int compare(Polygon p1, Polygon p2) {
				if (sortX) {
					return Integer.compare(p1.getCentroid().x, p2.getCentroid().x);
				} else {
					return Integer.compare(p1.getCentroid().y, p2.getCentroid().y);
				}				
			}
		});
		
			
		if (polygons.size() > 2) {
			List<Polygon> leftPolygons = new ArrayList<Polygon>();
			List<Polygon> rightPolygons = new ArrayList<Polygon>();
			splitPolygons(polygons, boundingBox.getSurfaceArea(), leftPolygons, rightPolygons);
			
			node.leftChild = new Node();
			node.rightChild = new Node();
			
			build(node.leftChild, leftPolygons, !sortX);
			build(node.rightChild, rightPolygons, !sortX);
		} else {
			Node leftChild = new Node();
			leftChild.p = polygons.get(0);
			leftChild.boundingBox = leftChild.p.getBoundingBox();
			node.leftChild = leftChild;
			
			if (polygons.size() == 2) {
				Node rightChild = new Node();
				rightChild.p = polygons.get(1);
				rightChild.boundingBox = rightChild.p.getBoundingBox();
				node.rightChild = rightChild;				
			}			
		}	
	}
	
	private void splitPolygons(List<Polygon> polygons, float completeSurfaceArea, List<Polygon> leftPolygons, List<Polygon> rightPolygons) {		
		int last = polygons.size()-1;
		
		BoundingBox leftBox = new BoundingBox(polygons.get(0).getBoundingBox());		
		BoundingBox rightBox = new BoundingBox(polygons.get(last).getBoundingBox());	
		
		float[] leftSurfaceArea = new float[polygons.size()-1];
		float[] rightSurfaceArea = new float[polygons.size()-1];
		
		leftSurfaceArea[0] = leftBox.getSurfaceArea();
		rightSurfaceArea[last-1] = rightBox.getSurfaceArea();
		for (int i = 1; i < last; ++i) {
			leftBox.extend(polygons.get(i).getBoundingBox());
			leftSurfaceArea[i] = leftBox.getSurfaceArea();
			
			rightBox.extend(polygons.get(last-i).getBoundingBox());
			rightSurfaceArea[last-i-1] = rightBox.getSurfaceArea();
		}
				
				
		float best = surfaceAreaHeuristic(leftSurfaceArea[0], 1, rightSurfaceArea[0], last, completeSurfaceArea);
		int numLeft = 1;		
		
		for (int i = 1; i < polygons.size()-1; ++i) {
			float sah = surfaceAreaHeuristic(leftSurfaceArea[i], i+1, rightSurfaceArea[i], last-i, completeSurfaceArea);
			if (sah < best) {
				best = sah;
				numLeft = i+1;
			}			
		}
		
		leftPolygons.addAll(polygons.subList(0, numLeft));
		rightPolygons.addAll(polygons.subList(numLeft, polygons.size()));		
	}
	
	private float surfaceAreaHeuristic(float leftSurfaceArea, int numLeft, float rightSurfaceArea, int numRight, float completeSurfaceArea) {
		return (leftSurfaceArea * numLeft + rightSurfaceArea * numRight) / completeSurfaceArea;
	}
	

}
