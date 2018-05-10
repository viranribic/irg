package hr.fer.zemris.irg.test.util;

import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.Vector;

/**
 * Class for changing available colours 
 * @author Viran
 *
 */
public class ColourManager {

	/**
	 * Available colours in this palate.
	 * @author Viran
	 *
	 */
	public enum EColours {
		Red, Green, Blue, Cyan, Yellow, Magenta
	}

	private EColours active = EColours.Red;

	/**
	 * ColourManager constructor.
	 */
	public ColourManager() {
	}
	
	/**
	 * ColourManager constructor.
	 * @param colour Starting colour. 
	 */
	public ColourManager(EColours colour) {
		this.active=colour;
	}
	
	/**
	 * Switch to next colour as listed in EColours enumeration.
	 */
	public void nextColour() {
		switch (active) {
		case Red: {
			active = EColours.Green;
			break;
		}
		case Green: {
			active = EColours.Blue;
			break;
		}
		case Blue: {
			active = EColours.Cyan;
			break;
		}
		case Cyan: {
			active = EColours.Yellow;
			break;
		}
		case Yellow: {
			active = EColours.Magenta;
			break;
		}
		case Magenta: {
			active = EColours.Red;
			break;
		}
		}
	}

	/**
	 * Switch to previous colour as listed in EColours enumeration.
	 */
	public void prevColour() {
		switch (active) {
		case Red: {
			active = EColours.Magenta;
			break;
		}
		case Green: {
			active = EColours.Red;
			break;
		}
		case Blue: {
			active = EColours.Green;
			break;
		}
		case Cyan: {
			active = EColours.Blue;
			break;
		}
		case Yellow: {
			active = EColours.Cyan;
			break;
		}
		case Magenta: {
			active = EColours.Yellow;
			break;
		}
		}
	}

	/**
	 * Get the current active colour as RGB value.
	 * @return RGB vector.
	 */
	public IVector getColourRGB() {
		switch (active) {
		case Red: {
			return new Vector(1, 0, 0);
		}
		case Green: {
			return new Vector(0, 1, 0);
		}
		case Blue: {
			return new Vector(0, 0, 1);
		}
		case Cyan: {
			return new Vector(0, 1, 1);
		}
		case Yellow: {
			return new Vector(1, 1, 0);
		}
		case Magenta: {
			return new Vector(1, 0, 1);
		}
		default:
			return new Vector(1, 1, 1);
		}
	}
}
