package hr.fer.zemris.irg.util;

import java.util.LinkedList;

import com.jogamp.opengl.GL2;

import hr.fer.zemris.irg.Bresenhman;
import hr.fer.zemris.irg.CohenSutherland;
import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.IncompatibleOperandException;
import hr.fer.zemris.linearna.Vector;

/**
 * Class in charge of all data control in regard to drawing.
 * @author Viran
 *
 */
public class GraphicsToolbox {

	private LinkedList<IVector> pointHistory = new LinkedList<>();
	private IVector lineBuffer;
	private IVector curMousePos;

	private int width;
	private int height;

	private boolean controlActive = false;
	private boolean lineClipingActive = false;
	private static final double PARALLEL_VEC_DISTANCE = 4;

	/**
	 * GraphicsToolbox constructor.
	 */
	public GraphicsToolbox() {
		this.curMousePos = new Vector(0, 0);
	}

	/**
	 * Refresh the mouse position after the user moved it.
	 * @param x New x mouse position.
	 * @param y New y mouse position.
	 */
	public void mouseMoved(int x, int y) {
		curMousePos.set(0, x).set(1, y);
	}

	/**
	 * Perform the necessary operations after the user clicked the mouse.
	 * @param x New x mouse position.
	 * @param y New y mouse position.
	 */
	public void mouseClicked(int x, int y) {
		if (lineBuffer == null) {
			lineBuffer = new Vector(x, y);
		} else {
			pointHistory.add(lineBuffer);
			pointHistory.add(new Vector(x, y));
			lineBuffer = null;
		}
	}

	/**
	 * Get a vector record form the drawn points.
	 * @param i Point in application history.
	 * @return Vector record. 
	 */
	public IVector getFromHistory(int i) {
		return pointHistory.get(i);
	}

	/**
	 * Get the number of points from application history.
	 * @return Number of points.
	 */
	public int getLineHistorySize() {
		return pointHistory.size();
	}

	/**
	 * Get the component of the currently drawn element.
	 * @return First fixed point as vector object.
	 */
	public IVector getFromBuffer() {
		return lineBuffer;
	}

	/**
	 * Get current mouse position.
	 * @return Current mouse position as vector.
	 */
	public IVector getCurrentPoint() {
		return curMousePos;
	}

	/**
	 * Set the current width of the application window.
	 * @param width Current window width.
	 */
	public void saveWidth(int width) {
		this.width = width;

	}

	/**
	 * Set the current height of the application window.
	 * @param height Current window height.
	 */
	public void saveHeight(int height) {
		this.height = height;
	}

	/**
	 * Get current window width.
	 * @return Current window width.
	 */
	public int loadWidth() {
		return width;
	}

	/**
	 * Get current window height.
	 * @return Current window height.
	 */
	public int loadHeight() {
		return height;
	}

	/**
	 * Toggle the parallel lines option.
	 */
	public void toggleControl() {
		this.controlActive = !controlActive;

	}

	/**
	 * Toggle the window restriction option.
	 */
	public void toggleLineClipping() {
		this.lineClipingActive = !lineClipingActive;
	}

	/**
	 * Draw a line from position v1 to position v2 on the given canvas.
	 * @param canvas Object on which the line should be situated.
	 * @param v1 Starting point of the line segment. 
	 * @param v2 Ending point of the line segment. 
	 */
	public void drawLine(GL2 canvas, IVector v1, IVector v2) {
		IVector v1copy = v1.copy();
		IVector v2copy = v2.copy();

		// resize v1 and v2 if needed
		if (lineClipingActive) {
			double yMax = 0.75 * height;// height/2+height/4;
			double yMin = 0.25 * height;// height/2-height/4;
			double xMax = 0.75 * width;// width/2+width/4;
			double xMin = 0.25 * width;// width/2-width/4;
			CohenSutherland.checkLineCliping(v1copy, v2copy, yMax, yMin, xMax, xMin);
		}

		if (v1copy.get(0) != -1 && v2copy.get(0) != -1)
			Bresenhman.drawLine(canvas, v1copy, v2copy);

		// control
		if (controlActive) {
			try {
				IVector dirVector = v2.nSub(v1);
				IVector dirOrtho=dirVector.normalise();

				double tmp;
				// orthogonal vector with the length of 4
				// vector 1
				tmp = dirOrtho.get(0);
				dirOrtho.set(0, -1*dirOrtho.get(1));
				dirOrtho.set(1, tmp);
				dirOrtho.scalarMultiply(PARALLEL_VEC_DISTANCE);
				

				// TODO try multiplying with a rotation matrix of 90 degrees.

				Bresenhman.drawLine(canvas, v1.add(dirOrtho), v2.add(dirOrtho));
			} catch (IncompatibleOperandException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Return the information whether the application is in line clipping state.
	 * @return True if the line clipping options is activates, false otherwise. 
	 */
	public boolean isClippingActive() {
		return lineClipingActive;
	}

}
