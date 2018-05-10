package hr.fer.zemris.irg.tasks;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

import hr.fer.zemris.objUtil.Face3D;
import hr.fer.zemris.objUtil.ObjectModel;
import hr.fer.zemris.objUtil.Vertex3D;

public class ProgStrat2 implements IStrategy {



	@Override
	public void reshape(GL2 gl2, int width, int height) {
		gl2.glMatrixMode(GL2.GL_PROJECTION);
		gl2.glLoadIdentity();
		GLU glu=GLU.createGLU(gl2);
		//
		glu.gluPerspective(63.43494882f, 1, 1, 100);
		gl2.glViewport(0, 0, width, height);
	}

	@Override
	public void display(GL2 gl2, ObjectModel model, GraphicsController controller) {

		gl2.glClearColor(0.0f, 1.0f, 0.0f, 1.0f);
		gl2.glClear(GL.GL_COLOR_BUFFER_BIT);

		GLU glu = GLU.createGLU(gl2);
		gl2.glMatrixMode(GL2.GL_MODELVIEW);
		gl2.glLoadIdentity();
		glu.gluLookAt(controller.getEyeX(), controller.getEyeY(), controller.getEyeZ(), 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);

		//glu.gluLookAt(3.f, 4.f, 1.f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
		renderScene(gl2,model);
	}

	private void renderScene(GL2 gl2,ObjectModel model) {

		for (Face3D face : model.getFaceIterable()) {
			gl2.glColor3f(1.f, .0f, .0f);
			gl2.glBegin(GL2.GL_LINE_LOOP);
			gl2.glColor3f(1, 0, 0);
			for (Vertex3D vertex : face.getVertexIterable()) {
				gl2.glVertex3f((float) vertex.get(0), (float) vertex.get(1), (float) vertex.get(2));
			}
			gl2.glEnd();
		}
	}
}
