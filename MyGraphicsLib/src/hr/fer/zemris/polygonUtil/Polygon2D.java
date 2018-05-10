package hr.fer.zemris.polygonUtil;

import java.util.LinkedList;

import com.jogamp.opengl.GL2;

import hr.fer.zemris.lineUtil.Bresenhman;
import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.IncompatibleOperandException;
import hr.fer.zemris.linearna.Vector;

/**
 * Polygon representation for two dimension.
 * 
 * @author Viran
 *
 */
public class Polygon2D {

	/**
	 * Polygon element in two dimensions.
	 * 
	 * @author Viran
	 *
	 */
	private class PolyElements2D {

		public IVector point;
		public IVector dirVector;
		public boolean left;

		/**
		 * PolyElements2D constructor.
		 * 
		 * @param point
		 *            Point in space.
		 * @param dirVector
		 *            Direction to the next element in space.
		 * @param left
		 *            Marker weather the next position is higher in y value than
		 *            this position.
		 */
		public PolyElements2D(IVector point, IVector dirVector, boolean left) {
			this.point = point;
			this.dirVector = dirVector;
			this.left = left;
		}

		/**
		 * PolyElements2D constructor.
		 * 
		 * @param point
		 *            Point in space.
		 */
		public PolyElements2D(IVector point) {
			this(point, null, false);
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("Point: " + this.point + "\n");
			sb.append("Vector to next:" + this.dirVector + "\n");
			sb.append("Is left:" + this.left + "\n");

			return sb.toString();
		}

	}

	private LinkedList<PolyElements2D> elements = new LinkedList<>();
	private boolean isConvex = false;
	private boolean isClockwise = false;

	/**
	 * Polygon2D constructor.
	 */
	public Polygon2D() {
	}

	/**
	 * Polygon2D constructor.
	 * 
	 * @param points
	 *            Point array from which the polygon is going to be built.
	 */
	public Polygon2D(IVector... points) {
		this(arrayToList(points));
	}

	/**
	 * Polygon2D constructor.
	 * 
	 * @param points
	 *            Point list from which the polygon is going to be built.
	 */
	public Polygon2D(LinkedList<IVector> points) {
		LinkedList<IVector> pointsC = new LinkedList<>(points);
		LinkedList<IVector> pointsHom = PolygonUtil.getHomogeneous(pointsC);
		if (PolygonUtil.isClockwise(pointsHom)) {
			this.isConvex = true;
			this.isClockwise = true;
		} else if (PolygonUtil.isCounterClockwise(pointsHom)) {
			this.isConvex = true;
			this.isClockwise = false;
			// Collections.reverse(pointsHom);
		} else {
			this.isConvex = false;
			for (int i = 0; i < pointsHom.size(); i++)
				elements.add(new PolyElements2D(pointsHom.get(i)));
			return;
			// throw new IllegalArgumentException("The given array is neither
			// clockwise or counterclockwise oriented.");
		}
		LinkedList<IVector> dirVectors = PolygonUtil.getDirVectors(pointsHom);
		LinkedList<Boolean> areLeft = PolygonUtil.getLeftFlags(pointsHom);

		for (int i = 0; i < pointsHom.size(); i++)
			elements.add(new PolyElements2D(pointsHom.get(i), dirVectors.get(i), areLeft.get(i)));

	}

	/**
	 * For the given vector array, make it a LinkedList object.
	 * 
	 * @param points
	 *            Vectors as array.
	 * @return Vectors as list.
	 */
	private static LinkedList<IVector> arrayToList(IVector[] points) {
		LinkedList<IVector> pointsLL = new LinkedList<>();
		for (int i = 0; i < points.length; i++)
			pointsLL.add(points[i]);
		return pointsLL;
	}

	/**
	 * Check if the polygon is convex.
	 * 
	 * @return True if the polygon is convex, false if it is concave.
	 */
	public boolean checkConvex() {
		return isConvex;
	}

	/**
	 * Clear the contents (points) of this polygon.
	 */
	public void clear() {
		this.elements.clear();

	}

	/**
	 * Check if adding the given point would yield in a convex polygon.
	 * 
	 * @param nextPoint
	 *            Point to be added.
	 * @return True if the polygon would remain convex, false otherwise.
	 */
	public boolean checkConvex(IVector nextPoint) {
		LinkedList<IVector> points = this.asList();

		if (this.isConvex) {
			// if it is convex adding this point it may stay convex...
			if (this.isClockwise) {
				// if already clockwise adding next point the polygon must stay
				// clockwise
				points.addLast(nextPoint);
				if (PolygonUtil.isClockwise(points))
					return true;
			} else {
				// if already counterclockwise adding next point the polygon
				// must stay counterclockwise
				points.addLast(nextPoint);
				if (PolygonUtil.isCounterClockwise(points))
					return true;
			}
		}
		// ..if it isn't convex no need in further checking, it will stay
		// concave.

		return false;
	}

