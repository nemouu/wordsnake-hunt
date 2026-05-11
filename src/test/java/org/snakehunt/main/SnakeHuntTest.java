package org.snakehunt.main;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.ValueSource;

import org.snakehunt.main.SnakeHuntAPI.ErrorType;

import java.util.*;

/**
 * In the SnakeHuntTest class, the methods of the SnakeHunt class are tested.
 * Parameterized tests are used where possible. For this purpose, the provided
 * files are used. Depending on the method being tested, the
 * <code>sh_pX_solution.xml</code>, <code>sh_pX_problem_instance.xml</code>, or
 * <code>sh_pX_incomplete.xml</code> files are used. If parameterized tests
 * are not possible due to the method structure, simple tests are used.
 * 
 * @author Philip Redecker
 */
class SnakeHuntTest {
	private static SnakeHuntAPI hunt;
	static int count = 1;

	@BeforeAll
	static void createHunt() {
		hunt = new SnakeHunt();
	}

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Simple_Tests {
		@DisplayName("Simple test for solveProblem.")
		@Test
		void testSolveProblemSimple() {
			assertTrue(hunt.solveProblem("res/sh_p1_problem_instance.xml", "res/sh_p1_problem_instance_simple_test.xml"),
					"\nThe problem instance p1 could not be solved, although a solution was expected.");
		}

		@DisplayName("Simple test for solveProblem with an incomplete jungle.")
		@Test
		void testSolveProblemSimpleIncomplete() {
			assertFalse(hunt.solveProblem("res/sh_p1_incomplete.xml", "res/sh_p1_incomplete_simple_test.xml"),
					"\nThe problem instance p1 is somehow processed, even though the jungle in the read file is empty.");
		}

		@DisplayName("Simple test for solveProblem that throws an exception.")
		@Test
		void testSolveProblemSimpleException() {
			assertFalse(hunt.solveProblem("res/s_p1_incomplete.xml", "res/sh_p1_incomplete_simple_test.xml"),
					"\nThe problem instance p1 is somehow processed, even though the file to be read does not exist.");
		}

		@DisplayName("Simple test for solveProblem, the output file has the wrong format.")
		@Test
		void testSolveProblemSimpleOutputNotExistent() {
			assertFalse(hunt.solveProblem("res/sh_p1_incomplete.xml", "res/sh_p1_incomplete_simple_test.l"),
					"\nThe problem instance p1 is somehow processed, even though the specified output file has the wrong format.");
		}

		@DisplayName("Simple test for generateProblem.")
		@Test
		void testGenerateProblemSimple() {
			assertTrue(
					hunt.generateProblem("res/sh_p1_incomplete.xml", "res/sh_p1_incomplete_simple_test.xml"),
					"\nNo jungle was generated, although the read file contains enough information to generate a suitable jungle.");
		}

		@DisplayName("Simple test for generateProblem with an error in execution.")
		@Test
		void testGenerateProblemError() {
			assertFalse(hunt.generateProblem("res/sh_p1_complete.xml", "res/sh_p1_incomplete_simple_test.xml"),
					"\nA jungle is generated, even though the read file actually does not contain enough information.");
		}

		@DisplayName("Simple test for generateProblem, the output file has the wrong format.")
		@Test
		void testGenerateProblemSimpleOuputNotExistent() {
			assertFalse(hunt.generateProblem("res/s_p1_incomplete.xml", "res/sh_p1_incomplete_simple_test.x"),
					"\nThe problem instance p1 is somehow processed, even though the specified output file has the wrong format.");
		}

		@DisplayName("Simple test for examineSolution with a solution without errors.")
		@Test
		void testExamineSolutionWithoutErrors() {
			List<ErrorType> error = new ArrayList<ErrorType>();
			assertIterableEquals(error, hunt.examineSolution("res/sh_p1_solution.xml"),
					"\nThe examination found errors, although the given solution contains no errors.");
		}

		@DisplayName("Simple test for examineSolution with solution, part 1.")
		@Test
		void testExamineSolutionOne() {
			List<ErrorType> error = new ArrayList<ErrorType>();
			error.add(ErrorType.ELEMENTS);
			error.add(ErrorType.ELEMENTS);
			assertIterableEquals(error, hunt.examineSolution("res/sh_p11_solution_error.xml"),
					"\nThe examination found no errors or not the correct ones, although the given solution contains errors.");
		}

		@DisplayName("Simple test for examineSolution with solution, part 2.")
		@Test
		void testExamineSolutionTwo() {
			List<ErrorType> error = new ArrayList<ErrorType>();
			error.add(ErrorType.USAGE);
			assertIterableEquals(error, hunt.examineSolution("res/sh_p12_solution_error.xml"),
					"\nThe examination found no errors or not the correct ones, although the given solution contains errors.");
		}

		@DisplayName("Simple test for examineSolution with solution, part 3.")
		@Test
		void testExamineSolutionThree() {
			List<ErrorType> error = new ArrayList<ErrorType>();
			error.add(ErrorType.NEIGHBORHOOD);
			assertIterableEquals(error, hunt.examineSolution("res/sh_p13_solution_error.xml"),
					"\nThe examination found no errors or not the correct ones, although the given solution contains errors.");
		}

		@DisplayName("Simple test for examineSolution with solution, part 4.")
		@Test
		void testExamineSolutionFour() {
			List<ErrorType> error = new ArrayList<ErrorType>();
			error.add(ErrorType.ASSIGNMENT);
			assertIterableEquals(error, hunt.examineSolution("res/sh_p14_solution_error.xml"),
					"\nThe examination found no errors or not the correct ones, although the given solution contains errors.");
		}

		@DisplayName("Simple test for examineSolution with solution, part 4.")
		@Test
		void testExamineSolutionWrongInputFile() {
			List<ErrorType> error = new ArrayList<ErrorType>();
			assertIterableEquals(error, hunt.examineSolution("res/sh_p1esung_error.xml"),
					"\nThe examination found errors, although the given input file does not exist.");
		}
	}

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Parameterized_Tests {
		@DisplayName("Parameterized test for solveProblem with given problem instances p1 - p15.")
		@ParameterizedTest
		@ValueSource(ints = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 })
		void testSolveProblemWithActualProblemsFromFiles(int number) {
			assertTrue(
					hunt.solveProblem("res/sh_p" + number + "_problem_instance.xml",
							"res/sh_p" + number + "_problem_instance_test.xml"),
					"\nThe problem instance p" + number
							+ " could not be solved, although a solution was expected.");
		}

		@DisplayName("Parameterized test for solveProblem with given incomplete problem instances p1 - p15.")
		@ParameterizedTest
		@ValueSource(ints = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 })
		void testSolveProblemWithoutAJungleInTheModel(int number) {
			assertFalse(
					hunt.solveProblem("res/sh_p" + number + "_incomplete.xml",
							"res/sh_p" + number + "_incomplete_test.xml"),
					"\nThe problem instance p" + number
							+ " is somehow processed, even though the jungle in the read file is empty.");
		}

		@DisplayName("Parameterized test for evaluateSolution with given solutions p1 - p15.")
		@ParameterizedTest
		@ValueSource(ints = { 8, 36, 81, 41, 69, 635, 198, 305, 362, 206, 450, 246, 967, 8734, 23642 })
		void testEvaluateProblemWithActualSolutionsFromFiles(int result) {
			assertEquals(result, hunt.evaluateSolution("res/sh_p" + count + "_solution.xml"),
					"\nThe evaluation of the solution in the file 'res/sh_p" + count
							+ "_solution.xml' does not correspond to the expected value " + result + ".");
			count++;
		}
	}
}

