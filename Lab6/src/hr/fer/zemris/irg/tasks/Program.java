package hr.fer.zemris.irg.tasks;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;

import hr.fer.zemris.objUtil.ObjectModel;

public class Program {

	private static ObjectModel model = new ObjectModel();
	private static GraphicsController controller= new GraphicsController();
	private static IStrategy strategy;
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
		
		//ask user for the mode in which the program should be run:
		
		Scanner in=new Scanner(System.in);
		while(true){
			System.out.println("To test for program 1,2, or 3 please type the corresponding number:\n");
			String line=in.nextLine();
			if(line.trim().equals("1")){
				strategy= new ProgStrat1();
				break;
			}else if(line.trim().equals("2")){
				strategy= new ProgStrat2();
				break;
			}else if(line.trim().equals("3")){
				strategy= new ProgStrat3();
				break;
			}
			System.out.println("Please type only numbers 1 , 2 or 3.");
		}
		in.close();
		
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
						// TODO Auto-generated method stub
						if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
							controller.reset();
						}else if(e.getKeyCode() == KeyEvent.VK_A){
							controller.incrementAngle();
						}else if(e.getKeyCode() == KeyEvent.VK_D){
							controller.decrementAngle();
						}else if(e.getKeyCode() == KeyEvent.VK_W){
							controller.incrementY();
						}else if(e.getKeyCode() == KeyEvent.VK_S){
							controller.decrementY();
						}else if(e.getKeyCode() == KeyEvent.VK_Q){
							controller.increaseRadius();
						}else if(e.getKeyCode() == KeyEvent.VK_E){
							controller.decreaseRadius();
						}
						e.consume();
						glcanvas.display();
					}
				});

				glcanvas.addGLEventListener(new GLEventListener() {

					@Override
					public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
						GL2 gl2 = drawable.getGL().getGL2();
						strategy.reshape(gl2,width,height);
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

						strategy.display(gl2, model, controller);	

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
}
