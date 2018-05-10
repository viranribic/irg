package hr.fer.zemris.irg.tasks;

import com.jogamp.opengl.GL2;

import hr.fer.zemris.objUtil.ObjectModel;

public interface IStrategy {

	void reshape(GL2 gl2, int width, int height);
	
	void display(GL2 gl2, ObjectModel model, GraphicsController controller);
	
}
