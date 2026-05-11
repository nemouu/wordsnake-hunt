package org.snakehunt.algorithm;

import java.util.*;

import org.snakehunt.model.*;

/**
 * A class SnakeSearch that implements the ISnakeSearch interface and thereby
 * implements the methods specified in ISnakeSearch. Various private methods can
 * be found here that enable the functionality specified by the interface.
 * 
 * @author Philip Redecker
 *
 */
public class SnakeSearch implements ISnakeSearch {
	private IModel model;
	private Solution currSolution;
	private Solution solution;
	private SolutionEvaluator evaluator;
	private Long currTime;
	private Double modelTime;
	private ISnakeSearchUtil functions;

	/**
	 * A parameterized constructor to which the model in which snakes are to be
	 * searched is directly passed. If there is no jungle in the model, an exception
	 * is thrown.
	 * 
	 * @param model The model in which to search.
	 * @throws IllegalArgumentException An exception is thrown if an inappropriate
	 *                                  model is passed.
	 */
	public SnakeSearch(IModel model) throws IllegalArgumentException {
		super();
		if (model.getJungle().numberOfTakenFields() == 0) {
			System.out.println("The model to be passed to SnakeSearch has a jungle without "
					+ "fields. A model with fields must be passed. If no fields are present, they can be created with the "
					+ "jungle generator.");
			System.out.println();
			throw new IllegalArgumentException(
					"The constructor of SnakeSearch must be passed a model that has " + "fields in the jungle.");
		}
		this.model = model;
		this.currSolution = new Solution();
		this.solution = new Solution();
		this.evaluator = new SolutionEvaluator();
		this.functions = new SnakeSearchUtil(model);
		this.modelTime = model.calculateTimeToNanoseconds();
	}

	/**
	 * A parameterless constructor, so that in future changes to the program it is
	 * possible to use this class, for example for testing.
	 */
	public SnakeSearch() {
		super();
	}

	@Override
	public void searchSnakes() {
		this.currTime = System.nanoTime();
		searchSnake();
	}

	private void searchSnake() {
		/*
		 * A method that enters the snake search. Snakes are built up one by one and
		 * then possibly broken down again if no new elements can be found or if a
		 * solution has been saved or a new combination is being tried. The method
		 * 'searchSnakeElement' is called from this method. Here, the 'solution' is
		 * overwritten if the 'currSolution' achieves more points. It should be noted
		 * that additional time queries have been inserted, so the program terminates
		 * faster when the time is up.
		 */
		if (evaluator.evaluateSolution(currSolution) > evaluator.evaluateSolution(solution)) {
			solution = new Solution();
			for (Snake snake : currSolution.getSnakes()) {
				Snake newSnake = new Snake(snake.getType());
				for (SnakeElement element : snake.getElements()) {
					newSnake.addElement(element);
				}
				solution.addSnake(newSnake);
			}
		}

		/*
		 * First, iterate over snake types to simplify prioritizing snake types.
		 */
		List<SnakeType> snakeTypes = functions.createValidSnakeTypes();
		for (SnakeType snakeType : snakeTypes) {
			List<Field> startingFields = functions.createValidStartingFields(snakeType);
			for (Field startingField : startingFields) {
				if (System.nanoTime() - currTime > modelTime) {
					return;
				}
				// Create a new snake with the corresponding snake head.
				startingField.setUsage(startingField.getUsage() - 1);
				Snake newSnake = new Snake(snakeType);
				SnakeElement snakeHead = new SnakeElement(startingField);
				newSnake.addElement(snakeHead);
				currSolution.addSnake(newSnake);

				// Try to search for the remaining snake elements.
				searchSnakeElement(snakeHead, newSnake);

				// Remove the snake and the snake head, and release the used fields.
				startingField.setUsage(startingField.getUsage() + 1);
				newSnake.removeLastElement();
				currSolution.removeLastSnake();
			}
		}
	}

	private void searchSnakeElement(SnakeElement previousElement, Snake currentSnake) {
		/*
		 * A method called by the 'searchSnake' method and by itself. The next element
		 * of the current snake is always searched for. So long until it is no longer
		 * possible either because no suitable subsequent element is found or until a
		 * solution is saved or a new combination is tried. If a snake is complete, this
		 * jumps into the 'searchSnake' method to search for the next snake.
		 */
		if (previousElement.getField().getCharacter().equals(currentSnake.characterOfLastElement())
				&& currentSnake.getElements().size() == currentSnake.getType().getSigns().length()) {
			searchSnake();
			return;
		}

		// List permissible neighbors and iterate over them one by one.
		List<Field> neighborFields = functions.createValidNeighbors(previousElement, currentSnake);
		for (Field neighborField : neighborFields) {
			if (System.nanoTime() - currTime > modelTime) {
				return;
			}

			// Add the next neighbor to the current snake.
			neighborField.setUsage(neighborField.getUsage() - 1);
			SnakeElement newElement = new SnakeElement(neighborField);
			currentSnake.addElement(newElement);

			// Search for the next snake element.
			searchSnakeElement(newElement, currentSnake);

			// Remove the individual snake elements and release the used fields again.
			neighborField.setUsage(neighborField.getUsage() + 1);
			currentSnake.removeLastElement();
		}
	}

	@Override
	public IModel getModel() {
		return model;
	}

	@Override
	public void setModel(IModel model) throws IllegalArgumentException {
		if (model.getJungle().numberOfTakenFields() == 0) {
			System.out.println("The model to be passed to SnakeSearch has a jungle without "
					+ "fields. A model with fields must be passed. If no fields are present, they can be created with the "
					+ "jungle generator.");
			System.out.println();
			throw new IllegalArgumentException(
					"SnakeSearch must be passed a model that has " + "fields in the jungle.");
		}
		this.model = model;
	}

	@Override
	public Solution getSolution() {
		return solution;
	}

	@Override
	public Long getCurrTime() {
		return currTime;
	}

	@Override
	public ISnakeSearchUtil getFunctions() {
		return functions;
	}

	@Override
	public void setFunctions(ISnakeSearchUtil functions) {
		this.functions = functions;
	}
}