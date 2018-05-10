package hr.fer.zemris.irg.demonstrations;

import java.util.Scanner;

import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.IncompatibleOperandException;
import hr.fer.zemris.linearna.Vector;

public class LineProjectionPlane {

	
	public static void main(String[] args) throws IncompatibleOperandException {
		Scanner sc=new Scanner(System.in);
		IVector v,c,plane;
		System.out.println("C:");
		String line=sc.nextLine();
		String[] coords=line.split(" ");
		c=new Vector(
					Double.parseDouble(coords[0]),
					Double.parseDouble(coords[1]),
					Double.parseDouble(coords[2])
					);
		c=c.copyPart(4).set(3, 1);
		System.out.println("Plane:");
		line=sc.nextLine();
		coords=line.split(" ");
		plane=new Vector(
					Double.parseDouble(coords[0]),
					Double.parseDouble(coords[1]),
					Double.parseDouble(coords[2]),
					Double.parseDouble(coords[3])
					);
		
		while(true){
			System.out.println("V:");
			line=sc.nextLine();
			coords=line.split(" ");
			v=new Vector(
						Double.parseDouble(coords[0]),
						Double.parseDouble(coords[1]),
						Double.parseDouble(coords[2])
						);
			v=v.copyPart(4).set(3, 1);
			IVector pt=c.nAdd(v.scalarMultiply(-(plane.dotProduct(c))/(plane.dotProduct(v))));
			System.out.println(pt.nFromHomogeneus());
			
		}
	}
}
