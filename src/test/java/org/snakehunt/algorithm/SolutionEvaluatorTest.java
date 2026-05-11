package org.snakehunt.algorithm;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import org.snakehunt.main.SnakeHunt;
import org.snakehunt.main.SnakeHuntAPI;

/**
 * The methods of the SolutionEvaluator class are tested in the
 * SolutionEvaluatorTest class. Here, a few simple tests are first carried out,
 * which are intended to cover a few important edge situations, especially the
 * situation where a passed model has no solution at all. Note: All classes in
 * the algorithm package are also tested again by the tests of the main
 * component.
 * 
 * @author Philip Redecker
 */
class SolutionEvaluatorTest {

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Simple_Tests {
		@DisplayName("Simple test for SolutionEvaluator, part 1.")
		@Test
		void testEvaluateSolutionExampleOne() {
			SnakeHuntAPI hunt = new SnakeHunt();
			assertEquals(8, hunt.evaluateSolution("res/sh_p1_solution.xml"),
					"\nThe solution check does not find the expected errors.");
		}

		@DisplayName("Simple test for SolutionEvaluator, part 2.")
		@Test
		void testEvaluateSolutionExampleTwo() {
			SnakeHuntAPI hunt = new SnakeHunt();
			assertEquals(36, hunt.evaluateSolution("res/sh_p2_solution.xml"),
					"\nThe solution check does not find the expected errors.");
		}

		@DisplayName("Simple test for SolutionEvaluator, part 3.")
		@Test
		void testEvaluateSolutionExampleThree() {
			SnakeHuntAPI hunt = new SnakeHunt();
			assertEquals(81, hunt.evaluateSolution("res/sh_p3_solution.xml"),
					"\nThe solution check does not find the expected errors.");
		}

		@DisplayName("Simple test for SolutionEvaluator, part 4.")
		@Test
		void testEvaluateSolutionExampleFour() {
			SnakeHuntAPI hunt = new SnakeHunt();
			assertEquals(41, hunt.evaluateSolution("res/sh_p4_solution.xml"),
					"\nThe solution check does not find the expected errors.");
		}

		@DisplayName("Simple test for SolutionEvaluator when no solution is present.")
		@Test
		void testEvaluateSolutionExampleNoErrors() {
			SnakeHuntAPI hunt = new SnakeHunt();
			assertEquals(0, hunt.evaluateSolution("res/sh_p4_problem_instance.xml"),
					"\nThe solution check does not find the expected errors.");
		}
	}
}

