package hr.fer.zemris.irg;

import java.util.LinkedList;

public class CircularLinkeList<E> extends LinkedList<E> {

	/**
	 * Auto generated setrialVersionUID.
	 */
	private static final long serialVersionUID = 6305168226350632752L;

	@Override
	public E get(int index) {
		index%=this.size();
		return super.get(index);
	}
	
}
