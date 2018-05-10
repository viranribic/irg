package hr.fer.zemris.irg;

import java.util.LinkedList;

import hr.fer.zemris.irg.EDotPolyRelation;
import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.IncompatibleOperandException;
import hr.fer.zemris.linearna.Vector;

public class Face3D {

	//private ArrayList<IVector> v=new ArrayList<>();
	private IVector surface=new Vector(0,0,0,0);
	private int[] indexes=new int[3];
	private LinkedList<Vertex3D> vertexList;
	
//	@Deprecated
//	public Face3D(IVector... vertices) {
//		if(vertices.length!=3)
//			throw new IllegalArgumentException("The given number of vertices must me 3!");
//		
//		for(IVector ver:vertices)
//			v.add(ver);
//		
//		try {
//			IVector v1= v.get(1).nSub(v.get(0));
//			IVector v2= v.get(2).nSub(v.get(0));
//			IVector norm=v1.nVectorProduct(v2);
//			surface=norm.copyPart(4);
//			surface.set(3, -norm.scalarProduct(this.v.get(0)));
//		} catch (IncompatibleOperandException e) {
//			System.out.println("The given polygon points aren't of the same dimenison.");
//			System.exit(-1);
//		}
//		// test for counterclockwise
//	}

	public Face3D(LinkedList<Vertex3D> vertexList, int...vertices ) {
		if(vertices.length!=3)
			throw new IllegalArgumentException("The given number of vertices must me 3!");
		for(int i=0;i<vertices.length;i++)
			this.indexes[i]=vertices[i]-1;
		this.vertexList=vertexList;
		
		try {
			IVector v1= vertexList.get(indexes[1]).nSub(vertexList.get(indexes[0]));
			IVector v2= vertexList.get(indexes[2]).nSub(vertexList.get(indexes[0]));
			IVector norm=v1.nVectorProduct(v2);
			surface=norm.copyPart(4);
			surface.set(3, -norm.scalarProduct(vertexList.get(indexes[0])));
		} catch (IncompatibleOperandException e) {
			System.out.println("The given polygon points aren't of the same dimenison.");
			System.exit(-1);
		}
		//TODO test for counterclockwise
	}
	
	public EDotPolyRelation compare(IVector point){
		IVector wokringPoint=null;
		if (point.getDimension()>4 || point.getDimension()<3)
			throw new IllegalArgumentException("This point is not of a valid dimension!");
		if (point.getDimension()==3){
			try {
				wokringPoint=point.copyPart(4);
				wokringPoint.set(3, 1);
			} catch (IncompatibleOperandException ignorable) {
				System.out.println("Conversion proccess to homogenus coordinate failed.");
				System.exit(-1);
			}
		}else
			wokringPoint=point;
		
		double scalar=0;
		try { scalar=this.surface.scalarProduct(wokringPoint);} catch (IncompatibleOperandException ignorable) {}
		
		if(scalar>0)
			return EDotPolyRelation.OUTSIDE;
		else if(scalar==0)
			return EDotPolyRelation.ON_EDGE;
		else 
			return EDotPolyRelation.INSIDE;
	}
	
	@Override
	public String toString() {
		StringBuilder sb= new StringBuilder();
		
		for(int i=0;i<indexes.length;i++)
			sb.append((indexes[i]+1)+" ");
		
		return sb.toString();
	}


//	public List<IVector> asListVector() {
//		return v;
//	}
//
//	public List<Vertex3D> asListVertex3D() {
//		LinkedList<Vertex3D> list=new LinkedList<>();
//		for(IVector vec:this.v)
//			list.add((Vertex3D)vec);
//		return list;
//	}


	public Face3D copy() {
		return new Face3D(this.vertexList,this.indexes);
	}


	public void printSurfaceEq() {
		System.out.println(this.surface);
	}
}
