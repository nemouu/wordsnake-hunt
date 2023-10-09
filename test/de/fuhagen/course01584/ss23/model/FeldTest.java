package de.fuhagen.course01584.ss23.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;
import java.util.stream.IntStream;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import de.fuhagen.course01584.ss23.model.Field;

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
class FeldTest {

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Einfache_Tests {
		@DisplayName("Einfacher positiver Test.")
		@Test
		void testeGetZeile() {
			int zeile = 1;
			Field feld = new Field(zeile, 0, 1);
			assertEquals(feld.getZeile(), zeile, () -> "\nDer Zeilenwert '" + feld.getZeile()
					+ "' entspricht nicht dem vorgegebenen Wert '" + zeile + "'.");
		}

		@DisplayName("Einfacher positiver Test bezueglich Attribut 'punkte'.")
		@Test
		void testePunkte() {
			int punkte = 1;
			Field feld = new Field("", 0, 0, 0, punkte, "");
			assertEquals(feld.getPunkte(), punkte, () -> "\nDer Zeilenwert '" + feld.getPunkte()
					+ "' entspricht nicht dem vorgegebenen Wert '" + punkte + "'.");
		}

		@DisplayName("Einfacher negativer Test.")
		@Test
		void testeGetSpalte() {
			int spalte = -1;
			assertThrows(IllegalArgumentException.class, () -> new Field(0, spalte, 1),
					() -> "\nFuer den (negativen) Spaltenwert '" + spalte + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher positiver Test bezueglich Attribut 'punkte' mit zu grossen Wert.")
		@Test
		void testePunkteGross() {
			int punkte = Integer.MAX_VALUE;
			assertThrows(IllegalArgumentException.class, () -> new Field("", 0, 0, 0, punkte, ""),
					() -> "Fuer den" + "zu grossen Wert von 'punkte '" + punkte + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher negativer Test bezueglich Attribut 'punkte' mit zu kleinem Wert.")
		@Test
		void testePunkteKlein() {
			int punkte = Integer.MIN_VALUE;
			assertThrows(IllegalArgumentException.class, () -> new Field("", 0, 0, 0, punkte, ""),
					() -> "Fuer den" + "zu kleinen Wert von 'punkte '" + punkte + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher positiver Test von setPunkte mit zu grossen Wert.")
		@Test
		void testeSetPunkteGross() {
			int punkte = Integer.MAX_VALUE;
			assertThrows(IllegalArgumentException.class, () -> new Field("", 0, 0, 0, 0, "").setPunkte(punkte),
					() -> "Fuer den" + "zu grossen Wert von 'punkte '" + punkte + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher negativer Test von setPunkte mit zu kleinem Wert.")
		@Test
		void testeSetPunkteKlein() {
			int punkte = Integer.MIN_VALUE;
			assertThrows(IllegalArgumentException.class, () -> new Field("", 0, 0, 0, 0, "").setPunkte(punkte),
					() -> "Fuer den" + "zu grossen Wert von 'punkte '" + punkte + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher positiver Test mit zu grosser Zahl in Zeile.")
		@Test
		void testeGetZeileMax() {
			int zeile = Integer.MAX_VALUE + 1;
			assertThrows(IllegalArgumentException.class, () -> new Field(zeile, 0, 1),
					() -> "\nFuer den zu grossen " + "Zeilenwert '" + zeile + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher positiver Test mit zu grosser Zahl in Spalte.")
		@Test
		void testeGetSpalteMax() {
			int spalte = Integer.MAX_VALUE + 1;
			assertThrows(IllegalArgumentException.class, () -> new Field(0, spalte, 1),
					() -> "\nFuer den zu grossen " + "Spaltenwert '" + spalte + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher positiver Test mit zu grosser Zahl in Verwendbarkeit.")
		@Test
		void testeGetVerwMax() {
			int verw = Integer.MAX_VALUE + 1;
			assertThrows(IllegalArgumentException.class, () -> new Field("", 0, 0, verw, 0, ""),
					() -> "\nFuer den zu grossen " + "Verwendbarkeitswert '" + verw + "' wird keine Ausnahme erzeugt.");
		}
	}

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Wiederholte_Tests {
		static Random zufallszahlenGenerator;

		@BeforeAll
		static void initAll() {
			zufallszahlenGenerator = new Random(1);
		}

		@DisplayName("Wiederholter negativer Test.")
		@RepeatedTest(10)
		void testeGetSpalte(RepetitionInfo repetitionInfo) {
			int spalte = zufallszahlenGenerator.nextInt(-100000, -1);
			assertThrows(IllegalArgumentException.class, () -> new Field(0, spalte, 1),
					() -> "\nFuer den (negativen) Spaltenwert '" + spalte + "' wird keine Ausnahme erzeugt.");
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

		@DisplayName("Parameterisierter positiver Test fuer Konstruktor und getSpalte.")
		@ParameterizedTest
		@MethodSource("erzeugePositiveParameterwerte")
		void testeKonstruktorUndGetSpaltePositiv(int spalte) {
			Field feld = new Field(0, spalte, 1);
			assertEquals(feld.getSpalte(), spalte, "\nDer Spaltenwert '" + feld.getSpalte()
					+ "' entspricht nicht dem vorgegebenen Wert '" + spalte + "'.");
		}

		@DisplayName("Parameterisierter positiver Test fuer Konstruktor und getZeile.")
		@ParameterizedTest
		@MethodSource("erzeugePositiveParameterwerte")
		void testeKonstruktorUndGetZeilePositiv(int zeile) {
			Field feld = new Field(zeile, 0, 1);
			assertEquals(feld.getZeile(), zeile, "\nDer Zeilenwert '" + feld.getZeile()
					+ "' entspricht nicht dem vorgegebenen Wert '" + zeile + "'.");
		}

		@DisplayName("Parameterisierter positiver Test fuer Konstruktor und getVerwendbarkeit.")
		@ParameterizedTest
		@MethodSource("erzeugePositiveParameterwerte")
		void testeKonstruktorUndGetVerwendbarkeitPositiv(int verw) {
			Field feld = new Field("", 0, 0, verw, 0, "");
			assertEquals(feld.getVerwendbarkeit(), verw, "\nDer Verwendungswert '" + feld.getVerwendbarkeit()
					+ "' entspricht nicht dem vorgegebenen Wert '" + verw + "'.");
		}

		@DisplayName("Parameterisierter Test fuer Konstruktor mit negativer Zeile.")
		@ParameterizedTest
		@MethodSource("erzeugeNegativeParameterwerte")
		void testeKonstruktorZeileNegativ(int zeile) {
			assertThrows(IllegalArgumentException.class, () -> new Field(zeile, 0, 1),
					() -> "\nFuer den (negativen) Zeilenwert '" + zeile + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Parameterisierter Test fuer Konstruktor mit negativer Spalte.")
		@ParameterizedTest
		@MethodSource("erzeugeNegativeParameterwerte")
		void testeKonstruktorSpalteNegativ(int spalte) {
			assertThrows(IllegalArgumentException.class, () -> new Field(0, spalte, 1),
					() -> "\nFuer den (negativen) Spaltenwert '" + spalte + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Parameterisierter negativer Test fuer setZeile.")
		@ParameterizedTest
		@MethodSource("erzeugeNegativeParameterwerte")
		void testeSetZeileNegativ(int zeile) {
			assertThrows(IllegalArgumentException.class, () -> new Field("", 0, 0, 0, 0, "").setZeile(zeile),
					() -> "\nBeim Setzen des (negativen) Zeilenwertes '" + zeile + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Parameterisierter negativer Test fuer setSpalte.")
		@ParameterizedTest
		@MethodSource("erzeugeNegativeParameterwerte")
		void testeSetSpalteNegativ(int spalte) {
			assertThrows(IllegalArgumentException.class, () -> new Field("", 0, 0, 0, 0, "").setSpalte(spalte),
					() -> "\nBeim Setzen des (negativen) Spaltenwertes '" + spalte + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Parameterisierter negativer Test fuer setVerwendbarkeit.")
		@ParameterizedTest
		@MethodSource("erzeugeNegativeParameterwerte")
		void testeSetVerwendbarkeitNegativ(int verw) {
			assertThrows(IllegalArgumentException.class, () -> new Field("", 0, 0, 0, 0, "").setSpalte(verw),
					() -> "\nBeim Setzen des (negativen) Verwendbarkeitswertes '" + verw
							+ "' wird keine Ausnahme erzeugt.");
		}
	}
}
