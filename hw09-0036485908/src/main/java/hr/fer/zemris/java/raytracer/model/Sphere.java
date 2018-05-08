package hr.fer.zemris.java.raytracer.model;

import hr.fer.zemris.math.Vector3;

public class Sphere extends GraphicalObject {

	private Point3D center;
	private double radius;
	private double kdr;
	private double kdg;
	private double kdb;
	private double krr;
	private double krg;
	private double krb;
	private double krn;

	//@formatter:off
	public Sphere(Point3D center, double radius,
				  double kdr, double kdg, double kdb,
				  double krr, double krg, double krb,
				  double krn) {
	//@formatter:off
		
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	//pseudocode used available at: http://www.lighthouse3d.com/tutorials/maths/ray-sphere-intersection/
	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {
		
		Point3D vpc = center.sub(ray.start);
		Point3D d = ray.direction;
		double scalarProduct = vpc.scalarProduct(d);
		
		//p is outside of the sphere
		if(scalarProduct < 0) {
			return null;
		}
		
		//distance can be computed
		vpc.scalarProduct(d);
		
	}

}