	/**
	 * Add the given point to this polygon.
	 * 
	 * @param nextPoint
	 *            Next point to be added to polygon.
	 * @return True if the operation is successful, false otherwise.
	 */
	public boolean add(IVector nextPoint) {
		if (elements.size() < 2) {
			IVector nxtPtH = PolygonUtil.toHomogeneous(nextPoint);
			return elements.add(new PolyElements2D(nxtPtH, null, false));
		} else if (elements.size() == 2) {

			LinkedList<IVector> list = this.asList();
			list.add(nextPoint);
			Polygon2D poly = new Polygon2D(list);

			this.elements = poly.elements;
			this.isClockwise = poly.isClockwise;
			this.isConvex = poly.isConvex;

			return true;
		} else {
			if (this.isConvex)
				return addForConvexPoly(nextPoint);
			else
				return addForConcavePoly(nextPoint);
		}
	}

	/**
	 * Point addition for a concave polygon.
	 * 
	 * @param nextPoint
	 *            Point to be added.
	 * @return True if successful, false otherwise.
	 */
	private boolean addForConcavePoly(IVector nextPoint) {
		IVector nxtPtH = PolygonUtil.toHomogeneous(nextPoint);
		return elements.add(new PolyElements2D(nxtPtH, null, false));
	}

	/**
	 * Point addition for a convex polygon.
	 * 
	 * @param nextPoint
	 *            Point to be added.
	 * @return True if successful, false otherwise.
	 */
	private boolean addForConvexPoly(IVector nextPoint) {
		// if clockwise -> add last | if counterclockwise add first
		PolyElements2D nextElement;
		PolyElements2D previousElement;

		nextElement = elements.getFirst();
		previousElement = elements.getLast();

		IVector nxtPtH = PolygonUtil.toHomogeneous(nextPoint);
		IVector nxtFirstDir = PolygonUtil.getDirVector(nxtPtH, nextElement.point);
		IVector lastNxtDir = PolygonUtil.getDirVector(previousElement.point, nxtPtH);

		boolean isLeft = PolygonUtil.getIsLeft(nxtPtH, nextElement.point);
		boolean isLeftPrev = PolygonUtil.getIsLeft(previousElement.point, nxtPtH);

		previousElement.dirVector = lastNxtDir;
		previousElement.left = isLeftPrev;

		elements.add(new PolyElements2D(nxtPtH, nxtFirstDir, isLeft));

		if (this.isClockwise) {
			if (PolygonUtil.isClockwise(this.asList())) {
				return true;
			} else {
				this.isClockwise = false;
				this.isConvex = false;
				return false;
			}
		} else {
			if (PolygonUtil.isCounterClockwise(this.asList())) {
				return true;
			} else {
				this.isClockwise = false;
				this.isConvex = false;
				return false;
			}
		}
	}

	/**
	 * Make a comparison between the point and the polygon.
	 * 
	 * @param point
	 *            Point we compare to this polygon.
	 * @return One of the EDotPolyRelation values.
	 */
	public EDotPolyRelation comparePointAndPolygon(IVector point) {
		point = PolygonUtil.toHomogeneous(point);
		if (this.isConvex) {
			LinkedList<PolyElements2D> elements = new LinkedList<>(this.elements);
			if (!this.isClockwise) {

				int greaterCounter = 0;
				int zeroCounter = 0;

				for (PolyElements2D element : elements) {
					double scalar = 0;
					try {
						scalar = element.dirVector.scalarProduct(point);
					} catch (IncompatibleOperandException e) {
					}
					if (scalar < 0)
						greaterCounter++;
					else if (scalar == 0)
						zeroCounter++;
				}

				if (greaterCounter == elements.size())
					return EDotPolyRelation.INSIDE;
				else if (zeroCounter > 0 && zeroCounter + greaterCounter == elements.size())
					return EDotPolyRelation.ON_EDGE;
				else
					return EDotPolyRelation.OUTSIDE;

			} else {

				int greaterCounter = 0;
				int zeroCounter = 0;

				for (PolyElements2D element : elements) {
					double scalar = 0;
					try {
						scalar = element.dirVector.scalarProduct(point);
					} catch (IncompatibleOperandException e) {
					}
					if (scalar > 0)
						greaterCounter++;
					else if (scalar == 0)
						zeroCounter++;
				}

				if (greaterCounter == elements.size())
					return EDotPolyRelation.INSIDE;
				else if (zeroCounter > 0 && zeroCounter + greaterCounter == elements.size())
					return EDotPolyRelation.ON_EDGE;
				else
					return EDotPolyRelation.OUTSIDE;

			}
		} else
			throw new IllegalArgumentException(
					"This polygon is not convex and therefor the relationsnihp can't be computed.");
	}

