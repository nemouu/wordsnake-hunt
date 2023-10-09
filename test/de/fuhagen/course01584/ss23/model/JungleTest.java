package de.fuhagen.course01584.ss23.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;
import java.util.stream.IntStream;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import de.fuhagen.course01584.ss23.model.Jungle;

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
	class Einfache_Tests {
		@DisplayName("Einfacher positiver Test.")
		@Test
		void testeGetZeilen() {
			int zeilen = 1;
			Jungle dschungel = new Jungle(zeilen, 0, null, "");
			assertEquals(dschungel.getZeilen(), zeilen, () -> "\nDer Zeilenanzahl '" + dschungel.getZeilen()
					+ "' entspricht nicht dem vorgegebenen Wert '" + zeilen + "'.");
		}

		@DisplayName("Einfacher negativer Test.")
		@Test
		void testeGetSpalten() {
			int spalten = -1;
			assertThrows(IllegalArgumentException.class, () -> new Jungle(0, spalten, null, ""),
					() -> "\nFuer die (negative) Spaltenanzahl '" + spalten + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher positiver Test mit zu grosser Zahl in Zeilen.")
		@Test
		void testeGetZeileMax() {
			int zeilen = Integer.MAX_VALUE;
			assertThrows(IllegalArgumentException.class, () -> new Jungle(zeilen, 0, null, ""),
					() -> "\nFuer den zu grossen " + "Zeilenwert '" + zeilen + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher positiver Test mit zu grosser Zahl in Spalten.")
		@Test
		void testeGetSpalteMax() {
			int spalten = Integer.MAX_VALUE;
			assertThrows(IllegalArgumentException.class, () -> new Jungle(0, spalten, null, ""),
					() -> "\nFuer den zu grossen " + "Spaltenwert '" + spalten + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher Test fuer toString Methode mit leerem Dschungel.")
		@Test
		void testeToStringLeer() {
			Jungle dschungel = new Jungle(0, 0, null, "");
			assertEquals("", dschungel.toString(), () -> "Es wird nicht der leere String erzeugt.");
		}

		@DisplayName("Einfacher Test fuer toString Methode mit normalem Dschungel.")
		@Test
		void testeToStringNormal() {
			Jungle dschungel = new Jungle(3, 4, "", 1);
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 4; j++) {
					dschungel.getFelder()[i][j].setZeichen("a");
				}
			}
			assertEquals(
					" (a,1,1) (a,1,1) (a,1,1) (a,1,1)\n\n (a,1,1) (a,1,1) (a,1,1) (a,1,1)\n\n (a,1,1) (a,1,1) (a,1,1) (a,1,1)\n\n",
					dschungel.toString(), () -> "Es wird nicht der erwartete String erzeugt.");
		}

		@DisplayName("Einfacher Test fuer toString Methode mit grossem Dschungel.")
		@Test
		void testeToStringGross() {
			Jungle dschungel = new Jungle(13, 14, "", 1);
			for (int i = 0; i < 13; i++) {
				for (int j = 0; j < 14; j++) {
					dschungel.getFelder()[i][j].setZeichen("a");
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
		void testeAnzahlFelder() {
			Jungle dschungel = new Jungle(13, 14, "", 1);
			for (int i = 0; i < 13; i++) {
				for (int j = 0; j < 14; j++) {
					dschungel.getFelder()[i][j].setZeichen("a");
				}
			}
			assertEquals(182, dschungel.anzahlFelder(),
					"\nDie Methode gibt nicht den richtigen Wert fuer die Dschungelgroesse "
							+ "aus. Der Dschungel hat 182 Felder aber es wurde " + dschungel.anzahlFelder()
							+ " ausgegeben.");
		}

		@DisplayName("Einfacher Test fuer die Methode anzahlFelder.")
		@Test
		void testeAnzahlBelegterFelder() {
			Jungle dschungel = new Jungle(13, 14, "", 1);
			for (int i = 0; i < 13; i++) {
				for (int j = 0; j < 14; j++) {
					dschungel.getFelder()[i][j].setZeichen("a");
				}
			}
			dschungel.getFelder()[4][7].setZeichen(null);
			dschungel.getFelder()[4][1].setZeichen(null);
			dschungel.getFelder()[5][7].setZeichen(null);
			dschungel.getFelder()[8][2].setZeichen(null);
			dschungel.getFelder()[12][1].setZeichen(null);
			dschungel.getFelder()[10][7].setZeichen(null);
			dschungel.getFelder()[4][3].setZeichen(null);
			assertEquals(175, dschungel.anzahlBelegterFelder(),
					"\nDie Methode gibt nicht den richtigen Wert fuer die belegten Felder des Dschungels "
							+ "aus. Der Dschungel hat 175 belegte Felder aber es wurde " + dschungel.anzahlFelder()
							+ " ausgegeben.");
		}
	}

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Parametrisierte_Tests {
		static Random zufallszahlenGenerator;

		@BeforeAll
		static void initAll() {
			zufallszahlenGenerator = new Random(1);
		}

		static IntStream erzeugeNegativeParameterwerte() {
			return IntStream.iterate(zufallszahlenGenerator.nextInt(-20, -1), i -> i - 10).limit(20);
		}

		static IntStream erzeugePositiveParameterwerte() {
			return IntStream.iterate(zufallszahlenGenerator.nextInt(1, 20), i -> i + 10).limit(20);
		}

		@DisplayName("Parameterisierter positiver Test fuer Konstruktor und getSpalten.")
		@ParameterizedTest
		@MethodSource("erzeugePositiveParameterwerte")
		void testeKonstruktorUndGetSpaltePositiv(int spalten) {
			Jungle dschungel = new Jungle(0, spalten, null, "");
			assertEquals(dschungel.getSpalten(), spalten, "\nDie Spaltenanzahl '" + dschungel.getSpalten()
					+ "' entspricht nicht dem vorgegebenen Wert '" + spalten + "'.");
		}

		@DisplayName("Parameterisierter positiver Test fuer Konstruktor und getZeilen.")
		@ParameterizedTest
		@MethodSource("erzeugePositiveParameterwerte")
		void testeKonstruktorUndGetZeilePositiv(int zeilen) {
			Jungle dschungel = new Jungle(zeilen, 0, null, "");
			assertEquals(dschungel.getZeilen(), zeilen, "\nDie Zeilenanzahl '" + dschungel.getZeilen()
					+ "' entspricht nicht dem vorgegebenen Wert '" + zeilen + "'.");
		}

		@DisplayName("Parameterisierter Test fuer Konstruktor mit negativer Zeilen.")
		@ParameterizedTest
		@MethodSource("erzeugeNegativeParameterwerte")
		void testeKonstruktorZeileNegativ(int zeilen) {
			assertThrows(IllegalArgumentException.class, () -> new Jungle(zeilen, 0, null, ""),
					() -> "\nFuer die (negative) Zeilenanzahl '" + zeilen + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Parameterisierter Test fuer Konstruktor mit negativer Spalten.")
		@ParameterizedTest
		@MethodSource("erzeugeNegativeParameterwerte")
		void testeKonstruktorSpalteNegativ(int spalten) {
			assertThrows(IllegalArgumentException.class, () -> new Jungle(0, spalten, null, ""),
					() -> "\nFuer die (negative) Spaltenanzahl '" + spalten + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Parameterisierter negativer Test fuer setZeilen.")
		@ParameterizedTest
		@MethodSource("erzeugeNegativeParameterwerte")
		void testeSetZeileNegativ(int zeilen) {
			assertThrows(IllegalArgumentException.class, () -> new Jungle(0, 0, null, "").setZeilen(zeilen),
					() -> "\nFuer die (negative) Zeilenanzahl '" + zeilen + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Parameterisierter negativer Test fuer setSpalten.")
		@ParameterizedTest
		@MethodSource("erzeugeNegativeParameterwerte")
		void testeSetSpalteNegativ(int spalten) {
			assertThrows(IllegalArgumentException.class, () -> new Jungle(0, 0, null, "").setSpalten(spalten),
					() -> "\nFuer die (negative) Spaltenanzahl '" + spalten + "' wird keine Ausnahme erzeugt.");
		}
	}
}
