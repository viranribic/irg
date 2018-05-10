package hr.fer.zemris.irg.tasks;

import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.IncompatibleOperandException;
import hr.fer.zemris.linearna.Vector;

public class Shader {

	private IVector lightPosition;
	private IVector ambientIntensity;
	private IVector diffuseIntensity;
	private IVector specularIntensity;
	private IVector materialDiffuse;
	private IVector materialAmbient;
	private IVector materialSpecular;
	private int n;

	public Shader(IVector lightPosition, IVector ambientIntensity, IVector diffuseIntensity, IVector specularIntensity,
			IVector materialAmbient, IVector materialDiffuse, IVector materialSpecular, int n) {
		this.lightPosition = lightPosition;
		this.ambientIntensity = ambientIntensity;
		this.diffuseIntensity = diffuseIntensity;
		this.specularIntensity = specularIntensity;
		this.materialAmbient = materialAmbient;
		this.materialDiffuse = materialDiffuse;
		this.materialSpecular = materialSpecular;
		this.n = n;
	}

	public IVector color(IVector eye, IVector point, IVector normal) {
		try {
			IVector l = this.lightPosition.nSub(point);
			double d_add_k=l.norm()+1e-5;
			l.normalise();

			IVector r = normal.nScalarMultiply(2 * normal.scalarProduct(l)).sub(l).normalise();
			
			IVector v = eye.nSub(point).normalise();
			double ln = l.scalarProduct(normal);
			ln=(ln<0)?0:ln;
			double rvN= Math.pow(r.scalarProduct(v), n);
			
			double Ir = generateIntensity(ambientIntensity.get(0),diffuseIntensity.get(0),specularIntensity.get(0),materialAmbient.get(0),materialDiffuse.get(0),materialSpecular.get(0),ln,rvN,d_add_k);
			double Ig = generateIntensity(ambientIntensity.get(1),diffuseIntensity.get(1),specularIntensity.get(1),materialAmbient.get(1),materialDiffuse.get(1),materialSpecular.get(1),ln,rvN,d_add_k);
			double Ib = generateIntensity(ambientIntensity.get(2),diffuseIntensity.get(2),specularIntensity.get(2),materialAmbient.get(2),materialDiffuse.get(2),materialSpecular.get(2),ln,rvN,d_add_k);

			return new Vector(Ir, Ig, Ib);
		} catch (IncompatibleOperandException e) {
			System.out.println("Error in color determination.");
			System.exit(-1);
			return null;
		}
	}

	private double generateIntensity(double Isrc_a, double Isrc_d, double Isrc_s, double ka, double kd, double ks,
			double ln, double rvN, double d_add_k) {
		double Ig = ka * Isrc_a;
		double Id = kd * Isrc_d * ln;
		double Is = ks * Isrc_s * rvN;

		return Ig + (Id + Is) ;// / (d_add_k);
	}
}
