package hr.fer.zemris.irg;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import hr.fer.zemris.irg.EDotPolyRelation;
import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.Vector;

public class Task5_2 {

	private static ObjectModel model = new ObjectModel();

	public static void main(String[] args) {
		// check arguments
		if (args.length != 1) {
			System.out.println(
					"This program accepts only one argument. That is the parth to the object definition file, of type .obj.");
			return;
		}
		// check file
		if (!args[0].endsWith(".obj")) {
			System.out.println(
					"This program accepts only one argument. That is the parth to the object definition file, of type .obj.");
			return;
		}

		readInput(args);
		//model.printSurfaceEqu();
		interactiveLoop();

	}

	private static void interactiveLoop() {
		Scanner input = new Scanner(System.in);
		System.out.println("Program started. Type \"help\" for help.");
		while (true) {
			System.out.print(">");
			String line = input.nextLine();
			if (line.startsWith("quit"))
				break;
			else if (line.startsWith("help"))
				displayHelpMessage();
			else if (line.startsWith("cmp")) {
				line = line.replace("cmp", " ").trim();
				processPoint(line);
			} else if (line.startsWith("normalize")) {
				System.out.println(processNormalization());
			} else
				System.out.println("There was an error. Plese try again or type \"h\" for more information.");
		}
		input.close();
	}

	private static String processNormalization() {
		model.normalize();
		return model.dumpToOBJ();
	}

	private static void processPoint(String numberS) {
		String[] numberSL = numberS.split(" ");
		IVector point = new Vector(0, 0, 0, 1); // prepare a homogeneous
												// coordinate

		for (int i = 0; i < numberSL.length; i++)
			point.set(i, Double.parseDouble(numberSL[i]));

		EDotPolyRelation outcome = model.compareToPoint(point);
		switch (outcome) {
		case INSIDE:
			System.out.println("Vector " + numberS + " is inside the model.");
			break;
		case ON_EDGE:
			System.out.println("Vector " + numberS + " is on one edge the model.");
			break;
		case OUTSIDE:
			System.out.println("Vector " + numberS + " is outside the model.");
			break;
		default:
			System.out.println("There was an error comparing the point.");
		}

	}

	private static void displayHelpMessage() {
		System.out.println("Help\n\t" + "Type \"quit\" for exiting.\n\t"
				+ "Type \"cmp\"a followed by a set of three double values (i.e. cmp 1.1 2.22 3.333) which represent a point in space.\n\t"
				+ "That point will be compared with the imported model after wich the program will decide if the given point\n\t"
				+ "is inside, outside or on the edge of the imported model.\n\t"
				+ "Type\"normalize\" in order to normalize the working model.");
	}

	private static void readInput(String[] args) {
		try (BufferedReader input = new BufferedReader(
				new InputStreamReader(new FileInputStream(new File(args[0])), "UTF8"));) {
			String line;
			while (true) {
				line = input.readLine();
				if (line == null)
					break;
				processInputLine(line);
			}
		} catch (IOException e) {
			System.out.println("File could not been opened! Exiting");
			return;
		}
	}

	private static void processInputLine(String line) {
		if (line.startsWith("v")) {
			String[] elementsS = line.replace("v", "").trim().split(" ");
			double[] elementsD = new double[elementsS.length];
			int i = 0;
			for (String s : elementsS)
				elementsD[i++] = Double.parseDouble(s);
			model.addVertex(elementsD);
		} else if (line.startsWith("f")) {
			String[] elementsS = line.replace("f", "").trim().split(" ");
			int[] elementsI = new int[elementsS.length];
			int i = 0;
			for (String s : elementsS)
				elementsI[i++] = Integer.parseInt(s);
			try {
				model.addFace(elementsI);
			} catch (IllegalArgumentException ignorable) {
				return;
			}
		}
	}
}
