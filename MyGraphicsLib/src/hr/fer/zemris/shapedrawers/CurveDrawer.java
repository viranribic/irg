package hr.fer.zemris.shapedrawers;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.Vector;

public class CurveDrawer {

	
	public static void drawCircle2D(IVector center, double radius, int divs, GL2 canvas){
		double t,x,y;
		
		if (center.getDimension()!=2)
			throw new IllegalArgumentException("drawCircle2D works only for 2D coordinates.");
		
		if (divs<2) return;
		
		canvas.glBegin(GL.GL_LINE_STRIP);
		for(int n=0;n<=divs;n++){
			t=2.0*Math.PI/divs;
			x= radius*Math.cos(t)+center.get(0);
			y= radius*Math.sin(t)+center.get(1);
			canvas.glVertex2d( x, y);
		}
		canvas.glEnd();
	}
	
	
	/**
	 * Note: THe function takes the points of a rectangle in which we want to draw the elipse.
	 * @param p1
	 * @param p2
	 * @param divs
	 * @param canvas
	 */
	public static void drawElipseByBound2D(IVector p1, IVector p2, int divs,GL2 canvas){
		double t,rx,ry,centerX,centerY,x,y;
		
		
		if (p1.getDimension()!=2 || p2.getDimension()!=2)
			throw new IllegalArgumentException("drawElipse2D works only for 2D coordinates.");
		
		if (divs<2) return;
		
		if(p1.get(0)>p2.get(0)){ // 0 = x coordinate
			double tmp= p1.get(0);
			p1.set(0, p2.get(0));
			p2.set(0, tmp);
		}
		
		if(p1.get(1)>p2.get(1)){ // 1 = y coordinate
			double tmp= p1.get(1);
			p1.set(1, p2.get(1));
			p2.set(1, tmp);
		}

		rx=(p2.get(0)-p1.get(0))/2.0;
		ry=(p2.get(1)-p1.get(1))/2.0;
		centerX= p1.get(0)+rx;
		centerY= p1.get(1)+ry;		
		
		canvas.glBegin(GL.GL_LINE_STRIP);
		for(int n=0;n<=divs;n++){
			t=2.0*Math.PI/divs;
			x= rx*Math.cos(t)+centerX;
			y= ry*Math.sin(t)+centerY;
			canvas.glVertex2d( x, y);
		}
		canvas.glEnd();
	}


	private static int[] factors(int n,int[] array){
		int a=1;

		for(int i=1;i<=n+1;i++){
			array[i-1]=a;
			a=a*(n-i+1)/i;
		}
		return array;
	}

	public static void drawBezier2D(IVector[] points, int divs,GL2 canvas){
		//IVector p=points[0].newInstance(points[0].getDimension());
		IVector p=new Vector(0,0);
		
		int n=points.length-1;
		double t,b;
		
		int[] factors= factors(n, new int[points.length]);
		
		canvas.glBegin(GL.GL_LINE_STRIP);
		for(int i=0;i<=divs;i++){
			
			t=1./divs*i;
			p.set(0,0).set(1, 0);
			
			for(int j=0;j<=n;j++){
				
				if(j==0){
					b= factors[j]*Math.pow(1-t, n);
				}else if( j==n){
					b= factors[j]*Math.pow(t, n);				
				}else{
					b= factors[j]*Math.pow(1-t, n-j)*Math.pow(t	, j);
				}
				p.set(0, p.get(0)+ b*points[j].get(0)).set(1, p.get(1)+ b*points[j].get(1));
				
				//try {p.add(points[j].nScalarMultiply(b)); } catch (IncompatibleOperandException ignorable) {}
			}
			
			canvas.glVertex2d(p.get(0), p.get(1));
		}
		canvas.glEnd();
		
	}


	public static double[] generateBinaryCoeficients(int size) {
		if(size<=0) return null;
		double[] array=new double[size];
		double n=size-1;
		double nCk=1;
		int loop=(size%2==0)?size/2:size/2+1;
		for(int k=0;k<loop;k++){
			array[k]=array[size-k-1]=nCk;
			nCk*=(n-k)/(k+1);
		}
		return array;
	}
}
