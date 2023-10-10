package de.fuhagen.course01584.ss23.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;
import java.util.stream.IntStream;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * In der Klasse FeldTest werden die Methoden der Klasse Feld getestet. Hierbei
 * werden alle Methoden parametrisierten Tests unterzogen, die Ausnahmen
 * hervorrufen oder die sonstige Logik enthalten. In diesem Fall sind das vor
 * allem die Konstruktoren und die Getter und Setter fuer 'zeile', 'spalte' und
 * 'verwendbarkeit'. Die uebrigen Getter und Setter wurden der Einfachheit wegen
 * uebersprungen. Zusaetzlich dazu werden bestimmte Sonderfaelle in einfachen
 * Test betrachtet.
 * 
 * @author Philip Redecker
 */
class FieldTest {

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Simple_Tests {
		@DisplayName("Einfacher positiver Test.")
		@Test
		void testGetRow() {
			int row = 1;
			Field field = new Field(row, 0, 1);
			assertEquals(field.getRow(), row, () -> "\nDer Zeilenwert '" + field.getRow()
					+ "' entspricht nicht dem vorgegebenen Wert '" + row + "'.");
		}

		@DisplayName("Einfacher positiver Test bezueglich Attribut 'punkte'.")
		@Test
		void testPoints() {
			int points = 1;
			Field field = new Field("", 0, 0, 0, points, "");
			assertEquals(field.getPoints(), points, () -> "\nDer Zeilenwert '" + field.getPoints()
					+ "' entspricht nicht dem vorgegebenen Wert '" + points + "'.");
		}

		@DisplayName("Einfacher negativer Test.")
		@Test
		void testGetColumn() {
			int column = -1;
			assertThrows(IllegalArgumentException.class, () -> new Field(0, column, 1),
					() -> "\nFuer den (negativen) Spaltenwert '" + column + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher positiver Test bezueglich Attribut 'punkte' mit zu grossen Wert.")
		@Test
		void testPointsLarge() {
			int points = Integer.MAX_VALUE;
			assertThrows(IllegalArgumentException.class, () -> new Field("", 0, 0, 0, points, ""),
					() -> "Fuer den" + "zu grossen Wert von 'punkte '" + points + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher negativer Test bezueglich Attribut 'punkte' mit zu kleinem Wert.")
		@Test
		void testPointsSmall() {
			int points = Integer.MIN_VALUE;
			assertThrows(IllegalArgumentException.class, () -> new Field("", 0, 0, 0, points, ""),
					() -> "Fuer den" + "zu kleinen Wert von 'punkte '" + points + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher positiver Test von setPunkte mit zu grossen Wert.")
		@Test
		void testSetPointsLarge() {
			int points = Integer.MAX_VALUE;
			assertThrows(IllegalArgumentException.class, () -> new Field("", 0, 0, 0, 0, "").setPoints(points),
					() -> "Fuer den" + "zu grossen Wert von 'punkte '" + points + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher negativer Test von setPunkte mit zu kleinem Wert.")
		@Test
		void testSetPointsSmall() {
			int points = Integer.MIN_VALUE;
			assertThrows(IllegalArgumentException.class, () -> new Field("", 0, 0, 0, 0, "").setPoints(points),
					() -> "Fuer den" + "zu grossen Wert von 'punkte '" + points + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher positiver Test mit zu grosser Zahl in Zeile.")
		@Test
		void testGetRowMax() {
			int row = Integer.MAX_VALUE + 1;
			assertThrows(IllegalArgumentException.class, () -> new Field(row, 0, 1),
					() -> "\nFuer den zu grossen " + "Zeilenwert '" + row + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher positiver Test mit zu grosser Zahl in Spalte.")
		@Test
		void testGetColumnMax() {
			int column = Integer.MAX_VALUE + 1;
			assertThrows(IllegalArgumentException.class, () -> new Field(0, column, 1),
					() -> "\nFuer den zu grossen " + "Spaltenwert '" + column + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher positiver Test mit zu grosser Zahl in Verwendbarkeit.")
		@Test
		void testGetUsageMax() {
			int usage = Integer.MAX_VALUE + 1;
			assertThrows(IllegalArgumentException.class, () -> new Field("", 0, 0, usage, 0, ""),
					() -> "\nFuer den zu grossen " + "Verwendbarkeitswert '" + usage + "' wird keine Ausnahme erzeugt.");
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

		@DisplayName("Wiederholter negativer Test.")
		@RepeatedTest(10)
		void testGetColumn(RepetitionInfo repetitionInfo) {
			int column = randomNumberGenerator.nextInt(-100000, -1);
			assertThrows(IllegalArgumentException.class, () -> new Field(0, column, 1),
					() -> "\nFuer den (negativen) Spaltenwert '" + column + "' wird keine Ausnahme erzeugt.");
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

		@DisplayName("Parameterisierter positiver Test fuer Konstruktor und getSpalte.")
		@ParameterizedTest
		@MethodSource("generatePositiveParametervalues")
		void testConstructorAndGetColumnPositive(int column) {
			Field field = new Field(0, column, 1);
			assertEquals(field.getColumn(), column, "\nDer Spaltenwert '" + field.getColumn()
					+ "' entspricht nicht dem vorgegebenen Wert '" + column + "'.");
		}

		@DisplayName("Parameterisierter positiver Test fuer Konstruktor und getZeile.")
		@ParameterizedTest
		@MethodSource("generatePositiveParametervalues")
		void testConstructorAndGetRowPositive(int row) {
			Field field = new Field(row, 0, 1);
			assertEquals(field.getRow(), row, "\nDer Zeilenwert '" + field.getRow()
					+ "' entspricht nicht dem vorgegebenen Wert '" + row + "'.");
		}

		@DisplayName("Parameterisierter positiver Test fuer Konstruktor und getVerwendbarkeit.")
		@ParameterizedTest
		@MethodSource("generatePositiveParametervalues")
		void testConstructorAndGetUsagePositive(int usage) {
			Field field = new Field("", 0, 0, usage, 0, "");
			assertEquals(field.getUsage(), usage, "\nDer Verwendungswert '" + field.getUsage()
					+ "' entspricht nicht dem vorgegebenen Wert '" + usage + "'.");
		}

		@DisplayName("Parameterisierter Test fuer Konstruktor mit negativer Zeile.")
		@ParameterizedTest
		@MethodSource("generateNegativeParametervalues")
		void testConstructorRowNegative(int row) {
			assertThrows(IllegalArgumentException.class, () -> new Field(row, 0, 1),
					() -> "\nFuer den (negativen) Zeilenwert '" + row + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Parameterisierter Test fuer Konstruktor mit negativer Spalte.")
		@ParameterizedTest
		@MethodSource("generateNegativeParametervalues")
		void testConstructorColumnNegative(int column) {
			assertThrows(IllegalArgumentException.class, () -> new Field(0, column, 1),
					() -> "\nFuer den (negativen) Spaltenwert '" + column + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Parameterisierter negativer Test fuer setZeile.")
		@ParameterizedTest
		@MethodSource("generateNegativeParametervalues")
		void testSetRowNegative(int row) {
			assertThrows(IllegalArgumentException.class, () -> new Field("", 0, 0, 0, 0, "").setRow(row),
					() -> "\nBeim Setzen des (negativen) Zeilenwertes '" + row + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Parameterisierter negativer Test fuer setSpalte.")
		@ParameterizedTest
		@MethodSource("generateNegativeParametervalues")
		void testSetColumnNegative(int column) {
			assertThrows(IllegalArgumentException.class, () -> new Field("", 0, 0, 0, 0, "").setColumn(column),
					() -> "\nBeim Setzen des (negativen) Spaltenwertes '" + column + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Parameterisierter negativer Test fuer setVerwendbarkeit.")
		@ParameterizedTest
		@MethodSource("generateNegativeParametervalues")
		void testSetUsageNegative(int usage) {
			assertThrows(IllegalArgumentException.class, () -> new Field("", 0, 0, 0, 0, "").setColumn(usage),
					() -> "\nBeim Setzen des (negativen) Verwendbarkeitswertes '" + usage
							+ "' wird keine Ausnahme erzeugt.");
		}
	}
}
