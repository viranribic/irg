package hr.fer.zemris.linearna;

/**
 * Abstract vector class.
 * 
 * @author Viran
 *
 */
public abstract class AbstractVector implements IVector {

	/**
	 * AbstractVector constructor.
	 */
	public AbstractVector() {
	}

	@Override
	public IVector copyPart(int n) throws IncompatibleOperandException {
		if (n <= 0)
			throw new IncompatibleOperandException();
		IVector copyV = this.newInstance(n);

		for (int i = 0; i < n; i++) {
			if (i < this.getDimension())
				copyV.set(i, this.get(i));
			else
				copyV.set(i, 0);
		}

		return copyV;
	}

	@Override
	public IVector add(IVector vec) throws IncompatibleOperandException {
		if (this.getDimension() != vec.getDimension())
			throw new IncompatibleOperandException();
		for (int i = this.getDimension() - 1; i >= 0; i--) {
			this.set(i, this.get(i) + vec.get(i));
		}
		return this;
	}

	@Override
	public IVector nAdd(IVector vec) throws IncompatibleOperandException {
		return this.copy().add(vec);
	}

	@Override
	public IVector sub(IVector vec) throws IncompatibleOperandException {
		if (this.getDimension() != vec.getDimension())
			throw new IncompatibleOperandException();
		for (int i = this.getDimension() - 1; i >= 0; i--) {
			this.set(i, this.get(i) - vec.get(i));
		}
		return this;
	}

	@Override
	public IVector nSub(IVector vec) throws IncompatibleOperandException {
		return this.copy().sub(vec);
	}

	@Override
	public IVector scalarMultiply(double scalar) {
		for (int i = this.getDimension() - 1; i >= 0; i--) {
			this.set(i, this.get(i) * scalar);
		}
		return this;
	}

	@Override
	public IVector nScalarMultiply(double scalar) {
		return this.copy().scalarMultiply(scalar);
	}
	
	@Override
	public double dotProduct(IVector vec) throws IncompatibleOperandException{
		if (this.getDimension() != vec.getDimension())
			throw new IncompatibleOperandException();
		
		double res=0;
		for (int i = this.getDimension() - 1; i >= 0; i--) {
			res+=this.get(i)*vec.get(i);
		}
		return res;
	}
	
	@Override
	public double nDotProduct(IVector vec) throws IncompatibleOperandException{
		return this.copy().dotProduct(vec);
	}
	
	@Override
	public double norm() {
		double res = 0;
		for (int i = this.getDimension() - 1; i >= 0; i--)
			res += this.get(i) * this.get(i);
		return Math.sqrt(res);
	}

	@Override
	public IVector normalise() {
		double norm = this.norm();
		if (norm == 0)
			return this;
		for (int i = this.getDimension() - 1; i >= 0; i--) {
			this.set(i, this.get(i) / norm);
		}
		return this;
	}

	@Override
	public IVector nNormalise() {
		return this.copy().normalise();
	}

	@Override
	public double cosine(IVector vec) throws IncompatibleOperandException {
		if (this.getDimension() != vec.getDimension())
			throw new IncompatibleOperandException();
		double thisNorm = this.norm();
		double vecNorm = this.norm();

		if (thisNorm == 0 || vecNorm == 0)
			throw new IncompatibleOperandException("Argument 'divisor' is 0");
		return this.scalarProduct(vec) / (thisNorm * vecNorm);
	}

	@Override
	public double scalarProduct(IVector vec) throws IncompatibleOperandException {
		if (this.getDimension() != vec.getDimension())
			throw new IncompatibleOperandException();
		double scalProd = 0;
		for (int i = this.getDimension() - 1; i >= 0; i--) {
			scalProd += this.get(i) * vec.get(i);
		}
		return scalProd;
	}

	@Override
	public IVector nVectorProduct(IVector vec) throws IncompatibleOperandException {
		if (this.getDimension() != vec.getDimension() && this.getDimension() != 3)
			throw new IncompatibleOperandException();

		IVector resVec = this.newInstance(this.getDimension());

		resVec.set(0, this.get(1) * vec.get(2) - this.get(2) * vec.get(1));
		resVec.set(1, this.get(2) * vec.get(0) - this.get(0) * vec.get(2));
		resVec.set(2, this.get(0) * vec.get(1) - this.get(1) * vec.get(0));

		return resVec;
	}

	@Override
	public IVector nFromHomogeneus() throws IncompatibleOperandException {
		if (this.getDimension() == 1)
			throw new IncompatibleOperandException("Vector is not in homogeneus form.");

		IVector res = this.copyPart(getDimension() - 1);
		double h = this.get(getDimension() - 1);

		if (h == 0)
			for (int i = this.getDimension() - 2; i >= 0; i--) {
				if (this.get(i) > 0)
					res.set(i, Double.POSITIVE_INFINITY);
				else if (this.get(i) < 0)
					res.set(i, Double.NEGATIVE_INFINITY);
				else
					throw new IncompatibleOperandException();
			}
		else
			for (int i = this.getDimension() - 2; i >= 0; i--)
				res.set(i, this.get(i) / h);

		return res;
	}

	@Override
	public IMatrix toRowMatrix(boolean liveView) {
		if (liveView)
			return new MatrixVectorView(this, true);
		else {
			IMatrix matrix = new Matrix(1, this.getDimension());
			for (int i = 0; i < this.getDimension(); i++)
				matrix.set(0, i, this.get(i));
			return matrix;
		}
	}

	@Override
	public IMatrix toColumnMatrix(boolean liveView) {
		if (liveView)
			return new MatrixVectorView(this, false);
		else {
			IMatrix matrix = new Matrix(this.getDimension(), 1);
			for (int i = 0; i < this.getDimension(); i++)
				matrix.set(i, 0, this.get(i));
			return matrix;
		}
	}

	@Override
	public double[] toArray() {
		double[] array = new double[this.getDimension()];

		for (int i = this.getDimension() - 1; i >= 0; i--) {
			array[i] = this.get(i);
		}

		return array;
	}

	@Override
	public String toString() {
		return this.toString(3);
	}

	/**
	 * Get this vector string representation with the specified value precision
	 * for each element.
	 * 
	 * @param precision
	 *            Vector element decimal positions shown.
	 * @return String representation.
	 */
	public String toString(int precision) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < this.getDimension(); i++)
			if (i == 0)
				sb.append(String.format("%." + precision + "f", this.get(i)));
			else
				sb.append(String.format(" %." + precision + "f", this.get(i)));

		return sb.toString();
	}

}
