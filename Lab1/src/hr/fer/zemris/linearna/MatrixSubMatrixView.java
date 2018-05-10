package hr.fer.zemris.linearna;

/**
 * A wrapper class around an existing matrix object for looking at one of its submatrix components.
 * @author Viran
 *
 */
public class MatrixSubMatrixView extends AbstractMatrix {

	private IMatrix original;
	private int[] rowIndexes;
	private int[] colIndexes;;
	
	/**
	 * MatrixSubMatrixView constructor.
	 * @param original Original matrix.
	 * @param row Row which is going to be deleted.
	 * @param col Column which is going to be deleted.
	 */
	public MatrixSubMatrixView(IMatrix original, int row, int col) {
		this.original=original;
		this.rowIndexes=new int[original.getRowsCount()-1];
		this.colIndexes=new int[original.getColsCount()-1];
		
		int rowCounter=0;
		
		for(int i=0;i<original.getRowsCount();i++)
			if(i!=row)
				rowIndexes[rowCounter++]=i;
					
		int colCounter=0;
		
		for(int i=0;i<original.getColsCount();i++)
			if(i!=col)
				colIndexes[colCounter++]=i;
								
	}
	
	/**
	 * MatrixSubMatrixView constructor.
	 * @param original Original matrix.
	 * @param rows Rows which are taken from this matrix.
	 * @param cols Columns which are taken from this matrix.
	 */
	private MatrixSubMatrixView(IMatrix original, int[] rows, int[] cols) {
		this.original=original;
		this.rowIndexes=rows;
		this.colIndexes=cols;
	}
	
	
	@Override
	public int getRowsCount() {
		return rowIndexes.length;
	}

	@Override
	public int getColsCount() {
		return colIndexes.length;
	}

	@Override
	public double get(int i, int j) {
		return original.get(rowIndexes[i], colIndexes[j]);
	}

	@Override
	public IMatrix set(int i, int j, double val) {
		original.set(rowIndexes[i], colIndexes[j],val);
		return this;
	}

	@Override
	public IMatrix copy() {
		return new MatrixSubMatrixView(original, rowIndexes, colIndexes);
	}

	@Override
	public IMatrix newInstance(int n, int m) {
		return original.newInstance(n, m);
//		int[] rows=new int[original.getRowsCount()];
//		int[] cols=new int[original.getColsCount()];
//		
//		for(int i=0;i<rows.length;i++)
//			rows[i]=i;
//		for(int i=0;i<cols.length;i++)
//			cols[i]=i;
//		
//		return new MatrixSubMatrixView(original.newInstance(), rows, cols);
//		//return new MatrixSubMatrixView(original.newInstance(), this.rowIndexes, this.colIndexes);
//		//return original.newInstance();
	}

}
