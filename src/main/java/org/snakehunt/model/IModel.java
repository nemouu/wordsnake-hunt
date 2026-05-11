package org.snakehunt.model;

import java.util.List;

/**
 * An interface for creating a link between the data model and other components
 * of the program. This allows future expansion of the program with a different
 * model or adapting the existing model if desired. It generally provides many
 * getters and setters, as well as some helper functions to support snake
 * hunting.
 * 
 * @author Philip Redecker
 *
 */
public interface IModel {

	/**
	 * Returns the time of the model. It returns either a number for the time
	 * constraint or two numbers for the time constraint and the time taken.
	 * 
	 * @return An array containing either one or two entries for the time constraint
	 *         and/or time taken.
	 */
	Double[] getTime();

	/**
	 * This method allows setting the time of the model even after instantiation.
	 * This is primarily for reading data but also for testing or future program
	 * expansion.
	 * 
	 * @param time An array of Doubles containing either one or two entries. One
	 *             entry for the time constraint and one for the time taken.
	 * @throws IllegalArgumentException If an invalid argument for time is passed,
	 *                                  an exception is thrown.
	 */
	void setTime(Double[] time) throws IllegalArgumentException;

	/**
	 * Returns the time unit of the model's time indications.
	 * 
	 * @return A String containing the time unit.
	 */
	String getUnitOfTime();

	/**
	 * This method allows setting the time unit of the model even after
	 * instantiation. It is important to pass a valid time unit. Valid units are
	 * <code>ms</code> (milliseconds), <code>s</code> (seconds), <code>min</code>
	 * (minutes), <code>h</code> (hours), and <code>d</code> (days). This method is
	 * primarily for reading data but also for testing or future program expansion.
	 * 
	 * @param unitOfTime A String containing the time unit to be passed to the
	 *                   model.
	 * @throws IllegalArgumentException If an invalid argument for time unit is
	 *                                  passed, an exception is thrown.
	 */
	void setUnitOfTime(String unitOfTime) throws IllegalArgumentException;

	/**
	 * Returns the jungle of the model with rows, columns, character set, and
	 * fields.
	 * 
	 * @return The jungle of the model.
	 */
	Jungle getJungle();

	/**
	 * This method allows setting the jungle of the model even after instantiation.
	 * This is primarily for reading data but also for testing or future program
	 * expansion.
	 * 
	 * @param jungle A jungle according to the data structure Jungle defined in this
	 *               package.
	 */
	void setJungle(Jungle jungle);

	/**
	 * Returns the snake types of the model.
	 * 
	 * @return A list of snake types according to the data structure SnakeType
	 *         defined in this package.
	 */
	List<SnakeType> getSnakeTypes();

	/**
	 * This method allows setting the snake types of the model even after
	 * instantiation. This is primarily for reading data but also for testing or
	 * future program expansion.
	 * 
	 * @param snakeTypes A list of snake types to be passed to the jungle.
	 */
	void setSnakeTypes(List<SnakeType> snakeTypes);

	/**
	 * A method that allows adding a single snake type, according to the data
	 * structure SnakeType, to the model.
	 * 
	 * @param inType The snake type to be added.
	 */
	void addSnakeType(SnakeType inType);

	/**
	 * Returns the solution of the model with the associated snake types.
	 * 
	 * @return The solution of the model, according to the data structure Solution
	 *         defined in this package.
	 */
	Solution getSolution();

	/**
	 * This method allows setting the solution of the model even after
	 * instantiation. This is primarily for reading data but also for testing or
	 * future program expansion.
	 * 
	 * @param solution A solution according to the data structure Solution defined
	 *                 in this package.
	 */
	void setSolution(Solution solution);

	/**
	 * This helper method converts the time in the model to nanoseconds, as time
	 * measurement in this program is done in nanoseconds. The time unit is taken
	 * into account.
	 * 
	 * @return A number corresponding to the time constraint in nanoseconds.
	 * @throws IllegalArgumentException If incorrect time unit information was
	 *                                  provided in the model or incorrect
	 *                                  parameters were passed, an exception is
	 *                                  thrown.
	 */
	Double calculateTimeToNanoseconds() throws IllegalArgumentException;

	/**
	 * This helper method converts the measured time in snake hunting to the
	 * original time unit of the model. Finally, the used time can be written to the
	 * model.
	 * 
	 * @param usedTimeInNanoseconds The measured time from snake hunting in
	 *                              nanoseconds.
	 * @return A number corresponding to the measured time in the model's time unit.
	 * @throws IllegalArgumentException If incorrect time unit information was
	 *                                  provided in the model or incorrect
	 *                                  parameters were passed, an exception is
	 *                                  thrown.
	 */
	Double calculateTimeInUnitGivenByModel(Long usedTimeInNanoseconds) throws IllegalArgumentException;
}