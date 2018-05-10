package hr.fer.zemris.linearna;

/**
 * N-dimensional vector model.
 * @author Viran
 *
 */
public class Vector extends AbstractVector {

	protected double[] elements;
	protected int dimension;
	protected boolean readOnly;
	
	/**
	 * Vector constructor from an array of elements.
	 * Default readOnly property is set to false.
	 * Given arraz is treated as object private.
	 * @param elements List of elements describing this vector.
	 */
	public Vector(double... elements) {
		this.elements=elements;
		dimension=elements.length;
		readOnly=false;
	}
	
	/**
	 * Vector constructor.
	 * @param readOnly Flag for setting the readOnly property to this vector.
	 * @param useGivenArray Flag for setting the given array as the value source.
	 * @param elements Array of elements describing the vector.
	 */
	public Vector(boolean readOnly, boolean useGivenArray,double... elements) {
		this.readOnly=readOnly;
		if(useGivenArray)
			this.elements=elements;
		else
			this.elements=elements.clone();
		this.dimension=elements.length;
		
	}
	
	@Override
	public double get(int dim) {
		return elements[dim];
	}

	@Override
	public IVector set(int dim, double val) {
		if(!readOnly)
			elements[dim]=val;	//maybe add stderr message
		return this;
	}

	@Override
	public int getDimension() {
		return dimension;
	}

	@Override
	public IVector copy() {
		if(readOnly)
			return this.newInstance(dimension); //maybe add stderr message
		
		Vector copy=new Vector(elements.clone());
		copy.dimension=this.dimension;
		copy.readOnly=false;
		
		return copy;
	}

	@Override
	public IVector newInstance(int i) {
		if(i<0)
			throw new IllegalArgumentException();
		double[] array=new double[i];
		return new Vector(array);
	}

	/**
	 * For the given input string return its vector representation.
	 * @param input Vector values.
	 */
	public static Vector parseSimple(String input) throws NumberFormatException{
		String[] sValues=input.trim().split("\\s+");
		double[] elements=new double[sValues.length];
		for(int i=0;i<elements.length;i++)
			elements[i]=Double.parseDouble(sValues[i]);
		Vector vector=new Vector(elements);
		return vector;
	}
}
