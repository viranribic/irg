package hr.fer.zemris.graphicsOperators;

import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.IncompatibleOperandException;
import hr.fer.zemris.linearna.Matrix;
import hr.fer.zemris.linearna.Vector;

public class MatrixOpFactory {

	public static IMatrix get(EMatrixType type, Object... parameters) {
		switch (type) {
		case TRANSLATION:return buildTranslation(parameters);
		case ROTATION_X: return buildRotX(parameters);
		case ROTATION_Y: return buildRotY(parameters);
		case ROTATION_Z: return buildRotZ(parameters);
		case SHEAR_MATRIX: return buildShear(parameters);
		case SCALING: return buildScaling(parameters);
		case Z_PARALLEL_PROJECTION: return buildZParallelProjection(parameters);
		case Z_PERSPECTIVE_PROJECTION: return buildZPerspectiveProjection(parameters);
		case LOOK_AT_MATRIX: return buildLookAt(parameters);
		case FRUSTUM_MATRIX: return buildFrustum(parameters);
		default: return null;
		}
	}

	private static IMatrix buildFrustum(Object[] parameters) {
		if(parameters.length!=6)
			throw new IllegalArgumentException("Number of parameters for frustum matix must be six double values as follows: left, right, bottom and top distances from the center as well as near and far distances from the viewers eyes.");
		float left=(Float)parameters[0];
		float right=(Float)parameters[1];
		float bottom=(Float)parameters[2];
		float top=(Float)parameters[3];
		float near=(Float)parameters[4];
		float far=(Float)parameters[5];
		
		float m00= 2*near/(right-left);
		float m11= 2*near/(top-bottom);
		float m20= (right+left)/(right-left);
		float m21= (top+bottom)/(top-bottom);
		float m22= -(far+near)/(far-near);
		float m23= -1;
		float m32= -2*far*near/(far-near);
		IVector v1=new Vector(m00,  0,  0,  0);
		IVector v2=new Vector(  0,m11,  0,  0);
		IVector v3=new Vector(m20,m21,m22,m23);
		IVector v4=new Vector(  0,  0,m32,  0);
		IMatrix matrix=new Matrix(new IVector[]{v1,v2,v3,v4});
		return matrix;
	}

	private static IMatrix buildLookAt(Object[] parameters) {
		if(parameters.length!=3)
			throw new IllegalArgumentException("Number of parameters for lookup matix must be three IVector objects as floows: eye coordinate, center coordinate and viewUp vector.");
		IVector eye= (IVector) parameters[0];
		IVector center= (IVector) parameters[1];
		IVector viewUp= (IVector) parameters[2];
		//normirani vektori! paziti na konvenciju uz koju je matrica zadana! 
		// konvencija mnozenja tocke s matricom
		//desni sustav
		try {
			IVector forward = center.nSub(eye);
			IVector up=viewUp.copy();
			forward.normalise();
			IVector side=forward.nVectorProduct(up);
			side.normalise();
			up=side.nVectorProduct(forward);
			side=side.copyPart(4);
			up=up.copyPart(4);
			forward=forward.copyPart(4);
			IMatrix M=new Matrix(new IVector[]{ side, up, forward.scalarMultiply(-1), new Vector(0,0,0,1)});
			M=M.nTranspose(true);
			return MatrixOpFactory.get(EMatrixType.TRANSLATION, -eye.get(0), -eye.get(1), -eye.get(2)).nMultiply(M);
		} catch (IncompatibleOperandException e) {
			return null;
		}
	}

	private static IMatrix buildZPerspectiveProjection(Object[] parameters) {
		if(parameters.length!=1)
			throw new IllegalArgumentException("Number of parameters for z projection matix must be only one: surface on z axis with value H paralel to xy.");
		
		double H=(Double)parameters[0];
		
		return new Matrix(
				new double[][]{
						{1, 0, 0,   0},
						{0, 1, 0,   0},
						{0, 0, 1, 1/H},
						{0, 0, 0,   0}	
				},4,4
			);
	}

	private static IMatrix buildZParallelProjection(Object[] parameters) {
		if(parameters.length!=1)
			throw new IllegalArgumentException("Number of parameters for z projection matix must be only one: surface on z axis with value H paralel to xy.");
		
		double H=(Double)parameters[0];
		
		return new Matrix(
				new double[][]{
						{1, 0, 0, 0},
						{0, 1, 0, 0},
						{0, 0, 1, 0},
						{0, 0, -H, 1}	
				},4,4
			);
	}

