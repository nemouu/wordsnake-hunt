package org.snakehunt.model;

import java.util.*;

/**
 * An interface for creating a link between the data model and neighborhood
 * relations, which are determining how snake segments are arranged in the
 * jungle of the model. This allows future expansion of the program with a
 * different form of neighborhood if desired.
 * 
 * @author Philip Redecker
 *
 */
public interface INeighborhood {

	/**
	 * Returns the type of neighborhood as a String. The String returned here
	 * describes the neighborhood and ideally should also be found in the class name
	 * of the implementing class.
	 * 
	 * @return The value of a variable <code>type</code> that describes the type of
	 *         neighborhood.
	 */
	String getType();

	/**
	 * Returns the parameters of the respective implementation. Depending on the
	 * implementation, this can be a single number or multiple numbers, which is why
	 * a list was chosen as the data structure. The parameters of the neighborhood
	 * affect the actually available neighbors of a field, which is why access to
	 * them is provided.
	 * 
	 * @return A list of parameters of the implemented neighborhood class.
	 */
	List<Integer> getParameters();

	/**
	 * It is possible to change the parameters of the implementing class through
	 * this method. This is primarily intended for testing the respective classes or
	 * for future program changes.
	 * 
	 * @param parameters A list of one or more parameters to be passed to the
	 *                   implementing class.
	 */
	void setParameters(List<Integer> parameters);

	/**
	 * This method determines the neighbors of a field in a given jungle. Generally,
	 * the parameters that have been passed to the implementing class are used for
	 * the search.
	 * 
	 * @param jungle The jungle in which to search for the neighbors.
	 * @param field  The field for which neighbors are to be determined.
	 * @return A list of neighbors of the input field located in the input jungle.
	 */
	List<Field> getNeighbors(Jungle jungle, Field field);
}