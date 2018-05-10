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

import hr.fer.zemris.irg.util.GraphicsToolbox;
import hr.fer.zemris.linearna.IVector;

/**
 * Third lab assignment task.
 * @author Viran
 *
 */
public class Task3_2 {

	private static GraphicsToolbox tbox= new GraphicsToolbox();
	
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
				
				//Keyboard Listener...
				glcanvas.addKeyListener(new KeyListener() {
					
					@Override
					public void keyTyped(KeyEvent e) {
						e.consume();
						if(e.getKeyChar()=='k'){
							tbox.toggleControl();
						}else if(e.getKeyChar()=='o'){
							tbox.toggleLineClipping();
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
						
						if(tbox.isClippingActive())
							gl2.glClearColor(0.0f, 1.0f, 0.0f, 1.0f);
						else
							gl2.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
						gl2.glClear(GL.GL_COLOR_BUFFER_BIT);
						
						gl2.glLoadIdentity();
						
						//draw existing lines
						gl2.glColor3d(0.0f,0.0f,0.0f);
						int hisotySize=tbox.getLineHistorySize();
						for(int i=0;i<hisotySize;i+=2){
							IVector v1=tbox.getFromHistory(i);
							IVector v2=tbox.getFromHistory(i+1);
							tbox.drawLine(gl2,v1.copy(),v2.copy());
						}
						
						
						//draw the  triangle
						IVector lineBuffer=tbox.getFromBuffer();
						if(lineBuffer!=null){
							IVector p1=lineBuffer;
							IVector p2=tbox.getCurrentPoint();
							tbox.drawLine(gl2,p1.copy(),p2.copy());
						}
					}
				});
				
				JFrame jframe=new JFrame("Crtanje linija na rasterskim prikaznim jedinicama");
				jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				jframe.setSize(640, 480);
				jframe.getContentPane().add(glcanvas,BorderLayout.CENTER);
				jframe.setVisible(true);
				glcanvas.requestFocusInWindow();
			}
		});
	}
}
