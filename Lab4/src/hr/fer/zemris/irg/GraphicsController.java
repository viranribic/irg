package hr.fer.zemris.irg;

import java.util.LinkedList;

import com.jogamp.opengl.GL2;

import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.Vector;

/**
 * Controller class for the polygon drawing lab assignment.
 * 
 * @author Viran
 *
 */
public class GraphicsController {

	private AppStateWrapper state = new AppStateWrapper();
	private Polygon2D polygon = new Polygon2D();
	private boolean isConvexCheck = false;
	private boolean isFillingCheck = false;
	private IVector curentMousePos = new Vector(0, 0);

	/**
	 * Toggle between checking if the polygon is convex or not. If the checking
	 * should be turned on, but the polygon is not convex, this operation will
	 * not proceed.
	 */
	public void toggleConvex() {
		if (state.equals(EAppState.POLYG_DEF))
			if (polygon.checkConvex() == false && this.isConvexCheck == false)
				System.out.println("The convex check couldn't be activated! Polygon is not convex!");
			else
				this.isConvexCheck = (this.isConvexCheck) ? false : true;
		System.out.println("Convex is now set to " + isConvexCheck + " .");

	}

	/**
	 * Toggle between options of filling the polygon in one colour, or letting
	 * it be made of edges only.
	 */
	public void toggleFilling() {
		if (state.equals(EAppState.POLYG_DEF))
			this.isFillingCheck = (this.isFillingCheck) ? false : true;
		System.out.println("Filling is now set to " + isFillingCheck + " .");
	}

	/**
	 * Change the application state. In first (default state) the user can
	 * define the polygon and in the second he can check for an arbitrary
	 * position weather it is inside, on or outside the polygon.
	 */
	public void changeAppState() {
		state.nextState();
		if (state.equals(EAppState.POLYG_DEF)) {
			this.polygon.clear();
			this.isConvexCheck = false;
			this.isFillingCheck = false;
		}
		System.out.println("Filling is now set to " + state + " .");
	}

	/**
	 * Set current mouse position.
	 * 
	 * @param x
	 *            Mouse coordinate x.
	 * @param y
	 *            Mouse coordinate y.
	 */
	public void setCurrentPosition(int x, int y) {
		curentMousePos.set(0, x).set(1, y);

	}

	/**
	 * Perform this method every time the user click with the mouse.
	 * 
	 * @param x
	 *            X coordinate of the click.
	 * @param y
	 *            Y coordinate of the click.
	 */
	public void userClicked(int x, int y) {
		if (state.equals(EAppState.POLYG_DEF)) {

			if (this.isConvexCheck) {
				IVector nextPoint = new Vector(x, y);
				if (polygon.checkConvex(nextPoint))
					this.polygon.add(nextPoint);
				else
					System.out.println("By adding the given point poylgon woudn't be convex anymore. The point is discarded.");
			} else {
				this.polygon.add(new Vector(x, y));
			}
		} else {
			EDotPolyRelation res;
			try {
				res = polygon.comparePointAndPolygon(new Vector(x, y));
			} catch (RuntimeException e) {
				System.out.println(e.getLocalizedMessage());
				return;
			}
			if (res.equals(EDotPolyRelation.INSIDE)) {
				System.out.println("Point (" + x + ", " + y + ") is on the inside the polygon. ");
			} else if (res.equals(EDotPolyRelation.OUTSIDE)) {
				System.out.println("Point (" + x + ", " + y + ") is on the outside of thy polygon.");
			} else {
				System.out.println("Point (" + x + ", " + y + ") is on the edge of the polygon.");
			}
		}
	}

	/**
	 * Return if the convex polygon check flag is set or not.
	 * 
	 * @return True if it is, false otherwise.
	 */
	public boolean isConvexCheckActive() {
		return this.isConvexCheck;
	}

	/**
	 * Fetch the points given by the user.
	 * 
	 * @return Points given by the user.
	 */
	public LinkedList<IVector> getUserPoints() {
		return this.polygon.asList();
	}

	/**
	 * Return the current mouse position memorised.
	 * 
	 * @return Current mouse position.
	 */
	public IVector getCurMouse() {
		return curentMousePos;
	}

	/**
	 * Check if the filling flag is active.
	 * 
	 * @return True if it is, false otherwise.
	 */
	public boolean isFillingActive() {
		return this.isFillingCheck;
	}

	/**
	 * Get the current state in which the application is.
	 * 
	 * @return Current application state.
	 */
	public AppStateWrapper getAppState() {
		return this.state;
	}

	/**
	 * Draw the polygon contained in this controller.
	 * 
	 * @param gl2
	 *            Canvas on which it should be drawn.
	 */
	public void drawPolygon(GL2 gl2) {
		if (this.getAppState().equals(EAppState.POLYG_DEF)) {
			polygon.tryDrawFilled(gl2, this.curentMousePos);
		} else {
			polygon.drawFilled(gl2);

		}
	}

}
