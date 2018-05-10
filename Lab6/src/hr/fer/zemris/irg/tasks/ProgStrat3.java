package hr.fer.zemris.irg.tasks;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

import hr.fer.zemris.graphicsOperators.EMatrixType;
import hr.fer.zemris.graphicsOperators.MatrixOpFactory;
import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.IncompatibleOperandException;
import hr.fer.zemris.linearna.Vector;
import hr.fer.zemris.objUtil.Face3D;
import hr.fer.zemris.objUtil.ObjectModel;
import hr.fer.zemris.objUtil.Vertex3D;

public class ProgStrat3 implements IStrategy {


	@Override
	public void display(GL2 gl2, ObjectModel model, GraphicsController controller) {
		gl2.glClearColor(0.0f, 1.0f, 0.0f, 1.0f);
		gl2.glClear(GL.GL_COLOR_BUFFER_BIT);

		gl2.glMatrixMode(GL2.GL_MODELVIEW);
		gl2.glLoadIdentity();
		IVector eyeVector=new Vector(controller.getEyeX(),controller.getEyeY(),controller.getEyeZ());;
		IMatrix tp=MatrixOpFactory.get(EMatrixType.LOOK_AT_MATRIX, eyeVector ,new Vector(0,0,0),new Vector(0,1,0));
		IMatrix pr=MatrixOpFactory.get(EMatrixType.FRUSTUM_MATRIX, -0.5f,0.5f,-0.5f,0.5f,1f,100f);
		try {
			IMatrix m= tp.nMultiply(pr);
			renderScene(gl2,m, model);
		} catch (IncompatibleOperandException e) {
			System.out.println("There was an error while computing lookAt/Frustum matrices. Rendering sequence stopped.");
		}	
	}

	@Override
	public void reshape(GL2 gl2, int width, int height) {
		gl2.glMatrixMode(GL2.GL_PROJECTION);
		gl2.glLoadIdentity();
		gl2.glViewport(0, 0, width, height);
	}
	
	private void renderScene(GL2 gl2,IMatrix m, ObjectModel model) {

		for (Face3D face : model.getFaceIterable()) {
			gl2.glColor3f(1.f, .0f, .0f);
			gl2.glBegin(GL2.GL_LINE_LOOP);
			gl2.glColor3f(1, 0, 0);
			for (Vertex3D vertex : face.getVertexIterable()) {
				try {
					IVector vertexHomogenus=vertex.copyPart(4).set(3, 1);
					IVector v=vertexHomogenus.toRowMatrix(false).nMultiply(m).toVector(false).nFromHomogeneus();
					System.out.println(v);
					gl2.glVertex2f((float) v.get(0), (float) v.get(1));
					
				} catch (IncompatibleOperandException e) {
					// TODO Auto-generated catch block
				}
			}
			gl2.glEnd();
		}
	}

}
