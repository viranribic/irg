package hr.fer.zemris.irg.test;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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
import com.jogamp.opengl.glu.GLU;


public class BookExampleJava {

	static{
		GLProfile.initSingleton();
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				GLProfile glprofile= GLProfile.getDefault();
				GLCapabilities glcapabilities=new GLCapabilities(glprofile);
				final GLCanvas glcanvas= new GLCanvas(glcapabilities);
				
				//Reagiranje na pritiske tipke na mišu...
				glcanvas.addMouseListener( new MouseListener() {
					
					@Override
					public void mouseReleased(java.awt.event.MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mousePressed(java.awt.event.MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseExited(java.awt.event.MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseEntered(java.awt.event.MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseClicked(java.awt.event.MouseEvent e) {
						System.out.println("Mis je kliknut na: x="+e.getX()+", y="+e.getY());
						// Napravi nesto
						// ...
						//Posalji zahtjev za ponovnim crtanjem
						glcanvas.display();
					}
				});
				
				//Reagiranje na pomicanje pokazivaca misa...
				glcanvas.addMouseMotionListener(new MouseMotionListener() {
					
					@Override
					public void mouseMoved(java.awt.event.MouseEvent e) {
						System.out.println("Mis pomaknut na: x="+e.getX()+", y="+e.getY());
						// Napravi nesto
						// ...
						// Posalji zahtjev za ponovnim crtanjem...
						glcanvas.display();
					}
					
					@Override
					public void mouseDragged(java.awt.event.MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
				});
				
				//Reagiranje na pritiske tipaka na tipkovnici...
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
						if(e.getKeyCode() == KeyEvent.VK_R){
							e.consume();
							//Napravi nesto
							// ...
							// Posalji zahtjev za ponovnim crtanjem
							glcanvas.display();
						}
						
					}
				});
			
				//Reagiranje na promjenu velicine platna, na zahtjev za crtanjem i slicno...
				glcanvas.addGLEventListener(new GLEventListener(
						) {
					
					@Override
					public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
						GL2 gl2=drawable.getGL().getGL2();
						gl2.glMatrixMode(GL2.GL_PROJECTION);
						gl2.glLoadIdentity();
						
						//coordinate system origin at lower left with width and height same as the window
						GLU glu = new GLU();
						glu.gluOrtho2D(0.0f, width, 0.0f, height);
						
						gl2.glMatrixMode(GL2.GL_MODELVIEW);
						gl2.glLoadIdentity();
						
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
						int width=drawable.getSurfaceWidth();
						int height=drawable.getSurfaceHeight();
						
						gl2.glClear(GL.GL_COLOR_BUFFER_BIT);
						
						//draw a triangle filling the window
						gl2.glLoadIdentity();
						gl2.glBegin(GL.GL_TRIANGLES);
						gl2.glColor3f(1, 0, 0);
						gl2.glVertex2f(0, 0);
						gl2.glColor3f(0, 1, 0);
						gl2.glVertex2f(width, 0);
						gl2.glColor3f(0, 0, 1);
						gl2.glVertex2f(width/2, height);
						gl2.glEnd();
						
					}
				});
			
				final JFrame jframe=new JFrame("Primjer prkaza obojanog trokuta.");
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
				jframe.setSize(640, 480);
				jframe.setVisible(true);
				glcanvas.requestFocusInWindow();
			}
				
			
		});
		
		
	
	}
}
