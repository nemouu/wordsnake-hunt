package de.fuhagen.course01584.ss23.model;

import java.util.*;

/**
 * An implementation of the INeighborhood interface that describes a
 * neighborhood between fields arranged in a star shape in the jungle. The first
 * parameter determines the maximum distance in the horizontal and vertical
 * directions, and the second parameter determines the maximum distance in both
 * diagonal directions.
 * 
 * @author Philip Redecker
 *
 */
public class StarNeighborhood implements INeighborhood {
	private final String type = "Stern";
	private List<Integer> parameters = new ArrayList<Integer>();

	/**
	 * A constructor to directly pass the parameters used in the class. When
	 * instantiating the class, it is directly known how far and where to search for
	 * neighbors.
	 * 
	 * @param parameters A list of numbers that determine where the neighboring
	 *                   fields of a field are located.
	 */
	public StarNeighborhood(List<Integer> parameters) {
		super();
		if (parameters.size() != 2) {
			throw new IllegalArgumentException(
					"For the 'StarNeighborhood' class, only a list with two entries can be passed.");
		}
		if (parameters.get(0) < 0 || parameters.get(1) < 0) {
			throw new IllegalArgumentException(
					"For the 'StarNeighborhood' class, only a list with positive entries can be passed.");
		}
		if (parameters.get(0) > Integer.MAX_VALUE - 1 || parameters.get(1) > Integer.MAX_VALUE - 1) {
			throw new IllegalArgumentException("For the 'StarNeighborhood' class, only a "
					+ "list with entries not greater than the largest integer value can be passed.");
		}
		if (parameters.get(0) == 0 && parameters.get(1) == 0) {
			throw new IllegalArgumentException(
					"For the 'StarNeighborhood' class, a list with both entries equal to 0 cannot be passed.");
		}
		this.parameters = parameters;
	}

	/**
	 * A parameterless constructor, so that it is possible to use this class for
	 * testing, for example, in future program changes.
	 */
	public StarNeighborhood() {
		super();
	}

	@Override
	public List<Field> getNeighbors(Jungle jungle, Field field) {
		List<Field> listOfNeighbors = new ArrayList<Field>();
		int param1 = parameters.get(0);
		int param2 = parameters.get(1);
		Field[][] jungleFields = jungle.getFields();
		for (int i = 1; i <= param1; i++) {
			if (field.getRow() - i >= 0) {
				listOfNeighbors.add(jungleFields[field.getRow() - i][field.getColumn()]);
			}
			if (field.getRow() + i < jungleFields.length) {
				listOfNeighbors.add(jungleFields[field.getRow() + i][field.getColumn()]);
			}
			if (field.getColumn() - i >= 0) {
				listOfNeighbors.add(jungleFields[field.getRow()][field.getColumn() - i]);
			}
			if (field.getColumn() + i < jungleFields[0].length) {
				listOfNeighbors.add(jungleFields[field.getRow()][field.getColumn() + i]);
			}
		}
		for (int i = 1; i <= param2; i++) {
			if (field.getRow() - i >= 0 && field.getColumn() - i >= 0) {
				listOfNeighbors.add(jungleFields[field.getRow() - i][field.getColumn() - i]);
			}
			if (field.getRow() - i >= 0 && field.getColumn() + i < jungleFields[0].length) {
				listOfNeighbors.add(jungleFields[field.getRow() - i][field.getColumn() + i]);
			}
			if (field.getRow() + i < jungleFields.length && field.getColumn() - i >= 0) {
				listOfNeighbors.add(jungleFields[field.getRow() + i][field.getColumn() - i]);
			}
			if (field.getRow() + i < jungleFields.length && field.getColumn() + i < jungleFields[0].length) {
				listOfNeighbors.add(jungleFields[field.getRow() + i][field.getColumn() + i]);
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
					"For the 'StarNeighborhood' class, only a list with two entries can be passed.");
		}
		if (parameters.get(0) < 0 || parameters.get(1) < 0) {
			throw new IllegalArgumentException(
					"For the 'StarNeighborhood' class, only a list with positive entries can be passed.");
		}
		if (parameters.get(0) > Integer.MAX_VALUE - 1 || parameters.get(1) > Integer.MAX_VALUE - 1) {
			throw new IllegalArgumentException("For the 'StarNeighborhood' class, only a "
					+ "list with entries not greater than the largest integer value can be passed.");
		}
		this.parameters = parameters;
	}
}