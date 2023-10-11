package de.fuhagen.course01584.ss23.algorithm;

import de.fuhagen.course01584.ss23.model.*;

/**
 * This class provides constructors and methods to enable the evaluation of
 * given solutions in the snake hunt. It counts the points of all snake types
 * and those of the fields occupied by the corresponding snakes. The user can
 * retrieve or print these results.
 * 
 * @author Philip Redecker
 *
 */
public class SolutionEvaluator {
	private int evaluation = 0;

	/**
	 * Creates a new instance of the SolutionEvaluator class.
	 */
	public SolutionEvaluator() {
		super();
	}

	/**
	 * A method to evaluate a given solution. Any solution can be passed. If this
	 * solution is empty, <code>0</code> is returned.
	 * 
	 * @param solution The solution to be evaluated.
	 * @return The points of the solution previously passed.
	 */
	public int evaluateSolution(Solution solution) {
		int points = 0;
		for (Snake snake : solution.getSnakes()) {
			points += snake.getType().getPoints();
			for (SnakeElement element : snake.getElements()) {
				points += element.getField().getPoints();
			}
		}
		evaluation = points;
		return points;
	}

	/**
	 * Outputs the evaluation of the solution to the console.
	 */
	public void printOutEvaluation() {
		System.out.println("The provided solution has " + evaluation + " points.");
	}

	/**
	 * This allows access to the evaluation of the last solution that was
	 * calculated. This is primarily intended for future program extension and/or
	 * testing.
	 * 
	 * @return The value of the variable <code>evaluation</code>.
	 */
	public int getEvaluation() {
		return evaluation;
	}
}