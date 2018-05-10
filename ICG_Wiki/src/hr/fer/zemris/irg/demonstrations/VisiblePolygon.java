package hr.fer.zemris.irg.demonstrations;

import java.util.Scanner;

import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.IncompatibleOperandException;
import hr.fer.zemris.linearna.Vector;

public class VisiblePolygon {

	public static void main(String[] args) throws IncompatibleOperandException {
		IVector a,b,c,d,o,g;
		Scanner in=new Scanner(System.in);
		String line;
		String[] arg;
		
		line=in.nextLine();
		line=line.replaceAll(",", "");
		arg=line.split(" ");
		a=new Vector(
				Double.parseDouble(arg[0]),
				Double.parseDouble(arg[1]),
				Double.parseDouble(arg[2])
				);
		
		line=in.nextLine();
		line=line.replaceAll(",", "");
		arg=line.split(" ");
		b=new Vector(
				Double.parseDouble(arg[0]),
				Double.parseDouble(arg[1]),
				Double.parseDouble(arg[2])
				);
		
		line=in.nextLine();
		line=line.replaceAll(",", "");
		arg=line.split(" ");
		c=new Vector(
				Double.parseDouble(arg[0]),
				Double.parseDouble(arg[1]),
				Double.parseDouble(arg[2])
				);
		
		line=in.nextLine();
		line=line.replaceAll(",", "");
		arg=line.split(" ");
		d=new Vector(
				Double.parseDouble(arg[0]),
				Double.parseDouble(arg[1]),
				Double.parseDouble(arg[2])
				);
		line=in.nextLine();
		line=line.replaceAll(",", "");
		arg=line.split(" ");
		o=new Vector(
				Double.parseDouble(arg[0]),
				Double.parseDouble(arg[1]),
				Double.parseDouble(arg[2])
				);
		line=in.nextLine();
		line=line.replaceAll(",", "");
		arg=line.split(" ");
		g=new Vector(
				Double.parseDouble(arg[0]),
				Double.parseDouble(arg[1]),
				Double.parseDouble(arg[2])
				);
		
		process(a,b,c,d,o,g);
		in.close();
	}

	private static void process(IVector a, IVector b, IVector c, IVector d, IVector o, IVector g) throws IncompatibleOperandException {
		IVector p1,p2,p3,p4;
		IVector p1Center,p2Center,p3Center,p4Center;
		
		p1=b.nSub(a).nVectorProduct(c.nSub(a));
		p2=c.nSub(b).nVectorProduct(d.nSub(b));
		p3=d.nSub(c).nVectorProduct(a.nSub(c));
		p4=a.nSub(d).nVectorProduct(b.nSub(d));
		
		//---------------------------------------
		double d1=-p1.dotProduct(a);
		double d2=-p2.dotProduct(b);
		double d3=-p3.dotProduct(c);
		double d4=-p4.dotProduct(d);
		
		p1=p1.copyPart(4).set(3, d1);
		p2=p2.copyPart(4).set(3, d2);
		p3=p3.copyPart(4).set(3, d3);
		p4=p4.copyPart(4).set(3, d4);
		
		a=a.copyPart(4).set(3, 1);
		b=b.copyPart(4).set(3, 1);
		c=c.copyPart(4).set(3, 1);
		d=d.copyPart(4).set(3, 1);
		o=o.copyPart(4).set(3, 1);
		
		
		if(p1.dotProduct(d)>0){
			p1.scalarMultiply(-1).set(3, d1);
			System.out.println("p1 repositioned");
		}
		if(p2.dotProduct(a)>0){
			p2.scalarMultiply(-1).set(3, d2);
			System.out.println("p2 repositioned");
		}
		if(p3.dotProduct(b)>0){
			p3.scalarMultiply(-1).set(3, d3);
			System.out.println("p3 repositioned");
		}
		if(p4.dotProduct(c)>0){
			p4.scalarMultiply(-1).set(3, d4);
			System.out.println("p4 repositioned");
		}
		
		if(p1.dotProduct(o)>0){
			System.out.println("P1 je vidljiv");
		}
		if(p2.dotProduct(o)>0){
			System.out.println("P2 je vidljiv");
		}
		if(p3.dotProduct(o)>0){
			System.out.println("P3 je vidljiv");
		}
		if(p4.dotProduct(o)>0){
			System.out.println("P4 je vidljiv");
		}
		//---------------------------------------
		
//		IVector center=a.nAdd(b).add(c).add(d).scalarMultiply(1/4);
//		System.out.println("Center: "+center);
//		p1Center=a.nAdd(b).nAdd(c).scalarMultiply(1/3);
//		p2Center=b.nAdd(c).nAdd(d).scalarMultiply(1/3);
//		p3Center=c.nAdd(d).nAdd(a).scalarMultiply(1/3);
//		p4Center=d.nAdd(a).nAdd(b).scalarMultiply(1/3);
//		
////		if(!isCW(a, b, c)){
////			p1.nScalarMultiply(-1);
////		}
////		if(!isCW(b, c, d)){
////			p2.nScalarMultiply(-1);
////		}
////		if(!isCW(c, d, a)){
////			p3.nScalarMultiply(-1);
////		}
////		if(!isCW(d, a, b)){
////			p4.nScalarMultiply(-1);
////		}
//		
//		determinateVisible(p1,p2,p3,p4,p1Center,p2Center,p3Center,p4Center,o);
	}

	private static void determinateVisible(IVector p1, IVector p2, IVector p3, IVector p4, IVector p1Center,
			IVector p2Center, IVector p3Center, IVector p4Center, IVector o) throws IncompatibleOperandException {
		IVector p1o,p2o,p3o,p4o;
		p1o=o.nSub(p1Center);
		p2o=o.nSub(p2Center);
		p3o=o.nSub(p3Center);
		p4o=o.nSub(p4Center);
		
		double res1=p1o.scalarProduct(p1);
		double res2=p2o.scalarProduct(p2);
		double res3=p3o.scalarProduct(p3);
		double res4=p4o.scalarProduct(p4);
		
		if(res1>0) System.out.println("P1 vidljiv");
		if(res2>0) System.out.println("P2 vidljiv");
		if(res3>0) System.out.println("P3 vidljiv");
		if(res4>0) System.out.println("P4 vidljiv");
	}

	public static boolean isCW(IVector a,IVector b,IVector c) throws IncompatibleOperandException{
		IVector p1=a.nVectorProduct(b);
		IVector p2=b.nVectorProduct(c);
		IVector p3=c.nVectorProduct(a);
		
		double one=p1.scalarProduct(c);
		double two=p2.scalarProduct(a);
		double three=p3.scalarProduct(b);
		
		if(one<0 && two<0 && three<0){
			return true;
		}else{
			return false;
		}
		
	}
}
