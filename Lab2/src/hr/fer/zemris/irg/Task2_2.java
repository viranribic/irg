package hr.fer.zemris.irg;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

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

import hr.fer.zemris.irg.test.util.GraphicsToolbox;
import hr.fer.zemris.linearna.IVector;

/**
 * Second task: triangle drawer. 
 * @author Viran
 *
 */
public class Task2_2 {

	private static GraphicsToolbox tbox=new GraphicsToolbox();
	private static int halfSqareDim=10;	
	
	static{
		GLProfile.initSingleton();
	}
	
	
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				GLProfile glprofile=GLProfile.getDefault();
				GLCapabilities glcapabilites = new GLCapabilities(glprofile);
				final GLCanvas glcanvas= new GLCanvas(glcapabilites);
				
				//Keyboard Listener...
				glcanvas.addKeyListener(new KeyListener() {
					
					@Override
					public void keyTyped(KeyEvent e) {
						e.consume();
						if(e.getKeyChar()=='n'){
							tbox.nextColour();
						}else if(e.getKeyChar()=='p'){
							tbox.previousColour();
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
				
				//Mouse Listener...
				glcanvas.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub
						
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
						tbox.mouseClicked(e.getX(), e.getY());
						glcanvas.display();
					}
				});
				
				glcanvas.addMouseMotionListener(new MouseMotionListener() {
					
					@Override
					public void mouseMoved(MouseEvent e) {
						tbox.mouseMoved(e.getX(), e.getY());
						glcanvas.display();
					}
					
					@Override
					public void mouseDragged(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
				});
				
				//GLEvent Listener
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
						tbox.saveWidth(width);
						tbox.saveHeight(height);
						
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
						
						//draw a triangle filling the window
						gl2.glLoadIdentity();
						
						//draw existing triangles
						int hisotySize=tbox.getTriangleHistorySize();
						for(int i=0;i<hisotySize;i++){
							IVector v=tbox.getFromHistory(i);
							//every new start
							if(i%3==0){
								gl2.glBegin(GL.GL_TRIANGLES);
							}
							
							gl2.glColor3d(v.get(2), v.get(3), v.get(4));
							gl2.glVertex2d(v.get(0), v.get(1));
							//every new end
							if(i%3==2){
								gl2.glEnd();
							}
						}
						//draw the working triangle
						int bufferSize=tbox.getTriangleBufferSize();
						switch(bufferSize){
						case 1:{
							IVector p1=tbox.getFromBuffer(0);
							IVector p2=tbox.getCurrentPoint();
							
							gl2.glBegin(GL.GL_LINE_STRIP);
							
							gl2.glColor3d(p1.get(2), p1.get(3), p1.get(4));
							gl2.glVertex2d(p1.get(0), p1.get(1));
							
							gl2.glColor3d(p2.get(2), p2.get(3), p2.get(4));
							gl2.glVertex2d(p2.get(0), p2.get(1));
							
							gl2.glEnd();
							break;
						}
						case 2:{
							IVector p1=tbox.getFromBuffer(0);
							IVector p2=tbox.getFromBuffer(1);
							IVector p3=tbox.getCurrentPoint();	
							
							gl2.glBegin(GL.GL_TRIANGLES);
							
							gl2.glColor3d(p1.get(2), p1.get(3), p1.get(4));
							gl2.glVertex2d(p1.get(0), p1.get(1));
							
							gl2.glColor3d(p2.get(2), p2.get(3), p2.get(4));
							gl2.glVertex2d(p2.get(0), p2.get(1));
							
							gl2.glColor3d(p3.get(2), p3.get(3), p3.get(4));
							gl2.glVertex2d(p3.get(0), p3.get(1));
							
							gl2.glEnd();
							break;
						}
						default:
							break;
						}


						IVector selCol=tbox.getActiveColour();
						int curWidth=tbox.loadWidth();
						gl2.glColor3d(selCol.get(0), selCol.get(1), selCol.get(2));
						
						gl2.glBegin(GL.GL_TRIANGLES);
						gl2.glVertex2d(curWidth-halfSqareDim, 0);
						gl2.glVertex2d(curWidth,0);
						gl2.glVertex2d(curWidth,halfSqareDim);
						gl2.glEnd();
						
						gl2.glBegin(GL.GL_TRIANGLES);
						gl2.glVertex2d(curWidth,halfSqareDim);
						gl2.glVertex2d(curWidth-halfSqareDim,halfSqareDim);
						gl2.glVertex2d(curWidth-halfSqareDim,0);
						gl2.glEnd();
					}
					
					
				});
			
				JFrame jframe= new JFrame("Prvi program u OpenGL-u");
				jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				jframe.getContentPane().add(glcanvas,BorderLayout.CENTER);
				jframe.setSize(640, 480);
				jframe.setVisible(true);
				glcanvas.requestFocusInWindow();
			}
		});
	}
}
