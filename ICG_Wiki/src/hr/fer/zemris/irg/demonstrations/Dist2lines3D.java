package hr.fer.zemris.irg.demonstrations;

import java.util.Scanner;

import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.IncompatibleOperandException;
import hr.fer.zemris.linearna.Vector;

public class Dist2lines3D {

	public static void main(String[] args) throws IncompatibleOperandException {
		Scanner sc=new Scanner(System.in);
		IVector p1,p2,c1,c2;
		System.out.println("p1:");
		String line=sc.nextLine();
		String[] coords=line.split(" ");
		p1=new Vector(
					Double.parseDouble(coords[0]),
					Double.parseDouble(coords[1]),
					Double.parseDouble(coords[2])
					);
		
		System.out.println("c1:");
		line=sc.nextLine();
		coords=line.split(" ");
		c1=new Vector(
					Double.parseDouble(coords[0]),
					Double.parseDouble(coords[1]),
					Double.parseDouble(coords[2])
					);
		System.out.println("p2:");
		line=sc.nextLine();
		coords=line.split(" ");
		p2=new Vector(
					Double.parseDouble(coords[0]),
					Double.parseDouble(coords[1]),
					Double.parseDouble(coords[2])
					);
		System.out.println("c2:");
		line=sc.nextLine();
		coords=line.split(" ");
		c2=new Vector(
					Double.parseDouble(coords[0]),
					Double.parseDouble(coords[1]),
					Double.parseDouble(coords[2])
					);
		
		System.out.println((c1.nSub(c2).scalarProduct(p1.nVectorProduct(p2)))/(p1.nVectorProduct(p2).norm()));
	}
}
