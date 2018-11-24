package searchEngine;

/**
 * <code>FullGraphException</code> is thrown when the program attempts to add a
 * WebPage to a full WebGraph.
 *
 * @author Sayan Sivakumaran e-mail: sayan.sivakumaran@stonybrook.edu Stony
 *         Brook ID: 110261379
 **/
public class FullGraphException extends Exception {
	private static final long serialVersionUID = -8568120812429473972L;

	/**
	 * Returns an instance of <code>FullGraphException</code>.
	 */
	public FullGraphException() {
		super();
	}

	/**
	 * Returns an instance of <code>FullGraphException</code> along with the
	 * specified message.
	 * 
	 * @param message The message that accompanies the error.
	 */
	public FullGraphException(String message) {
		super(message);
	}
}
