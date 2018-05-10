package hr.fer.zemris.irg.tasks;

public class GraphicsController {

	private static final double INIT_ANGLE = Math.atan2(1,3);
	private static final double INCREMENT=1;
	private static final double INIT_R = 1/Math.sin(INIT_ANGLE);
	private static final double EYE_Y=4; //Initial: (x,y,z)=(3,4,1)
	
	private double workingAngle=INIT_ANGLE;
	private double workingR=INIT_R;
	private double workingY=EYE_Y;
	
	
	public void reset() {
		this.workingAngle=INIT_ANGLE;
		this.workingR=INIT_R;
		this.workingY=EYE_Y;
	}

	public void incrementAngle() {
		this.workingAngle+=INCREMENT;
	}

	public void decrementAngle() {
		this.workingAngle-=INCREMENT;
	}

	public double getEyeX(){
		return workingR*Math.cos(workingAngle);
	}
	
	public double getEyeY(){
		return workingY;
	}
	
	public double getEyeZ(){
		return workingR*Math.sin(workingAngle);
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
}
