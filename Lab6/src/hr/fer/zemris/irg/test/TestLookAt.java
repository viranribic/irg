package hr.fer.zemris.irg.test;

import hr.fer.zemris.graphicsOperators.EMatrixType;
import hr.fer.zemris.graphicsOperators.MatrixOpFactory;
import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.Vector;

public class TestLookAt {

	
	public TestLookAt() {
		IMatrix tp=MatrixOpFactory.get(EMatrixType.LOOK_AT_MATRIX, new Vector(3,4,1),new Vector(0,0,0),new Vector(0,1,0));
		IMatrix m=tp;
		
		
	}
}