	private static IMatrix buildScaling(Object[] parameters) {
		if(parameters.length!=3)
			throw new IllegalArgumentException("Number of parameters for scaling matix must be three: scale coefficient for x,y and z coordinates.");
		
		double x=(Double)parameters[0];
		double y=(Double)parameters[1];
		double z=(Double)parameters[2];
				
//		return  new Matrix(
//				new Vector(x, 0, 0, 0),
//				new Vector(0, y, 0, 0),
//				new Vector(0, 0, z, 0),
//				new Vector(0, 0, 0, 1)
//				
//				);
		
		return new Matrix(
					new double[][]{
							{x, 0, 0, 0},
							{0, y, 0, 0},
							{0, 0, z, 0},
							{0, 0, 0, 1}	
					},4,4
				);
	}

	private static IMatrix buildShear(Object... parameters) {
		if(parameters.length!=3)
			throw new IllegalArgumentException("Number of parameters for shear matix must be three: rotation around x,y and z axis.");
		
		double alpha=(Double)parameters[0];
		double beta=(Double)parameters[1];
		double gama=(Double)parameters[2];
		
		double tgA=Math.tan(alpha);
		double tgB=Math.tan(beta);
		double tgG=Math.tan(gama);
		
		return new Matrix(
				new double[][]{
						{   1, tgA, tgA, 0},
						{ tgB,   1, tgB, 0},
						{ tgG, tgG,   1, 0},
						{   0,   0,   0, 1}	
				},4,4
			);
	}

	private static IMatrix buildRotZ(Object... parameters) {
		if(parameters.length!=1)
			throw new IllegalArgumentException("Number of parameters for rotation matix must be one: the rotation angle along the z axis, in counterclockwise direction.");
		
		double gama=(Double)parameters[0];
		double cos=Math.cos(gama);
		double sin=Math.sin(gama);
		
		return new Matrix(
				new double[][]{
						{ cos, sin, 0, 0},
						{-sin, cos, 0, 0},
						{   0,   0, 1, 0},
						{   0,   0, 0, 1}	
				},4,4
			);
	}

	private static IMatrix buildRotY(Object... parameters) {
		if(parameters.length!=1)
			throw new IllegalArgumentException("Number of parameters for rotation matix must be one: the rotation angle along the y axis, in counterclockwise direction.");
		
		double beta=(Double)parameters[0];
		double cos=Math.cos(beta);
		double sin=Math.sin(beta);
				
//		return  new Matrix(
//				new Vector( cos, 0,-sin, 0),
//				new Vector(   0, 1,   0, 0),
//				new Vector( sin, 0, cos, 0),
//				new Vector(   0, 0,   0, 1)
//				
//				);
		
		return new Matrix(
				new double[][]{
						{ cos, 0,-sin, 0},
						{   0, 1,   0, 0},
						{ sin, 0, cos, 0},
						{   0, 0,   0, 1}	
				},4,4
			);
	}

	private static IMatrix buildRotX(Object... parameters) {
		if(parameters.length!=1)
			throw new IllegalArgumentException("Number of parameters for rotation matix must be one: the rotation angle along the x axis, in counterclockwise direction.");
		
		double alpha=(Double)parameters[0];
		double cos=Math.cos(alpha);
		double sin=Math.sin(alpha);
				
//		return  new Matrix(
//				new Vector( 1,   0,   0, 0),
//				new Vector( 0, cos, sin, 0),
//				new Vector( 0,-sin, cos, 0),
//				new Vector( 0,   0,   0, 1)
//				
//				);
		
		return new Matrix(
				new double[][]{
						{ 1,   0,   0, 0},
						{ 0, cos, sin, 0},
						{ 0,-sin, cos, 0},
						{ 0,   0,   0, 1}	
				},4,4
			);
	}

	private static IMatrix buildTranslation(Object... parameters) {
		if(parameters.length!=3)
			throw new IllegalArgumentException("Number of parameters for translation matix must be three: offset for x,y and z coordinates.");
		
		double x=(Double)parameters[0];
		double y=(Double)parameters[1];
		double z=(Double)parameters[2];
				
//		return  new Matrix(
//				new Vector(1, 0, 0, 0),
//				new Vector(0, 1, 0, 0),
//				new Vector(0, 0, 1, 0),
//				new Vector(x, y, z, 1)
//				
//				);
		
		return new Matrix(
				new double[][]{
						{1, 0, 0, 0},
						{0, 1, 0, 0},
						{0, 0, 1, 0},
						{x, y, z, 1}	
				},4,4
			);
	}


}
