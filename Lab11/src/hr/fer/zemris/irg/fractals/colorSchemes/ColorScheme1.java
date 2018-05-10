package hr.fer.zemris.irg.fractals.colorSchemes;

import com.jogamp.opengl.GL2;

public class ColorScheme1 implements IColorScheme {

	
	private static ColorScheme1 scheme;

	private ColorScheme1() {
		//Singleton object
	}
	
	public static ColorScheme1 getScheme() {
		if(scheme==null){
			scheme=new ColorScheme1();
		}
		return scheme;
	}
	
	@Override
	public void colorScheme(GL2 gl2, int n, int maxLimit) {
		if (n == -1) {
			gl2.glColor3f(.0f, .0f, .0f);
		} else {
			gl2.glColor3d((double) n / maxLimit, 1 - (double) n / maxLimit / 2.,
					0.8 - (double) n / maxLimit / 3.);
		}
	}

}
