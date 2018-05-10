package hr.fer.zemris.irg.fractals;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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


public class JuliusFractals {

	private static JuliusController cntrlr = new JuliusController();
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

				glcanvas.addMouseListener(new MouseListener() {

					@Override
					public void mouseReleased(MouseEvent arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mousePressed(MouseEvent e) {
						if (SwingUtilities.isLeftMouseButton(e)) {
							cntrlr.leftClick(e.getPoint());
						} else if (SwingUtilities.isRightMouseButton(e)) {
							cntrlr.rightClick(e.getPoint());
						}
						glcanvas.display();
					}

					@Override
					public void mouseExited(MouseEvent arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mouseEntered(MouseEvent arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mouseClicked(MouseEvent arg0) {
						// TODO Auto-generated method stub

					}
				});

				glcanvas.addKeyListener(new KeyListener() {

					@Override
					public void keyTyped(KeyEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void keyReleased(KeyEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void keyPressed(KeyEvent e) {
						switch (e.getKeyCode()) {
						case KeyEvent.VK_0:
							cntrlr.setRe(0.11);
							cntrlr.setIm(0.65);
							break;
						
						case KeyEvent.VK_1:
							cntrlr.setRe(0.179180);
							cntrlr.setIm(0.588843);
							break;
						case KeyEvent.VK_2:
							cntrlr.setRe(0.32);
							cntrlr.setIm(0.043);
							break;
						case KeyEvent.VK_3:
							cntrlr.setRe(-0.807984);
							cntrlr.setIm(-0.145732);
							break;
						case KeyEvent.VK_4:
							cntrlr.setRe(-0.500036);
							cntrlr.setIm(-0.520156);
							break;
						case KeyEvent.VK_5:
							cntrlr.setRe(0.371588);
							cntrlr.setIm(-0.155767);
							break;
						case KeyEvent.VK_6:
							cntrlr.setRe(-0.74543);
							cntrlr.setIm(0.11301);
							break;
						
						case KeyEvent.VK_ENTER:
							// Print all the available key actions
							break;
						case KeyEvent.VK_ESCAPE:
							cntrlr.resetView();
							break;
						case KeyEvent.VK_B:
							// Print all the available key actions
							cntrlr.toggleBnW();
							break;
						case KeyEvent.VK_N:
							// Print all the available key actions
							cntrlr.changeColorSheme();
							break;

						case KeyEvent.VK_LEFT:
							if (e.isShiftDown()) {
								cntrlr.decLimit();
							} else if (e.isControlDown()) {
								
							} else {
								cntrlr.decReC();
							}
							break;
						case KeyEvent.VK_RIGHT:
							if (e.isShiftDown()) {
								cntrlr.incLimit();
							} else if (e.isControlDown()) {
								
							} else {
								cntrlr.incReC();
							}
							break;

						case KeyEvent.VK_DOWN:
							if (e.isShiftDown()) {
								cntrlr.decN();
							} else if (e.isControlDown()) {
								
							} else {
								cntrlr.decImC();
							}
							break;

						case KeyEvent.VK_UP:
							if (e.isShiftDown()) {
								cntrlr.incN();
							} else if (e.isControlDown()) {
								
							} else {
								cntrlr.incImC();
							}
							break;

						default:
							break;
						}
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
						gl2.glLoadIdentity();

						cntrlr.store(x, y, width, height);
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
						int height = cntrlr.height();
						int yCanvas = cntrlr.yCanvas();
						int width = cntrlr.width();
						int xCanvas = cntrlr.xCanvas();
						renderScenceJulius(gl2, xCanvas, yCanvas, width - 1, height - 1, cntrlr.umin(), cntrlr.umax(), cntrlr.vmin(), cntrlr.vmax(),
								cntrlr.getReC(), cntrlr.getImC(),cntrlr.limit(), cntrlr.n());
					}

				
					public void renderScenceJulius(GL2 gl2, int xCanvas, int yCanvas, int width, int height, 
							double umin, double umax, double vmin, double vmax, double reC, double imC,
							int limit, int n) {
						gl2.glPointSize(1);
						gl2.glBegin(GL2.GL_POINTS);
						ComplexNumber c= new ComplexNumber(reC,imC);
						double episilon= c.getMagnitude();
						episilon=(episilon<2.)?2.:episilon;
						for (int y = yCanvas; y < height; y++) {
							for (int x = xCanvas; x <= width; x++) {
								//
								double re = ((double) (x - xCanvas)) / (double) (width) * (umax - umin) + umin;
								double im = ((double) (y - yCanvas)) / (double) (height) * (vmax - vmin) + vmin;
								
								ComplexNumber z0 = new ComplexNumber(re, im);

								int testRes = FactorialDrawers.divergenceTest(z0,c, limit, episilon,n);
//								if(testRes==-1){
//									gl2.glColor3f(0f, 0f, 0f);
//								}else{
//									gl2.glColor3d((double)testRes/limit, 1-(double)testRes/limit/2.0, 9.8-(double)n/limit/3.0);
//								}
								cntrlr.getActiveScheme().colorScheme(gl2, testRes, limit);
								gl2.glVertex2i(x, y);
							}
						}
						gl2.glEnd();

					}
				});

				JFrame frame = new JFrame("Fractals");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setSize(600, 600);
				frame.getContentPane().add(glcanvas);
				frame.setVisible(true);
				glcanvas.requestFocusInWindow();

			}
		});
	}
}
