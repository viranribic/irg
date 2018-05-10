package hr.fer.zemris.irg;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;


import hr.fer.zemris.linearna.IVector;

/**
 * Bresenhman's algorithm for line drawing.
 * @author Viran
 *
 */
public class Bresenhman {

	/**
	 * Draw a line from point vS to vE, interpolating between them using Bresenhman's algorithm.
	 * @param gl2 Canvas on which to draw.
	 * @param vS Starting point.
	 * @param vE Ending point.
	 */
	public static void drawLine(GL2 gl2, IVector vS, IVector vE) {
		int xs=(int)vS.get(0);
		int ys=(int)vS.get(1);
		int xe=(int)vE.get(0);
		int ye=(int)vE.get(1);
		
		if(xs<=xe){
			if(ys<=ye){
				drawPos90(gl2,xs,ys,xe,ye);
			}else{
				drawNeg90(gl2,xs,ys,xe,ye);
			}
		}else{
			if(ys>=ye){
				drawPos90(gl2,xe,ye,xs,ys);
			}else{
				drawNeg90(gl2,xe,ye,xs,ys);
			}
		}
		
	}



	/**
	 * Bresenhman's algorithm line drawing implementation for lines with the 
	 * incline angle between 0 and 90 degrees.
	 * @param gl2 Canvas on which to draw.
	 * @param xs Starting point x coordinate.
	 * @param ys Starting point y coordinate.
	 * @param xe Ending point x coordinate.
	 * @param ye Ending point y coordinate.
	 */
	private static void drawNeg90(GL2 gl2,int xs, int ys, int xe, int ye) {
		int x,yc,correction;
		int a,yf;
		
		if(-(ye-ys)<= (xe-xs) ){
			a=(ye-ys)*2;
			yc=ys; yf=xe-xs; correction= (xe-xs)*2;
			for(x=xs;x<=xe;x++){
				drawElement(gl2, x, yc);
				yf+=a;
				if(yf<=0){
					yf+=correction;
					yc-=1;
				}
			}
		}else{
			x=xe; xe=ys; ys=x;
			x=xs; xs=ye; ye=x; 
			
			a=(ye-ys)*2;
			yc=ys; yf=xe-xs; correction= (xe-xs)*2;
			for(x=xs;x<=xe;x++){
				drawElement(gl2, yc, x);
				yf+=a;
				if(yf<=0){
					yf+=correction;
					yc-=1;
				}
			}
		}
	}



	/**
	 * Bresenhman's algorithm line drawing implementation for lines with the 
	 * decline angle between 0 and -90 degrees.
	 * @param gl2 Canvas on which to draw.
	 * @param xs Starting point x coordinate.
	 * @param ys Starting point y coordinate.
	 * @param xe Ending point x coordinate.
	 * @param ye Ending point y coordinate.
	 */
	private static void drawPos90(GL2 gl2,int xs, int ys, int xe, int ye) {
		int x,yc,correction;
		int a,yf;
		
		if(ye-ys <= xe-xs){
			a=(ye-ys)*2;
			yc=ys; yf=-(xe-xs); correction= (xe-xs)*-2;
			for(x=xs;x<=xe;x++){
				drawElement(gl2, x, yc);
				yf+=a;
				if(yf>=0){
					yf+=correction;
					yc+=1;
				}
			}
		}else{
			x=xe; xe=ye; ye=x;
			x=xs; xs=ys; ys=x;
			
			a=(ye-ys)*2;
			yc=ys; yf=-(xe-xs); correction= (xe-xs)*-2;
			for(x=xs;x<=xe;x++){
				drawElement(gl2, yc, x);
				yf+=a;
				if(yf>=0){
					yf+=correction;
					yc+=1;
				}
			}
		}
	}



	/**
	 * Light up one picture element on the given canvas with the given coordinates.
	 * @param gl2 Canvas on which to draw.
	 * @param x First coordinate.
	 * @param y Second coordinate.
	 */
	private static void drawElement(GL2 gl2,int x, int y){
		gl2.glBegin(GL.GL_POINTS);
		gl2.glVertex2i(x, y);
		gl2.glEnd();
	}
}
