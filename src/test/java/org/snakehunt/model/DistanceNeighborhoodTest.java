package org.snakehunt.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * In the DistanceNeighborhoodTest class, the methods of the
 * DistanceNeighborhood class are tested. First, a few simple tests are
 * performed to cover some important edge situations. Then there is a series of
 * parameterized tests that test the methods in a normal way. The getNeighbors
 * method is tested in its own test classes, both in a normal situation and in
 * edge situations, and with different parameters. To be able to verify
 * everything by hand, the jungle generated for the test is printed to the
 * console.
 * 
 * @author Philip Redecker
 */
class DistanceNeighborhoodTest {

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Simple_Tests {
		@DisplayName("Simple positive test for setParameters.")
		@Test
		void testSetParameters() {
			int param = 5;
			INeighborhood distanceNeighborhood = new DistanceNeighborhood(4);
			List<Integer> paramList = new ArrayList<Integer>();
			paramList.add(param);
			distanceNeighborhood.setParameters(paramList);
			assertEquals(5, distanceNeighborhood.getParameters().get(0), "\nThe parameter '" + distanceNeighborhood.getParameters().get(0)
					+ "' does not correspond to the specified value '" + param + "'.");
		}

		@DisplayName("Simple test for setParameters with too large values in the list.")
		@Test
		void testSetParametersLargeEntries() {
			int param = Integer.MAX_VALUE;
			List<Integer> paramList = new ArrayList<Integer>();
			paramList.add(param);
			assertThrows(IllegalArgumentException.class, () -> new DistanceNeighborhood(4).setParameters(paramList),
					() -> "No exception is generated for the too large parameter value '" + param + "'.");
		}

		@DisplayName("Simple test for setParameter with too large value.")
		@Test
		void testSetParameterLargeEntry() {
			int param = Integer.MAX_VALUE;
			assertThrows(IllegalArgumentException.class, () -> new DistanceNeighborhood(4).setParameter(param),
					() -> "No exception is generated for the too large parameter value '" + param + "'.");
		}
	}

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Simple_tests_for_method_getNeighbors_with_parameter_1 {
		static Jungle exampleJungle;
		static Random randomChar;
		static INeighborhood exampleNeighborhood;

		@BeforeAll
		static void initJungle() {
			randomChar = new Random(1);
			exampleJungle = new Jungle(6, 8, "asdertg", 1);
			for (int i = 0; i < exampleJungle.getRows(); i++) {
				for (int j = 0; j < exampleJungle.getColumns(); j++) {
					exampleJungle.getFields()[i][j].setCharacter("" + (char) (randomChar.nextInt(26) + 'a'));
				}
			}
			exampleNeighborhood = new DistanceNeighborhood(1);

			// The generated jungle is printed to the console so that a manual check
			// can also be performed.
			System.out.println();
			System.out.println(
					"The jungle is printed so that a manual check is also possible:");
			System.out.println();
			System.out.println(exampleJungle.toString());
		}

		@DisplayName("Test for getNeighbors with parameter 1 in the middle.")
		@Test
		void testNeighborsWithParameterOneMiddle() {
			INeighborhood distanceNeighborhood = new DistanceNeighborhood(1);
			Field fieldToBeTested = exampleJungle.getFields()[4][5];
			HashSet<Field> neighborFields = new HashSet<Field>();
			neighborFields.add(exampleJungle.getFields()[3][4]);
			neighborFields.add(exampleJungle.getFields()[3][5]);
			neighborFields.add(exampleJungle.getFields()[3][6]);
			neighborFields.add(exampleJungle.getFields()[4][4]);
			neighborFields.add(exampleJungle.getFields()[4][6]);
			neighborFields.add(exampleJungle.getFields()[5][4]);
			neighborFields.add(exampleJungle.getFields()[5][5]);
			neighborFields.add(exampleJungle.getFields()[5][6]);
			assertEquals(neighborFields.size(), distanceNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size(),
					"\nThe obtained list has the size "
							+ distanceNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size()
							+ " but a list of size " + neighborFields.size() + " was expected.");
			assertTrue(neighborFields.containsAll(distanceNeighborhood.getNeighbors(exampleJungle, fieldToBeTested)),
					"\nThe obtained list does not contain all or any elements of the expected list.");
			assertTrue(distanceNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).containsAll(neighborFields),
					"\nThe obtained list does not contain all or any elements of the expected list.");
		}