	/**
	 * Return a array of this polygons positions as 2D vectors.
	 * 
	 * @return Array of positions.
	 */
	public LinkedList<IVector> asList() {
		LinkedList<IVector> list = new LinkedList<>();
		for (PolyElements2D ele : elements)
			try {
				list.add(ele.point.nFromHomogeneus());
			} catch (IncompatibleOperandException e) {
				System.out.println("List coudn't be converted from homogenus points.");
				System.exit(-1);
				return null;
			}
		return list;
	}

	/**
	 * Try to draw this polygon on canvas taking into consideration the
	 * curentMousePosition as one of the polygon elements.
	 * 
	 * @param gl2
	 *            Canvas.
	 * @param curMouse
	 *            Current mouse coordinates.
	 * @throws IllegalArgumentException
	 *             Thrown if by adding the current mouse position this polygon
	 *             would become concave.
	 */
	public void tryDrawFilled(GL2 gl2, IVector curMouse) throws IllegalArgumentException {
		if (this.checkConvex(curMouse)) {
			Polygon2D copy = this.duplicate();
			copy.add(curMouse);
			copy.drawFilled(gl2);
		} else
			throw new IllegalArgumentException(
					"Mouse is not on a position on which the filled polygon could be drawn.");
	}

	/**
	 * Draw the filled polygon on the given canvas. Works only for
	 * counterclockwise elements.
	 * 
	 * @param gl2
	 *            Canvas on which the polygon should be drawn.
	 */
	public void drawFilled(GL2 gl2) {
		if (!this.isConvex)
			return;
		int i, i0, y;
		int xmin, xmax, ymin, ymax;
		double L, D, x;

		// initialise drawable object
		gl2.glColor3f(0.f, 0.f, 0.f);
		// Minimal and maximal coordinates:
		xmin = xmax = (int) elements.get(0).point.get(0);
		ymin = ymax = (int) elements.get(0).point.get(1);

		for (PolyElements2D element : elements) {
			if (xmin > element.point.get(0))
				xmin = (int) element.point.get(0);
			if (xmax < element.point.get(0))
				xmax = (int) element.point.get(0);
			if (ymin > element.point.get(1))
				ymin = (int) element.point.get(1);
			if (ymax < element.point.get(1))
				ymax = (int) element.point.get(1);
		}

		// Colour for every y between ymin and ymax
		for (y = ymin; y <= ymax; y++) {
			L = xmin;
			D = xmax;
			i0 = elements.size() - 1;
			// i0 is the beginning of the edge and i is its end
			for (i = 0; i < elements.size(); i0 = i++) {
				// if the edge is horizontal
				if (elements.get(i0).dirVector.get(0) == 0.) {
					if (elements.get(i0).point.get(1) == y) {
						if (elements.get(i0).point.get(0) < elements.get(i).point.get(0)) {
							L = elements.get(i0).point.get(0);
							D = elements.get(i).point.get(0);
						} else {
							L = elements.get(i).point.get(0);
							D = elements.get(i0).point.get(0);
						}
						break;
					}
				} else {
					// regular edge, find where do the vectors meet
					x = (-elements.get(i0).dirVector.get(1) * y - elements.get(i0).dirVector.get(2))
							/ (elements.get(i0).dirVector.get(0));

					if (this.isClockwise) {

						if (!elements.get(i0).left) {
							if (L < x)
								L = x;
						} else {
							if (D > x)
								D = x;
						}
					} else {
						if (elements.get(i0).left) {
							if (L < x)
								L = x;
						} else {
							if (D > x)
								D = x;
						}
					}
				}
			}
			// draw line
			Bresenhman.drawLine(gl2, new Vector(L, y), new Vector(D, y));

		}
	}

	/**
	 * Make a duplicate of this polygon.
	 * 
	 * @return This polygons duplicate.
	 */
	public Polygon2D duplicate() {
		Polygon2D clone = new Polygon2D();
		clone.isConvex = this.isConvex;
		clone.isClockwise = this.isClockwise;
		for (PolyElements2D ele : elements)
			clone.elements.add(new PolyElements2D(ele.point, ele.dirVector, ele.left));
		return clone;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int counter = 1;
		sb.append("Polygon:\n");
		for (PolyElements2D element : elements)
			sb.append("\tElement " + (counter++) + " :\n" + element);

		return sb.toString();
	}
}