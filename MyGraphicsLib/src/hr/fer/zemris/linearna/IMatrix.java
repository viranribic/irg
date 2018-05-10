package hr.fer.zemris.linearna;

/**
 * Matrix interface.
 * @author Viran
 *
 */
public interface IMatrix {

	/**
	 * Get the number of rows in this matrix.
	 * @return Number of rows.
	 */
	public int getRowsCount();
	
	/**
	 * Get the number of rows in this matrix.
	 * @return Number of columns.
	 */
	public int getColsCount();
	
	/**
	 * Get matrix element.
	 * @param i Element row.
	 * @param j Element column.
	 * @return Matrix value.
	 */
	public double get(int i, int j);
	
	/**
	 * Set matrix element.
	 * @param i Element row.
	 * @param j Element column.
	 * @param val New value.
	 * @return This matrix object.
	 */
	public IMatrix set(int i, int j, double val);
	
	/**
	 * Make a copy of this matrix.
	 * @return Copy of this matrix.
	 */
	public IMatrix copy();
	
	/**
	 * Make a new instance of this object, keeping the dimensions with values set to zero.
	 * @param n Number of rows.
	 * @param m Number of columns.
	 * @return Newly created instance.
	 */
	public IMatrix newInstance(int n, int m);
	
	/**
	 * Transpose this matrix .
	 * @param liveView If set to true, the new matrix object shares the same matrix grid as this object.
	 * @return Transposed matrix object. 
	 */
	public IMatrix nTranspose(boolean liveView);
	
	/**
	 * Add the given matrix values to this matrix.
	 * @param mat Second operand.
	 * @return This matrix.
	 * @throws IncompatibleOperandException Thrown if dimensions aren't compatible.
	 */
	public IMatrix add(IMatrix mat) throws IncompatibleOperandException;
	
	/**
	 * Add the given matrix values to this matrix, and output the result into a new object.
	 * This method does not change this matrix values.
	 * @param mat Second operand.
	 * @return New matrix.
	 * @throws IncompatibleOperandException Thrown if dimensions aren't compatible.
	 */
	public IMatrix nAdd(IMatrix mat) throws IncompatibleOperandException;
	
	/**
	 * Subtract the given matrix values to this matrix.
	 * @param mat Second operand.
	 * @return This matrix.
	 * @throws IncompatibleOperandException Thrown if dimensions aren't compatible.
	 */
	public IMatrix sub(IMatrix mat) throws IncompatibleOperandException;
	
	/**
	 * Subtract the given matrix values to this matrix, and output the result into a new object.
	 * This method does not change this matrix values.
	 * @param mat Second operand.
	 * @return New matrix.
	 * @throws IncompatibleOperandException Thrown if dimensions aren't compatible.
	 */
	public IMatrix nSub(IMatrix mat) throws IncompatibleOperandException;
	
	/**
	 * Perform matrix multiplication using this and the given matrix.
	 * @param mat Second operand.
	 * @return Newly created matrix.
	 * @throws IncompatibleOperandException Thrown if dimensions aren't compatible.
	 */
	public IMatrix nMultiply(IMatrix mat) throws IncompatibleOperandException;
	
	/**
	 * Multiply this matrix with the scalar value provided as argument.
	 * @param scalar Scalar value for multiplication.
	 * @return This matrix.
	 */
	public IMatrix scalarMultiply(double scalar);
	
	/**
	 * Multiply this matrix with the scalar value provided as argument, and output the result into a new object.
	 * This method does not change this matrix values.
	 * @param scalar Scalar value for multiplication.
	 * @return New matrix.
	 */
	public IMatrix nScalarMultiply(double scalar);
	
	/**
	 * Calculates the determinant for this matrix.
	 * @return Determinant.
	 * @throws IncompatibleOperandException Thrown if this object isn't a square matrix.
	 */
	public double determinant() throws IncompatibleOperandException;
	
	/**
	 * Generates a submatrix from this matrix.
	 * @param n Row which will be eliminated from the original (this) matrix.
	 * @param m Column which will be eliminated from the original (this) matrix.
	 * @param liveView If set to true, the new matrix object shares the same matrix grid as this object.
	 * @return Generated submatrix object.
	 */
	public IMatrix subMatrix(int n, int m, boolean liveView);
	
	/**
	 * Invert this matrix.
	 * @return Inverted matrix.
	 * @throws IncompatibleOperandException Thrown if this object isn't a square matrix.
	 */
	public IMatrix nInvert() throws IncompatibleOperandException;
	
	/**
	 * Calculate the adjugate matrix for this object.
	 * @param changeCurrent If true, all operations are made on this element. Otherwise, this matrix remains unchanged.
	 * @return Adjugate matrix.
	 * @throws IncompatibleOperandException Thrown if this object isn't a square matrix.
	 */
	public IMatrix adjugate(boolean changeCurrent) throws IncompatibleOperandException;
	
	/**
	 * Calculate the cofactor matrix for this object.
	 * @param changeCurrent If true, all operations are made on this element. Otherwise, this matrix remains unchanged.
	 * @return Cofactor matrix.
	 * @throws IncompatibleOperandException Thrown if this object isn't a square matrix.
	 */
	
	public IMatrix cofactor(boolean changeCurrent) throws IncompatibleOperandException;
	
	/**
	 * Transform this matrix into a 2D array structure.
	 * @return 2D array of values.
	 */
	public double[][] toArray();
	
	/**
	 * Converts this matrix into a vector, if it has the dimension of (n x 1) or (1 x n).
	 * @param liveView If set to true, the new matrix object shares the same matrix grid as this object.
	 * @return Vector representation.
	 */
	public IVector toVector(boolean liveView);
	
	/**
	 * Multiply this matrix with the one given as argument, which results in a new product matrix.
	 * @param matrix Product matrix.
	 * @return New matrix.
	 * @throws IncompatibleOperandException If the given matrix is incompatible with this matrix for this operation, this exception is trown.
	 */
	public IMatrix nMatrixMultiply(IMatrix matrix) throws IncompatibleOperandException;
	
	
}