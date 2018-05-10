package hr.fer.zemris.irg.fractals.colorSchemes;

import com.jogamp.opengl.GL2;

public interface IColorScheme {

	void colorScheme(GL2 gl2, int n, int maxLimit);
}
