package hr.fer.zemris.java.raytracer;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * A class representing a simplified raytracer, i.e. ray caster used for the
 * purpose of rendering 3-dimensional scenes.
 * 
 * @author Damjan Vučina
 */
public class RayCaster {

	/**
	 * The main method. Invoked when the program is run.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0), new Point3D(0, 0, 0),
				new Point3D(0, 0, 10), 20, 20);
	}

	/**
	 * Method that is used for the purpose of acquiring an object charged with the
	 * task of tracing the ray in the defined scene.
	 *
	 * @return the new object charged with the task of tracing the ray in the
	 *         defined scene.
	 */
	private static IRayTracerProducer getIRayTracerProducer() {

		return new IRayTracerProducer() {
			@SuppressWarnings("unused")
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical,
					int width, int height, long requestNo, IRayTracerResultObserver observer) {
				System.out.println("Započinjem izračune...");
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];

				Point3D vectorOG = eye.difference(eye, view).normalize();
				Point3D vectorVUV = viewUp.normalize();
				Point3D vectorJ = vectorVUV.sub(vectorOG.scalarMultiply(vectorOG.scalarProduct(vectorVUV)));
				Point3D vectorJnormed = vectorJ.normalize();
				Point3D vectorI = vectorOG.vectorProduct(vectorJnormed);
				Point3D vectorInormed = vectorI.normalize();

				// l=r
				double l = horizontal / 2;
				// t=b
				double t = vertical / 2;

				Point3D zAxis = new Point3D(0, 0, 0);
				Point3D yAxis = vectorJnormed;
				Point3D xAxis = vectorInormed;
				Point3D screenCorner = view.sub(new Point3D(horizontal / 2, -vertical / 2, 0));

				Scene scene = RayTracerViewer.createPredefinedScene();
				short[] rgb = new short[3];
				int offset = 0;
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						Point3D screenPoint = screenCorner
								.add(new Point3D(x * 1.0 / (width - 1), y * 1.0 / (height - 1), 0));
						Ray ray = Ray.fromPoints(eye, screenPoint);
						tracer(scene, ray, rgb);
						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
						offset++;
					}
				}
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}

			protected void tracer(Scene scene, Ray ray, short[] rgb) {
				rgb[0] = 0;
				rgb[1] = 0;
				rgb[2] = 0;
				RayIntersection closest = findClosestIntersection(scene, ray);
				if (closest == null) {
					return;
				}
				rgb[0] = 255;
				rgb[1] = 255;
				rgb[2] = 255;
			}

			private RayIntersection findClosestIntersection(Scene scene, Ray ray) {
				if (scene == null) {
					return null;
				}

				RayIntersection currentIntersection = null;
				for (GraphicalObject graphicalObject : scene.getObjects()) {

					currentIntersection = graphicalObject.findClosestRayIntersection(ray);
					if (currentIntersection != null) {
						return currentIntersection;
					}
				}
				return currentIntersection;
			}
		};
	}
}
