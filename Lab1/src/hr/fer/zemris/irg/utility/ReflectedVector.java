package hr.fer.zemris.irg.utility;

import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.IncompatibleOperandException;
/**
 * 
 * @author Viran
 *
 */
public class ReflectedVector {

	/**
	 * Calculate the reflected vector for the given main vector, symmetrical with the reference to base vector.
	 * @param main Vector which reflection we look for.
	 * @param base Base vector around which the reflection takes place.
	 * @return Reflected vector.
	 * @throws IncompatibleOperandException Thrown if dimensions aren't compatible.
	 */
	public static IVector reflectedVector(IVector main, IVector base) throws IncompatibleOperandException{
		/*
		 * Notation (shorter):
		 * 		r:=result vector
		 * 		n:=base vector
		 * 		m:=main vector
		 * 
		 * Formula:
		 * 		r= 2* n/norm[n]*dot(n,m)/(norm[n])-m
		 */
		IVector reflected= main.newInstance(main.getDimension());
		
		reflected=base.nScalarMultiply(2);
		
		reflected=reflected.scalarMultiply(1/base.norm());
		
		reflected=reflected.scalarMultiply(base.dotProduct(main)/base.norm());
		
		reflected=reflected.sub(main);
		
		return reflected;
	}
}
