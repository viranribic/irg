package hr.fer.zemris.irg.trash;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;

import hr.fer.zemris.irg.tasks.GraphicObjectBuilder;
import hr.fer.zemris.objUtil.Face3D;
import hr.fer.zemris.objUtil.ObjectModel;
import hr.fer.zemris.objUtil.Vertex3D;

public class Program1 {

	private static ObjectModel model = new ObjectModel();
	// private static MovementController mc = new MovementController(3, 4, 1, 0,
	// 0, 0);

	static {
		GLProfile.initSingleton();
	}

	public static void main(String[] args) {
		// check arguments
		if (args.length != 1) {
			System.out.println(
					"This program accepts only one argument. That is the parth to the object definition file, of type .obj.");
			return;
		}
		// check file
		if (!args[0].endsWith(".obj")) {
			System.out.println(
					"This program accepts only one argument. That is the parth to the object definition file, of type .obj.");
			return;
		}

		model = GraphicObjectBuilder.generateObjectFromFile(args[0]);
		model.normalize();
		
//		System.out.println("Print faces:");
//		int i=1;
//		for(Face3D face:model.getFaceIterable()){
//			System.out.println("\tFace "+(i++));
//			int j=1;
//			for(Vertex3D vertex:face.getVertexIterable()){
//				System.out.println("\t\tVertex "+(j++)+" :"+vertex);
//			}
//			System.out.println();
//		}
		
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				GLProfile glprofile = GLProfile.getDefault();
				GLCapabilities glcapabilities = new GLCapabilities(glprofile);
				final GLCanvas glcanvas = new GLCanvas(glcapabilities);

				glcanvas.addKeyListener(new KeyListener() {

					@Override
					public void keyTyped(KeyEvent e) {
					}

					@Override
					public void keyReleased(KeyEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void keyPressed(KeyEvent e) {
						// TODO Auto-generated method stub
						
					}
				});

				glcanvas.addGLEventListener(new GLEventListener() {

					@Override
					public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
						GL2 gl2 = drawable.getGL().getGL2();

						// 1. Reset projection matrix
						gl2.glMatrixMode(GL2.GL_PROJECTION);
						gl2.glLoadIdentity();
						gl2.glFrustum(-0.5f, +0.5f, -0.5f, +0.5f, 1.f, 100.0f);
						// 2. Adjust viewport
						gl2.glViewport(0, 0, width, height);
					}

					@Override
					public void init(GLAutoDrawable drawable) {
						// TODO Auto-generated method stub
					}

					@Override
					public void dispose(GLAutoDrawable drawable) {
						// TODO Auto-generated method stub

					}

					@Override
					public void display(GLAutoDrawable drawable) {
						GL2 gl2 = drawable.getGL().getGL2();

						gl2.glClearColor(0.0f, 1.0f, 0.0f, 1.0f);
						gl2.glClear(GL.GL_COLOR_BUFFER_BIT);

						GLU glu = GLU.createGLU(gl2);
						gl2.glMatrixMode(GL2.GL_MODELVIEW);
						gl2.glLoadIdentity();
						glu.gluLookAt(3f, 4f, 1.f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
						mojZadatak(gl2);

					}

					private void mojZadatak(GL2 gl2) {

						for (Face3D face : model.getFaceIterable()) {
							gl2.glColor3f(1.f, .0f, .0f);
							gl2.glBegin(GL2.GL_LINE_LOOP);
							gl2.glColor3f(1, 0, 0);
							//System.out.println("----------------");
							for (Vertex3D vertex : face.getVertexIterable()) {
								//System.out.println(vertex);
								gl2.glVertex3f((float) vertex.get(0), (float) vertex.get(1), (float) vertex.get(2));
							}
							gl2.glEnd();
						}
					}
				});

				JFrame jframe = new JFrame("IRGLab6");
				jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				jframe.setSize(640, 480);
				jframe.getContentPane().add(glcanvas, BorderLayout.CENTER);
				jframe.setVisible(true);
				glcanvas.requestFocusInWindow();
			}
		});

	}

	void crtanje(GL2 gl2) {
		gl2.glMatrixMode(GL2.GL_PROJECTION);
		gl2.glLoadIdentity();

	}

	static void kocka(GL2 gl2, float w) {
		float wp = w / 2.0f;
		// gornja stranica
		gl2.glBegin(GL.GL_LINE_LOOP);

		gl2.glVertex3f(-wp, -wp, wp);
		gl2.glVertex3f(wp, -wp, wp);
		gl2.glVertex3f(wp, wp, wp);
		gl2.glVertex3f(-wp, wp, wp);

		gl2.glEnd();

		// donja stranica
		gl2.glBegin(GL.GL_LINE_LOOP);

		gl2.glVertex3f(-wp, wp, -wp);
		gl2.glVertex3f(wp, wp, -wp);
		gl2.glVertex3f(wp, -wp, -wp);
		gl2.glVertex3f(-wp, -wp, -wp);

		gl2.glEnd();

		// desna stranica
		gl2.glBegin(GL.GL_LINE_LOOP);

		gl2.glVertex3f(wp, wp, -wp);
		gl2.glVertex3f(-wp, wp, -wp);
		gl2.glVertex3f(-wp, wp, wp);
		gl2.glVertex3f(wp, wp, wp);

		gl2.glEnd();

		// lijeva stranica
		gl2.glBegin(GL.GL_LINE_LOOP);

		gl2.glVertex3f(wp, -wp, wp);
		gl2.glVertex3f(-wp, -wp, wp);
		gl2.glVertex3f(-wp, -wp, -wp);
		gl2.glVertex3f(wp, -wp, -wp);

		gl2.glEnd();

		// prednja stranica
		gl2.glBegin(GL.GL_LINE_LOOP);

		gl2.glVertex3f(wp, -wp, -wp);
		gl2.glVertex3f(wp, wp, -wp);
		gl2.glVertex3f(wp, wp, wp);
		gl2.glVertex3f(wp, -wp, wp);

		gl2.glEnd();

		// stražnja stranica
		gl2.glBegin(GL.GL_LINE_LOOP);

		gl2.glVertex3f(-wp, -wp, wp);
		gl2.glVertex3f(-wp, wp, wp);
		gl2.glVertex3f(-wp, wp, -wp);
		gl2.glVertex3f(-wp, -wp, -wp);

		gl2.glEnd();

	}

	static void kocka1(GL2 gl2) {
		kocka(gl2, 1.f);
	}

	static void renderScene(GL2 gl2) {
		gl2.glColor3f(1.f, .2f, .2f);

		gl2.glPushMatrix();
		gl2.glScalef(10.0f, 10.0f, 10.0f);
		kocka1(gl2);
		gl2.glPopMatrix();

		gl2.glColor3f(0.f, .2f, 1.f);
		gl2.glPushMatrix();
		gl2.glTranslatef(10.f, 0.0f, 0.0f);
		gl2.glRotatef(30.0f, 0.0f, 0.0f, 1.0f);
		gl2.glScalef(5.f, 5.f, 5.f);
		kocka1(gl2);
		gl2.glPopMatrix();
	}
}
