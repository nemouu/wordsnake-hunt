package de.fuhagen.course01584.ss23.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.util.stream.IntStream;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * In der Klasse SternNachbarschaftTest werden die Methoden der Klasse
 * SternNachbarschaft getestet. Hierbei werden erst ein paar einfache Tests
 * durchgefuehrt, die dazu dienen sollen ein paar wichtige Randsituationen
 * abzudecken. Dann gibt es eine Reihe von parametrisierten Tests, die die
 * Methoden auf eine ganz normale Art und Weise testen. Die Methode getNachbarn
 * wird in eigenen Testklassen getestet. Sowohl in einer normalen Situation als
 * auch in Randsituationen und auch mit verschiedenen Parametern. Um alles auch
 * nochmal von Hand ueberrpuefen zu koennen wird der fuer den Test erzeugte
 * Dschungel einmal auf die Konsole ausgegeben.
 * 
 * @author Philip Redecker
 */
class StarNeighborhoodTest {

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Simple_Tests {
		@DisplayName("Einfacher positiver Test fuer setParameters.")
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
					"\nDie Parameter '" + starNeighborhood.getParameters().get(0) + "' und '"
							+ starNeighborhood.getParameters().get(1) + "' entsprechen nicht dem vorgegebenen Werten '"
							+ param3 + "' und '" + param4 + "'.");
		}

		@DisplayName("Einfacher Test fuer setParameters mit zu grossen Werten in der Liste")
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
					() -> "\nEiner oder mehrere der Parameterwerte '" + param1 + "' und '" + param2
							+ "' erzeugt/erzeugen keine Ausnahme, obwohl er/sie zu gross ist/sind.");
		}

		@DisplayName("Einfacher Test fuer setParameters mit zu kleinen Werten in der Liste")
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
					() -> "\nEiner oder mehrere der Parameterwerte '" + param1 + "' und '" + param2
							+ "' erzeugt/erzeugen keine Ausnahme, obwohl er/sie zu klein ist/sind.");
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

			// Es wird hier der erzeugte Dschungel auf die Konsole ausgegeben, damit
			// zusaetzlich auch durch Nachschauen getestet werden kann
			System.out.println();
			System.out.println(
					"Es wird der Dschungel ausgegeben, damit zusaetzlich eine Pruefung von Hand moeglich ist:");
			System.out.println();
			System.out.println(exampleJungle.toString());
		}

		@DisplayName("Test fuer getNachbarn mit den Parametern 1 und 2 in der Mitte.")
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
					"\nDie erhaltene Liste hat die Groesse "
							+ exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size()
							+ " aber es wurde eine Liste mit Groesse " + neighborFields.size() + " erwartet.");
			assertTrue(neighborFields.containsAll(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested)),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
			assertTrue(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).containsAll(neighborFields),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
		}

		@DisplayName("Test fuer getNachbarn mit den Parametern 1 und 2 in der linken oberen Ecke.")
		@Test
		void testNeighborsWithParametersOneAndTwoTopLeftCorner() {
			Field fieldToBeTested = exampleJungle.getFields()[0][0];
			HashSet<Field> neighborFields = new HashSet<Field>();
			neighborFields.add(exampleJungle.getFields()[0][1]);
			neighborFields.add(exampleJungle.getFields()[1][0]);
			neighborFields.add(exampleJungle.getFields()[1][1]);
			neighborFields.add(exampleJungle.getFields()[2][2]);
			assertEquals(neighborFields.size(), exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size(),
					"\nDie erhaltene Liste hat die Groesse "
							+ exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size()
							+ " aber es wurde eine Liste mit Groesse " + neighborFields.size() + " erwartet.");
			assertTrue(neighborFields.containsAll(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested)),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
			assertTrue(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).containsAll(neighborFields),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
		}

		@DisplayName("Test fuer getNachbarn mit den Parametern 1 und 2 in der rechten oberen Ecke.")

		@Test
		void testNeighborsWithParametersOneAndTwoTopRightCorner() {
			Field fieldToBeTested = exampleJungle.getFields()[0][7];
			HashSet<Field> neighborFields = new HashSet<Field>();
			neighborFields.add(exampleJungle.getFields()[0][6]);
			neighborFields.add(exampleJungle.getFields()[1][7]);
			neighborFields.add(exampleJungle.getFields()[1][6]);
			neighborFields.add(exampleJungle.getFields()[2][5]);
			assertEquals(neighborFields.size(), exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size(),
					"\nDie erhaltene Liste hat die Groesse "
							+ exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size()
							+ " aber es wurde eine Liste mit Groesse " + neighborFields.size() + " erwartet.");
			assertTrue(neighborFields.containsAll(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested)),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
			assertTrue(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).containsAll(neighborFields),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
		}

		@DisplayName("Test fuer getNachbarn mit den Parametern 1 und 2 in der linken unteren Ecke.")

		@Test
		void testNeighborsWithParametersOneAndTwoBottomLeftCorner() {
			Field fieldToBeTested = exampleJungle.getFields()[5][0];
			HashSet<Field> neighborFields = new HashSet<Field>();
			neighborFields.add(exampleJungle.getFields()[4][0]);
			neighborFields.add(exampleJungle.getFields()[4][1]);
			neighborFields.add(exampleJungle.getFields()[5][1]);
			neighborFields.add(exampleJungle.getFields()[3][2]);
			assertEquals(neighborFields.size(), exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size(),
					"\nDie erhaltene Liste hat die Groesse "
							+ exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size()
							+ " aber es wurde eine Liste mit Groesse " + neighborFields.size() + " erwartet.");
			assertTrue(neighborFields.containsAll(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested)),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
			assertTrue(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).containsAll(neighborFields),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
		}

		@DisplayName("Test fuer getNachbarn mit den Parametern 1 und 2 in der rechten unteren Ecke.")

		@Test
		void testNeighborsWithParametersOneAndTwoBottomRightCorner() {
			Field fieldToBeTested = exampleJungle.getFields()[5][7];
			HashSet<Field> neighborFields = new HashSet<Field>();
			neighborFields.add(exampleJungle.getFields()[4][7]);
			neighborFields.add(exampleJungle.getFields()[4][6]);
			neighborFields.add(exampleJungle.getFields()[5][6]);
			neighborFields.add(exampleJungle.getFields()[3][5]);
			assertEquals(neighborFields.size(), exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size(),
					"\nDie erhaltene Liste hat die Groesse "
							+ exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size()
							+ " aber es wurde eine Liste mit Groesse " + neighborFields.size() + " erwartet.");
			assertTrue(neighborFields.containsAll(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested)),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
			assertTrue(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).containsAll(neighborFields),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
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

		@DisplayName("Test fuer getNachbarn mit den Parametern 0 und 3 in der Mitte.")
		@Test
		void testNeighborsWithParametersThreeAndZeroMiddle() {
			Field fieldToBeTested = exampleJungle.getFields()[3][3];
			HashSet<Field> neighborFields = new HashSet<Field>();
			neighborFields.add(exampleJungle.getFields()[3][0]);
			neighborFields.add(exampleJungle.getFields()[0][3]);
			neighborFields.add(exampleJungle.getFields()[3][6]);
			assertEquals(neighborFields.size(), exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size(),
					"\nDie erhaltene Liste hat die Groesse "
							+ exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size()
							+ " aber es wurde eine Liste mit Groesse " + neighborFields.size() + " erwartet.");
			assertTrue(neighborFields.containsAll(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested)),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
			assertTrue(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).containsAll(neighborFields),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
		}

		@DisplayName("Test fuer getNachbarn mit den Parametern 0 und 3 in der linken oberen Ecke.")
		@Test
		void testNeighborsWithParametersThreeAndZeroTopLeftCorner() {
			Field fieldToBeTested = exampleJungle.getFields()[0][0];
			HashSet<Field> neighborFields = new HashSet<Field>();
			neighborFields.add(exampleJungle.getFields()[3][0]);
			neighborFields.add(exampleJungle.getFields()[0][3]);
			assertEquals(neighborFields.size(), exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size(),
					"\nDie erhaltene Liste hat die Groesse "
							+ exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size()
							+ " aber es wurde eine Liste mit Groesse " + neighborFields.size() + " erwartet.");
			assertTrue(neighborFields.containsAll(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested)),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
			assertTrue(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).containsAll(neighborFields),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
		}

		@DisplayName("Test fuer getNachbarn mit den Parametern 0 und 3 in der rechten oberen Ecke.")

		@Test
		void testNeighborsWithParametersThreeAndZeroTopRightCorner() {
			Field fieldToBeTested = exampleJungle.getFields()[0][7];
			HashSet<Field> neighborFields = new HashSet<Field>();
			neighborFields.add(exampleJungle.getFields()[0][4]);
			neighborFields.add(exampleJungle.getFields()[3][7]);
			assertEquals(neighborFields.size(), exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size(),
					"\nDie erhaltene Liste hat die Groesse "
							+ exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size()
							+ " aber es wurde eine Liste mit Groesse " + neighborFields.size() + " erwartet.");
			assertTrue(neighborFields.containsAll(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested)),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
			assertTrue(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).containsAll(neighborFields),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
		}

		@DisplayName("Test fuer getNachbarn mit den Parametern 0 und 3 in der linken unteren Ecke.")

		@Test
		void testNeighborsWithParametersThreeAndZeroBottomLeftCorner() {
			Field fieldToBeTested = exampleJungle.getFields()[5][0];
			HashSet<Field> neighborFields = new HashSet<Field>();
			neighborFields.add(exampleJungle.getFields()[2][0]);
			neighborFields.add(exampleJungle.getFields()[5][3]);
			assertEquals(neighborFields.size(), exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size(),
					"\nDie erhaltene Liste hat die Groesse "
							+ exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size()
							+ " aber es wurde eine Liste mit Groesse " + neighborFields.size() + " erwartet.");
			assertTrue(neighborFields.containsAll(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested)),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
			assertTrue(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).containsAll(neighborFields),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
		}

		@DisplayName("Test fuer getNachbarn mit den Parametern 0 und 3 in der rechten unteren Ecke.")

		@Test
		void testNeighborsWithParametersThreeAndZeroBottomRightCorner() {
			Field fieldToBeTested = exampleJungle.getFields()[5][7];
			HashSet<Field> neighborFields = new HashSet<Field>();
			neighborFields.add(exampleJungle.getFields()[2][7]);
			neighborFields.add(exampleJungle.getFields()[5][4]);
			assertEquals(neighborFields.size(), exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size(),
					"\nDie erhaltene Liste hat die Groesse "
							+ exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).size()
							+ " aber es wurde eine Liste mit Groesse " + neighborFields.size() + " erwartet.");
			assertTrue(neighborFields.containsAll(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested)),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
			assertTrue(exampleNeighborhood.getNeighbors(exampleJungle, fieldToBeTested).containsAll(neighborFields),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
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

		@DisplayName("Parameterisierter positiver Test fuer Konstruktor und getParameters.")
		@ParameterizedTest
		@MethodSource("generatePositiveParametervalues")
		void testConstructorAndGetParametersPositive(int param) {
			List<Integer> paramList = new ArrayList<Integer>();
			paramList.add(param);
			paramList.add(param + 1);
			INeighborhood starNeighborhood = new StarNeighborhood(paramList);
			assertIterableEquals(starNeighborhood.getParameters(), paramList,
					"\nDie Parameter '" + starNeighborhood.getParameters().get(0) + "' und '"
							+ starNeighborhood.getParameters().get(1) + "' entsprechen nicht dem vorgegebenen Werten '"
							+ param + "' und " + (param + 1) + "'.");
		}

		@DisplayName("Parameterisierter Test fuer Konstruktor mit negativem Parameter.")
		@ParameterizedTest
		@MethodSource("generateNegativeParametervalues")
		void testConstructorNegativeParameters(int param) {
			List<Integer> paramList = new ArrayList<Integer>();
			paramList.add(param);
			paramList.add(param - 1);
			assertThrows(IllegalArgumentException.class, () -> new StarNeighborhood(paramList),
					() -> "\nEiner oder mehrere der Parameterwerte '" + param + "' und '" + (param - 1)
							+ "' erzeugt/erzeugen keine Ausnahme, obwohl er/sie negativ ist/sind.");
		}

		@DisplayName("Parameterisierter Test fuer Konstruktor mit zu langer Eingabe.")
		@ParameterizedTest
		@MethodSource("generatePositiveParametervalues")
		void testConstructorParameterlistTooLong(int param) {
			List<Integer> paramList = new ArrayList<Integer>();
			paramList.add(param);
			paramList.add(param + 1);
			paramList.add(param + 2);
			assertThrows(IllegalArgumentException.class, () -> new StarNeighborhood(paramList),
					() -> "\nDie Eingabeliste hat die Groesse '" + paramList.size() + "' aber erzeugt keine Ausnahme.");
		}

		@DisplayName("Parameterisierter negativer Test fuer setParameters.")
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
					() -> "\nEiner oder mehrere der Parameterwerte '" + param + "' und '" + (param - 1)
							+ "' erzeugt/erzeugen keine Ausnahme, obwohl er/sie negativ ist/sind.");
		}

		@DisplayName("Parameterisierter Test fuer setParameters mit zu grosser Liste.")
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
					() -> "\nFuer die zu grosse Liste wird keine Ausnahme erzeugt.");
		}
	}
}