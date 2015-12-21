package datastructures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class DistanceFunctionImageGenerator {
	
	private static final int width = 1000;
	
	private static final int height = 1000;
	
	private static final DistanceFunction distFunc = new ExactDistanceFunction();
	
	
	public static void main(String[] args) throws IOException {
		String outputFileName = args[0];
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		Fragrance fragrance = fragrancePart();
		
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Ghost ghost = new Ghost(new Point(x, y));
				if (isCloseEnough(fragrance, ghost)) {
					image.setRGB(x, y, 0x0000ff);
				} else {
					image.setRGB(x, y, 0xffffff);
				}
			}
		}
		
		Graphics g = image.getGraphics();
		g.setColor(Color.BLACK);
		for (int i = 0; i < fragrance.getNumberOfPoints() - 1; i++) {
			Point p = fragrance.getPoint(i);
			Point q = fragrance.getPoint(i + 1);
			g.drawLine(p.x, p.y, q.x, q.y);
		}
		
		for (int i = 0; i < fragrance.getNumberOfPoints(); i++) {
			Point p = fragrance.getPoint(i);
			g.fillOval(p.x - 10, p.y - 10, 20, 20);
		}
		
		ImageIO.write(image, "png", new File(outputFileName));
		System.out.println("done");
		
	}
	
	private static Fragrance fragrancePart() {
		Fragrance fragrance = new Fragrance(2, new Point(500, 350), 200);
		fragrance.addPoint(new Point(500, 650));
		return fragrance;
	}
	
	private static Fragrance fragranceWithCorner() {
		Fragrance fragrance = new Fragrance(3, new Point(700, 300), 200);
		fragrance.addPoint(new Point(700, 700));
		fragrance.addPoint(new Point(300, 700));
		return fragrance;
	}
	
	private static Fragrance fragranceS() {
		Fragrance fragrance = new Fragrance(6, new Point(200, 500), 140);
		fragrance.addPoint(new Point(200, 200));
		fragrance.addPoint(new Point(500, 200));
		fragrance.addPoint(new Point(500, 500));
		fragrance.addPoint(new Point(800, 500));
		fragrance.addPoint(new Point(800, 200));
		return fragrance;
	}
	
	private static Fragrance fragranceSWithPauses() {
		Fragrance fragrance = new Fragrance(11, new Point(200, 500), 140);
		fragrance.addPoint(new Point(200, 200));
		fragrance.addPoint(new Point(500, 200));
		fragrance.addPoint(new Point(500, 500));
		fragrance.addPoint(new Point(500, 500));
		fragrance.addPoint(new Point(500, 500));
		fragrance.addPoint(new Point(500, 500));
		fragrance.addPoint(new Point(800, 500));
		fragrance.addPoint(new Point(800, 200));
		fragrance.addPoint(new Point(800, 200));
		return fragrance;
	}

	private static Fragrance fragrance1() {
		Fragrance fragrance = new Fragrance(7, new Point(200, 400), 140);
		fragrance.addPoint(new Point(200, 500));
		fragrance.addPoint(new Point(300, 600));
		fragrance.addPoint(new Point(320, 660));
		fragrance.addPoint(new Point(350, 540));
		fragrance.addPoint(new Point(400, 500));
		fragrance.addPoint(new Point(520, 400));
		return fragrance;
	}
	
	private static Fragrance fragranceStraightDifferentDistances() {
		Fragrance fragrance = new Fragrance(7, new Point(100, 200), 140);
		fragrance.addPoint(new Point(150, 200));
		fragrance.addPoint(new Point(250, 200));
		fragrance.addPoint(new Point(320, 200));
		fragrance.addPoint(new Point(350, 200));
		fragrance.addPoint(new Point(400, 200));
		fragrance.addPoint(new Point(520, 200));
		return fragrance;
	}
	
	private static Fragrance fragranceStraight() {
		Fragrance fragrance = new Fragrance(7, new Point(100, 100), 140);
		for (int i = 0; i < 6; i++) {
			fragrance.addPoint(new Point(150 + 50 * i, 130 + 30 * i));
		}
		return fragrance;
	}
	
	private static boolean isCloseEnough(Fragrance fragrance, Ghost ghost) {
		for (int i = 0; i < fragrance.getNumberOfPoints() - 1; i++) {
			if (distFunc.isCloseEnough(fragrance, i, ghost)) return true;
		}
		return false;
	}

}
