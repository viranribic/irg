package hr.fer.zemris.irg.demonstrations;

import java.util.Scanner;

import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.IncompatibleOperandException;
import hr.fer.zemris.linearna.Vector;

public class ReflectionRefractionSolver {

	public static void main(String[] args1) throws IncompatibleOperandException {
		Scanner sc=new Scanner(System.in);
		System.out.println("l:");
		String line=sc.nextLine();
		String[] args=line.split(" ");
		IVector l=new Vector(
				-Double.parseDouble(args[0]),
				-Double.parseDouble(args[1]),
				-Double.parseDouble(args[2])
				);
		System.out.println("n:");
		line=sc.nextLine();
		args=line.split(" ");
		IVector n=new Vector(
				Double.parseDouble(args[0]),
				Double.parseDouble(args[1]),
				Double.parseDouble(args[2])
				);
		System.out.println("n1:");
		line=sc.nextLine();
		double n1=Double.parseDouble(line);
		System.out.println("n2:");
		line=sc.nextLine();
		double n2=Double.parseDouble(line);
		
		process(n,l,n1,n2);
		sc.close();
	}

	private static void process(IVector n, IVector l, double n1, double n2) throws IncompatibleOperandException {
		n=n.normalise();
		l=l.normalise();
		IVector lo=n.nScalarMultiply(n.dotProduct(l));
		IVector lp=l.nSub(lo);
		
		IVector r=lp.nAdd(lo.scalarMultiply(-1));
		
		IVector tp=lp.nScalarMultiply(n1/n2);
		double s=Math.sqrt(1-(n1*n1)/(n2*n2)*(1-Math.pow(n.dotProduct(l),2)));
		IVector to=n.nScalarMultiply(-s);
		IVector t=tp.add(to);
		System.out.println("r: "+r);
		System.out.println("t: "+t);
		
	}
	
}
