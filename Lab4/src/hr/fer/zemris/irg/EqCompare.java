package hr.fer.zemris.irg;

/**
 * Interface for a testing if objects given as arguments result in a true operation.
 * @author Viran
 *
 * @param <T>
 */
public interface EqCompare<T> {
	/**
	 * Test is parameters satisfy this relation.
	 * @param o1 First object.
	 * @param o2 Second object.
	 * @return True if relation is satisfied, false otherwise.
	 */
	public boolean isTrue(T o1, T o2);
}
