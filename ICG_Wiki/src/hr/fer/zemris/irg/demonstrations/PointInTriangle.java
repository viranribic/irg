package hr.fer.zemris.irg.demonstrations;

import java.util.Scanner;

import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.IncompatibleOperandException;
import hr.fer.zemris.linearna.Vector;

public class PointInTriangle {

	public static void main(String[] args) throws IncompatibleOperandException {
		
		IVector v1,v2,v3;
		IVector pt;
		Scanner sc=new Scanner(System.in);
		String line=sc.nextLine();
		String[] coords=line.split(" ");
		v1=new Vector(
					Double.parseDouble(coords[0]),
					Double.parseDouble(coords[1]),
					Double.parseDouble(coords[2])
					);
		line=sc.nextLine();
		coords=line.split(" ");
		v2=new Vector(
					Double.parseDouble(coords[0]),
					Double.parseDouble(coords[1]),
					Double.parseDouble(coords[2])
					);
		line=sc.nextLine();
		coords=line.split(" ");
		v3=new Vector(
					Double.parseDouble(coords[0]),
					Double.parseDouble(coords[1]),
					Double.parseDouble(coords[2])
					);
		
		IVector p12=v1.nVectorProduct(v2);
		IVector p23=v2.nVectorProduct(v3);
		IVector p31=v3.nVectorProduct(v1);
		
		
		while(true){
			line=sc.nextLine();
			if(line.equals("q"))
				break;
			coords=line.split(" ");
			pt=new Vector(
					Double.parseDouble(coords[0]),
					Double.parseDouble(coords[1]),
					Double.parseDouble(coords[2])
					);
		
			double pt_1=pt.scalarProduct(p12);
			double pt_2=pt.scalarProduct(p23);
			double pt_3=pt.scalarProduct(p31);
			
			if( (pt_1 >0 && pt_2 >0 && pt_3 >0) || (pt_1 <0 && pt_2 <0 && pt_3 <0) ){
				System.out.println("Unutra.");
			} else if ( pt_1 == 0){
				System.out.println("na rubu.");
			} else if ( pt_2 == 0){
				System.out.println("na rubu.");
			} else if ( pt_3 == 0){
				System.out.println("na rubu.");
			}else{
				System.out.println("Izvan.");
			}
		}
		sc.close();
	}
	
}
