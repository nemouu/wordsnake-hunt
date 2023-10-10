package de.fuhagen.course01584.ss23.model;

/**
 * A class that models snake elements. Each snake consists of one or more snake
 * elements. Snake elements are the building blocks of snakes. Each snake
 * element is associated with a field in the jungle that contains the correct
 * character. The correct character is provided by the snake type's string to
 * which the snake elements belong.
 * 
 * @author Philip Redecker
 *
 */
public class SnakeElement {
	private Field field;

	/**
	 * A parameterized constructor for the SnakeElement class. During instantiation,
	 * a reference to the field belonging to this snake element can be provided,
	 * according to the snake type.
	 * 
	 * @param field The field associated with this snake element.
	 */
	public SnakeElement(Field field) {
		super();
		this.field = field;
	}

	/**
	 * A parameterless constructor, allowing this class to be used for future
	 * changes to the program, for example, for testing purposes.
	 */
	public SnakeElement() {
		super();
	}

	/**
	 * This method returns the field associated with this snake element.
	 * 
	 * @return The field associated with this snake element.
	 */
	public Field getField() {
		return field;
	}

	/**
	 * This method allows setting the field of the snake element even after
	 * instantiation. This method is primarily for reading data but also for testing
	 * and potential future program extension.
	 * 
	 * @param field The field to be assigned to the snake element.
	 */
	public void setField(Field field) {
		this.field = field;
	}
}