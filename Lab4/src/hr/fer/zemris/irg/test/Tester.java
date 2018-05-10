package hr.fer.zemris.irg.test;

import java.util.LinkedList;

import hr.fer.zemris.irg.Polygon2D;
import hr.fer.zemris.irg.PolygonUtil;
import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.Vector;

public class Tester {

	public static void main(String[] args) {
//		System.out.println("TEST 1.............");
//		test1();
//		System.out.println();
//		System.out.println("TEST 2.............");		
//		test2();
//		System.out.println();
		System.out.println("TEST 3.............");
		test3();
		System.out.println();
		System.out.println("TEST 4.............");		
		test4();
		
		
	}

	

	private static void test4() {
		LinkedList<IVector> points = new LinkedList<>();

		points.add(new Vector(0,0));
		points.add(new Vector(0,0.5));
		points.add(new Vector(0.5,1));
		points.add(new Vector(1,1));
		points.add(new Vector(1,0.5));
		
		Polygon2D poly = new Polygon2D(points);
		System.out.println("Convex? "+poly.checkConvex());
		System.out.println("Clockwise: " + PolygonUtil.isClockwise(poly.asList()) + " CClockwise: "
				+ PolygonUtil.isCounterClockwise(poly.asList()));
		System.out.println(poly.asList());
		System.out.println("Point (0.5,0,5) in poly? "+poly.comparePointAndPolygon(new Vector(0.5,0.5)));
		System.out.println("Point (0.25,0) on edge?"+poly.comparePointAndPolygon(new Vector(0.25,0)));
		System.out.println("Point (1,0) out of poly?"+poly.comparePointAndPolygon(new Vector(1,0)));
		
		System.out.println("Points before adding:");
		System.out.println("Point (0.5,0.01) in poly? "+poly.comparePointAndPolygon(new Vector(0.5,0.01)));
		System.out.println("Point (0.01,0.5) in poly? "+poly.comparePointAndPolygon(new Vector(0.01,0.5)));
		
		System.out.println("Point (0.45,0) on edge?"+poly.comparePointAndPolygon(new Vector(0.45,0)));
		System.out.println("Point (0,0.45) out of poly?"+poly.comparePointAndPolygon(new Vector(0,0.45)));
		
		System.out.println();
		System.out.println("Adding point: (0,0.5)");
		poly.add(new Vector(0,0.5));
		
		System.out.println("Convex? "+poly.checkConvex());
		System.out.println("Clockwise: " + PolygonUtil.isClockwise(poly.asList()) + " CClockwise: "
				+ PolygonUtil.isCounterClockwise(poly.asList()));
		System.out.println(poly.asList());
		
		System.out.println("Points after adding:");
		System.out.println("Point (0.5,0.01) in poly? "+poly.comparePointAndPolygon(new Vector(0.5,0.01)));
		System.out.println("Point (0.01,0.5) in poly? "+poly.comparePointAndPolygon(new Vector(0.01,0.5)));
		
		System.out.println("Point (0.45,0) on edge?"+poly.comparePointAndPolygon(new Vector(0.45,0)));
		System.out.println("Point (0,0.45) out of poly?"+poly.comparePointAndPolygon(new Vector(0,0.45)));
		
		
	}



	private static void test3() {
		LinkedList<IVector> points = new LinkedList<>();
		points.add(new Vector(0,0));
		points.add(new Vector(0.5,0));
		points.add(new Vector(1,0.5));
		points.add(new Vector(1,1));
		points.add(new Vector(0.5,1));

		Polygon2D poly = new Polygon2D(points);
		System.out.println("Convex? "+poly.checkConvex());
		System.out.println("Clockwise: " + PolygonUtil.isClockwise(poly.asList()) + " CClockwise: "
				+ PolygonUtil.isCounterClockwise(poly.asList()));
		System.out.println(poly.asList());
		System.out.println("Point (0.5,0.5) in poly? "+poly.comparePointAndPolygon(new Vector(0.5,0.5)));
		System.out.println("Point (0.25,0) on edge?"+poly.comparePointAndPolygon(new Vector(0.25,0)));
		System.out.println("Point (1,0) out of poly?"+poly.comparePointAndPolygon(new Vector(1,0)));
		
		System.out.println("Points before adding:");
		System.out.println("Point (0.5,0.01) in poly? "+poly.comparePointAndPolygon(new Vector(0.5,0.01)));
		System.out.println("Point (0.01,0.5) in poly? "+poly.comparePointAndPolygon(new Vector(0.01,0.5)));
		
		System.out.println("Point (0.45,0) on edge?"+poly.comparePointAndPolygon(new Vector(0.45,0)));
		System.out.println("Point (0,0.45) out of poly?"+poly.comparePointAndPolygon(new Vector(0,0.45)));
		
		System.out.println();
		System.out.println("Adding point: (0.5,0)");
		poly.add(new Vector(0.5,0));
		
		System.out.println("Convex? "+poly.checkConvex());
		System.out.println("Clockwise: " + PolygonUtil.isClockwise(poly.asList()) + " CClockwise: "
				+ PolygonUtil.isCounterClockwise(poly.asList()));
		System.out.println(poly.asList());
		
		System.out.println("Points after adding:");
		System.out.println("Point (0.5,0.01) in poly? "+poly.comparePointAndPolygon(new Vector(0.5,0.01)));
		System.out.println("Point (0.01,0.5) in poly? "+poly.comparePointAndPolygon(new Vector(0.01,0.5)));
		
		System.out.println("Point (0.45,0) on edge?"+poly.comparePointAndPolygon(new Vector(0.45,0)));
		System.out.println("Point (0,0.45) out of poly?"+poly.comparePointAndPolygon(new Vector(0,0.45)));
		
	}



