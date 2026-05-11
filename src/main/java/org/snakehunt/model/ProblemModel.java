package org.snakehunt.model;

import java.util.*;

/**
 * An implementation of the IModel interface to handle and process the data
 * needed for snake searching. The methods from the interface are implemented,
 * and additionally, some private methods are provided to output the model to
 * the console.
 * 
 * @author Philip Redecker
 *
 */
public class ProblemModel implements IModel {
	private String unitOfTime;
	private Double[] time;
	private Jungle jungle;
	private List<SnakeType> snakeTypes;
	private Solution solution;

	/**
	 * A parameterized constructor that can create an instance of the model.
	 * Parameters are passed in such a way that a possibly non-empty list of snake
	 * types is directly included in the model. This is passed as a constructor
	 * here.
	 * 
	 * @param jungle     A jungle according to the data structure defined in this
	 *                   package.
	 * @param snakeTypes A list of snake types according to the data structure
	 *                   defined in this package.
	 * @param solution   A solution according to the data structure defined in this
	 *                   package.
	 * @param time       An array of Doubles for time specification and/or time
	 *                   assignment.
	 * @throws IllegalArgumentException If an invalid argument is passed for the
	 *                                  'time' attribute, an exception is thrown.
	 */
	public ProblemModel(Jungle jungle, List<SnakeType> snakeTypes, Solution solution, Double[] time)
			throws IllegalArgumentException {
		super();
		if (time.length == 1) {
			if (time[0] == 0.0) {
				throw new IllegalArgumentException(
						"The 'time' attribute in the 'ProblemModel' class cannot be equal to 0.");
			}
			if (time[0] < 0.0) {
				throw new IllegalArgumentException(
						"The 'time' attribute in the 'ProblemModel' class cannot be less than 0.");
			}
			if (time[0] > Double.MAX_VALUE - 2E292) {
				throw new IllegalArgumentException(
						"The 'time' attribute in the 'ProblemModel' class cannot be too large.");
			}
		}
		if (time.length == 2) {
			if (time[0] == 0.0 || time[1] == 0.0) {
				throw new IllegalArgumentException(
						"The 'time' attribute in the 'ProblemModel' class cannot be equal to 0.");
			}
			if (time[0] < 0.0 || time[1] < 0.0) {
				throw new IllegalArgumentException(
						"The 'time' attribute in the 'ProblemModel' class cannot be less than 0.");
			}
			if (time[0] > Double.MAX_VALUE - 2E292 || time[1] > Double.MAX_VALUE - 2E292) {
				throw new IllegalArgumentException(
						"The 'time' attribute in the 'ProblemModel' class cannot be too large.");
			}
		}
		this.jungle = jungle;
		this.snakeTypes = snakeTypes;
		this.solution = solution;
		this.time = time;
	}

	/**
	 * An alternative parameterized constructor that allows instantiating a model
	 * without passing a list of snake types. In this case, an empty list of snake
	 * types is created in the constructor so that snake types can be added later.
	 * 
	 * @param jungle   A jungle according to the data structure defined in this
	 *                 package.
	 * @param solution A solution according to the data structure defined in this
	 *                 package.
	 * @param time     An array of Doubles for time specification and/or time
	 *                 assignment.
	 * @throws IllegalArgumentException If an invalid argument is passed for the
	 *                                  'time' attribute, an exception is thrown.
	 */
	public ProblemModel(Jungle jungle, Solution solution, Double[] time) throws IllegalArgumentException {
		super();
		if (time.length == 1) {
			if (time[0] == 0.0) {
				throw new IllegalArgumentException(
						"The 'time' attribute in the 'ProblemModel' class cannot be equal to 0.");
			}
			if (time[0] < 0.0) {
				throw new IllegalArgumentException(
						"The 'time' attribute in the 'ProblemModel' class cannot be less than 0.");
			}
			if (time[0] > Double.MAX_VALUE - 2E292) {
				throw new IllegalArgumentException(
						"The 'time' attribute in the 'ProblemModel' class cannot be too large.");
			}
		}
		if (time.length == 2) {
			if (time[0] == 0.0) {
				throw new IllegalArgumentException(
						"The 'time' attribute in the 'ProblemModel' class cannot be equal to 0.");
			}
			if (time[0] < 0.0 || time[1] < 0.0) {
				throw new IllegalArgumentException(
						"The 'time' attribute in the 'ProblemModel' class cannot be less than 0.");
			}
			if (time[0] > Double.MAX_VALUE - 2E292 || time[1] > Double.MAX_VALUE - 2E292) {
				throw new IllegalArgumentException(
						"The 'time' attribute in the 'ProblemModel' class cannot be too large.");
			}
		}
		this.jungle = jungle;
		this.snakeTypes = new ArrayList<SnakeType>();
		this.solution = solution;
		this.time = time;
		if (unitOfTime == null) {
			unitOfTime = "s";
		}
	}

	/**
	 * A default constructor to allow future changes to the program and facilitate
	 * testing by instantiating an empty list of snake types.
	 */
	public ProblemModel() {
		super();
		this.snakeTypes = new ArrayList<SnakeType>();
	}

