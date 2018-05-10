package hr.fer.zemris.objUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import hr.fer.zemris.graphicsOperators.EMatrixType;
import hr.fer.zemris.graphicsOperators.MatrixOpFactory;

import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.IncompatibleOperandException;
import hr.fer.zemris.polygonUtil.EDotPolyRelation;

public class ObjectModel {

	private LinkedList<Face3D> faceList = new LinkedList<>();
	private LinkedList<Vertex3D> vertexList = new LinkedList<>();
	private Map<Vertex3D, IVector> vectorNormals = new HashMap<>();
	private boolean vectoNormalsNormalised=false;
	
	public void addVertex(double[] elementsD) {
		if (elementsD.length != 3)
			throw new IllegalArgumentException("Vertex dimension is not a 3D point.");
		Vertex3D vertex = new Vertex3D(elementsD);
		vertexList.add(vertex);
//		vectorNormals.put(vertex, vertex.newInstance(vertex.getDimension()));
	}

	public void addFace(int[] elementsI) {
		if (elementsI.length != 3)
			throw new IllegalArgumentException("Polygon description is not defined with 3 points.");

		for (Integer i : elementsI)
			if (i > vertexList.size() || i <= 0)
				throw new IllegalArgumentException(
						"The given face definition points are not contained in the available point list.");
		Face3D face=new Face3D(vertexList, elementsI);
		faceList.add(face);
		
//		for(Integer vertexIndex:elementsI){
//			IVector normal=this.vectorNormals.get(this.vertexList.get(vertexIndex-1));
//			try {normal.add(face.getNormal());} catch (IncompatibleOperandException ignorable) { System.out.println("Error in model generator.");}
//		}
		
	}

	public EDotPolyRelation compareToPoint(IVector point) {
		int inside = 0, outside = 0;

		for (Face3D face : faceList) {
			EDotPolyRelation faceRes = face.compare(point);
			if (faceRes.equals(EDotPolyRelation.INSIDE))
				inside++;
			else if (faceRes.equals(EDotPolyRelation.OUTSIDE))
				outside++;

		}

		if (outside > 0)
			return EDotPolyRelation.OUTSIDE;
		else if (inside == faceList.size())
			return EDotPolyRelation.INSIDE;
		else
			return EDotPolyRelation.ON_EDGE;

	}

	public ObjectModel copy() {
		ObjectModel copy = new ObjectModel();

		for (Vertex3D vertex : this.vertexList)
			copy.vertexList.add(vertex.copyVertex());

		for (Face3D face : this.faceList)
			copy.faceList.add(face.copy());

		return copy;
	}

	public String dumpToOBJ() {
		StringBuilder sb = new StringBuilder();
		for (Vertex3D vertex : vertexList)
			sb.append("v " + vertex + "\n");

		for (Face3D face : faceList) {
			sb.append("f " + face.toString() + "\n");
		}

		return sb.toString();
	}

