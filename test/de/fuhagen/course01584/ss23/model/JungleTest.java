package de.fuhagen.course01584.ss23.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;
import java.util.stream.IntStream;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * In der Klasse DschungelTest werden die Methoden der Klasse Dschungel
 * getestet. Hierbei werden alle Methoden parametrisierten Tests unterzogen, die
 * Ausnahmen hervorrufen oder die sonstige Logik enthalten. In diesem Fall sind
 * das vor allem die Konstruktoren und die Getter und Setter fuer 'zeilen' und
 * 'spalten'. Die uebrigen Getter und Setter wurden der Einfachheit wegen
 * uebersprungen. Zusaetzlich dazu werden bestimmte Sonderfaelle in einfachen
 * Test betrachtet und es wurde auch die Methode toString der Klasse Dschungel
 * mit einfachen Tests getestet.
 * 
 * @author Philip Redecker
 */
class JungleTest {

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Simple_Tests {
		@DisplayName("Einfacher positiver Test.")
		@Test
		void testGetRows() {
			int zeilen = 1;
			Jungle dschungel = new Jungle(zeilen, 0, null, "");
			assertEquals(dschungel.getRows(), zeilen, () -> "\nDer Zeilenanzahl '" + dschungel.getRows()
					+ "' entspricht nicht dem vorgegebenen Wert '" + zeilen + "'.");
		}

		@DisplayName("Einfacher negativer Test.")
		@Test
		void testGetColumns() {
			int spalten = -1;
			assertThrows(IllegalArgumentException.class, () -> new Jungle(0, spalten, null, ""),
					() -> "\nFuer die (negative) Spaltenanzahl '" + spalten + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher positiver Test mit zu grosser Zahl in Zeilen.")
		@Test
		void testGetRowMax() {
			int zeilen = Integer.MAX_VALUE;
			assertThrows(IllegalArgumentException.class, () -> new Jungle(zeilen, 0, null, ""),
					() -> "\nFuer den zu grossen " + "Zeilenwert '" + zeilen + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher positiver Test mit zu grosser Zahl in Spalten.")
		@Test
		void testGetColumnMax() {
			int spalten = Integer.MAX_VALUE;
			assertThrows(IllegalArgumentException.class, () -> new Jungle(0, spalten, null, ""),
					() -> "\nFuer den zu grossen " + "Spaltenwert '" + spalten + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher Test fuer toString Methode mit leerem Dschungel.")
		@Test
		void testToStringEmpty() {
			Jungle dschungel = new Jungle(0, 0, null, "");
			assertEquals("", dschungel.toString(), () -> "Es wird nicht der leere String erzeugt.");
		}

		@DisplayName("Einfacher Test fuer toString Methode mit normalem Dschungel.")
		@Test
		void testToStringNormal() {
			Jungle dschungel = new Jungle(3, 4, "", 1);
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 4; j++) {
					dschungel.getFields()[i][j].setCharacter("a");
				}
			}
			assertEquals(
					" (a,1,1) (a,1,1) (a,1,1) (a,1,1)\n\n (a,1,1) (a,1,1) (a,1,1) (a,1,1)\n\n (a,1,1) (a,1,1) (a,1,1) (a,1,1)\n\n",
					dschungel.toString(), () -> "Es wird nicht der erwartete String erzeugt.");
		}

		@DisplayName("Einfacher Test fuer toString Methode mit grossem Dschungel.")
		@Test
		void testToStringLarge() {
			Jungle dschungel = new Jungle(13, 14, "", 1);
			for (int i = 0; i < 13; i++) {
				for (int j = 0; j < 14; j++) {
					dschungel.getFields()[i][j].setCharacter("a");
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
					dschungel.toString(), () -> "\nEs wird nicht der erwartete String erzeugt.");
		}

		@DisplayName("Einfacher Test fuer die Methode anzahlFelder.")
		@Test
		void testNumberOfFields() {
			Jungle dschungel = new Jungle(13, 14, "", 1);
			for (int i = 0; i < 13; i++) {
				for (int j = 0; j < 14; j++) {
					dschungel.getFields()[i][j].setCharacter("a");
				}
			}
			assertEquals(182, dschungel.numberOfFields(),
					"\nDie Methode gibt nicht den richtigen Wert fuer die Dschungelgroesse "
							+ "aus. Der Dschungel hat 182 Felder aber es wurde " + dschungel.numberOfFields()
							+ " ausgegeben.");
		}

		@DisplayName("Einfacher Test fuer die Methode anzahlFelder.")
		@Test
		void testNumberOfUsedFields() {
			Jungle dschungel = new Jungle(13, 14, "", 1);
			for (int i = 0; i < 13; i++) {
				for (int j = 0; j < 14; j++) {
					dschungel.getFields()[i][j].setCharacter("a");
				}
			}
			dschungel.getFields()[4][7].setCharacter(null);
			dschungel.getFields()[4][1].setCharacter(null);
			dschungel.getFields()[5][7].setCharacter(null);
			dschungel.getFields()[8][2].setCharacter(null);
			dschungel.getFields()[12][1].setCharacter(null);
			dschungel.getFields()[10][7].setCharacter(null);
			dschungel.getFields()[4][3].setCharacter(null);
			assertEquals(175, dschungel.numberOfTakenFields(),
					"\nDie Methode gibt nicht den richtigen Wert fuer die belegten Felder des Dschungels "
							+ "aus. Der Dschungel hat 175 belegte Felder aber es wurde " + dschungel.numberOfFields()
							+ " ausgegeben.");
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

		@DisplayName("Parameterisierter positiver Test fuer Konstruktor und getSpalten.")
		@ParameterizedTest
		@MethodSource("generatePositiveParametervalues")
		void testConstructorAndGetColumnPositive(int spalten) {
			Jungle dschungel = new Jungle(0, spalten, null, "");
			assertEquals(dschungel.getColumns(), spalten, "\nDie Spaltenanzahl '" + dschungel.getColumns()
					+ "' entspricht nicht dem vorgegebenen Wert '" + spalten + "'.");
		}

		@DisplayName("Parameterisierter positiver Test fuer Konstruktor und getZeilen.")
		@ParameterizedTest
		@MethodSource("generatePositiveParametervalues")
		void testConstructorAndGetRowPositive(int zeilen) {
			Jungle dschungel = new Jungle(zeilen, 0, null, "");
			assertEquals(dschungel.getRows(), zeilen, "\nDie Zeilenanzahl '" + dschungel.getRows()
					+ "' entspricht nicht dem vorgegebenen Wert '" + zeilen + "'.");
		}

		@DisplayName("Parameterisierter Test fuer Konstruktor mit negativer Zeilen.")
		@ParameterizedTest
		@MethodSource("generateNegativeParametervalues")
		void testConstructorRowNegative(int zeilen) {
			assertThrows(IllegalArgumentException.class, () -> new Jungle(zeilen, 0, null, ""),
					() -> "\nFuer die (negative) Zeilenanzahl '" + zeilen + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Parameterisierter Test fuer Konstruktor mit negativer Spalten.")
		@ParameterizedTest
		@MethodSource("generateNegativeParametervalues")
		void testConstructorColumnNegative(int spalten) {
			assertThrows(IllegalArgumentException.class, () -> new Jungle(0, spalten, null, ""),
					() -> "\nFuer die (negative) Spaltenanzahl '" + spalten + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Parameterisierter negativer Test fuer setZeilen.")
		@ParameterizedTest
		@MethodSource("generateNegativeParametervalues")
		void testSetRowNegative(int zeilen) {
			assertThrows(IllegalArgumentException.class, () -> new Jungle(0, 0, null, "").setRows(zeilen),
					() -> "\nFuer die (negative) Zeilenanzahl '" + zeilen + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Parameterisierter negativer Test fuer setSpalten.")
		@ParameterizedTest
		@MethodSource("generateNegativeParametervalues")
		void testSetColumnNegative(int spalten) {
			assertThrows(IllegalArgumentException.class, () -> new Jungle(0, 0, null, "").setColumns(spalten),
					() -> "\nFuer die (negative) Spaltenanzahl '" + spalten + "' wird keine Ausnahme erzeugt.");
		}
	}
}
