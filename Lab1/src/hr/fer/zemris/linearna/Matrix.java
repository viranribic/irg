package hr.fer.zemris.linearna;

/**
 * Matrix class.
 * 
 * @author Viran
 *
 */
public class Matrix extends AbstractMatrix {

	protected double[][] elements;
	protected int rows;
	protected int cols;

	/**
	 * Matrix constructor.
	 * 
	 * @param rows
	 *            Number of matrix rows.
	 * @param cols
	 *            Number of matrix columns.
	 */
	public Matrix(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		elements = new double[rows][cols];
	}

	/**
	 * Matrix constructor.
	 * 
	 * @param rows
	 *            Number of matrix rows.
	 * @param cols
	 *            Number of matrix columns.
	 * @param elements
	 *            Matrix elements.
	 * @param useGiven
	 *            If set to false, use the given matrix as this matrix
	 *            description. Otherwise copy the values.
	 */
	public Matrix(int rows, int cols, double[][] elements, boolean useGiven) {
		this.rows = rows;
		this.cols = cols;
		if (useGiven)
			this.elements = elements;
		else {
			this.elements = new double[rows][cols];
			for (int i = 0; i < rows; i++)
				for (int j = 0; j < cols; j++)
					this.elements[i][j] = elements[i][j];
		}
	}

	/**
	 * For the given vector list, generate a matrix with each vector being the one row of this matrix.
	 * All of the given vectors must be of the same dimension.
	 * This implementation makes a new copy of the given vectors for its 2D array.
	 * @param vectors Vectors which form this matrix.
	 */
	public Matrix(IVector ...vectors) {
		if(vectors.length==0)
			throw new IllegalArgumentException();
		int firstDim=vectors[0].getDimension();
		for(int i=1;i<vectors.length;i++)
			if(vectors[i].getDimension()!=firstDim)
				throw new IllegalArgumentException();
		
		this.rows=vectors.length;
		this.cols=firstDim;
		
		this.elements = new double[rows][cols];
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < cols; j++)
				this.elements[i][j] = vectors[i].get(j);
	}

	@Override
	public int getRowsCount() {
		return rows;
	}

	@Override
	public int getColsCount() {
		return cols;
	}

	@Override
	public double get(int i, int j) {
		if (i < 0 || j < 0 || i >= rows || j >= cols)
			throw new IllegalArgumentException();

		return elements[i][j];
	}

	@Override
	public IMatrix set(int i, int j, double val) {
		if (i < 0 || j < 0 || i >= rows || j >= cols)
			throw new IllegalArgumentException();
		elements[i][j] = val;
		return this;
	}

	@Override
	public IMatrix copy() {
		return new Matrix(rows, rows, elements, false);

	}

	@Override
	public IMatrix newInstance(int n,int m) {
		return new Matrix(n, m);
	}

	/**
	 * For the given input string return its vector representation.
	 * 
	 * @param input
	 *            Matrix description.
	 * @return Matrix object.
	 */
	public static Matrix parseSimple(String input) {
		int rowCount = 0;
		int colCount = 0;

		String[] rows = input.split("\\|");
		double[][] elements=null;
		rowCount = rows.length;

		int i = 0;
		int j = 0;

		for (String row : rows) {
			String[] valsInRow = row.trim().split("\\s+");
			if (i == 0 && j == 0) {
				colCount=valsInRow.length;
				elements = new double[rowCount][colCount];
			}
			
			for(String val:valsInRow){
				elements[i][j++]=Double.parseDouble(val);
			}
			j=0;
			i++;
		}

		return new Matrix(rowCount, colCount, elements, true);
	}
}
