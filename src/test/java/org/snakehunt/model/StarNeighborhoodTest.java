package org.snakehunt.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.util.stream.IntStream;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * The StarNeighborhoodTest class tests the methods of the StarNeighborhood class. 
 * Initially, a few simple tests are performed to cover important edge cases. 
 * This is followed by a series of parameterized tests that test the methods in a normal manner. 
 * The getNeighbors method is tested in its own test classes, covering both normal situations 
 * and edge cases with various parameters. To allow for manual verification, the generated 
 * jungle for the test is printed to the console.
 * 
 * @author Philip Redecker
 */
class StarNeighborhoodTest {

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Simple_Tests {
		@DisplayName("Simple positive test for setParameters.")
		@Test
		void testSetParameters() {
			int param1 = 5;
			int param2 = 6;
			List<Integer> paramList = new ArrayList<Integer>();
			paramList.add(param1);
			paramList.add(param2);
			INeighborhood starNeighborhood = new StarNeighborhood(paramList);
			int param3 = 7;
			int param4 = 8;
			List<Integer> paramList2 = new ArrayList<Integer>();
			paramList2.add(param3);
			paramList2.add(param4);
			starNeighborhood.setParameters(paramList2);
			assertEquals(paramList2, starNeighborhood.getParameters(),
					"\nThe parameters '" + starNeighborhood.getParameters().get(0) + "' and '"
							+ starNeighborhood.getParameters().get(1) + "' do not correspond to the specified values '"
							+ param3 + "' and '" + param4 + "'.");
		}

		@DisplayName("Simple test for setParameters with values that are too large in the list")
		@Test
		void testSetParametersLargeEntries() {
			List<Integer> paramList = new ArrayList<Integer>();
			paramList.add(2);
			paramList.add(4);
			int param1 = Integer.MAX_VALUE;
			int param2 = Integer.MAX_VALUE;
			List<Integer> paramListLarge = new ArrayList<Integer>();
			paramListLarge.add(param1);
			paramListLarge.add(param2);
			assertThrows(IllegalArgumentException.class,
					() -> new StarNeighborhood(paramList).setParameters(paramListLarge),
					() -> "\nOne or more of the parameter values '" + param1 + "' and '" + param2
							+ "' do not generate an exception, even though they are too large.");
		}

		@DisplayName("Simple test for setParameters with values that are too small in the list")
		@Test
		void testSetParametersSmallEntries() {
			List<Integer> paramList = new ArrayList<Integer>();
			paramList.add(2);
			paramList.add(4);
			int param1 = Integer.MIN_VALUE;
			int param2 = Integer.MIN_VALUE;
			List<Integer> paramListLarge = new ArrayList<Integer>();
			paramListLarge.add(param1);
			paramListLarge.add(param2);
			assertThrows(IllegalArgumentException.class,
					() -> new StarNeighborhood(paramList).setParameters(paramListLarge),
					() -> "\nOne or more of the parameter values '" + param1 + "' and '" + param2
							+ "' do not generate an exception, even though they are too small.");
		}
	}

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Simple_tests_for_the_method_getNeighbors_with_Parameters_1_and_2 {
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
			List<Integer> paramList = new ArrayList<Integer>();
			paramList.add(1);
			paramList.add(2);
			exampleNeighborhood = new StarNeighborhood(paramList);

			// The generated jungle is printed to the console here to allow for additional 
			// manual verification.
			System.out.println();
			System.out.println(
					"The jungle is printed so that manual verification is also possible:");
			System.out.println();
			System.out.println(exampleJungle.toString());
		}

