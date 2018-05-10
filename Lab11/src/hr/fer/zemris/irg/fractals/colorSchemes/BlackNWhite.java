package hr.fer.zemris.irg.fractals.colorSchemes;

import com.jogamp.opengl.GL2;

public class BlackNWhite implements IColorScheme {

	private static BlackNWhite scheme;

	private BlackNWhite() {
		//Singleton object
	}
	
	public static BlackNWhite getScheme() {
		if(scheme==null){
			scheme=new BlackNWhite();
		}
		return scheme;
	}
	
	@Override
	public void colorScheme(GL2 gl2, int n, int maxLimit) {
		if (n == -1) {
			gl2.glColor3f(.0f, .0f, .0f);
		} else {
			gl2.glColor3f(1.0f, 1.0f, 1.0f);
		}
	}

}