	@SuppressWarnings("unused")
	private static void test2() {
		LinkedList<IVector> points = new LinkedList<>();
		//set 1
//		points.add(new Vector(0, 0));
//		points.add(new Vector(1, 0));
//		points.add(new Vector(1, 1));
//		points.add(new Vector(0, 1));

		points.add(new Vector(0,0));
		points.add(new Vector(0,0.5));
		points.add(new Vector(0.5,1));
		points.add(new Vector(1,1));
		points.add(new Vector(1,0.5));
		
		Polygon2D poly = new Polygon2D(points);
		System.out.println("Convex? "+poly.checkConvex());
		System.out.println("Clockwise: " + PolygonUtil.isClockwise(poly.asList()) + " CClockwise: "
				+ PolygonUtil.isCounterClockwise(poly.asList()));
		System.out.println(poly.asList());
		System.out.println("Point (0.5,0,5) in poly? "+poly.comparePointAndPolygon(new Vector(0.5,0.5)));
		System.out.println("Point (0.25,0) on edge?"+poly.comparePointAndPolygon(new Vector(0.25,0)));
		System.out.println("Point (1,0) out of poly?"+poly.comparePointAndPolygon(new Vector(1,0)));
		
		System.out.println("Points before adding:");
		System.out.println("Point (0.5,0.01) in poly? "+poly.comparePointAndPolygon(new Vector(0.5,0.01)));
		System.out.println("Point (0.01,0.5) in poly? "+poly.comparePointAndPolygon(new Vector(0.01,0.5)));
		
		System.out.println("Point (0.45,0) on edge?"+poly.comparePointAndPolygon(new Vector(0.45,0)));
		System.out.println("Point (0,0.45) out of poly?"+poly.comparePointAndPolygon(new Vector(0,0.45)));
		
		System.out.println();
		System.out.println("Adding point:");
		poly.add(new Vector(0.5,0));
		System.out.println("Convex? "+poly.checkConvex());
		System.out.println("Clockwise: " + PolygonUtil.isClockwise(poly.asList()) + " CClockwise: "
				+ PolygonUtil.isCounterClockwise(poly.asList()));
		System.out.println(poly.asList());
		
		System.out.println("Points after adding:");
		System.out.println("Point (0.5,0.01) in poly? "+poly.comparePointAndPolygon(new Vector(0.5,0.01)));
		System.out.println("Point (0.01,0.5) in poly? "+poly.comparePointAndPolygon(new Vector(0.01,0.5)));
		
		System.out.println("Point (0.45,0) on edge?"+poly.comparePointAndPolygon(new Vector(0.45,0)));
		System.out.println("Point (0,0.45) out of poly?"+poly.comparePointAndPolygon(new Vector(0,0.45)));
		
		
	}

	@SuppressWarnings("unused")
	private static void test1() {
		LinkedList<IVector> points = new LinkedList<>();
		//set 1
//		points.add(new Vector(0, 0));
//		points.add(new Vector(0, 1));
//		points.add(new Vector(1, 1));
//		points.add(new Vector(1, 0));

		points.add(new Vector(0,0));
		points.add(new Vector(0.5,0));
		points.add(new Vector(1,0.5));
		points.add(new Vector(1,1));
		points.add(new Vector(0.5,1));

		Polygon2D poly = new Polygon2D(points);
		System.out.println("Convex? "+poly.checkConvex());
		System.out.println("Clockwise: " + PolygonUtil.isClockwise(poly.asList()) + " CClockwise: "
				+ PolygonUtil.isCounterClockwise(poly.asList()));
		System.out.println(poly.asList());
		System.out.println("Point (0.5,0.5) in poly? "+poly.comparePointAndPolygon(new Vector(0.5,0.5)));
		System.out.println("Point (0.25,0) on edge?"+poly.comparePointAndPolygon(new Vector(0.25,0)));
		System.out.println("Point (1,0) out of poly?"+poly.comparePointAndPolygon(new Vector(1,0)));
		
		System.out.println("Points before adding:");
		System.out.println("Point (0.5,0.01) in poly? "+poly.comparePointAndPolygon(new Vector(0.5,0.01)));
		System.out.println("Point (0.01,0.5) in poly? "+poly.comparePointAndPolygon(new Vector(0.01,0.5)));
		
		System.out.println("Point (0.45,0) on edge?"+poly.comparePointAndPolygon(new Vector(0.45,0)));
		System.out.println("Point (0,0.45) out of poly?"+poly.comparePointAndPolygon(new Vector(0,0.45)));
		
		System.out.println();
		System.out.println("Adding point:");
		poly.add(new Vector(0,0.5));
		System.out.println("Convex? "+poly.checkConvex());
		System.out.println("Clockwise: " + PolygonUtil.isClockwise(poly.asList()) + " CClockwise: "
				+ PolygonUtil.isCounterClockwise(poly.asList()));
		System.out.println(poly.asList());
		
		System.out.println("Points after adding:");
		System.out.println("Point (0.5,0.01) in poly? "+poly.comparePointAndPolygon(new Vector(0.5,0.01)));
		System.out.println("Point (0.01,0.5) in poly? "+poly.comparePointAndPolygon(new Vector(0.01,0.5)));
		
		System.out.println("Point (0.45,0) on edge?"+poly.comparePointAndPolygon(new Vector(0.45,0)));
		System.out.println("Point (0,0.45) out of poly?"+poly.comparePointAndPolygon(new Vector(0,0.45)));
		
	}
	

}
