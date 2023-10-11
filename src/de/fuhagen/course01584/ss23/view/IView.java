package de.fuhagen.course01584.ss23.view;

/**
 * An interface to establish an link between the data model and the view
 * component. This enables the program to be extended in the future with a
 * different form of view, if desired.
 * 
 * @author Philip Redecker
 *
 */
public interface IView {
	/**
	 * A method for displaying data found in the data model. It should be possible
	 * to display the model in all different situations the model can be in. View
	 * should be possible, for example, when a solution is available as well as when
	 * no solution is available.
	 */
	void view();
}