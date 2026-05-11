package org.snakehunt.model;

import java.util.*;

/**
 * An implementation of the INeighborhood interface that models a neighborhood
 * between fields that can also be very far apart. It is important to note that
 * in this implementation there are two parameters. It is always traversed
 * vertically and horizontally, meaning there are always <code>4</code> or
 * <code>8</code> neighbors.
 * 
 * @author Philip Redecker
 *
 */
public class JumpNeighborhood implements INeighborhood {
	private final String type = "Jump";
	private List<Integer> parameters = new ArrayList<Integer>();

	/**
	 * A constructor that directly accepts the parameters used in the class. When
	 * instantiating the class, it is directly known how far and where to look for
	 * neighbors.
	 * 
	 * @param parameters A list of numbers that determine where the neighboring
	 *                   fields of a field are located.
	 */
	public JumpNeighborhood(List<Integer> parameters) {
		super();
		if (parameters.size() != 2) {
			throw new IllegalArgumentException(
					"For the 'JumpNeighborhood' class, only a" + "list with two entries can be passed.");
		}
		if (parameters.get(0) < 0 || parameters.get(1) < 0) {
			throw new IllegalArgumentException(
					"For the 'JumpNeighborhood' class, only a" + "list with positive entries can be passed.");
		}
		if (parameters.get(0) > Integer.MAX_VALUE - 1 || parameters.get(1) > Integer.MAX_VALUE - 1) {
			throw new IllegalArgumentException("For the 'JumpNeighborhood' class, only a"
					+ "list with entries not greater than the largest integer value can be passed.");
		}
		if (parameters.get(0) == 0 && parameters.get(1) == 0) {
			throw new IllegalArgumentException(
					"For the 'JumpNeighborhood' class, a list" + "with both entries being 0 cannot be passed.");
		}
		this.parameters = parameters;
	}

	/**
	 * A parameterless constructor so that it is possible to use this class for
	 * testing, for example, in future changes to the program.
	 */
	public JumpNeighborhood() {
		super();
	}

	@Override
	public List<Field> getNeighbors(Jungle jungle, Field field) {
		/*
		 * In this method, the neighbors of a field in a jungle are found. All possible
		 * combinations with the parameters are tried, and it is always checked whether
		 * the respective neighbor is still within the jungle boundaries. Furthermore,
		 * it is considered that with certain parameters (for example, 0 and 2), some
		 * fields could appear multiple times in the list of neighbors. Therefore, it is
		 * always checked whether the field is already in the list of neighbors.
		 */
		List<Field> listOfNeighbors = new ArrayList<Field>();
		int param1 = parameters.get(0);
		int param2 = parameters.get(1);
		Field[][] jungleFields = jungle.getFields();
		if (field.getRow() + param1 < jungleFields.length && field.getColumn() + param2 < jungleFields[0].length) {
			if (!listOfNeighbors.contains(jungleFields[field.getRow() + param1][field.getColumn() + param2])) {
				listOfNeighbors.add(jungleFields[field.getRow() + param1][field.getColumn() + param2]);
			}
		}
		if (field.getRow() + param2 < jungleFields.length && field.getColumn() + param1 < jungleFields[0].length) {
			if (!listOfNeighbors.contains(jungleFields[field.getRow() + param2][field.getColumn() + param1])) {
				listOfNeighbors.add(jungleFields[field.getRow() + param2][field.getColumn() + param1]);
			}
		}
		if (field.getRow() - param1 >= 0 && field.getColumn() - param2 >= 0) {
			if (!listOfNeighbors.contains(jungleFields[field.getRow() - param1][field.getColumn() - param2])) {
				listOfNeighbors.add(jungleFields[field.getRow() - param1][field.getColumn() - param2]);
			}
		}
		if (field.getRow() - param2 >= 0 && field.getColumn() - param1 >= 0) {
			if (!listOfNeighbors.contains(jungleFields[field.getRow() - param2][field.getColumn() - param1])) {
				listOfNeighbors.add(jungleFields[field.getRow() - param2][field.getColumn() - param1]);
			}
		}
		if (field.getRow() - param1 >= 0 && field.getColumn() + param2 < jungleFields[0].length) {
			if (!listOfNeighbors.contains(jungleFields[field.getRow() - param1][field.getColumn() + param2])) {
				listOfNeighbors.add(jungleFields[field.getRow() - param1][field.getColumn() + param2]);
			}
		}
		if (field.getRow() + param1 < jungleFields.length && field.getColumn() - param2 >= 0) {
			if (!listOfNeighbors.contains(jungleFields[field.getRow() + param1][field.getColumn() - param2])) {
				listOfNeighbors.add(jungleFields[field.getRow() + param1][field.getColumn() - param2]);
			}
		}
		if (field.getRow() - param2 >= 0 && field.getColumn() + param1 < jungleFields[0].length) {
			if (!listOfNeighbors.contains(jungleFields[field.getRow() - param2][field.getColumn() + param1])) {
				listOfNeighbors.add(jungleFields[field.getRow() - param2][field.getColumn() + param1]);
			}
		}
		if (field.getRow() + param2 < jungleFields.length && field.getColumn() - param1 >= 0) {
			if (!listOfNeighbors.contains(jungleFields[field.getRow() + param2][field.getColumn() - param1])) {
				listOfNeighbors.add(jungleFields[field.getRow() + param2][field.getColumn() - param1]);
			}
		}
		return listOfNeighbors;
	}

	@Override
	public String toString() {
		return type + ", Parameters=(" + parameters.get(0) + ", " + parameters.get(1) + ")";
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public List<Integer> getParameters() {
		return parameters;
	}

	@Override
	public void setParameters(List<Integer> parameters) {
		if (parameters.size() != 2) {
			throw new IllegalArgumentException(
					"For the 'JumpNeighborhood' class, only a list with two entries can be passed.");
		}
		if (parameters.get(0) < 0 || parameters.get(1) < 0) {
			throw new IllegalArgumentException(
					"For the 'JumpNeighborhood' class, only a list with positive entries can be passed.");
		}
		if (parameters.get(0) > Integer.MAX_VALUE - 1 || parameters.get(1) > Integer.MAX_VALUE - 1) {
			throw new IllegalArgumentException("For the 'JumpNeighborhood' class, only a"
					+ "list with entries not greater than the largest integer value can be passed.");
		}
		this.parameters = parameters;
	}
}