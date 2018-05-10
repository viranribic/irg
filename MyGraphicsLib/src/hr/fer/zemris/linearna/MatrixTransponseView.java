package hr.fer.zemris.linearna;

/**
 * A wrapper class around an existing matrix object for treating it as a transposed matrix.
 * @author Viran
 *
 */
public class MatrixTransponseView extends AbstractMatrix {

	private IMatrix original;
	
	/**
	 * MatrixTransponseView constructor.
	 * @param original Matrix on which transposing operation takes place.
	 */
	public MatrixTransponseView(IMatrix original) {
		this.original=original;
	}
	
	@Override
	public int getRowsCount() {
		return original.getColsCount();
	}

	@Override
	public int getColsCount() {
		return original.getRowsCount();
	}

	@Override
	public double get(int i, int j) {
		return original.get(j, i);
	}

	@Override
	public IMatrix set(int i, int j, double val) {
		original.set(j, i, val);
		return this;
	}

	@Override
	public IMatrix copy() {
		return new MatrixTransponseView(original.copy());
	}


	@Override
	public IMatrix newInstance(int n, int m) {
		return original.newInstance(n, m);
		//return new MatrixTransponseView(original.newInstance());
		// return original.newInstance();
	}

}
