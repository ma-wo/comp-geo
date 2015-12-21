package io;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import datastructures.Polygon;

public class OpenStreetMapImporter {
	private SAXParser xmlParser;
		
	private class Node {
		public double lat;
		public double lon;
	}	
	
	private double minX;
	private double minY;
	private double maxX;
	private double maxY;
	
	private final int ZOOM_LEVEL = 16;
	
	private class XMLHandler extends DefaultHandler {		
		private Map<Long, Node> nodes;		
		private List<Long> wayNodes;		
		private boolean isBuilding;
		
		private List<List<Node>> buildings;
		
		public XMLHandler() {
			nodes = new HashMap<Long, Node>();
			buildings = new ArrayList<List<Node>>();
		}
		
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			if (qName.equalsIgnoreCase("node")) {				
				long id = Long.parseLong(attributes.getValue("id"));
				Node node = new Node();
				node.lat = Double.parseDouble(attributes.getValue("lat"));
				node.lon = Double.parseDouble(attributes.getValue("lon"));
				nodes.put(id, node);
			} else if (qName.equalsIgnoreCase("way")) {			
				wayNodes = new ArrayList<Long>();				
			} else if (qName.equalsIgnoreCase("nd")) {
				wayNodes.add(Long.parseLong(attributes.getValue("ref")));				
				
			} else if (qName.equalsIgnoreCase("tag")) {
				if (attributes.getValue("k").equals("building")) {
					isBuilding = true;					
				}
			} else if (qName.equalsIgnoreCase("bounds")) {
				minX = getX(Double.parseDouble(attributes.getValue("minlon")), ZOOM_LEVEL);
				maxY = getY(Double.parseDouble(attributes.getValue("minlat")), ZOOM_LEVEL);
				
				maxX = getX(Double.parseDouble(attributes.getValue("maxlon")), ZOOM_LEVEL);
				minY = getY(Double.parseDouble(attributes.getValue("maxlat")), ZOOM_LEVEL);				
			}
		}
		
		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			if (qName.equalsIgnoreCase("way")) {
				if (isBuilding) {
					List<Node> building = new ArrayList<Node>();
					for (long nodeId : wayNodes) {
						building.add(nodes.get(nodeId));
					}
					
					buildings.add(building);
					wayNodes.clear();
					isBuilding = false;
				}				
			}
		}
		
		public List<List<Node>> getBuildings() {
			return buildings;
		}
	}
	
	private double getX(double lon, int zoomLevel) {
		return 256* (lon+180)/360 * Math.pow(2, zoomLevel);
	}
	
	private double getY(double lat, int zoomLevel) {			
		return 256 * (1.0-(Math.log(Math.tan(lat * Math.PI/180) + 1/(Math.cos(Math.toRadians(lat)))) / Math.PI)) * Math.pow(2, zoomLevel-1);
	}
	
	public OpenStreetMapImporter() {
		
	}
	
	public List<Polygon> parseFile(String fileName, int width) {		
		try {
			File input = new File(fileName);
			SAXParserFactory factory = SAXParserFactory.newInstance();
			xmlParser = factory.newSAXParser();
			XMLHandler handler = new XMLHandler();			
			xmlParser.parse(input, handler);
			
			List<List<Node>> buildings = handler.getBuildings();
			List<Polygon> polygons = new ArrayList<Polygon>();
			
			
			double ratio = (maxX - minX) / (maxY - minY);
			int height = (int) (width / ratio);
			
			for (List<Node> building : buildings) {
				List<Point> points = new ArrayList<Point>();
				for (Node node : building) {
					Point p = new Point();
					p.x = (int) ((getX(node.lon, ZOOM_LEVEL) - minX) / (maxX - minX) * width);
					p.y = (int) ((getY(node.lat, ZOOM_LEVEL) - minY) / (maxY - minY) * height);
					points.add(p);
				}
				polygons.add(new Polygon(points));
			}
			
			return polygons;			
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		return null;		
	}
	
	public int getHeight(int width) {
		double ratio = (maxX - minX) / (maxY - minY);
		return (int) (width / ratio);
	}
}
