package hr.fer.zemris.irg.test;

import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.IncompatibleOperandException;
import hr.fer.zemris.linearna.Matrix;

/**
 * Second example.
 * @author Viran
 *
 */
public class EquationSystem {

	public static void main(String[] args) {
		IMatrix a= Matrix.parseSimple("3 5 | 2 10");
		IMatrix r= Matrix.parseSimple("2 | 8");
		IMatrix v;
		try {
			v = a.nInvert().nMultiply(r);
			System.out.println("Rjesenje sustava je: ");
			System.out.println(v);
		} catch (IncompatibleOperandException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
