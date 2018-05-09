package hr.fer.zemris.java.raytracer.model;

import static java.lang.Math.sqrt;
import static java.lang.Math.pow;
import static java.lang.Math.abs;

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

	//pseudocode and algorithm used available at: http://www.lighthouse3d.com/tutorials/maths/ray-sphere-intersection/
	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {
		
		Point3D vpc = center.sub(ray.start);
		Point3D d = ray.direction;
		double scalarProduct = vpc.scalarProduct(d);
		boolean isOuter;
		
		//p is outside of the sphere
		if(scalarProduct < 0) {
			return null;
		}
		//distance can be computed
		
		vpc.scalarProduct(d);
		
		Point3D pc = center.vectorProduct(ray.direction); //projection of c on the line, to be calcuated
		
		//no intersection
		if((abs(distanceBeetweenPoints(pc, center)) > radius)){
			return null;
		}
		
		double dist = sqrt(pow(radius, 2) - pow(abs(distanceBeetweenPoints(pc, center)), 2));
		double di1;
		
		//origin is outside of sphere
		if(vpc.norm() > radius) {
			di1 = abs(abs(distanceBeetweenPoints(pc, ray.direction))) - dist;
			isOuter = true;
			
			//origin is inside of sphere
		} else {
			di1 = abs(abs(distanceBeetweenPoints(pc, ray.direction))) + dist;
			isOuter = false;
		}
		return new RayIntersection(ray.start.add(ray.direction.normalize().scalarMultiply(di1)), dist, isOuter) {
			
			@Override
			public Point3D getNormal() {
				return getPoint().normalize();
			}
			
			@Override
			public double getKrr() {
				return krr;
			}
			
			@Override
			public double getKrn() {
				return krn;
			}
			
			@Override
			public double getKrg() {
				return krg;
			}
			
			@Override
			public double getKrb() {
				return krb;
			}
			
			@Override
			public double getKdr() {
				return kdr;
			}
			
			@Override
			public double getKdg() {
				return kdg;
			}
			
			@Override
			public double getKdb() {
				return kdb;
			}
		};
	}
	
	private double distanceBeetweenPoints(Point3D pointA, Point3D pointB) {
		 return sqrt(Math.pow(pointA.x - pointB.x, 2) + Math.pow(pointA.y - pointB.y, 2) + Math.pow(pointA.z - pointB.z, 2));
	}

}
