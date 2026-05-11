package org.snakehunt.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;
import java.util.stream.DoubleStream;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * In the ProblemModelTest class, the methods of the ProblemModel class are tested.
 * Simple tests are performed to cover some important edge cases.
 * Additionally, the toString method is tested several times.
 * There are also a series of parameterized tests that test the methods in a normal way.
 * To provide a sample jungle and a sample solution for all testing methods, these are initialized in a @BeforeAll method.
 * 
 * @author Philip Redecker
 */
class ProblemModelTest {

	private static Jungle exampleJungle;
	private static Solution exampleSolution;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		exampleJungle = new Jungle(4, 5, new Field[4][5], "ABCDEFG");
		exampleSolution = new Solution();
	}

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Simple_Tests {
		@DisplayName("Simple test of the constructor with a parameter that is too large for 'time'.")
		@Test
		void testConstructorLargeTime() {
			Double[] exampleTime = { Double.MAX_VALUE };
			assertThrows(IllegalArgumentException.class, () -> new ProblemModel(exampleJungle, exampleSolution, exampleTime),
					() -> "\nNo exception was generated for the value that is too large: '" + Double.MAX_VALUE + "'.");
		}

		@DisplayName("Simple test of the constructor with parameter 0.0 for 'time'.")
		@Test
		void testConstructorTimeZero() {
			Double[] exampleTime = { 0.0 };
			assertThrows(IllegalArgumentException.class, () -> new ProblemModel(exampleJungle, exampleSolution, exampleTime),
					() -> "\nNo exception was generated for the value that is too small: '" + 0.0 + "'.");
		}

		@DisplayName("Simple test of setTime() with a parameter that is too large.")
		@Test
		void testSetTimeLargeTime() {
			Double[] initTime = { 3.4 };
			Double[] exampleTime = { Double.MAX_VALUE };
			assertThrows(IllegalArgumentException.class,
					() -> new ProblemModel(exampleJungle, exampleSolution, initTime).setTime(exampleTime),
					() -> "\nNo exception was generated for the value that is too large: '" + Double.MAX_VALUE + "'.");
		}

		@DisplayName("Simple test of setTime() with parameter 0.0.")
		@Test
		void testSetTimeZero() {
			Double[] initTime = { 3.4 };
			Double[] exampleTime = { 0.0 };
			assertThrows(IllegalArgumentException.class,
					() -> new ProblemModel(exampleJungle, exampleSolution, initTime).setTime(exampleTime),
					() -> "\nNo exception was generated for the value that is too small: '" + 0.0 + "'.");
		}

		@DisplayName("Simple test of calculateTimeToNanoseconds, part 1")
		@Test
		void testCalculateTimeInNanosecondsOne() {
			Double[] exampleTime = { 3.0 };
			Jungle exampleJungle = new Jungle(3, 4, "asdertg", 1);
			IModel exampleModel = new ProblemModel(exampleJungle, null, exampleTime);
			assertEquals(3.0E9, exampleModel.calculateTimeToNanoseconds(),
					"\nThe calculated time does not match the expected value.");
		}

		@DisplayName("Simple test of calculateTimeToNanoseconds, part 2")
		@Test
		void testCalculateTimeInNanosecondsTwo() {
			Double[] exampleTime = { 2500.0 };
			Jungle exampleJungle = new Jungle(3, 4, "asdertg", 1);
			IModel exampleModel = new ProblemModel(exampleJungle, null, exampleTime);
			exampleModel.setUnitOfTime("ms");
			assertEquals(2.5E9, exampleModel.calculateTimeToNanoseconds(),
					"\nThe calculated time does not match the expected value.");
		}

		@DisplayName("Simple test of calculateTimeToNanoseconds, part 3")
		@Test
		void testCalculateTimeInNanosecondsThree() {
			Double[] exampleTime = { 0.03 };
			Jungle exampleJungle = new Jungle(3, 4, "asdertg", 1);
			IModel exampleModel = new ProblemModel(exampleJungle, null, exampleTime);
			exampleModel.setUnitOfTime("min");
			assertEquals(1.8E9, exampleModel.calculateTimeToNanoseconds(),
					"\nThe calculated time does not match the expected value.");
		}

		@DisplayName("Simple test of calculateTimeToNanoseconds, part 4")
		@Test
		void testCalculateTimeInNanosecondsFour() {
			Double[] exampleTime = { 0.00015 };
			Jungle exampleJungle = new Jungle(3, 4, "asdertg", 1);
			IModel exampleModel = new ProblemModel(exampleJungle, null, exampleTime);
			exampleModel.setUnitOfTime("h");
			assertEquals(5.4E8, exampleModel.calculateTimeToNanoseconds(),
					"\nThe calculated time does not match the expected value.");
		}

		@DisplayName("Simple test of calculateTimeToNanoseconds, part 5")
		@Test
		void testCalculateTimeInNanosecondsFive() {
			Double[] exampleTime = { 0.0000004 };
			Jungle exampleJungle = new Jungle(3, 4, "asdertg", 1);
			IModel exampleModel = new ProblemModel(exampleJungle, null, exampleTime);
			exampleModel.setUnitOfTime("d");
			assertEquals(3.456E7, exampleModel.calculateTimeToNanoseconds(),
					"\nThe calculated time does not match the expected value.");
		}

		@DisplayName("Simple test of calculateTimeInUnitGivenByModel, part 1")
		@Test
		void testCalculateTimeInUnitOfModelOne() {
			Double[] exampleTime = { 3.0 };
			Jungle exampleJungle = new Jungle(3, 4, "asdertg", 1);
			IModel exampleModel = new ProblemModel(exampleJungle, null, exampleTime);
			assertEquals(23.413434134, exampleModel.calculateTimeInUnitGivenByModel((long) 23413434134.0),
					"\nThe calculated time does not match the expected value.");
		}

		@DisplayName("Simple test of calculateTimeInUnitGivenByModel, part 2")
		@Test
		void testCalculateTimeInUnitOfModelTwo() {
			Double[] exampleTime = { 2500.0 };
			Jungle exampleJungle = new Jungle(3, 4, "asdertg", 1);
			IModel exampleModel = new ProblemModel(exampleJungle, null, exampleTime);
			exampleModel.setUnitOfTime("ms");
			assertEquals(23413.434134, exampleModel.calculateTimeInUnitGivenByModel((long) 23413434134.0),
					"\nThe calculated time does not match the expected value.");
		}

		@DisplayName("Simple test of calculateTimeInUnitGivenByModel, part 3")
		@Test
		void testCalculateTimeInUnitOfModelThree() {
			Double[] exampleTime = { 0.03 };
			Jungle exampleJungle = new Jungle(3, 4, "asdertg", 1);
			IModel exampleModel = new ProblemModel(exampleJungle, null, exampleTime);
			exampleModel.setUnitOfTime("min");
			assertEquals(0.3902239022333333, exampleModel.calculateTimeInUnitGivenByModel((long) 23413434134.0),
					"\nThe calculated time does not match the expected value.");
		}

		@DisplayName("Simple test of calculateTimeInUnitGivenByModel, part 4")
		@Test
		void testCalculateTimeInUnitOfModelFour() {
			Double[] exampleTime = { 0.00015 };
			Jungle exampleJungle = new Jungle(3, 4, "asdertg", 1);
			IModel exampleModel = new ProblemModel(exampleJungle, null, exampleTime);
			exampleModel.setUnitOfTime("h");
			assertEquals(0.006503731703888889, exampleModel.calculateTimeInUnitGivenByModel((long) 23413434134.0),
					"\nThe calculated time does not match the expected value.");
		}

		@DisplayName("Simple test of calculateTimeInUnitGivenByModel, part 5")
		@Test
		void testCalculateTimeInUnitOfModelFive() {
			Double[] exampleTime = { 0.0000004 };
			Jungle exampleJungle = new Jungle(3, 4, "asdertg", 1);
			IModel exampleModel = new ProblemModel(exampleJungle, null, exampleTime);
			exampleModel.setUnitOfTime("d");
			assertEquals(2.709888209953704E-4, exampleModel.calculateTimeInUnitGivenByModel((long) 23413434134.0),
					"\nThe calculated time does not match the expected value.");
		}

		@DisplayName("Simple test of setUnitOfTime, part 1")
		@Test
		void testSetTimeUnitOne() {
			Double[] exampleTime = { 0.0000004 };
			Jungle exampleJungle = new Jungle(3, 4, "asdertg", 1);
			IModel exampleModel = new ProblemModel(exampleJungle, null, exampleTime);
			exampleModel.setUnitOfTime("d");
			assertEquals("d", exampleModel.getUnitOfTime(), "\nThe unit of time does not match the expected value.");
		}

		@DisplayName("Simple test of setUnitOfTime, part 2")
		@Test
		void testSetTimeUnitTwo() {
			Double[] exampleTime = { 0.0000004 };
			Jungle exampleJungle = new Jungle(3, 4, "asdertg", 1);
			IModel exampleModel = new ProblemModel(exampleJungle, null, exampleTime);
			assertThrows(Exception.class, () -> exampleModel.setUnitOfTime("wrong"),
					"\nAn invalid unit of time was set, but no exception was thrown.");
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

		static DoubleStream generateNegativeParametervaluesDouble() {
			return DoubleStream.iterate(randomNumberGenerator.nextDouble(-10.0, -1.0), i -> i - 7.6).limit(10);
		}

		static DoubleStream generatePositiveParametervaluesDouble() {
			return DoubleStream.iterate(randomNumberGenerator.nextDouble(1.0, 10.0), i -> i + 7.6).limit(20);
		}

		@DisplayName("Parameterized positive test for constructor and getTime with one entry.")
		@ParameterizedTest
		@MethodSource("generatePositiveParametervaluesDouble")
		void testConstructorAndGetTimePositiveOneParameter(Double time) {
			Double[] exampleTime = { time };
			ProblemModel model = new ProblemModel(exampleJungle, exampleSolution, exampleTime);
			assertArrayEquals(model.getTime(), exampleTime,
					"\nOne or more of the passed values do not match the values obtained with getTime().");
		}

		@DisplayName("Parameterized positive test for constructor and getTime with two entries.")
		@ParameterizedTest
		@MethodSource("generatePositiveParametervaluesDouble")
		void testConstructorAndGetTimePositiveTwoParameters(Double time) {
			Double[] exampleTime = { time, time + 3.874 };
			ProblemModel model = new ProblemModel(exampleJungle, exampleSolution, exampleTime);
			assertArrayEquals(model.getTime(), exampleTime,
					"\nOne or more of the passed values do not match the values obtained with getTime().");
		}

		@DisplayName("Parameterized positive test for constructor and getTime with one entry.")
		@ParameterizedTest
		@MethodSource("generateNegativeParametervaluesDouble")
		void testConstructorAndGetTimeNegativeOneParameter(Double time) {
			Double[] exampleTime = { time };
			assertThrows(IllegalArgumentException.class, () -> new ProblemModel(exampleJungle, exampleSolution, exampleTime),
					"\nOne or more entries in 'time' are negative, but no exception was thrown.");
		}

		@DisplayName("Parameterized positive test for constructor and getTime with two entries.")
		@ParameterizedTest
		@MethodSource("generateNegativeParametervaluesDouble")
		void testConstructorAndGetTimeNegativeTwoParameters(Double time) {
			Double[] exampleTime = { time, time + 3.874 };
			assertThrows(IllegalArgumentException.class, () -> new ProblemModel(exampleJungle, exampleSolution, exampleTime),
					"\nOne or more entries in 'time' are negative, but no exception was thrown.");
		}

		@DisplayName("Parameterized positive test of setTime().")
		@ParameterizedTest
		@MethodSource("generatePositiveParametervaluesDouble")
		void testSetTimePositive(Double time) {
			Double[] exampleTime = { time };
			IModel exampleModel = new ProblemModel(exampleJungle, exampleSolution, exampleTime);
			assertEquals(time, exampleModel.getTime()[0],
					"\nThe given value for 'time' ('" + time
							+ "') does not match the obtained value for 'time', which is '"
							+ exampleModel.getTime()[0] + "'.");
		}

		@DisplayName("Parameterized positive test of setTime() with two entries.")
		@ParameterizedTest
		@MethodSource("generatePositiveParametervaluesDouble")
		void testSetTimePositiveWithTwoParameters(Double time) {
			Double[] exampleTime = { time, time + 4.5 };
			IModel exampleModel = new ProblemModel(exampleJungle, exampleSolution, exampleTime);
			assertArrayEquals(exampleTime, exampleModel.getTime(),
					"\nThe given value for 'time' does not match the obtained value for 'time'.");
		}

		@DisplayName("Parameterized negative test of setTime().")
		@ParameterizedTest
		@MethodSource("generateNegativeParametervaluesDouble")
		void testSetTimeNegative(Double time) {
			Double[] initTime = { 0.0 };
			Double[] exampleTime = { time };
			assertThrows(IllegalArgumentException.class,
					() -> new ProblemModel(exampleJungle, exampleSolution, initTime).setTime(exampleTime),
					"\nNo exception was generated for one or more negative values in 'time'.");
		}

		@DisplayName("Parameterized negative test of setTime().")
		@ParameterizedTest
		@MethodSource("generateNegativeParametervaluesDouble")
		void testSetTimeNegativeWithTwoParameters(Double time) {
			Double[] initTime = { 0.0 };
			Double[] exampleTime = { time, time - 3.4 };
			assertThrows(IllegalArgumentException.class,
					() -> new ProblemModel(exampleJungle, exampleSolution, initTime).setTime(exampleTime),
					"\nNo exception was generated for one or more negative values in 'time'.");
		}
	}
}
