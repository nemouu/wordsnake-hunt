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
			int zeile = 1;
			Field feld = new Field(zeile, 0, 1);
			assertEquals(feld.getRow(), zeile, () -> "\nDer Zeilenwert '" + feld.getRow()
					+ "' entspricht nicht dem vorgegebenen Wert '" + zeile + "'.");
		}

		@DisplayName("Einfacher positiver Test bezueglich Attribut 'punkte'.")
		@Test
		void testPoints() {
			int punkte = 1;
			Field feld = new Field("", 0, 0, 0, punkte, "");
			assertEquals(feld.getPoints(), punkte, () -> "\nDer Zeilenwert '" + feld.getPoints()
					+ "' entspricht nicht dem vorgegebenen Wert '" + punkte + "'.");
		}

		@DisplayName("Einfacher negativer Test.")
		@Test
		void testGetColumn() {
			int spalte = -1;
			assertThrows(IllegalArgumentException.class, () -> new Field(0, spalte, 1),
					() -> "\nFuer den (negativen) Spaltenwert '" + spalte + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher positiver Test bezueglich Attribut 'punkte' mit zu grossen Wert.")
		@Test
		void testPointsLarge() {
			int punkte = Integer.MAX_VALUE;
			assertThrows(IllegalArgumentException.class, () -> new Field("", 0, 0, 0, punkte, ""),
					() -> "Fuer den" + "zu grossen Wert von 'punkte '" + punkte + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher negativer Test bezueglich Attribut 'punkte' mit zu kleinem Wert.")
		@Test
		void testPointsSmall() {
			int punkte = Integer.MIN_VALUE;
			assertThrows(IllegalArgumentException.class, () -> new Field("", 0, 0, 0, punkte, ""),
					() -> "Fuer den" + "zu kleinen Wert von 'punkte '" + punkte + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher positiver Test von setPunkte mit zu grossen Wert.")
		@Test
		void testSetPointsLarge() {
			int punkte = Integer.MAX_VALUE;
			assertThrows(IllegalArgumentException.class, () -> new Field("", 0, 0, 0, 0, "").setPoints(punkte),
					() -> "Fuer den" + "zu grossen Wert von 'punkte '" + punkte + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher negativer Test von setPunkte mit zu kleinem Wert.")
		@Test
		void testSetPointsSmall() {
			int punkte = Integer.MIN_VALUE;
			assertThrows(IllegalArgumentException.class, () -> new Field("", 0, 0, 0, 0, "").setPoints(punkte),
					() -> "Fuer den" + "zu grossen Wert von 'punkte '" + punkte + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher positiver Test mit zu grosser Zahl in Zeile.")
		@Test
		void testGetRowMax() {
			int zeile = Integer.MAX_VALUE + 1;
			assertThrows(IllegalArgumentException.class, () -> new Field(zeile, 0, 1),
					() -> "\nFuer den zu grossen " + "Zeilenwert '" + zeile + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher positiver Test mit zu grosser Zahl in Spalte.")
		@Test
		void testGetColumnMax() {
			int spalte = Integer.MAX_VALUE + 1;
			assertThrows(IllegalArgumentException.class, () -> new Field(0, spalte, 1),
					() -> "\nFuer den zu grossen " + "Spaltenwert '" + spalte + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher positiver Test mit zu grosser Zahl in Verwendbarkeit.")
		@Test
		void testGetUsageMax() {
			int verw = Integer.MAX_VALUE + 1;
			assertThrows(IllegalArgumentException.class, () -> new Field("", 0, 0, verw, 0, ""),
					() -> "\nFuer den zu grossen " + "Verwendbarkeitswert '" + verw + "' wird keine Ausnahme erzeugt.");
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
			int spalte = randomNumberGenerator.nextInt(-100000, -1);
			assertThrows(IllegalArgumentException.class, () -> new Field(0, spalte, 1),
					() -> "\nFuer den (negativen) Spaltenwert '" + spalte + "' wird keine Ausnahme erzeugt.");
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
		void testConstructorAndGetColumnPositive(int spalte) {
			Field feld = new Field(0, spalte, 1);
			assertEquals(feld.getColumn(), spalte, "\nDer Spaltenwert '" + feld.getColumn()
					+ "' entspricht nicht dem vorgegebenen Wert '" + spalte + "'.");
		}

		@DisplayName("Parameterisierter positiver Test fuer Konstruktor und getZeile.")
		@ParameterizedTest
		@MethodSource("generatePositiveParametervalues")
		void testConstructorAndGetRowPositive(int zeile) {
			Field feld = new Field(zeile, 0, 1);
			assertEquals(feld.getRow(), zeile, "\nDer Zeilenwert '" + feld.getRow()
					+ "' entspricht nicht dem vorgegebenen Wert '" + zeile + "'.");
		}

		@DisplayName("Parameterisierter positiver Test fuer Konstruktor und getVerwendbarkeit.")
		@ParameterizedTest
		@MethodSource("generatePositiveParametervalues")
		void testConstructorAndGetUsagePositive(int verw) {
			Field feld = new Field("", 0, 0, verw, 0, "");
			assertEquals(feld.getUsage(), verw, "\nDer Verwendungswert '" + feld.getUsage()
					+ "' entspricht nicht dem vorgegebenen Wert '" + verw + "'.");
		}

		@DisplayName("Parameterisierter Test fuer Konstruktor mit negativer Zeile.")
		@ParameterizedTest
		@MethodSource("generateNegativeParametervalues")
		void testConstructorRowNegative(int zeile) {
			assertThrows(IllegalArgumentException.class, () -> new Field(zeile, 0, 1),
					() -> "\nFuer den (negativen) Zeilenwert '" + zeile + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Parameterisierter Test fuer Konstruktor mit negativer Spalte.")
		@ParameterizedTest
		@MethodSource("generateNegativeParametervalues")
		void testConstructorColumnNegative(int spalte) {
			assertThrows(IllegalArgumentException.class, () -> new Field(0, spalte, 1),
					() -> "\nFuer den (negativen) Spaltenwert '" + spalte + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Parameterisierter negativer Test fuer setZeile.")
		@ParameterizedTest
		@MethodSource("generateNegativeParametervalues")
		void testSetRowNegative(int zeile) {
			assertThrows(IllegalArgumentException.class, () -> new Field("", 0, 0, 0, 0, "").setRow(zeile),
					() -> "\nBeim Setzen des (negativen) Zeilenwertes '" + zeile + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Parameterisierter negativer Test fuer setSpalte.")
		@ParameterizedTest
		@MethodSource("generateNegativeParametervalues")
		void testSetColumnNegative(int spalte) {
			assertThrows(IllegalArgumentException.class, () -> new Field("", 0, 0, 0, 0, "").setColumn(spalte),
					() -> "\nBeim Setzen des (negativen) Spaltenwertes '" + spalte + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Parameterisierter negativer Test fuer setVerwendbarkeit.")
		@ParameterizedTest
		@MethodSource("generateNegativeParametervalues")
		void testSetUsageNegative(int verw) {
			assertThrows(IllegalArgumentException.class, () -> new Field("", 0, 0, 0, 0, "").setColumn(verw),
					() -> "\nBeim Setzen des (negativen) Verwendbarkeitswertes '" + verw
							+ "' wird keine Ausnahme erzeugt.");
		}
	}
}
