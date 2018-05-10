package hr.fer.zemris.irg.fractals.ifs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Random;

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

import hr.fer.zemris.irg.fractals.colorSchemes.IColorScheme;
import hr.fer.zemris.irg.fractals.ifs.IFSController.FunctionCoefs;

public class IFSFractals {

	private static IFSController cntrlr ;
	static {
		GLProfile.initSingleton();
	}

	public static void main(String[] args) {
		if(args.length!=1){
			System.out.println("Only one argument allowed: the path to the functions file.");
			return;
		}
		
		cntrlr=new IFSController(args[0]);
		
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				GLProfile glprofile = GLProfile.getDefault();
				GLCapabilities glcapabilities = new GLCapabilities(glprofile);
				final GLCanvas glcanvas = new GLCanvas(glcapabilities);

				

				

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
						renderSceneIFS(gl2);
					}

					private void renderSceneIFS(GL2 gl2) {
						Random rand=cntrlr.getRandom();
						int limit=cntrlr.getLimit();
						gl2.glPointSize(1);
						gl2.glColor3f(.0f, .7f, .3f);
						gl2.glBegin(GL2.GL_POINTS);
						double x0,y0;
						for(int counter=0;counter<cntrlr.getPointsNum();counter++){
							x0=0;
							y0=0;
							for(int iter=0;iter<limit;iter++){
								double x=0;
								double y=0;
								
								double p=rand.nextDouble();
								FunctionCoefs fc=cntrlr.getCoeficients(p);
								x= (fc.a*x0 + fc.b*y0)+fc.e;
								y= (fc.c*x0 + fc.d*y0)+fc.f;
								x0=x;
								y0=y;
							}
							double eta1=cntrlr.getEta1();
							double eta2=cntrlr.getEta2();
							double eta3=cntrlr.getEta3();
							double eta4=cntrlr.getEta4();
							
							gl2.glVertex2i(round(eta1*x0+eta2), round(eta3*y0+eta4));
							
						}
						gl2.glEnd();
					}

					private int round(double d) {
						if( d>=0) return (int)(d+0.5);
						return (int)(d-0.5);
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
