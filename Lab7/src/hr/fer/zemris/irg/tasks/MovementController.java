package hr.fer.zemris.irg.tasks;

import java.awt.event.KeyEvent;

import hr.fer.zemris.graphicsOperators.EMatrixType;
import hr.fer.zemris.graphicsOperators.MatrixOpFactory;
import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.IncompatibleOperandException;
import hr.fer.zemris.linearna.Vector;

public class MovementController {

	private static final double step = 0.1;
	private static final double degree = 0.01;
	//TODO add to use zoom
	//private static final double scale=1.1;

	private static IMatrix xPosRotM = MatrixOpFactory.get(EMatrixType.ROTATION_X, degree);
	private static IMatrix yPosRotM = MatrixOpFactory.get(EMatrixType.ROTATION_Y, degree);

	private static IMatrix xNegRotM=null;
	private static IMatrix yNegRotM=null;

	static {
		try {
			xNegRotM = xPosRotM.nInvert();
			yNegRotM = yPosRotM.nInvert();
		} catch (IncompatibleOperandException e) {
			System.out.println("Movement control rot matrix failed.");
			System.exit(-1);
		}
	}

	private IVector eyeVector;
	private IVector frontViewVector ;


	public MovementController(double eyeX, double eyeY, double eyeZ, double centerX, double centerY, double centerZ) {
		this.eyeVector = new Vector(eyeX, eyeY, eyeZ,1);
		IVector dirVector = new Vector(centerX, centerY, centerZ,1);
		try {
			frontViewVector = dirVector.sub(eyeVector);
		} catch (IncompatibleOperandException e) {
			System.out.println("Movement control vector subtraction failed.");
			System.exit(-1);
		}
		
	}

	public void typedKey(char keyChar) {
		if (Character.isAlphabetic(keyChar))
			keyChar = Character.toUpperCase(keyChar);

		System.out.println(keyChar);

		switch (keyChar) {
		case KeyEvent.VK_W: {
			// eyeY+=step;
			eyeVector.set(1, eyeVector.get(1) + step);
			break;
		}
		case KeyEvent.VK_A: {
			// eyeX+=step;
			eyeVector.set(0, eyeVector.get(0) + step);
			break;
		}
		case KeyEvent.VK_S: {
			// eyeY-=step;
			eyeVector.set(1, eyeVector.get(1) - step);
			break;
		}
		case KeyEvent.VK_D: {
			// eyeX-=step;
			eyeVector.set(0, eyeVector.get(0) - step);
			break;
		}
		case KeyEvent.VK_Q: {
			// eyeZ+=step;
			eyeVector.set(2, eyeVector.get(2) + step);
			break;
		}
		case KeyEvent.VK_E: {
			// eyeZ-=step;
			eyeVector.set(2, eyeVector.get(2) - step);
			break;
		}
		case KeyEvent.VK_8: {
			// centerY+=step;
			try {
				frontViewVector = frontViewVector.toRowMatrix(true).nMatrixMultiply(xPosRotM).toVector(true);
			} catch (IncompatibleOperandException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		case KeyEvent.VK_4: {
			// centerX+=step;
			try {
				frontViewVector = frontViewVector.toRowMatrix(true).nMatrixMultiply(yPosRotM).toVector(true);
			} catch (IncompatibleOperandException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		case KeyEvent.VK_2: {
			// centerY-=step;
			try {
				frontViewVector = frontViewVector.toRowMatrix(true).nMatrixMultiply(xNegRotM).toVector(true);
			} catch (IncompatibleOperandException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		case KeyEvent.VK_6: {
			// centerX-=step;
			try {
				frontViewVector = frontViewVector.toRowMatrix(true).nMatrixMultiply(yNegRotM).toVector(true);
			} catch (IncompatibleOperandException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		//TODO repair zoom in and out
//		case KeyEvent.VK_7: {
//			frontViewVector.scalarMultiply(scale);
//			break;
//		}
//		case KeyEvent.VK_9: {
//			frontViewVector.scalarMultiply(1/scale);
//			break;
//		}

		}

	}

	public double getEyeX() {
		return eyeVector.get(0);
	}

	public double getEyeY() {
		return eyeVector.get(1);
	}

	public double getEyeZ() {
		return eyeVector.get(2);
	}

	public double getCenterX() {
		try {
			return eyeVector.nAdd(frontViewVector).get(0);
		} catch (IncompatibleOperandException e) {
			return 0;
		}
	}

	public double getCenterY() {
		try {
			return eyeVector.nAdd(frontViewVector).get(1);
		} catch (IncompatibleOperandException e) {
			return 0;
		}}

	public double getCenterZ() {
		try {
			return eyeVector.nAdd(frontViewVector).get(2);
		} catch (IncompatibleOperandException e) {
			return 0;
		}	}

}
