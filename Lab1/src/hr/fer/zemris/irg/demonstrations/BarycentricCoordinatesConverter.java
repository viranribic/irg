package hr.fer.zemris.irg.demonstrations;

import java.util.Scanner;
import hr.fer.zemris.linearna.*;
/**
 *  First lab assignment, barycentric coordinates converter.
 * @author Viran
 *
 */
public class BarycentricCoordinatesConverter {

	public static IVector[] trianglePoints = new IVector[3];
	public static int pos = 0;
	public static IVector point;

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		getVectors(scanner);
		getPoint(scanner);
		scanner.close();

		IVector barycentric = generateBarycentric();
		System.out.println("Generirane baricentricke koordinate su:");
		System.out.println(barycentric);
	}

	/**
	 * Generate barycentric coordinates.
	 * @return Vector containing the coordinates.
	 */
	private static IVector generateBarycentric() {
		IMatrix coeficients=new Matrix(trianglePoints[0],trianglePoints[1],trianglePoints[2]);
		coeficients=coeficients.nTranspose(true);
		try {
			coeficients=coeficients.nInvert();
			IMatrix result= coeficients.nMultiply(point.toColumnMatrix(true));
			return result.toVector(true);
		} catch (IncompatibleOperandException e) {
			System.out.println("The given matrix can't be inverted.");
		}
		return null;
	}

	/**
	 * One point reader. 
	 * @param scanner Input scanner;
	 */
	private static void getPoint(Scanner scanner) {
		while (true) {
			System.out.println("Unesite tocku :");
			String line = scanner.nextLine();
			String[] values = line.trim().split(" ");
			if (values.length != 3) {
				System.out.println("Traze se 3 koordinate za tocku.");
				continue;
			}
			int i = 0;
			double[] elements = new double[3];
			for (String val : values) {
				try {
					elements[i] = Double.parseDouble(val);
				} catch (NumberFormatException e) {
					System.out.println("Zadana vrijednost ne moze se prihvatiti kao decimalni broj.");
					break;
				}
				i++;
			}
			if (i < 3) {
				continue;
			}

			point = new Vector(elements);
			break;
		}
	}

	/**
	 * Get vectors from user.
	 * @param scanner Input scanner;
	 */
	private static void getVectors(Scanner scanner) {
		int count = 0;
		while (count != 3) {
			System.out.println("Unesite vektor " + (pos + 1) + " :");
			String line = scanner.nextLine();
			String[] values = line.trim().split(" ");
			if (values.length != 3) {
				System.out.println("Traze se 3 koordinate po vektoru.");
				continue;
			}
			int i = 0;
			double[] elements = new double[3];
			for (String val : values) {
				try {
					elements[i] = Double.parseDouble(val);
				} catch (NumberFormatException e) {
					System.out.println("Zadana vrijednost ne moze se prihvatiti kao decimalni broj.");
					break;
				}
				i++;
			}
			if (i < 3) {
				continue;
			}

			trianglePoints[pos++] = new Vector(elements);
			count++;
		}
	}
}
