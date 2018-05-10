package hr.fer.zemris.irg.test;

import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.IncompatibleOperandException;
import hr.fer.zemris.linearna.Matrix;
import hr.fer.zemris.linearna.Vector;
/**
 * Third example.
 * @author Viran
 *
 */
public class BarycentricCoordTest2 {

	public static void main(String[] args) {
		IMatrix a=Matrix.parseSimple(" 1 5 3 | 0 0 8 | 1 1 1");
		IVector b=Vector.parseSimple(" 3 4 1");
		try {
			IVector res=a.nInvert().nMultiply(b.toColumnMatrix(true)).toVector(true);
			System.out.println("Trazene baricentricne koordinate su:");
			System.out.println(res);
		} catch (IncompatibleOperandException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
