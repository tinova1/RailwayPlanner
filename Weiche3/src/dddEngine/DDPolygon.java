package dddEngine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import common.vectorMath.objects3D.Point;

public class DDPolygon {
	private Polygon P;
	Color c;
	double AvgDist = 0;

	public DDPolygon(Point[] edges, Color c) {
		P = new Polygon();
		for (int i = 0; i < edges.length; i++) {
			P.addPoint((int) edges[i].getX(), (int) edges[i].getY());
		}
		this.c = c;
	}

	void drawPolygon(Graphics g) {
		g.setColor(c);
		g.fillPolygon(P);
		g.setColor(Color.black);
		g.drawPolygon(P);
	}
}
