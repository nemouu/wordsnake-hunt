package org.snakehunt.model;

/**
 * A class that models the jungle in the model and considers its rules. It is
 * possible to create instances of the Jungle class and perform various
 * operations. In particular, some methods are provided to support snake
 * hunting.
 * 
 * @author Philip Redecker
 *
 */
public class Jungle {
	private int rows;
	private int columns;
	private Field[][] fields;
	private String signs;

	/**
	 * A constructor for the Jungle class that takes two numbers for the number of
	 * rows and columns, a string for the set of characters used, and a
	 * 2-dimensional array for the fields. A complete jungle is created directly
	 * upon instantiation.
	 * 
	 * @param rows    A number representing the desired number of rows.
	 * @param columns A number representing the desired number of columns.
	 * @param fields  A 2-dimensional array of fields. These are the jungle fields.
	 * @param signs   A string containing the characters used in the jungle.
	 * @throws IllegalArgumentException If invalid arguments for rows and columns
	 *                                  are passed, an exception is thrown.
	 */
	public Jungle(int rows, int columns, Field[][] fields, String signs) throws IllegalArgumentException {
		super();
		if (rows < 0 || columns < 0) {
			throw new IllegalArgumentException(
					"For the 'Jungle' class, the 'rows' and 'columns' attributes cannot take negative values.");
		}
		if (Integer.MAX_VALUE - 1 < rows || Integer.MAX_VALUE - 1 < columns) {
			throw new IllegalArgumentException(
					"For the 'Jungle' class, the 'rows' and 'columns' attributes cannot be greater than the largest integer value.");
		}
		this.rows = rows;
		this.columns = columns;
		this.fields = fields;
		this.signs = signs;
	}

