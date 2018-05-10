package hr.fer.zemris.irg.tasks;

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

import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.Vector;
import hr.fer.zemris.objUtil.Face3D;
import hr.fer.zemris.objUtil.ObjectModel;
import hr.fer.zemris.objUtil.Vertex3D;
import hr.fer.zemris.optimisationAlgorithms.OptimisationAlgorithms;

public class Program1 {

	private static ObjectModel model = new ObjectModel();
	private static GraphicsController controller = new GraphicsController();
	private static OptimisationAlgorithms optAlgorithms = new OptimisationAlgorithms();
	
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
		model.generateVertexNormals();

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
						if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
							controller.reset();
						} else if (e.getKeyCode() == KeyEvent.VK_Z) {
							controller.toggleZBuffer();
						} else if (e.getKeyCode() == KeyEvent.VK_A) {
							controller.incrementAngle();
						} else if (e.getKeyCode() == KeyEvent.VK_D) {
							controller.decrementAngle();
						} else if (e.getKeyCode() == KeyEvent.VK_W) {
							controller.incrementY();
						} else if (e.getKeyCode() == KeyEvent.VK_S) {
							controller.decrementY();
						} else if (e.getKeyCode() == KeyEvent.VK_Q) {
							controller.increaseRadius();
						} else if (e.getKeyCode() == KeyEvent.VK_E) {
							controller.decreaseRadius();
						} else if (e.getKeyCode() == KeyEvent.VK_1) {
							controller.disableOptimisations();
						} else if (e.getKeyCode() == KeyEvent.VK_2) {
							controller.enableOpt1();
						} else if (e.getKeyCode() == KeyEvent.VK_3) {
							controller.enableOpt2();
						} else if (e.getKeyCode() == KeyEvent.VK_4) {
							controller.enableOpt3();
						} else if (e.getKeyCode() == KeyEvent.VK_C) {
							controller.enableConstantShading();
						} else if (e.getKeyCode() == KeyEvent.VK_G) {
							controller.enableGouraudsMethodShading();
						}
						e.consume();
						glcanvas.display();
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

						gl2.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
						gl2.glClear(GL.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

						GLU glu = GLU.createGLU(gl2);
						gl2.glMatrixMode(GL2.GL_MODELVIEW);
						gl2.glLoadIdentity();
						IVector eyeVector = new Vector(controller.getEyeX(), controller.getEyeY(),
								controller.getEyeZ());
						;
						glu.gluLookAt(eyeVector.get(0), eyeVector.get(1), eyeVector.get(2), 0.0f, 0.0f, 0.0f, 0.0f,
								1.0f, 0.0f);
						renderScene(gl2);

					}

					private void renderScene(GL2 gl2) {
						// polygon configuration
						gl2.glPolygonMode(GL2.GL_FRONT, GL2.GL_FILL);
						gl2.glEnable(GL2.GL_CULL_FACE);
						gl2.glCullFace(GL2.GL_BACK);
						if(controller.isZBufferEnabled())
							gl2.glEnable(GL2.GL_DEPTH_TEST);
						else
							gl2.glDisable(GL2.GL_DEPTH_TEST);
						
						// lighting configuration
						gl2.glEnable(GL2.GL_LIGHTING);
						gl2.glLightModelfv(GL2.GL_LIGHT_MODEL_AMBIENT, new float[] { 0.0f, 0.0f, 0.0f, 1.0f }, 0);

						gl2.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, new float[] { 4f, 5f, 3f, 1f }, 0);
						gl2.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, new float[] { 0.2f, 0.2f, 0.2f, 1f }, 0);
						gl2.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, new float[] { 0.8f, 0.8f, 0f, 1f }, 0);
						gl2.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, new float[] { 0f, 0f, 0f, 1f }, 0);

						gl2.glEnable(GL2.GL_LIGHT0);

						// material configuration
						gl2.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, new float[] { 1f, 1f, 1f, 1f }, 0);
						gl2.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, new float[] { 1f, 1f, 1f, 1f }, 0);
						gl2.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, new float[] { 0.01f, 0.01f, 0.01f, 1f }, 0);
						gl2.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, 96f);

						if (controller.useConstantShading()) {
							gl2.glShadeModel(GL2.GL_FLAT);
						} else if (controller.useSmoothShading()) {
							gl2.glShadeModel(GL2.GL_SMOOTH);
						} else {
							System.out.println("Default shader.");
							gl2.glShadeModel(GL2.GL_SMOOTH);
						}

						// model drawing
						for (Face3D face : model.getFaceIterable()) {
							
							gl2.glBegin(GL2.GL_POLYGON);
							gl2.glFrontFace(GL2.GL_CCW);
							
							// gl2.glColor3f(1, 0, 0); - shaders don't ask for
							// color
							
							for (Vertex3D vertex : face.getVertexIterable()) {
								if(controller.useSmoothShading()){
									IVector normal = model.getNormal(vertex);
									gl2.glNormal3f((float) normal.get(0), (float) normal.get(1), (float) normal.get(2));

								}else{
									IVector normal = face.getNormal();
									gl2.glNormal3f((float) normal.get(0), (float) normal.get(1), (float) normal.get(2));
									
								}
									
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