	@Override
	public Double calculateTimeToNanoseconds() throws IllegalArgumentException {
		switch (getUnitOfTime()) {
		case "ms": {
			return getTime()[0] * 1000000;
		}
		case "s": {
			return getTime()[0] * 1000000000;
		}
		case "min": {
			return getTime()[0] * 6.0 * (Math.pow(10, 10));
		}
		case "h": {
			return getTime()[0] * 3.6 * (Math.pow(10, 12));
		}
		case "d": {
			return getTime()[0] * 8.64 * (Math.pow(10, 13));
		}
		default:
			throw new IllegalArgumentException("The time unit of the model cannot be converted.");
		}
	}

	@Override
	public Double calculateTimeInUnitGivenByModel(Long usedTimeInNanoseconds) throws IllegalArgumentException {
		switch (getUnitOfTime()) {
		case "ms": {
			return (double) (usedTimeInNanoseconds / 1000000.0);
		}
		case "s": {
			return (double) (usedTimeInNanoseconds / 1000000000.0);
		}
		case "min": {
			return (double) (usedTimeInNanoseconds / (6.0 * (Math.pow(10, 10))));
		}
		case "h": {
			return (double) (usedTimeInNanoseconds / (3.6 * (Math.pow(10, 12))));
		}
		case "d": {
			return (double) (usedTimeInNanoseconds / (8.64 * (Math.pow(10, 13))));
		}
		default:
			throw new IllegalArgumentException("The time unit of the model cannot be converted.");
		}
	}

	@Override
	public String toString() {
		String snakeTypesToString = "";
		for (SnakeType snakeType : snakeTypes) {
			snakeTypesToString += " (" + snakeType.toString() + ")\n";
		}
		try {
			if (jungle.numberOfTakenFields() == 0) {
				return "The jungle of this problem has " + jungle.getRows() + " rows, " + jungle.getColumns()
						+ " columns and the character set '" + jungle.getSigns()
						+ "' \nbut no fields, and snakes of snake type/s\n\n" + snakeTypesToString
						+ "\ncan be searched. Fields can be generated with the 'e' command. The solution to this is "
						+ "not \npresent and can be searched for using the 'l' command.";
			} else if (solution == null && jungle.numberOfTakenFields() < jungle.numberOfFields()) {
				return "The jungle of this problem has " + jungle.getRows() + " rows, " + jungle.getColumns()
						+ " columns and the character set '" + jungle.getSigns() + "'. "
						+ "The fields are\nalways given in the format '(character, usability, points)' and empty fields "
						+ " are marked by '(Ø, 0, 0)'\n. The fields are arranged as follows: \n\n\n" + jungle.toString()
						+ "\nSnakes of snake type/s can be searched for \n\n" + snakeTypesToString
						+ "\nand there is currently no solution in the model. However, it should be noted that"
						+ " the parsed\njungle contains empty fields. " + jungle.numberOfFields()
						+ " fields were expected but only " + jungle.numberOfTakenFields()
						+ " were read. The remaining\n"
						+ "fields are empty. A complete jungle can be generated using the 'e' command.";
			} else if (solution == null || solution.getSnakes().size() == 0) {
				return "The jungle of this problem has " + jungle.getRows() + " rows, " + jungle.getColumns()
						+ " columns and the character set '" + jungle.getSigns() + "'. "
						+ "The fields are\ngiven in the format '(character, usability, points)' and"
						+ " are arranged as follows: \n\n\n" + jungle.toString() + "\nSnakes can be searched for "
						+ "of the snake type/s \n\n" + snakeTypesToString
						+ "\nand there is currently no solution in the model. A solution can be searched for with the 'l' command.";
			} else if (jungle.numberOfTakenFields() < jungle.numberOfFields()) {
				return "The jungle of this problem has " + jungle.getRows() + " rows, " + jungle.getColumns()
						+ " columns and the character set '" + jungle.getSigns() + "'. "
						+ "The\nfields are always given in the format '(character, usability, points)' and empty fields "
						+ " are marked by '(Ø, 0, 0)'\n. The fields are arranged as follows: \n\n\n" + jungle.toString()
						+ "\nSnakes can be searched for of the snake type/s \n\n" + snakeTypesToString
						+ "\n. Some information is given for each found snake. "
						+ "Then the order of the\nelements of the respective snake is given in the format '(character, row, column)' "
						+ "and finally a\njungle is given that highlights the snake. Unused fields are marked with ( ).\nIt is to be noted here that "
						+ " the parsed jungle contains empty fields. " + jungle.numberOfFields()
						+ " fields were expected but\nonly " + jungle.numberOfTakenFields()
						+ " were read. The remaining fields are empty. A complete jungle can be generated\nusing the 'e' command.\n\n"
						+ toStringSolution();
			} else {
				return "The jungle of this problem has " + jungle.getRows() + " rows, " + jungle.getColumns()
						+ " columns and the character set '" + jungle.getSigns() + "'. "
						+ "The\nfields are always given in the format '(character, usability, points)' and"
						+ " are arranged as follows: \n\n\n" + jungle.toString() + "\nSnakes can be searched for "
						+ "of the snake type/s \n\n" + snakeTypesToString
						+ "\n. Some information is given for each found snake. "
						+ "Then the order of the\nelements of the respective snake is given in the format '(character, row, column)' "
						+ "and finally a\njungle is given that highlights the snake. Unused fields are marked with ( ).\n\n"
						+ toStringSolution();
			}
		} catch (Exception e) {
			return "An error occurred while outputting the model in a text format.";
		}
	}

