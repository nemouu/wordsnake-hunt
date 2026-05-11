package org.snakehunt.model;

import java.util.*;

/**
 * A class that models snakes. These snakes are hidden, for example, by the
 * jungle generator in the jungle, or searched by the snake search in the
 * jungle. In addition to getters and setters, some utility methods are provided
 * that are used by other classes.
 * 
 * @author Philip Redecker
 */
public class Snake {
	private SnakeType type;
	private List<SnakeElement> elements;

	/**
	 * In this parameterized constructor, when instantiating this class, a snake
	 * type or a list of snake elements can be passed. In this case, the snake may
	 * be complete after instantiation.
	 * 
	 * @param type     The snake type the snake should have.
	 * @param elements A list of snake elements, according to the data structure
	 *                 snake element defined in this package.
	 */
	public Snake(SnakeType type, List<SnakeElement> elements) {
		super();
		this.type = type;
		this.elements = elements;
	}

	/**
	 * An alternative parameterized constructor. In this, only a snake type is
	 * passed. In the constructor itself, an empty list of snake elements, according
	 * to the data structure snake element defined in this package, is created, so
	 * that snake elements can be added to the snake at any time.
	 * 
	 * @param type The snake type the snake should have.
	 */
	public Snake(SnakeType type) {
		super();
		this.type = type;
		this.elements = new ArrayList<SnakeElement>();
	}

	/**
	 * A parameterless constructor, so that in future changes to the program, it is
	 * possible to use this class for testing, for example. In the constructor
	 * itself, an empty list of snake elements, according to the data structure
	 * snake element defined in this package, is created, so that snake elements can
	 * be added to the snake at any time.
	 */
	public Snake() {
		super();
		this.elements = new ArrayList<SnakeElement>();
	}

	/**
	 * A method that adds a snake element (at the end of the snake) to the snake.
	 * 
	 * @param inElement The snake element to be added.
	 */
	public void addElement(SnakeElement inElement) {
		this.elements.add(inElement);
	}

	/**
	 * A method that removes the last snake element of a snake.
	 */
	public void removeLastElement() {
		if (elements != null && elements.size() > 0) {
			this.elements.remove(elements.size() - 1);
		}
	}

	/**
	 * A method that returns a string containing the character of the next snake
	 * element. If the snake no longer has a next element, "" is returned.
	 * 
	 * @return A string containing the character of the next snake element. If there
	 *         is no next element, null is returned.
	 */
	public String characterOfNextElement() {
		if (elements.size() < type.getSigns().length()) {
			return type.getSigns().substring(elements.size(), elements.size() + 1);
		}
		return "";
	}

	/**
	 * A method that returns a string containing the character of the last snake
	 * element of the snake.
	 * 
	 * @return A string containing the character of the last snake element.
	 */
	public String characterOfLastElement() {
		return type.getSigns().substring(type.getSigns().length() - 1, type.getSigns().length());
	}

	/**
	 * Returns the snake type of the snake.
	 * 
	 * @return A snake type, according to the data structure snake type defined in
	 *         this package.
	 */
	public SnakeType getType() {
		return type;
	}

	/**
	 * This method allows setting the snake type of the snake even after
	 * instantiation. This method is mainly used for reading data, but also for
	 * testing or for future program extension.
	 * 
	 * @param type A snake type to be passed to the snake.
	 */
	public void setType(SnakeType type) {
		this.type = type;
	}

	/**
	 * Returns the snake elements of the snake.
	 * 
	 * @return A list of snake elements according to the data structure snake
	 *         element defined in this package.
	 */
	public List<SnakeElement> getElements() {
		return elements;
	}

	/**
	 * This method allows setting the snake elements of the snake even after
	 * instantiation. This method is mainly used for reading data, but also for
	 * testing or for future program extension.
	 * 
	 * @param elements A list of snake elements to be passed to the snake.
	 */
	public void setElements(List<SnakeElement> elements) {
		this.elements = elements;
	}
}