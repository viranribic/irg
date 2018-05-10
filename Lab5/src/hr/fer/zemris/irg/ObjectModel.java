package hr.fer.zemris.irg;

import java.util.LinkedList;

import hr.fer.zemris.graphicsOperators.EMatrixType;
import hr.fer.zemris.graphicsOperators.MatrixOpFactory;
import hr.fer.zemris.irg.EDotPolyRelation;
import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.IncompatibleOperandException;

public class ObjectModel {

	private LinkedList<Face3D> faceList = new LinkedList<>();
	private LinkedList<Vertex3D> vertexList = new LinkedList<>();

	public void addVertex(double[] elementsD) {
		if (elementsD.length != 3)
			throw new IllegalArgumentException("Vertex dimension is not a 3D point.");
		vertexList.add(new Vertex3D(elementsD));
	}

	public void addFace(int[] elementsI) {
		if (elementsI.length != 3)
			throw new IllegalArgumentException("Polygon description is not defined with 3 points.");

		for (Integer i : elementsI)
			if (i > vertexList.size() | i <= 0)
				throw new IllegalArgumentException(
						"The given face definition points are not contained in the available point list.");
		faceList.add(new Face3D(vertexList, elementsI)
		// new Face3D(vertexList.get(elementsI[0]-1),
		// vertexList.get(elementsI[1]-1), vertexList.get(elementsI[2]-1))
		);
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

}