		@DisplayName("Test for getNeighbors with parameter 1 at the top-left corner.")
		@Test
		void testNeighborsWithParameterOneTopLeftCorner() {
			Field fieldToBeTested = exampleJungle.getFields()[0][0];
			HashSet<Field> neighborFields = new HashSet<Field>();
			neighborFields.add(exampleJungle.getFields()[0][1]);
			neighborFields.add(exampleJungle.getFields()[1][0]);
			neighborFields.add(exampleJungle.getFields()[1][1]);
			assertEquals(neighborFields.size(), exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size(),
					"\nThe obtained list has the size "
							+ exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size()
							+ " but a list of size " + neighborFields.size() + " was expected.");
			assertTrue(neighborFields.containsAll(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested)),
					"\nThe obtained list does not contain all or any elements of the expected list.");
			assertTrue(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).containsAll(neighborFields),
					"\nThe obtained list does not contain all or any elements of the expected list.");
		}

		@DisplayName("Test for getNeighbors with parameter 1 at the top-right corner.")

		@Test
		void testNeighborsWithParameterOneTopRightCorner() {
			Field fieldToBeTested = exampleJungle.getFields()[0][7];
			HashSet<Field> neighborFields = new HashSet<Field>();
			neighborFields.add(exampleJungle.getFields()[0][6]);
			neighborFields.add(exampleJungle.getFields()[1][6]);
			neighborFields.add(exampleJungle.getFields()[1][7]);
			assertEquals(neighborFields.size(), exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size(),
					"\nThe obtained list has the size "
							+ exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size()
							+ " but a list of size " + neighborFields.size() + " was expected.");
			assertTrue(neighborFields.containsAll(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested)),
					"\nThe obtained list does not contain all or any elements of the expected list.");
			assertTrue(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).containsAll(neighborFields),
					"\nThe obtained list does not contain all or any elements of the expected list.");
		}

		@DisplayName("Test for getNeighbors with parameter 1 at the bottom-left corner.")

		@Test
		void testNeighborsWithParameterOneBottomLeftCorner() {
			Field fieldToBeTested = exampleJungle.getFields()[5][0];
			HashSet<Field> neighborFields = new HashSet<Field>();
			neighborFields.add(exampleJungle.getFields()[4][0]);
			neighborFields.add(exampleJungle.getFields()[4][1]);
			neighborFields.add(exampleJungle.getFields()[5][1]);
			assertEquals(neighborFields.size(), exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size(),
					"\nThe obtained list has the size "
							+ exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size()
							+ " but a list of size " + neighborFields.size() + " was expected.");
			assertTrue(neighborFields.containsAll(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested)),
					"\nThe obtained list does not contain all or any elements of the expected list.");
			assertTrue(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).containsAll(neighborFields),
					"\nThe obtained list does not contain all or any elements of the expected list.");
		}

		@DisplayName("Test for getNeighbors with parameter 1 at the bottom-right corner.")

		@Test
		void testNeighborsWithParameterOneBottomRightCorner() {
			Field fieldToBeTested = exampleJungle.getFields()[5][7];
			HashSet<Field> neighborFields = new HashSet<Field>();
			neighborFields.add(exampleJungle.getFields()[5][6]);
			neighborFields.add(exampleJungle.getFields()[4][6]);
			neighborFields.add(exampleJungle.getFields()[4][7]);
			assertEquals(neighborFields.size(), exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size(),
					"\nThe obtained list has the size "
							+ exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size()
							+ " but a list of size " + neighborFields.size() + " was expected.");
			assertTrue(neighborFields.containsAll(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested)),
					"\nThe obtained list does not contain all or any elements of the expected list.");
			assertTrue(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).containsAll(neighborFields),
					"\nThe obtained list does not contain all or any elements of the expected list.");
		}
	}

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Simple_tests_for_method_getNeighbors_with_parameter_2 {
		static Jungle exampleJungle;
		static Random randomChar;
		static INeighborhood exampleNeighborhood;

		@BeforeAll
		static void initJungle() {
			randomChar = new Random(1);
			exampleJungle = new Jungle(6, 8, "asdertg", 1);
			for (int i = 0; i < exampleJungle.getRows(); i++) {
				for (int j = 0; j < exampleJungle.getColumns(); j++) {
					exampleJungle.getFields()[i][j].setCharacter("" + (char) (randomChar.nextInt(26) + 'a'));
				}
			}
			exampleNeighborhood = new DistanceNeighborhood(2);
		}

		@DisplayName("Test for getNeighbors with parameter 2 in the middle.")
		@Test
		void testNeighborsWithParameterTwoMiddle() {
			Field fieldToBeTested = exampleJungle.getFields()[3][3];
			HashSet<Field> neighborFields = new HashSet<Field>();
			neighborFields.add(exampleJungle.getFields()[1][1]);
			neighborFields.add(exampleJungle.getFields()[1][2]);
			neighborFields.add(exampleJungle.getFields()[1][3]);
			neighborFields.add(exampleJungle.getFields()[1][4]);
			neighborFields.add(exampleJungle.getFields()[1][5]);
			neighborFields.add(exampleJungle.getFields()[2][1]);
			neighborFields.add(exampleJungle.getFields()[2][2]);
			neighborFields.add(exampleJungle.getFields()[2][3]);
			neighborFields.add(exampleJungle.getFields()[2][4]);
			neighborFields.add(exampleJungle.getFields()[2][5]);
			neighborFields.add(exampleJungle.getFields()[3][1]);
			neighborFields.add(exampleJungle.getFields()[3][2]);
			neighborFields.add(exampleJungle.getFields()[3][4]);
			neighborFields.add(exampleJungle.getFields()[3][5]);
			neighborFields.add(exampleJungle.getFields()[4][1]);
			neighborFields.add(exampleJungle.getFields()[4][2]);
			neighborFields.add(exampleJungle.getFields()[4][3]);
			neighborFields.add(exampleJungle.getFields()[4][4]);
			neighborFields.add(exampleJungle.getFields()[4][5]);
			neighborFields.add(exampleJungle.getFields()[5][1]);
			neighborFields.add(exampleJungle.getFields()[5][2]);
			neighborFields.add(exampleJungle.getFields()[5][3]);
			neighborFields.add(exampleJungle.getFields()[5][4]);
			neighborFields.add(exampleJungle.getFields()[5][5]);
			assertEquals(neighborFields.size(), exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size(),
					"\nThe obtained list has the size "
							+ exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size()
							+ " but a list of size " + neighborFields.size() + " was expected.");
			assertTrue(neighborFields.containsAll(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested)),
					"\nThe obtained list does not contain all or any elements of the expected list.");
			assertTrue(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).containsAll(neighborFields),
					"\nThe obtained list does not contain all or any elements of the expected list.");
		}

		@DisplayName("Test for getNeighbors with parameter 2 at the top-left corner.")
		@Test
		void testNeighborsWithParameterTwoTopLeftCorner() {
			Field fieldToBeTested = exampleJungle.getFields()[0][0];
			HashSet<Field> neighborFields = new HashSet<Field>();
			neighborFields.add(exampleJungle.getFields()[0][1]);
			neighborFields.add(exampleJungle.getFields()[0][2]);
			neighborFields.add(exampleJungle.getFields()[1][0]);
			neighborFields.add(exampleJungle.getFields()[1][1]);
			neighborFields.add(exampleJungle.getFields()[1][2]);
			neighborFields.add(exampleJungle.getFields()[2][0]);
			neighborFields.add(exampleJungle.getFields()[2][1]);
			neighborFields.add(exampleJungle.getFields()[2][2]);
			assertEquals(neighborFields.size(), exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size(),
					"\nThe obtained list has the size "
							+ exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size()
							+ " but a list of size " + neighborFields.size() + " was expected.");
			assertTrue(neighborFields.containsAll(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested)),
					"\nThe obtained list does not contain all or any elements of the expected list.");
			assertTrue(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).containsAll(neighborFields),
					"\nThe obtained list does not contain all or any elements of the expected list.");
		}

		@DisplayName("Test for getNeighbors with parameter 2 at the top-right corner.")

		@Test
		void testNeighborsWithParameterTwoTopRightCorner() {
			Field fieldToBeTested = exampleJungle.getFields()[0][7];
			HashSet<Field> neighborFields = new HashSet<Field>();
			neighborFields.add(exampleJungle.getFields()[0][5]);
			neighborFields.add(exampleJungle.getFields()[0][6]);
			neighborFields.add(exampleJungle.getFields()[1][5]);
			neighborFields.add(exampleJungle.getFields()[1][6]);
			neighborFields.add(exampleJungle.getFields()[1][7]);
			neighborFields.add(exampleJungle.getFields()[2][5]);
			neighborFields.add(exampleJungle.getFields()[2][6]);
			neighborFields.add(exampleJungle.getFields()[2][7]);
			assertEquals(neighborFields.size(), exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size(),
					"\nThe obtained list has the size "
							+ exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size()
							+ " but a list of size " + neighborFields.size() + " was expected.");
			assertTrue(neighborFields.containsAll(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested)),
					"\nThe obtained list does not contain all or any elements of the expected list.");
			assertTrue(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).containsAll(neighborFields),
					"\nThe obtained list does not contain all or any elements of the expected list.");
		}

		@DisplayName("Test for getNeighbors with parameter 2 at the bottom-left corner.")

		@Test
		void testNeighborsWithParameterTwoBottomLeftCorner() {
			Field fieldToBeTested = exampleJungle.getFields()[5][0];
			HashSet<Field> neighborFields = new HashSet<Field>();
			neighborFields.add(exampleJungle.getFields()[3][0]);
			neighborFields.add(exampleJungle.getFields()[3][1]);
			neighborFields.add(exampleJungle.getFields()[3][2]);
			neighborFields.add(exampleJungle.getFields()[4][0]);
			neighborFields.add(exampleJungle.getFields()[4][1]);
			neighborFields.add(exampleJungle.getFields()[4][2]);
			neighborFields.add(exampleJungle.getFields()[5][1]);
			neighborFields.add(exampleJungle.getFields()[5][2]);
			assertEquals(neighborFields.size(), exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size(),
					"\nThe obtained list has the size "
							+ exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size()
							+ " but a list of size " + neighborFields.size() + " was expected.");
			assertTrue(neighborFields.containsAll(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested)),
					"\nThe obtained list does not contain all or any elements of the expected list.");
			assertTrue(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).containsAll(neighborFields),
					"\nThe obtained list does not contain all or any elements of the expected list.");
		}

		@DisplayName("Test for getNeighbors with parameter 2 at the bottom-right corner.")

		@Test
		void testNeighborsWithParameterTwoBottomRightCorner() {
			Field fieldToBeTested = exampleJungle.getFields()[5][7];
			HashSet<Field> neighborFields = new HashSet<Field>();
			neighborFields.add(exampleJungle.getFields()[3][5]);
			neighborFields.add(exampleJungle.getFields()[3][6]);
			neighborFields.add(exampleJungle.getFields()[3][7]);
			neighborFields.add(exampleJungle.getFields()[4][5]);
			neighborFields.add(exampleJungle.getFields()[4][6]);
			neighborFields.add(exampleJungle.getFields()[4][7]);
			neighborFields.add(exampleJungle.getFields()[5][5]);
			neighborFields.add(exampleJungle.getFields()[5][6]);
			assertEquals(neighborFields.size(), exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size(),
					"\nThe obtained list has the size "
							+ exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size()
							+ " but a list of size " + neighborFields.size() + " was expected.");
			assertTrue(neighborFields.containsAll(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested)),
					"\nThe obtained list does not contain all or any elements of the expected list.");
			assertTrue(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).containsAll(neighborFields),
					"\nThe obtained list does not contain all or any elements of the expected list.");
		}
	}

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Parameterized_Tests {
		static Random randomNumberGenerator;

		@BeforeAll
		static void initAll() {
			randomNumberGenerator = new Random(1);
		}

		static IntStream generateNegativeParametervalues() {
			return IntStream.iterate(randomNumberGenerator.nextInt(-20, -1), i -> i - 10).limit(20);
		}

		static IntStream generatePositiveParametervalues() {
			return IntStream.iterate(randomNumberGenerator.nextInt(1, 20), i -> i + 10).limit(20);
		}

		@DisplayName("Parameterized positive test for constructor and getParameters.")
		@ParameterizedTest
		@MethodSource("generatePositiveParametervalues")
		void testConstructorAndGetParametersPositive(int param) {
			INeighborhood distanceNeighborhood = new DistanceNeighborhood(param);
			List<Integer> paramList = new ArrayList<Integer>();
			paramList.add(param);
			assertIterableEquals(distanceNeighborhood.getParameters(), paramList, "\nThe parameter '"
					+ distanceNeighborhood.getParameters().get(0) + "' does not correspond to the specified value '" + param + "'.");
		}

		@DisplayName("Parameterized test for constructor with negative parameter.")
		@ParameterizedTest
		@MethodSource("generateNegativeParametervalues")
		void testConstructorAndGetColumnPositive(int param) {
			assertThrows(IllegalArgumentException.class, () -> new DistanceNeighborhood(param),
					() -> "No exception is generated for the (negative) " + "parameter value '" + param + "'.");
		}

		@DisplayName("Parameterized negative test for setParameters.")
		@ParameterizedTest
		@MethodSource("generateNegativeParametervalues")
		void testSetParametersNegative(int param) {
			List<Integer> paramList = new ArrayList<Integer>();
			paramList.add(param);
			assertThrows(IllegalArgumentException.class, () -> new DistanceNeighborhood(1).setParameters(paramList),
					() -> "\nNo exception is generated when setting the (negative) parameter value '" + param + "'.");
		}

		@DisplayName("Parameterized test for setParameters with too large list.")
		@ParameterizedTest
		@MethodSource("generatePositiveParametervalues")
		void testSetParametersLargeList(int param) {
			List<Integer> paramList = new ArrayList<Integer>();
			paramList.add(param);
			paramList.add(param + 10);
			assertThrows(IllegalArgumentException.class, () -> new DistanceNeighborhood(4).setParameters(paramList),
					() -> "\nNo exception is generated for the too large list.");
		}

		@DisplayName("Parameterized negative test for setParameter.")
		@ParameterizedTest
		@MethodSource("generateNegativeParametervalues")
		void testSetParameterNegative(int param) {
			assertThrows(IllegalArgumentException.class, () -> new DistanceNeighborhood(1).setParameter(param),
					() -> "\nNo exception is generated when setting the (negative) parameter value '" + param + "'.");
		}
	}
}
