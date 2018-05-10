package hr.fer.zemris.irg;


import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.IncompatibleOperandException;
import hr.fer.zemris.linearna.Vector;

public class Vertex3D implements IVector{
	
	private Vector thisVector;

	public Vertex3D(double... elements) {
		if(elements.length!=3)
			throw new IllegalArgumentException("The given number of vertex elements must me 3!");
		thisVector=new Vector(false, true, elements);	
	}

	@Override
	public double get(int dim) {
		return thisVector.get(dim);
	}

	@Override
	public IVector set(int dim, double val) {
		return thisVector.set(dim, val);
	}

	@Override
	public int getDimension() {
		return thisVector.getDimension();
	}

	@Override
	public IVector copy() {
		return thisVector.copy();
	}

	@Override
	public IVector copyPart(int n) throws IncompatibleOperandException {
		return thisVector.copyPart(n);
	}

	@Override
	public IVector newInstance(int n) {
		return thisVector.newInstance(n);
	}

	@Override
	public IVector add(IVector vec) throws IncompatibleOperandException {
		return thisVector.add(vec);
	}

	@Override
	public IVector nAdd(IVector vec) throws IncompatibleOperandException {
		return thisVector.nAdd(vec);
	}

	@Override
	public IVector sub(IVector vec) throws IncompatibleOperandException {
		return thisVector.sub(vec);
	}

	@Override
	public IVector nSub(IVector vec) throws IncompatibleOperandException {
		return thisVector.nSub(vec);
	}

	@Override
	public IVector scalarMultiply(double scalar) {
		return thisVector.scalarMultiply(scalar);
	}

	@Override
	public IVector nScalarMultiply(double scalar) {
		return thisVector.nScalarMultiply(scalar);
	}

	@Override
	public double dotProduct(IVector vec) throws IncompatibleOperandException {
		return thisVector.dotProduct(vec);
	}

	@Override
	public double nDotProduct(IVector vec) throws IncompatibleOperandException {
		return thisVector.nDotProduct(vec);
	}

	@Override
	public double norm() {
		return thisVector.norm();
	}

	@Override
	public IVector normalise() {
		return thisVector.normalise();
	}

	@Override
	public IVector nNormalise() {
		return thisVector.nNormalise();
	}

	@Override
	public double cosine(IVector vec) throws IncompatibleOperandException {
		return thisVector.cosine(vec);
	}

	@Override
	public double scalarProduct(IVector vec) throws IncompatibleOperandException {
		return thisVector.scalarProduct(vec);
	}

	@Override
	public IVector nVectorProduct(IVector vec) throws IncompatibleOperandException {
		return thisVector.nVectorProduct(vec);
	}

	@Override
	public IVector nFromHomogeneus() throws IncompatibleOperandException {
		return thisVector.nFromHomogeneus();
	}

	@Override
	public IMatrix toRowMatrix(boolean liveView) {
		return thisVector.toRowMatrix(liveView);
	}

	@Override
	public IMatrix toColumnMatrix(boolean liveView) {
		return thisVector.toColumnMatrix(liveView);
	}

	@Override
	public double[] toArray() {
		return thisVector.toArray();
	}
	
	@Override
	public String toString() {
		StringBuilder sb= new StringBuilder();
		
		for(int i=0;i<this.getDimension();i++)
			sb.append(this.get(i)+" ");
		
		return sb.toString();
	}

	/**
	 * Make a copy of the vertex.
	 * @return Copy of this vertex object.
	 */
	public Vertex3D copyVertex() {
		return new Vertex3D(this.toArray());
	}
}
