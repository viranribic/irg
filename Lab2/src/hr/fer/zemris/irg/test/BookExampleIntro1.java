package hr.fer.zemris.irg.test;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;

public class BookExampleIntro1 {

	static{
		GLProfile.initSingleton();
		
	}
	
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				GLProfile glprofile=GLProfile.getDefault();
				GLCapabilities glcapabilities=new GLCapabilities(glprofile);
				final GLCanvas glcanvas=new GLCanvas(glcapabilities);
				
				glcanvas.addGLEventListener(new GLEventListener() {
					
					@Override
					public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
						GL2 gl2=drawable.getGL().getGL2();
						
						gl2.glDisable(GL.GL_DEPTH_TEST);
						gl2.glViewport(0, 0, width, height);
						gl2.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
						gl2.glLoadIdentity();
						gl2.glOrtho(0, width-1, height-1, 0, 0, 1);
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
						
						gl2.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
						gl2.glClear(GL.GL_COLOR_BUFFER_BIT);
						
						//draw a triangle filling the window
						gl2.glLoadIdentity();
						
						renderScene(gl2);
						//drawable.swapBuffers();
					}

					private void renderScene(GL2 gl2) {
						//gl2.glPointSize(1.0f);
						gl2.glColor3f(0.0f, 1.0f, 1.0f);
						gl2.glBegin(GL.GL_POINTS);
						gl2.glVertex2i(0, 0);
						gl2.glVertex2i(2, 2);
						gl2.glVertex2i(4, 4);
						gl2.glEnd();
						
						gl2.glBegin(GL.GL_LINE_STRIP);
						gl2.glVertex2i(50, 50);
						gl2.glVertex2i(150, 150);
						gl2.glVertex2i(50, 150);
						gl2.glVertex2i(50, 50);
						gl2.glEnd();
						
						//gl2.glPointSize(5.0f);
						gl2.glBegin(GL.GL_POINTS);
						gl2.glVertex2i(20,20);
						gl2.glVertex2i(30,20);
						gl2.glEnd();
						
						
					}
				});
			
				final JFrame jframe=new JFrame("Primjer1");
				jframe.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
				jframe.addWindowListener(new WindowListener() {
					
					@Override
					public void windowOpened(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowIconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeiconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeactivated(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowClosing(WindowEvent e) {
						jframe.dispose();
						System.exit(0);
						
					}
					
					@Override
					public void windowClosed(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowActivated(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
				});
				jframe.getContentPane().add(glcanvas, BorderLayout.CENTER);
				jframe.setSize(200, 200);
				jframe.setVisible(true);
				jframe.setLocation(0, 0);
				glcanvas.requestFocusInWindow();
			}
		});
	}
}
