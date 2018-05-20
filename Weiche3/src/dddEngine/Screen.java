package dddEngine;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import common.vectorMath.objects3D.CSYS;
import common.vectorMath.objects3D.Point;

public class Screen extends JPanel implements KeyListener {
	static long SleepTime = (long) (1000. / 30.); // 1/fps
	static double actualfps = 0;
	static long LastRefresh = 0;
	static Point ViewFrom = new Point(0, 0, 10);
	static Point ViewTo = new Point(0, 0, 0);

	int[] NewOrder;
	boolean[] Keys = new boolean[10];
	static CSYS camera = new CSYS(ViewFrom, new double[] { 1, 1, 1 }, new double[] { 0, 0, 0 });
	double zoom = 10;
	List<DDDPolygon> DDDPolyList = new ArrayList<>();
	List<DDPolygon> DDPolyList = new ArrayList<>();

	private Calculator calculator = new Calculator(this);
	
	private static double zoomMax = 100.;
	private static double zoomMin = 1.;

	Dimension getPanelSize() {
		return getSize();
	}

	public void addDDDPolygon(final DDDPolygon DPoly) {
		this.DDDPolyList.add(DPoly);
		this.DDPolyList.add(null);
	}

	void updatePolygon(final int listPos) {
		// projected Points are 3d Points projected to the view plane
		final DDDPolygon DPoly = DDDPolyList.get(listPos);
		final Point[] projectedPoints = new Point[DPoly.verts.length];
		for (int i = 0; i < DPoly.verts.length; i++) {
			projectedPoints[i] = calculator.calcScreenPos(Screen.camera, DPoly.verts[i]);
		}

		final DDPolygon newPoly = new DDPolygon(projectedPoints, DPoly.c);
		newPoly.AvgDist = DPoly.GetDist();
		DDPolyList.set(listPos, newPoly);
	}

	double getZoom() {
		return this.zoom;
	}

	public Screen() {/*
						 * DPolygons[NumberOf3DPolygons] = new DPolygon(new double[] { 0, 2, 2, 0 }, new
						 * double[] { 0, 0, 2, 2 }, new double[] { 0, 0, 0, 0 }, Color.gray);
						 * DPolygons[NumberOf3DPolygons] = new DPolygon(new double[] { 0, 2, 2, 0 }, new
						 * double[] { 0, 0, 2, 2 }, new double[] { 3, 3, 3, 3 }, Color.gray);
						 * DPolygons[NumberOf3DPolygons] = new DPolygon(new double[] { 0, 2, 2, 0 }, new
						 * double[] { 0, 0, 0, 0 }, new double[] { 0, 0, 3, 3 }, Color.gray);
						 * DPolygons[NumberOf3DPolygons] = new DPolygon(new double[] { 0, 2, 2, 0 }, new
						 * double[] { 2, 2, 2, 2 }, new double[] { 0, 0, 3, 3 }, Color.gray);
						 * DPolygons[NumberOf3DPolygons] = new DPolygon(new double[] { 0, 0, 0, 0 }, new
						 * double[] { 0, 2, 2, 0 }, new double[] { 0, 0, 3, 3 }, Color.gray);
						 * DPolygons[NumberOf3DPolygons] = new DPolygon(new double[] { 2, 2, 2, 2 }, new
						 * double[] { 0, 2, 2, 0 }, new double[] { 0, 0, 3, 3 }, Color.gray); for (int i
						 * = -4; i < 5; i++) for (int j = -4; j < 5; j++) DPolygons[NumberOf3DPolygons]
						 * = new DPolygon(new double[] { i, i, i + 1, i + 1 }, new double[] { j, j + 1,
						 * j + 1, j }, new double[] { 0, 0, 0, 0 }, Color.GREEN);
						 */
		/*final Cube cube = new Cube(new CSYS(), 0);
		cube.getCSYS().scale(new double[] { 4, 4, 4 });
		for (int i = 0; i < cube.getPolygons().length; i++) {
			addDPolygon(cube.getPolygons()[i]);
		}*/
		addKeyListener(this);
		setFocusable(true);
	}

	public void paintComponent(Graphics g) {
		Controls();
		g.clearRect(0, 0, 2000, 1200);
		g.drawString(System.currentTimeMillis() + "", 20, 20);
		g.drawString("Zoom " + zoom, 20, 40);
		g.drawString("FPS " + actualfps, 20, 60);

		for (int i = 0; i < DDDPolyList.size(); i++)
			updatePolygon(i);
		setOrder();

		for (int i = 0; i < DDPolyList.size(); i++) {
			DDPolyList.get(i).drawPolygon(g);
		}
		SleepAndRefresh();
	}

	void setOrder() {
		double[] k = new double[DDPolyList.size()];
		NewOrder = new int[DDPolyList.size()];

		for (int i = 0; i < DDPolyList.size(); i++) {
			k[i] = DDPolyList.get(i).AvgDist;
			NewOrder[i] = i;
		}

		double temp;
		int tempr;
		for (int a = 0; a < k.length - 1; a++) {
			for (int b = 0; b < k.length - 1; b++) {
				if (k[b] < k[b + 1]) {
					temp = k[b];
					tempr = NewOrder[b];
					NewOrder[b] = NewOrder[b + 1];
					k[b] = k[b + 1];

					NewOrder[b + 1] = tempr;
					k[b + 1] = temp;
				}
			}
		}
	}

