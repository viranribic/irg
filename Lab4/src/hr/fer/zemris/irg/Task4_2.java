package hr.fer.zemris.irg;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;

import hr.fer.zemris.linearna.IVector;

/**
 * Lab assignment task 4.
 * @author Viran
 *
 */
public class Task4_2 {
	private static GraphicsController controller = new GraphicsController();

	static {
		GLProfile.initSingleton();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				GLProfile glprofile = GLProfile.getDefault();
				GLCapabilities glcapabilities = new GLCapabilities(glprofile);
				final GLCanvas glcanvas = new GLCanvas(glcapabilities);

				glcanvas.addKeyListener(new KeyListener() {

					@Override
					public void keyTyped(KeyEvent e) {
						e.consume();
						if (e.getKeyChar() == 'k') {
							controller.toggleConvex();
						} else if (e.getKeyChar() == 'p') {
							controller.toggleFilling();
						} else if (e.getKeyChar() == 'n') {
							controller.changeAppState();
						}
						glcanvas.display();
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

				glcanvas.addMouseMotionListener(new MouseMotionListener() {

					@Override
					public void mouseMoved(MouseEvent e) {
						controller.setCurrentPosition(e.getX(), e.getY());
						glcanvas.display();
					}

					@Override
					public void mouseDragged(MouseEvent e) {
						// TODO Auto-generated method stub

					}
				});

				glcanvas.addMouseListener(new MouseListener() {

					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mousePressed(MouseEvent e) {
					}

					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mouseClicked(MouseEvent e) {
						controller.userClicked(e.getX(), e.getY());
						glcanvas.display();
					}
				});

				glcanvas.addGLEventListener(new GLEventListener() {

					@Override
					public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
						GL2 gl2 = drawable.getGL().getGL2();

						gl2.glDisable(GL.GL_DEPTH_TEST);
						gl2.glViewport(0, 0, width, height);
						gl2.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
						gl2.glLoadIdentity();
						gl2.glOrtho(0, width - 1, height - 1, 0, 0, 1);
						gl2.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);

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

						if (controller.isConvexCheckActive())
							gl2.glClearColor(0.0f, 1.0f, 0.0f, 1.0f);
						else
							gl2.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

						gl2.glClear(GL.GL_COLOR_BUFFER_BIT);
						gl2.glLoadIdentity();

						LinkedList<IVector> userPoints=controller.getUserPoints();
						IVector mousePoint=controller.getCurMouse();
						
						if (controller.isFillingActive()) {
							try{
							controller.drawPolygon(gl2);
							}catch(IllegalArgumentException e){
								drawLine(gl2, userPoints, mousePoint);
							}
						}else{
							drawLine(gl2, userPoints, mousePoint);
						}
						
					}

					private void drawLine(GL2 gl2, LinkedList<IVector> userPoints, IVector mousePoint) {
						gl2.glColor3f(0.f, 0.f, 0.f);
						if (controller.getAppState().equals(EAppState.POLYG_DEF)){
							gl2.glBegin(GL.GL_LINE_LOOP);
							for (IVector point : userPoints)
								gl2.glVertex2i((int) point.get(0), (int) point.get(1));
							gl2.glVertex2i((int) mousePoint.get(0), (int) mousePoint.get(1));
							gl2.glEnd();	
						}else{
							gl2.glBegin(GL.GL_LINE_LOOP);
							for (IVector point : userPoints)
								gl2.glVertex2i((int) point.get(0), (int) point.get(1));
							gl2.glEnd();
						}
					}

					

					
				});

				JFrame jframe = new JFrame("Crtanje linija na rasterskim prikaznim jedinicama");
				jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				jframe.setSize(640, 480);
				jframe.getContentPane().add(glcanvas, BorderLayout.CENTER);
				jframe.setVisible(true);
				glcanvas.requestFocusInWindow();
			}
		});
	}
}
