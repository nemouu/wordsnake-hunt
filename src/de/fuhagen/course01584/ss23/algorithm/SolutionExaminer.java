package de.fuhagen.course01584.ss23.algorithm;

import java.util.*;

import de.fuhagen.course01584.ss23.main.SnakeHuntAPI.ErrorType;
import de.fuhagen.course01584.ss23.model.*;

/**
 * This class provides constructors and methods to enable the examination of
 * solutions to problem instances in the snake hunt. The solution contained in a
 * model is checked for errors using selected methods. The errors have the type
 * defined in the 'ErrorType' enum specified in the 'SnakeSearchAPI' interface.
 * 
 * @author Philip Redecker
 *
 */
public class SolutionExaminer {
	private IModel model;
	private Jungle jungle;
	private List<Snake> snakes;
	private List<ErrorType> errorList;

	/**
	 * A parameterized constructor for the SolutionExaminer class that accepts a
	 * model. If an empty model or null is passed, an exception is thrown.
	 * 
	 * @param model The model whose solution the examiner should check.
	 * @throws IllegalArgumentException An exception is thrown if an inappropriate
	 *                                  model is passed to the constructor.
	 */
	public SolutionExaminer(IModel model) throws IllegalArgumentException {
		super();
		if (model.getSolution() == null || model.getSolution().getSnakes().size() == 0) {
			System.out.println("The model passed to the solution examiner has no solution. To "
					+ "check a solution, a model containing a solution must be passed. If "
					+ "there is no solution, it can be found using snake search. An empty "
					+ "error list will be returned.");
			System.out.println();
			throw new IllegalArgumentException(
					"The SolutionExaminer constructor must be passed a model containing a solution.");
		}
		this.model = model;
		this.jungle = model.getJungle();
		this.snakes = model.getSolution().getSnakes();
	}

	/**
	 * A parameterless constructor, making it possible to use this class for testing
	 * in future program changes.
	 */
	public SolutionExaminer() {
		super();
	}

	/**
	 * A public method that uses multiple private methods to determine the type and
	 * number of errors of type 'ErrorType' in the solution. If an error occurs
	 * multiple times, an element of the corresponding error type is added to the
	 * returned error list each time.
	 * 
	 * @return The (unordered) list of all errors present in the solution.
	 */
	public List<ErrorType> examineSolution() {
		errorList = new ArrayList<ErrorType>();
		examineNumber(errorList);
		examineAssignment(errorList);
		examineUsage(errorList);
		examineNeighborhood(errorList);
		return errorList;
	}

	private void examineNumber(List<ErrorType> errorList) {
		/*
		 * The solution contained in the model is checked for errors of type
		 * 'ErrorType.ELEMENTS'. If such an error occurs, an element of the
		 * corresponding error type is added to the error list.
		 */
		for (Snake snake : snakes) {
			if (snake.getElements().size() != snake.getType().getSigns().length()) {
				errorList.add(ErrorType.ELEMENTS);
			}
		}
	}

	private void examineAssignment(List<ErrorType> errorList) {
		/*
		 * The solution contained in the model is checked for errors of type
		 * 'ErrorType.ASSIGNMENT'. If such an error occurs, an element of the
		 * corresponding error type is added to the error list.
		 */
		for (Snake snake : snakes) {
			if (snake.getElements().size() == snake.getType().getSigns().length()) {
				for (int i = 0; i < snake.getElements().size(); i++) {
					if (!snake.getElements().get(i).getField().getCharacter()
							.equals(snake.getType().getSigns().substring(i, i + 1))) {
						errorList.add(ErrorType.ASSIGNMENT);
					}
				}
			}
		}
	}

	private void examineUsage(List<ErrorType> errorList) {
		/*
		 * The solution contained in the model is checked for errors of type
		 * 'ErrorType.USAGE'. If such an error occurs, an element of the corresponding
		 * error type is added to the error list. A 2-dimensional array with numbers is
		 * created to store the usability of the fields. This allows checking for
		 * usability errors in the solution without modifying the model itself.
		 */
		int[][] usageMatrix = new int[jungle.getRows()][jungle.getColumns()];
		for (int i = 0; i < usageMatrix.length; i++) {
			for (int j = 0; j < usageMatrix[0].length; j++) {
				usageMatrix[i][j] = jungle.getFields()[i][j].getUsage();
			}
		}
		for (Snake snake : snakes) {
			for (SnakeElement element : snake.getElements()) {
				if (usageMatrix[element.getField().getRow()][element.getField().getColumn()] < 1) {
					errorList.add(ErrorType.USAGE);
				}
				usageMatrix[element.getField().getRow()][element.getField().getColumn()]--;
			}
		}
	}

	private void examineNeighborhood(List<ErrorType> errorList) {
		/*
		 * The solution contained in the model is checked for errors of type
		 * 'ErrorType.NEIGHBORHOOD'. If such an error occurs, an element of the
		 * corresponding error type is added to the error list.
		 */
		for (Snake snake : snakes) {
			for (int i = 0; i < snake.getElements().size() - 1; i++) {
				snake.getType().getStructure().getNeighbors(jungle, snake.getElements().get(i).getField());
				if (!snake.getType().getStructure().getNeighbors(jungle, snake.getElements().get(i).getField())
						.contains(snake.getElements().get(i + 1).getField())) {
					errorList.add(ErrorType.NEIGHBORHOOD);
				}
			}
		}
	}

	/**
	 * Returns the model currently in the SolutionExaminer. This is primarily
	 * intended for future changes and testing.
	 * 
	 * @return Value of the 'model' variable.
	 */
	public IModel getModel() {
		return model;
	}

	/**
	 * Allows setting the model of the SolutionExaminer. It is possible to pass a
	 * model, even if the parameterless constructor was used initially. It is also
	 * generally possible to change the model after instantiating the class.
	 * 
	 * @param model The model to be passed.
	 * @throws IllegalArgumentException An exception is thrown if an inappropriate
	 *                                  model is passed.
	 */
	public void setModel(IModel model) throws IllegalArgumentException {
		if (model.getSolution() == null || model.getSolution().getSnakes().size() == 0) {
			System.out.println("The model passed to the solution examiner has no solution. To "
					+ "check a solution, a model containing a solution must be passed. If "
					+ "there is no solution, it can be found using snake search. An empty "
					+ "error list will be returned.");
			System.out.println();
			throw new IllegalArgumentException("The SolutionExaminer must be passed a model containing a solution.");
		}
		this.model = model;
	}

	/**
	 * Returns the current error list of the SolutionExaminer. This is primarily
	 * intended for future changes and testing.
	 * 
	 * @return The current error list of the SolutionExaminer.
	 */
	public List<ErrorType> getErrorList() {
		return errorList;
	}

	/**
	 * Returns the current list of snakes in the SolutionExaminer. This is primarily
	 * intended for future changes and testing.
	 * 
	 * @return The snake list of the SolutionExaminer.
	 */
	public List<Snake> getSnakes() {
		return snakes;
	}
}