	public void normalize() {
		double xmin, xmax, ymin, ymax, zmin, zmax;

		xmax = xmin = vertexList.getFirst().get(0);
		ymax = ymin = vertexList.getFirst().get(1);
		zmax = zmin = vertexList.getFirst().get(2);

		for (int i = 1; i < vertexList.size(); i++) {
			// x
			if (vertexList.get(i).get(0) > xmax)
				xmax = vertexList.get(i).get(0);
			if (vertexList.get(i).get(0) < xmin)
				xmin = vertexList.get(i).get(0);
			// y
			if (vertexList.get(i).get(1) > ymax)
				ymax = vertexList.get(i).get(1);
			if (vertexList.get(i).get(1) < ymin)
				ymin = vertexList.get(i).get(1);
			// z
			if (vertexList.get(i).get(2) > zmax)
				zmax = vertexList.get(i).get(2);
			if (vertexList.get(i).get(2) < zmin)
				zmin = vertexList.get(i).get(2);

		}

		double xCenter = (xmax + xmin) / 2;
		double yCenter = (ymax + ymin) / 2;
		double zCenter = (zmax + zmin) / 2;

		double M = xmax - xmin;
		if (M < (ymax - ymin))
			M = ymax - ymin;
		if (M < (zmax - zmin))
			M = zmax - zmin;

		IMatrix translationM = MatrixOpFactory.get(EMatrixType.TRANSLATION, -xCenter, -yCenter, -zCenter);
		IMatrix scalingM = MatrixOpFactory.get(EMatrixType.SCALING, 2 / M, 2 / M, 2 / M);
		// IMatrix resOp=null;
		// try {
		// resOp = translationM.nMultiply(scalingM);
		// } catch (IncompatibleOperandException e) {
		// throw new RuntimeException("Trnalsation matrix and scaling matrix are
		// incompatible.");
		// }

		for (int i = 0; i < vertexList.size(); i++) {
			try {
				IVector vertex = vertexList.get(i).copyPart(4);
				vertex.set(3, 1);
				IVector vector = vertex.toRowMatrix(true).nMultiply(translationM).nMultiply(scalingM).toVector(true);
				vertexList.set(i, new Vertex3D(vector.nFromHomogeneus().toArray()));
			} catch (IncompatibleOperandException e) {
				throw new RuntimeException("Vectors and operation matrix are not compatible.");
			}

		}

	}

	public void printSurfaceEqu() {
		for (Face3D face : faceList)
			face.printSurfaceEq();
	}

	public Iterable<Vertex3D> getVertexIterable() {
		return new Iterable<Vertex3D>() {

			@Override
			public Iterator<Vertex3D> iterator() {
				return vertexList.iterator();
			}
		};
	}

	public Iterable<Face3D> getFaceIterable() {
		return new Iterable<Face3D>() {

			@Override
			public Iterator<Face3D> iterator() {
				return faceList.iterator();
			}
		};
	}

	public void determinateFaceVisibilities1(IVector eye) {
		try {
			IVector eyeHomogenus = eye.copyPart(4).set(3, 1);
			for (Face3D face : this.faceList) {
				IVector faceSurfaceEq = face.getSurfaceEq();
				double product = eyeHomogenus.dotProduct(faceSurfaceEq);
				if (product > 0)
					face.setVisible(true);
				else if (product < 0)
					face.setVisible(false);
			}
		} catch (IncompatibleOperandException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void determinateFaceVisibilities2(IVector eye) {
		try {
			IVector e;
			for (Face3D face : this.faceList) {
				Vertex3D[] points = face.getFacePoints();
				IVector c = points[0].nAdd(points[1].nAdd(points[2])).scalarMultiply(1. / 3);
				e = c.sub(eye).scalarMultiply(-1);// e=eye-c=-(e-eye)
				IVector n = face.getNormal();

				double product = n.scalarProduct(e);
				if (product > 0)
					face.setVisible(true);
				else if (product < 0)
					face.setVisible(false);

			}

		} catch (IncompatibleOperandException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public IVector getNormal(Vertex3D vertex) {
		if(!this.vectoNormalsNormalised){
			System.out.println("NOT NORMALISED!");
			return null;
		}
		if (vectorNormals.containsKey(vertex))
			return vectorNormals.get(vertex);
		return null;
	}

	public void generateVertexNormals() {
		for(Face3D face:faceList){
			for(Vertex3D vertex:face.vertexIterable()){
				if(this.vectorNormals.containsKey(vertex)){
					IVector normal = this.vectorNormals.get(vertex);
					try {normal.add(face.getNormal());} catch (IncompatibleOperandException e) {}
					
				}else{
					this.vectorNormals.put(vertex, face.getNormal().copy());
				}
			}
		}
		
		for(IVector normal:vectorNormals.values()){
			normal.scalarMultiply(1./3);
			normal.normalise();
		}
		this.vectoNormalsNormalised=true;
	}
}
