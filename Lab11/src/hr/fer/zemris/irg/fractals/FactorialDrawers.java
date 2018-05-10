package hr.fer.zemris.irg.fractals;

import com.jogamp.opengl.GL2;

public class FactorialDrawers {

	public static void colorScheme2(GL2 gl2,int n){
	
	}
	
	/**
	 * Divergence test for a function given as:
	 * 	z(n+1)=z(n)^n+c
	 * @param c
	 * @param limit
	 * @param n
	 * @return
	 */
	public static int  divergenceTest(ComplexNumber c,int limit, int n){
		ComplexNumber z= new ComplexNumber();
		ComplexNumber nextZ=null;
		for(int i=1;i<=limit;i++){
			nextZ=z.toPower(n).add(c);
			if(nextZ.getMagnitude()>2) return i;
			z=nextZ;
		}
		return -1;
	}
	
	public static int  divergenceTest(ComplexNumber z0,ComplexNumber c,int limit, double epsilon,int n){
		ComplexNumber z= z0.clone();
		double modul = z.getMagnitude();
		if(modul>epsilon) return 0;
		ComplexNumber nextZ=null;
		for(int i=1;i<=limit;i++){
			nextZ=z.toPower(n).add(c);
			if(nextZ.getMagnitude()>epsilon) return i;
			z=nextZ;
		}
		return -1;
	}
	
	
}
