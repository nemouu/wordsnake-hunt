package org.snakehunt.algorithm;

import java.util.*;

import org.snakehunt.model.*;

/**
 * An interface to create a link between the snake search component and various
 * useful functions that offer specific functionalities utilized by the snake
 * search component. An interface is used here to keep the possibility open to
 * replace or adapt parts of these functions.
 * 
 * @author Philip Redecker
 */
public interface ISnakeSearchUtil {

	/**
	 * A method that determines the allowed snake types for a given problem instance
	 * and a specific moment during the search. These are added to a list and this
	 * list is then sorted following a specific heuristic.
	 * 
	 * @return A (sorted) list of snake types.
	 */
	List<SnakeType> createValidSnakeTypes();

	/**
	 * A method that determines the allowed starting fields for a given snake type
	 * and a given jungle. These are added to a list and this list is then sorted
	 * following a specific heuristic.
	 * 
	 * @param type The snake type for which the starting fields are to be
	 *             determined.
	 * @return A (sorted) list of starting fields for a snake type.
	 */
	List<Field> createValidStartingFields(SnakeType type);

	/**
	 * A method that determines the allowed neighbor fields for a given (incomplete)
	 * snake and a given jungle, depending on the last snake element that was added.
	 * The allowed neighbor fields are added to a list and this list is then sorted
	 * following a specific heuristic.
	 * 
	 * @param previousElement The last snake element added to the current snake.
	 * @param currentSnake    The current snake.
	 * @return A (sorted) list of neighbor fields for the current snake and snake
	 *         element.
	 */
	List<Field> createValidNeighbors(SnakeElement previousElement, Snake currentSnake);

	/**
	 * Returns the model currently used in the SnakeSearchUtil. This is mainly for
	 * future changes and also for associated tests.
	 * 
	 * @return The value of the <code>model</code> variable.
	 */
	IModel getModel();

	/**
	 * Sets the model for the SnakeSearchUtil. For example, it's possible to pass a
	 * model even if the parameterless constructor was initially used. It is also
	 * generally possible to change the model after class instantiation.
	 * 
	 * @param model The model to be passed.
	 */
	void setModel(IModel model);

}