	/**
	 * An alternative parameterized constructor for the Jungle class. It takes two
	 * numbers for the number of rows and columns, a string for the set of
	 * characters, and a number for creating empty fields. A 2-dimensional array is
	 * not passed here; instead, it is created directly in the constructor. The
	 * passed parameter 'parameter' is used to instantiate the individual fields
	 * (see constructors for the Field class).
	 * 
	 * @param rows      A number representing the desired number of rows.
	 * @param columns   A number representing the desired number of columns.
	 * @param signs     A string containing the characters used in the jungle.
	 * @param parameter A number passed to the constructor of individual fields. It
	 *                  determines the usability and points of the individual fields
	 *                  (see constructors for the Field class).
	 * @throws IllegalArgumentException If invalid arguments for rows and columns
	 *                                  are passed, an exception is thrown.
	 */
	public Jungle(int rows, int columns, String signs, int parameter) throws IllegalArgumentException {
		super();
		if (rows < 0 || columns < 0) {
			throw new IllegalArgumentException(
					"For the 'Jungle' class, the 'rows' and 'columns' attributes cannot take negative values.");
		}
		if (Integer.MAX_VALUE - 1 < rows || Integer.MAX_VALUE - 1 < columns) {
			throw new IllegalArgumentException(
					"For the 'Jungle' class, the 'rows' and 'columns' attributes cannot be greater than the largest integer value.");
		}
		this.rows = rows;
		this.columns = columns;
		this.signs = signs;
		this.fields = new Field[rows][columns];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				fields[i][j] = new Field(i, j, parameter);
				int idNumber = columns * i + j;
				fields[i][j].setId("F" + idNumber);
			}
		}
	}

	/**
	 * A parameterless constructor, so that it is possible to use this class for
	 * testing, for example, in future changes to the program.
	 */
	public Jungle() {
		super();
	}

	/**
	 * A method that outputs the number of fields that have a character, i.e. the
	 * number of non-empty fields. The method supports snake searching and is also
	 * used for testing the Jungle class.
	 * 
	 * @return The number of fields occupied by a character.
	 */
	public int numberOfTakenFields() {
		int amount = 0;
		if (fields == null) {
			return amount;
		}
		for (int i = 0; i < fields.length; i++) {
			for (int j = 0; j < fields[0].length; j++) {
				if (fields[i][j].getCharacter() != null) {
					amount++;
				}
			}
		}
		return amount;
	}

	/**
	 * A simple method that outputs the total number of fields. It simply calculates
	 * the length of the jungle times the width of the jungle. This method is only
	 * for easy access to the number of fields.
	 * 
	 * @return The total number of fields in the jungle.
	 */
	public int numberOfFields() {
		return fields.length * fields[0].length;
	}

	@Override
	public String toString() {
		String output = "";
		if (numberOfTakenFields() == 0) {
			return output;
		}
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (fields[i][j].getCharacter() == null) {
					output += " (Ø," + fields[i][j].getUsage() + "," + fields[i][j].getPoints() + ")";
				} else {
					output += " (" + fields[i][j].getCharacter() + "," + fields[i][j].getUsage() + ","
							+ fields[i][j].getPoints() + ")";
				}
			}
			output += "\n\n";
		}
		return output;
	}

	/**
	 * Returns the number of rows in the jungle. This is provided mainly for testing
	 * or for future program expansion.
	 * 
	 * @return The number of rows in the jungle.
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * This method allows setting the rows of the jungle even after instantiation.
	 * This is mainly for testing or for future program expansion.
	 * 
	 * @param rows The number of rows to be passed to the jungle.
	 * @throws IllegalArgumentException If an invalid argument for rows is passed,
	 *                                  an exception is thrown.
	 */
	public void setRows(int rows) throws IllegalArgumentException {
		if (rows < 0) {
			throw new IllegalArgumentException(
					"For the 'Jungle' class, the 'rows' attribute cannot take negative values.");
		}
		if (Integer.MAX_VALUE < rows) {
			throw new IllegalArgumentException(
					"For the 'Jungle' class, the 'rows' attribute cannot be greater than the largest integer value.");
		}
		this.rows = rows;
	}

	/**
	 * Returns the number of columns in the jungle. This is provided mainly for
	 * testing or for future program expansion.
	 * 
	 * @return The number of columns in the jungle.
	 */
	public int getColumns() {
		return columns;
	}

	/**
	 * This method allows setting the columns of the jungle even after
	 * instantiation. This is mainly for testing or for future program expansion.
	 * 
	 * @param columns The number of columns to be passed to the jungle.
	 * @throws IllegalArgumentException If an invalid argument for columns is
	 *                                  passed, an exception is thrown.
	 */
	public void setColumns(int columns) throws IllegalArgumentException {
		if (columns < 0) {
			throw new IllegalArgumentException(
					"For the 'Jungle' class, the 'columns' attribute cannot take negative values.");
		}
		if (Integer.MAX_VALUE - 1 < columns) {
			throw new IllegalArgumentException(
					"For the 'Jungle' class, the 'columns' attribute cannot be greater than the largest integer value.");
		}
		this.columns = columns;
	}

	/**
	 * With this method, it is possible to set an individual field. Since fields
	 * contain information about where they are exactly in the jungle (see Field
	 * class), only a simple assignment is needed.
	 * 
	 * @param inField The field to be placed in the jungle.
	 */
	public void setField(Field inField) {
		this.fields[inField.getRow()][inField.getColumn()] = inField;
	}

	/**
	 * Returns the fields of the jungle in the form of the internal data structure
	 * (2D array of fields). It is used at multiple program locations to search for
	 * fields in the jungle, and this method helps. However, it is also used for
	 * testing or for future program expansion.
	 * 
	 * @return The fields of the jungle (2D array of fields).
	 */
	public Field[][] getFields() {
		return fields;
	}

	/**
	 * This method allows setting the fields of the jungle even after instantiation.
	 * This is mainly for testing or for future program expansion.
	 * 
	 * @param fields A 2D array of fields to be passed to the jungle.
	 */
	public void setFields(Field[][] fields) {
		this.fields = fields;
	}

	/**
	 * Returns the character set of the jungle. Many other parts of the program need
	 * to know the character set of the jungle (for example, the jungle generator),
	 * so it is accessible via this method. However, it is also provided for testing
	 * or for future program expansion.
	 * 
	 * @return The character set of the jungle.
	 */
	public String getSigns() {
		return signs;
	}

	/**
	 * This method allows setting the character set of the jungle even after
	 * instantiation. This is mainly for testing or for future program expansion.
	 * 
	 * @param signs The character set to be passed to the jungle.
	 */
	public void setSigns(String signs) {
		this.signs = signs;
	}
}