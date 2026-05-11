package org.snakehunt.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;
import java.util.stream.IntStream;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * In the JungleTest class, the methods of the Jungle class are tested. Here,
 * all methods are subjected to parameterized tests that cause exceptions or
 * contain other logic. In this case, these are primarily the constructors and
 * the getters and setters for 'rows' and 'columns'. The remaining getters and
 * setters were skipped for simplicity. Additionally, certain special cases are
 * considered in simple tests, and the toString method of the Jungle class was
 * also tested with simple tests.
 * 
 * @author Philip Redecker
 */
class JungleTest {

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Simple_Tests {
		@DisplayName("Simple positive test.")
		@Test
		void testGetRows() {
			int rows = 1;
			Jungle jungle = new Jungle(rows, 0, null, "");
			assertEquals(jungle.getRows(), rows, () -> "\nThe number of rows '" + jungle.getRows()
					+ "' does not correspond to the specified value '" + rows + "'.");
		}

		@DisplayName("Simple negative test.")
		@Test
		void testGetColumns() {
			int columns = -1;
			assertThrows(IllegalArgumentException.class, () -> new Jungle(0, columns, null, ""),
					() -> "\nNo exception is generated for the (negative) number of columns '" + columns + "'.");
		}

		@DisplayName("Simple positive test with too large number in rows.")
		@Test
		void testGetRowMax() {
			int rows = Integer.MAX_VALUE;
			assertThrows(IllegalArgumentException.class, () -> new Jungle(rows, 0, null, ""),
					() -> "\nNo exception is generated for the too large row value '" + rows + "'.");
		}

		@DisplayName("Simple positive test with too large number in columns.")
		@Test
		void testGetColumnMax() {
			int columns = Integer.MAX_VALUE;
			assertThrows(IllegalArgumentException.class, () -> new Jungle(0, columns, null, ""),
					() -> "\nNo exception is generated for the too large column value '" + columns + "'.");
		}

		@DisplayName("Simple test for toString method with empty jungle.")
		@Test
		void testToStringEmpty() {
			Jungle jungle = new Jungle(0, 0, null, "");
			assertEquals("", jungle.toString(), () -> "The empty string is not generated.");
		}

		@DisplayName("Simple test for toString method with normal jungle.")
		@Test
		void testToStringNormal() {
			Jungle jungle = new Jungle(3, 4, "", 1);
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 4; j++) {
					jungle.getFields()[i][j].setCharacter("a");
				}
			}
			assertEquals(
					" (a,1,1) (a,1,1) (a,1,1) (a,1,1)\n\n (a,1,1) (a,1,1) (a,1,1) (a,1,1)\n\n (a,1,1) (a,1,1) (a,1,1) (a,1,1)\n\n",
					jungle.toString(), () -> "The expected string is not generated.");
		}

		@DisplayName("Simple test for toString method with large jungle.")
		@Test
		void testToStringLarge() {
			Jungle jungle = new Jungle(13, 14, "", 1);
			for (int i = 0; i < 13; i++) {
				for (int j = 0; j < 14; j++) {
					jungle.getFields()[i][j].setCharacter("a");
				}
			}
			assertEquals(
					" (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1)\n\n"
							+ " (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1)\n\n"
							+ " (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1)\n\n"
							+ " (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1)\n\n"
							+ " (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1)\n\n"
							+ " (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1)\n\n"
							+ " (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1)\n\n"
							+ " (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1)\n\n"
							+ " (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1)\n\n"
							+ " (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1)\n\n"
							+ " (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1)\n\n"
							+ " (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1)\n\n"
							+ " (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1) (a,1,1)\n\n",
					jungle.toString(), () -> "\nThe expected string is not generated.");
		}

		@DisplayName("Simple test for the numberOfFields method.")
		@Test
		void testNumberOfFields() {
			Jungle jungle = new Jungle(13, 14, "", 1);
			for (int i = 0; i < 13; i++) {
				for (int j = 0; j < 14; j++) {
					jungle.getFields()[i][j].setCharacter("a");
				}
			}
			assertEquals(182, jungle.numberOfFields(),
					"\nThe method does not return the correct value for the jungle size. "
							+ "The jungle has 182 fields, but " + jungle.numberOfFields()
							+ " was returned.");
		}

		@DisplayName("Simple test for the numberOfTakenFields method.")
		@Test
		void testNumberOfUsedFields() {
			Jungle jungle = new Jungle(13, 14, "", 1);
			for (int i = 0; i < 13; i++) {
				for (int j = 0; j < 14; j++) {
					jungle.getFields()[i][j].setCharacter("a");
				}
			}
			jungle.getFields()[4][7].setCharacter(null);
			jungle.getFields()[4][1].setCharacter(null);
			jungle.getFields()[5][7].setCharacter(null);
			jungle.getFields()[8][2].setCharacter(null);
			jungle.getFields()[12][1].setCharacter(null);
			jungle.getFields()[10][7].setCharacter(null);
			jungle.getFields()[4][3].setCharacter(null);
			assertEquals(175, jungle.numberOfTakenFields(),
					"\nThe method does not return the correct value for the occupied fields of the jungle. "
							+ "The jungle has 175 occupied fields, but " + jungle.numberOfTakenFields()
							+ " was returned.");
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

		@DisplayName("Parameterized positive test for constructor and getColumns.")
		@ParameterizedTest
		@MethodSource("generatePositiveParametervalues")
		void testConstructorAndGetColumnPositive(int columns) {
			Jungle jungle = new Jungle(0, columns, null, "");
			assertEquals(jungle.getColumns(), columns, "\nThe number of columns '" + jungle.getColumns()
					+ "' does not correspond to the specified value '" + columns + "'.");
		}

		@DisplayName("Parameterized positive test for constructor and getRows.")
		@ParameterizedTest
		@MethodSource("generatePositiveParametervalues")
		void testConstructorAndGetRowPositive(int rows) {
			Jungle jungle = new Jungle(rows, 0, null, "");
			assertEquals(jungle.getRows(), rows, "\nThe number of rows '" + jungle.getRows()
					+ "' does not correspond to the specified value '" + rows + "'.");
		}

		@DisplayName("Parameterized test for constructor with negative rows.")
		@ParameterizedTest
		@MethodSource("generateNegativeParametervalues")
		void testConstructorRowNegative(int rows) {
			assertThrows(IllegalArgumentException.class, () -> new Jungle(rows, 0, null, ""),
					() -> "\nNo exception is generated for the (negative) number of rows '" + rows + "'.");
		}

		@DisplayName("Parameterized test for constructor with negative columns.")
		@ParameterizedTest
		@MethodSource("generateNegativeParametervalues")
		void testConstructorColumnNegative(int columns) {
			assertThrows(IllegalArgumentException.class, () -> new Jungle(0, columns, null, ""),
					() -> "\nNo exception is generated for the (negative) number of columns '" + columns + "'.");
		}

		@DisplayName("Parameterized negative test for setRows.")
		@ParameterizedTest
		@MethodSource("generateNegativeParametervalues")
		void testSetRowNegative(int rows) {
			assertThrows(IllegalArgumentException.class, () -> new Jungle(0, 0, null, "").setRows(rows),
					() -> "\nNo exception is generated for the (negative) number of rows '" + rows + "'.");
		}

		@DisplayName("Parameterized negative test for setColumns.")
		@ParameterizedTest
		@MethodSource("generateNegativeParametervalues")
		void testSetColumnNegative(int columns) {
			assertThrows(IllegalArgumentException.class, () -> new Jungle(0, 0, null, "").setColumns(columns),
					() -> "\nNo exception is generated for the (negative) number of columns '" + columns + "'.");
		}
	}
}
