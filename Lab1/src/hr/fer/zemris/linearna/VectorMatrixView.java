package hr.fer.zemris.linearna;

/**
 * Class which enables to view a row or column matrix object as a vector.
 * @author Viran
 *
 */
public class VectorMatrixView extends AbstractVector{

	private IMatrix original;
	private int dimension;
	private boolean rowMatrix;
	
	/**
	 * VectorMatrixView constructor.
	 * @param original Matrix which we want to use as a vector.
	 */
	public VectorMatrixView(IMatrix original) {
		if(original.getRowsCount()!=1 && original.getColsCount()!=1 )
			throw new IllegalArgumentException("Given matrix is not possible to view as a vector.");
		this.original=original;
		
		if(original.getRowsCount()==1){
			rowMatrix=true;
			dimension=original.getColsCount();
		}else{
			rowMatrix=false;
			dimension=original.getRowsCount();
		}
		
	}
	
	@Override
	public double get(int dim) {
		if(dim>=dimension || dim<0)
			throw new IllegalArgumentException();
		if(rowMatrix)
			return original.get(0, dim);
		else
			return original.get(dim, 0);
	}

	@Override
	public IVector set(int dim, double val) {
		if(dim>=dimension || dim<0)
			throw new IllegalArgumentException();
		if(rowMatrix)
			original.set(0, dim,val);
		else
			original.set(dim, 0,val);
		return this;
	}

	@Override
	public int getDimension() {
		return dimension;
	}

	@Override
	public IVector copy() {
		IVector copyV=this.newInstance(dimension);
		if(rowMatrix)
			for(int i=0;i<dimension;i++)
				copyV.set(i, original.get(0, i));
		else
			for(int i=0;i<dimension;i++)
				copyV.set(i, original.get(i, 0));
		
		return copyV;
	}

	@Override
	public IVector newInstance(int n) {
		if(n<=0)
			throw new IllegalArgumentException();
		double[] elements=new double[n];
		return new Vector(elements);
	}

}
