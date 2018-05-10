package hr.fer.zemris.irg.lab8;

import java.awt.Point;
import java.util.LinkedList;

import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.Vector;

public class GraphicsController {

	// private static final int epsilon = 3;
	private static final int epsilonPow2 = 16;

	private LinkedList<IVector> positions = new LinkedList<>();

	private boolean isHolding = false;
	private int holdingIndex = -1;

	private boolean controlPolyEnabled=true;

	private boolean approximationEnabled=true;

	private boolean interpolationEnabled=true;

	public void reset() {
		this.positions.clear();
		System.out.println("Reset");
	}

	public void relesedRight(Point point) {
		IVector pos = new Vector(point.getX(), point.getY());
		if (this.isHolding) {
			this.positions.set(this.holdingIndex, pos);
			this.isHolding = false;
			this.holdingIndex = -1;
			System.out.println("This.isHolding: " + this.isHolding + " this.holdingIndex " + this.holdingIndex);

		}
		System.out.println("Right mouse button relesed");
	}

	public void mouseMovedTo(Point point) {
		if (this.isHolding) {
			IVector pos = new Vector(point.getX(), point.getY());
			this.positions.set(this.holdingIndex, pos);
			//this.positions.remove(this.holdingIndex+1);
			System.out.println("This.isHolding: " + this.isHolding + " this.holdingIndex " + this.holdingIndex);

		}
		System.out.println("Mouse moved");
	}

	public void pressedRight(Point point) {
		IVector pos = new Vector(point.getX(), point.getY());

		this.holdingIndex = findClosestIndex(pos);
		if (!this.isHolding && this.holdingIndex!=-1) {
			this.positions.set(this.holdingIndex, pos);
			//this.positions.remove(this.holdingIndex+1);
			this.isHolding = true;

			System.out.println("This.isHolding: " + this.isHolding + " this.holdingIndex " + this.holdingIndex);
		} // else continue
		System.out.println("Right mouse button pressed");
	}

	private int findClosestIndex(IVector pos) {
		int xDist, yDist;
		for (int i = 0; i < this.positions.size(); i++) {
			IVector listPos = this.positions.get(i);
			xDist = (int) (listPos.get(0) - pos.get(0));
			yDist = (int) (listPos.get(1) - pos.get(1));
			if ((xDist * xDist) + (yDist + yDist) <= (epsilonPow2))
				return i;
		}
		return -1;
	}

	public void clickedLeft(Point point) {
		this.positions.add(new Vector(point.getX(), point.getY()));
		System.out.println(this.positions);
		System.out.println("Left mouse button clicked");
	}

	public boolean inMoveState() {
		return isHolding;
	}

	public void mouseMovedTo_safe(Point point) {
		if (this.inMoveState()) {
			IVector pos = new Vector(point.getX(), point.getY());
			this.positions.set(this.holdingIndex, pos);
		}

	}

	public LinkedList<IVector> getPoints() {
		return this.positions;
	}

	public void toggleControlPolygon() {
		this.controlPolyEnabled=(this.controlPolyEnabled)?false:true;
		
	}

	public void toggleApproximation() {
		this.approximationEnabled=(this.approximationEnabled)?false:true;
	}

	public void toggleInterpolation() {
		this.interpolationEnabled=(this.interpolationEnabled)?false:true;
	}

	public boolean controlPolyEnabled() {
		return this.controlPolyEnabled;
	}

	public boolean approximationEnabled() {
		return this.approximationEnabled;
	}

	public boolean interpolationEnabled() {
		return this.interpolationEnabled;
	}

}
