package hr.fer.zemris.irg.fractals;

import java.awt.Point;
import java.util.Random;
import java.util.Stack;

import hr.fer.zemris.irg.fractals.colorSchemes.BlackNWhite;
import hr.fer.zemris.irg.fractals.colorSchemes.ColorScheme1;
import hr.fer.zemris.irg.fractals.colorSchemes.ColorScheme2;
import hr.fer.zemris.irg.fractals.colorSchemes.IColorScheme;

public class JuliusController {

	private class ComplexAreaRecord {
		double umin;
		double umax;
		double vmin;
		double vmax;

		public ComplexAreaRecord(double umin, double umax, double vmin, double vmax) {
			this.umin = umin;
			this.umax = umax;
			this.vmin = vmin;
			this.vmax = vmax;
		}
	}

	private enum ColorSchemes {
		COLOR1, COLOR2
	}

	private static final double UNIT = 0.005;

	// display
	// complex domain
	private double umin;
	private double vmin;
	private double umax;
	private double vmax;

	// canvas elements

	private int xCanvas;
	private int yCanvas;
	private int width;
	private int height;

	private int limit;
	private int n;
	private double reC;
	private double imC;

	private boolean isBnW;

	// drawing
	private ColorSchemes eActiveScheme;
	// zooming
	private Stack<ComplexAreaRecord> zoomingRecords = new Stack<>();

	private Random random=new Random();

	public JuliusController() {
		this.umin = -2.;
		this.umax = 2.;
		this.vmin = -1.2;
		this.vmax = +1.2;
		
		this.reC=0.11;
		this.imC=0.65;

		this.limit = 64;
		this.n = 2;

		this.xCanvas = 0;
		this.yCanvas = 0;
		this.width = 640;
		this.height = 480;

		this.isBnW = true;
		this.eActiveScheme = ColorSchemes.COLOR2;
	}


	// ----------------------------------

	// --------------limit---------------
	public void decLimit() {
		this.limit--;
		if (this.limit < 1)
			this.limit = 1;
	}

	public void incLimit() {
		this.limit++;
	}
	// ----------------------------------

	// --------------n-------------------
	public void decN() {
		this.n--;
		if (this.n < 1)
			this.n = 1;
	}

	public void incN() {
		this.n++;
	}
	// ----------------------------------

	public double umin() {
		return this.umin;
	}

	public double vmin() {
		return this.vmin;
	}

	public double umax() {
		return this.umax;
	}

	public double vmax() {
		return this.vmax;
	}

	// Fractal specific
	public int limit() {
		return limit;
	}

	public int n() {
		return n;
	}

	// Canvas specific
	public void store(int x, int y, int width, int height) {
		this.xCanvas = x;
		this.yCanvas = y;
		this.width = width;
		this.height = height;
	}

	public int xCanvas() {
		return xCanvas;
	}

	public int yCanvas() {
		return yCanvas;
	}

	public int width() {
		return width;
	}

	public int height() {
		return height;
	}

	public void toggleBnW() {
		this.isBnW = this.isBnW ? false : true;
	}

	public void changeColorSheme() {
		if (this.eActiveScheme == ColorSchemes.COLOR1) {
			this.eActiveScheme = ColorSchemes.COLOR2;
		} else if (this.eActiveScheme == ColorSchemes.COLOR2) {
			this.eActiveScheme = ColorSchemes.COLOR1;
		}
	}

	public IColorScheme getActiveScheme() {
		if (this.isBnW) {
			return BlackNWhite.getScheme();
		} else {
			if (this.eActiveScheme == ColorSchemes.COLOR1)
				return ColorScheme1.getScheme();
			else
				return ColorScheme2.getScheme();
		}
	}

	public void leftClick(Point point) {
		int x = point.x;
		int y = point.y;

		double dx = width;
		double dy = height;
		double du = umax - umin;
		double dv = vmax - vmin;

		double xOffset = (1. / 16. * height / 2.);
		double yOffset = (1. / 16. * width / 2.);

		double xmin = x - xOffset;
		double xmax = x + xOffset;
		double ymin = y - yOffset;
		double ymax = y + yOffset;

		double umin = du / dx * xmin + this.umin;
		double vmin = dv / dy * ymin + this.vmin;
		double umax = du / dx * xmax + this.umin;
		double vmax = dv / dy * ymax + this.vmin;

		// save old coordinates to stack -> set this
		this.zoomingRecords.push(new ComplexAreaRecord(this.umin, this.umax, this.vmin, this.vmax));
		this.umin = umin;
		this.vmin = vmin;
		this.umax = umax;
		this.vmax = vmax;
		
		this.limit*=1.2;
	}

	public void rightClick(Point point) {
		if (!this.zoomingRecords.isEmpty()) {
			ComplexAreaRecord prevArea = this.zoomingRecords.pop();
			this.umin = prevArea.umin;
			this.vmin = prevArea.vmin;
			this.umax = prevArea.umax;
			this.vmax = prevArea.vmax;
			this.limit/=1.2;
		}
		// adjust resolution
		// this.decLimit();
	}

	public void resetView() {
		if (!this.zoomingRecords.isEmpty()) {
			ComplexAreaRecord prevArea = this.zoomingRecords.firstElement();
			this.umin = prevArea.umin;
			this.vmin = prevArea.vmin;
			this.umax = prevArea.umax;
			this.vmax = prevArea.vmax;
			
			this.zoomingRecords.clear();
			this.limit=64;
		}
	}


	public Random getRandom() {
		return this.random;
	}


	public void decReC() {
		this.reC-=UNIT;
		
	}


	public void incReC() {
		this.reC+=UNIT;
	}


	public void decImC() {
		this.imC-=UNIT;
	}


	public void incImC() {
		this.imC+=UNIT;
	}


	public double getReC() {
		return reC;
	}
	
	public double getImC() {
		return imC;
	}


	public void setRe(double d) {
		this.reC=d;
	}


	public void setIm(double d) {
		this.imC=d;
	}


	

}
