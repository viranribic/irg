package hr.fer.zemris.irg.lab8;

import java.util.LinkedList;

import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.IncompatibleOperandException;
import hr.fer.zemris.linearna.Matrix;
import hr.fer.zemris.linearna.Vector;

public class tester {

	public static void main(String[] args) {
		LinkedList<IVector> points = new LinkedList<>();
		points.add(new Vector(1,0));
		points.add(new Vector(0,0));
		points.add(new Vector(0,1));
		
		int div=20;
		IMatrix BiTiP=generateInterpolationControlPoints(points);
		IMatrix B = generateCoefMatric(points.size());
		IMatrix TiP= null;
		try {TiP=B.nMatrixMultiply(BiTiP);} catch (IncompatibleOperandException e) {}
		for(int i=0;i<=div;i++){
			
		}
		
	}

	public static void printM(double[][] matrix, int rows, int cols) {
		if (rows <= 0 || cols <= 0)
			return;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++)
				System.out.printf("%2.0f ", matrix[i][j]);
			System.out.println();
		}
	}

	public static IMatrix generateInterpolationControlPoints(LinkedList<IVector> points) {
		if (points.size() <= 2)
			return null;
		//IVector[] controlPoints = new IVector[points.size()];
		IVector[] pointsArray = new IVector[points.size()];
		IMatrix P = new Matrix(points.toArray(pointsArray));
		IMatrix T = generateParamtersMatrix(pointsArray.length);
		IMatrix B = generateCoefMatric(pointsArray.length);
		IMatrix BiTiP = null;
		IMatrix Bi = null;
		IMatrix Ti = null;
		IMatrix BiTi=null;
		try {
			Bi=B.nInvert();
			Ti=T.nInvert();
			BiTi=Bi.nMatrixMultiply(Ti);
			BiTiP=BiTi.nMatrixMultiply(P);
		} catch (IncompatibleOperandException e) {
		}
		System.out.println("Matrix P:\n"+P);
		System.out.println("Matrix T:\n"+T);
		System.out.println("Matrix B:\n"+B);
		System.out.println("Matrix Bi:\n"+Bi);
		System.out.println("Matrix Ti:\n"+Ti);
		System.out.println("Matrix BiTi:\n"+BiTi);
		System.out.println("Matrix BiTiP:\n"+BiTiP);
		
		return BiTiP;
//
//		double[][] matrix = BiTiP.toArray();
//		for (int i = 0; i < controlPoints.length; i++)
//			controlPoints[i]=new Vector(matrix[i]);
//		return controlPoints;
	}

	private static IMatrix generateCoefMatric(int order) {
		double[][] matrix = generateBernsteinMatix(order);
		IVector[] vectors = new IVector[order];

		for (int i = 0; i < order; i++)
			vectors[i] = new Vector(matrix[i]);

		return new Matrix(vectors);
	}

	private static double[][] generateBernsteinMatix(int order) {
		if (order <= 0)
			return new double[][] { { 1 } };
		double[] binCoefs = generateBinaryCoeficients(order);

		double[][] matrix = new double[order][order];

		for (int i = 0; i < order; i++) {
			double[] polyCoefs = generateBinaryPolynomCoefs(order - i);

			for (int j = 0; j < polyCoefs.length; j++) {
				matrix[i][j] = binCoefs[i] * polyCoefs[j];

			}
		}

		return matrix;
	}

	private static double[] generateBinaryPolynomCoefs(int order) {
		if (order <= 0)
			return new double[] { 1 };
		double[] binaryCoeficinets = generateBinaryCoeficients(order);
		for (int i = 0; i < binaryCoeficinets.length; i++)
			if (i % 2 == 1)
				binaryCoeficinets[i] *= -1;
		return binaryCoeficinets;
	}

	private static double[] generateBinaryCoeficients(int size) {
		if (size <= 0)
			return new double[] { 1 };
		double[] array = new double[size];
		double n = size - 1;
		double nCk = 1;
		int loop = (size % 2 == 0) ? size / 2 : size / 2 + 1;
		for (int k = 0; k < loop; k++) {
			array[k] = array[size - k - 1] = nCk;
			nCk *= (n - k) / (k + 1);
		}
		return array;
	}

	private static IMatrix generateParamtersMatrix(int dim) {
		IVector[] rows = new IVector[dim];

		for (int dimIndex = 0; dimIndex < dim; dimIndex++) {
			double t = ((double) dimIndex) / (dim-1); //it just has to be -1

			double[] rowVals = new double[dim];
			// last position is always 1
			rowVals[dim - 1] = 1;
			for (int index = dim - 2; index >= 0; index--) {
				rowVals[index] = t;
				t *= t;
			}
			rows[dimIndex] = new Vector(rowVals);

		}

		return new Matrix(rows);
	}


}
