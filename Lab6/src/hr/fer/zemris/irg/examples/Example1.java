package hr.fer.zemris.irg.examples;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;


public class Example1 {

	static{
		GLProfile.initSingleton();
	}
	
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater( new Runnable() {
			
			@Override
			public void run() {
				GLProfile glprofile=GLProfile.getDefault();
				GLCapabilities glcapabilities= new  GLCapabilities(glprofile);
				final GLCanvas glcanvas=new GLCanvas(glcapabilities);
				
				glcanvas.addGLEventListener(new GLEventListener() {
					
					@Override
					public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
						GL2 gl2 = drawable.getGL().getGL2();

						gl2.glMatrixMode(GL2.GL_PROJECTION);
						gl2.glLoadIdentity();
						
						gl2.glViewport(width/4, height/4, width/2, height/2);
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
						GL2 gl2=drawable.getGL().getGL2();
						
						
						gl2.glClearColor(1, 1, 1, 0);
						gl2.glClear(GL.GL_COLOR_BUFFER_BIT);
						
						gl2.glMatrixMode(GL2.GL_MODELVIEW);
						gl2.glLoadIdentity();
						
//						gl2.glColor3f(1, 0, 0);
//						gl2.glBegin(GL.GL_LINE_STRIP);
//						gl2.glVertex3f(-.9f, -.9f, -.9f);
//						gl2.glVertex3f( .9f, -.9f, -.9f);
//						gl2.glEnd();
//						
//						gl2.glColor3f(1, 0, 0);
//						gl2.glBegin(GL.GL_LINE_STRIP);
//						gl2.glVertex3f(-.9f, -.7f, -.9f);
//						gl2.glVertex3f( .9f, -.7f, 3.1f);
//						gl2.glEnd();
						
						gl2.glBegin(GL.GL_TRIANGLE_FAN);
						
						gl2.glColor3f(1f, 0f, 0f);
						gl2.glVertex3f(-.9f, -.9f, -0.99f);
						
						gl2.glColor3f(0f, 1f, 0f);
						gl2.glVertex3f(+.9f, -.9f, -0.99f);
						
						gl2.glColor3f(0f, 0f, 1f);
						gl2.glVertex3f(-.75f, +.75f, +2f);
						
						gl2.glEnd();
						gl2.glColor3f(0, .5f, .5f);
						gl2.glBegin(GL.GL_LINE_LOOP);
						gl2.glPointSize(10);
						gl2.glVertex3f(-.95f, -.95f, -.95f);
						gl2.glVertex3f(+.95f, -.95f, -.95f);
						gl2.glVertex3f(+.95f, +.95f, -.95f);
						gl2.glVertex3f(-.95f, +.95f, -.95f);
						
						gl2.glEnd();
						
					}
				});
				
				JFrame jframe= new JFrame("Lab6Example1");
				jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				jframe.setSize(640, 480);
				jframe.getContentPane().add(glcanvas,BorderLayout.CENTER);
				jframe.setVisible(true);
				glcanvas.requestFocusInWindow();
			}
		});
		
	}
}
