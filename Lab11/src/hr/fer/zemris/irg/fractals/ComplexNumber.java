package hr.fer.zemris.irg.fractals;

public class ComplexNumber {

	private double re;
	private double im;

	public ComplexNumber() {
		this(0, 0);
	}

	public ComplexNumber(double re) {
		this(re, 0);
	}

	public ComplexNumber(double re, double im) {
		this.re = re;
		this.im = im;
	}

	// getters & setters
	public double getRe() {
		return re;
	}

	public ComplexNumber setRe(double re) {
		this.re = re;
		return this;
	}

	public double getIm() {
		return im;
	}

	public ComplexNumber setIm(double im) {
		this.im = im;
		return this;
	}
	
	//angle & magnitude getters & setters
	
	public double getMagnitude(){
		return Math.sqrt( re*re+im*im);
	}

	public double getAngle(){
		return Math.atan2(im, re);
	}
	
	public ComplexNumber setAngle(double angle){
		double magnitude=this.getMagnitude();
		return this.setMagnitudeAngle(magnitude, angle);
		
	}
	
	public ComplexNumber setMagnitude(double magnitude){
		double angle=this.getAngle();
		return this.setMagnitudeAngle(magnitude, angle);
	}
	
	public ComplexNumber setMagnitudeAngle(double magnitude, double angle){
		this.re=magnitude*Math.cos(angle);
		this.im=magnitude*Math.sin(angle);
		return this;
	}
	
	public ComplexNumber toPower(int n){
		double magnitude=Math.pow(this.getMagnitude(),n);
		double angel=this.getAngle()*n;
		this.setMagnitudeAngle(magnitude, angel);
		return this;
	}
	
	public ComplexNumber nToPower(int n){
		return this.clone().nToPower(n);
	}

	public ComplexNumber add(ComplexNumber other){
		this.re+=other.re;
		this.im+=other.im;
		return this;
	}
	
	public ComplexNumber nAdd(ComplexNumber other){
		return this.clone().add(other);
	}
	
	public ComplexNumber sub(ComplexNumber other){
		this.re-=other.re;
		this.im-=other.im;
		return this;
	}
	
	public ComplexNumber nSub(ComplexNumber other){
		return this.clone().sub(other);
	}
	
	public ComplexNumber mul(ComplexNumber other){
		double magnitude=this.getMagnitude()*other.getMagnitude();
		double angle=this.getAngle()+other.getAngle();
		return this.setMagnitudeAngle(magnitude, angle);
	}
	
	public ComplexNumber nMul(ComplexNumber other){
		return this.clone().mul(other);
	}
	
	public ComplexNumber div(ComplexNumber other){
		double magnitude=this.getMagnitude()/other.getMagnitude();
		double angle=this.getAngle()-other.getAngle();
		return this.setMagnitudeAngle(magnitude, angle);
	}
	
	public ComplexNumber nDiv(ComplexNumber other){
		return this.clone().div(other);
	}
	
	public ComplexNumber clone(){
		return new ComplexNumber(re, im);
	}

	public ComplexNumber[] roots(int n){
		ComplexNumber[] roots=new ComplexNumber[n];
		double magnitude=Math.pow(this.getMagnitude(),1./n);
		for(int k=0;k<n;k++){
			double alpha=(this.getAngle()+2*Math.PI*k)/n;
			roots[k]=new ComplexNumber();
			roots[k].setMagnitudeAngle(magnitude, alpha);
		}
		return roots;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(im);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(re);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComplexNumber other = (ComplexNumber) obj;
		if (Double.doubleToLongBits(im) != Double.doubleToLongBits(other.im))
			return false;
		if (Double.doubleToLongBits(re) != Double.doubleToLongBits(other.re))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("%.3f "+((Math.signum(im)<0)?"-":"+")+" i%.3f", this.re,Math.abs(this.im));
	}
}
