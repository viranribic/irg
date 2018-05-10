package hr.fer.zemris.irg.demonstrations;

import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.IncompatibleOperandException;
import hr.fer.zemris.linearna.Matrix;
import hr.fer.zemris.linearna.Vector;
/**
 * First lab assignment task 1.
 * @author Viran
 *
 */
public class Task1 {

	private static final String vec1="2 3 -4";
	private static final String vec2="-1 4 -3";
	private static final String vec3="2 2 4";
	
	private static final String mat1="1 2 3 | 2 1 3 | 4 5 1";
	private static final String mat2="-1 2 -3 | 5 -2 7 | -4 -1 3";
	private static final String mat3="-24 18 5 | 20 -15 -4 | -5 4 1";
	private static final String mat4="1 2 3 | 0 1 4 | 5 6 0";
	
	
	public static void main(String[] args) throws NumberFormatException, IncompatibleOperandException {
		//Vectors:
		IVector v1=Vector.parseSimple(vec1).add(Vector.parseSimple(vec2));
		System.out.println("v1:");
		System.out.println("\t"+v1);
		System.out.println();
		
		double s=v1.dotProduct(Vector.parseSimple(vec2));
		System.out.println("s:");
		System.out.println("\t"+s);
		System.out.println();
		
		IVector v2=v1.nVectorProduct(Vector.parseSimple(vec3));
		System.out.println("v2:");
		System.out.println("\t"+v2);
		System.out.println();
		
		IVector v3=v2.nNormalise();
		System.out.println("v3:");
		System.out.println("\t"+v3);
		System.out.println();
		
		IVector v4=v2.nScalarMultiply(-1);
		System.out.println("v4:");
		System.out.println("\t"+v4);
		System.out.println();
		
		//Matrices:
		IMatrix m1=Matrix.parseSimple(mat1).add(Matrix.parseSimple(mat2));
		System.out.println("M1:");
		System.out.println(m1);
		System.out.println();

		IMatrix m2=Matrix.parseSimple(mat1).nMultiply(Matrix.parseSimple(mat2).nTranspose(true));
		System.out.println("M2:");
		System.out.println(m2);
		System.out.println();

		IMatrix m3=Matrix.parseSimple(mat3).nInvert().nMultiply(Matrix.parseSimple(mat4).nInvert());
		System.out.println("M3:");
		System.out.println(m3);
		System.out.println();
	}
}
