package org.snakehunt.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;
import java.util.stream.IntStream;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * The SnakeTypeTest class tests the methods of the SnakeType class. 
 * Initially, a few simple tests are performed to cover important edge cases. 
 * In addition, the toString method is tested several times. This is followed 
 * by a series of parameterized tests that test the methods in a normal manner. 
 * To provide an example neighborhood for all testing methods, it is 
 * initialized in a @BeforeAll method.
 * 
 * @author Philip Redecker
 */
class SnakeTypeTest {

	private static INeighborhood exampleNeighborhood;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		exampleNeighborhood = new DistanceNeighborhood(5);
	}

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Simple_Tests {
		@DisplayName("Simple positive test regarding attribute 'points' with a value that is too large.")
		@Test
		void testPointsLarge() {
			int points = Integer.MAX_VALUE;
			assertThrows(IllegalArgumentException.class, () -> new SnakeType("", exampleNeighborhood, "", points, 1),
					() -> "No exception is generated for the too large value of 'points' '" + points + "'.");
		}

		@DisplayName("Simple negative test regarding attribute 'points' with a value that is too small.")
		@Test
		void testPointsSmall() {
			int points = Integer.MIN_VALUE;
			assertThrows(IllegalArgumentException.class, () -> new SnakeType("", exampleNeighborhood, "", points, 1),
					() -> "No exception is generated for the too small value of 'points' '" + points + "'.");
		}

		@DisplayName("Simple positive test of setPoints with a value that is too large.")
		@Test
		void testSetPointsLarge() {
			int points = Integer.MAX_VALUE;
			assertThrows(IllegalArgumentException.class,
					() -> new SnakeType("", exampleNeighborhood, "", 0, 1).setPoints(points),
					() -> "No exception is generated for the too large value of 'points' '" + points + "'.");
		}

		@DisplayName("Simple negative test of setPoints with a value that is too small.")
		@Test
		void testSetPointsSmall() {
			int points = Integer.MIN_VALUE;
			assertThrows(IllegalArgumentException.class,
					() -> new SnakeType("", exampleNeighborhood, "", 0, 1).setPoints(points),
					() -> "No exception is generated for the too small value of 'points' '" + points + "'.");
		}

		@DisplayName("Simple positive test regarding attribute 'amount' with a value that is too large.")
		@Test
		void testAmountLarge() {
			int amount = Integer.MAX_VALUE;
			assertThrows(IllegalArgumentException.class, () -> new SnakeType("", exampleNeighborhood, "", 1, amount),
					() -> "No exception is generated for the too large value of 'amount' '" + amount + "'.");
		}

		@DisplayName("Simple positive test regarding attribute 'amount' with a value that is too small.")
		@Test
		void testAmountSmall() {
			int amount = Integer.MIN_VALUE;
			assertThrows(IllegalArgumentException.class, () -> new SnakeType("", exampleNeighborhood, "", 1, amount),
					() -> "No exception is generated for the too small value of 'amount' '" + amount + "'.");
		}

		@DisplayName("Simple positive test regarding attribute 'amount' with value 0.")
		@Test
		void testAmountZero() {
			int amount = 0;
			assertThrows(IllegalArgumentException.class, () -> new SnakeType("", exampleNeighborhood, "", 1, amount),
					() -> "No exception is generated for the too small value of 'amount' '" + amount + "'.");
		}

		@DisplayName("Simple positive test of setAmount with a value that is too large.")
		@Test
		void testSetAmountLarge() {
			int amount = Integer.MAX_VALUE;
			assertThrows(IllegalArgumentException.class,
					() -> new SnakeType("", exampleNeighborhood, "", 0, 1).setAmount(amount),
					() -> "No exception is generated for the too large value of 'amount' '" + amount + "'.");
		}

		@DisplayName("Simple positive test of setAmount with a value that is too small.")
		@Test
		void testSetAmountSmall() {
			int amount = Integer.MAX_VALUE;
			assertThrows(IllegalArgumentException.class,
					() -> new SnakeType("", exampleNeighborhood, "", 0, 1).setAmount(amount),
					() -> "No exception is generated for the too large value of 'amount' '" + amount + "'.");
		}

		@DisplayName("Simple positive test of setAmount with value 0.")
		@Test
		void testSetAmountZero() {
			int amount = -1;
			assertThrows(IllegalArgumentException.class,
					() -> new SnakeType("", exampleNeighborhood, "", 0, 1).setAmount(amount),
					() -> "No exception is generated for the too small value of 'amount' '" + amount + "'.");
		}

		@DisplayName("Test toString method when as much as possible is empty")
		@Test
		void testToStringEmpty() {
			assertThrows(NullPointerException.class, () -> new SnakeType(null, null, null, 0, 1),
					"\nThe empty string is not returned, even though the model is empty.");
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

		@DisplayName("Parameterized positive test for constructor and getPoints.")
		@ParameterizedTest
		@MethodSource("generatePositiveParametervalues")
		void testConstructorAndGetPointsPositive(int points) {
			SnakeType type = new SnakeType("a", exampleNeighborhood, "a", points, 1);
			assertEquals(type.getPoints(), points, "\nThe value for points '" + type.getPoints()
					+ "' does not match the specified value '" + points + "'.");
		}

		@DisplayName("Parameterized negative test for constructor and getPoints.")
		@ParameterizedTest
		@MethodSource("generateNegativeParametervalues")
		void testConstructorAndGetPointsNegative(int points) {
			assertThrows(IllegalArgumentException.class, () -> new SnakeType("a", exampleNeighborhood, "a", points, 1),
					"\nNo exception was thrown, even though the value for points is negative.");
		}

		@DisplayName("Parameterized positive test for constructor and getAmount.")
		@ParameterizedTest
		@MethodSource("generatePositiveParametervalues")
		void testConstructorAndGetAmountPositive(int amount) {
			SnakeType type = new SnakeType("a", exampleNeighborhood, "a", 1, amount);
			assertEquals(type.getAmount(), amount, "\nThe value for amount '" + type.getAmount()
					+ "' does not match the specified value '" + amount + "'.");
		}

		@DisplayName("Parameterized negative test for constructor and getAmount.")
		@ParameterizedTest
		@MethodSource("generateNegativeParametervalues")
		void testConstructorAndGetAmountNegative(int amount) {
			assertThrows(IllegalArgumentException.class, () -> new SnakeType("a", exampleNeighborhood, "a", 1, amount),
					"\nNo exception is generated for the (negative) value for amount '" + amount + "'.");
		}

		@DisplayName("Parameterized positive test for setPoints and getPoints.")
		@ParameterizedTest
		@MethodSource("generatePositiveParametervalues")
		void testSetPointsPositive(int points) {
			SnakeType type = new SnakeType("a", exampleNeighborhood, "a", 1, 1);
			type.setPoints(points);
			assertEquals(type.getPoints(), points, "\nThe value for points '" + type.getPoints()
					+ "' does not match the specified value '" + points + "'.");
		}

		@DisplayName("Parameterized negative test for setPoints and getPoints.")
		@ParameterizedTest
		@MethodSource("generateNegativeParametervalues")
		void testSetPointsNegative(int points) {
			SnakeType type = new SnakeType("a", exampleNeighborhood, "a", 1, 1);
			assertThrows(IllegalArgumentException.class, () -> type.setPoints(points),
					"\nNo exception was thrown, even though the value for points is negative.");
		}

		@DisplayName("Parameterized positive test for setAmount and getAmount.")
		@ParameterizedTest
		@MethodSource("generatePositiveParametervalues")
		void testSetAmountPositive(int amount) {
			SnakeType type = new SnakeType("a", exampleNeighborhood, "a", 1, 1);
			type.setAmount(amount);
			assertEquals(type.getAmount(), amount, "\nThe value for amount '" + type.getAmount()
					+ "' does not match the specified value '" + amount + "'.");
		}

		@DisplayName("Parameterized negative test for setAmount.")
		@ParameterizedTest
		@MethodSource("generateNegativeParametervalues")
		void testSetAmountNegative(int amount) {
			assertThrows(IllegalArgumentException.class,
					() -> new SnakeType("a", exampleNeighborhood, "a", 1, 1).setAmount(amount),
					"\nNo exception is generated for the (negative) value for amount '" + amount + "'.");
		}
	}
}
