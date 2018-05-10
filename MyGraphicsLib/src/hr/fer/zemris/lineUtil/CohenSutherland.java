package hr.fer.zemris.lineUtil;

import hr.fer.zemris.linearna.IVector;

/**
 * Cohen-Sutherland line clipping algorithm.
 * @author Viran
 *
 */
public class CohenSutherland {

	private static final short TOP = 0b1000;
	private static final short BOTTOM = 0b0100;
	private static final short LEFT = 0b0010;
	private static final short RIGHT = 0b0001;

	/**
	 * Check if the lines between the given points is in the visible range and modify the given 
	 * coordinates to display the visible segment.
	 * If the lines isn't visible at all modify the coordinates to point at positions -1,-1. 
	 * @param v1 Starting point of the line.
	 * @param v2 Ending point of the line.
	 * @param yMax Upper y value boundary. 
	 * @param yMin Lower y value boundary. 
	 * @param xMax Upper x value boundary. 
	 * @param xMin Lower x value boundary. 
	 */
	public static void checkLineCliping(IVector v1, IVector v2, double yMax, double yMin, double xMax, double xMin) {
		// TODO to make it more generic, add the boundaries to vectors and
		// vectors to a lost to
		// count the dimensions more easily
		short vec1Flags = genFlags(v1, yMax, yMin, xMax, xMin);
		short vec2Flags = genFlags(v2, yMax, yMin, xMax, xMin);

		int x1 = (int) v1.get(0);
		int x2 = (int) v2.get(0);
		int y1 = (int) v1.get(1);
		int y2 = (int) v2.get(1);

		while (true) {
			if (vec1Flags == 0 && vec2Flags == 0)
				return;
			else if ((vec1Flags & vec2Flags) != 0) {
				// set to not visible
				v1.set(0, -1);
				v1.set(1, -1);
				v2.set(0, -1);
				v2.set(1, -1);
				return;
			} else {
				// line clipping needed
				double x=0,y=0;

				short flags = vec1Flags != 0 ? vec1Flags : vec2Flags;
				if ((flags & TOP) != 0) {
					x = x1 + (x2 - x1) * (yMax - y1) / (y2 - y1);
					y = yMax;
				} else if ((flags & BOTTOM) != 0) {
					x = x1 + (x2 - x1) * (yMin - y1) / (y2 - y1);
					y = yMin;
				} else if ((flags & RIGHT) != 0) {
					y=y1+(y2-y1)*(xMax-x1)/(x2-x1);
					x=xMax;
				} else if ((flags & LEFT) != 0) {
					y=y1+(y2-y1)*(xMin-x1)/(x2-x1);
					x=xMin;
				}
				
				if(flags==vec1Flags){
					v1.set(0, x);
					v1.set(1, y);
					vec1Flags=genFlags(v1, yMax, yMin, xMax, xMin);
				}else{
					v2.set(0, x);
					v2.set(1, y);
					vec2Flags=genFlags(v2, yMax, yMin, xMax, xMin);					
				}
			}
		}

	}

	/**
	 * Generate the control flags of the given vector in correspondence to the boundary values.
	 * @param vec Tested vector.
	 * @param yMax Upper y value boundary. 
	 * @param yMin Lower y value boundary. 
	 * @param xMax Upper x value boundary. 
	 * @param xMin Lower x value boundary. 
	 * @return 4 bit short number containing the needed information.
	 */
	private static short genFlags(IVector vec, double yMax, double yMin, double xMax, double xMin) {
		if (vec.getDimension() == 2) {
			return gen2DFlags(vec, yMax, yMin, xMax, xMin);
		} else if (vec.getDimension() == 3) {
			//return gen3DFlags(vec, yMax, yMin, xMax, xMin);
		} else {
			System.out.println("CohenSutherland algorithm. Can't compute for vectors of dimensions greater than 3.");
			System.exit(-1);
			// throw new Exception("CohenSutherland algorithm. Can't compute for
			// vectors of dimensions greater than 3.");
		}
		return 0;
	}

	/**
	 * Generate the control flags of the given vector in correspondence to the boundary values.
	 * This method is a special case, processing the given vector for the 2D vector.
	 * Return type is a short variable type with the first 4 bits of the following representation:
	 * \t Bit 4: 1 if the y coordinate is greater than yMax.
	 * \t Bit 3: 1 if the y coordinate is less than yMin.
	 * \t Bit 2: 1 if the x coordinate is greater than xMax.
	 * \t Bit 1: 1 if the x coordinate is less than xMin.
	 * 
	 * @param vec Tested vector.
	 * @param yMax Upper y value boundary. 
	 * @param yMin Lower y value boundary. 
	 * @param xMax Upper x value boundary. 
	 * @param xMin Lower x value boundary. 
	 * @return 4 bit short number containing the needed information.
	 */
	private static short gen2DFlags(IVector vec, double yMax, double yMin, double xMax, double xMin) {
		short flags = 0;
		if (vec.get(1) > yMax)
			flags |= TOP; // else 0
		if (vec.get(1) < yMin)
			flags |= BOTTOM; // else 0
		if (vec.get(0) > xMax)
			flags |= RIGHT; // else 0
		if (vec.get(0) < xMin)
			flags |= LEFT; // else 0

		return flags;
	}

}
