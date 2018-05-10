package hr.fer.zemris.optimisationAlgorithms;

import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.IncompatibleOperandException;

public class OptimisationAlgorithms {

	public static boolean isAntiClockwise(IVector[] points2d) throws IncompatibleOperandException {
		for (int curIndex = 0; curIndex < points2d.length; curIndex++) {
			
				int nextIndex = (curIndex + 1) % points2d.length;
				IVector vC = points2d[curIndex];
				IVector vN = points2d[nextIndex];
				IVector dir = vC.nVectorProduct(vN);
				int nextNextIndex = (curIndex + 2) % points2d.length;
				IVector vNN = points2d[nextNextIndex];
				double scalar = dir.nDotProduct(vNN);
				if(scalar<0)
					return false; 
			
		}
		return true;
	}

}
