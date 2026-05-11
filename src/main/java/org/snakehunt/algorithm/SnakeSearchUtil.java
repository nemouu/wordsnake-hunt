package org.snakehunt.algorithm;

import java.util.*;

import org.snakehunt.model.*;

/**
 * A class SnakeSearchUtil that implements the ISnakeSearchUtil interface and
 * thereby implements the methods specified in ISnakeSearchUtil. This class
 * offers various functions that aim to simplify and accelerate the snake search
 * in the SnakeSearch class. By implementing these functions through an
 * interface, the functionality remains adaptable.
 * 
 * @author Philip Redecker
 *
 */
public class SnakeSearchUtil implements ISnakeSearchUtil {
	private IModel model;
	private Double averagePointsAllTypes;
	private int pointsOfMostValuableType;

	/**
	 * A parameterized constructor that directly accepts the model in which the
	 * search is to be conducted. Under no circumstances will an exception occur
	 * here, as exceptions are handled in the SnakeSearch class.
	 * 
	 * @param model The model to be passed to the utility class.
	 */
	public SnakeSearchUtil(IModel model) {
		super();
		this.model = model;
		this.averagePointsAllTypes = getAveragePointsAllTypes(model.getSnakeTypes());
		this.pointsOfMostValuableType = getPointsOfMostValuableSnakeType(model.getSnakeTypes());
	}

	/**
	 * A parameterless constructor, allowing this class to be used for future
	 * program changes, for example, for testing.
	 */
	public SnakeSearchUtil() {
		super();
	}

	@Override
	public List<SnakeType> createValidSnakeTypes() {
		/*
		 * List all snake types in the model and compare them using a comparator to
		 * ensure that the snake type that is expected to earn the most points is at the
		 * top of the list. Details about the SnakeTypeComparator can be found below in
		 * this class.
		 */
		List<SnakeType> validSnakeTypes = new ArrayList<SnakeType>();
		for (SnakeType snakeType : model.getSnakeTypes()) {
			validSnakeTypes.add(snakeType);
		}
		validSnakeTypes.sort(new SnakeTypeComp());
		return validSnakeTypes;
	}

	@Override
	public List<Field> createValidStartingFields(SnakeType type) {
		/*
		 * List all starting fields for a specific snake type. Only fields that have the
		 * same character as the first character of the snake type's string and have a
		 * usage greater than 0 are added. The fields are then sorted using a
		 * FieldComparator. Details about the FieldComparator can be found below in this
		 * class.
		 */
		List<Field> validStartingFields = new ArrayList<Field>();
		for (int i = 0; i < model.getJungle().getFields().length; i++) {
			for (int j = 0; j < model.getJungle().getFields()[0].length; j++) {
				if (model.getJungle().getFields()[i][j].getCharacter().equals(type.getSigns().substring(0, 1))
						&& model.getJungle().getFields()[i][j].getUsage() > 0) {
					validStartingFields.add(model.getJungle().getFields()[i][j]);
				}
			}
		}
		validStartingFields.sort(new FieldComp(type));
		return validStartingFields;
	}

	@Override
	public List<Field> createValidNeighbors(SnakeElement previousElement, Snake currentSnake) {
		/*
		 * List possible neighbors for a snake and a snake element. Neighbors found
		 * using getNeighbors() and having a usage greater than 0 are added. Fields are
		 * then sorted using a FieldComparator. Details about the FieldComparator can be
		 * found below in this class.
		 */
		List<Field> validNeighborFields = new ArrayList<Field>();
		for (Field neighborField : currentSnake.getType().getStructure().getNeighbors(model.getJungle(),
				previousElement.getField())) {
			if (neighborField.getCharacter().equals(currentSnake.characterOfNextElement())
					&& neighborField.getUsage() > 0) {
				validNeighborFields.add(neighborField);
			}
		}
		validNeighborFields.sort(new FieldComp(currentSnake.getType()));
		return validNeighborFields;
	}

	@Override
	public IModel getModel() {
		return model;
	}

	@Override
	public void setModel(IModel model) {
		this.model = model;
	}

