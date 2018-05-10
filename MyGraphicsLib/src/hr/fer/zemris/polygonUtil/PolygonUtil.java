package hr.fer.zemris.polygonUtil;

import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.IncompatibleOperandException;

/**
 * Utility class for working with polygons.
 * @author Viran
 *
 */
public class PolygonUtil {

	/**
	 * Check if the given list can form a clockwise polygon.
	 * @param points Polygon candidate.
	 * @return True if a clockwise polygon can be made, false otherwise.
	 */
	public static boolean isClockwise(List<IVector> points) {
		return genericDirectionTest(points, (Double o1, Double o2) -> o1 >= o2);	// in display system o1 <= o2 for clockwise must be o1 >= o2
		
	}

	/**
	 * Check if the given list can form a counterclockwise polygon.
	 * @param points Polygon candidate.
	 * @return True if a counterclockwise polygon can be made, false otherwise.
	 */
	public static boolean isCounterClockwise(List<IVector> points) {
		return genericDirectionTest(points, (Double o1, Double o2) -> o1 <= o2);	// in display system o1 >= o2 for clockwise must be o1 <= o2
	}

	/**
	 * Generic clockwise/counterclockwise tester.  
	 * @param points List of points which should be tested.
	 * @param comparator Comparator set to (Double o1, Double o2) -> o1 >= o2 for isClockwise, or (Double o1, Double o2) -> o1 >= o2 for isCounterClockwise methods.
	 * @return True if a convex polygon can be made, false otherwise.
	 */
	private static boolean genericDirectionTest(List<IVector> points, EqCompare<Double> comparator) {
		if(points.size()<3)
			throw new IllegalArgumentException("The given list can't construct a polyogon.");
		
		LinkedList<IVector> homCoords = PolygonUtil.getHomogeneous(points);
		LinkedList<IVector> dirVectors = PolygonUtil.getDirVectors(homCoords);
		boolean result = true;
		int size = dirVectors.size();
		try {
			for (int i = 0, j = 2; i < size; i++, j = (j + 1) % size) {
				IVector t = homCoords.get(j);
				IVector b = dirVectors.get(i);
				if (!comparator.isTrue(t.scalarProduct(b), 0.)) {
					result = false;
					break;
				}
			}
		} catch (IncompatibleOperandException e) {
			System.out.println("Scalar product is undefined for the given vectors during polygon direction test.");
			System.exit(-1);
			return false;
		}
		return result;
	}

	/**
	 * Build a list of flag describing if a set of points make a left polygon edge.
	 * @param pointsHom Points to be tested.
	 * @return Set of isLeft values.
	 */
	public static LinkedList<Boolean> getLeftFlags(List<IVector> pointsHom) {
		if(pointsHom.size()<1)
			throw new IllegalArgumentException("At least 2 points need to be given.");
		LinkedList<Boolean> dir = new LinkedList<>();
		int size = pointsHom.size();
		for (int i = 0, j = 1; i < size; i++, j = (j + 1) % size)
			dir.add(getIsLeft(pointsHom.get(i), pointsHom.get(j)));
		return dir;
	}

	/**
	 * 
	 * @param t1
	 * @param t2
	 * @return
	 */
	public static Boolean getIsLeft(IVector t1, IVector t2) {
		return t1.get(1) < t2.get(1);
	}

	/**
	 * 
	 * @param pointsHom
	 * @return
	 */
	public static LinkedList<IVector> getDirVectors(List<IVector> pointsHom) {
		if(pointsHom.size()<1)
			throw new IllegalArgumentException("At least 2 points need to be given.");
		LinkedList<IVector> dir = new LinkedList<>();
		int size = pointsHom.size();
		for (int i = 0, j = 1; i < size; i++, j = (j + 1) % size)
			dir.add(getDirVector(pointsHom.get(i), pointsHom.get(j)));
		return dir;
	}

	/**
	 * 
	 * @param t1
	 * @param t2
	 * @return
	 */
	public static IVector getDirVector(IVector t1, IVector t2) {
		try {
			return t1.nVectorProduct(t2);
		} catch (IncompatibleOperandException e) {
			System.out.println("Direction vector coudn't be calculated for t1=" + t1 + " t2=" + t2 + " .");
			System.exit(-1);
			return null;
		}
	}

	/**
	 * Transform the given 2D vectors into homogeneous vectors.
	 * @param points List of points in 2D.
	 * @return A list of homogeneous points in 2D.
	 */
	public static LinkedList<IVector> getHomogeneous(List<IVector> points) {
		LinkedList<IVector> hom = new LinkedList<>();
		for (IVector pt : points)
			hom.add(toHomogeneous(pt));
		return hom;
	}

	/**
	 * Transform the given 2D vector into a homogeneous.
	 * @param pt A point in 2D.
	 * @return A homogeneous point in 2D.
	 */
	public static IVector toHomogeneous(IVector pt) {
		IVector ptH;
		try {
			ptH = pt.copyPart(pt.getDimension()+1);
			ptH.set(pt.getDimension(), 1);
			return ptH;
		} catch (IncompatibleOperandException e) {
			System.out.println("To homogenus coordinate transformation coudn't be made.");
			System.exit(-1);
			return null;
		}

	}

	
}
