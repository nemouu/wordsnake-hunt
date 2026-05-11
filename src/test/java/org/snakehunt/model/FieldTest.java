package org.snakehunt.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;
import java.util.stream.IntStream;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * In the FieldTest class, the methods of the Field class are tested. Here, all
 * methods are subjected to parameterized tests that cause exceptions or contain
 * other logic. In this case, these are primarily the constructors and the
 * getters and setters for 'row', 'column', and 'usage'. The remaining getters
 * and setters were skipped for simplicity. Additionally, certain special cases
 * are considered in simple tests.
 * 
 * @author Philip Redecker
 */
class FieldTest {

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Simple_Tests {
		@DisplayName("Simple positive test.")
		@Test
		void testGetRow() {
			int row = 1;
			Field field = new Field(row, 0, 1);
			assertEquals(field.getRow(), row, () -> "\nThe row value '" + field.getRow()
					+ "' does not correspond to the specified value '" + row + "'.");
		}

		@DisplayName("Simple positive test regarding the 'points' attribute.")
		@Test
		void testPoints() {
			int points = 1;
			Field field = new Field("", 0, 0, 0, points, "");
			assertEquals(field.getPoints(), points, () -> "\nThe points value '" + field.getPoints()
					+ "' does not correspond to the specified value '" + points + "'.");
		}

		@DisplayName("Simple negative test.")
		@Test
		void testGetColumn() {
			int column = -1;
			assertThrows(IllegalArgumentException.class, () -> new Field(0, column, 1),
					() -> "\nNo exception is generated for the (negative) column value '" + column + "'.");
		}

		@DisplayName("Simple negative test regarding the 'points' attribute with a too large value.")
		@Test
		void testPointsLarge() {
			int points = Integer.MAX_VALUE;
			assertThrows(IllegalArgumentException.class, () -> new Field("", 0, 0, 0, points, ""),
					() -> "No exception is generated for the too large value of 'points' '" + points + "'.");
		}

		@DisplayName("Simple negative test regarding the 'points' attribute with a too small value.")
		@Test
		void testPointsSmall() {
			int points = Integer.MIN_VALUE;
			assertThrows(IllegalArgumentException.class, () -> new Field("", 0, 0, 0, points, ""),
					() -> "No exception is generated for the too small value of 'points' '" + points + "'.");
		}

		@DisplayName("Simple positive test of setPoints with a too large value.")
		@Test
		void testSetPointsLarge() {
			int points = Integer.MAX_VALUE;
			assertThrows(IllegalArgumentException.class, () -> new Field("", 0, 0, 0, 0, "").setPoints(points),
					() -> "No exception is generated for the too large value of 'points' '" + points + "'.");
		}

		@DisplayName("Simple negative test of setPoints with a too small value.")
		@Test
		void testSetPointsSmall() {
			int points = Integer.MIN_VALUE;
			assertThrows(IllegalArgumentException.class, () -> new Field("", 0, 0, 0, 0, "").setPoints(points),
					() -> "No exception is generated for the too small value of 'points' '" + points + "'.");
		}

		@DisplayName("Simple positive test with a too large number in row.")
		@Test
		void testGetRowMax() {
			int row = Integer.MAX_VALUE + 1;
			assertThrows(IllegalArgumentException.class, () -> new Field(row, 0, 1),
					() -> "\nNo exception is generated for the too large row value '" + row + "'.");
		}

		@DisplayName("Simple positive test with a too large number in column.")
		@Test
		void testGetColumnMax() {
			int column = Integer.MAX_VALUE + 1;
			assertThrows(IllegalArgumentException.class, () -> new Field(0, column, 1),
					() -> "\nNo exception is generated for the too large column value '" + column + "'.");
		}

		@DisplayName("Simple positive test with a too large number in usage.")
		@Test
		void testGetUsageMax() {
			int usage = Integer.MAX_VALUE + 1;
			assertThrows(IllegalArgumentException.class, () -> new Field("", 0, 0, usage, 0, ""),
					() -> "\nNo exception is generated for the too large usage value '" + usage + "'.");
		}
	}

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Repeated_Tests {
		static Random randomNumberGenerator;

		@BeforeAll
		static void initAll() {
			randomNumberGenerator = new Random(1);
		}

		@DisplayName("Repeated negative test.")
		@RepeatedTest(10)
		void testGetColumn(RepetitionInfo repetitionInfo) {
			int column = randomNumberGenerator.nextInt(-100000, -1);
			assertThrows(IllegalArgumentException.class, () -> new Field(0, column, 1),
					() -> "\nNo exception is generated for the (negative) column value '" + column + "'.");
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

		@DisplayName("Parameterized positive test for constructor and getColumn.")
		@ParameterizedTest
		@MethodSource("generatePositiveParametervalues")
		void testConstructorAndGetColumnPositive(int column) {
			Field field = new Field(0, column, 1);
			assertEquals(field.getColumn(), column, "\nThe column value '" + field.getColumn()
					+ "' does not correspond to the specified value '" + column + "'.");
		}

		@DisplayName("Parameterized positive test for constructor and getRow.")
		@ParameterizedTest
		@MethodSource("generatePositiveParametervalues")
		void testConstructorAndGetRowPositive(int row) {
			Field field = new Field(row, 0, 1);
			assertEquals(field.getRow(), row, "\nThe row value '" + field.getRow()
					+ "' does not correspond to the specified value '" + row + "'.");
		}

		@DisplayName("Parameterized positive test for constructor and getUsage.")
		@ParameterizedTest
		@MethodSource("generatePositiveParametervalues")
		void testConstructorAndGetUsagePositive(int usage) {
			Field field = new Field("", 0, 0, usage, 0, "");
			assertEquals(field.getUsage(), usage, "\nThe usage value '" + field.getUsage()
					+ "' does not correspond to the specified value '" + usage + "'.");
		}

		@DisplayName("Parameterized test for constructor with negative row.")
		@ParameterizedTest
		@MethodSource("generateNegativeParametervalues")
		void testConstructorRowNegative(int row) {
			assertThrows(IllegalArgumentException.class, () -> new Field(row, 0, 1),
					() -> "\nNo exception is generated for the (negative) row value '" + row + "'.");
		}

		@DisplayName("Parameterized test for constructor with negative column.")
		@ParameterizedTest
		@MethodSource("generateNegativeParametervalues")
		void testConstructorColumnNegative(int column) {
			assertThrows(IllegalArgumentException.class, () -> new Field(0, column, 1),
					() -> "\nNo exception is generated for the (negative) column value '" + column + "'.");
		}

		@DisplayName("Parameterized negative test for setRow.")
		@ParameterizedTest
		@MethodSource("generateNegativeParametervalues")
		void testSetRowNegative(int row) {
			assertThrows(IllegalArgumentException.class, () -> new Field("", 0, 0, 0, 0, "").setRow(row),
					() -> "\nNo exception is generated when setting the (negative) row value '" + row + "'.");
		}

		@DisplayName("Parameterized negative test for setColumn.")
		@ParameterizedTest
		@MethodSource("generateNegativeParametervalues")
		void testSetColumnNegative(int column) {
			assertThrows(IllegalArgumentException.class, () -> new Field("", 0, 0, 0, 0, "").setColumn(column),
					() -> "\nNo exception is generated when setting the (negative) column value '" + column + "'.");
		}

		@DisplayName("Parameterized negative test for setUsage.")
		@ParameterizedTest
		@MethodSource("generateNegativeParametervalues")
		void testSetUsageNegative(int usage) {
			assertThrows(IllegalArgumentException.class, () -> new Field("", 0, 0, 0, 0, "").setUsage(usage),
					() -> "\nNo exception is generated when setting the (negative) usage value '" + usage
							+ "'.");
		}
	}
}
