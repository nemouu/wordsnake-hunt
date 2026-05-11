package org.snakehunt.main;

import java.util.List;

/**
 * An interface to create an interface between the program and the program's
 * user. It should be possible to use the program through a console or terminal
 * in an implemented class, and it should be possible to use the program in API
 * mode through this interface, for example, by incorporating this program as a
 * .jar file.
 * 
 * @author Philip Redecker
 *
 */
public interface SnakeHuntAPI {
	/**
	 * Reads the given input file with a complete problem instance and starts the
	 * solution procedure for the snake hunt. The found solution is saved along with
	 * the problem instance in the output file.
	 * 
	 * @param xmlInputFile  File path to an XML file with the problem instance to be
	 *                      solved.
	 * @param xmlOutputFile File path to an XML file for the problem instance and
	 *                      the generated solution.
	 * @return <code>true</code> if at least one snake was found, otherwise
	 *         <code>false</code>. In case of an error, <code>false</code> is also
	 *         returned.
	 */
	public boolean solveProblem(String xmlInputFile, String xmlOutputFile);

	/**
	 * Reads the given input file with a (possibly incomplete) problem instance and
	 * creates a new problem instance based on the given parameters. The generated
	 * problem instance is saved in the provided output file.
	 * 
	 * @param xmlInputFile  File path to an XML file with parameters for a problem
	 *                      instance to be created.
	 * @param xmlOutputFile File path to an XML file for the generated problem
	 *                      instance.
	 * @return <code>true</code> on success, otherwise <code>false</code>. In case
	 *         of an error, <code>false</code> is also returned.
	 */
	public boolean generateProblem(String xmlInputFile, String xmlOutputFile);

	/**
	 * Possible error types for a solution of a problem instance.
	 * <ul>
	 * <li><code>ELEMENTS</code>: A snake does not have the correct number of snake
	 * elements.</li>
	 * <li><code>ASSIGNMENT</code>: A snake element is assigned to a field with a
	 * wrong character.</li>
	 * <li><code>USAGE</code>: A snake element is assigned to an already maximally
	 * used field.</li>
	 * <li><code>NEIGHBORHOOD</code>: A snake element is not in the neighborhood of
	 * the previous snake element.</li>
	 * </ul>
	 */
	public enum ErrorType {
		/**
		 * A snake does not have the correct number of snake elements.
		 */
		ELEMENTS,
		/**
		 * A snake element is assigned to a field with a wrong character.
		 */
		ASSIGNMENT,
		/**
		 * A snake element is assigned to an already maximally used field.
		 */
		USAGE,
		/**
		 * A snake element is not in the neighborhood of the previous snake element.
		 */
		NEIGHBORHOOD
	}

	/**
	 * Reads the problem instance and solution from the given file and verifies the
	 * solution for validity. Both the type and frequency of violated conditions are
	 * determined.
	 * 
	 * @param xmlInputFile File path to an XML file with a problem instance and its
	 *                     associated solution.
	 * @return List of found individual errors. In case of an error, an empty list
	 *         is returned.
	 */
	public List<ErrorType> examineSolution(String xmlInputFile);

	/**
	 * Reads the problem instance and solution from the given file and calculates
	 * the achieved score. The calculation is done regardless of the solution's
	 * validity. Points are awarded for each found snake of a snake type and for
	 * each field used by a snake element (for each usage).
	 * 
	 * @param xmlInputFile File path to an XML file with a problem instance and
	 *                     solution.
	 * @return Total achieved score. In case of an error, <code>0</code> is
	 *         returned.
	 */
	public int evaluateSolution(String xmlInputFile);
}