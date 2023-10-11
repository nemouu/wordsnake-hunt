package de.fuhagen.course01584.ss23.model;

/**
 * A class that models the fields of the jungle in the model. Fields contain
 * information about their row and column, their ID, points, usability, and
 * their own character. It offers various constructors and otherwise only
 * getters and setters for the individual variables.
 * 
 * @author Philip Redecker
 *
 */
public class Field {
	private String id;
	private int row;
	private int column;
	private int usage;
	private int points;
	private String character;

	/**
	 * A parameterized constructor to directly pass all attributes as parameters. A
	 * field instantiated this way could be added directly to a jungle, for example.
	 * The constructor is also used for testing and/or future program extension.
	 * 
	 * @param id        A string containing the ID of the field.
	 * @param row       The number of the row where the field is to be found.
	 * @param column    The number of the column where the field is to be found.
	 * @param usage     The usability the field should have.
	 * @param points    The points the field should have.
	 * @param character A string containing the character of the field.
	 * @throws IllegalArgumentException If an invalid argument for row, column,
	 *                                  usability, or points is passed, an exception
	 *                                  is thrown.
	 */
	public Field(String id, int row, int column, int usage, int points, String character)
			throws IllegalArgumentException {
		super();
		if (row < 0 || column < 0) {
			throw new IllegalArgumentException(
					"For the 'Field' class, 'row' and 'column' attributes must not take negative values.");
		}
		if (Integer.MAX_VALUE < row || Integer.MAX_VALUE < column || Integer.MAX_VALUE < usage) {
			throw new IllegalArgumentException(
					"For the 'Field' class, 'row', 'column', and 'usage' attributes must not exceed the "
							+ "largest integer value");
		}
		if (points > Integer.MAX_VALUE - 1 || points < 0) {
			throw new IllegalArgumentException(
					"For the 'Field' class, the 'points' attribute must not be too large or too small.");
		}
		if (usage > Integer.MAX_VALUE - 1 || usage < Integer.MIN_VALUE + 1) {
			throw new IllegalArgumentException(
					"For the 'Field' class, the 'usage' attribute must not be too large or too small.");
		}
		this.id = id;
		this.row = row;
		this.column = column;
		this.usage = usage;
		this.points = points;
		this.character = character;
	}

	/**
	 * An alternative parameterized constructor that only takes numbers for row and
	 * column, and another parameter for points and usability. This constructor is
	 * used to initialize fields either when reading from a file, where the field
	 * data will be overwritten later, or for example, in the jungle generator.
	 * 
	 * @param row       The number of the row where the field is to be found.
	 * @param column    The number of the column where the field is to be found.
	 * @param parameter The number for points and usability of the field.
	 * @throws IllegalArgumentException If an invalid argument for row, column, or
	 *                                  parameter is passed, an exception is thrown.
	 */
	public Field(int row, int column, int parameter) throws IllegalArgumentException {
		super();
		if (row < 0 || column < 0) {
			throw new IllegalArgumentException(
					"For the 'Field' class, 'row' and 'column' attributes must not take negative values.");
		}
		if (row > Integer.MAX_VALUE - 1 || row > Integer.MAX_VALUE - 1) {
			throw new IllegalArgumentException(
					"For the 'Field' class, 'row' and 'column' attributes must not exceed the largest integer value");
		}
		if (parameter < Integer.MIN_VALUE + 1 || parameter > Integer.MAX_VALUE - 1) {
			throw new IllegalArgumentException(
					"For the 'Field' class, 'usage' and 'points' attributes must not exceed the largest "
							+ "integer value and must not be less than the smallest integer value.");
		}
		this.row = row;
		this.column = column;
		this.usage = parameter;
		this.points = parameter;
	}

	/**
	 * A parameterless constructor so that it is possible to use this class for
	 * testing, for example, in future changes to the program.
	 */
	public Field() {
		super();
	}