	void SleepAndRefresh() {
		long actualSleepTime;
		while (true) {
			actualSleepTime = System.currentTimeMillis() - LastRefresh;
			if (actualSleepTime > SleepTime) {
				LastRefresh = System.currentTimeMillis();
				actualfps = 1000./(double)actualSleepTime;
				repaint();
				break;
			} else {
				try {
					Thread.sleep((long) (SleepTime - System.currentTimeMillis() - LastRefresh));
				} catch (Exception e) {

				}
			}
		}
	}

	void Controls() {
		/*
		 * Point ViewVector = new Point(ViewTo[0] - ViewFrom[0], ViewTo[1] -
		 * ViewFrom[1], ViewTo[2] - ViewFrom[2]); if (Keys[4]) { ViewFrom[0] +=
		 * ViewVector.getX(); ViewFrom[1] += ViewVector.getY(); ViewFrom[2] +=
		 * ViewVector.getZ(); ViewTo[0] += ViewVector.getX(); ViewTo[1] +=
		 * ViewVector.getY(); ViewTo[2] += ViewVector.getZ(); } if (Keys[6]) {
		 * ViewFrom[0] -= ViewVector.getX(); ViewFrom[1] -= ViewVector.getY();
		 * ViewFrom[2] -= ViewVector.getZ(); ViewTo[0] -= ViewVector.getX(); ViewTo[1]
		 * -= ViewVector.getY(); ViewTo[2] -= ViewVector.getZ(); } Point VerticalVector
		 * = new Point(0, 0, 1); Point SideViewVector = VectorUtils.crossP(ViewVector,
		 * VerticalVector); if (Keys[5]) { ViewFrom[0] += SideViewVector.getX();
		 * ViewFrom[1] += SideViewVector.getY(); ViewFrom[2] += SideViewVector.getZ();
		 * ViewTo[0] += SideViewVector.getX(); ViewTo[1] += SideViewVector.getY();
		 * ViewTo[2] += SideViewVector.getZ(); } if (Keys[7]) { ViewFrom[0] -=
		 * SideViewVector.getX(); ViewFrom[1] -= SideViewVector.getY(); ViewFrom[2] -=
		 * SideViewVector.getZ(); ViewTo[0] -= SideViewVector.getX(); ViewTo[1] -=
		 * SideViewVector.getY(); ViewTo[2] -= SideViewVector.getZ(); }
		 */
		final double inc = .1; // increment value
		if (Keys[0]) { // left
			ViewFrom.move(-inc, 0, 0);
			ViewTo.move(-inc, 0, 0);
		}
		if (Keys[1]) { // right
			ViewFrom.move(inc, 0, 0);
			ViewTo.move(inc, 0, 0);
		}
		if (Keys[2]) { // up
			ViewFrom.move(0,-inc, 0);
			ViewTo.move(0, -inc, 0);
		}
		if (Keys[3]) { // down
			ViewFrom.move(0, inc, 0);
			ViewTo.move(0, inc, 0);
		}
		if (Keys[4]) { // W
			zoom += 10.;
		}
		if (Keys[6]) { // A
			zoom -= 10.;
		}
		
		if(zoom>zoomMax) {
			zoom=zoomMax;
		}if(zoom<zoomMin) {
			zoom=zoomMin;
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_LEFT)
			Keys[0] = true;
		if (arg0.getKeyCode() == KeyEvent.VK_RIGHT)
			Keys[1] = true;
		if (arg0.getKeyCode() == KeyEvent.VK_UP)
			Keys[2] = true;
		if (arg0.getKeyCode() == KeyEvent.VK_DOWN)
			Keys[3] = true;
		if (arg0.getKeyCode() == KeyEvent.VK_W)
			Keys[4] = true;
		if (arg0.getKeyCode() == KeyEvent.VK_A)
			Keys[5] = true;
		if (arg0.getKeyCode() == KeyEvent.VK_S)
			Keys[6] = true;
		if (arg0.getKeyCode() == KeyEvent.VK_D)
			Keys[7] = true;
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_LEFT)
			Keys[0] = false;
		if (arg0.getKeyCode() == KeyEvent.VK_RIGHT)
			Keys[1] = false;
		if (arg0.getKeyCode() == KeyEvent.VK_UP)
			Keys[2] = false;
		if (arg0.getKeyCode() == KeyEvent.VK_DOWN)
			Keys[3] = false;
		if (arg0.getKeyCode() == KeyEvent.VK_W)
			Keys[4] = false;
		if (arg0.getKeyCode() == KeyEvent.VK_A)
			Keys[5] = false;
		if (arg0.getKeyCode() == KeyEvent.VK_S)
			Keys[6] = false;
		if (arg0.getKeyCode() == KeyEvent.VK_D)
			Keys[7] = false;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}
}
