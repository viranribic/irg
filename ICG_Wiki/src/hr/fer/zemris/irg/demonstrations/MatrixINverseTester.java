package hr.fer.zemris.irg.demonstrations;

import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.IncompatibleOperandException;
import hr.fer.zemris.linearna.Matrix;
import hr.fer.zemris.linearna.Vector;

public class MatrixINverseTester {

	private static final double t0=0.8;
	private static final double t1=0.3;
	private static final double t2=0.9;
	private static final double t3=0.6;
	
	private static final IVector vA= new Vector(9.56, 2.88);
	private static final IVector vB= new Vector(3.58, 2.40 );
	private static final IVector vC= new Vector(6.00, 6.51);
	private static final IVector vD= new Vector(12.26, 0.32);
	
	
	
	
	public static void main(String[] args) {
		IMatrix B=new Matrix(
					new Vector(-1, 3,-3, 1),
					new Vector( 3,-6, 3, 0),
					new Vector(-3, 3, 0, 0),
					new Vector( 1, 0, 0, 0)					
				);
		
		IMatrix T=new Matrix(
				new Vector( Math.pow(t0, 3), Math.pow(t0, 2), Math.pow(t0, 1), Math.pow(t0, 0)),
				new Vector( Math.pow(t1, 3), Math.pow(t1, 2), Math.pow(t1, 1), Math.pow(t1, 0)),
				new Vector( Math.pow(t2, 3), Math.pow(t2, 2), Math.pow(t2, 1), Math.pow(t2, 0)),
				new Vector( Math.pow(t3, 3), Math.pow(t3, 2), Math.pow(t3, 1), Math.pow(t3, 0))					
			);
	
		IMatrix P=new Matrix(
				vA,
				vB,
				vC,
				vD
				);
	
		System.out.println(B+"\n");
		System.out.println(T+"\n");
		System.out.println(P+"\n");
		try {
			System.out.println(B.nInvert().nMatrixMultiply(T.nInvert()).nMatrixMultiply(P));
		} catch (IncompatibleOperandException e) {
			System.out.println("there was an error.");
		}
	}
}