	private Double getProbablePointsOfOneType(SnakeType type) {
		/*
		 * A helper function for the SnakeTypeComparator. It calculates the points that
		 * a snake of this type is likely to earn in the current jungle. It includes all
		 * jungle fields that have a character that appears in the snake type's string.
		 */
		int pointsTotal = 0;
		int numberOfFields = 0;
		Double points = 0.0;
		for (int i = 0; i < model.getJungle().getFields().length; i++) {
			for (int j = 0; j < model.getJungle().getFields()[0].length; j++) {
				if (model.getJungle().getFields()[i][j].getUsage() > 0
						&& type.getSigns().contains(model.getJungle().getFields()[i][j].getCharacter())) {
					pointsTotal += model.getJungle().getFields()[i][j].getPoints();
					numberOfFields++;
				}
			}
		}
		if (numberOfFields > 0) {
			points = ((double) pointsTotal / numberOfFields);
		}
		return points;
	}

	private int getAmountNeighbors(SnakeType type, Jungle jungle, Field field) {
		/*
		 * A helper function for the FieldComparator. It returns the number of neighbors
		 * with usage greater than 0 for a snake type, a jungle, and a (current) field.
		 */
		int amount = 0;
		for (Field neighborField : type.getStructure().getNeighbors(jungle, field)) {
			if (neighborField.getUsage() > 0) {
				amount++;
			}
		}
		return amount;
	}

	private Double getAveragePointsAllTypes(List<SnakeType> types) {
		/*
		 * A helper function for the FieldComparator. It calculates the average points
		 * of a snake type in a problem instance.
		 */
		int points = 0;
		for (SnakeType snakeType : types) {
			points += snakeType.getPoints();
		}
		if (types.size() == 0) {
			return 0.0;
		} else {
			Double pointsTotal = (double) (points / types.size());
			return pointsTotal;
		}
	}

	private int getPointsOfMostValuableSnakeType(List<SnakeType> types) {
		/*
		 * A helper function for the FieldComparator. It returns the points of the snake
		 * type with the most points in a problem instance.
		 */
		int points = 0;
		for (SnakeType snakeType : types) {
			if (snakeType.getPoints() > points) {
				points = snakeType.getPoints();
			}
		}
		return points;
	}

	/**
	 * A custom Comparator class that implements the Comparator class from
	 * java.util. It aims to compare the actual points of individual snake types
	 * more effectively. It considers the length of the respective snake and the
	 * average points of the fields that are eligible for the snake.
	 * 
	 * @author Philip Redecker
	 *
	 */
	private class SnakeTypeComp implements Comparator<SnakeType> {

		@Override
		public int compare(SnakeType type1, SnakeType type2) {
			if (((type1.getSigns().length() * getProbablePointsOfOneType(type1))
					+ type1.getPoints()) > ((type2.getSigns().length() * getProbablePointsOfOneType(type2))
							+ type2.getPoints())) {
				return -1;
			} else {
				return 0;
			}
		}
	}

	/**
	 * A custom Comparator class that implements the Comparator class from
	 * java.util. This class allows sorting based on three different criteria. If a
	 * problem instance has snakes that earn significantly more points compared to
	 * other snakes of the same problem, it sorts according to the MRV (Most
	 * Remaining Value) strategy. This means that the paths that are likely to
	 * result in an early termination are traversed first, thus allowing the search
	 * to continue as quickly as possible. If the points of all snakes are rather
	 * similar, it sorts based on the number of free neighboring fields. This
	 * ensures, for example, that fields in the corner of a jungle are avoided.
	 * Finally, in both of these cases, when there is a tie, it simply sorts based
	 * on field points.
	 * 
	 * @author Philip Redecker
	 *
	 */
	private class FieldComp implements Comparator<Field> {
		private SnakeType type;

		public FieldComp(SnakeType type) {
			super();
			this.type = type;
		}

		@Override
		public int compare(Field field1, Field field2) {
			if (pointsOfMostValuableType - averagePointsAllTypes > 15.0) {
				if (((getAmountNeighbors(type, model.getJungle(), field1)) < (getAmountNeighbors(type,
						model.getJungle(), field2)))) {
					return -1;
				}
			} else {
				if (getAmountNeighbors(type, model.getJungle(), field1)
						- getAmountNeighbors(type, model.getJungle(), field2) > 4) {
					return -1;
				}
			}
			if (field1.getPoints() > field2.getPoints()) {
				return -1;
			} else {
				return 0;
			}
		}
	}
}