package de.fuhagen.course01584.ss23.model;

import java.util.*;

/**
 * An implementation of the INeighborhood interface that describes a
 * neighborhood between directly adjacent fields. It considers all fields that
 * are above, below, next to, and diagonally adjacent to the origin field. The
 * number of neighbors to be added depends on the input parameter, i.e., the
 * distance.
 * 
 * @author Philip Redecker
 *
 */
public class DistanceNeighborhood implements INeighborhood {
	private final String type = "Distanz";
	private List<Integer> parameter = new ArrayList<Integer>();

	/**
	 * A constructor to directly pass the parameter used in the class. Upon
	 * instantiation of the class, it is known how far to search for neighbors.
	 * 
	 * @param parameter A number determining where the neighboring fields of a field
	 *                  are located.
	 */
	public DistanceNeighborhood(int parameter) {
		super();
		if (parameter < 1) {
			throw new IllegalArgumentException(
					"For the DistanceNeighborhood class, the 'parameters' attribute must not have elements that are negative.");
		}
		if (Integer.MAX_VALUE - 1 < parameter) {
			throw new IllegalArgumentException(
					"For the DistanceNeighborhood class, the 'parameters' attribute must not have elements larger than the largest integer value.");
		}
		this.parameter.add(parameter);
	}

	/**
	 * A parameterless constructor so that it is possible to use this class for
	 * testing, for example, in future changes to the program.
	 */
	public DistanceNeighborhood() {
		super();
	}

	@Override
	public List<Field> getNeighbors(Jungle jungle, Field field) {
		/*
		 * In this method, the neighbors of a field in a jungle are found. It considers
		 * all fields that are at the distance of the parameter from the origin field.
		 * It always checks if the respective neighbor is still within the boundaries of
		 * the jungle. Also, some fields may be considered multiple times, so it always
		 * checks whether a specific field is already in the list of neighbors.
		 */
		List<Field> listOfNeighbors = new ArrayList<Field>();
		int param = parameter.get(0);
		Field[][] jungleFields = jungle.getFields();
		for (int i = 0; i < param + 1; i++) {
			for (int j = 0; j < param + 1; j++) {
				if (i == 0 && j == 0) {
				} else {
					if (field.getRow() + i < jungleFields.length && field.getColumn() + j < jungleFields[0].length) {
						if (listOfNeighbors
								.contains(jungleFields[field.getRow() + i][field.getColumn() + j]) == false) {
							listOfNeighbors.add(jungleFields[field.getRow() + i][field.getColumn() + j]);
						}
					}
					if (field.getRow() - i >= 0 && field.getColumn() - j >= 0) {
						if (listOfNeighbors
								.contains(jungleFields[field.getRow() - i][field.getColumn() - j]) == false) {
							listOfNeighbors.add(jungleFields[field.getRow() - i][field.getColumn() - j]);
						}
					}
					if (field.getRow() + i < jungleFields.length && field.getColumn() - j >= 0) {
						if (listOfNeighbors
								.contains(jungleFields[field.getRow() + i][field.getColumn() - j]) == false) {
							listOfNeighbors.add(jungleFields[field.getRow() + i][field.getColumn() - j]);
						}
					}
					if (field.getRow() - i >= 0 && field.getColumn() + j < jungleFields[0].length) {
						if (listOfNeighbors
								.contains(jungleFields[field.getRow() - i][field.getColumn() + j]) == false) {
							listOfNeighbors.add(jungleFields[field.getRow() - i][field.getColumn() + j]);
						}
					}
				}
			}
		}
		return listOfNeighbors;
	}

	@Override
	public String toString() {
		return type + ", Parameter=" + parameter.get(0);
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public List<Integer> getParameters() {
		return parameter;
	}

	@Override
	public void setParameters(List<Integer> parameters) {
		if (parameters.size() > 1) {
			throw new IllegalArgumentException("For the DistanceNeighborhood class, the 'parameters' "
					+ "attribute cannot have more than one element.");
		}
		if (parameters.get(0) < 0) {
			throw new IllegalArgumentException("For the DistanceNeighborhood class, the 'parameters' "
					+ "attribute must not have elements that are negative.");
		}
		if (Integer.MAX_VALUE - 1 < parameters.get(0)) {
			throw new IllegalArgumentException("For the DistanceNeighborhood class, the 'parameters' "
					+ "attribute must not have elements larger than the largest integer value.");
		}
		this.parameter = parameters;
	}

	/**
	 * A method to set a single parameter. This is especially useful in this
	 * implementation of the interface since only one parameter is needed. The
	 * method is mainly used to test the class and its other methods.
	 * 
	 * @param parameter A number to be passed as a parameter.
	 */
	public void setParameter(int parameter) {
		if (parameter < 1) {
			throw new IllegalArgumentException("For the DistanceNeighborhood class, the 'parameters' "
					+ "attribute must not have elements that are negative.");
		}
		if (Integer.MAX_VALUE - 1 < parameter) {
			throw new IllegalArgumentException("For the DistanceNeighborhood class, the 'parameters' "
					+ "attribute must not have elements that are larger than the largest integer value.");
		}
		this.parameter.set(0, parameter);
	}
}