		@DisplayName("Test for getNeighbors with parameters 1 and 2 in the middle.")
		@Test
		void testNeighborsWithParametersOneAndTwoMiddle() {
			Field fieldToBeTested = exampleJungle.getFields()[3][3];
			HashSet<Field> neighborFields = new HashSet<Field>();
			neighborFields.add(exampleJungle.getFields()[1][1]);
			neighborFields.add(exampleJungle.getFields()[3][2]);
			neighborFields.add(exampleJungle.getFields()[2][2]);
			neighborFields.add(exampleJungle.getFields()[4][4]);
			neighborFields.add(exampleJungle.getFields()[5][5]);
			neighborFields.add(exampleJungle.getFields()[2][3]);
			neighborFields.add(exampleJungle.getFields()[2][4]);
			neighborFields.add(exampleJungle.getFields()[1][5]);
			neighborFields.add(exampleJungle.getFields()[3][4]);
			neighborFields.add(exampleJungle.getFields()[4][3]);
			neighborFields.add(exampleJungle.getFields()[4][2]);
			neighborFields.add(exampleJungle.getFields()[5][1]);
			assertEquals(neighborFields.size(), exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size(),
					"\nThe obtained list has size "
							+ exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size()
							+ " but a list with size " + neighborFields.size() + " was expected.");
			assertTrue(neighborFields.containsAll(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested)),
					"\nThe obtained list does not contain all or any elements of the expected list.");
			assertTrue(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).containsAll(neighborFields),
					"\nThe obtained list does not contain all or any elements of the expected list.");
		}

		@DisplayName("Test for getNeighbors with parameters 1 and 2 in the top-left corner.")
		@Test
		void testNeighborsWithParametersOneAndTwoTopLeftCorner() {
			Field fieldToBeTested = exampleJungle.getFields()[0][0];
			HashSet<Field> neighborFields = new HashSet<Field>();
			neighborFields.add(exampleJungle.getFields()[0][1]);
			neighborFields.add(exampleJungle.getFields()[1][0]);
			neighborFields.add(exampleJungle.getFields()[1][1]);
			neighborFields.add(exampleJungle.getFields()[2][2]);
			assertEquals(neighborFields.size(), exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size(),
					"\nThe obtained list has size "
							+ exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size()
							+ " but a list with size " + neighborFields.size() + " was expected.");
			assertTrue(neighborFields.containsAll(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested)),
					"\nThe obtained list does not contain all or any elements of the expected list.");
			assertTrue(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).containsAll(neighborFields),
					"\nThe obtained list does not contain all or any elements of the expected list.");
		}

		@DisplayName("Test for getNeighbors with parameters 1 and 2 in the top-right corner.")

		@Test
		void testNeighborsWithParametersOneAndTwoTopRightCorner() {
			Field fieldToBeTested = exampleJungle.getFields()[0][7];
			HashSet<Field> neighborFields = new HashSet<Field>();
			neighborFields.add(exampleJungle.getFields()[0][6]);
			neighborFields.add(exampleJungle.getFields()[1][7]);
			neighborFields.add(exampleJungle.getFields()[1][6]);
			neighborFields.add(exampleJungle.getFields()[2][5]);
			assertEquals(neighborFields.size(), exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size(),
					"\nThe obtained list has size "
							+ exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size()
							+ " but a list with size " + neighborFields.size() + " was expected.");
			assertTrue(neighborFields.containsAll(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested)),
					"\nThe obtained list does not contain all or any elements of the expected list.");
			assertTrue(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).containsAll(neighborFields),
					"\nThe obtained list does not contain all or any elements of the expected list.");
		}

		@DisplayName("Test for getNeighbors with parameters 1 and 2 in the bottom-left corner.")

		@Test
		void testNeighborsWithParametersOneAndTwoBottomLeftCorner() {
			Field fieldToBeTested = exampleJungle.getFields()[5][0];
			HashSet<Field> neighborFields = new HashSet<Field>();
			neighborFields.add(exampleJungle.getFields()[4][0]);
			neighborFields.add(exampleJungle.getFields()[4][1]);
			neighborFields.add(exampleJungle.getFields()[5][1]);
			neighborFields.add(exampleJungle.getFields()[3][2]);
			assertEquals(neighborFields.size(), exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size(),
					"\nThe obtained list has size "
							+ exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size()
							+ " but a list with size " + neighborFields.size() + " was expected.");
			assertTrue(neighborFields.containsAll(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested)),
					"\nThe obtained list does not contain all or any elements of the expected list.");
			assertTrue(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).containsAll(neighborFields),
					"\nThe obtained list does not contain all or any elements of the expected list.");
		}

		@DisplayName("Test for getNeighbors with parameters 1 and 2 in the bottom-right corner.")

		@Test
		void testNeighborsWithParametersOneAndTwoBottomRightCorner() {
			Field fieldToBeTested = exampleJungle.getFields()[5][7];
			HashSet<Field> neighborFields = new HashSet<Field>();
			neighborFields.add(exampleJungle.getFields()[4][7]);
			neighborFields.add(exampleJungle.getFields()[4][6]);
			neighborFields.add(exampleJungle.getFields()[5][6]);
			neighborFields.add(exampleJungle.getFields()[3][5]);
			assertEquals(neighborFields.size(), exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size(),
					"\nThe obtained list has size "
							+ exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size()
							+ " but a list with size " + neighborFields.size() + " was expected.");
			assertTrue(neighborFields.containsAll(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested)),
					"\nThe obtained list does not contain all or any elements of the expected list.");
			assertTrue(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).containsAll(neighborFields),
					"\nThe obtained list does not contain all or any elements of the expected list.");
		}
	}

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Simple_tests_for_the_method_getNeighbors_with_Parameters_3_and_0 {
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
			List<Integer> paramList = new ArrayList<Integer>();
			paramList.add(0);
			paramList.add(3);
			exampleNeighborhood = new JumpNeighborhood(paramList);
		}

		@DisplayName("Test for getNeighbors with parameters 0 and 3 in the middle.")
		@Test
		void testNeighborsWithParametersThreeAndZeroMiddle() {
			Field fieldToBeTested = exampleJungle.getFields()[3][3];
			HashSet<Field> neighborFields = new HashSet<Field>();
			neighborFields.add(exampleJungle.getFields()[3][0]);
			neighborFields.add(exampleJungle.getFields()[0][3]);
			neighborFields.add(exampleJungle.getFields()[3][6]);
			assertEquals(neighborFields.size(), exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size(),
					"\nThe obtained list has size "
							+ exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size()
							+ " but a list with size " + neighborFields.size() + " was expected.");
			assertTrue(neighborFields.containsAll(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested)),
					"\nThe obtained list does not contain all or any elements of the expected list.");
			assertTrue(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).containsAll(neighborFields),
					"\nThe obtained list does not contain all or any elements of the expected list.");
		}

		@DisplayName("Test for getNeighbors with parameters 0 and 3 in the top-left corner.")
		@Test
		void testNeighborsWithParametersThreeAndZeroTopLeftCorner() {
			Field fieldToBeTested = exampleJungle.getFields()[0][0];
			HashSet<Field> neighborFields = new HashSet<Field>();
			neighborFields.add(exampleJungle.getFields()[3][0]);
			neighborFields.add(exampleJungle.getFields()[0][3]);
			assertEquals(neighborFields.size(), exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size(),
					"\nThe obtained list has size "
							+ exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size()
							+ " but a list with size " + neighborFields.size() + " was expected.");
			assertTrue(neighborFields.containsAll(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested)),
					"\nThe obtained list does not contain all or any elements of the expected list.");
			assertTrue(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).containsAll(neighborFields),
					"\nThe obtained list does not contain all or any elements of the expected list.");
		}

		@DisplayName("Test for getNeighbors with parameters 0 and 3 in the top-right corner.")

		@Test
		void testNeighborsWithParametersThreeAndZeroTopRightCorner() {
			Field fieldToBeTested = exampleJungle.getFields()[0][7];
			HashSet<Field> neighborFields = new HashSet<Field>();
			neighborFields.add(exampleJungle.getFields()[0][4]);
			neighborFields.add(exampleJungle.getFields()[3][7]);
			assertEquals(neighborFields.size(), exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size(),
					"\nThe obtained list has size "
							+ exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size()
							+ " but a list with size " + neighborFields.size() + " was expected.");
			assertTrue(neighborFields.containsAll(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested)),
					"\nThe obtained list does not contain all or any elements of the expected list.");
			assertTrue(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).containsAll(neighborFields),
					"\nThe obtained list does not contain all or any elements of the expected list.");
		}

		@DisplayName("Test for getNeighbors with parameters 0 and 3 in the bottom-left corner.")

		@Test
		void testNeighborsWithParametersThreeAndZeroBottomLeftCorner() {
			Field fieldToBeTested = exampleJungle.getFields()[5][0];
			HashSet<Field> neighborFields = new HashSet<Field>();
			neighborFields.add(exampleJungle.getFields()[2][0]);
			neighborFields.add(exampleJungle.getFields()[5][3]);
			assertEquals(neighborFields.size(), exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size(),
					"\nThe obtained list has size "
							+ exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size()
							+ " but a list with size " + neighborFields.size() + " was expected.");
			assertTrue(neighborFields.containsAll(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested)),
					"\nThe obtained list does not contain all or any elements of the expected list.");
			assertTrue(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).containsAll(neighborFields),
					"\nThe obtained list does not contain all or any elements of the expected list.");
		}

		@DisplayName("Test for getNeighbors with parameters 0 and 3 in the bottom-right corner.")

		@Test
		void testNeighborsWithParametersThreeAndZeroBottomRightCorner() {
			Field fieldToBeTested = exampleJungle.getFields()[5][7];
			HashSet<Field> neighborFields = new HashSet<Field>();
			neighborFields.add(exampleJungle.getFields()[2][7]);
			neighborFields.add(exampleJungle.getFields()[5][4]);
			assertEquals(neighborFields.size(), exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size(),
					"\nThe obtained list has size "
							+ exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size()
							+ " but a list with size " + neighborFields.size() + " was expected.");
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
			List<Integer> paramList = new ArrayList<Integer>();
			paramList.add(param);
			paramList.add(param + 1);
			INeighborhood starNeighborhood = new StarNeighborhood(paramList);
			assertIterableEquals(starNeighborhood.getParameters(), paramList,
					"\nThe parameters '" + starNeighborhood.getParameters().get(0) + "' and '"
							+ starNeighborhood.getParameters().get(1) + "' do not correspond to the specified values '"
							+ param + "' and " + (param + 1) + "'.");
		}

		@DisplayName("Parameterized test for constructor with negative parameter.")
		@ParameterizedTest
		@MethodSource("generateNegativeParametervalues")
		void testConstructorNegativeParameters(int param) {
			List<Integer> paramList = new ArrayList<Integer>();
			paramList.add(param);
			paramList.add(param - 1);
			assertThrows(IllegalArgumentException.class, () -> new StarNeighborhood(paramList),
					() -> "\nOne or more of the parameter values '" + param + "' and '" + (param - 1)
							+ "' do not generate an exception, even though they are negative.");
		}

		@DisplayName("Parameterized test for constructor with input list too long.")
		@ParameterizedTest
		@MethodSource("generatePositiveParametervalues")
		void testConstructorParameterlistTooLong(int param) {
			List<Integer> paramList = new ArrayList<Integer>();
			paramList.add(param);
			paramList.add(param + 1);
			paramList.add(param + 2);
			assertThrows(IllegalArgumentException.class, () -> new StarNeighborhood(paramList),
					() -> "\nThe input list has size '" + paramList.size() + "' but does not generate an exception.");
		}

		@DisplayName("Parameterized negative test for setParameters.")
		@ParameterizedTest
		@MethodSource("generateNegativeParametervalues")
		void testSetParametersNegative(int param) {
			List<Integer> paramListInit = new ArrayList<Integer>();
			paramListInit.add(11);
			paramListInit.add(13);
			List<Integer> paramList = new ArrayList<Integer>();
			paramList.add(param);
			paramList.add(param - 1);
			assertThrows(IllegalArgumentException.class,
					() -> new StarNeighborhood(paramListInit).setParameters(paramList),
					() -> "\nOne or more of the parameter values '" + param + "' and '" + (param - 1)
							+ "' do not generate an exception, even though they are negative.");
		}

		@DisplayName("Parameterized test for setParameters with a list that is too large.")
		@ParameterizedTest
		@MethodSource("generatePositiveParametervalues")
		void testSetParametersLargeList(int param) {
			List<Integer> paramList = new ArrayList<Integer>();
			paramList.add(param);
			paramList.add(param + 1);
			List<Integer> paramListLarge = new ArrayList<Integer>();
			paramListLarge.add(param + 2);
			paramListLarge.add(param + 3);
			paramListLarge.add(param + 4);
			assertThrows(IllegalArgumentException.class,
					() -> new StarNeighborhood(paramList).setParameters(paramListLarge),
					() -> "\nNo exception is generated for the list that is too large.");
		}
	}
}