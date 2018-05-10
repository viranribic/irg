package hr.fer.zemris.linearna;

/**
 * Vector interface.
 * @author Viran
 *
 */
public interface IVector {

	/**
	 * For the given dimension, provide the corresponding value.
	 * @param dim Vector dimension.
	 * @return Value of the given dimension.
	 */
	public double get(int dim);
	
	/**
	 * Set the new value at the given dimension.
	 * @param dim Dimension modified.
	 * @param val New value.
	 * @return This vector object.
	 */
	public IVector set(int dim, double val);
	
	/**
	 * Get the dimension of this vector.
	 * @return Vector dimension.
	 */
	public int getDimension();
	
	/**
	 * Copy this vector values into a new object.
	 * @return Copy of this vector.
	 */
	public IVector copy();
	
	/**
	 * Make a new vector with the size of n. 
	 * If n is less or equal to this vector size, the first n elements will be copied from it.
	 * If n is greater than this vector size, after copying this vector values the rest will be filled with zeros.
	 * @param n Dimension of new vector.
	 * @return Newly created vector.
	 * @throws IncompatibleOperandException Thrown if dimensions aren't compatible.
	 */
	public IVector copyPart(int n) throws IncompatibleOperandException;
	
	/**
	 * Make a new instance of this object type, keeping the dimensions with values set to zero..
	 * @param n The dimension of a new vector.
	 * @return Newly created vector.
	 */
	public IVector newInstance(int n);
	
	/**
	 * Add the given vector values to this vector.
	 * @param vec Vector to be added.
	 * @return This vector.
	 * @throws IncompatibleOperandException Thrown if dimensions aren't compatible.
	 */
	public IVector add(IVector vec) throws IncompatibleOperandException;
	
	/**
	 * Add the given vector values to this vector, and output the result into a new object.
	 * This method does not change this vector values.
	 * @param vec Vector to be added.
	 * @return New vector.
	 * @throws IncompatibleOperandException Thrown if dimensions aren't compatible.
	 */
	public IVector nAdd(IVector vec) throws IncompatibleOperandException;
	
	/**
	 * Subtract the given vector values to this vector.
	 * @param vec Vector to be subtracted.
	 * @return This vector.
	 */
	public IVector sub(IVector vec) throws IncompatibleOperandException;
	
	/**
	 * Subtract the given vector values to this vector, and output the result into a new object.
	 * This method does not change this vector values.
	 * @param vec Vector to be subtracted.
	 * @return New vector.
	 */
	public IVector nSub(IVector vec) throws IncompatibleOperandException;
	
	/**
	 * Multiply this vector with the scalar value provided as argument.
	 * @param scalar Scalar value for multiplication.
	 * @return This vector.
	 */
	public IVector scalarMultiply(double scalar);
	
	/**
	 * Multiply this vector with the scalar value provided as argument, and output the result into a new object.
	 * This method does not change this vector values.
	 * @param scalar Scalar value for multiplication.
	 * @return New vector.
	 */
	public IVector nScalarMultiply(double scalar);
	
	/**
	 * Multiply the given vector values to this vector, resulting in a dot product.
	 * @param vec Vector to be multiplied.
	 * @return Scalar product.
	 * @throws IncompatibleOperandException Thrown if dimensions aren't compatible.
	 */
	public double dotProduct(IVector vec) throws IncompatibleOperandException;
	
	/**
	 * Multiply the given vector values to this vector, resulting in a dot product, and output the result into a new object.
	 * This method does not change this vector values.
	 * @param vec Vector to be multiplied.
	 * @return Scalar product.
	 * @throws IncompatibleOperandException Thrown if dimensions aren't compatible.
	 */
	public double nDotProduct(IVector vec) throws IncompatibleOperandException;
	
	
	
	/**
	 * Calculates and outputs the norm of a vector.
	 * @return Vector norm.
	 */
	public double norm();
	
	/**
	 * Normalise this vector.
	 * @return This vector.
	 */
	public IVector normalise();
	
	/**
	 * Normalise this vector, and output the result into a new object.
	 * This method does not change this vector values. 
	 * @return New vector.
	 */
	public IVector nNormalise();
	
	/**
	 * Find the cosine value between this and the given vector angle.
	 * @param vec The other vector of calculation.
	 * @return The cosine of the angle between two vectors.
	 */
	public double cosine(IVector vec) throws IncompatibleOperandException;
	
	/**
	 * Calculate the scalar product of two vectors.
	 * @param vec The other vector of calculation.
	 * @return Scalar product value.
	 */
	public double scalarProduct(IVector vec) throws IncompatibleOperandException;
	
	/**
	 * Calculate the vector product of two vectors and output it as a new vector object.
	 * Note: 3D Euclidean space is closed under the cross product operation.
	 * For a non three dimensional vector this method throws IncompatibleOperandException.
	 * 
	 * @param vec The other vector of calculation.
	 * @return Vector product of the provided vectors.
	 */
	public IVector nVectorProduct(IVector vec) throws IncompatibleOperandException;
	
	/**
	 * Transform a vector in homogeneous coordinates to the corresponding vector in Cartesian coordinates.
	 * @return New vector in expressed in Cartesian coordinates.
	 * @throws IncompatibleOperandException Thrown if this object can't be transformed into Euclidian coordinates.
	 */
	public IVector nFromHomogeneus() throws IncompatibleOperandException;
	
	/**
	 * Converts this vector into a row matrix.
	 * @param liveView If set to true, the new matrix object shares the same matrix grid as this object.
	 * @return Converted matrix.
	 */
	public IMatrix toRowMatrix(boolean liveView);
	
	/**
	 * Converts this vector into a column matrix.
	 * @param liveView If set to true, the new matrix object shares the same matrix grid as this object.
	 * @return Converted matrix.
	 */
	public IMatrix toColumnMatrix(boolean liveView);
	
	/**
	 * Transform this vector into a array structure.
	 * @return Vector as array.
	 */
	public double[] toArray();
	
	
}
