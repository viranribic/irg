package hr.fer.zemris.irg.lab8;

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

import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.IncompatibleOperandException;
import hr.fer.zemris.linearna.Matrix;
import hr.fer.zemris.linearna.Vector;

/**
 * Lab assignment task 4.
 * 
 * @author Viran
 *
 */
public class Zadatak8 {
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
						// TODO Auto-generated method stub

					}

					@Override
					public void keyReleased(KeyEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void keyPressed(KeyEvent e) {
						if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
							controller.reset();
						}else if (e.getKeyCode() == KeyEvent.VK_1) {
							controller.toggleControlPolygon();
						}else if (e.getKeyCode() == KeyEvent.VK_2) {
							controller.toggleApproximation();
						}else if (e.getKeyCode() == KeyEvent.VK_3) {
							controller.toggleInterpolation();
						}
						e.consume();
						glcanvas.display();
					}
				});

				glcanvas.addMouseMotionListener(new MouseMotionListener() {

					@Override
					public void mouseMoved(MouseEvent e) {

					}

					@Override
					public void mouseDragged(MouseEvent e) {

						if (SwingUtilities.isRightMouseButton(e)) {
							controller.mouseMovedTo(e.getPoint());
							glcanvas.display();
						}

					}
				});

				glcanvas.addMouseListener(new MouseListener() {

					@Override
					public void mouseReleased(MouseEvent e) {
						if (SwingUtilities.isRightMouseButton(e)) {
							controller.relesedRight(e.getPoint());

						}
						glcanvas.display();
					}

					@Override
					public void mousePressed(MouseEvent e) {
						if (SwingUtilities.isRightMouseButton(e)) {
							controller.pressedRight(e.getPoint());

						}
						glcanvas.display();
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
						if (SwingUtilities.isLeftMouseButton(e)) {

							controller.clickedLeft(e.getPoint());
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
						gl2.glClear(GL.GL_COLOR_BUFFER_BIT);

						LinkedList<IVector> points = controller.getPoints();

						IVector[] pointsArray = new IVector[points.size()];
						// draw control
						points.toArray(pointsArray);
						if(controller.controlPolyEnabled())
							drawControlPolygon(gl2, pointsArray);
						// draw approximation
						if(controller.approximationEnabled())
							bezierApproximationArc(pointsArray, 20, gl2);
						// draw interpolation
						if(controller.interpolationEnabled())
							bezierInterpolationArc(points, 20, gl2);

					}

					private void bezierInterpolationArc(LinkedList<IVector> points, int divs, GL2 gl2) {
						if(points.size()<=2) return;
						IMatrix BiTiP=generateBiTiPMatrix(points);
						IVector[] pointsArray = new IVector[points.size()];
						points.toArray(pointsArray);
						IMatrix B = generateCoefMatric(pointsArray.length);
						IMatrix TiP=null;
						try {TiP=B.nMatrixMultiply(BiTiP);} catch (IncompatibleOperandException e) {}
						
						//-----------------------------------------------------------
						gl2.glBegin(GL.GL_LINE_STRIP);
						for (int i = 0; i <= divs; i++) {
							double t = 1. / divs * i;
							IMatrix T=genTVector(t,points.size());
							try {T=T.nMatrixMultiply(TiP);} catch (IncompatibleOperandException e) {}
							gl2.glVertex2d(T.get(0,0), T.get(0,1));
						}
						gl2.glEnd();
						//-----------------------------------------------------------
						
						
					}

					private IMatrix genTVector(double t, int size) {
						if (size==0) return null;
						if (size==1) return new Matrix( new Vector(1));
						double[] T=new double[size];
						T[size-1]=1;
						for(int i=size-2;i>=0;i--){
							T[i]=t;
							t*=t;
						}
						
						return new Matrix( new Vector(T));
					}

					private void drawControlPolygon(GL2 gl2, IVector[] array) {
						gl2.glBegin(GL2.GL_LINE_STRIP);
						gl2.glColor3f(1.f, 0.f, 0.f);
						for (IVector v : array)
							gl2.glVertex2f((float) v.get(0), (float) v.get(1));
						gl2.glEnd();
					}

					public IMatrix generateBiTiPMatrix(LinkedList<IVector> points) {
						if (points.size() <= 2)
							return null;
						//IVector[] controlPoints = new IVector[points.size()];
						IVector[] pointsArray = new IVector[points.size()];
						IMatrix P = new Matrix(points.toArray(pointsArray));
						IMatrix T = generateParamtersMatrix(pointsArray.length);
						IMatrix B = generateCoefMatric(pointsArray.length);
						IMatrix BiTiP = null;
						IMatrix Bi = null;
						IMatrix Ti = null;
						IMatrix BiTi=null;
						try {
							Bi=B.nInvert();
							Ti=T.nInvert();
							BiTi=Bi.nMatrixMultiply(Ti);
							BiTiP=BiTi.nMatrixMultiply(P);
						} catch (IncompatibleOperandException e) {
						}
						return BiTiP;
				//
//						double[][] matrix = BiTiP.toArray();
//						for (int i = 0; i < controlPoints.length; i++)
//							controlPoints[i]=new Vector(matrix[i]);
//						return controlPoints;
					}

					private IMatrix generateCoefMatric(int order) {
						double[][] matrix = generateBernsteinMatix(order);
						IVector[] vectors = new IVector[order];

						for (int i = 0; i < order; i++)
							vectors[i] = new Vector(matrix[i]);

						return new Matrix(vectors);
					}

					private double[][] generateBernsteinMatix(int order) {
						if (order <= 0)
							return new double[][] { { 1 } };
						double[] binCoefs = generateBinaryCoeficients(order);

						double[][] matrix = new double[order][order];

						for (int i = 0; i < order; i++) {
							double[] polyCoefs = generateBinaryPolynomCoefs(order - i);

							for (int j = 0; j < polyCoefs.length; j++) {
								matrix[i][j] = binCoefs[i] * polyCoefs[j];

							}
						}

						return matrix;
					}

					private double[] generateBinaryPolynomCoefs(int order) {
						if (order <= 0)
							return new double[] { 1 };
						double[] binaryCoeficinets = generateBinaryCoeficients(order);
						for (int i = 0; i < binaryCoeficinets.length; i++)
							if (i % 2 == 1)
								binaryCoeficinets[i] *= -1;
						return binaryCoeficinets;
					}

					private double[] generateBinaryCoeficients(int size) {
						if (size <= 0)
							return new double[] { 1 };
						double[] array = new double[size];
						double n = size - 1;
						double nCk = 1;
						int loop = (size % 2 == 0) ? size / 2 : size / 2 + 1;
						for (int k = 0; k < loop; k++) {
							array[k] = array[size - k - 1] = nCk;
							nCk *= (n - k) / (k + 1);
						}
						return array;
					}

					private IMatrix generateParamtersMatrix(int dim) {
						IVector[] rows = new IVector[dim];

						for (int dimIndex = 0; dimIndex < dim; dimIndex++) {
							double t = ((double) dimIndex) / (dim-1); //it just has to be -1

							double[] rowVals = new double[dim];
							// last position is always 1
							rowVals[dim - 1] = 1;
							for (int index = dim - 2; index >= 0; index--) {
								rowVals[index] = t;
								t *= t;
							}
							rows[dimIndex] = new Vector(rowVals);

						}

						return new Matrix(rows);
					}

					private int[] factors(int n, int[] array) {
						int a = 1;

						for (int i = 1; i <= n + 1; i++) {
							array[i - 1] = a;
							a = a * (n - i + 1) / i;
						}
						return array;
					}

					public void bezierApproximationArc(IVector[] points, int divs, GL2 canvas) {
						if (points == null)
							return;
						if (points.length <= 2)
							return;
						// IVector
						// p=points[0].newInstance(points[0].getDimension());
						IVector p = new Vector(0, 0);

						int n = points.length - 1;
						double t, b;

						int[] factors = factors(n, new int[points.length]);

						canvas.glBegin(GL.GL_LINE_STRIP);
						for (int i = 0; i <= divs; i++) {

							t = 1. / divs * i;
							p.set(0, 0).set(1, 0);

							for (int j = 0; j <= n; j++) {

								if (j == 0) {
									b = factors[j] * Math.pow(1 - t, n);
								} else if (j == n) {
									b = factors[j] * Math.pow(t, n);
								} else {
									b = factors[j] * Math.pow(1 - t, n - j) * Math.pow(t, j);
								}
								p.set(0, p.get(0) + b * points[j].get(0)).set(1, p.get(1) + b * points[j].get(1));

								// try {p.add(points[j].nScalarMultiply(b)); }
								// catch (IncompatibleOperandException
								// ignorable) {}
							}

							canvas.glVertex2d(p.get(0), p.get(1));
						}
						canvas.glEnd();

					}
				});

				JFrame jframe = new JFrame("Bezierove krivulje");
				jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				jframe.setSize(640, 480);
				jframe.getContentPane().add(glcanvas, BorderLayout.CENTER);
				jframe.setVisible(true);
				glcanvas.requestFocusInWindow();
			}

		});
	}

}
