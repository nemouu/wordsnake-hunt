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
			int rows = 1;
			Jungle jungle = new Jungle(rows, 0, null, "");
			assertEquals(jungle.getRows(), rows, () -> "\nDer Zeilenanzahl '" + jungle.getRows()
					+ "' entspricht nicht dem vorgegebenen Wert '" + rows + "'.");
		}

		@DisplayName("Einfacher negativer Test.")
		@Test
		void testGetColumns() {
			int columns = -1;
			assertThrows(IllegalArgumentException.class, () -> new Jungle(0, columns, null, ""),
					() -> "\nFuer die (negative) Spaltenanzahl '" + columns + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher positiver Test mit zu grosser Zahl in Zeilen.")
		@Test
		void testGetRowMax() {
			int rows = Integer.MAX_VALUE;
			assertThrows(IllegalArgumentException.class, () -> new Jungle(rows, 0, null, ""),
					() -> "\nFuer den zu grossen " + "Zeilenwert '" + rows + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher positiver Test mit zu grosser Zahl in Spalten.")
		@Test
		void testGetColumnMax() {
			int columns = Integer.MAX_VALUE;
			assertThrows(IllegalArgumentException.class, () -> new Jungle(0, columns, null, ""),
					() -> "\nFuer den zu grossen " + "Spaltenwert '" + columns + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher Test fuer toString Methode mit leerem Dschungel.")
		@Test
		void testToStringEmpty() {
			Jungle jungle = new Jungle(0, 0, null, "");
			assertEquals("", jungle.toString(), () -> "Es wird nicht der leere String erzeugt.");
		}

		@DisplayName("Einfacher Test fuer toString Methode mit normalem Dschungel.")
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
					jungle.toString(), () -> "Es wird nicht der erwartete String erzeugt.");
		}

		@DisplayName("Einfacher Test fuer toString Methode mit grossem Dschungel.")
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
					jungle.toString(), () -> "\nEs wird nicht der erwartete String erzeugt.");
		}

		@DisplayName("Einfacher Test fuer die Methode anzahlFelder.")
		@Test
		void testNumberOfFields() {
			Jungle jungle = new Jungle(13, 14, "", 1);
			for (int i = 0; i < 13; i++) {
				for (int j = 0; j < 14; j++) {
					jungle.getFields()[i][j].setCharacter("a");
				}
			}
			assertEquals(182, jungle.numberOfFields(),
					"\nDie Methode gibt nicht den richtigen Wert fuer die Dschungelgroesse "
							+ "aus. Der Dschungel hat 182 Felder aber es wurde " + jungle.numberOfFields()
							+ " ausgegeben.");
		}

		@DisplayName("Einfacher Test fuer die Methode anzahlFelder.")
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
					"\nDie Methode gibt nicht den richtigen Wert fuer die belegten Felder des Dschungels "
							+ "aus. Der Dschungel hat 175 belegte Felder aber es wurde " + jungle.numberOfFields()
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
		void testConstructorAndGetColumnPositive(int columns) {
			Jungle jungle = new Jungle(0, columns, null, "");
			assertEquals(jungle.getColumns(), columns, "\nDie Spaltenanzahl '" + jungle.getColumns()
					+ "' entspricht nicht dem vorgegebenen Wert '" + columns + "'.");
		}

		@DisplayName("Parameterisierter positiver Test fuer Konstruktor und getZeilen.")
		@ParameterizedTest
		@MethodSource("generatePositiveParametervalues")
		void testConstructorAndGetRowPositive(int rows) {
			Jungle jungle = new Jungle(rows, 0, null, "");
			assertEquals(jungle.getRows(), rows, "\nDie Zeilenanzahl '" + jungle.getRows()
					+ "' entspricht nicht dem vorgegebenen Wert '" + rows + "'.");
		}

		@DisplayName("Parameterisierter Test fuer Konstruktor mit negativer Zeilen.")
		@ParameterizedTest
		@MethodSource("generateNegativeParametervalues")
		void testConstructorRowNegative(int rows) {
			assertThrows(IllegalArgumentException.class, () -> new Jungle(rows, 0, null, ""),
					() -> "\nFuer die (negative) Zeilenanzahl '" + rows + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Parameterisierter Test fuer Konstruktor mit negativer Spalten.")
		@ParameterizedTest
		@MethodSource("generateNegativeParametervalues")
		void testConstructorColumnNegative(int columns) {
			assertThrows(IllegalArgumentException.class, () -> new Jungle(0, columns, null, ""),
					() -> "\nFuer die (negative) Spaltenanzahl '" + columns + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Parameterisierter negativer Test fuer setZeilen.")
		@ParameterizedTest
		@MethodSource("generateNegativeParametervalues")
		void testSetRowNegative(int rows) {
			assertThrows(IllegalArgumentException.class, () -> new Jungle(0, 0, null, "").setRows(rows),
					() -> "\nFuer die (negative) Zeilenanzahl '" + rows + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Parameterisierter negativer Test fuer setSpalten.")
		@ParameterizedTest
		@MethodSource("generateNegativeParametervalues")
		void testSetColumnNegative(int columns) {
			assertThrows(IllegalArgumentException.class, () -> new Jungle(0, 0, null, "").setColumns(columns),
					() -> "\nFuer die (negative) Spaltenanzahl '" + columns + "' wird keine Ausnahme erzeugt.");
		}
	}
}
