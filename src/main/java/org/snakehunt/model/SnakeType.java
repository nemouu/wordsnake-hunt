package org.snakehunt.model;

/**
 * A class that models snake types. Each snake in the model has a snake type,
 * and certain snake types are predefined in the model itself. Snakes of these
 * snake types can then be searched for in the jungle, if it exists. In addition
 * to getters and setters, some helper methods are provided that can be used by
 * other classes.
 * 
 * @author Philip Redecker
 *
 */
public class SnakeType {
	private String id;
	private INeighborhood structure;
	private String signs;
	private int points;
	private int amount;

	/**
	 * A parameterized constructor that allows passing all possible information to
	 * an instance of this class directly upon instantiation. The attributes ID,
	 * string, and points are passed, as well as the neighborhood structure that
	 * snakes of this snake type must follow. Regarding the attribute 'amount,' it
	 * is important to note that this mainly serves the jungle generator, which
	 * always needs to distribute exactly this many snakes in the jungle. For the
	 * snake search, the actual number of found snakes does not have to match this
	 * amount here.
	 * 
	 * @param id        A string containing the ID that this snake type should have.
	 * @param structure The neighborhood structure that this snake type should
	 *                  follow.
	 * @param signs     The signs that snakes of this snake type must have.
	 * @param points    The points that finding snakes of this snake type achieves.
	 * @param amount    The number of snakes of this type to be distributed in the
	 *                  jungle generator in the jungle.
	 * @throws IllegalArgumentException If an invalid argument is used for the
	 *                                  parameters 'amount,' 'points,' and/or
	 *                                  'signs,' an exception is thrown.
	 */
	public SnakeType(String id, INeighborhood structure, String signs, int points, int amount)
			throws IllegalArgumentException {
		super();
		if (amount < 1) {
			throw new IllegalArgumentException(
					"For the class 'SnakeType,' the 'amount' attribute cannot be less than 1.");
		}
		if (amount > Integer.MAX_VALUE - 1) {
			throw new IllegalArgumentException(
					"For the class 'SnakeType,' the 'amount' attribute cannot be too large.");
		}
		if (points < 0 || points > Integer.MAX_VALUE - 1) {
			throw new IllegalArgumentException(
					"For the class 'SnakeType,' the 'points' attribute cannot be too small or too large.");
		}
		if (signs.length() < 1) {
			throw new IllegalArgumentException(
					"The snake type needs at least one character " + "so that it can be searched for at all.");
		}
		this.id = id;
		this.structure = structure;
		this.signs = signs;
		this.points = points;
		this.amount = amount;
	}

	/**
	 * A parameterless constructor, so that it is possible to use this class for
	 * testing, for example, in future program changes.
	 */
	public SnakeType() {
		super();
	}

	@Override
	public String toString() {
		if (id == null || structure == null || signs == null) {
			return "";
		}
		return "ID=" + id + ", Neighborhood Structure=" + structure.getType() + ", String=" + signs + ", Points="
				+ points + ", Amount=" + amount;
	}

	/**
	 * This method returns a string that contains the ID of the snake type.
	 * 
	 * @return A string that contains the ID of the snake type.
	 */
	public String getId() {
		return id;
	}

	/**
	 * This method allows setting the ID of a snake type even after instantiation.
	 * This method is mainly used for reading data, but also for testing and
	 * subsequent program extension.
	 * 
	 * @param id The ID to be passed to this snake type.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * This method returns the neighborhood structure that the snake type is subject
	 * to.
	 * 
	 * @return The neighborhood structure of the snake type.
	 */
	public INeighborhood getStructure() {
		return structure;
	}

	/**
	 * Through this method, setting the neighborhood structure of a snake type is
	 * possible even after instantiation. This method is primarily used for reading
	 * data, but also for testing and future program extension.
	 * 
	 * @param structure The neighborhood structure to be passed to this snake type.
	 */
	public void setStructure(INeighborhood structure) {
		this.structure = structure;
	}

	/**
	 * This method returns a string containing the string of the snake type.
	 * 
	 * @return A string containing the string of the snake type.
	 */
	public String getSigns() {
		return signs;
	}

	/**
	 * Through this method, setting the string of a snake type is possible even
	 * after instantiation. It is important to ensure that a valid string is passed.
	 * This method is primarily used for reading data, but also for testing and
	 * future program extension.
	 * 
	 * @param signs The string to be passed to this snake type.
	 * @throws IllegalArgumentException If an invalid argument is passed for the
	 *                                  string, an exception is thrown.
	 */
	public void setSigns(String signs) throws IllegalArgumentException {
		if (signs.length() < 1) {
			throw new IllegalArgumentException(
					"The snake type needs at least one character, " + "so that it can be searched for at all.");
		}
		this.signs = signs;
	}

	/**
	 * This method returns the points of the snake type as an integer.
	 * 
	 * @return The points of the snake type, an integer.
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * Through this method, setting the points of a snake type is possible even
	 * after instantiation. It is important to ensure that a valid point value is
	 * passed. This method is primarily used for reading data, but also for testing
	 * and future program extension.
	 * 
	 * @param points The points to be passed to this snake type.
	 * @throws IllegalArgumentException If an invalid argument is passed for the
	 *                                  points, an exception is thrown.
	 */
	public void setPoints(int points) throws IllegalArgumentException {
		if (points < 0 || points > Integer.MAX_VALUE - 1) {
			throw new IllegalArgumentException(
					"For the 'SnakeType' class, the 'points' attribute cannot be too small or too large.");
		}
		this.points = points;
	}

	/**
	 * This method returns the number of snakes of this snake type as an integer.
	 * 
	 * @return The number of snakes of a snake type.
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * Through this method, setting the number of snakes of a snake type is possible
	 * even after instantiation. It is important to ensure that a valid number is
	 * passed. This method is primarily used for reading data, but also for testing
	 * and future program extension.
	 * 
	 * @param amount The number of snakes of this snake type to be distributed in
	 *               the jungle.
	 * @throws IllegalArgumentException If an invalid argument is passed for the
	 *                                  number, an exception is thrown.
	 */
	public void setAmount(int amount) throws IllegalArgumentException {
		if (amount < 0) {
			throw new IllegalArgumentException(
					"For the 'SnakeType' class, the 'amount' attribute cannot be less than 0.");
		}
		if (amount > Integer.MAX_VALUE - 1) {
			throw new IllegalArgumentException(
					"For the 'SnakeType' class, the 'amount' attribute cannot be too large.");
		}
		this.amount = amount;
	}
}