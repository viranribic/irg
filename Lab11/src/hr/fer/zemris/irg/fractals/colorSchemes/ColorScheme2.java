package hr.fer.zemris.irg.fractals.colorSchemes;

import com.jogamp.opengl.GL2;

public class ColorScheme2 implements IColorScheme {

	private static final int HARD_CODED_CONSTANT = 16;

	private static ColorScheme2 scheme;

	private ColorScheme2() {
		//Singleton object
	}
	
	public static ColorScheme2 getScheme() {
		if(scheme==null){
			scheme=new ColorScheme2();
		}
		return scheme;
	}
	
	@Override
	public void colorScheme(GL2 gl2, int n, int maxLimit) {
		if(n==-1){
			gl2.glColor3f(0f, 0f, 0f);
			
		}else if(maxLimit < HARD_CODED_CONSTANT){
			int r= (int)((n-1)/(double)(maxLimit-1) * 255 +0.5);
			int g = 255-r;
			int b = ((n-1)%(maxLimit/2))*255/(maxLimit/2);
			gl2.glColor3f((float)(r/255f), (float)(g/255f), (float)(b/255f));
		} else {
			int lim = maxLimit<32? maxLimit:32;
			int r=(n-1)*255/lim;
			int g=((n-1)%(lim/4))*255/(lim/4);
			int b= ((n-1)%(lim/8))*255/(lim/8);
			gl2.glColor3f((float)(r/255f), (float)(g/255f), (float)(b/255f));
		}
	}

}
