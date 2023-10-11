package de.fuhagen.course01584.ss23.model;

import java.util.*;

/**
 * A class that allows the storage of a solution in the model. A solution found
 * by snake search or a solution that was read can be stored here. Some methods
 * are provided to support data processing and algorithm classes.
 * 
 * @author Philip Redecker
 *
 */
public class Solution {
	private List<Snake> snakes;

	/**
	 * A parameterized constructor in which a list of snakes is directly passed,
	 * according to the data structure defined in this package, Snake. In this case,
	 * an instance of a solution class has a list of (solution) snakes.
	 * 
	 * @param snakes A list of snakes according to the data structure defined in
	 *               this package, Snake.
	 */
	public Solution(List<Snake> snakes) {
		super();
		this.snakes = snakes;
	}

	/**
	 * A parameterless constructor, so that it is possible to use this class for
	 * testing, for example, in future program changes. Here, an empty list of
	 * snakes is created directly so that snakes can be added to the solution at any
	 * time, if desired.
	 */
	public Solution() {
		super();
		this.snakes = new ArrayList<Snake>();
	}

	/**
	 * A method that counts and returns the various types of snakes that are
	 * actually present in a solution. This number does not necessarily correspond
	 * to the number of snake types contained in the model according to the
	 * specifications. This is used particularly in the data processing classes and
	 * when testing this class.
	 * 
	 * @return The number of different snake types in the solution.
	 */
	public int getNumberOfDifferentSnakeTypes() {
		List<SnakeType> differentSnakeTypes = new ArrayList<SnakeType>();
		for (Snake snake : snakes) {
			if (!differentSnakeTypes.contains(snake.getType())) {
				differentSnakeTypes.add(snake.getType());
			}
		}
		return differentSnakeTypes.size();
	}

	/**
	 * This method allows finding and returning a snake in the solution based on the
	 * ID of its snake type.
	 * 
	 * @param id A string containing the ID of the snake type to search for.
	 * @return <code>null</code> if no snake was found, otherwise the snake is
	 *         returned.
	 */
	public Snake getSnakeWithSnakeTypeID(String id) {
		for (Snake snake : snakes) {
			if (snake.getType().getId().equals(id)) {
				return snake;
			}
		}
		return null;
	}

	/**
	 * A method that can add a snake to the solution.
	 * 
	 * @param inSnake A snake according to the data structure defined in this
	 *                package, Snake.
	 */
	public void addSnake(Snake inSnake) {
		this.snakes.add(inSnake);
	}

	/**
	 * A method that removes the last snake in the solution.
	 */
	public void removeLastSnake() {
		if (this.snakes != null) {
			if (this.snakes.size() > 0) {
				this.snakes.remove(snakes.size() - 1);
			}
		}
	}

	/**
	 * Returns the snakes in the solution.
	 * 
	 * @return A list of snakes according to the data structure defined in this
	 *         package, Snake.
	 */
	public List<Snake> getSnakes() {
		return snakes;
	}

	/**
	 * Through this method, setting the snakes of the solution is possible even
	 * after instantiation. This method is primarily used for reading data, but also
	 * for testing and subsequent program extension.
	 * 
	 * @param snakes A list of snakes to be passed to the solution.
	 */
	public void setSnakes(List<Snake> snakes) {
		this.snakes = snakes;
	}
}