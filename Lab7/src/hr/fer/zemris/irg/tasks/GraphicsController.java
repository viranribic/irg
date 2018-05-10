package hr.fer.zemris.irg.tasks;

import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.IncompatibleOperandException;
import hr.fer.zemris.linearna.Vector;
import hr.fer.zemris.objUtil.Face3D;
import hr.fer.zemris.objUtil.ObjectModel;
import hr.fer.zemris.objUtil.Vertex3D;
import hr.fer.zemris.optimisationAlgorithms.OptimisationAlgorithms;

public class GraphicsController {

	private static final double INIT_ANGLE = Math.atan2(1, 3);
	private static final double INCREMENT = 1;
	private static final double INIT_R = 1 / Math.sin(INIT_ANGLE);
	private static final double EYE_Y = 4; // Initial: (x,y,z)=(3,4,1)

	private double workingAngle = INIT_ANGLE;
	private double workingR = INIT_R;
	private double workingY = EYE_Y;
	private short optimisationCode;

	public void reset() {
		this.workingAngle = INIT_ANGLE;
		this.workingR = INIT_R;
		this.workingY = EYE_Y;
	}

	public void incrementAngle() {
		this.workingAngle += INCREMENT;
	}

	public void decrementAngle() {
		this.workingAngle -= INCREMENT;
	}

	public double getEyeX() {
		return workingR * Math.cos(workingAngle);
	}

	public double getEyeY() {
		return workingY;
	}

	public double getEyeZ() {
		return workingR * Math.sin(workingAngle);
	}

	public void incrementY() {
		this.workingY++;

	}

	public void decrementY() {
		this.workingY--;
	}

	public void increaseRadius() {
		this.workingR++;
	}

	public void decreaseRadius() {
		this.workingR--;
	}

	public void disableOptimisations() {
		System.out.println("No algorithm is being applied.");
		this.optimisationCode = 0;
	}

	public void enableOpt1() {
		System.out.println("Algorithm 1 is being applied.");
		this.optimisationCode = 1;
	}

	public void enableOpt2() {
		System.out.println("Algorithm 2 is being applied.");
		this.optimisationCode = 2;
	}

	public void enableOpt3() {
		System.out.println("Algorithm 3 is being applied.");
		this.optimisationCode = 3;
	}

	public void optUsingSurfaces(ObjectModel model) {
		if (this.optimisationCode == 1)
			model.determinateFaceVisibilities1(new Vector(this.getEyeX(), this.getEyeY(), this.getEyeZ()));
		else if(this.optimisationCode == 0)
			this.setAllFacesVisible(model);
			
	}

	private void setAllFacesVisible(ObjectModel model) {
		for(Face3D face:model.getFaceIterable())
			face.setVisible(true);
	}

	public void optUsingNormalVector(ObjectModel model) {
		if (this.optimisationCode == 2)
			model.determinateFaceVisibilities2(new Vector(this.getEyeX(), this.getEyeY(), this.getEyeZ()));
		else if(this.optimisationCode == 0)
			this.setAllFacesVisible(model);
	}

	public boolean opIn2DKeep(IVector[] points2d) throws IncompatibleOperandException {
		if (this.optimisationCode == 3) {
			return OptimisationAlgorithms.isAntiClockwise(points2d);
		} else
			return true;
	}

}
