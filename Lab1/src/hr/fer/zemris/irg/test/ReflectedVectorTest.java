package hr.fer.zemris.irg.test;

import hr.fer.zemris.irg.utility.ReflectedVector;
import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.IncompatibleOperandException;
import hr.fer.zemris.linearna.Vector;

/**
 * Forth example.
 * @author Viran
 *
 */
public class ReflectedVectorTest {

	public static void main(String[] args) {
		IVector n=Vector.parseSimple("3 3");
		IVector m=Vector.parseSimple("2 3");
		
		System.out.println("Vrijednosti reflektiranog vektora: ");
		
		try {
			System.out.println(ReflectedVector.reflectedVector(m, n));
		} catch (IncompatibleOperandException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