	/**
	 * Returns the ID of the field.
	 * 
	 * @return A string containing the ID of the field.
	 */
	public String getId() {
		return id;
	}

	/**
	 * This method allows setting the ID of the field even after instantiation. This
	 * is primarily for testing and/or future program extension.
	 * 
	 * @param id A string containing the ID to be passed to the field.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Returns the row of the field (in the jungle).
	 * 
	 * @return The number of the row where the field is found.
	 */
	public int getRow() {
		return row;
	}

	/**
	 * This method allows setting the row of the field even after instantiation.
	 * This is primarily for testing and/or future program extension.
	 * 
	 * @param row The number of the row to be passed to the field.
	 * @throws IllegalArgumentException If an invalid argument for row is passed, an
	 *                                  exception is thrown.
	 */
	public void setRow(int row) throws IllegalArgumentException {
		if (row < 0) {
			throw new IllegalArgumentException(
					"For the 'Field' class, the 'row' attribute must not take negative values.");
		}
		if (row > Integer.MAX_VALUE - 1) {
			throw new IllegalArgumentException(
					"For the 'Field' class, the 'row' attribute must not exceed the largest integer value.");
		}
		this.row = row;
	}

	/**
	 * Returns the column of the field (in the jungle).
	 * 
	 * @return The number of the column where the field is found.
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * This method allows setting the column of the field even after instantiation.
	 * This is primarily for testing and/or future program extension.
	 * 
	 * @param column The number of the column to be passed to the field.
	 * @throws IllegalArgumentException If an invalid argument for column is passed,
	 *                                  an exception is thrown.
	 */
	public void setColumn(int column) throws IllegalArgumentException {
		if (column < 0) {
			throw new IllegalArgumentException(
					"For the 'Field' class, the 'column' attribute must not take negative values.");
		}
		if (column > Integer.MAX_VALUE - 1) {
			throw new IllegalArgumentException(
					"For the 'Field' class, the 'column' attribute must not exceed the largest integer value.");
		}
		this.column = column;
	}

	/**
	 * Returns the (current) usability of the field. This is the number of times the
	 * field can still be used for a solution.
	 * 
	 * @return The usability of the field.
	 */
	public int getUsage() {
		return usage;
	}

	/**
	 * This method allows setting the usability of the field even after
	 * instantiation. This is primarily for testing and/or future program extension.
	 * 
	 * @param usage The usability to be passed to the field.
	 * @throws IllegalArgumentException If an invalid argument for usability is
	 *                                  passed, an exception is thrown.
	 */
	public void setUsage(int usage) throws IllegalArgumentException {
		if (usage > Integer.MAX_VALUE - 1) {
			throw new IllegalArgumentException(
					"For the 'Field' class, the 'usage' attribute must not exceed the largest integer value.");
		}
		this.usage = usage;
	}

	/**
	 * Returns the points of the field. These are the points the field brings when
	 * used for a solution.
	 * 
	 * @return The points of the field.
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * This method allows setting the points of the field even after instantiation.
	 * This is primarily for testing and/or future program extension.
	 * 
	 * @param points The points to be passed to the field.
	 * @throws IllegalArgumentException If an invalid argument for points is passed,
	 *                                  an exception is thrown.
	 */
	public void setPoints(int points) throws IllegalArgumentException {
		if (points > Integer.MAX_VALUE - 1 || points < 0) {
			throw new IllegalArgumentException(
					"For the 'Field' class, the 'points' attribute must not be too large or too small.");
		}
		this.points = points;
	}

	/**
	 * Returns the character of the field.
	 * 
	 * @return A string containing the character of the field.
	 */
	public String getCharacter() {
		return character;
	}

	/**
	 * This method allows setting the character of the field even after
	 * instantiation. This is primarily for testing and/or future program extension.
	 * 
	 * @param character A string containing the character to be passed to the field.
	 */
	public void setCharacter(String character) {
		this.character = character;
	}
}