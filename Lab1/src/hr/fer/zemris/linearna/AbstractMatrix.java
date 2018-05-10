package hr.fer.zemris.linearna;

/**
 * Abstract matrix.
 * @author Viran
 *
 */
public abstract class AbstractMatrix implements IMatrix {

	/**
	 * AbstractMatrix constructor.
	 */
	public AbstractMatrix() {
	}
	
	@Override
	public IMatrix nTranspose(boolean liveView) {
		if(liveView)
			return new MatrixTransponseView(this);
		else
			return new MatrixTransponseView(this.copy());
	}

	@Override
	public IMatrix add(IMatrix mat) throws IncompatibleOperandException {
		if(this.getColsCount()!=mat.getColsCount() || this.getRowsCount()!=mat.getRowsCount())
			throw new IncompatibleOperandException();
		for(int i=0;i<this.getRowsCount();i++)
			for(int j=0;j<this.getColsCount();j++)
				this.set(i, j, this.get(i, j)+mat.get(i, j));
		return this;
	}

	@Override
	public IMatrix nAdd(IMatrix mat) throws IncompatibleOperandException {
		return this.copy().add(mat);
	}

	@Override
	public IMatrix sub(IMatrix mat) throws IncompatibleOperandException {
		if(this.getColsCount()!=mat.getColsCount() || this.getRowsCount()!=mat.getRowsCount())
			throw new IncompatibleOperandException();
		for(int i=0;i<this.getRowsCount();i++)
			for(int j=0;j<this.getColsCount();j++)
				this.set(i, j, this.get(i, j)-mat.get(i, j));
		return this;
	}

	@Override
	public IMatrix nSub(IMatrix mat) throws IncompatibleOperandException {
		return this.copy().sub(mat);
	}

	@Override
	public IMatrix nMultiply(IMatrix mat) throws IncompatibleOperandException {
		if(this.getColsCount()!=mat.getRowsCount())
			throw new IncompatibleOperandException();
		IMatrix res=this.newInstance(this.getRowsCount(), mat.getColsCount());
		
		for(int i=0;i<res.getRowsCount();i++)
			for(int j=0;j<res.getColsCount();j++){
				double sum=0;
				for(int thisCol=0, matRow=0;thisCol<this.getRowsCount();thisCol++,matRow++)
						sum+=this.get(i, thisCol)*mat.get(matRow, j);
					
				res.set(i, j, sum);
			}
		
		return res;
	}

	@Override
	public double determinant() throws IncompatibleOperandException {
		if(this.getRowsCount()!=this.getColsCount())
			throw new IncompatibleOperandException();
		
		if(this.getColsCount()==1 && this.getRowsCount()==1)
			return this.get(0, 0);
		
		double res=0;
		for(int i=0;i<this.getColsCount();i++){
			res+=((0+i)%2==0?1:-1)*this.get(0, i)*this.subMatrix(0, i, true).determinant();
		}
		return res;
	}

	@Override
	public IMatrix subMatrix(int n, int m, boolean liveView) {
		if(liveView)
			return new MatrixSubMatrixView(this, n, m);
		else
			return new MatrixSubMatrixView(this.copy(), n, m);
	}
	
	@Override
	public IMatrix scalarMultiply(double scalar){
		for(int i=0;i<this.getRowsCount();i++)
			for(int j=0;j<this.getColsCount();j++)
				this.set(i, j, this.get(i, j)*scalar);
		return this;
	}
	
	@Override
	public IMatrix nScalarMultiply(double scalar){
		return this.copy().scalarMultiply(scalar);
	}
	
	@Override
	public IMatrix nInvert() throws IncompatibleOperandException {
		double det=this.determinant();
		return this.adjugate(false).scalarMultiply(1/det);
	}

	@Override
	public IMatrix adjugate(boolean changeCurrent) throws IncompatibleOperandException{
		return new MatrixTransponseView(this.cofactor(changeCurrent));
	}
	
	@Override
	public IMatrix cofactor(boolean changeCurrent) throws IncompatibleOperandException{
		IMatrix cofactorMatrix;
		if(changeCurrent){
			cofactorMatrix=this;
		}else{
			cofactorMatrix=this.newInstance(this.getRowsCount(), this.getColsCount());
		}
		for(int i=0;i<cofactorMatrix.getRowsCount();i++)
			for(int j=0;j<cofactorMatrix.getColsCount();j++)
				cofactorMatrix.set(i, j, ((i+j)%2==0?1:-1)*this.subMatrix(i, j, true).determinant());
		return cofactorMatrix;
	}
	
	@Override
	public double[][] toArray() {
		double[][] array=new double[this.getRowsCount()][this.getColsCount()];
		for(int i=0;i<this.getRowsCount();i++)
			for(int j=0;j<this.getColsCount();j++)
				array[i][j]=this.get(i, j);
		return array;
	}

	@Override
	public IVector toVector(boolean liveView) {
		if(liveView)
			return new VectorMatrixView(this);
		else
			return new VectorMatrixView(this.copy());
	}

	@Override
	public String toString(){
		return this.toString(3);
	}
	
	@Override
	public IMatrix nMatrixMultiply(IMatrix matrix) throws IncompatibleOperandException {
		if(this.getColsCount() != matrix.getRowsCount())
			throw new IncompatibleOperandException();
		
		IMatrix result= this.newInstance(this.getRowsCount(), matrix.getColsCount());
		
		for(int i=0;i<result.getRowsCount();i++){
			for(int j=0;j<result.getColsCount();j++){
				double sum=0;
				
				for(int collectorIndex=0; collectorIndex<this.getColsCount();collectorIndex++)
					sum+=this.get(i, collectorIndex)*matrix.get(collectorIndex, j);
				result.set(i, j, sum);
			}
		}
		
		return result;
	}

	/**
	 * Get this matrix string representation with the specified value precision
	 * for each element.
	 * 
	 * @param precision
	 *            Matrix element decimal positions shown.
	 * @return String representation.
	 */
	public String toString(int precision){
		StringBuilder sb = new StringBuilder();

		for(int i=0;i<this.getRowsCount();i++){
			for(int j=0;j<this.getColsCount();j++)
				if (j == 0)
					sb.append(String.format("%." + precision + "f", this.get(i,j)));
				else
					sb.append(String.format(" %." + precision + "f", this.get(i,j)));
			sb.append("\n");
		}		
		return sb.toString();
	}
}
