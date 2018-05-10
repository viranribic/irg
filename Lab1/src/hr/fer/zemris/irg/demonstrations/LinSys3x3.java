package hr.fer.zemris.irg.demonstrations;

import java.util.Scanner;

import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.IncompatibleOperandException;
import hr.fer.zemris.linearna.Matrix;
import hr.fer.zemris.linearna.Vector;
/**
 *  First lab assignment, linear equation solver.
 * @author Viran
 *
 */
public class LinSys3x3 {

	private static String eq1;
	private static String eq2;
	private static String eq3;

	public static void main(String[] args) throws IncompatibleOperandException {

		System.out.println("Write 3 equations with 3 variables.");
		System.out.println("a*x+b*y+c*z=d -> input: \" a b c d \" ");
		Scanner input = new Scanner(System.in);
		System.out.println("Equation 1:");
		eq1 = input.nextLine();
		System.out.println("Equation 2:");
		eq2 = input.nextLine();
		System.out.println("Equation 3:");
		eq3 = input.nextLine();
		input.close();

		eq1 = eq1.trim();
		eq2 = eq2.trim();
		eq3 = eq3.trim();

		int lastSpacePos1 = eq1.lastIndexOf(" ");
		int lastSpacePos2 = eq2.lastIndexOf(" ");
		int lastSpacePos3 = eq3.lastIndexOf(" ");

		String left1 = eq1.substring(0, lastSpacePos1);
		String left2 = eq2.substring(0, lastSpacePos2);
		String left3 = eq3.substring(0, lastSpacePos3);

		String right1 = eq1.substring(lastSpacePos1, eq1.length());
		String right2 = eq2.substring(lastSpacePos2, eq2.length());
		String right3 = eq3.substring(lastSpacePos3, eq3.length());

		String sMatrix = left1 + " | " + left2 + " | " + left3;

		IVector r = Vector.parseSimple(right1 + " " + right2 + " " + right3);
		IMatrix m = Matrix.parseSimple(sMatrix);

		IVector sol = m.nInvert().nMultiply(r.toColumnMatrix(true)).toVector(true);
		System.out.println("Result: (x y z)");
		System.out.println("(" + sol + ")");
	}
}
