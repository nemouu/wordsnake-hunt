package org.snakehunt.algorithm;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.snakehunt.ioprocessing.IReader;
import org.snakehunt.ioprocessing.ReaderXML;
import org.snakehunt.main.SnakeHunt;
import org.snakehunt.main.SnakeHuntAPI;
import org.snakehunt.main.SnakeHuntAPI.ErrorType;

/**
 * The methods of the SolutionExaminer class are tested in the
 * SolutionExaminerTest class. Here, a few simple tests are first carried out,
 * which are intended to cover a few important edge situations. In particular,
 * the absence of a solution in the model or the situation where no errors are
 * found is investigated. For error detection, the given faulty files
 * sh_pX_solution_error.xml are used. Note: All classes in the algorithm
 * package are also tested again by the tests of the main component.
 * 
 * @author Philip Redecker
 */
class SolutionExaminerTest {

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Simple_Tests {
		@DisplayName("Simple test for SolutionExaminer, part 1.")
		@Test
		void testExamineSolutionExampleOne() {
			SnakeHuntAPI hunt = new SnakeHunt();
			List<ErrorType> error = new ArrayList<ErrorType>();
			error.add(ErrorType.ELEMENTS);
			error.add(ErrorType.ELEMENTS);
			assertEquals(error, hunt.examineSolution("res/sh_p11_solution_error.xml"),
					"\nThe solution check does not find the expected errors.");
		}

		@DisplayName("Simple test for SolutionExaminer, part 2.")
		@Test
		void testExamineSolutionExampleTwo() {
			SnakeHuntAPI hunt = new SnakeHunt();
			List<ErrorType> error = new ArrayList<ErrorType>();
			error.add(ErrorType.USAGE);
			assertEquals(error, hunt.examineSolution("res/sh_p12_solution_error.xml"),
					"\nThe solution check does not find the expected errors.");
		}

		@DisplayName("Simple test for SolutionExaminer, part 3.")
		@Test
		void testExamineSolutionExampleThree() {
			SnakeHuntAPI hunt = new SnakeHunt();
			List<ErrorType> error = new ArrayList<ErrorType>();
			error.add(ErrorType.NEIGHBORHOOD);
			assertEquals(error, hunt.examineSolution("res/sh_p13_solution_error.xml"),
					"\nThe solution check does not find the expected errors.");
		}

		@DisplayName("Simple test for SolutionExaminer, part 4.")
		@Test
		void testExamineSolutionExampleFour() {
			SnakeHuntAPI hunt = new SnakeHunt();
			List<ErrorType> error = new ArrayList<ErrorType>();
			error.add(ErrorType.ASSIGNMENT);
			assertEquals(error, hunt.examineSolution("res/sh_p14_solution_error.xml"),
					"\nThe solution check does not find the expected errors.");
		}

		@DisplayName("Simple test for SolutionExaminer when there are no errors in the solution.")
		@Test
		void testExamineSolutionExampleNoErrors() {
			SnakeHuntAPI hunt = new SnakeHunt();
			List<ErrorType> error = new ArrayList<ErrorType>();
			assertEquals(error, hunt.examineSolution("res/sh_p11_solution.xml"),
					"\nThe solution check does not find the expected errors.");
		}

		@DisplayName("Simple test for SolutionExaminer when there is no solution.")
		@Test
		void testExamineSolutionExampleNoSolution() {
			SnakeHuntAPI hunt = new SnakeHunt();
			List<ErrorType> error = new ArrayList<ErrorType>();
			assertEquals(error, hunt.examineSolution("res/sh_p11_problem_instance.xml"),
					"\nThe solution check does not find the expected errors.");
		}

		@DisplayName("Simple test for SolutionExaminer when there is no solution, with exception.")
		@Test
		void testExamineSolutionExampleNoSolutionException() throws Exception {
			IReader readerXML = new ReaderXML();
			readerXML.readFile("res/sh_p11_problem_instance.xml");
			assertThrows(IllegalArgumentException.class, () -> new SolutionExaminer(readerXML.getTransferredModel()),
					"\nA model without a solution is passed, but no exception is triggered.");
		}
	}
}
