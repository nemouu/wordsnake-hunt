package de.fuhagen.course01584.ss23.algorithm;

import de.fuhagen.course01584.ss23.model.*;

/**
 * An interface to establish a connection between the snake search component,
 * the main component and the data model. This allows future extension,
 * customization or replacement of the snake search with a different
 * implementation. The interface is used to keep the concrete implementation of
 * the SnakeSearch class replaceable.
 * 
 * @author Philip Redecker
 */
public interface ISnakeSearch {

	/**
	 * A method for searching snakes in a given jungle. It should search for snakes
	 * until the jungle has been completely searched or until a specified time limit
	 * has been reached. The solution is stored in a private variable
	 * <code>solution</code>.
	 */
	void searchSnakes();

	/**
	 * Returns the value of the <code>functions</code> variable. This is another
	 * interface, so getter and setter methods are provided in this class. This is
	 * intended for future program extension and/or testing.
	 * 
	 * @return The value of the <code>functions</code> variable.
	 */
	ISnakeSearchUtil getFunctions();

	/**
	 * Sets the value of the <code>functions</code> variable. This is another
	 * interface, so getter and setter methods are provided in this class. This is
	 * intended for future program extension and/or testing.
	 * 
	 * @param functions An instance of a class that implements the
	 *                  <code>ISnakeSearchUtil</code> interface.
	 */
	void setFunctions(ISnakeSearchUtil functions);

	/**
	 * Returns the model currently used in the snake search. This is mainly for
	 * future changes and also for associated tests.
	 * 
	 * @return The value of the <code>model</code> variable.
	 */
	IModel getModel();

	/**
	 * Sets the model for the snake search. For example, it's possible to pass a
	 * model even if the parameterless constructor was initially used. It is also
	 * generally possible to change the model after class instantiation.
	 * 
	 * @param model The model to be passed.
	 * @throws IllegalArgumentException An exception is thrown if an inappropriate
	 *                                  model is passed.
	 */
	void setModel(IModel model) throws IllegalArgumentException;

	/**
	 * Returns the solution found by the snake search. Note that calling this method
	 * only makes sense after the snake search has actually searched for a solution.
	 * If this method is called earlier, an incomplete solution may be returned.
	 * 
	 * @return The value of the <code>solution</code> variable.
	 */
	Solution getSolution();

	/**
	 * Returns the time when the snake search was started. This is mainly necessary
	 * for using this class in the main component of this program.
	 * 
	 * @return The value of the <code>currTime</code> variable.
	 */
	Long getCurrTime();

}