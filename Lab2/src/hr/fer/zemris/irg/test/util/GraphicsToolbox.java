package hr.fer.zemris.irg.test.util;

import java.util.LinkedList;
import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.Vector;

/**
 * Class in charge of all data control in regard to drawing.
 * @author Viran
 *
 */
public class GraphicsToolbox {
	
	private LinkedList<IVector> pointColorHistory=new LinkedList<>();
	private ColourManager manager=new ColourManager();
	private LinkedList<IVector> triangleBuffer=new LinkedList<>();
	private IVector curMousePos;
	private int width;
	private int height;
	
	/**
	 * GraphicsToolbox constructor.
	 */
	public GraphicsToolbox() {
		IVector colour=manager.getColourRGB();
		this.curMousePos=new Vector(0,0,colour.get(0),colour.get(1),colour.get(2));
	}
	
	/**
	 * Choose next colour in list.
	 */
	public void nextColour(){
		manager.nextColour();
		IVector colour=manager.getColourRGB();
		curMousePos.set(2, colour.get(0)).set(3, colour.get(1)).set(4, colour.get(2));
	
	}
	
	/**
	 * Choose previous colour in list.
	 */
	public void previousColour(){
		manager.prevColour();
		IVector colour=manager.getColourRGB();
		curMousePos.set(2, colour.get(0)).set(3, colour.get(1)).set(4, colour.get(2));
	
	}
	
	/**
	 * Get the currently active colour.
	 * @return RGB colour vector.
	 */
	public IVector getActiveColour(){
		return manager.getColourRGB();
	}
	
	/**
	 * Refresh the mouse position after the user moved it.
	 * @param x New x mouse position.
	 * @param y New y mouse position.
	 */
	public void mouseMoved(int x, int y){
		IVector colour=manager.getColourRGB();
		curMousePos.set(0, x).set(1, y).set(2, colour.get(0)).set(3, colour.get(1)).set(4, colour.get(2));
	}
	
	/**
	 * Perform the necessary operations after the user clicked the mouse.
	 * @param x New x mouse position.
	 * @param y New y mouse position.
	 */
	public void mouseClicked(int x,int y){
		if(triangleBuffer.size()!=2){
			IVector colour=manager.getColourRGB();
			triangleBuffer.add(new Vector(x,y,colour.get(0),colour.get(1),colour.get(2)));
		}else{
			pointColorHistory.addAll(triangleBuffer);
			IVector colour=manager.getColourRGB();
			pointColorHistory.add(new Vector(x,y,colour.get(0),colour.get(1),colour.get(2)));
			triangleBuffer.clear();
		}
	}
	
	/**
	 * Get a vector record form the drawn points.
	 * @param i Point in application history.
	 * @return Vector record. 
	 */
	public IVector getFromHistory(int i){
		return pointColorHistory.get(i);
	}
	
	/**
	 * Get the number of points from application history.
	 * @return Number of points.
	 */
	public int getTriangleHistorySize(){
		return pointColorHistory.size();
	}
	
	/**
	 * Get the point component at the specified index. 
	 * @param i Index of the component.
	 * @return Point in lst.
	 */
	public IVector getFromBuffer(int i){
		return triangleBuffer.get(i);
	}
	
	/**
	 * Get the number of elements waiting in buffer to be formed in a triangle.
	 * @return Number of buffered points. 
	 */
	public int getTriangleBufferSize(){
		return triangleBuffer.size();
	}

	/**
	 * Get the current mouse position.
	 * @return Mouse coordinates as a vector object.
	 */
	public IVector getCurrentPoint() {
		return curMousePos;
	}

	/**
	 * Set the current window width to this object's variable.
	 * @param width Current windows width.
	 */
	public void saveWidth(int width) {
		this.width=width;
		
	}

	/**
	 * Set the current window height to this object's variable. 
	 * @param height Current window height.
	 */
	public void saveHeight(int height) {
		this.height=height;
	}
	
	/**
	 * Get the current window width to this object's variable. 
	 * @return Current window width.
	 */
	public int loadWidth(){
		return width;
	}
	
	/**
	 * Get the current window height to this object's variable. 
	 * @return Current window height.
	 */
	public int loadHeight(){
		return height;
	}
	
}