	private String toStringSolution() {
		String output = "";
		int newLine = 1;
		for (int i = 0; i < solution.getSnakes().size(); i++) {
			List<Field> solutionFields = new ArrayList<Field>();
			output += " (" + (i + 1) + ") Information: (ID=" + solution.getSnakes().get(i).getType().getId()
					+ ", string=" + solution.getSnakes().get(i).getType().getSigns() + ", neighborhood structure="
					+ solution.getSnakes().get(i).getType().getStructure().toString() + ")\n\n     Path: ";
			for (int l = 0; l < solution.getSnakes().get(i).getElements().size(); l++) {
				solutionFields.add(solution.getSnakes().get(i).getElements().get(l).getField());
				output += "(" + solution.getSnakes().get(i).getElements().get(l).getField().getCharacter() + ", "
						+ solution.getSnakes().get(i).getElements().get(l).getField().getRow() + ", "
						+ solution.getSnakes().get(i).getElements().get(l).getField().getColumn() + ")";
				if (l < solution.getSnakes().get(i).getElements().size() - 1) {
					output += " -> ";
				}
				if (l == 13) {
					output += "\n              ";
					newLine++;
				} else if (l > newLine * 13) {
					output += "\n              ";
					newLine++;
				}
			}
			output += " \n\n     Jungle: ";
			for (int j = 0; j < jungle.getRows(); j++) {
				if (j > 0) {
					output += "                ";
				}
				for (int k = 0; k < jungle.getColumns(); k++) {
					if (solutionFields.contains(jungle.getFields()[j][k])) {
						output += " (" + jungle.getFields()[j][k].getCharacter() + ")";
					} else {
						output += " ( )";
					}
				}
				if (j != jungle.getRows() - 1) {
					output += "\n\n";
				}
			}
			output += "\n\n\n";
		}
		return output;
	}

	@Override
	public Double[] getTime() {
		return time;
	}

	@Override
	public void setTime(Double[] time) throws IllegalArgumentException {
		if (time.length == 1) {
			if (time[0] == 0.0) {
				throw new IllegalArgumentException("The 'time' attribute cannot be zero in the 'ProblemModel' class.");
			}
			if (time[0] < 0.0) {
				throw new IllegalArgumentException(
						"The 'time' attribute cannot be less than 0 in the 'ProblemModel' class.");
			}
			if (time[0] > Double.MAX_VALUE - 2E292) {
				throw new IllegalArgumentException(
						"The 'time' attribute cannot be too large in the 'ProblemModel' class.");
			}
		}
		if (time.length == 2) {
			if (time[0] == 0.0) {
				throw new IllegalArgumentException("The 'time' attribute cannot be zero in the 'ProblemModel' class.");
			}
			if (time[0] < 0.0 || time[1] < 0.0) {
				throw new IllegalArgumentException(
						"The 'time' attribute cannot be less than 0 in the 'ProblemModel' class.");
			}
			if (time[0] > Double.MAX_VALUE - 2E292 || time[1] > Double.MAX_VALUE - 2E292) {
				throw new IllegalArgumentException(
						"The 'time' attribute cannot be too large in the 'ProblemModel' class.");
			}
		}
		this.time = time;
	}

	@Override
	public String getUnitOfTime() {
		return unitOfTime;
	}

	@Override
	public void setUnitOfTime(String unitOfTime) throws IllegalArgumentException {
		if (unitOfTime.equals("ms") || unitOfTime.equals("s") || unitOfTime.equals("min") || unitOfTime.equals("h")
				|| unitOfTime.equals("d")) {
			this.unitOfTime = unitOfTime;
		} else {
			throw new IllegalArgumentException("An invalid time unit was passed. Valid time units are "
					+ "'ms' (milliseconds), 's' (seconds), 'min' (minutes), 'h' (hours), and 'd' (days).");
		}
	}

	@Override
	public Jungle getJungle() {
		return jungle;
	}

	@Override
	public void setJungle(Jungle jungle) {
		this.jungle = jungle;
	}

	@Override
	public List<SnakeType> getSnakeTypes() {
		return snakeTypes;
	}

	@Override
	public void setSnakeTypes(List<SnakeType> snakeTypes) {
		this.snakeTypes = snakeTypes;
	}

	@Override
	public void addSnakeType(SnakeType inTypes) {
		this.snakeTypes.add(inTypes);
	}

	@Override
	public Solution getSolution() {
		return solution;
	}

	@Override
	public void setSolution(Solution solution) {
		this.solution = solution;
	}
}