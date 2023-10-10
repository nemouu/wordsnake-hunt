package de.fuhagen.course01584.ss23.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;
import java.util.stream.DoubleStream;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * In der Klasse ProblemModellTest werden die Methoden der Klasse ProblemModell
 * getestet. Hierbei werden erst ein paar einfache Tests durchgefuehrt, die dazu
 * dienen sollen ein paar wichtige Randsituationen abzudecken. Ausserdem wird
 * hier auch die Methode toString mehrere Male getestet. Dann gibt es eine Reihe
 * von parametrisierten Tests, die die Methoden auf eine ganz normale Art und
 * Weise testen. Um einen Beispieldschungel und eine Beispielloesung fuer alle
 * Methoden zum Testen bereitstellen zu koennen werden diese in einer @BeforeAll
 * Methode initialisiert.
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
		@DisplayName("Einfacher Test des Konstruktors mit zu grossem Parameter fuer 'zeit'.")
		@Test
		void testConstructorLargeTime() {
			Double[] bspZeit = { Double.MAX_VALUE };
			assertThrows(IllegalArgumentException.class, () -> new ProblemModel(exampleJungle, exampleSolution, bspZeit),
					() -> "\nFuer den zu grossen Wert '" + Double.MAX_VALUE + "' wurde keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher Test des Konstruktors mit Parameter 0.0 fuer 'zeit'.")
		@Test
		void testConstructorTimeZero() {
			Double[] bspZeit = { 0.0 };
			assertThrows(IllegalArgumentException.class, () -> new ProblemModel(exampleJungle, exampleSolution, bspZeit),
					() -> "\nFuer den zu kleinen Wert '" + 0.0 + "' wurde keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher Test von setZeit() mit zu grossem Parameter.")
		@Test
		void testSetTimeLargeTime() {
			Double[] initZeit = { 3.4 };
			Double[] bspZeit = { Double.MAX_VALUE };
			assertThrows(IllegalArgumentException.class,
					() -> new ProblemModel(exampleJungle, exampleSolution, initZeit).setTime(bspZeit),
					() -> "\nFuer den zu grossen Wert '" + Double.MAX_VALUE + "' wurde keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher Test von setZeit() mit Parameter 0.0.")
		@Test
		void testSetTimeZero() {
			Double[] initZeit = { 3.4 };
			Double[] bspZeit = { 0.0 };
			assertThrows(IllegalArgumentException.class,
					() -> new ProblemModel(exampleJungle, exampleSolution, initZeit).setTime(bspZeit),
					() -> "\nFuer den zu grossen Wert '" + 0.0 + "' wurde keine Ausnahme erzeugt.");
		}

		@DisplayName("Test toString Methode, wenn moeglichst viel leer ist, Teil 1")
		@Test
		void testToStringEmptyOne() {
			Double[] bspZeit = { 3.0 };
			IModel bspModell = new ProblemModel(new Jungle(0, 0, null, 1), exampleSolution, bspZeit);
			assertEquals("Der Dschungel dieses Problemes hat 0 Zeilen, 0 Spalten und die Zeichenmenge 'null' \naber "
					+ "keine Felder und es kann nach Schlangen der Schlangenart/en \n\n"
					+ "\ngesucht werden. Die Felder koennen mit dem Befehl 'e' erzeugt werden. Die Loesung hierzu ist "
					+ "nicht \nvorhanden und kann mit dem Befehl 'l' gesucht werden.", bspModell.toString(),
					"\nEs wird nicht der entsprechende String zurueckgegeben, obwohl das Modell leer ist.");
		}

		@DisplayName("Test toString Methode, wenn moeglichst viel leer ist, Teil 2")
		@Test
		void testToStringTwo() {
			Double[] bspZeit = { 3.0 };
			IModel bspModell = new ProblemModel(new Jungle(3, 3, "FERNUNI", 1), exampleSolution, bspZeit);
			assertEquals("Der Dschungel dieses Problemes hat 3 Zeilen, 3 Spalten und die "
					+ "Zeichenmenge 'FERNUNI' \naber keine Felder und es kann nach Schlangen der Schlangenart/en \n\n"
					+ "\ngesucht werden. Die Felder koennen mit dem Befehl 'e' erzeugt werden. Die Loesung hierzu ist "
					+ "nicht \nvorhanden und kann mit dem Befehl 'l' gesucht werden.", bspModell.toString(),
					"\nEs wird nicht der entsprechende String zurueckgegeben, obwohl das Modell leer ist.");
		}

		@DisplayName("Test toString Methode mit ohne Loesung")
		@Test
		void testToStringWithoutSolution() {
			Double[] bspZeit = { 3.0 };
			SnakeType art1 = new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 1, 1);
			SnakeType art2 = new SnakeType("A1", new DistanceNeighborhood(3), "JUNITTEST", 2, 4);
			Jungle bspDschungel = new Jungle(3, 4, "asdertg", 1);
			for (int i = 0; i < bspDschungel.getRows(); i++) {
				for (int j = 0; j < bspDschungel.getColumns(); j++) {
					bspDschungel.getFields()[i][j].setCharacter("a");
				}
			}
			IModel bspModell = new ProblemModel(bspDschungel, null, bspZeit);
			bspModell.addSnakeType(art1);
			bspModell.addSnakeType(art2);
			assertEquals("Der Dschungel dieses Problemes hat 3 Zeilen, " + "4 Spalten und die Zeichenmenge 'asdertg'. "
					+ "Die Felder werden\nimmer in dem Format '(Zeichen, Verwendbarkeit, Punkte)' angegeben und"
					+ " sind wie folgt angeordnet: \n\n\n (a,1,1) (a,1,1) (a,1,1) (a,1,1)\n\n (a,1,1) (a,1,1) (a,1,1) (a,1,1)"
					+ "\n\n (a,1,1) (a,1,1) (a,1,1) (a,1,1)\n\n\nEs kann nach Schlangen" + " der Schlangenart/en \n\n"
					+ " (ID=A0, Nachbarschaftsstruktur=Distanz, Zeichenkette=FERNUNI, Punkte=1, Anzahl=1)\n"
					+ " (ID=A1, Nachbarschaftsstruktur=Distanz, Zeichenkette=JUNITTEST, Punkte=2, Anzahl=4)"
					+ "\n\ngesucht werden und im Modell ist aktuell keine Loesung vorhanden. Es kann mit dem Befehl 'l' nach einer"
					+ " Loesung\ngesucht werden.", bspModell.toString(),
					"\nEs wird nicht der richtige String zurueckgegeben.");

		}

		@DisplayName("Einfacher Test von berechneZeitInNanosekunden, Teil 1 ")
		@Test
		void testCalculateTimeInNanosecondsOne() {
			Double[] bspZeit = { 3.0 };
			Jungle bspDschungel = new Jungle(3, 4, "asdertg", 1);
			IModel bspModell = new ProblemModel(bspDschungel, null, bspZeit);
			assertEquals(3.0E9, bspModell.calculateTimeToNanoseconds(bspZeit[0]),
					"\nDie errechnete Zeit stimmt " + "nicht mit dem zu erwartenden Wert ueberein.");
		}

		@DisplayName("Einfacher Test von berechneZeitInNanosekunden, Teil 2 ")
		@Test
		void testCalculateTimeInNanosecondsTwo() {
			Double[] bspZeit = { 2500.0 };
			Jungle bspDschungel = new Jungle(3, 4, "asdertg", 1);
			IModel bspModell = new ProblemModel(bspDschungel, null, bspZeit);
			bspModell.setUnitOfTime("ms");
			assertEquals(2.5E9, bspModell.calculateTimeToNanoseconds(bspZeit[0]),
					"\nDie errechnete Zeit stimmt " + "nicht mit dem zu erwartenden Wert ueberein.");
		}

		@DisplayName("Einfacher Test von berechneZeitInNanosekunden, Teil 3 ")
		@Test
		void testCalculateTimeInNanosecondsThree() {
			Double[] bspZeit = { 0.03 };
			Jungle bspDschungel = new Jungle(3, 4, "asdertg", 1);
			IModel bspModell = new ProblemModel(bspDschungel, null, bspZeit);
			bspModell.setUnitOfTime("min");
			assertEquals(1.8E9, bspModell.calculateTimeToNanoseconds(bspZeit[0]),
					"\nDie errechnete Zeit stimmt " + "nicht mit dem zu erwartenden Wert ueberein.");
		}

		@DisplayName("Einfacher Test von berechneZeitInNanosekunden, Teil 4 ")
		@Test
		void testCalculateTimeInNanosecondsFour() {
			Double[] bspZeit = { 0.00015 };
			Jungle bspDschungel = new Jungle(3, 4, "asdertg", 1);
			IModel bspModell = new ProblemModel(bspDschungel, null, bspZeit);
			bspModell.setUnitOfTime("h");
			assertEquals(5.4E8, bspModell.calculateTimeToNanoseconds(bspZeit[0]),
					"\nDie errechnete Zeit stimmt " + "nicht mit dem zu erwartenden Wert ueberein.");
		}

		@DisplayName("Einfacher Test von berechneZeitInNanosekunden, Teil 5 ")
		@Test
		void testCalculateTimeInNanosecondsFive() {
			Double[] bspZeit = { 0.0000004 };
			Jungle bspDschungel = new Jungle(3, 4, "asdertg", 1);
			IModel bspModell = new ProblemModel(bspDschungel, null, bspZeit);
			bspModell.setUnitOfTime("d");
			assertEquals(3.456E7, bspModell.calculateTimeToNanoseconds(bspZeit[0]),
					"\nDie errechnete Zeit stimmt " + "nicht mit dem zu erwartenden Wert ueberein.");
		}

		@DisplayName("Einfacher Test von berechneZeitInModellEinheit, Teil 1 ")
		@Test
		void testCalculateTimeInUnitOfModelOne() {
			Double[] bspZeit = { 3.0 };
			Jungle bspDschungel = new Jungle(3, 4, "asdertg", 1);
			IModel bspModell = new ProblemModel(bspDschungel, null, bspZeit);
			assertEquals(23.413434134, bspModell.calculateTimeInUnitGivenByModel((long) 23413434134.0),
					"\nDie errechnete Zeit stimmt " + "nicht mit dem zu erwartenden Wert ueberein.");
		}

		@DisplayName("Einfacher Test von berechneZeitInModellEinheit, Teil 2 ")
		@Test
		void testCalculateTimeInUnitOfModelTwo() {
			Double[] bspZeit = { 2500.0 };
			Jungle bspDschungel = new Jungle(3, 4, "asdertg", 1);
			IModel bspModell = new ProblemModel(bspDschungel, null, bspZeit);
			bspModell.setUnitOfTime("ms");
			assertEquals(23413.434134, bspModell.calculateTimeInUnitGivenByModel((long) 23413434134.0),
					"\nDie errechnete Zeit stimmt " + "nicht mit dem zu erwartenden Wert ueberein.");
		}

		@DisplayName("Einfacher Test von berechneZeitInModellEinheit, Teil 3 ")
		@Test
		void testCalculateTimeInUnitOfModelThree() {
			Double[] bspZeit = { 0.03 };
			Jungle bspDschungel = new Jungle(3, 4, "asdertg", 1);
			IModel bspModell = new ProblemModel(bspDschungel, null, bspZeit);
			bspModell.setUnitOfTime("min");
			assertEquals(0.3902239022333333, bspModell.calculateTimeInUnitGivenByModel((long) 23413434134.0),
					"\nDie errechnete Zeit stimmt " + "nicht mit dem zu erwartenden Wert ueberein.");
		}

		@DisplayName("Einfacher Test von berechneZeitInModellEinheit, Teil 4 ")
		@Test
		void testCalculateTimeInUnitOfModelFour() {
			Double[] bspZeit = { 0.00015 };
			Jungle bspDschungel = new Jungle(3, 4, "asdertg", 1);
			IModel bspModell = new ProblemModel(bspDschungel, null, bspZeit);
			bspModell.setUnitOfTime("h");
			assertEquals(0.006503731703888889, bspModell.calculateTimeInUnitGivenByModel((long) 23413434134.0),
					"\nDie errechnete Zeit stimmt " + "nicht mit dem zu erwartenden Wert ueberein.");
		}

		@DisplayName("Einfacher Test von berechneZeitInModellEinheit, Teil 5 ")
		@Test
		void testCalculateTimeInUnitOfModelFive() {
			Double[] bspZeit = { 0.0000004 };
			Jungle bspDschungel = new Jungle(3, 4, "asdertg", 1);
			IModel bspModell = new ProblemModel(bspDschungel, null, bspZeit);
			bspModell.setUnitOfTime("d");
			assertEquals(2.709888209953704E-4, bspModell.calculateTimeInUnitGivenByModel((long) 23413434134.0),
					"\nDie errechnete Zeit stimmt " + "nicht mit dem zu erwartenden Wert ueberein.");
		}

		@DisplayName("Einfacher Test von setZeiteinheit, Teil 1 ")
		@Test
		void testSetTimeUnitOne() {
			Double[] bspZeit = { 0.0000004 };
			Jungle bspDschungel = new Jungle(3, 4, "asdertg", 1);
			IModel bspModell = new ProblemModel(bspDschungel, null, bspZeit);
			bspModell.setUnitOfTime("d");
			assertEquals("d", bspModell.getUnitOfTime(), "\nDie Zeiteinheit entspricht nicht dem erwarteten Wert.");
		}

		@DisplayName("Einfacher Test von setZeiteinheit, Teil 2 ")
		@Test
		void testSetTimeUnitTwo() {
			Double[] bspZeit = { 0.0000004 };
			Jungle bspDschungel = new Jungle(3, 4, "asdertg", 1);
			IModel bspModell = new ProblemModel(bspDschungel, null, bspZeit);
			assertThrows(Exception.class, () -> bspModell.setUnitOfTime("falsch"),
					"\nEs wird eine ungueltige Zeiteinheit gesetzt aber es wird keine Ausnahme ausgeloest.");
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

		@DisplayName("Parameterisierter positiver Test fuer Konstruktor und getZeit mit einem Eintrag.")
		@ParameterizedTest
		@MethodSource("erzeugePositiveParameterwerteDouble")
		void testConstructorAndGetTimePositiveOneParameter(Double zeit) {
			Double[] bspDouble = { zeit };
			ProblemModel modell = new ProblemModel(exampleJungle, exampleSolution, bspDouble);
			assertArrayEquals(modell.getTime(), bspDouble,
					"\nEiner oder mehrere der uebergebenen Werte stimmen nicht mit den Werten ueberein, "
							+ "die mit getZeit() erhalten werden.");
		}

		@DisplayName("Parameterisierter positiver Test fuer Konstruktor und getZeit mit zwei Eintraegen.")
		@ParameterizedTest
		@MethodSource("erzeugePositiveParameterwerteDouble")
		void testConstructorAndGetTimePositiveTwoParameters(Double zeit) {
			Double[] bspDouble = { zeit, zeit + 3.874 };
			ProblemModel modell = new ProblemModel(exampleJungle, exampleSolution, bspDouble);
			assertArrayEquals(modell.getTime(), bspDouble,
					"\nEiner oder mehrere der uebergebenen Werte stimmen nicht mit den Werten ueberein, "
							+ "die mit getZeit() erhalten werden.");
		}

		@DisplayName("Parameterisierter positiver Test fuer Konstruktor und getZeit mit einem Eintrag.")
		@ParameterizedTest
		@MethodSource("erzeugeNegativeParameterwerteDouble")
		void testConstructorAndGetTimeNegativeOneParameter(Double zeit) {
			Double[] bspDouble = { zeit };
			assertThrows(IllegalArgumentException.class, () -> new ProblemModel(exampleJungle, exampleSolution, bspDouble),
					() -> "\nEin oder mehrere Eintraege in 'zeit' sind negativ und es wird keine Ausnahme ausgeloest.");
		}

		@DisplayName("Parameterisierter positiver Test fuer Konstruktor und getZeit mit zwei Eintraegen.")
		@ParameterizedTest
		@MethodSource("erzeugeNegativeParameterwerteDouble")
		void testConstructorAndGetTimeNegativeTwoParameters(Double zeit) {
			Double[] bspDouble = { zeit, zeit + 3.874 };
			assertThrows(IllegalArgumentException.class, () -> new ProblemModel(exampleJungle, exampleSolution, bspDouble),
					() -> "\nEin oder mehrere Eintraege in 'zeit' sind negativ und es wird keine Ausnahme ausgeloest.");
		}

		@DisplayName("Parametrisierter positiver Test von setZeit().")
		@ParameterizedTest
		@MethodSource("erzeugePositiveParameterwerteDouble")
		void testSetTimePositive(Double zeit) {
			Double[] bspZeit = { zeit };
			IModel bspModell = new ProblemModel(exampleJungle, exampleSolution, bspZeit);
			assertEquals(zeit, bspModell.getTime()[0],
					"\nDer gegebene Wert fuer 'zeit' ('" + zeit
							+ "') entspricht nicht dem erhaltenen Wert fuer 'zeit', denn der ist '"
							+ bspModell.getTime()[0] + "'.");
		}

		@DisplayName("Parametrisierter positiver Test von setZeit() mit zwei Eintraegen.")
		@ParameterizedTest
		@MethodSource("erzeugePositiveParameterwerteDouble")
		void testSetTimePositiveWithTwoParameters(Double zeit) {
			Double[] bspZeit = { zeit, zeit + 4.5 };
			IModel bspModell = new ProblemModel(exampleJungle, exampleSolution, bspZeit);
			assertArrayEquals(bspZeit, bspModell.getTime(),
					"\nDer gegebene Wert fuer 'zeit' entspricht nicht dem erhaltenen Wert fuer 'zeit'.");
		}

		@DisplayName("Parametrisierter negativer Test von setZeit().")
		@ParameterizedTest
		@MethodSource("erzeugeNegativeParameterwerteDouble")
		void testSetTimeNegative(Double zeit) {
			Double[] initZeit = { 0.0 };
			Double[] bspZeit = { zeit };
			assertThrows(IllegalArgumentException.class,
					() -> new ProblemModel(exampleJungle, exampleSolution, initZeit).setTime(bspZeit),
					() -> "\nFuer einen oder mehrere negative Werte in 'zeit' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Parametrisierter negativer Test von setZeit().")
		@ParameterizedTest
		@MethodSource("erzeugeNegativeParameterwerteDouble")
		void testSetTimeNegativeWithTwoParameters(Double zeit) {
			Double[] initZeit = { 0.0 };
			Double[] bspZeit = { zeit, zeit - 3.4 };
			assertThrows(IllegalArgumentException.class,
					() -> new ProblemModel(exampleJungle, exampleSolution, initZeit).setTime(bspZeit),
					() -> "\nFuer einen oder mehrere negative Werte in 'zeit' wird keine Ausnahme erzeugt.");
		}
	}
}
