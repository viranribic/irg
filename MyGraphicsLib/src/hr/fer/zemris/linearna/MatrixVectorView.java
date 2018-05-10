package hr.fer.zemris.linearna;
/**
 * Class which enables to view a row or column matrix object as a vector.
 * @author Viran
 *
 */
public class MatrixVectorView extends AbstractMatrix {

	private boolean asRowMatrix;
	private IVector original;
	
	/**
	 * MatrixVectorView constructor.
	 * @param original Original vector.
	 * @param asRowMatrix Decision should the vector be treated as row or column matrix.
	 */
	public MatrixVectorView(IVector original, boolean asRowMatrix) {
		this.asRowMatrix=asRowMatrix;
		this.original=original;
	}
	
	@Override
	public int getRowsCount() {
		if(asRowMatrix)
			return 1;
		else
			return original.getDimension();
	}

	@Override
	public int getColsCount() {
		if(asRowMatrix)
			return original.getDimension();
		else
			return 1;
	}

	@Override
	public double get(int i, int j) {
		if(asRowMatrix){
			if(i!=0)
				throw new IllegalArgumentException("Given index is out of bound.");
			return original.get(j);
		}else{
			if(j!=0)
				throw new IllegalArgumentException("Given index is out of bound.");
			return original.get(i);
		}
	}

	@Override
	public IMatrix set(int i, int j, double val) {
		if(asRowMatrix){
			if(i!=0)
				throw new IllegalArgumentException("Given index is out of bound.");
			original.set(j,val);
		}else{
			if(j!=0)
				throw new IllegalArgumentException("Given index is out of bound.");
			original.set(i,val);
		}
		return this;
	}

	@Override
	public IMatrix copy() {
		IMatrix matrix;
		if(asRowMatrix){
			matrix=new Matrix(1,original.getDimension());
			for(int i=0;i<original.getDimension();i++)
				matrix.set(0, i, original.get(i));
			
		}else{
			matrix=new Matrix(original.getDimension(),1);
			for(int i=0;i<original.getDimension();i++)
				matrix.set(i, 0, original.get(i));
			
		}
		return matrix;
	}

	@Override
	public IMatrix newInstance(int n, int m) {
		return new Matrix(n,m);
		//		if(asRowMatrix){
//			return new Matrix(0,original.getDimension());
//		}else{
//			return new Matrix(original.getDimension(),0);			
//		}
	}

}
